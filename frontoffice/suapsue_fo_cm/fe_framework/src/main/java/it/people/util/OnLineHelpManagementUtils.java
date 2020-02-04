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

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.people.db.DBConnector;
import it.people.db.fedb.Service;
import it.people.db.fedb.ServiceFactory;
import it.people.process.AbstractPplProcess;
import it.people.taglib.StepOnLineHelpReaderTag;
import it.people.wrappers.IRequestWrapper;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova Sep
 *         28, 2013 11:31:40 AM
 */
public class OnLineHelpManagementUtils {

    private static Logger logger = LogManager
	    .getLogger(OnLineHelpManagementUtils.class);

    public static final Action editAction = new OnLineHelpManagementUtils().new Action(
	    "onLineHelpManagement.edit", "label.onLineHelpManagement.edit");
    public static final Action saveAction = new OnLineHelpManagementUtils().new Action(
	    "onLineHelpManagement.save", "label.onLineHelpManagement.save");
    public static final Action previewAction = new OnLineHelpManagementUtils().new Action(
	    "onLineHelpManagement.preview",
	    "label.onLineHelpManagement.preview");
    public static final Action cancelAction = new OnLineHelpManagementUtils().new Action(
	    "onLineHelpManagement.cancel", "label.onLineHelpManagement.cancel");
    public static final Action removeAction = new OnLineHelpManagementUtils().new Action(
	    "onLineHelpManagement.remove", "label.onLineHelpManagement.remove");
    public static final Action insertAction = new OnLineHelpManagementUtils().new Action(
	    "onLineHelpManagement.insert", "label.onLineHelpManagement.insert");
    public static final Action closePreviewAction = new OnLineHelpManagementUtils().new Action(
	    "onLineHelpManagement.closePreview",
	    "label.onLineHelpManagement.closePreview");

    private OnLineHelpManagementUtils() {

    }

    public static final boolean updateHelp(AbstractPplProcess process,
	    IRequestWrapper request) {

	boolean result = true;

	String communeKey = process.getContext().getCommune().getKey();
	String _package = process.getProcessName();
	String viewName = process.getProcessWorkflow().getView().getName();
	String activityId = process.getView().getCurrentActivity().getId();
	String stepId = process.getView().getCurrentActivity().getCurrentStep()
		.getId();

	String helpText = request.getUnwrappedRequest().getParameter(
		OnLineHelpManagementUtils.Controls.TEXT_AREA);
//	boolean isShared = request.getUnwrappedRequest().getParameter(
//		OnLineHelpManagementUtils.Controls.IS_SHARED_CHECKBOX) != null;
	boolean isShared = true;
	boolean isActive = request.getUnwrappedRequest().getParameter(
		OnLineHelpManagementUtils.Controls.IS_ACTIVE_CHECKBOX) != null;
	int helpId = (Integer) request.getUnwrappedRequest().getSession()
		.getAttribute(OnLineHelpManagementUtils.SessionKeys.HELP_ID);

	Connection connection = null;
	PreparedStatement preparedStatement = null;

	try {
	    connection = DBConnector.getInstance().connect(DBConnector.FEDB);
	    if (helpId > 0) {
		preparedStatement = connection
			.prepareStatement(getQuery("updateOnLineHelpOnDb"));
		preparedStatement.setString(1, helpText);
		preparedStatement.setInt(2, helpId);
	    } else {
		preparedStatement = connection
			.prepareStatement(getQuery((isShared ? "insertCommonOnLineHelpOnDb"
				: "insertOnLineHelpOnDb")));
		preparedStatement.setString(1, viewName);
		preparedStatement.setString(2, activityId);
		preparedStatement.setString(3, stepId);
		preparedStatement.setString(4, helpText);
		preparedStatement.setInt(5, (isActive ? 1 : 0));
		if (!isShared) {
		    Service serviceConf = (new ServiceFactory()).getService(
			    _package, communeKey);
		    preparedStatement.setInt(6, serviceConf.getId());
		}
	    }
	    result = (preparedStatement.executeUpdate() == 1);
	} catch (Exception e) {
	    logger.error("Error while removing help from db.", e);
	    result = false;
	} finally {
	    try {
		if (preparedStatement != null) {
		    preparedStatement.close();
		}
		if (connection != null) {
		    connection.close();
		}
	    } catch (Exception ignore) {
	    }
	}

	return result;

    }

    public static final boolean removeHelp(AbstractPplProcess process,
	    Integer helpId) {

	boolean result = true;

	Connection connection = null;
	PreparedStatement preparedStatement = null;

	try {
	    connection = DBConnector.getInstance().connect(DBConnector.FEDB);
	    preparedStatement = connection
		    .prepareStatement(getQuery("removeOnLineHelpOnDb"));
	    preparedStatement.setInt(1, helpId.intValue());
	    result = (preparedStatement.executeUpdate() == 1);
	} catch (Exception e) {
	    logger.error("Error while removing help from db.", e);
	    result = false;
	} finally {
	    try {
		if (preparedStatement != null) {
		    preparedStatement.close();
		}
		if (connection != null) {
		    connection.close();
		}
	    } catch (Exception ignore) {
	    }
	}

	return result;

    }

