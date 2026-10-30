package cross.cross_scripts.fix_reassignment_ruvo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableMap;

import cross.cross_scripts.CrossJpa;
import it.wego.cross.beans.EventoBean;
import it.wego.cross.constants.Constants;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.ProcessiDao;
import it.wego.cross.dao.UtentiDao;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.AttoriComunicazioneDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.dto.RecapitoDTO;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheEventiAllegati;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Utente;
import it.wego.cross.service.ProcessiServiceImpl;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.RecapitoUtils;
import it.wego.cross.utils.Utils;

public class FixReassignmentRuvo {
	private static final Logger logger = LogManager.getLogger(FixReassignmentRuvo.class);

	public static final String COLLEGA_ANAGRAFICA = "ANAG";
	public static final String MODIFICA_PROCURATORE = "MODPROC";
	public static final String RIMUOVI_ANAGRAFICA = "RANAG";
	public static final String COLLEGA_ENDOPROCEDIMENTO = "ENDO";
	public static final String RIMUOVI_ENDOPROCEDIMENTO = "RENDO";
	public static final String AGGIUNGI_DATO_CATASTALE = "ADDCATASTALE";
	public static final String MODIFICA_DATO_CATASTALE = "MODCATASTALE";
	public static final String RIMUOVI_DATO_CATASTALE = "DELCATASTALE";
	public static final String AGGIUNGI_INDIRIZZO_INTERVENTO = "ADDINDINT";
	public static final String MODIFICA_INDIRIZZO_INTERVENTO = "MODINDINT";
	public static final String RIMUOVI_INDIRIZZO_INTERVENTO = "DELINDINT";
	public static final String RIASSEGNA_PRATICA = "REASPRAT";
	public static final String RIASSEGNA_PRATICA_TEMPORIZZATA = "RIASPRAT";
	public static final String RIASSEGNA_PRATICA_AUTOMATICO = "RIASPRATAUTO";
	public static final String ASSEGNA_PRATICA = "ASPRAT";
	public static final String MARCA_MAIL_COME_CONSEGNATA = "SENTMAIL";
	public static final String MAIL_REINVIATA = "RESENTMAIL";
	public static final String COLLEGA_PRATICA = "CPRAT";
	public static final String SCOLLEGA_PRATICA = "SPRAT";

	public static final ImmutableMap<String, String> SYSTEM_EVENTS
	= new ImmutableMap.Builder<String, String>()
	.put(COLLEGA_ANAGRAFICA, "Collegamento nuova anagrafica")
	.put(MODIFICA_PROCURATORE, "Cambio tecnico procuratore")
	.put(RIMUOVI_ANAGRAFICA, "Rimozione anagrafica")
	.put(COLLEGA_ENDOPROCEDIMENTO, "Collegamento nuovo endoprocedimento")
	.put(RIMUOVI_ENDOPROCEDIMENTO, "Rimozione endoprocedimento")
	.put(AGGIUNGI_DATO_CATASTALE, "Inserimento nuovo dato catastale")
	.put(RIMUOVI_DATO_CATASTALE, "Rimozione dato catastale")
	.put(AGGIUNGI_INDIRIZZO_INTERVENTO, "Inserimento nuovo indirizzo intervento")
	.put(RIMUOVI_INDIRIZZO_INTERVENTO, "Rimozione indirizzo intervento")
	.put(MODIFICA_DATO_CATASTALE, "Modifica dato catastale")
	.put(MODIFICA_INDIRIZZO_INTERVENTO, "Modifica indirizzo intervento")
	.put(RIASSEGNA_PRATICA, "Riassegnazione pratica")
	.put(RIASSEGNA_PRATICA_TEMPORIZZATA, "Riassegnazione temporizzata")
	.put(RIASSEGNA_PRATICA_AUTOMATICO, "Riassegnazione automatizzata")
	.put(ASSEGNA_PRATICA, "Assegnazione pratica")
	.put(MARCA_MAIL_COME_CONSEGNATA, "Email marcata forzatamente come consegnata")
	.put(MAIL_REINVIATA, "Email rispedita")
	.put(COLLEGA_PRATICA, "Collegamento pratica")
	.put(SCOLLEGA_PRATICA, "Scollegamento pratica")
	.build();

	private EntityManager em;
	private UtentiDao utentiDao;
	private ProcessiServiceImpl processiService;
	private PraticaDao praticaDao;
	private ProcessiDao processiDao;

