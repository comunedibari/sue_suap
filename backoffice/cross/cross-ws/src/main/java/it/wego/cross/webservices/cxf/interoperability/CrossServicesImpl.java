/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.webservices.cxf.interoperability;

import it.wego.cross.actions.ComunicazioneAction;
import it.wego.cross.actions.PraticheAction;
import it.wego.cross.dao.AnagraficheDao;
import it.wego.cross.dao.EntiDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.UtentiDao;
import it.wego.cross.dto.ComunicazioneDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheEventiAllegati;
import it.wego.cross.entity.PraticheEventiAnagrafiche;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Recapiti;
import it.wego.cross.entity.Utente;
import it.wego.cross.events.comunicazione.bean.ComunicazioneBean;
import it.wego.cross.exception.CrossServicesException;
import it.wego.cross.serializer.UtentiSerializer;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.WorkFlowService;
import it.wego.cross.utils.AnagraficaUtils;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author giuseppe
 */
@Component("utilService")
@WebService(endpointInterface = "it.wego.cross.webservices.cxf.interoperability.CrossServices")
public class CrossServicesImpl implements CrossServices {

    @Autowired
    private EntiDao entiDao;
    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private AnagraficheDao anagraficheDao;
    @Autowired
    private UtentiDao utentiDao;
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private ComunicazioneAction comunicazioneAction;
    @Autowired
    private AnagraficaUtils anagraficaUtils;
    @Autowired
    private UtentiSerializer utentiSerializer;    
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private PraticheAction praticheAction;

    @Override
    public Evento inserisciEvento(Evento evento) throws CrossServicesException {
        try {
            Evento e = new Evento();
            e = inserisci(evento);
            return e;
        } catch (Exception ex) {
            Log.WS.error("Si è verificato un errore durante l'inserimento dell'evento", ex);
            throw new CrossServicesException(ex.getMessage());
        }
    }

    @Override
    public Eventi getListaEventi(Integer idPratica, String identificativoPratica, String codiceEnte) throws CrossServicesException {
        Enti ente = entiDao.findByCodEnte(codiceEnte);
        Pratica pratica = getPratica(idPratica, identificativoPratica, ente);
        List<Evento> eventiList = getEventiRegistrati(pratica);
        Eventi eventi = new Eventi();
        eventi.setEvento(eventiList);
        return eventi;
    }

    @Transactional(rollbackFor = Exception.class)
    private Evento inserisci(Evento evento) throws CrossServicesException, Exception {
    	Utente system = utentiDao.findUtenteByCodiceFiscale("INTEGRAZIONE");
        Enti ente = entiDao.findByCodEnte(evento.getCodiceEnte());
        it.wego.cross.entity.Pratica pratica = getPratica(evento.getIdPratica(), evento.getIdentificativoPratica(), ente);
        Integer idPratica = pratica.getIdPratica();
        //Utente utenteOriginale = pratica.getIdUtente();
        //ProcessiEventi pe = getEvento(pratica, evento);
        ProcessiEventi pe = getEventoIntegrazione(pratica, evento);
        ComunicazioneDTO comunicazione = getComunicazione(pe, evento, pratica);
        
        //PraticheEventi evt = comunicazioneAction.gestisciEvento(comunicazione, pratica, pe, utentiSerializer.serializeUtente(system));
        
        ComunicazioneBean cb = comunicazioneAction.gestisciEvento(comunicazione, pratica, pe, utentiSerializer.serializeUtente(system));
        Integer idEventoPratica = cb.getIdEventoPratica();
        PraticheEventi evt = praticheService.getPraticaEvento(idEventoPratica);
        pratica = praticheService.getPratica(idPratica);
        praticheService.startCommunicationProcess(pratica, evt, cb);
        
        evento.setNumeroProtocollo(evt.getProtocollo());
        evento.setDataProtocollo(evt.getDataProtocollo());
        evento.setIdPratica(evt.getIdPratica().getIdPratica());
        evento.setIdEvento(evt.getIdPraticaEvento());
        comunicazioneAction.aggiornaUtentePraticaIntegrazione(pratica, cb);
        Log.APP.info("InserisciEventoWS: Operazione terminata con successo");
        return evento;
    }

