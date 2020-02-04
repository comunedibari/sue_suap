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

import java.util.Locale;

import it.people.layout.ButtonKey;
import it.people.process.AbstractPplProcess;
import it.people.util.MessageBundleHelper;

import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.BaseHandlerTag;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica
 * 
 */
public class StepOnLineHelpTag extends BaseHandlerTag {

    private static final long serialVersionUID = 641177385455954824L;

    protected String processName;
    protected String openinNewWindow;

    protected String labelKey = null;
    protected String altKey = null;
    protected String titleKey = null;

    protected String offLabelKey = null;
    protected String offAltKey = null;
    protected String offTitleKey = null;

    protected String jsFunctionName = null;

    private boolean isLinkTag = false;
    private AbstractPplProcess process;

    protected static Logger logger = Logger.getLogger(StepOnLineHelpTag.class);

    protected static String COMMAND_PROPERTY = "";
    protected static final String ON_LINE_HELP_DEFAULT_LABEL = "Aiuto";
    protected static final String ON_LINE_HELP_DEFAULT_ALT = "Aiuto in linea";
    protected static final String ON_LINE_HELP_DEFAULT_TITLE = "Aiuto in linea";
    protected static final String ON_LINE_HELP_OFF_DEFAULT_LABEL = "Nascondi aiuto";
    protected static final String ON_LINE_HELP_OFF_DEFAULT_ALT = "Nascondi aiuto in linea";
    protected static final String ON_LINE_HELP_OFF_DEFAULT_TITLE = "Nascondi aiuto in linea";
    protected static final String ON_LINE_HELP_DEFAULT_JS_FUNCTION_NAME = "openHelpUrl";

    protected static final String ON_LINE_HELP_DEFAULT_LABEL_KEY = "label.onlinehelp";
    protected static final String ON_LINE_HELP_DEFAULT_ALT_KEY = "label.onlinehelp.alt";
    protected static final String ON_LINE_HELP_DEFAULT_TITLE_KEY = "label.onlinehelp.title";

    protected static final String ON_LINE_HELP_OFF_DEFAULT_LABEL_KEY = "label.onlinehelp.off";
    protected static final String ON_LINE_HELP_OFF_DEFAULT_ALT_KEY = "label.onlinehelp.off.alt";
    protected static final String ON_LINE_HELP_OFF_DEFAULT_TITLE_KEY = "label.onlinehelp.off.title";

    public String getProcessName() {
	return processName;
    }

    public void setProcessName(String name) {
	this.processName = name;
    }

    /**
     * @return the openinNewWindow
     */
    public String getOpeninNewWindow() {
	return openinNewWindow;
    }

    /**
     * @param openinNewWindow
     *            the openinNewWindow to set
     */
    public void setOpeninNewWindow(String openinNewWindow) {
	this.openinNewWindow = openinNewWindow;
	this.setLinkTag(Boolean.parseBoolean(this.openinNewWindow));
    }

    /**
     * @return the labelKey
     */
    public String getLabelKey() {
	return labelKey;
    }

    /**
     * @param labelKey
     *            the labelKey to set
     */
    public void setLabelKey(String labelKey) {
	this.labelKey = labelKey;
    }

    /**
     * @return the altKey
     */
    public String getAltKey() {
	return altKey;
    }

    /**
     * @param altKey
     *            the altKey to set
     */
    public void setAltKey(String altKey) {
	this.altKey = altKey;
    }

    /**
     * @return the titleKey
     */
    public String getTitleKey() {
	return titleKey;
    }

    /**
     * @param titleKey
     *            the titleKey to set
     */
    public void setTitleKey(String titleKey) {
	this.titleKey = titleKey;
    }

    /**
     * @return the jsFunctionName
     */
    public String getJsFunctionName() {
	return (jsFunctionName == null) ? ON_LINE_HELP_DEFAULT_JS_FUNCTION_NAME
		: jsFunctionName;
    }

    /**
     * @param jsFunctionName
     *            the jsFunctionName to set
     */
    public void setJsFunctionName(String jsFunctionName) {
	this.jsFunctionName = jsFunctionName;
    }

