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
package it.people.console.security.auth;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import it.people.console.persistence.utils.DataSourceFactory;
import it.people.console.system.AbstractLogger;
import it.people.console.system.UserPreferencesManager;
import it.people.console.utils.Constants;
import it.people.console.utils.StringUtils;
import it.people.core.PplUserData;

/**
 * <p>Questa classe verifica se l'utente che ha eseguito l'accesso sia effettivamente presente 
 * nell'elenco degli utenti abilitati per l'accesso alla PeopleConsole.
 * <p>L'autenticazione viene sempre verificata ad eccezione dell'autenticazione dell'utente root.
 * In questo modo eventuali cambi di ruoli vengono riflessi subito sullo stato dell'account.
 * <p>Durante il processo di autenticazione viene anche eseguito il controllo della valorizzazione 
 * dell'account e-mail nel database della PeopleConsole e nel caso in cui non sia registrato ne viene 
 * eseguita la registrazione.
 * 
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 23/giu/2011 10.45.55
 *
 */
public class AuthenticationManager extends AbstractLogger implements
		org.springframework.security.authentication.AuthenticationManager {
	
	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationManager#authenticate(org.springframework.security.core.Authentication)
	 */
	public Authentication authenticate(Authentication _authenticationToken)
			throws AuthenticationException {

		UserPreferencesManager userPreferencesManager = new UserPreferencesManager(DataSourceFactory.getInstance().getDataSource());
		SIRACLoginAuthenticationToken authentication = null;
		SIRACLoginAuthenticationToken authenticationToken = (SIRACLoginAuthenticationToken)_authenticationToken;
		PplUserData userData = (PplUserData)authenticationToken.getDetails();
		
		if (logger.isDebugEnabled()) {
			logger.debug("authenticate");
		}
		
		if (!((String)_authenticationToken.getPrincipal()).equalsIgnoreCase(Constants.Security.ROOT_USER_LOGIN_IDP)) {
			
			int userId = 0;
			
			Connection connection = null;
			CallableStatement callableStatement = null;
			ResultSet resultSet = null;
			try {
				connection = DataSourceFactory.getInstance().getDataSource().getConnection();
				callableStatement = connection.prepareCall("select id, email, first_name, last_name from pc_users where lower(tax_code) = ?");
				callableStatement.setString(1, userData.getCodiceFiscale());
				resultSet = callableStatement.executeQuery();
				if (resultSet.next()) {
					userId = resultSet.getInt(1);
					String userEMail = resultSet.getString(2);
					String userFirstName = resultSet.getString(3);
					String userLastName = resultSet.getString(4);
					if (logger.isDebugEnabled()) {
						logger.debug("User e-mail address from db = '" + userEMail + "' for user id = '" + userId + "'.");
					}
					if (StringUtils.isEmptyString(userEMail)) {
						if (logger.isDebugEnabled()) {
							logger.debug("Updating user e-mail address with = '" + userData.getEmailaddress() + "' for user id = '" + userId + "'.");
						}
						updateUserEMail(userId, userData.getEmailaddress(), connection);
						if (logger.isDebugEnabled()) {
							logger.debug("User e-mail address for user id = '" + userId + "' updated.");
						}
					}
					if (StringUtils.isEmptyString(userFirstName)) {
						if (logger.isDebugEnabled()) {
							logger.debug("Updating user first name with = '" + userData.getNome() + "' for user id = '" + userId + "'.");
						}
						updateUserFirstName(userId, userData.getNome(), connection);
						if (logger.isDebugEnabled()) {
							logger.debug("User first name for user id = '" + userId + "' updated.");
						}
					}
					if (StringUtils.isEmptyString(userLastName)) {
						if (logger.isDebugEnabled()) {
							logger.debug("Updating user last name with = '" + userData.getCognome() + "' for user id = '" + userId + "'.");
						}
						updateUserLastName(userId, userData.getCognome(), connection);
						if (logger.isDebugEnabled()) {
							logger.debug("User last name for user id = '" + userId + "' updated.");
						}
					}
					if (logger.isDebugEnabled()) {
						logger.debug("Retrieving granted authorities for user");
					}
					authentication = new SIRACLoginAuthenticationToken(authenticationToken.getPrincipal(), 
							authenticationToken.getCredentials(), 
							getUserGrantedAuthorities(userId, connection));
					
					PeopleExtendedUserData pplExtendedUserData = new PeopleExtendedUserData((PplUserData)authenticationToken.getDetails(), 
							getNodesUserSecuritySettings(userId, connection), getFeServicesUserSecuritySettings(userId, connection), 
							getBeServicesUserSecuritySettings(userId, connection));
					
					authentication.setDetails(pplExtendedUserData);
					authentication.setAuthenticated(true);
					userPreferencesManager.loadPreferences(userId);
				} else {
					closeDbObjects(resultSet, callableStatement, connection);
					logger.error("Unauthorized user");
					throw new AccessDeniedException("Unauthorized user");
				}
				if (resultSet.next()) {
					closeDbObjects(resultSet, callableStatement, connection);
					logger.error("Credentials integrity exception");
					throw new AccessDeniedException("Unauthorized user");
				}
			} catch (SQLException e) {
				logger.error("Error while getting connection from data source", e);
				throw new AccessDeniedException("Unauthorized user");
			} finally {
				closeDbObjects(resultSet, callableStatement, connection);
			}
		} else {
			authentication = new SIRACLoginAuthenticationToken(authenticationToken.getPrincipal(), 
					authenticationToken.getCredentials(), 
					authenticationToken.getAuthorities());
			authentication.setDetails(authenticationToken.getDetails());
			userPreferencesManager.loadPreferences(0);
			authentication.setAuthenticated(true);
		}
		
		return authentication;
	}

	/**
	 * @param userId
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	private Collection<GrantedAuthority> getUserGrantedAuthorities(int userId, Connection connection) throws SQLException {
		
		Collection<GrantedAuthority> result = new Vector<GrantedAuthority>();
		
		if (logger.isDebugEnabled()) {
			logger.debug("Executing granted authorities query");
		}
		CallableStatement callableStatement = connection.prepareCall("select authCatalog.authority from pc_users_authorities usersAuth join pc_authorities_catalog authCatalog on usersAuth.authorityRef = authCatalog.id where usersAuth.userRef = ?");
		callableStatement.setInt(1, userId);
		ResultSet resultSet = callableStatement.executeQuery();
		while(resultSet.next()) {
			String grantedAuthority = resultSet.getString(1);
			if (logger.isDebugEnabled()) {
				logger.debug("Adding granted authority '" + grantedAuthority + "'.");
			}
			result.add(new GrantedAuthorityImpl(grantedAuthority));
		}
		if (callableStatement != null) {
			callableStatement.close();
		}
		if (resultSet != null) {
			resultSet.close();
		}
		
		return result;
		
	}
	
	/**
	 * @param userId
	 * @param eMailAddress
	 * @param connection
	 * @throws SQLException
	 */
	private void updateUserEMail(int userId, String eMailAddress, Connection connection) throws SQLException {

		CallableStatement callableStatement = connection.prepareCall("update pc_users set email = ? where id = ?");
		callableStatement.setString(1, eMailAddress);
		callableStatement.setInt(2, userId);
		callableStatement.executeUpdate();
		callableStatement.close();
		
	}

	/**
	 * @param userId
	 * @param firstName
	 * @param connection
	 * @throws SQLException
	 */
	private void updateUserFirstName(int userId, String firstName, Connection connection) throws SQLException {

		CallableStatement callableStatement = connection.prepareCall("update pc_users set first_name = ? where id = ?");
		callableStatement.setString(1, firstName);
		callableStatement.setInt(2, userId);
		callableStatement.executeUpdate();
		callableStatement.close();
		
	}

	/**
	 * @param userId
	 * @param lastName
	 * @param connection
	 * @throws SQLException
	 */
	private void updateUserLastName(int userId, String lastName, Connection connection) throws SQLException {

		CallableStatement callableStatement = connection.prepareCall("update pc_users set last_name = ? where id = ?");
		callableStatement.setString(1, lastName);
		callableStatement.setInt(2, userId);
		callableStatement.executeUpdate();
		callableStatement.close();
		
	}
	
	private PplUserAllowedData<Integer> getNodesUserSecuritySettings(int userId, Connection connection) throws SQLException {

		boolean allPermissions = false;
		ArrayList<Integer> permissions = new ArrayList<Integer>();
		
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		callableStatement = connection.prepareCall("select puan.* from pc_users_allowed_nodes as puan join pc_users as pu on pu.id = puan.userref where pu.id = ?");
		callableStatement.setInt(1, userId);
		resultSet = callableStatement.executeQuery();
		while(resultSet.next()) {
			permissions.add(resultSet.getInt(3));
			if (resultSet.getInt(3) == Constants.UNBOUND_VALUE) {
				allPermissions = true;
				permissions = new ArrayList<Integer>();
				break;
			}
		}
		if (callableStatement != null) {
			callableStatement.close();
		}
		if (resultSet != null) {
			resultSet.close();
		}
			
		return new PplUserAllowedData<Integer>(allPermissions, permissions);
		
	}

	private PplUserAllowedData<String> getFeServicesUserSecuritySettings(int userId, Connection connection) throws SQLException {

		boolean allPermissions = false;
		ArrayList<String> permissions = new ArrayList<String>();
		
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		callableStatement = connection.prepareCall("select puaf.* from pc_users_allowed_feservices as puaf join pc_users as pu on pu.id = puaf.userref where pu.id = ?");
		callableStatement.setInt(1, userId);
		resultSet = callableStatement.executeQuery();
		while(resultSet.next()) {
			permissions.add(resultSet.getString(3));
			if (resultSet.getString(3) == Constants.UNBOUND_STRING_VALUE) {
				allPermissions = true;
				permissions = new ArrayList<String>();
				break;
			}
		}
		if (callableStatement != null) {
			callableStatement.close();
		}
		if (resultSet != null) {
			resultSet.close();
		}
			
		return new PplUserAllowedData<String>(allPermissions, permissions);
		
	}

	private PplUserAllowedData<String> getBeServicesUserSecuritySettings(int userId, Connection connection) throws SQLException {

		boolean allPermissions = false;
		ArrayList<String> permissions = new ArrayList<String>();
		
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		callableStatement = connection.prepareCall("select puab.* from pc_users_allowed_beservices as puab join pc_users as pu on pu.id = puab.userref where pu.id = ?");
		callableStatement.setInt(1, userId);
		resultSet = callableStatement.executeQuery();
		while(resultSet.next()) {
			permissions.add(resultSet.getString(3));
			if (resultSet.getString(3) == Constants.UNBOUND_STRING_VALUE) {
				allPermissions = true;
				permissions = new ArrayList<String>();
				break;
			}
		}
		if (callableStatement != null) {
			callableStatement.close();
		}
		if (resultSet != null) {
			resultSet.close();
		}
			
		return new PplUserAllowedData<String>(allPermissions, permissions);
		
	}
	
	/**
	 * @param resultSet
	 * @param callableStatement
	 * @param connection
	 */
	private void closeDbObjects(ResultSet resultSet, CallableStatement callableStatement, Connection connection) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			logger.warn("Error closing connection");
		}
	}
	
}
