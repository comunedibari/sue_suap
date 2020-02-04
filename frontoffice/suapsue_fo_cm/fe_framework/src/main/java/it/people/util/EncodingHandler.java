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
package it.people.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 
 * @author mirkoc filtro per settare su tutte le request la codifica UTF-8
 *         necessario per le POST
 */

public class EncodingHandler implements Filter {

    private static Logger logger = LogManager.getLogger(EncodingHandler.class);

    private String encoding = null;

    public void init(FilterConfig filterConfig) throws ServletException {

	this.encoding = String.valueOf(filterConfig.getServletContext()
		.getInitParameter("encoding"));

    }

    public void doFilter(ServletRequest request, ServletResponse response,
	    FilterChain chain) throws IOException, ServletException {

	String encoding = selectEncoding(request);

	if (encoding != null) {
	    try {
		request.setCharacterEncoding(encoding);
	    } catch (UnsupportedEncodingException uex) {
		logger.warn("Wrong encoding specified (" + this.encoding
			+ "). No specific encoding will be set.", uex);
	    }
	    // response.setContentType("text/html; charset=" + encoding);
	    // response.setCharacterEncoding(encoding);
	}

	chain.doFilter(request, response);
    }

    protected String selectEncoding(ServletRequest request) {
	return (this.encoding);
    }

    public void destroy() {
	this.encoding = null;
    }

}
