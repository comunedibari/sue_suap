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

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import it.people.console.domain.PairElement;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 29/gen/2011 15.29.14
 *
 */
public class EditableRowSelect {

	private List<PairElement<String, String>> selectData;
	
	private DataSource peopleDb;
	
	private String query;
	
	private Object[] queryParameters;
	
	/**
	 * @param peopleDb
	 * @param query
	 * @param queryParameters
	 */
	public EditableRowSelect(DataSource peopleDb, String query, Object[] queryParameters) {
		this.setPeopleDb(peopleDb);
		this.setQuery(query);
		this.setQueryParameters(queryParameters);
		this.setSelectData(new ArrayList<PairElement<String, String>>());
		this.update();
	}

	/**
	 * 
	 */
	public void update() {
		
		this.getSelectData().clear();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			
			connection = this.getPeopleDb().getConnection();
			preparedStatement = connection.prepareStatement(this.getQuery());
			if (this.getQueryParameters() != null) {
				bindQueryParameters(preparedStatement);
			}
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				this.getSelectData().add(new PairElement<String, String>(resultSet.getString(1), 
						resultSet.getString(2)));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch(SQLException e) {
				
			}
		}
		
	}
	
	/**
	 * @param preparedStatement
	 * @throws SQLException
	 */
	private void bindQueryParameters(PreparedStatement preparedStatement) throws SQLException {
		
		for (int index = 0; index < this.getQueryParameters().length; index++) {
			Object parameter = this.getQueryParameters()[index];

			if (parameter instanceof BigDecimal) {
				preparedStatement.setBigDecimal(index + 1, (BigDecimal)parameter);
			} else if (parameter instanceof Blob) {
				preparedStatement.setBlob(index + 1, (Blob)parameter);
			} else if (parameter instanceof Boolean) {
				preparedStatement.setBoolean(index + 1, (Boolean)parameter);
			} else if (parameter instanceof Byte) {
				preparedStatement.setByte(index + 1, (Byte)parameter);
			} else if (parameter instanceof Clob) {
				preparedStatement.setClob(index + 1, (Clob)parameter);
			} else if (parameter instanceof Date) {
				preparedStatement.setDate(index + 1, (Date)parameter);
			} else if (parameter instanceof Double) {
				preparedStatement.setDouble(index + 1, (Double)parameter);
			} else if (parameter instanceof Float) {
				preparedStatement.setFloat(index + 1, (Float)parameter);
			} else if (parameter instanceof Integer) {
				preparedStatement.setInt(index + 1, (Integer)parameter);
			} else if (parameter instanceof Long) {
				preparedStatement.setLong(index + 1, (Long)parameter);
			} else if (parameter instanceof Object) {
				preparedStatement.setObject(index + 1, parameter);
			} else if (parameter instanceof Short) {
				preparedStatement.setShort(index + 1, (Short)parameter);
			} else if (parameter instanceof String) {
				preparedStatement.setString(index + 1, (String)parameter);
			} else if (parameter instanceof Time) {
				preparedStatement.setTime(index + 1, (Time)parameter);
			} else if (parameter instanceof Timestamp) {
				preparedStatement.setTimestamp(index + 1, (Timestamp)parameter);
			} else if (parameter instanceof URL) {
				preparedStatement.setURL(index + 1, (URL)parameter);
			} else {
				throw new IllegalArgumentException();
			}
		}
		
	}
	
	/**
	 * @return the peopleDb
	 */
	private DataSource getPeopleDb() {
		return peopleDb;
	}

	/**
	 * @param peopleDb the peopleDb to set
	 */
	private void setPeopleDb(DataSource peopleDb) {
		this.peopleDb = peopleDb;
	}

	/**
	 * @return the selectData
	 */
	public final List<PairElement<String, String>> getSelectData() {
		if (this.selectData == null) {
			this.setSelectData(new ArrayList<PairElement<String, String>>());
		}
		return selectData;
	}

	/**
	 * @param selectData the selectData to set
	 */
	public final void setSelectData(List<PairElement<String, String>> selectData) {
		this.selectData = selectData;
	}

	/**
	 * @return the query
	 */
	private final String getQuery() {
		return query;
	}

	/**
	 * @param query the query to set
	 */
	private final void setQuery(String query) {
		this.query = query;
	}

	/**
	 * @return the queryParameters
	 */
	private final Object[] getQueryParameters() {
		return queryParameters;
	}

	/**
	 * @param queryParameters the queryParameters to set
	 */
	private final void setQueryParameters(Object[] queryParameters) {
		this.queryParameters = queryParameters;
	}

	
}
