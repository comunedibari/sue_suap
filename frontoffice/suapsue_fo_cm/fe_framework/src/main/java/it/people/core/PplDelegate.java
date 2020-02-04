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
 * User: sergio Date: Jan 6, 2004 Time: 2:55:10 PM <br>
 * <br>
 * Classe che mappa la tabella 'Pending_Process_Delegate', i delegati associati
 * al procedimento pendente.
 */
public class PplDelegate extends PplUser {

    // process_id
    private Long m_oid;

    // user_id
    // private String m_userID; ...da PplUser

    // delegate_id
    // private String m_delegateID;
    private PplUser m_owner;

    public PplDelegate() {
	super();
	m_owner = new PplUser();
    }

    public PplDelegate(String ownerId, String delegateId) {
	super();
	setUserID(delegateId);
	m_owner = new PplUser();
	m_owner.setUserID(ownerId);
    }

    public PplDelegate(PplUser p_owner, PplUser p_delegate) {
	super();
	setUserID(p_delegate.getUserID());
	m_owner = p_owner;
    }

    /*
     * public PplDelegate(String delegateId, String eMail, PplUser p_owner) {
     * super(); setUserID(delegateId); setEMail(eMail); m_owner = p_owner; }
     */

    public PplUser getOwner() {
	return m_owner;
    }

    public void setOwner(PplUser p_owner) {
	m_owner = p_owner;
    }

    // PROCESS_ID
    public Long getOid() {
	return m_oid;
    }

    public void setOid(Long oid) {
	m_oid = oid;
    }

    // USER_ID
    public String getOwnerID() {
	return m_owner.getUserID();
    }

    public void setOwnerID(String p_ownerID) {
	this.m_owner.setUserID(p_ownerID);
    }

    // DELEGATE_ID
    public String getDelegateID() {
	return getUserID();
    }

    public void setDelegateID(String p_delegateID) {
	this.setUserID(p_delegateID);
    }

}
