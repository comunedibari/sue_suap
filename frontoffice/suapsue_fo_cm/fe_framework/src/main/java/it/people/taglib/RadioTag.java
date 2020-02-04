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
 * Creato il 31-lug-2007 da Cedaf s.r.l.
 *
 */
package it.people.taglib;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 */
public class RadioTag extends org.apache.struts.taglib.html.RadioTag {
    protected String id = null;

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

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    protected String renderRadioElement(String serverValue, String checkedValue)
	    throws JspException {

	StringBuffer results = new StringBuffer("<input type=\"radio\"");

	prepareAttribute(results, "name", prepareName());
	prepareAttribute(results, "accesskey", getAccesskey());
	prepareAttribute(results, "tabindex", getTabindex());
	prepareAttribute(results, "value",
		TagUtils.getInstance().filter(serverValue));
	if (serverValue.equals(checkedValue)) {
	    results.append(" checked=\"checked\"");
	}

	if (getId() != null)
	    prepareAttribute(results, "id", getId());

	results.append(prepareEventHandlers());
	results.append(prepareStyles());
	prepareOtherAttributes(results);
	if (this.getXhtml() != null) {
	    if (this.isXhtmlRequired()) {
		results.append(" />");
	    } else {
		results.append(">");
	    }
	} else {
	    results.append(getElementClose());
	}
	return results.toString();
    }

}
