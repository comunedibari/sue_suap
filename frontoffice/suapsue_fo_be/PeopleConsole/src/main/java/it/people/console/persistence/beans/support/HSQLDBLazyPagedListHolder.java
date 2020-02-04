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

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import it.people.console.persistence.dao.QueryRunner;
import it.people.console.persistence.exceptions.LazyPagedListHolderException;
import it.people.console.persistence.jdbc.core.RowMapperResultSetEditableRowWithMetaData;
import it.people.console.persistence.jdbc.core.RowMapperResultSetWithMetaData;
import it.people.console.security.Command;

/**
 * @author Riccardo Forafo'
 * @version 1.0
 * @created 23-nov-2010 19:00:36
 */
public class HSQLDBLazyPagedListHolder extends AbstractLazyPagedListHolder {

	protected static final String BACKED_COUNT_QUERY = "SELECT COUNT(*) AS \"" + COUNT_COLUMN_NAME + "\" FROM (select $" + QUERY_PLACE_HOLDER + "$);";
	protected static final String BACKED_QUERY = "select LIMIT $" + LOWER_PAGE_LIMIT + "$ $" + UPPER_PAGE_LIMIT + "$ $" + QUERY_PLACE_HOLDER + "$";

	public void finalize() throws Throwable {
		super.finalize();
	}

	public HSQLDBLazyPagedListHolder(final String pagedListId, final DataSource dataSource, 
			final String query, final int pageSize, final List<String> rowColumnsIdentifiers, 
			final List<Command> rowActions) throws LazyPagedListHolderException{
		super(pagedListId, dataSource, query, pageSize, rowColumnsIdentifiers, rowActions);
	}

