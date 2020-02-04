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
package it.people.console.persistence;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import it.people.console.beans.Option;
import it.people.console.domain.BEService;
import it.people.console.domain.PairElement;
import it.people.console.domain.TripleElement;
import it.people.console.dto.BEServiceDTO;
import it.people.console.dto.ExtendedAvailableService;
import it.people.console.dto.FENodeDTO;
import it.people.console.dto.UserAccountDTO;
import it.people.console.persistence.exceptions.PersistenceBrokerException;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 13/lug/2011 11.08.30
 *
 */
public interface IPersistenceBroker {

	public Collection<TripleElement<String, String, String>> getAllRoles(String query);
	
	public Collection<PairElement<String, String>> getRegisteredNodes();

	public Collection<String> getRegisteredNodesCodes();
	
	public Collection<PairElement<String, String>> getRegisteredFEServices();

	public Collection<PairElement<String, String>> getRegisteredBEServices();
	
	public boolean accountExists(String taxCode);
	
	public void deleteAccount(int userId) throws PersistenceBrokerException;
	
	public UserAccountDTO getAccountData(int userId);
	
	public void saveUserCertificate(int userRef, String alias, int validity, String base64Certificate, int sentMail);

	public void deleteUserCertificate(int userRef) throws PersistenceBrokerException;
	
	public int updateConfiguration(String key, String value) throws PersistenceBrokerException;
	
	public Map<Integer, FENodeDTO> getRegisteredNodesWithBEServices() throws PersistenceBrokerException;
	
	public Map<Integer, BEServiceDTO> getRegisteredBEServicesAllData() throws PersistenceBrokerException;

	public Map<Integer, BEServiceDTO> getNodeRegisteredBEServicesAllData(int[] nodesList) throws PersistenceBrokerException;

	public boolean isRegisteredBeNode(String beLogicalName, int nodeId) throws PersistenceBrokerException;
	
	public List<Option> getFrameworkLocales();

	public List<Option> getFrameworkRegisterableLocales();
	
	public List<Option> getFEServicesRegisteredPackages();

	public List<Option> getServiceLocales(String servicePackage);

	public List<Option> getServiceRegisterableLocales(String servicePackage);

	public List<Option> getServiceLocalesByNodeId(String servicePackage, String nodeId);

	public List<Option> getServiceRegisterableLocalesByNodeId(String servicePackage, String nodeId);
	
	public Map<String, Integer> loadPagedListHoldersPreferences(int userId);
	
	public List<Option> getServiceTableValuesTableId(String servicePackage, String comuneId);
	
	public void updatePagedListHoldersPreferences(int userId, Map<String, Integer> preferences);
	
	public List<TripleElement<String, String, String>> getFeNodesList();
	
	public Map<Long, List<ExtendedAvailableService>> getFeNodesAvailableServices(String[] feNodesIds);

	public Map<Long, List<BEService>> getNodesOrphanedBeServices(String[] feNodesIds);

	public Map<Long, List<BEService>> getAllNodesBeServices(String[] feNodesIds);
	
	public boolean registerBundle(String bundle, String nodeId, String locale, String active, String group);
	
	public long getServiceMessagesBundleRefByNodeIdLocale(String bundle, String nodeId, String locale);

	public String getServiceMessageKeyById(long messageId);
	
    public void updateBundle(long bundleRef, String key, String value, String active, String group);

    public Map<String, Vector<String>> getAllFEInterfaces();
    
    public Map<String, Vector<String>> getFEInterfaces(String[] communeCodes);
    
	public Map<String, List<ExtendedAvailableService>> getRegisteredBEServicesGroupByURL() throws PersistenceBrokerException;

    //TABLEVALUES
	public boolean registerNewTableValueProperty(String newValue, int tableValueRef);

	public void updateTableValueProperty(String oldValue, int oldTableValueRef, String newValue, int tableValueRef);

	public void deleteTableValueProperty(String value, int tableValueRef);

	//SERVICE AUDIT PROCESSORS
	public void registerServiceAuditProcessor(long serviceid, String auditProcessor, int active);
	
	

	
}
