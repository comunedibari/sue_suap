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
package it.people.console.persistence.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import it.people.console.persistence.jdbc.core.ColumnMapRowMapper;
import it.people.console.persistence.jdbc.core.EditableRow;
import it.people.console.persistence.jdbc.core.EditableRowColumnMapRowMapper;
import it.people.console.persistence.jdbc.core.RowMapperResultSetEditableRowWithMetaData;
import it.people.console.persistence.jdbc.core.RowMapperResultSetExtractorEditableRowWithMetaData;
import it.people.console.persistence.jdbc.core.RowMapperResultSetExtractorWithMetaData;
import it.people.console.persistence.jdbc.core.RowMapperResultSetWithMetaData;
import it.people.console.system.AbstractLogger;

/**
 * @author Riccardo Forafo'
 * @version 1.0
 * @created 23-nov-2010 19:00:36
 */
public class QueryRunner extends AbstractLogger {

	/**
	 * @param dataSource
	 * @param query
	 * @return
	 */
	public static SqlRowSetMetaData getQueryRowSetMetaData(final DataSource dataSource, final String query){

		SqlRowSetMetaData result = null;
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		result = jdbcTemplate.queryForRowSet(normalizeQuery(query)).getMetaData();
		
		return result;
		
	}
	
	/**
	 * 
	 * @param query
	 */
	public static SqlRowSet queryForRowSet(final DataSource dataSource, final String query){

		SqlRowSet result = null;
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		result = jdbcTemplate.queryForRowSet(normalizeQuery(query));
		
		return result;
		
	}

	/**
	 * 
	 * @param query
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> queryForList(final DataSource dataSource, final String query){

		List<Map<String, Object>> result = new ArrayList();
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		result = jdbcTemplate.query(normalizeQuery(query), new ColumnMapRowMapper());
				
		return result;
		
	}

	/**
	 * 
	 * @param query
	 */
	public static List<EditableRow> queryForListEditableRow(final DataSource dataSource, final String query){

		List<EditableRow> result = null;
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		result = jdbcTemplate.query(normalizeQuery(query), new EditableRowColumnMapRowMapper());
		
		
		return result;
		
	}
	
	/**
	 * 
	 * @param query
	 */
	public static RowMapperResultSetWithMetaData queryForListWithMetaData(final DataSource dataSource, final String query){

		RowMapperResultSetWithMetaData result = null;
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		result = jdbcTemplate.query(normalizeQuery(query), new RowMapperResultSetExtractorWithMetaData(new ColumnMapRowMapper()));
		
		
		return result;
		
	}

	/**
	 * 
	 * @param query
	 */
	public static RowMapperResultSetEditableRowWithMetaData queryForListEditableRowWithMetaData(final DataSource dataSource, final String query){

		RowMapperResultSetEditableRowWithMetaData result = null;
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		result = jdbcTemplate.query(normalizeQuery(query), new RowMapperResultSetExtractorEditableRowWithMetaData(new EditableRowColumnMapRowMapper()));
		
		
		return result;
		
	}
	
	private static String normalizeQuery(String query) {
		
		String result = query;
		
		result = query.replaceAll("\\\\.", ".");
		
		return result;
		
	}
	
}
