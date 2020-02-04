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

import it.people.core.AbortSignManager;
import it.people.core.PeopleContext;
import it.people.core.ProcessManager;
import it.people.core.persistence.exception.peopleException;
import it.people.error.MessagesFactory;
import it.people.error.errorMessage;
import it.people.process.AbstractPplProcess;
import it.people.process.SummaryState;
import it.people.util.ActivityLogger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Category;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * User: sergio Date: Sep 17, 2003 Time: 3:21:35 PM <br>
 * 
 */
public class AbortSign extends Action {

    private Category cat = Category.getInstance(AbortSign.class.getName());

    /**
     * Termina il processo di firma.
     * 
     * @param p_actionMapping
     *            Oggetto contenente la "mappatura" delle azioni e dei forward
     * @param p_actionForm
     *            Form inviata
     * @param p_servletRequest
     *            Request
     * @param p_servletResponse
     *            Respose
     * @return Restituisce il forward da eseguire a seconda dell'esito
     *         dell'azione
     * @throws Exception
     */
    public ActionForward execute(ActionMapping p_actionMapping,
	    ActionForm p_actionForm, HttpServletRequest p_servletRequest,
	    HttpServletResponse p_servletResponse) throws Exception {
	super.execute(p_actionMapping, p_actionForm, p_servletRequest,
		p_servletResponse);
	AbstractPplProcess process = (AbstractPplProcess) p_actionForm;
	try {
	    String processOid = p_servletRequest.getParameter("processId");
	    if (processOid != null) {
		PeopleContext context = PeopleContext.create(p_servletRequest);
		Long oid = new Long(processOid);
		ProcessManager.getInstance().load(process, context, oid);

		if (process != null) {
		    // Elimino gli oggetti firmati
		    try {
			AbortSignManager.getInstance().abortSign(
				process.getOid());
		    } catch (peopleException e) {
			errorMessage error = MessagesFactory.getInstance()
				.getErrorMessage(process.getCommune().getKey(),
					"SaveProcess.dbError");
			error.setErrorForward("/framework/genericErrors/ProcessError.jsp");
			error.setErrorSender("goActivityProcess.do?actIndex="
				+ process.getView().getCurrentActivityIndex());
			p_servletRequest.setAttribute("errorMessage", error);
			return p_actionMapping.findForward("failed");
		    }

		    // Reimposta lo stato del procedimento
		    process.setStatus("S_EDIT");

		    // Reimposta l'attivit� precedente il riepilogo come
		    // quella corrente
		    if (process.isSignEnabled()) {
			if (process.getSummaryState() != SummaryState.NONE) {
			    process.getView().prevStep(context);
			}
		    }

		    try {
			ProcessManager.getInstance()
				.set(PeopleContext.create(p_servletRequest),
					process);
		    } catch (peopleException e) {
			errorMessage error = MessagesFactory.getInstance()
				.getErrorMessage(process.getCommune().getKey(),
					"SaveProcess.dbError");
			error.setErrorForward("/framework/genericErrors/ProcessError.jsp");
			error.setErrorSender("goActivityProcess.do?actIndex="
				+ process.getView().getCurrentActivityIndex());
			p_servletRequest.setAttribute("errorMessage", error);
			return p_actionMapping.findForward("failed");
		    }

		    cat.debug("success");
		    ActivityLogger.getInstance().log(process,
			    "Processo di firma abortiro", ActivityLogger.INFO);

		}
	    }
	    cat.debug("Processo " + processOid + " non trovato");
	    return p_actionMapping.findForward("success");

	} catch (peopleException pex) {
	    cat.error(pex);
	} catch (Exception ex) {
	    cat.error(ex);
	}
	if (process != null) {
	    errorMessage error = MessagesFactory.getInstance().getErrorMessage(
		    process.getCommune().getKey(), "SaveProcess.dbError");
	    error.setErrorForward("/framework/genericErrors/ProcessError.jsp");
	    error.setErrorSender("goActivityProcess.do?actIndex="
		    + process.getView().getCurrentActivityIndex());
	    p_servletRequest.setAttribute("errorMessage", error);
	}
	cat.debug("failed");
	return p_actionMapping.findForward("failed");

    }
}
