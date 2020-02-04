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

import java.io.Serializable;

/**
 * 
 * User: Luigi Corollo Date: 15-gen-2004 Time: 10.54.32 <br>
 * <br>
 * Rappresenta la tabella 'AMMINISTRATORE_COMMUNE', che contiene i comuni e i
 * relativi amministratori.
 */
public class PplAdminCommune implements Serializable {

    private Long m_oid;

    private String m_userID;
    private String m_communeID;
    private Long m_userRef;

    public String getUserID() {
	return m_userID;
    }

    public void setUserID(String p_userID) {
	m_userID = p_userID;
    }

    public String getCommuneID() {
	return m_communeID;
    }

    public void setCommuneID(String p_communeID) {
	m_communeID = p_communeID;
    }

    public String toString() {
	return "PplAdminCommune[" + m_communeID + "]";
    }

    public void setOid(Long p_oid) {
	m_oid = p_oid;
    }

    public Long getOid() {
	return m_oid;
    }

    public void setUserRef(Long p_userRef) {
	m_userRef = p_userRef;
    }

    public Long getUserRef() {
	return m_userRef;
    }
}
