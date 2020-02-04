package it.wego.cross.controller;

import it.wego.cross.actions.EntiAction;
import it.wego.cross.actions.ErroriAction;
import it.wego.cross.actions.PraticheAction;
import it.wego.cross.beans.JsonResponse;
import it.wego.cross.dto.PermessiEnteProcedimentoDTO;
import it.wego.cross.beans.grid.GridComuneBean;
import it.wego.cross.beans.grid.GridEntiBean;
import it.wego.cross.beans.grid.GridPermessiEnteProcedimento;
import it.wego.cross.beans.grid.GridProcessi;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.EnteDTO;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.ProcessoDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.Processi;
import it.wego.cross.entity.Utente;
import it.wego.cross.exception.CrossException;
import it.wego.cross.serializer.EntiSerializer;
import it.wego.cross.serializer.FilterSerializer;
import it.wego.cross.serializer.LuoghiSerializer;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.ProcedimentiService;
import it.wego.cross.service.ProcessiService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import it.wego.cross.validator.IsValid;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class GestioneEntiController extends AbstractController {

    //variabili relative alle comunicazione utente sulle operazioni effettuate
    private final Character OPERAZIONE_MODIFICA = 'M';
    private final Character OPERAZIONE_AGGIUNTA = 'A';
    private final Character OPERAZIONE_ELIMINA = 'E';
    private final Character OPERAZIONE_MODIFICA_ENDOPROC = 'D';
    private final String OPERAZIONE_EFFETTUATA = "operazioneeffettuata";
    //
    private final String GESTIONE_ENDOPROCEDIMENTI = "ente_gestione_endoprocedimenti";
    private final String AJAX = "ajax";
    private final String HOMEPAGE_ENTI = "ente_index";
    private final String REDIRECT_HOMEPAGE = "redirect:/ente/index.htm";
    private final String REDIRECT_MODIFICA = "redirect:/ente/modifica.htm";
    private final String REDIRECT_SELEZIONAPROCESSO = "redirect:/ente/selezionaProcesso.htm";
    private final String REDIRECT_ENDOPROCEDIMENTI = "redirect:/ente/endoprocedimenti.htm";
    private final String PAGE_ADD_ENTE = "ente_aggiungi";
    private final String PAGE_MODIFY_ENTE = "ente_modifica";
    private final String PAGE_ADD_PADRE = "ente_aggiungi_padre";
    private final String PAGE_ADD_PROCEDIMENTI = "ente_aggiungi_procedimenti";
    private final String PAGE_ADD_COMUNI = "ente_visualizza_comuni";
    private final String PAGE_RICERCA_COMUNI = "ente_ricerca_comuni";
    private final String PAGE_SELEZIONA_PROCESSO = "ente_seleziona_processo";
    private final String DELETE_ENTI_SESSION = "delete_enti_session";
//    private String CODICE_ENTE = "enti_codEnte";
    private final String ID_ENTE = "enti_idEnte";
    private final String CODICE_PROCEDIMENTO = "enti_codProcesso";
    private final String filtroRicercaEnti = "filtroRicercaEnti";
    @Autowired
    private EntiService entiService;
    @Autowired
    private ProcedimentiService procedimentiService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private FilterSerializer filterSerializer;
    @Autowired
    private ProcessiService processiService;
    @Autowired
    protected Validator validator;
    @Autowired
    private IsValid isValid;
    @Autowired
    private EntiAction entiAction;
    @Autowired
    private ErroriAction erroriAction;
    @Autowired
    private PraticheAction praticheAction;

    @RequestMapping("/ente/index/ajax")
    public String listAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        Utente utente = utentiService.getUtenteConnesso(request);
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        String column = paginator.getSidx();
        String order = paginator.getSord();
        Integer firstRecord = (page * maxResult) - maxResult;
        Filter filter = (Filter) request.getSession().getAttribute(filtroRicercaEnti);
        if (filter == null) {
            filter = new Filter();
        }
        filter.setPage(page);
        filter.setLimit(maxResult);
        filter.setOffset(firstRecord);
        filter.setOrderColumn(column);
        filter.setOrderDirection(order);
        GridEntiBean json = new GridEntiBean();
        try {
            String tipoFiltro = (String) request.getParameter("tipoFiltro");
            if ((!Utils.e(tipoFiltro) && tipoFiltro.toUpperCase().equals("UTENTE"))) {
                filter.setDenominazione((String) request.getParameter("descrizione"));
                filter.setCodiceFiscale((String) request.getParameter("codiceFiscale"));
                filter.setPartitaIva((String) request.getParameter("partitaIva"));
            }
            request.getSession().setAttribute(filtroRicercaEnti, filter);
            List<Enti> entiCensiti = entiService.findAll(filter);
            List<EnteDTO> enti = new LinkedList<EnteDTO>();
            for (Enti ente : entiCensiti) {
                EnteDTO e = EntiSerializer.serializer(ente);
                enti.add(e);
            }
            Long countRighe = entiService.countAll(filter);
//            Integer records = (enti.size() < maxResult) ? enti.size() : enti.size() / maxResult;
            Integer totalRecords = countRighe.intValue();
            totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
            json.setPage(page);
            json.setRecords(countRighe.intValue());
            json.setTotal(totalRecords);
            json.setRows(enti);
            model.addAttribute("json", json.toString());
        } catch (Exception e) {
            String msg = "Impossibile effettuare la ricerca.";
            json.setErrors(Arrays.asList(msg));
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_ENTE_INDEX_AJAX, "Errore nell'esecuzione del controller /ente/index/ajax", e, null, utente);
            erroriAction.saveError(errore);
            model.addAttribute("json", json.toString());
            Log.APP.error(msg, e);
        }
        return AJAX;
    }

    @RequestMapping("/ente/index")
    public String list(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("paginator") JqgridPaginator paginator,
            @ModelAttribute(OPERAZIONE_EFFETTUATA) String operazioneEffettuataatt) {
        String messaggiOperazioneEffettuata = messaggioOperazioneEffettuata(operazioneEffettuataatt);
        model.addAttribute("messaggiooperazioneeffettuata", messaggiOperazioneEffettuata);
        Filter filter = (Filter) request.getSession().getAttribute(filtroRicercaEnti);
        if (filter == null) {
            filter = new Filter();
        }
        Message error = (Message) request.getSession().getAttribute(DELETE_ENTI_SESSION);
        request.getSession().removeAttribute(DELETE_ENTI_SESSION);
        model.addAttribute("message", error);
        model.addAttribute("filtroRicerca", filter);
        request.getSession().setAttribute(ID_ENTE, null);
//        request.getSession().setAttribute(CODICE_ENTE, null);
        return HOMEPAGE_ENTI;
    }

    @RequestMapping("/ente/aggiungi")
    public String add(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute EnteDTO enteDTO, BindingResult result,
            @ModelAttribute("paginator") JqgridPaginator paginator,
            final RedirectAttributes redirectAttributes) {
        String action = request.getParameter("action");
        Utente utente = utentiService.getUtenteConnesso(request);
        if (enteDTO == null) {
            enteDTO = new EnteDTO();
        }
        String codEnte = enteDTO.getCodEnte();
        if (action != null && "add".equals(action)) {
            Enti ente = EntiSerializer.serializer(enteDTO);
            boolean isCorrect = false;
            try {
                enteDTO.setIdEnte(1); // per passare il validate
                if ((enteDTO.getUnitaOrganizzativa() == null) || ("".equals(enteDTO.getUnitaOrganizzativa()))) {
                    enteDTO.setUnitaOrganizzativa(enteDTO.getCodEnte());
                }
                validator.validate(enteDTO, result);
                List<String> err = isValid.Ente(result, enteDTO);

                enteDTO.setIdEnte(null);
                // enteDTO.setCodEnte(null);
                if (err != null) {
                    Message msg = new Message();
                    msg.setMessages(err);
                    model.addAttribute("ente", enteDTO);
                    model.addAttribute("message", msg);
                    return PAGE_ADD_ENTE;
                }
                if ((ente.getUnitaOrganizzativa() == null) || ("".equals(ente.getUnitaOrganizzativa()))) {
                    ente.setUnitaOrganizzativa(ente.getCodEnte());
                }

                Enti e = entiService.findByCodEnte(ente.getCodEnte());
                if (e != null) {
                    String message = messageSource.getMessage("error.ente.insert.codEnte", null, Locale.getDefault());
                    Message error = new Message();
                    error.setMessages(Arrays.asList(message));
                    model.addAttribute("ente", enteDTO);
                    request.setAttribute("message", error);
                    return PAGE_ADD_ENTE;
                }
                entiAction.inserisciEnte(ente);
                isCorrect = true;
            } catch (Exception ex) {
                Log.APP.error("ERRORE: Operatore.add" + ex.getMessage(), ex);
                Message error = new Message();
                error.setMessages(Arrays.asList("Errore inserendo l'ente"));
                request.setAttribute("message", error);
                ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_ENTE_AGGIUNGI, "Errore nell'esecuzione del controller /ente/aggiungi", ex, null, utente);
                erroriAction.saveError(errore);
            }
            if (isCorrect) {
                redirectAttributes.addFlashAttribute(OPERAZIONE_EFFETTUATA, OPERAZIONE_AGGIUNTA + "#" + enteDTO.getDescrizione());
                return REDIRECT_HOMEPAGE;
            } else {
                enteDTO.setCodEnte(codEnte);
                model.addAttribute("ente", enteDTO);
                return PAGE_ADD_ENTE;
            }
        } else {
            enteDTO.setCodEnte(codEnte);
            model.addAttribute("ente", enteDTO);
            return PAGE_ADD_ENTE;
        }
    }

    @RequestMapping("/ente/modifica")
    public String modify(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute EnteDTO enteDTO,
            BindingResult result,
            @ModelAttribute("paginator") JqgridPaginator paginator,
            final RedirectAttributes redirectAttributes,
            @ModelAttribute(OPERAZIONE_EFFETTUATA) String operazioneEffettuataatt) {
        String messaggiOperazioneEffettuata = messaggioOperazioneEffettuata(operazioneEffettuataatt);
        model.addAttribute("messaggiooperazioneeffettuata", messaggiOperazioneEffettuata);
        Utente utente = utentiService.getUtenteConnesso(request);
        if (enteDTO.getIdEnte() != null) {
            String isSuap = entiService.isSuap(enteDTO.getIdEnte());
            model.addAttribute("isSuap", isSuap);
        }
        String action = request.getParameter("action");
        String submit = request.getParameter("submit");
        String modifica = messageSource.getMessage("ente.button.modifica", null, Locale.getDefault());
        String collegaPadre = messageSource.getMessage("ente.button.padre.collega", null, Locale.getDefault());
        String scollegaPadre = messageSource.getMessage("ente.button.padre.scollega", null, Locale.getDefault());
        String gestioneProcedimenti = messageSource.getMessage("ente.button.procedimenti.collega", null, Locale.getDefault());
        String gestioneComuni = messageSource.getMessage("ente.button.comuni.collega", null, Locale.getDefault());
        String gestioneEndoprocedimenti = messageSource.getMessage("ente.button.endoprocedimenti", null, Locale.getDefault());
        Integer idEnte;
        if (!Utils.e(request.getParameter("idEnte"))) {
            idEnte = Integer.valueOf(request.getParameter("idEnte"));
        } else {
            idEnte = (Integer) request.getSession().getAttribute(ID_ENTE);
        }
        if (Utils.e(idEnte)) {
            Log.APP.warn("Codice ente non specificato");
            Message error = new Message();
            error.setMessages(Arrays.asList("Specificare il codice ente"));
            request.setAttribute("message", error);
            return PAGE_MODIFY_ENTE;
        } else {
            request.getSession().setAttribute(ID_ENTE, idEnte);
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
            if (action != null && "modify".equals(action)) {
                if (submit.equals(modifica)) {
                    validator.validate(enteDTO, result);
                    List<String> err = isValid.Ente(result, enteDTO);
                    if (err != null) {
                        Message msg = new Message();
                        msg.setMessages(err);
//                        Enti ente = findEnteByCodice(codEnte);
                        Enti ente = EntiSerializer.serializer(enteDTO);
                        model.addAttribute("ente", ente);
                        model.addAttribute("message", msg);
                        return PAGE_MODIFY_ENTE;
                    }
                    try {
                        entiAction.modificaEnte(enteDTO);
                        redirectAttributes.addFlashAttribute(OPERAZIONE_EFFETTUATA, OPERAZIONE_MODIFICA + "#" + enteDTO.getDescrizione());
                        return REDIRECT_HOMEPAGE;
                    } catch (Exception e) {
                        Log.APP.error("Si è verificato un errore modificando l'ente", e);
                        Message error = new Message();
                        if ((e.getMessage() != null) && (e.getMessage().contains("Duplicate"))) {
                            error.setMessages(Arrays.asList("Una o più chiavi, relative all'entità che si sta cercando di salvare, risulta già presente nel database."));
                        } else {
                            error.setMessages(Arrays.asList("Si è verificato un errore modificando l'ente"));
                        }
                        model.addAttribute("message", error);
                        model.addAttribute("ente", enteDTO);
                        ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_ENTE_MODIFICA, "Errore nell'esecuzione del controller /ente/modifica", e, null, utente);
                        erroriAction.saveError(errore);
                        return PAGE_MODIFY_ENTE;
                    }
                } else if (collegaPadre.equals(submit)) {
                    return "redirect:/ente/collegaEnte.htm";
                } else if (scollegaPadre.equals(submit)) {
                    Enti ente = null;
                    try {
                        ente = entiAction.scollegaPadre(idEnte);
                    } catch (Exception ex) {
                        Log.APP.error("ERRORE: Operatore.scollegaPadres" + ex.getMessage(), ex);
                        Message error = new Message();
                        error.setMessages(Arrays.asList("Errore scollegando il padre"));
                        request.setAttribute("message", error);
                        ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_ENTE_MODIFICA, "Errore nell'esecuzione del controller /ente/modifica", ex, null, utente);
                        erroriAction.saveError(errore);
                        model.addAttribute("ente", ente);
                        return modify(model, request, response, enteDTO, result, paginator, redirectAttributes, "");
                    }
                    model.addAttribute("ente", ente);
                    return modify(model, request, response, enteDTO, result, paginator, redirectAttributes, "");
                } else if (gestioneProcedimenti.equals(submit)) {
                    return "redirect:/ente/procedimenti.htm";
                } else if (gestioneComuni.equals(submit)) {
                    return "redirect:/ente/comuni.htm";
                } else if (gestioneEndoprocedimenti.equals(submit)) {
                    redirectAttributes.addFlashAttribute("idEnte", idEnte.toString());
                    redirectAttributes.addFlashAttribute("enteDTO", enteDTO);
                    return "redirect:/ente/endoprocedimenti.htm?idente=" + idEnte.toString();
                } else {
                    Message error = new Message();
                    error.setMessages(Arrays.asList("Azione specificata non valida"));
                    request.setAttribute("message", error);
                    return PAGE_MODIFY_ENTE;
                }
            } else {
                Enti ente = entiService.findByIdEnte(idEnte);
                if (enteDTO.getIdEnte() == null) {
                    String isSuap = ente.getTipoEnte();
                    model.addAttribute("isSuap", isSuap);
                }
                if ((ente.getUnitaOrganizzativa() == null) || ("".equals(ente.getUnitaOrganizzativa()))) {
                    ente.setUnitaOrganizzativa(ente.getCodEnte());
                }
                model.addAttribute("ente", ente);
                return PAGE_MODIFY_ENTE;
            }
        }
    }

    @RequestMapping("/ente/endoprocedimenti")
    String gestisciEndoprocedimenti(Model model,
            @ModelAttribute("idente") String idEnte,
            HttpServletRequest request,
            @ModelAttribute("erroriSalvataggio") String erroriSalvataggio) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            if (idEnte == null || idEnte.isEmpty()) {
                idEnte = request.getParameter("idEnte");
            }
            Boolean nonVisualizzareEntiSuapNellAlbero = true;
            String alberoEnteProcedimentiEnti = entiAction.getAlberoEnteProcedimentiEnti(Integer.parseInt(idEnte), nonVisualizzareEntiSuapNellAlbero);
            model.addAttribute("tree", alberoEnteProcedimentiEnti);
            model.addAttribute("idEnte", idEnte);

        } catch (Exception ex) {
            Log.APP.error("ERRORE: Ente.endoprocedimenti" + ex.getMessage(), ex);
            Message error = new Message();
            error.setMessages(Arrays.asList("Errore nella visualizzazione degli endoprocedimenti"));
            request.setAttribute("message", error);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_ENTE_ENDOPROCEDIMENTI, "Errore nell'esecuzione del controller /ente/endoprocedimenti", ex, null, utente);
            erroriAction.saveError(errore);
            model.addAttribute("idEnte", idEnte);
            return GESTIONE_ENDOPROCEDIMENTI;
        }
        if ((!"".equals(erroriSalvataggio) && (erroriSalvataggio != null))) {
            Message error = new Message();
            error.setMessages(Arrays.asList(erroriSalvataggio));
            request.setAttribute("message", error);
        }
        return GESTIONE_ENDOPROCEDIMENTI;
    }

    @RequestMapping("/ente/endoprocedimenti/salva")
    String gestisciEndoprocedimentiAjax(Model model,
            @ModelAttribute("selected") String selected,
            @ModelAttribute("idEnte") Integer idEnte,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {
        Utente utente = utentiService.getUtenteConnesso(request);
        EnteDTO ente = EntiSerializer.serializer(entiService.findByIdEnte(idEnte));
        try {
            if (!"nessunaModifica".equals(selected)) {
                List<String> selectedList = Arrays.asList(selected.split(","));
                List<Integer> selectedProc = new ArrayList();
                for (String proc : selectedList) {
                    if (Arrays.asList(proc.split("#")).size() > 1) {
                        String procedimento = Arrays.asList(proc.split("#")).get(1);
                        selectedProc.add(Integer.parseInt(procedimento));
                    }
                }
                entiAction.modificaEndoProcedimenti(idEnte, selectedProc);
            } else {
                redirectAttributes.addFlashAttribute("enteDTO", ente);
            }
        } catch (Exception ex) {
            Log.APP.error("ERRORE: Ente.endoprocedimenti" + ex.getMessage(), ex);
            Message error = new Message();
            error.setMessages(Arrays.asList("Si è verificato un errore nel salvataggio delle modifiche, nessuna modifica è stata apportata."));
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_ENTE_ENDOPROCEDIMENTI_SALVA, "Errore nell'esecuzione del controller /ente/endoprocedimenti/salva", ex, null, utente);
            erroriAction.saveError(errore);
            redirectAttributes.addFlashAttribute("idente", idEnte);
            redirectAttributes.addFlashAttribute("erroriSalvataggio", "Si è verificato un errore nel salvataggio delle modifiche, nessuna modifica è stata apportata.");
            return REDIRECT_ENDOPROCEDIMENTI;
        }
        redirectAttributes.addFlashAttribute(OPERAZIONE_EFFETTUATA, OPERAZIONE_MODIFICA_ENDOPROC + "#" + ente.getDescrizione());
        return REDIRECT_MODIFICA;
    }

    @RequestMapping("/ente/comuni")
    public String comuniCollegati(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        return PAGE_ADD_COMUNI;
    }

    @RequestMapping("/ente/comuni/ajax")
    public String comuniCollegatiAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
