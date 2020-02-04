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
import java.util.Map;
import java.util.Vector;

import it.people.console.persistence.exceptions.LazyPagedListHolderException;
import it.people.console.persistence.jdbc.core.ColumnHeaderInformation;
import it.people.console.persistence.jdbc.core.ColumnMetaData;
import it.people.console.persistence.jdbc.support.Decodable;
import it.people.console.persistence.jdbc.support.IRowsStatusModeler;
import it.people.console.security.Command;

/**
 * @author Riccardo Forafo'
 * @version 1.0
 * @created 23-nov-2010 19:00:36
 */
public interface ILazyPagedListHolder {

	public String getQuery();

	public void setPageSize(final int pageSize);

	public int getPageSize();

	public List<?> getPageList();

	public int getPage();

	public int getPageCount();

	public boolean isFirstPage();

	public boolean isLastPage();

	public void nextPage();

	public void previousPage();

	public void setPage(final int pageNumber);

	public void applyFilters(final List<FilterProperties> filters) throws LazyPagedListHolderException;

	public void removeFilters() throws LazyPagedListHolderException;
	
	public void applyOrder(String orderColumn, String orderType) throws LazyPagedListHolderException;
	
	public void firstPage();
	
	public void lastPage();
	
	public Map<String, ColumnMetaData> getColumnsMetaData();

	public Map<String, Decodable> getConverters();

	public void setConverters(Map<String, Decodable> converters);
	
	public List<ColumnHeaderInformation> getColumnsAliases();
	
	public List<Command> getRowActions();

	public void setVisibleColumnsNames(List<String> visibleColumnsNames);

	public void setEditableRowModelers(Map<String, Object> editableRowModelers);

	public void setRowModelers(Map<String, Object> rowModelers);

	public Map<String, Object> getRowModelers();
	
	public void setRowsStatusModelers(IRowsStatusModeler<Integer> rowsStatusModelers);

	public IRowsStatusModeler<Integer> getRowsStatusModelers();
	
	public Map<String, Object> getEditableRowModelers();
	
	public List<String> getVisibleColumnsNames();

	public List<String> getRowColumnsIdentifiers();

	public List<String> getEditableRowColumns();
	
	public String getPagedListId();
	
	public boolean isEditMode();

	public void setEditMode(boolean editMode);

	public EditableRowActions getEditableRowActions();
	
	public boolean isCanAddRow();
	
	public boolean isNewRowEditMode();

	public void setNewRowEditMode(boolean newRowEditMode);
	
	public void update();


	
	public void setExtendedColumnsData(Map<String, Vector<String>> extendedData);

	public Map<String, Vector<String>> getExtendedColumnsData();

	
	public Map<String, String> getColumnsQueries();

	public void setColumnsQueries(Map<String, String> columnsQueries);
	
	public boolean isDeleteActionEnabled();

	public void setDeleteActionEnabled(boolean deleteActionEnabled);
	
}
