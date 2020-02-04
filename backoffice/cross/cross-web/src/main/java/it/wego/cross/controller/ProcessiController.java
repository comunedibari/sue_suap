package it.wego.cross.controller;

import it.wego.cross.actions.ErroriAction;
import it.wego.cross.actions.ProcessiAction;
import it.wego.cross.beans.JsonResponse;
import it.wego.cross.beans.grid.GridAnagraficaBean;
import it.wego.cross.beans.grid.GridEntiBean;
import it.wego.cross.beans.grid.GridEventiBean;
import it.wego.cross.beans.grid.GridProcessi;
import it.wego.cross.beans.grid.GridStepsBean;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.constants.ConfigurationConstants;
import it.wego.cross.constants.Workflow;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dto.AnagraficaDTO;
import it.wego.cross.dto.AttoreDTO;
import it.wego.cross.dto.EmailDTO;
import it.wego.cross.dto.EnteDTO;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.dto.ProcessoDTO;
import it.wego.cross.dto.ProcessoEventoDTO;
import it.wego.cross.dto.StatoPraticaDTO;
import it.wego.cross.dto.StepDTO;
import it.wego.cross.dto.TipologiaScadenzeDTO;
import it.wego.cross.dto.dozer.ProcessoEventoAnagraficaDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.Processi;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.ProcessiSteps;
import it.wego.cross.entity.ProcessiStepsPK;
import it.wego.cross.entity.Utente;
import it.wego.cross.exception.CrossException;
import it.wego.cross.serializer.EventiSerializer;
import it.wego.cross.service.AnagraficheService;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.EventiService;
import it.wego.cross.service.LookupService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.ProcessiService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author CS
 */
@Controller
public class ProcessiController extends AbstractController {

    //variabili relative alle comunicazione utente sulle operazioni effettuate
    private final Character OPERAZIONE_MODIFICA_PROCESSO = 'M';
    private final Character OPERAZIONE_AGGIUNTA_PROCESSO = 'A';
    private final Character OPERAZIONE_ELIMINA_PROCESSO = 'E';
    private final Character OPERAZIONE_MODIFICA_EVENTO = 'm';
    private final Character OPERAZIONE_AGGIUNTA_EVENTO = 'a';
    private final Character OPERAZIONE_ELIMINA_EVENTO = 'e';
    private final String OPERAZIONE_EFFETTUATA = "operazioneeffettuata";
    //
    private final String AGGIUNGI_PROCESSO = "aggiungi_processo";
    private final String AGGIUNGI_EVENTO = "aggiungi_evento";
    private final String MODIFICA_PROCESSO = "modifica_processo";
    private final String MODIFICA_EVENTO = "modifica_evento";
    private final String HOME_PROCESSI = "redirect:/processi/lista.htm";
    private final String HOME_EVENTI = "redirect:/processi/eventi/lista.htm";
    private final String HOME_STEP = "redirect:/processi/eventi/flusso.htm";
    private final String LISTA_PROCESSI = "lista_processi";
    private final String LISTA_PROCESSI_EVENTI = "lista_processi_eventi";
    private final String LISTA_PROCESSI_STEP = "lista_processi_step";
    private static final String IDPROCESSO_SELEZIONATO = "gestione_processi_id_processo";
    private static final String IDEVENTO_SELEZIONATO = "gestione_processi_id_evento";
    private static final String AJAX_PAGE = "ajax";
    @Autowired
    private ProcessiService processiService;
    @Autowired
    private EventiService eventiService;
    @Autowired
    private LookupService lookupService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ProcessiAction processiAction;
    @Autowired
    private ErroriAction erroriAction;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private EventiSerializer eventiSerializer;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private AnagraficheService anagraficheService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private EntiService entiService;

    @RequestMapping("/processi/lista")
    public String processiLista(Model model,
            @ModelAttribute EmailDTO email,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute(OPERAZIONE_EFFETTUATA) String operazioneEffettuataatt) {
        String messaggiOperazioneEffettuata = messaggioOperazioneEffettuata(operazioneEffettuataatt);
        model.addAttribute("messaggiooperazioneeffettuata", messaggiOperazioneEffettuata);
        return LISTA_PROCESSI;
    }

