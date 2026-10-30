/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;

import com.google.common.base.Strings;

import it.wego.cross.actions.SystemEventAction;
import it.wego.cross.beans.EventoBean;
import it.wego.cross.dao.AllegatiDao;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.ProcedimentiDao;
import it.wego.cross.dao.ProcessiDao;
import it.wego.cross.dao.TemplateDao;
import it.wego.cross.dao.UsefulDao;
import it.wego.cross.dao.UtentiDao;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.dto.dozer.PraticaProcedimentiPKDTO;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaEventiProcedimenti;
import it.wego.cross.entity.PraticaEventiProcedimentiPK;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheEventiAllegati;
import it.wego.cross.entity.PraticheEventiAllegatiPK;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Utente;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.plugins.documenti.GestioneAllegati;
import it.wego.cross.service.AnagraficheService;
import it.wego.cross.service.DocEngineService;
import it.wego.cross.service.EventiService;
import it.wego.cross.service.MailService;
import it.wego.cross.service.PluginService;
import it.wego.cross.service.PraticheProtocolloService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.UtentiService;
import it.wego.cross.service.WorkFlowService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;

/**
 *
 * @author Gabriele
 */
@Repository
public abstract class AbstractEvent {

    @Autowired
    protected PraticaDao praticaDao;
    @Autowired
    protected AllegatiDao allegatiDao;
    @Autowired
    protected PluginService pluginService;
    @Autowired
    protected PraticheService praticheService;
    @Autowired
    protected PraticheProtocolloService praticheProtocolloService;
    @Autowired
    protected DocEngineService docEngineService;
    @Autowired
    protected WorkFlowService workFlowService;
    @Autowired
    protected ProcessiDao processiDao;
    @Autowired
    protected UsefulDao usefulDao;
    @Autowired
    protected LookupDao lookupDao;
    @Autowired
    protected MailService mailService;
    @Autowired
    protected EventiService eventiService;
    @Autowired
    protected TemplateDao templateDao;
    @Autowired
    protected ApplicationContext appContext;
    @Autowired
    private ProcedimentiDao procedimentiDao;
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private AnagraficheService anagraficheService;
    @Autowired
    private UtentiDao utentiDao;

    private MessageSource messageSource;
    
    @Autowired
    private SystemEventAction systemEventAction;
    
    public abstract void execute(EventoBean eb) throws Exception;

