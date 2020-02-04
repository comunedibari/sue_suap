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
import it.people.process.AbstractPplProcess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Category;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * User: sergio Date: Sep 8, 2003 Time: 4:54:08 PM <br>
 * Quest'azione permette di spostarsi all'attivit� voluta (ogni processo �
 * costituito da una o pi� attivit�, dotate a loro volta di steps)
 */
public class GoActivityProcess extends Action {

    private Category cat = Category.getInstance(GoActivityProcess.class
	    .getName());
    protected static final String ACTIVITY_INDEX_NAME = "actIndex";

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
    public ActionForward execute(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception {
	super.execute(mapping, form, request, response);

	AbstractPplProcess pplProcess = (AbstractPplProcess) form;
	if (pplProcess.isInizialized()) {

	    String activityToGo = request.getParameter(ACTIVITY_INDEX_NAME);
	    if (activityToGo == null)
		activityToGo = (String) request
			.getAttribute(ACTIVITY_INDEX_NAME);

	    if (activityToGo != null) {
		int nextActivityIndex = Integer.parseInt(activityToGo);
		pplProcess.getView().goActivity(PeopleContext.create(request),
			nextActivityIndex);

		// pplProcess.getView().setCurrentActivityIndex(
		// nextActivityIndex );
	    }

	    cat.debug("success");
	    return mapping.findForward("success");
	}

	cat.debug("failed");
	return mapping.findForward("failed");
    }
}
