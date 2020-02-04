package it.wego.cross.serializer;

import it.wego.cross.constants.Workflow;
import it.wego.cross.dto.EmailDTO;
import it.wego.cross.entity.Email;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author CS
 */
@Component
public class EmailSerializer {

    @Autowired
    private TaskService taskService;

    public List<EmailDTO> serialize(List<Email> emailListDB) {
        List<EmailDTO> emailList = new ArrayList<EmailDTO>();
        for (Email emailDB : emailListDB) {
            emailList.add(serialize(emailDB));
        }
        return emailList;
    }

    public EmailDTO serialize(Email emailDB) {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setCorpoEmail(emailDB.getCorpoEmail());
        emailDTO.setDataAggiornamento(Utils.convertDataOraToString(emailDB.getDataAggiornamento()));
        emailDTO.setDataInserimento(Utils.convertDataOraToString(emailDB.getDataInserimento()));
        emailDTO.setEmailDestinatario(emailDB.getEmailDestinatario());
        emailDTO.setIdEmail(emailDB.getIdEmail());
        emailDTO.setIdMessaggio(emailDB.getIdMessaggio());
        emailDTO.setIdMessaggioPec(emailDB.getIdMessaggioPec());
        emailDTO.setOggettoEmail(emailDB.getOggettoEmail());
        emailDTO.setOggettoRisposta(emailDB.getOggettoRisposta());
        emailDTO.setCorpoRisposta(emailDB.getCorpoRisposta());
        emailDTO.setPathEml(emailDB.getPathEml());
        if (emailDB.getStato() != null) {
            emailDTO.setStato(emailDB.getStato().getCodice());
            emailDTO.setStatoDescrizione(emailDB.getStato().getDescrizione());
        }
        PraticheEventi pe = emailDB.getIdPraticaEvento();
        //Cerco se c'è un task associato alla email
        Task task = taskService.createTaskQuery().processVariableValueEquals(Workflow.ID_EMAIL, emailDB.getIdEmail()).singleResult();
        if (task != null){
            String taskId = task.getId();
            emailDTO.setTaskId(taskId);
        }
        emailDTO.setIdEvento(pe.getIdPraticaEvento());
        emailDTO.setDescrizioneEvento(pe.getIdEvento().getDesEvento());
        Pratica p = pe.getIdPratica();
        emailDTO.setIdPratica(p.getIdPratica());
        emailDTO.setOggettoPratica(p.getOggettoPratica());
        return emailDTO;
    }
}
