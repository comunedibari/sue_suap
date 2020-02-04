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
 * Created on 10-ott-2005
 * 
 */
package it.people.action;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import it.people.action.dispatching.LookupElement;
import it.people.layout.ButtonKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

/**
 * @author Fabbri Michele Cedaf s.r.l.
 * 
 *         La classe effettua il dispatch delle richieste alle action di
 *         navigazione del processo, tra cui: next, previous, loopback,
 *         start_payment, ecc. La definizione dei nomi logici delle action da
 *         invocare � costruita nel metodo getLookUp.
 * 
 * @deprecated la action di dispatch non � pi� utilizzata, al suo posto �
 *             stata riscritta il PeopleRequestProcessor per effettuare il
 *             dispatch delle richieste.
 */
public class DispatchProcess extends Action {

    private static Category log = Logger.getInstance(DispatchProcess.class);
    protected static List lookupList = new ArrayList();

    /**
     * La classe cerca nei parametri della richiesta il nome del pulsante
     * premuto, quindi effettua un forward alla action corrispondente.
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

	synchronized (lookupList) {
	    // N.B. synchronized non � un lock, la jvm non serializza
	    // l'accesso
	    // all'oggetto ma pu� eseguire pi� accessi all'oggetto fintanto
	    // che
	    // questo pu� essere considerato un accesso atomico.
	    // In altre parole fintanto che nessun thread scrive sull'oggetto
	    // tutti possono accedervi.

	    if (lookupList.isEmpty())
		lookupList = getLookup();
	}

	// Collega il pulsante premuto con la action corrispondente
	LookupElement foundElement = null;
	Iterator iter = lookupList.iterator();
	while (iter.hasNext() && foundElement == null) {
	    LookupElement element = (LookupElement) iter.next();
	    if (request.getParameter(element.getKey()) != null) {
		// Il submit � relativo ad una Action registrata
		// I parametri sono sempre passati tutti
		foundElement = element;
	    }
	}

	ActionRedirect redirect;
	if (foundElement != null)
	    redirect = new ActionRedirect(mapping.findForward(foundElement
		    .getActionName()));
	else
	    redirect = new ActionRedirect(mapping.findForward("default"));

	// Aggiunge tutti i parametri alla nuova richiesta
	// addParameters(request, redirect, response.getCharacterEncoding());
	return redirect;
    }

    protected void addParameters(HttpServletRequest request,
	    ActionRedirect redirect, String encoding) {
	Enumeration parametersName = request.getParameterNames();
	while (parametersName.hasMoreElements()) {
	    String name = (String) parametersName.nextElement();
	    String values[] = request.getParameterValues(name);
	    try {
		for (int i = 0; i < values.length; i++)
		    redirect.addParameter(name, values[i]);

		if (log.isDebugEnabled())
		    log.debug("DispatchProcess: parametro copiato nome='"
			    + name + "', valore/i='" + values.toString() + "'");
	    } catch (Exception ex) {
		log.error("DispatchProcess: impossibile copiare il parametro '"
			+ name + "', \n" + ex.getMessage());
	    }
	}
    }

    /**
     * Inizializza un array list con il collegamento tra il nome del campo di
     * submit e il nome logico della action da richiamare.
     * 
     * @return
     */
    protected List getLookup() {
	ArrayList list = new ArrayList(12);
	list.add(new LookupElement(ButtonKey.NEXT, "next"));
	list.add(new LookupElement(ButtonKey.NEXT_MODULE, "next"));
	list.add(new LookupElement(ButtonKey.PREVIOUS, "previous"));
	list.add(new LookupElement(ButtonKey.PREVIOUS_MODULE, "previous"));
	list.add(new LookupElement(ButtonKey.LOOPBACK, "loopback"));
	list.add(new LookupElement(ButtonKey.LOOPBACK_VALIDATE,
		"loopbackValidate"));
	list.add(new LookupElement(ButtonKey.SAVE, "save"));
	list.add(new LookupElement(ButtonKey.SAVE_AND_SEND, "saveSignSend"));
	list.add(new LookupElement(ButtonKey.SIGN, "saveSignSend"));
	list.add(new LookupElement(ButtonKey.START_PAYMENT, "startPayment"));
	list.add(new LookupElement(ButtonKey.ADD_NEW_OBJECT, "addNewObject"));
	list.add(new LookupElement(ButtonKey.SAVE_UPLOADED_FILE,
		"saveUploadedFile"));
	list.add(new LookupElement(ButtonKey.LOGIN, "login"));

	// TODO: removeObject: invocabile solo direttamente, in realt� di
	// potrebbe
	// prevedere anche una modalit� di invocazione con un submit che
	// potrebbe
	// essere usata con i radiobutton.
	// protected static final String REMOVE_OBJECT = "removeObject";

	return list;
    }
}