    public PraticheEventi insertEvento(EventoBean eb) throws Exception {
        Pratica pratica = praticheService.getPratica(eb.getIdPratica());
        ProcessiEventi processoEvento = praticheService.findProcessiEventi(eb.getIdEventoProcesso());
        Utente utente = utentiService.findUtenteByIdUtente(eb.getIdUtente());
        PraticheEventi eventoPratica = new PraticheEventi();
        eventoPratica.setIdPratica(pratica);
        eventoPratica.setIdEvento(processoEvento);
        eventoPratica.setDataEvento(eb.getDataEvento());
        eventoPratica.setIdUtente(utente);//devo inserire l'utente INTEGRAZIONE
        eventoPratica.setOggetto(eb.getNote());
        eventoPratica.setNote(eb.getNote());
        eventoPratica.setDescrizioneEvento(processoEvento.getDesEvento());
        if (processoEvento.getVerso() != null) {
            eventoPratica.setVerso(processoEvento.getVerso().toString());
        }
        eventoPratica.setVisibilitaCross(eb.getVisibilitaCross() ? "S" : "N");
        eventoPratica.setVisibilitaUtente(eb.getVisibilitaUtente() ? "S" : "N");

        eventoPratica.setPraticheEventiAllegatiList(new ArrayList<PraticheEventiAllegati>());
//        if (eb.getEventoProcesso().getFlgProtocollazione().equalsIgnoreCase("S")) {
        eventoPratica.setDataProtocollo(eb.getDataProtocollo());
        eventoPratica.setProtocollo(eb.getNumeroProtocollo());

        if (eb.getIdEventoPraticaPadre() != null) {
            PraticheEventi praticaEventoPadre = praticheService.getPraticaEvento(eb.getIdEventoPraticaPadre());
            eventoPratica.setPraticaEventoRef(praticaEventoPadre);
        }
//        }

        praticaDao.insert(eventoPratica);
        usefulDao.flush();
        if (eb.getPraticaProcedimentiSelected() != null) {
            for (PraticaProcedimentiPKDTO praticaProcedimento : eb.getPraticaProcedimentiSelected()) {
                PraticaEventiProcedimenti praticaEventiProcedimenti = new PraticaEventiProcedimenti();
                PraticaEventiProcedimentiPK praticaEventiProcedimentiPK = new PraticaEventiProcedimentiPK();
                praticaEventiProcedimentiPK.setIdPraticaEvento(eventoPratica.getIdPraticaEvento());
                ProcedimentiEnti procedimentiEnti = procedimentiDao.findProcedimentiEntiByProcedimentoEnte(praticaProcedimento.getIdEnte(), praticaProcedimento.getIdProcedimento());
                praticaEventiProcedimentiPK.setIdProcEnte(procedimentiEnti.getIdProcEnte());
                praticaEventiProcedimenti.setPraticaEventiProcedimentiPK(praticaEventiProcedimentiPK);
                praticaDao.insert(praticaEventiProcedimenti);
            }
        }
        //Inserimento Pratiche_eventi_anagrafiche
        
//        PraticheEventiAnagrafiche eventoPraticaAnagrafica = new PraticheEventiAnagrafiche();
//        eventoPraticaAnagrafica.setPraticheEventi(eventoPratica);
//        Anagrafica anagrafica = anagraficheService.findAnagraficaByCodFiscale("07818790722");
//        List<AnagraficaRecapiti> anagraficaRecapitiList = anagrafica.getAnagraficaRecapitiList();
//        eventoPraticaAnagrafica.setAnagrafica(anagrafica);
//        eventoPraticaAnagrafica.setIdRecapito(anagraficaRecapitiList.get(0).getIdRecapito());
//        PraticheEventiAnagrafichePK pk = new PraticheEventiAnagrafichePK(eventoPratica.getIdPraticaEvento(), anagrafica.getIdAnagrafica());
//        eventoPraticaAnagrafica.setPraticheEventiAnagrafichePK(pk);
//		praticaDao.insert(eventoPraticaAnagrafica);
		
		/////
        Enti ente = pratica.getIdProcEnte().getIdEnte();
        Integer idComune = null;
        if (pratica.getIdComune() != null) {
            idComune = pratica.getIdComune().getIdComune();
        }
        GestioneAllegati gestioneAllegati = pluginService.getGestioneAllegati(ente.getIdEnte(), idComune);
        List<Integer> allegati = new ArrayList<Integer>();
        for (Allegato allegato : eb.getAllegati()) {
            Allegati allegatoEntity;
            if (!Utils.e(allegato.getIdEsterno())) {
                allegatoEntity = allegatiDao.findByIdFileEsterno(allegato.getIdEsterno());
                if (allegatoEntity == null) {
                    allegatoEntity = getNuovoAllegato(allegato);
                }
            } else {
                if (allegato.getId() != null) {
                    allegatoEntity = allegatiDao.getAllegato(allegato.getId());
                } else {
                    allegatoEntity = getNuovoAllegato(allegato);
                }
            }
            Log.APP.info("Aggiungi allegato " + allegatoEntity.getNomeFile() + " all'evento " + eventoPratica.getIdEvento().getDesEvento());
            if(gestioneAllegati != null)
            	gestioneAllegati.add(allegatoEntity, ente, pratica.getIdComune(), eventoPratica.getDataEvento());
            if (allegato.getId() == null) {
                allegatiDao.insertAllegato(allegatoEntity);
            } else {
                allegatiDao.mergeAllegato(allegatoEntity);
            }
            usefulDao.flush();
            //Pulisco l'allegato dagli eventuali file binari
            allegato.setFile(null);
            allegati.add(allegatoEntity.getId());
            PraticheEventiAllegati praticaEventoAllegato = new PraticheEventiAllegati();
            PraticheEventiAllegatiPK key = new PraticheEventiAllegatiPK();
            key.setIdPraticaEvento(eventoPratica.getIdPraticaEvento());
            key.setIdAllegato(allegatoEntity.getId());
            praticaEventoAllegato.setPraticheEventiAllegatiPK(key);
            praticaEventoAllegato.setFlgIsPrincipale(allegato.getFileOrigine() ? "S" : "N");

            allegatiDao.inserPraticheEventiAllegato(praticaEventoAllegato);
            if (allegato.getSpedisci()) {
                praticaEventoAllegato.setFlgIsToSend("S");
            } else {
                praticaEventoAllegato.setFlgIsToSend("N");
            }
            praticaEventoAllegato.setAllegati(allegatoEntity);
            eventoPratica.getPraticheEventiAllegatiList().add(praticaEventoAllegato);
        }

        eb.setIdEventoPratica(eventoPratica.getIdPraticaEvento());
        eb.setIdAllegati(allegati);
        return eventoPratica;
    }

