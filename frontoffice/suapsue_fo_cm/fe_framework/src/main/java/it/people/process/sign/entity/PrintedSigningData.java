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

import it.people.process.AbstractPplProcess;
import it.people.util.MimeType;

import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 
 * User: sergio Date: Nov 28, 2003 Time: 7:13:17 PM <br>
 * <br>
 */
public class PrintedSigningData extends SigningData {

    private static final Logger logger = LogManager
	    .getLogger(PrintedSigningData.class);

    HttpServletRequest m_request = null;
    HttpServletResponse m_response = null;
    AbstractPplProcess m_process = null;

    public PrintedSigningData() {
	super();
    }

    public PrintedSigningData(String key, String friendlyName,
	    String printModuleName) {
	super(key, friendlyName, printModuleName);
    }

    public HttpServletRequest getRequest() {
	return m_request;
    }

    public void setRequest(HttpServletRequest p_request) {
	m_request = p_request;
    }

    public HttpServletResponse getResponse() {
	return m_response;
    }

    public void setResponse(HttpServletResponse p_response) {
	m_response = p_response;
    }

    public AbstractPplProcess getProcess() {
	return m_process;
    }

    public void setProcess(AbstractPplProcess p_process) {
	m_process = p_process;
    }

    public byte[] getBytes() {
	if (m_process != null && m_request != null && m_response != null) {
	    // Creazione della pagina riassuntiva
	    StringWriter sw = new StringWriter();

	    // Chiamo il modulo di print dato il nome
	    String printName = (String) super.getObject();
	    m_process.getPrint(MimeType.HTML).print(printName, sw, m_request,
		    m_response);

	    try {
		return sw.toString().getBytes("UTF-8");
	    } catch (Exception e) {
		logger.warn(
			"Unable to return byte with specified encoding UTF-8.",
			e);
		return sw.toString().getBytes();
	    }

	}

	return new byte[0];
    }

    public byte[] getBytes(MimeType mimeType) {

	if (m_process != null && m_request != null && m_response != null) {
	    // Creazione della pagina riassuntiva
	    StringWriter sw = new StringWriter();

	    // Chiamo il modulo di print dato il nome
	    String printName = (String) super.getObject();
	    m_process.getPrint(MimeType.COMPLETE_HTML).print(printName, sw,
		    m_request, m_response);

	    try {
		return sw.toString().getBytes("UTF-8");
	    } catch (Exception e) {
		logger.warn(
			"Unable to return byte with specified encoding UTF-8.",
			e);
		return sw.toString().getBytes();
	    }

	}

	return new byte[0];

    }

}
