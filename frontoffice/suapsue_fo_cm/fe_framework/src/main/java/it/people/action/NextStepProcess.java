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

import java.util.Enumeration;

import it.people.SummaryActivity;
import it.people.core.PeopleContext;
import it.people.core.PplUser;
import it.people.process.AbstractPplProcess;
import it.people.util.CreateNotSubmittableProcessHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Category;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * User: sergio Date: Sep 8, 2003 Time: 3:53:15 PM <br>
 * <br>
 * L'azione permette la navigazione allo step sucessivo a quello corrente
 */
public class NextStepProcess extends Action {

    private Category cat = Category
	    .getInstance(NextStepProcess.class.getName());

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
	// super.execute(p_actionMapping, p_actionForm, p_httpServletRequest,
	// p_httpServletResponse);

	PplUser user = PeopleContext.create(p_httpServletRequest).getUser();

	AbstractPplProcess pplProcess = (AbstractPplProcess) p_actionForm;

	if (pplProcess.isInizialized()) {
	    // pplProcess.getObserver().notifyAll();

	    pplProcess.getView().nextStep(
		    PeopleContext.create(p_httpServletRequest));
	    cat.debug("success");

	    pplProcess.updateLastCoords();
	    if (pplProcess.isLastActivityLastStep()) {
		CreateNotSubmittableProcessHelper.addNotSubmittableProcess(
			p_httpServletRequest, pplProcess);
	    }

	    // Verifica lo step impostato � quello di riepilogo
	    if (pplProcess.getView().getCurrentActivity() instanceof SummaryActivity) {
		SummaryActivity summaryActivity = (SummaryActivity) pplProcess
			.getView().getCurrentActivity();
		// L'utente anonimo deve essere sempre rimandato al riepilogo
		// In caso di mancata validazione non attivo il processo di
		// firma.
		// La motivazione di questa scelta � che il processo di firma
		// � abbastanza pesante prevedendo il salavataggio della
		// pratica
		// stessa.
		if (summaryActivity.isSignEnabled() && !user.isAnonymous()
			&& pplProcess.getData().validate()) {
		    return p_actionMapping.findForward("signProcess");
		} else {
		    return p_actionMapping.findForward("success");
		}
	    } else {
		return p_actionMapping.findForward("success");
	    }
	}

	cat.debug("failed");
	return p_actionMapping.findForward("failed");
    }

}
