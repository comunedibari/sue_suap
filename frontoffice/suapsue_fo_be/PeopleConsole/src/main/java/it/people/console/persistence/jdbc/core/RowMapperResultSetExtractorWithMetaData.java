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
package it.people.console.persistence.jdbc.core;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import it.people.console.system.AbstractLogger;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 15/gen/2011 09.33.02
 *
 */
public class RowMapperResultSetExtractorWithMetaData extends AbstractLogger implements ResultSetExtractor<RowMapperResultSetWithMetaData> {

	private final RowMapper<Map<String, Object>> rowMapper;

	private final int rowsExpected;


	/**
	 * Create a new RowMapperResultSetExtractorWithMetaData.
	 * @param rowMapper the RowMapper which creates an object for each row
	 */
	public RowMapperResultSetExtractorWithMetaData(RowMapper<Map<String, Object>> rowMapper) {
		this(rowMapper, 0);
	}

	/**
	 * Create a new RowMapperResultSetExtractorWithMetaData.
	 * @param rowMapper the RowMapper which creates an object for each row
	 * @param rowsExpected the number of expected rows
	 * (just used for optimized collection handling)
	 */
	public RowMapperResultSetExtractorWithMetaData(RowMapper<Map<String, Object>> rowMapper, int rowsExpected) {
		Assert.notNull(rowMapper, "RowMapper is required");
		this.rowMapper = rowMapper;
		this.rowsExpected = rowsExpected;
	}


	public RowMapperResultSetWithMetaData extractData(ResultSet rs) throws SQLException {
		RowMapperResultSetWithMetaData result = new RowMapperResultSetWithMetaData();
		if (logger.isDebugEnabled()) {
			logger.debug("Retrieving meta data...");
		}
		result.setMetaData(getMetaData(rs));
		result.setColumnsAliases(getColumnsAliases(rs));
		if (logger.isDebugEnabled()) {
			logger.debug("Retrieving data...");
		}
		List<Map<String, Object>> dataBuffer = (this.rowsExpected > 0 ? new ArrayList<Map<String, Object>>(this.rowsExpected) : new ArrayList<Map<String, Object>>());
		int rowNum = 0;
		while (rs.next()) {
			dataBuffer.add(this.rowMapper.mapRow(rs, rowNum++));
		}
		result.setData(dataBuffer);
		return result;
	}
	
	private Map<String, ColumnMetaData> getMetaData(ResultSet rs) throws SQLException {
		
		Map<String, ColumnMetaData> results = new HashMap<String, ColumnMetaData>();
		
		ResultSetMetaData rsMetaData = rs.getMetaData();

		if (logger.isDebugEnabled()) {
			logger.debug("Retrieving meta data for " + rsMetaData.getColumnCount() + " column(s).");
		}
		
		for (int index = 1; index <= rsMetaData.getColumnCount(); index++) {
			String key = rsMetaData.getColumnName(index);
			if (results.containsKey(key)) {
				if (logger.isDebugEnabled()) {
					logger.debug("Column '" + key + "' already exists, prefixing with table name.");
				}
				key = rsMetaData.getTableName(index) + "." + key;
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Meta data for column " + index + ":");
				logger.debug("\tColumn name: '" + key + "'.");
				logger.debug("\tColumn label: '" + rsMetaData.getColumnLabel(index) + "'.");
				logger.debug("\tColumn type: '" + rsMetaData.getColumnType(index) + "'.");
				logger.debug("\tColumn class name: '" + rsMetaData.getColumnClassName(index) + "'.");
				logger.debug("\tColumn display size: '" + rsMetaData.getColumnDisplaySize(index) + "'.");
				logger.debug("\tColumn type name: '" + rsMetaData.getColumnTypeName(index) + "'.");
				logger.debug("\tColumn precision: '" + rsMetaData.getPrecision(index) + "'.");
				logger.debug("\tColumn scale: '" + rsMetaData.getScale(index) + "'.");
			}
			results.put(key, new ColumnMetaData(rsMetaData.getColumnName(index), 
					rsMetaData.getColumnLabel(index), rsMetaData.getColumnType(index),
					rsMetaData.getColumnClassName(index), rsMetaData.getColumnDisplaySize(index), 
					rsMetaData.getColumnTypeName(index), rsMetaData.getPrecision(index), 
					rsMetaData.getScale(index)));
		}
		
		return results;
		
	}

	private List<ColumnHeaderInformation> getColumnsAliases(ResultSet rs) throws SQLException {
		
		List<ColumnHeaderInformation> results = new ArrayList<ColumnHeaderInformation>();
		
		ResultSetMetaData rsMetaData = rs.getMetaData();

		if (logger.isDebugEnabled()) {
			logger.debug("Retrieving column aliases for " + rsMetaData.getColumnCount() + " column(s).");
		}
		
		for (int index = 1; index <= rsMetaData.getColumnCount(); index++) {
			if (logger.isDebugEnabled()) {
				logger.debug("Meta data for column " + index + ":");
				logger.debug("\tColumn name: '" + rsMetaData.getColumnName(index) + "'.");
				logger.debug("\tColumn label: '" + rsMetaData.getColumnLabel(index) + "'.");
			}
			results.add(new ColumnHeaderInformation(rsMetaData.getColumnName(index), rsMetaData.getColumnLabel(index)));
		}
		
		return results;
		
	}
	
}
