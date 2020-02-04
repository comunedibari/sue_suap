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
import it.people.core.persistence.exception.peopleException;
import it.people.error.MessagesFactory;
import it.people.error.errorMessage;
import it.people.process.AbstractPplProcess;
import it.people.process.sign.ConcreteSign;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Category;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * User: sergio Date: Nov 27, 2003 Time: 5:11:38 PM <br>
 * <br>
 * Questa classe avvia la firma di uno specifico step.
 */
public class SignStepBegin extends Action {

    private Category cat = Category.getInstance(SignStepBegin.class.getName());

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

	ActionForward afwd = null;

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
		// Inizializzo tutti gli step di firma in base alle informazioni
		// inserite dall'utente
		if (pplProcess.getSign().isInitialized()) {
		    int redirectMode = pplProcess.getSign().beginStep(
			    p_httpServletRequest, p_httpServletResponse);
		    switch (redirectMode) {
		    case ConcreteSign.AUTOMATIC_REDIRECT:
			afwd = p_actionMapping
				.findForward("automaticalRedirect");
			break;
		    case ConcreteSign.MANUAL_REDIRECT:
			// il forward e' gia' stato fatto all'interno della
			// BeginStep
			afwd = null;
			break;
		    }
		}
	    }
	} catch (Exception ex) {
	    cat.error(ex);
	    errorMessage m_error = MessagesFactory.getInstance()
		    .getErrorMessage(commune.getKey(), "SignProcess.dbError");
	    m_error.setErrorForward("/framework/genericErrors/ProcessError.jsp");
	    p_httpServletRequest.setAttribute("errorMessage", m_error);
	    afwd = p_actionMapping.findForward("failed");
	}
	return afwd;
    }
}
