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
package it.people.console.web.controllers.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import it.people.console.beans.ObjectError;

import it.people.console.domain.FENode;
import it.people.console.domain.FENodeAndServicesRegistrationResult;
import it.people.console.domain.FENodeToNodeCopy;
import it.people.console.domain.NodeToNodeCopyResult;
import it.people.console.persistence.IPersistenceManager;
import it.people.console.persistence.PersistenceManagerFactory;
import it.people.console.persistence.AbstractPersistenceManager.Mode;
import it.people.console.persistence.exceptions.PersistenceManagerException;
import it.people.console.utils.CastUtils;
import it.people.console.utils.StringUtils;
import it.people.console.web.client.exceptions.FeServiceReferenceException;
import it.people.console.web.servlet.mvc.MessageSourceAwareController;
import it.people.feservice.FEInterface;
import it.people.feservice.beans.AvailableService;
import it.people.feservice.exceptions.FEInterfaceExtException;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 05/feb/2011 11.44.22
 *
 */
public class NodeToNodeCopier extends MessageSourceAwareController {

	private static Logger logger = LoggerFactory.getLogger(NodeToNodeCopier.class);
	
	public NodeToNodeCopier() {
		
	}
	
	public FENodeAndServicesRegistrationResult<NodeToNodeCopyResult> copyNode(DataSource peopleDb, FENodeToNodeCopy feNodeToNodeCopy) {

		
		FENodeAndServicesRegistrationResult<NodeToNodeCopyResult> result = new FENodeAndServicesRegistrationResult<NodeToNodeCopyResult>();
		updateSelectedAreasServices(feNodeToNodeCopy, result);
		boolean destinationNodeAvailable = true;

		result.setNewNodeRegistration(feNodeToNodeCopy.isToNewNode());
		
		if (logger.isDebugEnabled()) {
			logger.debug("copy from node '" + feNodeToNodeCopy.getSelectedFromNodeName() + "' to node '" + 
					feNodeToNodeCopy.getSelectedToNodeName() + "'...");
			if (feNodeToNodeCopy.isToNewNode()) {
				logger.debug("Destination node is a new node.");
			}
		}

		if (feNodeToNodeCopy.isToNewNode()) {
			if (logger.isDebugEnabled()) {
				if (feNodeToNodeCopy.isToNewNode()) {
					logger.debug("Registering destination node.");
				}
			}
			result.setNewNodeRegistrationError(saveNode(peopleDb, feNodeToNodeCopy));
			destinationNodeAvailable = result.getNewNodeRegistrationError() == null;
		}
		
		if (destinationNodeAvailable) {

			boolean validFeNodeCopy = feNodeCopy(peopleDb, feNodeToNodeCopy);

			boolean validPeopleDbNodeCopy = true;
			if (validFeNodeCopy) {
				validPeopleDbNodeCopy = peopleDbNodeCopy(peopleDb, feNodeToNodeCopy.getSelectedServicesPackages().toArray(
						new String[feNodeToNodeCopy.getSelectedServicesPackages().size()]), 
						feNodeToNodeCopy.getSelectedFromNodeId(), 
						feNodeToNodeCopy.getSelectedToNodeId(), feNodeToNodeCopy, result);
			} else {
				result.addMessages("Si è verificato un errore nella copia dei servizi. Nessun servizio è stato copiato.");
			}
			
		}
		
		return result;
		
	}

	private void updateSelectedAreasServices(FENodeToNodeCopy feNodeToNodeCopy, 
			FENodeAndServicesRegistrationResult<NodeToNodeCopyResult> resultsBinding) {
		
		if (!feNodeToNodeCopy.getSelectedAreas().isEmpty()) {
			Iterator<AvailableService> availableServiceIterator = feNodeToNodeCopy.getAvailableServiceCollection().iterator();
			while(availableServiceIterator.hasNext()) {
				AvailableService availableService = availableServiceIterator.next();
				if (feNodeToNodeCopy.getSelectedAreas().contains(availableService.getActivity()) && 
						!feNodeToNodeCopy.getSelectedServicesPackages().contains(availableService.get_package())) {
					resultsBinding.addMessages(this.getProperty("log.nodeToNodeCopier.addServiceFromSelectedArea", new String[] {
							availableService.getServiceName(), availableService.getActivity()
					}));
					feNodeToNodeCopy.getSelectedServicesPackages().add(String.valueOf(availableService.get_package()));
				}
			}
		}
		
	}
	
