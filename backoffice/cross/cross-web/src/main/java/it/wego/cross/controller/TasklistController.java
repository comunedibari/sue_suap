package it.wego.cross.controller;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import it.wego.cross.actions.EmailAction;
import it.wego.cross.actions.ErroriAction;
import it.wego.cross.beans.BaseResponseBean;
import it.wego.cross.beans.grid.GridNotificheBean;
import it.wego.cross.beans.grid.GridTasklistBean;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.constants.Workflow;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dto.EmailDTO;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.TaskDTO;
import it.wego.cross.entity.Email;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.Utente;
import it.wego.cross.service.MailService;
import it.wego.cross.utils.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author CS
 */
@Controller
public class TasklistController extends AbstractController {

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
    private static final String EMAIL_ERRORE_GENERICO = "E";
    private static final String EMAIL_ERRORE_SERVER = "M";
    private static final String EMAIL_DA_INVIARE = "S";

    @Autowired
    private ErroriAction erroriAction;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ManagementService managementService;
    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private EmailAction emailAction;
    @Autowired
    private MailService mailService;

    @RequestMapping("/workflow/tasklist")
    public ModelAndView tasklistShow(Model model, HttpServletRequest request) {
    	/* Commento per evitare timeout di activi nell attesa di risolvere il problema */
        //Boolean taskListEnabled = (Boolean) request.getSession().getAttribute(SessionConstants.TASKLIST_ENABLED);
        Boolean taskListEnabled = false;
        if (taskListEnabled != null && !taskListEnabled) {
            return new ModelAndView("redirect:/index.htm");
        }
        List<Task> taskList = taskService.createTaskQuery().orderByTaskCreateTime().asc().list();
        model.addAttribute("task_list", taskList);
        return new ModelAndView("task_list_show");
    }

    @RequestMapping("/workflow/tasklist/ajax")
    public String listAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        Utente utente = utentiService.getUtenteConnesso(request);
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        Integer firstRecord = (page * maxResult) - maxResult;

