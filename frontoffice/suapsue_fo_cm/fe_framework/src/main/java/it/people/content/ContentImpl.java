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
package it.people.content;

/**
 * 
 * User: sergio Date: Oct 3, 2003 Time: 8:06:57 PM <br>
 * <br>
 * Procedimento contenuto in una categoria.
 */
public class ContentImpl {
    private Long m_oid;
    private String m_key;
    private String m_name;
    private Class m_processClass;
    private String m_processName;

    public ContentImpl() {
	m_key = null;
	m_name = "";
	m_processClass = null;
	m_processName = null;
    }

    public ContentImpl(String key, String name, Class process,
	    String processName) {
	m_key = key;
	m_name = name;
	m_processClass = process;
	m_processName = processName;
    }

    public Long getOid() {
	return m_oid;
    }

    public void setOid(Long p_oid) {
	m_oid = p_oid;
    }

    public String getKey() {
	return m_key;
    }

    public void setKey(String p_key) {
	m_key = p_key;
    }

    public String getName() {
	return m_name;
    }

    public void setName(String p_name) {
	m_name = p_name;
    }

    public String getProcessName() {
	return m_processName;
    }

    public void setProcessName(String p_name) {
	m_processName = p_name;
    }

    public Class getProcessClass() {
	return m_processClass;
    }

    public void setProcessClass(Class p_processClass) {
	m_processClass = p_processClass;
    }
}
