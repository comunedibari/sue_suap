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

/**
 * 
 * User: sergio Date: Dec 31, 2003 Time: 11:47:52 AM <br>
 * <br>
 */
public class PplPrincipal {
    private String m_userID;
    private PplRole m_role;
    private Long m_oid;

    public PplPrincipal() {
	m_userID = null;
	m_role = null;
    }

    /**
     * Crea un oggetto PplPrincipal associando all'utente il ruolo
     * 
     * @param userID
     *            Id utente
     * @param role
     *            Ruolo
     */
    public PplPrincipal(String userID, PplRole role) {
	m_userID = userID;
	m_role = role;
    }

    public Long getOid() {
	return m_oid;
    }

    public void setOid(Long oid) {
	m_oid = oid;
    }

    public String getUserID() {
	return m_userID;
    }

    public void setUserID(String p_userID) {
	m_userID = p_userID;
    }

    public PplRole getRole() {
	return m_role;
    }

    public void setRole(PplRole p_role) {
	m_role = p_role;
    }

    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof PplPrincipal))
	    return false;

	final PplPrincipal pplPrincipal = (PplPrincipal) o;

	if (m_role != null ? !m_role.equals(pplPrincipal.m_role)
		: pplPrincipal.m_role != null)
	    return false;
	if (m_userID != null ? !m_userID.equals(pplPrincipal.m_userID)
		: pplPrincipal.m_userID != null)
	    return false;

	return true;
    }

    public boolean like(PplPrincipal principal) {
	if (principal == null)
	    return false;
	if (this == principal)
	    return true;

	if (m_role != null && principal.m_role != null
		&& !m_role.equals(principal.m_role))
	    return false;
	if (m_userID != null && principal.m_userID != null
		&& !m_userID.equals(principal.m_userID))
	    return false;
	return true;
    }

    public int hashCode() {
	int result;
	result = (m_userID != null ? m_userID.hashCode() : 0);
	result = 29 * result + (m_role != null ? m_role.hashCode() : 0);
	return result;
    }
}
