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

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

import it.people.console.beans.ObjectError;
import it.people.console.domain.FEServiceMassiveParametersModifiable;
import it.people.console.domain.FEServicesMassiveChange;
import it.people.console.domain.FeNodeOperationResults;
import it.people.console.domain.FeServiceOperationResults;
import it.people.console.domain.OperationResults;
import it.people.console.dto.ExtendedAvailableService;
import it.people.console.system.AbstractLogger;
import it.people.console.utils.Constants;
import it.people.console.web.client.exceptions.FeServiceReferenceException;
import it.people.console.web.servlet.mvc.MessageSourceAwareController;
import it.people.feservice.FEInterface;
import it.people.feservice.beans.CommunePackageVO;
import it.people.feservice.beans.DependentModule;
import it.people.feservice.beans.FeServiceChangeResult;
import it.people.feservice.beans.ServiceVO;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 24/ott/2011 16.08.07
 *
 */
@Service
public class FeServicesMassiveChangeExecutor extends MessageSourceAwareController {

	public FeServicesMassiveChangeExecutor() {
		
	}
	
	/**
	 * @param feServicesMassiveChange
	 */
	public OperationResults<FeServiceOperationResults> doMassiveParametersChange(DataSource dataSource, FEServicesMassiveChange feServicesMassiveChange) {
		
		OperationResults<FeServiceOperationResults> result = new OperationResults<FeServiceOperationResults>();
		
		//nodeId.communeKey.activity.subActivity._package
		
		FEServiceMassiveParametersModifiable feServiceMassiveParametersModifiable = feServicesMassiveChange.getFeServiceMassiveParametersModifiable();
		
		Iterator<String> selectedServicesIterator = feServicesMassiveChange.getSelectedServicesPackages().iterator();
		while(selectedServicesIterator.hasNext()) {
			String selectedService = selectedServicesIterator.next();
			if (logger.isDebugEnabled()) {
				logger.debug("Searching service data for selectd service '" + selectedService + "'...");
			}
			SelectedServiceTokens selectedServiceTokens = new SelectedServiceTokens(selectedService);
			
			FeServiceOperationResults feServiceOperationResults = new FeServiceOperationResults();
			feServiceOperationResults.setServiceName("");
			feServiceOperationResults.setServicePackage(selectedServiceTokens.get_package());
			feServiceOperationResults.setServiceActivity(selectedServiceTokens.getActivity());
			feServiceOperationResults.setServiceSubActivity(selectedServiceTokens.getSubActivity());
			
			Iterator<ExtendedAvailableService> extendedAvailableServiceIterator = feServicesMassiveChange.getAvailableServices().get(Long.parseLong(selectedServiceTokens.getNodeId())).iterator();
			FEInterface nodeFEInterface = null;
			feServiceOperationResults.addMessages("Inizio registrazione modifiche per i servizi del nodo " + selectedServiceTokens.getCommuneKey());
			while(extendedAvailableServiceIterator.hasNext()) {
				ExtendedAvailableService extendedAvailableService = extendedAvailableServiceIterator.next();
				if (nodeFEInterface == null) {
					try {
						nodeFEInterface = this.getFEInterface(extendedAvailableService.getReference());
					} catch (FeServiceReferenceException e) {
						e.printStackTrace();
						feServiceOperationResults.addErrors(new ObjectError("error", 
								this.getProperty("error.feservices.massiveChange.feInterface.error", 
										new String[] {selectedServiceTokens.getCommuneKey()})));
						break;
					}
				}
				if ((extendedAvailableService.getNodeId() + "." + extendedAvailableService.getCommuneKey() + "." + 
						extendedAvailableService.getActivity() + "." + extendedAvailableService.getSubActivity() + "." + 
						extendedAvailableService.get_package()).equalsIgnoreCase(selectedService)) {
					ServiceVO serviceVO = new ServiceVO();

					feServiceOperationResults.setServiceName(extendedAvailableService.getServiceName());
					feServiceOperationResults.addMessages("Registrazione modifiche per il servizio " + extendedAvailableService.getServiceName());
					
					serviceVO.setNome(extendedAvailableService.getServiceName());
					serviceVO.setLogLevel(feServiceMassiveParametersModifiable.getLogLevel());
					serviceVO.setStato(feServiceMassiveParametersModifiable.getServiceStatus());
					serviceVO.setSignEnabled(feServiceMassiveParametersModifiable.getProcessSignEnabled());
					serviceVO.setReceiptMailIncludeAttachment(feServiceMassiveParametersModifiable.getAttachmentsInCitizenReceipt());
					serviceVO.setSendmailtoowner(feServiceMassiveParametersModifiable.getSendmailtoowner().intValue());
					serviceVO.setCommuneId(extendedAvailableService.getCommuneKey());
					serviceVO.setServicePackage(extendedAvailableService.get_package());
					
					boolean noneActivationType = false;
					if (feServiceMassiveParametersModifiable.getProcessActivationType().getActivationType() != null) {
						DependentModule dependentModule = new DependentModule();
						dependentModule.setName(Constants.FEService.SUBMIT_PROCESS_ID);
						switch (feServiceMassiveParametersModifiable.getProcessActivationType().getActivationType()) {
							case webService:
								dependentModule.setMailAddress(Constants.FEService.SUBMIT_PROCESS_ADDRESS_UNDEFINED);
								break;
							case eMail:
								dependentModule.setMailAddress(feServiceMassiveParametersModifiable.getProcessActivationType().geteMailAddress());
								break;
							default: //notSupported process activation type
								noneActivationType = true;
								dependentModule.setMailAddress(Constants.FEService.SUBMIT_PROCESS_ADDRESS_UNDEFINED);
								break;
						}
						serviceVO.setDependentModules(new DependentModule[] {dependentModule});
					} else {
						serviceVO.setDependentModules(null);
					}

					try {
						nodeFEInterface.updateService(serviceVO);
						updateMassiveParametersLocalChanges(dataSource, extendedAvailableService, serviceVO, feServiceMassiveParametersModifiable, noneActivationType);
						feServiceOperationResults.addMessages("Servizio " + extendedAvailableService.getServiceName() + " correttamente modificato.");						
					} catch (RemoteException e) {
						feServiceOperationResults.addErrors(new ObjectError("error", 
								this.getProperty("error.feservices.massiveChange.serviceChange.error", 
										new String[] {extendedAvailableService.getServiceName(), selectedServiceTokens.getCommuneKey()})));
						e.printStackTrace();
					}
					break;
				}
			}
			result.addOperationResult(feServiceOperationResults);
		}
		
		return result;
		
	}

//	public OperationResults<FeServiceOperationResults> doMassiveDeletion(DataSource dataSource, FEServicesMassiveChange feServicesMassiveChange) {
//		
//		OperationResults<FeServiceOperationResults> result = new OperationResults<FeServiceOperationResults>();
//
//		FEInterface nodeFEInterface = null;
//		
//		Iterator<String> selectedServicesIterator = feServicesMassiveChange.getSelectedServicesPackages().iterator();
//		while(selectedServicesIterator.hasNext()) {
//			String selectedService = selectedServicesIterator.next();
//			if (logger.isDebugEnabled()) {
//				logger.debug("Searching service data for selectd service '" + selectedService + "'...");
//			}
//			FeServiceOperationResults feServiceOperationResults = new FeServiceOperationResults();
//			SelectedServiceTokens selectedServiceTokens = new SelectedServiceTokens(selectedService);
//			feServiceOperationResults.setServiceName("");
//			feServiceOperationResults.setServicePackage(selectedServiceTokens.get_package());
//			feServiceOperationResults.setServiceActivity(selectedServiceTokens.getActivity());
//			feServiceOperationResults.setServiceSubActivity(selectedServiceTokens.getSubActivity());
//			Iterator<ExtendedAvailableService> extendedAvailableServiceIterator = feServicesMassiveChange.getAvailableServices().get(Long.parseLong(selectedServiceTokens.getNodeId())).iterator();
//			feServiceOperationResults.addMessages("Inizio eliminazione per i servizi del nodo " + selectedServiceTokens.getCommuneKey());
//			while(extendedAvailableServiceIterator.hasNext()) {
//				ExtendedAvailableService extendedAvailableService = extendedAvailableServiceIterator.next();
//				if ((extendedAvailableService.getNodeId() + "." + extendedAvailableService.getCommuneKey() + "." + 
//						extendedAvailableService.getActivity() + "." + extendedAvailableService.getSubActivity() + "." + 
//						extendedAvailableService.get_package()).equalsIgnoreCase(selectedService)) {
//
//					feServiceOperationResults.setServiceName(extendedAvailableService.getServiceName());
//					
//					try {
//						if (nodeFEInterface == null) {
//							nodeFEInterface = this.getFEInterface(extendedAvailableService.getReference());
//						}
//						nodeFEInterface.deleteServiceByPackage(extendedAvailableService.getCommuneKey(), extendedAvailableService.get_package());
//						doMassiveLocalDeletion(dataSource, extendedAvailableService.getServiceId());
//						feServiceOperationResults.addMessages("Servizio " + extendedAvailableService.getServiceName() + " correttamente eliminato.");						
//					} catch (RemoteException e) {
//						feServiceOperationResults.addErrors(new ObjectError("error", 
//								this.getProperty("error.feservices.massiveChange.serviceDeletion.error", 
//										new String[] {extendedAvailableService.getServiceName(), selectedServiceTokens.getCommuneKey()})));
//						e.printStackTrace();
//					}
//					catch (FeServiceReferenceException e) {
//						e.printStackTrace();
//						feServiceOperationResults.addErrors(new ObjectError("error", 
//								this.getProperty("error.feservices.massiveChange.feInterface.error", 
//										new String[] {selectedServiceTokens.getCommuneKey()})));
//						break;
//					}					
//					break;
//				}
//			}
//			result.addOperationResult(feServiceOperationResults);
//		}
//		
//		return result;
//		
//	}

