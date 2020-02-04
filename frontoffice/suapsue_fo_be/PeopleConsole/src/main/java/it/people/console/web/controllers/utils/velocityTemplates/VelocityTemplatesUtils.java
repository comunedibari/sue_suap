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
package it.people.console.web.controllers.utils.velocityTemplates;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import it.people.console.domain.VelocityTemplate;
import it.people.feservice.beans.VelocityTemplateDataVO;
import it.people.feservice.beans.VelocityTemplateBean;

/**
 * @author Andrea Piemontese - Engineering Ingegneria Informatica S.p.A.
 * @created 13/nov/2012 12:01:27
 *
 */
public class VelocityTemplatesUtils {

	
	//Template suffixes
	private static String BODY_SUFFIX = ".body";
	private static String BODY_MAPPER_SUFFIX = ".body_mapper";
	private static String SUBJECT_MAPPER_SUFFIX = ".subject_mapper";
	private static String SUBJECT_SUFFIX = ".subject";
	
	
	//Static logger
	protected static Logger logger = LoggerFactory.getLogger(VelocityTemplatesUtils.class);
	
	
	//noninstantiability
    private VelocityTemplatesUtils() {
    	
        throw new AssertionError();
    }
	
	/**
	 * Create a VelocityTemplate 
	 * @param communeId
	 * @param frontEndServiceId is the serviceID for the FRONT-END side (Not PeopleConsole service ID)
	 * @param shortkey
	 * @param value
	 * @param description
	 * @return
	 */
	public static VelocityTemplateBean buildVelocityTemplateBean(String communeId, String frontEndServiceId, String key,
			String value, String servicePackage, String description) {
		
		VelocityTemplateBean result = new VelocityTemplateBean(communeId, frontEndServiceId, key, value,
				servicePackage,  description);
		return result;
	}
	
	
	/**
	 * build a VelocityTemplateDataVO to update or insert one or more Velocity Template
	 * 
	 * 
	 * @param templates
	 * @param servicePackage the servicePackage, if not null, will be used to get serviceId
	 * @param communeId the communeId, if not null, will overwrite templates commmuneId
	 * @return
	 */
	public static VelocityTemplateDataVO buildVelocityTemplateVOToUpdate(List<VelocityTemplate> templates,
			String servicePackage, String communeId) {

		VelocityTemplateDataVO result = new VelocityTemplateDataVO();
		
		List <VelocityTemplateBean> beansList = new ArrayList<VelocityTemplateBean>();
		
		//Create Template Beans
		Iterator <VelocityTemplate> templatesIter = templates.iterator();
		
		while (templatesIter.hasNext()) {
			VelocityTemplate tmplt = templatesIter.next();
			
			if (communeId != null) {
				tmplt.setCommuneId(communeId);
			}
			if (servicePackage != null) {
				tmplt.setServicePackage(servicePackage);
			}

			beansList.add(buildVelocityTemplateBean(tmplt.getCommuneId(), tmplt.getFrontEndServiceId(), 
							tmplt.getKey().concat(BODY_SUFFIX), tmplt.getTemplateBody(), servicePackage, tmplt.getDescription()));
			
			beansList.add(buildVelocityTemplateBean(tmplt.getCommuneId(), tmplt.getFrontEndServiceId(), 
					tmplt.getKey().concat(BODY_MAPPER_SUFFIX), tmplt.getTemplateBodyMapper(), servicePackage, tmplt.getDescription()));
			
			beansList.add(buildVelocityTemplateBean(tmplt.getCommuneId(), tmplt.getFrontEndServiceId(), 
						tmplt.getKey().concat(SUBJECT_SUFFIX), tmplt.getTemplateSubject(), servicePackage, tmplt.getDescription()));
			
			beansList.add(buildVelocityTemplateBean(tmplt.getCommuneId(), tmplt.getFrontEndServiceId(), 
						tmplt.getKey().concat(SUBJECT_MAPPER_SUFFIX), tmplt.getTemplateSubjectMapper(), servicePackage,  
						tmplt.getDescription()));
		}
		
		result.setVelocityTemplates(beansList.toArray(new VelocityTemplateBean[beansList.size()]));
		
		return result;
	}
	
	
	
