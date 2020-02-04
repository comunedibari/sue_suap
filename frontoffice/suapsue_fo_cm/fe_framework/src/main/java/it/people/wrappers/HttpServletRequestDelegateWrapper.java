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
 * Created on 23-apr-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.people.wrappers;

import it.people.core.PeopleContext;
import it.people.core.PplUser;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

/**
 * @author Zoppello
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HttpServletRequestDelegateWrapper extends
	HttpServletRequestWrapper implements IRequestWrapper {

    protected HttpServletRequest m_httpServletRequest = null;

    public HttpServletRequestDelegateWrapper(
	    HttpServletRequest p_httpServletRequest) {
	super(p_httpServletRequest);
	m_httpServletRequest = p_httpServletRequest;
    }

    public Object getSessionAttribute(String p_attributeName) {
	HttpSession session = m_httpServletRequest.getSession();
	if (session != null)
	    return session.getAttribute(p_attributeName);
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.wrappers.IRequestWrapper#getAttribute(java.lang.String)
     */
    public Object getAttribute(String p_attributeName) {

	return m_httpServletRequest.getAttribute(p_attributeName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.wrappers.IRequestWrapper#getAttributeNames()
     */
    public Enumeration getAttributeNames() {

	return m_httpServletRequest.getAttributeNames();
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.wrappers.IRequestWrapper#getParameter(java.lang.String)
     */
    public String getParameter(String p_parameterName) {

	return m_httpServletRequest.getParameter(p_parameterName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.wrappers.IRequestWrapper#getParameterMap()
     */
    public Map getParameterMap() {

	return m_httpServletRequest.getParameterMap();
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.wrappers.IRequestWrapper#getParameterNames()
     */
    public Enumeration getParameterNames() {

	return m_httpServletRequest.getParameterNames();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.people.wrappers.IRequestWrapper#getParameterValues(java.lang.String)
     */
    public String[] getParameterValues(String p_parameterName) {

	return m_httpServletRequest.getParameterValues(p_parameterName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.wrappers.IRequestWrapper#removeAttribute(java.lang.String)
     */
    public void removeAttribute(String p_attributeName) {

	m_httpServletRequest.removeAttribute(p_attributeName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.wrappers.IRequestWrapper#setAttribute(java.lang.String,
     * java.lang.Object)
     */
    public void setAttribute(String p_attributeName, Object p_attributeValue) {
	m_httpServletRequest.setAttribute(p_attributeName, p_attributeValue);

    }

    public PplUser getPplUser() {
	PeopleContext pplContext = PeopleContext.create(m_httpServletRequest);
	return pplContext.getUser();
    }

    // -----------------------------------------------------------------
    public HttpServletRequest getUnwrappedRequest() {
	return m_httpServletRequest;
    }

}
