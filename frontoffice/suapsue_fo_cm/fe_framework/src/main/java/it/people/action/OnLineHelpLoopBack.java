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
import it.people.wrappers.HttpServletRequestDelegateWrapper;
import it.people.wrappers.IRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class OnLineHelpLoopBack extends Action {

    private static final Logger logger = LogManager
	    .getLogger(OnLineHelpLoopBack.class);

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
    public ActionForward execute(ActionMapping p_actionMapping,
	    ActionForm p_actionForm, HttpServletRequest p_httpServletRequest,
	    HttpServletResponse p_httpServletResponse) throws Exception {
	super.execute(p_actionMapping, p_actionForm, p_httpServletRequest,
		p_httpServletResponse);

	AbstractPplProcess pplProcess = (AbstractPplProcess) p_actionForm;
	if (pplProcess.isInizialized()) {
	    PeopleContext context = PeopleContext.create(p_httpServletRequest);
	    IRequestWrapper requestWrapper = new HttpServletRequestDelegateWrapper(
		    p_httpServletRequest);

	    pplProcess.getView().onLineHelploopBack(pplProcess, requestWrapper);

	    pplProcess.getView().draw(context, p_httpServletRequest,
		    p_httpServletResponse);
	    return null;
	}
	if (logger.isDebugEnabled()) {
	    logger.debug("failed");
	}
	return p_actionMapping.findForward("failed");
    }

}
