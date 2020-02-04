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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import it.people.console.persistence.exceptions.LazyPagedListHolderException;
import it.people.console.utils.Constants;
import it.people.console.persistence.utils.DBDetector;
import it.people.console.security.Command;
import it.people.feservice.beans.interfaces.IpagedArrayResult;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 24/nov/2010 09.22.04
 *
 */
public class LazyPagedListHolderFactory {

	public static ILazyPagedListHolder getLazyPagedListHolder(final String pagedListId, 
			final DataSource dataSource, String query, final int pageSize, 
			final List<String> rowColumnsIdentifiers, final List<Command> rowActions) throws LazyPagedListHolderException {
				
		ILazyPagedListHolder result = null;
		
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			if (DBDetector.getDatabaseProductName(connection).equalsIgnoreCase(Constants.Persistence.MYSQL_DB)) {
				result = new MySQLLazyPagedListHolder(pagedListId, dataSource, query, pageSize, 
						rowColumnsIdentifiers, rowActions);
			}
			if (DBDetector.getDatabaseProductName(connection).equalsIgnoreCase(Constants.Persistence.ORACLE_DB)) {
				result = new OracleLazyPagedListHolder(pagedListId, dataSource, query, pageSize, 
						rowColumnsIdentifiers, rowActions);
			}
			if (DBDetector.getDatabaseProductName(connection).startsWith(Constants.Persistence.HSQLDB_DB)) {
				result = new HSQLDBLazyPagedListHolder(pagedListId, dataSource, query, pageSize, 
						rowColumnsIdentifiers, rowActions);
			}
		} catch (SQLException e) {
			throw new LazyPagedListHolderException(e);
		}
		finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
		
	}

	public static ILazyPagedListHolder getLazyPagedListHolder(final String pagedListId, 
			final DataSource dataSource, String query, final int pageSize, 
			final List<String> rowColumnsIdentifiers, final List<String> editableRowColumns, 
			final boolean canAddRow) throws LazyPagedListHolderException {
				
		ILazyPagedListHolder result = null;
		
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			if (DBDetector.getDatabaseProductName(connection).equalsIgnoreCase(Constants.Persistence.MYSQL_DB)) {
				result = new MySQLLazyPagedListHolder(pagedListId, dataSource, query, pageSize, 
						rowColumnsIdentifiers, editableRowColumns, canAddRow);
			}
			if (DBDetector.getDatabaseProductName(connection).equalsIgnoreCase(Constants.Persistence.ORACLE_DB)) {
				result = new OracleLazyPagedListHolder(pagedListId, dataSource, query, pageSize, 
						rowColumnsIdentifiers, editableRowColumns, canAddRow);
			}
			if (DBDetector.getDatabaseProductName(connection).startsWith(Constants.Persistence.HSQLDB_DB)) {
				result = new HSQLDBLazyPagedListHolder(pagedListId, dataSource, query, pageSize, 
						rowColumnsIdentifiers, editableRowColumns, canAddRow);
			}
		} catch (SQLException e) {
			throw new LazyPagedListHolderException(e);
		}
		finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
		
	}
	
	/// Factory method to build a PagedListHolder working on Objects as source of data
	
	/**
	 * 
	 * @param pagedListId Id for PagedListHolder
	 * @param sourceObject on object that can provide paged data
	 * @param methodName the method to call to fetch data. This must return an object implementing {@link IpagedArrayResult}
	 * @param methodParamTypes the java types of params
	 * @param methodParams the param to call the method. This Array must include two mandatory Integer params: lowerPageLimit and pageSize.
	 * @param lowerPageLimitParamPos the position in the methodParams array of lower page limit
	 * @param pageSizeParamPos the position in the methodParams array of page size
	 * @param rowColumnsIdentifiers the attributes names to extract data from result data 
	 * @return
	 * @throws LazyPagedListHolderException
	 */
	public static ILazyPagedListHolder getLazyPagedListHolder(
			final String pagedListId, Object sourceObject,
			final String methodName, final Class[] methodParamTypes,
			final Object[] methodParams, int lowerPageLimitParamPos,
			int pageSizeParamPos, final List<String> rowColumnsIdentifiers, 
			final List<String> visibleColumnsIdentifiers,
			final List<String> visibleColumnsLabels) throws LazyPagedListHolderException {
		
		ILazyPagedListHolder result = null;
		
		result = new ObjectArrayLazyPagedListHolder(pagedListId, sourceObject,
				methodName, methodParamTypes, methodParams,
				lowerPageLimitParamPos, pageSizeParamPos, rowColumnsIdentifiers, visibleColumnsLabels);
		
		result.setVisibleColumnsNames(visibleColumnsIdentifiers);
		
		return result;
	}
	
	
	
}
