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
 * Created on 5-ago-2004
 *
 */
package it.people.taglib;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.SelectTag;

/**
 * @author Zoppello
 */
public class PplSelectTag extends SelectTag {

    /**
     * (non-Javadoc)
     * 
     * @see org.apache.struts.taglib.html.SelectTag#renderSelectStartElement()
     */
    protected String renderSelectStartElement() throws JspException {
	StringBuffer results = new StringBuffer("<select");

	results.append(" name=\"");
	// * @since Struts 1.1
	if (this.indexed) {
	    prepareIndex(results, name);
	}
	results.append(property);
	results.append("\"");

	// AGGIUNTA id
	if (id != null) {
	    results.append(" id=\"");
	    results.append(id);
	    results.append("\"");
	}

	if (accesskey != null) {
	    results.append(" accesskey=\"");
	    results.append(accesskey);
	    results.append("\"");
	}
	if (multiple != null) {
	    results.append(" multiple=\"multiple\"");
	}
	if (size != null) {
	    results.append(" size=\"");
	    results.append(size);
	    results.append("\"");
	}
	if (tabindex != null) {
	    results.append(" tabindex=\"");
	    results.append(tabindex);
	    results.append("\"");
	}
	results.append(prepareEventHandlers());
	results.append(prepareStyles());
	results.append(">");

	return results.toString();
    }
}
