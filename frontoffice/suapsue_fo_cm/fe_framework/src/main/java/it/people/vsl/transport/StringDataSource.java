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
package it.people.vsl.transport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

public class StringDataSource implements DataSource {

    private String m_string;
    private String m_name;
    private String m_contentType;

    public StringDataSource() {
	m_name = "Ricevuta.txt.p7m";
	m_contentType = "application/octet-stream";
    }

    public StringDataSource(String p_name, String p_data) {
	m_string = p_data;
	m_name = p_name;
	m_contentType = "application/pkcs7-mime";
    }

    public void setName(String p_name) {
	m_name = p_name;
    }

    public String getName() {
	return m_name;
    }

    public void setContentType(String p_contentType) {
	m_contentType = p_contentType;
    }

    public String getContentType() {
	return m_contentType;
    }

    public OutputStream getOutputStream() throws java.io.IOException {
	return new ByteArrayOutputStream();
    }

    public InputStream getInputStream() throws java.io.IOException {
	return new ByteArrayInputStream(m_string.getBytes());
    }

}