//        int codEnte = (Integer) request.getSession().getAttribute(CODICE_ENTE);
        GridComuneBean json = new GridComuneBean();
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            int codEnte = (Integer) request.getSession().getAttribute(ID_ENTE);
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
            Long count = entiService.countComuniCollegati(codEnte, filter);
            List<LkComuni> comuni = entiService.getComuniCollegati(codEnte, filter);
            List<ComuneDTO> comuniDaVisualizzare = new LinkedList<ComuneDTO>();
            for (LkComuni comune : comuni) {
                ComuneDTO c = LuoghiSerializer.serialize(comune);
                comuniDaVisualizzare.add(c);
            }

            Integer records = (comuniDaVisualizzare.size() < maxResult) ? comuniDaVisualizzare.size() : comuniDaVisualizzare.size() / maxResult;
            Integer totalRecords = count.intValue();
            totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));

            json.setPage(page);
            json.setRecords(records);
            json.setTotal(totalRecords);
            json.setRows(comuniDaVisualizzare);
            model.addAttribute("json", json.toString());
        } catch (Exception e) {
            String msg = "Impossibile effettuare la ricerca.";
            json.setErrors(Arrays.asList(msg));
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_ENTE_COMUNI_AJAX, "Errore nell'esecuzione del controller /ente/comuni/ajax", e, null, utente);
            erroriAction.saveError(errore);
            model.addAttribute("json", json.toString());
            Log.APP.error(msg, e);
        }
        return AJAX;
    }

    @RequestMapping("/ente/comuni/ricerca")
    public String ricercaComuni(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        return PAGE_RICERCA_COMUNI;
    }

    @RequestMapping("/ente/comuni/ricerca/ajax")
