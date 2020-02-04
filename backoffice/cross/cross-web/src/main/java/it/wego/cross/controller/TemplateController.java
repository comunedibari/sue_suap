package it.wego.cross.controller;

/**
 *
 * @original_author : Gabriele Muscas [gabriele.muscas@wego.it]
 */
import it.wego.cross.dto.ProcedimentoDTO;
import it.wego.cross.dto.TemplateDTO;
import it.wego.cross.dto.ProcessoDTO;
import it.wego.cross.dto.EventoTemplateDTO;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.EnteDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.actions.ErroriAction;
import it.wego.cross.actions.TemplateAction;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.beans.grid.*;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.entity.EventiTemplate;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.Utente;
import it.wego.cross.serializer.FilterSerializer;
import it.wego.cross.serializer.ProcedimentiSerializer;
import it.wego.cross.serializer.ProcessiSerializer;
import it.wego.cross.service.EventiService;
import it.wego.cross.service.ProcedimentiService;
import it.wego.cross.service.TemplateService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TemplateController extends AbstractController {

    private final Character OPERAZIONE_MODIFICA_COLLEGAMENTO = 'M';
    private final Character OPERAZIONE_AGGIUNTA_COLLEGAMENTO = 'A';
    private final Character OPERAZIONE_ELIMINA_COLLEGAMENTO = 'E';
    private final Character OPERAZIONE_MODIFICA_TEMPLATE = 'm';
    private final Character OPERAZIONE_AGGIUNTA_TEMPLATE = 'a';
    private final Character OPERAZIONE_ELIMINA_TEMPLATE = 'e';
    private final String OPERAZIONE_EFFETTUATA = "messaggio";
    private final String OPERAZIONE_EFFETTUATA_STATICO = "messaggiooperazioneeffettuata";
    @Autowired
    protected Validator validator;
    @Autowired
    private EventiService eventiService;
    @Autowired
    private ProcedimentiService procedimentiService;
    @Autowired
    private TemplateAction templateAction;
    @Autowired
    private ErroriAction erroriAction;
    @Autowired
    private TemplateService templateService;
    //Pages
    private static final String AJAX_PAGE = "ajax";
    private static final String eventiTemplateLista = "eventiTemplate_lista";
    private static final String templateLista = "template_lista";
    private static final String MODIFICA = "template_modifica";

    /**
     * Lista eventi template
     */
    @RequestMapping("/gestione/eventoTemplate/lista")
    public String listaEventiTemplate(Model model, HttpServletRequest request, HttpServletResponse response) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            List<ProcedimentiEnti> procedimentiEnti = procedimentiService.findAllProcedimentiEnti();
            List<ProcessoDTO> listaProcessi = new ArrayList<ProcessoDTO>();
            List<String> processiId = new ArrayList<String>();
            List<String> procedimentiId = new ArrayList<String>();
            List<ProcedimentoDTO> listaProcedimenti = new ArrayList<ProcedimentoDTO>();
            List<EnteDTO> listaEnti = eventiService.getTuttiEnti();
            for (ProcedimentiEnti pe : procedimentiEnti) {
                if (!(procedimentiId.contains(pe.getIdEnte().getIdEnte()+"-"+pe.getIdProc().getIdProc()))) {
                    ProcedimentoDTO procedimento = ProcedimentiSerializer.serialize(pe.getIdProc());
                    procedimento.setIdEnteDestinatario(pe.getIdEnte().getIdEnte());
                    listaProcedimenti.add(procedimento);
                    procedimentiId.add(pe.getIdEnte().getIdEnte()+"-"+ pe.getIdProc().getIdProc());
                }
                if (pe.getIdProcesso() != null && !(processiId.contains(pe.getIdEnte().getIdEnte()+"-"+pe.getIdProcesso().getIdProcesso()))) {
                    ProcessoDTO processo = ProcessiSerializer.serilize(pe.getIdProcesso());
                    processo.setIdEnte(pe.getIdEnte().getIdEnte());
                    listaProcessi.add(processo);
                    processiId.add(pe.getIdEnte().getIdEnte()+"-"+ pe.getIdProcesso().getIdProcesso());
                }
            }
            List<EventoDTO> listaEventi = eventiService.findAllProcessiEventi();
            List<TemplateDTO> listaTemplate = eventiService.getTemplates(new Filter());
            Filter filter = (Filter) request.getSession().getAttribute("eventoTemplateLista");
            if (filter == null) {
                filter = new Filter();
            }
            model.addAttribute("filtroRicerca", filter);
            model.addAttribute("listaEventi", listaEventi);
            model.addAttribute("listaProcessi", listaProcessi);
            model.addAttribute("listaEnti", listaEnti);
            model.addAttribute("listaProcedimenti", listaProcedimenti);
            model.addAttribute("listaTemplate", listaTemplate);

        } catch (Exception ex) {
            Message msg = new Message();
            msg.setMessages(Arrays.asList("Impossibile effettuare la ricerca."));
            Log.APP.error(msg.toString(), ex);
            response.setHeader("HTTP/1.1", "500 Internal Server Error");
        }
        return eventiTemplateLista;
    }

    @RequestMapping("/gestione/eventoTemplate/lista/ajax")
    public String listaEventiTemplateAjax(Model model,
            @ModelAttribute EventoTemplateDTO eventoTemplate,
            BindingResult result, JqgridPaginator paginator,
            HttpServletRequest request,
            HttpServletResponse response) {
        Utente utente = utentiService.getUtenteConnesso(request);
        String msg = "";
        try {
            String action = request.getParameter("action");
            if (action != null) {
                if (action.equals("salva") || action.equals("modifica")) {
                    msg="Errore in fase di inserimento";
                    GridIdStringListBean json = new GridIdStringListBean();
                    EventoTemplateDTO eventoTemplateDB = eventiService.getTemplates(eventoTemplate);
                    String desEvento = eventiService.findProcessiEventiById(eventoTemplate.getIdEvento()).getDesEvento();
                    List<EventiTemplate> eventiTemplate = templateService.getEventiTemplate(eventoTemplate.getIdEvento(), eventoTemplate.getIdEnte(), eventoTemplate.getIdProcedimento(), eventoTemplate.getIdTemplate());
                    boolean isAlreadyPresent = !eventiTemplate.isEmpty();
                    if (action.equals("salva")) {
                        if (isAlreadyPresent) {
                            json.setErrors(Arrays.asList("Impossibile salvare. Template gia esistente"));
                        } else {
                            templateAction.salvaEventoTemplate(eventoTemplate);
                            String messaggio = messaggioOperazioneEffettuata(OPERAZIONE_AGGIUNTA_COLLEGAMENTO + "#" + desEvento);
                            json.setMessages(Arrays.asList(messaggio));
                        }
                    }
                    if (action.equals("modifica")) {
                        msg="Errore in fase di modifica";
                        if (eventoTemplateDB != null) {
                            if (isAlreadyPresent) {
                                json.setErrors(Arrays.asList("Impossibile salvare. Template gia esistente"));
                            } else {
                                templateAction.salvaEventoTemplate(eventoTemplate);
                                String messaggio = messaggioOperazioneEffettuata(OPERAZIONE_MODIFICA_COLLEGAMENTO + "#" + desEvento);
                                json.setMessages(Arrays.asList(messaggio));
                            }
                        } else {
                            json.setErrors(Arrays.asList("Impossiile modificare, il template non esiste"));
                        }
                    }
                    model.addAttribute("json", json.toString());
                }
                if (action.equals("elimina")) {
                    msg="Errore in fase di cancellazione";
                    GridIdStringListBean json = new GridIdStringListBean();
                    templateAction.eliminaEventoTemplate(eventoTemplate);
                    String messaggio = messaggioOperazioneEffettuata(OPERAZIONE_ELIMINA_COLLEGAMENTO + "#" + ".");
                    json.setMessages(Arrays.asList(messaggio));
                    model.addAttribute("json", json.toString());
                }
            } else {
                msg="Errore in fase di ricerca";
                Integer maxResult = Integer.parseInt(paginator.getRows());
                Integer page = Integer.parseInt(paginator.getPage());
                String column = paginator.getSidx();
                String order = paginator.getSord();
                Integer firstRecord = (page * maxResult) - maxResult;
                Filter filter = (Filter) request.getSession().getAttribute("filtroRicerca");
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
                    filter.setDescEvento(eventoTemplate.getDescEvento());
                    filter.setIdEnte(eventoTemplate.getIdEnte());
                    filter.setIdProcedimento(eventoTemplate.getIdProcedimento());
                    filter.setIdTemplate(eventoTemplate.getIdTemplate());
                }
                request.getSession().setAttribute("eventoTemplateLista", filter);
                List<EventoTemplateDTO> lista = eventiService.findEventiTemplate(filter);
                GridEventoTemplateBean json = new GridEventoTemplateBean();
                json.setRows(lista);
                json.setTotal(filter.getTotale());
                json.setPage(Integer.parseInt(paginator.getPage()));
                json.setRecords(lista.size());
                model.addAttribute("json", json.toString());
            }
        } catch (Exception ex) {
            GridEventoTemplateBean json = new GridEventoTemplateBean();
            json.setErrors(Arrays.asList(msg));
            model.addAttribute("json", json.toString());
            Log.APP.error(msg, ex);
        }
        return AJAX_PAGE;
    }

    @RequestMapping("/gestione/template/lista")
    public String listaTemplate(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute(OPERAZIONE_EFFETTUATA) String operazioneEffettuataatt) {
        Filter filter = (Filter) request.getSession().getAttribute("filterListaTemplate");
        if (filter == null) {
            filter = new Filter();
        }
        model.addAttribute("filtroRicerca", filter);
        model.addAttribute(OPERAZIONE_EFFETTUATA, operazioneEffettuataatt);
        return templateLista;
    }

    @RequestMapping("/gestione/template/lista/ajax")
    public String listaTemplateAjax(Model model, @ModelAttribute TemplateDTO template, JqgridPaginator paginator, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            String action = request.getParameter("action");
            if (action != null) {
                if (action.equals("elimina")) {
                    GridIdStringListBean json = new GridIdStringListBean();
                    Filter filter = new Filter();
                    filter.setIdTemplate(template.getIdTemplate());
                    List<EventoTemplateDTO> lista = eventiService.findEventiTemplate(filter);
                    if (lista != null && lista.size() > 0) {
                        json.setErrors(Arrays.asList("Impossibile eliminare. Il documento Template e' associato ad almeno un evento"));
                    } else {
                        templateAction.eliminaTemplate(template);
                        String messaggio = messaggioOperazioneEffettuata(OPERAZIONE_ELIMINA_TEMPLATE + "#" + ".");
                        json.setMessages(Arrays.asList(messaggio));
                    }
                    model.addAttribute("json", json.toString());
                }
                if (action.equals("modifica")) {
                    templateAction.salvaTemplate(template);
                }
                if (action.equals("salva")) {
                    GridIdStringListBean json = new GridIdStringListBean();
                    if (template.getAllegato() != null && template.getAllegato().getFile() != null && template.getAllegato().getFile().getSize() > 0) {
                        templateAction.salvaTemplate(template);
                    } else {
                        json.setErrors(Arrays.asList("L'allegato e' obbligatorio"));
                    }
                    model.addAttribute("json", json.toString());
                }
            } else {
                Integer maxResult = Integer.parseInt(paginator.getRows());
                Integer page = Integer.parseInt(paginator.getPage());
                String column = paginator.getSidx();
                String order = paginator.getSord();
                Integer firstRecord = (page * maxResult) - maxResult;
                Filter filter = (Filter) request.getSession().getAttribute("filterListaTemplate");
                if (filter == null) {
                    filter = FilterSerializer.getSearchFilter(paginator);
                }
                filter.setPage(page);
                filter.setLimit(maxResult);
                filter.setOffset(firstRecord);
                filter.setOrderColumn(column);
                filter.setOrderDirection(order);

                GridTemplateBean json = new GridTemplateBean();
                String tipoFiltro = (String) request.getParameter("tipoFiltro");
                if ((!Utils.e(tipoFiltro) && tipoFiltro.toUpperCase().equals("UTENTE"))) {
                    filter.setIdEvento(template.getIdEvento());
                    filter.setIdEnte(template.getIdEnte());
                    filter.setIdProcedimento(template.getIdProcedimento());
                    filter.setIdTemplate(template.getIdTemplate());
                    filter.setDescrizioneTemplate(template.getDescrizioneTemplate());
                }
                request.getSession().setAttribute("filterListaTemplate", filter);
                List<TemplateDTO> listaTemplate = eventiService.getTemplates(filter);
                json.setRows(listaTemplate);
                json.setTotal(filter.getTotale());
                json.setPage(Integer.parseInt(paginator.getPage()));
                json.setRecords(listaTemplate.size());
                json.setRecords(listaTemplate.size());
                model.addAttribute("json", json.toString());
            }
        } catch (Exception ex) {
            String msg = "Impossibile effettuare l'operazione. E' probabile che il documento template sia associato a qualche evento";
            GridIdStringListBean json = new GridIdStringListBean();
            json.setErrors(Arrays.asList(msg));
            model.addAttribute("json", json.toString());
            Log.APP.error(msg, ex);
        }
        return AJAX_PAGE;
    }

    @RequestMapping("/gestione/template/modifica")
    public String modificaTemplate(Model model,
            @ModelAttribute TemplateDTO template,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            template = eventiService.getTemplate(template);
            model.addAttribute("template", template);
        } catch (Exception ex) {
            Message msg = new Message();
            msg.setMessages(Arrays.asList("Impossibile effettuare la ricerca."));
            Log.APP.error(msg.toString(), ex);
            response.setHeader("HTTP/1.1", "500 Internal Server Error");
        }

        return MODIFICA;
    }

    @RequestMapping("/gestione/template/salva")
    public String salvaTemplate(Model model,
            @ModelAttribute TemplateDTO template,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response,
            final RedirectAttributes redirectAttributes) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            List<String> errors = new ArrayList<String>();
            if (template.getDescrizioneTemplate() == null || template.getDescrizioneTemplate().trim().equals("")) {
                errors.add("La descrizione e' obbligatoria");
            }
            if (template.getIdTemplate() == null && (template.getAllegato() == null || template.getAllegato().getFile() == null || template.getAllegato().getFile().getSize() == 0)) {
                errors.add("L'allegato e' obbligatorio");
            }

            Message msg = new Message();
            if (errors.size() > 0) {
                msg.setMessages(errors);
                model.addAttribute("template", template);
                model.addAttribute("message", msg);
            } else {
                TemplateDTO temp = eventiService.getTemplate(template);
                templateAction.salvaTemplate(template);
                String messaggio;
                if (temp != null && temp.getIdTemplate() != null) {
                    messaggio = messaggioOperazioneEffettuata(OPERAZIONE_MODIFICA_TEMPLATE + "#" + template.getDescrizioneTemplate());
                } else {
                    messaggio = messaggioOperazioneEffettuata(OPERAZIONE_AGGIUNTA_TEMPLATE + "#" + template.getDescrizioneTemplate());
                }
                redirectAttributes.addFlashAttribute(OPERAZIONE_EFFETTUATA_STATICO, messaggio);
                return "redirect:/gestione/template/lista.htm";
            }

        } catch (Exception ex) {
            Message msg = new Message();
            msg.setMessages(Arrays.asList("Impossibile effettuare la ricerca."));
            Log.APP.error(msg.toString(), ex);
        }
        return MODIFICA;
    }

    private String messaggioOperazioneEffettuata(String input) {
        if (input != null && input.length() != 0) {
            List<String> inputlist = Arrays.asList(input.split("#"));
            Character tipoOperazione = inputlist.get(0).charAt(0);
            if ((inputlist.size() == 2) && (inputlist.get(0).length() == 1)) {
                String parametri = inputlist.get(1);
                switch (tipoOperazione) {
                    case 'M':
                        return "Salvataggio delle modifiche apportate alla relazione relativa all'evento\" " + parametri + "\"";
                    case 'A':
                        return "Salvataggio in database della nuova relazione relativa all'evento \" " + parametri + "\"";
                    case 'E':
                        return "Eliminazione del collegamento selezionato " + parametri;
                    case 'm':
                        return "Salvataggio delle modifiche apportate al template \" " + parametri + "\"";
                    case 'a':
                        return "Salvataggio in database del template \" " + parametri + "\"";
                    case 'e':
                        return "Eliminazione del template selezionato " + parametri;
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
