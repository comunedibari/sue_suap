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
 * Creato il 26-giu-2006 da Cedaf s.r.l.
 *
 */
package it.people.taglib;

import java.io.IOException;

import it.people.City;
import it.people.PeopleConstants;
import it.people.core.CommuneManager;
import it.people.layout.multicommune.MultiCommuneCss;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 */

public class PeopleFrameworkCss extends TagSupport {
    protected static Logger logger = Logger.getLogger(PeopleFrameworkCss.class);

    public int doStartTag() throws JspException {
	HttpServletRequest request = (HttpServletRequest) this.pageContext
		.getRequest();
	HttpSession session = (HttpSession) this.pageContext.getSession();
	MultiCommuneCss multiCommuneCss = new MultiCommuneCss(request);

	printCssLink(multiCommuneCss, "/css/people.css");

	City city = (City) session
		.getAttribute(PeopleConstants.SESSION_NAME_COMMUNE);
	if (city != null
		&& !city.getKey().equals(CommuneManager.DEFAULT_COMMUNE_KEY)) {
	    printCssLink(multiCommuneCss, "/css/people_" + city.getKey()
		    + ".css");
	}

	return super.doStartTag();
    }

    protected void printCssLink(MultiCommuneCss multiCommuneCss, String cssPath)
	    throws JspException {
	if (multiCommuneCss.cssExist(cssPath)) {
	    try {
		HttpServletRequest request = (HttpServletRequest) this.pageContext
			.getRequest();
		pageContext.getOut().println(
			"<link rel=\"stylesheet\" type=\"text/css\" href=\""
				+ request.getContextPath() + cssPath + "\" />");
	    } catch (IOException ioex) {
		String errorMessage = "Impossibile includere la risorsa: '"
			+ cssPath + "'";
		logger.error(errorMessage, ioex);
		throw new JspException(errorMessage, ioex);
	    }
	}
    }
}
