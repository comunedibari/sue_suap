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

import it.people.City;
import it.people.PeopleConstants;
import it.people.process.AbstractPplProcess;
import it.people.process.data.AbstractData;
import it.people.util.MessageBundleHelper;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;

public class PrivacyDisclaimerTag extends TagSupport {

    private static final long serialVersionUID = 8999735275425026026L;

    private static Logger logger = Logger.getLogger(PrivacyDisclaimerTag.class);

    private static final String DEFAULT_PRIVACY_MESSAGE_KEY = "privacy.disclaimer";
    public static final String PRIVACY_CHECKBOX_LABEL_KEY = "privacy.checkbox.label";
    private static final String DEFAULT_CSS_CLASS = "privacyDisclaimer";

    private String privacyMessageKey = DEFAULT_PRIVACY_MESSAGE_KEY;
    private String privacyCheckboxLabelKey = PRIVACY_CHECKBOX_LABEL_KEY;
    private String cssClass = DEFAULT_CSS_CLASS;

    public PrivacyDisclaimerTag() {
	super();

    }

    public void release() {
	super.release();

    }

    /**
     * @return the privacyMessageKey
     */
    protected final String getPrivacyMessageKey() {
	return (this.privacyMessageKey == null || (this.privacyMessageKey != null && this.privacyMessageKey
		.equalsIgnoreCase(""))) ? DEFAULT_PRIVACY_MESSAGE_KEY
		: this.privacyMessageKey;
    }

    /**
     * @param privacyMessageKey
     *            the privacyMessagekey to set
     */
    public final void setPrivacyMessageKey(String privacyMessageKey) {
	this.privacyMessageKey = privacyMessageKey;
    }

    /**
     * @return the privacyCheckboxLabelKey
     */
    protected final String getPrivacyCheckboxLabelKey() {
	return (this.privacyCheckboxLabelKey == null || (this.privacyCheckboxLabelKey != null && this.privacyCheckboxLabelKey
		.equalsIgnoreCase(""))) ? PRIVACY_CHECKBOX_LABEL_KEY
		: this.privacyCheckboxLabelKey;
    }

    /**
     * @param privacyCheckboxLabelKey
     *            the privacyCheckboxLabelKey to set
     */
    public final void setPrivacyCheckboxLabelKey(String privacyCheckboxLabelKey) {
	this.privacyCheckboxLabelKey = privacyCheckboxLabelKey;
    }

    /**
     * @return the cssClass
     */
    protected final String getCssClass() {
	return (this.cssClass == null || (this.cssClass != null && this.cssClass
		.equalsIgnoreCase(""))) ? DEFAULT_CSS_CLASS : this.cssClass;
    }

    /**
     * @param cssClass
     *            the cssClass to set
     */
    public final void setCssClass(String cssClass) {
	this.cssClass = cssClass;
    }

    public int doStartTag() throws JspException {

	Locale locale = (Locale) pageContext.getSession().getAttribute(
		PeopleConstants.USER_LOCALE_KEY);

	if (locale == null)
	    locale = pageContext.getRequest().getLocale();

	if (locale == null)
	    locale = Locale.getDefault();

	try {
	    AbstractPplProcess process = null;
	    try {
		process = (AbstractPplProcess) TagUtils.getInstance().lookup(
			pageContext, Constants.BEAN_KEY, null);
	    } catch (Exception ex) {
	    }
	    ;

	    // se process == null, sono nel caso di formbean diversi
	    // da AbstractPplProcess oe mancanza di formbeans
	    return this.privacyDisclaimer(process, locale);

	} catch (JspException jspe) {
	    // in caso di errore tenta comunque di mostrare il messaggio di
	    // default
	    logger.warn("Problema nella localizzazione del messaggio:\n"
		    + jspe.getMessage());
	    return super.doStartTag();
	}
    }

    public int privacyDisclaimer(AbstractPplProcess process, Locale locale)
	    throws JspException {

	City city = (City) pageContext.getSession().getAttribute("City");
	String comuneKey = null;
	if (city != null)
	    comuneKey = city.getOid();

	if (logger.isDebugEnabled())
	    logger.debug("Localizzazione per comune key [" + comuneKey + "]");

	String message = this.getPrivacyMessageKey();
	if (process == null) {
	    // messaggio relativo al framework
	    message = MessageBundleHelper.message(this.getPrivacyMessageKey(),
		    null, comuneKey, locale);
	} else {
	    // messaggio relativo allo svolgimento di una pratica
	    message = MessageBundleHelper.message(this.getPrivacyMessageKey(),
		    null, process.getProcessName(), comuneKey, locale);
	}

	String checkboxLabel = this.getPrivacyCheckboxLabelKey();
	if (process == null) {
	    // messaggio relativo al framework
	    checkboxLabel = MessageBundleHelper.message(
		    this.getPrivacyCheckboxLabelKey(), null, comuneKey, locale);
	} else {
	    // messaggio relativo allo svolgimento di una pratica
	    checkboxLabel = MessageBundleHelper.message(
		    this.getPrivacyCheckboxLabelKey(), null,
		    process.getProcessName(), comuneKey, locale);
	}

	StringBuilder tag = new StringBuilder();
	tag.append(
		"<table style=\"padding: 5px; width:96%;\" id=\"privacyDisclaimerTable\">")
		.append("	<tr>").append("		<td>").append("			<br/>")
		.append("			<div class=\"" + this.getCssClass() + "\">")
		.append("				" + message).append("			</div>").append("		</td>")
		.append("	</tr>");
	if (process != null && process.isPrivacyDisclaimerRequireAcceptance()) {
	    AbstractData data = (AbstractData) process.getData();
	    String checked = data.isPrivacyDisclaimerAccepted() ? "checked=\"checked\""
		    : "";
	    tag.append("	<tr>")
		    .append("		<td align=\"center\">")
		    .append("			<input type=\"checkbox\" name=\"data.privacyDisclaimerAccepted\" "
			    + checked + " id=\"privacyDisclaimerAcceptance\">")
		    .append("			<label for=\"privacyDisclaimerAcceptance\">"
			    + checkboxLabel + "</label>").append("			<br/>")
		    .append("		</td>").append("	</tr>");
	}

	tag.append("</table>");

	tag.append("<html type=\"hidden\" name=\"fwkPrivacyStep\" value=\"fwkPrivacyStep\" />");

	JspWriter out = pageContext.getOut();
	try {
	    out.write(tag.toString());
	} catch (IOException e) {
	    throw new JspException(e);
	}

	return 0;
    }

    public int doEndTag() throws JspException {
	return super.doEndTag();
    }

}