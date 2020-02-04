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

import java.rmi.RemoteException;

import it.people.feservice.beans.FeServiceChangeResult;
import it.people.feservice.beans.IndicatorsVO;
import it.people.feservice.beans.PeopleAdministratorVO;
import it.people.feservice.beans.ProcessFilter;
import it.people.feservice.beans.ProcessesDeletionResultVO;
import it.people.feservice.beans.ProcessesVO;
import it.people.feservice.beans.ServiceOnlineHelpWorkflowElements;
import it.people.feservice.beans.UserNotificationDataVO;
import it.people.feservice.beans.VelocityTemplateDataVO;


public interface FEInterface extends java.rmi.Remote {
	
    /**
     * @param word
     * @return
     * @throws java.rmi.RemoteException
     */
    public java.lang.String echo(java.lang.String word) throws java.rmi.RemoteException;
    
    /**
     * @param comune
     * @param codice
     * @param descrizione
     * @param announcementMessage
     * @param showAnnouncement
     * @throws java.rmi.RemoteException
     */
    public void registerNode(java.lang.String comune, java.lang.String codice, java.lang.String descrizione, java.lang.String announcementMessage, 
    		java.lang.Boolean showAnnouncement, java.lang.Boolean onlineSign, java.lang.Boolean offlineSign) throws java.rmi.RemoteException;
    
    /**
     * @param comune
     * @param codice
     * @param descrizione
     * @param aooPrefix
     * @param announcementMessage
     * @param showAnnouncement
     * @throws java.rmi.RemoteException
     */
    public void registerNodeWithAoo(java.lang.String comune, java.lang.String codice, java.lang.String descrizione, java.lang.String aooPrefix, 
    		java.lang.String announcementMessage, java.lang.Boolean showAnnouncement, java.lang.Boolean onlineSign, java.lang.Boolean offlineSign) throws java.rmi.RemoteException;
    
    /**
     * @param communeId
     * @param packageName
     * @return
     * @throws java.rmi.RemoteException
     */
    public it.people.feservice.beans.ServiceVO registerService(java.lang.String communeId, java.lang.String packageName) throws java.rmi.RemoteException;
    
    /**
     * @param theService
     * @throws java.rmi.RemoteException
     */
    public void configureService(it.people.feservice.beans.ServiceVO theService) throws java.rmi.RemoteException;
    
    /**
     * @param parameterVO
     * @throws java.rmi.RemoteException
     */
    public void configureServiceParameter(it.people.feservice.beans.ConfigParameterVO parameterVO) throws java.rmi.RemoteException;
    
    /**
     * @param referenceVO
     * @throws java.rmi.RemoteException
     */
    public void configureServiceReference(it.people.feservice.beans.DependentModuleVO referenceVO) throws java.rmi.RemoteException;
    
    /**
     * @param communeId
     * @return
     * @throws java.rmi.RemoteException
     */
    public it.people.feservice.beans.LogBean[] getAllLogs(java.lang.String communeId) throws java.rmi.RemoteException;
    
    /**
     * @param communeId
     * @param serviceName
     * @param logLevel
     * @return
     * @throws java.rmi.RemoteException
     */
    public it.people.feservice.beans.LogBean[] getLogsForService(java.lang.String communeId, java.lang.String serviceName, java.lang.String logLevel) throws java.rmi.RemoteException;
    
    /**
     * @param communeId
     * @param from
     * @param to
     * @return
     * @throws java.rmi.RemoteException
     */
    public it.people.feservice.beans.LogBean[] getLogsForDate(java.lang.String communeId, java.util.Calendar from, java.util.Calendar to) throws java.rmi.RemoteException;
    
    /**
     * @param communeId
     * @param serviceName
     * @param logLevel
     * @param from
     * @param to
     * @return
     * @throws java.rmi.RemoteException
     */
    public it.people.feservice.beans.LogBean[] getLogsForDateAndService(java.lang.String communeId, java.lang.String serviceName, java.lang.String logLevel, java.util.Calendar from, java.util.Calendar to) throws java.rmi.RemoteException;
    
