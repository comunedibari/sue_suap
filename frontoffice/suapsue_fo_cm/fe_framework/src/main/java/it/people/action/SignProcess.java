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

import it.people.City;
import it.people.core.CommuneManager;
import it.people.core.PeopleContext;
import it.people.core.ProcessManager;
import it.people.core.persistence.exception.peopleException;
import it.people.error.MessagesFactory;
import it.people.error.errorMessage;
import it.people.process.AbstractPplProcess;
import it.people.process.sign.ConcreteSign;
import it.people.util.Device;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Category;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * User: sergio Date: Sep 8, 2003 Time: 2:25:58 PM <br>
 * <br>
 * Questa classe inizia il processo di firma. In perticolare: salva il processo
 * e controlla se ci sono degli step non completati: se si, invoca la
 * SignStepBegin, altrimenti la EndSignProcess
 */
public class SignProcess extends Action {

    private Category cat = Category.getInstance(SignProcess.class.getName());

    /**
     * 
     * @param p_actionMapping
     *            Oggetto contenente la "mappatura" delle azioni e dei forward
     * @param p_actionForm
     *            Form inviata
     * @param p_httpServletRequest
     *            Request
     * @param p_httpServletResponse
     *            Respose
     * @return Restituisce il forward da eseguire a seconda dell'esito
     *         dell'azione
     * @throws Exception
     */

    public ActionForward execute(ActionMapping p_actionMapping,
	    ActionForm p_actionForm, HttpServletRequest p_httpServletRequest,
	    HttpServletResponse p_httpServletResponse) throws Exception {
	super.execute(p_actionMapping, p_actionForm, p_httpServletRequest,
		p_httpServletResponse);

	AbstractPplProcess pplProcess = (AbstractPplProcess) p_actionForm;
	City commune = null;

	if (pplProcess != null)
	    commune = pplProcess.getCommune();
	else {
	    try {
		commune = CommuneManager.getInstance().get();
	    } catch (peopleException pplEx) {
		cat.error(pplEx);
	    }
	}

	try {

	    if (pplProcess.isInizialized()) {
		PeopleContext peopleContext = PeopleContext
			.create(p_httpServletRequest);

		if (peopleContext.getUser().isAnonymous()) {
		    cat.info("Tentato l'accesso come utente anonimo.");
		    return p_actionMapping.findForward("failed");
		}

		if (!pplProcess.isSignEnabled()) {
		    try {
			ProcessManager.getInstance().set(peopleContext,
				pplProcess);
		    } catch (peopleException e) {
			cat.error(e);
			p_actionMapping.findForward("failed");
		    }
		    return p_actionMapping.findForward("endSignProcess");
		}

		// Inizializzo tutti gli step di firma in base alle informazioni
		// inserite dall'utente
		if (!pplProcess.getSign().isInitialized())

		    /**
		     * Salvo il processo e lo setto in stato di firma
		     */

		    pplProcess.setStatus("S_SIGN");
		try {
		    ProcessManager.getInstance().set(peopleContext, pplProcess);
		} catch (peopleException e) {
		    cat.error(e);
		    p_actionMapping.findForward("failed");
		}

		pplProcess.getSign().initialize(commune, Device.HTML,
			p_httpServletRequest);

		if (pplProcess.getSign().firstStep(peopleContext) == ConcreteSign.NOOTHER_SIGNSTEP) {
		    cat.debug("endSignProcess");
		    return p_actionMapping.findForward("endSignProcess");
		}

		cat.debug("success");
		return p_actionMapping.findForward("success");
	    }
	} catch (peopleException e) {
	    // todo usare comune invece della Key
	    cat.error(e);
	    errorMessage m_error = MessagesFactory.getInstance()
		    .getErrorMessage(commune.getKey(), "SignProcess.dbError");
	    m_error.setErrorForward("/framework/genericErrors/ProcessError.jsp");
	    p_httpServletRequest.setAttribute("errorMessage", m_error);
	} catch (Exception ex) {
	    cat.error(ex);
	    errorMessage m_error = MessagesFactory.getInstance()
		    .getErrorMessage(commune.getKey(), "SignProcess.dbError");
	    m_error.setErrorForward("/framework/genericErrors/ProcessError.jsp");
	    p_httpServletRequest.setAttribute("errorMessage", m_error);
	}
	cat.debug("failed");
	return p_actionMapping.findForward("failed");
    }
}
