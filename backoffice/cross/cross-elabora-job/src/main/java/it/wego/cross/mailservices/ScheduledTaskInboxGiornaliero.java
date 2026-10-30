package it.wego.cross.mailservices;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import it.wego.cross.mailservices.scheduler.JobElaboraMeseInCorso;
import it.wego.cross.mailservices.sql.DaoConfigCross;
import it.wego.cross.mailservices.utilities.myUtility;

@Component
public class ScheduledTaskInboxGiornaliero {
	private static final Logger logger = LoggerFactory.getLogger(ScheduledTaskInboxGiornaliero.class.getName());

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

	@Scheduled(cron = "${cronjobInboxGiornaliero}")
	public void execute() throws InterruptedException {
		System.out.println("EXECUTE - Job Elabora mail inbox cross giornaliero --->>> START! Time is " + formatter.format(LocalDateTime.now()));
		
		Thread[] thread=new Thread[20];
		Properties configProperties;
		try {
			DaoConfigCross dao = new DaoConfigCross();		
			configProperties = myUtility.readConfigProperties("application.properties");
			int numero_giorni_da_elaborare = Integer.valueOf(configProperties.getProperty("numero.giorni.elaborazione.giornaliera"));
			
			String dataCorrente = dao.queryString("SELECT CURRENT_DATE() as data_corrente", "data_corrente");
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd", Locale.ITALY);
			LocalDate date = LocalDate.parse(dataCorrente, formatter);
			int anno = date.getYear(); 
			int giorno = date.getDayOfMonth();
			int mese = date.getMonthValue(); 
						
		    System.out.println("ANNO:" + anno + " MESE:" + mese + " GIORNO:" + giorno);
	
	    	int giorno_elab = giorno; 
		    for(int i=0; i<(numero_giorni_da_elaborare+1); i++)
			{
		    	if(giorno_elab<1) {
		    		mese--;
		    		if(mese == 0){
		    			mese = 12;giorno_elab=31;
		    		} else {
		    			String last_day_previous_month = dao.queryString("SELECT LAST_DAY(CURDATE() - INTERVAL 1 MONTH) as last_day_previous_month",
		    					"last_day_previous_month");
		    			date = LocalDate.parse(last_day_previous_month, formatter);
		    			anno = date.getYear(); 
		    			giorno_elab = giorno = date.getDayOfMonth();
		    			mese = date.getMonthValue();		    			
		    		}
		    	} 
		    		
			    System.out.println("giornaliero ANNO:" + anno + " MESE:" + mese + " GIORNO:" + giorno_elab);
				logger.info("EXECUTE - Job Elabora mail inbox cross --->>> START! Thread  ANNO:" + anno + " MESE:" + mese + " GIORNO:" + giorno_elab);
				
				thread[i] = new Thread(
					new JobElaboraMeseInCorso(
							anno,
							mese,
							giorno_elab
							));

		    	giorno_elab--;
			}
		    for(int i=0; i<(numero_giorni_da_elaborare+1); i++) {thread[i].start();}
			
		    for(int i=0; i<(numero_giorni_da_elaborare+1); i++){
				thread[i].join();
				System.out.println("EXECUTE - Job Elabora mail inbox cross giornaliero --->>> END! Thread n:" +  i);
			}				    
				    
		} catch (Exception e) {
			e.printStackTrace();
		}				
		
		System.out.println("EXECUTE - Job Elabora mail inbox cross giornaliero <<<--- END! Time is " +  formatter.format(LocalDateTime.now()));
	}
		
}