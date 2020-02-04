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
/**
 * 
 */
package it.people.util;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import it.people.db.DBConnector;
import it.people.exceptions.PeopleDBException;
import it.people.util.dto.VelocityModelObject;
import it.people.util.dto.VelocityValue;
import it.people.vsl.exception.FormatterException;
import it.people.vsl.transport.PplMailType;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         08/ott/2012 22:04:23
 */
public class VelocityUtils {

    private static Logger logger = LogManager.getLogger(VelocityUtils.class);

    private Map<String, List<FormatterFunction>> formatterFunctionsCache;

    private Map<String, VelocityModelObject> modelObjects;

    private Map<String, Object> rawModelObjects;

    private String communeKey;

    private String servicePackage;

    /**
     * @param modelObjects
     * @param rawModelObjects
     * @param communeKey
     * @param servicePackage
     */
    public VelocityUtils(Map<String, VelocityModelObject> modelObjects,
	    Map<String, Object> rawModelObjects, String communeKey,
	    String servicePackage) {
	this.setFormatterFunctionsCache(new HashMap<String, List<FormatterFunction>>());
	this.setModelObjects(modelObjects);
	this.setRawModelObjects(rawModelObjects);
	this.setCommuneKey(communeKey);
	this.setServicePackage(servicePackage);
    }

    /**
     * @param formatterFunctionsCache
     *            the formatterFunctionsCache to set
     */
    private void setFormatterFunctionsCache(
	    Map<String, List<FormatterFunction>> formatterFunctionsCache) {
	this.formatterFunctionsCache = formatterFunctionsCache;
    }

    /**
     * @return the formatterFunctionsCache
     */
    private Map<String, List<FormatterFunction>> getFormatterFunctionsCache() {
	return this.formatterFunctionsCache;
    }

    /**
     * @return the modelObjects
     */
    private Map<String, VelocityModelObject> getModelObjects() {
	return this.modelObjects;
    }

    /**
     * @param modelObjects
     *            the modelObjects to set
     */
    private void setModelObjects(Map<String, VelocityModelObject> modelObjects) {
	this.modelObjects = modelObjects;
    }

    /**
     * @return the rawModelObjects
     */
    private Map<String, Object> getRawModelObjects() {
	return this.rawModelObjects;
    }

    /**
     * @param rawModelObjects
     *            the rawModelObjects to set
     */
    private void setRawModelObjects(Map<String, Object> rawModelObjects) {
	this.rawModelObjects = rawModelObjects;
    }

    /**
     * @return the communeKey
     */
    private String getCommuneKey() {
	return this.communeKey;
    }

    /**
     * @param communeKey
     *            the communeKey to set
     */
    private void setCommuneKey(String communeKey) {
	this.communeKey = communeKey;
    }

    /**
     * @return the servicePackage
     */
    private String getServicePackage() {
	return this.servicePackage;
    }

    /**
     * @param servicePackage
     *            the servicePackage to set
     */
    private void setServicePackage(String servicePackage) {
	this.servicePackage = servicePackage;
    }

    /**
     * @param mailType
     * @param mailPart
     * @return
     */
    public String mergeTemplate(PplMailType mailType, MailPart mailPart) {

	Map<String, String> templates = this.getVelocityTemplates(mailType);
	Vector<VelocityValue> velocityValues = this.prepareVelocityValues(
		mailType, mailPart, templates);
	return this.mergeTemplate(templates.get(mailPart.getValue()),
		velocityValues);

    }

    /**
     * @param template
     * @param values
     * @return
     */
    public String mergeTemplate(String template, Vector<VelocityValue> values) {

	String result = "";
	StringWriter stringWriter = new StringWriter();
	StringReader reader = new StringReader(template);

	Velocity.init();
	VelocityContext context = initializeContext(values);

	BufferedWriter writer = new BufferedWriter(stringWriter);

	VelocityEngine ve = new VelocityEngine();
	ve.evaluate(context, writer, "vmTemplate", reader);

	try {
	    writer.flush();
	    stringWriter.flush();
	    result = stringWriter.toString();
	    stringWriter.close();
	    reader.close();
	} catch (IOException e) {
	}

	return result;

    }

