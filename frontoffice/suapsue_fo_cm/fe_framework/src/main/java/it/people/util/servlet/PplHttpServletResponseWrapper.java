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
package it.people.util.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Sep 29, 2003 Time: 7:54:44 AM To
 * change this template use Options | File Templates.
 */
public class PplHttpServletResponseWrapper extends HttpServletResponseWrapper {
    private StringWriter m_bufferSW = new StringWriter();
    private PplServletOutputStream m_bufferOS = new PplServletOutputStream();

    public PplHttpServletResponseWrapper(HttpServletResponse response) {
	super(response);
    }

    public ServletOutputStream getOutputStream() throws IOException {
	// return new PplServletOutputStream();
	return m_bufferOS;
    }

    public PrintWriter getWriter() throws IOException {
	return new PrintWriter(m_bufferSW);
    }

    public String getResponseString() {
	if (m_bufferSW.getBuffer().length() > 0)
	    return m_bufferSW.toString();
	else
	    return m_bufferOS.getResponseString();
    }
}
