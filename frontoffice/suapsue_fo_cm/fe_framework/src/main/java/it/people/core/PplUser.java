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

import it.people.process.common.entity.AbstractEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * User: sergio Date: Sep 17, 2003 Time: 2:51:05 PM <br>
 * <br>
 * 
 */
public class PplUser extends AbstractEntity {

    private Long m_oid;

    private String m_userID;
    private String m_EMail;
    private String m_userName;
    private PplUserData m_userData;
    private List m_adminCommuni = new ArrayList();
    protected boolean peopleAdmin = false;
    protected boolean anonymous = false;

    private Map<String, Object> extendedAttributes = new HashMap<String, Object>();

    /**
     * 
     */
    public PplUser() {
	super();
	this.resetExtendedAttributes();
    }

    /**
     * Indica se l'utente � anonimo
     */
    public boolean isAnonymous() {
	return anonymous;
    }

    /**
     * Permette di impostare lo stato di utente anonimo, utilizzato dal
     * framework
     * 
     * @param anonymous
     *            The anonymous to set.
     */
    public void setAnonymous(boolean anonymous) {
	this.anonymous = anonymous;
    }

    // Utente Admin per concessione e autorizzazione
    public void setPeopleAdmin(boolean peopleAdmin) {
	this.peopleAdmin = peopleAdmin;
    }

    public boolean isPeopleAdmin() {
	return this.peopleAdmin;
    }

    // fine Utente Admin per concessione e autorizzazione

    public boolean isAdministrator() {
	return (m_adminCommuni.size() > 0);
    }

    /**
     * restituisce se l'utente e' un amministratore del comune 'communeID'
     * 
     * @param communeID
     * @return
     */
    public boolean isAdministratorOf(String communeID) {
	if (communeID != null && communeID.length() > 0) {
	    Iterator it = m_adminCommuni.iterator();
	    while (it.hasNext()) {
		if (communeID.equals(((PplAdminCommune) it.next())
			.getCommuneID()))
		    return true;
	    }
	}
	return false;
    }

    public String getUserID() {
	return m_userID;
    }

    public void setUserID(String p_userID) {
	m_userID = p_userID;
    }

    public String getEMail() {
	return m_EMail;
    }

    public void setEMail(String p_EMail) {
	m_EMail = p_EMail;
    }

    public boolean equals(Object pplUsr) {
	if (m_userID != null && pplUsr instanceof PplUser)
	    return m_userID.equals(((PplUser) pplUsr).getUserID());

	return (pplUsr == null);
    }

    public int hashCode() {
	return m_userID.hashCode();
    }

    public List getAdminCommuni() {
	return m_adminCommuni;
    }

    public void setAdminCommuni(List p_adminCommuni) {
	m_adminCommuni = p_adminCommuni;
    }

    public String getUserName() {
	return m_userName;
    }

    public void setUserName(String m_userName) {
	this.m_userName = m_userName;
    }

    public PplUserData getUserData() {
	return m_userData;
    }

    public void setUserData(PplUserData p_userData) {
	m_userData = p_userData;
    }

    public void setOid(Long p_oid) {
	m_oid = p_oid;
    }

    public Long getOid() {
	return m_oid;
    }

    /**
     * @param key
     * @param value
     */
    public void addExtendedAttribute(final String key, final Object value) {
	if (this.getExtendedAttributes() == null) {
	    this.resetExtendedAttributes();
	}
	this.getExtendedAttributes().put(key, value);
    }

    /**
     * @param key
     * @return
     */
    public Object getExtendedAttribute(final String key) {
	if (this.getExtendedAttributes() == null) {
	    return null;
	} else {
	    return this.getExtendedAttributes().get(key);
	}
    }

    /**
     * @return the extendedAttributes
     */
    private Map<String, Object> getExtendedAttributes() {
	return extendedAttributes;
    }

    /**
     * @param extendedAttributes
     *            the extendedAttributes to set
     */
    private void setExtendedAttributes(Map<String, Object> extendedAttributes) {
	this.extendedAttributes = extendedAttributes;
    }

    /**
	 * 
	 */
    private void resetExtendedAttributes() {
	this.setExtendedAttributes(new HashMap<String, Object>());
    }

    public static PplUser EVERYBODY = new PplUser();

    static {
	EVERYBODY.setUserID("EveryBody");
	EVERYBODY.setEMail("");
    }
}