        GridTasklistBean json = new GridTasklistBean();
        try {

            TaskQuery query = taskService.createTaskQuery();
            if (paginator.getSidx().equals("taskDate")) {
                if (paginator.getSord().equalsIgnoreCase("asc")) {
                    query = query.orderByTaskCreateTime().asc();

                } else {
                    query = query.orderByTaskCreateTime().desc();
                }
            }
            if (paginator.getSidx().equals("taskId")) {
                if (paginator.getSord().equalsIgnoreCase("asc")) {
                    query = query.orderByTaskId().asc();

                } else {
                    query = query.orderByTaskId().desc();
                }
            }
//            List<Task> taskList = query.includeProcessVariables().processDefinitionKey("cross_communication_process").listPage(firstRecord, maxResult);
            List<Task> taskList = query.includeProcessVariables().processVariableValueEquals(Workflow.TIPO_TASK, Workflow.TIPO_TASK_TASK).processDefinitionKey("cross_communication_process").listPage(firstRecord, maxResult);
//            List<Task> taskList = query.includeProcessVariables().processDefinitionKey("cross_communication_process").listPage(firstRecord, maxResult);
            for (Task task : taskList) {
                TaskDTO taskDTO = new TaskDTO();
                taskDTO.setTaskId(task.getId());
                taskDTO.setInstanceId(task.getProcessInstanceId());
                taskDTO.setTaskKey(task.getTaskDefinitionKey());
                taskDTO.setName(task.getName());
                taskDTO.setTaskDate(task.getCreateTime());
                taskDTO.setVariables(task.getProcessVariables());
                json.getRows().add(taskDTO);
            }

//            Integer totalRecords = taskList.size();
            Integer totalRecords = (int) taskService.createTaskQuery().includeProcessVariables().processVariableValueEquals(Workflow.TIPO_TASK, Workflow.TIPO_TASK_TASK).processDefinitionKey("cross_communication_process").count();
//            Integer totalRecords = (int) taskService.createTaskQuery().includeProcessVariables().processDefinitionKey("cross_communication_process").count();
            totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
            json.setPage(page);
            json.setRecords((int) taskService.createTaskQuery().count());
            json.setTotal(totalRecords);
            model.addAttribute("json", json.toString());
        } catch (Exception e) {
            String msg = "Impossibile effettuare la ricerca.";
            json.setErrors(Arrays.asList(msg));
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_WORKFLOW_TASKLIST_INDEX_AJAX, "Errore nell'esecuzione del controller /workflow/tasklist/ajax", e, null, utente);
            erroriAction.saveError(errore);
            model.addAttribute("json", json.toString());
            Log.APP.error(msg, e);
        }
        return AJAX_PAGE;
    }

    @RequestMapping("/workflow/task/completeProtocol")
    public ModelAndView processoTaskCompleteProtocol(Model model,
            @RequestParam(value = "taskId", required = true) String taskId,
            @RequestParam(value = "protocolloAutomatico", required = true) Boolean protocolloAutomatico,
            @RequestParam(value = "protocolloSegnatura", required = false) String protocolloSegnatura,
            @RequestParam(value = "protocolloData", required = false) String protocolloData
    ) {
        Map<String, String> result;
        try {
            result = new HashMap<String, String>();
            result.put("success", "true");
            result.put("msg", "Task completato correttamente");

            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("evento_protocollo_riprova", protocolloAutomatico);
            variables.put("evento_protocollo_segnatura", protocolloSegnatura);
            variables.put("evento_protocollo_data", protocolloData);
            taskService.complete(taskId, variables);

        } catch (Exception ex) {
            Log.APP.error("Impossibile completare il task", ex);
            result = ImmutableMap.of("success", "false", "msg", "Impossibile completare il task");
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PROCESSI_PROCESSO_TASK_COMPLETE, "/workflow/task/completeProtocol", ex, null, null);
            erroriAction.saveError(errore);
        }
        model.addAttribute("json", gson.toJson(result));
        return new ModelAndView(AJAX_PAGE);
    }

    @RequestMapping("/workflow/instance/delete")
    public ModelAndView processoInstanceDelete(Model model,
            HttpServletRequest request,
            @RequestParam(value = "instanceId", required = true) String instanceId
    ) {
        Map<String, String> result;
        try {
            it.wego.cross.dto.dozer.UtenteDTO utenteDTO = (it.wego.cross.dto.dozer.UtenteDTO) request.getSession().getAttribute(SessionConstants.UTENTE_CONNESSO_FULL);

            ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
            runtimeService.deleteProcessInstance(instanceId, "Istanza cancellata da " + utenteDTO.getCognome() + " " + utenteDTO.getNome());

            result = new HashMap<String, String>();
            result.put("success", "true");
            result.put("msg", "Istanza eliminata correttamente");

        } catch (Exception ex) {
            Log.APP.error("Impossibile eliminare l'istanza", ex);
            result = ImmutableMap.of("success", "false", "msg", "Impossibile eliminare l'istanza");
            //TODO: recuperare info utente connesso
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PROCESSI_PROCESSO_TASK_COMPLETE, "/workflow/instance/delete", ex, null, null);
            erroriAction.saveError(errore);
        }
        model.addAttribute("json", gson.toJson(result));
        return new ModelAndView(AJAX_PAGE);
    }

    @RequestMapping("/workflow/task/getBrokenEmail")
    public String processoTaskGetBrokenEmail(Model model,
            @RequestParam(value = "taskId", required = true) String taskId) {
        Task task = taskService.createTaskQuery().includeProcessVariables().taskId(taskId).singleResult();
        Map<String, Object> processVariables = task.getProcessVariables();
        Integer idEmail = (Integer) processVariables.get(Workflow.ID_EMAIL);
        Email broken = mailService.findByIdEmail(idEmail);
        String result;
        if (broken != null) {
            EmailDTO dto = new EmailDTO();
            dto.setIdEmail(broken.getIdEmail());
            dto.setIdEvento(broken.getIdPraticaEvento().getIdPraticaEvento());
            dto.setEmailDestinatario(broken.getEmailDestinatario());
            dto.setOggettoEmail(broken.getOggettoEmail());
            dto.setCorpoEmail(broken.getCorpoEmail());
            dto.setOggettoRisposta(broken.getOggettoRisposta());
            result = gson.toJson(dto);
        } else {
            BaseResponseBean response = new BaseResponseBean();
            response.setMessage("Nessuna email in errore");
            result = gson.toJson(response);
        }
        model.addAttribute("json", result);
        return AJAX_PAGE;
    }

    @RequestMapping("/workflow/task/completeMail")
    public ModelAndView processoTaskCompleteMail(Model model,
            @RequestParam(value = "taskId", required = true) String taskId,
            @RequestParam(value = "mailId", required = true) Integer mailId,
            @RequestParam(value = "destinatario", required = true) String destinatario) {
        Map<String, String> result;
        try {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(destinatario), "Destinatario non valorizzato");
            Preconditions.checkArgument(EmailValidator.getInstance().isValid(destinatario), "Email non valida");
            Email email = mailService.findByIdEmail(mailId);
            email.setEmailDestinatario(destinatario);
            emailAction.updateEmail(email);
            Map<String, Object> variables = new HashMap<String, Object>();
            taskService.complete(taskId, variables);
            result = new HashMap<String, String>();
            result.put("success", "true");
            result.put("msg", "Task completato correttamente");
        } catch (Exception ex) {
            Log.APP.error("Impossibile completare il task", ex);
            result = ImmutableMap.of("success", "false", "msg", "Impossibile completare il task: " + ex.getMessage());
            //TODO: recuperare info utente connesso
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PROCESSI_PROCESSO_TASK_COMPLETE, "/workflow/task/completeMail", ex, null, null);
            erroriAction.saveError(errore);
        }
        model.addAttribute("json", gson.toJson(result));
        return new ModelAndView(AJAX_PAGE);
    }

    @RequestMapping("/workflow/notifiche/ajax/count")
    public String countNotifichePerUtente(Model model, HttpServletRequest request) {
        Utente connectedUser = utentiService.getUtenteConnesso(request);
        String username = connectedUser.getUsername();
        //Fisso a 5 il numero di risultati
        Integer maxResult = 5;
        GridNotificheBean json = new GridNotificheBean();
        /* Commento count della query per evitare timeout di activi nell attesa di risolvere il problema */
        //long totalCount = taskService.createTaskQuery().taskInvolvedUser(username).processVariableValueEquals(Workflow.TIPO_TASK, Workflow.TIPO_TASK_NOTIFICA).count();
        long totalCount =5;
        /* Commento count della query per evitare timeout di activi nell attesa di risolvere il problema */
        //List<Task> taskList = taskService.createTaskQuery().taskInvolvedUser(username).processVariableValueEquals(Workflow.TIPO_TASK, Workflow.TIPO_TASK_NOTIFICA).listPage(0, maxResult);
        List<Task> taskList = new ArrayList<Task>();
        for (Task task : taskList) {
            TaskDTO taskDTO = getTaskDTO(task);
            json.getRows().add(taskDTO);
        }
        json.setTotal((int) totalCount);
        model.addAttribute("json", json.toString());
        return AJAX_PAGE;
    }

    @RequestMapping("/workflow/notifiche/list")
    public ModelAndView notificheShow(Model model) {
        return new ModelAndView("notifiche_show");
    }

    @RequestMapping("/workflow/notifiche/list/ajax")
    public ModelAndView notificheShowAjax(Model model, @ModelAttribute("paginator") JqgridPaginator paginator, HttpServletRequest request) {
        Utente connectedUser = utentiService.getUtenteConnesso(request);
        String username = connectedUser.getUsername();
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        Integer firstRecord = (page * maxResult) - maxResult;
        TaskQuery query = taskService.createTaskQuery();
        /* Commento count della query per evitare timeout di activi nell attesa di risolvere il problema */
        //long totalCount = query.taskInvolvedUser(username).processVariableValueEquals(Workflow.TIPO_TASK, Workflow.TIPO_TASK_NOTIFICA).count();
        long totalCount =5;
        if (paginator.getSidx().equals("taskDate")) {
            if (paginator.getSord().equalsIgnoreCase("asc")) {
                query = query.orderByTaskCreateTime().asc();

            } else {
                query = query.orderByTaskCreateTime().desc();
            }
        } else {
            query = query.orderByTaskCreateTime().desc();
        }
        /* Commento count della query per evitare timeout di activi nell attesa di risolvere il problema */
        //List<Task> taskList = query.taskInvolvedUser(username).includeProcessVariables().processVariableValueEquals(Workflow.TIPO_TASK, Workflow.TIPO_TASK_NOTIFICA).listPage(firstRecord, maxResult);
        List<Task> taskList = new ArrayList<Task>();
        GridNotificheBean json = new GridNotificheBean();
        for (Task task : taskList) {
            TaskDTO taskDTO = getTaskDTO(task);
            json.getRows().add(taskDTO);
        }

        Integer totalRecords = (int) totalCount;
        totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
        json.setPage(page);
        json.setRecords((int) totalCount);
        json.setTotal(totalRecords);
        model.addAttribute("json", json.toString());
        return new ModelAndView(AJAX_PAGE);
    }

    @RequestMapping("/workflow/notifiche/markRead")
    public ModelAndView markAsRead(Model model,
            @RequestParam(value = "taskId", required = true) String taskId) {
        Map<String, String> result;
        try {
            taskService.complete(taskId);
            result = ImmutableMap.of("success", "true", "msg", "La notifica è stata marcata come letta");
        } catch (Exception ex) {
            Log.APP.error("Impossibile completare il task", ex);
            result = ImmutableMap.of("success", "false", "msg", "Impossibile confermare la lettura della notifica");
            //Recuperare indo utente connesso
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PROCESSI_PROCESSO_TASK_COMPLETE, "/workflow/notifiche/markRead", ex, null, null);
            erroriAction.saveError(errore);
        }
        model.addAttribute("json", gson.toJson(result));
        return new ModelAndView(AJAX_PAGE);
    }

    @RequestMapping("/workflow/notifiche/detail")
    public String notificaDetail(Model model,
            @RequestParam(value = "taskId", required = true) String taskId) {
        Task task = taskService.createTaskQuery().includeProcessVariables().taskId(taskId).singleResult();
        Map<String, Object> variables = taskService.getVariables(task.getId());
        String messaggio = (String) variables.get(Workflow.MESSAGGIO_NOTIFICA);
        messaggio = StringEscapeUtils.escapeHtml(messaggio);
        model.addAttribute("json", messaggio);
        return AJAX_PAGE;
    }

    @RequestMapping("/workflow/notifiche/event")
    public String notificaGoToEvent(Model model,
            @RequestParam(value = "taskId", required = true) String taskId,
            HttpServletRequest request) {
        try {
            Task task = taskService.createTaskQuery().includeProcessVariables().taskId(taskId).singleResult();
            Map<String, Object> variables = taskService.getVariables(task.getId());
            taskService.complete(taskId);
            Integer idPratica = (Integer) variables.get(Workflow.ID_PRATICA);
            Integer idPraticaEvento = (Integer) variables.get(Workflow.ID_PRATICA_EVENTO);
            request.getSession().setAttribute(SessionConstants.ID_PRATICA_SELEZIONATA, idPratica);
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

    private TaskDTO getTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskId(task.getId());
        taskDTO.setInstanceId(task.getProcessInstanceId());
        taskDTO.setTaskKey(task.getTaskDefinitionKey());
        taskDTO.setName(task.getName());
        taskDTO.setTaskDate(task.getCreateTime());
        Map<String, Object> variables = taskService.getVariables(task.getId());
        taskDTO.setVariables(variables);
        return taskDTO;
    }

    @RequestMapping("/workflow/mytask")
    public ModelAndView myTasklistShow(Model model, HttpServletRequest request) {
        Utente utenteConnesso = utentiService.getUtenteConnesso(request);
        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateUser(utenteConnesso.getUsername());
        List<Task> taskList = taskQuery.orderByTaskCreateTime().asc().list();
        model.addAttribute("task_list", taskList);
        return new ModelAndView("mytask_list_show");
    }

    @RequestMapping("/workflow/mytask/ajax")
    public String myTasklistAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        Utente utente = utentiService.getUtenteConnesso(request);
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        Integer firstRecord = (page * maxResult) - maxResult;

        GridTasklistBean json = new GridTasklistBean();
        try {
            TaskQuery query = taskService.createTaskQuery().taskCandidateUser(utente.getUsername());
            if (paginator.getSidx().equals("taskDate")) {
                if (paginator.getSord().equalsIgnoreCase("asc")) {
                    query = query.orderByTaskCreateTime().asc();

                } else {
                    query = query.orderByTaskCreateTime().desc();
                }
            }
            if (paginator.getSidx().equals("taskId")) {
                if (paginator.getSord().equalsIgnoreCase("asc")) {
                    query = query.orderByTaskId().asc();

                } else {
                    query = query.orderByTaskId().desc();
                }
            }

            List<Task> taskList = query.includeProcessVariables().processVariableValueEquals(Workflow.TIPO_TASK, Workflow.TIPO_TASK_TASK).listPage(firstRecord, maxResult);
            for (Task task : taskList) {
                TaskDTO taskDTO = new TaskDTO();
                taskDTO.setTaskId(task.getId());
                taskDTO.setInstanceId(task.getProcessInstanceId());
                taskDTO.setTaskKey(task.getTaskDefinitionKey());
                taskDTO.setName(task.getName());
                taskDTO.setTaskDate(task.getCreateTime());
                Map<String, Object> processVariables = task.getProcessVariables();
                Integer idPraticaEvento = (Integer) processVariables.get(Workflow.ID_PRATICA_EVENTO);
                if (idPraticaEvento != null) {
                    PraticheEventi evento = praticaDao.getDettaglioPraticaEvento(idPraticaEvento);
                    processVariables.put("idEvento", idPraticaEvento);
                    processVariables.put("descrizioneEvento", evento.getIdEvento().getDesEvento());
                }
                taskDTO.setVariables(processVariables);

                json.getRows().add(taskDTO);
            }

            query = taskService.createTaskQuery().taskCandidateUser(utente.getUsername());
            Integer totalRecords = (int) query.processVariableValueEquals(Workflow.TIPO_TASK, Workflow.TIPO_TASK_TASK).count();
            totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
            json.setPage(page);
            json.setRecords((int) taskService.createTaskQuery().count());
            json.setTotal(totalRecords);
            model.addAttribute("json", json.toString());
        } catch (Exception e) {
            String msg = "Impossibile effettuare la ricerca.";
            json.setErrors(Arrays.asList(msg));
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_WORKFLOW_TASKLIST_INDEX_AJAX, "Errore nell'esecuzione del controller /workflow/tasklist/ajax", e, null, utente);
            erroriAction.saveError(errore);
            model.addAttribute("json", json.toString());
            Log.APP.error(msg, e);
        }
        return AJAX_PAGE;
    }

}
