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
import it.people.core.persistence.PersistenceManager;
import it.people.core.persistence.PersistenceManagerFactory;
import it.people.core.persistence.exception.peopleException;
import it.people.util.PeopleProperties;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Dec 5, 2003 Time: 11:07:07 AM To
 * change this template use Options | File Templates.
 */
public class PplUserManagerTomcatImpl implements PplUserManager {
    public final static String SU_USERNAME = "Admin";

    private static PplUserManagerTomcatImpl ourInstance;

    public synchronized static PplUserManagerTomcatImpl getInstance() {
	if (ourInstance == null) {
	    ourInstance = new PplUserManagerTomcatImpl();
	}
	return ourInstance;
    }

    private PplUserManagerTomcatImpl() {
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
	    PplUser user = get(request.getUserPrincipal().getName(),
		    request.getHeader("EMAIL_ADDRESS"),
		    request.getHeader("SOCIAL_SECURITY_NUMBER"));
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
     * @throws peopleException
     * 
     */
    public PplUser get(String userName, String eMail, String userID)
	    throws peopleException {
	if (userName == null || userName.length() == 0)
	    return null;
	if (((Boolean) PeopleProperties.INTERTNAL_USER_REPOSITORY.getValue())
		.booleanValue() && !SU_USERNAME.equals(userName)) {
	    PersistenceManager persMgr = PersistenceManagerFactory
		    .getInstance().get(PplUser.class,
			    PersistenceManager.Mode.READ);

	    try {
		Collection users = null;

		Criteria crtr = new Criteria();
		crtr.addEqualTo("USER_NAME", userName);

		users = persMgr.get(QueryFactory.newQuery(PplUser.class, crtr));
		if (users != null && !users.isEmpty()) {
		    Iterator iter = users.iterator();
		    return (PplUser) iter.next();
		} else {
		    throw new InvalidUserException("Invalid user: userName="
			    + userName + ", userID=" + userID);
		}
	    } finally {
		if (persMgr != null) {
		    persMgr.close();
		}
	    }

	} else {
	    PplUser user = new PplUser();
	    if (SU_USERNAME.equals(userName)) {
		eMail = "peopleMail@people.com";
		userID = "00000000000000";
	    }
	    if (userName == null || userName.length() == 0 || userID == null
		    || userID.length() == 0)
		throw new InvalidUserException("Invalid user: userName="
			+ userName + ", userID=" + userID);
	    user.setUserName(userName);
	    user.setEMail(eMail);
	    user.setUserID(userID);
	    return user;
	}
    }

    public PplUser get(HttpSession p_session) throws peopleException {
	return null;
    }
}
