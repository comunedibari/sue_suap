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
package it.people.backend.webservice.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A. - Sede di Genova
 *
 */
public class DBConnector {

	private static Logger _logger = LoggerFactory.getLogger(DBConnector.class);
	
	private static final String DATA_SOURCE_NAME = "jdbc/BEServiceDB";
	private static final String JNDI_PREFIX = "java:comp/env/";
	private static final String TEST_LOCAL_DB_DRIVERCLASSNAME_KEY = "test.local.db.driverClassName";
	private static final String TEST_LOCAL_DB_URL_KEY = "test.local.db.url";
	private static final String TEST_LOCAL_DB_USERNAME_KEY = "test.local.db.username";
	private static final String TEST_LOCAL_DB_PASSWORD_KEY = "test.local.db.password";
	
	protected static DBConnector instance = null;
	
	protected static DataSource dataSource = null;
	
	protected static Properties configuration;
	
	private DBConnector() throws PersistenceException {

    	if (_logger.isInfoEnabled()) {
    		_logger.info("Initializing db connector...");
    	}
		
        try {

        	if (_logger.isDebugEnabled()) {
        		_logger.debug("Loading persistence configuration...");
        	}
    		configuration = readConfig();
        	
        	dataSource = getContextDataSource(configuration);
            if (dataSource == null) {
            	if (_logger.isDebugEnabled()) {
            		_logger.debug("Initial context is null, switching to local database.");
            	}
            	dataSource = getLocalDataSource(configuration);
            }

        } catch (InvalidPropertiesFormatException e) {
        	_logger.error("", e);
			throw new PersistenceException(e, "Error loading persistence configuration.");
		} catch (IOException e) {
        	_logger.error("",e);
			throw new PersistenceException(e, "Error loading persistence configuration.");
		}

    	if (_logger.isInfoEnabled()) {
    		_logger.info("Db connector succesfully instantiated.");
    	}
		
	}
	
	private Properties readConfig() throws InvalidPropertiesFormatException, IOException {
		
		Properties result = null;
		
		result = new Properties();
		
		result.loadFromXML(DBConnector.class.getResourceAsStream("/config.xml"));
		
		return result;
		
	}
	
	public static DBConnector getInstance() throws PersistenceException {
		
		if (instance == null) {
			instance = new DBConnector();
		}
		
		return instance;
		
	}
	
	public Connection getConnection() throws PersistenceException {
		
		Connection result = null;
		
		if (dataSource == null) {
			throw new PersistenceException("Data source is null.");
		}
		
		try {
			result = dataSource.getConnection();
		} catch (SQLException e) {
			throw new PersistenceException(e, "SQL exception while getting connection.");
		}
		
		return result;
		
	}

	private DataSource getContextDataSource(Properties configuration) {
		
		DataSource result = null;

        Context initialContext;
		try {
			initialContext = new InitialContext();
	    	result = (DataSource)initialContext.lookup(JNDI_PREFIX + DATA_SOURCE_NAME);
		} catch (Exception e) {
			_logger.warn("", e);
		}
		
		return result;
		
	}
	
	private DataSource getLocalDataSource(Properties configuration) {
		
		BasicDataSource result = new BasicDataSource();
		
		result.setDriverClassName(configuration.getProperty(TEST_LOCAL_DB_DRIVERCLASSNAME_KEY).trim());
		result.setUrl(configuration.getProperty(TEST_LOCAL_DB_URL_KEY).trim());
		result.setUsername(configuration.getProperty(TEST_LOCAL_DB_USERNAME_KEY).trim());
		result.setPassword(configuration.getProperty(TEST_LOCAL_DB_PASSWORD_KEY).trim());
		
		return result;
		
	}
	
}
