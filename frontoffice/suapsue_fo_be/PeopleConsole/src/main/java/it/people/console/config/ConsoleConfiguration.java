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
package it.people.console.config;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.people.console.config.exceptions.ConsoleConfigurationException;
import it.people.console.persistence.utils.DataSourceFactory;
import it.people.console.system.AbstractLogger;

/**
 * <p>For primitive types setters parameter must be a class of corresponding primitive type
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 24/giu/2011 15.57.16
 *
 */
public final class ConsoleConfiguration extends AbstractLogger {
	
	private static ConsoleConfiguration instance = null;
	
	private boolean rootAndConsoleAdminAlwaysGrantAccess = true;
	
	private int rootPasswordMinLength = 8;
	
	private int certificatesStandardValidity = 365;

	private boolean mailTransportAuth;
	
	private String mailTransportFrom;
	
	private String mailTransportHost;
	
	private String mailTransportPassword;
	
	private int mailTransportPort;
	
	private String mailTransportProtocol;
	
	private String mailTransportUsername;
	
	private boolean mailTransportUsessl;
	
	private boolean mailTransportUsetls;
	
	//Monitoring FTP configuration for "Osservatorio" 
	private String monitoringFTPHost;
	
	private String monitoringFTPPassword;
	
	private int monitoringFTPPort;
	
	private String monitoringFTPUser;
	
	
	//QUARTZ Scheduler
	private String consoleInfoUpdateTrigger;
	
	
	/**
	 * @throws ConsoleConfigurationException
	 */
	private ConsoleConfiguration() throws ConsoleConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("Initializing console configuration instance...");
		}
		this.loadConfiguration();
		if (logger.isDebugEnabled()) {
			logger.debug("Console configuration instance initialized.");
		}
	}

	/**
	 * @return
	 * @throws ConsoleConfigurationException 
	 */
	public static final ConsoleConfiguration instance() throws ConsoleConfigurationException {
		if (getInstance() == null) {
			setInstance(new ConsoleConfiguration());
		}
		return getInstance();
	}
	
	/**
	 * @return the instance
	 */
	private static ConsoleConfiguration getInstance() {
		return instance;
	}

	/**
	 * @param instance the instance to set
	 */
	private static void setInstance(ConsoleConfiguration instance) {
		ConsoleConfiguration.instance = instance;
	}

	/**
	 * @throws ConsoleConfigurationException 
	 * 
	 */
	private final void loadConfiguration() throws ConsoleConfigurationException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Loading configuration from database...");
		}
		String sql = "select _key, _type, _value from pc_configuration";
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Opening connection...");
			}
			connection = DataSourceFactory.getInstance().getDataSource().getConnection();
			if (logger.isDebugEnabled()) {
				logger.debug("Preparing call with sql = '" + sql + "'...");
			}
			callableStatement = connection.prepareCall(sql);
			if (logger.isDebugEnabled()) {
				logger.debug("Executing query...");
			}
			resultSet = callableStatement.executeQuery();
			if (logger.isDebugEnabled()) {
				logger.debug("Looping through resultset...");
			}
			while(resultSet.next()) {
				
				String _key = resultSet.getString(1);
				String _type = resultSet.getString(2);
				String _value = resultSet.getString(3);
				
				if (logger.isDebugEnabled()) {
					logger.debug("Setting key '" + _key + "' of type '" + _type + "' with value '" + _value + "'...");
				}
				
				try {
					Method method = ConsoleConfiguration.class.getDeclaredMethod("set" + sanitizeFirstChar(_key), getClassNameFromType(_type));
					method.invoke(this, getArgumentValue(_type, _value));
				} catch (SecurityException e) {
					logger.error("Key '" + _key + "' of type '" + _type + "' generated a security exception.");
					throw new ConsoleConfigurationException(e, "Key '" + _key + "' of type '" + _type + "' generated a security exception.");
				} catch (NoSuchMethodException e) {
					logger.error("Key '" + _key + "' of type '" + _type + "' generated a no such method exception.");
					throw new ConsoleConfigurationException(e, "Key '" + _key + "' of type '" + _type + "' generated a no such method exception.");
				} catch (IllegalArgumentException e) {
					logger.error("Key '" + _key + "' of type '" + _type + "' generated a illegal argument exception.");
					throw new ConsoleConfigurationException(e, "Key '" + _key + "' of type '" + _type + "' generated a illegal argument exception.");
				} catch (IllegalAccessException e) {
					logger.error("Key '" + _key + "' of type '" + _type + "' generated a illegal access exception.");
					throw new ConsoleConfigurationException(e, "Key '" + _key + "' of type '" + _type + "' generated a illegal access exception.");
				} catch (InvocationTargetException e) {
					logger.error("Key '" + _key + "' of type '" + _type + "' generated a invocation target exception.");
					throw new ConsoleConfigurationException(e, "Key '" + _key + "' of type '" + _type + "' generated a invocation target exception.");
				}
				
			}
		} catch(SQLException e) {
			
		} finally {
			try {
				if (logger.isDebugEnabled()) {
					logger.debug("Closing db objects...");
				}
				if (logger.isDebugEnabled()) {
					logger.debug("Closing resultset...");
				}
				if (resultSet != null) {
					resultSet.close();
				}
				if (logger.isDebugEnabled()) {
					logger.debug("Closing callable statement...");
				}
				if (callableStatement != null) {
					callableStatement.close();
				}
				if (logger.isDebugEnabled()) {
					logger.debug("Closing connection...");
				}
				if (connection != null) {
					connection.close();
				}
			} catch(SQLException e) {
				logger.warn("Exception while closing db objects.");
			}
		}
		
	}
	
	/**
	 * @param classType
	 * @return
	 */
	private Class<?> getClassNameFromType(String classType) {
		
		Class<?> result = null;
		
		switch (Enum.valueOf(ConsoleConfigurationCatalog.CatalogTypes.class, "_" + classType.toLowerCase())) {
			case _array:
				result = Array.class;
				break;
			case _boolean:
				result = Boolean.class;
				break;
			case _byte:
				result = Byte.class;
				break;
			case _character:
				result = Character.class;
				break;
			case _double:
				result = Double.class;
				break;
			case _float:
				result = Float.class;
				break;
			case _integer:
				result = Integer.class;
				break;
			case _long:
				result = Long.class;
				break;
			case _short:
				result = Short.class;
				break;
			case _string:
				result = String.class;
				break;
		}
		
		return result;
		
	}

	/**
	 * @param classType
	 * @param value
	 * @return
	 */
	private Object getArgumentValue(String classType, String value) {
		
		Object result = null;
		
		switch (Enum.valueOf(ConsoleConfigurationCatalog.CatalogTypes.class, "_" + classType.toLowerCase())) {
			case _array:
				result = null;
				break;
			case _boolean:
				result = Boolean.valueOf(value);
				break;
			case _byte:
				result = Byte.valueOf(value);
				break;
			case _character:
				result = Character.valueOf(value.charAt(0));
				break;
			case _double:
				result = Double.valueOf(value);
				break;
			case _float:
				result = Float.valueOf(value);
				break;
			case _integer:
				result = Integer.valueOf(value);
				break;
			case _long:
				result = Long.valueOf(value);
				break;
			case _short:
				result = Short.valueOf(value);
				break;
			case _string:
				result = String.valueOf(value);
				break;
		}
		
		return result;
		
	}
	
	/**
	 * @param propertyName
	 * @return
	 */
	private String sanitizeFirstChar(String propertyName) {
		return propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
	}

	
