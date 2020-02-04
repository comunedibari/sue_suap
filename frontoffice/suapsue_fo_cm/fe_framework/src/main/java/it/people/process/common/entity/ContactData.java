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
package it.people.process.common.entity;

/**
 * Created by IntelliJ IDEA. User: acuffaro Date: 7-ott-2003 Time: 15.12.34 To
 * change this template use Options | File Templates.
 */

public class ContactData extends AbstractEntity {

    private static final long serialVersionUID = 6519640466210910645L;

    private String m_phone;
    private String m_fax;
    private String m_email;

    public ContactData() {
	m_phone = "";
	m_fax = "";
	m_email = "";
    }

    public ContactData(String p_pn, String p_fx, String p_em) {
	if (p_pn != null && p_fx != null && p_em != null) {
	    m_phone = p_pn;
	    m_fax = p_fx;
	    m_email = p_em;
	}
    }

    public String getPhone() {
	return m_phone;
    }

    public void setPhone(String p_phone) {
	if (p_phone != null) {
	    m_phone = p_phone;
	}
    }

    public String getFax() {
	return m_fax;
    }

    public void setFax(String p_fax) {
	if (p_fax != null) {
	    m_fax = p_fax;
	}
    }

    public String getEmail() {
	return m_email;
    }

    public void setEmail(String p_email) {
	if (p_email != null) {
	    m_email = p_email;
	}
    }
}
