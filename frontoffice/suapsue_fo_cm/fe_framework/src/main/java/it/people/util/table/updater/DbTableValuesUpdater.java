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
package it.people.util.table.updater;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.people.db.DBConnector;
import it.people.exceptions.PeopleDBException;
import it.people.util.PathScanner;

import it.people.util.resourcesupdater.IResourceUpdater;
import it.people.util.table.TableValuesNameUtils;
import it.people.util.table.UnpackedTableValueName;

/**
 * @author Andrea Piemontese - Engineering Ingegneria Informatica 07/06/2012
 */
public class DbTableValuesUpdater implements IResourceUpdater {

    private static Logger logger = LogManager
	    .getLogger(DbTableValuesUpdater.class);

    private static DbTableValuesUpdater instance = null;

    private DbTableValuesUpdater() {

    }

    public static DbTableValuesUpdater getInstance() {

	if (instance == null) {
	    instance = new DbTableValuesUpdater();
	}

	return instance;
    }

    public boolean update(String basePath) {

	boolean doTableValuesUpdate = false;
	Connection feDbConnection = null;
	PreparedStatement preparedStatement = null;
	PreparedStatement insertPreparedStatement = null;
	PreparedStatement updatePreparedStatement = null;
	ResultSet resultSet = null;
	boolean autoCommit = false;

	try {
	    // Get DB connection
	    feDbConnection = DBConnector.getInstance()
		    .connect(DBConnector.FEDB);
	    autoCommit = feDbConnection.getAutoCommit();
	    feDbConnection.setAutoCommit(false);

	    // Load queries
	    boolean tableValuesQueriesLoaded = true;
	    Properties tableValuesQueries = new Properties();

	    if (logger.isDebugEnabled()) {
		logger.debug("Reading table values queries...");
	    }
	    try {
		tableValuesQueries
			.loadFromXML(DbTableValuesUpdater.class
				.getResourceAsStream("/it/people/resources/TableValuesQueries.xml"));
	    } catch (InvalidPropertiesFormatException e) {
		logger.error(e);
		tableValuesQueriesLoaded = false;
	    } catch (IOException e) {
		logger.error(e);
		tableValuesQueriesLoaded = false;
	    }

	    if (tableValuesQueriesLoaded) {

		if (logger.isDebugEnabled()) {
		    logger.debug("Table Values queries loaded.");
		}

		// Update if tables are empty only
		doTableValuesUpdate = checkDbEmptyTableValues(feDbConnection,
			tableValuesQueries, preparedStatement, resultSet);

		if (doTableValuesUpdate) {

		    String classesPath = "WEB-INF"
			    + System.getProperty("file.separator") + "classes";

		    String servicesPath = classesPath
			    + System.getProperty("file.separator") + "it"
			    + System.getProperty("file.separator") + "people"
			    + System.getProperty("file.separator") + "fsl"
			    + System.getProperty("file.separator") + "servizi";

		    if (logger.isDebugEnabled()) {
			logger.debug("Paths data: ");
			logger.debug("\tBase path: " + basePath);
			logger.debug("\tClasses path: " + classesPath);
			logger.debug("\tServices path: " + servicesPath);
		    }

		    if (logger.isDebugEnabled()) {
			logger.debug("Getting path scanner instance...");
		    }

		    // Get all tablevalues DAT files
		    PathScanner pathScanner = new PathScanner();
		    TableValuesFileFilter tableValuesFileFilter = new TableValuesFileFilter();

		    Collection<File> tableValuesFiles = pathScanner
			    .listFiles(new File(sanitizeBasePath(basePath)
				    + servicesPath), tableValuesFileFilter);

		    // Chek if update DB from files
		    if (!tableValuesFiles.isEmpty()) {
			doUpdate(tableValuesFiles, feDbConnection,
				tableValuesQueries, preparedStatement,
				insertPreparedStatement,
				updatePreparedStatement, resultSet, basePath,
				classesPath, false);
		    } else {
			logger.warn("Table values files path and names is empty.");
		    }
		}

	    } else {
		logger.error("Table Values queries not loaded.");
		throw new PeopleDBException("Tablevalues queries not loaded.");
	    }

	    feDbConnection.setAutoCommit(autoCommit);

	} catch (SQLException e) {
	    doRollback(feDbConnection, autoCommit);
	    logger.error(e);
	} catch (PeopleDBException e) {
	    doRollback(feDbConnection, autoCommit);
	    logger.error(e);
	} finally {
	    try {
		if (resultSet != null) {
		    resultSet.close();
		}
		if (insertPreparedStatement != null) {
		    insertPreparedStatement.close();
		}
		if (updatePreparedStatement != null) {
		    updatePreparedStatement.close();
		}
		if (preparedStatement != null) {
		    preparedStatement.close();
		}
		if (feDbConnection != null) {
		    feDbConnection.close();
		}
	    } catch (SQLException ignore) {

	    }
	}

	return (doTableValuesUpdate);
    }

