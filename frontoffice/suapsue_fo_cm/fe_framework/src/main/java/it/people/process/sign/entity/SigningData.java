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
package it.people.process.sign.entity;

import it.people.util.MimeType;

/**
 * 
 * User: sergio Date: Nov 28, 2003 Time: 6:52:49 PM <br>
 * <br>
 */
public abstract class SigningData {
    private String m_key;
    private Object m_content;
    private String m_friendlyName;

    public SigningData() {
	m_key = null;
	m_content = null;
    }

    public SigningData(String key, String friendlyName, Object content) {
	this();
	m_key = key;
	m_friendlyName = friendlyName;
	m_content = content;
    }

    public String getKey() {
	return m_key;
    }

    public void setKey(String p_key) {
	m_key = p_key;
    }

    protected Object getObject() {
	return m_content;
    }

    protected void setObject(Object content) {
	m_content = content;
    }

    public String getFriendlyName() {
	return m_friendlyName;
    }

    public void setFriendlyName(String p_friendlyName) {
	m_friendlyName = p_friendlyName;
    }

    public abstract byte[] getBytes();

    public abstract byte[] getBytes(MimeType mimeType);

}