	public HSQLDBLazyPagedListHolder(final String pagedListId, final DataSource dataSource, 
			final String query, final int pageSize, final List<String> rowColumnsIdentifiers, 
			final List<String> editableRowColumns, final boolean canAddRow) throws LazyPagedListHolderException{
		super(pagedListId, dataSource, query, pageSize, rowColumnsIdentifiers, editableRowColumns, canAddRow);
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.AbstractLazyPagedListHolder#boundPageList(java.lang.String, int, int)
	 */
	@Override
	protected List<?> boundPageList(String query, int lowerLimit, int upperLimit) {

		List<?> result = null;
		String slicedQuery = this.getBackedQuery().replaceAll("\\$" + LOWER_PAGE_LIMIT + "\\$", String.valueOf(lowerLimit))
			.replaceAll("\\$" + UPPER_PAGE_LIMIT + "\\$", String.valueOf(upperLimit));
		try {
			if (this.isEditableRows()) {
				if (this.getColumnsMetaData() == null) {
					RowMapperResultSetEditableRowWithMetaData listEditableRowWithMetaData = QueryRunner.queryForListEditableRowWithMetaData(this.getDataSource(), slicedQuery);
					this.setColumnsMetaData(listEditableRowWithMetaData.getMetaData());
					this.setColumnsAliases(listEditableRowWithMetaData.getColumnsAliases());
					result = listEditableRowWithMetaData.getData();
				}
				else {
					result = QueryRunner.queryForListEditableRow(this.getDataSource(), slicedQuery);
				}
			}
			else {
				if (this.getColumnsMetaData() == null) {
					RowMapperResultSetWithMetaData listWithMetaData = QueryRunner.queryForListWithMetaData(this.getDataSource(), slicedQuery);
					this.setColumnsMetaData(listWithMetaData.getMetaData());
					this.setColumnsAliases(listWithMetaData.getColumnsAliases());
					result = listWithMetaData.getData();
				}
				else {
					result = QueryRunner.queryForList(this.getDataSource(), slicedQuery);
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return result;
				
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.AbstractLazyPagedListHolder#boundFilteredPageList(java.util.List, java.lang.String, int, int)
	 */
	@Override
	protected List<?> boundFilteredPageList(final List<FilterProperties> filters, final String query, final int lowerLimit, final int upperLimit) {

		List<?> result = null;
		String slicedQuery = this.getBackedQuery().replaceAll("\\$" + LOWER_PAGE_LIMIT + "\\$", String.valueOf(lowerLimit))
			.replaceAll("\\$" + UPPER_PAGE_LIMIT + "\\$", String.valueOf(upperLimit));
		try {
			if (this.isEditableRows()) {
				if (this.getColumnsMetaData() == null) {
					RowMapperResultSetEditableRowWithMetaData listEditableRowWithMetaData = QueryRunner.queryForListEditableRowWithMetaData(this.getDataSource(), slicedQuery);
					this.setColumnsMetaData(listEditableRowWithMetaData.getMetaData());
					this.setColumnsAliases(listEditableRowWithMetaData.getColumnsAliases());
					result = listEditableRowWithMetaData.getData();
				}
				else {
					result = QueryRunner.queryForListEditableRow(this.getDataSource(), slicedQuery);
				}
			}
			else {
				if (this.getColumnsMetaData() == null) {
					RowMapperResultSetWithMetaData listWithMetaData = QueryRunner.queryForListWithMetaData(this.getDataSource(), slicedQuery);
					this.setColumnsMetaData(listWithMetaData.getMetaData());
					this.setColumnsAliases(listWithMetaData.getColumnsAliases());
					result = listWithMetaData.getData();
				}
				else {
					result = QueryRunner.queryForList(this.getDataSource(), slicedQuery);
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return result;
		
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.AbstractLazyPagedListHolder#boundPageSqlRowSet(java.lang.String, int, int)
	 */
	@Override
	protected SqlRowSet boundPageSqlRowSet(String query, int lowerLimit, int upperLimit) {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.AbstractLazyPagedListHolder#nextPage()
	 */
	@Override
	public void nextPage() {

		if (!this.isLastPage()) {
			this.page++;
			this.setLowerPageLimit(this.getLowerPageLimit() + this.getPageSize());
			this.setUpperPageLimit(this.getPageSize());
			this.setPageList(boundPageList(this.getBackedQuery(), 
					this.getLowerPageLimit(), this.getUpperPageLimit()));
		}		
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.AbstractLazyPagedListHolder#previousPage()
	 */
	@Override
	public void previousPage() {

		if (!this.isFirstPage()) {
			this.page--;
			this.setLowerPageLimit(this.getLowerPageLimit() - this.getPageSize());
			this.setUpperPageLimit(this.getPageSize());
			this.setPageList(boundPageList(this.getBackedQuery(), 
					this.getLowerPageLimit(), this.getUpperPageLimit()));
		}
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.AbstractLazyPagedListHolder#setBackedCountQuery(java.lang.String)
	 */
	@Override
	protected void setBackedCountQuery(String query) {

		this.backedCountQuery = BACKED_COUNT_QUERY.replaceAll("\\$" + QUERY_PLACE_HOLDER + "\\$", query) ;;
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.AbstractLazyPagedListHolder#setBackedQuery(java.lang.String)
	 */
	@Override
	protected void setBackedQuery(String query) {

		this.backedQuery = BACKED_QUERY.replaceAll("\\$" + QUERY_PLACE_HOLDER + "\\$", query) ;
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.AbstractLazyPagedListHolder#setPage(int)
	 */
	@Override
	public void setPage(int pageNumber) {

		if (pageNumber <= this.getPageCount() && pageNumber > 0) {
			this.page = pageNumber;
			this.setLowerPageLimit(((pageNumber - 1) * this.getPageSize()));
			this.setUpperPageLimit(this.getPageSize());
			this.setPageList(boundPageList(this.getBackedQuery(), 
					this.getLowerPageLimit(), this.getUpperPageLimit()));
		}
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.AbstractLazyPagedListHolder#setPageSize(int)
	 */
	@Override
	public void setPageSize(int pageSize) {

		if (pageSize <= 0) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		if (pageSize != this.getPageSize()) {
			this.pageSize = pageSize;
			if (!this.isNewPageSet()) {
				this.page = 1;
			}
			this.setUpperPageLimit(this.pageSize);
			this.setPageList(boundPageList(this.getBackedQuery(), this.getLowerPageLimit(), this.getUpperPageLimit()));
		}
		
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.AbstractLazyPagedListHolder#internalInit()
	 */
	@Override
	protected void internalInit() {
		this.setLowerPageLimit(0);
		this.query = prepareQuery();
		this.setBackedQuery(this.query);
		this.setBackedCountQuery(this.query);
	}

	private String prepareQuery() {
		return (this.query.startsWith("select ")) ? this.query.substring("select ".length()) : this.query;
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.AbstractLazyPagedListHolder#internalApplyFilters(java.util.List)
	 */
	protected void internalApplyFilters(final List<FilterProperties> filters) {
		
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.AbstractLazyPagedListHolder#removeFilters()
	 */
	public void removeFilters() {
		
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.AbstractLazyPagedListHolder#applyOrder(java.lang.String, java.lang.String)
	 */
	public void applyOrder(String orderColumn, String orderType) {
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.AbstractLazyPagedListHolder#update()
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}
