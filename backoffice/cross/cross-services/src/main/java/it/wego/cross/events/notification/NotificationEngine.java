/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.notification;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.wego.cross.constants.Workflow;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.service.UtentiService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Giuseppe
 */
@Component
public class NotificationEngine {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private UtentiService utentiService;

    public void createNotification(PraticheEventi praticheEventi, List<String> assigneeList, String message) throws Exception {
        createNotification(praticheEventi, assigneeList, null, message, Boolean.TRUE);
    }

    public void createNotification(PraticheEventi praticheEventi, List<String> assigneeList, String title, String message) throws Exception {
        createNotification(praticheEventi, assigneeList, title, message, Boolean.TRUE);
    }

    public void createNotification(PraticheEventi praticheEventi, List<String> assigneeList, String title, String message, Boolean includeAdminOnPratica) throws Exception {
        Preconditions.checkNotNull(praticheEventi, "Nessun evento indicato");

//        if (assigneeList == null) {
//            assigneeList = new ArrayList<String>();
//        }
        assigneeList = assigneeList == null ? Lists.<String>newArrayList() : Lists.<String>newArrayList(assigneeList);
        List<UtenteDTO> utenti = utentiService.findAdminSuPratica(praticheEventi.getIdPratica().getIdPratica(), new Filter());
        if (utenti != null && !utenti.isEmpty()) {
            for (UtenteDTO utente : utenti) {
                if (!assigneeList.contains(utente.getUsername())) {
                    assigneeList.add(utente.getUsername());
                }
            }
        }

        Preconditions.checkArgument(!Strings.isNullOrEmpty(message), "Nessun messaggio specificato");
        Preconditions.checkArgument(!assigneeList.isEmpty(), "Specificare almeno un destinatario della notifica");

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put(Workflow.ID_PRATICA_EVENTO, praticheEventi.getIdPraticaEvento());
        variables.put(Workflow.MESSAGGIO_NOTIFICA, message);
        //Titolo di default: descrizione evento
        variables.put(Workflow.TITOLO_NOTIFICA, Objects.firstNonNull(title, praticheEventi.getIdEvento().getDesEvento()));
        //Rimuove duplicati e null
        List<String> assignee = ImmutableSet.copyOf(Iterables.filter(assigneeList, Predicates.not(Predicates.isNull()))).asList();
        variables.put(Workflow.DESTINATARI_NOTIFICA, assignee);
        runtimeService.startProcessInstanceByMessage("nuovaNotifica", variables);
    }
}