    /**
     * @param tableFiles
     * @param feDbConnection
     * @param tableValuesQueries
     * @param preparedStatement
     * @param insertPreparedStatement
     * @param updatePreparedStatement
     * @param resultSet
     * @param basePath
     * @param classesPath
     * @throws SQLException
     */
    private void doUpdate(Collection<File> tableFiles,
	    Connection feDbConnection, Properties tableValuesQueries,
	    PreparedStatement preparedStatement,
	    PreparedStatement insertPreparedStatement,
	    PreparedStatement updatePreparedStatement, ResultSet resultSet,
	    String basePath, String classesPath, boolean isFrameworkBundles)
	    throws SQLException {

	deleteDbTableValues(feDbConnection, preparedStatement,
		tableValuesQueries);

	if (logger.isDebugEnabled()) {
	    logger.debug("Iterating over form table values files path and names...");
	}

	@SuppressWarnings("unchecked")
	Iterator<File> tableValueFilesIterator = tableFiles.iterator();
	while (tableValueFilesIterator.hasNext()) {

	    try {
		// Recupera proprietà del tableValue file corrente
		File currentDatFile = tableValueFilesIterator.next();
		String tableValueFileNameAndPath = currentDatFile
			.getAbsolutePath();

		String absolutePath = currentDatFile.getAbsolutePath();
		String completeClassPath = sanitizeBasePath(basePath)
			+ classesPath;
		String datFileName = currentDatFile.getName();

		if (logger.isDebugEnabled()) {
		    logger.debug("tableValueFileNameAndPath = "
			    + tableValueFileNameAndPath);
		    logger.debug("completeClassPath = " + completeClassPath);
		    logger.debug("datFileName = " + datFileName);
		}

		UnpackedTableValueName unpackedTableValueName = TableValuesNameUtils
			.getUnpackedTableValueName(datFileName,
				completeClassPath, absolutePath);

		try {

		    if (logger.isDebugEnabled()) {
			logger.debug("Creating hash for Tablevalue file markers...");
		    }

		    // Hash delle proprietà del file e associazione timestamp
		    String tableValueNameToHash = unpackedTableValueName
			    .getTableId();
		    tableValueNameToHash += unpackedTableValueName.getProcess();

		    if (unpackedTableValueName.getNodeId() != null) {
			tableValueNameToHash += unpackedTableValueName
				.getNodeId();
		    }
		    if (unpackedTableValueName.getLocale() != null) {
			tableValueNameToHash += unpackedTableValueName
				.getLocale();
		    }

		    MessageDigest messageDigest = MessageDigest
			    .getInstance("SHA-1");
		    byte[] tableValueHash = messageDigest
			    .digest(tableValueNameToHash.getBytes("UTF-8"));

		    String bundleHash = convertToHex(tableValueHash);

		    if (logger.isDebugEnabled()) {
			logger.debug("Loading tablevalues for process "
				+ unpackedTableValueName.getProcess());
		    }

		    // Insert table value data and get the tablevalue id
		    insertPreparedStatement = feDbConnection
			    .prepareStatement(
				    tableValuesQueries
					    .getProperty("updater.insertTableValue.query"),
				    PreparedStatement.RETURN_GENERATED_KEYS);

		    insertPreparedStatement.setString(1,
			    unpackedTableValueName.getTableId());
		    insertPreparedStatement.setString(2,
			    unpackedTableValueName.getProcess());
		    insertPreparedStatement.setString(3,
			    unpackedTableValueName.getNodeId());
		    insertPreparedStatement.setString(4,
			    unpackedTableValueName.getLocale());
		    insertPreparedStatement.setString(5,
			    unpackedTableValueName.getCharset());
		    insertPreparedStatement.executeUpdate();
		    resultSet = insertPreparedStatement.getGeneratedKeys();

		    // Check if valid insert and Id
		    boolean validTableValueRef = false;
		    int tableValueRef = 0;

		    if (resultSet.next()) {
			tableValueRef = resultSet.getInt(1);
			validTableValueRef = true;
		    }

		    if (validTableValueRef) {
			if (logger.isDebugEnabled()) {
			    logger.debug("Inserted Table Value with id = "
				    + tableValueRef);
			}
			// Insert bundle hash
			Timestamp datFileTimestamp = new Timestamp(
				currentDatFile.lastModified());
			datFileTimestamp.setNanos(0);

			insertPreparedStatement.clearParameters();
			insertPreparedStatement = feDbConnection
				.prepareStatement(tableValuesQueries
					.getProperty("updater.insertBundleHash.query"));
			insertPreparedStatement.setString(1, bundleHash);
			insertPreparedStatement.setTimestamp(2,
				datFileTimestamp);
			insertPreparedStatement.executeUpdate();

			// Insert tablevalues properties
			if (logger.isDebugEnabled()) {
			    logger.debug("Preparing batch insertion of tablevalues values...");
			}
			insertTableValueProperties(tableValueFileNameAndPath,
				insertPreparedStatement, feDbConnection,
				tableValueRef, tableValuesQueries);

			// Commit all changes
			feDbConnection.commit();

		    } else {
			logger.warn("Unable to obtain bundle ref.");
			feDbConnection.rollback();
		    }

		} catch (NoSuchAlgorithmException e) {
		    logger.error(e);
		} catch (UnsupportedEncodingException e) {
		    logger.error(e);
		}
	    } catch (Exception e) {
		logger.error(e);
	    }

	}

    }