    /**
     * @param communeId
     * @param serviceName
     * @param logLevel
     * @param from
     * @param to
     * @param orderBy
     * @param orderType
     * @return
     * @throws java.rmi.RemoteException
     */
    public it.people.feservice.beans.LogBean[] getOrderedLogs(java.lang.String communeId, java.lang.String serviceName, java.lang.String logLevel, java.util.Calendar from, java.util.Calendar to, java.lang.String orderBy, java.lang.String orderType) throws java.rmi.RemoteException;
    
    /**
     * @param communeId
     * @param startingPoint
     * @param duration
     * @return
     * @throws java.rmi.RemoteException
     */
    public it.people.feservice.beans.AuditConversationsBean[] getAllAuditConversationsForComune(java.lang.String communeId, java.lang.String startingPoint, java.lang.String duration) throws java.rmi.RemoteException;
    
    /**
     * @param communeId
     * @param taxCode
     * @param processName
     * @param from
     * @param to
     * @param startingPoint
     * @param duration
     * @return
     * @throws java.rmi.RemoteException
     */
    public it.people.feservice.beans.AuditConversationsBean[] getAuditConversationsForAllParameters(java.lang.String communeId, java.lang.String taxCode, java.lang.String processName, java.util.Calendar from, java.util.Calendar to, java.lang.String startingPoint, java.lang.String duration) throws java.rmi.RemoteException;
	
    /**
     * @param communeId
     * @param taxCode
     * @param processName
     * @param startingPoint
     * @param duration
     * @return
     * @throws java.rmi.RemoteException
     */
    public it.people.feservice.beans.AuditConversationsBean[] getAuditConversationsForService(java.lang.String communeId, java.lang.String taxCode, java.lang.String processName, java.lang.String startingPoint, java.lang.String duration) throws java.rmi.RemoteException;
    
    /**
     * @param communeId
     * @param from
     * @param to
     * @param startingPoint
     * @param duration
     * @return
     * @throws java.rmi.RemoteException
     */
    public it.people.feservice.beans.AuditConversationsBean[] getAuditConversationsForDate(java.lang.String communeId, java.util.Calendar from, java.util.Calendar to, java.lang.String startingPoint, java.lang.String duration) throws java.rmi.RemoteException;
    
    /**
     * @param communeId
     * @return
     * @throws java.rmi.RemoteException
     */
    public java.lang.String[] getAuditUsersForComune(java.lang.String communeId) throws java.rmi.RemoteException;
    
    /**
     * @param userId
     * @param userAccrId
     * @return
     * @throws java.rmi.RemoteException
     */
    public it.people.feservice.beans.AuditUserBean getAuditUser(java.lang.String userId, java.lang.String userAccrId) throws java.rmi.RemoteException;
    
    /**
     * @param id
     * @return
     * @throws java.rmi.RemoteException
     */
    public it.people.feservice.beans.AuditConversationsBean getAuditConversation(java.lang.String id) throws java.rmi.RemoteException;
	
    /**
     * @param id
     * @return
     * @throws java.rmi.RemoteException
     */
    public it.people.feservice.beans.AuditFeBeXmlBean getAuditFeBeXml(java.lang.String id) throws java.rmi.RemoteException;

    /**
     * <p>Delete a front end service and all it's related data.
     * <p>First search for the service by package name and if the service is found delete all the service data from the following tables:
     * <ul>
     * <li>configuration</li>
     * <li>connected_services</li>
     * <li>context_elements</li>
     * <li>reference</li>
     * <li>log_messages</li>
     * <li>service</li>
     * </ul>
     * 
     * @param communeId
     * @param packageName
     * @return
     * @throws java.rmi.RemoteException
     */
    public boolean deleteServiceByPackage(java.lang.String communeId, java.lang.String packageName) throws java.rmi.RemoteException;
    
    /**
     * @param communeId
     * @return
     * @throws java.rmi.RemoteException
     */
    public boolean deleteAllServices(java.lang.String communeId) throws java.rmi.RemoteException;
    
