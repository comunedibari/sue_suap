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
package it.people.util.messagebundle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.people.db.DBConnector;

/**
 * @author Riccardo Foraf� - Engineering Ingegneria Informatica - Genova
 *         31/lug/2011 12.31.54
 */
public class DatabaseMessageBundleStrategy extends
	AbstractMessageBundleStrategy implements IMessageBundleHelper {

    private static final Logger logger = LogManager
	    .getLogger(DatabaseMessageBundleStrategy.class);

    private static final int ACTIVE_STATUS = 1;
    private static final int INACTIVE_STATUS = 0;

    private static final String FRAMEWORK_BUNDLES = "it.people.resources.FormLabels";

    private FilesMessageBundleStrategy delegate;

    /**
     * @param bundleBaseName
     */
    public DatabaseMessageBundleStrategy(String bundleBaseName) {
	super(bundleBaseName);
	delegate = new FilesMessageBundleStrategy(bundleBaseName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.people.util.messagebundle.IMessageBundleHelper#getAllFrameworkBundles
     * (javax.servlet.ServletContext)
     */
    /*
     * bundle nodeid locale bundle locale bundle nodeid bundle
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List getAllFrameworkBundles(ServletContext servletContext) {

	List result = new ArrayList();
	Properties messageBundlesQueries = new Properties();
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Vector localesBuffer = new Vector();
	Vector nodesIdsBuffer = new Vector();
	try {
	    messageBundlesQueries
		    .loadFromXML(DatabaseMessageBundleStrategy.class
			    .getResourceAsStream("/it/people/resources/MessageBundlesQueries.xml"));
	    connection = DBConnector.getInstance().connect(DBConnector.FEDB);

	    preparedStatement = connection
		    .prepareStatement(messageBundlesQueries
			    .getProperty("getBundleLocales.query"));
	    preparedStatement.setString(1, FRAMEWORK_BUNDLES);
	    preparedStatement.setInt(2, ACTIVE_STATUS);
	    resultSet = preparedStatement.executeQuery();
	    while (resultSet.next()) {
		localesBuffer.add(resultSet.getString(1));
	    }
	    if (logger.isDebugEnabled()) {
		logger.debug("Locales buffer size = " + localesBuffer.size());
	    }

	    preparedStatement = connection
		    .prepareStatement(messageBundlesQueries
			    .getProperty("getBundleNodesIds.query"));
	    preparedStatement.setString(1, FRAMEWORK_BUNDLES);
	    preparedStatement.setInt(2, ACTIVE_STATUS);
	    resultSet = preparedStatement.executeQuery();
	    while (resultSet.next()) {
		nodesIdsBuffer.add(resultSet.getString(1));
	    }

	    if (logger.isDebugEnabled()) {
		logger.debug("Nodes ids buffer size = " + nodesIdsBuffer.size());
	    }

	    if (!nodesIdsBuffer.isEmpty()) {
		Iterator nodesIdsBufferIterator = nodesIdsBuffer.iterator();
		while (nodesIdsBufferIterator.hasNext()) {
		    String nodeId = (String) nodesIdsBufferIterator.next();
		    if (logger.isDebugEnabled()) {
			logger.debug("Node id = " + nodeId);
		    }
		    if (!localesBuffer.isEmpty()) {
			Iterator localesBufferIterator = localesBuffer
				.iterator();
			while (localesBufferIterator.hasNext()) {
			    String locale = (String) localesBufferIterator
				    .next();
			    if (logger.isDebugEnabled()) {
				logger.debug("Executing query for node id = "
					+ nodeId + " and locale = " + locale);
			    }
			    preparedStatement = connection
				    .prepareStatement(messageBundlesQueries
					    .getProperty("bundleNodeIdLocale.query"));
			    preparedStatement.setString(1, FRAMEWORK_BUNDLES);
			    preparedStatement.setString(2, nodeId);
			    preparedStatement.setString(3, locale);
			    preparedStatement.setInt(4, ACTIVE_STATUS);
			    preparedStatement.setInt(5, ACTIVE_STATUS);
			    resultSet = preparedStatement.executeQuery();
			    if (resultSet.next()) {
				result.add(new DbResourceBundle(resultSet));
			    } // if (resultSet.next())
			    preparedStatement = connection
				    .prepareStatement(messageBundlesQueries
					    .getProperty("bundleLocale.query"));
			    preparedStatement.setString(1, FRAMEWORK_BUNDLES);
			    preparedStatement.setString(2, locale);
			    preparedStatement.setInt(3, ACTIVE_STATUS);
			    preparedStatement.setInt(4, ACTIVE_STATUS);
			    resultSet = preparedStatement.executeQuery();
			    if (resultSet.next()) {
				result.add(new DbResourceBundle(resultSet));
			    } // if (resultSet.next())
			} // while(localesBufferIterator.hasNext())
		    } // if (!localesBuffer.isEmpty())
		    preparedStatement = connection
			    .prepareStatement(messageBundlesQueries
				    .getProperty("bundleNodeId.query"));
		    preparedStatement.setString(1, FRAMEWORK_BUNDLES);
		    preparedStatement.setString(2, nodeId);
		    preparedStatement.setInt(3, ACTIVE_STATUS);
		    preparedStatement.setInt(4, ACTIVE_STATUS);
		    resultSet = preparedStatement.executeQuery();
		    if (resultSet.next()) {
			result.add(new DbResourceBundle(resultSet));
		    } // if (resultSet.next())
		} // while(nodesIdsBufferIterator.hasNext())
	    } // if (!nodesIdsBuffer.isEmpty())

	    preparedStatement = connection
		    .prepareStatement(messageBundlesQueries
			    .getProperty("bundle.query"));
	    preparedStatement.setString(1, FRAMEWORK_BUNDLES);
	    preparedStatement.setInt(2, ACTIVE_STATUS);
	    preparedStatement.setInt(3, ACTIVE_STATUS);
	    resultSet = preparedStatement.executeQuery();
	    boolean found = false;
	    while (resultSet.next()) {
		found = true;
		result.add(new DbResourceBundle(resultSet));
	    }
	    if (!found) {
		if (logger.isInfoEnabled()) {
		    logger.info("No db framework bundle found, switching to delegate...");
		}
		result = delegate.getAllFrameworkBundles(servletContext);
	    } else {
		if (logger.isInfoEnabled()) {
		    logger.info("Db framework bundle found, no need to switching to delegate...");
		}
	    }
	} catch (Exception e) {
	    logger.warn(
		    "Exception reading framework bundle from db, switching to delegate...",
		    e);
	    result = delegate.getAllFrameworkBundles(servletContext);
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
     * @see it.people.util.messagebundle.IMessageBundleHelper#
     * internalResourceBundleInstantiation(java.lang.String)
     */
    public ResourceBundle internalResourceBundleInstantiation(String bundleName) {

	ResourceBundle result = null;
	Properties messageBundlesQueries = new Properties();
	UnpackedBundleName unpackedBundleName = BundleNameUtils
		.getUnpackedBundleName(bundleName);

	boolean isValidNodeId = StringUtils.isNotBlank(unpackedBundleName
		.getNodeId());
	boolean isValidLocale = StringUtils.isNotBlank(unpackedBundleName
		.getLocale());

	if (logger.isDebugEnabled()) {
	    logger.debug("Bundle name = " + bundleName);
	    logger.debug("Search by node id? " + isValidNodeId);
	    logger.debug("Search by locale? " + isValidLocale);
	}

	String bundle = "";
	String sql = "";

	if (StringUtils.isNotBlank(unpackedBundleName.getFramework())) {
	    bundle = FRAMEWORK_BUNDLES;
	    if (logger.isDebugEnabled()) {
		logger.debug("Search for framework bundle...");
	    }
	}

	if (StringUtils.isNotBlank(unpackedBundleName.getProcess())) {
	    bundle = unpackedBundleName.getProcess();
	    if (logger.isDebugEnabled()) {
		logger.debug("Search for process bundle = '" + bundle + "'...");
	    }
	}

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	try {
	    connection = DBConnector.getInstance().connect(DBConnector.FEDB);
	    messageBundlesQueries
		    .loadFromXML(DatabaseMessageBundleStrategy.class
			    .getResourceAsStream("/it/people/resources/MessageBundlesQueries.xml"));
	    if (isValidNodeId && isValidLocale) {
		sql = messageBundlesQueries
			.getProperty("bundleNodeIdLocale.query");
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, bundle);
		preparedStatement.setString(2, unpackedBundleName.getNodeId());
		preparedStatement.setString(3, unpackedBundleName.getLocale());
		preparedStatement.setInt(4, ACTIVE_STATUS);
		preparedStatement.setInt(5, ACTIVE_STATUS);
	    } else if (isValidNodeId && !isValidLocale) {
		sql = messageBundlesQueries.getProperty("bundleNodeId.query");
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, bundle);
		preparedStatement.setString(2, unpackedBundleName.getNodeId());
		preparedStatement.setInt(3, ACTIVE_STATUS);
		preparedStatement.setInt(4, ACTIVE_STATUS);
	    } else if (!isValidNodeId && isValidLocale) {
		sql = messageBundlesQueries.getProperty("bundleLocale.query");
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, bundle);
		preparedStatement.setString(2, unpackedBundleName.getLocale());
		preparedStatement.setInt(3, ACTIVE_STATUS);
		preparedStatement.setInt(4, ACTIVE_STATUS);
	    } else {
		sql = messageBundlesQueries.getProperty("bundle.query");
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, bundle);
		preparedStatement.setInt(2, ACTIVE_STATUS);
		preparedStatement.setInt(3, ACTIVE_STATUS);
	    }

	    resultSet = preparedStatement.executeQuery();
	    boolean found = false;
	    while (resultSet.next()) {
		found = true;
		result = new DbResourceBundle(resultSet);
	    }
	    if (!found) {
		if (logger.isInfoEnabled()) {
		    logger.info("No bundles found, switching to delegate...");
		}
		result = delegate
			.internalResourceBundleInstantiation(bundleName);
	    } else {
		if (logger.isInfoEnabled()) {
		    logger.info("Db bundles found, no need to switching to delegate...");
		}
	    }
	} catch (Exception e) {
	    try {
		logger.warn(
			"Exception reading bundle from db, switching to delegate...",
			e);
		result = delegate
			.internalResourceBundleInstantiation(bundleName);
	    } catch (IOException e1) {
	    }
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

}