// Catalog properties setter and getter
	
	/**
	 * @return the rootAndConsoleAdminAlwaysGrantAccess
	 */
	public final boolean isRootAndConsoleAdminAlwaysGrantAccess() {
		return rootAndConsoleAdminAlwaysGrantAccess;
	}

	/**
	 * @param rootAndConsoleAdminAlwaysGrantAccess the rootAndConsoleAdminAlwaysGrantAccess to set
	 */
	public final void setRootAndConsoleAdminAlwaysGrantAccess(
			Boolean rootAndConsoleAdminAlwaysGrantAccess) {
		this.rootAndConsoleAdminAlwaysGrantAccess = rootAndConsoleAdminAlwaysGrantAccess;
	}

	/**
	 * @return the rootPasswordMinLength
	 */
	public final int getRootPasswordMinLength() {
		return rootPasswordMinLength;
	}

	/**
	 * @param rootPasswordMinLength the rootPasswordMinLength to set
	 */
	public final void setRootPasswordMinLength(Integer rootPasswordMinLength) {
		this.rootPasswordMinLength = rootPasswordMinLength;
	}

	/**
	 * @return the certificatesStandardValidity
	 */
	public final int getCertificatesStandardValidity() {
		return certificatesStandardValidity;
	}

	/**
	 * @param certificatesStandardValidity the certificatesStandardValidity to set
	 */
	public final void setCertificatesStandardValidity(
			Integer certificatesStandardValidity) {
		this.certificatesStandardValidity = certificatesStandardValidity;
	}

	/**
	 * @return the mailTransportAuth
	 */
	public final boolean isMailTransportAuth() {
		return this.mailTransportAuth;
	}

	/**
	 * @param mailTransportAuth the mailTransportAuth to set
	 */
	public final void setMailTransportAuth(Boolean mailTransportAuth) {
		this.mailTransportAuth = mailTransportAuth;
	}

	/**
	 * @return the mailTransportFrom
	 */
	public final String getMailTransportFrom() {
		return this.mailTransportFrom;
	}

	/**
	 * @param mailTransportFrom the mailTransportFrom to set
	 */
	public final void setMailTransportFrom(String mailTransportFrom) {
		this.mailTransportFrom = mailTransportFrom;
	}

	/**
	 * @return the mailTransportHost
	 */
	public final String getMailTransportHost() {
		return this.mailTransportHost;
	}

	/**
	 * @param mailTransportHost the mailTransportHost to set
	 */
	public final void setMailTransportHost(String mailTransportHost) {
		this.mailTransportHost = mailTransportHost;
	}

	/**
	 * @return the mailTransportPassword
	 */
	public final String getMailTransportPassword() {
		return this.mailTransportPassword;
	}

	/**
	 * @param mailTransportPassword the mailTransportPassword to set
	 */
	public final void setMailTransportPassword(String mailTransportPassword) {
		this.mailTransportPassword = mailTransportPassword;
	}

	/**
	 * @return the mailTransportPort
	 */
	public final int getMailTransportPort() {
		return this.mailTransportPort;
	}

	/**
	 * @param mailTransportPort the mailTransportPort to set
	 */
	public final void setMailTransportPort(Integer mailTransportPort) {
		this.mailTransportPort = mailTransportPort;
	}

	/**
	 * @return the mailTransportProtocol
	 */
	public final String getMailTransportProtocol() {
		return this.mailTransportProtocol;
	}

	/**
	 * @param mailTransportProtocol the mailTransportProtocol to set
	 */
	public final void setMailTransportProtocol(String mailTransportProtocol) {
		this.mailTransportProtocol = mailTransportProtocol;
	}

	/**
	 * @return the mailTransportUsername
	 */
	public final String getMailTransportUsername() {
		return this.mailTransportUsername;
	}

	/**
	 * @param mailTransportUsername the mailTransportUsername to set
	 */
	public final void setMailTransportUsername(String mailTransportUsername) {
		this.mailTransportUsername = mailTransportUsername;
	}

	/**
	 * @return the mailTransportUsessl
	 */
	public final boolean isMailTransportUsessl() {
		return this.mailTransportUsessl;
	}

	/**
	 * @param mailTransportUsessl the mailTransportUsessl to set
	 */
	public final void setMailTransportUsessl(Boolean mailTransportUsessl) {
		this.mailTransportUsessl = mailTransportUsessl;
	}

	/**
	 * @return the mailTransportUsetls
	 */
	public final boolean isMailTransportUsetls() {
		return this.mailTransportUsetls;
	}

	/**
	 * @param mailTransportUsetls the mailTransportUsetls to set
	 */
	public final void setMailTransportUsetls(Boolean mailTransportUsetls) {
		this.mailTransportUsetls = mailTransportUsetls;
	}

	/**
	 * @param rootAndConsoleAdminAlwaysGrantAccess the rootAndConsoleAdminAlwaysGrantAccess to set
	 */
	public final void setRootAndConsoleAdminAlwaysGrantAccess(
			boolean rootAndConsoleAdminAlwaysGrantAccess) {
		this.rootAndConsoleAdminAlwaysGrantAccess = rootAndConsoleAdminAlwaysGrantAccess;
	}

	/**
	 * @param rootPasswordMinLength the rootPasswordMinLength to set
	 */
	public final void setRootPasswordMinLength(int rootPasswordMinLength) {
		this.rootPasswordMinLength = rootPasswordMinLength;
	}

	/**
	 * @param certificatesStandardValidity the certificatesStandardValidity to set
	 */
	public final void setCertificatesStandardValidity(
			int certificatesStandardValidity) {
		this.certificatesStandardValidity = certificatesStandardValidity;
	}

	/**
	 * @return the monitoringFTPHost
	 */
	public String getMonitoringFTPHost() {
		return monitoringFTPHost;
	}

	/**
	 * @param monitoringFTPHost the monitoringFTPHost to set
	 */
	public void setMonitoringFTPHost(String monitoringFTPHost) {
		this.monitoringFTPHost = monitoringFTPHost;
	}

	/**
	 * @return the monitoringFTPPassword
	 */
	public String getMonitoringFTPPassword() {
		return monitoringFTPPassword;
	}

	/**
	 * @param monitoringFTPPassword the monitoringFTPPassword to set
	 */
	public void setMonitoringFTPPassword(String monitoringFTPPassword) {
		this.monitoringFTPPassword = monitoringFTPPassword;
	}

	/**
	 * @return the monitoringFTPPort
	 */
	public int getMonitoringFTPPort() {
		return monitoringFTPPort;
	}

	/**
	 * @param monitoringFTPPort the monitoringFTPPort to set
	 */
	public void setMonitoringFTPPort(Integer monitoringFTPPort) {
		this.monitoringFTPPort = monitoringFTPPort;
	}

	/**
	 * @return the monitoringFTPUser
	 */
	public String getMonitoringFTPUser() {
		return monitoringFTPUser;
	}

	/**
	 * @param monitoringFTPUser the monitoringFTPUser to set
	 */
	public void setMonitoringFTPUser(String monitoringFTPUser) {
		this.monitoringFTPUser = monitoringFTPUser;
	}

	/**
	 * @return the consoleInfoUpdateTrigger
	 */
	public String getConsoleInfoUpdateTrigger() {
		return consoleInfoUpdateTrigger;
	}

	/**
	 * @param consoleInfoUpdateTrigger the consoleInfoUpdateTrigger to set
	 */
	public void setConsoleInfoUpdateTrigger(String consoleInfoUpdateTrigger) {
		this.consoleInfoUpdateTrigger = consoleInfoUpdateTrigger;
	}
	
}