    /**
     * Delete all rows from tablevalues DB tables (tablevalues,
     * tablevalues_properties, tablevalues_hash)
     * 
     * @param feDbConnection
     * @param preparedStatement
     * @param tableValuesQueries
     */
    private void deleteDbTableValues(Connection feDbConnection,
	    PreparedStatement preparedStatement, Properties tableValuesQueries) {

	// Delete DB table content for table values
	try {
	    preparedStatement = feDbConnection
		    .prepareStatement(tableValuesQueries
			    .getProperty("updater.truncateTablevaluesProperties.query"));
	    preparedStatement.executeUpdate();

	    preparedStatement = feDbConnection
		    .prepareStatement(tableValuesQueries
			    .getProperty("updater.truncateTablevaluesHash.query"));
	    preparedStatement.executeUpdate();

	    preparedStatement = feDbConnection
		    .prepareStatement(tableValuesQueries
			    .getProperty("updater.truncateTablevalues.query"));
	    preparedStatement.executeUpdate();

	} catch (SQLException e) {
	    logger.error("SQL exception deleting DB tablevalus tables");
	}
    }

    /**
     * Read DAT file and insert tablevalues properties (value) for each line of
     * the file.
     * 
     * @param tableValueFileNameAndPath
     * @param insertPreparedStatement
     * @param feDbConnection
     * @param tableValueRef
     * @param tableValuesQueries
     */
    private void insertTableValueProperties(String tableValueFileNameAndPath,
	    PreparedStatement insertPreparedStatement,
	    Connection feDbConnection, int tableValueRef,
	    Properties tableValuesQueries) {

	try {
	    insertPreparedStatement = feDbConnection
		    .prepareStatement(tableValuesQueries
			    .getProperty("updater.insertTableValueProperties.query"));
	} catch (SQLException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}

	// Read from DAT files
	InputStream input = null;
	String line = null;

	try {
	    input = new FileInputStream(tableValueFileNameAndPath);

	    // TODO Add charset encoding management here
	    InputStreamReader inputStreamReader = new InputStreamReader(input);
	    BufferedReader reader = new BufferedReader(inputStreamReader);

	    while ((line = reader.readLine()) != null) {

		if (logger.isDebugEnabled()) {
		    logger.debug("Dat file LINE Value: " + line);
		}
		insertPreparedStatement.setString(1, line);
		insertPreparedStatement.setInt(2, tableValueRef);
		insertPreparedStatement.addBatch();
	    }
	    reader.close();

	} catch (FileNotFoundException e) {
	    logger.debug("Dat file not found", e);
	} catch (IOException e) {
	    logger.error("Error reading DAT file", e);
	} catch (SQLException e) {
	    logger.error("SQL exception inserting Table Values properties from DAT file:\n "
		    + tableValueFileNameAndPath
		    + " Exception: "
		    + e.getMessage());
	}

	if (logger.isDebugEnabled()) {
	    logger.debug("Executing batch insertion of bundle values...");
	}
	int[] batchResult;
	try {
	    batchResult = insertPreparedStatement.executeBatch();

	    if (logger.isDebugEnabled()) {
		logger.debug(batchResult.length
			+ " rows inserted from DAT file "
			+ tableValueFileNameAndPath);
	    }
	} catch (SQLException e) {
	    logger.error("SQL exception inserting Table Values properties from DAT file:\n "
		    + tableValueFileNameAndPath
		    + " Exception: "
		    + e.getMessage());
	}

    }

