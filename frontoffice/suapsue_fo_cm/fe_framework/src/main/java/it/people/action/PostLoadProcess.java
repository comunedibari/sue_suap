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

import it.people.City;
import it.people.PeopleConstants;
import it.people.core.PplUserData;
import it.people.process.AbstractPplProcess;
import it.people.process.PplData;
import it.people.process.data.AbstractData;
import it.people.propertymgr.PropertyFormatException;
import it.people.util.PeopleProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Category;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PostLoadProcess extends Action {

    private Category cat = Category
	    .getInstance(PostLoadProcess.class.getName());

    public ActionForward execute(ActionMapping p_actionMapping,
	    ActionForm p_actionForm, HttpServletRequest p_servletRequest,
	    HttpServletResponse p_servletResponse) throws Exception {

	// FIX - 20080121 by CEFRIEL

	// Questa action � stata introdotta per supportare l'accesso da parte
	// di rappresentanti e/o operatori di
	// associazioni di categoria. Viene posta subito a valle della
	// InitProcess che le passa il controllo mediante
	// una REDIRECT e non una FORWARD come per le altre action. In questo
	// modo � possibile per il filtro di
	// modifica del profilo intercettare la richiesta e presentare la pagina
	// con i dati dei profili richiedente
	// e titolare, consentendo l'aggiornamento del domicilio elettronico.
	// Il codice in questa action memorizza nell'AbstractData (a cui
	// corrisponde il ProcessData del generico
	// servizio) il valore del domicilio elettronico aggiornato, cos�
	// all'atto del salvataggio della pratica,
	// esso venga memorizzato su database con gli altri dati e possa quindi
	// essere ripristinato.

	super.execute(p_actionMapping, p_actionForm, p_servletRequest,
		p_servletResponse);

	HttpSession session = ((HttpServletRequest) p_servletRequest)
		.getSession();
	String communeId = ((City) session
		.getAttribute(PeopleConstants.SESSION_NAME_COMMUNE)).getKey();

	String sirac_authenticated_user_data_attribute_name = "";
	try {
	    sirac_authenticated_user_data_attribute_name = PeopleProperties.SIRAC_AUTHENTICATED_USER_DATA_ATTRIBUTE_NAME
		    .getValueString(communeId);

	} catch (PropertyFormatException e2) {
	    if (cat.isDebugEnabled()) {
		cat.error("PplUserManagerWebServiceAuthImpl::get() - Impossibile recuperare il nome dell'attributo di sessione con i dati dell'utente autenticato."
			+ e2.getMessage());
	    }
	    throw new Exception(
		    "PplUserManagerWebServiceAuthImpl::get() - Impossibile recuperare il nome dell'attributo di sessione con i dati dell'utente autenticato.");
	}

	PplUserData userdata = (PplUserData) p_servletRequest.getSession()
		.getAttribute(sirac_authenticated_user_data_attribute_name);

	PplData pplData = ((AbstractPplProcess) p_actionForm).getData();
	AbstractData abstractData = (AbstractData) pplData;
	abstractData.setDomicilioElettronico(userdata.getEmailaddress());

	return p_actionMapping.findForward("success");

    }

}
