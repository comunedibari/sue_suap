/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 *  
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * 
 * For convenience a plain text copy of the English version
 * of the Licence can be found in the file LICENCE.txt in
 * the top-level directory of this software distribution.
 * 
 * You may obtain a copy of the Licence in any of 22 European
 * Languages at:
 * 
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.console.persistence.beans.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.sql.DataSource;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.util.Assert;

import it.people.console.persistence.dao.QueryRunner;
import it.people.console.persistence.exceptions.LazyPagedListHolderException;
import it.people.console.persistence.jdbc.core.ColumnHeaderInformation;
import it.people.console.persistence.jdbc.core.ColumnMetaData;
import it.people.console.persistence.jdbc.support.Decodable;
import it.people.console.persistence.jdbc.support.IRowsStatusModeler;
import it.people.console.security.AbstractCommand;
import it.people.console.security.Command;
import it.people.console.security.InputCommand;
import it.people.console.system.AbstractLogger;
import it.people.console.utils.StringUtils;
import it.people.feservice.beans.interfaces.IpagedArrayResult;

/**
 * @author Riccardo Forafo'
 * @version 1.0
 * @created 23-nov-2010 19:00:36
 */
public abstract class AbstractLazyPagedListHolder extends AbstractLogger implements ILazyPagedListHolder {

	protected static final int DEFAULT_PAGE_SIZE = 10;
	protected static final int DEFAULT_MAX_LINKED_PAGES = 1;
	protected static final String COUNT_COLUMN_NAME = "elementsCount";
	protected static final String QUERY_PLACE_HOLDER = "query";
	protected static final String FILTERS_PLACE_HOLDER = "filters";
	protected static final String ORDER_BY_PLACE_HOLDER = "orderby";
	protected static final String LOWER_PAGE_LIMIT = "lowerPageLimit";
	protected static final String UPPER_PAGE_LIMIT = "upperPageLimit";
	protected List<?> pageList;
	protected List<FilterProperties> filters;
	protected SqlRowSet pageRowSet;
	protected String query;
	protected String backedQuery;
	protected String backedCountQuery;
	protected int pageSize = DEFAULT_PAGE_SIZE;
	protected int page = 1;
	protected boolean newPageSet;
	protected Map<String, ColumnMetaData> columnsMetaData = null;
	protected List<ColumnHeaderInformation> columnsAliases = new ArrayList<ColumnHeaderInformation>();
	protected List<Command> rowActions = new ArrayList<Command>();
	protected List<String> visibleColumnsNames = new ArrayList<String>();
	protected List<String> rowColumnsIdentifiers = new ArrayList<String>();
	protected List<String> visibleColumnsLabels = new ArrayList<String>();
	protected List<String> editableRowColumns = new ArrayList<String>();
	protected Map<String, String> columnsOrder = new HashMap<String, String>();
	protected EditableRowActions editableRowActions = null;
	protected Map<String, Object> editableRowModelers = null;
	protected Map<String, Object> rowModelers = null;
	protected IRowsStatusModeler<Integer> rowsStatusModelers;
	protected String id;
	protected boolean editableRows = false;
	protected boolean editMode = false;
	protected boolean canAddRow = false;
	protected boolean newRowEditMode = false;
	protected Map<String, Decodable> converters = null;
	protected Map<String, Vector<String>> extendedData = null;	
	protected Map<String, String> columnsQueries = null;
	//private int maxLinkedPages = DEFAULT_MAX_LINKED_PAGES;
	protected boolean deleteActionEnabled = true;
	
	protected int queryCount = 0;
	protected int lowerPageLimit = 1;
	protected int upperPageLimit;
	protected DataSource dataSource;

