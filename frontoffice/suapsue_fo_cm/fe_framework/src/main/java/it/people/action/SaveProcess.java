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
import it.people.core.ProcessManager;
import it.people.core.persistence.exception.peopleException;
import it.people.error.MessagesFactory;
import it.people.error.errorMessage;
import it.people.process.AbstractPplProcess;
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
 * <br>
 * Questa classe permette di salvare sulla base dati un processo.
 */
public class SaveProcess extends Action {

    private Category cat = Category.getInstance(SaveProcess.class.getName());

    public static final String SAVE_STATUS = "SAVE_STATUS";

    /**
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
	AbstractPplProcess process = null;
	try {
	    process = (AbstractPplProcess) p_actionForm;
	    if (process.getStatus().equals("S_EDIT")) {
		try {
		    PeopleContext theContext = PeopleContext
			    .create(p_servletRequest);
		    ProcessManager.getInstance().set(theContext, process);

		    // Ripristinare lo status dei processi salvati.
		    // StatusHelper.setProcessStatus(peopleId, oid,
		    // ProcessStatus.SAVED);

		    /*
		     * SubmittedProcess sp = CreateSubmittedProcessHelper.
		     * createSubmittedProcess(theContext, process);
		     * //CreateSubmittedProcessHelper
		     * .addProcessHistory(theContext, sp,
		     * SubmittedProcessState.SAVED);
		     * CreateSubmittedProcessHelper. setProcessState(theContext,
		     * sp, SubmittedProcessState.SAVED);
		     */
		} catch (peopleException e) {
		    cat.error(e);
		    setSessionSaveMessage(p_servletRequest, false);
		    return p_actionMapping.findForward("failed");
		}
		cat.debug("success");
		ActivityLogger.getInstance().log(process,
			"Salvataggio Processo", ActivityLogger.INFO);
		setSessionSaveMessage(p_servletRequest, true);
		return p_actionMapping.findForward("success");
	    }
	    /*
	     * } catch(peopleException pex){ cat.error(pex);
	     */} catch (Exception ex) {
	    cat.error(ex);
	    setSessionSaveMessage(p_servletRequest, false);
	}
	if (process != null) {
	    errorMessage error = MessagesFactory.getInstance().getErrorMessage(
		    process.getCommune().getKey(), "SaveProcess.dbError");
	    error.setErrorForward("/framework/genericErrors/ProcessError.jsp");
	    error.setErrorSender("goActivityProcess.do?actIndex="
		    + process.getView().getCurrentActivityIndex());
	    p_servletRequest.setAttribute("errorMessage", error);
	    setSessionSaveMessage(p_servletRequest, false);
	}
	cat.debug("failed");
	setSessionSaveMessage(p_servletRequest, false);
	return p_actionMapping.findForward("failed");
    }

    private void setSessionSaveMessage(HttpServletRequest p_servletRequest,
	    boolean value) {
	if (value) {
	    p_servletRequest.getSession().setAttribute(SAVE_STATUS, value);
	} else {
	    p_servletRequest.getSession().removeAttribute(SAVE_STATUS);
	}
    }

}
