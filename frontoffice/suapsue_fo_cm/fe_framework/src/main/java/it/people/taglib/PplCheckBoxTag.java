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

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.CheckboxTag;

/**
 * @author Zoppello
 */
public class PplCheckBoxTag extends CheckboxTag {

    /**
     * Generate the required input tag.
     * <p>
     * Support for indexed property since Struts 1.1
     * 
     * @exception JspException
     *                if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {

	// Create an appropriate "input" element based on our parameters
	StringBuffer results = new StringBuffer("<input type=\"checkbox\"");
	results.append(" name=\"");
	// * @since Struts 1.1
	if (indexed)
	    prepareIndex(results, name);
	results.append(this.property);
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
	if (tabindex != null) {
	    results.append(" tabindex=\"");
	    results.append(tabindex);
	    results.append("\"");
	}
	results.append(" value=\"");
	if (value == null)
	    results.append("on");
	else
	    results.append(value);
	results.append("\"");

	// Object result = RequestUtils.lookup(pageContext, name, property,
	// null); deprecato
	Object result = TagUtils.getInstance().lookup(pageContext, name,
		property, null);

	if (result == null)
	    result = "";
	if (!(result instanceof String))
	    result = result.toString();
	String checked = (String) result;
	if (checked.equalsIgnoreCase(value) || checked.equalsIgnoreCase("true")
		|| checked.equalsIgnoreCase("yes")
		|| checked.equalsIgnoreCase("on"))
	    results.append(" checked=\"checked\"");
	results.append(prepareEventHandlers());
	results.append(prepareStyles());
	results.append(getElementClose());

	// Print this field to our output writer
	TagUtils.getInstance().write(pageContext, results.toString());
	// ResponseUtils.write(pageContext, results.toString()); deprecato

	// Continue processing this page
	this.text = null;
	return (EVAL_BODY_TAG);

    }

}