    /**
     * @param values
     * @return
     */
    private VelocityContext initializeContext(Vector<VelocityValue> values) {

	VelocityContext result = new VelocityContext();

	if (values != null && !values.isEmpty()) {
	    Iterator<VelocityValue> valuesIterator = values.iterator();
	    while (valuesIterator.hasNext()) {
		VelocityValue velocityDTO = valuesIterator.next();
		result.put(velocityDTO.getParameterKey(),
			velocityDTO.getParameterValue());
	    }
	}
	if (this.getRawModelObjects() != null
		&& !this.getRawModelObjects().isEmpty()) {
	    Iterator<String> rawModelObjectsKeysIterator = this
		    .getRawModelObjects().keySet().iterator();
	    while (rawModelObjectsKeysIterator.hasNext()) {
		String key = rawModelObjectsKeysIterator.next();
		result.put(key, this.getRawModelObjects().get(key));
	    }
	}

	return result;

    }

    public Map<String, String> getVelocityTemplates(PplMailType mailType) {

	Map<String, String> result = null;
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;
	Properties queries = new Properties();
	String velocityTemplatePrefix = "";

	switch (mailType) {
	case generalError:
	    velocityTemplatePrefix = "people.generalError.notifyPplAdmin";
	    break;
	case sendError:
	    velocityTemplatePrefix = "people.sendError.notifyPplAdmin";
	    break;
	case notSendProcessDumpError:
	    velocityTemplatePrefix = "people.notSendProcessDumpError.notifyPplAdmin";
	    break;
	case userSignalledBug:
	    velocityTemplatePrefix = "people.bugNotifiedByUser.notifyPplAdmin";
	    break;
	case userSuggestion:
	    velocityTemplatePrefix = "people.suggestionByUser.notifyPplAdmin";
	    break;
	case newAccreditation:
	    velocityTemplatePrefix = "people.newAccreditation.notifyPplAdmin";
	    break;
	case sendErrorNotifyUser:
	    velocityTemplatePrefix = "people.sendError.notifyUser";
	    break;
	case sendNotifyUser:
	    velocityTemplatePrefix = "people.send.notifyUser";
	    break;
	case userSignalledBugUserReceipt:
	    velocityTemplatePrefix = "people.send.userSignalledBugUserReceipt";
	    break;
	case userSuggestionUserReceipt:
	    velocityTemplatePrefix = "people.send.userSuggestionUserReceipt";
	    break;
	default:
	    break;
	}

	try {
	    queries.loadFromXML(VelocityUtils.class
		    .getResourceAsStream("/it/people/resources/Queries.xml"));
	    connection = DBConnector.getInstance().connect(DBConnector.FEDB);

	    statement = connection.createStatement();

	    boolean rowsFound = false;

	    String query = "";
	    if (!StringUtils.isEmpty(this.getCommuneKey())
		    && !StringUtils.isEmpty(this.getServicePackage())) {
		// Search for commune and service template
		query = queries
			.getProperty(
				"pplAdminsEMailTemplatesByCommuneAndService.query")
			.replace("?", velocityTemplatePrefix)
			.replace("#", this.getCommuneKey())
			.replace("$", this.getServicePackage());
		resultSet = statement.executeQuery(query);
		if (resultSet.first()) {
		    rowsFound = true;
		}
	    }

	    if (!StringUtils.isEmpty(this.getCommuneKey()) && !rowsFound) {
		// Search for commune template
		query = queries
			.getProperty("pplAdminsEMailTemplatesByCommune.query")
			.replace("?", velocityTemplatePrefix)
			.replace("#", this.getCommuneKey());
		resultSet = statement.executeQuery(query);
		if (resultSet.first()) {
		    rowsFound = true;
		}
	    }

	    if (!rowsFound) {
		// Search generic template
		query = queries.getProperty("pplAdminsEMailTemplates.query")
			.replace("?", velocityTemplatePrefix);
		resultSet = statement.executeQuery(query);
		if (resultSet.first()) {
		    rowsFound = true;
		}
	    }

	    if (rowsFound) {
		if (result == null) {
		    result = new HashMap<String, String>();
		}
		result.put(
			resultSet.getString(1).substring(
				velocityTemplatePrefix.length() + 1),
			resultSet.getString(2));
		while (resultSet.next()) {
		    result.put(
			    resultSet.getString(1).substring(
				    velocityTemplatePrefix.length() + 1),
			    resultSet.getString(2));
		}
	    }

	} catch (InvalidPropertiesFormatException e) {
	    logger.error("Unable to read Query XML file: \n" + e);
	} catch (IOException e) {
	    logger.error("Unable to read Query XML file: \n" + e);
	} catch (SQLException e) {
	    logger.error("Unable to get velocity templates: \n" + e);
	} catch (PeopleDBException e) {
	    logger.error("Unable to get velocity templates: \n" + e);
	} finally {
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
	    } catch (SQLException ignore) {

	    }
	}