    @RequestMapping("/processi/lista/ajax")
    public String processiListaAjax(Model model, @ModelAttribute ProcessoDTO processo, @ModelAttribute("paginator") JqgridPaginator paginator, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        GridProcessi json = new GridProcessi();
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            Integer maxResult = Integer.parseInt(paginator.getRows());
            Integer page = Integer.parseInt(paginator.getPage());
            String column = paginator.getSidx();
            String order = paginator.getSord();
            Integer firstRecord = (page * maxResult) - maxResult;
            Filter filter = new Filter();
            filter.setLimit(maxResult);
            filter.setOffset(firstRecord);
            filter.setOrderColumn(column);
            filter.setOrderDirection(order);
            Integer totalRecords = processiService.countListaProcessi().intValue();
            List<ProcessoDTO> processi = processiService.getListaProcessi(filter);
            Integer records = (processi.size() < maxResult) ? processi.size() : processi.size() / maxResult;
            totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
            json.setPage(page);
            json.setRecords(records);
            json.setTotal(totalRecords);
            json.setRows(processi);
        } catch (Exception e) {
//            String msgError = "Errore reperendo i processi";
//            Message error = new Message();
//            error.setMessages(Arrays.asList(msgError));
//            Log.SQL.error(msgError, e);    
            String msg = "Impossibile effettuare la ricerca.";
            json.setErrors(Arrays.asList(msg));
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PROCESSI_LISTA_AJAX, "Errore nell'esecuzione del controller /processi/lista/ajax", e, null, utente);
            erroriAction.saveError(errore);
            model.addAttribute("json", json.toString());
            Log.SQL.error(msg, e);
        }
        model.addAttribute("json", json.toString());
        return AJAX_PAGE;
    }

    @RequestMapping("/processi/aggiungi")
    public String aggiungiProcesso(Model model, @ModelAttribute EmailDTO email, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        return AGGIUNGI_PROCESSO;
    }

    @RequestMapping("/processi/salvaProcesso")
    public String salvaProcesso(Model model, @ModelAttribute("processo") Processi processo,
            HttpServletRequest request,
            HttpServletResponse response,
            final RedirectAttributes redirectAttribute) {
        String submit = request.getParameter("submitaction");
        Utente utente = utentiService.getUtenteConnesso(request);
//        String idProcesso = request.getParameter("idProcesso");
//        String codice = request.getParameter("codice");
        Integer idProcesso = processo.getIdProcesso();
        String codice = processo.getCodProcesso();
        String descrizione = processo.getDesProcesso();
        Message msg = new Message();
        if (!Utils.e(idProcesso)) {
            try {
                String cancella = messageSource.getMessage("processo.button.cancella", null, Locale.getDefault());
                if (!Utils.e(submit) && submit.equals(cancella)) {
//                    processiAction.cancellaProcesso(Integer.valueOf(idProcesso));
                    List<String> praticheCollegate = processiAction.cancellaProcessoCascata(idProcesso);
                    if (praticheCollegate.isEmpty()) {
                        redirectAttribute.addFlashAttribute(OPERAZIONE_EFFETTUATA, OPERAZIONE_ELIMINA_PROCESSO + "#" + descrizione);
                    } else {
                        msg.setError(true);
                        msg.setMessages(praticheCollegate);
                        model.addAttribute("processo", processo);
                        model.addAttribute("message", msg);
                        return MODIFICA_PROCESSO;
                    }
                } else {
                    if ((!Utils.e(codice)) || (!Utils.e(descrizione))) {
                        processiAction.aggiornaProcesso(idProcesso, codice, descrizione);
                        redirectAttribute.addFlashAttribute(OPERAZIONE_EFFETTUATA, OPERAZIONE_MODIFICA_PROCESSO + "#" + descrizione);
                    } else {
                        msg.setMessages(Arrays.asList("Valorizzare il campo codice del processo ed il campo descrizione del processo"));
                        msg.setError(true);
                        model.addAttribute("processo", processo);
                        model.addAttribute("message", msg);
                        return MODIFICA_PROCESSO;
                    }
                }
            } catch (Exception ex) {
                Log.APP.error("Si è verificato un errore gestendo il dettaglio dell'evento", ex);

                if (msg.getMessages() != null && msg.getMessages().size() > 0) {
                    model.addAttribute("message", msg);
                }
                ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PROCESSI_SALVAPROCESSO, "Errore nell'esecuzione del controller /processi/salvaProcesso", ex, null, utente);
                erroriAction.saveError(errore);
                msg.setMessages(Arrays.asList("Errore salvando il processo"));
                msg.setError(true);
                model.addAttribute("processo", processo);
                model.addAttribute("message", msg);
                return MODIFICA_PROCESSO;
            }
        } else {
            try {
                if ((!Utils.e(codice)) || (!Utils.e(descrizione))) {
                    processiAction.inserisciProcesso(codice, descrizione);
                    redirectAttribute.addFlashAttribute(OPERAZIONE_EFFETTUATA, OPERAZIONE_AGGIUNTA_PROCESSO + "#" + descrizione);
                } else {
                    msg.setMessages(Arrays.asList("Valorizzare il campo codice del processo ed il campo descrizione del processo"));
                    msg.setError(true);
                    model.addAttribute("processo", processo);
                    model.addAttribute("message", msg);
                    return AGGIUNGI_PROCESSO;
                }
            } catch (Exception ex) {
                ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PROCESSI_SALVAPROCESSO, "Errore nell'esecuzione del controller /processi/salvaProcesso", ex, null, utente);
                erroriAction.saveError(errore);
                Log.APP.error("Si è verificato un errore gestendo il dettaglio dell'evento", ex);
                msg.setMessages(Arrays.asList("Errore salvando il processo"));
                if (msg.getMessages() != null && msg.getMessages().size() > 0) {
                    msg.setError(true);
                    model.addAttribute("processo", processo);
                    model.addAttribute("message", msg);
                }
                return AGGIUNGI_PROCESSO;
            }
        }
        return HOME_PROCESSI;
    }

    @RequestMapping("/processi/modifica")
    public String modificaProcesso(Model model, HttpServletRequest request, HttpServletResponse response) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            Integer idProcesso = Integer.valueOf(request.getParameter("idProcesso"));
            Processi processo = processiService.findProcessoById(idProcesso);
            ProcessoDTO proc = new ProcessoDTO();
            proc.setCodProcesso(processo.getCodProcesso());
            proc.setDesProcesso(processo.getDesProcesso());
            proc.setIdProcesso(processo.getIdProcesso());
            model.addAttribute("processo", proc);
        } catch (Exception e) {
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PROCESSI_SALVAPROCESSO, "Errore nell'esecuzione del controller /processi/salvaProcesso", e, null, utente);
            erroriAction.saveError(errore);
            Log.APP.error("Caricando il processo da modificare", e);
        }
        return MODIFICA_PROCESSO;
    }

    @RequestMapping("/processi/eventi/lista")
    public String processiEventiLista(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute(OPERAZIONE_EFFETTUATA) String operazioneEffettuataatt) {
        String messaggiOperazioneEffettuata = messaggioOperazioneEffettuata(operazioneEffettuataatt);
        model.addAttribute("messaggiooperazioneeffettuata", messaggiOperazioneEffettuata);
        HttpSession session = request.getSession();
        String idProcesso = request.getParameter("idProcesso");
        if (!Utils.e(idProcesso)) {
            session.setAttribute(IDPROCESSO_SELEZIONATO, Integer.valueOf(request.getParameter("idProcesso")));
        }
        Filter filter = (Filter) request.getSession().getAttribute("processiEventiLista");
        if (filter == null) {
            filter = new Filter();
        }
        model.addAttribute("filtroRicerca", filter);
        return LISTA_PROCESSI_EVENTI;
    }

    @RequestMapping("/processi/eventi/lista/ajax")
    public String processiEventiListaAjax(Model model, @ModelAttribute("paginator") JqgridPaginator paginator, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        GridEventiBean json = new GridEventiBean();
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            Integer maxResult = Integer.parseInt(paginator.getRows());
            Integer page = Integer.parseInt(paginator.getPage());
            String column = paginator.getSidx();
            String order = paginator.getSord();
            Integer firstRecord = (page * maxResult) - maxResult;
            //^^CS MODIFICA gestione fgiltri ricerca in sessione
            Filter filter = (Filter) request.getSession().getAttribute("processiEventiLista");
            if (filter == null) {
                filter = new Filter();
            }
            filter.setPage(page);
            filter.setLimit(maxResult);
            filter.setOffset(firstRecord);
            filter.setOrderColumn(column);
            filter.setOrderDirection(order);
            Integer idProcesso = (Integer) request.getSession().getAttribute(IDPROCESSO_SELEZIONATO);
            request.getSession().setAttribute("processiEventiLista", filter);
            Integer totalRecords = eventiService.countEventiByIdProcesso(idProcesso, filter).intValue();
            List<EventoDTO> eventi = eventiService.findEventiByIdProcesso(idProcesso, filter);
            Integer records = (eventi.size() < maxResult) ? eventi.size() : eventi.size() / maxResult;
            totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
            json.setPage(page);
            json.setRecords(records);
            json.setTotal(totalRecords);
            json.setRows(eventi);
        } catch (Exception e) {
            String msgError = "Errore reperendo i processi";
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            model.addAttribute("message", error);
            Log.SQL.error(msgError, e);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PROCESSI_EVENTI_LISTA_AJAX, "Errore nell'esecuzione del controller /processi/eventi/lista/ajax", e, null, utente);
            erroriAction.saveError(errore);
        }
        model.addAttribute("json", json.toString());
        return AJAX_PAGE;
    }

    @RequestMapping("/processi/eventi/aggiungi")
    public String aggiungiEvento(Model model, HttpServletRequest request, HttpServletResponse response) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            List<AttoreDTO> attori = lookupService.findAllAttori();
            List<StatoPraticaDTO> statiPratica = lookupService.findAllStatiPratica();
            List<TipologiaScadenzeDTO> tipologiaScadenza = lookupService.findAllLkScadenze();
            model.addAttribute("statoPratica", statiPratica);
            model.addAttribute("destinatari", attori);
            model.addAttribute("mittenti", attori);
            model.addAttribute("tipologiaScadenza", tipologiaScadenza);
            model.addAttribute("isGestioneAnagraficheEventi", Utils.True(configurationService.getCachedConfiguration(ConfigurationConstants.IS_GESTIONE_ANAGRAFICHE_EVENTO_ENABLE, null, null)));
            ProcessoEventoDTO evento = inizializzaProcessoEvento();
            Integer idProcesso = (Integer) request.getSession().getAttribute(IDPROCESSO_SELEZIONATO);
            evento.setIdProcesso(idProcesso);
            model.addAttribute("evento", evento);
        } catch (Exception e) {
            String msgError = "Errore caricando il nuovo evento da aggiungere";
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            Log.APP.error(msgError, e);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PROCESSI_EVENTI_AGGIUNGI, "Errore nell'esecuzione del controller /processi/eventi/aggiungi", e, null, utente);
            erroriAction.saveError(errore);
        }
        return AGGIUNGI_EVENTO;
    }

    private ProcessoEventoDTO inizializzaProcessoEvento() {
        ProcessoEventoDTO evento = new ProcessoEventoDTO();
        evento.setFlgPortale("N");
        evento.setFlgMail("N");
        evento.setFlgAllegatiEmail("N");
        evento.setFlgDestinatari("N");
        evento.setFlgDestinatariSoloEnti("N");
        evento.setFlgProtocollazione("N");
        evento.setFlgRicevuta("N");
        evento.setFlgFirmato("N");
        evento.setFlgApriSottoPratica("N");
        evento.setFlgAutomatico("N");
        evento.setFlgVisualizzaProcedimenti("N");
        evento.setFunzioneApplicativa("comunicazione");
        return evento;
    }

    @RequestMapping("/processi/eventi/aggiungi/salvaEvento")
    public String salvaEvento(Model model,
            @ModelAttribute("evento") ProcessoEventoDTO evento,
            BindingResult result, HttpServletRequest request,
            HttpServletResponse response,
            final RedirectAttributes redirectAttributes) {
        Utente utente = utentiService.getUtenteConnesso(request);
        if (evento.getIdEvento() != null) {
            String submit = request.getParameter("submitaction");
            String cancella = messageSource.getMessage("processo.button.cancella", null, Locale.getDefault());
            try {
                if (!Utils.e(submit) && submit.equals(cancella)) {
                    processiAction.cancellaEvento(evento);
                    redirectAttributes.addFlashAttribute(OPERAZIONE_EFFETTUATA, OPERAZIONE_ELIMINA_EVENTO + "#" + evento.getDescrizioneEvento());
                } else {
                    redirectAttributes.addFlashAttribute(OPERAZIONE_EFFETTUATA, OPERAZIONE_MODIFICA_EVENTO + "#" + evento.getDescrizioneEvento());
                    processiAction.aggiornaEvento(evento);
                }
            } catch (Exception ex) {
                String errorMsg = "Errore salvando l'evento. Verifica se i campi obbligatori sono stati correttamente valorizzati.";
                if (ex instanceof CrossException) {
                    errorMsg = ex.getMessage();
                }
                Log.APP.error(errorMsg, ex);
                Message msg = new Message();
                msg.setMessages(Arrays.asList(errorMsg));
                if (msg.getMessages() != null && !msg.getMessages().isEmpty()) {
                    model.addAttribute("message", msg);
                }
                ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PROCESSI_EVENTI_AGGIUNGI_SALVAEVENTO, "/processi/eventi/aggiungi/salvaEvento", ex, null, utente);
                erroriAction.saveError(errore);
                List<AttoreDTO> attori = lookupService.findAllAttori();
                List<StatoPraticaDTO> statiPratica = lookupService.findAllStatiPratica();
                List<TipologiaScadenzeDTO> tipologiaScadenza = lookupService.findAllLkScadenze();
                model.addAttribute("tipologiaScadenza", tipologiaScadenza);
                model.addAttribute("statoPratica", statiPratica);
                model.addAttribute("destinatari", attori);
                model.addAttribute("mittenti", attori);
                model.addAttribute("evento", evento);
                model.addAttribute("error", msg);
                return MODIFICA_EVENTO;
            }
        } else {
            try {
                processiAction.inserisciEvento(evento);
                redirectAttributes.addFlashAttribute(OPERAZIONE_EFFETTUATA, OPERAZIONE_AGGIUNTA_EVENTO + "#" + evento.getDescrizioneEvento());
            } catch (Exception ex) {
                Log.APP.error("Si è verificato un errore gestendo il dettaglio dell'evento", ex);
                Message msg = new Message();
                msg.setMessages(Arrays.asList("Errore salvando l'evento. Verifica se i campi obbligatori sono stati correttamente valorizzati."));
                if (msg.getMessages() != null && msg.getMessages().size() > 0) {
                    model.addAttribute("message", msg);
                }
                ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PROCESSI_EVENTI_AGGIUNGI_SALVAEVENTO, "/processi/eventi/aggiungi/salvaEvento", ex, null, utente);
                erroriAction.saveError(errore);
                List<AttoreDTO> attori = lookupService.findAllAttori();
                List<StatoPraticaDTO> statiPratica = lookupService.findAllStatiPratica();
                List<TipologiaScadenzeDTO> tipologiaScadenza = lookupService.findAllLkScadenze();
                model.addAttribute("tipologiaScadenza", tipologiaScadenza);
                model.addAttribute("statoPratica", statiPratica);
                model.addAttribute("destinatari", attori);
                model.addAttribute("mittenti", attori);
                model.addAttribute("evento", evento);
                return AGGIUNGI_EVENTO;
            }
        }
        return HOME_EVENTI;
    }

    @RequestMapping("/processi/eventi/modifica")
    public String modificaEvento(Model model, HttpServletRequest request, HttpServletResponse response) {
        List<AttoreDTO> attori = lookupService.findAllAttori();
        List<StatoPraticaDTO> statiPratica = lookupService.findAllStatiPratica();
        List<TipologiaScadenzeDTO> tipologiaScadenza = lookupService.findAllLkScadenze();
        model.addAttribute("tipologiaScadenza", tipologiaScadenza);
        model.addAttribute("statoPratica", statiPratica);
        model.addAttribute("destinatari", attori);
        model.addAttribute("mittenti", attori);
        model.addAttribute("isGestioneAnagraficheEventi", Utils.True(configurationService.getCachedConfiguration(ConfigurationConstants.IS_GESTIONE_ANAGRAFICHE_EVENTO_ENABLE, null, null)));
        Integer idEvento = Integer.valueOf(request.getParameter("idEvento"));
        ProcessiEventi processoEvento = processiService.findProcessiEventiByIdEvento(idEvento);
        ProcessoEventoDTO evento = eventiSerializer.serializeProcessoEvento(processoEvento);
        model.addAttribute("evento", evento);
        return MODIFICA_EVENTO;
    }

    @RequestMapping("/processi/eventi/cercaAnagraficaAjax")
    public String cercaAnagrafica(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("descrizione") String descrizione,
            @RequestParam("action") String action) {
        GridAnagraficaBean json = new GridAnagraficaBean();
        List<AnagraficaDTO> anagrafiche = anagraficheService.searchAnagraficheLike(descrizione);
        json.setRows(anagrafiche);
        json.setTotal(anagrafiche.size());
        model.addAttribute("json", json.toString());
        return AJAX_PAGE;
    }

    @RequestMapping("/processi/eventi/cercaEnteAjax")
    public String cercaEnte(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("descrizione") String descrizione,
            @RequestParam("action") String action) {
        GridEntiBean json = new GridEntiBean();
        List<EnteDTO> enti = entiService.searchEntiLike(descrizione);
        json.setRows(enti);
        json.setTotal(enti.size());
        model.addAttribute("json", json.toString());
        return AJAX_PAGE;
    }

    @RequestMapping("/processi/eventi/flusso")
    public String visualizzaFlusso(Model model, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String idEvento = request.getParameter("idEvento");
        if (!Utils.e(idEvento)) {
            session.setAttribute(IDEVENTO_SELEZIONATO, Integer.valueOf(idEvento));
        }
        return LISTA_PROCESSI_STEP;
    }

    @RequestMapping("/processi/eventi/flusso/ajax")
    public String visualizzaFlussoAjax(Model model, @ModelAttribute("paginator") JqgridPaginator paginator, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        GridStepsBean json = new GridStepsBean();
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            Integer maxResult = Integer.parseInt(paginator.getRows());
            Integer page = Integer.parseInt(paginator.getPage());
            String column = paginator.getSidx();
            String order = paginator.getSord();
            Integer firstRecord = (page * maxResult) - maxResult;
            Filter filter = new Filter();
            filter.setLimit(maxResult);
            filter.setOffset(firstRecord);
            filter.setOrderColumn(column);
            filter.setOrderDirection(order);
            Integer idEvento = (Integer) request.getSession().getAttribute(IDEVENTO_SELEZIONATO);
            List<StepDTO> steps = eventiService.getSteps(idEvento, filter);
            Integer totalRecords = steps.size();
            Integer records = (steps.size() < maxResult) ? steps.size() : steps.size() / maxResult;
            totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
            json.setPage(page);
            json.setRecords(records);
            json.setTotal(totalRecords);
            json.setRows(steps);
        } catch (Exception e) {
            String msgError = "Errore reperendo i processi";
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            model.addAttribute("message", error);
            Log.SQL.error(msgError, e);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PROCESSI_EVENTI_FLUSSO_AJAX, "/processi/eventi/flusso/ajax", e, null, utente);
            erroriAction.saveError(errore);
        }
        model.addAttribute("json", json.toString());
        return AJAX_PAGE;
    }

    @RequestMapping("/processi/eventi/flusso/salva")
    public String salvaStepFlusso(Model model,
            HttpServletRequest request,
            HttpServletResponse response //            ,final RedirectAttributes redirectAttributes
    ) {
        Utente utente = utentiService.getUtenteConnesso(request);
        Integer idEventoResult = Integer.valueOf(request.getParameter("idEventoResult"));
        Integer idEvento = (Integer) request.getSession().getAttribute(IDEVENTO_SELEZIONATO);
        String operazione = request.getParameter("step");
        ProcessiSteps step = processiService.findStepByKey(idEvento, idEventoResult);
        if (!Utils.e(operazione)) {
            //CONTROLLA SE GIA' PRESENTE
            try {
                if (step != null) {
                    step.setTipoOperazione(operazione);
                    processiAction.aggiornaStep(step);
//                    redirectAttributes.addFlashAttribute(OPERAZIONE_EFFETTUATA, OPERAZIONE_MODIFICA_EVENTO + "#" + operazione);
                } else {
                    ProcessiSteps newStep = new ProcessiSteps();
                    ProcessiStepsPK key = new ProcessiStepsPK();
                    key.setIdEventoResult(idEventoResult);
                    key.setIdEventoTrigger(idEvento);
                    newStep.setProcessiStepsPK(key);
                    newStep.setTipoOperazione(operazione);
                    processiAction.inserisciStep(newStep);
//                    redirectAttributes.addFlashAttribute(OPERAZIONE_EFFETTUATA, OPERAZIONE_AGGIUNTA_EVENTO + "#" + operazione);
                }
            } catch (Exception ex) {
                String msgError = "Errore reperendo i processi";
                Message error = new Message();
                error.setMessages(Arrays.asList(msgError));
                model.addAttribute("message", error);
                Log.SQL.error(msgError, ex);
                ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PROCESSI_EVENTI_FLUSSO_SALVA, "/processi/eventi/flusso/salva", ex, null, utente);
                erroriAction.saveError(errore);
            }
        } else if (step != null) {
            try {
                processiAction.cancellaStep(idEventoResult, idEvento);
//                redirectAttributes.addFlashAttribute(OPERAZIONE_EFFETTUATA, OPERAZIONE_AGGIUNTA_EVENTO + "#" + step.getTipoOperazione());
            } catch (Exception ex) {
                String msgError = "Errore reperendo i processi";
                Message error = new Message();
                error.setMessages(Arrays.asList(msgError));
                model.addAttribute("message", error);
                Log.SQL.error(msgError, ex);
                ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PROCESSI_EVENTI_FLUSSO_SALVA, "/processi/eventi/flusso/salva", ex, null, utente);
                erroriAction.saveError(errore);
            }
        } else {
            //Questo caso non dovrebbe esistere
        }
        return HOME_STEP;
    }

    @RequestMapping("/processi/processo/deploy")
    public ModelAndView processoDeploy(Model model) {
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<ProcessDefinition> processList = repositoryService.createProcessDefinitionQuery().list();
        model.addAttribute("process_list", processList);

        List<ProcessInstance> instanceList = runtimeService.createProcessInstanceQuery().list();
        model.addAttribute("instance_list", instanceList);

        List<Task> taskList = taskService.createTaskQuery().list();
        model.addAttribute("task_list", taskList);

        List<HistoricProcessInstance> historyInstanceList = historyService.createHistoricProcessInstanceQuery().list();
        model.addAttribute("history_instance_list", historyInstanceList);

//        ProcessInstance instanceExample = instanceList.get(0);
//        BpmnModel bpmnModel = ActivitiUtil.getRepositoryService().getBpmnModel(pde.getId());
//        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(instanceExample.getProcessDefinitionId());
//        ProcessDiagramGenerator.generateDiagram(processDefinition, "png", runtimeService.getActiveActivityIds(instanceExample.getId()));
//        InputStream definitionImageStream = ProcessDiagramGenerator.generateDiagram(processDefinition, "png", runtimeService.getActiveActivityIds(instanceExample.getId()));
        return new ModelAndView("processi_processo_deploy");
    }

    @RequestMapping("/processi/processo/deploySubmit")
    public ModelAndView processoDeploySubmit(Model model, @RequestParam("processName") String processName, @RequestParam("processFile") MultipartFile processFile, RedirectAttributes redirectAttributes) {
        try {
            ZipInputStream zis = new ZipInputStream(processFile.getInputStream());

            repositoryService.createDeployment()
                    .name(processName)
                    .addZipInputStream(zis)
                    .deploy();
            redirectAttributes.addFlashAttribute("message", new Message(Boolean.FALSE, Arrays.asList("Processo deployato correttamente")));

        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message", new Message(Boolean.TRUE, Arrays.asList("Impossibile deployare il processo")));
            Log.APP.error(""+redirectAttributes.getFlashAttributes().get("message"), ex);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PROCESSI_PROCESSO_DEPLOY, "/processi/processo/deploySubmit", ex, null, null);
            erroriAction.saveError(errore);
        }
        return new ModelAndView("redirect:/processi/processo/deploy.htm");
    }

    @RequestMapping("/process/undeploy")
    public ModelAndView processUndeploySubmit(Model model, @RequestParam("processId") String processId, RedirectAttributes redirectAttributes) {
        try {
            ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processId);

            repositoryService.deleteDeployment(processDefinition.getDeploymentId(), true);

            redirectAttributes.addFlashAttribute("message", new Message(Boolean.FALSE, Arrays.asList("Processo undeployato correttamente")));

        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message", new Message(Boolean.TRUE, Arrays.asList("Impossibile undeployare il processo")));
            Log.APP.error(""+redirectAttributes.getFlashAttributes().get("message"), ex);
            //TODO: verificare se ci sono le info dell'utente connesso e salvarle
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PROCESSI_PROCESSO_DEPLOY, "/process/undeploy", ex, null, null);
            erroriAction.saveError(errore);
        }
        return new ModelAndView("redirect:/processi/processo/deploy.htm");
    }

    @RequestMapping("/process/istance/delete")
    public ModelAndView instanceDeleteSubmit(Model model, @RequestParam("historicInstanceId") String historicInstanceId, RedirectAttributes redirectAttributes) {
        try {
            historyService.deleteHistoricProcessInstance(historicInstanceId);

            redirectAttributes.addFlashAttribute("message", new Message(Boolean.FALSE, Arrays.asList("Istanza eliminata correttamente")));

        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message", new Message(Boolean.TRUE, Arrays.asList("Impossibile eliminare l'istanza")));
            Log.APP.error(""+redirectAttributes.getFlashAttributes().get("message"), ex);
            //TODO: verificare se ci sono le info dell'utente connesso e salvarle
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PROCESSI_PROCESSO_DEPLOY, "/process/istance/delete", ex, null, null);
            erroriAction.saveError(errore);
        }
        return new ModelAndView("redirect:/processi/processo/deploy.htm");
    }

    @RequestMapping("/process/instance/updateInstanceVariables")
    public ModelAndView updateInstanceVariables(Model model, RedirectAttributes redirectAttributes) {
        List<Task> list = taskService.createTaskQuery().processDefinitionKey("cross_communication_process").list();
        for (Task task : list) {
            runtimeService.setVariable(task.getProcessInstanceId(), Workflow.TIPO_TASK, Workflow.TIPO_TASK_TASK);
        }
        redirectAttributes.addFlashAttribute("message", new Message(Boolean.FALSE, Arrays.asList("Aggiornamento massivo eseguito correttamente ")));
        return new ModelAndView("redirect:/processi/processo/deploy.htm");
    }

    private String messaggioOperazioneEffettuata(String input) {
        if (input != null && input.length() != 0) {
            List<String> inputlist = Arrays.asList(input.split("#"));
            Character tipoOperazione = inputlist.get(0).charAt(0);
            if ((inputlist.size() == 2) && (inputlist.get(0).length() == 1)) {
                String parametri = inputlist.get(1);
                switch (tipoOperazione) {
                    case 'M':
                        return "Salvataggio delle modifiche apportate al processo " + parametri;
                    case 'A':
                        return "Salvataggio in database del nuovo processo: " + parametri;
                    case 'E':
                        return "Rimozione del processo: " + parametri;
                    case 'm':
                        return "Salvataggio delle modifiche apportate all'evento " + parametri;
                    case 'a':
                        return "Collegamento dell'evento: " + parametri + " al processo corrente";
                    case 'e':
                        return "Rimozione del collegamento tra l'evento " + parametri + " e il processo corrente";
                    default:
                        return "";
                }
            } else {
                return "";
            }
        } else {
            return "";
        }
    }
}
