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
package it.people.taglib;

import it.people.Step;
import it.people.core.PplPermission;
import it.people.parser.FieldValidator;
import it.people.parser.StringValidator;

import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.TextTag;
import org.apache.struts.util.ResponseUtils;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Sep 30, 2003 Time: 9:17:50 AM To
 * change this template use Options | File Templates.
 */
public class TextFieldTag extends TextTag {

    protected String xhtml;

    protected boolean isXhtmlRequired() {
	return Boolean.parseBoolean(this.getXhtml());
    }

    public String getXhtml() {
	return xhtml;
    }

    public void setXhtml(String xhtml) {
	this.xhtml = xhtml;
    }

    public int doStartTag() throws JspException {
	addFieldToStep();
	FieldValidator validator = null;
	if (validator != null && (maxlength == null || maxlength.length() == 0))
	    if (validator instanceof StringValidator)
		maxlength = Integer.toString(((StringValidator) validator)
			.getMaxLength());

	// Verificare le permission della pagina (READ | WRITE)
	PplPermission permission = (PplPermission) pageContext.getRequest()
		.getAttribute("PagePermission");

	// se non � gi� disabilitato verifico i permessi
	if (!super.getDisabled()) {
	    if (permission != null
		    && !permission.and(PplPermission.WRITE).equals(
			    PplPermission.WRITE))
		super.setDisabled(true);
	    else
		super.setDisabled(false);
	}

	// Create an appropriate "input" element based on our parameters
	StringBuffer results = new StringBuffer("<input type=\"");
	results.append(type);
	results.append("\" name=\"");
	// * @since Struts 1.1
	if (indexed)
	    prepareIndex(results, name);
	results.append(property);
	results.append("\"");

	// AGGIUNTA id
	results.append(" id=\"");
	if (id != null) {
	    results.append(id);
	} else {
	    // Se non specificato diversamente dell'id � fatto il rendering
	    // con il nome della propriet� in modo da permettere il
	    // collegamento
	    // con il tag FieldLabel per il rispetto dell'ACCESSIBILIT�
	    results.append(property);
	}
	results.append("\"");

	if (accesskey != null) {
	    results.append(" accesskey=\"");
	    results.append(accesskey);
	    results.append("\"");
	}
	if (accept != null) {
	    results.append(" accept=\"");
	    results.append(accept);
	    results.append("\"");
	}
	if (maxlength != null) {
	    results.append(" maxlength=\"");
	    results.append(maxlength);
	    results.append("\"");
	}
	if (cols != null) {
	    results.append(" size=\"");
	    results.append(cols);
	    results.append("\"");
	}
	if (tabindex != null) {
	    results.append(" tabindex=\"");
	    results.append(tabindex);
	    results.append("\"");
	}
	results.append(" value=\"");
	if (value != null) {
	    results.append(ResponseUtils.filter(value));
	} else if (redisplay || !"password".equals(type)) {
	    // Object value = RequestUtils.lookup(pageContext, name, property,
	    // null); deprecato
	    Object value = TagUtils.getInstance().lookup(pageContext, name,
		    property, null);
	    if (value == null)
		value = "";
	    results.append(ResponseUtils.filter(value.toString()));
	}
	results.append("\"");
	results.append(prepareEventHandlers());
	results.append(prepareStyles());
	if (this.getXhtml() != null) {
	    if (this.isXhtmlRequired()) {
		results.append(" />");
	    } else {
		results.append(">");
	    }
	} else {
	    results.append(getElementClose());
	}

	// Print this field to our output writer
	// ResponseUtils.write(pageContext, results.toString()); deprecato
	TagUtils.getInstance().write(pageContext, results.toString());

	// Continue processing this page
	return (EVAL_BODY_TAG);
    }

    private void addFieldToStep() {
	try {
	    int firstDot = property.indexOf('.');
	    if (firstDot != -1) {
		String fieldProperty = property.substring(firstDot + 1);
		String dataProperty = property.substring(0, firstDot);
		// Object dataBean = RequestUtils.lookup(pageContext, name,
		// dataProperty, null); deprecato
		Object dataBean = TagUtils.getInstance().lookup(pageContext,
			name, dataProperty, null);

		Step currentStep = (Step) pageContext.getRequest()
			.getAttribute("CurrentStep");
		if (currentStep != null)
		    currentStep.addField(fieldProperty, PropertyUtils
			    .getPropertyDescriptor(dataBean, fieldProperty));
	    }
	} catch (Exception ex) {

	}

    }

}
