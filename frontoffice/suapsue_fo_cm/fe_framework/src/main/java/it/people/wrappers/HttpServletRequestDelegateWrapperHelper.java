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
/*
 * Created on 12-feb-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.wrappers;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Category;

/**
 * @author max
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class HttpServletRequestDelegateWrapperHelper {
    /**
     * Logger for this class
     */
    // private static final Logger logger = Logger
    // .getLogger(ProfiloLocaleStep.class);

    private Category logger = Category
	    .getInstance(HttpServletRequestDelegateWrapperHelper.class
		    .getName());

    // HashMap errors= new HashMap();
    HttpServletRequest m_UnwrappedRequest = null;

    /**
     * @param arg0
     */
    public HttpServletRequestDelegateWrapperHelper(
	    IRequestWrapper p_httpServletRequest) {
	// super(p_httpServletRequest.getUnwrappedRequest());
	m_UnwrappedRequest = p_httpServletRequest.getUnwrappedRequest();
    }

    public HttpServletRequestDelegateWrapperHelper(
	    HttpServletRequest p_httpServletRequest) {
	m_UnwrappedRequest = p_httpServletRequest;
    }

    public void setSessionAttribute(String p_attributeName,
	    Object p_attributeValue) {
	HttpSession session = m_UnwrappedRequest.getSession();
	if (session != null)
	    session.setAttribute(p_attributeName, p_attributeValue);
	return;
    }

    public void addSiracError(String errorKey, String p_errorValue) {
	if (errorKey == null) {
	    logger.error("HttpServletRequestDelegateWrapperHelper:: addsiracError:: Can't add new error. null errorKey.");
	    return;
	}
	if (p_errorValue == null) {
	    logger.error("HttpServletRequestDelegateWrapperHelper:: addsiracError::Can't add new error. null errorValue.");
	    return;
	}
	// errors.put(errorKey, p_errorValue);
	getSiracErrors().put(errorKey, p_errorValue);
    }

    public void removeSiracError(String errorKey) {
	if (errorKey == null)
	    return;
	// logger.error("HttpServletRequestDelegateWrapperHelper:: removeSiracError:: Can't remove error. null errorKey.");
	// errors.remove("errorKey");
	getSiracErrors().remove(errorKey);
    }

    public HashMap getSiracErrors() {
	HashMap errors = ((HashMap) m_UnwrappedRequest
		.getAttribute("SiracErrors"));
	if (errors == null) {
	    m_UnwrappedRequest.setAttribute("SiracErrors", new HashMap());
	}
	return ((HashMap) m_UnwrappedRequest.getAttribute("SiracErrors"));
    }

    public boolean isSiracErrorEmpty() {
	return (getSiracErrors().size() == 0);
    }

    public void cleanSiracErrors() {
	getSiracErrors().clear();
    }

}