	return result;

    }

    public Vector<VelocityValue> prepareVelocityValues(PplMailType mailType,
	    MailPart mailPart, Map<String, String> templates) {

	Vector<VelocityValue> result = null;

	FormattersUtil formattersUtil = new FormattersUtil();
	Properties valuesMapper = getValuesMapper(mailType, mailPart, templates);

	if (valuesMapper != null) {
	    for (Object keyObj : valuesMapper.keySet()) {
		String key = String.valueOf(keyObj);
		String value = valuesMapper.getProperty(key);

		if (value.contains(".")) {
		    String objectName = value.substring(0, value.indexOf('.'));
		    String mappedMethodBuffer = value.substring(value
			    .indexOf('.') + 1);

		    String mappedMethod = "";
		    String formatterParameters = "";
		    int formatterIndexStart = mappedMethodBuffer.indexOf('[');
		    int formatterIndexEnd = mappedMethodBuffer.indexOf(']');
		    if (formatterIndexEnd == 0) {
			formatterIndexEnd = mappedMethodBuffer.length();
		    }

		    if (formatterIndexStart > 0) {
			formatterParameters = mappedMethodBuffer.substring(
				formatterIndexStart + 1, formatterIndexEnd);
			mappedMethod = mappedMethodBuffer.substring(0,
				formatterIndexStart);
			if (logger.isDebugEnabled()) {
			    logger.debug("Formatter parameters = "
				    + formatterParameters);
			}

			if (!this.getFormatterFunctionsCache().containsKey(
				mappedMethod)) {
			    if (formatterParameters.indexOf(';') > 0) {
				StringTokenizer tokenizer = new StringTokenizer(
					formatterParameters, ";");
				while (tokenizer.hasMoreTokens()) {
				    FormatterFunction formatterFunction = new FormatterFunction(
					    tokenizer.nextToken().trim());
				    if (logger.isDebugEnabled()) {
					logger.debug("Formatter function = "
						+ formatterFunction);
				    }
				    if (!this.getFormatterFunctionsCache()
					    .containsKey(mappedMethod)) {
					this.getFormatterFunctionsCache()
						.put(mappedMethod,
							new ArrayList<FormatterFunction>());
				    }
				    if (!this.getFormatterFunctionsCache()
					    .get(mappedMethod)
					    .contains(formatterFunction)) {
					this.getFormatterFunctionsCache()
						.get(mappedMethod)
						.add(formatterFunction);
				    }
				}
			    } else {
				FormatterFunction formatterFunction = new FormatterFunction(
					formatterParameters);
				if (logger.isDebugEnabled()) {
				    logger.debug("Formatter function = "
					    + formatterFunction);
				}
				if (!this.getFormatterFunctionsCache()
					.containsKey(mappedMethod)) {
				    this.getFormatterFunctionsCache().put(
					    mappedMethod,
					    new ArrayList<FormatterFunction>());
				}
				if (!this.getFormatterFunctionsCache()
					.get(mappedMethod)
					.contains(formatterFunction)) {
				    this.getFormatterFunctionsCache()
					    .get(mappedMethod)
					    .add(formatterFunction);
				}
			    }
			}

		    } else {
			mappedMethod = mappedMethodBuffer;
		    }

		    try {
			String modelValue = null;
			if (this.getModelObjects().containsKey(objectName)) {
			    Method method = null;
			    VelocityModelObject velocityModelObject = this
				    .getModelObjects().get(objectName);
			    try {
				method = velocityModelObject.getObjectClass()
					.getDeclaredMethod(mappedMethod);
				modelValue = String.valueOf(method
					.invoke(velocityModelObject
						.getInstance()));
			    } catch (NoSuchMethodException e) {
				method = velocityModelObject.getObjectClass()
					.getSuperclass()
					.getDeclaredMethod(mappedMethod);
				modelValue = String.valueOf(method
					.invoke(velocityModelObject
						.getInstance()));
			    }
			}
			if (modelValue != null) {
			    if (result == null) {
				result = new Vector<VelocityValue>();
			    }
			    result.add(new VelocityValue(key, applyFormatters(
				    modelValue, mappedMethod, formattersUtil)));
			}
		    } catch (Exception e) {
			logger.error("Error while getting mapper method '"
				+ mappedMethod + "'.", e);
		    }
		}
	    }
	}

	return result;

    }

    private Properties getValuesMapper(PplMailType mailType, MailPart mailPart,
	    Map<String, String> templates) {

	Properties result = null;

	String mapper = "";

	if (mailPart.equals(MailPart.subject)) {
	    mapper = "subject_mapper";
	} else {
	    mapper = "body_mapper";
	}

	if (templates.containsKey(mapper)) {
	    String mapperContent = String.valueOf(templates.get(mapper));
	    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
		    mapperContent.getBytes());
	    result = new Properties();
	    try {
		result.load(byteArrayInputStream);
	    } catch (IOException e) {
		logger.error("Unable to retrieve mapper content.", e);
	    }
	}

	return result;

    }

    private String applyFormatters(final String modelValue,
	    final String mappedMethod, FormattersUtil formattersUtil)
	    throws FormatterException {

	String result = modelValue;

	if (this.getFormatterFunctionsCache().containsKey(mappedMethod)) {
	    List<FormatterFunction> fromatterFunctions = this
		    .getFormatterFunctionsCache().get(mappedMethod);
	    try {
		Iterator<FormatterFunction> formattersIterator = fromatterFunctions
			.iterator();
		while (formattersIterator.hasNext()) {
		    FormatterFunction formatterFunction = formattersIterator
			    .next();

		    Method formatterMethod = null;
		    Method[] methods = FormattersUtil.class.getMethods();
		    for (Method method : methods) {
			if (method.getName().equalsIgnoreCase(
				formatterFunction.getMethod())) {
			    formatterMethod = method;
			    break;
			}
		    }
		    if (formatterMethod != null) {

			@SuppressWarnings("unchecked")
			ArrayList<String> workParameters = (ArrayList<String>) ((ArrayList<String>) formatterFunction
				.getParameters()).clone();

			workParameters.add(0, result);
			String buffer = (String) formatterMethod.invoke(
				formattersUtil, workParameters.toArray());
			result = buffer;
		    }
		}
	    } catch (Exception e) {
		throw new FormatterException();
	    }
	}

	return result;

    }

    public enum MailPart {

	subject("subject"), body("body");

	private String value;

	private MailPart(final String value) {
	    this.setValue(value);
	}

	/**
	 * @param value
	 *            the value to set
	 */
	private void setValue(String value) {
	    this.value = value;
	}

	/**
	 * @return the value
	 */
	public final String getValue() {
	    return this.value;
	}

    }

}
