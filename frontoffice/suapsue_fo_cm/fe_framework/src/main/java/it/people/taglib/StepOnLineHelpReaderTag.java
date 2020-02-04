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
package it.people.taglib;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;
import java.util.Locale;
import java.util.Properties;

import it.people.IStep;
import it.people.db.DBConnector;
import it.people.exceptions.PeopleDBException;
import it.people.layout.ButtonKey;
import it.people.process.AbstractPplProcess;
import it.people.util.MessageBundleHelper;
import it.people.util.OnLineHelpManagementUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica
 * 
 */
public class StepOnLineHelpReaderTag extends TagSupport {

    private static final long serialVersionUID = -4459800346162715413L;

    protected static Logger logger = Logger
	    .getLogger(StepOnLineHelpReaderTag.class);

    public static final String DB_HELP_KEY = IStep.READ_ON_LINE_HELP_FROM_DB;
    public static final String JSP_EXTENSION = ".jsp";
    public static final String HELP_CONTENT_QUERY_KEY = "helpContentQuery";
    public static final String COMMON_HELP_CONTENT_QUERY_KEY = "commonHelpContentQuery";

    private static String HELP_NOT_AVAILABLE_MESSAGE_KEY = "label.onlinehelp.notAvailable";

    protected String processName;

    private AbstractPplProcess process;

    // private Connection fedbConnection;

    /**
     * @return
     */
    public String getProcessName() {
	return processName;
    }

    /**
     * @param name
     */
    public void setProcessName(String name) {
	this.processName = name;
    }

    /**
     * @return the process
     */
    private AbstractPplProcess getProcess() {
	return process;
    }

    /**
     * @param process
     *            the process to set
     */
    private void setProcess(AbstractPplProcess process) {
	this.process = process;
    }

    // /**
    // * @return the fedbConnection
    // */
    // private Connection getFedbConnection() {
    // return fedbConnection;
    // }
    //
    // /**
    // * @param fedbConnection the fedbConnection to set
    // */
    // private void setFedbConnection(Connection fedbConnection) {
    // this.fedbConnection = fedbConnection;
    // }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    public int doStartTag() throws JspException {

	this.setProcess((AbstractPplProcess) pageContext
		.findAttribute(processName));

	String helpUrl = this.getProcess().getView().getCurrentActivity()
		.getCurrentStep().getHelpUrl();
	if (helpUrl != null && helpUrl.trim().equalsIgnoreCase("")) {
	    helpUrl = DB_HELP_KEY;
	}
	DbHelpData helpContent = null;

	// DB_HELP_KEY constant is automatically inserted by
	// it.people.process.workflow.xml.XMLWorkFlow
	// if workflow.xml doesn't define a JSP page for the step
	if (helpUrl.endsWith(DB_HELP_KEY)) {
	    // try {
	    // this.setFedbConnection(DBConnector.getInstance().connect(DBConnector.FEDB));
	    // helpContent = this.readDbContent(this.getProcess(),
	    // this.getFedbConnection());
	    helpContent = this.readDbContent(this.getProcess());
	    // } catch (PeopleDBException e) {
	    // }
	} else if (helpUrl.toLowerCase().endsWith(JSP_EXTENSION)) {
	    helpContent = new DbHelpData(this.writeJspContent(helpUrl), true,
		    true, true, -1);
	}