    /**
     * @return the offLabelKey
     */
    public final String getOffLabelKey() {
	return offLabelKey;
    }

    /**
     * @param offLabelKey
     *            the offLabelKey to set
     */
    public final void setOffLabelKey(String offLabelKey) {
	this.offLabelKey = offLabelKey;
    }

    /**
     * @return the offAltKey
     */
    public final String getOffAltKey() {
	return offAltKey;
    }

    /**
     * @param offAltKey
     *            the offAltKey to set
     */
    public final void setOffAltKey(String offAltKey) {
	this.offAltKey = offAltKey;
    }

    /**
     * @return the offTitleKey
     */
    public final String getOffTitleKey() {
	return offTitleKey;
    }

    /**
     * @param offTitleKey
     *            the offTitleKey to set
     */
    public final void setOffTitleKey(String offTitleKey) {
	this.offTitleKey = offTitleKey;
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

    // SUBMIT TAG
    // ________________________________________________________________________

    // ----------------------------------------------------- Instance Variables

    /**
     * The name of the generated input field.
     */
    protected String property = null;

    /**
     * The body content of this tag (if any).
     */
    protected String text = null;

    /**
     * The value of the button label.
     */
    protected String value = null;

    // ------------------------------------------------------------- Properties

    /**
     * Return the property.
     */
    public String getProperty() {

	return (this.property);

    }

    /**
     * Set the property name.
     * 
     * @param property
     *            The property name
     */
    public void setProperty(String property) {

	this.property = property;

    }

    /**
     * Return the label value.
     */
    public String getValue() {

	return (this.value);

    }

    /**
     * Set the label value.
     * 
     * @param value
     *            The label value
     */
    public void setValue(String value) {

	this.value = value;

    }

    // LINK TAG
    // ________________________________________________________________________

    /**
     * The anchor to be added to the end of the generated hyperlink.
     */
    protected String anchor = null;

    public String getAnchor() {
	return (this.anchor);
    }

    public void setAnchor(String anchor) {
	this.anchor = anchor;
    }

    /**
     * <p>
     * The logical forward name from which to retrieve the hyperlink URI.
     * </p>
     * <p>
     * Usage note: If a forward config is used in a hyperlink, and a module is
     * specified, the path must lead to another action and not directly to a
     * page. This is in keeping with rule that in a modular application all
     * links must be to an action rather than a page.
     * </p>
     */
    protected String forward = null;

    public String getForward() {
	return (this.forward);
    }

    public void setForward(String forward) {
	this.forward = forward;
    }

    /**
     * The hyperlink URI.
     */
    protected String href = null;

    public String getHref() {
	return (this.href);
    }

    public void setHref(String href) {
	this.href = href;
    }

    /**
     * The link name for named links.
     */
    private String linkName = null;

    private String getLinkName() {
	return (this.getLabelKey());
    }

    private void setLinkName(String linkName) {
	this.linkName = linkName;
    }

    /**
     * The JSP bean name for query parameters.
     */
    protected String name = null;

    public String getName() {
	return (this.name);
    }

    public void setName(String name) {
	this.name = name;
    }

    /**
     * The module-relative page URL (beginning with a slash) to which this
     * hyperlink will be rendered.
     */
    protected String page = null;

    public String getPage() {
	return (this.page);
    }

    public void setPage(String page) {
	this.page = page;
    }

    /**
     * The module-relative action (beginning with a slash) which will be called
     * by this link
     */
    protected String action = null;

    public String getAction() {
	return (this.action);
    }

    public void setAction(String action) {
	this.action = action;
    }

    /**
     * The module prefix (beginning with a slash) which will be used to find the
     * action for this link.
     */
    protected String module = null;

    public String getModule() {
	return (this.module);
    }

    public void setModule(String module) {
	this.module = module;
    }

    /**
     * The single-parameter request parameter name to generate.
     */
    protected String paramId = null;

    public String getParamId() {
	return (this.paramId);
    }

    public void setParamId(String paramId) {
	this.paramId = paramId;
    }

    /**
     * The single-parameter JSP bean name.
     */
    protected String paramName = null;

    public String getParamName() {
	return (this.paramName);
    }

    public void setParamName(String paramName) {
	this.paramName = paramName;
    }

    /**
     * The single-parameter JSP bean property.
     */
    protected String paramProperty = null;

    public String getParamProperty() {
	return (this.paramProperty);
    }

    public void setParamProperty(String paramProperty) {
	this.paramProperty = paramProperty;
    }

    /**
     * The single-parameter JSP bean scope.
     */
    protected String paramScope = null;

    public String getParamScope() {
	return (this.paramScope);
    }

    public void setParamScope(String paramScope) {
	this.paramScope = paramScope;
    }

    /**
     * The scope of the bean specified by the name property, if any.
     */
    protected String scope = null;

    public String getScope() {
	return (this.scope);
    }

    public void setScope(String scope) {
	this.scope = scope;
    }

    /**
     * The window target.
     */
    protected String target = null;

    public String getTarget() {
	return (this.target);
    }

    public void setTarget(String target) {
	this.target = target;
    }

    /**
     * Include transaction token (if any) in the hyperlink?
     */
    protected boolean transaction = false;

    public boolean getTransaction() {
	return (this.transaction);
    }

    public void setTransaction(boolean transaction) {
	this.transaction = transaction;
    }

    /**
     * Name of parameter to generate to hold index number
     */
    protected String indexId = null;

    public String getIndexId() {
	return (this.indexId);
    }

    public void setIndexId(String indexId) {
	this.indexId = indexId;
    }

    protected boolean useLocalEncoding = false;

    public boolean isUseLocalEncoding() {
	return useLocalEncoding;
    }

    public void setUseLocalEncoding(boolean b) {
	useLocalEncoding = b;
    }

    /**
     * @return the isLinkTag
     */
    protected boolean isLinkTag() {
	return isLinkTag;
    }

    /**
     * @param isLinkTag
     *            the isLinkTag to set
     */
    protected void setLinkTag(boolean isLinkTag) {
	this.isLinkTag = isLinkTag;
    }

    public int doStartTag() throws JspException {

	this.setProcess((AbstractPplProcess) pageContext
		.findAttribute(processName));

	if (this.getProcess().getView().getCurrentActivity().getCurrentStep()
		.getHelpUrl() != null) {
	    if (!this.isLinkTag) {
		this.submitTagDoStartTag();
	    } else {
		this.linkTagDoStartTag();
	    }
	}

	return super.doStartTag();

    }

    public int doAfterBody() throws JspException {

	if (this.isLinkTag()) {
	    return this.linkTagDoAfterBody();
	} else {
	    return this.submitTagDoAfterBody();
	}

    }

    public int doEndTag() throws JspException {

	if (this.isLinkTag()) {
	    return this.linkTagDoEndTag();
	} else {
	    return this.submitTagDoEndTag();
	}

    }

    public void release() {

	super.release();

	processName = null;
	openinNewWindow = null;

	labelKey = null;
	altKey = null;
	titleKey = null;
	jsFunctionName = null;

	property = null;
	text = null;
	value = null;

	anchor = null;
	forward = null;
	href = null;
	linkName = null;
	name = null;
	page = null;
	action = null;
	module = null;
	paramId = null;
	paramName = null;
	paramProperty = null;
	paramScope = null;
	property = null;
	scope = null;
	target = null;
	text = null;
	transaction = false;
	indexId = null;
	useLocalEncoding = false;

    }

    // SUBMIT TAG METHODS
    // ________________________________________________________________________

    /**
     * Process the start of this tag.
     * 
     * @exception JspException
     *                if a JSP exception has occurred
     */
    public int submitTagDoStartTag() throws JspException {

	// Do nothing until doEndTag() is called
	this.text = null;
	return (EVAL_BODY_TAG);

    }

    /**
     * Save the associated label from the body content.
     * 
     * @exception JspException
     *                if a JSP exception has occurred
     */
    public int submitTagDoAfterBody() throws JspException {

	if (bodyContent != null) {
	    String value = bodyContent.getString().trim();
	    if (value.length() > 0) {
		text = value;
	    }
	}
	return (SKIP_BODY);

    }

    /**
     * Process the end of this tag.
     * <p>
     * Support for Indexed property since Struts 1.1
     * 
     * @exception JspException
     *                if a JSP exception has occurred
     */
    public int submitTagDoEndTag() throws JspException {

	// Generate an HTML element
	StringBuffer results = new StringBuffer();
	results.append(submitTagGetElementOpen());
	prepareAttribute(results, "name", submitTagPrepareName());
	submitTagPrepareButtonAttributes(results);
	results.append(prepareEventHandlers());
	results.append(prepareStyles());
	prepareOtherAttributes(results);
	results.append(getElementClose());

	TagUtils.getInstance().write(pageContext, results.toString());

	return (EVAL_PAGE);

    }

    /**
     * Render the opening element.
     * 
     * @return The opening part of the element.
     */
    protected String submitTagGetElementOpen() {
	return "<input type=\"submit\"";
    }

    /**
     * Prepare the name element
     * 
     * @return The element name.
     */
    protected String submitTagPrepareName() throws JspException {

	StringBuffer results = new StringBuffer();
	results.append(ButtonKey.ON_LINE_HELP_LOOP_BACK);

	// results.append("$" + this.commandProperty);

	return results.toString();

    }

    /**
     * Render the button attributes
     * 
     * @param results
     *            The StringBuffer that output will be appended to.
     */
    protected void submitTagPrepareButtonAttributes(StringBuffer results)
	    throws JspException {
	prepareAttribute(results, "accesskey", getAccesskey());
	prepareAttribute(results, "tabindex", getTabindex());
	submitTagPrepareValue(results);
    }

    /**
     * Render the value element
     * 
     * @param results
     *            The StringBuffer that output will be appended to.
     */
    protected void submitTagPrepareValue(StringBuffer results) {

	// Acquire the label value we will be generating
	String label = value;
	if ((label == null) && (text != null))
	    label = text;
	if ((label == null) || (label.length() < 1))
	    label = submitTagGetDefaultValue();

	String onLineHelpLabelKey = ON_LINE_HELP_DEFAULT_LABEL_KEY;

	if (this.getProcess() != null
		&& this.getProcess().getView() != null
		&& this.getProcess().getView().getCurrentActivity() != null
		&& this.getProcess().getView().getCurrentActivity()
			.getCurrentStep() != null) {
	    if (this.getProcess().getView().getCurrentActivity()
		    .getCurrentStep().isContextualHelpActive()) {
		onLineHelpLabelKey = this.getOffLabelKey();
	    } else {
		onLineHelpLabelKey = this.getLabelKey();
	    }
	}

	label = MessageBundleHelper.message(onLineHelpLabelKey, null, this
		.getProcess().getCommune().getOid(), Locale.getDefault());

	prepareAttribute(results, "value", label);

    }

    /**
     * Return the default value.
     * 
     * @return The default value if none supplied.
     */
    protected String submitTagGetDefaultValue() {
	return "Submit";
    }

    // LINK TAG METHODS
    // ________________________________________________________________________

    // --------------------------------------------------------- Public Methods

    /**
     * Render the beginning of the hyperlink.
     * <p>
     * Support for indexed property since Struts 1.1
     * 
     * @exception JspException
     *                if a JSP exception has occurred
     */
    public int linkTagDoStartTag() throws JspException {

	String onLineHelpDefaultAltKey = ON_LINE_HELP_DEFAULT_ALT_KEY;
	String onLineHelpDefaultTitleKey = ON_LINE_HELP_DEFAULT_ALT_KEY;
	String onLineHelpDefaultLabelKey = ON_LINE_HELP_DEFAULT_LABEL_KEY;

	String onLineHelpAltKey = this.getAltKey();
	String onLineHelpTitleKey = this.getTitleKey();
	String onLineHelpLabelKey = this.getLabelKey();

	if (this.getProcess() != null
		&& this.getProcess().getView() != null
		&& this.getProcess().getView().getCurrentActivity() != null
		&& this.getProcess().getView().getCurrentActivity()
			.getCurrentStep() != null) {
	    if (this.getProcess().getView().getCurrentActivity()
		    .getCurrentStep().isContextualHelpActive()) {
		onLineHelpDefaultAltKey = ON_LINE_HELP_OFF_DEFAULT_ALT_KEY;
		onLineHelpDefaultTitleKey = ON_LINE_HELP_OFF_DEFAULT_ALT_KEY;
		onLineHelpDefaultLabelKey = ON_LINE_HELP_OFF_DEFAULT_LABEL_KEY;
		onLineHelpAltKey = this.getOffAltKey();
		onLineHelpTitleKey = this.getOffTitleKey();
		onLineHelpLabelKey = this.getOffLabelKey();
	    } else {
		onLineHelpDefaultAltKey = ON_LINE_HELP_DEFAULT_ALT_KEY;
		onLineHelpDefaultTitleKey = ON_LINE_HELP_DEFAULT_ALT_KEY;
		onLineHelpDefaultLabelKey = ON_LINE_HELP_DEFAULT_LABEL_KEY;
		onLineHelpAltKey = this.getAltKey();
		onLineHelpTitleKey = this.getTitleKey();
		onLineHelpLabelKey = this.getLabelKey();
	    }
	}

	String alt = MessageBundleHelper.message(
		(onLineHelpAltKey == null) ? onLineHelpDefaultAltKey
			: onLineHelpAltKey, null, this.getProcess()
			.getCommune().getOid(), Locale.getDefault());

	String title = MessageBundleHelper.message(
		(onLineHelpTitleKey == null) ? onLineHelpDefaultTitleKey
			: onLineHelpTitleKey, null, this.getProcess()
			.getCommune().getOid(), Locale.getDefault());

	// Generate the opening anchor element
	StringBuffer results = new StringBuffer("<a");

	// Special case for name anchors
	prepareAttribute(results, "name", "on_line_help");

	prepareAttribute(results, "href", "#");
	prepareAttribute(results, "accesskey", getAccesskey());
	prepareAttribute(results, "tabindex", getTabindex());
	prepareAttribute(results, "alt",
		(alt == null) ? ON_LINE_HELP_DEFAULT_ALT : alt);
	prepareAttribute(results, "title",
		(title == null) ? ON_LINE_HELP_DEFAULT_TITLE : title);
	prepareAttribute(results, "onclick",
		linkTagCalculateURL(this.getProcess()));
	results.append(prepareStyles());
	results.append(prepareEventHandlers());
	prepareOtherAttributes(results);
	results.append(">");

	TagUtils.getInstance().write(pageContext, results.toString());

	this.setLinkName(MessageBundleHelper.message(onLineHelpDefaultLabelKey,
		null, this.getProcess().getCommune().getOid(),
		Locale.getDefault()));

	this.text = (getLinkName() == null) ? onLineHelpLabelKey
		: getLinkName();

	return (SKIP_BODY);

    }

    /**
     * Save the associated label from the body content.
     * 
     * @exception JspException
     *                if a JSP exception has occurred
     */
    public int linkTagDoAfterBody() throws JspException {

	if (bodyContent != null) {
	    String value = bodyContent.getString().trim();
	    if (value.length() > 0)
		text = value;
	}
	return (SKIP_BODY);

    }

    /**
     * Render the end of the hyperlink.
     * 
     * @exception JspException
     *                if a JSP exception has occurred
     */
    public int linkTagDoEndTag() throws JspException {

	// Prepare the textual content and ending element of this hyperlink
	StringBuffer results = new StringBuffer();
	if (text != null) {
	    results.append(text);
	}
	results.append("</a>");

	TagUtils.getInstance().write(pageContext, results.toString());

	return (EVAL_PAGE);

    }

    // ------------------------------------------------------ Protected Methods

    /**
     * Return the complete URL to which this hyperlink will direct the user.
     * Support for indexed property since Struts 1.1
     * 
     * @exception JspException
     *                if an exception is thrown calculating the value
     */
    protected String linkTagCalculateURL(AbstractPplProcess process)
	    throws JspException {

	String url = null;

	String jsFunction = (this.getJsFunctionName() == null) ? ON_LINE_HELP_DEFAULT_JS_FUNCTION_NAME
		: this.getJsFunctionName();

	String helpUrl = process.getView().getCurrentActivity()
		.getCurrentStep().getHelpUrl();

	url = jsFunction + "(" + helpUrl + ");";

	return (url);

    }

}
