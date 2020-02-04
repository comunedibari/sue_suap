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

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import it.people.console.persistence.dao.ObjectQueryRunner;
import it.people.console.persistence.exceptions.LazyPagedListHolderException;
import it.people.console.persistence.jdbc.core.RowMapperResultSetEditableRowWithMetaData;
import it.people.console.persistence.jdbc.core.RowMapperResultSetWithMetaData;
import it.people.feservice.beans.interfaces.IpagedArrayResult;

/**
 * @author Andrea Piemontese
 * @version 1.0
 * @created 26-lug-2012
 */
public class ObjectArrayLazyPagedListHolder extends AbstractLazyPagedListHolder implements Serializable {

	
	private static final long serialVersionUID = 5827006785401207478L;

	//Object and Method providing the data (Array of objects) 
	private Object sourceObject;
	private String sourceMethodName;
	private Class[] methodParamTypes;
	private Object[] methodParams;
	//Positions of LIMIT params in methodParams[]
	private int lowerPageLimitMethodParamPos;
	private int pageSizeMethodParamPos;
	
	
	public void finalize() throws Throwable {
		super.finalize();
	}

	public ObjectArrayLazyPagedListHolder(final String pagedListId, final DataSource dataSource, 
			final String query, final int pageSize, final List<String> rowColumnsIdentifiers, 
			final List<String> editableRowColumns, final boolean canAddRow) throws LazyPagedListHolderException{
		
		super(pagedListId, dataSource, query, pageSize, rowColumnsIdentifiers, editableRowColumns, canAddRow);
		//TODO canAddRow not implemented
		throw new NotImplementedException();
	}
	
	/**
	 * 
	 * @param pagedListId Id for PagedListHolder
	 * @param sourceObject on object that can provide paged data
	 * @param methodName the method to call to fetch data. This <em> must </em> return an object implementing {@link IpagedArrayResult}
	 * @param methodParamTypes the java types of params
	 * @param methodParams the param to call the method. This Array <em> must </em> include two mandatory Integer params: lowerPageLimit and pageSize.
	 * @param lowerPageLimitParamPos the position in the methodParams array of lower page limit
	 * @param pageSizeParamPos the position in the methodParams array of page size
	 * @param rowColumnsIdentifiers the attributes names to extract data from result data 
	 * @param visibleColumnsLabels 
	 * @return
	 * @throws LazyPagedListHolderException
	 */
	@SuppressWarnings("rawtypes")
	public ObjectArrayLazyPagedListHolder(final String pagedListId, Object sourceObject, final String methodName,
			final Class[] methodParamTypes, final Object[] methodParams, int lowerPageLimitParamPos, int pageSizeParamPos,
			final List<String> rowColumnsIdentifiers, final List<String> visibleColumnsLabels) throws LazyPagedListHolderException {
		
		super(pagedListId, (Integer) methodParams[pageSizeParamPos],
				rowColumnsIdentifiers, visibleColumnsLabels, sourceObject, methodName,
				methodParamTypes, methodParams);

		//Init the object providing the data
		this.sourceObject = sourceObject;
		this.sourceMethodName = methodName;
		this.methodParamTypes = methodParamTypes;
		this.methodParams = methodParams;
		this.lowerPageLimitMethodParamPos = lowerPageLimitParamPos;
		this.pageSizeMethodParamPos = pageSizeParamPos;
		
	}
	
