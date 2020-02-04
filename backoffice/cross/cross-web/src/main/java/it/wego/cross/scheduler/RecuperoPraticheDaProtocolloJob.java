/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang.time.DateUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import it.wego.cross.actions.ErroriAction;
import it.wego.cross.actions.PraticheAction;
import it.wego.cross.actions.SchedulerAction;
import it.wego.cross.constants.AnaTipiEvento;
import it.wego.cross.constants.ConfigurationConstants;
import it.wego.cross.constants.Constants;
import it.wego.cross.constants.Error;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.ProcessiDao;
import it.wego.cross.dto.AllegatoRicezioneDTO;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheEventiAllegati;
import it.wego.cross.entity.PraticheEventiAnagrafiche;
import it.wego.cross.entity.PraticheProtocollo;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.events.comunicazione.bean.ComunicazioneBean;
import it.wego.cross.plugins.protocollo.GestioneProtocollo;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloResponse;
import it.wego.cross.plugins.protocollo.beans.FilterProtocollo;
import it.wego.cross.plugins.protocollo.beans.SoggettoProtocollo;
import it.wego.cross.serializer.PraticheSerializer;
import it.wego.cross.serializer.ProtocolloSerializer;
import it.wego.cross.service.AllegatiService;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.PluginService;
import it.wego.cross.service.PraticheProtocolloService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.PraticaUtils;
import it.wego.cross.utils.Utils;

/**
 *
 * @author giuseppe
 */
