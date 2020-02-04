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
/**
 * 
 */
package it.people.action;

import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova Oct 5,
 *         2013 6:08:55 PM
 */
public class PeopleActionServlet extends ActionServlet {

    private static Logger logger = LogManager
	    .getLogger(PeopleActionServlet.class);

    /**
	 * 
	 */
    private static final long serialVersionUID = 8025613602911023008L;

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.struts.action.ActionServlet#init()
     */
    public void init() throws ServletException {

	super.init();

	boolean validEncoding = true;
	String encoding = String.valueOf(this.getServletContext()
		.getInitParameter("encoding"));
	try {
	    StringBuilder encodingTest = new StringBuilder("test".getBytes(
		    encoding).toString());
	} catch (UnsupportedEncodingException uex) {
	    logger.warn("Wrong encoding specified (" + encoding
		    + "). No specific encoding will be set.", uex);
	    validEncoding = false;
	}

	if (validEncoding) {
	    ModuleConfig moduleConfig = initModuleConfig("", config);
	    initModuleMessageResources(moduleConfig);
	    initModuleDataSources(moduleConfig);
	    initModulePlugIns(moduleConfig);
	    moduleConfig.getControllerConfig().updateContentType(
		    "text/html; charset=" + encoding);
	    moduleConfig.freeze();
	}

    }

}