    private Allegati getNuovoAllegato(Allegato allegato) {

        Allegati a = new Allegati();
        a.setDescrizione(allegato.getDescrizione());
        a.setFile(allegato.getFile());
        a.setNomeFile(allegato.getNomeFile());
        a.setIdFileEsterno(allegato.getIdEsterno());
        //Imposto il mime type di default
        String mimeType = !Strings.isNullOrEmpty(allegato.getTipoFile()) ? allegato.getTipoFile() : MediaType.APPLICATION_OCTET_STREAM_VALUE;
        a.setTipoFile(mimeType);
        a.setPathFile(allegato.getPathFile());
        a.setDimensione(allegato.getDimensione());
        if(allegato.getPathFile() != null && allegato.getPathFile().length() > 0) {
        	File file = new File(allegato.getPathFile());
        	a.setDimensione(file.length());
        	
        }
        return a;
    }

	public void aggiornaUtentePraticaPerChiusura(Pratica pratica, PraticheEventi praticaEvento) {
		try {
			List<ProcessiEventi> processiEventiChiusura = praticheService.getProcessiEventiChiusura(pratica.getIdProcesso());
			if(processiEventiChiusura.contains(praticaEvento.getIdEvento())) {
				Utente utenteArchivio = utentiService.findUtenteDaUsername("cilanongestite");
				pratica.setIdUtente(utenteArchivio);
				praticheService.aggiornaPratica(pratica);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Pratica aggiornamentoUtentePratica(EventoBean eb) throws Exception { 
        Pratica pratica = praticheService.getPratica(eb.getIdPratica());
        Utente procuratore = utentiDao.findUtenteByCodiceFiscale("INTEGRAZIONE");
        Utente administrator = utentiDao.findUtenteByIdUtente(1);
        
        Log.APP.info("aggiornamentoUtentePratica - procuratore: " + procuratore.getIdUtente().intValue());
        Log.APP.info("aggiornamentoUtentePratica - Utente evento: " + eb.getIdUtente().intValue());

        if (eb.getIdUtente().equals(procuratore.getIdUtente())){
	        pratica.setIntegrazione("S");
	        pratica.setDataUltimoEventoEsterno(new Date());
	        
	        List<EventoDTO> eventiPratica = praticheService.getEventiPratica(pratica);
        	
        	EventoDTO eventoIstr = null;
        	List<EventoDTO> archiveEvents = new LinkedList<>();

        	for (EventoDTO evento : eventiPratica) {
        		String descrizione = evento.getDescrizione();
				if (descrizione!= null && descrizione.toLowerCase().contains("(istr)") && 
						(eventoIstr == null || eventoIstr.getIdPraticaEvento() < evento.getIdPraticaEvento()))
					eventoIstr = evento;
									
				String note = evento.getNote();
				if(note != null && note.toLowerCase().contains(" a archivio") &&
						(archiveEvents.isEmpty() || archiveEvents.get(archiveEvents.size()-1).getIdPraticaEvento() < evento.getIdPraticaEvento()))
					archiveEvents.add(evento);
				
			}
        	Utente nuovoAssegnatario = null;
        	if(eventoIstr != null && !pratica.getIdUtente().getCognome().toLowerCase().equals("deposito")) {
        		Log.APP.info("aggiornamentoUtentePratica - trovato evento ISTR: " + eventoIstr.getIdPraticaEvento());
        		nuovoAssegnatario = utentiService.findUtenteByIdUtente(eventoIstr.getIdUtente());
        	}
        	else if (!archiveEvents.isEmpty() && pratica.getIdUtente().getNome().toLowerCase().contains("archivio")) {
        		EventoDTO lastEvent = archiveEvents.get(archiveEvents.size()-1);
        		if(lastEvent.getIdUtente().equals(administrator.getIdUtente()) && archiveEvents.size() > 1)
        			lastEvent = archiveEvents.get(archiveEvents.size()-2);
        		Log.APP.info("aggiornamentoUtentePratica - trovato evento archivio: " + lastEvent.getIdPraticaEvento());
        		nuovoAssegnatario = utentiService.findUtenteByIdUtente(lastEvent.getIdUtente());
        	}

    		if(nuovoAssegnatario != null) {

    			Utente precedenteAssegnatario = utentiService.findUtenteByIdUtente(pratica.getIdUtente().getIdUtente());
    			Log.APP.info("aggiornamentoUtentePratica - precedenteAssegnatario:" + precedenteAssegnatario.getIdUtente().intValue());
        		Log.APP.info("aggiornamentoUtentePratica - nuovoAssegnatario :" + nuovoAssegnatario.getIdUtente().intValue());
        			        			
                PraticheEventi riassegnaPratica = systemEventAction.riassegnaPratica(pratica, precedenteAssegnatario, nuovoAssegnatario, null);
                Log.APP.info(riassegnaPratica.getNote());
    		}
        }

        return pratica;
    }
}