	/**
	 * 
	 * @param queryRunner
	 * @param query
	 * @param pageSize
	 * @throws LazyPagedListHolderException 
	 */
	protected AbstractLazyPagedListHolder(final String pagedListId, final DataSource dataSource, 
			final String query, final int pageSize, final List<String> rowColumnsIdentifiers, 
			final List<Command> rowActions) throws LazyPagedListHolderException{

		Assert.hasText(query, "Query must not be null or an empty string");
		this.pageSize = pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE;
		this.query = escapeSpecialCharacters(query);
		this.setRowColumnsIdentifiers(rowColumnsIdentifiers);
		this.setRowActions(rowActions);
		this.setBackedQuery(this.query);
		this.setBackedCountQuery(this.query);
		this.setDataSource(dataSource);
		this.setPagedListId(pagedListId);

		internalInit();
		
		this.setUpperPageLimit(this.getPageSize());
		this.setQueryCount(countQueryElements(this.getBackedCountQuery()));
		this.setPageList(boundPageList(this.getBackedQuery(), this.getLowerPageLimit(), 
				this.getUpperPageLimit()));
	}

	
	
	/**
	 * This constructor use an object (instead of Datasource and query) to extract data 
	 * 
	 * @param pagedListId
	 * @param pageSize
	 * @param rowColumnsIdentifiers the attributes names to extract data from result data 
	 * @param methodName the method to call to fetch data. This <em> must </em> return an object implementing {@link IpagedArrayResult}
	 * @param methodParamTypes the java types of params
	 * @param methodParams the param to call the method. This Array <em> must </em> include two mandatory Integer params: lowerPageLimit and pageSize.
	 * @param sourceObject 
	 * @param sourceObject2 
	 * @throws LazyPagedListHolderException
	 */
	protected AbstractLazyPagedListHolder(final String pagedListId,
			final int pageSize, final List<String> rowColumnsIdentifiers, List<String> visibleColumnsLabels,
			Object sourceObject, String methodName, Class[] methodParamTypes,
			Object[] methodParams) throws LazyPagedListHolderException {

		
		this.setRowColumnsIdentifiers(rowColumnsIdentifiers);
		this.setVisibleColumnsLabels(visibleColumnsLabels);
		this.setPagedListId(pagedListId);
		
		//Page params
		this.pageSize = pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE;
		this.setUpperPageLimit(this.getPageSize());		
		
		//Datasource and query not used
		this.setDataSource(null);
		this.query = "NOT_USED";	
		this.setBackedQuery(this.query);
		this.setBackedCountQuery(this.query);
		
		//Editable row settings
		this.setEditableRows(false);
		this.setEditableRowActions(initCommandActions());
		this.setCanAddRow(false);
		
		internalInit();
		
		//Set Initial page content and total count
		this.setPageList(boundObjectPageList(sourceObject, methodName, methodParamTypes, methodParams));
	
	}
	
	
	
	protected AbstractLazyPagedListHolder(final String pagedListId, final DataSource dataSource, 
			final String query, final int pageSize, final List<String> rowColumnsIdentifiers, 
			final List<String> editableRowColumns, final boolean canAddRow) throws LazyPagedListHolderException{

		Assert.hasText(query, "Query must not be null or an empty string");
		this.pageSize = pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE;
		this.setEditableRows(true);
		this.setRowColumnsIdentifiers(rowColumnsIdentifiers);
		this.setEditableRowColumns(editableRowColumns);
		this.query = escapeSpecialCharacters(query);
		this.setBackedQuery(this.query);
		this.setBackedCountQuery(this.query);
		this.setDataSource(dataSource);
		this.setPagedListId(pagedListId);
		this.setEditableRowActions(initCommandActions());
		this.setCanAddRow(canAddRow);

		internalInit();
		
		this.setUpperPageLimit(this.getPageSize());
		this.setQueryCount(countQueryElements(this.getBackedCountQuery()));
		this.setPageList(boundPageList(this.getBackedQuery(), this.getLowerPageLimit(), this.getUpperPageLimit()));
		
	}
	
