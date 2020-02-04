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
 * User: Luigi Corollo Date: 8-gen-2004 Time: 10.22.11 <br>
 * <br>
 * Classe che mappa la tabella 'Process_Delegate', i delegati associati agli
 * utenti per processo e periodo di tempo.
 */
public class PplProcessDelegate implements Serializable {
    private Long m_oid;
    // private Class m_processClassName;
    private String m_processName;
    private String m_communeId;
    private String m_userId;
    private String m_delegateId;
    private java.sql.Timestamp m_validFrom;
    private java.sql.Timestamp m_validTo;

    public PplProcessDelegate() {
    };

    public Long getOid() {
	return m_oid;
    }

    public void setOid(Long p_oid) {
	this.m_oid = p_oid;
    }

    public String getProcessName() {
	return m_processName;
    }

    public void setProcessName(String p_processClassName) {
	this.m_processName = p_processClassName;
    }

    public String getCommuneId() {
	return m_communeId;
    }

    public void setCommuneId(String p_communeId) {
	this.m_communeId = p_communeId;
    }

    public String getUserId() {
	return m_userId;
    }

    public void setUserId(String p_userId) {
	this.m_userId = p_userId;
    }

    public String getDelegateId() {
	return m_delegateId;
    }

    public void setDelegateId(String p_delegateId) {
	this.m_delegateId = p_delegateId;
    }

    public java.sql.Timestamp getValidFrom() {
	return m_validFrom;
    }

    public void setValidFrom(java.sql.Timestamp p_validFrom) {
	this.m_validFrom = p_validFrom;
    }

    public java.sql.Timestamp getValidTo() {
	return m_validTo;
    }

    public void setValidTo(java.sql.Timestamp p_validTo) {
	this.m_validTo = p_validTo;
    }

}
