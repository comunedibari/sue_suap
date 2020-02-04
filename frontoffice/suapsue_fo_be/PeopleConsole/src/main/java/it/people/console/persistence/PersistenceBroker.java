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

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.people.console.beans.Option;
import it.people.console.domain.AvailableServicesListOrdererByActivity;
import it.people.console.domain.BEService;
import it.people.console.domain.PairElement;
import it.people.console.domain.TripleElement;
import it.people.console.dto.BEServiceDTO;
import it.people.console.dto.ExtendedAvailableService;
import it.people.console.dto.FENodeDTO;
import it.people.console.dto.UserAccountDTO;
import it.people.console.persistence.exceptions.PersistenceBrokerException;
import it.people.console.system.MessageSourceAwareClass;
import it.people.console.utils.Constants;
import it.people.console.utils.SQLUtils;
import it.people.console.utils.StringUtils;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 13/lug/2011 11.07.10
 *
 */
@Service
public class PersistenceBroker extends MessageSourceAwareClass implements IPersistenceBroker {

	@Autowired
	private DataSource dataSourcePeopleDB;
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#getAllRoles(java.lang.String)
	 */
	public Collection<TripleElement<String, String, String>> getAllRoles(String query) {

		Collection<TripleElement<String, String, String>> result = new ArrayList<TripleElement<String, String, String>>();
		
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = this.getDataSourcePeopleDB().getConnection();
			callableStatement = connection.prepareCall(query);
			resultSet = callableStatement.executeQuery();
			while(resultSet.next()) {
				int roleId = resultSet.getInt(1);
				String authority = resultSet.getString(2);
				String roleLabel = resultSet.getString(3);
				result.add(new TripleElement<String, String, String>(String.valueOf(roleId), authority, roleLabel));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.releaseObject(connection, callableStatement, resultSet);
		}
		
		return result;
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#getRegisteredNodes()
	 */
	public Collection<PairElement<String, String>> getRegisteredNodes() {

		Collection<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
		
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = this.getDataSourcePeopleDB().getConnection();
			callableStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.REGISTERED_NODES_QUERY));
			resultSet = callableStatement.executeQuery();
			while(resultSet.next()) {
				int nodeId = resultSet.getInt(1);
				String nodeLabel = resultSet.getString(2);
				result.add(new PairElement<String, String>(String.valueOf(nodeId), nodeLabel));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.releaseObject(connection, callableStatement, resultSet);
		}
		
