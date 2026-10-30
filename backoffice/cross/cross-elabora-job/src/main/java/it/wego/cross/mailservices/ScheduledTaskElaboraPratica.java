package it.wego.cross.mailservices;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTaskElaboraPratica {

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

	//@Scheduled(fixedRate = 5000, initialDelay = 4000)
	@Scheduled(cron = "${cronjobRiassegnaUtentePratica}")
	public void execute() throws InterruptedException {
		// some logic that will be executed on a schedule
		System.out.println("Code is being executed... Time: " + formatter.format(LocalDateTime.now()));
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		System.out.println("EXECUTE - Job Elabora riassegnazione utente pratica --->>> START! Time is " +  dateFormat.format(cal.getTime()));
		/*
		JobRunnableRiassegnaPratiche elabora = new JobRunnableRiassegnaPratiche((int) Thread.currentThread().getId());
		*/
		System.out.println("EXECUTE - Job Elabora riassegnazione utente pratica <<<--- END! Time is " +  dateFormat.format(cal.getTime()));

	}
}