    private ObjectError saveNode(DataSource peopleDb, FENodeToNodeCopy feNodeToNodeCopy) {
    	
    	ObjectError result = null;
    	
		IPersistenceManager feNodesPersistenceManager = PersistenceManagerFactory.getInstance()
			.get(FENode.class, Mode.WRITE);
		
		try {
	    	FEInterface feInterface = this.getFEInterface(feNodeToNodeCopy.getNewFeNode().getFeServiceURL());
	    	feInterface.registerNodeWithAoo(feNodeToNodeCopy.getNewFeNode().getMunicipality(), 
	    			feNodeToNodeCopy.getNewFeNode().getMunicipalityCode(), 
	    			feNodeToNodeCopy.getNewFeNode().getName(), feNodeToNodeCopy.getNewFeNode().getAooPrefix(),
	    			feNodeToNodeCopy.getNewFeNode().getAnnouncementMessage(), 
	    			new Boolean(feNodeToNodeCopy.getNewFeNode().isShowAnnouncement()),
	    			feNodeToNodeCopy.getNewFeNode().isOnlineSign(),
	    			feNodeToNodeCopy.getNewFeNode().isOfflineSign());
			feNodesPersistenceManager.set(feNodeToNodeCopy.getNewFeNode());
			updateToNodeId(peopleDb, feNodeToNodeCopy);
		} catch (PersistenceManagerException e) {
			logger.error(this.getProperty("error.feservices.nodeToNodeCopy.toNewNode.dbError"), e);
			result = new ObjectError("error", this.getProperty("error.feservices.nodeToNodeCopy.toNewNode.error"));
		} catch (FeServiceReferenceException e) {
			logger.error(this.getProperty("error.feservices.nodeToNodeCopy.toNewNode.feReferenceError"), e);
			result = new ObjectError("error", this.getProperty("error.feservices.nodeToNodeCopy.toNewNode.error"));
		} catch (RemoteException e) {
			logger.error(this.getProperty("error.feservices.nodeToNodeCopy.toNewNode.remoteException"), e);
			result = new ObjectError("error", this.getProperty("error.feservices.nodeToNodeCopy.toNewNode.error"));
		}
		finally {
			feNodesPersistenceManager.close();
		}
    	
		return result;
		
    }

