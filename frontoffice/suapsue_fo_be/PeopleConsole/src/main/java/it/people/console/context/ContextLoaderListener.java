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
package it.people.console.context;

import javax.servlet.ServletContextEvent;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import it.people.console.config.ConsoleConfiguration;
import it.people.console.config.exceptions.ConsoleConfigurationException;
import it.people.console.persistence.dbupgrade.DbUpgradeManager;
import it.people.console.utils.Constants;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 11/giu/2011 13.58.33
 *
 */
public class ContextLoaderListener extends org.springframework.web.context.ContextLoaderListener {

	private static final Logger logger = LoggerFactory.getLogger(ContextLoaderListener.class);
	
	public ContextLoaderListener() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {

		super.contextDestroyed(servletContextEvent);
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

		super.contextInitialized(servletContextEvent);
		DbUpgradeManager.upgrade();
		try {
			servletContextEvent.getServletContext().setAttribute(Constants.System.SERVLET_CONTEXT_CONSOLE_CONFIGURATION_PROPERTIES, 
					ConsoleConfiguration.instance());
		} catch (ConsoleConfigurationException e) {
			e.printStackTrace();
			
//			this.closeWebApplicationContext(servletContextEvent.getServletContext());
//			XmlWebApplicationContext xmlWebApplicationContext = (XmlWebApplicationContext)WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
//			xmlWebApplicationContext.stop();
		}

	}

}
