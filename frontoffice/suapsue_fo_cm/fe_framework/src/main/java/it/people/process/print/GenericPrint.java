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
package it.people.process.print;

import it.people.City;
import it.people.process.AbstractPplProcess;
import it.people.util.MimeType;
import it.people.util.servlet.PplHttpServletRequestWrapper;
import it.people.util.servlet.PplHttpServletResponseWrapper;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Category;
import org.apache.struts.taglib.html.Constants;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Oct 7, 2003 Time: 10:51:46 PM To
 * change this template use Options | File Templates.
 */
public class GenericPrint extends ConcretePrint {

    private Category cat = Category.getInstance(GenericPrint.class.getName());

    // private String m_jspPath = null;

    public GenericPrint() {
	super();
    }

    public GenericPrint(AbstractPplProcess parent) {
	super(parent);
    }

    public void initialize(City comune, MimeType type) {
	addPrinter("printModuleDatiSubmitter", getPrintPage());
    }

    protected void doPrint(Writer writer, HttpServletRequest request,
	    HttpServletResponse response) {
	if (m_actualPrinterPath != null) {
	    try {
		// Per il corretto funzionamento della localizzazione dei
		// messaggi
		// della tag library sarebbe necessario che i tag fossero
		// inclusi nel
		// html:form.
		// In questo contesto non � possibile quindi viene simulato il
		// comportamento dell'html:form includendo il bean principale.
		AbstractPplProcess pplProcess = this.getParent();
		request.setAttribute(Constants.BEAN_KEY, pplProcess);

		// esecuzione della signPrintPage del servizio
		PplHttpServletResponseWrapper innerResponse = new PplHttpServletResponseWrapper(
			response);
		request.getRequestDispatcher(m_actualPrinterPath).include(
			new PplHttpServletRequestWrapper(request),
			innerResponse);
		writer.write(innerResponse.getResponseString());
	    } catch (Exception ex) {
		cat.error(ex);
	    }
	}
    }
}
