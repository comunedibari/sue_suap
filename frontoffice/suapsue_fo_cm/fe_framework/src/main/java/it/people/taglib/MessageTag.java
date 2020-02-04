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
import it.people.util.MessageBundleHelper;

import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.html.FormTag;

/**
 * Classe utilizzata per le Label
 */
public class MessageTag extends org.apache.struts.taglib.bean.MessageTag {

    private static Logger logger = Logger.getLogger(MessageTag.class);

    // classe css per segnalare l'errore configurabile nel tag
    // (default: 'labelInputFieldError' )
    private String m_errCssClass = "labelInputFieldError";

    public MessageTag() {
	super();

    }

    public void release() {
	super.release();

    }

    public int doStartTag() throws JspException {
	try {
	    Locale locale = (Locale) pageContext.getSession().getAttribute(
		    PeopleConstants.USER_LOCALE_KEY);

	    if (locale == null)
		locale = pageContext.getRequest().getLocale();

	    if (locale == null)
		locale = Locale.getDefault();

	    FormTag formTag = (FormTag) pageContext.getAttribute(
		    Constants.FORM_KEY, PageContext.REQUEST_SCOPE);

	    AbstractPplProcess process = null;
	    try {
		process = (AbstractPplProcess) TagUtils.getInstance().lookup(
			pageContext, Constants.BEAN_KEY, null);
	    } catch (Exception ex) {
	    }
	    ;

	    // se process == null, sono nel caso di formbean diversi
	    // da AbstractPplProcess oe mancanza di formbeans
	    return this.labelToHtml(process, locale);

	} catch (JspException jspe) {
	    // in caso di errore tenta comunque di mostrare il messaggio di
	    // default
	    logger.warn("Problema nella localizzazione del messaggio:\n"
		    + jspe.getMessage());
	    return super.doStartTag();
	}
    }

    public int labelToHtml(AbstractPplProcess process, Locale locale)
	    throws JspException {

	TagUtils tagUtils = TagUtils.getInstance();

	String key = this.key;
	// ******** RETROCOMPATIBILITA' ************************
	// Per retrocompatibilit� con le label che sostiuivano
	// {0} con il carattere *
	setArg0("");
	// *****************************************************
	if (key == null) {
	    Object value = tagUtils.lookup(pageContext, name, property, scope);
	    if (value != null && !(value instanceof String)) {
		JspException e = new JspException(messages.getMessage(
			"message.property", key));
		tagUtils.saveException(pageContext, e);
		throw e;
	    }
	    key = (String) value;
	}
	Object args[] = new Object[5];
	args[0] = arg0;
	args[1] = arg1;
	args[2] = arg2;
	args[3] = arg3;
	args[4] = arg4;

	City city = (City) pageContext.getSession().getAttribute("City");
	String comuneKey = null;
	if (city != null)
	    comuneKey = city.getOid();

	if (logger.isDebugEnabled())
	    logger.debug("Localizzazione per comune key [" + comuneKey + "]");

	String message = key;
	if (process == null) {
	    // messaggio relativo al framework
	    message = MessageBundleHelper.message(key, args, comuneKey, locale);
	} else {
	    // messaggio relativo allo svolgimento di una pratica
	    message = MessageBundleHelper.message(key, args,
		    process.getProcessName(), comuneKey, locale);
	}

	tagUtils.write(pageContext, message);
	return 0;
    }

    public int doEndTag() throws JspException {
	return super.doEndTag();
    }

    // ErrCssClass
    public String getErrCssClass() {
	return m_errCssClass;
    }

    public void setErrCssClass(String m_errCssClass) {
	this.m_errCssClass = m_errCssClass;
    }

}
