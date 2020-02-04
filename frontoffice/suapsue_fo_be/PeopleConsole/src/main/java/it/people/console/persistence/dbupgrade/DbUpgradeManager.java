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
package it.people.console.persistence.dbupgrade;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.people.console.config.ConsoleVersion;
import it.people.console.persistence.dbupgrade.exceptions.UpgradeQueueException;
import it.people.console.persistence.exceptions.DbUpgradeManagerException;
import it.people.console.persistence.utils.DataSourceFactory;
import it.people.console.system.AbstractLogger;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 11/giu/2011 11.31.13
 *
 */
public class DbUpgradeManager extends AbstractLogger {

	private static final String VERSION_QUERY = "select major_number, minor_number from _version;";
	
	private static DbUpgradeManager instance = null;
	
	private DataSource dataSource;
	
	private int actualMajorNumber = 0;
	
	private int actualMinorNumber = 0;
	
	private DbUpgradeManager() {
		this.setDataSource(DataSourceFactory.getInstance().getDataSource());
		Connection connection = null;
		try {
			connection = this.getDataSource().getConnection();
			if (this.upgradeRequired(connection)) {
				String log = doUpgrade(connection);
				if (logger.isDebugEnabled()) {
					logger.debug("Upgrade log:");
					logger.debug("\t" + log);
				}
			}
		} catch (SQLException e) {
			logger.error("Error while verifying if db upgrade is required.", e);
		} catch (DbUpgradeManagerException e) {
			logger.error("Error while verifying if db upgrade is required.", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.warn("Errore while closing connection.", e);
				}
			}
		}
	}
	
	private boolean upgradeRequired(Connection connection) throws SQLException, DbUpgradeManagerException {
		
		boolean result = false;
		
		logger.info("Verifying db version...");

		int dbMajorNumber = 0;
		int dbMinorNumber = 0;
		
		if (this.versionTableExists(connection)) {
			PreparedStatement preparedStatement = connection.prepareCall(VERSION_QUERY);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				dbMajorNumber = resultSet.getInt(1);
				dbMinorNumber = resultSet.getInt(2);
				logger.info("Db version = '" + dbMajorNumber + "." + dbMinorNumber + "'.");
				logger.info("Required db version = '" + ConsoleVersion.getDbMajorNumber() 
						+ "." + ConsoleVersion.getDbMinorNumber() + "'.");
				Float dbNumber = Float.parseFloat(dbMajorNumber + "." + dbMinorNumber);
				Float requiredDbNumber = Float.parseFloat(ConsoleVersion.getDbMajorNumber() 
						+ "." + ConsoleVersion.getDbMinorNumber());
				if (dbNumber.compareTo(requiredDbNumber) < 0) {
					logger.info("Required db version is greater than actual db version.");
					logger.info("Upgrading from " + dbNumber + " to " + requiredDbNumber + ".");
					result = true;
				} else {
					logger.info("Required db version is equal or greater than actual db version, no upgrade required");
				}
			} else {
				throw new DbUpgradeManagerException("Version table exists but is empty.");
			}
		} else {
			Float dbNumber = Float.parseFloat(dbMajorNumber + "." + dbMinorNumber);
			Float requiredDbNumber = Float.parseFloat(ConsoleVersion.getDbMajorNumber() 
					+ "." + ConsoleVersion.getDbMinorNumber());
			if (dbNumber.compareTo(requiredDbNumber) < 0) {
				logger.info("Required db version is greater than actual db version.");
				logger.info("Upgrading from " + dbNumber + " to " + requiredDbNumber + ".");
				result = true;
			} else {
				logger.info("Required db version is equal or greater than actual db version, no upgrade required");
			}
			result = true;
		}
		
		this.setActualMajorNumber(dbMajorNumber);
		this.setActualMinorNumber(dbMinorNumber);
		
		return result;
		
	}
	
	private boolean versionTableExists(Connection connection) throws SQLException {
		
		boolean result = false;
		
		if (logger.isDebugEnabled()) {
			logger.debug("Verifying if version table exists...");
		}
		
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet dbTables = databaseMetaData.getTables(connection.getCatalog(), null, "_version", null);
		result = dbTables.next();
		
		dbTables.close();
		
		return result;
		
	}
	
	private String doUpgrade(Connection connection) throws DbUpgradeManagerException {
		
		String result = "";

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Reading upgrading xml table...");
			}
			InputStream upgradesFiles = this.getClass().getResourceAsStream("upgrades.xml");
			if (logger.isDebugEnabled()) {
				logger.debug("Retrieving upgrade scripts queue...");
			}
			LinkedList<String> upgradeScriptsQueue = UpgradeQueue.getUpgradeQueue(upgradesFiles, 
					this.getActualMajorNumber(), this.getActualMinorNumber());

			if (upgradeScriptsQueue.isEmpty()) {
				if (logger.isDebugEnabled()) {
					logger.debug("Upgrade scripts queue is empty.");
				}
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("Reading upgrade scripts queue...");
				}
				while(!upgradeScriptsQueue.isEmpty()) {
					String upgradingDbScriptName = upgradeScriptsQueue.poll();

					if (logger.isDebugEnabled()) {
						logger.debug("Opening input stream for script file " + upgradingDbScriptName);
					}
					
					InputStream upgradingDbInputStream = this.getClass().getResourceAsStream("scripts/" + upgradingDbScriptName);
					if (upgradingDbInputStream == null) {
						logger.error("Unable to read upgrade script (null input stream).");
						throw new DbUpgradeManagerException("Unable to read upgrade script (null input stream).");
					}
	
					if (logger.isDebugEnabled()) {
						logger.debug("Reading upgrading script bytes...");
					}
					byte[] upgradingDbScriptBytes = new byte[upgradingDbInputStream.available()];
					upgradingDbInputStream.read(upgradingDbScriptBytes);
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					byteArrayOutputStream.write(upgradingDbScriptBytes);
					byteArrayOutputStream.flush();
					String upgradingDbScript = byteArrayOutputStream.toString();
					byteArrayOutputStream.close();
					upgradingDbInputStream.close();
					if (logger.isDebugEnabled()) {
						logger.debug("Upgrading script:");
						logger.debug("\t" + upgradingDbScript);
					}
					logger.info("Executing upgrade...");
	
					Statement statement = connection.createStatement();
					if (logger.isDebugEnabled()) {
						logger.debug("Dropping existing procedure...");
					}
					statement.execute("drop procedure if exists updateDb");
					if (logger.isDebugEnabled()) {
						logger.debug("Executing upgrading procedure...");
					}
					statement.execute(upgradingDbScript);
	
					if (logger.isDebugEnabled()) {
						logger.debug("Calling upgrading procedure...");
					}
					CallableStatement callableStatement = connection.prepareCall("{ call updateDb(?) }");
					callableStatement.registerOutParameter(1, java.sql.Types.LONGVARCHAR);
					callableStatement.execute();
	
					if (logger.isDebugEnabled()) {
						logger.debug("Getting output log from upgrading procedure...");
					}
					result = callableStatement.getString(1);
	
					callableStatement.close();
					if (logger.isDebugEnabled()) {
						logger.debug("Dropping upgrading procedure...");
					}
					statement.execute("drop procedure if exists updateDb");
					statement.close();
	
					logger.info("Upgrade done.");
					
				}
			
			}

		} catch(UpgradeQueueException e) {
			logger.error("Unable to read upgrade queue", e);
			throw new DbUpgradeManagerException(e, "Unable to read upgrade queue.");
		} catch (IOException e) {
			logger.error("Unable to read upgrade script", e);
			throw new DbUpgradeManagerException(e, "Unable to read upgrade script.");
		} catch (SQLException e) {
			logger.error("Unable to execute upgrade script", e);
			throw new DbUpgradeManagerException(e, "Unable to execute upgrade script.");
		}
		
		return result;
		
	}
	
	
	/**
	 * @return
	 */
	public static DbUpgradeManager upgrade() {
		if (instance == null) {
			instance = new DbUpgradeManager();
		}
		return instance;
	}

	private DataSource getDataSource() {
		return dataSource;
	}

	private void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * @return the actualMajorNumber
	 */
	private int getActualMajorNumber() {
		return this.actualMajorNumber;
	}

	/**
	 * @param actualMajorNumber the actualMajorNumber to set
	 */
	private void setActualMajorNumber(int actualMajorNumber) {
		this.actualMajorNumber = actualMajorNumber;
	}

	/**
	 * @return the actualMinorNumber
	 */
	private int getActualMinorNumber() {
		return this.actualMinorNumber;
	}

	/**
	 * @param actualMinorNumber the actualMinorNumber to set
	 */
	private void setActualMinorNumber(int actualMinorNumber) {
		this.actualMinorNumber = actualMinorNumber;
	}
	
}