    private Pratica getPratica(Integer idPratica, String identificativoPratica, Enti ente) {
        Pratica pratica = null;
        if (idPratica != null && idPratica > 0) {
            pratica = praticaDao.findPratica(idPratica);
        } 
        /* Requisito R6*/
        if (idPratica == 0 && (identificativoPratica != null || ("").equalsIgnoreCase(identificativoPratica))) {
            pratica = praticaDao.findPraticaByIdentificativo(identificativoPratica);
        } 
        /* Requisito R6*/
         else if (identificativoPratica != null && ente != null) {
//            String[] riferimentiPratica = identificativoPratica.split("-");
//            if (riferimentiPratica.length == 3) {
//                String registro = riferimentiPratica[0];
//                String anno = riferimentiPratica[1];
//                if (!Utils.isInteger(anno)) {
//                    throw new CrossServicesException("Il valore inserito per l'anno non e' corretto. Valore ricevuto " + anno);
//                }
//                String numero = riferimentiPratica[2];
                pratica = praticaDao.findPraticaByIdentificativo(identificativoPratica);
//            } else {
//                throw new CrossServicesException("L'identificativo pratica ricevuto (" + identificativoPratica + ") deve essere nel formato registro/anno/numero protocollo");
//            }
        } else {
//            if (ente != null) {
//                throw new CrossServicesException("Riferimenti della pratica non validi");
//            } else {
//                throw new CrossServicesException("Non e' stato rilevato nessun ente per il codice specificato");
//            }
        	throw new CrossServicesException("L'identificativo pratica ricevuto (" + identificativoPratica + ") non è corretto");
        }
        if (pratica != null) {
            return pratica;
        } else {
            throw new CrossServicesException("Non è stata individuata nessuna pratica per i seguenti valori: idPratica=" + idPratica + ", identificativoPratica=" + identificativoPratica);
        }
    }

    private ProcessiEventi getEvento(Pratica pratica, Evento evento) throws CrossServicesException, Exception {
        List<ProcessiEventi> eventi;
        eventi = workFlowService.findAvailableEvents(pratica.getIdPratica());
        ProcessiEventi pe = null;
        for (ProcessiEventi evt : eventi) {
            if (evt.getCodEvento().toUpperCase().equals(evento.getCodiceEvento().toUpperCase())) {
                pe = evt;
                break;
            }
        }
        if (pe == null) {
            String error = "Non e' stato trovato nessun evento con codice " + evento.getCodiceEvento() + " per la pratica " + evento.getIdPratica() + " " + evento.getIdentificativoPratica();
            throw new CrossServicesException(error);
        }
        return pe;
    }
    
    private ProcessiEventi getEventoIntegrazione(Pratica pratica, Evento evento) throws CrossServicesException, Exception {
        ProcessiEventi pe = workFlowService.findProcessiEventiByIdProcessoCodEvento(pratica.getIdProcesso(), "PROCINT");
        if (pe == null) {
            String error = "Non e' stato trovato nessun evento con codice " + evento.getCodiceEvento() + " per la pratica " + evento.getIdPratica() + " " + evento.getIdentificativoPratica();
            throw new CrossServicesException(error);
        }
        return pe;
    }

    private List<String> getSoggettiComunicazione(Evento evento, Pratica pratica) {
        List<String> soggetti = new ArrayList<String>();
        for (Soggetto soggetto : evento.getSoggetto().getSoggetti()) {
            if (TipoSoggetto.ENTE.equals(soggetto.getTipoSoggetto())) {
                Enti entetmp = entiDao.findByCodEnte(soggetto.getCodice());
                soggetti.add("E|" + entetmp.getIdEnte());
            } else {
                Anagrafica ana = anagraficheDao.findByCodiceFiscale(soggetto.getCodiceFiscale());
                Recapiti recapito = anagraficaUtils.cercaIlRecapito(ana, pratica, false);
                soggetti.add("A|" + recapito.getIdRecapito());
            }
        }
        return soggetti;
    }

