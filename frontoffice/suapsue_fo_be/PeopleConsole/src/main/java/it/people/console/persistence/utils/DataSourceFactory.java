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
package it.people.console.persistence.utils;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import it.people.console.system.AbstractLogger;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 26/nov/2010 11.00.58
 *
 */
public class DataSourceFactory extends AbstractLogger {

	private static DataSourceFactory instance = null;
	private static DataSource dataSource;
	private static Properties dbProperties;

	private static final String CONFIGURATION_FILE = "/it/people/console/config/config.properties";
	private static final String DB_TYPE_KEY = "db.type";
	private static final String DB_TYPE_TEMPLATE = "dbtype";
	private static final String DRIVER_CLASS_NAME_TEMPLATE_KEY = "jdbc.dbtype.driverClassName";
	private static final String DB_URL_TEMPLATE_KEY = "jdbc.dbtype.url";
	private static final String DB_USER_NAME_TEMPLATE_KEY = "jdbc.dbtype.username";
	private static final String DB_USER_PASSWORD_TEMPLATE_KEY = "jdbc.dbtype.password";

	private static final String JNDI_COMMON_CONTEXT_KEY = "jndi.common.context";
	private static final String JNDI_DATA_SOURCE_NAME_KEY = "jndi.data.source.name";

	private DataSourceFactory() {
		init();
		dataSource = this.searchAvailableDataSource();
	}

	public static DataSourceFactory getInstance() {
		if (instance == null) {
			instance = new DataSourceFactory();
		}
		return instance;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public String getDbType() {
		return dbProperties.getProperty(DB_TYPE_KEY);
	}

	private void init() {

		if (logger.isDebugEnabled()) {
			logger.debug("Initializing factory.");
		}

		dbProperties = new Properties();
		try {
			dbProperties.load(DataSourceFactory.class.getResourceAsStream(CONFIGURATION_FILE));
		} catch (Exception e) {
			dbProperties = null;
			logger.error("Failed to initialize factory.", e);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Factory initialized.");
		}

	}

	private DataSource searchAvailableDataSource() {

		if (logger.isDebugEnabled()) {
			logger.debug("Searching available data source...");
			logger.debug("Search for JNDI data source...");
		}
		
		DataSource result = this._getJNDIDataSource();
		
		if (result == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("JNDI data source not available.");
				logger.debug("Search for basic data source...");
			}
			result = this._getDataSource();
			if (logger.isDebugEnabled()) {
				if (result == null) {
					logger.debug("Basic data source not available.");
				}
				else {
					logger.debug("Basic data source available.");
				}
			}
		}
		else {
			if (logger.isDebugEnabled()) {
				logger.debug("JNDI data source available.");
			}
		}
		
		return result;
		
	}
	
	private DataSource _getDataSource() {

		if (logger.isDebugEnabled()) {
			logger.debug("Getting basic " + dbProperties.getProperty(DB_TYPE_KEY) + " data source.");
		}

		BasicDataSource result = new BasicDataSource();

		result.setDriverClassName(dbProperties.getProperty(DRIVER_CLASS_NAME_TEMPLATE_KEY
				.replace(DB_TYPE_TEMPLATE, dbProperties.getProperty(DB_TYPE_KEY))));
		result.setUrl(dbProperties.getProperty(DB_URL_TEMPLATE_KEY
				.replace(DB_TYPE_TEMPLATE, dbProperties.getProperty(DB_TYPE_KEY))));
		result.setUsername(dbProperties.getProperty(DB_USER_NAME_TEMPLATE_KEY
				.replace(DB_TYPE_TEMPLATE, dbProperties.getProperty(DB_TYPE_KEY))));
		result.setPassword(dbProperties.getProperty(DB_USER_PASSWORD_TEMPLATE_KEY
				.replace(DB_TYPE_TEMPLATE, dbProperties.getProperty(DB_TYPE_KEY))));

		return result;

	}

	private DataSource _getJNDIDataSource() {

		DataSource result = null;

		String jndiDataSource = dbProperties.getProperty(JNDI_COMMON_CONTEXT_KEY) + 
			dbProperties.getProperty(JNDI_DATA_SOURCE_NAME_KEY);

		if (logger.isDebugEnabled()) {
			logger.debug("Getting JNDI data source '" + jndiDataSource + "'.");
		}

		try {
			Context initialContext = new InitialContext();
			result = (DataSource)initialContext.lookup(jndiDataSource);
		} catch (NamingException e) {
			logger.error("JNDI data source not available.", e);
		}

		return result;

	}

}