	public FixReassignmentRuvo(CrossJpa jpa) {
		em = jpa.get();
		praticaDao = new PraticaDao(em);
		utentiDao = new UtentiDao(em);
		processiDao = new ProcessiDao(em);
		processiService = new ProcessiServiceImpl(processiDao);
	}

	public void run() throws Exception {

//		Query q = em.createNativeQuery("SELECT id_pratica FROM TMP_PRATICHE_DA_RIASS_RUVO_07 p ");
//				
//
//		List<Integer> idPratiche= q.getResultList();
//		for(Integer idPratica: idPratiche) {
//			handlePaperwork(idPratica);
//		}

//		handlePaperwork(5144);
	}

	private void handlePaperwork(Integer idPratica) throws Exception {
		Pratica pratica = praticaDao.findPratica(idPratica);
		logger.info(pratica.getIdPratica() + " " + pratica.getIdentificativoPratica());
		Utente procuratore = utentiDao.findUtenteByCodiceFiscale("INTEGRAZIONE");
		EventoBean eb = new EventoBean();
		eb.setIdUtente(procuratore.getIdUtente());
		eb.setIdPratica(idPratica);
		aggiornamentoUtentePratica(eb);
	}

	public Pratica aggiornamentoUtentePratica(EventoBean eb) throws Exception { 
		Pratica pratica = praticaDao.findPratica(eb.getIdPratica());
		Utente procuratore = utentiDao.findUtenteByCodiceFiscale("INTEGRAZIONE");

		Log.APP.info("aggiornamentoUtentePratica - procuratore: " + procuratore.getIdUtente().intValue());
		Log.APP.info("aggiornamentoUtentePratica - Utente evento: " + eb.getIdUtente().intValue());

		if (eb.getIdUtente().equals(procuratore.getIdUtente())){
			pratica.setIntegrazione("S");
			pratica.setDataUltimoEventoEsterno(new Date());

			List<EventoDTO> eventiPratica = getEventiPratica(pratica);

			EventoDTO eventoIstr = null;
			EventoDTO archiveEvent = null;

			for (EventoDTO evento : eventiPratica) {
				String descrizione = evento.getDescrizione();
				if (descrizione!= null && descrizione.toLowerCase().contains("(istr)") && 
						(eventoIstr == null || eventoIstr.getIdPraticaEvento() < evento.getIdPraticaEvento()))
					eventoIstr = evento;

				String note = evento.getNote();
				if(note != null && note.toLowerCase().contains(" a archivio") &&
						(archiveEvent == null || archiveEvent.getIdPraticaEvento() < evento.getIdPraticaEvento()))
					archiveEvent = evento;

			}
			Utente nuovoAssegnatario = null;
			if(eventoIstr != null) {
				Log.APP.info("aggiornamentoUtentePratica - trovato evento ISTR: " + eventoIstr.getIdPraticaEvento());
				nuovoAssegnatario = findUtenteByIdUtente(eventoIstr.getIdUtente());
			}
			else if (archiveEvent != null && pratica.getIdUtente().getNome().toLowerCase().contains("archivio")) {
				Log.APP.info("aggiornamentoUtentePratica - trovato evento archivio: " + archiveEvent.getIdPraticaEvento());
				nuovoAssegnatario = findUtenteByIdUtente(archiveEvent.getIdUtente());
			}
			//        	Log.APP.info("nuovoAssegnatario: " + nuovoAssegnatario.getCodiceFiscale());
			if(nuovoAssegnatario != null) {

				Utente precedenteAssegnatario = findUtenteByIdUtente(pratica.getIdUtente().getIdUtente());
				Log.APP.info("aggiornamentoUtentePratica - precedenteAssegnatario:" + precedenteAssegnatario.getIdUtente().intValue());
				Log.APP.info("aggiornamentoUtentePratica - nuovoAssegnatario :" + nuovoAssegnatario.getIdUtente().intValue());
				try {
					em.getTransaction().begin();
					PraticheEventi riassegnaPratica = riassegnaPratica(pratica, precedenteAssegnatario, nuovoAssegnatario, null);
					Log.APP.info(riassegnaPratica.getNote());
					em.getTransaction().commit();
				}
				catch(Exception e) {
					em.getTransaction().rollback();
					logger.error("ROLLBACK per "+ eb.getIdPratica());
				}
			}
		}

		return pratica;
	}

