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

import it.people.core.PeopleContext;
import it.people.core.PplPermission;
import it.people.core.PplPrincipal;
import it.people.core.PplSecurityManager;
import it.people.core.PplUser;
import it.people.process.AbstractPplProcess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * User: sergio Date: Dec 4, 2003 Time: 2:15:20 PM <br>
 * Questa azione controlla se tutti gli step di firma sono stati completati. Se
 * l'utente corrente � il richiedente aggiunge alla request il relativo
 * oggetto PplPrincipal
 */
public class EndSignProcess extends Action {

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

	if (pplProcess.isInizialized() && pplProcess.isSignEnabled()
		&& pplProcess.getSign().isInitialized()) {

	    if (pplProcess.getSign().isComplete()) {

		PplUser user = PeopleContext.create(p_httpServletRequest)
			.getUser();
		PplPrincipal[] princs = pplProcess
			.findPrincipalWithDelegate(user);

		if (PplSecurityManager.getInstance().canDo(PplPermission.SEND,
			princs, pplProcess.getProcessSendACEs())) {
		    p_httpServletRequest.setAttribute("pplPermission",
			    PplPermission.SEND);
		}

		return p_actionMapping.findForward("completed");
	    }
	}
	if (pplProcess.isInizialized() && !pplProcess.isSignEnabled()) {

	    PplUser user = PeopleContext.create(p_httpServletRequest).getUser();
	    PplPrincipal[] princs = pplProcess.findPrincipalWithDelegate(user);

	    if (PplSecurityManager.getInstance().canDo(PplPermission.SEND,
		    princs, pplProcess.getProcessSendACEs())) {
		p_httpServletRequest.setAttribute("pplPermission",
			PplPermission.SEND);
	    }
	    return p_actionMapping.findForward("completed");

	}
	return p_actionMapping.findForward("incompleted");
    }
}