    /**
     * @param communeId
     * @return
     * @throws java.rmi.RemoteException
     */
    public it.people.feservice.beans.NodeDeployedServices getNodeDeployedServices(String communeId) throws java.rmi.RemoteException;
    
	/**
	 * @param servicePackage
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public ServiceOnlineHelpWorkflowElements getServiceOnlineHelpWorkflowElements(String servicePackage) throws java.rmi.RemoteException;

    /**
     * @param selectedServices
     * @param areasLogicalNamesPrefix
     * @param areasLogicalNamesSuffix
     * @param servicesLogicalNamesPrefix
     * @param servicesLogicalNamesSuffix
     * @param fromCommuneId
     * @param toCommuneId
     * @return
     * @throws java.rmi.RemoteException
     */
    public boolean nodeCopy(String[] selectedServices, String[] areasLogicalNamesPrefix,
    		String[] areasLogicalNamesSuffix, String[] servicesLogicalNamesPrefix,
    		String[] servicesLogicalNamesSuffix, String fromCommuneId, String toCommuneId) throws java.rmi.RemoteException;
    
    /**
     * @param query
     * @return
     * @throws java.rmi.RemoteException
     */
    public it.people.feservice.beans.AuditStatisticheBean[] getStatistiche(java.lang.String query) throws java.rmi.RemoteException;
    
    /**
     * @param query
     * @param queryCount
     * @return
     * @throws java.rmi.RemoteException
     */
    public it.people.feservice.beans.AuditConversationsBean[] getAuditConversations(java.lang.String query, java.lang.String queryCount) throws java.rmi.RemoteException;

    /**
     * @param theService
     * @throws java.rmi.RemoteException
     */
    public void updateService(it.people.feservice.beans.ServiceVO theService) throws java.rmi.RemoteException;
    
    /**
     * @param bundle
     * @param nodeId
     * @param locale
     * @param key
     * @param value
     * @param active
     * @param group
     * @throws java.rmi.RemoteException
     */
    public void updateBundle(java.lang.String bundle, java.lang.String nodeId, java.lang.String locale, 
    		java.lang.String key, java.lang.String value, 
    		java.lang.String active, java.lang.String group) throws java.rmi.RemoteException;

    /**
     * @param bundle
     * @param nodeId
     * @param locale
     * @param active
     * @param group
     * @throws java.rmi.RemoteException
     */
    public void registerBundle(java.lang.String bundle, java.lang.String nodeId, java.lang.String locale, 
    		java.lang.String active, java.lang.String group) throws java.rmi.RemoteException;

    /**
     * @param bundle
     * @param nodeId
     * @param locale
     * @throws java.rmi.RemoteException
     */
    public void deleteBundle(java.lang.String bundle, java.lang.String nodeId, java.lang.String locale) throws java.rmi.RemoteException;
    
    /**
     * @param services
     * @throws java.rmi.RemoteException
     */
    public void updateFeServices(it.people.feservice.beans.ServiceVO[] services) throws java.rmi.RemoteException;
    
    /**
     * @param servicesCommunePackage
     * @return
     * @throws java.rmi.RemoteException
     */
    public FeServiceChangeResult[] deleteFeServicesByPackages(it.people.feservice.beans.CommunePackageVO[] servicesCommunePackage) throws java.rmi.RemoteException;