	public OperationResults<FeNodeOperationResults> doMassiveDeletion(DataSource dataSource, FEServicesMassiveChange feServicesMassiveChange) {
		
		OperationResults<FeNodeOperationResults> result = new OperationResults<FeNodeOperationResults>();

		Set<Long> selectedNodes = feServicesMassiveChange.getSelectedServices().keySet();
		Iterator<Long> selectedNodesIterator = selectedNodes.iterator();
		while(selectedNodesIterator.hasNext()) {
			FeNodeOperationResults feNodeOperationResults = new FeNodeOperationResults();
			Long nodeId = selectedNodesIterator.next();
			Iterator<ExtendedAvailableService> extendedAvailableServiceIterator = feServicesMassiveChange.getSelectedServices().get(nodeId).iterator();
			feNodeOperationResults.addMessages("Inizio eliminazione per i servizi del nodo " + feServicesMassiveChange.getSelectedServices().get(nodeId).get(0).getCommuneKey());
			FEInterface nodeFEInterface = null;
			CommunePackageVO[] communePackages = new CommunePackageVO[feServicesMassiveChange.getSelectedServices().get(nodeId).size()];
			int index = 0;
			while(extendedAvailableServiceIterator.hasNext()) {
				
				ExtendedAvailableService extendedAvailableService = extendedAvailableServiceIterator.next();

				try {
					if (nodeFEInterface == null) {
						nodeFEInterface = this.getFEInterface(extendedAvailableService.getReference());
						feNodeOperationResults.setId(String.valueOf(nodeId));
						feNodeOperationResults.setName(extendedAvailableService.getNodeName());
						feNodeOperationResults.setMunicipality(extendedAvailableService.getCommune());
						feNodeOperationResults.setMunicipalityCode(extendedAvailableService.getCommuneKey());
					}
				} catch (FeServiceReferenceException e) {
					e.printStackTrace();
					break;
				}					
				
				CommunePackageVO communePackageVO = new CommunePackageVO();
				communePackageVO.setServiceId(extendedAvailableService.getServiceId());
				communePackageVO.setCommuneId(extendedAvailableService.getCommuneKey());
				communePackageVO.setServicePackage(extendedAvailableService.get_package());
				communePackages[index] = communePackageVO;
				index++;
				
			}

			if (nodeFEInterface != null) {
				
				try {
					FeServiceChangeResult[] feServiceChangeResults = nodeFEInterface.deleteFeServicesByPackages(communePackages);
					
					List<ExtendedAvailableService> nodeSelectedServices = feServicesMassiveChange.getSelectedServices().get(nodeId);
					for(index = 0; index < feServiceChangeResults.length; index++) {
						FeServiceChangeResult feServiceChangeResult = feServiceChangeResults[index];
						ExtendedAvailableService extendedAvailableService = getSelectExtendedAvailableService(nodeSelectedServices, feServiceChangeResult.getServiceId());
						FeServiceOperationResults feServiceOperationResults = new FeServiceOperationResults();
						feServiceOperationResults.setServiceName(extendedAvailableService.getServiceName());
						feServiceOperationResults.setServicePackage(extendedAvailableService.get_package());
						feServiceOperationResults.setServiceActivity(extendedAvailableService.getActivity());
						feServiceOperationResults.setServiceSubActivity(extendedAvailableService.getSubActivity());
						if (!feServiceChangeResult.isError()) {
							boolean deleted = doMassiveLocalDeletion(dataSource, feServiceChangeResult.getServiceId());
							if (deleted) {
								feServiceOperationResults.addMessages("Servizio " + extendedAvailableService.getServiceName() + " correttamente eliminato.");						
							} else {
								feServiceOperationResults.addMessages("Il servizio " + extendedAvailableService.getServiceName() + " è stato eliminato, ma non è stato possibile eliminare i servizi di back end associati.");						
							}
						} else {
							feServiceOperationResults.addMessages("Si è verificato un problema ed il servizio " + extendedAvailableService.getServiceName() + " non è stato eliminato.");						
						}
						feNodeOperationResults.addFeServiceOperationResults(feServiceOperationResults);
					}
					
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				
			} else {
				feNodeOperationResults.addErrors(new ObjectError("error", 
				this.getProperty("error.feservices.massiveChange.feInterface.error", 
						new String[] {""})));
			}
			result.addOperationResult(feNodeOperationResults);
		}
					
		return result;
		
	}
	
	/**
	 * @param feServicesMassiveChange
	 */
	public void prepareCompleteServicesList(FEServicesMassiveChange feServicesMassiveChange) {
		
		feServicesMassiveChange.clearSelectedServices();
		
		Iterator<Long> availableServicesNodesIterator = feServicesMassiveChange.getAvailableServices().keySet().iterator();
		while(availableServicesNodesIterator.hasNext()) {
			List<ExtendedAvailableService> nodeExtendedServices = feServicesMassiveChange.getAvailableServices().get(availableServicesNodesIterator.next());
			Iterator<ExtendedAvailableService> nodeExtendedServicesIterator = nodeExtendedServices.iterator();
			while(nodeExtendedServicesIterator.hasNext()) {
				ExtendedAvailableService extendedAvailableService = nodeExtendedServicesIterator.next();
				String serviceId = extendedAvailableService.getNodeId() + "." + extendedAvailableService.getCommuneKey() + 
					"." + extendedAvailableService.getActivity() + "." + extendedAvailableService.getSubActivity() + "." + 
					extendedAvailableService.get_package();
				if (feServicesMassiveChange.getSelectedServicesPackages().contains(serviceId)) {
					feServicesMassiveChange.addSelectedService(extendedAvailableService);
				}
			}
		}
		
		// Verifica se ci sono nodi selezionati
		// Se ci sono nodi selezionati aggiungi a selectedServices tutti i servizi che non sono già in selectedservices
		if (feServicesMassiveChange.getSelectedNodes() != null && !feServicesMassiveChange.getSelectedNodes().isEmpty()) {
			Iterator<String> selectedNodesIterator = feServicesMassiveChange.getSelectedNodes().iterator();
			while(selectedNodesIterator.hasNext()) {
				String selectedNode = selectedNodesIterator.next();
				List<ExtendedAvailableService> nodeExtendedServices = feServicesMassiveChange.getAvailableServices().get(Long.parseLong(selectedNode));
				Iterator<ExtendedAvailableService> nodeExtendedServicesIterator = nodeExtendedServices.iterator();
				while(nodeExtendedServicesIterator.hasNext()) {
					ExtendedAvailableService extendedAvailableService = nodeExtendedServicesIterator.next();
					String serviceId = extendedAvailableService.getNodeId() + "." + extendedAvailableService.getCommuneKey() + 
						"." + extendedAvailableService.getActivity() + "." + extendedAvailableService.getSubActivity() + "." + 
						extendedAvailableService.get_package();
					if (!feServicesMassiveChange.getSelectedServicesPackages().contains(serviceId)) {
						feServicesMassiveChange.getSelectedServicesPackages().add(serviceId);
						feServicesMassiveChange.addSelectedService(extendedAvailableService);
					}
				}
			}
		}
		
		//Verifica se ci sono aree appartenenti a nodi non selezionati
		//Se ci sono aggiungi a selectedServices tutti i servizi che non sono già in selectedservices
		if (!feServicesMassiveChange.getSelectedAreas().isEmpty()) {
			Iterator<String> selectedAreasIterator = feServicesMassiveChange.getSelectedAreas().iterator();
			while(selectedAreasIterator.hasNext()) {
				String selectedArea = selectedAreasIterator.next();
				String areaNodeId = getAreaNodeId(selectedArea);
				String area = getArea(selectedArea);
				if (feServicesMassiveChange.getSelectedNodes() == null || (feServicesMassiveChange.getSelectedNodes() != null && 
						(!feServicesMassiveChange.getSelectedNodes().contains(areaNodeId) || feServicesMassiveChange.getSelectedNodes().isEmpty()))) {
					List<ExtendedAvailableService> nodeExtendedServices = feServicesMassiveChange.getAvailableServices().get(Long.parseLong(areaNodeId));
					Iterator<ExtendedAvailableService> nodeExtendedServicesIterator = nodeExtendedServices.iterator();
					while(nodeExtendedServicesIterator.hasNext()) {
						ExtendedAvailableService extendedAvailableService = nodeExtendedServicesIterator.next();
						if (extendedAvailableService.getActivity().equalsIgnoreCase(area)) {
							String serviceId = extendedAvailableService.getNodeId() + "." + extendedAvailableService.getCommuneKey() + 
								"." + extendedAvailableService.getActivity() + "." + extendedAvailableService.getSubActivity() + "." + 
								extendedAvailableService.get_package();
							if (!feServicesMassiveChange.getSelectedServicesPackages().contains(serviceId)) {
								feServicesMassiveChange.getSelectedServicesPackages().add(serviceId);
								feServicesMassiveChange.addSelectedService(extendedAvailableService);
							}
						}
					}
				}
			}
		}
		
	}
	
	/**
	 * @param dataSource
	 * @param serviceId
	 * @return
	 */
	private boolean doMassiveLocalDeletion(DataSource dataSource, long serviceId) {

		boolean result = true;
		PreparedStatement preparedStatement = null;
		PreparedStatement deleteBeServicesPreparedStatement = null;		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		boolean autoCommit = true;

		String feServiceNodeIdQuery = "select nodeid from service where id = ?";
		
		String associatedBeServicesQuery = new StringBuilder().append("select count(svc.id), ref.value from service svc ")
			.append("join reference ref on ref.serviceid = svc.id ")
			.append("where svc.nodeid = (select svc1.nodeid from service svc1 where svc1.id = ?) and ref.value in (select ")
			.append("ref1.value from reference ref1 where ref1.serviceid = ?) ")
			.append("group by 2").toString();
		
		String deleteBeServiceQuery = "delete from benode where nodobe = ? and nodeid = ?";
		
		try {
			// Elimina il servizio
			connection = dataSource.getConnection();
			autoCommit = connection.getAutoCommit();
			connection.setAutoCommit(false);
			statement = connection.createStatement();

			// Recupera il nodo a cui è associato il servizio
			preparedStatement = connection.prepareStatement(feServiceNodeIdQuery);
			preparedStatement.setInt(1, (new Long(serviceId)).intValue());
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int serviceNodeId = resultSet.getInt(1);
				
				// Verifica se ci sono servizi di back end usati solo da questo servizio
				preparedStatement = connection.prepareStatement(associatedBeServicesQuery);
				preparedStatement.setInt(1, (new Long(serviceId)).intValue());
				preparedStatement.setInt(2, (new Long(serviceId)).intValue());
				resultSet = preparedStatement.executeQuery();
				int beServicesToDelete = 0;
				while(resultSet.next()) {
					if (resultSet.getInt(1) == 1) {
						if (deleteBeServicesPreparedStatement == null) {
							deleteBeServicesPreparedStatement = connection.prepareStatement(deleteBeServiceQuery);
						}
						deleteBeServicesPreparedStatement.setString(1, resultSet.getString(2));
						deleteBeServicesPreparedStatement.setInt(2, serviceNodeId);
						deleteBeServicesPreparedStatement.addBatch();
						beServicesToDelete++;
					}
				}
				int[] deletedBeServices = new int[0];
				if (deleteBeServicesPreparedStatement != null) {
					deletedBeServices = deleteBeServicesPreparedStatement.executeBatch();
				}
				if (beServicesToDelete == deletedBeServices.length) {
					logger.info("Deleted " + deletedBeServices.length + " be services for fe service " + serviceId + ".");
				} else {
					logger.info("Error while deleting be services for fe service " + serviceId + ".");
				}
			}
			
			// configuration
			statement.addBatch("DELETE FROM configuration WHERE serviceid = " + serviceId);

			// reference
			statement.addBatch("DELETE FROM reference WHERE serviceid = " + serviceId);

			// service
			statement.addBatch("DELETE FROM service WHERE id = " + serviceId);
			
			statement.executeBatch();

			connection.commit();
			connection.setAutoCommit(autoCommit);
		} catch (SQLException ex) {
			try {
				connection.rollback();
				connection.setAutoCommit(autoCommit);
			} catch (SQLException rollbackEx) {
				logger.error("Impossibile effettuare il roll-back");
			}
			result = false;
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
				if (deleteBeServicesPreparedStatement != null) {
					deleteBeServicesPreparedStatement.close();
				}
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
	
	/**
	 * @param extendedAvailableService
	 * @param serviceVO
	 * @param feServiceMassiveParametersModifiable
	 * @param noneActivationType
	 */
	private boolean updateMassiveParametersLocalChanges(DataSource dataSource, ExtendedAvailableService extendedAvailableService, ServiceVO serviceVO, 
			FEServiceMassiveParametersModifiable feServiceMassiveParametersModifiable, boolean noneActivationType) {

		boolean result = true;
    	String updateServiceLocal = "update service set nome = ?, loglevel = ?, stato = ?, signenabled = ?, receiptmailattachment = ?, sendmailtoowner = ? where id = ?";
    	String updateReferenceLocal = "update reference set address = ? where serviceid = ? and name = ?";
    	String updateReferenceLocalNone = "update reference set address = ?, value = '' where serviceid = ? and name = ?";

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			
			//Update local db
			preparedStatement = connection.prepareStatement(updateServiceLocal);
			preparedStatement.setString(1, extendedAvailableService.getServiceName());
			preparedStatement.setInt(2, feServiceMassiveParametersModifiable.getLogLevel());
			preparedStatement.setInt(3, feServiceMassiveParametersModifiable.getServiceStatus());
			preparedStatement.setInt(4, feServiceMassiveParametersModifiable.getProcessSignEnabled());
			preparedStatement.setInt(5, feServiceMassiveParametersModifiable.getAttachmentsInCitizenReceipt());
			preparedStatement.setInt(6, feServiceMassiveParametersModifiable.getSendmailtoowner());
			preparedStatement.setLong(7, extendedAvailableService.getServiceId());
			preparedStatement.executeUpdate();

			if (serviceVO.getDependentModules() != null) {
				preparedStatement = connection.prepareStatement(noneActivationType ? updateReferenceLocalNone : updateReferenceLocal);
				preparedStatement.setString(1, serviceVO.getDependentModules()[0].getMailAddress());
				preparedStatement.setLong(2, extendedAvailableService.getServiceId());
				preparedStatement.setString(3, Constants.FEService.SUBMIT_PROCESS_ID);
				preparedStatement.executeUpdate();
			}
			
		} catch(SQLException e) {
			logger.error("Update service error:", e);
			result = false;
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
			} catch(Exception ignore) {
				
			}
		}
		
		return result;
		
	}
	
