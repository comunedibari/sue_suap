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
package it.people;

import it.people.core.PplACE;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.util.IUserPanel;
import it.people.util.OnLineHelpManagementUtils;
import it.people.util.PplProperty;
import it.people.util.frontend.WorkflowController;
import it.people.validator.PeopleValidatorUtil;
import it.people.wrappers.IRequestWrapper;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.ValidatorResult;
import org.apache.commons.validator.ValidatorResults;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Sep 3, 2003 Time: 2:05:59 PM To
 * change this template use Options | File Templates.
 */
public class Step implements IStep {

    private transient it.people.logger.Logger serviceLogger = null;
    private transient Logger frameworkLogger = Logger.getLogger(Step.class);

    // < CCD - 2012.03.18
    public static final String REQUIRED_OPERATOR_DATA_STEP_ID = "FWROD";
    public static final String PRIVACY_DISCLAIMER_STEP_ID = "FWPD";
    public static final String PRE_PRIVACY_DISCLAIMER_STEP_ID = "FWPREPD";

    // CCD - 2012.03.18 >

    public Step() {
	m_jspPath = null;
	m_helpUrl = null;
	m_descriptors = new HashMap();
	m_state = StepState.ACTIVE;
	m_accessController = null;
	m_ACEs = null;
	m_id = null;
	m_name = null;
    }

    public void service(AbstractPplProcess process, IRequestWrapper request)
	    throws IOException, ServletException {
	frameworkLogger.debug("Step Execute Business Logic STEP ["
		+ this.getFullIdentifier() + "] Class ["
		+ this.getClass().getName() + "]");
    }

    public void loopBack(AbstractPplProcess process, IRequestWrapper request,
	    String propertyName, int index) throws IOException,
	    ServletException {
	frameworkLogger.debug("Step Execute LoopBackOnStep STEP ["
		+ this.getFullIdentifier() + "] Class ["
		+ this.getClass().getName() + "]");
    }

    /**
     * @return Returns the dto.
     */
    public String getDto() {
	return dto;
    }

    /**
     * @param dto
     *            The dto to set.
     */
    public void setDto(String dto) {
	this.dto = dto;
    }

    public Step(String p_jspPath, String p_helpUrl, StepState state) {
	this(p_jspPath, p_helpUrl, state, null);
    }

    public Step(String p_jspPath, String p_helpUrl,
	    WorkflowController controller) {
	this(p_jspPath, p_helpUrl, StepState.ACTIVE, controller);
    }

    public Step(String p_jspPath, String p_helpUrl, StepState state,
	    WorkflowController controller) {
	this(p_jspPath, p_helpUrl, state, controller, null);
    }

    public Step(String m_jspPath, String m_helpUrl, StepState state,
	    WorkflowController controller, PplACE[] ACL) {
	this();
	this.m_accessController = controller;
	this.m_jspPath = m_jspPath;
	this.m_helpUrl = m_helpUrl;
	this.m_state = state;
	this.m_ACEs = ACL;
    }

    public StepState getState() {
	return m_state;
    }

    public void setState(StepState p_state) {
	m_state = p_state;
    }

    public boolean isActive() {
	return StepState.ACTIVE.equals(m_state)
		|| StepState.COMPLETED.equals(m_state);
    }

    public String getJspPath() {
	return m_jspPath;
    }

    public void setJspPath(String m_jspPath) {
	this.m_jspPath = m_jspPath;
    }

    public String getHelpUrl() {
	return m_helpUrl;
    }

    public void setHelpUrl(String m_helpUrl) {
	this.m_helpUrl = m_helpUrl;
    }

    public WorkflowController getAccessController() {
	return m_accessController;
    }

    public void setAccessController(WorkflowController p_accessController) {
	m_accessController = p_accessController;
    }

