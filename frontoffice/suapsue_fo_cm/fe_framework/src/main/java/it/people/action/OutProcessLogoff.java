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
 * Creato il 13-lug-2006 da Cedaf s.r.l.
 *
 */
package it.people.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import it.people.City;
import it.people.PeopleConstants;
import it.people.propertymgr.PropertyFormatException;
import it.people.util.PeopleProperties;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 */
public class OutProcessLogoff extends Action {
    Logger log = Logger.getLogger(OutProcessLogoff.class);

    public ActionForward execute(ActionMapping actionMapping,
	    ActionForm actionForm, HttpServletRequest servletRequest,
	    HttpServletResponse servletResponse) throws Exception {

	try {
	    String communeId = ((City) servletRequest.getSession()
		    .getAttribute(PeopleConstants.SESSION_NAME_COMMUNE))
		    .getKey();
	    String url = PeopleProperties.HOMEPAGE_ADDRESS
		    .getValueString(communeId);
	    servletRequest.getSession().invalidate();
	    return (url != null && !url.equalsIgnoreCase("")) ? new ActionForward(
		    url, true) : actionMapping.findForward("success");
	} catch (PropertyFormatException pfex) {
	}

	servletRequest.getSession().invalidate();
	return actionMapping.findForward("success");
    }
}
