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
package it.people.feservice;

import it.people.feservice.beans.AvailableService;
import it.people.feservice.beans.DbAvailableService;
import it.people.feservice.beans.FEServiceReferenceVO;
import it.people.feservice.beans.NodeDeployedServices;
import it.people.feservice.beans.PeopleAdministratorVO;
import it.people.feservice.beans.ServiceOnlineHelpWorkflowElements;
import it.people.feservice.beans.VelocityTemplateBean;
import it.people.feservice.beans.VelocityTemplateDataVO;
import it.people.feservice.exceptions.FEInterfaceExtException;
import it.people.feservice.utils.Base64;
import it.people.feservice.utils.ConfigDocumentReader;
import it.people.feservice.utils.ConfigFileFileFilter;
import it.people.feservice.utils.FEInterfaceExtConstants;
import it.people.feservice.utils.PathScanner;
import it.people.feservice.utils.StringUtils;
import it.people.feservice.utils.WorkflowDocumentReader;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 * 22/set/2011 17.14.06
 */
public class FEInterfaceExtImpl implements FEInterfaceExt {

	public static final String SERVICES_ROOT = FEInterfaceImpl.SERVICE_PACKAGE_START_PATTERN;
	
	private static Logger _logger = LoggerFactory.getLogger(FEInterfaceExtImpl.class);
		
	private DataSource feDbDataSource;

	private DataSource legacyDataSource;
	
	private boolean isDebug = false;
	
//	private Level previousLevel = _logger.getLevel();

	public FEInterfaceExtImpl(DataSource feDbDataSource) {
		this.setFeDbDataSource(feDbDataSource);
	}
	
	public FEInterfaceExtImpl(DataSource feDbDataSource, DataSource legacyDataSource) {
		this.setFeDbDataSource(feDbDataSource);
		this.setLegacyDataSource(legacyDataSource);
	}
	
	public NodeDeployedServices getNodeDeployedServices(String basePath)
			throws it.people.feservice.exceptions.FEInterfaceExtException {
		
		if (_logger.isDebugEnabled()) {
			_logger.debug("Base path = " + basePath);
		}
		
		NodeDeployedServices result = null;
		
		if (_logger.isDebugEnabled()) {
			_logger.debug("Validating base path...");
		}
		File baseDirFoler = new File(basePath);
		if (!baseDirFoler.isDirectory()) {
			_logger.error("Base path '" + basePath + "' is not a folder.");
			throw new FEInterfaceExtException("Base path '" + basePath + "' is not a folder.");
		}
		if (_logger.isDebugEnabled()) {
			_logger.debug("Base path is valid.");
		}

		if (_logger.isDebugEnabled()) {
			_logger.debug("Validating services root...");
		}
		File servicesRoot = new File(pathBuilder(basePath, SERVICES_ROOT));
		if (!servicesRoot.isDirectory()) {
			_logger.error("Services root '" + servicesRoot + "' is not a folder.");
			throw new FEInterfaceExtException("Services root '" + servicesRoot + "' is not a folder.");
		}
		if (_logger.isDebugEnabled()) {
			_logger.debug("Services root is valid.");
		}
		
		Vector availableServices = new Vector();
		try {
			availableServices = getAvailableServices(servicesRoot, pathBuilder(basePath, SERVICES_ROOT));
			result = new NodeDeployedServices((AvailableService[])availableServices.toArray(new AvailableService[availableServices.size()]));
		} catch (ParserConfigurationException e) {
			throw new FEInterfaceExtException("Error while parsing service configuration file.", e);
		} catch (SAXException e) {
			throw new FEInterfaceExtException("Error while parsing service configuration file.", e);
		} catch (IOException e) {
			throw new FEInterfaceExtException("Error while parsing service configuration file.", e);
		}
		
		return result;
		
	}

	private DataSource getFeDbDataSource() {
		return feDbDataSource;
	}

	private void setFeDbDataSource(DataSource feDbDataSource) {
		this.feDbDataSource = feDbDataSource;
	}

	private DataSource getLegacyDataSource() {
		return legacyDataSource;
	}

	private void setLegacyDataSource(DataSource legacyDataSource) {
		this.legacyDataSource = legacyDataSource;
	}
	
    private String pathBuilder(String basePath, String servicesPath) {

		if (_logger.isDebugEnabled()) {
			_logger.debug("Building services root path...");
		}
    	
        String result = "";
        
        result = sanitizePath(basePath) + servicesPath.replace('.', File.separatorChar);

		if (_logger.isDebugEnabled()) {
			_logger.debug("Services root path = " + result + ".");
		}
        
        return result;
        
    }
    
    private String sanitizePath(String path) {

		if (_logger.isDebugEnabled()) {
			_logger.debug("Sanitizing base path...");
			_logger.debug("Input base path = " + path);
		}
		
    	String result = path;
    	
    	if (!path.endsWith(FEInterfaceExtConstants.WINDOWS_PATH_SEPARATOR) 
    			&& !path.endsWith(FEInterfaceExtConstants.NIX_PATH_SEPARATOR)) {
    		result += File.separator;
    	}

		if (_logger.isDebugEnabled()) {
			_logger.debug("Base path = " + result + ".");
		}
    	
    	return result;
    	
    }

	public final boolean isDebug() {
		return isDebug;
	}

//	public final void setDebug(final boolean isDebug) {
//		if (isDebug) {
//			_logger.setLevel(Level.DEBUG);
//		}
//		else {
//			_logger.setLevel(previousLevel);
//		}
//		this.isDebug = isDebug;
//	}
    
