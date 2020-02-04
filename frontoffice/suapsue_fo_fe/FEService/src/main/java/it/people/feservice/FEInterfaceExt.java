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

import it.people.feservice.beans.NodeDeployedServices;
import it.people.feservice.beans.PeopleAdministratorVO;
import it.people.feservice.beans.ServiceOnlineHelpWorkflowElements;
import it.people.feservice.beans.VelocityTemplateDataVO;


public interface FEInterfaceExt {

	public NodeDeployedServices getNodeDeployedServices(String basePath) throws it.people.feservice.exceptions.FEInterfaceExtException;

	public boolean nodeCopy(String[] selectedServices, String[] areasLogicalNamesPrefix,
    		String[] areasLogicalNamesSuffix, String[] servicesLogicalNamesPrefix,
    		String[] servicesLogicalNamesSuffix, String fromCommuneId, String toCommuneId) throws java.rmi.RemoteException;
	
    public ServiceOnlineHelpWorkflowElements getServiceOnlineHelpWorkflowElements(String servicePackage, String basePath) throws it.people.feservice.exceptions.FEInterfaceExtException;

    public void updateBundle(java.lang.String bundle, java.lang.String nodeId, java.lang.String locale, 
    		java.lang.String key, java.lang.String value, 
    		java.lang.String active, java.lang.String group) throws java.rmi.RemoteException;

    public void registerBundle(java.lang.String bundle, java.lang.String nodeId, java.lang.String locale, 
    		java.lang.String active, java.lang.String group) throws java.rmi.RemoteException;

    public void deleteBundle(java.lang.String bundle, java.lang.String nodeId, java.lang.String locale) throws java.rmi.RemoteException;
    
    public boolean deleteBeServicesReferencesByPackages(it.people.feservice.beans.FEServiceReferenceVO[] servicesReferences) throws java.rmi.RemoteException;
    
	public boolean isDebug();
	
	public void setDebug(final boolean isDebug);
	
	public void configureTableValueProperty(it.people.feservice.beans.TableValuePropertyVO tableValueProperty) throws java.rmi.RemoteException;
	
	public void setAsPeopleAdministrator(java.lang.String userId, java.lang.String eMail, java.lang.String userName, java.lang.String[] allowedCommune, java.lang.String mailReceiverTypeFlags) throws java.rmi.RemoteException;

	public PeopleAdministratorVO getPeopleAdministrator(java.lang.String userId) throws java.rmi.RemoteException;
	
	public void removeFromPeopleAdministrator(java.lang.String userId) throws java.rmi.RemoteException;
	
	public VelocityTemplateDataVO getVelocityTemplatesData(String communeId, String servicePackage, boolean retrieveAll) throws java.rmi.RemoteException;

	public boolean updateVelocityTemplatesData(VelocityTemplateDataVO templateDataVO, boolean delete) throws java.rmi.RemoteException;
}
