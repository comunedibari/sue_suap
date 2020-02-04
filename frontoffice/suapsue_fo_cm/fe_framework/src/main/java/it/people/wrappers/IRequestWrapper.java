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

import it.people.core.PplUser;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Zoppello
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public interface IRequestWrapper {

    /**
     * 
     * @param p_attributeName
     * @return
     */
    public Object getSessionAttribute(String p_attributeName);

    /**
     * @param p_attributeName
     * @return
     */
    public Object getAttribute(String p_attributeName);

    /**
     * @return
     */
    public Enumeration getAttributeNames();

    /**
     * @param p_parameterName
     * @return
     */
    public String getParameter(String p_parameterName);

    /**
     * @return
     */
    public Map getParameterMap();

    /**
     * @return
     */
    public Enumeration getParameterNames();

    /**
     * @param p_parameterName
     * @return
     */
    public String[] getParameterValues(String p_parameterName);

    /**
     * @param p_attributeName
     */
    public void removeAttribute(String p_attributeName);

    /**
     * @param p_attributeName
     * @param p_attributeValue
     */
    public void setAttribute(String p_attributeName, Object p_attributeValue);

    /**
     * 
     * @return
     */
    public PplUser getPplUser();

    // -----------------------------------------------------------------
    public HttpServletRequest getUnwrappedRequest();
}
