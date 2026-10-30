package it.wego.cross.mailservices;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SearchTerm;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import it.wego.cross.mailservices.utilities.myUtility;

@EnableScheduling
@SpringBootApplication
public class CrossJobelaboraMailinboxApplication
{
	private static final Logger LogClass = LoggerFactory.getLogger(CrossJobelaboraMailinboxApplication.class.getName());

	public static void main(String[] args) {
						
		SpringApplication.run(CrossJobelaboraMailinboxApplication.class, args);
	}

	public void run(String... args) 
	{  	
		/*
		try {
			this.testConnessione();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
/*-----------------------------------------------------------------------------------------------------------
		*    *    *    *    *  command to be executed
		┬    ┬    ┬    ┬    ┬
		│    │    │    │    │
		│    │    │    │    │
		│    │    │    │    └───── day of week (0 - 7) (0 or 7 are Sunday, or use names)
		│    │    │    └────────── month (1 - 12)
		│    │    └─────────────── day of month (1 - 31)
		│    └──────────────────── hour (0 - 23)
		└───────────────────────── min (0 - 59)
------------------------------------------------------------------------------------------------------------*/

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		System.out.println("CrossJobelaboraMailinboxApplication RUN " + dateFormat.format(cal.getTime()));
		
		Properties configProperties;
		String cronjobInboxMassivo = "";
		String cronjobInboxGiornaliero="";
		String cronjobRiassegnaUtentePratica="";

		try {	

			System.out.println("JOB MAILSERVICE START " + dateFormat.format(cal.getTime()));
			LogClass.trace("JOB MAILSERVICE STAR");

			///0 0/5 14 * * ?	Fire every 5 minutes starting at 2pm every day        
			configProperties = myUtility.readConfigProperties("application.properties");
			cronjobInboxMassivo = configProperties.getProperty("cronjobInboxMassivo");
			cronjobInboxGiornaliero = configProperties.getProperty("cronjobInboxGiornaliero");
			cronjobRiassegnaUtentePratica = configProperties.getProperty("cronjobRiassegnaUtentePratica");

			String cronSchedulazione = "0 0 0 * * ?";

//JOB 1	------------------------------------------------------------------------------------------------------
/* *			
			cronSchedulazione = ((cronjobInboxMassivo != "") ? cronjobInboxMassivo: cronSchedulazione);

			JobDetail JobElaboraInboxMassivo = JobBuilder.newJob(JobElaboraInboxMassivo.class)
	                .withIdentity("JobElaboraInboxMassivo", "group").build();
			
			System.out.println("CRON SCHEDULAZIONE JOB ELABORA INBOX MASSIVO:" + cronSchedulazione);

			Trigger triggerInboxMassivo = (Trigger) TriggerBuilder.newTrigger()
	                .withIdentity("cronTriggerInboxMassivo", "group")
	                .withSchedule(CronScheduleBuilder.cronSchedule(cronSchedulazione))
	                .build();
	        
			System.out.println("CREATE TRIGGER JOB ELABORA INBOX MASSIVO " + dateFormat.format(cal.getTime()));

			Scheduler schedulerInboxMassivo = (Scheduler) new StdSchedulerFactory().getScheduler();

			System.out.println("START JOB ELABORA INBOX MASSIVO" + dateFormat.format(cal.getTime()));
			((Service) schedulerInboxMassivo).start();

			System.out.println("SCHEDULE JOB ELABORA INBOX MASSIVO " + dateFormat.format(cal.getTime()));
			((org.quartz.Scheduler) schedulerInboxMassivo).scheduleJob(JobElaboraInboxMassivo, (org.quartz.Trigger) triggerInboxMassivo);

			System.out.println("SLEEP JOB ELABORA INBOX MASSIVO" + dateFormat.format(cal.getTime()));
			Thread.sleep(1000);
* */
//JOB 2 ---------------------------------------------------------------------------------------------------------
/*			
			cronSchedulazione = ((cronjobInboxGiornaliero != "") ? cronjobInboxGiornaliero: cronSchedulazione);

			JobDetail JobElaboraInboxGiornaliero = JobBuilder.newJob(JobElaboraInboxGiornaliero.class)
	                .withIdentity("JobElaboraInboxGiornaliero", "group").build();
			
			System.out.println("CRON SCHEDULAZIONE JOB ELABORA INBOX GIORNALIERO:" + cronSchedulazione);

			Trigger triggerInboxGiornaliero = (Trigger) TriggerBuilder.newTrigger()
	                .withIdentity("cronTriggerInboxGiornaliero", "group")
	                .withSchedule(CronScheduleBuilder.cronSchedule(cronSchedulazione))
	                .build();
	        
			System.out.println("CREATE TRIGGER JOB ELABORA INBOX GIORNALIERO " + dateFormat.format(cal.getTime()));

			Scheduler schedulerInboxGiornaliero = (Scheduler) new StdSchedulerFactory().getScheduler();

			System.out.println("START JOB ELABORA INBOX GIORNALIERO" + dateFormat.format(cal.getTime()));
			((Service) schedulerInboxGiornaliero).start();

			System.out.println("SCHEDULE JOB ELABORA INBOX GIORNALIERO " + dateFormat.format(cal.getTime()));
			((org.quartz.Scheduler) schedulerInboxGiornaliero).scheduleJob(JobElaboraInboxGiornaliero, (org.quartz.Trigger) triggerInboxGiornaliero);

			System.out.println("SLEEP JOB ELABORA INBOX GIORNALIERO" + dateFormat.format(cal.getTime()));
			Thread.sleep(1000);
*/			
//JOB 3	-------------------------------------------------------------------------------------------------------------		
/* *			
			cronSchedulazione = ((cronjobRiassegnaUtentePratica != "") ? cronjobRiassegnaUtentePratica: cronSchedulazione);

			JobDetail JobElaboraRiassegnazioneUtentePratica = JobBuilder.newJob(JobElaboraRiassegnazioneUtentePratica.class)
	                .withIdentity("JobElaboraRiassegnazioneUtentePratica", "group").build();
			
			System.out.println("CRON SCHEDULAZIONE JOB ELABORA RIASSEGNAZIONE UTENTE PRATICA:" + cronSchedulazione);

			Trigger triggerRiassegnazioneUtentePratica = (Trigger) TriggerBuilder.newTrigger()
	                .withIdentity("cronTriggerRiassegnazioneUtentePratica", "group")
	                .withSchedule(CronScheduleBuilder.cronSchedule(cronSchedulazione))
	                .build();
	        
			System.out.println("CREATE TRIGGER JOB ELABORA RIASSEGNAZIONE UTENTE PRATICA " + dateFormat.format(cal.getTime()));

			Scheduler schedulerRiassegnazioneUtentePratica = (Scheduler) new StdSchedulerFactory().getScheduler();

			System.out.println("START JOB ELABORA RIASSEGNAZIONE UTENTE PRATICA" + dateFormat.format(cal.getTime()));
			((Service) schedulerRiassegnazioneUtentePratica).start();

			System.out.println("SCHEDULE JOB ELABORA RIASSEGNAZIONE UTENTE PRATICA " + dateFormat.format(cal.getTime()));
			((org.quartz.Scheduler) schedulerRiassegnazioneUtentePratica).scheduleJob(JobElaboraRiassegnazioneUtentePratica, (org.quartz.Trigger) triggerRiassegnazioneUtentePratica);
			System.out.println("SLEEP JOB ELABORA RIASSEGNAZIONE UTENTE PRATICA" + dateFormat.format(cal.getTime()));
			Thread.sleep(1000);
* */
//END	-------------------------------------------------------------------------------------------------------------		
			System.out.println("END" + dateFormat.format(cal.getTime()));
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	public void testConnessione() throws Exception {
		LogClass.info("EMAIL: recupero lo store");
	
		
		
		String host = "mail.pec.rupar.puglia.it";
		String port = "993";

		//String host = "pop3s.pec.rupar.puglia.it";
		//String port = "995";

		Properties properties = new Properties();		 
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", port);
        
        String userName = "";
        String password = "";
        String keyword = "CONSEGNA";
        
        // SSL setting
        properties.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.imap.socketFactory.fallback", "false");
        properties.setProperty("mail.imap.socketFactory.port", String.valueOf(port));
 
        Session session = Session.getDefaultInstance(properties);
 
        try {
            // connects to the message store
            //Store store = session.getStore("imaps");
            Store store = session.getStore("imap"); // porta 993
            //store.connect(userName, password);
    		store.connect(host, userName, password);

            // opens the inbox folder
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_ONLY);
 
            // creates a search criterion
            SearchTerm searchCondition = new SearchTerm() {
                @Override
                public boolean match(Message message) {
                    try {
                        if (message.getSubject().contains(keyword)) {
                            return true;
                        }
                    } catch (MessagingException ex) {
                        ex.printStackTrace();
                    }
                    return false;
                }
            };
 
            // performs search through the folder
            Message[] foundMessages = folderInbox.search(searchCondition);
            System.out.println("Found message #" + foundMessages.length);
 
            for (int i = 0; i < foundMessages.length; i++) {
                Message message = foundMessages[i];
                String subject = message.getSubject();
    			System.out.println("Found message #" + i + ": " + subject + " msgid:" + message.getHeader("Message-ID")[0]);

            }
 
            // disconnect
            folderInbox.close(false);
            store.close();
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider.");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store.");
            ex.printStackTrace();
        }
	}	
}
