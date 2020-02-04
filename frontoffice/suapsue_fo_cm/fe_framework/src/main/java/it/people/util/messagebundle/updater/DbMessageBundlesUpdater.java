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
package it.people.util.messagebundle.updater;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Vector;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.people.db.DBConnector;
import it.people.exceptions.PeopleDBException;
import it.people.util.PathScanner;
import it.people.util.messagebundle.BundleNameUtils;
import it.people.util.messagebundle.UnpackedBundleName;
import it.people.util.resourcesupdater.IResourceUpdater;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         23/set/2011 12.08.44
 */
public class DbMessageBundlesUpdater implements IResourceUpdater {

    private static Logger logger = LogManager
	    .getLogger(DbMessageBundlesUpdater.class);

    private static DbMessageBundlesUpdater instance = null;

    private DbMessageBundlesUpdater() {

    }

    public static DbMessageBundlesUpdater getInstance() {

	if (instance == null) {
	    instance = new DbMessageBundlesUpdater();
	}

	return instance;
    }

    public boolean update(String basePath) {

	boolean fwkMessagesUpdated = false;
	boolean svcMessagesUpdated = false;
	Connection feDbConnection = null;
	PreparedStatement preparedStatement = null;
	PreparedStatement insertPreparedStatement = null;
	PreparedStatement updatePreparedStatement = null;
	ResultSet resultSet = null;
	boolean autoCommit = false;

	try {
	    feDbConnection = DBConnector.getInstance()
		    .connect(DBConnector.FEDB);
	    autoCommit = feDbConnection.getAutoCommit();
	    feDbConnection.setAutoCommit(false);

	    boolean messageBundlesQueriesLoaded = true;
	    Properties messageBundlesQueries = new Properties();

	    if (logger.isDebugEnabled()) {
		logger.debug("Reading message bundles queries...");
	    }
	    try {
		messageBundlesQueries
			.loadFromXML(DbMessageBundlesUpdater.class
				.getResourceAsStream("/it/people/resources/MessageBundlesQueries.xml"));
	    } catch (InvalidPropertiesFormatException e) {
		logger.error(e);
		messageBundlesQueriesLoaded = false;
	    } catch (IOException e) {
		logger.error(e);
		messageBundlesQueriesLoaded = false;
	    }

	    if (messageBundlesQueriesLoaded) {
		if (logger.isDebugEnabled()) {
		    logger.debug("Message bundles queries loaded.");
		}

		String classesPath = "WEB-INF"
			+ System.getProperty("file.separator") + "classes";

		String formLabelsPath = classesPath
			+ System.getProperty("file.separator") + "it"
			+ System.getProperty("file.separator") + "people"
			+ System.getProperty("file.separator") + "resources";

		String servicesLabelsPath = classesPath
			+ System.getProperty("file.separator") + "it"
			+ System.getProperty("file.separator") + "people"
			+ System.getProperty("file.separator") + "fsl"
			+ System.getProperty("file.separator") + "servizi";

		if (logger.isDebugEnabled()) {
		    logger.debug("Paths data: ");
		    logger.debug("\tBase path: " + basePath);
		    logger.debug("\tClasses path: " + classesPath);
		    logger.debug("\tForm labels path: " + formLabelsPath);
		    logger.debug("\tServices path: " + servicesLabelsPath);
		}

		if (logger.isDebugEnabled()) {
		    logger.debug("Getting path scanner instance...");
		}
		PathScanner pathScanner = new PathScanner();

		// Get all FormLabels files
		if (logger.isDebugEnabled()) {
		    logger.debug("Getting form labels filter instance...");
		}
		FormLabelsFileFilter formLabelsFileFilter = new FormLabelsFileFilter();
		if (logger.isDebugEnabled()) {
		    logger.debug("Getting form labels files path and names...");
		}
		@SuppressWarnings("unchecked")
		Vector<String> formLabelsFiles = pathScanner.scanFiles(
			new File(sanitizeBasePath(basePath) + formLabelsPath),
			formLabelsFileFilter);
		if (!formLabelsFiles.isEmpty()) {
		    fwkMessagesUpdated = doUpdate(formLabelsFiles,
			    feDbConnection, messageBundlesQueries,
			    preparedStatement, insertPreparedStatement,
			    updatePreparedStatement, resultSet, basePath,
			    classesPath, true);
		} else {
		    logger.warn("Form labels files path and names is empty.");
		}

		// Get all services files
		if (logger.isDebugEnabled()) {
		    logger.debug("Getting services labels filter instance...");
		}
		MessaggiPropertiesFileFilter messaggiPropertiesFileFilter = new MessaggiPropertiesFileFilter();
		if (logger.isDebugEnabled()) {
		    logger.debug("Getting services labels files path and names...");
		}
		@SuppressWarnings("unchecked")
		Vector<String> servicesLabelsFiles = pathScanner.scanFiles(
			new File(sanitizeBasePath(basePath)
				+ servicesLabelsPath),
			messaggiPropertiesFileFilter);

		if (logger.isDebugEnabled()) {
		    Iterator iter = servicesLabelsFiles.iterator();
		    while (iter.hasNext()) {
			logger.debug("servicesLabelFile: "
				+ String.valueOf(iter.next()));
		    }
		}

		if (!formLabelsFiles.isEmpty()) {
		    svcMessagesUpdated = doUpdate(servicesLabelsFiles,
			    feDbConnection, messageBundlesQueries,
			    preparedStatement, insertPreparedStatement,
			    updatePreparedStatement, resultSet, basePath,
			    classesPath, false);
		} else {
		    logger.warn("Form labels files path and names is empty.");
		}

	    } else {
		logger.error("Message bundles queries not loaded.");
		throw new PeopleDBException(
			"Message bundles queries not loaded.");
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
	// synchronizeLegacyDb();
	return (fwkMessagesUpdated || svcMessagesUpdated);

    }

    /**
     * @param labelsFiles
     * @param feDbConnection
     * @param messageBundlesQueries
     * @param preparedStatement
     * @param insertPreparedStatement
     * @param updatePreparedStatement
     * @param resultSet
     * @param basePath
     * @param classesPath
     * @throws SQLException
     */
    private boolean doUpdate(@SuppressWarnings("rawtypes") Vector labelsFiles,
	    Connection feDbConnection, Properties messageBundlesQueries,
	    PreparedStatement preparedStatement,
	    PreparedStatement insertPreparedStatement,
	    PreparedStatement updatePreparedStatement, ResultSet resultSet,
	    String basePath, String classesPath, boolean isFrameworkBundles)
	    throws SQLException {

	boolean result = false;
	if (logger.isDebugEnabled()) {
	    logger.debug("Iterating over form labels files path and names...");
	}
	@SuppressWarnings("unchecked")
	Iterator<String> formLabelsFilesIterator = labelsFiles.iterator();
	while (formLabelsFilesIterator.hasNext()) {
	    try {
		String formLabelsFileNameAndPath = formLabelsFilesIterator
			.next();
		File formLabelFile = new File(formLabelsFileNameAndPath);
		String fileName = formLabelFile.getName();
		String bundleName = getMessageFilePath(
			formLabelFile.getAbsolutePath(),
			sanitizeBasePath(basePath) + classesPath);

		if (logger.isDebugEnabled()) {
		    logger.debug("bundleName = " + bundleName);
		    logger.debug("formLabelsFileNameAndPath = "
			    + formLabelsFileNameAndPath);
		    logger.debug("fileName = " + fileName);
		}

		try {
		    if (logger.isDebugEnabled()) {
			logger.debug("Creating hash of bundle name...");
		    }
		    UnpackedBundleName unpackedBundleName = BundleNameUtils
			    .getUnpackedBundleName(bundleName);
		    String bundleNameToHashing = bundleName;
		    if (unpackedBundleName.getNodeId() != null) {
			bundleNameToHashing += unpackedBundleName.getNodeId();
		    }
		    if (unpackedBundleName.getLocale() != null) {
			bundleNameToHashing += unpackedBundleName.getLocale();
		    }

		    MessageDigest messageDigest = MessageDigest
			    .getInstance("SHA-1");
		    byte[] bundleNameHash = messageDigest
			    .digest(bundleNameToHashing.getBytes("UTF-8"));

		    String bundleHash = convertToHex(bundleNameHash);

		    if (logger.isDebugEnabled()) {
			logger.debug("Hash of file name = " + bundleHash);
			logger.debug("Searching bundle timestamp for hash = "
				+ bundleHash);
		    }

		    preparedStatement = feDbConnection
			    .prepareStatement(messageBundlesQueries
				    .getProperty("updater.bundleHashTimestamp.query"));
		    preparedStatement.setString(1, bundleHash);

		    resultSet = preparedStatement.executeQuery();

		    Timestamp hashTimestamp = null;
		    if (resultSet.next()) {
			hashTimestamp = resultSet.getTimestamp(1);
		    }

		    Timestamp formLabelFileTimestamp = new Timestamp(
			    formLabelFile.lastModified());
		    formLabelFileTimestamp.setNanos(0);
		    if (hashTimestamp != null) {
			if (logger.isDebugEnabled()) {
			    logger.debug("Bundle timestamp = "
				    + hashTimestamp.toString());
			    logger.debug("Bundle file timestamp = "
				    + formLabelFileTimestamp.toString());
			}
			if (formLabelFileTimestamp.after(hashTimestamp)) {
			    if (logger.isDebugEnabled()) {
				logger.debug("Bundle timestamp older than bundle file timestamp: proceed to bundle update.");
			    }
			    if (logger.isDebugEnabled()) {
				logger.debug("Loading resource bundle for bundle "
					+ bundleName);
			    }

			    InputStreamReader reader = new InputStreamReader(
				    new FileInputStream(
					    formLabelsFileNameAndPath));
			    ResourceBundle resourceBundle = new PropertyResourceBundle(
				    reader);

			    Iterator<String> keySetIterator = resourceBundle
				    .keySet().iterator();
			    if (logger.isDebugEnabled()) {
				logger.debug("Retrieving unpacked bundle name object...");
			    }

			    // Get bundle ref
			    boolean isNullNodeId = false;
			    boolean isNullLocale = false;
			    String query = messageBundlesQueries
				    .getProperty("updater.getBundleId.query");
			    String buffer = query;
			    if (unpackedBundleName.getNodeId() == null
				    || (unpackedBundleName.getNodeId() != null && unpackedBundleName
					    .getNodeId().equalsIgnoreCase(
						    "null"))) {
				buffer = query.replace("nodeId = ?",
					"nodeId is null");
				query = buffer;
				isNullNodeId = true;
			    }
			    if (unpackedBundleName.getLocale() == null) {
				buffer = query.replace("_locale = ?",
					"_locale is null");
				query = buffer;
				isNullLocale = true;
			    }
			    insertPreparedStatement = feDbConnection
				    .prepareStatement(query);
			    int parameterIndex = 1;
			    insertPreparedStatement.setString(
				    parameterIndex,
				    isFrameworkBundles ? unpackedBundleName
					    .getFramework()
					    : unpackedBundleName.getProcess());
			    if (!isNullNodeId) {
				parameterIndex++;
				insertPreparedStatement.setString(
					parameterIndex,
					unpackedBundleName.getNodeId());
			    }
			    if (!isNullLocale) {
				parameterIndex++;
				insertPreparedStatement.setString(
					parameterIndex,
					unpackedBundleName.getLocale());
			    }
			    resultSet = insertPreparedStatement.executeQuery();

			    boolean validBundleRef = false;
			    int bundleRef = 0;
			    if (resultSet.next()) {
				bundleRef = resultSet.getInt(1);
				validBundleRef = true;
			    }
			    if (validBundleRef) {
				result = true;
				if (logger.isDebugEnabled()) {
				    logger.debug("Updating bundle with id = "
					    + bundleRef);
				}
				// Update bundle hash
				insertPreparedStatement.clearParameters();
				insertPreparedStatement = feDbConnection
					.prepareStatement(messageBundlesQueries
						.getProperty("updater.updateBundleHash.query"));
				insertPreparedStatement.setTimestamp(1,
					formLabelFileTimestamp);
				insertPreparedStatement
					.setString(2, bundleHash);
				insertPreparedStatement.executeUpdate();

				// For each key value pair search if exists: if
				// exists do update else insert
				insertPreparedStatement = feDbConnection
					.prepareStatement(messageBundlesQueries
						.getProperty("updater.insertBundleProperties.query"));
				// updatePreparedStatement =
				// feDbConnection.prepareStatement(
				// messageBundlesQueries.getProperty("updater.updateBundleProperties.query"));
				int insertAmount = 0;
				int updateAmount = 0;
				while (keySetIterator.hasNext()) {
				    String key = keySetIterator.next();
				    String value = resourceBundle
					    .getString(key);
				    if (logger.isDebugEnabled()) {
					logger.debug("Key = " + key);
					logger.debug("Value = " + value);
				    }

				    preparedStatement = feDbConnection
					    .prepareStatement(messageBundlesQueries
						    .getProperty("updater.bundlePropertyExists.query"));
				    preparedStatement.setInt(1, bundleRef);
				    preparedStatement.setString(2, key);
				    resultSet = preparedStatement
					    .executeQuery();

				    if (resultSet.next()
					    && resultSet.getInt(1) > 0) {
					// updatePreparedStatement.setString(1,
					// value);
					// updatePreparedStatement.setInt(2,
					// bundleRef);
					// updatePreparedStatement.setString(3,
					// key);
					// updatePreparedStatement.addBatch();
					updateAmount++;
				    } else {
					insertPreparedStatement.setInt(1,
						bundleRef);
					insertPreparedStatement.setString(2,
						key);
					insertPreparedStatement.setString(3,
						value);
					insertPreparedStatement.setInt(4, 1);
					insertPreparedStatement.setString(5,
						null);
					insertPreparedStatement.addBatch();
					insertAmount++;
				    }
				}

				int[] batchInsertAmount = insertPreparedStatement
					.executeBatch();
				// int[] batchUpdateAmount =
				// updatePreparedStatement.executeBatch();

				if (batchInsertAmount.length != insertAmount) {
				    logger.error("Batch insert of bundle returns result different from expected size.");
				} else {
				    if (logger.isDebugEnabled()) {
					logger.debug(insertAmount
						+ " rows inserted for bundle "
						+ bundleName);
				    }
				}
				// if (batchUpdateAmount.length != updateAmount)
				// {
				// logger.error("Batch update of bundle returns result different from expected size.");
				// } else {
				// if (logger.isDebugEnabled()) {
				// logger.debug(updateAmount +
				// " rows updated for bundle " + bundleName);
				// }
				// }
			    }

			} else {
			    if (logger.isDebugEnabled()) {
				logger.debug("Bundle timestamp equals to bundle file timestamp: no bundle update needed.");
			    }
			}
		    } else {
			if (logger.isDebugEnabled()) {
			    logger.debug("No timestamp found for hash: proceed to bundle insertion.");
			}
			if (logger.isDebugEnabled()) {
			    logger.debug("Loading resource bundle for bundle "
				    + bundleName);
			}

			InputStreamReader reader = new InputStreamReader(
				new FileInputStream(formLabelsFileNameAndPath));
			ResourceBundle resourceBundle = new PropertyResourceBundle(
				reader);

			Iterator<String> keySetIterator = resourceBundle
				.keySet().iterator();
			if (logger.isDebugEnabled()) {
			    logger.debug("Retrieving unpacked bundle name object...");
			}

			if (logger.isDebugEnabled()) {
			    logger.debug("Inserting bundle data...");
			}
			result = true;
			// Insert bundle data and get the bundle id
			insertPreparedStatement = feDbConnection
				.prepareStatement(
					messageBundlesQueries
						.getProperty("updater.insertBundle.query"),
					PreparedStatement.RETURN_GENERATED_KEYS);
			if (isFrameworkBundles) {
			    insertPreparedStatement.setString(1,
				    unpackedBundleName.getFramework());
			} else {
			    insertPreparedStatement.setString(1,
				    unpackedBundleName.getProcess());
			}
			insertPreparedStatement.setString(2,
				unpackedBundleName.getNodeId());
			insertPreparedStatement.setString(3,
				unpackedBundleName.getLocale());
			insertPreparedStatement.setInt(4, 1);
			insertPreparedStatement.setString(5, null);
			insertPreparedStatement.executeUpdate();
			resultSet = insertPreparedStatement.getGeneratedKeys();

			boolean validBundleRef = false;
			int bundleRef = 0;
			if (resultSet.next()) {
			    bundleRef = resultSet.getInt(1);
			    validBundleRef = true;
			}

			if (validBundleRef) {
			    if (logger.isDebugEnabled()) {
				logger.debug("Inserted bundle with id = "
					+ bundleRef);
			    }
			    // Insert bundle hash
			    insertPreparedStatement.clearParameters();
			    insertPreparedStatement = feDbConnection
				    .prepareStatement(messageBundlesQueries
					    .getProperty("updater.insertBundleHash.query"));
			    insertPreparedStatement.setString(1, bundleHash);
			    insertPreparedStatement.setTimestamp(2,
				    formLabelFileTimestamp);
			    insertPreparedStatement.executeUpdate();

			    if (logger.isDebugEnabled()) {
				logger.debug("Preparing batch insertion of bundle values...");
			    }
			    insertPreparedStatement = feDbConnection
				    .prepareStatement(messageBundlesQueries
					    .getProperty("updater.insertBundleProperties.query"));
			    while (keySetIterator.hasNext()) {
				String key = keySetIterator.next();
				String value = resourceBundle.getString(key);
				if (logger.isDebugEnabled()) {
				    logger.debug("Key = " + key);
				    logger.debug("Value = " + value);
				}
				insertPreparedStatement.setInt(1, bundleRef);
				insertPreparedStatement.setString(2, key);
				insertPreparedStatement.setString(3, value);
				insertPreparedStatement.setInt(4, 1);
				insertPreparedStatement.setString(5, null);
				insertPreparedStatement.addBatch();
			    }
			    if (logger.isDebugEnabled()) {
				logger.debug("Executing batch insertion of bundle values...");
			    }
			    int[] batchResult = insertPreparedStatement
				    .executeBatch();
			    if (batchResult.length != resourceBundle.keySet()
				    .size()) {
				logger.error("Batch insert of bundle returns result different from key set size.");
			    } else {
				if (logger.isDebugEnabled()) {
				    logger.debug(batchResult.length
					    + " rows inserted for bundle "
					    + bundleName);
				}
			    }
			    feDbConnection.commit();
			} else {
			    logger.warn("Unable to obtain bundle ref.");
			    feDbConnection.rollback();
			}
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

	return result;

    }

    public Vector getSynchronizationInstructions() {

	Vector result = new Vector();

	Connection feDbConnection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	boolean autoCommit = false;

	boolean messageBundlesQueriesLoaded = true;
	Properties messageBundlesQueries = new Properties();

	if (logger.isDebugEnabled()) {
	    logger.debug("Reading message bundles queries...");
	}
	try {
	    messageBundlesQueries
		    .loadFromXML(DbMessageBundlesUpdater.class
			    .getResourceAsStream("/it/people/resources/MessageBundlesQueries.xml"));
	} catch (InvalidPropertiesFormatException e) {
	    logger.error(e);
	    messageBundlesQueriesLoaded = false;
	} catch (IOException e) {
	    logger.error(e);
	    messageBundlesQueriesLoaded = false;
	}

	if (messageBundlesQueriesLoaded) {
	    if (logger.isDebugEnabled()) {
		logger.debug("Message bundles queries loaded.");
	    }

	    try {
		feDbConnection = DBConnector.getInstance().connect(
			DBConnector.FEDB);
		autoCommit = feDbConnection.getAutoCommit();
		feDbConnection.setAutoCommit(false);

		// Read all bundles table values
		if (logger.isDebugEnabled()) {
		    logger.debug("Reading all bundles table values...");
		}
		preparedStatement = feDbConnection
			.prepareStatement(messageBundlesQueries
				.getProperty("updater.synchonizer.bundles"));
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
		    String instruction = "insert into bundles(id, bundle, nodeId, _locale, active, _group) values(";
		    instruction += String.valueOf(resultSet.getInt(1)) + ", ";
		    instruction += "'"
			    + sanitizeSingleQuotes(resultSet.getString(2))
			    + "', ";

		    if (resultSet.getString(3) == null)
			instruction += sanitizeSingleQuotes(resultSet
				.getString(3)) + ", ";
		    else
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

		    instruction += String.valueOf(resultSet.getInt(5)) + ", ";

		    if (resultSet.getString(6) == null)
			instruction += sanitizeSingleQuotes(resultSet
				.getString(6)) + ") ";
		    else
			instruction += "'"
				+ sanitizeSingleQuotes(resultSet.getString(6))
				+ "') ";

		    if (logger.isDebugEnabled()) {
			logger.debug("Adding instruction [ " + instruction
				+ "]");
		    }
		    result.add(instruction);
		}

		// Read all bundles_properties table values
		if (logger.isDebugEnabled()) {
		    logger.debug("Reading all bundles_properties table values...");
		}
		preparedStatement = feDbConnection
			.prepareStatement(messageBundlesQueries
				.getProperty("updater.synchonizer.bundlesProperties"));
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
		    String instruction = "insert into bundles_properties(id, bundleRef, _key, _value, active, _group) values(";
		    instruction += String.valueOf(resultSet.getInt(1)) + ", ";
		    instruction += String.valueOf(resultSet.getInt(2)) + ", ";
		    instruction += "'"
			    + sanitizeSingleQuotes(resultSet.getString(3))
			    + "', ";
		    instruction += "'"
			    + sanitizeSingleQuotes(resultSet.getString(4))
			    + "', ";
		    instruction += String.valueOf(resultSet.getInt(5)) + ", ";

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
		    result.add(instruction);
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

	return result;

    }

    // private void synchronizeLegacyDb() {
    //
    // if (logger.isDebugEnabled()) {
    // logger.debug("Synchronizing legacy db...");
    // }
    //
    // Connection feDbConnection = null;
    // Connection legacyDbConnection = null;
    // PreparedStatement preparedStatement = null;
    // PreparedStatement insertPreparedStatement = null;
    // ResultSet resultSet = null;
    //
    // boolean bundlesSynchronized = false;
    // boolean bundlesPropertiesSynchronized = false;
    // boolean legacyDbAutoCommit = true;
    //
    // try {
    //
    // feDbConnection = DBConnector.getInstance().connect(DBConnector.FEDB);
    // legacyDbConnection =
    // DBConnector.getInstance().connect(DBConnector.LEGACYDB);
    //
    // legacyDbAutoCommit = legacyDbConnection.getAutoCommit();
    // legacyDbConnection.setAutoCommit(false);
    //
    // if (logger.isDebugEnabled()) {
    // logger.debug("Truncating legacy db bundles tables...");
    // }
    // preparedStatement =
    // legacyDbConnection.prepareStatement("truncate bundles_properties");
    // preparedStatement.execute();
    // preparedStatement =
    // legacyDbConnection.prepareStatement("truncate bundles");
    // preparedStatement.execute();
    // if (logger.isDebugEnabled()) {
    // logger.debug("Legacy db bundles tables truncated.");
    // }
    //
    // if (logger.isDebugEnabled()) {
    // logger.debug("Updating legacy db bundles tables...");
    // }
    //
    //
    // String sqlSelect = "select * from bundles";
    // String sqlInsert =
    // "insert into bundles(id, bundle, nodeId, _locale, active, _group) values(?, ?, ?, ?, ?, ?)";
    // preparedStatement = feDbConnection.prepareStatement(sqlSelect);
    // resultSet = preparedStatement.executeQuery();
    //
    // int insertExecuted = 0;
    // boolean insertPreparedStatementExecuted = false;
    // insertPreparedStatement = legacyDbConnection.prepareStatement(sqlInsert);
    // while(resultSet.next()) {
    // insertPreparedStatement.setInt(1, resultSet.getInt(1));
    // insertPreparedStatement.setString(2, resultSet.getString(2));
    // insertPreparedStatement.setString(3, resultSet.getString(3));
    // insertPreparedStatement.setString(4, resultSet.getString(4));
    // insertPreparedStatement.setInt(5, resultSet.getInt(5));
    // insertPreparedStatement.setString(6, resultSet.getString(6));
    // insertPreparedStatement.addBatch();
    // insertPreparedStatementExecuted = true;
    // insertExecuted++;
    // }
    // if (insertPreparedStatementExecuted) {
    // int[] result = insertPreparedStatement.executeBatch();
    // bundlesSynchronized = result.length == insertExecuted;
    //
    // sqlSelect = "select * from bundles_properties";
    // sqlInsert =
    // "insert into bundles_properties(id, bundleRef, _key, _value, active, _group) values(?, ?, ?, ?, ?, ?)";
    // preparedStatement = feDbConnection.prepareStatement(sqlSelect);
    // resultSet = preparedStatement.executeQuery();
    //
    // insertExecuted = 0;
    // insertPreparedStatementExecuted = false;
    // insertPreparedStatement = legacyDbConnection.prepareStatement(sqlInsert);
    // while(resultSet.next()) {
    // insertPreparedStatement.setInt(1, resultSet.getInt(1));
    // insertPreparedStatement.setInt(2, resultSet.getInt(2));
    // insertPreparedStatement.setString(3, resultSet.getString(3));
    // insertPreparedStatement.setString(4, resultSet.getString(4));
    // insertPreparedStatement.setInt(5, resultSet.getInt(5));
    // insertPreparedStatement.setString(6, resultSet.getString(6));
    // insertPreparedStatement.addBatch();
    // insertPreparedStatementExecuted = true;
    // insertExecuted++;
    // }
    //
    // if (insertPreparedStatementExecuted) {
    // result = insertPreparedStatement.executeBatch();
    // bundlesPropertiesSynchronized = result.length == insertExecuted;
    // }
    //
    // }
    //
    // if (logger.isDebugEnabled()) {
    // logger.debug("Legacy db bundles tables updated.");
    // }
    //
    // if (bundlesSynchronized && bundlesPropertiesSynchronized) {
    // legacyDbConnection.commit();
    // } else {
    // legacyDbConnection.rollback();
    // }
    //
    // } catch(SQLException e) {
    // doRollback(legacyDbConnection, legacyDbAutoCommit);
    // logger.error("Fe db and Legacy db bundles synchronization failure.", e);
    // } catch (PeopleDBException e) {
    // doRollback(legacyDbConnection, legacyDbAutoCommit);
    // logger.error("Fe db and Legacy db bundles synchronization failure.", e);
    // } finally {
    // try {
    // if (resultSet != null) {
    // resultSet.close();
    // }
    // if (preparedStatement != null) {
    // preparedStatement.close();
    // }
    // if (feDbConnection != null) {
    // feDbConnection.close();
    // }
    // if (legacyDbConnection != null) {
    // legacyDbConnection.setAutoCommit(legacyDbAutoCommit);
    // legacyDbConnection.close();
    // }
    // } catch(SQLException ignore) {
    // }
    // }
    //
    // }

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
     * @param completePath
     * @param completeClassesPath
     * @return
     */
    private String getMessageFilePath(String completePath,
	    String completeClassesPath) {

	return completePath.substring(completeClassesPath.length() + 1)
		.replace(System.getProperty("file.separator"), ".")
		.replace(".properties", "");

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
