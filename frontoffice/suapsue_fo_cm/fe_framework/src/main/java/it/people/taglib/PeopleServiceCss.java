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

import it.people.layout.multicommune.MultiCommuneCss;
import it.people.process.AbstractPplProcess;
import it.people.process.view.ConcreteView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/**
 * @author Michele Fabbri - Cedaf s.r.l. Il custom tag permette di includere
 *         tutti i css relativi ad un servizio, � necessario indicare nella
 *         propriet�: processName il nome dell'abstractPplProcess.
 * 
 *         I css inclusi sono nell'ordine di inclusione i seguenti: - css di
 *         area generico (es. 'servizi/esempi/css/esempi.css') - css di area per
 *         il comune (es. 'servizi/esempi/css/esempi_040007.css') - css di
 *         sottoarea generico (es. 'servizi/esempi/tutorial/css/tutorial.css') -
 *         css di sottoarea per il comune (es.
 *         'servizi/esempi/tutorial/css/tutorial_040007.css') - css di servizio
 *         generico (es.
 *         'servizi/esempi/tutorial/serviziotutorial1/view/default/css/tutorial.css'
 *         ) - css di servizio per il comune (es.
 *         'servizi/esempi/tutorial/serviziotutorial1/view/040007/css/tutorial.css'
 *         )
 */
public class PeopleServiceCss extends TagSupport {
    protected String processName;
    protected static Logger logger = Logger.getLogger(PeopleServiceCss.class);

    public int doStartTag() throws JspException {
	HttpServletRequest request = (HttpServletRequest) this.pageContext
		.getRequest();
	AbstractPplProcess process = (AbstractPplProcess) pageContext
		.findAttribute(processName);
	ConcreteView view = process.getView();
	MultiCommuneCss multiCommuneCss = new MultiCommuneCss(request);

	printCssLink(multiCommuneCss, view.getCssPathCategory(request));
	printCssLink(multiCommuneCss, view.getCssPathCategoryCommune(request));
	printCssLink(multiCommuneCss, view.getCssPathSubcategory(request));
	printCssLink(multiCommuneCss,
		view.getCssPathSubcategoryCommune(request));
	printCssLink(multiCommuneCss, view.getCssPathService(request));
	printCssLink(multiCommuneCss, view.getCssPathServiceCommune(request));

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

    public String getProcessName() {
	return processName;
    }

    public void setProcessName(String name) {
	this.processName = name;
    }
}
