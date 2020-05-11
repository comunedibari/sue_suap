/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.actions;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import it.wego.cross.beans.EventoBean;
import it.wego.cross.beans.MailContentBean;
import it.wego.cross.constants.AnaTipiEvento;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.constants.StatiEmail;
import it.wego.cross.constants.Workflow;
import it.wego.cross.dao.MailDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Email;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.EventiTemplate;
import it.wego.cross.entity.LkStatiMail;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheEventiAllegati;
import it.wego.cross.entity.PraticheEventiAnagrafiche;
import it.wego.cross.entity.PraticheProtocollo;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Utente;
import it.wego.cross.events.AbstractEvent;
import it.wego.cross.events.comunicazione.bean.ComunicazioneBean;
import it.wego.cross.factory.EventCreator;
import it.wego.cross.mail.EmailConfig;
import it.wego.cross.mail.GestoreMail;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.plugins.protocollo.GestioneProtocollo;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloRequest;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloResponse;
import it.wego.cross.plugins.protocollo.beans.SoggettoProtocollo;
import it.wego.cross.serializer.AllegatiSerializer;
import it.wego.cross.serializer.PraticheSerializer;
import it.wego.cross.serializer.ProtocolloSerializer;
import it.wego.cross.service.ClearService;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.EventiService;
import it.wego.cross.service.MailService;
import it.wego.cross.service.PluginService;
import it.wego.cross.service.PraticheProtocolloService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.UtentiService;
import it.wego.cross.service.WorkFlowService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.mail.internet.MimeMessage;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author gabriele
 */
@Component
public class WorkflowAction {

    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private EventiService eventiService;
    @Autowired
    private ErroriAction erroriAction;
    @Autowired
    private PluginService pluginService;
    @Autowired
    private MailService mailService;
    @Autowired
    private GestoreMail gestoreMail;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ConfigurationService configuration;
    @Autowired
    private ClearService clearService;
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private MailDao emailDao;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private PraticheProtocolloService praticheProtocolloService;
    @Autowired
    private ApplicationContext appContext;
    private static final List<String> CODICI_ERRORE_PRATICA_CLEAR = Lists.newArrayList("105", "20", "23", "24", "25", "26", "27",
            "28", "29", "30", "32", "33", "106", "107", "111", "206", "207", "1", "2", "209", "205", "34", "109", "110", "112", "208");
    private static final List<String> CODICI_ERRORE_EVENTO_CLEAR = Lists.newArrayList("400", "401", "402", "403", "405", "409", "410",
            "412", "404", "413", "414");

    @Transactional
    public void protocollaPraticaEvento(DelegateExecution execution) throws Exception {
        Log.WORKFLOW.info("Metodo protocollaPraticaEvento invocato.");

        Map<String, Object> variables = execution.getVariables();
        Integer idPraticaEvento = (Integer) variables.get(Workflow.ID_PRATICA_EVENTO);
        PraticheEventi praticaEvento = praticaDao.getDettaglioPraticaEvento(idPraticaEvento);

        Log.WORKFLOW.info("idPraticaEvento da protocollare: " + praticaEvento.getIdPraticaEvento());

        ProcessiEventi eventoProcesso = praticaEvento.getIdEvento();
        Pratica pratica = praticaEvento.getIdPratica();
        Enti ente = pratica.getIdProcEnte().getIdEnte();
        Integer idComune = null;
        if (pratica.getIdComune() != null) {
            idComune = pratica.getIdComune().getIdComune();
        }

        //L'evento richiede protocollazione
        if (eventoProcesso.getFlgProtocollazione().equalsIgnoreCase("S")) {
            //L'evento non ha una segnatura di protocollo
            if (Utils.e(praticaEvento.getProtocollo())) {
                GestioneProtocollo gestioneProtocollo = pluginService.getGestioneProtocollo(ente.getIdEnte(), idComune);
                try {
                    List<Allegato> allegati = new ArrayList<Allegato>();
                    List<Allegati> allegatiEntity = new ArrayList<Allegati>();

                    for (PraticheEventiAllegati pea : praticaEvento.getPraticheEventiAllegatiList()) {
                        Allegato a = AllegatiSerializer.serializeAllegato(pea.getAllegati());
                        a.setFileOrigine(pea.getFlgIsPrincipale().equalsIgnoreCase("S"));
                        allegati.add(a);
                        allegatiEntity.add(pea.getAllegati());
                    }

                    List<SoggettoProtocollo> soggettiProtocollo = new ArrayList<SoggettoProtocollo>();
                    for (PraticheEventiAnagrafiche praticaEventoAnagrafica : praticaEvento.getPraticheEventiAnagraficheList()) {
                        soggettiProtocollo.add(ProtocolloSerializer.serialize(praticaEventoAnagrafica.getAnagrafica()));
                    }
                    for (Enti enteLoop : praticaEvento.getEntiList()) {
                        soggettiProtocollo.add(ProtocolloSerializer.serialize(enteLoop));
                    }

                    DocumentoProtocolloRequest protocolloRequest = gestioneProtocollo.getDocumentoProtocolloDaDatabase(pratica, praticaEvento, allegati, soggettiProtocollo, String.valueOf(eventoProcesso.getVerso()), praticaEvento.getOggetto());

                    protocolloRequest.setIdPratica(pratica.getIdPratica());
                    DocumentoProtocolloResponse responseProtocollo = gestioneProtocollo.protocolla(protocolloRequest);
                    eventiService.aggiornaAllegatiConRiferimentiProtocollo(pratica, responseProtocollo, allegatiEntity, ente.getIdEnte());
                    String riferimentoProtocollo = responseProtocollo.getCodRegistro() + "/" + responseProtocollo.getAnnoProtocollo() + "/" + responseProtocollo.getNumeroProtocollo();
                    praticaEvento.setProtocollo(riferimentoProtocollo);
                    praticaEvento.setDataProtocollo(responseProtocollo.getDataProtocollo());
                    String note = praticaEvento.getNote() != null ? praticaEvento.getNote() + "<br />" : "";
                    note = note + Strings.nullToEmpty(responseProtocollo.getNote());
                    praticaEvento.setNote(note);
                    praticaDao.update(praticaEvento);
                    //Aggiorno i dati relativi al protocollo della pratica
//                    pratica.setProtocollo(responseProtocollo.getFascicolo());
//                    pratica.setCodRegistro(responseProtocollo.getCodRegistro());
//                    pratica.setDataProtocollazione(responseProtocollo.getDataCreazioneFascicolo());
//                    pratica.setAnnoRiferimento(!Strings.isNullOrEmpty(responseProtocollo.getAnnoFascicolo()) ? Integer.valueOf(responseProtocollo.getAnnoFascicolo()) : null);
//                    praticaDao.update(pratica);
                    //La protocollazione automatica è andata bene
                    execution.setVariable(Workflow.MANUAL_PROTOCOL, false);
                } catch (Exception e) {
                    Log.WORKFLOW.error("Errore nella protocollazione dell'evento", e);
                    //La protocollazione automatica è andata male
                    ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_WORKFLOW_PROTOCOLLA, "Errore nella protocollazione dell'evento", e, pratica, null);
                    //Non visibile nella console. Visibile solo tramite tasklist. Sono gli errori gestiti da workflow.
                    errore.setStatus("E");
                    Integer idError = erroriAction.saveError(errore);
                    execution.setVariable(Workflow.LAST_ERROR_MESSAGE, idError);
                    List<String> candidateUsers = getCandidateUsers(praticaEvento, true);
                    execution.setVariable(Workflow.CANDIDATE_USERS, candidateUsers);
                    execution.setVariable(Workflow.MANUAL_PROTOCOL, true);
                }
            } else {
                //L'evento ha già una segnatura di protocollo
                execution.setVariable(Workflow.MANUAL_PROTOCOL, false);
            }

        } else {
            //L'evento non richiede protocollazione
            execution.setVariable(Workflow.MANUAL_PROTOCOL, false);
        }

