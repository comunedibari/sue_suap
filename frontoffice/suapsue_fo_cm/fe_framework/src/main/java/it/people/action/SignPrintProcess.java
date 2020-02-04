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
/*
 * Created on 20-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 *
 *
 * FIRMA_DIGITALE
 */
package it.people.action;

import it.people.City;
import it.people.core.CommuneManager;
import it.people.core.persistence.exception.peopleException;
import it.people.error.MessagesFactory;
import it.people.error.errorMessage;
import it.people.process.AbstractPplProcess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Category;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Michele Fabbri
 * 
 *         Questa classe inizia il processo di stampa
 */
public class SignPrintProcess extends Action {

    private Category cat = Category.getInstance(SignProcess.class.getName());

    public ActionForward execute(ActionMapping p_actionMapping,
	    ActionForm p_actionForm, HttpServletRequest p_httpServletRequest,
	    HttpServletResponse p_httpServletResponse) throws Exception {
	super.execute(p_actionMapping, p_actionForm, p_httpServletRequest,
		p_httpServletResponse);

	ActionForward afwd = null;

	AbstractPplProcess pplProcess = (AbstractPplProcess) p_actionForm;
	City commune = null;

	if (pplProcess != null)
	    commune = pplProcess.getCommune();
	else {
	    try {
		commune = CommuneManager.getInstance().get();
	    } catch (peopleException pplEx) {
		cat.error(pplEx);
		pplEx.printStackTrace(System.out);
	    }
	}

	try {
	    if (pplProcess.isInizialized())
		if (pplProcess.getSign().isInitialized())
		    pplProcess.getSign().print(p_httpServletRequest,
			    p_httpServletResponse);
	} catch (Exception ex) {
	    cat.error(ex);
	    ex.printStackTrace(System.out);
	    errorMessage m_error = MessagesFactory.getInstance()
		    .getErrorMessage(commune.getKey(), "SignProcess.dbError");
	    m_error.setErrorForward("/framework/genericErrors/ProcessError.jsp");
	    p_httpServletRequest.setAttribute("errorMessage", m_error);
	    afwd = p_actionMapping.findForward("failed");
	}

	return afwd;
    }
}
