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

/**
 * Created by IntelliJ IDEA. User: sergio Date: Sep 24, 2003 Time: 7:41:58 AM To
 * change this template use Options | File Templates.
 */
public class PipelineDataHolderWrapper {
    private PipelineDataHolder m_dataHolder;

    public PipelineDataHolderWrapper(PipelineDataHolder pdh) {
	m_dataHolder = pdh;
    }

    public int getAssigned() {
	return m_dataHolder.m_assigned;
    }

    public void setAssigned(int assignedTo) {
	m_dataHolder.setAssigned(assignedTo);
    }

    public String getKey() {
	return m_dataHolder.getKey();
    }

    public void setKey(String key) {
	m_dataHolder.setKey(key);
    }

}