    public static final void storeHelpBuffer(IRequestWrapper request) {

	String helpText = request.getUnwrappedRequest().getParameter(
		OnLineHelpManagementUtils.Controls.TEXT_AREA);
//	boolean isShared = request.getUnwrappedRequest().getParameter(
//		OnLineHelpManagementUtils.Controls.IS_SHARED_CHECKBOX) != null;
	boolean isShared = true;
	boolean isActive = request.getUnwrappedRequest().getParameter(
		OnLineHelpManagementUtils.Controls.IS_ACTIVE_CHECKBOX) != null;
	request.getUnwrappedRequest()
		.getSession()
		.setAttribute(
			OnLineHelpManagementUtils.SessionKeys.HELP_TEXT_BUFFER,
			helpText);
	request.getUnwrappedRequest()
		.getSession()
		.setAttribute(
			OnLineHelpManagementUtils.SessionKeys.HELP_IS_SHARED_BUFFER,
			isShared);
	request.getUnwrappedRequest()
		.getSession()
		.setAttribute(
			OnLineHelpManagementUtils.SessionKeys.HELP_IS_ACTIVE_BUFFER,
			isActive);

    }

    public static boolean isHelpBufferActive(ServletRequest request) {

	return ((HttpServletRequest) request).getSession().getAttribute(
		OnLineHelpManagementUtils.SessionKeys.HELP_TEXT_BUFFER) != null;

    }

    public static boolean isFromPreview(ServletRequest request) {

	return request
		.getParameter(OnLineHelpManagementUtils.Controls.TEXT_AREA) != null;

    }

    public static StepOnLineHelpReaderTag.DbHelpData updateDbHelpData(
	    ServletRequest request, StepOnLineHelpReaderTag.DbHelpData helpData) {

	String helpText = "";
//	boolean isShared = false;
	boolean isShared = true;
	boolean isActive = false;

	HttpServletRequest httpServletRequest = (HttpServletRequest) request;

	if (httpServletRequest.getSession().getAttribute(
		OnLineHelpManagementUtils.SessionKeys.HELP_TEXT_BUFFER) != null) {
	    helpText = String
		    .valueOf(httpServletRequest
			    .getSession()
			    .getAttribute(
				    OnLineHelpManagementUtils.SessionKeys.HELP_TEXT_BUFFER));
//	    isShared = (Boolean) httpServletRequest
//		    .getSession()
//		    .getAttribute(
//			    OnLineHelpManagementUtils.SessionKeys.HELP_IS_SHARED_BUFFER);
	    isActive = (Boolean) httpServletRequest
		    .getSession()
		    .getAttribute(
			    OnLineHelpManagementUtils.SessionKeys.HELP_IS_ACTIVE_BUFFER);
	} else {
	    helpText = request
		    .getParameter(OnLineHelpManagementUtils.Controls.TEXT_AREA);
//	    isShared = request
//		    .getParameter(OnLineHelpManagementUtils.Controls.IS_SHARED_CHECKBOX) != null;
	    isActive = request
		    .getParameter(OnLineHelpManagementUtils.Controls.IS_ACTIVE_CHECKBOX) != null;
	}

	helpData.setHelpContent(helpText);
	helpData.setShared(isShared);
	helpData.setActive(isActive);

	return helpData;

    }

    private static String getQuery(String queryKey) {

	String result = "";

	try {
	    Properties props = new Properties();
	    props.loadFromXML(StepOnLineHelpReaderTag.class
		    .getResourceAsStream("/it/people/resources/TaglibQueries.xml"));
	    result = props.getProperty(queryKey);
	} catch (InvalidPropertiesFormatException e) {
	} catch (IOException e) {
	}

	return result;

    }

    public class SessionKeys {

	public static final String EDIT_ACTIVE = "editActive";

	public static final String PREVIEW_ACTIVE = "previewActive";

	public static final String HELP_TEXT_BUFFER = "helpTextBuffer";

	public static final String HELP_IS_SHARED_BUFFER = "helpIsSharedBuffer";

	public static final String HELP_IS_ACTIVE_BUFFER = "helpIsActiveBuffer";

	public static final String HELP_ID = "helpId";

    }

    public class Controls {

	public static final String IS_SHARED_CHECKBOX = "onLineHelpManagement.shared.help";

	public static final String IS_ACTIVE_CHECKBOX = "onLineHelpManagement.active.help";

	public static final String TEXT_AREA = "onLineHelpManagement.text";

    }

    public class Action {

	private String property;

	private String labelKey;

	public Action(final String property, final String labelKey) {
	    this.setProperty(property);
	    this.setLabelKey(labelKey);
	}

	/**
	 * @param property
	 *            the property to set
	 */
	private void setProperty(String property) {
	    this.property = property;
	}

	/**
	 * @param labelKey
	 *            the labelKey to set
	 */
	private void setLabelKey(String labelKey) {
	    this.labelKey = labelKey;
	}

	/**
	 * @return the property
	 */
	public final String getProperty() {
	    return property;
	}

	/**
	 * @return the labelKey
	 */
	public final String getLabelKey() {
	    return labelKey;
	}

    }

}