    public final boolean validate(AbstractPplProcess process,
	    ServletContext application, HttpServletRequest request,
	    IValidationErrors errors) throws ParserException {

	if (frameworkLogger.isDebugEnabled())
	    frameworkLogger
		    .debug("Step::validate() - Avvio validazione - processo["
			    + getParentView().getName() + "] step ["
			    + this.getFullIdentifier() + "]");

	Iterator iter = m_descriptors.keySet().iterator();
	if (!iter.hasNext()) {
	    setState(StepState.ACTIVE);
	    if (frameworkLogger.isDebugEnabled())
		frameworkLogger
			.debug("Step::validate() - Nessuna validazione richiesta");
	    return false;
	}

	boolean returnValue = true;

	Validator validator = PeopleValidatorUtil.getValidatorForStep(this,
		process, application, request, (ActionErrors) errors);
	if (frameworkLogger.isDebugEnabled())
	    frameworkLogger
		    .debug("Step::validate() - validator caricato - validator = ["
			    + validator + "]");

	try {

	    ValidatorResults validatorResults = validator.validate();
	    if (frameworkLogger.isDebugEnabled())
		frameworkLogger
			.debug("Step::validate() - validator results ottenuto");

	    // TODO: verificare la possibilita' di inglobare tutto il codice
	    // seguente fino alla fine del while dentro un if (debug.enabled())

	    Iterator it = validatorResults.get();
	    while (it.hasNext()) {
		String fld = (String) it.next();

		ValidatorResult validatorResult = validatorResults
			.getValidatorResult(fld);
		frameworkLogger.debug("--> Field [" + fld + "]");

		Map actionMap = validatorResult.getActionMap();
		Set keySet = actionMap.keySet();
		Iterator keyIterator = keySet.iterator();
		while (keyIterator.hasNext()) {
		    String keyItem = (String) keyIterator.next();
		    frameworkLogger.debug("      Rule [" + keyItem + "] ["
			    + validatorResult.isValid(keyItem) + "]");
		}
	    }

	    frameworkLogger.debug("Step::validate:: SONO STATE RISCONTRATI ["
		    + errors.getSize() + "] ERRORI");

	    if (!errors.isEmpty()) {
		setState(StepState.ACTIVE);
		returnValue = false;
	    }
	} catch (ValidatorException ve) {
	    throw new ParserException(ve.getMessage());
	}
	if (returnValue)
	    setState(StepState.COMPLETED);
	return returnValue;
    }

    public boolean logicalValidate(AbstractPplProcess process,
	    IRequestWrapper request, IValidationErrors errors)
	    throws ParserException {
	frameworkLogger.debug("Perform Logical Validation on Step");
	return true;
    }

    public void addField(PplProperty attr) {
	m_descriptors.put(attr.getName(), attr);
    }

    public void addField(PropertyDescriptor propDesc) {
	m_descriptors.put(propDesc.getName(), propDesc);
    }

    public void addField(String propertyName, PropertyDescriptor propDesc) {
	m_descriptors.put(propertyName, propDesc);
    }

    public PplACE[] getACL() {
	return m_ACEs;
    }

    public boolean containsField(String propertyName) {
	return m_descriptors.containsKey(propertyName);
    }

    public String[] getFields() {
	return (String[]) m_descriptors.keySet().toArray(new String[0]);
    }

    public String getName() {
	return m_name;
    }

    public void setName(String name) {
	m_name = name;
    }

    public String getId() {
	return m_id;
    }

    public void setId(String id) {
	m_id = id;
    }

    public IView getParentView() {
	return m_parentView;
    }

    public void setParentView(IView view) {
	m_parentView = view;
    }

    public String getFullIdentifier() {
	return getParentView().getName().toLowerCase() + ":" + getId();
    }

    /**
     * Registra un messaggio di log al livello debug
     * 
     * @deprecated utilizzare AbstractPplProcess.debug(String message)
     * @param process
     * @param message
     */
    public void debug(AbstractPplProcess process, String message) {
	process.debug(message);
    }

    /**
     * Registra un messaggio di log al livello info
     * 
     * @deprecated utilizzare AbstractPplProcess.info(String message)
     * @param process
     * @param message
     */
    public void info(AbstractPplProcess process, String message) {
	process.info(message);
    }

    /**
     * Registra un messaggio di log al livello warning
     * 
     * @deprecated utilizzare AbstractPplProcess.warn(String message)
     * @param process
     * @param message
     */
    public void warn(AbstractPplProcess process, String message) {
	process.warn(message);
    }

    /**
     * Registra un messaggio di log al livello error
     * 
     * @deprecated utilizzare AbstractPplProcess.error(String message)
     * @param process
     * @param message
     */
    public void error(AbstractPplProcess process, String message) {
	process.error(message);
    }

    /**
     * Registra un messaggio di log al livello fatal
     * 
     * @deprecated utilizzare AbstractPplProcess.fatal(String message)
     * @param process
     * @param message
     */
    public void fatal(AbstractPplProcess process, String message) {
	process.fatal(message);
    }

    /*
     * 
     * @see it.people.IStep#defineControl(it.people.process.AbstractPplProcess,
     * it.people.wrappers.IRequestWrapper) L'implementazione di default di
     * define control non fa nulla;
     */
    public void defineControl(AbstractPplProcess process,
	    IRequestWrapper request) {
    }

