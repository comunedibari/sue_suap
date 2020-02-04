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
package it.people.action;

import it.people.layout.ButtonKey;
import it.people.process.AbstractPplProcess;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.MultipartRequestHandler;

/**
 * <p>
 * Title: People project
 * </p>
 * <p>
 * Description: People project
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Espin
 * </p>
 * 
 * @author Sergio Chiaravalli
 * @version 1.0
 */
public class PeopleRequestProcessor extends
	org.apache.struts.action.RequestProcessor {

    protected static Logger logger = Logger
	    .getLogger(PeopleRequestProcessor.class);
    protected static final String DISPATCH_PATH = "/lookupDispatchProcess";
    protected static final String GOACTIVITY_ACTION_PATH = "/goActivityProcess";
    protected static final String GOACTIVITY_PARAM = "goActivityProcessTo";
    protected static final String ACTIVITY_INDEX_PARAM_NAME = "actIndex";
    protected static final String COMMANDLOOPBACK_INDEX_PARAM_NAME = "commandLoopbackIndex";
    protected static final String COMMANDLOOPBACK_PROPERTY_PARAM_NAME = "commandLoopbackProperty";

    /**
     * Il gestore dei processi � stato riscritto per poter effettuare il
     * dispatch delle richieste in funzione del pulsante premuto (es. avanti,
     * indietro, loopback) senza rimuovere i paramtri inseriti nel query string.
     * 
     * Il dispatching � attivato non appena e' stata processata la request e
     * quindi sono disponibili i suoi parametri.
     * 
     * Il dispatch si basa sui mapping di una action fittizia, la cui funzione
     * e' quella di configurare il dispatch.
     * 
     * L'alternativa, gi� provata, era quella di avere una action di
     * dispatching questa soluzione non richiedeva la ridefinizione del
     * RequestProcessor ma aveva lo svantaggio di non conservare i parametri
     * inseriti in querystring. Ho tentato di utilizzare la classe
     * ActionRedirect, per aggiungere i parametri ma in questo modo nel caso i
     * parametri fossero molti si incappava in errore.
     * 
     * 
     * @author Michele Fabbri - cedaf
     * @param request
     *            The servlet request we are processing
     * @param response
     *            The servlet response we are creating
     * @exception IOException
     *                if an input/output error occurs
     * @exception ServletException
     *                if a processing exception occurs
     */
    public void process(HttpServletRequest request, HttpServletResponse response)
	    throws IOException, ServletException {

	// Wrap multipart requests with a special wrapper
	request = processMultipart(request);

	// Identify the path component we will use to select a mapping
	String path = processPath(request, response);
	if (path == null) {
	    return;
	}

	if (log.isDebugEnabled()) {
	    log.debug("Processing a '" + request.getMethod() + "' for path '"
		    + path + "'");
	}

	// Select a Locale for the current user if requested
	processLocale(request, response);

	// Set the content type and no-caching headers if requested
	processContent(request, response);
	processNoCache(request, response);

	// General purpose preprocessing hook
	if (!processPreprocess(request, response)) {
	    return;
	}

	this.processCachedMessages(request, response);

	// Identify the mapping for this request
	ActionMapping mapping = processMapping(request, response, path);
	if (mapping == null) {
	    return;
	}

	// Check for any role required to perform this action
	if (!processRoles(request, response, mapping)) {
	    return;
	}

	// Process any ActionForm bean related to this request
	ActionForm form = this.processActionForm(request, response, mapping);
	processPopulate(request, response, form, mapping);

	// In questo punto la request � stata processata e sono
	// disponibili i suoi parametri, e' possibile effettuare
	// il dispatching
	mapping = processDispatch(request, response, mapping);

	if (!processValidate(request, response, form, mapping)) {
	    return;
	}

	// Process a forward or include specified by this mapping
	if (!processForward(request, response, mapping)) {
	    return;
	}

	if (!processInclude(request, response, mapping)) {
	    return;
	}

	Action action = processActionCreate(request, response, mapping);

	if (action == null) {
	    return;
	}

	// Call the Action instance itself
	ActionForward forward = processActionPerform(request, response, action,
		form, mapping);

	// if (request.getSession() != null) {
	// if (request.getSession().getAttribute("fwkPrivacy") != null) {
	// request.getSession().removeAttribute("fwkPrivacy");
	// forward.updatePath(forward.getPath() +
	// "?codEnte=A138&accreditato=TRUE&processName=it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico&processName=&codEveVita=1&idBookmark=1");
	// }
	// }

	// Process the returned ActionForward instance
	processForwardConfig(request, response, forward);

    }

    // ----------------------------------------------------- Processing Methods

    protected ActionMapping processDispatch(HttpServletRequest request,
	    HttpServletResponse response, ActionMapping mapping)
	    throws IOException, ServletException {

	if (!DISPATCH_PATH.equals(mapping.getPath())) {
	    // non e' richiesto nessun dispatching
	    return mapping;
	}

	// e' richiesto il dispatching
	// legge i parametri direttamente dalla request gia' processata
	HashSet parameters = new HashSet();
	for (Enumeration parametersEnum = request.getParameterNames(); parametersEnum
		.hasMoreElements();)
	    parameters.add((String) parametersEnum.nextElement());

	String actionPath = null;

	// Verifica se e' richiesto il salto ad un'attivita'
	String activityIndex = parseGoActivity(parameters);

	if (activityIndex != null) {
	    // e' richiesto il salto ad una attivita'
	    actionPath = GOACTIVITY_ACTION_PATH;
	    request.setAttribute(ACTIVITY_INDEX_PARAM_NAME, activityIndex);
	} else {
	    // Verifica se e' stato impostato per il commandLoopBack un indice
	    parseAndSetCommandLoopBackValue(request, parameters);

	    // e' richiesto il dispatching
	    // determina la action di destinazione
	    //
	    // Nel caso di upload di un file di dimensioni eccedenti il limite
	    // impostato
	    // non sono valorizzati i parameter della request ma � presente
	    // l'attributo
	    // org.apache.struts.upload.MaxLengthExceeded
	    boolean uploadMaxLengthExceeded = false;
	    if (request
		    .getAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED) != null)
		uploadMaxLengthExceeded = ((Boolean) request
			.getAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED))
			.booleanValue();
	    String actionName = lookup(parameters, mapping,
		    uploadMaxLengthExceeded);
	    actionPath = mapping.findForwardConfig(actionName).getPath();

	    // rimuove il .do se il mapping � su una action
	    if (actionPath.indexOf(".do") == -1)
		throw new ServletException(
			"Solo il mapping a Action � consentito");

	    actionPath = actionPath.substring(0, actionPath.length() - 3);
	}

	// Is there a mapping for this path?
	return processMapping(request, response, actionPath);
    }

    protected String parseGoActivity(HashSet parameters) {
	for (Iterator iter = parameters.iterator(); iter.hasNext();) {
	    String param = (String) iter.next();
	    if (param.startsWith(GOACTIVITY_PARAM)) {
		return param.substring(GOACTIVITY_PARAM.length());
	    }

	}
	return null;
    }

    protected void parseAndSetCommandLoopBackValue(HttpServletRequest request,
	    HashSet parameters) {
	String value = "";
	for (Iterator iter = parameters.iterator(); iter.hasNext();) {
	    String param = (String) iter.next();
	    if (param.startsWith(ButtonKey.LOOPBACK_VALIDATE)) {
		parameters.remove(param);
		parameters.add(ButtonKey.LOOPBACK_VALIDATE);
		value = param.substring(ButtonKey.LOOPBACK_VALIDATE.length());
		break;
	    } else if (param.startsWith(ButtonKey.LOOPBACK)) {
		parameters.remove(param);
		parameters.add(ButtonKey.LOOPBACK);
		value = param.substring(ButtonKey.LOOPBACK.length());
		break;
	    }
	    if (param.startsWith(ButtonKey.USER_PANEL_LOOP_BACK)) {
		parameters.remove(param);
		parameters.add(ButtonKey.USER_PANEL_LOOP_BACK);
		value = param
			.substring(ButtonKey.USER_PANEL_LOOP_BACK.length());
		break;
	    }
	    if (param.startsWith(ButtonKey.ON_LINE_HELP_LOOP_BACK)) {
		parameters.remove(param);
		parameters.add(ButtonKey.ON_LINE_HELP_LOOP_BACK);
		value = param.substring(ButtonKey.ON_LINE_HELP_LOOP_BACK
			.length());
		break;
	    }
	    if (param.startsWith(ButtonKey.ON_LINE_HELP_MANAGEMENT_LOOP_BACK)) {
		parameters.remove(param);
		parameters.add(ButtonKey.ON_LINE_HELP_MANAGEMENT_LOOP_BACK);
		value = param
			.substring(ButtonKey.ON_LINE_HELP_MANAGEMENT_LOOP_BACK
				.length());
		break;
	    }
	}

	if (!value.equalsIgnoreCase("")) {
	    String[] values = value.split("\\$");
	    // il primo elemento dell'array � vuoto
	    request.setAttribute(COMMANDLOOPBACK_PROPERTY_PARAM_NAME, values[1]);
	    if (values.length == 3)
		request.setAttribute(COMMANDLOOPBACK_INDEX_PARAM_NAME,
			values[2]);
	}
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.apache.struts.action.RequestProcessor#processValidate(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse,
     *      org.apache.struts.action.ActionForm,
     *      org.apache.struts.action.ActionMapping)
     */
    protected boolean processValidate(HttpServletRequest request,
	    HttpServletResponse response, ActionForm form, ActionMapping mapping)
	    throws IOException, ServletException {

	String path = processPath(request, response);

	/*
	 * if ((path.equalsIgnoreCase("/prevStepProcess")) ||
	 * (path.equalsIgnoreCase("/removeObject"))) return true; else return
	 * super.processValidate(request, response, form, mapping);
	 */

	AbstractPplProcess pplProcess = null;
	try {
	    // Verifica se la validazione è relativa alla form dei servizi
	    pplProcess = (AbstractPplProcess) form;
	} catch (ClassCastException ccex) {
	    return super.processValidate(request, response, form, mapping);
	}

	// Determina l'attivita' di destinazione
	String attivita = (String) request
		.getParameter(ACTIVITY_INDEX_PARAM_NAME);
	if (attivita == null)
	    attivita = (String) request.getAttribute(ACTIVITY_INDEX_PARAM_NAME);

	/*
	 * Se l'attività di destinazione è precedente o uguale a quella
	 * corrente non viene effettuato il controllo di validazione dei dati
	 */
	boolean activityControl = false;
	if (pplProcess != null && attivita != null) {
	    int currentActivity = pplProcess.getView()
		    .getCurrentActivityIndex();
	    int destinationActivity = Integer.parseInt(attivita);
	    activityControl = destinationActivity <= currentActivity;
	}

	// ETNO ENG
	/* path.equalsIgnoreCase("/prevStepProcess") || */
	// e' considerato anche il caso di salto di attivita per mezzo dei
	// pulsanti
	// invocando il dispatch.
	if (path.equalsIgnoreCase("/removeObject")
		|| (path.equalsIgnoreCase("/goActivityProcess") && activityControl)
		|| (path.equalsIgnoreCase(DISPATCH_PATH) && activityControl)) {

	    ((AbstractPplProcess) form).cleanErrors();
	    return true;
	} else {
	    return super.processValidate(request, response, form, mapping);
	}
    }

    private class MappingParameter {
	private ActionMapping mapping;

	public ActionMapping getMapping() {
	    return mapping;
	}

	public void setMapping(ActionMapping mapping) {
	    this.mapping = mapping;
	}
    }

    /**
     * 
     * @author Michele Fabbri - cedaf
     * @param request
     * @return
     */
    protected String lookup(Set parameters, ActionMapping mapping,
	    boolean uploadMaxLengthExceeded) throws ServletException {

	String[] forwards = mapping.findForwards();

	// Ridefinita la tabella di lookup, il collegamento tra il nome del
	// pulsante
	// es. navigation.button.next e la corrispondente action � definito
	// direttamente
	// nello struts-config.xml nella action con path =
	// 'lookupDispatchProcess'.
	// Questa modalit� permette di aggiungere delle azioni collegate ai
	// pulsanti
	// senza la necessit� di modificare la classe PeopleRequestProcessor
	for (int i = 0; i < forwards.length; i++) {
	    String forward = forwards[i];

	    // Nel caso di upload di un file di dimensioni eccedenti il limite
	    // impostato
	    // non sono valorizzati i parameter della request ma � presente
	    // l'attributo
	    // org.apache.struts.upload.MaxLengthExceeded
	    if (parameters.contains(forward)
		    || (uploadMaxLengthExceeded && forward
			    .equals(ButtonKey.SAVE_UPLOADED_FILE)))
		return forward;
	}

	return "default";
    }
}