	if (helpContent != null
		|| (helpContent == null
			&& this.getProcess().getContext().getUser()
				.isPeopleAdmin() && pageContext
			.getSession()
			.getAttribute(
				OnLineHelpManagementUtils.SessionKeys.EDIT_ACTIVE) != null)) {
	    try {
		if (this.getProcess().getContext().getUser().isPeopleAdmin()) {

		    if (helpContent == null) {
			helpContent = new DbHelpData("", false, false, false, 0);
		    }

		    pageContext.getSession().setAttribute(
			    OnLineHelpManagementUtils.SessionKeys.HELP_ID,
			    new Integer(helpContent.getId()));

		    if (OnLineHelpManagementUtils.isFromPreview(pageContext
			    .getRequest())
			    || OnLineHelpManagementUtils
				    .isHelpBufferActive(pageContext
					    .getRequest())) {
			OnLineHelpManagementUtils.updateDbHelpData(
				pageContext.getRequest(), helpContent);
		    }

		    if (pageContext.getSession().getAttribute(
			    OnLineHelpManagementUtils.SessionKeys.EDIT_ACTIVE) != null) {
			String saveLabel = MessageBundleHelper.message(
				OnLineHelpManagementUtils.saveAction
					.getLabelKey(), null, this.getProcess()
					.getCommune().getOid(), Locale
					.getDefault());
			String cancelLabel = MessageBundleHelper.message(
				OnLineHelpManagementUtils.cancelAction
					.getLabelKey(), null, this.getProcess()
					.getCommune().getOid(), Locale
					.getDefault());
			String previewLabel = MessageBundleHelper.message(
				OnLineHelpManagementUtils.previewAction
					.getLabelKey(), null, this.getProcess()
					.getCommune().getOid(), Locale
					.getDefault());
			String allNodesLabel = MessageBundleHelper.message(
				"label.onLineHelpManagement.shared.help", null,
				this.getProcess().getCommune().getOid(),
				Locale.getDefault());
			String activeLabel = MessageBundleHelper.message(
				"label.onLineHelpManagement.active.help", null,
				this.getProcess().getCommune().getOid(),
				Locale.getDefault());
			pageContext
				.getOut()
				.write("<textarea name=\""
					+ OnLineHelpManagementUtils.Controls.TEXT_AREA
					+ "\" id=\""
					+ OnLineHelpManagementUtils.Controls.TEXT_AREA
					+ "\" rows=\"20\" cols=\"80\">");
			pageContext.getOut()
				.write(helpContent.getHelpContent());
			pageContext.getOut().write("</textarea>");

			pageContext
				.getOut()
				.write("<div style=\"text-align: right;margin-top: 10px;\">");
			pageContext
				.getOut()
				.write("<label for=\""
					+ OnLineHelpManagementUtils.Controls.IS_ACTIVE_CHECKBOX
					+ "\" />" + activeLabel + "</label>");
			pageContext
				.getOut()
				.write("<input type=\"checkbox\" name=\""
					+ OnLineHelpManagementUtils.Controls.IS_ACTIVE_CHECKBOX
					+ "\" id=\""
					+ OnLineHelpManagementUtils.Controls.IS_ACTIVE_CHECKBOX
					+ "\""
					+ (helpContent.isActive() ? "checked=\"checked\""
						: "") + " />");
			/*
			pageContext
				.getOut()
				.write("<label for=\""
					+ OnLineHelpManagementUtils.Controls.IS_SHARED_CHECKBOX
					+ "\" />" + allNodesLabel + "</label>");
			pageContext
				.getOut()
				.write("<input type=\"checkbox\" name=\""
					+ OnLineHelpManagementUtils.Controls.IS_SHARED_CHECKBOX
					+ "\" id=\""
					+ OnLineHelpManagementUtils.Controls.IS_SHARED_CHECKBOX
					+ "\""
					+ (helpContent.isShared() ? "checked=\"checked\""
						: "") + " />");
			*/
			pageContext.getOut().write("</div>");

			pageContext
				.getOut()
				.write("<div style=\"text-align: right;margin-top: 10px;\">");
			pageContext
				.getOut()
				.write("<input type=\"submit\" name=\""
					+ ButtonKey.ON_LINE_HELP_MANAGEMENT_LOOP_BACK
					+ "$"
					+ OnLineHelpManagementUtils.saveAction
						.getProperty()
					+ "\" id=\""
					+ ButtonKey.ON_LINE_HELP_MANAGEMENT_LOOP_BACK
					+ "_"
					+ OnLineHelpManagementUtils.saveAction
						.getProperty() + "\" value=\""
					+ saveLabel
					+ "\" class=\"btn\" />&nbsp;");
			pageContext
				.getOut()
				.write("<input type=\"submit\" name=\""
					+ ButtonKey.ON_LINE_HELP_MANAGEMENT_LOOP_BACK
					+ "$"
					+ OnLineHelpManagementUtils.previewAction
						.getProperty()
					+ "\" id=\""
					+ ButtonKey.ON_LINE_HELP_MANAGEMENT_LOOP_BACK
					+ "_"
					+ OnLineHelpManagementUtils.previewAction
						.getProperty() + "\" value=\""
					+ previewLabel
					+ "\" class=\"btn\" />&nbsp;");
			pageContext
				.getOut()
				.write("<input type=\"submit\" name=\""
					+ ButtonKey.ON_LINE_HELP_MANAGEMENT_LOOP_BACK
					+ "$"
					+ OnLineHelpManagementUtils.cancelAction
						.getProperty()
					+ "\" id=\""
					+ ButtonKey.ON_LINE_HELP_MANAGEMENT_LOOP_BACK
					+ "_"
					+ OnLineHelpManagementUtils.cancelAction
						.getProperty() + "\" value=\""
					+ cancelLabel + "\" class=\"btn\" />");

			pageContext.getOut().write("</div>");
		    } else if (pageContext
			    .getSession()
			    .getAttribute(
				    OnLineHelpManagementUtils.SessionKeys.PREVIEW_ACTIVE) != null) {
			String closePreviewLabel = MessageBundleHelper.message(
				OnLineHelpManagementUtils.closePreviewAction
					.getLabelKey(), null, this.getProcess()
					.getCommune().getOid(), Locale
					.getDefault());
			pageContext
				.getOut()
				.write(String
					.valueOf(pageContext
						.getSession()
						.getAttribute(
							OnLineHelpManagementUtils.SessionKeys.HELP_TEXT_BUFFER)));
			pageContext
				.getOut()
				.write("<div style=\"text-align: right;margin-top: 10px;\">");
			pageContext
				.getOut()
				.write("<input type=\"submit\" name=\""
					+ ButtonKey.ON_LINE_HELP_MANAGEMENT_LOOP_BACK
					+ "$"
					+ OnLineHelpManagementUtils.closePreviewAction
						.getProperty()
					+ "\" id=\""
					+ ButtonKey.ON_LINE_HELP_MANAGEMENT_LOOP_BACK
					+ "_"
					+ OnLineHelpManagementUtils.closePreviewAction
						.getProperty() + "\" value=\""
					+ closePreviewLabel
					+ "\" class=\"btn\" />");
			pageContext.getOut().write("</div>");
		    } else {
			String editLabel = MessageBundleHelper.message(
				OnLineHelpManagementUtils.editAction
					.getLabelKey(), null, this.getProcess()
					.getCommune().getOid(), Locale
					.getDefault());
			String removeLabel = MessageBundleHelper.message(
				OnLineHelpManagementUtils.removeAction
					.getLabelKey(), null, this.getProcess()
					.getCommune().getOid(), Locale
					.getDefault());
			pageContext.getOut()
				.write(helpContent.getHelpContent());
			pageContext
				.getOut()
				.write("<div style=\"text-align: right;margin-top: 10px;\">");
			pageContext
				.getOut()
				.write("<input type=\"submit\" name=\""
					+ ButtonKey.ON_LINE_HELP_MANAGEMENT_LOOP_BACK
					+ "$"
					+ OnLineHelpManagementUtils.editAction
						.getProperty()
					+ "\" id=\""
					+ ButtonKey.ON_LINE_HELP_MANAGEMENT_LOOP_BACK
					+ "_"
					+ OnLineHelpManagementUtils.editAction
						.getProperty() + "\" value=\""
					+ editLabel + "\" class=\"btn\" />");
			pageContext
				.getOut()
				.write("<input type=\"submit\" name=\""
					+ ButtonKey.ON_LINE_HELP_MANAGEMENT_LOOP_BACK
					+ "$"
					+ OnLineHelpManagementUtils.removeAction
						.getProperty()
					+ "\" id=\""
					+ ButtonKey.ON_LINE_HELP_MANAGEMENT_LOOP_BACK
					+ "_"
					+ OnLineHelpManagementUtils.removeAction
						.getProperty() + "\" value=\""
					+ removeLabel + "\" class=\"btn\" />");

			pageContext.getOut().write("</div>");
		    }
		} else {
		    pageContext.getOut().write(helpContent.getHelpContent());
		}
	    } catch (IOException e) {
	    }
	} else {
	    try {
		pageContext.getOut().write(
			MessageBundleHelper.message(
				HELP_NOT_AVAILABLE_MESSAGE_KEY, null, this
					.getProcess().getCommune().getOid(),
				Locale.getDefault()));
		if (this.getProcess().getContext().getUser().isPeopleAdmin()) {
		    String addLabel = MessageBundleHelper
			    .message(OnLineHelpManagementUtils.insertAction
				    .getLabelKey(), null, this.getProcess()
				    .getCommune().getOid(), Locale.getDefault());
		    pageContext
			    .getOut()
			    .write("<div style=\"text-align: right;margin-top: 10px;\">");
		    pageContext
			    .getOut()
			    .write("<input type=\"submit\" name=\""
				    + ButtonKey.ON_LINE_HELP_MANAGEMENT_LOOP_BACK
				    + "$"
				    + OnLineHelpManagementUtils.insertAction
					    .getProperty()
				    + "\" id=\""
				    + ButtonKey.ON_LINE_HELP_MANAGEMENT_LOOP_BACK
				    + "_"
				    + OnLineHelpManagementUtils.insertAction
					    .getProperty() + "\" value=\""
				    + addLabel + "\" class=\"btn\" />");
		    pageContext.getOut().write("</div>");
		}
	    } catch (IOException e) {
	    }
	}