	private Vector getAvailableServices(File servicesRoot, String basePath) throws ParserConfigurationException, SAXException, IOException {

		Vector result = new Vector();
		Base64 base64 = new Base64();

		if (_logger.isDebugEnabled()) {
			_logger.debug("Initialize path scanner.");
		}
		PathScanner scanner = new PathScanner();
		
		if (_logger.isDebugEnabled()) {
			_logger.debug("Searching config files...");
		}
		Vector scannedServicesPaths = scanner.scanFiles(servicesRoot, new ConfigFileFileFilter());
		if (_logger.isDebugEnabled()) {
			_logger.debug(scannedServicesPaths.size() + " config file(s) retrivied.");
		}
		
		if (!scannedServicesPaths.isEmpty()) {
			Iterator scannedServicesPathsIterator = scannedServicesPaths.iterator();
			if (_logger.isDebugEnabled()) {
				_logger.debug("Iterating on services config files...");
			}
			while(scannedServicesPathsIterator.hasNext()) {
				String scannedServicePath = (String)scannedServicesPathsIterator.next();
				if (_logger.isDebugEnabled()) {
					_logger.debug("Adding available service for service path '" + scannedServicePath + "'.");
				}
				result.add(ConfigDocumentReader.getAvailableService(base64, scannedServicePath, basePath));
			}
			if (_logger.isDebugEnabled()) {
				_logger.debug("Iteration done.");
			}
		}
		else {
			if (_logger.isDebugEnabled()) {
				_logger.debug("No available services paths.");
			}
		}
		
		return result;
		
	}

