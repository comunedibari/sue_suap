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

import it.people.City;
import it.people.PeopleConstants;
import it.people.core.persistence.exception.peopleException;
import java.io.Serializable;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * User: sergio Date: Sep 17, 2003 Time: 2:50:07 PM <br>
 * <br>
 * 
 */
public class PeopleContext implements Serializable {

    private org.apache.log4j.Category cat = org.apache.log4j.Category
	    .getInstance(PeopleContext.class.getName());

    private static final String currentUserAttribute = "currentLoggedUser";

    private HttpSession m_session;

    private PplUser m_user;

    private String characterEncoding;

    private PeopleContext() {
	m_session = null;
	m_user = null;
	characterEncoding = PeopleConstants.DEFAULT_CHARACTER_ENCODING;
    }

    /**
     * 
     * @param request
     * @return il contesto inizializzato.
     */
    public static PeopleContext create(HttpServletRequest request) {
	PeopleContext pc = new PeopleContext();
	pc.m_session = request.getSession();

	PplUser user = (PplUser) pc.m_session
		.getAttribute(currentUserAttribute);
	if (user == null) {
	    try {
		user = PplUserManagerFactory.getInstance().getPplUserManager()
			.get(request);
	    } catch (peopleException pplEx) {

	    }

	    pc.m_session.setAttribute(currentUserAttribute, user);
	}
	pc.setUser(user);

	if (user == null)
	    return null;
	return pc;
    }

    /**
     * Ottiene il contesto settando l'utente passato come parametro.
     * 
     * @param user
     *            l'utente da settare nel contesto.
     * @return il contesto inizializzato.
     */
    public static PeopleContext create(PplUser user) {
	PeopleContext pc = new PeopleContext();
	pc.m_session = null;
	pc.setUser(user);
	return pc;
    }

    /**
     * Ottiene il contesto solo se l'username passato esiste.
     * 
     * @param userName
     *            la username dell'utente da settare nel contesto.
     * @return il contesto inizializzato.
     */
    public static PeopleContext create(String userName) {

	return create(userName, "email@people.it", "000000000000");
    }

    public static PeopleContext create(String userName, String eMail, String ssn) {
	PeopleContext pc = new PeopleContext();
	pc.m_session = null;
	PplUser user = null;
	try {
	    user = PplUserManagerFactory.getInstance().getPplUserManager()
		    .get(userName, eMail, ssn);
	    if (user != null) {
		pc.setUser(user);
		return pc;
	    }
	} catch (peopleException pplEx) {

	}
	return null;
    }

    public HttpSession getSession() {
	return m_session;
    }

    public PplUser getUser() {
	return m_user;
    }

    public void setUser(PplUser p_user) {
	m_user = p_user;
    }

    public void removeUser() {
	m_user = null;
    }

    public City getCommune() {
	return (City) this.m_session
		.getAttribute(PeopleConstants.SESSION_NAME_COMMUNE);
    }

    public Locale getLocale() {
	Locale locale = (Locale) this.getSession().getAttribute(
		PeopleConstants.USER_LOCALE_KEY);
	if (locale == null) {
	    locale = Locale.ITALY;
	}
	return locale;
    }

    /**
     * @param characterEncoding
     */
    public final void setCharacterEncoding(final String characterEncoding) {
	this.characterEncoding = characterEncoding;
    }

    /**
     * @return
     */
    public final String getCharacterEncoding() {
	return StringUtils.defaultString(this.characterEncoding,
		PeopleConstants.DEFAULT_CHARACTER_ENCODING);
    }

}