    private ComunicazioneDTO getComunicazione(ProcessiEventi pe, Evento evento, Pratica pratica) throws CrossServicesException {
        ComunicazioneDTO comunicazione = new ComunicazioneDTO();
        comunicazione.setVisualizzaEventoSuCross("S");
        comunicazione.setVisualizzaEventoPortale(pe.getFlgPortale());
        List<String> soggetti = getSoggettiComunicazione(evento, pratica);
        if (Utils.flagString(pe.getFlgMail()).equals("S")) {
            comunicazione.setInviaEmail("OK");
            if (pe.getVerso() == 'O') {
                comunicazione.setDestinatariIds(soggetti);
            } else if (pe.getVerso() == 'I') {
            	comunicazione.setDestinatariIds(soggetti);
                //comunicazione.setMittentiIds(soggetti);
            }
        }
        comunicazione.getScadenzeCustom();
        int counter = 0;
        boolean contieneAllegatoPrincipale = false;
        if (evento.getAllegati() != null) {
            for (Allegato allegato : evento.getAllegati().getAllegati()) {
            	if(allegato != null) {
	                if ((!Utils.e(allegato.getIdFileEsterno())) && (!Utils.e(allegato.getFile()))) {
	                    throw new CrossServicesException("Non e' possibile recuperare il file fisico poiche' non sono stati valorizzati i campi idFileEsterno o file " + allegato.getNomeFile());
	                }
	                if (Utils.e(allegato.getDescrizione())) {
	                    throw new CrossServicesException("Non e' stata specificata la descrizione dell'allegato " + allegato.getNomeFile());
	                }
	                if (Utils.e(allegato.getNomeFile())) {
	                    throw new CrossServicesException("Non e' stato specificato il nome del file dell'allegato ");
	                }
	                if (Utils.e(allegato.getMimeType())) {
	                    throw new CrossServicesException("Non e' stato specificato il mime type dell'allegato");
	                }
	                it.wego.cross.dto.AllegatoDTO allegatoDTO = new it.wego.cross.dto.AllegatoDTO();
	                allegatoDTO.setDescrizione(allegato.getDescrizione());
	                if (allegato.getFile() != null) {
	                    allegatoDTO.setFileContent(Base64.decodeBase64(allegato.getFile()));
	                }
	                allegatoDTO.setNomeFile(counter + "_" + allegato.getNomeFile());
	                allegatoDTO.setTipoFile(allegato.getMimeType());
	                allegatoDTO.setIdFileEsterno(allegato.getIdFileEsterno());
	                allegatoDTO.setPrincipale(allegato.getPrincipale());
	                comunicazione.getAllegatiManuali().add(allegatoDTO);
	                if (!contieneAllegatoPrincipale && !Utils.e(allegato.getPrincipale()) && allegato.getPrincipale()) {
	                    contieneAllegatoPrincipale = true;
	                }
	                counter++;
	            }
            }
        }
        if (pe.getFlgProtocollazione() != null && pe.getFlgProtocollazione().equalsIgnoreCase("S")) {
            if (!contieneAllegatoPrincipale) {
                throw new CrossServicesException("Non e' stato indicato l'allegato principale per la protocollazione");
            }
        }
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setOggetto(evento.getComunicazione().getOggetto());
        eventoDTO.setContenuto(evento.getComunicazione().getContenuto());
        eventoDTO.setDescrizione(evento.getDescrizioneEvento());
        eventoDTO.setOggetto(evento.getDescrizioneEvento());
        //eventoDTO.setNote(evento.getDescrizioneEvento());
        comunicazione.setEvento(eventoDTO);
        if (!Utils.e(evento.getNumeroProtocollo())) {
            comunicazione.setNumeroDiProtocollo(evento.getNumeroProtocollo());
            comunicazione.setDataDiProtocollo(evento.getDataProtocollo());
        }
        return comunicazione;
    }

