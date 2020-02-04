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
package it.people.util.resourcesupdater;

import java.io.File;
import java.net.MalformedURLException;

import java.net.URL;

import it.people.util.messagebundle.updater.DbMessageBundlesUpdater;
import it.people.util.table.updater.DbTableValuesUpdater;

import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * @author Andrea Piemontese - Engineering Ingegneria Informatica 07/06/2012
 */
public class ResourcesUpdaterListener implements ServletContextListener {

    private Logger logger = Logger.getLogger(ResourcesUpdaterListener.class);

    private static final String SYNCHRONIZATION_SERVICE = "synchronization.service";
    private static final String SYNCHRONIZATION_SERVICE_ADDRESS = "synchronization.service.address";
    private static final String SYNCHRONIZATION_SERVICE_SCHEDULER_JOB = "synchronization.service.scheduler.job";
    private static final String SYNCHRONIZATION_SERVICE_SCHEDULER_CRON_EXPRESSION = "synchronization.service.scheduler.cron.expression";
    private static final String DOCUMENT_BASE_JNDI_NAME = "java:comp/env/documentbase";

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
     * ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
	// TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
     * .ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

	// Get People Context
	String peopleContextBasePath = "";
	try {
	    logger.debug("Getting people context...");
	    InitialContext ic = new InitialContext();
	    String docbasePath = (String) ic.lookup(DOCUMENT_BASE_JNDI_NAME);
	    peopleContextBasePath = getBasePathFromDocbase(docbasePath);
	} catch (Exception e) {
	    logger.error("Unable to lookup JNDI for docbase path.", e);
	}

	// Check message bundle update and synch need
	logger.debug("Getting message bundle updater instance...");
	DbMessageBundlesUpdater dbMessageBundlesUpdater = DbMessageBundlesUpdater
		.getInstance();
	logger.debug("Updating bundles...");
	boolean bundleSynchNeeded = dbMessageBundlesUpdater
		.update(peopleContextBasePath);
	logger.debug("Bundles updated.");

	// Check table value update and synch need
	logger.debug("Getting message bundle updater instance...");
	DbTableValuesUpdater dbTableValuesUpdater = DbTableValuesUpdater
		.getInstance();
	logger.debug("Updating tablevalues...");
	boolean tablevalueSynchNeeded = dbTableValuesUpdater
		.update(peopleContextBasePath);
	logger.debug("Tablevalues updated.");

	// Test synchronization need
	ISynchronizationService synchronizationService = null;

	// GET Synchronization Service
	String synchronizationServiceClassName = (String) servletContextEvent
		.getServletContext().getInitParameter(SYNCHRONIZATION_SERVICE);
	String synchronizationServiceAddress = (String) servletContextEvent
		.getServletContext().getInitParameter(
			SYNCHRONIZATION_SERVICE_ADDRESS);
	String synchronizationServiceSchedulerJobClassName = (String) servletContextEvent
		.getServletContext().getInitParameter(
			SYNCHRONIZATION_SERVICE_SCHEDULER_JOB);
	String synchronizationServiceSchedulerCronExpression = (String) servletContextEvent
		.getServletContext().getInitParameter(
			SYNCHRONIZATION_SERVICE_SCHEDULER_CRON_EXPRESSION);

	try {
	    Class<ISynchronizationService> peopleConsoleServiceClass = (Class<ISynchronizationService>) Class
		    .forName(synchronizationServiceClassName);
	    synchronizationService = (ISynchronizationService) peopleConsoleServiceClass
		    .newInstance();
	} catch (ClassNotFoundException e) {
	    logger.error("", e);
	} catch (InstantiationException e) {
	    logger.error("", e);
	} catch (IllegalAccessException e) {
	    logger.error("", e);
	}

	if (bundleSynchNeeded) {
	    synchronizationService.update(
		    dbMessageBundlesUpdater.getSynchronizationInstructions(),
		    synchronizationServiceAddress);
	}

	if (tablevalueSynchNeeded) {
	    synchronizationService.update(
		    dbTableValuesUpdater.getSynchronizationInstructions(),
		    synchronizationServiceAddress);
	}

	if (logger.isDebugEnabled()) {
	    logger.debug("Initializing scheduler for synch service...");
	}
	if (synchronizationService != null) {
	    synchronizationService.initializeScheduler(
		    servletContextEvent.getServletContext(),
		    peopleContextBasePath,
		    synchronizationServiceSchedulerJobClassName,
		    synchronizationServiceSchedulerCronExpression,
		    synchronizationServiceClassName,
		    synchronizationServiceAddress);
	} else {
	    logger.error("synchronizationService is null, scheduler initialization failed.");
	}

    }

    /**
     * Get only base path for a WebApp from docbase path.
     * 
     * @param docbasePath
     *            the file path of the documentbase.
     * @return
     */
    private String getBasePathFromDocbase(String docbasePath) {

	URL docbaseURL = null;

	try {
	    docbaseURL = new URL(docbasePath);
	} catch (MalformedURLException e) {
	    logger.error("Unable to get docbase URL");
	}
	File file = new File(docbaseURL.getFile());
	String result = file.getAbsolutePath();

	// Remove trailing classes path if present
	String trailingPathComplete = "WEB-INF"
		+ System.getProperty("file.separator") + "classes"
		+ System.getProperty("file.separator");
	String trailingPath = "WEB-INF" + System.getProperty("file.separator")
		+ "classes";

	if (result.endsWith(trailingPathComplete)) {
	    result = result.replace(trailingPathComplete, "");
	} else if (result.endsWith(trailingPath)) {
	    result = result.replace(trailingPath, "");
	}

	return result;
    }

}
