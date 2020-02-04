package it.wego.cross.scheduler;

import it.wego.cross.dao.MailDao;
import it.wego.cross.entity.Email;
import it.wego.cross.service.MailService;
import it.wego.cross.utils.Log;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author CS
 */
public class SpedisciMailJob implements Job {

    @Autowired
    private MailService mailService;
    @Autowired
    private MailDao emailDao;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Log.SCHEDULER.info("Vedo se ci sono email da inviare");
        try {
            schedulerSendEmail();
        } catch (Exception ex) {
            Log.SCHEDULER.error("ERRORE Scheduler SendEmail \n", ex);
        }
    }

    private void schedulerSendEmail() throws Exception {
        List<Email> emailDB = emailDao.findEmailInstaging();
        for (Email eml : emailDB) {
            mailService.invioSingolaEmail(eml);
        }
    }
}