    /**
     * Check Table Values DB tables for data
     * 
     * @param feDbConnection
     * @param tableValuesQueries
     * @param resultSet
     * @param preparedStatement
     * @return boolean
     */
    private boolean checkDbEmptyTableValues(Connection feDbConnection,
	    Properties tableValuesQueries, PreparedStatement preparedStatement,
	    ResultSet resultSet) {

	boolean result = false;

	// Check for empty tables: if it's so, update from file Dat.
	try {
	    preparedStatement = feDbConnection
		    .prepareStatement(tableValuesQueries
			    .getProperty("updater.checkEmptyTablevalues.query"));
	    resultSet = preparedStatement.executeQuery();

	    if (resultSet.next() && resultSet.getInt(1) == 0) {
		result = true;
		if (logger.isDebugEnabled()) {
		    logger.debug("DB table empty: table values update needed");
		}
	    }

	} catch (SQLException e) {
	    logger.error("Unable to check if empty Table for Table Values: "
		    + e);
	}
	return result;
    }

    public Vector<String> getSynchronizationInstructions() {

	Vector<String> syncInstructions = new Vector<String>();

	Connection feDbConnection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	boolean autoCommit = false;

	boolean tableValuesQueriesLoaded = true;
	Properties tableValuesQueries = new Properties();

	if (logger.isDebugEnabled()) {
	    logger.debug("Reading tablevalues queries...");
	}
	try {
	    tableValuesQueries
		    .loadFromXML(DbTableValuesUpdater.class
			    .getResourceAsStream("/it/people/resources/TableValuesQueries.xml"));
	} catch (InvalidPropertiesFormatException e) {
	    logger.error(e);
	    tableValuesQueriesLoaded = false;
	} catch (IOException e) {
	    logger.error(e);
	    tableValuesQueriesLoaded = false;
	}

	if (tableValuesQueriesLoaded) {
	    if (logger.isDebugEnabled()) {
		logger.debug("Tablevalues queries loaded.");
	    }

	    try {
		feDbConnection = DBConnector.getInstance().connect(
			DBConnector.FEDB);
		autoCommit = feDbConnection.getAutoCommit();
		feDbConnection.setAutoCommit(false);

		if (logger.isDebugEnabled()) {
		    logger.debug("DELETING ALL FROM tablevalues Table to update from files..");
		}
		// Insert delete instructions to delete TABLEVALUES_PROPERTIES
		// and TABLEVALUES
		syncInstructions.add("delete from tablevalues_properties");
		syncInstructions.add("delete from tablevalues");

		// Read all bundles table values
		if (logger.isDebugEnabled()) {
		    logger.debug("Reading all table values...");
		}

		preparedStatement = feDbConnection
			.prepareStatement(tableValuesQueries
				.getProperty("updater.synchonizer.tableValues"));
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
		    String instruction = "insert into tablevalues(id, tableId, processName, nodeId, locale, charset) values(";
		    instruction += String.valueOf(resultSet.getInt(1)) + ", ";
		    instruction += "'"
			    + sanitizeSingleQuotes(resultSet.getString(2))
			    + "', ";
		    instruction += "'"
			    + sanitizeSingleQuotes(resultSet.getString(3))
			    + "', ";

		    if (resultSet.getString(4) == null)
			instruction += sanitizeSingleQuotes(resultSet
				.getString(4)) + ", ";
		    else
			instruction += "'"
				+ sanitizeSingleQuotes(resultSet.getString(4))
				+ "', ";

		    if (resultSet.getString(5) == null)
			instruction += sanitizeSingleQuotes(resultSet
				.getString(5)) + ", ";
		    else
			instruction += "'"
				+ sanitizeSingleQuotes(resultSet.getString(5))
				+ "', ";

		    if (resultSet.getString(6) == null)
			instruction += sanitizeSingleQuotes(resultSet
				.getString(6)) + ")";
		    else
			instruction += "'"
				+ sanitizeSingleQuotes(resultSet.getString(6))
				+ "')";

		    if (logger.isDebugEnabled()) {
			logger.debug("Adding instruction [ " + instruction
				+ "]");
		    }
		    syncInstructions.add(instruction);
		}

		// Read all bundles_properties table values
		if (logger.isDebugEnabled()) {
		    logger.debug("Reading all bundles_properties table values...");
		}
		preparedStatement = feDbConnection
			.prepareStatement(tableValuesQueries
				.getProperty("updater.synchonizer.tableValuesProperties"));
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
		    String instruction = "insert into tablevalues_properties(id, value, tablevalueRef) values(";
		    instruction += String.valueOf(resultSet.getInt(1)) + ", ";
		    instruction += "'"
			    + sanitizeSingleQuotes(resultSet.getString(2))
			    + "', ";
		    instruction += "'" + String.valueOf(resultSet.getInt(3))
			    + "')";
		    if (logger.isDebugEnabled()) {
			logger.debug("Adding instruction [ " + instruction
				+ "]");
		    }
		    syncInstructions.add(instruction);
		}

		feDbConnection.setAutoCommit(autoCommit);
	    } catch (SQLException e) {
		doRollback(feDbConnection, autoCommit);
		logger.error(e);
	    } catch (PeopleDBException e) {
		doRollback(feDbConnection, autoCommit);
		logger.error(e);
	    } finally {
		try {
		    if (resultSet != null) {
			resultSet.close();
		    }
		    if (preparedStatement != null) {
			preparedStatement.close();
		    }
		    if (feDbConnection != null) {
			feDbConnection.close();
		    }
		} catch (SQLException ignore) {

		}
	    }
	}

	return syncInstructions;
    }

    /**
     * @param feDbConnection
     * @param autoCommit
     */
    private void doRollback(Connection dbConnection, boolean autoCommit) {
	try {
	    dbConnection.rollback();
	    dbConnection.setAutoCommit(autoCommit);
	} catch (SQLException ignore) {

	}
    }

    /**
     * @param basePath
     * @return
     */
    private String sanitizeBasePath(String basePath) {

	if (!basePath.endsWith(System.getProperty("file.separator"))) {
	    return basePath + System.getProperty("file.separator");
	} else {
	    return basePath;
	}

    }

    private String sanitizeSingleQuotes(String value) {

	if (value != null && value.contains("'")) {
	    return value.replaceAll("'", "''");
	} else {
	    return value;
	}

    }

    /**
     * @param data
     * @return
     */
    private static String convertToHex(byte[] data) {
	StringBuffer buf = new StringBuffer();
	for (int i = 0; i < data.length; i++) {
	    int halfbyte = (data[i] >>> 4) & 0x0F;
	    int two_halfs = 0;
	    do {
		if ((0 <= halfbyte) && (halfbyte <= 9))
		    buf.append((char) ('0' + halfbyte));
		else
		    buf.append((char) ('a' + (halfbyte - 10)));
		halfbyte = data[i] & 0x0F;
	    } while (two_halfs++ < 1);
	}
	return buf.toString();
    }

}
