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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import it.people.feservice.utils.StringUtils;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 27/gen/2011 22.33.41
 *
 */
public class NodeNameConverter implements Decodable {

	private DataSource dataSource;
	
	private Map<String, String> feNodesList = new HashMap<String, String>();
	
	public NodeNameConverter(final DataSource dataSource) {
		this.setDataSource(dataSource);
		this.setFeNodesList(this.initFeNodesList());
	}

	/**
	 * @return the dataSource
	 */
	private DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource the dataSource to set
	 */
	private void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * @param feNodesList the feNodesList to set
	 */
	private void setFeNodesList(Map<String, String> feNodesList) {
		this.feNodesList = feNodesList;
	}

	/**
	 * @return the feNodesList
	 */
	private Map<String, String> getFeNodesList() {
		return feNodesList;
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.jdbc.support.Decodable#decode(java.lang.Object)
	 */
	public String decode(Object value) {
		String result = this.getFeNodesList().get(value);
		return StringUtils.nullToEmptyString(result);
	}

	private Map<String, String> initFeNodesList() {

		String queryNodesList = "SELECT id, comune FROM fenode";

		Map<String, String> result = new HashMap<String, String>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = this.getDataSource().getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(queryNodesList);
			while(resultSet.next()) {
				result.put(resultSet.getString(1), resultSet.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
		
	}
	
}
