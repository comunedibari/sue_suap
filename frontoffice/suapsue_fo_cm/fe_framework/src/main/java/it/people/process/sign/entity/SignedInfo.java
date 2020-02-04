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

import org.apache.xerces.utils.Base64;

/**
 * 
 * User: sergio Date: Oct 3, 2003 Time: 5:17:23 PM <br>
 * <br>
 */
public class SignedInfo {

    private byte[] m_pkcs7Data;
    private byte[] m_data;
    private byte[] m_sign;
    private String m_userDN;

    private String m_mailAddress;

    // private String m_holderKey;

    public SignedInfo() {
	m_userDN = null;
	m_mailAddress = null;
    }

    public SignedInfo(String userDN) {
	// m_holderKey = holderKey;
	m_mailAddress = extractMailAddress(userDN);
    }

    public byte[] getPkcs7Data() {
	return m_pkcs7Data;
    }

    public void setPkcs7Data(byte[] p_pkcs7Data) {
	m_pkcs7Data = p_pkcs7Data;
    }

    public byte[] getData() {
	return m_data;
    }

    public void setData(byte[] p_data) {
	m_data = p_data;
    }

    public byte[] getSign() {
	return m_sign;
    }

    public void setSign(byte[] p_sign) {
	m_sign = p_sign;
    }

    public String getUserDN() {
	return m_userDN;
    }

    public void setUserDN(String p_userDN) {
	m_userDN = p_userDN;
	m_mailAddress = extractMailAddress(m_userDN);
    }

    /*
     * public String getHolderKey() { return m_holderKey; }
     */

    public String getMailAddress() {
	return m_mailAddress;
    }

    private String extractMailAddress(String userDN) {
	int mailIndex = userDN.indexOf("MAIL=");
	if (mailIndex != -1) {
	    int mailEnd = userDN.indexOf(',', mailIndex);
	    if (mailEnd != -1)
		return userDN.substring(mailIndex + 5, mailEnd);
	}
	return null;
    }

    public String getEncodedSing() {
	return new String(Base64.encode(m_sign));
    }
}