public class RecuperoPraticheDaProtocolloJob implements Job {

    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private PluginService pluginService;
    @Autowired
    private PraticheProtocolloService praticheProtocolloService;
    @Autowired
    private AllegatiService allegatiService;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private EntiService entiService;
    @Autowired
    private PraticheSerializer praticheSerializer;
    @Autowired
    private SchedulerAction schedulerAction;
    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private PraticheAction praticheAction;
    @Autowired
    private ErroriAction erroriAction;
    @Autowired
    private ProcessiDao processiDao;
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Log.SCHEDULER.info("Starting scheduler ...");
        List<Integer> entiProtocollo = configurationService.findIdEntiFromConfiguration(ConfigurationConstants.SINCRONIZZA_PROTOCOLLO);
        if (entiProtocollo != null && !entiProtocollo.isEmpty()) {
            for (Integer idEnte : entiProtocollo) {
                eseguiSincronizzazione(idEnte);
            }
        } else {
            eseguiSincronizzazione(null);
        }
        /* Requisito R9 */
        protocollaPraticheSenzaProtocollo();
        /* Fine Requisito R9 */
    }

    private void sincronizzaPratiche(@Nullable Integer idEnte) throws JobExecutionException {
        try {
            //Cerco sempre le pratiche e i documenti degli ultimi 5 giorni
            String giorniSincronizzazione = configurationService.getCachedConfiguration(ConfigurationConstants.GIORNI_SINCRONIZZA_PROTOCOLLO, idEnte, null);
            Integer giorni = Integer.valueOf(giorniSincronizzazione);
            Date startDate = DateUtils.addDays(new Date(), -giorni);
            Date endDate = new Date();
            Log.SCHEDULER.info("Data inizio: " + Utils.dateItalianFormat(startDate));
            Log.SCHEDULER.info("Data fine: " + Utils.dateItalianFormat(endDate));
            for (int i = 0; i <= giorni; i++) {
                Date dateToSearch = Utils.addDaysToDate(startDate, i);
                Log.SCHEDULER.info("Scarico le pratiche ed i documenti per il giorno " + Utils.dateItalianFormat(dateToSearch));
                FilterProtocollo filter = new FilterProtocollo();
                filter.setDataDocumentoDa(dateToSearch);
                filter.setDataDocumentoA(dateToSearch);
                String tipoDocumentoPratica = configurationService.getCachedPluginConfiguration("protocollo.genuit.documento.pratica", idEnte, null);
                //Salvo le pratiche in arrivo
                filter.setTipoDocumento(tipoDocumentoPratica);
                Log.SCHEDULER.info("Scarico tutti i documenti di tipo " + tipoDocumentoPratica);
                List<DocumentoProtocolloResponse> pratiche = getDocumenti(filter);
                Log.SCHEDULER.info("Ho recuperato le pratiche dal protocollo, le inserisco...");
                salvaPratiche(pratiche, idEnte);
                Log.SCHEDULER.info("Inserimento delle pratiche terminato");
                //Salvo i documenti in arrivo: DOCARR
                String tipoDocumentoInArrivo = configurationService.getCachedPluginConfiguration("protocollo.genuit.documento.arrivo", idEnte, null);
                filter.setTipoDocumento(tipoDocumentoInArrivo);
                Log.SCHEDULER.info("Scarico tutti i documenti di tipo " + tipoDocumentoInArrivo);
                pratiche = getDocumenti(filter);
                Log.SCHEDULER.info("Ho recuperato i documenti dal protocollo, li inserisco...");
                salvaPratiche(pratiche, idEnte);
                Log.SCHEDULER.info("Inserimento dei documenti terminato");
            }
        } catch (Exception ex) {
            Log.SCHEDULER.error("Si è verificato un errore in fase di interrogazione del protocollo", ex);
            //Esco dallo scheduler
            throw new JobExecutionException("Errore interrogando il protocollo", ex);
        }
    }

    private List<DocumentoProtocolloResponse> getDocumenti(FilterProtocollo filter) throws Exception {
        List<Integer> entiProtocollo = configurationService.findIdEntiFromConfiguration(SessionConstants.PROTOCOLLO_PLUGIN_ID);
        if (entiProtocollo != null && !entiProtocollo.isEmpty()) {
            List<DocumentoProtocolloResponse> documenti = new ArrayList<DocumentoProtocolloResponse>();
            for (Integer idEnte : entiProtocollo) {
                Enti ente = entiService.findByIdEnte(idEnte);
                Log.SCHEDULER.info("Recupero il plugin di gestione protocollo");
                GestioneProtocollo gp = pluginService.getGestioneProtocollo(idEnte, null);
                Log.SCHEDULER.info("Sto contattando il webservice del protocollo ...");
                List<DocumentoProtocolloResponse> documentiProtocollo = gp.queryProtocollo(filter, ente, null);
                Log.SCHEDULER.info("Operazione terminata correttamente. Trovati " + documenti.size() + " documenti.");
                if (documentiProtocollo != null && !documentiProtocollo.isEmpty()) {
                    for (DocumentoProtocolloResponse doc : documentiProtocollo) {
                        if (!documenti.contains(doc)) {
                            documenti.add(doc);
                        }
                    }
                }
            }
            return documenti;
        } else {
            //Ho una sola configurazione per il protocollo
            Log.SCHEDULER.info("Recupero il plugin di gestione protocollo");
            GestioneProtocollo gp = pluginService.getGestioneProtocollo(null, null);
            Log.SCHEDULER.info("Sto contattando il webservice del protocollo ...");
            List<DocumentoProtocolloResponse> documenti = gp.queryProtocollo(filter, null, null);
            Log.SCHEDULER.info("Operazione terminata correttamente. Trovati " + documenti.size() + " documenti.");
            return documenti;
        }

    }

    private void salvaPratiche(List<DocumentoProtocolloResponse> documenti, @Nullable Integer idEnte) throws JobExecutionException {
        try {
            Log.SCHEDULER.info("Inizio la procedura di verifica e salvataggio dei documenti");
            if (documenti != null && documenti.size() > 0) {
                for (DocumentoProtocolloResponse doc : documenti) {
                    if (!isPresent(doc, idEnte)) {
                        //Inserisco il record in db
                        Log.SCHEDULER.info("Documento non presente, procedo con l'inserimento.");
                        PraticheProtocollo pratica = praticheSerializer.serializzaPratica(doc, Constants.CARICAMENTO_MODALITA_AUTOMATICA, idEnte);
                        schedulerAction.salva(pratica);
                    }
                }
            }
        } catch (Exception ex) {
            Log.SCHEDULER.error("Si è verificato un errore cercando di popolare il database", ex);
            //Esco dallo scheduler
            throw new JobExecutionException("Si è verificato un errore cercando di popolare il database", ex);
        }
    }

    private boolean isPresent(DocumentoProtocolloResponse doc, @Nullable Integer idEnte) throws Exception {
        boolean isPresent = true;
        Log.SCHEDULER.info("Cerco nelle pratiche da protocollo quella con codice documento " + doc.getAllegatoOriginale().getIdEsterno());
        List<PraticheProtocollo> documentiProtocollo = praticheProtocolloService.findByCodDocumento(doc.getAllegatoOriginale().getIdEsterno());
        if (documentiProtocollo != null && !documentiProtocollo.isEmpty()) {
            //Pratica già inserita in banca dati
            Log.SCHEDULER.info("Documento '" + doc.getAllegatoOriginale().getIdEsterno() + "' già presente in banca dati con id '" + documentiProtocollo.get(0).getIdProtocollo() + "'. Skip");
        } else {
            Log.SCHEDULER.info("Cerco tra gli allegati il documento con id " + doc.getAllegatoOriginale().getIdEsterno());
            Log.SCHEDULER.info("Cerco negli allegato, l'allegato con id file esterno " + doc.getAllegatoOriginale().getIdEsterno());
            Allegati allegato = allegatiService.findAllegatoByIdFileEsterno(doc.getAllegatoOriginale().getIdEsterno());
            if (allegato != null) {
                //Il documento l'ho già inserito in banca dati, quindi la Pratica già inserita in banca dati
                Log.SCHEDULER.info("Documento già presente in banca dati. Skip...");
            } else {
                String tipoDocumentoPratica = configurationService.getCachedPluginConfiguration("protocollo.genuit.documento.pratica", idEnte, null);
                if (doc.getTipoDocumento().equalsIgnoreCase(tipoDocumentoPratica)) {
                    Log.SCHEDULER.info("Verifico se il documento è una pratica");
                    Integer annoFascicolo = Integer.valueOf(doc.getAnnoFascicolo());
                    Log.SCHEDULER.info("Cerco una pratica con i seguenti parametri: ");
                    Log.SCHEDULER.info("- Registro: " + doc.getCodRegistro());
                    Log.SCHEDULER.info("- Fascicolo: " + doc.getFascicolo());
                    Log.SCHEDULER.info("- Protocollo: " + doc.getNumeroProtocollo());
                    Log.SCHEDULER.info("- Anno: " + annoFascicolo);
                    Pratica pratica = praticheService.findByRegistroFascicoloProtocolloAnno(doc.getCodRegistro(), doc.getFascicolo(), doc.getNumeroProtocollo(), annoFascicolo);
                    if (pratica == null) {
                        Log.SCHEDULER.info("Trovata la pratica per il documento con id='" + doc.getIdDocumento() + "'");
                        isPresent = false;
                    } else {
                        Log.SCHEDULER.info("Cerco una pratica da protocollo con i seguenti parametri: ");
                        Log.SCHEDULER.info("- Registro: " + doc.getCodRegistro());
                        Log.SCHEDULER.info("- Fascicolo: " + doc.getFascicolo());
                        Log.SCHEDULER.info("- Anno: " + annoFascicolo);
                        Log.SCHEDULER.info("- Tipo documento: " + tipoDocumentoPratica);
                        PraticheProtocollo praticaProtocollo = praticheProtocolloService.findByRegistroFascicoloAnnoTipo(doc.getCodRegistro(), doc.getFascicolo(), annoFascicolo, tipoDocumentoPratica);
                        if (praticaProtocollo != null) {
                            Log.SCHEDULER.info("Trovata una pratica da protocollo a cui collegare il documento");
                            isPresent = false;
                        } else {
                            Log.SCHEDULER.info("Nessuna pratica trovata per il documento cercato.");
                        }
                    }
                } else {
                    Log.SCHEDULER.info("Verifico se il documento è una comunicazione in ingresso");
                    Integer annoProtocollo = Integer.valueOf(doc.getAnnoFascicolo());
                    Log.SCHEDULER.info("Cerco una pratica con i seguenti parametri: ");
                    Log.SCHEDULER.info("- Registro: " + doc.getCodRegistro());
                    Log.SCHEDULER.info("- Fascicolo: " + doc.getFascicolo());
                    Log.SCHEDULER.info("- Anno: " + annoProtocollo);
                    List<Pratica> pratica = praticheService.findByRegistroFascicoloAnno(doc.getCodRegistro(), doc.getFascicolo(), annoProtocollo);
                    if (pratica != null && !pratica.isEmpty()) {
                        Log.SCHEDULER.info("Trovata la pratica con fascicolo='" + doc.getFascicolo() + "'");
                        isPresent = false;
                    } else {
                        Log.SCHEDULER.info("Non ci sono pratiche con fascicolo='" + doc.getFascicolo() + "'. Non inserisco il documento");
                    }
                }
            }
        }
        Log.SCHEDULER.info("Il documento e' presente? " + isPresent);
        return isPresent;
    }
    
    
    /* Requisito R9 */
    private void protocollaPraticheSenzaProtocollo () throws JobExecutionException {
    	
    	Log.SCHEDULER.info("Recupero elenco delle pratiche non protocollate");
    	
    	//List<Pratica> listPratiche = praticheService.findByRegistroFascicoloAnno("", "", 0);
    	List<Pratica> listPratiche = praticheService.findPraticheDaRiprotocollare("");
    	
    	Log.SCHEDULER.info("Numero di pratiche da protocollare: "+listPratiche.size());
    	
    	for (int i =0; i<listPratiche.size(); i++) {
            
			
	    	Log.SCHEDULER.info("Recupero la lista degli eventi");
            
            List<PraticheEventi> listPraticaEvento = praticaDao.findAllEventsForPratica(listPratiche.get(i).getIdPratica());
            
            Integer idPraticaEvento = null;
            for(int e=0; e<listPraticaEvento.size(); e++) {
            	if("Ricezione pratica".equalsIgnoreCase(listPraticaEvento.get(e).getDescrizioneEvento())) {
            		idPraticaEvento = listPraticaEvento.get(e).getIdPraticaEvento();
            		break;
            	}
            }
            PraticheEventi praticaEvento = praticaDao.getDettaglioPraticaEvento(idPraticaEvento);

            Log.SCHEDULER.info("Recupero le anagrafiche associate alla paratica");
            
            List<SoggettoProtocollo> soggettiProtocollo = new ArrayList<SoggettoProtocollo>();
            for (PraticheEventiAnagrafiche praticaEventoAnagrafica : praticaEvento.getPraticheEventiAnagraficheList()) {
                soggettiProtocollo.add(ProtocolloSerializer.serialize(praticaEventoAnagrafica.getAnagrafica()));
            }
            
            it.wego.cross.xml.Pratica praticaXml = new it.wego.cross.xml.Pratica();
			try {
				praticaXml = PraticaUtils.getPraticaFromXML(new String(listPratiche.get(i).getIdStaging().getXmlPratica()));
			} catch (Exception e) {
				Log.SCHEDULER.error("Errore nel recupero del xml", e);
				throw new JobExecutionException("Errore update pratica", e);
			}
			
            
            List<String> destinatariMail = getDestinatariEmail(praticaXml);
            
            ProcessiEventi eventoProcesso = processiDao.findProcessiEventiByCodEventoIdProcesso(AnaTipiEvento.RICEZIONE_PRATICA, listPratiche.get(i).getIdProcesso().getIdProcesso());
            
            Log.SCHEDULER.info("Recupero la lista degli allegati");
            List<AllegatoRicezioneDTO> allegati = new ArrayList<AllegatoRicezioneDTO>();
            for (PraticheEventiAllegati pea : praticaEvento.getPraticheEventiAllegatiList()) {
                Allegati a = null;
				try {
					a = pea.getAllegati();
				} catch (Exception ex) {
		            Log.SCHEDULER.error("Si è verificato un errore recuperando gli allegati", ex);
		            //Esco dallo scheduler
		            throw new JobExecutionException("Errore recupero allegati", ex);
				}
                
				AllegatoRicezioneDTO dto = new AllegatoRicezioneDTO();
				dto.setAllegato(a);
				dto.setModelloDomanda("N");
                //Lo imposto come da inviare, in quanto è una ricevuta dell'evento
				dto.setSend(Boolean.TRUE);
                allegati.add(dto);
            }
            
            try {
				ComunicazioneBean cb = praticheAction.inserisciEventoRicezione(praticaXml, listPratiche.get(i), eventoProcesso, destinatariMail, allegati);
	            praticheService.startCommunicationProcess(listPratiche.get(i), praticaEvento, cb);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.SCHEDULER.error("Errore nella protocollazione della pratica", e);
				throw new JobExecutionException("Errore update pratica", e);
			}
    	}
    }
    
    private List<String> getDestinatariEmail(it.wego.cross.xml.Pratica praticaCross) {
        List<String> email = new ArrayList<String>();
        try {
            it.wego.cross.xml.Recapito notifica = praticaCross.getNotifica();

            if (notifica != null) {
                if (!Utils.e(notifica.getPec())) {
                    email.add(notifica.getPec());
                    return email;
                } else if (!Utils.e(notifica.getEmail())) {
                    email.add(notifica.getEmail());
                    return email;
                }
            }
            if (praticaCross.getAnagrafiche() != null) {
                for (it.wego.cross.xml.Anagrafiche anagrafiche : praticaCross.getAnagrafiche()) {
                    //Verifica se è il richiedente
                    if (anagrafiche.getCodTipoRuolo().equalsIgnoreCase("R") || anagrafiche.getCodTipoRuolo().equalsIgnoreCase("B")) {
                        it.wego.cross.xml.Recapiti recapitiXml = anagrafiche.getAnagrafica().getRecapiti();
                        if (recapitiXml != null && recapitiXml.getRecapito() != null && !recapitiXml.getRecapito().isEmpty()) {
                            for (it.wego.cross.xml.Recapito r : recapitiXml.getRecapito()) {
                                if (!Utils.e(r.getPec())) {
                                    email.add(r.getPec());
                                } else if (!Utils.e(r.getEmail())) {
                                    email.add(r.getEmail());
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            //Non bloccante
            try {
                String err = "Errore cercando i destinatari della comunicazione";
                ErroreDTO errore = erroriAction.getError(Error.ERRORE_PARSING_PRATICA, err, ex, null, null);
                erroriAction.saveError(errore);
            } catch (Exception ex1) {
                Log.APP.error("getDestinatariEmail", ex1);
            }
        }
        return email;
    }
    
    /* Fine Requisito R9 */
    

    	

    private void eseguiSincronizzazione(Integer idEnte) throws JobExecutionException {
        String sincronizzaProtocolloValue = configurationService.getCachedConfiguration(ConfigurationConstants.SINCRONIZZA_PROTOCOLLO, idEnte, null);
        Log.SCHEDULER.info("Eseguo l'operazione di scheduling? " + sincronizzaProtocolloValue);
        boolean sincronizzaProtocollo = sincronizzaProtocolloValue != null && sincronizzaProtocolloValue.equalsIgnoreCase("true");
        if (sincronizzaProtocollo) {
            sincronizzaPratiche(idEnte);
            Log.SCHEDULER.info("End of scheduled operations");
        }
    }
}
