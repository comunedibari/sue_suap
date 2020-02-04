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

import it.people.process.AbstractPplProcess;
import it.people.util.status.StatusHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Category;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * User: Michele Fabbri - Cedaf s.r.l. Date: 20/04/2006 Time: 11.40 <br>
 * 
 */
public class AbortPayment extends Action {
    private Category cat = Category.getInstance(AbortPayment.class.getName());

    /**
     * Annula il pagamento
     * 
     * @param p_actionMapping
     *            Oggetto contenente il mapping delle azioni e dei forward
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
	try {
	    AbstractPplProcess process = (AbstractPplProcess) p_actionForm;
	    StatusHelper.deletePendingProcessStatus(process
		    .getIdentificatoreProcedimento());
	    cat.debug("Pagamento annullato correttamente");
	    return p_actionMapping.findForward("success");
	} catch (Exception ex) {
	    cat.error("Errore nell'annullamento del pagamento", ex);
	    return p_actionMapping.findForward("failed");
	}
    }
}
