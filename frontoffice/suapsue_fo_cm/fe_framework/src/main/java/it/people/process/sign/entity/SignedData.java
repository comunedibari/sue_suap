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

/**
 * 
 * User: sergio Date: Nov 30, 2003 Time: 8:29:18 PM <br>
 * <br>
 * Classe che rappresenta dati firmati.
 */
public class SignedData {

    private String m_key;
    private SignedInfo m_signedContent;
    private String m_friendlyName = "noname";

    private boolean m_persistented;
    private String m_path;

    public SignedData() {
	m_key = null;
	m_signedContent = null;
    }

    public SignedData(String key, String friendlyName, SignedInfo si) {
	this();
	m_key = key;

	if (friendlyName != null) {
	    if (!friendlyName.endsWith(".p7m"))
		m_friendlyName = friendlyName + ".p7m";
	    else
		m_friendlyName = friendlyName;
	} else {
	    m_friendlyName = "noname.p7m";
	}
	m_signedContent = si;
    }

    public String getKey() {
	return m_key;
    }

    public void setKey(String p_key) {
	m_key = p_key;
    }

    public SignedInfo getSignedContent() {
	return m_signedContent;
    }

    public void setSignedContent(SignedInfo p_signedContent) {
	m_signedContent = p_signedContent;
    }

    public String getFriendlyName() {
	return m_friendlyName;
    }

    public void setFriendlyName(String p_friendlyName) {
	m_friendlyName = p_friendlyName;
    }

    public boolean isPersistented() {
	return m_persistented;
    }

    public String getPath() {
	return m_path;
    }

    public void setPath(String p_path) {
	if (p_path != null) {
	    m_path = p_path;
	    m_persistented = true;
	} else {
	    m_path = null;
	    m_persistented = false;
	}
    }
}