	/**
	 * @param selectedArea
	 * @return
	 */
	private String getAreaNodeId(String selectedArea) {
		
		String result = null;
		
		int firstDotPosition = selectedArea.indexOf('.');
		if(firstDotPosition > 0) {
			result = selectedArea.substring(0, firstDotPosition);
		}
		
		return result;
		
	}

	/**
	 * @param selectedArea
	 * @return
	 */
	private String getArea(String selectedArea) {
		
		String result = null;
		
		int lastDotPosition = selectedArea.lastIndexOf('.');
		if(lastDotPosition > 0) {
			result = selectedArea.substring(lastDotPosition + 1);
		}
		
		return result;
		
	}

	protected class SelectedServiceTokens extends AbstractLogger {
		
		private String nodeId;
		
		private String communeKey;
		
		private String activity;
		
		private String subActivity;
		
		private String _package;
		
		public SelectedServiceTokens(final String selectedService) {
			this.setNodeId("");
			this.setCommuneKey("");
			this.setActivity("");
			this.setSubActivity("");
			this.set_package("");
			this.tokenizeService(selectedService);
		}

		/**
		 * @param nodeId the nodeId to set
		 */
		private void setNodeId(String nodeId) {
			this.nodeId = nodeId;
		}

		/**
		 * @param communeKey the communeKey to set
		 */
		private void setCommuneKey(String communeKey) {
			this.communeKey = communeKey;
		}