    private List<Evento> getEventiRegistrati(Pratica pratica) {
        List<PraticheEventi> eventiPratica = pratica.getPraticheEventiList();
        List<Evento> eventi = new ArrayList<Evento>();
        if (eventiPratica != null && !eventiPratica.isEmpty()) {
            for (PraticheEventi evt : eventiPratica) {
                Evento evento = serializeEvento(evt);
                if(evento.getDescrizioneEvento().contains("Ricezione pratica") || evento.getDescrizioneEvento().contains("(amm)") || evento.getDescrizioneEvento().contains("(proc)"))
                	eventi.add(evento);
            }
        }
        return eventi;
    }

    private Evento serializeEvento(PraticheEventi evt) {
        Evento e = new Evento();
        e.setIdEvento(evt.getIdPraticaEvento());
        e.setIdPratica(evt.getIdPratica().getIdPratica());
        String identificativoPratica = evt.getIdPratica().getCodRegistro() + "/" + evt.getIdPratica().getAnnoRiferimento() + "/" + evt.getIdPratica().getProtocollo();
        e.setIdentificativoPratica(identificativoPratica);
        e.setNumeroProtocollo(evt.getProtocollo());
        e.setDataProtocollo(evt.getDataProtocollo());
        e.setCodiceEvento(evt.getIdEvento().getCodEvento());
        e.setDescrizioneEvento(evt.getIdEvento().getDesEvento() + "-" + evt.getOggetto());
        e.setCodiceEnte(evt.getIdPratica().getIdProcEnte().getIdEnte().getCodEnte());
        e.setDataEvento(evt.getDataEvento());
        Allegati allegati = getAllegati(evt);
        e.setAllegati(allegati);
        Soggetti soggetti = new Soggetti();
        List<Soggetto> soggettiList = new ArrayList<Soggetto>();
        List<PraticheEventiAnagrafiche> pea = evt.getPraticheEventiAnagraficheList();
        if (pea != null && !pea.isEmpty()) {
            for (PraticheEventiAnagrafiche p : pea) {
                Soggetto soggetto = new Soggetto();
                Anagrafica a = p.getAnagrafica();
                soggetto.setCodiceFiscale(a.getCodiceFiscale());
                soggetto.setTipoSoggetto(TipoSoggetto.ANAGRAFICA);
                soggettiList.add(soggetto);
            }
        }
        List<Enti> entiEvento = evt.getEntiList();
        if (entiEvento != null && !entiEvento.isEmpty()) {
            for (Enti ente : entiEvento) {
                Soggetto soggetto = new Soggetto();
                soggetto.setCodiceFiscale(ente.getCodiceFiscale());
                soggetto.setCodice(ente.getCodEnte());
                soggetto.setTipoSoggetto(TipoSoggetto.ENTE);
                soggettiList.add(soggetto);
            }
        }
        soggetti.setSoggetti(soggettiList);
        return e;
    }

    private Allegati getAllegati(PraticheEventi evt) {
        Allegati allegati = new Allegati();
        List<PraticheEventiAllegati> allegatiEvento = evt.getPraticheEventiAllegatiList();
        List<Allegato> allegatiList = new ArrayList<Allegato>();
        if (allegatiEvento != null && !allegatiEvento.isEmpty()) {
            for (PraticheEventiAllegati pea : allegatiEvento) {
                it.wego.cross.entity.Allegati allegato = pea.getAllegati();
                Allegato a = new Allegato();
                a.setDescrizione(allegato.getDescrizione());
                a.setIdFileEsterno(allegato.getIdFileEsterno());
                if (allegato.getFile() != null) {
                    a.setFile(Base64.encodeBase64String(allegato.getFile()));
                }
                a.setMimeType(allegato.getTipoFile());
                a.setNomeFile(allegato.getNomeFile());
                a.setPrincipale(pea.getFlgIsPrincipale().equalsIgnoreCase("S"));
                allegatiList.add(a);
            }
        }
        allegati.setAllegati(allegatiList);
        return allegati;
    }
}
