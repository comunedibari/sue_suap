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
 * Creato il 27-lug-2007 da Cedaf s.r.l.
 *
 */
package it.people.taglib;

import javax.servlet.jsp.JspException;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 */
public class TextareaTag extends org.apache.struts.taglib.html.TextareaTag {
    protected String renderTextareaElement() throws JspException {
	StringBuffer results = new StringBuffer("<textarea");

	String nameAttribute = prepareName();

	prepareAttribute(results, "name", nameAttribute);
	prepareAttribute(results, "accesskey", getAccesskey());
	prepareAttribute(results, "tabindex", getTabindex());
	prepareAttribute(results, "cols", getCols());
	prepareAttribute(results, "rows", getRows());

	if (getId() != null)
	    prepareAttribute(results, "id", getId());
	else
	    prepareAttribute(results, "id", nameAttribute);

	results.append(prepareEventHandlers());
	results.append(prepareStyles());
	prepareOtherAttributes(results);
	results.append(">");

	results.append(this.renderData());

	results.append("</textarea>");
	return results.toString();
    }
}
