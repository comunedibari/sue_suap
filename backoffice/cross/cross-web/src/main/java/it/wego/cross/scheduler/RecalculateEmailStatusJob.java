package it.wego.cross.scheduler;

import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import it.wego.cross.constants.StatiEmail;
import it.wego.cross.dao.JobDao;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.service.PraticheService;
import it.wego.cross.utils.Log;

/**
 *
 * @author CS
 */
public class RecalculateEmailStatusJob implements Job {

	@Autowired
	private JobDao jobDao;	
	@Autowired
	private PraticaDao praticaDao;
	@Autowired
	private LookupDao lookupDao;
	@Autowired
	private PraticheService praticheService;
	@Autowired
	private MessageSource messageSource;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		Map<Integer,Long> countPerEvents = jobDao.extractEmailPerEventsWithStatus10();
		
		for(Entry<Integer, Long> countPerEvent:countPerEvents.entrySet()) {
			Integer idPraticaEvento = countPerEvent.getKey();
			Long numberOfMails = countPerEvent.getValue();
			Long numberOfSentEmails = jobDao.extractEmailSent(idPraticaEvento);
			if(numberOfMails.equals(numberOfSentEmails)) {
				PraticheEventi pe = praticaDao.getDettaglioPraticaEvento(idPraticaEvento);
				pe.setStatoMail(lookupDao.findStatoMailByCodice(StatiEmail.MARCATA_COME_CONSEGNATA));
				try {
					praticheService.updatePraticaEventoTransactional(pe);
				} catch (Exception e) {
					String msgError = messageSource.getMessage("error.update.paperwork_events", null, Locale.getDefault());
					Log.SQL.error(msgError, e);
					Log.APP.error("Si è verificato un errore cercando di aggiornare una praticaEvento", e);
				}
			}
		}

	}
}
