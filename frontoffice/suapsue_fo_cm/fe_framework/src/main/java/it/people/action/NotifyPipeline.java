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

import it.people.core.persistence.exception.peopleException;
import it.people.vsl.Pipeline;
import it.people.vsl.PipelineFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Category;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * User: sergio Date: Sep 17, 2003 Time: 7:44:03 AM <br>
 * <br>
 * La classe permette di "sollecitare" la lavorazione di una pratica
 */
public class NotifyPipeline extends Action {

    private Category cat = Category.getInstance(NotifyPipeline.class.getName());

    /**
     * 
     * @param mapping
     *            Oggetto contenente la "mappatura" delle azioni e dei forward
     * @param form
     *            Form inviata
     * @param request
     *            Request
     * @param response
     *            Respose
     * @return Restituisce il forward da eseguire a seconda dell'esito
     *         dell'azione
     * @throws Exception
     */

    public ActionForward execute(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

	// La pagina di arrivo � passata dalla sendprocess
	ActionForward afwd = mapping.findForward("failed");
	String redirectPage = (String) request.getAttribute("redirectPage");
	if (redirectPage != null) {
	    afwd = mapping.findForward(redirectPage);
	    if (afwd == null)
		afwd = new ActionForward(redirectPage);
	}

	// Estrazione della pipeline contente il dataholder
	String dataKey = (String) request.getAttribute("dataKey");
	if (dataKey == null)
	    dataKey = request.getParameter("dataKey");

	Pipeline pipeline = PipelineFactory.getInstance().getPipeline(dataKey);

	// Si presentano due possibilit�:
	// - il dataholder � ancora in lavorazione su qualche handler e
	// quindi esiste una pipeline che lo contiene (pipeline != null)
	// - il dataholder � gi� stato processato e rimosso e quindi non
	// � contenuto in nessuna pipeline (pipeline == null)
	if (pipeline != null) {
	    try {
		pipeline.process(request, response, dataKey);
	    } catch (peopleException pex) {
		afwd = mapping.findForward("failed");
		cat.error(pex);
	    }
	}

	return afwd;
    }
}
