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

import java.io.File;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Oct 13, 2003 Time: 12:00:27 PM
 * To change this template use Options | File Templates.
 */
public class SignedAttachment extends Attachment {

    private static final long serialVersionUID = 5454914923125951946L;

    private String m_sign;

    public SignedAttachment() {
	super();
	m_sign = null;
    }

    public SignedAttachment(String name, String path, String sign) {
	super(name, path);
	m_sign = sign;
    }

    public SignedAttachment(String name, File file, String sign) {
	super(name, file);
	m_sign = sign;
    }

    public String getSign() {
	return m_sign;
    }

    public void setSign(String p_sign) {
	m_sign = p_sign;
    }
}
