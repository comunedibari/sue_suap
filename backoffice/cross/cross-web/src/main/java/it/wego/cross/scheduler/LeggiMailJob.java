package it.wego.cross.scheduler;

import it.wego.cross.service.MailService;
import it.wego.cross.utils.Log;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author CS
 */
public class LeggiMailJob implements Job {

    @Autowired
    private MailService mailService;
    private Boolean running = false;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            Log.SCHEDULER.info("Leggo le email ricevute");
            if (!running) {
                running = true;
                Log.SCHEDULER.info("Accedo alla casella email");
                mailService.gestioneEmail();
                running = false;
            } else {
                Log.SCHEDULER.info("Lo scheduler EMAIL e' in esecuzione non faccio niente ...");
            }
        } catch (Exception ex) {
            Log.SCHEDULER.error("ERRORE LeggiMailJob \n", ex);
            running = false;
        }
    }
}
