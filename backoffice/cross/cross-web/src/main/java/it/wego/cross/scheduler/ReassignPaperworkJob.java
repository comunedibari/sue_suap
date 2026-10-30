package it.wego.cross.scheduler;

import java.util.List;
import java.util.Locale;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import it.wego.cross.actions.SystemEventAction;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.UtentiDao;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.Utente;
import it.wego.cross.service.PraticheService;
import it.wego.cross.utils.Log;

/**
 *
 * @author CS
 */
public class ReassignPaperworkJob implements Job {

	@Autowired
	private PraticaDao praticaDao;
	@Autowired
	private UtentiDao utentiDao;
	@Autowired
	private PraticheService praticheService;
	@Autowired
	private MessageSource messageSource;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		List<PraticheEventi>paperworksToReassign = praticaDao.getPaperworkToReassign();
		for(PraticheEventi paperworkToReassign: paperworksToReassign) {
			boolean isAutomated = false;
			try {
				Utente utente = paperworkToReassign.getUtenteRiassegnazione();
				Utente utenteCorrente = utentiDao.findUtenteByIdPratica(paperworkToReassign.getIdPratica().getIdPratica()); 
				if(utente ==null) {
					List<PraticheEventi> events = praticaDao.descSortedPraticheEventi(paperworkToReassign.getIdPratica().getIdPratica());
					PraticheEventi istrEvent = null;
					PraticheEventi archiveEvent = null;
					for(PraticheEventi event: events) {
						String description = event.getDescrizioneEvento();
						if(description != null && description.toUpperCase().contains("(ISTR)") && istrEvent == null )
							istrEvent = event;
						String note = event.getNote();
						if(note != null && note.toLowerCase().contains(" a archivio") && archiveEvent == null )
							archiveEvent = event;
					}

					if(istrEvent != null) 
						utente = istrEvent.getIdUtente();
					else if(archiveEvent != null && "Archivio".equalsIgnoreCase(utenteCorrente.getNome())) 
						utente = archiveEvent.getIdUtente();
					else 
						utente = utenteCorrente;
					isAutomated = true;
				}
				
				Log.SCHEDULER.info((isAutomated ? SystemEventAction.RIASSEGNA_PRATICA_AUTOMATICO : SystemEventAction.RIASSEGNA_PRATICA)+": Riassegnazione della pratica l'utente " + utente);
				praticheService.reassignPaperworkFromAdmin(utente.getIdUtente(), paperworkToReassign.getIdPratica().getIdPratica(), null, isAutomated);
			} catch (Exception e) {
				String msgError = messageSource.getMessage("error.pratiche.assegnazione.utente", null, Locale.getDefault());
				Log.SQL.error(msgError, e);
				Log.APP.error("Si è verificato un errore cercando di riassegnare la pratica", e);
			}
		}

	}
}
