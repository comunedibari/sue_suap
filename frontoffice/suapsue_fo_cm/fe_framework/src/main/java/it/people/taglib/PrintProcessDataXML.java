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

import it.people.process.AbstractPplProcess;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class PrintProcessDataXML extends TagSupport {

    private String m_header;
    private String m_footer;
    private String m_name;

    public void setHeader(String p_header) {
	m_header = p_header;
    }

    public void setFooter(String p_footer) {
	m_footer = p_footer;
    }

    public void setName(String p_name) {
	m_name = p_name;
    }

    public PrintProcessDataXML() {
	super();
	m_header = " --- BEGIN PROCESS DATA XML ---";
	m_footer = " --- END PROCESS DATA XML ---";
    }

    public int doEndTag() throws JspException {

	try {
	    AbstractPplProcess process = (AbstractPplProcess) pageContext
		    .findAttribute(m_name);
	    if (process != null) {
		String xmlData = process.getMarshalledData();
		String print = m_header + "\n" + xmlData + "\n" + m_footer;

		pageContext.getOut().println(print);

	    }

	} catch (Exception e) {

	}
	return EVAL_PAGE;
    }

    public void release() {
	super.release();
	m_header = null;
	m_footer = null;
	m_name = null;

    }

}
