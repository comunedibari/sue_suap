package it.wego.cross.mailservices.scheduler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class JobElaboraRiassegnazioneUtentePratica implements Job {
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		System.out.println("EXECUTE - Job Elabora riassegnazione utente pratica --->>> START! Time is " +  dateFormat.format(cal.getTime()));
		
		JobRunnableRiassegnaPratiche elabora = new JobRunnableRiassegnaPratiche((int) Thread.currentThread().getId());
		
		System.out.println("EXECUTE - Job Elabora riassegnazione utente pratica <<<--- END! Time is " +  dateFormat.format(cal.getTime()));
	}
}