	public final String getQuery(){
		return query;
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#setPageSize(int)
	 */
	public abstract void setPageSize(final int pageSize);

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#getPageSize()
	 */
	public final int getPageSize(){
		return this.pageSize;
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#getPageList()
	 */
	public final List<?> getPageList(){
		return pageList;
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#getPage()
	 */
	public final int getPage(){
		return this.page;
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#getPageCount()
	 */
	public final int getPageCount(){
		float nrOfPages = (float) this.getQueryCount() / this.getPageSize();
		return (int)((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages);
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#isFirstPage()
	 */
	public final boolean isFirstPage(){
		return this.getPage() == 1;
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#isLastPage()
	 */
	public final boolean isLastPage(){
		return this.getPage() == this.getPageCount();
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#nextPage()
	 */
	public abstract void nextPage();

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#previousPage()
	 */
	public abstract void previousPage();

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#setPage(int)
	 */
	public abstract void setPage(final int pageNumber);

	/**
	 * @return
	 */
	protected String getBackedQuery(){
		return backedQuery;
	}

	/**
	 * 
	 * @param query
	 */
	protected abstract void setBackedQuery(final String query);
	
	/**
	 * @return
	 */
	protected String getBackedCountQuery(){
		return backedCountQuery;
	}

	/**
	 * 
	 * @param query
	 */
	protected abstract void setBackedCountQuery(final String query);

	/**
	 * 
	 * @param isNewPageSet
	 */
	protected void setNewPageSet(final boolean isNewPageSet){
		this.newPageSet = isNewPageSet;
	}

	/**
	 * @return
	 */
	protected boolean isNewPageSet(){
		return this.newPageSet;
	}

	/**
	 * @return
	 */
	protected int getLowerPageLimit(){
		return lowerPageLimit;
	}

	/**
	 * 
	 * @param lowerPageLimit
	 */
	protected void setLowerPageLimit(final int lowerPageLimit){
		this.lowerPageLimit = lowerPageLimit;
	}

	/**
	 * @return
	 */
	protected int getUpperPageLimit(){
		return upperPageLimit;
	}

	/**
	 * 
	 * @param upperPageLimit
	 */
	protected void setUpperPageLimit(final int upperPageLimit){
		this.upperPageLimit = upperPageLimit;
	}

	/**
	 * @return
	 */
	protected int getQueryCount(){
		return queryCount;
	}
	
	/**
	 * 
	 * @param queryCount
	 */
	protected void setQueryCount(final int queryCount){
		this.queryCount = queryCount;
	}

	/**
	 * @return the visibleColumnsLabels
	 */
	protected List<String> getVisibleColumnsLabels() {
		return visibleColumnsLabels;
	}



	/**
	 * @param visibleColumnsLabels the visibleColumnsLabels to set
	 */
	protected void setVisibleColumnsLabels(List<String> visibleColumnsLabels) {
		this.visibleColumnsLabels = visibleColumnsLabels;
	}
	
	
	
	
	/**
	 * 
	 * @param pageList
	 */
	protected void setPageList(final List<?> pageList){
		this.pageList = pageList;
	}

	/**
	 * 
	 * @param query
	 * @param queryRunner
	 * @throws LazyPagedListHolderException 
	 */
	protected int countQueryElements(final String query) throws LazyPagedListHolderException {

		int result = -1;
		try {
			SqlRowSet sqlRowSet = QueryRunner.queryForRowSet(dataSource, query);
			sqlRowSet.next();
			result = sqlRowSet.getInt(COUNT_COLUMN_NAME);
		} catch (Exception e) {
			throw new LazyPagedListHolderException(e);
		}
		return result;
				
	}

	
	/**
	 * 
	 * @param sourceObject
	 * @param methodName
	 * @param methodParamTypes
	 * @param methodParams
	 * @return
	 */
	protected List <?> boundObjectPageList(Object sourceObject, final String methodName, 
			final Class[] methodParamTypes, final Object[] methodParams) {
		
		throw new NotImplementedException("This method needs to be implemented to use Object version of LazyPagedListHolder.");
	}
	
	/**
	 * 
	 * @param queryRunner
	 * @param query
	 * @param lowerLimit
	 * @param upperLimit
	 */
	protected abstract List<?> boundPageList(final String query, final int lowerLimit, final int upperLimit);

	/**
	 * @param filters
	 * @param query
	 * @param lowerLimit
	 * @param upperLimit
	 * @return
	 */
	protected abstract List<?> boundFilteredPageList(final List<FilterProperties> filters, final String query, final int lowerLimit, final int upperLimit);
	
	/**
	 * @param query
	 * @param lowerLimit
	 * @param upperLimit
	 * @return
	 */
	protected abstract SqlRowSet boundPageSqlRowSet(final String query, final int lowerLimit, final int upperLimit);
	
	/**
	 * 
	 * @param query
	 */
	protected final String escapeSpecialCharacters(final String query){

		String result = query;
		String buffer = "";
		ArrayList<Character> specialChars = new ArrayList<Character>();
		specialChars.add('$');
		specialChars.add('(');
		specialChars.add(')');
		specialChars.add('*');
		specialChars.add('+');
		specialChars.add('-');
		specialChars.add('.');
		specialChars.add('?');
		specialChars.add('[');
		specialChars.add('\\');
		specialChars.add(']');
		specialChars.add('^');
		specialChars.add('{');
		specialChars.add('|');
		specialChars.add('}');
		for(int index = 0; index < query.length(); index++) {
			char character = query.charAt(index);
			if (specialChars.contains(character)) {
				buffer += "\\" + character;
			}
			else {
				buffer += character;
			}
		}
		if (!StringUtils.isEmptyString(buffer)) {
			result = buffer.toString();
		}
		return result;
		
	}

	/**
	 * @return
	 */
	protected DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource
	 */
	protected void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * @param query
	 */
	protected void setQuery(String query) {
		this.query = query;
	}

	/**
	 * 
	 */
	protected void internalInit() {
		this.setExtendedColumnsData(new HashMap<String, Vector<String>>());
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#applyFilters(java.util.List)
	 */
	public void applyFilters(final List<FilterProperties> filters) throws LazyPagedListHolderException {
		this.setFilters(filters);
		if (filters != null && !filters.isEmpty()) {
			internalApplyFilters(filters);
		}
	}
	
	/**
	 * @param filters
	 */
	protected abstract void internalApplyFilters(final List<FilterProperties> filters) throws LazyPagedListHolderException;
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#removeFilters()
	 */
	public abstract void removeFilters() throws LazyPagedListHolderException;

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#update()
	 */
	public abstract void update();
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#applyOrder(java.lang.String, java.lang.String)
	 */
	public abstract void applyOrder(String orderColumn, String orderType) throws LazyPagedListHolderException;

	/**
	 * @param filters
	 */
	protected void setFilters(List<FilterProperties> filters) {
		this.filters = filters;
	}
	
	/**
	 * @return
	 */
	protected List<FilterProperties> getFilters() {
		return this.filters;
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#firstPage()
	 */
	public void firstPage() {
		this.setPage(1);
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#lastPage()
	 */
	public void lastPage() {
		this.setPage(this.getPageCount());
	}

	/**
	 * @param columnsMetaData
	 */
	protected void setColumnsMetaData(Map<String, ColumnMetaData> columnsMetaData) {
		this.columnsMetaData = columnsMetaData;
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#getColumnsMetaData()
	 */
	public final Map<String, ColumnMetaData> getColumnsMetaData() {
		return columnsMetaData;
	}
	
	/**
	 * @param columnsAliases the columnsAliases to set
	 */
	protected void setColumnsAliases(List<ColumnHeaderInformation> columnsAliases) {
		this.columnsAliases = columnsAliases;
	}

	/**
	 * @return the columnsAliases
	 */
	public final List<ColumnHeaderInformation> getColumnsAliases() {
		return columnsAliases;
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#setRowActions(java.util.List)
	 */
	public final void setRowActions(List<Command> rowActions) {
		this.rowActions = rowActions;
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#getRowActions()
	 */
	public final List<Command> getRowActions() {
		return rowActions;
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#setVisibleColumnsNames(java.util.List)
	 */
	public final void setVisibleColumnsNames(List<String> visibleColumnsNames) {
		this.visibleColumnsNames = visibleColumnsNames;
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#getVisibleColumnsNames()
	 */
	public final List<String> getVisibleColumnsNames() {
		return this.visibleColumnsNames;
	}

	/**
	 * @param rowColumnsIdentifiers
	 */
	protected void setRowColumnsIdentifiers(List<String> rowColumnsIdentifiers) {
		this.rowColumnsIdentifiers = rowColumnsIdentifiers;
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#getRowColumnsIdentifiers()
	 */
	public List<String> getRowColumnsIdentifiers() {
		return this.rowColumnsIdentifiers;
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#getPagedListId()
	 */
	public final String getPagedListId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	protected void setPagedListId(String id) {
		this.id = id;
	}

	/**
	 * @return the editableRows
	 */
	protected boolean isEditableRows() {
		return editableRows;
	}

	/**
	 * @param canAddRow the canAddRow to set
	 */
	protected void setCanAddRow(boolean canAddRow) {
		this.canAddRow = canAddRow;
	}

	/**
	 * @param newRowEditMode the newRowEditMode to set
	 */
	public void setNewRowEditMode(boolean newRowEditMode) {
		this.newRowEditMode = newRowEditMode;
	}

	/**
	 * @param editableRows the editableRows to set
	 */
	protected void setEditableRows(boolean editableRows) {
		this.editableRows = editableRows;
	}

	/**
	 * @return the editableRowActions
	 */
	public EditableRowActions getEditableRowActions() {
		return editableRowActions;
	}

	/**
	 * @param editableRowActions the editableRowActions to set
	 */
	protected void setEditableRowActions(EditableRowActions editableRowActions) {
		this.editableRowActions = editableRowActions;
	}

	/**
	 * @return the editMode
	 */
	public boolean isEditMode() {
		return editMode;
	}

	/**
	 * @return the canAddRow
	 */
	public final boolean isCanAddRow() {
		return canAddRow;
	}

	/**
	 * @return the newRowEditMode
	 */
	public final boolean isNewRowEditMode() {
		return newRowEditMode;
	}

	/**
	 * @param editMode the editMode to set
	 */
	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	/**
	 * @param editableRowColumns the editableRowColumns to set
	 */
	protected void setEditableRowColumns(List<String> editableRowColumns) {
		this.editableRowColumns = editableRowColumns;
	}

	/**
	 * @return the editableRowColumns
	 */
	public final List<String> getEditableRowColumns() {
		return editableRowColumns;
	}
	
	/**
	 * @return
	 */
	protected EditableRowActions initCommandActions() {

		EditableRowActions result = null;
		
		Command deleteAction = new InputCommand("delete", "delete", null, 
				"delete.png", "delete-dis.png", AbstractCommand.CommandActions.delete);
		Command editAction = new InputCommand("edit", "edit", null, 
				"edit.png", "edit-dis.png", AbstractCommand.CommandActions.edit);
		Command cancelAction = new InputCommand("cancel", "cancel", null, 
				"cancel.png", "cancel-dis.png", AbstractCommand.CommandActions.cancel);
		Command saveAction = new InputCommand("save", "save", null, 
				"confirm.png", "confirm-dis.png", AbstractCommand.CommandActions.save);

		Command cancelNewAction = new InputCommand("cancelNew", "cancelNew", null, 
				"cancel.png", "cancel-dis.png", AbstractCommand.CommandActions.cancelNew);
		Command saveNewAction = new InputCommand("saveNew", "saveNew", null, 
				"confirm.png", "confirm-dis.png", AbstractCommand.CommandActions.saveNew);
		
		result = new EditableRowActions(deleteAction, editAction, 
				cancelAction, saveAction, cancelNewAction, saveNewAction);
		
		result.setDeleteActionEnabled(this.isDeleteActionEnabled());
		
		return result;
		
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#getConverters()
	 */
	public Map<String, Decodable> getConverters() {
		return this.converters;
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#setConverters(java.util.Map)
	 */
	public void setConverters(Map<String, Decodable> converters) {
		this.converters = converters;
	}

	/**
	 * @return the columnsOrder
	 */
	protected Map<String, String> getColumnsOrder() {
		return columnsOrder;
	}

	/**
	 * @param orderColumn
	 * @param orderType
	 * @return
	 */
	protected String updateColumnSorting(String orderColumn, String orderType) {
		
		String result = " order by ";
		boolean isAddedColumns = false;
		
		if (orderType == null && this.getColumnsOrder().containsKey(orderColumn)) {
			this.getColumnsOrder().remove(orderColumn);
		}
		else {
			this.getColumnsOrder().put(orderColumn, orderType);
		}
		int index = 1;
		for(String column : this.getColumnsOrder().keySet()) {
			result += column + " " + this.getColumnsOrder().get(column) + " ";
			if (index < this.getColumnsOrder().size()) {
				result += ", ";
			}
			isAddedColumns = true;
			index++;
		}
		
		return isAddedColumns ? result + " " : "";
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#setEditableRowModelers(java.util.Map)
	 */
	public void setEditableRowModelers(Map<String, Object> editableRowModelers) {
		this.editableRowModelers = editableRowModelers;
	}

	public Map<String, Object> getEditableRowModelers() {
		return this.editableRowModelers;
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#getRowModelers()
	 */
	public Map<String, Object> getRowModelers() {
		return this.rowModelers;
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#setRowModelers(java.util.Map)
	 */
	public void setRowModelers(Map<String, Object> rowModelers) {
		this.rowModelers = rowModelers;
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#setRowsStatusModelers(it.people.console.persistence.jdbc.support.IRowsStatusModeler)
	 */
	public void setRowsStatusModelers(IRowsStatusModeler<Integer> rowsStatusModelers) {
		this.rowsStatusModelers = rowsStatusModelers;
	}
	
	/**
	 * @return
	 */
	public IRowsStatusModeler<Integer> getRowsStatusModelers() {
		return this.rowsStatusModelers;
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#setExtendedColumnsData(java.util.Map)
	 */
	public final void setExtendedColumnsData(Map<String, Vector<String>> extendedData) {
		this.extendedData = extendedData;
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#getExtendedColumnsData()
	 */
	public final Map<String, Vector<String>> getExtendedColumnsData() {
		return this.extendedData;
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#getColumnsQueries()
	 */
	public final Map<String, String> getColumnsQueries() {
		return this.columnsQueries;
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.ILazyPagedListHolder#setColumnsQueries(java.util.Map)
	 */
	public final void setColumnsQueries(Map<String, String> columnsQueries) {
		this.columnsQueries = columnsQueries;
	}

	/**
	 * @return the deleteActionEnabled
	 */
	public final boolean isDeleteActionEnabled() {
		return this.deleteActionEnabled;
	}

	/**
	 * @param deleteActionEnabled the deleteActionEnabled to set
	 */
	public final void setDeleteActionEnabled(boolean deleteActionEnabled) {
		this.deleteActionEnabled = deleteActionEnabled;
		if (this.getEditableRowActions() != null) {
			this.getEditableRowActions().setDeleteActionEnabled(deleteActionEnabled);
		}
	}
	
}
