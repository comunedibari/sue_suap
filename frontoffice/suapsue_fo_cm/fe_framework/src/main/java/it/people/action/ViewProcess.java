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
import it.people.core.exception.MappingException;
import it.people.layout.LayoutMenu;
import it.people.layout.NavigationBar;
import it.people.process.AbstractPplProcess;
import it.people.wrappers.HttpServletRequestDelegateWrapper;
import it.people.wrappers.IRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.PropertyUtils;

import org.apache.log4j.Category;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 * 
 * User: sergio Date: Sep 8, 2003 Time: 2:25:58 PM <br>
 * <br>
 * Questa classe permette di visualizzare ed editare un processo.
 */
public class ViewProcess extends Action {

    private Category cat = Category.getInstance(ViewProcess.class.getName());

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

	// TODO Controllare questa situazione che potrebbe essere fonte di
	// errori
	// per la gestione degli allegati, per ora commentata out.
	try {
	    FormFile ff = (FormFile) PropertyUtils.getProperty(
		    pplProcess.getData(), "uploadFile");

	    if (ff != null) {
		PropertyUtils.setProperty(pplProcess.getData(), "uploadFile",
			null);
	    }
	} catch (Exception ex) {
	    cat.debug(ex);
	}

	if (pplProcess.getStatus().equals("S_SIGN")) {
	    cat.debug("Status = " + pplProcess.getStatus());
	    cat.debug("signProcess");
	    return p_actionMapping.findForward("signProcess");
	}

	if (pplProcess.isInizialized()) {
	    PeopleContext context = PeopleContext.create(p_httpServletRequest);
	    IRequestWrapper requestWrapper = new HttpServletRequestDelegateWrapper(
		    p_httpServletRequest);

	    if (pplProcess.getValidationErrors() == null
		    || pplProcess.getValidationErrors().isEmpty()) {
		try {
		    // prima di eseguire il metodo service devo istanziare il
		    // DTO corretto...
		    pplProcess.createDto();
		    pplProcess.getView().service(pplProcess, requestWrapper);
		    pplProcess.writeDto();
		} catch (MappingException e) {
		    cat.error("Errore di SCRITTURA DEI DATI SUL TO", e);
		}
	    }

	    HttpSession session = p_httpServletRequest.getSession(true);

	    refreshLayoutComponents(pplProcess, session);
	    // draw blocca il processo e genera la pagina quindi PRIMA bisogna
	    // refreshare i componenti di layout
	    pplProcess.getView().draw(context, p_httpServletRequest,
		    p_httpServletResponse);
	    return null;
	}
	cat.debug("failed");
	return p_actionMapping.findForward("failed");
    }

    private void refreshLayoutComponents(AbstractPplProcess process,
	    HttpSession session) {

	LayoutMenu theMenu = null;
	NavigationBar theNavBar = null;

	theMenu = (LayoutMenu) session.getAttribute("menuObject");
	if (theMenu != null) {
	    theMenu.update(process.getView());
	    // theMenu.setActivityList(process.getView().getActivities());
	    session.setAttribute("menuObject", theMenu);
	}

	theNavBar = (NavigationBar) session.getAttribute("navBarObject");
	if (theNavBar != null) {
	    theNavBar.update(process.getView());
	    session.setAttribute("navBarObject", theNavBar);
	}
    }
}
