package it.wego.cross.controller;

import com.google.common.base.Strings;
import it.wego.cross.actions.ErroriAction;
import it.wego.cross.actions.UtentiAction;
import it.wego.cross.beans.JsonResponse;
import it.wego.cross.beans.ProcedimentoPermessiBean;
import it.wego.cross.beans.grid.GridProcedimentiEnte;
import it.wego.cross.beans.grid.GridUtentiBean;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.constants.Constants;
import it.wego.cross.dao.AuthorizationDao;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.*;
import it.wego.cross.entity.view.ProcedimentiLocalizzatiView;
import it.wego.cross.serializer.FilterSerializer;
import it.wego.cross.serializer.UtentiSerializer;
import it.wego.cross.service.ProcedimentiService;
import it.wego.cross.service.UtentiService;
import it.wego.cross.utils.Log;
import it.wego.cross.validator.IsValid;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UtentiController extends AbstractController {

    //variabili relative alle comunicazione utente sulle operazioni effettuate
    private final String OPERAZIONE_EFFETTUATA = "operazioneeffettuata";
    private final Character OPERAZIONE_MODIFICA = 'M';
    private final Character OPERAZIONE_AGGIUNTA = 'A';
    private final Character OPERAZIONE_ELIMINA = 'E';
    private final Character OPERAZIONE_RIATTIVA = 'R';
    //
    private final String HOMEPAGE_UTENTI = "utente_index";
    private final String PAGE_ADD_USER = "utente_aggiungi";
    private final String PAGE_MODIFY_USER = "utente_modifica";
    private final String SALVA_ANAGRAFICA = "salvaAnagrafica";
    private final String REDIRECT_HOMEPAGE = "redirect:/utenti/index.htm";
    private final String AJAX = "ajax";
    private static final String COD_UTENTE_SELEZIONATO = "user_id_selected";
    private static final String COD_ENTE_SELEZIONATO = "ente_id_selezionato";
    private static final String UTENTI_PROCEDIMENTI_FILTER = "utenti_procedimenti_filter";
    private static final String filtroRicercaUtenti = "filtroRicercaUtenti";
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private UtentiAction utentiAction;
    @Autowired
    private ProcedimentiService procedimentiService;
    @Autowired
    private FilterSerializer filterSerializer;
    @Autowired
    protected Validator validator;
    @Autowired
    private IsValid isValid;
    @Autowired
    private UtentiSerializer utentiSerializer;
    @Autowired
    private AuthorizationDao authorizationDao;

    
    @RequestMapping("/utenti/index/ajax")
    public String listAjax(Model model,
            HttpServletRequest request,
            HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        Utente utente = utentiService.getUtenteConnesso(request);
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        String column = paginator.getSidx();
        if ("nominativo".equalsIgnoreCase(column)) {
            column = "cognome";
        }
        String order = paginator.getSord();
        Integer firstRecord = (page * maxResult) - maxResult;
        Filter filter = (Filter) request.getSession().getAttribute(filtroRicercaUtenti);
        if (filter == null) {
            filter = new Filter();
        }
        filter.setLimit(maxResult);
        filter.setOffset(firstRecord);
        filter.setOrderColumn(column);
        filter.setOrderDirection(order);
        GridUtentiBean json = new GridUtentiBean();
        try {
            filter.setNome((String) request.getParameter("nome"));
            filter.setCodiceFiscale((String) request.getParameter("codiceFiscale"));
            filter.setCognome((String) request.getParameter("cognome"));
            filter.setUsername((String) request.getParameter("username"));
            filter.setUserStatus((String) request.getParameter("userStatus"));
            filter.setSuperuser((String) request.getParameter("superuser"));
            Long count = utentiService.countAll(filter);
            List<Utente> utenti = utentiService.findAll(filter);
            List<UtenteDTO> users = new ArrayList<UtenteDTO>();
            for (Utente u : utenti) {
                UtenteDTO user = utentiSerializer.serializeUtente(u);
                users.add(user);
            }
            Integer totalRecords = count.intValue();
            totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
            json.setPage(page);
            json.setRecords(count.intValue());
            json.setTotal(totalRecords);
            json.setRows(users);
        } catch (Exception e) {
            String msg = "Impossibile visualizzare la lista di utenti. Impossibile effettuare la ricerca.";
            json.setErrors(Arrays.asList(msg));
            Log.APP.error(msg, e);
        }
        model.addAttribute("json", json.toString());
        return AJAX;
    }

    @RequestMapping("/utenti/index")
    public String list(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute(OPERAZIONE_EFFETTUATA) String operazioneEffettuataatt) {
        Filter filter = (Filter) request.getSession().getAttribute(filtroRicercaUtenti);
        if (filter == null) {
            filter = new Filter();
        }
        model.addAttribute("filtroRicerca", filter);
        //Azzero i parametri in sessione
        String messaggiooperazioneeffettuata = messaggioOperazioneEffettuata(operazioneEffettuataatt);
        model.addAttribute("messaggiooperazioneeffettuata", messaggiooperazioneeffettuata);
        request.getSession().setAttribute(COD_UTENTE_SELEZIONATO, null);
        request.getSession().setAttribute(COD_ENTE_SELEZIONATO, null);
        request.getSession().setAttribute(UTENTI_PROCEDIMENTI_FILTER, null);
        return HOMEPAGE_UTENTI;
    }

    @RequestMapping("/utenti/aggiungi")
    public String add(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute UtenteDTO utente,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
        String action = request.getParameter("action");
        Utente utenteConnesso = utentiService.getUtenteConnesso(request);
        if (action != null && "add".equals(action)) {
            validator.validate(utente, result);
            List<String> err = isValid.Utente(result, utente);
            utente.setStatus(Constants.UTENTE_ATTIVO);
            utente.setDataAttivazione(new Date());
            if(!(utente.getPassword()!=null && utente.getPassword()!="")){
            	err.add( messageSource.getMessage("error.UtenteDTO.password", null, Locale.getDefault()));
            }
             boolean isCorrect = false;
            if (err.isEmpty()) {
                Utente u = utentiService.findUtenteDaUsername(utente.getUsername());
                if (u != null) {
                    String message = messageSource.getMessage("errore.user.duplicate", null, Locale.getDefault());
                    Message error = new Message();
                    error.setMessages(Arrays.asList(message));
                    request.setAttribute("message", error);
                } else {
                    try {
                        Utente utentedb = UtentiSerializer.serializeUtenteDTOSenzaRuoli(utente);
                        if (!Strings.isNullOrEmpty(utente.getPassword())) {
                            utentedb.setPassword(utentiService.encodePasswordWithSsha(utente.getPassword()));
                        }
                        utentiAction.salvaUtente(utentedb);
                        isCorrect = true;
                    } catch (Exception ex) {
                        String message = messageSource.getMessage("errore.user.insert", null, Locale.getDefault());
                        Message error = new Message();
                        error.setMessages(Arrays.asList(message));
                        request.setAttribute("message", error);
                        Log.APP.error(message, ex);
                    }
                }
            } else {
                Message error = new Message();
                error.setMessages(err);
                request.setAttribute("message", error);
            }
            if (isCorrect) {
                redirectAttributes.addFlashAttribute(OPERAZIONE_EFFETTUATA, OPERAZIONE_AGGIUNTA + "#" + utente.getNome() + " " + utente.getCognome());
                return REDIRECT_HOMEPAGE;
            } else {
                model.addAttribute("utente", utente);
                return PAGE_ADD_USER;
            }
        } else {
            return PAGE_ADD_USER;
        }
    }

    @RequestMapping("/utenti/modifica")
    public String modify(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute UtenteDTO utente,
            BindingResult result,
            @ModelAttribute("paginator") JqgridPaginator paginator,
            final RedirectAttributes redirectAttributes) {
        String action = request.getParameter("action");
        Utente utenteConnesso = utentiService.getUtenteConnesso(request);
        if (action != null && SALVA_ANAGRAFICA.equals(action)) {
            validator.validate(utente, result);
            List<String> err = isValid.Utente(result, utente);
            boolean isCorrect = false;
            if (err.isEmpty()) {
                try {
                    utentiAction.modificaUtente(utente);
                    isCorrect = true;
                } catch (Exception ex) {
                    String message = messageSource.getMessage("errore.user.insert", null, Locale.getDefault());
                    Message error = new Message();
                    error.setMessages(Arrays.asList(message));
                    if (ex.getMessage().contains("Duplicate")) {
                        error.setMessages(Arrays.asList("Il codice fiscale o il nome utente immessi risultano già presenti nel database. Controllore se l'utente è già presente come disabilitato."));
                    }
                    request.setAttribute("message", error);
                    Log.APP.error(message, ex);
                }
            } else {
                isCorrect = false;
                Message error = new Message();
                error.setMessages(err);
                request.setAttribute("message", error);
            }
            if (isCorrect) {
                redirectAttributes.addFlashAttribute(OPERAZIONE_EFFETTUATA, OPERAZIONE_MODIFICA + "#" + utente.getNome() + " " + utente.getCognome());
                return REDIRECT_HOMEPAGE;
            } else {
                model.addAttribute("utente", utente);
                return PAGE_MODIFY_USER;
            }
        } else {
            utente = utentiSerializer.serializeUtente(utentiService.findUtenteByIdUtente(utente.getIdUtente()));
            model.addAttribute("utente", utente);
            model.addAttribute("tree", utentiService.getAlberoEntiRuoli(utente.getIdUtente()));
            return PAGE_MODIFY_USER;
        }
    }

    @RequestMapping("/utenti/modifica/gestisciruoliAjax")
    public String gestisciRuoliAjax(Model model, HttpServletRequest request, HttpServletResponse response, String select, String key, Integer parent, Integer idUtente) {
        Utente utenteConnesso = utentiService.getUtenteConnesso(request);
        JsonResponse json = new JsonResponse();
        try {
            if (select != null && select.equalsIgnoreCase("true")) {
                // nuova selezione
                utentiAction.salvaRuolo(idUtente, parent, key);
                UtenteRuoloEnte ure = authorizationDao.getRuoloByKey(idUtente,  parent, key);
                Map<String,String> mappa = new HashMap<String, String>();
                mappa.put("idRuoloEnte",ure.getIdUtenteRuoloEnte().toString());
                json.setAttributes(mappa);
            }
            if (select != null && select.equalsIgnoreCase("false")) {
                // elimina
                utentiAction.eliminaRuolo(idUtente, parent, key);
            }
            json.setSuccess(Boolean.TRUE);
            json.setMessage("Aggiornamento effettuato con successo");
        } catch (Exception ex) {
            Log.APP.error("Si è verificato un errore nel controller /utenti/modifica/gestisciruoliAjax ", ex);
            json.setSuccess(Boolean.FALSE);
            json.setMessage(ex.getMessage());
        }
        model.addAttribute("json", json.toString());
        return AJAX;
    }

    @RequestMapping("/utenti/elimina")
    public String delete(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            final RedirectAttributes redirectAttributes) {
        Utente utenteConnesso = utentiService.getUtenteConnesso(request);
        int codUtente = Integer.valueOf(request.getParameter("codUtente"));
        try {
            Utente utente = utentiService.findUtenteByIdUtente(codUtente);
            utentiAction.disabilitaUtente(codUtente);
            redirectAttributes.addFlashAttribute(OPERAZIONE_EFFETTUATA, OPERAZIONE_ELIMINA + "#" + utente.getNome() + " " + utente.getCognome());
        } catch (Exception ex) {
            String message = messageSource.getMessage("errore.user.delete", null, Locale.getDefault());
            Log.APP.error(message, ex);
            Message error = new Message();
            error.setMessages(Arrays.asList(message));
            request.setAttribute("message", error);
        }
        return REDIRECT_HOMEPAGE;
    }

    @RequestMapping("/utenti/attiva")
    public String resume(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            final RedirectAttributes redirectAttributes) {
        int codUtente = Integer.valueOf(request.getParameter("codUtente"));
        Utente utenteConnesso = utentiService.getUtenteConnesso(request);
        try {
            Utente utente = utentiService.findUtenteByIdUtente(codUtente);
            utentiAction.riabilitaUtente(codUtente);
            redirectAttributes.addFlashAttribute(OPERAZIONE_EFFETTUATA, OPERAZIONE_RIATTIVA + "#" + utente.getNome() + " " + utente.getCognome());
        } catch (Exception ex) {
            String message = messageSource.getMessage("errore.user.delete", null, Locale.getDefault());
            Log.APP.error(message, ex);
            Message error = new Message();
            error.setMessages(Arrays.asList(message));
            request.setAttribute("message", error);
        }
        return REDIRECT_HOMEPAGE;
    }

    @RequestMapping("/utenti/procedimenti/select/ajax")
    public String listProcedimentiAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        GridProcedimentiEnte json = new GridProcedimentiEnte();
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            Integer idUtenteRuoloEnte = Integer.parseInt(request.getParameter("idUtenteRuoloEnte"));
            UtenteRuoloEnte ure = authorizationDao.getUtenteRuoloEnte(idUtenteRuoloEnte);
            Integer maxResult = Integer.parseInt(paginator.getRows());
            Integer page = Integer.parseInt(paginator.getPage());
            String column = paginator.getSidx();
            String order = paginator.getSord();
            Integer firstRecord = (page * maxResult) - maxResult;
            Filter filter = filterSerializer.getSearchFilter(request);
            filter.setIdUtente(ure.getIdUtente().getIdUtente());
            filter.setPage(page);
            filter.setLimit(maxResult);
            filter.setOffset(firstRecord);
            filter.setOrderColumn(column);
            filter.setOrderDirection(order);
            filter.setIdEnte(ure.getIdEnte().getIdEnte());
            request.getSession().setAttribute(UTENTI_PROCEDIMENTI_FILTER, filter);
            Long count = procedimentiService.countProcedimentiLocalizzati("it", filter);
            List<ProcedimentiLocalizzatiView> procedimenti = procedimentiService.getProcedimentiLocalizzati("it", filter);
            List<ProcedimentoPermessiBean> procedimentiPermessi = new ArrayList<ProcedimentoPermessiBean>();
            for (ProcedimentiLocalizzatiView p : procedimenti) {
                ProcedimentoPermessiBean procedimento = procedimentiService.getProcedimentoPermessiBean(ure.getIdUtente().getIdUtente(), ure.getIdEnte().getIdEnte(), ure.getCodPermesso().getCodPermesso(), p.getIdProc(), "it");
                procedimento.setCodPermesso(ure.getCodPermesso().getCodPermesso());
                procedimento.setDescrizionePermesso(ure.getCodPermesso().getDescrizione());
                procedimentiPermessi.add(procedimento);
            }
            Integer totalRecords = count.intValue();
            totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));

            json.setPage(page);
            json.setRecords(count.intValue());
            json.setTotal(totalRecords);
            json.setRows(procedimentiPermessi);
            model.addAttribute("json", json.toString());
        } catch (ParseException ex) {
            String msg = "Si è verificato un errore cercando di eseguire una query di ricerca";
            Log.APP.error(msg, ex);
            json.setErrors(Arrays.asList(msg));
        }
        return AJAX;
    }

    @RequestMapping("/utenti/procedimenti/cambiostato/ajax")
    public String selezionaProcedimento(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirect,
            Integer idUtente,
            Integer idEnte,
            Integer idProcedimento,
            String codPermesso) {
        Utente utenteConnesso = utentiService.getUtenteConnesso(request);
        JsonResponse json = new JsonResponse();
        try {
            UtenteRuoloEnte ure = authorizationDao.getRuoloByKey(idUtente, idEnte, codPermesso);
            ProcedimentiEnti pe = procedimentiService.findProcedimentiEnti(idEnte, idProcedimento);
            UtenteRuoloProcedimento urp = authorizationDao.getUtenteRuoloProcedimento(ure.getIdUtenteRuoloEnte(), pe.getIdProcEnte());
            if (urp != null) {
                utentiAction.eliminaRuoloProcedimento(urp);
            } else {
                utentiAction.inserisciRuoloProcedimento(ure, pe);
            }
            json.setSuccess(Boolean.TRUE);
            json.setMessage("Aggiornamento effettuato con successo");
        } catch (Exception ex) {
            Log.APP.error("Si è verificato un errore nel controller /utenti/modifica/gestisciruoliAjax ", ex);
            json.setSuccess(Boolean.FALSE);
            json.setMessage(ex.getMessage());
        }
        model.addAttribute("json", json.toString());
        return AJAX;
    }

    private String messaggioOperazioneEffettuata(String input) {
        if (input != null && input.length() != 0) {
            List<String> inputlist = Arrays.asList(input.split("#"));
            Character tipoOperazione = inputlist.get(0).charAt(0);
            if ((inputlist.size() == 2) && (inputlist.get(0).length() == 1)) {
                String parametri = inputlist.get(1);
                switch (tipoOperazione) {
                    case 'M':
                        return "Salvataggio delle modifiche apportate all'utente " + parametri;
                    case 'A':
                        return "Salvataggio in database del nuovo utente: " + parametri;
                    case 'E':
                        return "Scollegamento dell'utente: " + parametri;
                    case 'R':
                        return "Riattivazione dell'utente: " + parametri;
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
