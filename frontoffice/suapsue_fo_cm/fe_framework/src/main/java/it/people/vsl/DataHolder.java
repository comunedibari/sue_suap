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

import it.people.util.DataHolderStatus;

/**
 * Created by IntelliJ IDEA. User: acuffaro Date: 16-set-2003 Time: 16.10.16 To
 * change this template use Options | File Templates.
 */
public class DataHolder {

    int m_assigned;
    String m_key;
    DataHolderStatus m_status;
    PipelineData m_plineData;

    public DataHolder() {
	m_assigned = -1;
	m_key = "";
	m_status = null;
	m_plineData = null;
    }

    public int getAssigned() {
	return m_assigned;
    }

    public void assignTo(int p_assigned) {
	m_assigned = p_assigned;
    }

    public String getKey() {
	return m_key;
    }

    public void setKey(String p_key) {
	m_key = p_key;
    }

    public DataHolderStatus getStatus() {
	return m_status;
    }

    private void setStatus(DataHolderStatus p_status) {
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

    public PipelineData getPlineData() {
	return m_plineData;
    }

    public void setPlineData(PipelineData p_plineData) {
	m_plineData = p_plineData;
    }
}