    private void updateToNodeId(DataSource peopleDb, FENodeToNodeCopy feNodeToNodeCopy) {
    	
    	Connection connection = null;
    	PreparedStatement preparedStatement = null;
    	ResultSet resultSet = null;
    	String query = "SELECT id FROM fenode WHERE codicecomune = ? AND comune = ? AND nodofe = ? AND reference = ?";
    	
    	try {
    		connection = peopleDb.getConnection();
    		preparedStatement = connection.prepareStatement(query);
    		preparedStatement.setString(1, feNodeToNodeCopy.getNewFeNode().getMunicipalityCode());
    		preparedStatement.setString(2, feNodeToNodeCopy.getNewFeNode().getMunicipality());
    		preparedStatement.setString(3, feNodeToNodeCopy.getNewFeNode().getName());
    		preparedStatement.setString(4, feNodeToNodeCopy.getNewFeNode().getFeServiceURL());
    		resultSet = preparedStatement.executeQuery();
    		if (resultSet.next()) {
    			feNodeToNodeCopy.setSelectedToNodeId(resultSet.getInt(1));
    		}
    	} catch(SQLException e) {
    		
    	}
    	finally {
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
    			
    		} catch(SQLException e) {
        		
        	}
    	}
    	
    }
    
    private boolean feNodeCopy(DataSource peopleDb, FENodeToNodeCopy feNodeToNodeCopy) {
    	
    	boolean result = true;

    	try {
    		
    		String[] areasLogicalNamesPrefix = getAreasLogicalNamesPrefix(feNodeToNodeCopy);
    		String[] areasLogicalNamesSuffix = getAreasLogicalNamesSuffix(feNodeToNodeCopy);
    		String[] servicesLogicalNamesPrefix = getServicesLogicalNamesPrefix(feNodeToNodeCopy);
    		String[] servicesLogicalNamesSuffix = getServicesLogicalNamesSuffix(feNodeToNodeCopy);
    	
    		String feReference = "";
    		if (feNodeToNodeCopy.isToNewNode()) {
    			feReference = feNodeToNodeCopy.getNewFeNode().getFeServiceURL();
    		} else {
    			feReference = getToNodeFeReference(feNodeToNodeCopy, peopleDb);
    		}
    		if (StringUtils.isEmptyString(feReference)) {
    			throw new FeServiceReferenceException("FEInterface reference is null.");
    		}
			FEInterface feInterface = this.getFEInterface(feReference);
			result = feInterface.nodeCopy(feNodeToNodeCopy.getSelectedServicesPackages().toArray(
				new String[feNodeToNodeCopy.getSelectedServicesPackages().size()]), 
				areasLogicalNamesPrefix,
				areasLogicalNamesSuffix,
				servicesLogicalNamesPrefix,
				servicesLogicalNamesSuffix,
					feNodeToNodeCopy.getSelectedFromNodeKey(), 
					(feNodeToNodeCopy.isToNewNode() ? feNodeToNodeCopy.getNewFeNode().getMunicipalityCode() : 
						feNodeToNodeCopy.getSelectedToNodeKey()));
		} catch (FeServiceReferenceException e) {
			logger.error("feNodeCopy", e);
			result = false;
		} catch (RemoteException e) {
			logger.error("feNodeCopy", e);
			result = false;
		}
    	
    	return result;
    	
    }
    
    private boolean peopleDbNodeCopy(DataSource peopleDb, String[] selectedServices, int fromCommuneId, 
    		int toCommuneId, FENodeToNodeCopy feNodeToNodeCopy, 
    		FENodeAndServicesRegistrationResult<NodeToNodeCopyResult> resultsBinding) {

    	boolean result = true;

    	Connection connection = null;
    	PreparedStatement selectFromCommunePreparedStatement = null;
    	PreparedStatement selectPreparedStatement = null;
    	PreparedStatement insertPreparedStatement = null;
    	PreparedStatement selectExistingBePreparedStatement = null;
    	ResultSet selectResultSet = null;
    	ResultSet selectDataResultSet = null;
    	ResultSet generatedKeyResultSet = null;
    	ResultSet selectExistingBeResultSet = null;

    	// Select all service from service with communeid = fromCommuneId that are not already associated
    	// with toCommuneId and write to service table with toCommuneId

    	try {
    		if (logger.isDebugEnabled()) {
    			logger.debug("Searching registered services for commune '" + fromCommuneId + "'...");
    		}

    		connection = peopleDb.getConnection();

    		String selectQuery = "SELECT id, nome, package, loglevel, stato, attivita, sottoattivita, PROCESS, " + 
    			"receiptmailattachment, signenabled FROM service WHERE nodeid = ? AND package " + 
    			"NOT IN (SELECT package FROM service WHERE nodeid = ?)";
    		String insertQuery = "INSERT INTO service(nodeid, nome, package, loglevel, stato, attivita, " + 
    			"sottoattivita, PROCESS, receiptmailattachment, signenabled) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    		String selectConfigurationQuery = "SELECT NAME, VALUE FROM configuration WHERE serviceid = ?";
    		String insertConfigurationQuery = "INSERT INTO configuration(serviceid, NAME, VALUE) VALUES(?, ?, ?)";
    		String selectReferenceQuery = "SELECT NAME, VALUE, address, dump, mailincludexml FROM reference WHERE serviceid = ?";
    		String insertReferenceQuery = "INSERT INTO reference(serviceid, NAME, VALUE, address, dump, mailincludexml) VALUES(?, ?, ?, ?, ?, ?)";
    		
    		String selectBeNodesQuery = "SELECT nodobe, reference, useenvelope, disablecheckdelegate FROM " +
    				"benode AS be JOIN reference AS ref ON ref.value = be.nodobe JOIN service AS srv ON srv.id = " +
    				"ref.serviceid where be.nodeid = ? and srv.id = ?";
    		String insertBeNodeQuery = "INSERT INTO benode(nodeid, nodobe, reference, useenvelope, disablecheckdelegate) VALUES(?, ?, ?, ?, ?)";

    		String selectExistingBe = "SELECT id FROM benode WHERE nodeid = ? AND nodobe = ?";
    		
			String selectInClause = " AND package IN (";
			for(int index = 0; index < (selectedServices.length - 1); index++) {
				selectInClause += "'" + selectedServices[index] + "', ";
			}
			selectInClause += "'" + selectedServices[selectedServices.length - 1] + "')";
			selectQuery += selectInClause;

			String selectBeNodesInClause = " AND srv.package IN (";
			for(int index = 0; index < (selectedServices.length - 1); index++) {
				selectBeNodesInClause += "'" + selectedServices[index] + "', ";
			}
			selectBeNodesInClause += "'" + selectedServices[selectedServices.length - 1] + "')";
			selectBeNodesQuery += selectBeNodesInClause;
			
    		selectFromCommunePreparedStatement = connection.prepareStatement(selectQuery);
    		selectFromCommunePreparedStatement.setInt(1, fromCommuneId);
    		selectFromCommunePreparedStatement.setInt(2, toCommuneId);
    		selectResultSet = selectFromCommunePreparedStatement.executeQuery();

    		while(selectResultSet.next()) {

    			String serviceArea = selectResultSet.getString(6);
    			String _package = selectResultSet.getString(3);
    			String logicalPrefix = getLogicalPrefix(serviceArea, _package, feNodeToNodeCopy);
    			String logicalSuffix = getLogicalSuffix(serviceArea, _package, feNodeToNodeCopy);
    			String beUrlServicePrefix = getBeUrlServicePrefix(serviceArea, _package, feNodeToNodeCopy);
    			String beUrlServiceSuffix = getBeUrlServiceSuffix(serviceArea, _package, feNodeToNodeCopy);

    			
    			NodeToNodeCopyResult nodeToNodeCopyResult = new NodeToNodeCopyResult();
    			nodeToNodeCopyResult.setServiceName(selectResultSet.getString(2));
    			nodeToNodeCopyResult.setServiceActivity(selectResultSet.getString(6));
    			nodeToNodeCopyResult.setServiceSubActivity(selectResultSet.getString(7));
    			nodeToNodeCopyResult.setServicePackage(selectResultSet.getString(3));
    			
    			int fromServiceId = selectResultSet.getInt(1);

    			insertPreparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
    			insertPreparedStatement.setInt(1, toCommuneId);
    			insertPreparedStatement.setString(2, selectResultSet.getString(2));
    			insertPreparedStatement.setString(3, selectResultSet.getString(3));
    			insertPreparedStatement.setInt(4, selectResultSet.getInt(4));
    			insertPreparedStatement.setInt(5, selectResultSet.getInt(5));
    			insertPreparedStatement.setString(6, selectResultSet.getString(6));
    			insertPreparedStatement.setString(7, selectResultSet.getString(7));
    			insertPreparedStatement.setString(8, selectResultSet.getString(8));
    			insertPreparedStatement.setInt(9, selectResultSet.getInt(9));
    			insertPreparedStatement.setInt(10, selectResultSet.getInt(10));
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
    					insertPreparedStatement.setString(3, logicalPrefix + selectDataResultSet.getString(2) + logicalSuffix);
    					insertPreparedStatement.setString(4, selectDataResultSet.getString(3));
    					insertPreparedStatement.setInt(5, selectDataResultSet.getInt(4));
    					insertPreparedStatement.setInt(6, selectDataResultSet.getInt(5));
    					insertPreparedStatement.execute();
    					insertPreparedStatement.clearParameters();
    				}

    				selectPreparedStatement.close();

    				
    				
    				
    				
    				selectPreparedStatement.close();
    				selectPreparedStatement = connection.prepareStatement(selectBeNodesQuery);
    				selectPreparedStatement.setInt(1, fromCommuneId);
    				selectPreparedStatement.setInt(2, fromServiceId);

    				selectDataResultSet = selectPreparedStatement.executeQuery();

    				insertPreparedStatement.close();
    				insertPreparedStatement = connection.prepareStatement(insertBeNodeQuery);
    				
    				selectExistingBePreparedStatement = connection.prepareStatement(selectExistingBe);
    				
    				while(selectDataResultSet.next()) {
    					
    					selectExistingBePreparedStatement.clearParameters();
    					selectExistingBePreparedStatement.setInt(1, toCommuneId);
    					selectExistingBePreparedStatement.setString(2, selectDataResultSet.getString(1));
    					selectExistingBeResultSet = selectExistingBePreparedStatement.executeQuery();
    					
    					if (!selectExistingBeResultSet.next()) {
    						
        					String newReference = getNewBeReference(serviceArea, _package, selectDataResultSet.getString(2), feNodeToNodeCopy, 
        							beUrlServicePrefix, beUrlServiceSuffix);
        					String referenceNewLogicaName = logicalPrefix + selectDataResultSet.getString(1) + logicalSuffix;
        					nodeToNodeCopyResult.addMessages(this.getProperty("log.nodeToNodeCopier.beLogicalNameRegistration", new String[] {
        							referenceNewLogicaName, selectDataResultSet.getString(1)
        					}));
        					nodeToNodeCopyResult.addMessages(this.getProperty("log.nodeToNodeCopier.beUrlRegistration", new String[] {
        							newReference, selectDataResultSet.getString(2)
        					}));

        					insertPreparedStatement.setInt(1, toCommuneId);
        					insertPreparedStatement.setString(2, referenceNewLogicaName);
        					insertPreparedStatement.setString(3, newReference);
        					insertPreparedStatement.setInt(4, selectDataResultSet.getInt(3));
        					insertPreparedStatement.setInt(5, selectDataResultSet.getInt(4));
        					insertPreparedStatement.execute();
        					insertPreparedStatement.clearParameters();
    					}
    					
    				}

    				selectPreparedStatement.close();
    				
    				
    			}

    			insertPreparedStatement.clearParameters();
    			
    			resultsBinding.addServicesRegistrationResult(nodeToNodeCopyResult);

    		}

    	} catch (SQLException e) {
    		logger.error("Node copy error:", e);
    		resultsBinding.addRegistrationErrors(new ObjectError(this.getProperty("log.nodeToNodeCopier.nodeCopyError"), 
    				CastUtils.exceptionToString(e)));
    		result = false;
    	} catch (MalformedURLException e) {
    		logger.error("Node copy error:", e);
    		resultsBinding.addRegistrationErrors(new ObjectError(this.getProperty("log.nodeToNodeCopier.nodeCopyError"), 
    				CastUtils.exceptionToString(e)));
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

    	return result;

    }
    
    /**
     * @param serviceArea
     * @param serviceId
     * @param actualReference
     * @param feNodeToNodeCopy
     * @return
     * @throws MalformedURLException
     */
    private String getNewBeReference(String serviceArea, String _package, String actualReference, FENodeToNodeCopy feNodeToNodeCopy, 
    		String beUrlServicePrefix, String beUrlServiceSuffix) throws MalformedURLException {
    	
    	String newBeHostNamePortAndContext = getNewBeHostNamePortAndContext(serviceArea, _package, feNodeToNodeCopy);

    	String actualReferenceUrl = actualReference;
    	String newHostPortContext = null;
    	
    	if (!StringUtils.isEmptyString(newBeHostNamePortAndContext)) {
        	String hostPortContext = actualReferenceUrl.substring(0, actualReferenceUrl.lastIndexOf('/'));
        	newHostPortContext = actualReference.replace(hostPortContext, sanitizeUrl(newBeHostNamePortAndContext));
    	}
    	else {
    		newHostPortContext = actualReference;
    	}

    	String serviceName = StringUtils.nullToEmpty(newHostPortContext.substring(newHostPortContext.lastIndexOf('/') + 1));
    	
    	return newHostPortContext.substring(0, newHostPortContext.lastIndexOf('/')) + "/" + beUrlServicePrefix + serviceName + beUrlServiceSuffix;
    	
    }
    
    /**
     * @param url
     * @return
     */
    private String sanitizeUrl(String url) {
    	
    	return url.endsWith("/") ? url.substring(0, url.lastIndexOf('/')) : url;
    	
    }
    
    /**
     * @param serviceArea
     * @param serviceId
     * @param feNodeToNodeCopy
     * @return
     */
    private String getNewBeHostNamePortAndContext(String serviceArea, String _package, FENodeToNodeCopy feNodeToNodeCopy) {
    	
    	//Se servizio non ha be url, allora verifica se area ha be url; se area non ha be url, allora usa indirizzo be generico
    	if (feNodeToNodeCopy.getSelectedServicesBEUrl().containsKey(_package) && 
    			!StringUtils.isEmptyString(feNodeToNodeCopy.getSelectedServicesBEUrl().get(_package))) {
    		return feNodeToNodeCopy.getSelectedServicesBEUrl().get(_package);
    	}

    	if (feNodeToNodeCopy.getSelectedAreasBEUrl().containsKey(serviceArea) && 
    			!StringUtils.isEmptyString(feNodeToNodeCopy.getSelectedAreasBEUrl().get(serviceArea))) {
    		return feNodeToNodeCopy.getSelectedAreasBEUrl().get(serviceArea);
    	}

    	return feNodeToNodeCopy.getBeServicesUrl();
    	
    }
    
    private String getLogicalPrefix(String serviceArea, String _package, FENodeToNodeCopy feNodeToNodeCopy) {
    	
    	if (feNodeToNodeCopy.getSelectedServicesLogicalNamePrefix().containsKey(_package) && 
    			!StringUtils.isEmptyString(feNodeToNodeCopy.getSelectedServicesLogicalNamePrefix().get(_package))) {
    		return feNodeToNodeCopy.getSelectedServicesLogicalNamePrefix().get(_package);
    	}

    	if (feNodeToNodeCopy.getSelectedAreasServicesLogicalNamePrefix().containsKey(serviceArea) && 
    			!StringUtils.isEmptyString(feNodeToNodeCopy.getSelectedAreasServicesLogicalNamePrefix().get(serviceArea))) {
    		return feNodeToNodeCopy.getSelectedAreasServicesLogicalNamePrefix().get(serviceArea);
    	}
    	
    	return "";
    	
    }

    private String getLogicalSuffix(String serviceArea, String _package, FENodeToNodeCopy feNodeToNodeCopy) {
    	
    	if (feNodeToNodeCopy.getSelectedServicesLogicalNameSuffix().containsKey(_package) && 
    			!StringUtils.isEmptyString(feNodeToNodeCopy.getSelectedServicesLogicalNameSuffix().get(_package))) {
    		return feNodeToNodeCopy.getSelectedServicesLogicalNameSuffix().get(_package);
    	}

    	if (feNodeToNodeCopy.getSelectedAreasServicesLogicalNameSuffix().containsKey(serviceArea) && 
    			!StringUtils.isEmptyString(feNodeToNodeCopy.getSelectedAreasServicesLogicalNameSuffix().get(serviceArea))) {
    		return feNodeToNodeCopy.getSelectedAreasServicesLogicalNameSuffix().get(serviceArea);
    	}
    	
    	return "";
    	
    }

    /**
     * @param serviceArea
     * @param _package
     * @param feNodeToNodeCopy
     * @return
     */
    private String getBeUrlServicePrefix(String serviceArea, String _package, FENodeToNodeCopy feNodeToNodeCopy) {
    	
    	if (feNodeToNodeCopy.getSelectedServicesNamePrefix().containsKey(_package) && 
    			!StringUtils.isEmptyString(feNodeToNodeCopy.getSelectedServicesNamePrefix().get(_package))) {
    		return feNodeToNodeCopy.getSelectedServicesNamePrefix().get(_package);
    	}

    	if (feNodeToNodeCopy.getSelectedAreasServicesNamePrefix().containsKey(serviceArea) && 
    			!StringUtils.isEmptyString(feNodeToNodeCopy.getSelectedAreasServicesNamePrefix().get(serviceArea))) {
    		return feNodeToNodeCopy.getSelectedAreasServicesNamePrefix().get(serviceArea);
    	}
    	
    	return "";
    	
    }
    
    /**
     * @param serviceArea
     * @param _package
     * @param feNodeToNodeCopy
     * @return
     */
    private String getBeUrlServiceSuffix(String serviceArea, String _package, FENodeToNodeCopy feNodeToNodeCopy) {
    	
    	if (feNodeToNodeCopy.getSelectedServicesNameSuffix().containsKey(_package) && 
    			!StringUtils.isEmptyString(feNodeToNodeCopy.getSelectedServicesNameSuffix().get(_package))) {
    		return feNodeToNodeCopy.getSelectedServicesNameSuffix().get(_package);
    	}

    	if (feNodeToNodeCopy.getSelectedAreasServicesNameSuffix().containsKey(serviceArea) && 
    			!StringUtils.isEmptyString(feNodeToNodeCopy.getSelectedAreasServicesNameSuffix().get(serviceArea))) {
    		return feNodeToNodeCopy.getSelectedAreasServicesNameSuffix().get(serviceArea);
    	}
    	
    	return "";
    	
    }
    
	/**
	 * @param feNodeToNodeCopy
	 * @return
	 */
	private String[] getAreasLogicalNamesPrefix(FENodeToNodeCopy feNodeToNodeCopy) {
		
		Vector<String> result = new Vector<String>();

		if (!feNodeToNodeCopy.getSelectedAreasServicesLogicalNamePrefix().isEmpty()) {
			Iterator<String> iterator = feNodeToNodeCopy.getSelectedAreasServicesLogicalNamePrefix().keySet().iterator();
			while(iterator.hasNext()) {
				String key = iterator.next();
				String value = feNodeToNodeCopy.getSelectedAreasServicesLogicalNamePrefix().get(key);
				result.add(key + "$" + value);
			}
		}
		
		return result.toArray(new String[result.size()]);
		
	}
	
	/**
	 * @param feNodeToNodeCopy
	 * @return
	 */
	private String[] getAreasLogicalNamesSuffix(FENodeToNodeCopy feNodeToNodeCopy) {
		
		Vector<String> result = new Vector<String>();

		if (!feNodeToNodeCopy.getSelectedAreasServicesLogicalNameSuffix().isEmpty()) {
			Iterator<String> iterator = feNodeToNodeCopy.getSelectedAreasServicesLogicalNameSuffix().keySet().iterator();
			while(iterator.hasNext()) {
				String key = iterator.next();
				String value = feNodeToNodeCopy.getSelectedAreasServicesLogicalNameSuffix().get(key);
				result.add(key + "$" + value);
			}
		}
		
		return result.toArray(new String[result.size()]);
		
	}
	
	/**
	 * @param feNodeToNodeCopy
	 * @return
	 */
	private String[] getServicesLogicalNamesPrefix(FENodeToNodeCopy feNodeToNodeCopy) {
		
		Vector<String> result = new Vector<String>();

		if (!feNodeToNodeCopy.getSelectedServicesLogicalNamePrefix().isEmpty()) {
			Iterator<String> iterator = feNodeToNodeCopy.getSelectedServicesLogicalNamePrefix().keySet().iterator();
			while(iterator.hasNext()) {
				String key = iterator.next();
				String value = feNodeToNodeCopy.getSelectedServicesLogicalNamePrefix().get(key);
				result.add(key + "$" + value);
			}
		}

		return result.toArray(new String[result.size()]);
		
	}
	
	/**
	 * @param feNodeToNodeCopy
	 * @return
	 */
	private String[] getServicesLogicalNamesSuffix(FENodeToNodeCopy feNodeToNodeCopy) {
		
		Vector<String> result = new Vector<String>();

		if (!feNodeToNodeCopy.getSelectedServicesLogicalNameSuffix().isEmpty()) {
			Iterator<String> iterator = feNodeToNodeCopy.getSelectedServicesLogicalNameSuffix().keySet().iterator();
			while(iterator.hasNext()) {
				String key = iterator.next();
				String value = feNodeToNodeCopy.getSelectedServicesLogicalNameSuffix().get(key);
				result.add(key + "$" + value);
			}
		}

		return result.toArray(new String[result.size()]);
		
	}

	/**
	 * @param feNodeToNodeCopy
	 * @param peopleDb
	 * @return
	 */
	private String getToNodeFeReference(FENodeToNodeCopy feNodeToNodeCopy, DataSource peopleDb) {

		String result = null;
    	Connection connection = null;
    	PreparedStatement preparedStatement = null;
    	ResultSet resultSet = null;
    	String query = "SELECT reference FROM fenode WHERE comune = ? AND id = ?";
    	
    	try {
    		connection = peopleDb.getConnection();
    		preparedStatement = connection.prepareStatement(query);
    		preparedStatement.setString(1, feNodeToNodeCopy.getSelectedToNodeName());
    		preparedStatement.setInt(2, feNodeToNodeCopy.getSelectedToNodeId());
    		resultSet = preparedStatement.executeQuery();
    		if (resultSet.next()) {
    			result = resultSet.getString(1);
    		}
    	} catch(SQLException e) {
    		
    	}
    	finally {
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
    			
    		} catch(SQLException e) {
        		
        	}
    	}
	
    	return result;
		
	}
	
}
