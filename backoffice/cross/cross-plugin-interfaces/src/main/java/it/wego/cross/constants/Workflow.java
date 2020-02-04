/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.constants;

/**
 *
 * @author Giuseppe
 */
public class Workflow {

    public static final String ID_PRATICA_EVENTO = "idPraticaEvento";
    public static final String MANUAL_PROTOCOL = "require_manual_protocol";
    public static final String ID_PRATICA = "idPratica";
    public static final String OGGETTO_PRATICA = "oggettoPratica";
    public static final String RICHIEDENTI_PRATICA = "richiedentiPratica";
    public static final String IDENTIFICATIVO_PRATICA = "identificativoPratica";
    public static final String FASCICOLO_PRATICA = "fascicoloPratica";
    public static final String MESSAGGIO_NOTIFICA = "testoMessaggio";
    public static final String TITOLO_NOTIFICA = "titoloNotifica";
    public static final String DESTINATARI_NOTIFICA = "assigneeList";
    public static final String POTENZIALI_PROPRIETARI = "potentialOwner";
    public static final String TIPO_TASK = "tipoTask";
    public static final String TIPO_TASK_TASK = "task";
    public static final String TIPO_TASK_NOTIFICA = "notifica";
    public static final String ID_EMAIL = "idEmail";
    public static final String LAST_ERROR_MESSAGE = "last_error_message";
    public static final String EVENTO_BEAN = "eventoBean";
    public static final String EVENTO_BEAN_CLASS = "eventoBeanClass";
    public static final String AUTOMATIC_EVENTS_LIST = "automatic_events_list";
    public static final String EXECUTE_EVENT_FAILED = "execute_event_failed";
    public static final String AUTOMATIC_EVENT_CREATION_FAILED = "eventi_automatici_failed";
    public static final String AUTOMATIC_EVENT_EXECUTION_FAILED = "esegui_eventi_automatici_failed";
    public static final String CREA_SOTTOPRATICHE_EVENT_EXECUTION_FAILED = "creazione_sottopratiche_failed";
    public static final String CLEAR_INTEGRATION_FAILED = "clear_integration_failed";
    
    //Mappo gli ID del flusso di protocollazione
    public static final String PROTOCOLLO_TASK_PROTOCOLLA = "protocolla";
    public static final String PROTOCOLLO_TASK_PROTOCOLLO_MANUALE = "protocol_manual_management";
    public static final String PROTOCOLLO_TASK_AGGIORNA_PRATICA = "aggiorna_pratica_proto";
    public static final String PROTOCOLLO_TASK_AGGIORNA_DOCUMENTO = "aggiorna_documento";
    public static final String PROTOCOLLO_TASK_INVIO_EMAIL = "send_mail";
    public static final String PROTOCOLLO_TASK_INVIO_EMAIL_MANUALE = "mail_manual_management";
    public static final String PROTOCOLLO_TASK_AGGIORNA_PRATICA_DOPO_INVIO_EMAIL = "aggiorna_pratica_mail";
    
    //Gruppi Workflow
    public static final String CANDIDATE_USERS = "candidateUsers";
//    public static final String GROUP_SUPERUSERS = "SUPERUSERS";
}