	@SuppressWarnings("rawtypes")
	protected List <?> boundObjectPageList(Object sourceObject, final String methodName, 
			final Class[] methodParamTypes, final Object[] methodParams) {
		
		List<?> result = null;
		
		try {
			// Get PagedArrayResult first
			IpagedArrayResult pagedResult = (IpagedArrayResult) reflectionCall(
					sourceObject, methodName, methodParamTypes, methodParams);
			List<?> pageObjectsList = Arrays.asList(pagedResult
					.getPartialResult());

			//Set queryCount
			this.setQueryCount(pagedResult.getTotalResultCount());
			
			if (this.isEditableRows()) {
				if (this.getColumnsMetaData() == null) {

					RowMapperResultSetEditableRowWithMetaData listEditableRowWithMetaData = ObjectQueryRunner
							.queryForListEditableRowWithMetaData(pageObjectsList,
									this.getRowColumnsIdentifiers(),
									this.getVisibleColumnsLabels());
					
					this.setColumnsMetaData(listEditableRowWithMetaData.getMetaData());
					this.setColumnsAliases(listEditableRowWithMetaData.getColumnsAliases());
					result = listEditableRowWithMetaData.getData();
				}
				else {
					result = ObjectQueryRunner.queryForListEditableRow(pageObjectsList, this.getRowColumnsIdentifiers());
				}
			}
			else {
				if (this.getColumnsMetaData() == null) {
					
					RowMapperResultSetWithMetaData listWithMetaData = ObjectQueryRunner
							.queryForListWithMetaData(pageObjectsList,
									this.getRowColumnsIdentifiers(),
									this.getVisibleColumnsLabels());

					this.setColumnsMetaData(listWithMetaData.getMetaData());
					this.setColumnsAliases(listWithMetaData.getColumnsAliases());
					result = listWithMetaData.getData();
				}
				else {
					result = ObjectQueryRunner.queryForList(pageObjectsList, this.getRowColumnsIdentifiers());
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
		throw new NotImplementedException("boundPageList - not implemented");
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.AbstractLazyPagedListHolder#boundPageSqlRowSet(java.lang.String, int, int)
	 */
	@Override
	protected SqlRowSet boundPageSqlRowSet(String query, int lowerLimit, int upperLimit) {
		throw new NotImplementedException("boundPageSqlRowSet - not implemented");
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
			
			//Set pagination in method params before call
			this.methodParams[this.lowerPageLimitMethodParamPos] = this.getLowerPageLimit();
			this.methodParams[this.pageSizeMethodParamPos] = this.getPageSize();
			
			this.setPageList(boundObjectPageList(sourceObject, sourceMethodName, methodParamTypes, 
					methodParams));
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
			
			//Set pagination in method params before call
			this.methodParams[this.lowerPageLimitMethodParamPos] = this.getLowerPageLimit();
			this.methodParams[this.pageSizeMethodParamPos] = this.getPageSize();
			
			this.setPageList(boundObjectPageList(sourceObject, sourceMethodName, methodParamTypes, 
					methodParams));
		}
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.AbstractLazyPagedListHolder#setBackedCountQuery(java.lang.String)
	 */
	@Override
	protected void setBackedCountQuery(String query) {
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.AbstractLazyPagedListHolder#setBackedQuery(java.lang.String)
	 */
	@Override
	protected void setBackedQuery(String query) {

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
			
			//Set pagination in method params before call
			this.methodParams[this.lowerPageLimitMethodParamPos] = this.getLowerPageLimit();
			this.methodParams[this.pageSizeMethodParamPos] = this.getPageSize();
			
			this.setPageList(boundObjectPageList(sourceObject, sourceMethodName, methodParamTypes, 
					methodParams));
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
			
			//Set pagination in method params before call
			this.methodParams[this.pageSizeMethodParamPos] = this.getPageSize();
			
			this.setPageList(boundObjectPageList(sourceObject, sourceMethodName, methodParamTypes, 
					methodParams));
		}
		
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.AbstractLazyPagedListHolder#internalInit()
	 */
	@Override
	protected void internalInit() {
		this.setLowerPageLimit(0);
	}

	protected void internalApplyFilters(final List<FilterProperties> filters) throws LazyPagedListHolderException {
		
		throw new NotImplementedException("intrnalApplyFilter not implemented yet");
	}
	
	public void removeFilters() throws LazyPagedListHolderException {
		
		throw new NotImplementedException();
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.AbstractLazyPagedListHolder#applyOrder(java.lang.String, java.lang.String)
	 */
	public void applyOrder(String orderColumn, String orderType) throws LazyPagedListHolderException {

	}
	
	

	public void update() {
		if (logger.isDebugEnabled()) {
			logger.debug("Updating holder...");
		}
		this.setPage(this.getPage());
		if (logger.isDebugEnabled()) {
			logger.debug("Holder updated.");
		}
	}

	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.beans.support.AbstractLazyPagedListHolder#boundPageList(java.lang.String, int, int)
	 */
	@Override
	protected List<?> boundPageList(String query, int lowerLimit, int upperLimit) {
		throw new NotImplementedException("boundPageList - not needed in this implementation.");
	}
	
	
	/**
	 * Allow for instance call, avoiding certain class circular dependencies. <br />
	 * This method do not call private methods.l
	 * @param objInstance instance on which method is invoked (if null, static call)
	 * @param classname name of the class containing the method 
	 * (can be null - ignored, actually - if instance if provided, must be provided if static call)
	 * @param amethodname name of the method to invoke
	 * @param parameterTypes array of Classes
	 * @param parameters array of Object
	 * @return resulting ArrayList <Object>
	 * @throws Exception if any problem
	 */
	private static Object reflectionCall(final Object objInstance, final String amethodname, final Class[] parameterTypes, final Object[] parameters) throws Exception {
		Object res = null;
		
	    try {
	    	Class aclass = objInstance.getClass();
	        final Method amethod = aclass.getDeclaredMethod(amethodname, parameterTypes);
	    	res = amethod.invoke(objInstance, parameters);
	    
	    } catch (final SecurityException e) {
	        throw new Exception("reflectionCall: Security Exception ", e);
	    } catch (final NoSuchMethodException e) {
	        throw new Exception("reflectionCall: NoSuchMethodException ", e);
	    } catch (final IllegalArgumentException e) {
	        throw new Exception("reflectionCall: IllegalArgumentException ", e);
	    } catch (final IllegalAccessException e) {
	        throw new Exception("reflectionCall: IllegalAccessException ", e);
	    } catch (final InvocationTargetException e) {
	        throw new Exception("reflectionCall: InvocationTargetException", e);
	    } 
	    
	    return res;
	}
	
}
