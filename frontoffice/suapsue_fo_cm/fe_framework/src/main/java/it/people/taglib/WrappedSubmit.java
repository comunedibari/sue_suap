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
 * Creato il 22-giu-2006 da Cedaf s.r.l.
 *
 */
package it.people.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.taglib.TagUtils;

/**
 * Effettua il rendering di un tag input di tipo submit con il testo suddiviso
 * in pi� righe
 * 
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 */
public class WrappedSubmit extends TagSupport {
    protected static final String NEW_LINE_HTML_BUTTON = "&#10;";

    private int lineLength;
    private String name;
    private String value;
    private String cssClass;
    private boolean disabled;

    public int doStartTag() throws JspException {
	if (this.name == null)
	    throw new JspException(
		    "Il parametro 'name' deve essere valorizzato");

	if (this.value == null)
	    throw new JspException(
		    "Il parametro 'value' deve essere valorizzato");

	// Rendering del tag html
	String output = "<input type=\"submit\"" + " name=\"" + this.name
		+ "\"" + " value=\"" + wrapString(this.value, this.lineLength)
		+ "\"";

	if (this.cssClass != null)
	    output += " class=\"" + this.cssClass + "\"";

	if (this.cssClass != null)
	    output += " class=\"" + this.cssClass + "\"";

	output += " />";

	TagUtils.getInstance().write(pageContext, output);
	return super.doStartTag();
    }

    /**
     * Spezza su pi� righe la stringa passata, introducendo il carattere #10;
     * 
     * @param value
     * @param lenght
     * @return
     */
    protected String wrapString(String value, int lenght) {
	// la stringa � pi� corta della lunghezza massima
	if (value.length() <= lenght || lenght == 0)
	    return value;

	String[] elem = value.split(" ");

	// non ci sono spazi e quindi non � possibile spezzare
	// la stringa su pi� righe
	if (elem.length < 2)
	    return value;

	String returnValue = "";
	int i = 0;
	int currLength = 0;
	while (i < elem.length - 1) {
	    currLength += elem[i].length();
	    returnValue += elem[i++];
	    // determina se la lunghezza della riga
	    // con aggiunta la nuova parola e lo spazio
	    // supera quella consentita
	    if ((currLength + elem[i].length() + 1) > lenght) {
		currLength = 0;
		returnValue += NEW_LINE_HTML_BUTTON;
	    } else {
		currLength++;
		returnValue += " ";
	    }
	}
	returnValue += elem[i];

	return returnValue;
    }

    public String getCssClass() {
	return cssClass;
    }

    public void setCssClass(String cssClass) {
	this.cssClass = cssClass;
    }

    public boolean getDisabled() {
	return disabled;
    }

    public void setDisabled(boolean disabled) {
	this.disabled = disabled;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }

    public int getLineLength() {
	return lineLength;
    }

    public void setLineLength(int lineLength) {
	this.lineLength = lineLength;
    }
}