        Log.WORKFLOW.info("Metodo protocollaPraticaEvento terminato.");
    }

    @Transactional
    public void aggiornaProtoPraticaEvento(DelegateExecution execution) throws Exception {
        Log.WORKFLOW.debug("Metodo aggiornaProtoPraticaEvento invocato.");
        Map<String, Object> variables = execution.getVariables();
        Integer idPraticaEvento = (Integer) variables.get(Workflow.ID_PRATICA_EVENTO);
        String protocolloSegnatura = (String) variables.get("evento_protocollo_segnatura");
        String protocolloDataString = (String) variables.get("evento_protocollo_data");
        Date protocolloData = Utils.italianFormatToDate(protocolloDataString);
        PraticheEventi praticaEvento = praticaDao.getDettaglioPraticaEvento(idPraticaEvento);
        Log.WORKFLOW.debug("Evento da aggiornare: " + praticaEvento.getDescrizioneEvento() + "(" + praticaEvento.getIdPraticaEvento() + ")");
        praticaEvento.setProtocollo(protocolloSegnatura);
        praticaEvento.setDataProtocollo(protocolloData);
        Log.WORKFLOW.debug("Metodo aggiornaProtoPraticaEvento terminato.");
    }

    @Transactional
    public void aggiornaDocumentoPraticaEvento(DelegateExecution execution) throws Exception {
        Log.WORKFLOW.debug("Metodo aggiornaDocumentoPraticaEvento invocato.");

        Log.WORKFLOW.debug("Metodo aggiornaDocumentoPraticaEvento chiamato.");
    }

    @Transactional
    public void executeEvent(DelegateExecution execution) throws Exception {
        Log.WORKFLOW.debug("Metodo executeEvent invocato.");
        Map<String, Object> variables = execution.getVariables();
        Integer idPratica = (Integer) variables.get(Workflow.ID_PRATICA);
        Pratica pratica = praticheService.getPratica(idPratica);
        Integer idPraticaEvento = (Integer) variables.get(Workflow.ID_PRATICA_EVENTO);
        PraticheEventi praticaEvento = praticaDao.getDettaglioPraticaEvento(idPraticaEvento);
        try {
            String eventoBeanJson = (String) variables.get(Workflow.EVENTO_BEAN);
            String eventoClassName = (String) variables.get(Workflow.EVENTO_BEAN_CLASS);
            Class<?> eventoClass = Class.forName(eventoClassName);
            EventoBean eb = (EventoBean) new Gson().fromJson(eventoBeanJson, eventoClass);
            //Se arrivo da una pratica-protocollo devo aggiornare il relativo record in banca dati e valorizzare i dati relativi agli estremi di protocollazione, se non mi sono stati passati
            if (eb.getIdPraticaProtocollo() != null) {
                PraticheProtocollo praticaProtocollo = praticheProtocolloService.findPraticaProtocolloById(eb.getIdPraticaProtocollo());
                //Aggiorno le informazioni relative alla pratica-protocollo. Mi serve per non rendere più visibile il documento dalla sezione Comunicazioni in ingresso
                praticaProtocollo.setIdPratica(pratica);
                praticaProtocollo.setDataPresaInCaricoCross(new Date());
                Utente utente = utentiService.findUtenteByIdUtente(eb.getIdUtente());
                praticaProtocollo.setIdUtentePresaInCarico(utente);
                praticheProtocolloService.aggiorna(praticaProtocollo);
            }
            ProcessiEventi processoEvento = praticheService.findProcessiEventi(eb.getIdEventoProcesso());
            String funzioneApplicativaEvento = null;
            if(processoEvento != null)
            	funzioneApplicativaEvento = processoEvento.getFunzioneApplicativa();
            if (eb.getDataEvento() == null) {
                eb.setDataEvento(Utils.now());
            }
            AbstractEvent eventoRepo;
            if (funzioneApplicativaEvento == null) {
                eventoRepo = (AbstractEvent) appContext.getBean("nop_event");
            } else {
                try {
                    eventoRepo = (AbstractEvent) appContext.getBean(funzioneApplicativaEvento);
                } catch (BeansException e) {
                    Log.APP.warn("ATTENZIONE NON HO TROVATO LA FUNZIONE APPLICATIVA '" + funzioneApplicativaEvento + "' USO QUELLA DI DEFAULT");
                    eventoRepo = (AbstractEvent) appContext.getBean("nop_event");
                }
            }
            eventoRepo.execute(eb);
            execution.setVariable(Workflow.EVENTO_BEAN, new Gson().toJson(eb));
            execution.setVariable(Workflow.EXECUTE_EVENT_FAILED, false);
            Log.WORKFLOW.debug("Metodo executeEvent chiamato.");
        } catch (Exception ex) {
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_WORKFLOW_EXECUTE_EVENT, "Errore nella esecuzione dell'evento", ex, pratica, null);
            errore.setStatus("E");
            Integer idError = erroriAction.saveError(errore);
            execution.setVariable(Workflow.LAST_ERROR_MESSAGE, idError);
            List<String> candidateUsers = getCandidateUsers(praticaEvento, true);
            execution.setVariable(Workflow.CANDIDATE_USERS, candidateUsers);
            execution.setVariable(Workflow.EXECUTE_EVENT_FAILED, true);
            Log.WORKFLOW.debug("Metodo executeEvent chiamato.");
        }
    }

    @Transactional
    public void createAutomaticTasks(DelegateExecution execution) throws Exception {
        Log.WORKFLOW.debug("Metodo createAutomaticTasks invocato.");
        Map<String, Object> variables = execution.getVariables();
        Integer idPratica = (Integer) variables.get(Workflow.ID_PRATICA);
        Integer idPraticaEvento = (Integer) variables.get(Workflow.ID_PRATICA_EVENTO);
        PraticheEventi praticaEvento = praticaDao.getDettaglioPraticaEvento(idPraticaEvento);
        if (idPratica != null) {
            try {
                List<Integer> automaticTasks = new ArrayList<Integer>();
                for (ProcessiEventi processoEvento : workFlowService.findAvailableEvents(idPratica)) {
                    if ("S".equalsIgnoreCase(processoEvento.getFlgAutomatico())) {
                        automaticTasks.add(processoEvento.getIdEvento());
                    }
                }
                execution.setVariable(Workflow.AUTOMATIC_EVENT_CREATION_FAILED, false);
                execution.setVariable(Workflow.AUTOMATIC_EVENTS_LIST, automaticTasks);
            } catch (Exception ex) {
                Pratica pratica = praticaDao.findPratica(idPratica);
                ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_WORKFLOW_AUTOMATIC_EVENT, "Errore nella creazione dell'evento automatico", ex, pratica, null);
                errore.setStatus("E");
                Integer idError = erroriAction.saveError(errore);
                List<String> candidateUsers = getCandidateUsers(praticaEvento, true);
                execution.setVariable(Workflow.CANDIDATE_USERS, candidateUsers);
                execution.setVariable(Workflow.LAST_ERROR_MESSAGE, idError);
                execution.setVariable(Workflow.AUTOMATIC_EVENT_CREATION_FAILED, true);
            }
        } else {
            Log.WORKFLOW.warn("Metodo createAutomaticTasks. Non ho trovato ID pratica");
            execution.setVariable(Workflow.AUTOMATIC_EVENT_CREATION_FAILED, false);
        }
        Log.WORKFLOW.debug("Metodo createAutomaticTasks chiamato.");
    }

    @Transactional
    public void runAutomaticTasks(DelegateExecution execution) throws Exception {
        Log.WORKFLOW.debug("Metodo runAutomaticTasks invocato.");
        Map<String, Object> variables = execution.getVariables();
        List<Integer> automaticEvents = (List<Integer>) variables.get(Workflow.AUTOMATIC_EVENTS_LIST);
        Integer idPratica = (Integer) variables.get(Workflow.ID_PRATICA);
        Pratica pratica = praticheService.getPratica(idPratica);
        Integer idEventoOrigine = (Integer) variables.get(Workflow.ID_PRATICA_EVENTO);
        PraticheEventi praticaEvento = praticaDao.getDettaglioPraticaEvento(idEventoOrigine);
        Integer idUtente = null;
        Utente utenteEvento = null;
        if (praticaEvento.getIdUtente() != null) {
            idUtente = praticaEvento.getIdUtente().getIdUtente();
            utenteEvento = praticaEvento.getIdUtente();
        }
        try {
            if (automaticEvents != null && !automaticEvents.isEmpty()) {
                String eventoBeanJson = (String) variables.get(Workflow.EVENTO_BEAN);
                EventoBean eventoBean = new Gson().fromJson(eventoBeanJson, EventoBean.class);
                for (Integer idProcessoEvento : automaticEvents) {
                    ProcessiEventi evento = eventiService.findProcessiEventiById(idProcessoEvento);
//                    EventoBean eventoBeanAutomatico = EventCreator.createEventoBean(praticaEvento.getIdEvento());
                    String eventoClassName = (String) variables.get(Workflow.EVENTO_BEAN_CLASS);
                    Class<?> eventoClass = Class.forName(eventoClassName);
                    EventoBean eventoBeanAutomatico = (EventoBean) eventoClass.newInstance();
                    eventoBeanAutomatico.setEventoAutomatico(Boolean.TRUE);
                    eventoBeanAutomatico.setIdPratica(pratica.getIdPratica());
                    eventoBeanAutomatico.setIdUtente(idUtente);
                    eventoBeanAutomatico.setIdEventoProcesso(idProcessoEvento);
                    if (evento.getEventiTemplateList() != null && !evento.getEventiTemplateList().isEmpty()) {
                        for (EventiTemplate eventoTemplate : evento.getEventiTemplateList()) {
                            byte[] allegatoContent = workFlowService.generaDocumento(eventoTemplate, pratica, utenteEvento);
                            Allegato allegato = new Allegato();
                            allegato.setDescrizione(eventoTemplate.getIdTemplate().getDescrizione());
                            allegato.setFile(allegatoContent);
                            allegato.setFileOrigine(eventoBeanAutomatico.getAllegati() == null || eventoBeanAutomatico.getAllegati().isEmpty());
                            allegato.setNomeFile(eventoTemplate.getIdTemplate().getNomeFile());
                            allegato.setProtocolla(Boolean.TRUE);
                            allegato.setSpedisci(Boolean.TRUE);
                            allegato.setTipoFile(MediaType.APPLICATION_OCTET_STREAM_VALUE);
                            eventoBeanAutomatico.addAllegato(allegato);
                        }
                        if (praticaEvento.getPraticheEventiAllegatiList() != null && !praticaEvento.getPraticheEventiAllegatiList().isEmpty()) {
                            for (PraticheEventiAllegati pea : praticaEvento.getPraticheEventiAllegatiList()) {
                                Allegato allegato = AllegatiSerializer.serializeAllegato(pea.getAllegati());
                                allegato.setFileOrigine(Boolean.FALSE);
                                allegato.setProtocolla(Boolean.TRUE);
                                allegato.setSpedisci(Boolean.TRUE);
                                eventoBeanAutomatico.addAllegato(allegato);
                            }
                        }
                    } else {
                        if (praticaEvento.getPraticheEventiAllegatiList() != null && !praticaEvento.getPraticheEventiAllegatiList().isEmpty()) {
                            for (PraticheEventiAllegati pea : praticaEvento.getPraticheEventiAllegatiList()) {
                                String origine = pea.getFlgIsPrincipale();
                                Allegato allegato = AllegatiSerializer.serializeAllegato(pea.getAllegati());
                                allegato.setFileOrigine(origine.equalsIgnoreCase("S"));
                                allegato.setProtocolla(Boolean.TRUE);
                                allegato.setSpedisci(Boolean.TRUE);
                                eventoBeanAutomatico.addAllegato(allegato);
                            }
                        }
                    }
//                    if ("S".equalsIgnoreCase(evento.getFlgAllMail())) {
//                        if (praticaEvento.getPraticheEventiAllegatiList() != null && !praticaEvento.getPraticheEventiAllegatiList().isEmpty()) {
//                            List<PraticheEventiAllegati> praticheEventiAllegatiList = praticaEvento.getPraticheEventiAllegatiList();
//                            for (PraticheEventiAllegati pea : praticheEventiAllegatiList) {
//                                Allegato allegato = AllegatiSerializer.serializeAllegato(pea.getAllegati());
//                                eventoBeanAutomatico.addAllegato(allegato);
//                            }
//                        } else {
//                            for (EventiTemplate eventoTemplate : evento.getEventiTemplateList()) {
//                                Utente utenteEvento = utentiService.findUtenteByIdUtente(idUtente);
//                                byte[] allegatoContent = workFlowService.generaDocumento(eventoTemplate, pratica, utenteEvento);
//                                Allegato allegato = new Allegato();
//                                allegato.setDescrizione(eventoTemplate.getIdTemplate().getDescrizione());
//                                allegato.setFile(allegatoContent);
//                                allegato.setFileOrigine(eventoBeanAutomatico.getAllegati() == null || eventoBeanAutomatico.getAllegati().isEmpty());
//                                allegato.setNomeFile(eventoTemplate.getIdTemplate().getNomeFile());
//                                allegato.setProtocolla(Boolean.TRUE);
//                                allegato.setSpedisci(Boolean.TRUE);
//                                allegato.setTipoFile(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//                                eventoBeanAutomatico.addAllegato(allegato);
//                            }
//                        }
//                    }
                    Log.WORKFLOW.info("Eseguo il gestisci processo evento per l'evento automatizzato " + evento.getCodEvento());
                    execution.setVariable(Workflow.AUTOMATIC_EVENT_EXECUTION_FAILED, false);
                    workFlowService.gestisciProcessoEvento(eventoBeanAutomatico);
                    eventoBean.getMessages().putAll(eventoBeanAutomatico.getMessages());

                    //TODO: forse è il caso di spostarlo in gestisci processo evento
                    PraticheEventi eventoAutomaticoSuPratica = praticheService.getPraticaEvento(eventoBeanAutomatico.getIdEventoPratica());
                    Map<String, Object> variabiliEventoPratica = getVariabiliPratica(eventoBeanAutomatico, eventoAutomaticoSuPratica);
                    Log.WORKFLOW.info("Eseguo il flusso per l'evento automatico " + eventoAutomaticoSuPratica.getDescrizioneEvento());
                    runtimeService.startProcessInstanceByKey("cross_communication_process", variabiliEventoPratica);
                }
            } else {
                Log.WORKFLOW.info("Non ci sono eventi automatici da eseguire");
                execution.setVariable(Workflow.AUTOMATIC_EVENT_EXECUTION_FAILED, false);
            }
        } catch (Exception ex) {
            Log.WORKFLOW.error("Errore eseguendo il servizio runAutomaticTasks");
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_WORKFLOW_EXECUTE_EVENT, "Errore nella esecuzione dei runAutomaticTasks", ex, pratica, null);
            errore.setStatus("E");
            Integer idError = erroriAction.saveError(errore);
            execution.setVariable(Workflow.LAST_ERROR_MESSAGE, idError);
            execution.setVariable(Workflow.AUTOMATIC_EVENT_EXECUTION_FAILED, true);
            List<String> candidateUsers = getCandidateUsers(praticaEvento, true);
            execution.setVariable(Workflow.CANDIDATE_USERS, candidateUsers);
        }
        Log.WORKFLOW.debug("Metodo runAutomaticTasks chiamato.");
    }

    @Transactional
    public void startProcessClearIntegration(DelegateExecution execution) throws Exception {
        Log.WORKFLOW.debug("Metodo di sincronizzazione con Clear invocato.");
        Map<String, Object> variables = execution.getVariables();
        Integer idPraticaEvento = (Integer) variables.get(Workflow.ID_PRATICA_EVENTO);
        PraticheEventi praticaEvento = praticaDao.getDettaglioPraticaEvento(idPraticaEvento);

        if (configurationService.isClearEnabled(praticaEvento.getIdPratica().getIdProcEnte().getIdEnte().getIdEnte())) {
            Log.WS.info("L'integrazione con il sistema di monitoraggio Clear è attiva");

            try {
                String eventoAperturaCrossTokenString = configurationService.getCachedConfiguration(SessionConstants.CLEAR_CLIENT_EVENTO_APERTURA, praticaEvento.getIdPratica().getIdProcEnte().getIdEnte().getIdEnte(), praticaEvento.getIdPratica().getIdComuneInteger());
                if (StringUtils.isEmpty(eventoAperturaCrossTokenString)) {
                    throw new Exception("Impossibile trovare la proprietà " + SessionConstants.CLEAR_CLIENT_EVENTO_APERTURA + " per l'ente con id " + praticaEvento.getIdPratica().getIdProcEnte().getIdEnte().getIdEnte() + " e comune con id" + praticaEvento.getIdPratica().getIdComuneInteger());
                }

                List<String> eventoAperturaCrossTokenList = Arrays.asList(StringUtils.split(eventoAperturaCrossTokenString, ";"));
                //Se sto facendo l'evento di apertura allora creo la pratica in Clear
                if (eventoAperturaCrossTokenList.contains(praticaEvento.getIdEvento().getCodEvento())) {
                    it.wego.cross.webservices.client.clear.stubs.Log creaPraticaResponse = clearService.creaPratica(praticaEvento.getIdPratica());
                    if (creaPraticaResponse.getMessaggio().getValue().equalsIgnoreCase("0:ok")) {
                        Log.WS.info("Pratica creata correttamente");
                    } else {
                        if (creaPraticaResponse.getMessaggio().getValue().startsWith("410:")) {
                            Log.WS.warn(creaPraticaResponse.getMessaggio().getValue());
                        } else {
                            throw new Exception(creaPraticaResponse.getMessaggio().getValue());
                        }
                    }
                }

                //Creo l'evento su clear a meno che non sia l'evento di ricezione che avviene prima di quello di apertura
                if (praticheService.praticaHasPastEvent(praticaEvento.getIdPratica(), eventoAperturaCrossTokenList)) {
                    it.wego.cross.webservices.client.clear.stubs.Log creaEventoResponse = clearService.creaEvento(praticaEvento);
                    if (creaEventoResponse.getMessaggio().getValue().equalsIgnoreCase("0:ok")) {
                        Log.WS.info("Evento creato correttamente");
                    } else {
                        if (creaEventoResponse.getMessaggio().getValue().contains(":")
                                && (CODICI_ERRORE_PRATICA_CLEAR.contains(creaEventoResponse.getMessaggio().getValue().split(":")[0])
                                || CODICI_ERRORE_EVENTO_CLEAR.contains(creaEventoResponse.getMessaggio().getValue().split(":")[0]))) {
                            Log.WS.warn(creaEventoResponse.getMessaggio().getValue());
                        } else {
                            throw new Exception(creaEventoResponse.getMessaggio().getValue());
                        }
                    }
                }
                execution.setVariable(Workflow.CLEAR_INTEGRATION_FAILED, false);
            } catch (Exception e) {
                Log.WS.error("Errore nell'integrazione con Clear", e);
                Integer idPratica = (Integer) variables.get(Workflow.ID_PRATICA);
                Pratica pratica = praticaDao.findPratica(idPratica);
                Log.WORKFLOW.error("Errore eseguendo il servizio startProcessClearIntegration");
                ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_CLEAR, "Errore nell'integrazione con Clear", e, pratica, praticaEvento.getIdUtente());
                errore.setStatus("E");
                erroriAction.saveError(errore);
                execution.setVariable(Workflow.CLEAR_INTEGRATION_FAILED, false);
//                Integer idError = erroriAction.saveError(errore);
//                execution.setVariable(Workflow.LAST_ERROR_MESSAGE, idError);
//                execution.setVariable(Workflow.CLEAR_INTEGRATION_FAILED, true);
//                erroriAction.saveError(errore);
//                List<String> candidateUsers = getCandidateUsers(praticaEvento, true);
//                execution.setVariable(Workflow.CANDIDATE_USERS, candidateUsers);
            }
        } else {
            execution.setVariable(Workflow.CLEAR_INTEGRATION_FAILED, false);
        }
        Log.WORKFLOW.debug("Metodo di sincronizzazione con Clear chiamato.");
    }

    @Transactional
    public void createSottopraticheTask(DelegateExecution execution) throws Exception {
        Log.WORKFLOW.debug("Metodo di creazione sottopratiche chiamato.");
        Map<String, Object> variables = execution.getVariables();

        Log.WORKFLOW.debug("Recupero l'evento pratica.");
        Integer idPraticaEvento = (Integer) variables.get(Workflow.ID_PRATICA_EVENTO);
        PraticheEventi praticaEvento = praticaDao.getDettaglioPraticaEvento(idPraticaEvento);
        try {
            String eventoBean = (String) variables.get(Workflow.EVENTO_BEAN);
            EventoBean eb = new Gson().fromJson(eventoBean, EventoBean.class);

            Map<String, Map> eventoSottopraticheMap = (Map<String, Map>) eb.getMessages().get("EVENTO_SOTTO_PRATICA_BEANS");
            if (eventoSottopraticheMap != null) {
                for (Map.Entry<String, Map> evento : eventoSottopraticheMap.entrySet()) {
                    String idSottoPraticaEvento = evento.getKey();
                    PraticheEventi eventoSottopratica = praticheService.getPraticaEvento(Integer.valueOf(idSottoPraticaEvento));
                    Map<String, String> eventoSottopraticaMap = evento.getValue();
                    String jsonEventoSottoPratica = eventoSottopraticaMap.get("json");
                    Class<?> eventoSottoPraticaBeanClass = Class.forName(eventoSottopraticaMap.get("classname"));
                    EventoBean eventoSottoPraticaBean = (EventoBean) new Gson().fromJson(jsonEventoSottoPratica, eventoSottoPraticaBeanClass);
                    praticheService.startCommunicationProcess(eventoSottopratica.getIdPratica(), eventoSottopratica, eventoSottoPraticaBean);
                }
            }

//            for (PraticheEventi praticaEventoSottoPratica : eventiRicezionePratichefiglie) {
//                Map<String, String> eventoSottopraticaMap = eventoSottopraticheMap.get(praticaEventoSottoPratica.getIdPraticaEvento().toString());
//                String jsonEventoSottoPratica = eventoSottopraticaMap.get("json");
//                Class<?> eventoSottoPraticaBeanClass = Class.forName(eventoSottopraticaMap.get("classname"));
//                EventoBean eventoSottoPraticaBean = (EventoBean) new Gson().fromJson(jsonEventoSottoPratica, eventoSottoPraticaBeanClass);
//                praticheService.startCommunicationProcess(praticaEventoSottoPratica.getIdPratica(), praticaEventoSottoPratica, eventoSottoPraticaBean);
//            }
            execution.setVariable(Workflow.CREA_SOTTOPRATICHE_EVENT_EXECUTION_FAILED, false);
        } catch (Exception e) {
            Log.WS.error("Errore nella creazione delle sottopratiche", e);
            Integer idPratica = (Integer) variables.get(Workflow.ID_PRATICA);
            Pratica pratica = praticaDao.findPratica(idPratica);
            Log.WORKFLOW.error("Errore eseguendo il servizio runAutomaticTasks");
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_WORKFLOW_EXECUTE_CREAZIONE_SOTTOPRATICHE_EVENT, "Errore nella creazione delle sottopratiche", e, pratica, praticaEvento.getIdUtente());
            errore.setStatus("E");
            Integer idError = erroriAction.saveError(errore);
            execution.setVariable(Workflow.LAST_ERROR_MESSAGE, idError);
            execution.setVariable(Workflow.CREA_SOTTOPRATICHE_EVENT_EXECUTION_FAILED, true);
            erroriAction.saveError(errore);
            List<String> candidateUsers = getCandidateUsers(praticaEvento, true);
            execution.setVariable(Workflow.CANDIDATE_USERS, candidateUsers);
        }

        Log.WORKFLOW.debug("Metodo di creazione sottopratiche terminato.");
    }

    @Transactional
    public void startProcessSendMail(DelegateExecution execution) throws Exception {
        Map<String, Object> variables = execution.getVariables();
        Integer idPraticaEvento = (Integer) variables.get(Workflow.ID_PRATICA_EVENTO);
        PraticheEventi praticaEvento = praticaDao.getDettaglioPraticaEvento(idPraticaEvento);
        for (Email email : praticaEvento.getEmailList()) {
            if (email.getStato().getCodice().equals(StatiEmail.DA_PROTOCOLLARE) && !Strings.isNullOrEmpty(email.getEmailDestinatario())) {
                Log.WORKFLOW.info("Avvio il flusso di invio email per la mail con ID " + email.getIdEmail());
                variables.put(Workflow.ID_EMAIL, email.getIdEmail());
                Log.WORKFLOW.info("Marco l'email come da inviare");
                LkStatiMail emailDaInviare = emailDao.findstatoByCodStato(StatiEmail.DA_INVIARE);
                email.setStato(emailDaInviare);
                emailDao.update(email);
                runtimeService.startProcessInstanceByKey("cross_send_mail_process", variables);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void sendMailPraticaEvento(DelegateExecution execution) throws Exception {
        Log.WORKFLOW.debug("Metodo sendMailPraticaEvento invocato.");
        Map<String, Object> variables = execution.getVariables();
        Integer idEmail = (Integer) variables.get(Workflow.ID_EMAIL);
        Integer idPraticaEvento = (Integer) variables.get(Workflow.ID_PRATICA_EVENTO);
        PraticheEventi praticaEvento = praticaDao.getDettaglioPraticaEvento(idPraticaEvento);
        Email email = mailService.findByIdEmail(idEmail);
        try {
            //Non tratto le email consegnate
            if (!email.getStato().getCodice().equals(StatiEmail.CONFERMATA)) {
                //Azzero oggetto risposta e corpo risposta: in questo modo se tutto va bene non visualizzo i vecchi errori
                //In caso di nuovi errori, questi verranno comunque scritti dallo scheduler di lettura
                email.setOggettoRisposta(null);
                email.setCorpoRisposta(null);
                //Indico che l'ultimo aggiornamento risale ad ora
                email.setDataAggiornamento(new Date());
                mailService.updateEmail(email);
                Pratica pratica = praticaEvento.getIdPratica();
                Integer idEnte = pratica.getIdProcEnte().getIdEnte().getIdEnte();
                Integer idComune = pratica.getIdComune().getIdComune();

                String replayTo = configuration.getCachedConfiguration(SessionConstants.MAIL_REPLAY_TO, idEnte, idComune);
                Boolean hasError = Boolean.FALSE;

                String mailBody;
                String mailSubject;
                //Se Evento di ricezione, ricalcolo il corpo/oggetto della mail
                if (AnaTipiEvento.RICEZIONE_PRATICA.equalsIgnoreCase(praticaEvento.getIdEvento().getCodEvento()) || ("PROCINT").equalsIgnoreCase(praticaEvento.getIdEvento().getCodEvento())) {
                    MailContentBean mailContent = workFlowService.getMailContent(pratica, praticaEvento);
                    mailBody = mailContent.getContenuto();
                    mailSubject = mailContent.getOggetto();
                } else {
                    StringBuilder sb = new StringBuilder(email.getCorpoEmail());
                    if (!Utils.e(praticaEvento.getProtocollo())) {
                        sb.append(messageSource.getMessage("email.corpoProtocollo", Arrays.asList(praticaEvento.getProtocollo()).toArray(), Locale.getDefault()));
                    }
                    sb.append(messageSource.getMessage("email.replyTo", Arrays.asList(replayTo).toArray(), Locale.getDefault()));
                    mailBody = sb.toString();
                    mailSubject = email.getOggettoEmail();
                }

                try {
                    EmailConfig emailConfig = mailService.getSmtpProprieties(idEnte, idComune);
                    List<Allegati> attachmentsToSend = new ArrayList<Allegati>();
                    for (PraticheEventiAllegati a : praticaEvento.getPraticheEventiAllegatiList()) {
                        if (a.getFlgIsToSend().equalsIgnoreCase("S")) {
                            attachmentsToSend.add(a.getAllegati());
                        }
                    }
                    emailConfig.setAllegati(attachmentsToSend);
                    String mittente = pratica.getIdProcEnte().getIdEnte().getPec() != null ? pratica.getIdProcEnte().getIdEnte().getPec() : pratica.getIdProcEnte().getIdEnte().getEmail();
                    emailConfig.setFrom(mittente);
                    emailConfig.setTo(email.getEmailDestinatario());

                    emailConfig.setSubject(mailSubject);
                    emailConfig.setContent(mailBody);

                    if (!Utils.e(email.getPathEml())) {
                        FileUtils.deleteQuietly(new File(email.getPathEml()));
                    }

                    MimeMessage emailEml = gestoreMail.preparaMail(emailConfig);
                    String filepath = gestoreMail.salvaEML(emailEml, emailConfig);
                    email.setPathEml(filepath);

                    if (mailBody.contains("#ERRORE#")) {
                        throw new Exception("Il corpo della mail contiene un errore.");
                    }
                    if (mailSubject.contains("#ERRORE#")) {
                        throw new Exception("L'oggetto della mail contiene un errore.");
                    }
                    email.setCorpoEmail(mailBody);
                    email.setOggettoEmail(mailSubject);
                    mailService.invioSingolaEmail(email);
                } catch (Exception ex) {
                    if (!hasError) {
                        ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_WORKFLOW_MAIL, "Errore nell'invio della mail", ex, pratica, null);
                        errore.setStatus("E");
                        Integer idError = erroriAction.saveError(errore);
                        execution.setVariable(Workflow.LAST_ERROR_MESSAGE, idError);
                    }
                    //Aggiorno la mail con le info di errore
                    email.setOggettoRisposta("Errore nell'invio della mail");
                    email.setCorpoRisposta(ex.getMessage());
                    LkStatiMail erroreServer = mailService.findStatoMailByCodice(StatiEmail.ERRORE_SERVER);
                    email.setStato(erroreServer);
                    mailService.updateEmail(email);
                    hasError = Boolean.TRUE;
                }

                if (hasError) {
                    List<String> candidateUsers = getCandidateUsers(praticaEvento, true);
                    execution.setVariable(Workflow.CANDIDATE_USERS, candidateUsers);
                    execution.setVariable("require_manual_mail", true);
                    String notifica = messageSource.getMessage("pratica.email.notifica", Arrays.asList(email.getOggettoEmail(), email.getEmailDestinatario()).toArray(), Locale.getDefault());
//                    notificationEngine.createNotification(praticaEvento, candidateUsers, notifica);
                } else {
                    execution.setVariable("require_manual_mail", false);
                }
            }
        } catch (Exception e) {
            email.setOggettoRisposta("Errore generico nell'invio della mail");
            email.setCorpoRisposta(e.getMessage());
            LkStatiMail mailInErrore = mailService.findStatoMailByCodice(StatiEmail.ERRORE_GENERICO);
            email.setStato(mailInErrore);
            mailService.updateEmail(email);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_WORKFLOW_MAIL, "Errore nella creazione del file eml", e, null, null);
            errore.setStatus("E");
            execution.setVariable("require_manual_mail", true);
            Integer idError = erroriAction.saveError(errore);
            execution.setVariable(Workflow.LAST_ERROR_MESSAGE, idError);
            Log.WORKFLOW.error("Errore nell'invio della mail. L'operazione viene sottoposta a un amministratore.", e);
//            String notifica = messageSource.getMessage("pratica.email.notifica", new Object[]{
//                email.getOggettoEmail(),
//                email.getEmailDestinatario()
//            }, Locale.getDefault());
//            List<String> candidateUsers = getCandidateUsers(praticaEvento, true);
//            notificationEngine.createNotification(praticaEvento, candidateUsers, notifica);
        }

        Log.WORKFLOW.debug("Metodo sendMailPraticaEvento terminato.");
    }

    @Transactional
    public void valorizzaDatiPratica(DelegateExecution execution) throws Exception {
        Log.WORKFLOW.debug("Metodo valorizzaDatiPratica invocato.");
        Map<String, Object> variables = execution.getVariables();
        Integer idPraticaEvento = (Integer) variables.get(Workflow.ID_PRATICA_EVENTO);
        Preconditions.checkNotNull(idPraticaEvento, "Non è stato valorizzato l'id evento");
        PraticheEventi praticaEvento = praticaDao.getDettaglioPraticaEvento(idPraticaEvento);
//        String eventoBean = (String) variables.get(Workflow.EVENTO_BEAN);
//        EventoBean eb = new Gson().fromJson(eventoBean, EventoBean.class);
        Map<String, Object> variabili = getVariabiliPratica(praticaEvento);
        variabili.put(Workflow.TIPO_TASK, Workflow.TIPO_TASK_NOTIFICA);
        for (Map.Entry entry : variabili.entrySet()) {
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            execution.setVariable(key, value);
        }
        Log.WORKFLOW.debug("Metodo valorizzaDatiPratica terminato.");
    }

    @Transactional
    public void aggiornaMailPraticaPraticaEvento(DelegateExecution execution) throws Exception {
        Log.WORKFLOW.debug("Metodo aggiornaMailPraticaPraticaEvento invocato.");
        Log.WORKFLOW.debug("Metodo aggiornaMailPraticaPraticaEvento terminato.");
    }

    public Map<String, Object> getVariabiliPratica(EventoBean eventoBean, PraticheEventi evento) throws Exception {
        Map<String, Object> variabili = getVariabiliPratica(evento);
//        variabili.put(Workflow.EVENTO_BEAN, eventoBean);
        String json = new Gson().toJson(eventoBean);
        variabili.put(Workflow.EVENTO_BEAN, json);
        String className = eventoBean.getClass().getCanonicalName();
        variabili.put(Workflow.EVENTO_BEAN_CLASS, className);
        return variabili;
    }

    public Map<String, Object> getVariabiliPratica(PraticheEventi evento) throws Exception {
        Pratica pratica = evento.getIdPratica();
        Preconditions.checkNotNull(pratica, "L'ID evento è " + evento.getIdEvento());
        Map<String, Object> variabili = new HashMap<String, Object>();
        variabili.put(Workflow.ID_PRATICA, pratica.getIdPratica());
        variabili.put(Workflow.ID_PRATICA_EVENTO, evento.getIdPraticaEvento());
        String identificativoPratica = pratica.getIdentificativoPratica();
        variabili.put(Workflow.IDENTIFICATIVO_PRATICA, identificativoPratica);
        String descrizionePratica = pratica.getOggettoPratica();
        variabili.put(Workflow.OGGETTO_PRATICA, descrizionePratica);
        String fascicolo = pratica.getCodRegistro() + "/" + pratica.getAnnoRiferimento() + "/" + pratica.getProtocollo();
        variabili.put(Workflow.FASCICOLO_PRATICA, fascicolo);
        variabili.put(Workflow.RICHIEDENTI_PRATICA, PraticheSerializer.getRichiedentiFromPratica(pratica));
        variabili.put(Workflow.TIPO_TASK, Workflow.TIPO_TASK_TASK);
        return variabili;
    }

    public List<String> getCandidateUsers(PraticheEventi evento, boolean includeAllSuperusers) {
        List<String> candidateUser = new ArrayList<String>();
        Utente istruttore = evento.getIdPratica().getIdUtente();
        if (istruttore != null) {
            candidateUser.add(istruttore.getUsername());
        }
        if (includeAllSuperusers) {
            List<String> systemSuperusers = utentiService.findAllSystemSuperusersUsername();
            candidateUser.addAll(systemSuperusers);
        }
        //Rimuovo i duplicati
        return ImmutableSet.copyOf(Iterables.filter(candidateUser, Predicates.not(Predicates.isNull()))).asList();
    }

    public void riassegnaTaskAssociatiAllaPratica(Integer idPratica, Utente assegnatario) {
        //Recupero tutti i task non di notifica
        List<Task> taskAssociatiAllaPratica = taskService.createTaskQuery()
                .includeProcessVariables().active()
                .processVariableValueEquals(Workflow.ID_PRATICA, idPratica)
                .processVariableValueEquals(Workflow.TIPO_TASK, Workflow.TIPO_TASK_TASK)
                .list();
        String nuovoIstruttore = assegnatario.getUsername();
        Pratica pratica = praticaDao.findPratica(idPratica);
        if (pratica != null) {
            String attualeIstruttore = pratica.getIdUtente() != null ? pratica.getIdUtente().getUsername() : null;
            for (Task task : taskAssociatiAllaPratica) {
                if (!Strings.isNullOrEmpty(attualeIstruttore)) {
                    taskService.deleteCandidateUser(task.getId(), attualeIstruttore);
                }
                taskService.addCandidateUser(task.getId(), nuovoIstruttore);
                taskService.saveTask(task);
            }
        }
    }
}
