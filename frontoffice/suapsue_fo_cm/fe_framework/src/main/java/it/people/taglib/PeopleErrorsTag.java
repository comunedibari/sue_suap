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
/*
 * Created on 10-mag-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.people.taglib;

import it.people.City;
import it.people.IStep;
import it.people.PeopleConstants;
import it.people.process.AbstractPplProcess;
import it.people.util.MessageBundleHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.jsp.JspException;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.ErrorsTag;

/**
 * @author Zoppello
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PeopleErrorsTag extends ErrorsTag {

    public PeopleErrorsTag() {
	super();
    }

    public int doStartTag() throws JspException {

	AbstractPplProcess process = (AbstractPplProcess) TagUtils
		.getInstance().lookup(pageContext,
			org.apache.struts.taglib.html.Constants.BEAN_KEY, null,
			null);

	// Recupera i messaggi di validazione
	ActionMessages messages = null;
	try {
	    messages = TagUtils.getInstance().getActionMessages(pageContext,
		    name);
	} catch (JspException e) {
	    TagUtils.getInstance().saveException(pageContext, e);
	    throw e;
	}

	// Recupera i messaggi di servizio
	ArrayList serviceMessages = process.getServiceError();

	// Se non ci sono messaggi da mostrare esce
	boolean showValidationMessages = (messages != null && !messages
		.isEmpty());
	boolean showServiceMessages = (serviceMessages != null && !serviceMessages
		.isEmpty());
	if (!showValidationMessages && !showServiceMessages)
	    return 1;

	// Lingua e comune servono alla localizzazione dei messaggi
	Locale locale = (Locale) pageContext.getSession().getAttribute(
		PeopleConstants.USER_LOCALE_KEY);

	if (locale == null)
	    locale = Locale.getDefault();

	City city = (City) pageContext.getSession().getAttribute("City");
	String communeKey = null;
	if (city != null)
	    communeKey = city.getOid();

	String processName = process.getProcessName();

	IStep step = process.getView().getCurrentActivity().getCurrentStep();
	String viewName = step.getParentView().getName();

	// Determina i messaggi di contorno all'elenco
	String headerMessage = MessageBundleHelper.message("errors.header",
		null, processName, communeKey, locale);

	String footerMessage = MessageBundleHelper.message("errors.footer",
		null, processName, communeKey, locale);

	String prefixMessage = MessageBundleHelper.message("errors.prefix",
		null, processName, communeKey, locale);

	String suffixMessage = MessageBundleHelper.message("errors.suffix",
		null, processName, communeKey, locale);

	StringBuffer output = new StringBuffer();

	// Scrive l'intestazione
	appendSection(headerMessage, output);

	// Scrive i messaggi di errore di validazione
	showValidationMessages(messages, prefixMessage, suffixMessage,
		processName, communeKey, locale, output);

	// Scrive i messaggi di servizio
	showServiceErrors(serviceMessages, prefixMessage, suffixMessage,
		processName, communeKey, locale, output);

	// Scrive il footer
	appendSection(footerMessage, output);

	TagUtils.getInstance().write(pageContext, output.toString());
	return 1;
    }

    protected void appendSection(String section, StringBuffer output) {
	if (section != null) {
	    output.append(section);
	    output.append("\r\n");
	}
    }

    /**
     * Stampa i messaggi di errore causati dalla validazione, se il custom tag
     * prevede la specifica di una determinata property sono stampati solo i
     * messaggi ad essa relativi
     * 
     * @param messages
     * @param prefixMessage
     * @param suffixMessage
     * @param processName
     * @param communeKey
     * @param locale
     * @param output
     */
    protected void showValidationMessages(ActionMessages messages,
	    String prefixMessage, String suffixMessage, String processName,
	    String communeKey, Locale locale, StringBuffer output) {
	if (messages == null || messages.isEmpty())
	    return;

	// Nel caso sia specificata una properties mostra solo i
	// messaggi relativi ad essa
	Iterator reports = null;
	if (property == null)
	    reports = messages.get();
	else
	    reports = messages.get(property);

	// Mostra i messaggi di errore di validazione
	while (reports.hasNext()) {
	    ActionMessage report = (ActionMessage) reports.next();

	    Object[] arguments = report.getValues();
	    if (arguments != null) {

		String keyValue = null;
		if (arguments[0] != null && arguments[0] instanceof String) {
		    keyValue = MessageBundleHelper.message(
			    ((String) arguments[0]), report.getValues(),
			    processName, communeKey, locale);
		    if (keyValue != null) {
			arguments[0] = keyValue;
		    }
		}
	    }
	    String message = message = MessageBundleHelper
		    .message(report.getKey(), arguments, processName,
			    communeKey, locale);

	    if (prefixMessage != null)
		output.append(prefixMessage);

	    if (message != null) {
		output.append(message);
		output.append("\r\n");
	    }

	    if (suffixMessage != null)
		output.append(suffixMessage);
	}
    }

    protected void showServiceErrors(ArrayList serviceMessages,
	    String prefixMessage, String suffixMessage, String processName,
	    String communeKey, Locale locale, StringBuffer output) {
	if (serviceMessages == null || serviceMessages.isEmpty())
	    return;

	// Nel caso sia specificata una properties i messaggi
	// generali non devono essere mostrati
	if (property != null)
	    return;

	// mostra i messaggi di errore generici del servizio
	for (Iterator iter = serviceMessages.iterator(); iter.hasNext();) {
	    String serviceErrorMessageKey = (String) iter.next();
	    String message = MessageBundleHelper.message(
		    serviceErrorMessageKey, new String[] {}, processName,
		    communeKey, locale);

	    if (prefixMessage != null)
		output.append(prefixMessage);

	    if (message != null) {
		output.append(message);
		output.append("\r\n");
	    } else {
		// Se non trova il messaggio stampa la chiave
		output.append(serviceErrorMessageKey);
		output.append("\r\n");
	    }

	    if (suffixMessage != null)
		output.append(suffixMessage);

	}
    }

}
