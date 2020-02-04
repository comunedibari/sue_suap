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
 * Created on 15-giu-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.people.core;

import it.people.City;
import it.people.PeopleConstants;
import it.people.core.exception.InvalidUserException;
import it.people.core.persistence.PersistenceManager;
import it.people.core.persistence.PersistenceManagerFactory;
import it.people.core.persistence.exception.peopleException;
import it.people.propertymgr.PropertyFormatException;
import it.people.util.PeopleProperties;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;

/**
 * @author max
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PplUserManagerWebServiceAuthImpl implements PplUserManager {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
	    .getLogger(PplUserManagerWebServiceAuthImpl.class);

    public final static String SU_USERNAME = "Admin";
    // public final static String ANONYMOUS_USERID = "ANONYMOUS@";

    /**
     * Questo ruolo e' utilizzato per l'autenticazione dell'amministratore il
     * ruolo � definito a livello di application server.
     */
    public static final String PEOPLE_ADMIN_ROLE = "peopleAdmin";

    private static PplUserManagerWebServiceAuthImpl ourInstance;

    public synchronized static PplUserManagerWebServiceAuthImpl getInstance() {
	if (ourInstance == null) {
	    ourInstance = new PplUserManagerWebServiceAuthImpl();
	}
	return ourInstance;
    }

    private PplUserManagerWebServiceAuthImpl() {
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

	    HttpSession session = ((HttpServletRequest) request).getSession();

	    String communeId = ((City) session
		    .getAttribute(PeopleConstants.SESSION_NAME_COMMUNE))
		    .getKey();
	    PplUser user = null;

	    if (request.isUserInRole("admin")) {
		user = getAdminUser(request.getUserPrincipal().getName(),
			request.getHeader("EMAIL_ADDRESS"),
			request.getHeader("SOCIAL_SECURITY_NUMBER"));

	    } else {
		user = get(request.getHeader("USER_LOGIN_NAME"),
			request.getHeader("USER_EMAIL_ADDRESS"),
			request.getHeader("USER_IDENTIFICATION"));
	    }

	    // =====================================================================================
	    // Codice Modificato per eliminare la dipendenza dalle classi del
	    // SIRAC

	    String sirac_authenticated_user_data_attribute_name = "";
	    try {
		sirac_authenticated_user_data_attribute_name = PeopleProperties.SIRAC_AUTHENTICATED_USER_DATA_ATTRIBUTE_NAME
			.getValueString(communeId);

	    } catch (PropertyFormatException e2) {
		// e2.printStackTrace();
		if (logger.isDebugEnabled()) {
		    logger.error("PplUserManagerWebServiceAuthImpl::get() - Impossibile recuperare il nome dell'attributo di sessione con i dati dell'utente autenticato."
			    + e2.getMessage());
		}
		throw new peopleException(
			"PplUserManagerWebServiceAuthImpl::get() - Impossibile recuperare il nome dell'attributo di sessione con i dati dell'utente autenticato.");
	    }

	    // Modifica Michele Fabbri - Cedaf
	    // Determina se l'utente � utente amministratore
	    if (request.isUserInRole("admin")) {
		try {
		    user.setPeopleAdmin(((HttpServletRequest) request)
			    .isUserInRole(PplUserManagerWebServiceAuthImpl.PEOPLE_ADMIN_ROLE));
		} catch (Exception ex) {
		    logger.error("PplUserManagerWebServiceAuthImpl::get() - Impossibile determinare se l'utente � amministratore");
		}
	    } else {

		String userIdWithoutCA = (user.getUserID() != null && user
			.getUserID().contains("@")) ? user.getUserID()
			.substring(0, user.getUserID().indexOf('@')) : user
			.getUserID();

		PersistenceManager persMgr = PersistenceManagerFactory
			.getInstance().get(PplUser.class,
				PersistenceManager.Mode.READ);
		Collection users = null;

		Criteria crtr = new Criteria();
		crtr.addEqualTo("USER_ID", userIdWithoutCA);

		users = persMgr.get(QueryFactory.newQuery(PplUser.class, crtr));
		if (users != null && !users.isEmpty()) {
		    Iterator usersIterator = users.iterator();
		    PplUser adminUser = (PplUser) usersIterator.next();

		    if (user != null
			    && !user.getUserID().equalsIgnoreCase(
				    PeopleConstants.ANONYMOUS_USERID)) {
			persMgr = PersistenceManagerFactory.getInstance().get(
				PplUser.class, PersistenceManager.Mode.WRITE);
			PplUserData userData = (PplUserData) session
				.getAttribute(sirac_authenticated_user_data_attribute_name);
			adminUser.setEMail(userData.getEmailaddress());
			adminUser.setUserName(userData.getNome() + " "
				+ userData.getCognome());
			persMgr.set(adminUser);
		    }

		    boolean isCommuneAdmin = false;
		    List communi = adminUser.getAdminCommuni();
		    Iterator communiIterator = communi.iterator();
		    while (communiIterator.hasNext() && !isCommuneAdmin) {
			PplAdminCommune pplAdminCommune = (PplAdminCommune) communiIterator
				.next();
			isCommuneAdmin = pplAdminCommune.getCommuneID()
				.equalsIgnoreCase("*")
				|| pplAdminCommune.getCommuneID()
					.equalsIgnoreCase(communeId);
		    }

		    user.setPeopleAdmin(isCommuneAdmin);
		}
		if (persMgr != null) {
		    persMgr.close();
		}

	    }

	    if (user != null) {
		user.setUserData((PplUserData) session
			.getAttribute(sirac_authenticated_user_data_attribute_name));

		// Determina se l'utente � anonimo
		user.setAnonymous(user.getUserID().equalsIgnoreCase(
			PeopleConstants.ANONYMOUS_USERID));

		return user;
	    }
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

    public PplUser getAdminUser(String userName, String eMail, String userID)
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
}
