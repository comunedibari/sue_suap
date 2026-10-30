package it.wego.cross.mailservices;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import it.wego.cross.mailservices.scheduler.JobElaboraMensileMassivo;
import it.wego.cross.mailservices.utilities.myUtility;

@Component
public class ScheduledTaskInboxMassivo {
	private static final Logger logger = LoggerFactory.getLogger(ScheduledTaskInboxMassivo.class.getName());

	private static final String TIPO_ELAB_ANNUALE = "annuale"; 
	private static final String TIPO_ELAB_GIORNALIERA = "giornaliera"; 
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

	//@Scheduled(fixedRate = 5000, initialDelay = 4000)
	//@Scheduled(cron = "0 0 0 * * *")
	
	@Scheduled(cron = "${cronjobInboxMassivo}")
	public void execute() throws InterruptedException {
		System.out.println("Code is being executed... Time: " + formatter.format(LocalDateTime.now()));

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		System.out.println("EXECUTE - Job Elabora mail inbox cross massivo --->>> START! Time is " +  dateFormat.format(cal.getTime()));
		
		Thread[] thread=new Thread[20];
		Properties configProperties;
		try {
			configProperties = myUtility.readConfigProperties("application.properties");
			String anno_elab = configProperties.getProperty("anno.elaborazione").trim();
			String tipo_elab = configProperties.getProperty("tipo.elaborazione").trim();
			
			for(int mese=0; mese<12; mese++) {
				System.out.println("EXECUTE - Job Elabora mail inbox cross massivo --->>> START! Thread mese:" +  mese);
				
				thread[mese] = new Thread(
					new JobElaboraMensileMassivo(
							anno_elab,
							(mese+1)
							));
			}
			for(int mese=0; mese<12; mese++) {thread[mese].start();}
			
			for(int mese=0; mese<12; mese++) {
				thread[mese].join();
				System.out.println("EXECUTE - Job Elabora mail inbox cross massivo --->>> END! Thread mese:" +  mese);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}				
		
		System.out.println("EXECUTE - Job Elabora mail inbox cross massivo <<<--- END! Time is " +  dateFormat.format(cal.getTime()));

	}
}