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
/*
 * DBConnector.java
 *
 * Created on October 25, 2004, 10:36 AM
 */

package it.people.db;

import it.people.exceptions.PeopleDBException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

/**
 * 
 * @author manelli
 */
public class DBConnector {
    private static final Logger logger = Logger.getLogger(DBConnector.class);
    private static final String CONFIGURATION_FILE = "/it/people/resources/config.properties";
    private static final String DB_TYPE_KEY = "db.type";
    private static final String DB_TYPE_TEMPLATE = "dbtype";

    private static final String DRIVER_CLASS_NAME_TEMPLATE_KEY = "jdbc.dbtype.driverClassName";
    private static final String DB_URL_TEMPLATE_KEY = "jdbc.dbtype.url";
    private static final String DB_USER_NAME_TEMPLATE_KEY = "jdbc.dbtype.username";
    private static final String DB_USER_PASSWORD_TEMPLATE_KEY = "jdbc.dbtype.password";

    private static DBConnector instance;
    private static HashMap dbSources;
    public static final String FEDB = "FEDB";
    public static final String LEGACYDB = "LegacyDB";
    private static Properties dbProperties = new Properties();

    /** Creates a new instance of DBConnector */
    protected DBConnector() throws PeopleDBException {
	DataSource ds;
	dbSources = new HashMap();

	try {
	    Context initContext = new InitialContext();

	    ds = (DataSource) initContext.lookup("java:comp/env/jdbc/FEDB");
	    dbSources.put(FEDB, ds);

	    ds = (DataSource) initContext.lookup("java:comp/env/jdbc/LegacyDB");
	    dbSources.put(LEGACYDB, ds);

	} catch (NamingException ex) {
	    String errorMessage = "DBConnector :: cannot lookup DataSources, "
		    + "check your Application Server configuration.";

	    logger.fatal(errorMessage, ex);
	    throw new PeopleDBException(errorMessage);
	}
    }

    /**
     * <p>
     * Only used for testing purpose by reflection
     * 
     * @param test
     * 
     * @see it.people.db.DBConnectorTestCase
     */
    @SuppressWarnings("unused")
    private DBConnector(boolean test) {
	dbSources = new HashMap();
	try {
	    initDbProperties();
	} catch (Exception e) {
	}
    }

    public static DBConnector getInstance() throws PeopleDBException {
	if (instance == null) {
	    instance = new DBConnector();
	}

	return instance;
    }

    public Connection connect(String dbSelector) throws PeopleDBException {
	Connection conn;
	DataSource ds;

	ds = (DataSource) dbSources.get(dbSelector);

	if (ds == null) {
	    String errorMessage = "DBConnector :: Cannot find a valid DataSource for this selector";
	    logger.error(errorMessage);
	    throw new PeopleDBException(
		    "DBConnector :: Cannot find a valid DataSource for this selector");
	}

	try {
	    conn = ds.getConnection();
	} catch (SQLException ex) {
	    String errorMessage = "DBConnector :: Cannot get a valid Connection for this selector";
	    logger.error(errorMessage, ex);
	    throw new PeopleDBException(ex.getMessage());
	}

	return conn;
    }

    /**
     * <p>
     * Only used for testing purpose by reflection
     * 
     * @param test
     * 
     * @see it.people.db.DBConnectorTestCase
     */
    @SuppressWarnings("unused")
    private void setTestDataSources() {

	BasicDataSource legacyDb = new BasicDataSource();

	legacyDb.setDriverClassName(dbProperties
		.getProperty(DRIVER_CLASS_NAME_TEMPLATE_KEY.replace(
			DB_TYPE_TEMPLATE, dbProperties.getProperty(DB_TYPE_KEY)
				+ "." + LEGACYDB)));
	legacyDb.setUrl(dbProperties.getProperty(DB_URL_TEMPLATE_KEY.replace(
		DB_TYPE_TEMPLATE, dbProperties.getProperty(DB_TYPE_KEY) + "."
			+ LEGACYDB)));
	legacyDb.setUsername(dbProperties.getProperty(DB_USER_NAME_TEMPLATE_KEY
		.replace(DB_TYPE_TEMPLATE,
			dbProperties.getProperty(DB_TYPE_KEY) + "." + LEGACYDB)));
	legacyDb.setPassword(dbProperties
		.getProperty(DB_USER_PASSWORD_TEMPLATE_KEY.replace(
			DB_TYPE_TEMPLATE, dbProperties.getProperty(DB_TYPE_KEY)
				+ "." + LEGACYDB)));
	dbSources.put(LEGACYDB, legacyDb);

	BasicDataSource feDb = new BasicDataSource();

	feDb.setDriverClassName(dbProperties
		.getProperty(DRIVER_CLASS_NAME_TEMPLATE_KEY.replace(
			DB_TYPE_TEMPLATE, dbProperties.getProperty(DB_TYPE_KEY)
				+ "." + FEDB)));
	feDb.setUrl(dbProperties.getProperty(DB_URL_TEMPLATE_KEY.replace(
		DB_TYPE_TEMPLATE, dbProperties.getProperty(DB_TYPE_KEY) + "."
			+ FEDB)));
	feDb.setUsername(dbProperties.getProperty(DB_USER_NAME_TEMPLATE_KEY
		.replace(DB_TYPE_TEMPLATE,
			dbProperties.getProperty(DB_TYPE_KEY) + "." + FEDB)));
	feDb.setPassword(dbProperties.getProperty(DB_USER_PASSWORD_TEMPLATE_KEY
		.replace(DB_TYPE_TEMPLATE,
			dbProperties.getProperty(DB_TYPE_KEY) + "." + FEDB)));
	dbSources.put(FEDB, feDb);

	instance = this;

    }

    private void initDbProperties() throws IOException {

	dbProperties.load(DBConnector.class
		.getResourceAsStream(CONFIGURATION_FILE));

    }

}
