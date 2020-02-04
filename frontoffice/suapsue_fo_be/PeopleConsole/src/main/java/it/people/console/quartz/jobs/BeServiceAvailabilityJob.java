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
package it.people.console.quartz.jobs;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import it.people.console.beservices.BeServicesChecker;


/**
 * This Quartz Job checks the availability of BackEnd services trying to get WS WSDL
 * 
 * @author Andrea Piemontese - Engineering Ingegneria Informatica S.p.A.
 * @created 12/ott/2012 14:37:08
 *
 */


@DisallowConcurrentExecution
public class BeServiceAvailabilityJob implements Job {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext jobCtx) throws JobExecutionException {
		
		ApplicationContext appCtx = null;
		BeServicesChecker beServicesChecker = null;
		
		try {
			//Get the Application Context from scheduler data map
			appCtx = (ApplicationContext) jobCtx.getScheduler().getContext()
					.get(JobConstants.SchedulerContext.APPLICATION_CONTEXT_KEY);
			
			beServicesChecker = (BeServicesChecker) appCtx.getBean("beServicesChecker");
			beServicesChecker.updateBeServicesAvailability();
			
		} catch (SchedulerException e) {
			logger.error("Exception executing BeServiceAvailabilityJob", e);
		}
	}

}
