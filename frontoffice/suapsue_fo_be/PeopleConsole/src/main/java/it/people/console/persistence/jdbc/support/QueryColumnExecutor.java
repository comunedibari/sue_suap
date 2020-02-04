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
package it.people.console.persistence.jdbc.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import it.people.console.persistence.utils.DataSourceFactory;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 06/lug/2011 22.55.07
 *
 */
public class QueryColumnExecutor<T> {

	/**
	 * @param query
	 * @param parameters
	 * @return
	 */
	public Vector<T> execute(String query, String[] parameters) {
		
		Vector<T> result = new Vector<T>();
		Connection connection = null;
		PreparedStatement preparedStatment = null;
		ResultSet resultSet = null;
		try {
			connection = DataSourceFactory.getInstance().getDataSource().getConnection();
			preparedStatment = connection.prepareStatement(query);
			bindParameters(preparedStatment, parameters);
			resultSet = preparedStatment.executeQuery();
			while(resultSet.next()) {
				result.add((T)resultSet.getObject(1));
			}
		} catch(SQLException e) {
			
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatment != null) {
					preparedStatment.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch(SQLException e) {
				
			}
		}
		
		return result;
		
	}
	
	/**
	 * @param preparedStatment
	 * @param parameters
	 * @throws SQLException
	 */
	private void bindParameters(PreparedStatement preparedStatment, String[] parameters) throws SQLException {
		if (parameters != null) {
			for(int index = 0; index < parameters.length; index++) {
				preparedStatment.setObject(index + 1, parameters[index]);
			}
		}
	}
	
}
