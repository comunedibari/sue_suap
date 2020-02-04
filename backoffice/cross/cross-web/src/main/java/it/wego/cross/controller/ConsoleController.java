/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.controller;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.wego.cross.actions.EmailAction;
import it.wego.cross.actions.ErroriAction;
import it.wego.cross.actions.PraticheAction;
import it.wego.cross.beans.ConsoleBean;
import it.wego.cross.beans.JsonResponse;
import it.wego.cross.beans.grid.GridEmailBean;
import it.wego.cross.beans.grid.GridErrori;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.constants.StatiEmail;
import it.wego.cross.constants.Workflow;
import it.wego.cross.dto.EmailDTO;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.PraticaDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Email;
import it.wego.cross.entity.LkStatiMail;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.Utente;
import it.wego.cross.serializer.EmailSerializer;
import it.wego.cross.serializer.PraticheSerializer;
import it.wego.cross.service.ErroriService;
import it.wego.cross.service.MailService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.WorkFlowService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * s
 */
@Controller
@RequestMapping("/console")
public class ConsoleController extends AbstractController {

    private static final String INDEX = "console_index";
    private static final String PRATICHE = "console_pratiche";
    private static final String EMAIL = "console_email";
    private static final String AJAX = "ajax";
    private static final String REDIRECTINDEX = "redirect:/console/index.htm";
    private static final String DETTAGLIO = "console_dettaglio";
    private static final String REDIRECTDETTAGLIO = "redirect:/console/dettaglio.htm";
    private static final String MODIFICAXML = "console_modificaxml";
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private EmailAction emailAction;
    @Autowired
    private MailService mailService;
    @Autowired
    private ErroriService erroriService;
    @Autowired
    private ErroriAction erroriAction;
    @Autowired
    private PraticheAction praticheAction;
    @Autowired
    private PraticheSerializer praticheSerializer;
    @Autowired
    private TaskService taskService;
    @Autowired
    private WorkFlowService workflowService;
    @Autowired
    private EmailSerializer emailSerializer;

    @RequestMapping("/index")
    public String index(ModelMap model, HttpServletRequest request) throws Exception {
        Utente utenteConnesso = utentiService.getUtenteConnesso(request);
        Filter filter = new Filter();
        filter.setConnectedUser(utenteConnesso);
        boolean isSuperuser = String.valueOf(utenteConnesso.getSuperuser()).equalsIgnoreCase("S");
        Long emailNonInviate = mailService.countMailNonInviate(filter, isSuperuser);
        Long praticheNonProtocollate = praticheService.countPraticheNonProtocollate();
        model.addAttribute("praticheNonProtocollate", praticheNonProtocollate);
        model.addAttribute("emailNonInviate", emailNonInviate);
        return INDEX;
    }

    @RequestMapping("/index/ajax")
    public String getErroriSON(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        Filter filter = getFilter(paginator);
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        GridErrori json = new GridErrori();
        //Visualizza solo errori attivi
        filter.setTipoFiltro("T");
        Long count = erroriService.countErrori(filter);
        List<ErroreDTO> dtos = erroriService.getErrori(filter);
        Integer totalRecords = count.intValue();
        totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
        json.setPage(page);
        json.setRecords(count.intValue());
        json.setTotal(totalRecords);
        json.setRows(dtos);
        model.addAttribute("json", json.toString());
        return AJAX;
    }

    @RequestMapping("/index/info")
    public String indexData(ModelMap model, HttpServletRequest request) throws Exception {
        Utente utenteConnesso = utentiService.getUtenteConnesso(request);
        Filter filter = new Filter();
        filter.setConnectedUser(utenteConnesso);
        boolean isSuperuser = String.valueOf(utenteConnesso.getSuperuser()).equalsIgnoreCase("S");
        Long emailNonInviate = mailService.countMailNonInviate(filter, isSuperuser);
        Long praticheNonProtocollate = praticheService.countPraticheNonProtocollate();
        ConsoleBean json = new ConsoleBean();
        json.setEmailNonInviate(emailNonInviate);
        json.setPraticheNonProtocollate(praticheNonProtocollate);
        model.addAttribute("json", json.toString());
        return AJAX;
    }

    private Filter getFilter(JqgridPaginator paginator) throws NumberFormatException {
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
        return filter;
    }