	public List<EventoDTO> getEventiPratica(Pratica pratica) throws Exception {
		List<EventoDTO> eventi = new ArrayList<EventoDTO>();
		for (PraticheEventi pe : pratica.getPraticheEventiList()) {
			EventoDTO evento = serializeEvento(pe);
			eventi.add(evento);
		}
		return eventi;
	}

	public EventoDTO serializeEvento(PraticheEventi praticaEvento) throws Exception {
		EventoDTO e = new EventoDTO();
		//Forzo oggetto e contenuto
		e.setOggetto("OGGETTO DELLA COMUNICAZIONE");
		e.setContenuto("CONTENUTO DELLA EMAIL DA INVIARE\nIl testo &egrave; in html.\nCordiali saluti");
		//FINE
		e.setIdPratica(praticaEvento.getIdPratica().getIdPratica());
		e.setIdPraticaEvento(praticaEvento.getIdPraticaEvento());
		AttoriComunicazioneDTO destinatari = new AttoriComunicazioneDTO();
		//        List<AnagraficaRecapitoDTO> recapiti = getAnagraficheRecapito(praticaEvento.getPraticheEventiAnagraficheList());
		//        destinatari.setAnagrafiche(recapiti);
		//        List<EnteDTO> enti = getEnti(praticaEvento.getEntiList());
		//        destinatari.setEnti(enti);
		if (praticaEvento.getIdRecapitoNotifica() != null) {
			RecapitoDTO rec = new RecapitoDTO();
			RecapitoUtils.copyEntity2Recapito(praticaEvento.getIdRecapitoNotifica(), rec);
			destinatari.setNotifica(rec);
		}
		e.setNumProtocollo(praticaEvento.getProtocollo());
		e.setDataProtocollo(praticaEvento.getDataProtocollo());
		e.setDestinatari(destinatari);
		if (praticaEvento.getIdUtente() != null) {
			String operatore = praticaEvento.getIdUtente().getCognome() + " " + praticaEvento.getIdUtente().getNome();
			e.setOperatore(operatore);
		}
		if (praticaEvento.getDataEvento() != null) {
			e.setDataEvento(Utils.dateItalianFormat(praticaEvento.getDataEvento()));
		}
		if (praticaEvento.getIdUtente() != null) {
			e.setIdUtente(praticaEvento.getIdUtente().getIdUtente());
			e.setUsername(praticaEvento.getIdUtente().getUsername());
		}
		e.setNote(praticaEvento.getNote());

		if (praticaEvento.getIdEvento() != null) {
			e.setIdEvento(praticaEvento.getIdEvento().getIdEvento());
			e.setDescrizione(praticaEvento.getIdEvento().getDesEvento());
			e.setStatoPost(praticaEvento.getIdEvento().getStatoPost());
			//TODO: gestione scadenza custom
			//            e.setVisualizzaGiorniScadenzaCustom(pe.getIdEvento().getFlgVisualizzaScadenzaCustom());
			if (praticaEvento.getIdEvento().getIdTipoMittente() != null) {
				e.setIdTipoMittente(praticaEvento.getIdEvento().getIdTipoMittente().getIdTipoAttore());
				e.setMittente(praticaEvento.getIdEvento().getIdTipoMittente().getDesTipoAttore());
			}
			if (praticaEvento.getIdEvento().getIdTipoDestinatario() != null) {
				e.setIdTipoDestinatario(praticaEvento.getIdEvento().getIdTipoDestinatario().getIdTipoAttore());
				e.setDestinatario(praticaEvento.getIdEvento().getIdTipoDestinatario().getDesTipoAttore());
			}
			//            if (pe.getIdEvento().getVerso() != null) {
			//                e.setVerso(pe.getIdEvento().getVerso().toString());
			//            }
			e.setIdScript(praticaEvento.getIdEvento().getIdScriptEvento());
			e.setPubblicazionePortale(praticaEvento.getIdEvento().getFlgPortale());

			e.setInvioEmail(praticaEvento.getIdEvento().getFlgMail());
			e.setAllegatoEmail(praticaEvento.getIdEvento().getFlgAllMail());
			e.setProtocollo(praticaEvento.getIdEvento().getFlgProtocollazione());
			e.setScaricaRicevuta(praticaEvento.getIdEvento().getFlgRicevuta());
			e.setMostraDestinatari(praticaEvento.getIdEvento().getFlgDestinatari());
			e.setCaricaDocumentoFirmato(praticaEvento.getIdEvento().getFlgFirmato());

			e.setApriSottopratiche(praticaEvento.getIdEvento().getFlgApriSottopratica());
			e.setDestinatariSoloEnti(praticaEvento.getIdEvento().getFlgDestinatariSoloEnti());
			e.setVisualizzaProcedimentiRiferimento(praticaEvento.getIdEvento().getFlgVisualizzaProcedimenti());
			e.setProcedimentoRiferimento(praticaEvento.getIdEvento().getIdProcedimentoRiferimento());
			e.setNumeroMassimoDestinatari(praticaEvento.getIdEvento().getMaxDestinatari());

			if (praticaEvento.getIdEvento().getIdTipoDestinatario() != null) {
				e.setIdTipoDestinatario(praticaEvento.getIdEvento().getIdTipoDestinatario().getIdTipoAttore());
				e.setDestinatario(praticaEvento.getIdEvento().getIdTipoDestinatario().getDesTipoAttore());
			}
			//Il verso di un evento Pratica è il verso di evento_pratica non dell'evento collegato...che puo cambiare nel tempo.
			if (praticaEvento.getIdEvento().getVerso() != null) {
				e.setVerso(praticaEvento.getVerso());
			}
			e.setFunzioneApplicativa(praticaEvento.getIdEvento().getFunzioneApplicativa());
		} else {
			e.setPubblicazionePortale(praticaEvento.getVisibilitaUtente());
		}

		if (praticaEvento.getPraticheEventiAllegatiList() != null) {
			List<AllegatoDTO> allegati = new ArrayList<>();
			for (PraticheEventiAllegati pea : praticaEvento.getPraticheEventiAllegatiList()) {
				Allegati allegato = pea.getAllegati();
				AllegatoDTO al = new AllegatoDTO();
				al.setDescrizione(allegato.getDescrizione());
				al.setIdAllegato(allegato.getId());
				al.setNomeFile(allegato.getNomeFile());
				al.setTipoFile(allegato.getTipoFile());
				al.setPrincipale(Constants.IS_MAIN.equals(pea.getFlgIsPrincipale()));
				allegati.add(al);
			}
			e.setAllegati(allegati);
		}

		//        if (praticaEvento.getEmailList() != null && praticaEvento.getEmailList().size() > 0) {
		//            e.setEmail(emailSerializer.serialize(praticaEvento.getEmailList()));
		//        }
		return e;
	}
	public Utente findUtenteByIdUtente(Integer idUtente) {
		if (idUtente == null) {
			return null;
		} else {
			Utente user = utentiDao.findUtenteByIdUtente(idUtente);
			return user;
		}
	}

