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
 * Created on 20-lug-2004
 */
package it.people.util.table;

import it.people.db.DBConnector;
import it.people.exceptions.PeopleDBException;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Andrea Piemontese Engineering S.p.A.
 * 
 */
public class DbTableValuesStrategy implements ITableStrategy {

    private static Logger logger = LogManager
	    .getLogger(DbTableValuesStrategy.class);

    // Tablevalues Separator to split lines
    private static final String SEPARATORE = "|";
    private static Logger _log = Logger.getLogger(DbTableValuesStrategy.class);
    private String defaultCharset = null;

    // Delegate in case of failure of this strategy
    private ITableStrategy delegate;

    /**
     * Costruttore
     */
    public DbTableValuesStrategy() {
	super();
	// Create delegate strategy
	delegate = new TextTableStrategy();
	delegate.setCharset(defaultCharset);
    }

    /**
     * @param process
     * @param tableId
     * @return
     * @throws TableNotFoundException
     *             -------------------------------
     * @see it.people.util.table.ITableHelper#getTableValues(it.people.process.AbstractPplProcess,
     *      java.lang.String)
     */
    public Collection getTableValues(String processName, String codiceComune,
	    String tableId) throws TableNotFoundException {

	Collection result = null;
	boolean tryGenericComune = false;
	boolean useDelegate = false;

	// TODO Add support for Locale
	String locale = null;

	try {
	    result = getDbTableValue(processName, codiceComune, tableId, locale);
	} catch (TableNotFoundException e) {
	    // Need to switch to delegate
	    tryGenericComune = true;
	}

	if (tryGenericComune) {
	    try {
		result = getDbTableValue(processName, null, tableId, locale);
	    } catch (TableNotFoundException e) {
		// Need to switch to delegate
		useDelegate = true;
	    }
	}

	// Use TextTableStrategy
	if (useDelegate) {
	    result = delegate
		    .getTableValues(processName, codiceComune, tableId);
	}

	return result;
    }

    /**
     * Retrieve Table Values from DB and build a collection of values
     * 
     * @param processName
     * @param codiceComune
     * @param tableId
     * @return
     * @throws TableNotFoundException
     */
    private Collection getDbTableValue(String processName, String codiceComune,
	    String tableId, String locale) throws TableNotFoundException {

	Properties tableValuesQueries = new Properties();

	Collection result = new ArrayList();
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	int tableValueId = -1;

	// Query DB to get tablevalues
	try {
	    tableValuesQueries
		    .loadFromXML(DbTableValuesStrategy.class
			    .getResourceAsStream("/it/people/resources/TableValuesQueries.xml"));

	} catch (InvalidPropertiesFormatException e) {
	    _log.error("Unable to read TableValues Query XML file: \n" + e);
	} catch (IOException e) {
	    _log.error("Unable to open TableValues Query XML file: \n" + e);
	}

	try {
	    connection = DBConnector.getInstance().connect(DBConnector.FEDB);

	    // Prepare statement checking null (Locale column: not used yet)
	    if ((codiceComune == null) && (locale == null)) {
		preparedStatement = connection
			.prepareStatement(tableValuesQueries
				.getProperty("getTableValueIdNoLocaleNoNodeId.query"));
		preparedStatement.setString(1, tableId);
		preparedStatement.setString(2, processName);
	    } else if ((codiceComune != null) && (locale == null)) {
		preparedStatement = connection
			.prepareStatement(tableValuesQueries
				.getProperty("getTableValueIdNoLocale.query"));
		preparedStatement.setString(1, tableId);
		preparedStatement.setString(2, processName);
		preparedStatement.setString(3, codiceComune);
	    } else if ((codiceComune == null) && (locale != null)) {
		preparedStatement = connection
			.prepareStatement(tableValuesQueries
				.getProperty("getTableValueIdNoNodeId.query"));
		preparedStatement.setString(1, tableId);
		preparedStatement.setString(2, processName);
		preparedStatement.setString(3, locale);
	    } else {
		preparedStatement = connection
			.prepareStatement(tableValuesQueries
				.getProperty("getTableValueId.query"));
		preparedStatement.setString(1, tableId);
		preparedStatement.setString(2, processName);
		preparedStatement.setString(3, codiceComune);
		preparedStatement.setString(4, locale);
	    }

	    // Execute Query
	    resultSet = preparedStatement.executeQuery();

	    // GET Id first
	    if ((!resultSet.next()) || resultSet == null) {
		_log.debug("TableValues not found on DB");
		throw new TableNotFoundException();
	    } else {
		tableValueId = resultSet.getInt(1);
	    }

	    // Get values for tableValueId
	    preparedStatement = connection.prepareStatement(tableValuesQueries
		    .getProperty("getTableValuesPropertiesForId.query"));
	    preparedStatement.setInt(1, tableValueId);
	    resultSet = preparedStatement.executeQuery();

	    // Check if empty result set
	    if (!resultSet.next()) {
		_log.debug("TableValues not found on DB");
		throw new TableNotFoundException();
	    } else {

		// Read a tablevalues "line" from resultset an tokenize it into
		// values
		String value = null;
		List<String> label = null;
		OptionBean option = null;
		StringTokenizer tokenizer;
		do {
		    String line = resultSet.getString(2);
		    tokenizer = new StringTokenizer(line, SEPARATORE);

		    label = new ArrayList<String>();
		    for (int i = 0; tokenizer.hasMoreTokens(); i++) {
			if (i == 0) {
			    value = tokenizer.nextToken();
			} else {
			    label.add(tokenizer.nextToken());
			}
		    }
		    option = new OptionBean(label, value);
		    result.add(option);
		} while (resultSet.next());
	    }

	} catch (SQLException e) {
	    _log.debug("TableValues not found on DB");
	    throw new TableNotFoundException();
	} catch (PeopleDBException e) {
	    _log.error("Unable to open connection: \n" + e);
	} finally {
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
	    } catch (Exception ignore) {
	    }
	}

	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.util.table.ITableHelper#setCharset(java.lang.String)
     */
    public void setCharset(String charset) {

	this.defaultCharset = charset;
	// Change also delegate strategy charset
	this.delegate.setCharset(charset);
    }

}