    @RequestMapping("/mail")
    public String mail(HttpServletRequest request, ModelMap model) {
        return EMAIL;
    }

    @RequestMapping("/mail/ajax")
    public String getMailNonInviateJSON(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) throws Exception {
        Filter filter = getFilter(paginator);
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        GridEmailBean json = new GridEmailBean();
        Utente utenteConnesso = utentiService.getUtenteConnesso(request);
        filter.setConnectedUser(utenteConnesso);
        boolean isSuperuser = String.valueOf(utenteConnesso.getSuperuser()).equalsIgnoreCase("S");
        Long count = mailService.countMailNonInviate(filter, isSuperuser);
        List<EmailDTO> dtos = mailService.getMailNonInviate(filter, isSuperuser);
        Integer totalRecords = count.intValue();
        totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
        json.setPage(page);
        json.setRecords(count.intValue());
        json.setTotal(totalRecords);
        json.setRows(dtos);
        model.addAttribute("json", json.toString());
        return AJAX;
    }

    @RequestMapping("/mail/event")
    public String mailGoToPraticaDetail(Model model,
            @RequestParam(value = "evento", required = true) String evento,
            HttpServletRequest request) {
        try {
            Integer idPraticaEvento = Integer.valueOf(evento);
            PraticheEventi evt = praticheService.getPraticaEvento(idPraticaEvento);
            Pratica pratica = evt.getIdPratica();
            request.getSession().setAttribute(SessionConstants.ID_PRATICA_SELEZIONATA, pratica.getIdPratica());
            String urlToRedirect = "/pratica/comunicazione/dettaglio_evento.htm?id_pratica_evento=" + String.valueOf(idPraticaEvento) + "&submit=Dettaglio";
            return "redirect:" + urlToRedirect;
        } catch (Exception ex) {
            Log.APP.error("Non è stato possibile accedere all'evento che ha generato la notifica", ex);
            Message error = new Message();
            error.setMessages(Arrays.asList("Non è stato possibile confermare la lettura della notifica"));
            error.setError(Boolean.TRUE);
            model.addAttribute("message", error);
            return "redirect:/index.htm";
        }
    }

    @RequestMapping("/mail/error")
    public String getdettaglioErroreMailJson(Model model,
            @ModelAttribute("mailId") String mailId) throws Exception {
        Email email = mailService.findByIdEmail(Integer.valueOf(mailId));
        JsonResponse json = JsonResponse.buildJsonResponse(Boolean.TRUE, !Strings.isNullOrEmpty(email.getCorpoRisposta()) ? email.getCorpoRisposta() : "Dettaglio messaggio non presente");
        model.addAttribute("json", json.toString());
        return AJAX;

    }

