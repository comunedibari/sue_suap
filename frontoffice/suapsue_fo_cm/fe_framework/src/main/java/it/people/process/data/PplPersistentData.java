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
package it.people.process.data;

import it.people.City;
import it.people.core.PplDelegate;
import it.people.core.PplPrincipal;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Sep 15, 2003 Time: 4:38:07 PM To
 * change this template use Options | File Templates.
 */
public class PplPersistentData implements java.io.Serializable {

    private Long oid;
    private City commune;
    private String userID;
    private String processData;
    private Class processClass;
    private Timestamp lastModifiedTime;
    private Timestamp creationTime;
    private String contentID;
    private String contentName;
    private String processName;
    private String processDataID;

    private List m_principals = new ArrayList();
    private List m_delegates = new ArrayList();

    private Boolean m_sent;
    private String m_status;

    private String offLineSignDocumentHash = "";

    private boolean offLineSignWaiting = false;

    /**
     * @return Returns the contentName.
     */
    public String getContentName() {
	return contentName;
    }

    /**
     * @param contentName
     *            The contentName to set.
     */
    public void setContentName(String contentName) {
	this.contentName = contentName;
    }

    public PplPersistentData() {
    }

    public String getProcessDataID() {
	return processDataID;
    }

    public void setProcessDataID(String processDataID) {
	this.processDataID = processDataID;
    }

    public String getStatus() {
	if (m_status != null)
	    return m_status;
	else
	    return new String("S_EDIT");
    }

    public void setStatus(String p_status) {
	if (p_status != null)
	    m_status = p_status;
	else
	    m_status = "S_EDIT";
    }

    public String getProcessName() {
	return this.processName;
    }

    public void setProcessName(String name) {
	this.processName = name;
    }

    public Long getOid() {
	return this.oid;
    }

    public void setOid(Long oid) {
	this.oid = oid;
    }

    public City getCommune() {
	return this.commune;
    }

    public void setCommune(City commune) {
	this.commune = commune;
    }

    public String getUserID() {
	return this.userID;
    }

    public void setUserID(String userID) {
	this.userID = userID;
    }

    public String getProcessData() {
	return this.processData;
    }

    public void setProcessData(String processData) {
	this.processData = processData;
    }

    public Class getProcessClass() {
	return this.processClass;
    }

    public void setProcessClass(Class processClass) {
	this.processClass = processClass;
    }

    public Timestamp getLastModifiedTime() {
	return this.lastModifiedTime;
    }

    public void setLastModifiedTime(Timestamp lastModifiedTime) {
	this.lastModifiedTime = lastModifiedTime;
    }

    public Timestamp getCreationTime() {
	return this.creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
	this.creationTime = creationTime;
    }

    public Boolean getSent() {
	if (m_sent == null)
	    return new Boolean(false);
	return m_sent;
    }

    public void setSent(Boolean p_sent) {
	if (p_sent == null)
	    p_sent = new Boolean(false);
	m_sent = p_sent;
    }

    // principal
    public PplPrincipal[] getPrincipal() {
	return (PplPrincipal[]) m_principals
		.toArray(new PplPrincipal[m_principals.size()]);
    }

    public PplPrincipal getPrincipal(int index) {
	return (PplPrincipal) m_principals.get(index);
    }

    public void setPrincipal(PplPrincipal[] principals) {
	m_principals.clear();
	for (int i = 0; i < principals.length; i++)
	    m_principals.add(principals[i]);
    }

    public void setPrincipal(int index, PplPrincipal principal) {
	m_principals.set(index, principal);
    }

    public void addPrincipal(PplPrincipal principal) {
	m_principals.add(principal);
    }

    // delegate
    public PplDelegate[] getDelegate() {
	return (PplDelegate[]) m_delegates.toArray(new PplDelegate[m_delegates
		.size()]);
    }

    public PplDelegate getDelegate(int index) {
	return (PplDelegate) m_delegates.get(index);
    }

    public void setDelegate(PplDelegate[] delegates) {
	m_delegates.clear();
	for (int i = 0; i < delegates.length; i++)
	    m_delegates.add(delegates[i]);
    }

    public void setDelegate(int index, PplDelegate delegate) {
	m_delegates.set(index, delegate);
    }

    public void addDelegate(PplDelegate delegate) {
	m_delegates.add(delegate);
    }

    public String getContentID() {
	return this.contentID;
    }

    public void setContentID(String contentID) {
	this.contentID = contentID;
    }

    /**
     * @return the offLineSignDocumentHash
     */
    public final String getOffLineSignDocumentHash() {
	return this.offLineSignDocumentHash;
    }

    /**
     * @param offLineSignDocumentHash
     *            the offLineSignDocumentHash to set
     */
    public final void setOffLineSignDocumentHash(String offLineSignDocumentHash) {
	this.offLineSignDocumentHash = offLineSignDocumentHash;
    }

    /**
     * @return the offLineSignWaiting
     */
    public final boolean isOffLineSignWaiting() {
	return this.offLineSignWaiting;
    }

    /**
     * @param offLineSignWaiting
     *            the offLineSignWaiting to set
     */
    public final void setOffLineSignWaiting(boolean offLineSignWaiting) {
	this.offLineSignWaiting = offLineSignWaiting;
    }

}
