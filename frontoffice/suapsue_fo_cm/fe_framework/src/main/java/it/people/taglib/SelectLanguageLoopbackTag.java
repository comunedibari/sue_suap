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
 * Created on 17-ott-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.taglib;

import javax.servlet.jsp.JspException;

import it.people.layout.ButtonKey;

import org.apache.struts.taglib.html.SubmitTag;
import org.apache.struts.taglib.TagUtils;

/**
 * @author FabMi
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SelectLanguageLoopbackTag extends SubmitTag {
    protected String lang;
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

    /**
     * @return the lang
     */
    public final String getLang() {
	return this.lang;
    }

    /**
     * @param lang
     *            the lang to set
     */
    public final void setLang(String lang) {
	this.lang = lang;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.struts.taglib.html.SubmitTag#doEndTag()
     */
    public int doEndTag() throws JspException {

	// Generate an HTML element
	StringBuffer results = new StringBuffer();
	results.append(getElementOpen());
	prepareAttribute(results, "name", prepareName());
	prepareButtonAttributes(results);
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

	TagUtils.getInstance().write(pageContext, results.toString());

	return (EVAL_PAGE);

    }

}