	private Vector getDbAvailableServices(String communeId) throws SQLException {

		Vector result = new Vector();

		if (_logger.isDebugEnabled()) {
			_logger.debug("Searching registered services for commune '" + communeId + "'...");
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = this.getFeDbDataSource().getConnection();
			preparedStatement = connection.prepareStatement(FEInterfaceExtConstants.COMMUNE_REGISTERED_SERVICES);
			preparedStatement.setString(1, communeId);
			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				
				int id = resultSet.getInt("id");
				String serviceName = StringUtils.nullToEmptyString(resultSet.getString("serviceName"));
				String activity = StringUtils.nullToEmptyString(resultSet.getString("activity"));
				String subActivity = StringUtils.nullToEmptyString(resultSet.getString("subActivity"));
				
				result.add(new DbAvailableService(id, serviceName, activity, subActivity));
				
			}
		}
		finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		
		if (_logger.isDebugEnabled()) {
			_logger.debug(result.size() + " config file(s) retrivied.");
		}
		
		
		return result;
		
	}

	/* (non-Javadoc)
	 * @see it.people.feservice.FEInterfaceExt#nodeCopy(java.lang.String[], java.lang.String[], java.lang.String[], java.lang.String[], java.lang.String[], java.lang.String, java.lang.String)
	 */
	public boolean nodeCopy(String[] selectedServices, String[] areasLogicalNamesPrefix,
    		String[] areasLogicalNamesSuffix, String[] servicesLogicalNamesPrefix,
    		String[] servicesLogicalNamesSuffix, String fromCommuneId, String toCommuneId)
			throws FEInterfaceExtException {

		boolean result = false;
		
		Connection connection = null;
		PreparedStatement selectFromCommunePreparedStatement = null;
		PreparedStatement selectPreparedStatement = null;
		PreparedStatement insertPreparedStatement = null;
		ResultSet selectResultSet = null;
		ResultSet selectDataResultSet = null;
		ResultSet generatedKeyResultSet = null;
		
		// Select all service from service with communeid = fromCommuneId that are not already associated
		// with toCommuneId and write to service table with toCommuneId

		Map<String, String> areasLogicalNamesPrefixMap = tokenizePrefixesOrSuffixes(areasLogicalNamesPrefix);
		Map<String, String> areasLogicalNamesSuffixMap = tokenizePrefixesOrSuffixes(areasLogicalNamesSuffix);
		Map<String, String> servicesLogicalNamesPrefixMap = tokenizePrefixesOrSuffixes(servicesLogicalNamesPrefix);
		Map<String, String> servicesLogicalNamesSuffixMap = tokenizePrefixesOrSuffixes(servicesLogicalNamesSuffix);
		
		try {
			if (_logger.isDebugEnabled()) {
				_logger.debug("Searching registered services for commune '" + fromCommuneId + "'...");
			}
			
			connection = this.getFeDbDataSource().getConnection();
			
			String selectQuery = "SELECT id, nome, package, logid, statusid, attivita, sottoattivita, PROCESS, receiptmailattachment, signenabled, sendmailtoowner FROM service WHERE communeid = ? AND package NOT IN (SELECT package FROM service WHERE communeid = ?)";
			String insertQuery = "INSERT INTO service(communeid, nome, package, logid, statusid, attivita, sottoattivita, PROCESS, receiptmailattachment, signenabled, sendmailtoowner) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			String selectConfigurationQuery = "SELECT NAME, VALUE FROM configuration WHERE serviceid = ?";
			String insertConfigurationQuery = "INSERT INTO configuration(serviceid, NAME, VALUE) VALUES(?, ?, ?)";
			String selectReferenceQuery = "SELECT NAME, VALUE, address, mailincludexml FROM reference WHERE serviceid = ?";
			String insertReferenceQuery = "INSERT INTO reference(serviceid, NAME, VALUE, address, mailincludexml) VALUES(?, ?, ?, ?, ?)";

			String selectInClause = " AND package IN (";
			for(int index = 0; index < (selectedServices.length - 1); index++) {
				selectInClause += "'" + selectedServices[index] + "', ";
			}
			selectInClause += "'" + selectedServices[selectedServices.length - 1] + "')";
			selectQuery += selectInClause;
			
			selectFromCommunePreparedStatement = connection.prepareStatement(selectQuery);
			selectFromCommunePreparedStatement.setString(1, fromCommuneId);
			selectFromCommunePreparedStatement.setString(2, toCommuneId);
			selectResultSet = selectFromCommunePreparedStatement.executeQuery();
			
			while(selectResultSet.next()) {
				
				int fromServiceId = selectResultSet.getInt(1);
				
				String logicalNamePrefix = getLogicalNamePrefix(selectResultSet.getString(6), selectResultSet.getString(3), 
						areasLogicalNamesPrefixMap, servicesLogicalNamesPrefixMap);
				String logicalNameSuffix = getLogicalNameSuffix(selectResultSet.getString(6), selectResultSet.getString(3), 
						areasLogicalNamesSuffixMap, servicesLogicalNamesSuffixMap);
				
				insertPreparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
				insertPreparedStatement.setString(1, toCommuneId);
				insertPreparedStatement.setString(2, selectResultSet.getString(2));
				insertPreparedStatement.setString(3, selectResultSet.getString(3));
				insertPreparedStatement.setInt(4, selectResultSet.getInt(4));
				insertPreparedStatement.setInt(5, selectResultSet.getInt(5));
				insertPreparedStatement.setString(6, selectResultSet.getString(6));
				insertPreparedStatement.setString(7, selectResultSet.getString(7));
				insertPreparedStatement.setString(8, selectResultSet.getString(8));
				insertPreparedStatement.setInt(9, selectResultSet.getInt(9));
				insertPreparedStatement.setInt(10, selectResultSet.getInt(10));
				insertPreparedStatement.setInt(11, selectResultSet.getInt(11));
				insertPreparedStatement.execute();

				generatedKeyResultSet = insertPreparedStatement.getGeneratedKeys();
				
				if (generatedKeyResultSet.next()) {
					int newServiceId = generatedKeyResultSet.getInt(1);
					
					selectPreparedStatement = connection.prepareStatement(selectConfigurationQuery);
					selectPreparedStatement.setInt(1, fromServiceId);
					
					selectDataResultSet = selectPreparedStatement.executeQuery();
					
					insertPreparedStatement.close();
					insertPreparedStatement = connection.prepareStatement(insertConfigurationQuery);
					while(selectDataResultSet.next()) {
						insertPreparedStatement.setInt(1, newServiceId);
						insertPreparedStatement.setString(2, selectDataResultSet.getString(1));
						insertPreparedStatement.setString(3, selectDataResultSet.getString(2));
						insertPreparedStatement.execute();
						insertPreparedStatement.clearParameters();
					}

					selectPreparedStatement.close();
					selectPreparedStatement = connection.prepareStatement(selectReferenceQuery);
					selectPreparedStatement.setInt(1, fromServiceId);
					
					selectDataResultSet = selectPreparedStatement.executeQuery();
					
					insertPreparedStatement.close();
					insertPreparedStatement = connection.prepareStatement(insertReferenceQuery);
					while(selectDataResultSet.next()) {
						insertPreparedStatement.setInt(1, newServiceId);
						insertPreparedStatement.setString(2, selectDataResultSet.getString(1));
						insertPreparedStatement.setString(3, logicalNamePrefix + selectDataResultSet.getString(2) + logicalNameSuffix);
						insertPreparedStatement.setString(4, selectDataResultSet.getString(3));
						insertPreparedStatement.setInt(5, selectDataResultSet.getInt(4));
						insertPreparedStatement.execute();
						insertPreparedStatement.clearParameters();
					}

					selectPreparedStatement.close();
					
				}
				
				insertPreparedStatement.clearParameters();
				result = true;
				
			}
			
		} catch (SQLException e) {
			_logger.error("Node copy error:", e);
			it.people.core.Logger.error("Node copy error: " + StringUtils.exceptionToString(e));
			e.printStackTrace();
			result = false;
		}
		finally {
			try {
				if (selectResultSet != null) {
					selectResultSet.close();
				}
				if (selectDataResultSet != null) {
					selectDataResultSet.close();
				}
				if (generatedKeyResultSet != null) {
					generatedKeyResultSet.close();
				}
				if (selectFromCommunePreparedStatement != null) {
					selectFromCommunePreparedStatement.close();
				}
				if (selectPreparedStatement != null) {
					selectPreparedStatement.close();
				}
				if (insertPreparedStatement != null) {
					insertPreparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			}
			catch (SQLException e) {
				
			}
		}
		
		// Select all references and configurations from reference and configuration table belonging 
		// to previous selected services and write all for the new toCommuneKey
		
		return result;
		
	}

	/* (non-Javadoc)
	 * @see it.people.feservice.FEInterfaceExt#getServiceOnlineHelpWorkflowElements(java.lang.String, java.lang.String)
	 */
	public ServiceOnlineHelpWorkflowElements getServiceOnlineHelpWorkflowElements(
			String servicePackage, String basePath) throws FEInterfaceExtException {

		ServiceOnlineHelpWorkflowElements result = new ServiceOnlineHelpWorkflowElements();
		
		try {
			result = WorkflowDocumentReader.getServiceWorkflowForOnlineHelp(servicePackage, basePath);
		} catch (XPathExpressionException e) {
			throw new FEInterfaceExtException();
		} catch (ParserConfigurationException e) {
			throw new FEInterfaceExtException();
		} catch (SAXException e) {
			throw new FEInterfaceExtException();
		} catch (IOException e) {
			throw new FEInterfaceExtException();
		}
		
		return result;
		
	}

	/**
	 * @param value
	 * @return
	 */
	private Map<String, String> tokenizePrefixesOrSuffixes(String[] value) {
		
		Map<String, String> result = new HashMap<String, String>();
		
		if (value != null && value.length > 0) {
			for(int index = 0; index < value.length; index++) {
				StringTokenizer tokenizer = new StringTokenizer(value[index], "$");
				if (tokenizer.countTokens() > 1) {
					result.put(tokenizer.nextToken(), tokenizer.nextToken());
				}
			}
		}
		
		return result;
		
	}

	/**
	 * @param area
	 * @param _package
	 * @param areasPrefixesMap
	 * @param servicesPrefixesMap
	 * @return
	 */
	private String getLogicalNamePrefix(String area, String _package, Map<String, String> areasPrefixesMap, Map<String, String> servicesPrefixesMap) {
		
		if (servicesPrefixesMap.containsKey(_package) && 
				!StringUtils.nullToEmptyString(servicesPrefixesMap.get(_package)).equalsIgnoreCase("")) {
			return servicesPrefixesMap.get(_package);
		}

		if (areasPrefixesMap.containsKey(area) && 
				!StringUtils.nullToEmptyString(areasPrefixesMap.get(area)).equalsIgnoreCase("")) {
			return areasPrefixesMap.get(area);
		}
		
		return "";
		
	}
	
	/**
	 * @param area
	 * @param _package
	 * @param areasSuffixesMap
	 * @param servicesSuffixesMap
	 * @return
	 */
	private String getLogicalNameSuffix(String area, String _package, Map<String, String> areasSuffixesMap, Map<String, String> servicesSuffixesMap) {
		
		if (servicesSuffixesMap.containsKey(_package) && 
				!StringUtils.nullToEmptyString(servicesSuffixesMap.get(_package)).equalsIgnoreCase("")) {
			return servicesSuffixesMap.get(_package);
		}

		if (areasSuffixesMap.containsKey(area) && 
				!StringUtils.nullToEmptyString(areasSuffixesMap.get(area)).equalsIgnoreCase("")) {
			return areasSuffixesMap.get(area);
		}
		
		return "";
		
	}

    /* (non-Javadoc)
     * @see it.people.feservice.FEInterfaceExt#updateBundle(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void updateBundle(java.lang.String bundle, java.lang.String nodeId, java.lang.String locale, 
    		java.lang.String key, java.lang.String value, 
    		java.lang.String active, java.lang.String group) throws java.rmi.RemoteException {
    	
    	//Verifica se esiste la chiave in bundles_properties: se non esiste inserisce altrimenti aggiorna

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {

			connection = this.getFeDbDataSource().getConnection();
			
			String query = FEInterfaceExtConstants.SEARCH_SERVICE_MESSAGE_BUNDLE_REF_BY_NODE_ID_LOCALE;
			String buffer = query;
			if (locale == null || (locale != null && locale.equalsIgnoreCase(""))) {
				query = buffer.replace("$", "is null");
			} else {
				query = buffer.replace("$", "= '" + locale + "'");
			}
			buffer = query;
			if (nodeId == null || (nodeId != null && nodeId.equalsIgnoreCase(""))) {
				query = buffer.replace("!", "is null");
			} else {
				query = buffer.replace("!", "='" + nodeId + "'");
			}
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bundle);
			resultSet = preparedStatement.executeQuery();
			int bundleRefId = -1;
			if (resultSet.next()) {
				bundleRefId = resultSet.getInt(1);
			} else {
				throw new RemoteException();
			}
			
			preparedStatement = connection.prepareStatement(FEInterfaceExtConstants.SEARCH_BUNDLE_KEY);
			preparedStatement.setInt(1, bundleRefId);
			preparedStatement.setString(2, key);
			
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			int count = resultSet.getInt(1);
			if(count > 0) {
				preparedStatement = connection.prepareStatement(FEInterfaceExtConstants.UPDATE_BUNDLE_PROPERTY);
				preparedStatement.setString(1, value);
				preparedStatement.setInt(2, 1);
				preparedStatement.setString(3, null);
				preparedStatement.setInt(4, bundleRefId);
				preparedStatement.setString(5, key);
				preparedStatement.executeUpdate();
			} else {
				preparedStatement = connection.prepareStatement(FEInterfaceExtConstants.INSERT_BUNDLE_PROPERTY);
				preparedStatement.setInt(1, bundleRefId);
				preparedStatement.setString(2, key);
				preparedStatement.setString(3, value);
				preparedStatement.setInt(4, 1);
				preparedStatement.setString(5, null);
				preparedStatement.executeUpdate();
			}
		} catch(SQLException e) {
			throw new RemoteException();
		}
		finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch(SQLException ignore) {
				
			}
		}
		
    }

    /* (non-Javadoc)
     * @see it.people.feservice.FEInterfaceExt#registerBundle(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void registerBundle(java.lang.String bundle, java.lang.String nodeId, java.lang.String locale, 
    		java.lang.String active, java.lang.String group) throws java.rmi.RemoteException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String sanitizedLocale = (locale != null && locale.equalsIgnoreCase("")) ? null : locale;
			connection = this.getFeDbDataSource().getConnection();
			preparedStatement = connection.prepareStatement(FEInterfaceExtConstants.REGISTER_BUNDLE);
			preparedStatement.setString(1, bundle);
			preparedStatement.setString(2, nodeId);
			preparedStatement.setString(3, sanitizedLocale);
			preparedStatement.setInt(4, 1);
			preparedStatement.setString(5, null);
			preparedStatement.executeUpdate();
		} catch(SQLException e) {
			throw new RemoteException();
		}
		finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch(SQLException ignore) {
				
			}
		}
    	
    }

    /* (non-Javadoc)
     * @see it.people.feservice.FEInterfaceExt#deleteBundle(java.lang.String, java.lang.String, java.lang.String)
     */
    public void deleteBundle(java.lang.String bundle, java.lang.String nodeId, java.lang.String locale) throws java.rmi.RemoteException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean autoCommit = true;
		
		try {
			connection = this.getFeDbDataSource().getConnection();
			autoCommit = connection.getAutoCommit();
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			String query = FEInterfaceExtConstants.GET_BUNDLE_ID;
			String buffer = query;
			if (nodeId == null) {
				buffer = query.replace("#nodeidvalue#", "is null");
			} else {
				buffer = query.replace("#nodeidvalue#", "= '" + nodeId + "'");
			}
			query = buffer;
			if (locale == null) {
				buffer = query.replace("#localevalue#", "is null");
			} else {
				buffer = query.replace("#localevalue#", "= '" + locale + "'");
			}
			query = buffer;
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bundle);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int bundleId = resultSet.getInt(1);
				preparedStatement = connection.prepareStatement(FEInterfaceExtConstants.DELETE_BUNDLE_PROPERTIES);
				preparedStatement.setInt(1, bundleId);
				preparedStatement.executeUpdate();
				query = FEInterfaceExtConstants.DELETE_BUNDLE;
				buffer = query;
				if (nodeId == null) {
					buffer = query.replace("#nodeidvalue#", "is null");
				} else {
					buffer = query.replace("#nodeidvalue#", "= '" + nodeId + "'");
				}
				query = buffer;
				if (locale == null) {
					buffer = query.replace("#localevalue#", "is null");
				} else {
					buffer = query.replace("#localevalue#", "= '" + locale + "'");
				}
				query = buffer;
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, bundle);
				preparedStatement.executeUpdate();
				connection.setAutoCommit(autoCommit);
			}
			if (resultSet.next()) {
				connection.rollback();
				throw new RemoteException();
			}
		} catch(SQLException e) {
			throw new RemoteException();
		}
		finally {
			try {
				connection.setAutoCommit(autoCommit);
				if (resultSet != null) {
					resultSet.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch(SQLException ignore) {
				
			}
		}
    	
    }
    
	/* (non-Javadoc)
	 * @see it.people.feservice.FEInterfaceExt#deleteBeServicesReferencesByPackages(it.people.feservice.beans.FEServiceReferenceVO[])
	 */
	public boolean deleteBeServicesReferencesByPackages(
			FEServiceReferenceVO[] servicesReferences) throws RemoteException {
		
		boolean result = true;

		if (servicesReferences != null && servicesReferences.length > 0) {
			boolean executeUpdate = false;
			for (int index = 0; index < servicesReferences.length; index++) {
				FEServiceReferenceVO feServiceReferenceVO = servicesReferences[index];
				Connection connection = null;
				PreparedStatement preparedStatement = null;
				PreparedStatement deletePreparedStatement = null;
				ResultSet resultSet = null;
				
				try {
					connection = this.getFeDbDataSource().getConnection();
					
					deletePreparedStatement = connection.prepareStatement(FEInterfaceExtConstants.DELETE_BE_REFERENCE_BY_PACKAGE);
					
					preparedStatement = connection.prepareStatement(FEInterfaceExtConstants.SEARCH_SERVICE_ID);
					preparedStatement.setString(1, feServiceReferenceVO.getCommuneId());
					preparedStatement.setString(2, feServiceReferenceVO.getServicePackage());
					preparedStatement.setString(3, feServiceReferenceVO.getActivity());
					preparedStatement.setString(4, feServiceReferenceVO.getSubActivity());
					preparedStatement.setString(5, feServiceReferenceVO.getProcess());
					resultSet = preparedStatement.executeQuery();
					if (resultSet.next()) {
						deletePreparedStatement.clearParameters();
						deletePreparedStatement.clearWarnings();
						deletePreparedStatement.setInt(1, resultSet.getInt(1));
						deletePreparedStatement.setString(2, feServiceReferenceVO.getReference());
						deletePreparedStatement.addBatch();
						executeUpdate = true;
					}
					if (executeUpdate) {
						int[] deletions = deletePreparedStatement.executeBatch();
					}
				} catch(SQLException e) {
					throw new RemoteException();
				}
				finally {
					try {
						if (resultSet != null) {
							resultSet.close();
						}
						if (preparedStatement != null) {
							preparedStatement.close();
						}
						if (deletePreparedStatement != null) {
							deletePreparedStatement.close();
						}
						if (connection != null) {
							connection.close();
						}
					} catch(SQLException ignore) {
						
					}
				}				
			}
		}
		
		return result;
	}

	@Override
	public void configureTableValueProperty(it.people.feservice.beans.TableValuePropertyVO tableValueProperty) throws java.rmi.RemoteException {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		//Get action apply on Values
		String action = tableValueProperty.getAction();
		
		try {
			conn = this.getFeDbDataSource().getConnection();
			conn.setAutoCommit(false);
			
			if (action.equalsIgnoreCase("save")) {
				ps = conn.prepareStatement(FEInterfaceExtConstants.UPDATE_TABLEVALUE_PROPERTY);
				ps.setString(1, tableValueProperty.getValue());
				ps.setInt(2, tableValueProperty.getTableValueRef());
				ps.setString(3, tableValueProperty.getOldValue());
				ps.setInt(4, tableValueProperty.getTableValueRef());
			}
			else if (action.equalsIgnoreCase("saveNew")) {
				
				//Check if Id already exists
				checkTableValueProperty(conn, tableValueProperty.getValue(),  tableValueProperty.getTableValueRef());
				
				ps = conn.prepareStatement(FEInterfaceExtConstants.INSERT_TABLEVALUE_PROPERTY, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, tableValueProperty.getValue());
				ps.setInt(2, tableValueProperty.getTableValueRef());
			}
			else if (action.equalsIgnoreCase("delete")) {
				ps = conn.prepareStatement(FEInterfaceExtConstants.DELETE_TABLEVALUE_PROPERTIES);
				ps.setString(1, tableValueProperty.getOldValue());
				ps.setInt(2, tableValueProperty.getTableValueRef());
			}
			else {
				_logger.error("Configure Tablevalue property: ACTION not defined!"); 
				throw new RemoteException("Configure Tablevalue property: ACTION not defined!");
			}
			
			//Execute and save chcanges
			ps.execute(); 
			ps.close();
			conn.commit();
			
		} catch(SQLException e) {
			_logger.error("Unable to configure (SAVE, UPDATE, DELETE) a Tablevalue Property"); 
			throw new RemoteException("Unable to configure (SAVE, UPDATE, DELETE) a Tablevalue Property");
		} 
		finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (conn != null) {
					conn.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch(SQLException ignore) {}
		}
	}
	
	
	private void checkTableValueProperty(Connection conn, String value, int tableValueRef) throws SQLException {

		
		PreparedStatement ps = conn.prepareStatement(FEInterfaceExtConstants.CHECK_TABLEVALUE_PROPERTY_ID);
		ps.setString(1, value);
		ps.setInt(2, tableValueRef);
		ResultSet res = ps.executeQuery();
		
		if(res.next()) {
			_logger.debug("Unable to insert in tablevalues_properties: duplicate ID.");
			throw new SQLException("Unable to insert in tablevalues_properties: duplicate ID.");
		}
		res.close();
		ps.close();
	}
	

	public void setAsPeopleAdministrator(java.lang.String userId, java.lang.String eMail, java.lang.String userName, java.lang.String[] allowedCommune, java.lang.String mailReceiverTypeFlags) throws java.rmi.RemoteException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = this.getLegacyDataSource().getConnection();
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

			preparedStatement = connection.prepareStatement(FEInterfaceExtConstants.CHECK_PEOPLE_ADMINISTRATOR);
			preparedStatement.setString(1, userId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next() && resultSet.getLong(1) > 0) {
				
				if (resultSet.getLong(1) > 1) {
					throw new SQLException("People administrator integrity failure.");
				}

				preparedStatement = connection.prepareStatement(FEInterfaceExtConstants.GET_PEOPLE_ADMINISTRATOR_OID);
				preparedStatement.setString(1, userId);
				resultSet = preparedStatement.executeQuery();

				if (!resultSet.next()) {
					throw new SQLException("People administrator integrity failure.");
				}
				
				long userOid = resultSet.getLong(1);
				//Update user_profile
				preparedStatement = connection.prepareStatement(FEInterfaceExtConstants.UPDATE_USER_PROFILE);
				preparedStatement.setString(1, eMail);
				preparedStatement.setString(2, userName);
				preparedStatement.setString(3, StringUtils.nullToEmptyString(mailReceiverTypeFlags));
				preparedStatement.setString(4, userId);
				preparedStatement.executeUpdate();
				
				//Clear amministratore_commune
				preparedStatement = connection.prepareStatement(FEInterfaceExtConstants.DELETE_AMMINISTRATORE_COMMUNE);
				preparedStatement.setLong(1, userOid);
				preparedStatement.execute();
				
				//Update amministratore_commune
				if (allowedCommune != null && allowedCommune.length > 0) {
					preparedStatement = connection.prepareStatement(FEInterfaceExtConstants.INSERT_AMMINISTRATORE_COMMUNE);
					boolean doBatch = false;
					for (int index = 0; index < allowedCommune.length; index++) {
						String commune = String.valueOf(allowedCommune[index]);
						if (commune != null & commune.length() > 0) {
							preparedStatement.setLong(1, userOid);
							preparedStatement.setString(2, commune);
							preparedStatement.addBatch();
							preparedStatement.clearParameters();
							doBatch = true;
						}
					}
					if (doBatch) {
						preparedStatement.executeBatch();
					}
				}
				
			} else {
				preparedStatement.clearParameters();
				preparedStatement.clearWarnings();
				
				Long userOid = null;
				if (org.apache.commons.lang.StringUtils.isEmpty(eMail) & org.apache.commons.lang.StringUtils.isEmpty(userName)) {
					preparedStatement = connection.prepareStatement(FEInterfaceExtConstants.INSERT_PARTIAL_USER_PROFILE, PreparedStatement.RETURN_GENERATED_KEYS);
					preparedStatement.setString(1, userId);
					preparedStatement.setString(2, StringUtils.nullToEmptyString(mailReceiverTypeFlags));
					preparedStatement.executeUpdate();
					resultSet = preparedStatement.getGeneratedKeys();
					if (resultSet.next()) {
						userOid = resultSet.getLong(1);
					} else {
						_logger.error("Unable to get oid for update of existing people administrator.");
						try {
							connection.rollback();
						} catch (Exception ignore) {}
						throw new RemoteException("Unable to get oid for update of existing people administrator.");
					}
				} else {
					preparedStatement = connection.prepareStatement(FEInterfaceExtConstants.INSERT_COMPLETE_USER_PROFILE, PreparedStatement.RETURN_GENERATED_KEYS);
					preparedStatement.setString(1, userId);
					preparedStatement.setString(2, eMail);
					preparedStatement.setString(3, userName);
					preparedStatement.setString(4, StringUtils.nullToEmptyString(mailReceiverTypeFlags));
					preparedStatement.executeUpdate();
					resultSet = preparedStatement.getGeneratedKeys();
					if (resultSet.next()) {
						userOid = resultSet.getLong(1);
					} else {
						_logger.error("Unable to get oid for update of existing people administrator.");
						try {
							connection.rollback();
						} catch (Exception ignore) {}
						throw new RemoteException("Unable to get oid for update of existing people administrator.");
					}
				}
				if (allowedCommune != null && allowedCommune.length > 0) {
					preparedStatement = connection.prepareStatement(FEInterfaceExtConstants.INSERT_AMMINISTRATORE_COMMUNE);
					boolean doBatch = false;
					for (int index = 0; index < allowedCommune.length; index++) {
						String commune = String.valueOf(allowedCommune[index]);
						if (commune != null & commune.length() > 0) {
							preparedStatement.setLong(1, userOid);
							preparedStatement.setString(2, commune);
							preparedStatement.addBatch();
							preparedStatement.clearParameters();
							doBatch = true;
						}
					}
					if (doBatch) {
						preparedStatement.executeBatch();
					}
				}
				
			}
			
			connection.commit();
			
		} catch(SQLException e) {
			_logger.error("Unable to save or update people administrator.", e);
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (Exception ignore) {}
			throw new RemoteException("Unable to save or update people administrator.");
		} 
		finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (connection != null) {
					connection.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch(SQLException ignore) {}
		}
		
	}

	public void removeFromPeopleAdministrator(java.lang.String userId) throws java.rmi.RemoteException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = this.getLegacyDataSource().getConnection();
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

			preparedStatement = connection.prepareStatement(FEInterfaceExtConstants.CHECK_PEOPLE_ADMINISTRATOR);
			preparedStatement.setString(1, userId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next() && resultSet.getLong(1) > 0) {
				
				if (resultSet.getLong(1) > 1) {
					throw new SQLException("People administrator integrity failure.");
				}

				preparedStatement = connection.prepareStatement(FEInterfaceExtConstants.GET_PEOPLE_ADMINISTRATOR_OID);
				preparedStatement.setString(1, userId);
				resultSet = preparedStatement.executeQuery();

				if (!resultSet.next()) {
					throw new SQLException("People administrator integrity failure.");
				}
				
				long userOid = resultSet.getLong(1);

				//Clear amministratore_commune
				preparedStatement = connection.prepareStatement(FEInterfaceExtConstants.DELETE_AMMINISTRATORE_COMMUNE);
				preparedStatement.setLong(1, userOid);
				preparedStatement.execute();
				
				//Clear user profiile
				preparedStatement = connection.prepareStatement(FEInterfaceExtConstants.DELETE_USER_PROFILE);
				preparedStatement.setString(1, userId);
				preparedStatement.execute();				
				
			}
			
			connection.commit();
			
		} catch(SQLException e) {
			_logger.error("Unable to delete people administrator.", e);
			try {
				connection.rollback();
			} catch (Exception ignore) {}
			throw new RemoteException("Unable to delete people administrator.");
		} 
		finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (connection != null) {
					connection.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch(SQLException ignore) {}
		}
		
	}

	public PeopleAdministratorVO getPeopleAdministrator(java.lang.String userId) throws java.rmi.RemoteException {

		PeopleAdministratorVO result = new PeopleAdministratorVO();
		String userIdFromDb = "";
		String userName = "";
		String eMail = "";
		String eMailsReceiverTypesFlags = "";
		Vector<String> allowedCommune = new Vector<String>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = this.getLegacyDataSource().getConnection();
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

			preparedStatement = connection.prepareStatement(FEInterfaceExtConstants.CHECK_PEOPLE_ADMINISTRATOR);
			preparedStatement.setString(1, userId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next() && resultSet.getLong(1) > 0) {
				
				if (resultSet.getLong(1) > 1) {
					throw new SQLException("People administrator integrity failure.");
				}

				preparedStatement = connection.prepareStatement(FEInterfaceExtConstants.GET_PEOPLE_ADMINISTRATOR_OID);
				preparedStatement.setString(1, userId);
				resultSet = preparedStatement.executeQuery();

				if (!resultSet.next()) {
					throw new SQLException("People administrator integrity failure.");
				}
				
				long userOid = resultSet.getLong(1);

				//Get user profile data
				preparedStatement = connection.prepareStatement(FEInterfaceExtConstants.GET_PEOPLE_ADMINISTRATOR_DATA);
				preparedStatement.setString(1, userId);
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					userIdFromDb = resultSet.getString("user_id");
					userName = resultSet.getString("e_mail");
					eMail = resultSet.getString("user_name");
					eMailsReceiverTypesFlags = resultSet.getString("receiver_type_flags");
				} else {
					throw new SQLException("People administrator integrity failure.");
				}
				
				//Clear amministratore_commune data
				preparedStatement = connection.prepareStatement(FEInterfaceExtConstants.GET_PEOPLE_ADMINISTRATOR_ALLOWED_COMMUNE);
				preparedStatement.setLong(1, userOid);
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next()) {
					allowedCommune.add(resultSet.getString("commune_id"));
				}

				String [] allowedCommuneArray = null;
				if (!allowedCommune.isEmpty()) {
					allowedCommuneArray = allowedCommune.toArray(new String[allowedCommune.size()]);
				}
				result.setUserId(userIdFromDb);
				result.setUserName(userName);
				result.seteMail(eMail);
				result.seteMailsReceiverTypesFlags(eMailsReceiverTypesFlags);
				result.setAllowedCommune(allowedCommuneArray);
				
			}
			
			connection.commit();
			
		} catch(SQLException e) {
			_logger.error("Unable to get people administrator data.", e);
			try {
				connection.rollback();
			} catch (Exception ignore) {}
			throw new RemoteException("Unable to get people administrator data.");
		} 
		finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (connection != null) {
					connection.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch(SQLException ignore) {}
		}
		
		return result;
		
	}


	@Override
	public VelocityTemplateDataVO getVelocityTemplatesData(String communeId, String servicePackage, boolean retrieveAll) throws RemoteException {
	
		//Return object
		VelocityTemplateDataVO templatesDTO = new VelocityTemplateDataVO();
		List <VelocityTemplateBean> templateList = new ArrayList<VelocityTemplateBean>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
			connection = this.getFeDbDataSource().getConnection();
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

			//Get generic tamplates
			if ((communeId == null) && (servicePackage == null)) {
				preparedStatement = connection.prepareStatement(FEInterfaceExtConstants
						.GET_VELOCITY_TEMPLATES_DATA_GENERIC);
				//No parameters to set here.
			} else if ((communeId != null) && (servicePackage != null)) {
				//Get templates for specific service
				preparedStatement = connection.prepareStatement(FEInterfaceExtConstants
						.GET_VELOCITY_TEMPLATES_DATA_FOR_SERVICE);
				preparedStatement.setString(1, communeId);
				preparedStatement.setString(2, servicePackage);
				preparedStatement.setString(3, communeId);
				preparedStatement.setString(4, communeId);
				preparedStatement.setString(5, servicePackage);
				preparedStatement.setString(6, communeId);
				preparedStatement.setString(7, servicePackage);
				preparedStatement.setString(8, communeId);
				
			} else if ((communeId != null) && (servicePackage == null)) {
				//Get templates for anode
				preparedStatement = connection.prepareStatement(FEInterfaceExtConstants
						.GET_VELOCITY_TEMPLATES_DATA_FOR_NODE);
				preparedStatement.setString(1, communeId);
				preparedStatement.setString(2, communeId);
				
			} else if ((communeId == null) && (servicePackage != null)) {
				throw new FEInterfaceExtException("Unable to get Velocity Template data for " +
						"generic service: no communeId specified)");
			}

			//Execute query
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				templateList.add(new VelocityTemplateBean(
						rs.getString("_communeId") == null ? null : rs.getString("_communeId"),
						rs.getInt("_serviceId") == 0 ? null : Integer.toString(rs.getInt("_serviceId")),
						rs.getString("_key"),
						rs.getString("_value"),
						servicePackage,
						rs.getString("_description") == null ? null : rs.getString("_description")));
			}
		} catch(SQLException e) {
			_logger.error("Unable to get Velocity Tempolate data.", e);
			try {
				connection.rollback();
			} catch (Exception ignore) {}
			throw new RemoteException("Unable to get Velocity Template data.");
		} 
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (connection != null) {
					connection.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch(SQLException ignore) {}
		}
		
		templatesDTO.setVelocityTemplates(templateList.toArray(new VelocityTemplateBean[templateList.size()]));	
		
		return templatesDTO;
	}

	@Override
	public boolean updateVelocityTemplatesData(VelocityTemplateDataVO templateDataVO, boolean delete)
			throws RemoteException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement checkPreparedStatement = null;
		ResultSet rs = null;
		
		VelocityTemplateBean[] velocityTemplates = templateDataVO.getVelocityTemplates();
		List <VelocityTemplateBean> templateList = Arrays.asList(velocityTemplates);
		Iterator<VelocityTemplateBean> templateListIter = templateList.iterator();
		
		try {
			connection = this.getFeDbDataSource().getConnection();
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			
			if (delete) {
				
				while (templateListIter.hasNext()) {
					VelocityTemplateBean template = templateListIter.next();
					
					String deleteQuery = FEInterfaceExtConstants.DELETE_VELOCITY_TEMPLATES_DATA;
					
					//Retrieve serviceID if needed
					if ((template.getServiceId() == null) 
							&& (template.getServicePackage() != null) && (template.getCommuneId() != null)) {
						
						//Retrieve serviceId using package and communeId
						String searchIdQuery = FEInterfaceExtConstants.SEARCH_SERVICE_ID_FOR_PKG_AND_COMMUNE;
						preparedStatement = connection.prepareStatement(searchIdQuery);
						preparedStatement.setString(1, template.getCommuneId());
						preparedStatement.setString(2, template.getServicePackage());
						ResultSet serviceIdResultSet = preparedStatement.executeQuery();
						
						if (serviceIdResultSet.next()) {
							int serviceId = serviceIdResultSet.getInt(1);
							//Set serviceId found by pkg and communeId
						    template.setServiceId(String.valueOf(serviceId));
						} else {
							throw new RemoteException("Velocity Template update: no SERVICE ID found " +
									"for given communeId and servicePackage");
						}
						
					} else if ((template.getServicePackage() != null) && (template.getCommuneId() == null)) {
						throw new RemoteException("If service package is set, then communeId must be set" +
								" to retrieve the proper serviceId");
					}
					
					//SET VALUE IN DELETE QUERY
					if (template.getCommuneId() == null) {
						deleteQuery = String.format(deleteQuery, "IS NULL", "%s");
					} else {
						deleteQuery = String.format(deleteQuery, " = '"+ template.getCommuneId() +"'", "%s");
					} 
					if (template.getServiceId() == null) {
						deleteQuery = String.format(deleteQuery, "IS NULL");
						
					} else {
						deleteQuery = String.format(deleteQuery, " = "+ template.getServiceId());
					}

					preparedStatement = connection.prepareStatement(deleteQuery);
					preparedStatement.setString(1, template.getKey());
	
					preparedStatement.executeUpdate();
				}	
				
			} else {

				//Update or Insert
				while (templateListIter.hasNext()) {
					
					String checkQuery = FEInterfaceExtConstants.CHECK_VELOCITY_TEMPLATE_EXISTS;
					VelocityTemplateBean template = templateListIter.next();
					
					//Retrieve serviceID if needed
					if ((template.getServiceId() == null) 
							&& (template.getServicePackage() != null) && (template.getCommuneId() != null)) {
						
						//Retrieve serviceId using package and communeId
						String searchIdQuery = FEInterfaceExtConstants.SEARCH_SERVICE_ID_FOR_PKG_AND_COMMUNE;
						preparedStatement = connection.prepareStatement(searchIdQuery);
						preparedStatement.setString(1, template.getCommuneId());
						preparedStatement.setString(2, template.getServicePackage());
						ResultSet serviceIdResultSet = preparedStatement.executeQuery();
						
						if (serviceIdResultSet.next()) {
							int serviceId = serviceIdResultSet.getInt(1);
							//Set serviceId found by pkg and communeId
						    template.setServiceId(String.valueOf(serviceId));
						} else {
							throw new RemoteException("Velocity Template update: no SERVICE ID found " +
									"for given communeId and servicePackage");
						}
						
					} else if ((template.getServicePackage() != null) && (template.getCommuneId() == null)) {
						throw new RemoteException("If service package is set, then communeId must be set" +
								" to retrieve the proper serviceId");
					}
					
					
					//Check for existence 
					if (template.getCommuneId() == null) {
						checkQuery = String.format(checkQuery, "IS NULL", "%s");
					} else {
						checkQuery = String.format(checkQuery, " = '"+ template.getCommuneId() +"'", "%s");
					} 
					if (template.getServiceId() == null) {
						checkQuery = String.format(checkQuery, "IS NULL");
					} else {
						checkQuery = String.format(checkQuery, " = "+ template.getServiceId());
					}

					checkPreparedStatement = connection.prepareStatement(checkQuery);
					checkPreparedStatement.setString(1, template.getKey());
					ResultSet existResult = checkPreparedStatement.executeQuery();
					
					existResult.next();
					boolean rowExists = (existResult.getInt(1) > 0);	
					checkPreparedStatement.clearParameters();
					
					if (!rowExists) {
						//INSERT
						preparedStatement = connection.prepareStatement(FEInterfaceExtConstants
								.INSERT_VELOCITY_TEMPLATES_DATA);
						
						if (template.getCommuneId() == null) {
							preparedStatement.setNull(1, java.sql.Types.VARCHAR);
						} else {
							preparedStatement.setString(1, template.getCommuneId());
						} 
						if (template.getServiceId() == null) {
							preparedStatement.setNull(2, java.sql.Types.INTEGER);
						} else {
							preparedStatement.setInt(2, Integer.parseInt(template.getServiceId()));
						}
						preparedStatement.setString(3, template.getKey());
						preparedStatement.setString(4, template.getValue());
						preparedStatement.setString(5, template.getDescription());
						preparedStatement.executeUpdate();

					} else {
						//UPDATE
						String updateQuery = FEInterfaceExtConstants.UPDATE_VELOCITY_TEMPLATES_DATA;

						if (template.getCommuneId() == null) {
							updateQuery = String.format(updateQuery, "IS NULL", "%s");
						} else {
							updateQuery = String.format(updateQuery, " = '"+ template.getCommuneId() +"'", "%s");
						} 
						if (template.getServiceId() == null) {
							updateQuery = String.format(updateQuery, "IS NULL");
						} else {
							updateQuery = String.format(updateQuery, " = "+ template.getServiceId());
						}

						preparedStatement = connection.prepareStatement(updateQuery);
						preparedStatement.setString(1, template.getValue());
						preparedStatement.setString(2, template.getDescription());
						preparedStatement.setString(3, template.getKey());
						preparedStatement.executeUpdate();
					}
				}
			}
			
			connection.commit();
			
		} catch(SQLException e) {
			_logger.error("Unable to update Velocity Template data.", e);
			try {
				connection.rollback();
			} catch (Exception ignore) {}
			throw new RemoteException("Unable to update Velocity Template data.");
		} 
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (connection != null) {
					connection.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (checkPreparedStatement != null) {
					checkPreparedStatement.close();
				}
			} catch(SQLException ignore) {}
		}
		
		return true;
	}

	@Override
	public void setDebug(boolean isDebug) {
		// TODO Auto-generated method stub
		
	}
	
}
