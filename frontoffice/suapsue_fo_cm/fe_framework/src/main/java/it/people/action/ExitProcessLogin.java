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
 * Creato il 4-giu-2007 da Cedaf s.r.l.
 *
 */
package it.people.action;

import it.people.PeopleConstants;
import it.people.process.AbstractPplProcess;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 */
public class ExitProcessLogin extends ExitProcess {
    private static Logger logger = Logger.getLogger(ExitProcessLogin.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.people.action.ExitProcess#exit(org.apache.struts.action.ActionMapping,
     * org.apache.struts.action.ActionForm,
     * javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */
    public ActionForward exit(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws IOException, ServletException {

	try {
	    if (form instanceof AbstractPplProcess) {
		logger.debug("Richiesto il login da dentro il servizio");
		AbstractPplProcess pplProcess = (AbstractPplProcess) form;
		String processName = pplProcess.getProcessName();
		request.getSession().setAttribute(
			PeopleConstants.REQUEST_PROCESS_NAME, processName);
		logger.debug("Nome servizio = \"" + processName + "\"");
	    }
	    return super.exit(mapping, form, request, response);

	} catch (IOException ioEx) {
	    throw ioEx;
	} catch (ServletException sEx) {
	    throw sEx;
	} catch (Exception ex) {
	    logger.error("Errore nel reindirizzamento al login", ex);
	    throw new ServletException(ex);
	}
    }
}