	/**
	 * 
	 * Build a VelocityTemplate getting data from DB and filling all the attributes to be shown
	 * 
	 * @param dataSourcePeopleDB
	 * @param communeId
	 * @param serviceId
	 * @param shortKey
	 * @param serviceName
	 * @param servicePackage
	 * @return
	 */
	public static VelocityTemplate buildVelocityTemplate(DataSource dataSourcePeopleDB, 
			String communeId, String serviceId, String shortKey, String serviceName, String servicePackage,
			String nodeName) {
		
		VelocityTemplate result = new VelocityTemplate();
		
		//Get data from DB
		String buildTemplateQuery = "SELECT _communeId, _serviceId, _key, _value, _description FROM velocity_templates " +
				"WHERE ((? AND _communeId = ?) OR (? AND _communeId IS NULL)) " +
				"AND ((? AND _serviceId = ?) OR (? AND _serviceId IS NULL)) " +
				"AND (_key LIKE CONCAT(?, '.body') OR _key LIKE CONCAT(?, '.body_%') " +
				"OR  _key LIKE CONCAT(?, '.subject') OR _key LIKE CONCAT(?, '.subject_%')) " +
				"ORDER BY _key";
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
			connection = dataSourcePeopleDB.getConnection();
			
			preparedStatement = connection.prepareStatement(buildTemplateQuery);
			
			//Set commune Id
			if (communeId != null) {
				preparedStatement.setBoolean(1, true);
				preparedStatement.setString(2, communeId);
				preparedStatement.setBoolean(3, false);
			} else {
				preparedStatement.setBoolean(1, false);
				preparedStatement.setString(2, communeId);
				preparedStatement.setBoolean(3, true);
			}
			
			//Set service Id
			if (serviceId != null) {
				preparedStatement.setBoolean(4, true);
				preparedStatement.setInt(5, Integer.parseInt(serviceId));
				preparedStatement.setBoolean(6, false);
			} else {
				preparedStatement.setBoolean(4, false);
				preparedStatement.setInt(5, 0);
				preparedStatement.setBoolean(6, true);
			}
			preparedStatement.setString(7, shortKey);	
			preparedStatement.setString(8, shortKey);	
			preparedStatement.setString(9, shortKey);	
			preparedStatement.setString(10, shortKey);	
			
			rs = preparedStatement.executeQuery();
		
			result.setCommuneId(communeId);
			result.setKey(shortKey);
			result.setFrontEndServiceId(serviceId);
			result.setServiceName(serviceName);
			result.setServicePackage(servicePackage);
			result.setNodeName(nodeName);
			
			//Create Templte Beans
			while (rs.next()) {
				
				String fullKey = rs.getString("_key");
				
				if (fullKey.equals(shortKey.concat(BODY_SUFFIX))) {
					result.setTemplateBody(rs.getString("_value"));
					result.setDescription(rs.getString("_description"));
					
				} else if (fullKey.equals(shortKey.concat(BODY_MAPPER_SUFFIX))) {
					result.setTemplateBodyMapper(rs.getString("_value"));
					
				} else if (fullKey.equals(shortKey.concat(SUBJECT_SUFFIX))) {
					result.setTemplateSubject(rs.getString("_value"));
					
				} else if (fullKey.equals(shortKey.concat(SUBJECT_MAPPER_SUFFIX))) {
					result.setTemplateSubjectMapper(rs.getString("_value"));
				} else {
					logger.error("Unknown key for Velocity Template " + shortKey);
					throw new Exception("Unknown key for Velocity Template " + shortKey);
				}
			}
			
		} catch (SQLException e) {
			logger.error("SQL Exception building Velocity Template");
			e.printStackTrace();
		}
		catch (Exception e) {
			logger.error("Exception building Velocity Template");
			e.printStackTrace();
		} 
		finally {
			try {
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException ignore) {}
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException ignore) {}
		}
		return result;
	}
	
	
	
	/**
	 * 
	 * build a VelocityTemplateDataVO getting data from DB: only fills attributes to delete
	 * 
	 * @param communeId
	 * @param serviceId
	 * @param shortKey
	 * @return
	 */
	public static VelocityTemplateDataVO buildVelocityTemplateVOToDelete(DataSource dataSourcePeopleDB, 
			String communeId, String serviceId, String shortKey) {
		
		VelocityTemplateDataVO result = new VelocityTemplateDataVO();
		
		List <VelocityTemplateBean> beansList = new ArrayList<VelocityTemplateBean>();
		
		//Get data from DB
		String buildTemplateQuery = "SELECT _communeId, _serviceId, _key FROM velocity_templates " +
				"WHERE ((? AND _communeId = ?) OR (? AND _communeId IS NULL)) " +
				"AND ((? AND _serviceId = ?) OR (? AND _serviceId IS NULL)) " +
				"AND (_key LIKE CONCAT(?, '.body') OR _key LIKE CONCAT(?, '.body_%') " +
				"OR  _key LIKE CONCAT(?, '.subject') OR _key LIKE CONCAT(?, '.subject_%'))";
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			
			preparedStatement = connection.prepareStatement(buildTemplateQuery);
			
			//Set commune Id
			if (communeId != null) {
				preparedStatement.setBoolean(1, true);
				preparedStatement.setString(2, communeId);
				preparedStatement.setBoolean(3, false);
			} else {
				preparedStatement.setBoolean(1, false);
				preparedStatement.setString(2, communeId);
				preparedStatement.setBoolean(3, true);
			}
			
			//Set service Id
			if (serviceId != null) {
				preparedStatement.setBoolean(4, true);
				preparedStatement.setInt(5, Integer.parseInt(serviceId));
				preparedStatement.setBoolean(6, false);
			} else {
				preparedStatement.setBoolean(4, false);
				preparedStatement.setInt(5, 0);
				preparedStatement.setBoolean(6, true);
			}
			preparedStatement.setString(7, shortKey);	
			preparedStatement.setString(8, shortKey);	
			preparedStatement.setString(9, shortKey);	
			preparedStatement.setString(10, shortKey);	
			
			resultSet = preparedStatement.executeQuery();
		
			//Create Templte Beans
			while (resultSet.next()) {
				beansList.add(buildVelocityTemplateBean(communeId, serviceId, 
						resultSet.getString("_key"), null, null, null));
			}
			
			result.setVelocityTemplates(beansList.toArray(new VelocityTemplateBean[beansList.size()]));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
		finally {
			try {
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException ignore) {}
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException ignore) {}
		}
		return result;
	}
	
	/**
	 * Delete and rewrite data in the velocity templates "TEMP" table
	 * 
	 * @param newData
	 * @param connection
	 * @throws SQLException
	 */
	public static void rewriteVelocityTemplateTable(VelocityTemplateDataVO newData, Connection connection)
			throws SQLException {

		PreparedStatement preparedStatement = null;

		String insertQuery = "INSERT INTO velocity_templates (_communeId, _serviceId, _key, _value, _description) "
				+ "VALUES (?,?,?,?,?)";

		String deleteQuery = "DELETE from velocity_templates";

		try {
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

			preparedStatement = connection.prepareStatement(deleteQuery);
			preparedStatement.execute();

			// Write new data
			if (newData != null) {

				List<VelocityTemplateBean> templates = Arrays.asList(newData.getVelocityTemplates());

				Iterator <VelocityTemplateBean> templatIter = templates.iterator();
				preparedStatement = connection.prepareStatement(insertQuery);

				while (templatIter.hasNext()) {
					VelocityTemplateBean template = templatIter.next();

					if (template.getCommuneId() == null) {
						preparedStatement.setNull(1, java.sql.Types.VARCHAR);
					} else {
						preparedStatement.setString(1, template.getCommuneId());
					}

					if (template.getServiceId() == null) {
						preparedStatement.setNull(2, java.sql.Types.INTEGER);
					} else {
						preparedStatement.setInt(2, Integer.parseInt(template.getServiceId()));
					}

					preparedStatement.setString(3, template.getKey());
					preparedStatement.setString(4, template.getValue());
					preparedStatement.setString(5, template.getDescription());
					preparedStatement.execute();
				}
			}
			connection.commit();

		} catch (SQLException e) {
			logger.error("Unable to rewrite table for velocity temoplates data.", e);
			try {
				connection.rollback();
				logger.error("Rewrite table for velocity temoplates data: transaction rolled-back.", e);
			} catch (Exception ignore) {
			}
			throw new SQLException("Unable to rewrite table for velocity templates data.");
		}

		finally {
			try {
				if (connection != null) {
					// Leave open
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException ignore) {
			}
		}
	}
	
	
}
