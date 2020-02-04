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
package it.people.console.web.servlet.ws.dbupdater;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Vector;

import javax.sql.DataSource;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

import it.people.console.system.AbstractLogger;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 28/gen/2012 11:18:27
 *
 */
@Endpoint
public class DbUpdaterEndpoint extends AbstractLogger{

	@Autowired
	private DataSource dataSourcePeopleDB;
	
		
	@PayloadRoot(localPart = "UpdateRequest", namespace="http://client.ws.console.people.it/schemas")
	public void handleUpdateRequest(@RequestPayload Element updateRequest) {

		if (logger.isInfoEnabled()) {
			logger.info("Updating bundles Messages and Table Values...");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Update request with buffer size = " + updateRequest.getContent().size());
		}
		
		if (updateRequest != null && updateRequest.getContent() != null && updateRequest.getContent().size() > 0) {
			
			Connection connection = null;
			Statement statement = null;
			boolean autocommit = false;
			
			try {
				connection = dataSourcePeopleDB.getConnection();
				autocommit = connection.getAutoCommit();
				connection.setAutoCommit(false);
				statement = connection.createStatement();

				boolean doRollback = false;

				//Execute normal update without batch update
				Vector <PreparedStatement> statementBuffer = new Vector<PreparedStatement>();
				
				for (int index = 0; index < updateRequest.getContent().size(); index++) {
					Element element = (Element)updateRequest.getContent().get(index);
					if (logger.isDebugEnabled()) {
						logger.debug("Update instruction: " + element.getText());
					}
					//Do some check - filtering here if needed
					statementBuffer.add(connection.prepareStatement(element.getText()));
				}
				
				//Execute the update
				if (!statementBuffer.isEmpty()) {
					Iterator <PreparedStatement> bundlesPropertiesBufferIterator = statementBuffer.iterator();
					while(bundlesPropertiesBufferIterator.hasNext() ) {
						bundlesPropertiesBufferIterator.next().execute();
					}
				}

				//Check rollback
				if (!doRollback) {
					if (logger.isDebugEnabled()) {
						logger.debug("No rollback required...");
					}
					connection.commit();
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("Rollback required...");
					}
					connection.rollback();
				}
				connection.setAutoCommit(autocommit);
				
			} catch(SQLException e) {
				try {
					connection.rollback();
					connection.setAutoCommit(autocommit);
				} catch(Exception e1) {
					logger.error("Error while roll back.", e1);
				}
				logger.error("", e);
			} finally {
				try {
					if (statement != null) {
						statement.close();
					}
					if (connection != null) {
						connection.close();
					}
				} catch(Exception ignore) {
					
				}
			}
		}
		if (logger.isInfoEnabled()) {
			logger.info("Bundles updated and Table Values updated.");
		}
	}

	
//	@PayloadRoot(localPart = "UpdateRequest", namespace="http://client.ws.console.people.it/schemas")
//	public void handleUpdateRequest(@RequestPayload Element updateRequest) {
//
//		if (logger.isInfoEnabled()) {
//			logger.info("Updating bundles Messages and Table Values...");
//		}
//		if (logger.isDebugEnabled()) {
//			logger.debug("Update request with buffer size = " + updateRequest.getContent().size());
//		}
//		
//		if (updateRequest != null && updateRequest.getContent() !=  null && updateRequest.getContent().size() > 0) {
//			Connection connection = null;
//			PreparedStatement preparedStatement = null;
//			PreparedStatement bundlesPreparedStatement = null;
//			PreparedStatement bundlesPropertiesPreparedStatement = null;
//			Statement statement = null;
//			boolean autocommit = false;
//			
//			try {
//				connection = dataSourcePeopleDB.getConnection();
//				autocommit = connection.getAutoCommit();
//				connection.setAutoCommit(false);
//				statement = connection.createStatement();
//
//				//TODO Remove this: will be sent from FEService in the update instructions
//				if (logger.isDebugEnabled()) {
//					logger.debug("Deleting from bundles_properties table...");
//				}
//				statement.execute("delete from bundles_properties");
//				if (logger.isDebugEnabled()) {
//					logger.debug("Deleting from bundles table...");
//				}
//				statement.execute("delete from bundles");
//				
//				boolean doRollback = false;
//				boolean supportsBatchUpdates = false;
//				
//				if (logger.isDebugEnabled()) {
//					logger.debug("Verifying if connection supports batch updates...");
//				}
//				if (connection.getMetaData() != null) {
//					supportsBatchUpdates = connection.getMetaData().supportsBatchUpdates();
//				}
//				if (logger.isDebugEnabled()) {
//					logger.debug("Connection supports batch updates = " + supportsBatchUpdates);
//				}
//				
//				Vector<PreparedStatement> bundlesBuffer = new Vector<PreparedStatement>();
//				Vector<PreparedStatement> bundlesPropertiesBuffer = new Vector<PreparedStatement>();
//				
//				//Execute update for BUNDLE AND BUNDLE_PROPERTIES
//				for (int index = 0; index < updateRequest.getContent().size(); index++) {
//					Element element = (Element)updateRequest.getContent().get(index);
//					if (logger.isDebugEnabled()) {
//						logger.debug("Update instruction = " + element.getText());
//					}
//					if (element.getText().contains("bundles_properties")) {
//						bundlesPropertiesBuffer.add(connection.prepareStatement(element.getText()));
//					} else {
//						bundlesBuffer.add(connection.prepareStatement(element.getText()));
//					}
//				}
//				
//				if (!bundlesBuffer.isEmpty()) {
//					Iterator<PreparedStatement> bundlesBufferIterator = bundlesBuffer.iterator();
//					while(bundlesBufferIterator.hasNext() ) {
//						bundlesBufferIterator.next().execute();
//					}
//				}
//
//				if (!bundlesPropertiesBuffer.isEmpty()) {
//					Iterator<PreparedStatement> bundlesPropertiesBufferIterator = bundlesPropertiesBuffer.iterator();
//					while(bundlesPropertiesBufferIterator.hasNext() ) {
//						bundlesPropertiesBufferIterator.next().execute();
//					}
//				}
//				
//				if (!doRollback) {
//					if (logger.isDebugEnabled()) {
//						logger.debug("No rollback required...");
//					}
//					connection.commit();
//				} else {
//					if (logger.isDebugEnabled()) {
//						logger.debug("Rollback required...");
//					}
//					connection.rollback();
//				}
//				connection.setAutoCommit(autocommit);
//			} catch(SQLException e) {
//				try {
//					connection.rollback();
//					connection.setAutoCommit(autocommit);
//				} catch(Exception e1) {
//					logger.error("Error while roll back.", e1);
//				}
//				logger.error("", e);
//			} finally {
//				try {
//					if (statement != null) {
//						statement.close();
//					}
//					if (bundlesPreparedStatement != null) {
//						bundlesPreparedStatement.close();
//					}
//					if (bundlesPropertiesPreparedStatement != null) {
//						bundlesPropertiesPreparedStatement.close();
//					}
//					if (preparedStatement != null) {
//						preparedStatement.close();
//					}
//					if (connection != null) {
//						connection.close();
//					}
//				} catch(Exception ignore) {
//					
//				}
//			}
//		}
//
//		if (logger.isInfoEnabled()) {
//			logger.info("Bundles updated.");
//		}
//		
//	}
	
}
