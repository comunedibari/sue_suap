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
package it.people.console.quartz;

import static org.quartz.CronScheduleBuilder.cronSchedule;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.quartz.JobDetailBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import it.people.console.config.ConsoleConfiguration;
import it.people.console.config.exceptions.ConsoleConfigurationException;
import it.people.console.quartz.jobs.BeServiceAvailabilityJob;
import it.people.console.quartz.jobs.JobConstants;
import it.people.console.system.AbstractLogger;

/**
 * Quartz Scheduler for PeopleConsole managed by Spring
 * 
 * @author Andrea Piemontese - Engineering Ingegneria Informatica S.p.A.
 * @created 12/ott/2012 10:49:25
 *
 */
@Service
public class ConsoleScheduler extends AbstractLogger {

	@Autowired
	private WebApplicationContext ctx;
	
	@Autowired
	private SchedulerFactoryBean quartzSchedulerFactory;
	
	private Scheduler scheduler = null;
	
	private boolean isInitialized = false;
		
	private static final int SYSTEM_JOBS_START_DELAY = 00000;
	
	@PostConstruct
	protected boolean initializeScheduler() {
			
		try {
			
			//Apply application context to scheduler factory
			quartzSchedulerFactory.setApplicationContext(ctx);
			quartzSchedulerFactory.setApplicationContextSchedulerContextKey(
					JobConstants.SchedulerContext.APPLICATION_CONTEXT_KEY);
			// Manually re-Init the Spring Bean.. (Important)
			quartzSchedulerFactory.afterPropertiesSet();

			// Grab the Scheduler instance from the Factory
			scheduler = quartzSchedulerFactory.getScheduler();
			scheduler.start();

			isInitialized = true;

			// Schedule all Jobs
			startAllSystemJobs(SYSTEM_JOBS_START_DELAY);

		} catch (SchedulerException se) {
			logger.error("Exception initializing Console Scheduler ", se);

		} catch (Exception e) {
			logger.error("Unable to set quartzSchedulerFactory ApplicationContext properties", e);
		}
		return true;
	}
	
	/**
	 * Schedule all system jobs needed by PeopleConosle
	 * @param startDelyMillis start all systems jobs with a delay of given milliseconds
	 * @throws SchedulerException
	 */
	public void startAllSystemJobs(int startDelyMillis) throws SchedulerException {
		
		try {
			String infoUpdateMinutes = ConsoleConfiguration.instance().getConsoleInfoUpdateTrigger();
		
			scheduleBeServiceAvailabilityJob("0 0/" + infoUpdateMinutes + " * * * ?", startDelyMillis);
		
		} catch (ConsoleConfigurationException e) {

		}
		
	}
	
	@PreDestroy
	protected boolean shutDownScheduler() {
		
		if (isInitialized) {
		 try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			logger.error("Error shutting down Quartz scheduler instance (PreDestroy)");
			return false;
		}
		}
		return true;
	}
	
	/**
	 * Schedule or RE-schedule beServiceAvailability Job
	 * @param cronExpression
	 * @param startDealyMilis start the trigger FIRST execution after given milliseconds. 
	 * @throws SchedulerException
	 */
	public void scheduleBeServiceAvailabilityJob(String cronExpression, int startDelayMillis) throws SchedulerException {
		
		JobKey jobKey = new JobKey(JobConstants.BE_SERVICE_AVAILABILITY_JOB_KEY, Scheduler.DEFAULT_GROUP);
		TriggerKey trigKey = new TriggerKey(JobConstants.BE_SERVICE_AVAILABILITY_JOB_KEY, Scheduler.DEFAULT_GROUP);
		
		//Build the Job detail
		JobDetail jobDetail = JobBuilder.newJob(BeServiceAvailabilityJob.class)
				.withIdentity(jobKey).build();
		
		//Build trigger
		Trigger cronTrigger = TriggerBuilder.newTrigger()
		.withIdentity(trigKey).startAt(new Date(System.currentTimeMillis() + startDelayMillis))
		.withSchedule(cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing())
		.forJob(jobKey)
		.build();
		
		//Check if schedule or reschedule
		if (!scheduler.checkExists(jobKey)) {
			getSchedulerInstance().scheduleJob(jobDetail, cronTrigger);
		} 
		else {
			scheduler.rescheduleJob(trigKey, cronTrigger);
		}
	}

	/**
	 * @return the isInitialized
	 */
	public boolean isInitialized() {
		return isInitialized;
	}
	
	/**
	 * @return the scheduler
	 */
	public Scheduler getSchedulerInstance() {
		return scheduler;
	}

	/**
	 * @param scheduler the scheduler to set
	 */
	@SuppressWarnings("unused")
	private void setSchedulerInstance(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	
}