//    @-Transactional
    public String ricercaComuniAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            Integer maxResult = Integer.parseInt(paginator.getRows());
            Integer page = Integer.parseInt(paginator.getPage());
            String column = paginator.getSidx();
            String order = paginator.getSord();
            Integer firstRecord = (page * maxResult) - maxResult;
            Filter filter = filterSerializer.getSearchFilter(request);
            filter.setLimit(maxResult);
            filter.setOffset(firstRecord);
            filter.setOrderColumn(column);
            filter.setOrderDirection(order);

            Long count = entiService.countComuniFiltrati(filter);
            List<LkComuni> comuni = entiService.findComuniFiltrati(filter);

            List<ComuneDTO> comuniDaVisualizzare = new LinkedList<ComuneDTO>();

            for (LkComuni comune : comuni) {
                ComuneDTO c = LuoghiSerializer.serialize(comune);
                comuniDaVisualizzare.add(c);
            }

            Integer records = (comuniDaVisualizzare.size() < maxResult) ? comuniDaVisualizzare.size() : comuniDaVisualizzare.size() / maxResult;
            Integer totalRecords = count.intValue();
            totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));

            GridComuneBean json = new GridComuneBean();
            json.setPage(page);
            json.setRecords(records);
            json.setTotal(totalRecords);
            json.setRows(comuniDaVisualizzare);
            model.addAttribute("json", json.toString());
            return AJAX;
        } catch (ParseException ex) {
            Log.APP.error("Si è verificato un errore cercando di valorizzare il filtro di ricerca", ex);
            Message messaggi = new Message();
            messaggi.getMessages().add("Errore nella ricerca dei comuni");
            model.addAttribute("message", messaggi);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_ENTE_COMUNI_AJAX, "Errore nell'esecuzione del controller /ente/comuni/ricerca/ajax", ex, null, utente);
            erroriAction.saveError(errore);
            return PAGE_RICERCA_COMUNI;
        }
    }

    @RequestMapping("/ente/procedimenti")
    public String visualizzaProcedimenti(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("paginator") JqgridPaginator paginator,
            @ModelAttribute("message") Message message
    ) {
        Filter filter = (Filter) request.getSession().getAttribute("ricercaEntiProcedimenti");
        int codEnte = (Integer) request.getSession().getAttribute(ID_ENTE);
        Enti ente = entiService.findByIdEnte(codEnte);
        if (filter == null) {
            filter = new Filter();
        }
        model.addAttribute("ente", ente);
        model.addAttribute("filtroRicerca", filter);
        return PAGE_ADD_PROCEDIMENTI;
    }

    @RequestMapping("/ente/procedimenti/ajax")
    public String visualizzaProcedimentiAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            int codEnte = (Integer) request.getSession().getAttribute(ID_ENTE);
            Enti ente = entiService.findByIdEnte(codEnte);
            Integer maxResult = Integer.parseInt(paginator.getRows());
            Integer page = Integer.parseInt(paginator.getPage());
            String column = paginator.getSidx();
            String order = paginator.getSord();
            Integer firstRecord = (page * maxResult) - maxResult;
            Filter filter = filterSerializer.getSearchFilter(request);
            filter.setEnteSelezionato(ente);
            filter.setPage(page);
            filter.setLimit(maxResult);
            filter.setOffset(firstRecord);
            filter.setOrderColumn(column);
            filter.setOrderDirection(order);
            request.getSession().setAttribute("ricercaEntiProcedimenti", filter);
            Long count = procedimentiService.contaProcedimentiLocalizzati(filter, codEnte);
            List<PermessiEnteProcedimentoDTO> procedimenti = procedimentiService.getProcedimentiLocalizzati(codEnte, filter);
            Integer totalRecords = count.intValue();
            totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));

            GridPermessiEnteProcedimento json = new GridPermessiEnteProcedimento();
            json.setPage(page);
            json.setRecords(count.intValue());
            json.setTotal(totalRecords);
            json.setRows(procedimenti);
            model.addAttribute("json", json.toString());
            return AJAX;
        } catch (Exception ex) {
            Log.APP.debug("Si è verificato un errore cercando di valorizzare il filtro di ricerca", ex);
            Message messaggi = new Message();
            messaggi.getMessages().add("Errore nella ricerca dei procedimenti");
            model.addAttribute("message", messaggi);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_ENTE_PROCEDIMENTI_AJAX, "Errore nell'esecuzione del controller /ente/procedimenti/ajax", ex, null, utente);
            erroriAction.saveError(errore);
            return PAGE_ADD_PROCEDIMENTI;
        }
    }

    @RequestMapping("/ente/procedimenti/modificaResponsabileAjax")
    public String modificaResponsabileProcedimentoAjax(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "action", required = true) String azione,
            PermessiEnteProcedimentoDTO permessiEnteProcedimentoDTO) {
        Utente utente = utentiService.getUtenteConnesso(request);
        JsonResponse json = new JsonResponse();
        try {
            int codEnte = (Integer) request.getSession().getAttribute(ID_ENTE);
            Enti ente = entiService.findByIdEnte(codEnte);

            praticheAction.aggiornaResponsabileProcedimento(permessiEnteProcedimentoDTO, ente, azione);

            json.setSuccess(Boolean.TRUE);
            json.setMessage("Modifica responsabile effettuata con successo");
            model.addAttribute("json", json.toString());
            return AJAX;
        } catch (Exception ex) {
            Log.APP.debug("Si è verificato un errore cercando di aggiornare il responsabile del procedimento", ex);
            json.setSuccess(Boolean.FALSE);
            json.setMessage("Errore nella modifica del responsabile del procedimento");
            model.addAttribute("json", json.toString());
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_ENTE_PROCEDIMENTI_AJAX, "Errore nella modifica del responsabile del procedimento ajax", ex, null, utente);
            erroriAction.saveError(errore);
            return PAGE_ADD_PROCEDIMENTI;
        }
    }

    @RequestMapping("/ente/selezionaProcedimento")
    public ModelAndView handleProcess(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("paginator") JqgridPaginator paginator,
            RedirectAttributes redirectAttributes
    ) {
        Utente utente = utentiService.getUtenteConnesso(request);
        String abilita = messageSource.getMessage("ente.procedimenti.abilita", null, Locale.getDefault());
        String disabilita = messageSource.getMessage("ente.procedimenti.disabilita", null, Locale.getDefault());
        String submit = request.getParameter("submit");
        int idProc = Integer.valueOf(request.getParameter("idProc"));
        int codEnte = (Integer) request.getSession().getAttribute(ID_ENTE);
        try {
            if (submit.contains(abilita)) {
                entiAction.salvaProcedimentiEnte(codEnte, idProc);
            } else if (submit.equals(disabilita)) {
                entiAction.disabilitaProcedimentiEnti(codEnte, idProc);
            } else {
                Message error = new Message();
                error.setMessages(Arrays.asList("Azione specificata non valida"));
                request.setAttribute("message", error);
            }
        } catch (Exception e) {
            Log.APP.debug("Si è verificato un errore cercando di valorizzare il filtro di ricerca", e);
            Message messaggi = new Message();
            if (e instanceof CrossException) {
                messaggi.getMessages().add(e.getMessage());
            } else {
                messaggi.getMessages().add("Errore nella ricerca dei procedimenti");
            }
            redirectAttributes.addFlashAttribute("message", messaggi);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_ENTE_SELEZIONAPROCEDIMENTO, "Errore nell'esecuzione del controller /ente/selezionaProcedimento", e, null, utente);
            erroriAction.saveError(errore);
        }
        return new ModelAndView("redirect:/ente/procedimenti.htm");
    }

    @RequestMapping("/ente/elimina")
    public String delete(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("paginator") JqgridPaginator paginator,
            final RedirectAttributes redirectAttributes) {
        int codEnte = Integer.valueOf(request.getParameter("codEnte"));
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            Enti ente = entiService.findByIdEnte(codEnte);
            entiAction.eliminaEnte(codEnte);
            if (ente != null) {
                redirectAttributes.addFlashAttribute(OPERAZIONE_EFFETTUATA, OPERAZIONE_ELIMINA + "#" + ente.getDescrizione());
            }
        } catch (Exception ex) {
            Message error = new Message();
            error.setMessages(Arrays.asList("Impossibile cancellare l'ente perchè collegato ad altre entità."));
            request.getSession().setAttribute(DELETE_ENTI_SESSION, error);
            Log.APP.error("Errore cancellando l'ente.", ex);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_ENTE_ELIMINA, "Errore nell'esecuzione del controller /ente/elimina", ex, null, utente);
            erroriAction.saveError(errore);
        }
        return REDIRECT_HOMEPAGE;
    }

    @RequestMapping("/ente/comuni/seleziona")
    public String selezionaComune(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        int codEnte = (Integer) request.getSession().getAttribute(ID_ENTE);
//        int codEnte = (Integer) request.getSession().getAttribute(CODICE_ENTE);
        int idComune = Integer.valueOf(request.getParameter("idComune"));
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            entiAction.collegaEnteComune(codEnte, idComune);
        } catch (Exception ex) {
            Log.SQL.error("Si è verificato un errore collegando il comune", ex);
            Message error = new Message();
            error.setMessages(Arrays.asList("Errore selezionando il comune"));
            request.setAttribute("message", error);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_ENTE_COMUNI_SELEZIONA, "Errore nell'esecuzione del controller /ente/comuni/seleziona", ex, null, utente);
            erroriAction.saveError(errore);
        }
        return "redirect:/ente/comuni.htm";
    }

    @RequestMapping("/ente/comuni/cancella")
    public String cancellaComune(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        int codEnte = (Integer) request.getSession().getAttribute(ID_ENTE);
//        int codEnte = (Integer) request.getSession().getAttribute(CODICE_ENTE);
        int idComune = Integer.valueOf(request.getParameter("idComune"));
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            entiAction.cancellaRelazioneEnteComune(codEnte, idComune);
        } catch (Exception ex) {
            Message error = new Message();
            error.setMessages(Arrays.asList("Errore cancellando il comune"));
            request.setAttribute("message", error);
            Log.APP.error("Errore cancellando il comune", ex);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_ENTE_COMUNI_CANCELLA, "Errore nell'esecuzione del controller /ente/comuni/cancella", ex, null, utente);
            erroriAction.saveError(errore);
        }
        return "redirect:/ente/comuni.htm";
    }

    @RequestMapping("/ente/collegaEnte")
    public String collegaEnte(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            Integer codEnte = (Integer) request.getSession().getAttribute(ID_ENTE);
//        Integer codEnte = (Integer) request.getSession().getAttribute(CODICE_ENTE);
            Integer maxResult = Integer.parseInt(paginator.getRows());
            Integer page = Integer.parseInt(paginator.getPage());
            String column = "idEnte";
            String order = "DESC";
            Integer firstRecord = (page * maxResult) - maxResult;
            Filter filter = new Filter();
            filter.setLimit(maxResult);
            filter.setOffset(firstRecord);
            filter.setOrderColumn(column);
            filter.setOrderDirection(order);
            List<Enti> enti = entiService.findAll(filter);
            Long countRighe = entiService.countAll(filter);
            Integer totalRecords = countRighe.intValue();
            paginator.setTotal(totalRecords);
            model.addAttribute("codEnteOrigine", codEnte);
            model.addAttribute("paginate", paginator);
            model.addAttribute("enti", enti);
        } catch (Exception e) {
            Message error = new Message();
            error.setMessages(Arrays.asList("Errore cancellando il comune"));
            request.setAttribute("message", error);
            Log.APP.error("Errore cancellando il comune", e);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_ENTE_COLLEGAENTE, "Errore nell'esecuzione del controller /ente/collegaEnte", e, null, utente);
            erroriAction.saveError(errore);
        }
        return PAGE_ADD_PADRE;
    }

    @RequestMapping("/ente/collegaPadre")
    public String connect(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("paginator") JqgridPaginator paginator,
            final RedirectAttributes redirectAttributes) {
        int codEnte = Integer.valueOf(request.getParameter("codEnte"));
        Utente utente = utentiService.getUtenteConnesso(request);
        int codEntePadre = Integer.valueOf(request.getParameter("codEntePadre"));
        try {
            entiAction.collegaPadre(codEnte, codEntePadre);
        } catch (Exception ex) {
            Log.APP.error("ERRORE: Operatore.collegaPadre" + ex.getMessage(), ex);
            Message error = new Message();
            error.setMessages(Arrays.asList("Non è stato possibile associare l'ente. Contattare l'amministratore"));
            request.setAttribute("message", error);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_ENTE_COLLEGAPADRE, "Errore nell'esecuzione del controller /ente/collegaPadre", ex, null, utente);
            erroriAction.saveError(errore);
        }
        model.addAttribute("codEnte", codEnte);
        return modify(model, request, response, null, null, paginator, redirectAttributes, "");
    }

    @RequestMapping("/ente/selezionaProcesso")
    public String selezionaProcesso(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        if (!Utils.e(request.getParameter("idProc"))) {
            Integer idProcedimento = Integer.valueOf(request.getParameter("idProc"));
            request.getSession().setAttribute(CODICE_PROCEDIMENTO, idProcedimento);
        }
        Filter filter = (Filter) request.getSession().getAttribute("entiSelezionaProcesso");
        if (filter == null) {
            filter = new Filter();
        }
        model.addAttribute("filtroRicerca", filter);
        request.getSession().setAttribute("entiSelezionaProcesso", filter);
        return PAGE_SELEZIONA_PROCESSO;
    }

    @RequestMapping("/ente/selezionaProcesso/ajax")
    public String selezionaProcessoAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            Integer idProcedimento = (Integer) request.getSession().getAttribute(CODICE_PROCEDIMENTO);
            Integer idEnte = (Integer) request.getSession().getAttribute(ID_ENTE);