    @RequestMapping(value = "/mail/resend", method = RequestMethod.POST)
    public String markMailToResend(Model model,
            @ModelAttribute("mailId") String mailId,
            @ModelAttribute("taskId") String taskId,
            @ModelAttribute("destinatario") String destinatario,
            HttpServletRequest request
    ) throws Exception {
        JsonResponse response = null;
        Email email = mailService.findByIdEmail(Integer.valueOf(mailId));
        try {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(destinatario), "Destinatario non valorizzato");
            Preconditions.checkArgument(EmailValidator.getInstance().isValid(destinatario), "Email sintatticamente non valida: " + destinatario);
            email.setEmailDestinatario(destinatario);
            if (!Strings.isNullOrEmpty(taskId)) {
                emailAction.updateEmail(email);
                //Ho un taskid, quindi lo completo
                Map<String, Object> variables = new HashMap<String, Object>();
                Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
                //Il task non può essere null
                boolean isInManualProtocolStatus = workflowService.isInManualProtocolStatus(task);
                if (isInManualProtocolStatus) {
                    variables = workflowService.getVariablesForProtocolTask(email);
                }
                taskService.complete(taskId, variables);
            } else {
                Utente utenteConnesso = utentiService.getUtenteConnesso(request);
                emailAction.rispedisciEmail(email, utenteConnesso);
            }
            response = JsonResponse.buildJsonResponse(Boolean.TRUE, "Operazione eseguita correttamente");
        } catch (Exception ex) {
            LkStatiMail mailDaInviare = mailService.findStatoMailByCodice(StatiEmail.ERRORE_GENERICO);
            email.setStato(mailDaInviare);
            email.setCorpoRisposta(ex.getMessage());
            emailAction.updateEmail(email);
            response = JsonResponse.buildJsonResponse(Boolean.FALSE, ex.getMessage());
        } finally {
            model.addAttribute("json", response.toString());
            return AJAX;
        }
    }

    @RequestMapping(value = "/mail/markAsReceived", method = RequestMethod.POST)
    public String markMailAsReceived(Model model,
            @ModelAttribute("mailId") String mailId,
            @ModelAttribute("taskId") String taskId,
            HttpServletRequest request) throws Exception {
        JsonResponse response = null;
        Email email = mailService.findByIdEmail(Integer.valueOf(mailId));
        Utente utenteConnesso = utentiService.getUtenteConnesso(request);
        try {
            emailAction.markAsReceived(email, taskId, utenteConnesso);
            response = JsonResponse.buildJsonResponse(Boolean.TRUE, "Operazione eseguita correttamente");
        } catch (Exception ex) {
            response = JsonResponse.buildJsonResponse(Boolean.FALSE, ex.getMessage());
        } finally {
            model.addAttribute("json", response.toString());
            return AJAX;
        }
    }

    @RequestMapping("/mail/detail")
    public String getMailDetail(Model model,
            @RequestParam(value = "mailId", required = true) String mailId,
            @RequestParam(value = "taskId", required = false) String taskId
    ) throws Exception {
        Email email = mailService.findByIdEmail(Integer.valueOf(mailId));
        EmailDTO dto = new EmailDTO();
        dto.setIdEmail(email.getIdEmail());
        dto.setEmailDestinatario(email.getEmailDestinatario());
        dto.setOggettoEmail(email.getOggettoEmail());
        dto.setCorpoEmail(email.getCorpoEmail());
        dto.setOggettoRisposta(email.getOggettoRisposta());
        //SAL 4
        dto.setCorpoRisposta(email.getCorpoRisposta());
        if (!Strings.isNullOrEmpty(taskId)) {
            dto.setTaskId(taskId);
        } else {
            Task task = taskService.createTaskQuery().processVariableValueEquals(Workflow.ID_EMAIL, Integer.valueOf(mailId)).singleResult();
            if (task != null) {
                dto.setTaskId(task.getId());
            }
        }
        model.addAttribute("json", gson.toJson(dto));
        return AJAX;

    }

    @Deprecated
    private String getXmlPraticaOriginale(Pratica pratica) throws Exception {
        String praticaOriginale = null;
        if (pratica.getIdStaging() != null && pratica.getIdStaging().getXmlRicevuto() != null) {
            praticaOriginale = new String(pratica.getIdStaging().getXmlRicevuto());
        }
        if (praticaOriginale != null) {
            //Il WS di CROSS fa in ricezione una conversione da ISO-8859-1 
            praticaOriginale = Utils.convertXmlCharset(praticaOriginale, "UTF-8", "ISO-8859-1");
        }
        return praticaOriginale;
    }

    @RequestMapping("/gestisci")
    public String cancellaErrore(Model model,
            @ModelAttribute("idErrore") Integer idErrore,
            @ModelAttribute("azione") String azione, RedirectAttributes redattr) {
        try {
            if ("hide".equals(azione)) {
                erroriAction.setErrorToDeleted(idErrore);
                return REDIRECTINDEX;
            } else if ("dettaglio".equals(azione)) {
                redattr.addFlashAttribute("idErrore", idErrore);
                return REDIRECTDETTAGLIO;
            }
        } catch (Exception e) {
            Log.APP.error("Errore non trovato", e);
            return REDIRECTINDEX;
        }
        return REDIRECTINDEX;
    }

    @RequestMapping("/dettaglio")
    public String dettaglioErrore(Model model,
            @ModelAttribute("idErrore") Integer idErrore) {
        try {
            ErroreDTO errore = erroriService.findErroreById(idErrore);
            model.addAttribute("errore", errore);
            model.addAttribute("idErrore", idErrore);

        } catch (Exception e) {
            Log.APP.error("Errore non trovato", e);
            return DETTAGLIO;
        }
        return DETTAGLIO;
    }
}
