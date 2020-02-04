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
package it.people.vsl;

import it.people.core.PeopleContext;
import it.people.core.PplUser;
import it.people.util.DataHolderStatus;

import java.util.Date;

/**
 * Created by IntelliJ IDEA. User: acuffaro Date: 16-set-2003 Time: 14.50.21 To
 * change this template use Options | File Templates.
 */
public class PipelineDataHolder implements java.io.Serializable {

    private static final long serialVersionUID = 2981367881012252325L;

    private PplUser m_user;

    int m_assigned;
    String m_key;
    DataHolderStatus m_status;
    PipelineData m_plineData;

    public PipelineDataHolder() {
	m_user = null;
	m_assigned = -1;
	m_key = createKey();
	m_status = null;
	m_plineData = null;
    }

    public PipelineDataHolder(PeopleContext context, PipelineData data) {
	this();
	m_user = context.getUser();
	m_plineData = data;
    }

    public int getAssigned() {
	return m_assigned;
    }

    public void setAssigned(int assignedTo) {
	m_assigned = assignedTo;
    }

    public void assignTo(int p_assigned) {
	m_assigned = p_assigned;
    }

    public String getKey() {
	return m_key;
    }

    public void setKey(String key) {
	m_key = key;
    }

    public DataHolderStatus getStatus() {
	return m_status;
    }

    public void setStatus(DataHolderStatus p_status) {
	m_status = p_status;
    }

    public void setStatusAssigned() {
	m_status = DataHolderStatus.ASSIGNED;
    }

    public void setStatusWorking() {
	m_status = DataHolderStatus.WORKING;
    }

    public void setStatusCompleted() {
	m_status = DataHolderStatus.COMPLETED;
    }

    public void setStatusAborted() {
	m_status = DataHolderStatus.ABORTED;
    }

    public PipelineData getPlineData() {
	return m_plineData;
    }

    public void setPlineData(PipelineData p_plineData) {
	m_plineData = p_plineData;
    }

    public synchronized void nextHandler() {
	m_assigned++;
	m_status = DataHolderStatus.ASSIGNED;
    }

    public synchronized void invalidate() {
	m_assigned = -1;
	m_status = DataHolderStatus.ASSIGNED;
	m_key = null;
    }

    public boolean isInvalid() {
	if (m_assigned == -1 && m_key == null)
	    return true;
	return false;
    }

    public PplUser getUser() {
	return m_user;
    }

    public void setUser(PplUser user) {
	m_user = user;
    }

    protected String createKey() {
	return Long.toString((new Date()).getTime());
    }

}
