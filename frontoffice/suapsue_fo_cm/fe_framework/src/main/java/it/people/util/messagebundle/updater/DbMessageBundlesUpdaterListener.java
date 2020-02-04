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
package it.people.util.messagebundle.updater;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * @author Riccardo Foraf� - Engineering Ingegneria Informatica - Genova
 *         22/set/2011 22.03.08
 */
public class DbMessageBundlesUpdaterListener implements ServletContextListener {

    private Logger logger = Logger
	    .getLogger(DbMessageBundlesUpdaterListener.class);

    private static final String SYNCHRONIZATION_SERVICE = "synchronization.service";
    private static final String SYNCHRONIZATION_SERVICE_ADDRESS = "synchronization.service.address";
    private static final String SYNCHRONIZATION_SERVICE_SCHEDULER_JOB = "synchronization.service.scheduler.job";
    private static final String SYNCHRONIZATION_SERVICE_SCHEDULER_CRON_EXPRESSION = "synchronization.service.scheduler.cron.expression";

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

	String contextPath = servletContextEvent.getServletContext()
		.getRealPath("/");
	logger.debug("contextPath = " + contextPath);

	logger.debug("Getting updater instance...");
	DbMessageBundlesUpdater dbMessageBundlesUpdater = DbMessageBundlesUpdater
		.getInstance();
	logger.debug("Updating bundles...");
	boolean synchronizationNeeded = dbMessageBundlesUpdater
		.update(contextPath);
	logger.debug("Bundles updated.");

	ISynchronizationService synchronizationService = null;

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
	    Class peopleConsoleServiceClass = Class
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

	if (synchronizationNeeded) {
	    synchronizationService.update(
		    dbMessageBundlesUpdater.getSynchronizationInstructions(),
		    synchronizationServiceAddress);
	}

	if (logger.isDebugEnabled()) {
	    logger.debug("Initializing scheduler for synchronization service...");
	}
	if (synchronizationService != null) {
	    synchronizationService.initializeScheduler(
		    servletContextEvent.getServletContext(),
		    synchronizationServiceSchedulerJobClassName,
		    synchronizationServiceSchedulerCronExpression,
		    synchronizationServiceClassName,
		    synchronizationServiceAddress);
	} else {
	    logger.error("synchronizationService is null, scheduler initialization failed.");
	}

    }

}
