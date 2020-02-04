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
package it.people.feservice.console.synchronization.scheduler;

import javax.servlet.ServletContext;
 
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import it.people.feservice.console.synchronization.service.Synchronizer;
import it.people.util.messagebundle.updater.DbMessageBundlesUpdater;
import it.people.util.resourcesupdater.ISynchronizationService;
import it.people.util.table.updater.DbTableValuesUpdater;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 * 06/feb/2012 15:41:47
 */
public class SynchronizationJob implements Job {

	private static Logger _logger = LoggerFactory.getLogger(SynchronizationJob.class);
	
	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		
		//Keept but may be useless in future
		ServletContext servletContext = (ServletContext)jobExecutionContext.getJobDetail().getJobDataMap().get(Synchronizer.SERVLET_CONTEXT);
		
		String synchronizationServiceClassName = jobExecutionContext.getJobDetail().getJobDataMap().getString(Synchronizer.SERVICE_CLASS_NAME);
		String synchronizationServiceAddress = jobExecutionContext.getJobDetail().getJobDataMap().getString(Synchronizer.SERVICE_ADDRESS);
		String peopleContextPath = jobExecutionContext.getJobDetail().getJobDataMap().getString(Synchronizer.PEOPLE_CONTEXT_PATH);

		//Check for bundle synch need..
		if (_logger.isDebugEnabled()) {
			_logger.debug("Getting bundle updater instance...");
		}
		DbMessageBundlesUpdater dbMessageBundlesUpdater = DbMessageBundlesUpdater.getInstance();
		if (_logger.isDebugEnabled()) {
			_logger.debug("Updating bundles...");
		}
		boolean bundleSynchNeeded = dbMessageBundlesUpdater.update(peopleContextPath);
		if (_logger.isDebugEnabled()) {
			_logger.debug("Bundles updated.");
		}

		
		//Check for tablevalues Synch
		if (_logger.isDebugEnabled()) {
			_logger.debug("Getting tablevalues updater instance...");
		}
		DbTableValuesUpdater dbTableValuesUpdater = DbTableValuesUpdater.getInstance();
		if (_logger.isDebugEnabled()) {
			_logger.debug("Updating tablevalues...");
		}
		boolean tablevalueSynchNeeded = dbTableValuesUpdater.update(peopleContextPath);
		if (_logger.isDebugEnabled()) {
			_logger.debug("Tablevalues updated.");
		}
		
		if (bundleSynchNeeded || tablevalueSynchNeeded) {
		
			ISynchronizationService synchronizationService = null;
			try {
				Class peopleConsoleServiceClass = Class.forName(synchronizationServiceClassName);
				synchronizationService = (ISynchronizationService)peopleConsoleServiceClass.newInstance();
			} catch (ClassNotFoundException e) {
				_logger.error("", e);
			} catch (InstantiationException e) {
				_logger.error("", e);
			} catch (IllegalAccessException e) {
				_logger.error("", e);
			}
			
			//Start Synch if needed 
			if (bundleSynchNeeded) {
				synchronizationService.update(dbMessageBundlesUpdater.getSynchronizationInstructions(), synchronizationServiceAddress);
			}
			
			if (tablevalueSynchNeeded) {
				synchronizationService.update(dbTableValuesUpdater.getSynchronizationInstructions(), synchronizationServiceAddress);
			}
	
		}
	}

}
