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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

import it.people.console.beans.ObjectError;
import it.people.console.domain.BEService;
import it.people.console.domain.BEServicesMassiveChange;
import it.people.console.domain.BeServiceOperationResults;
import it.people.console.domain.OperationResults;
import it.people.console.utils.Constants;
import it.people.console.utils.StringUtils;
import it.people.console.web.servlet.mvc.MessageSourceAwareController;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 24/ott/2011 16.08.07
 *
 */
@Service
public class BeServicesMassiveChangeExecutor extends MessageSourceAwareController {

	public BeServicesMassiveChangeExecutor() {
		
	}
	
	/**
	 * @param beServicesMassiveChange
	 */
	public OperationResults<BeServiceOperationResults> doMassiveParametersChange(DataSource dataSource, BEServicesMassiveChange beServicesMassiveChange) {
		
		OperationResults<BeServiceOperationResults> result = new OperationResults<BeServiceOperationResults>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		boolean autoCommit = true;

		try {
		
			connection = dataSource.getConnection();
			autoCommit = connection.getAutoCommit();
			connection.setAutoCommit(true);
			preparedStatement = connection.prepareStatement(this.getProperty(Constants.Queries.BE_SERVICES_MASSIVE_CHANGE_QUERY));
			
			if (beServicesMassiveChange.getSelectedServicesId() == null || (beServicesMassiveChange.getSelectedServicesId() != null 
					&& beServicesMassiveChange.getSelectedServicesId().isEmpty())) {
				
				doNodeCompleteServicesChange(preparedStatement, beServicesMassiveChange, result);
				
			} else {
				Vector<String> completeNodeDeletion = new Vector<String>();
				Vector<String> partialNodeDeletion = new Vector<String>();
				Vector<String> partialNodeDeletionServicesId = new Vector<String>();
				Iterator<String> selectedServicesIdIterator = beServicesMassiveChange.getSelectedServicesId().iterator();
				while(selectedServicesIdIterator.hasNext()) {
					String selectedServiceId = selectedServicesIdIterator.next();
					String nodeId = selectedServiceId.substring(0, selectedServiceId.indexOf('.'));
					String serviceId = selectedServiceId.substring(selectedServiceId.indexOf('.') + 1);
					if (!partialNodeDeletionServicesId.contains(serviceId)) {
						partialNodeDeletionServicesId.add(serviceId);
					}
					if (!partialNodeDeletion.contains(nodeId)) {
						partialNodeDeletion.add(nodeId);
					}
				}
				if (beServicesMassiveChange.getSelectedNodes() != null && !beServicesMassiveChange.getSelectedNodes().isEmpty()) {
					Iterator<String> selectedNodesIterator = beServicesMassiveChange.getSelectedNodes().iterator();
					while(selectedNodesIterator.hasNext()) {
						String nodeId = selectedNodesIterator.next();
						if (!partialNodeDeletion.contains(nodeId) && !completeNodeDeletion.contains(nodeId)) {
							completeNodeDeletion.add(nodeId);
						}
					}
				}
				if (!completeNodeDeletion.isEmpty()) {
					
					doNodeCompleteServicesChange(preparedStatement, beServicesMassiveChange, result);
					
				}
				if (!partialNodeDeletion.isEmpty()) {
					Iterator<String> partialNodeDeletionIterator = partialNodeDeletion.iterator();
					while(partialNodeDeletionIterator.hasNext()) {
						List<BEService> beServices = beServicesMassiveChange.getAvailableServices().get(Long.parseLong(partialNodeDeletionIterator.next()));
						Iterator<BEService> beServicesIterator = beServices.iterator();
						while(beServicesIterator.hasNext()) {
							BeServiceOperationResults beServiceOperationResults = new BeServiceOperationResults();
							BEService beService = beServicesIterator.next();
							if (partialNodeDeletionServicesId.contains(String.valueOf(beService.getId()))) {

								boolean urlChanged = changeUrl(beService, beServicesMassiveChange, beServiceOperationResults);
								boolean transportEnvelopeStatusChanged = beService.isTransportEnvelopeEnabled() != beServicesMassiveChange.isTransportEnvelopeEnabled();
								boolean delegationControlForbiddenStatusChanged = beService.isDelegationControlForbidden() != beServicesMassiveChange.isDelegationControlForbidden();
								
								if (urlChanged || transportEnvelopeStatusChanged || delegationControlForbiddenStatusChanged) {

								try {
									preparedStatement.setString(1, beService.getBackEndURL());
									preparedStatement.setInt(2, (beServicesMassiveChange.isTransportEnvelopeEnabled() ? 1 : 0));
									preparedStatement.setInt(3, (beServicesMassiveChange.isDelegationControlForbidden() ? 1 : 0));
									preparedStatement.setInt(4, beService.getId().intValue());
									preparedStatement.execute();
									beServiceOperationResults.setServiceName(beService.getLogicalServiceName());
									if (transportEnvelopeStatusChanged) {
										beServiceOperationResults.addMessages((beServicesMassiveChange.isTransportEnvelopeEnabled() ? "Attivato " : "Disattivato ") 
											+ "l'utilizzo della busta di trasporto.");
									}
									if (delegationControlForbiddenStatusChanged) {
										beServiceOperationResults.addMessages((beServicesMassiveChange.isDelegationControlForbidden() ? "Attivata " : "Disattivata ") 
											+ "l'inibizione del controllo delle deleghe.");
									}
									beServiceOperationResults.addMessages(this.getProperty("beservices.massiveChange.service.successfully.change", 
											new String[] {beService.getLogicalServiceName(), beService.getNodeName()}));
									result.addOperationResult(beServiceOperationResults);
									
									preparedStatement.clearParameters();
									preparedStatement.clearWarnings();
								} catch (SQLException ex) {
									beServiceOperationResults.addErrors(new ObjectError("error", 
											this.getProperty("error.beservices.massiveChange.change.service.error", 
													new String[] {beService.getLogicalServiceName(), beService.getNodeName()})));
								}
								}
								
							}
						}
					}
				}
			}

		} catch (SQLException ex) {
			result.addRegistrationErrors(new ObjectError("error", this.getProperty("error.beservices.massiveChange.db.error")));
		} finally {
			try {
				connection.setAutoCommit(autoCommit);
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch(SQLException ignore) {
				
			}
		}
		
		return result;
		
	}

	public OperationResults<BeServiceOperationResults> doMassiveDeletion(DataSource dataSource, BEServicesMassiveChange beServicesMassiveChange) {
		
		OperationResults<BeServiceOperationResults> result = new OperationResults<BeServiceOperationResults>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		boolean autoCommit = true;

		try {
		
			connection = dataSource.getConnection();
			autoCommit = connection.getAutoCommit();
			connection.setAutoCommit(true);
			preparedStatement = connection.prepareStatement(this.getProperty(Constants.Queries.NODES_ORPHANED_BE_SERVICES_DELETION_QUERY));
			
			if (beServicesMassiveChange.getSelectedServicesId() == null || (beServicesMassiveChange.getSelectedServicesId() != null 
					&& beServicesMassiveChange.getSelectedServicesId().isEmpty())) {
				
				doNodeCompleteServicesDeletion(preparedStatement, beServicesMassiveChange.getSelectedNodes(), beServicesMassiveChange.getAvailableServices(), result);
				
			} else {
				Vector<String> completeNodeDeletion = new Vector<String>();
				Vector<String> partialNodeDeletion = new Vector<String>();
				Vector<String> partialNodeDeletionServicesId = new Vector<String>();
				Iterator<String> selectedServicesIdIterator = beServicesMassiveChange.getSelectedServicesId().iterator();
				while(selectedServicesIdIterator.hasNext()) {
					String selectedServiceId = selectedServicesIdIterator.next();
					String nodeId = selectedServiceId.substring(0, selectedServiceId.indexOf('.'));
					String serviceId = selectedServiceId.substring(selectedServiceId.indexOf('.') + 1);
					if (!partialNodeDeletionServicesId.contains(serviceId)) {
						partialNodeDeletionServicesId.add(serviceId);
					}
					if (!partialNodeDeletion.contains(nodeId)) {
						partialNodeDeletion.add(nodeId);
					}
				}
				if (beServicesMassiveChange.getSelectedNodes() != null && !beServicesMassiveChange.getSelectedNodes().isEmpty()) {
					Iterator<String> selectedNodesIterator = beServicesMassiveChange.getSelectedNodes().iterator();
					while(selectedNodesIterator.hasNext()) {
						String nodeId = selectedNodesIterator.next();
						if (!partialNodeDeletion.contains(nodeId) && !completeNodeDeletion.contains(nodeId)) {
							completeNodeDeletion.add(nodeId);
						}
					}
				}
				if (!completeNodeDeletion.isEmpty()) {
					
					doNodeCompleteServicesDeletion(preparedStatement, completeNodeDeletion, beServicesMassiveChange.getAvailableServices(), result);
					
				}
				if (!partialNodeDeletion.isEmpty()) {
					Iterator<String> partialNodeDeletionIterator = partialNodeDeletion.iterator();
					while(partialNodeDeletionIterator.hasNext()) {
						List<BEService> beServices = beServicesMassiveChange.getAvailableServices().get(Long.parseLong(partialNodeDeletionIterator.next()));
						Iterator<BEService> beServicesIterator = beServices.iterator();
						while(beServicesIterator.hasNext()) {
							BeServiceOperationResults beServiceOperationResults = new BeServiceOperationResults();
							BEService beService = beServicesIterator.next();
							if (partialNodeDeletionServicesId.contains(String.valueOf(beService.getId()))) {

								try {
									preparedStatement.setInt(1, beService.getId().intValue());
									preparedStatement.execute();
									beServiceOperationResults.setServiceName(beService.getLogicalServiceName());
									beServiceOperationResults.addMessages(this.getProperty("beservices.massiveChange.service.successfully.deletion", 
											new String[] {beService.getLogicalServiceName(), beService.getNodeName()}));
									result.addOperationResult(beServiceOperationResults);
									
									preparedStatement.clearParameters();
									preparedStatement.clearWarnings();
								} catch (SQLException ex) {
									beServiceOperationResults.addErrors(new ObjectError("error", 
											this.getProperty("error.beservices.massiveChange.deletion.service.error", 
													new String[] {beService.getLogicalServiceName(), beService.getNodeName()})));
								}
								
							}
						}
					}
				}
			}

		} catch (SQLException ex) {
			result.addRegistrationErrors(new ObjectError("error", this.getProperty("error.beservices.massiveChange.db.error")));
		} finally {
			try {
				connection.setAutoCommit(autoCommit);
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch(SQLException ignore) {
				
			}
		}
		
		return result;
		
	}

	private boolean doNodeCompleteServicesDeletion(PreparedStatement preparedStatement, final List<String> nodesList, final Map<Long, List<BEService>> availableServices, 
			OperationResults<BeServiceOperationResults> operationResults) {
		
		boolean result = true;
			
		Iterator<String> selectedNodesIterator = nodesList.iterator();
		while(selectedNodesIterator.hasNext()) {
			List<BEService> beServices = availableServices.get(Long.parseLong(selectedNodesIterator.next()));
			Iterator<BEService> beServicesIterator = beServices.iterator();
				
				while(beServicesIterator.hasNext()) {
					BeServiceOperationResults beServiceOperationResults = new BeServiceOperationResults();
					BEService beService = beServicesIterator.next();

					try {
						preparedStatement.setInt(1, beService.getId().intValue());
						preparedStatement.execute();
						beServiceOperationResults.setServiceName(beService.getLogicalServiceName());
						beServiceOperationResults.addMessages(this.getProperty("beservices.massiveChange.service.successfully.deletion", 
								new String[] {beService.getLogicalServiceName(), beService.getNodeName()}));
						operationResults.addOperationResult(beServiceOperationResults);
						
						preparedStatement.clearParameters();
						preparedStatement.clearWarnings();
					} catch (SQLException ex) {
						beServiceOperationResults.addErrors(new ObjectError("error", 
								this.getProperty("error.beservices.massiveChange.deletion.service.error", 
										new String[] {beService.getLogicalServiceName(), beService.getNodeName()})));
						result = false;
					}
					
				}
			
		}
		
		return result;
		
	}

	private boolean doNodeCompleteServicesChange(PreparedStatement preparedStatement, BEServicesMassiveChange beServicesMassiveChange, 
			OperationResults<BeServiceOperationResults> operationResults) {
		
		boolean result = true;
			
		Iterator<String> selectedNodesIterator = beServicesMassiveChange.getSelectedNodes().iterator();
		while(selectedNodesIterator.hasNext()) {
			List<BEService> beServices = beServicesMassiveChange.getAvailableServices().get(Long.parseLong(selectedNodesIterator.next()));
			Iterator<BEService> beServicesIterator = beServices.iterator();
				
				while(beServicesIterator.hasNext()) {
					BeServiceOperationResults beServiceOperationResults = new BeServiceOperationResults();
					BEService beService = beServicesIterator.next();

					boolean urlChanged = changeUrl(beService, beServicesMassiveChange, beServiceOperationResults);
					boolean transportEnvelopeStatusChanged = beService.isTransportEnvelopeEnabled() != beServicesMassiveChange.isTransportEnvelopeEnabled();
					boolean delegationControlForbiddenStatusChanged = beService.isDelegationControlForbidden() != beServicesMassiveChange.isDelegationControlForbidden();
					
					if (urlChanged || transportEnvelopeStatusChanged || delegationControlForbiddenStatusChanged) {
					try {
						preparedStatement.setString(1, beService.getBackEndURL());
						preparedStatement.setInt(2, (beServicesMassiveChange.isTransportEnvelopeEnabled() ? 1 : 0));
						preparedStatement.setInt(3, (beServicesMassiveChange.isDelegationControlForbidden() ? 1 : 0));
						preparedStatement.setInt(4, beService.getId().intValue());
						preparedStatement.execute();
						beServiceOperationResults.setServiceName(beService.getLogicalServiceName());
						if (transportEnvelopeStatusChanged) {
							beServiceOperationResults.addMessages((beServicesMassiveChange.isTransportEnvelopeEnabled() ? "Attivato " : "Disattivato ") 
								+ "l'utilizzo della busta di trasporto.");
						}
						if (delegationControlForbiddenStatusChanged) {
							beServiceOperationResults.addMessages((beServicesMassiveChange.isDelegationControlForbidden() ? "Attivata " : "Disattivata ") 
								+ "l'inibizione del controllo delle deleghe.");
						}
						beServiceOperationResults.addMessages(this.getProperty("beservices.massiveChange.service.successfully.change", 
								new String[] {beService.getLogicalServiceName(), beService.getNodeName()}));
						operationResults.addOperationResult(beServiceOperationResults);
						
						preparedStatement.clearParameters();
						preparedStatement.clearWarnings();
					} catch (SQLException ex) {
						beServiceOperationResults.addErrors(new ObjectError("error", 
								this.getProperty("error.beservices.massiveChange.change.service.error", 
										new String[] {beService.getLogicalServiceName(), beService.getNodeName()})));
						result = false;
					}
					}
				}
			
		}
		
		return result;
		
	}
	
	private boolean changeUrl(BEService beService, BEServicesMassiveChange beServicesMassiveChange, BeServiceOperationResults beServiceOperationResults) {
	
		boolean result = false;
		
		try {

		    String newStringUrl = beService.getBackEndURL();
		    
			if (beServicesMassiveChange.isAdvancedUrlSubstitution()) {
				
			    newStringUrl = beService.getBackEndURL().replaceAll(beServicesMassiveChange.getFind(), beServicesMassiveChange.getReplace());			    
			    
			} else {

				URL backendUrl = new URL(beService.getBackEndURL());

				String newProtocol = backendUrl.getProtocol();
				String newHost = backendUrl.getHost();
				String newPort = String.valueOf(backendUrl.getPort());
				String path = backendUrl.getPath();
//				String query = backendUrl.getQuery();
			    
				if (!StringUtils.isEmptyString(beServicesMassiveChange.getProtocol())) {
					newProtocol = beServicesMassiveChange.getProtocol();
				}
				if (!StringUtils.isEmptyString(beServicesMassiveChange.getHost())) {
					newHost = beServicesMassiveChange.getHost();
				}
				if (!StringUtils.isEmptyString(beServicesMassiveChange.getPort())) {
					newPort = beServicesMassiveChange.getPort();
				}

				newStringUrl = newProtocol + "://" + newHost + (StringUtils.isEmptyString(newPort) ? "" : ":" + newPort) + path;
								
			}

			    if (!newStringUrl.equalsIgnoreCase(beService.getBackEndURL())) {
				beServiceOperationResults.addMessages("Modificata l'url di back end da '" + beService.getBackEndURL() + "' a '" + newStringUrl + "'.");
				
				beService.setBackEndURL(newStringUrl);
				result = true;
			    }			
			
		} catch (MalformedURLException e) {
			beServiceOperationResults.addErrors(new ObjectError("error", 
					this.getProperty("error.beservices.massiveChange.serviceChange.url.error", 
							new String[] {beService.getLogicalServiceName(), beService.getNodeName()})));
		}
				
		return result;
		
	}
	
}
