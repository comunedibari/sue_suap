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
package it.people.process;

/**
 * 
 * User: sergio Date: Sep 19, 2003 Time: 10:49:47 AM <br>
 * <br>
 * Questa classe rappresenta le informazioni correlate ai processi inviati a
 * People
 * 
 */
public class SubmittedProcessInformation {
    private Long m_oid;

    private String m_key;
    private String m_path;
    private Object m_data;

    public SubmittedProcessInformation() {
	m_key = null;
	m_path = null;
	m_data = null;
    }

    public SubmittedProcessInformation(String key, String path, Object data) {
	this();
	m_key = key;
	m_path = path;
	m_data = data;
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

    public String getPath() {
	return m_path;
    }

    public void setPath(String p_path) {
	m_path = p_path;
    }

    public String getMarshalledData() {
	if (m_data != null)
	    return m_data.toString();
	return "";
    }

    public void setMarshalledData(String str) {
	m_data = str;
    }

    public Object getData() {
	return m_data;
    }

    public void setData(Object p_data) {
	m_data = p_data;
    }

}
