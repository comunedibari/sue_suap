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
package it.people.feservice.console.synchronization.service;

import static org.quartz.CronScheduleBuilder.cronSchedule;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.xml.rpc.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import it.people.console.ws.client.definitions.DbUpdater;
import it.people.console.ws.client.definitions.DbUpdaterServiceLocator;
import it.people.util.resourcesupdater.ISynchronizationService;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         29/gen/2012 18:13:19
 */
public class Synchronizer implements ISynchronizationService {

	private static Logger logger = LoggerFactory.getLogger(Synchronizer.class);

	public static final String SERVLET_CONTEXT = "SERVLET_CONTEXT";
	public static final String SERVICE_ADDRESS = "SERVICE_ADDRESS";
	public static final String SERVICE_CLASS_NAME = "SERVICE_CLASS_NAME";
	public static final String PEOPLE_CONTEXT_PATH = "PEOPLE_CONTEXT_PATH";

	public Synchronizer() {
		super();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.people.util.messagebundle.updater.ISynchronizationService#update(java.util
	 * .Vector)
	 */
	@Override
	public void update(Vector<String> instructions, String serviceAddress) {

		DbUpdaterServiceLocator serviceLocator = new DbUpdaterServiceLocator();
		try {
			DbUpdater service = serviceLocator.getDbUpdaterSoap11(new URL(serviceAddress));
			org.apache.axis.client.Stub serviceStub = (org.apache.axis.client.Stub) service;
			serviceStub.setTimeout(120000);
			service.update(instructions.toArray(new String[instructions.size()]));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.people.util.messagebundle.updater.ISynchronizationService#
	 * initializeScheduler(javax.servlet.ServletContext, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void initializeScheduler(ServletContext context, String peopleContextPath, String jobClassName,
			String cronExpression, String serviceClassName, String serviceAddress) {

		Class jobClass = null;
		Scheduler scheduler = null;
		try {
			jobClass = Class.forName(jobClassName);

			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();

			JobDetail jobDetail = JobBuilder.newJob(jobClass)
					.withIdentity("SynchronizationService", "SynchronizationGroup").build();
			jobDetail.getJobDataMap().put(Synchronizer.SERVLET_CONTEXT, context);
			jobDetail.getJobDataMap().put(Synchronizer.SERVICE_CLASS_NAME, serviceClassName);
			jobDetail.getJobDataMap().put(Synchronizer.SERVICE_ADDRESS, serviceAddress);
			// Add people Context Path to JDM
			jobDetail.getJobDataMap().put(Synchronizer.PEOPLE_CONTEXT_PATH, peopleContextPath);

			Trigger cronTrigger;
			cronTrigger = TriggerBuilder.newTrigger()
					.withIdentity("SynchronizationServiceCronTrigger", "ConnectsTriggers").startNow()
					.withSchedule(cronSchedule(cronExpression).withMisfireHandlingInstructionFireAndProceed()).build();

			scheduler.scheduleJob(jobDetail, cronTrigger);
		} catch (ClassNotFoundException e) {
			logger.error("Scheduling initializing of synchronization service failed.", e);
		} catch (SchedulerException e) {
			logger.error("Scheduling initializing of synchronization service failed.", e);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// catch (ParseException e) {
		// _logger.error("Scheduling initializing of synchronization service failed.",
		// e);
		// }

	}

}