		/**
		 * @param activity the activity to set
		 */
		private void setActivity(String activity) {
			this.activity = activity;
		}

		/**
		 * @param subActivity the subActivity to set
		 */
		private void setSubActivity(String subActivity) {
			this.subActivity = subActivity;
		}

		/**
		 * @param _package the _package to set
		 */
		private void set_package(String _package) {
			this._package = _package;
		}

		/**
		 * @return the nodeId
		 */
		public final String getNodeId() {
			return this.nodeId;
		}

		/**
		 * @return the communeKey
		 */
		public final String getCommuneKey() {
			return this.communeKey;
		}

		/**
		 * @return the activity
		 */
		public final String getActivity() {
			return this.activity;
		}

		/**
		 * @return the subActivity
		 */
		public final String getSubActivity() {
			return this.subActivity;
		}

		/**
		 * @return the _package
		 */
		public final String get_package() {
			return this._package;
		}
		
		private void tokenizeService(String selectedService) {
			
			//nodeId.communeKey.activity.subActivity._package
			
			int firstDotPosition = selectedService.indexOf('.');
			int secondDotPosition = selectedService.substring(firstDotPosition + 1).indexOf('.') + firstDotPosition + 1;
			int thirdDotPosition = selectedService.substring(secondDotPosition + 1).indexOf('.') + secondDotPosition + 1;
			int fourthDotPosition = selectedService.substring(thirdDotPosition + 1).indexOf('.') + thirdDotPosition + 1;
						
			String nodeId = selectedService.substring(0, firstDotPosition);
			String communeKey = selectedService.substring(firstDotPosition + 1, secondDotPosition);
			String activity = selectedService.substring(secondDotPosition + 1, thirdDotPosition);
			String subActivity = selectedService.substring(thirdDotPosition + 1, fourthDotPosition);
			String _package = selectedService.substring(fourthDotPosition + 1);

			if (logger.isDebugEnabled()) {
				logger.debug("Selected service = " + selectedService);
				logger.debug("First dot position = " + firstDotPosition);
				logger.debug("Second dot position = " + secondDotPosition);
				logger.debug("Third dot position = " + thirdDotPosition);
				logger.debug("Fourth dot position = " + fourthDotPosition);
				logger.debug("Tokenized data:");
				logger.debug("nodeId = " + nodeId);
				logger.debug("communeKey = " + communeKey);
				logger.debug("activity = " + activity);
				logger.debug("subActivity = " + subActivity);
				logger.debug("_package = " + _package);
			}
		
			this.setNodeId(nodeId);
			this.setCommuneKey(communeKey);
			this.setActivity(activity);
			this.setSubActivity(subActivity);
			this.set_package(_package);
			
		}
		
	}

	private ExtendedAvailableService getSelectExtendedAvailableService(List<ExtendedAvailableService> nodeSelectedServices, Long serviceId) {
		
		ExtendedAvailableService result = null;
		
		Iterator<ExtendedAvailableService> nodeSelectedServicesIterator = nodeSelectedServices.iterator();
		while(nodeSelectedServicesIterator.hasNext()) {
			ExtendedAvailableService nodeSelectedService = nodeSelectedServicesIterator.next();
			if (new Long(nodeSelectedService.getServiceId()).compareTo(serviceId) == 0) {
				result = nodeSelectedService;
				break;
			}
		}
		
		return result;
		
	}
	
}
