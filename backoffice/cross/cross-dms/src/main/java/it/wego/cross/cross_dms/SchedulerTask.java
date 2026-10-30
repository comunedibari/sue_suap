package it.wego.cross.cross_dms;


import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Properties;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import it.wego.cross.cross_dms.utilities.LogClass;
import it.wego.cross.cross_dms.utilities.myUtility;

public class SchedulerTask {
	private static LogClass logger;

	private static Timer timer = new Timer();

	private static Calendar getFirstTime() {
		Calendar cal = Calendar.getInstance();

		int currentMinute = cal.get(Calendar.MINUTE);

		if (currentMinute < 45) {
			cal.set(Calendar.MINUTE, 45);
		}
		if (currentMinute < 30) {
			cal.set(Calendar.MINUTE, 30);
		}
		if (currentMinute < 15) {
			cal.set(Calendar.MINUTE, 15);
		}
		if (currentMinute >= 45) {
			cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 1);
			cal.set(Calendar.MINUTE, 0);
		}

		cal.set(Calendar.SECOND, 0);

		return cal;
	}

	public static void main(String... args) {
		Calendar firstTaskTime = getFirstTime();
		System.out.println("START AT: " + firstTaskTime.getTime());

		Properties configProperties;
		String crontimebatch = "";

		try {
			myUtility utl = new myUtility();
			System.out.println("DMS START");
			logger.trace("DMS START");

			///0 0/5 14 * * ?	Fire every 5 minutes starting at 2pm and ending at 2:55pm, every day        
			configProperties = myUtility.readConfigProperties("dms.properties");
			crontimebatch = configProperties.getProperty("crontimebatch");
	
			JobDetail jobElaboraPratiche = JobBuilder.newJob(JobPratiche.class)
	                .withIdentity("JobPratiche", "group").build();
	
			String cronSchedulazione = "0 0/1 * * * ?";
			cronSchedulazione = "0 0/" + ((crontimebatch != "") ? crontimebatch: 10)  + " * * * ?";
	        Trigger trigger = TriggerBuilder.newTrigger()
	                .withIdentity("cronTrigger", "group")
	                .withSchedule(CronScheduleBuilder.cronSchedule(cronSchedulazione))
	                .build();
	        
	        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
	        scheduler.start();
	        scheduler.scheduleJob(jobElaboraPratiche, trigger);

			Thread.sleep(1000);
			//scheduler3.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}

class MyTask extends TimerTask {
	public void run() {
		System.out.println("running task");
	}
}