		return result;
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#getRegisteredNodesCodes()
	 */
	public Collection<String> getRegisteredNodesCodes() {

		Collection<String> result = new ArrayList<String>();
		
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = this.getDataSourcePeopleDB().getConnection();
			callableStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.REGISTERED_NODES_CODES_QUERY));
			resultSet = callableStatement.executeQuery();
			while(resultSet.next()) {
				result.add(resultSet.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.releaseObject(connection, callableStatement, resultSet);
		}
		
		return result;
		
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#getRegisteredFEServices()
	 */
	public Collection<PairElement<String, String>> getRegisteredFEServices() {

		Collection<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
		
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = this.getDataSourcePeopleDB().getConnection();
			callableStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.REGISTERED_FE_SERVICES_QUERY));
			resultSet = callableStatement.executeQuery();
			while(resultSet.next()) {
				String servicePkg = resultSet.getString(1);
				String serviceLabel = resultSet.getString(2);
				result.add(new PairElement<String, String>(servicePkg, serviceLabel));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.releaseObject(connection, callableStatement, resultSet);
		}
		
		return result;
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#getRegisteredBEServices()
	 */
	public Collection<PairElement<String, String>> getRegisteredBEServices() {

		Collection<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
		
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = this.getDataSourcePeopleDB().getConnection();
			callableStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.REGISTERED_BE_SERVICES_QUERY));
			resultSet = callableStatement.executeQuery();
			while(resultSet.next()) {
				String beName = resultSet.getString(1);
				result.add(new PairElement<String, String>(beName, beName));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.releaseObject(connection, callableStatement, resultSet);
		}
		
		return result;
		
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#getRegisteredBEServicesUrlsAndNodeNames()
	 */
	public Map<String, List<ExtendedAvailableService>> getRegisteredBEServicesGroupByURL() throws PersistenceBrokerException {

		Map <String, List<ExtendedAvailableService>> servicesMap = new HashMap<String, List<ExtendedAvailableService>>();
		
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = this.getDataSourcePeopleDB().getConnection();
			callableStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.REGISTERED_BE_SERVICES_URLS_AND_NODE_NAMES_QUERY));
			resultSet = callableStatement.executeQuery();
			
			while(resultSet.next()) {
				
				ExtendedAvailableService service = new ExtendedAvailableService();
				service.setCommuneKey(resultSet.getString(1));
				service.setCommune(resultSet.getString(2));
				service.setServiceName(resultSet.getString(3));
				service.setReference(resultSet.getString(4));

				//Add to Map: if URL exists, add availableService to list (group by URL)
				if (servicesMap.containsKey(service.getReference())) {
					servicesMap.get(service.getReference()).add(service);
				}
				else {
					List <ExtendedAvailableService> servicesList = new ArrayList <ExtendedAvailableService> ();
					servicesList.add(service);
					servicesMap.put(service.getReference(), servicesList);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.releaseObject(connection, callableStatement, resultSet);
		}
		
		return servicesMap;
	}
	
	

	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#isRegisteredBeNode(java.lang.String, int)
	 */
	public boolean isRegisteredBeNode(String beLogicalName, int nodeId) throws PersistenceBrokerException {

		boolean result = false;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = this.getDataSourcePeopleDB().getConnection();
			preparedStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.IS_REGISTERED_BE_SERVICE_QUERY));
			preparedStatement.setString(1, beLogicalName);
			preparedStatement.setInt(2, nodeId);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			int countValue = resultSet.getInt(1);
			result = countValue > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.releaseObject(connection, preparedStatement, resultSet);
		}
		
		return result;
		
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#accountExists(java.lang.String)
	 */
	public boolean accountExists(String taxCode) {

		boolean result = false;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = this.getDataSourcePeopleDB().getConnection();
			preparedStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.ACCOUNT_EXISTS_QUERY));
			preparedStatement.setString(1, taxCode);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				result = resultSet.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.releaseObject(connection, preparedStatement, resultSet);
		}
		
		return result;
		
	}

	public void deleteAccount(int userId) throws PersistenceBrokerException {

		Connection connection = null;
		CallableStatement callableStatement = null;
		
		try {
			connection = this.getDataSourcePeopleDB().getConnection();
			callableStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.DELETE_ACCOUNT_QUERY1));
			callableStatement.setInt(1, userId);
			callableStatement.executeUpdate();

			callableStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.DELETE_ACCOUNT_QUERY2));
			callableStatement.setInt(1, userId);
			callableStatement.executeUpdate();

			callableStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.DELETE_ACCOUNT_QUERY3));
			callableStatement.setInt(1, userId);
			callableStatement.executeUpdate();

			callableStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.DELETE_ACCOUNT_QUERY4));
			callableStatement.setInt(1, userId);
			callableStatement.executeUpdate();

			callableStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.DELETE_ACCOUNT_QUERY5));
			callableStatement.setInt(1, userId);
			callableStatement.executeUpdate();

			callableStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.DELETE_ACCOUNT_QUERY6));
			callableStatement.setInt(1, userId);
			callableStatement.executeUpdate();

			callableStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.DELETE_ACCOUNT_QUERY8));
			callableStatement.setInt(1, userId);
			callableStatement.executeUpdate();
			
			callableStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.DELETE_ACCOUNT_QUERY7));
			callableStatement.setInt(1, userId);
			callableStatement.executeUpdate();
			
		} catch (SQLException e) {
			throw new PersistenceBrokerException(e);
		} finally {
			this.releaseObject(connection, callableStatement);
		}
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#getAccountData(int)
	 */
	public UserAccountDTO getAccountData(int userId) {

		UserAccountDTO result = null;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = this.getDataSourcePeopleDB().getConnection();
			
			// User data
			preparedStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.GET_ACCOUNT_DATA_QUERY));
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				result = new UserAccountDTO();
				result.setFirstName(resultSet.getString(1));
				result.setLastName(resultSet.getString(2));
				result.setTaxCode(resultSet.getString(3));
				result.setEMail(resultSet.getString(4));
				result.seteMailReceiverTypesFlags(resultSet.getString(5));
			}
			
			//Allowed nodes
			preparedStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.GET_ACCOUNT_ALLOWED_NODES_QUERY));
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				result.getVectorAllowedNodes().add(resultSet.getString(1));
			}
			
			//Allowed fe services
			preparedStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.GET_ACCOUNT_ALLOWED_FESERVICES_QUERY));
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				result.getVectorAllowedFEServices().add(resultSet.getString(1));
			}
			
			//Allowed be services
			preparedStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.GET_ACCOUNT_ALLOWED_BESERVICES_QUERY));
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				result.getVectorAllowedBEServices().add(resultSet.getString(1));
			}
			
			//Authorities
			preparedStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.GET_ACCOUNT_AUTHORITIES_QUERY));
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				result.getVectorRoles().add(resultSet.getString(1));
			}
			
			//Allowed ppl admin nodes
			preparedStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.GET_ACCOUNT_ALLOWED_PPLADMIN_NODES_QUERY));
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				result.getVectorAllowedPeopleAdministrationNodes().add(resultSet.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.releaseObject(connection, preparedStatement, resultSet);
		}
		
		return result;
		
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#saveUserCertificate(int, java.lang.String, int, java.lang.String)
	 */
	public void saveUserCertificate(int userRef, String alias, 
			int validity, String base64Certificate, int sentMail) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = this.getDataSourcePeopleDB().getConnection();
			preparedStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.INSERT_USER_CERTIFICATE_QUERY));
			preparedStatement.setInt(1, userRef);
			preparedStatement.setString(2, alias);
			preparedStatement.setInt(3, validity);
			preparedStatement.setTimestamp(4, SQLUtils.getTimestamp());
			preparedStatement.setString(5, base64Certificate);
			preparedStatement.setInt(6, sentMail);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.releaseObject(connection, preparedStatement);
		}
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#deleteUserCertificate(int)
	 */
	public void deleteUserCertificate(int userRef) throws PersistenceBrokerException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = this.getDataSourcePeopleDB().getConnection();
			preparedStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.DELETE_USER_CERTIFICATE_QUERY));
			preparedStatement.setInt(1, userRef);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceBrokerException(e);
		} finally {
			this.releaseObject(connection, preparedStatement);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#updateConfiguration(java.lang.String, java.lang.String)
	 */
	public int updateConfiguration(String key, String value) throws PersistenceBrokerException {

		int result = 0;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = this.getDataSourcePeopleDB().getConnection();
			preparedStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.UPDATE_CONFIGURATION_QUERY));
			preparedStatement.setString(1, value);
			preparedStatement.setString(2, key);
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceBrokerException(e);
		} finally {
			this.releaseObject(connection, preparedStatement);
		}
		
		return result;
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#getRegisteredNodesWithBEServices()
	 */
	public Map<Integer, FENodeDTO> getRegisteredNodesWithBEServices() throws PersistenceBrokerException {

		Map<Integer, FENodeDTO> result = new TreeMap<Integer, FENodeDTO>();
		
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = this.getDataSourcePeopleDB().getConnection();
			callableStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.REGISTERED_NODES_WITH_BE_SERVICES_QUERY));
			resultSet = callableStatement.executeQuery();
			while(resultSet.next()) {
				int nodeId = resultSet.getInt(1);
				String municipalityCode = resultSet.getString(2);
				String municipality = resultSet.getString(3);
				String name = resultSet.getString(4);
				String reference = resultSet.getString(5);
				FENodeDTO feNodeDTO = new FENodeDTO();
				feNodeDTO.setNodeId(nodeId);
				feNodeDTO.setMunicipality(municipality);
				feNodeDTO.setMunicipalityCode(municipalityCode);
				feNodeDTO.setName(name);
				feNodeDTO.setFeServiceURL(reference);
				result.put(new Integer(nodeId), feNodeDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.releaseObject(connection, callableStatement, resultSet);
		}
		
		return result;
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#getRegisteredBEServicesAllData()
	 */
	public Map<Integer, BEServiceDTO> getRegisteredBEServicesAllData() throws PersistenceBrokerException {

		Map<Integer, BEServiceDTO> result = new TreeMap<Integer, BEServiceDTO>();
		
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = this.getDataSourcePeopleDB().getConnection();
			callableStatement = connection.prepareCall(this.getProperty(
					Constants.Queries.REGISTERED_NODES_WITH_BE_SERVICES_QUERY));
			resultSet = callableStatement.executeQuery();
			while(resultSet.next()) {
				int serviceId = resultSet.getInt(1);
				int nodeId = resultSet.getInt(2);
				String nodeName = resultSet.getString(3);
				String reference = resultSet.getString(4);
				int useEnvelope = resultSet.getInt(5);
				int disableCheckDelegate = resultSet.getInt(6);
				
				BEServiceDTO beServiceDTO = new BEServiceDTO();
				beServiceDTO.setServiceId(serviceId);
				beServiceDTO.setLogicalServiceName(nodeName);
				beServiceDTO.setBackEndURL(reference);
				beServiceDTO.setDelegationControlForbidden(String.valueOf(disableCheckDelegate));
				beServiceDTO.setTransportEnvelopeEnabled(String.valueOf(useEnvelope));

				result.put(new Integer(nodeId), beServiceDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.releaseObject(connection, callableStatement, resultSet);
		}
		
		return result;
		
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#getNodeRegisteredBEServicesAllData(int[])
	 */
	public Map<Integer, BEServiceDTO> getNodeRegisteredBEServicesAllData(int[] nodesList) throws PersistenceBrokerException {

		Map<Integer, BEServiceDTO> result = new TreeMap<Integer, BEServiceDTO>();
		
		String queryParameter = "";
		for(int index = 0; index < nodesList.length - 1; index++) {
			queryParameter += nodesList[index] + ", ";
		}
		queryParameter += nodesList[nodesList.length - 1];
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = this.getDataSourcePeopleDB().getConnection();
			preparedStatement = connection.prepareStatement(this.getProperty(
					Constants.Queries.NODE_REGISTERED_BE_SERVICES_ALL_DATA_QUERY));
			preparedStatement.setObject(1, queryParameter);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int serviceId = resultSet.getInt(1);
				int nodeId = resultSet.getInt(2);
				String nodeName = resultSet.getString(3);
				String reference = resultSet.getString(4);
				int useEnvelope = resultSet.getInt(5);
				int disableCheckDelegate = resultSet.getInt(6);
				
				BEServiceDTO beServiceDTO = new BEServiceDTO();
				beServiceDTO.setServiceId(serviceId);
				beServiceDTO.setLogicalServiceName(nodeName);
				beServiceDTO.setBackEndURL(reference);
				beServiceDTO.setDelegationControlForbidden(String.valueOf(disableCheckDelegate));
				beServiceDTO.setTransportEnvelopeEnabled(String.valueOf(useEnvelope));

				result.put(new Integer(nodeId), beServiceDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.releaseObject(connection, preparedStatement, resultSet);
		}
		
		return result;
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#getFrameworkLocales()
	 */
	public List<Option> getFrameworkLocales() {
		
		List<Option> result = new ArrayList<Option>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		result.add(new Option(null, "Generale"));
		try {
			connection = this.getDataSourcePeopleDB().getConnection();
			preparedStatement = connection.prepareStatement(this.getProperty(
					Constants.Queries.FRAMEWORK_MESSAGES_LOCALES));
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {

				result.add(new Option(resultSet.getString(1), resultSet.getString(2)));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.releaseObject(connection, preparedStatement, resultSet);
		}
		
		return result;
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#getFEServicesRegisteredPackages()
	 */
	public List<Option> getFEServicesRegisteredPackages() {
		
		List<Option> result = new ArrayList<Option>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = this.getDataSourcePeopleDB().getConnection();
			preparedStatement = connection.prepareStatement(this.getProperty(
					Constants.Queries.FE_REGISTERED_SERVICES_PACKAGES));
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {

				result.add(new Option(resultSet.getString(1), resultSet.getString(2)));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.releaseObject(connection, preparedStatement, resultSet);
		}
		
		return result;
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#getServiceLocales(java.lang.String)
	 */
	public List<Option> getServiceLocales(String servicePackage) {
		
		List<Option> result = new ArrayList<Option>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		result.add(new Option(null, "Generale"));
		try {
			connection = this.getDataSourcePeopleDB().getConnection();
			preparedStatement = connection.prepareStatement(this.getProperty(
					Constants.Queries.SERVICE_MESSAGES_LOCALES));
			preparedStatement.setString(1, servicePackage);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {

				result.add(new Option(resultSet.getString(1), resultSet.getString(2)));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.releaseObject(connection, preparedStatement, resultSet);
		}
		
		return result;
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#getFrameworkRegisterableLocales()
	 */
	public List<Option> getFrameworkRegisterableLocales() {
		
		List<Option> result = new ArrayList<Option>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = this.getDataSourcePeopleDB().getConnection();
			preparedStatement = connection.prepareStatement(this.getProperty(
					Constants.Queries.FRAMEWORK_MESSAGES_REGISTERABLE_LOCALES));
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {

				result.add(new Option(resultSet.getString(1), resultSet.getString(2)));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.releaseObject(connection, preparedStatement, resultSet);
		}
		
		return result;
		
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#getServiceRegisterableLocales(java.lang.String)
	 */
	public List<Option> getServiceRegisterableLocales(String servicePackage) {
		
		List<Option> result = new ArrayList<Option>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = this.getDataSourcePeopleDB().getConnection();
			preparedStatement = connection.prepareStatement(this.getProperty(
					Constants.Queries.SERVICE_MESSAGES_REGISTERABLE_LOCALES));
			preparedStatement.setString(1, servicePackage);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {

				result.add(new Option(resultSet.getString(1), resultSet.getString(2)));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.releaseObject(connection, preparedStatement, resultSet);
		}
		
		return result;
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#loadPagedListHoldersPreferences(int)
	 */
	public Map<String, Integer> loadPagedListHoldersPreferences(int userId) {
		
		Map<String, Integer> result = new HashMap<String, Integer>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = this.getDataSourcePeopleDB().getConnection();
			preparedStatement = connection.prepareStatement(this.getProperty(
					Constants.Queries.USER_PREFERENCES_PAGED_LIST_HOLDERS_SETTINGS));
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {

				result.put(resultSet.getString(1), Integer.parseInt(resultSet.getString(2)));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.releaseObject(connection, preparedStatement, resultSet);
		}
		
		return result;
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#updatePagedListHoldersPreferences(int, java.util.Map)
	 */
	public void updatePagedListHoldersPreferences(int userId, Map<String, Integer> preferences) {
		
		if (!preferences.isEmpty()) {
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			PreparedStatement updatePreparedStatement = null;
			PreparedStatement insertPreparedStatement = null;
			ResultSet resultSet = null;
			
			try {
				connection = this.getDataSourcePeopleDB().getConnection();

				updatePreparedStatement = connection.prepareStatement(this.getProperty(
						Constants.Queries.USER_PREFERENCES_PAGED_LIST_HOLDERS_SETTINGS_UPDATE));
				insertPreparedStatement = connection.prepareStatement(this.getProperty(
						Constants.Queries.USER_PREFERENCES_PAGED_LIST_HOLDERS_SETTINGS_INSERT));
				
				boolean updateBatchExecution = false;
				boolean insertBatchExecution = false;
				for(Map.Entry<String, Integer> preference : preferences.entrySet()) {
					preparedStatement = connection.prepareStatement(this.getProperty(
							Constants.Queries.USER_PREFERENCES_PAGED_LIST_HOLDERS_SETTINGS_COUNT));
					preparedStatement.setInt(1, userId);
					preparedStatement.setString(2, preference.getKey());
					resultSet = preparedStatement.executeQuery();
					if(resultSet.next()) {
						updatePreparedStatement.setString(1, String.valueOf(preference.getValue()));
						updatePreparedStatement.setInt(2, userId);
						updatePreparedStatement.setString(3, preference.getKey());
						updatePreparedStatement.addBatch();
						updateBatchExecution = true;
					} else {
						insertPreparedStatement.setInt(1, userId);
						insertPreparedStatement.setString(2, preference.getKey());
						insertPreparedStatement.setString(3, String.valueOf(preference.getValue()));
						insertPreparedStatement.addBatch();
						insertBatchExecution = true;
					}
				}
				if (updateBatchExecution) {
					updatePreparedStatement.executeBatch();
				}
				if (insertBatchExecution) {
					insertPreparedStatement.executeBatch();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				this.releaseObject(connection, new Statement[] {preparedStatement, updatePreparedStatement, insertPreparedStatement} , 
						new ResultSet[] {resultSet});
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#getFeNodesList()
	 */
	public List<TripleElement<String, String, String>> getFeNodesList() {

		List<TripleElement<String, String, String>> result = new ArrayList<TripleElement<String, String, String>>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			preparedStatement = connection.prepareStatement(this.getProperty(
							Constants.Queries.FE_NODES_LIST_QUERY));
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				result.add(new TripleElement<String, String, String>(resultSet.getString(1), 
						resultSet.getString(2), resultSet.getString(3)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.releaseObject(connection, preparedStatement, resultSet);
		}
		
		return result;
		
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#getFeNodesAvailableServices(java.lang.String[])
	 */
	public Map<Long, List<ExtendedAvailableService>> getFeNodesAvailableServices(String[] feNodesIds) {

		Map<Long, List<ExtendedAvailableService>> result = new HashMap<Long, List<ExtendedAvailableService>>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			preparedStatement = connection.prepareStatement(this.getProperty(
					Constants.Queries.FE_NODES_AVAILABLE_SERVICES_QUERY));
			for(int index = 0; index < feNodesIds.length; index++) {
				Long nodeId = Long.parseLong(feNodesIds[index]);
				List<ExtendedAvailableService> availableServices = new ArrayList<ExtendedAvailableService>();
				preparedStatement.setLong(1, nodeId);
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next()) {
					ExtendedAvailableService extendedAvailableService = new ExtendedAvailableService();
					extendedAvailableService.setServiceId(resultSet.getLong(1));
					extendedAvailableService.setServiceName(resultSet.getString(2));
					extendedAvailableService.set_package(resultSet.getString(3));
					extendedAvailableService.setActivity(resultSet.getString(4));
					extendedAvailableService.setSubActivity(resultSet.getString(5));
					extendedAvailableService.setNodeId(resultSet.getInt(6));
					extendedAvailableService.setCommuneKey(resultSet.getString(7));
					extendedAvailableService.setCommune(resultSet.getString(8));
					extendedAvailableService.setNodeName(resultSet.getString(9));
					extendedAvailableService.setReference(resultSet.getString(10));
					availableServices.add(extendedAvailableService);
				}
				Collections.sort(availableServices, new AvailableServicesListOrdererByActivity());				
				result.put(nodeId, availableServices);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.releaseObject(connection, preparedStatement, resultSet);
		}
		
		return result;
		
	}

	
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#getServiceTableValuesTableId(java.lang.String)
	 */
	@Override
	public List<Option> getServiceTableValuesTableId(String servicePackage, String comuneId) {
		
		List<Option> result = new ArrayList<Option>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = this.getDataSourcePeopleDB().getConnection();
			
			if (comuneId != null) {
				
				preparedStatement = connection.prepareStatement(this.getProperty(
						Constants.Queries.TABLEVALUES_TABLEID_BY_PROCESS_AND_NODEID));
				preparedStatement.setString(1, servicePackage);
				preparedStatement.setString(2, comuneId);
			}
			else {
				preparedStatement = connection.prepareStatement(this.getProperty(
						Constants.Queries.TABLEVALUES_TABLEID_BY_PROCESS_NODEID_NULL));
				preparedStatement.setString(1, servicePackage);
			}
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {

				//Build label string
				String label = resultSet.getString(2);

				if (resultSet.getString(4) != null) {
					label = label.concat(" [Locale: " + resultSet.getString(4) + "]");
				}
				result.add(new Option(label, resultSet.getString(1)));
			}
		
		} catch (SQLException e) {
			logger.error("SQLException retrieving Table Values TableId");
		} finally {
			this.releaseObject(connection, preparedStatement, resultSet);
		}
		
		return result;
	}
	
	
	
	
	
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#getServiceLocalesByNodeId(java.lang.String, java.lang.String)
	 */
	public List<Option> getServiceLocalesByNodeId(String servicePackage, String nodeId) {
		
		List<Option> result = new ArrayList<Option>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		result.add(new Option(null, "Generale"));
		try {
			connection = this.getDataSourcePeopleDB().getConnection();
			preparedStatement = connection.prepareStatement(this.getProperty(
					Constants.Queries.SERVICE_MESSAGES_BY_NODE_ID_LOCALES));
			preparedStatement.setString(1, servicePackage);
			preparedStatement.setString(2, nodeId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {

				result.add(new Option(resultSet.getString(1), resultSet.getString(2)));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.releaseObject(connection, preparedStatement, resultSet);
		}
		
		return result;
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#getServiceRegisterableLocalesByNodeId(java.lang.String, java.lang.String)
	 */
	public List<Option> getServiceRegisterableLocalesByNodeId(String servicePackage, String nodeId) {
		
		List<Option> result = new ArrayList<Option>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = this.getDataSourcePeopleDB().getConnection();
			preparedStatement = connection.prepareStatement(this.getProperty(
					Constants.Queries.SERVICE_MESSAGES_BY_NODE_ID_REGISTERABLE_LOCALES));
			preparedStatement.setString(1, servicePackage);
			preparedStatement.setString(2, nodeId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {

				result.add(new Option(resultSet.getString(1), resultSet.getString(2)));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.releaseObject(connection, preparedStatement, resultSet);
		}
		
		return result;
		
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#registerBundle(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean registerBundle(String bundle, String nodeId, String locale, String active, String group) {
		
		boolean result = true;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			String sanitizedLocale = (locale != null && locale.equalsIgnoreCase("")) ? null : locale;
			connection = dataSourcePeopleDB.getConnection();
			preparedStatement = connection.prepareStatement(this.getProperty(
					Constants.Queries.REGISTER_BUNDLE_QUERY));
			preparedStatement.setString(1, bundle);
			preparedStatement.setString(2, nodeId);
			preparedStatement.setString(3, sanitizedLocale);
			preparedStatement.setInt(4, Integer.parseInt(active));
			preparedStatement.setString(5, group);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.releaseObject(connection, preparedStatement);
		}
		
		return result;
		
	}
	
	//// TABLEVALUES
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#registerNewTableValueProperty(java.lang.String, int)
	 */
	@Override
	public boolean registerNewTableValueProperty(String value, int tableValueRef) {
		
		boolean result = true;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			
			preparedStatement = connection.prepareStatement(this.getProperty(
					Constants.Queries.INSERT_TABLEVALUE_PROPERTY));
			preparedStatement.setString(1, value);
			preparedStatement.setInt(2, tableValueRef);
			preparedStatement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.releaseObject(connection, preparedStatement);
		}
		return result;
	}
	
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#registerServiceAuditProcessor(long, java.lang.String, int)
	 */
	@Override
	public void registerServiceAuditProcessor(long serviceid, String auditProcessor, int active) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			
			//Check if exists, then update or insert new
			preparedStatement = connection.prepareStatement(this.getProperty(
					Constants.Queries.CHECK_EXISTS_SERVICE_AUDIT_PROCESSOR));
			preparedStatement.setLong(1, serviceid);
			preparedStatement.setString(2, auditProcessor);
			ResultSet result = preparedStatement.executeQuery();
			
			if (result.next()) {
				//Update
				preparedStatement = connection.prepareStatement(this.getProperty(
						Constants.Queries.UPDATE_SERVICE_AUDIT_PROCESSOR));
				preparedStatement.setInt(1, active);
				preparedStatement.setLong(2, serviceid);
				preparedStatement.setString(3, auditProcessor);
				preparedStatement.execute();
				
			} else {
				//Insert new
				preparedStatement = connection.prepareStatement(this.getProperty(
						Constants.Queries.INSERT_SERVICE_AUDIT_PROCESSOR));
				preparedStatement.setLong(1, serviceid);
				preparedStatement.setString(2, auditProcessor);
				preparedStatement.setInt(3, active);
				preparedStatement.execute();
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.releaseObject(connection, preparedStatement);
		}
		
			
		
	}
	
	

	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#updateTableValueProperty(int, java.lang.String, int)
	 */
	@Override
	public void updateTableValueProperty(String oldValue, int oldTableValueRef, String newValue, int tableValueRef) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			
			preparedStatement = connection.prepareStatement(this.getProperty(
					Constants.Queries.UPDATE_TABLEVALUE_PROPERTY));
			preparedStatement.setString(1, newValue);
			preparedStatement.setInt(2, tableValueRef);
			preparedStatement.setString(3, oldValue);
			preparedStatement.setInt(4, oldTableValueRef);
			preparedStatement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.releaseObject(connection, preparedStatement);
		}
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#deleteTableValueProperty(int)
	 */
	@Override
	public void deleteTableValueProperty(String value, int tableValueRef) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			
			preparedStatement = connection.prepareStatement(this.getProperty(
					Constants.Queries.DELETE_TABLEVALUE_PROPERTIES));
			preparedStatement.setString(1, value);
			preparedStatement.setInt(2, tableValueRef);
			preparedStatement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.releaseObject(connection, preparedStatement);
		}
	}
	
	

	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#getServiceMessagesBundleRefByNodeIdLocale(java.lang.String, java.lang.String, java.lang.String)
	 */
	public long getServiceMessagesBundleRefByNodeIdLocale(String bundle, String nodeId, String locale) {
		
		long result = -1;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			String query = this.getProperty(Constants.Queries.SERVICE_MESSAGES_BUNDLE_REF_BY_NODE_ID_LOCALE_QUERY);
			String buffer = query;
			if (StringUtils.isEmptyString(locale)) {
				query = buffer.replace("$", "is null");
			} else {
				query = buffer.replace("$", "= '" + locale + "'");
			}
			buffer = query;
			if (StringUtils.isEmptyString(nodeId)) {
				query = buffer.replace("!", "is null");
			} else {
				query = buffer.replace("!", "='" + nodeId + "'");
			}
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bundle);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				result = resultSet.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.releaseObject(connection, preparedStatement, resultSet);
		}
		
		return result;
		
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#getServiceMessageKeyById(long)
	 */
	public String getServiceMessageKeyById(long messageId) {
		
		String result = "";

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			preparedStatement = connection.prepareStatement(this.getProperty(
					Constants.Queries.SERVICE_MESSAGE_KEY_BY_ID_QUERY));
			preparedStatement.setLong(1, messageId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				result = resultSet.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.releaseObject(connection, preparedStatement, resultSet);
		}
		
		return result;
		
	}

    /* (non-Javadoc)
     * @see it.people.console.persistence.IPersistenceBroker#updateBundle(long, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void updateBundle(long bundleRef, String key, String value, String active, String group) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSourcePeopleDB.getConnection();

			preparedStatement = connection.prepareStatement(this.getProperty(Constants.Queries.SEARCH_BUNDLE_KEY_QUERY));
			preparedStatement.setLong(1, bundleRef);
			preparedStatement.setString(2, key);
			
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			int count = resultSet.getInt(1);
			if(count > 0) {
				preparedStatement = connection.prepareStatement(this.getProperty(Constants.Queries.UPDATE_BUNDLE_PROPERTY_QUERY));
				preparedStatement.setString(1, value);
				preparedStatement.setInt(2, 1);
				preparedStatement.setString(3, null);
				preparedStatement.setLong(4, bundleRef);
				preparedStatement.setString(5, key);
				preparedStatement.executeUpdate();
			} else {
				preparedStatement = connection.prepareStatement(this.getProperty(Constants.Queries.INSERT_BUNDLE_PROPERTY_QUERY));
				preparedStatement.setLong(1, bundleRef);
				preparedStatement.setString(2, key);
				preparedStatement.setString(3, value);
				preparedStatement.setInt(4, 1);
				preparedStatement.setString(5, null);
				preparedStatement.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.releaseObject(connection, preparedStatement, resultSet);
		}
    	
    }

	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#getNodesOrphanedBeServices(java.lang.String[])
	 */
	public Map<Long, List<BEService>> getNodesOrphanedBeServices(
			String[] feNodesIds) {

		Map<Long, List<BEService>> result = new HashMap<Long, List<BEService>>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		
		try {
			connection = dataSourcePeopleDB.getConnection();
			preparedStatement = connection.prepareStatement(this.getProperty(Constants.Queries.NODES_ORPHANED_BE_SERVICES_QUERY));
			
			for(int index = 0; index < feNodesIds.length; index++) {
				Long nodeId = Long.parseLong(feNodesIds[index]);
				List<BEService> availableServices = new ArrayList<BEService>();
				preparedStatement.setLong(1, nodeId);
				resultSet = preparedStatement.executeQuery();
			
				while(resultSet.next()) {
					BEService beService = new BEService();
					beService.setId(new Long(resultSet.getInt(1)));
					beService.setNodeId(String.valueOf(resultSet.getInt(2)));
					beService.setNodeName(resultSet.getString(3));
					beService.setCommune(resultSet.getString(4));
					beService.setCommuneKey(resultSet.getString(5));
					beService.setLogicalServiceName(resultSet.getString(6));
					beService.setBackEndURL(resultSet.getString(7));
					beService.setTransportEnvelopeEnabled(resultSet.getInt(8) == 1);
					beService.setDelegationControlForbidden(resultSet.getInt(9) == 1);
					availableServices.add(beService);
				}
				result.put(nodeId, availableServices);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.releaseObject(connection, preparedStatement, resultSet);
		}
		
		return result;
		
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceBroker#getAllNodesBeServices(java.lang.String[])
	 */
	public Map<Long, List<BEService>> getAllNodesBeServices(String[] feNodesIds) {

		Map<Long, List<BEService>> result = new HashMap<Long, List<BEService>>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		
		try {
			connection = dataSourcePeopleDB.getConnection();
			preparedStatement = connection.prepareStatement(this.getProperty(Constants.Queries.ALL_BE_SERVICES_MASSIVE_CHANGE_QUERY));
			
			for(int index = 0; index < feNodesIds.length; index++) {
				Long nodeId = Long.parseLong(feNodesIds[index]);
				List<BEService> availableServices = new ArrayList<BEService>();
				preparedStatement.setLong(1, nodeId);
				resultSet = preparedStatement.executeQuery();
			
				while(resultSet.next()) {
					BEService beService = new BEService();
					beService.setId(new Long(resultSet.getInt(1)));
					beService.setNodeId(String.valueOf(resultSet.getInt(2)));
					beService.setNodeName(resultSet.getString(3));
					beService.setCommune(resultSet.getString(4));
					beService.setCommuneKey(resultSet.getString(5));
					beService.setLogicalServiceName(resultSet.getString(6));
					beService.setBackEndURL(resultSet.getString(7));
					beService.setTransportEnvelopeEnabled(resultSet.getInt(8) == 1);
					beService.setDelegationControlForbidden(resultSet.getInt(9) == 1);
					availableServices.add(beService);
				}
				result.put(nodeId, availableServices);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.releaseObject(connection, preparedStatement, resultSet);
		}
		
		return result;
		
	}

    /* (non-Javadoc)
     * @see it.people.console.persistence.IPersistenceBroker#getAllFEInterfaces()
     */
    public Map<String, Vector<String>> getAllFEInterfaces() {

    	Map<String, Vector<String>> result = new HashMap<String, Vector<String>>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		
		try {
			connection = dataSourcePeopleDB.getConnection();
			preparedStatement = connection.prepareStatement(this.getProperty(Constants.Queries.ALL_FE_INTERFACES));
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				if (result.get(resultSet.getString("reference")) == null) {
					result.put(resultSet.getString("reference"), new Vector<String>());
					result.get(resultSet.getString("reference")).add(resultSet.getString("codicecomune"));
				} else {
					result.get(resultSet.getString("reference")).add(resultSet.getString("codicecomune"));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.releaseObject(connection, preparedStatement, resultSet);
		}
		
		return result;
    	
    }
    
    /* (non-Javadoc)
     * @see it.people.console.persistence.IPersistenceBroker#getFEInterfaces(java.lang.String[])
     */
    public Map<String, Vector<String>> getFEInterfaces(String[] communeCodes) {

    	Map<String, Vector<String>> result = new HashMap<String, Vector<String>>();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		if (communeCodes != null && communeCodes.length > 0) {
			StringBuilder stringBuilder = new StringBuilder().append("'");
			for (int index = 0; index < (communeCodes.length - 1); index++) {
				stringBuilder.append(communeCodes[index]).append("','");
			}
			stringBuilder.append(communeCodes[communeCodes.length - 1]).append("'");
			
			try {
				connection = dataSourcePeopleDB.getConnection();
				statement = connection.createStatement();
				resultSet = statement.executeQuery(this.getProperty(Constants.Queries.FE_INTERFACES_BY_COMMUNE_LIST).replace("?", stringBuilder.toString()));
				
				while(resultSet.next()) {
					if (result.get(resultSet.getString("reference")) == null) {
						result.put(resultSet.getString("reference"), new Vector<String>());
						result.get(resultSet.getString("reference")).add(resultSet.getString("codicecomune"));
					} else {
						result.get(resultSet.getString("reference")).add(resultSet.getString("codicecomune"));
					}
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				this.releaseObject(connection, statement, resultSet);
			}
		}
		
		return result;
    	
    }
	
	
	/**
	 * @param connection
	 * @param statement
	 */
	private void releaseObject(Connection connection, Statement statement) {
		this.releaseObject(connection, statement, null);
	}
	
	/**
	 * @param connection
	 * @param statement
	 * @param resultSet
	 */
	private void releaseObject(Connection connection, Statement statement, ResultSet resultSet) {
		
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch(SQLException e) {
			
		}
		
	}

	private void releaseObject(Connection connection, Statement[] statements, ResultSet[] resultSets) {
		
		try {
			if (resultSets != null) {
				for(int index = 0; index < resultSets.length; index++) {
					ResultSet resultSet = resultSets[index];
					if (resultSet != null) {
						resultSet.close();
					}
				}
			}
			if (statements != null) {
				for(int index = 0; index < statements.length; index++) {
					Statement statement = statements[index];
					if (statement != null) {
						statement.close();
					}
				}
			}
			if (connection != null) {
				connection.close();
			}
		} catch(SQLException e) {
			
		}
		
	}
	
	/**
	 * @return the dataSourcePeopleDB
	 */
	private DataSource getDataSourcePeopleDB() {
		return this.dataSourcePeopleDB;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSourcePeopleDB = dataSource;
	}

}