	return super.doStartTag();

    }

    public DbHelpData readDbContent(AbstractPplProcess process) {

	DbHelpData result = null;
	String communeId = process.getCommune().getOid();
	String _package = process.getProcessName();
	String viewName = process.getProcessWorkflow().getView().getName();
	String activityId = process.getView().getCurrentActivity().getId();
	String stepId = process.getView().getCurrentActivity().getCurrentStep()
		.getId();
	String activeHelp = (this.getProcess().getContext().getUser()
		.isPeopleAdmin() ? "" : " AND soh.enabled = 1");

	if (logger.isDebugEnabled()) {
	    logger.debug("Reading on line help db content for step:\n"
		    + "\tcommuneId = '" + communeId + "';\n" + "\t_package = '"
		    + _package + "';\n" + "\tviewName = '" + viewName + "';\n"
		    + "\tactivityId = '" + activityId + "';\n" + "\tstepId = '"
		    + stepId + "';\n");
	}

	Connection connection = null;
	PreparedStatement preparedStatment = null;
	ResultSet resultSet = null;
	try {
	    connection = DBConnector.getInstance().connect(DBConnector.FEDB);
	    preparedStatment = connection
		    .prepareStatement(getQuery(COMMON_HELP_CONTENT_QUERY_KEY)
			    + activeHelp);
	    preparedStatment.setString(1, _package);
	    preparedStatment.setString(2, viewName);
	    preparedStatment.setString(3, activityId);
	    preparedStatment.setString(4, stepId);
	    resultSet = preparedStatment.executeQuery();
	    if (resultSet.next()) {
		result = new DbHelpData(resultSet.getString(2), true,
			(resultSet.getInt(3) == 1), false, resultSet.getInt(1));
	    } else {
		preparedStatment = connection
			.prepareStatement(getQuery(HELP_CONTENT_QUERY_KEY)
				+ activeHelp);
		preparedStatment.setString(1, communeId);
		preparedStatment.setString(2, _package);
		preparedStatment.setString(3, viewName);
		preparedStatment.setString(4, activityId);
		preparedStatment.setString(5, stepId);
		resultSet = preparedStatment.executeQuery();
		if (resultSet.next()) {
		    result = new DbHelpData(resultSet.getString(2), true,
			    (resultSet.getInt(3) == 1), false,
			    resultSet.getInt(1));
		}
	    }
	} catch (SQLException e) {
	    logger.warn("Error while reading on line help from db.", e);
	} catch (PeopleDBException e) {
	    logger.warn("Error while reading on line help from db.", e);
	} finally {
	    try {
		if (preparedStatment != null) {
		    preparedStatment.close();
		}
		if (resultSet != null) {
		    resultSet.close();
		}
		if (connection != null) {
		    connection.close();
		}
	    } catch (SQLException e) {
	    }
	}

	return result;

    }

    public String writeJspContent(String helpUrl) {

	String result = null;

	return result;

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.jsp.tagext.TagSupport#doAfterBody()
     */
    public int doAfterBody() throws JspException {

	return super.doAfterBody();

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    public int doEndTag() throws JspException {

	return super.doEndTag();

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.jsp.tagext.TagSupport#release()
     */
    public void release() {

	super.release();

	processName = null;

	// if (getFedbConnection() != null) {
	// try {
	// getFedbConnection().close();
	// } catch (SQLException e) {
	// }
	// }
	// setFedbConnection(null);

    }

    private String getQuery(String queryKey) {

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

    /**
     * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
     *         Sep 28, 2013 5:16:50 PM
     */
    public class DbHelpData {

	/**
		 * 
		 */
	private String helpContent = null;

	/**
		 * 
		 */
	private boolean shared = false;

	/**
		 * 
		 */
	private boolean active = false;

	/**
		 * 
		 */
	private boolean fromJsp = false;

	/**
		 * 
		 */
	private int id = 0;

	/**
	 * @param helpContent
	 * @param shared
	 * @param fromJsp
	 */
	public DbHelpData(String helpContent, boolean shared, boolean active,
		boolean fromJsp, int id) {
	    this.setId(id);
	    this.setHelpContent(helpContent);
	    this.setShared(shared);
	    this.setActive(active);
	    this.setFromJsp(fromJsp);
	}

	/**
	 * @param helpContent
	 *            the helpContent to set
	 */
	public void setHelpContent(String helpContent) {
	    this.helpContent = helpContent;
	}

	/**
	 * @param isShared
	 *            the isShared to set
	 */
	public void setShared(boolean shared) {
	    this.shared = shared;
	}

	/**
	 * @param isActive
	 *            the isActive to set
	 */
	public void setActive(boolean active) {
	    this.active = active;
	}

	/**
	 * @param fromJsp
	 */
	public void setFromJsp(boolean fromJsp) {
	    this.fromJsp = fromJsp;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	private final void setId(int id) {
	    this.id = id;
	}

	/**
	 * @return the helpContent
	 */
	public final String getHelpContent() {
	    return helpContent;
	}

	/**
	 * @return the shared
	 */
	public final boolean isShared() {
	    return shared;
	}

	/**
	 * @return the active
	 */
	public final boolean isActive() {
	    return active;
	}

	/**
	 * @return
	 */
	public final boolean isFromJsp() {
	    return fromJsp;
	}

	/**
	 * @return the id
	 */
	public final int getId() {
	    return id;
	}

    }

}
