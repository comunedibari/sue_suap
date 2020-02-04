package it.wego.cross.actions;

import com.google.common.base.Strings;
import it.wego.cross.constants.StatiEmail;
import it.wego.cross.constants.Workflow;
import it.wego.cross.dao.MailDao;
import it.wego.cross.dto.EmailDTO;
import it.wego.cross.entity.Email;
import it.wego.cross.entity.LkStatiMail;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Utente;
import it.wego.cross.mail.EmailConfig;
import it.wego.cross.service.MailService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.WorkFlowService;
import java.util.Date;
import java.util.Map;
import javax.mail.Flags;
import javax.mail.Message;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author CS
 */
@Component
public class EmailAction {

    @Autowired
    private MailService mailService;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private SystemEventAction systemEventAction;
    @Autowired
    private WorkFlowService workflowService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private MailDao emailDao;

    @Transactional(rollbackFor = Exception.class)
    public void cambiaStato(EmailDTO email) throws Exception {
        mailService.cambiaStato(email.getIdEmail(), email.getStato());
    }

//    @Transactional(rollbackFor = Exception.class)
//    public void gestioneEmail() throws Exception {
//        mailService.gestioneEmail();
//    }
    @Transactional(rollbackFor = Exception.class)
    public void updateEmail(Email email) throws Exception {
        mailService.updateEmail(email);
    }

    @Transactional
    public void aggiornaMail(Email emailDB, Message email, EmailConfig emailConfig) throws Exception {
        emailDao.update(emailDB);
        emailDao.flush();
        email.setFlag(Flags.Flag.SEEN, true);
        mailService.aggiornaStato(emailDB);
    }

    @Transactional(rollbackFor = Exception.class)
    public void markAsReceived(Email email, String taskId, Utente utenteConnesso) throws Exception {
        LkStatiMail mailDaConfermare = mailService.findStatoMailByCodice(StatiEmail.MARCATA_COME_CONSEGNATA);
        email.setStato(mailDaConfermare);
        mailService.updateEmail(email);
        Pratica pratica = email.getIdPraticaEvento().getIdPratica();
        systemEventAction.marcaEmailComeConsegnata(pratica, email, utenteConnesso);
        //Potrei non avere il task ID perché l'errore è stato rilevato dallo scheduler in un secondo momento (compatibilità all'indietro con la vecchia versione del flusso)
        if (!Strings.isNullOrEmpty(taskId)) {
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            //Mi serve per la gestione del vecchio flusso di protocollazione
            boolean isInManualProtocolStatus = workflowService.isInManualProtocolStatus(task);
            if (isInManualProtocolStatus) {
                //Gestione del pregresso
                //se ho il taskid procedo con il flusso
                Map<String, Object> variables = workflowService.getVariablesForProtocolTask(email);
                taskService.complete(taskId, variables);
            } else {
                String instanceId = task.getProcessInstanceId();
                runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
                runtimeService.deleteProcessInstance(instanceId, "Istanza cancellata da " + utenteConnesso.getCognome() + " " + utenteConnesso.getNome());
            }
        }
    }

    @Transactional
    public void rispedisciEmail(Email email, Utente utenteConnesso) throws Exception {
        Pratica pratica = email.getIdPraticaEvento().getIdPratica();
        Task task = taskService.createTaskQuery().processVariableValueEquals(Workflow.ID_EMAIL, email.getIdEmail()).singleResult();
        if (task == null) {
            LkStatiMail mailDaConfermare = mailService.findStatoMailByCodice(StatiEmail.DA_INVIARE);
            email.setStato(mailDaConfermare);
            email.setOggettoRisposta(null);
            email.setCorpoRisposta(null);
            mailService.updateEmail(email);
            systemEventAction.rispedisciEmail(pratica, email, utenteConnesso);
            praticheService.startSendMailProcess(email);
        } else {
            throw new Exception("E' già attiva una richiesta di invio email");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void rispedisciEmailInviata(Integer emailId, String taskId, String destinatario, Utente utenteConnesso) throws Exception{
        //Se voglio rispedire una email, la inserisco come email nuova sull'evento, non vado in update come per il
        //rispedisci email
        Email email = mailService.findByIdEmail(emailId);
        Email newEmail = new Email();
        newEmail.setIdPraticaEvento(email.getIdPraticaEvento());
        newEmail.setDataInserimento(new Date());
        newEmail.setEmailDestinatario(destinatario);
        newEmail.setCorpoEmail(email.getCorpoEmail());
        newEmail.setOggettoEmail(email.getOggettoEmail());
        LkStatiMail mailDaInviare = mailService.findStatoMailByCodice(StatiEmail.DA_INVIARE);
        newEmail.setStato(mailDaInviare);
        newEmail.setGruppo(0);
        newEmail.setTipoDestinazione(StatiEmail.SCONOSCIUTA);
        emailDao.insert(newEmail);
        emailDao.flush();
        systemEventAction.rispedisciEmail(email.getIdPraticaEvento().getIdPratica(), newEmail, utenteConnesso);
        praticheService.startSendMailProcess(newEmail);
    }

}
