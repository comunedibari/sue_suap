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
 * Created on 8-mar-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.action;

import it.people.action.dispatching.LookupDispatchAction;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ExitProcess extends LookupDispatchAction {

    private Category cat = Category.getInstance(ExitProcess.class.getName());

    public ActionForward exit(ActionMapping mapping, ActionForm form,
	    HttpServletRequest req, HttpServletResponse res)
	    throws IOException, ServletException {

	return mapping.findForward("exit");
    }

    public ActionForward cancel(ActionMapping mapping, ActionForm form,
	    HttpServletRequest req, HttpServletResponse res)
	    throws IOException, ServletException {

	return mapping.findForward("cancelExit");
    }

    protected Map getKeyMethodMap() {
	Map map = new HashMap();
	map.put("label.exitProcess.ok", "exit");
	map.put("label.exitProcess.cancel", "cancel");
	return map;
    }
}
