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
package it.people.core;

import it.people.core.exception.InvalidUserException;
import it.people.core.persistence.exception.peopleException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Mar 12, 2004 Time: 10:48:04 AM
 * To change this template use Options | File Templates.
 */
public class PplUserManagerSiteMinderImpl implements PplUserManager {
    private static PplUserManagerSiteMinderImpl ourInstance;

    public synchronized static PplUserManagerSiteMinderImpl getInstance() {
	if (ourInstance == null) {
	    ourInstance = new PplUserManagerSiteMinderImpl();
	}
	return ourInstance;
    }

    private PplUserManagerSiteMinderImpl() {
    }

    /**
     * Crea il PplUser dalla request.
     * 
     * @param request
     *            Request
     * @return Restuisce l'utente creato dai dati della request
     */
    public PplUser get(HttpServletRequest request) throws peopleException {
	if (request != null && request.getUserPrincipal() != null) {
	    PplUser user = get(request.getHeader("USER_LOGIN_NAME"),
		    request.getHeader("USER_EMAIL_ADDRESS"),
		    request.getHeader("USER_IDENTIFICATION"));
	    return user;
	}
	return null;
    }

    /**
     * Ottiene il PplUser dal db.
     * 
     * @param userName
     *            Nome utente
     * @param eMail
     *            contiene l'indirizzo email dell'utente
     * @param userID
     *            contiene il codice fiscale dell'iutente
     * @return Restituisce l'utente crecato nel DB per USER_NAME
     * @throws it.people.core.persistence.exception.peopleException
     */
    public PplUser get(String userName, String eMail, String userID)
	    throws peopleException {
	PplUser user = new PplUser();
	if (userName == null || userName.length() == 0 || userID == null
		|| userID.length() == 0)
	    throw new InvalidUserException("Invalid user: userName=" + userName
		    + ", userID=" + userID);
	user.setUserName(userName);
	user.setEMail(eMail);
	user.setUserID(userID);
	return user;
    }

    public PplUser get(HttpSession p_session) throws peopleException {
	return null;
    }
}
