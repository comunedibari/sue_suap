package it.wego.cross.cross_dms;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.util.Date;


	public class JobPratiche implements Job{ 

		public void execute(JobExecutionContext context) throws JobExecutionException {
			System.out.println("Job Elabora pratiche Civilia --->>> START! Time is " + new Date());

			String[] args = null;
			
			Main.main(args);

			System.out.println("Job Elabora pratiche Civilia ---<<< END! Time is " + new Date());		
	    } 
		
	}