//            Integer idEnte = (Integer) request.getSession().getAttribute(CODICE_ENTE);
            ProcedimentiEnti procedimentoEnte = procedimentiService.findProcedimentiEnti(idEnte ,idProcedimento);
            Integer maxResult = Integer.parseInt(paginator.getRows());
            Integer page = Integer.parseInt(paginator.getPage());
            String column = paginator.getSidx();
            String order = paginator.getSord();
            Integer firstRecord = (page * maxResult) - maxResult;
            //^^CS MODIFICA gestione fgiltri ricerca in sessione
            Filter filter = (Filter) request.getSession().getAttribute("entiSelezionaProcesso");
            if (filter == null) {
                filter = new Filter();
            }
            filter.setPage(page);
            filter.setLimit(maxResult);
            filter.setOffset(firstRecord);
            filter.setOrderColumn(column);
            filter.setOrderDirection(order);
            String tipoFiltro = (String) request.getParameter("tipoFiltro");
            if ((!Utils.e(tipoFiltro) && tipoFiltro.toUpperCase().equals("UTENTE"))) {
                Filter filtertmp = filterSerializer.getSearchFilter(request);
                filtertmp.setProcedimento(request.getParameter("procedimento"));
                filtertmp.setProcesso(request.getParameter("processo"));
                filtertmp.setLimit(filter.getLimit());
                filtertmp.setOffset(filter.getOffset());
                filtertmp.setOrderColumn(filter.getOrderColumn());
                filtertmp.setOrderDirection(filter.getOrderDirection());
                filter = filtertmp;
            }
            request.getSession().setAttribute("entiSelezionaProcesso", filter);
            //^^CS MODIFICA Integer count = countListaProcessi();
            List<ProcessoDTO> processi = processiService.getListaProcessi(filter, procedimentoEnte);
            Integer records = (processi.size() < maxResult) ? processi.size() : processi.size() / maxResult;
            //Integer totalRecords = count; ^^CS MODIFICA
            Integer totalRecords = filter.getTotale();
            totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));

            GridProcessi json = new GridProcessi();
            json.setPage(page);
            json.setRecords(records);
            json.setTotal(totalRecords);
            json.setRows(processi);
            model.addAttribute("json", json.toString());
            return AJAX;
        } catch (ParseException ex) {
            Log.APP.error("Si è verificato un errore cercando di valorizzare il filtro di ricerca", ex);
            Message messaggi = new Message();
            messaggi.getMessages().add("Errore nella ricerca dei procedimenti");
            model.addAttribute("message", messaggi);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_ENTE_SELEZIONAPROCESSO_AJAX, "Errore nell'esecuzione del controller /ente/selezionaProcesso/ajax", ex, null, utente);
            erroriAction.saveError(errore);
            return PAGE_ADD_PROCEDIMENTI;
        }
    }

    @RequestMapping("/ente/salvaProcesso")
    public String salvaProcesso(Model model, HttpServletRequest request, HttpServletResponse response) {
        Integer idProcedimento;
        Utente utente = utentiService.getUtenteConnesso(request);
        if (!Utils.e(request.getParameter("idProc"))) {
            idProcedimento = Integer.valueOf(request.getParameter("idProc"));
            request.getSession().setAttribute(CODICE_PROCEDIMENTO, idProcedimento);
        } else {
            idProcedimento = (Integer) request.getSession().getAttribute(CODICE_PROCEDIMENTO);
        }
        Integer idEnte = (Integer) request.getSession().getAttribute(ID_ENTE);
//        Integer idEnte = (Integer) request.getSession().getAttribute(CODICE_ENTE);
        Integer idProcesso = null;
        if (!Utils.e(request.getParameter("idProcesso"))) {
            idProcesso = Integer.valueOf(request.getParameter("idProcesso"));
        }
        ProcedimentiEnti procedimentoEnte = procedimentiService.findProcedimentiEnti(idEnte ,idProcedimento);
        if (procedimentoEnte == null) {
            //Devo creare la relazione
            procedimentoEnte = new ProcedimentiEnti();
            Procedimenti procedimento = procedimentiService.findProcedimentoByIdProc(idProcedimento);
            procedimentoEnte.setIdProc(procedimento);
            Enti ente = entiService.findByIdEnte(idEnte);
            procedimentoEnte.setIdEnte(ente);
        }
        Processi processo = processiService.findProcessoById(idProcesso);
        procedimentoEnte.setIdProcesso(processo);
        try {
            entiAction.salvaProcedimentoEnte(procedimentoEnte);
        } catch (Exception ex) {
            Log.APP.error("Si è verificato un errore salvando i procedimento", ex);
            Message messaggi = new Message();
            messaggi.getMessages().add("Errore nella ricerca dei procedimenti");
            model.addAttribute("message", messaggi);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_ENTE_SALVAPROCESSO, "Errore nell'esecuzione del controller /ente/salvaProcesso", ex, null, utente);
            erroriAction.saveError(errore);
        }
        return REDIRECT_SELEZIONAPROCESSO;
    }
    //MESSAGGI UTENTE

    private String messaggioOperazioneEffettuata(String input) {
        if (input != null && input.length() != 0) {
            List<String> inputlist = Arrays.asList(input.split("#"));
            Character tipoOperazione = inputlist.get(0).charAt(0);
            if ((inputlist.size() == 2) && (inputlist.get(0).length() == 1)) {
                String parametri = inputlist.get(1);
                switch (tipoOperazione) {
                    case 'M':
                        return "Salvataggio delle modifiche apportate all'ente " + parametri;
                    case 'A':
                        return "Salvataggio in database del nuovo ente: " + parametri;
                    case 'E':
                        return "Rimozione dell'ente: " + parametri;
                    case 'D':
                        return "Modifica degli endoprocedimenti associati all'ente " + parametri;
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