	@Transactional
	public PraticheEventi riassegnaPratica(Pratica pratica, Utente precedenteAssegnatario, Utente nuovoAssegnatario, Utente utenteConnesso) throws Exception {
		pratica.setIdUtente(nuovoAssegnatario);
		praticaDao.update(pratica);
		String msg = "Pratica passata in carico da "+precedenteAssegnatario.getNome()+" "+precedenteAssegnatario.getCognome()+" a "+nuovoAssegnatario.getNome()+" "+nuovoAssegnatario.getCognome();
		PraticheEventi salvaEventoGenerico = salvaEventoGenerico(pratica, "REASPRAT", msg, utenteConnesso);
		return salvaEventoGenerico;
	}

	public PraticheEventi salvaEventoGenerico(Pratica pratica, String codEvento, String note, Utente utenteConnesso) throws Exception {
		PraticheEventi evento = new PraticheEventi();
		evento.setIdPratica(pratica);
		String desEvento = SYSTEM_EVENTS.get(codEvento);
		ProcessiEventi evt = processiService.requireEventoDiSistema(codEvento, desEvento, pratica.getIdProcesso());
		evento.setIdEvento(evt);
		evento.setDataEvento(new Date());
		evento.setIdUtente(utenteConnesso);
		evento.setVisibilitaCross("S");
		evento.setVisibilitaUtente("N");
		evento.setDescrizioneEvento(desEvento);
		evento.setNote(note);
		praticaDao.insert(evento);
		return evento;
	}
}