    private IView m_parentView;
    private String m_id;
    private String m_name;
    private String m_jspPath;
    private String m_helpUrl;
    private StepState m_state;

    private HashMap m_descriptors;
    private WorkflowController m_accessController;
    private PplACE[] m_ACEs;
    private String dto = null;

    /* Aggiunta per visualizzazione help contestuale dello step */
    public boolean contextualHelpActive = false;
    public String contextualHelpText = "";

    /* Aggiunta per visualizzazione user functions panel */
    public boolean userPanelActive = false;

    public String userPanelViewActive = "";

    /*
     * (non-Javadoc)
     * 
     * @see it.people.IStep#getContextualHelpText()
     */
    public String getContextualHelpText() {
	return this.contextualHelpText;
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.IStep#isContextualHelpActive()
     */
    public boolean isContextualHelpActive() {
	return this.contextualHelpActive;
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.IStep#setContextualHelpActive(boolean)
     */
    public void setContextualHelpActive(boolean contextualHelpActive) {
	this.contextualHelpActive = contextualHelpActive;
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.IStep#setContextualHelpText(java.lang.String)
     */
    public void setContextualHelpText(String contextualHelpText) {
	this.contextualHelpText = contextualHelpText;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.people.IStep#onLineHelpLoopBack(it.people.process.AbstractPplProcess,
     * it.people.wrappers.IRequestWrapper)
     */
    public final void onLineHelpLoopBack(AbstractPplProcess process,
	    IRequestWrapper request) {
	if (this.isContextualHelpActive()) {
	    this.setContextualHelpActive(false);
	    request.getUnwrappedRequest()
		    .getSession()
		    .removeAttribute(
			    OnLineHelpManagementUtils.SessionKeys.EDIT_ACTIVE);
	} else {
	    this.setContextualHelpActive(true);
	}
	try {
	    this.service(process, request);
	} catch (IOException e) {

	} catch (ServletException e) {

	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.IStep#onLineHelpManagementLoopBack(it.people.process.
     * AbstractPplProcess, it.people.wrappers.IRequestWrapper, java.lang.String)
     */
    public void onLineHelpManagementLoopBack(AbstractPplProcess process,
	    IRequestWrapper request, String propertyName) {

	if (OnLineHelpManagementUtils.editAction.getProperty()
		.equalsIgnoreCase(propertyName)) {
	    request.getUnwrappedRequest()
		    .getSession()
		    .setAttribute(
			    OnLineHelpManagementUtils.SessionKeys.EDIT_ACTIVE,
			    true);
	} else if (OnLineHelpManagementUtils.previewAction.getProperty()
		.equalsIgnoreCase(propertyName)) {
	    request.getUnwrappedRequest()
		    .getSession()
		    .setAttribute(
			    OnLineHelpManagementUtils.SessionKeys.PREVIEW_ACTIVE,
			    true);
	    request.getUnwrappedRequest()
		    .getSession()
		    .removeAttribute(
			    OnLineHelpManagementUtils.SessionKeys.EDIT_ACTIVE);
	    OnLineHelpManagementUtils.storeHelpBuffer(request);
	} else if (OnLineHelpManagementUtils.saveAction.getProperty()
		.equalsIgnoreCase(propertyName)) {
	    request.getUnwrappedRequest()
		    .getSession()
		    .removeAttribute(
			    OnLineHelpManagementUtils.SessionKeys.EDIT_ACTIVE);
	    OnLineHelpManagementUtils.updateHelp(process, request);
	    request.getUnwrappedRequest()
		    .getSession()
		    .removeAttribute(
			    OnLineHelpManagementUtils.SessionKeys.HELP_ID);
	    request.getUnwrappedRequest()
		    .getSession()
		    .removeAttribute(
			    OnLineHelpManagementUtils.SessionKeys.HELP_TEXT_BUFFER);
	    request.getUnwrappedRequest()
		    .getSession()
		    .removeAttribute(
			    OnLineHelpManagementUtils.SessionKeys.HELP_IS_SHARED_BUFFER);
	    request.getUnwrappedRequest()
		    .getSession()
		    .removeAttribute(
			    OnLineHelpManagementUtils.SessionKeys.HELP_IS_ACTIVE_BUFFER);
	} else if (OnLineHelpManagementUtils.cancelAction.getProperty()
		.equalsIgnoreCase(propertyName)) {
	    request.getUnwrappedRequest()
		    .getSession()
		    .removeAttribute(
			    OnLineHelpManagementUtils.SessionKeys.EDIT_ACTIVE);
	    request.getUnwrappedRequest()
		    .getSession()
		    .removeAttribute(
			    OnLineHelpManagementUtils.SessionKeys.HELP_ID);
	    request.getUnwrappedRequest()
		    .getSession()
		    .removeAttribute(
			    OnLineHelpManagementUtils.SessionKeys.HELP_TEXT_BUFFER);
	    request.getUnwrappedRequest()
		    .getSession()
		    .removeAttribute(
			    OnLineHelpManagementUtils.SessionKeys.HELP_IS_SHARED_BUFFER);
	    request.getUnwrappedRequest()
		    .getSession()
		    .removeAttribute(
			    OnLineHelpManagementUtils.SessionKeys.HELP_IS_ACTIVE_BUFFER);
	} else if (OnLineHelpManagementUtils.removeAction.getProperty()
		.equalsIgnoreCase(propertyName)) {
	    OnLineHelpManagementUtils
		    .removeHelp(
			    process,
			    (Integer) request
				    .getUnwrappedRequest()
				    .getSession()
				    .getAttribute(
					    OnLineHelpManagementUtils.SessionKeys.HELP_ID));
	    request.getUnwrappedRequest()
		    .getSession()
		    .removeAttribute(
			    OnLineHelpManagementUtils.SessionKeys.HELP_ID);
	} else if (OnLineHelpManagementUtils.insertAction.getProperty()
		.equalsIgnoreCase(propertyName)) {
	    request.getUnwrappedRequest()
		    .getSession()
		    .setAttribute(
			    OnLineHelpManagementUtils.SessionKeys.EDIT_ACTIVE,
			    true);
	    request.getUnwrappedRequest()
		    .getSession()
		    .setAttribute(
			    OnLineHelpManagementUtils.SessionKeys.HELP_ID,
			    new Integer(0));
	    request.getUnwrappedRequest()
		    .getSession()
		    .removeAttribute(
			    OnLineHelpManagementUtils.SessionKeys.HELP_TEXT_BUFFER);
	    request.getUnwrappedRequest()
		    .getSession()
		    .removeAttribute(
			    OnLineHelpManagementUtils.SessionKeys.HELP_IS_SHARED_BUFFER);
	    request.getUnwrappedRequest()
		    .getSession()
		    .removeAttribute(
			    OnLineHelpManagementUtils.SessionKeys.HELP_IS_ACTIVE_BUFFER);
	} else if (OnLineHelpManagementUtils.closePreviewAction.getProperty()
		.equalsIgnoreCase(propertyName)) {
	    request.getUnwrappedRequest()
		    .getSession()
		    .removeAttribute(
			    OnLineHelpManagementUtils.SessionKeys.PREVIEW_ACTIVE);
	    request.getUnwrappedRequest()
		    .getSession()
		    .setAttribute(
			    OnLineHelpManagementUtils.SessionKeys.EDIT_ACTIVE,
			    true);
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.IStep#isUserPanelActive()
     */
    public final boolean isUserPanelActive() {
	return this.userPanelActive;
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.IStep#setUserPanelActive(boolean)
     */
    public final void setUserPanelActive(boolean userPanelActive) {
	this.userPanelActive = userPanelActive;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.people.IStep#userPanelLoopBack(it.people.process.AbstractPplProcess,
     * it.people.wrappers.IRequestWrapper, java.lang.String)
     */
    public void userPanelLoopBack(AbstractPplProcess process,
	    IRequestWrapper request, String parameter) {
	if (this.isUserPanelActive()) {
	    this.setUserPanelActive(false);
	    this.setUserPanelViewActive("");
	    request.removeAttribute(IUserPanel.USER_PANEL_PARAMETER_KEY);
	} else {
	    this.setUserPanelActive(true);
	    this.setUserPanelViewActive(parameter);
	    request.setAttribute(IUserPanel.USER_PANEL_PARAMETER_KEY, parameter);
	}
	process.getUserPanel().processPanel(request, parameter);
	try {
	    this.service(process, request);
	} catch (IOException e) {

	} catch (ServletException e) {

	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.IStep#setUserPanelViewActive(java.lang.String)
     */
    @Override
    public void setUserPanelViewActive(String userFunctionsPanelViewActive) {
	this.userPanelViewActive = userFunctionsPanelViewActive;
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.IStep#getUserPanelViewActive()
     */
    @Override
    public String getUserPanelViewActive() {
	return this.userPanelViewActive;
    }

    // private PplBean m_processData;
}