    /**
     * @param servicesReferences
     * @return
     * @throws java.rmi.RemoteException
     */
    public boolean deleteBeServicesReferencesByPackages(it.people.feservice.beans.FEServiceReferenceVO[] servicesReferences) throws java.rmi.RemoteException;

//    public boolean updatePeopleAdminUser()
    
    
    /**
     * @param tableValueProperty
     * @throws java.rmi.RemoteException
     */
    public void configureTableValueProperty(it.people.feservice.beans.TableValuePropertyVO tableValueProperty) throws java.rmi.RemoteException;
    
    
    /**
     * 
     * @param serviceAuditProcessor
     * @throws java.rmi.RemoteException
     */
    public void configureServiceAuditProcessor(it.people.feservice.beans.ServiceAuditProcessorVO serviceAuditProcessor) throws java.rmi.RemoteException;
    
    
    /**
     * 
     * @param indicatorFilter
     * @param lowerPageLimit
     * @param pageSize
     * @return
     * @throws java.rmi.RemoteException
     */
	public IndicatorsVO getMonitoringIndicators(
			it.people.feservice.beans.IndicatorFilter indicatorFilter,
			int lowerPageLimit, int pageSize,
			String[] selectedEnti, String[] selectedAttivita, boolean retrieveAll) throws java.rmi.RemoteException;

	/**
	 * @param userId
	 * @param allowedCommune
	 * @param mailReceiverTypeFlags
	 * @throws java.rmi.RemoteException
	 */
	public void setAsPeopleAdministrator(java.lang.String userId, java.lang.String eMail, java.lang.String userName, java.lang.String[] allowedCommune, java.lang.String mailReceiverTypeFlags) throws java.rmi.RemoteException;

	/**
	 * @param userId
	 * @throws java.rmi.RemoteException
	 */
	public void removeFromPeopleAdministrator(java.lang.String userId) throws java.rmi.RemoteException;

	/**
	 * @param userId
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public PeopleAdministratorVO getPeopleAdministrator(java.lang.String userId) throws java.rmi.RemoteException;
	
	/**
	 * Retrieve processes also known as "Pratiche"
	 * 
	 * @param processFilter filter for processes 
	 * @param lowerPageLimit pagination settings, get processes starting from this lower limit 
	 * @param pageSize pagination settings, page size
	 * @param selectedUsers
	 * @param retrieveAll if true returns all the processes bypassing pagination settings
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public ProcessesVO getProcesses(it.people.feservice.beans.ProcessFilter processFilter,
			int lowerPageLimit, int pageSize, String[] selectedUsers, String[] selectedNodes, 
			boolean retrieveAll) throws java.rmi.RemoteException;

	
	/**
	 * Retrieve all users that submitted processes (also known as "Pratiche")
	 * 
	 * @return
	 * @throws java.rmi.RemoteException
	 * 
	 */
	public String[] getProcessUsers() throws java.rmi.RemoteException;


	/**
	 * 
	 * @param processFilter filter for processes
	 * @param selectedUsers selected users to filter deletion
	 * @param selectedNodes selected nodes to filter deletion
	 * @param archiveInFile if true, archive the deleted processes in a file as sql dump
	 * @return
	 * @throws RemoteException
	 */
	public ProcessesDeletionResultVO deleteProcesses(ProcessFilter processFilter,
			String[] selectedUsers, String[] selectedNodes,
			boolean archiveInFile) throws RemoteException;
	
	
	/**
	 * 
	 * @param communeId
	 * @param servicePackage
	 * @param retrieveAll
	 * @return
	 * @throws RemoteException
	 */
	public VelocityTemplateDataVO getVelocityTemplatesData(String communeId, String servicePackage, boolean retrieveAll) throws RemoteException;
	
	
	/**
	 * Create, Update and Delete templates
	 * 
	 * @param templateDataVO
	 * @return
	 * @throws RemoteException
	 */
	public boolean updateVelocityTemplatesData(VelocityTemplateDataVO templateDataVO, boolean delete) throws RemoteException;

/**
	 * 
	 * @author gguidi - Jun 19, 2013
	 *
	 * @param type (ERRORE|SUGGERIMENTO)
	 * @param userId eventuale filtro
	 * @param communeId eventuale filtro
	 * @param from eventuale filtro
	 * @param to eventuale filtro
	 * @return
	 * @throws RemoteException
	 */
	public UserNotificationDataVO getUserNotifications(ProcessFilter processFilter, int lowerPageLimit, int pageSize, String type, String userId, String firstname, String lastname, String email, String communeId, java.util.Calendar from, java.util.Calendar to, String sortType, String sortColumn) throws RemoteException;
		
	
}
