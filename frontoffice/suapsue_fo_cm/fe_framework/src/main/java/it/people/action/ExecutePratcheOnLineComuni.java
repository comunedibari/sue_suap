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
import it.people.core.SubmittedProcessSearchManager;
import it.people.core.persistence.exception.peopleException;
import it.people.error.errorMessage;
import it.people.process.SubmittedProcess;
import it.people.process.SubmittedProcessState;
import it.people.vsl.CreateSubmittedProcessHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Category;
import org.apache.ojb.broker.query.Criteria;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * User: Luigi Corollo Date: 12-gen-2004 Time: 16.25.09 <br>
 * <br>
 * Aggiunge gli stati al Processo inviato dall'utente.
 */
public class ExecutePratcheOnLineComuni extends Action {
    private static String ERROR_JSP = "/framework/genericErrors/ProcessError.jsp";
    private errorMessage m_error;
    private Category cat = Category
	    .getInstance(ExecutePratcheOnLineComuni.class.getName());

    public ExecutePratcheOnLineComuni() {
    }

    public ActionForward execute(ActionMapping p_actionMapping,
	    ActionForm p_actionForm, HttpServletRequest p_servletRequest,
	    HttpServletResponse p_servletResponse) throws Exception {
	super.execute(p_actionMapping, p_actionForm, p_servletRequest,
		p_servletResponse);

	try {
	    PeopleContext peopleContext = PeopleContext
		    .create(p_servletRequest);

	    // recupero i dati dell'operazione
	    String status = p_servletRequest.getParameter("status");

	    SubmittedProcessState state = null;
	    boolean isCompleted = false;
	    boolean isProtocollated = false;
	    if (SubmittedProcessState.COMPLETED.toString().equals(status)) {
		state = SubmittedProcessState.COMPLETED;
		isCompleted = true;
	    } else if (SubmittedProcessState.PROTOCOLLED.toString().equals(
		    status)) {
		state = SubmittedProcessState.PROTOCOLLED;
		isProtocollated = true;
	    } else if (SubmittedProcessState.REJECTED.toString().equals(status))
		state = SubmittedProcessState.REJECTED;
	    else if (SubmittedProcessState.SUBMITTED.toString().equals(status))
		state = SubmittedProcessState.SUBMITTED;

	    Collection processes = null;
	    Iterator processSubIter = null;

	    // Recupero il comune
	    String comuneId = peopleContext.getCommune().getOid();

	    // Recupero l'id della pratica
	    String praticaId = p_servletRequest.getParameter("praticaId");

	    // Cerco il processo
	    Criteria crtr = null;
	    crtr = new Criteria();
	    if (comuneId != null) {
		crtr.addEqualTo("commune", comuneId);
	    }
	    if (praticaId != null) {
		crtr.addEqualTo("people_protocol_id", praticaId);
	    }

	    PeopleContext pplContext = PeopleContext.create(p_servletRequest);
	    processes = SubmittedProcessSearchManager.getInstance().getAll(
		    pplContext, crtr);
	    if (processes.size() > 0)
		processSubIter = processes.iterator();

	    // Eseguo l'operazione
	    if (processSubIter != null) {
		processSubIter = processes.iterator();

		while (processSubIter.hasNext()) {
		    SubmittedProcess sp = (SubmittedProcess) processSubIter
			    .next();

		    if (isCompleted) {
			// Setto Completed
			sp.setCompleted(new Boolean(true));
		    } else if (isProtocollated) {
			// Setto il protocollo
			String communeProtocolloId = p_servletRequest
				.getParameter("communeProtocolloId");

			if (communeProtocolloId != null
				&& communeProtocolloId.length() > 0) {
			    sp.setCommuneProtocollId(communeProtocolloId);
			} else {
			    // Errore il campo e' vuoto o nullo
			    List propertiesNotValid = new ArrayList();
			    propertiesNotValid.add("communeProtocolloId");
			    p_servletRequest.setAttribute("propertiesNotValid",
				    propertiesNotValid);
			    return p_actionMapping.findForward("input");
			}
		    }

		    // addProcessHistory
		    CreateSubmittedProcessHelper.addProcessHistory(pplContext,
			    sp, state);
		}
	    }

	    if (isCompleted)
		// Ritorno all'elenco
		return p_actionMapping.findForward("list");
	    else
		// Ritorno alla pagina di viewPraticaOnLineComune.jsp
		return p_actionMapping.findForward("edit");

	} catch (Exception ex) {
	    cat.error(ex);
	} catch (peopleException e) {
	    cat.error(e);
	}
	return p_actionMapping.findForward("failed");
    }
}
