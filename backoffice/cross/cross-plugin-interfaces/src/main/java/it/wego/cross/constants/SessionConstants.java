/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.constants;

/**
 *
 * @author giuseppe
 */
public class SessionConstants {

    public static final String UTENTE_CONNESSO = "user";
    public static final String UTENTE_CONNESSO_FULL = "user_full";
    public static final String SUPERUSER = "superuser";
    public static final String ESTRAZIONIUSER = "estrazioniuser";
    public static final String ESTRAZIONICILATODOUSER = "estrazioniCilaToDouser";
    public static final String ID_PRATICA_SELEZIONATA = "id_pratica_selezionata";
    public static final String EVENTO_SELEZIONATO = "evento_selezionato";
    public static final String RUOLO = "ruolo";
    public static final String ADMIN = "ADMIN";
    public static final String OPERATORE = "OPERATORE";
    public static final String SEGRETERIA = "segreteria";
    public static final String RUOLO_GESTIONE_PRATICHE = "RUOLO_GESTIONE_PRATICHE";
    public static final String AMMINISTRATORE_CONSOLE = "CONSOLE";
    public static final int ROW_PER_PAGE = 10;
    public static final String ENTE = "ENTE";
    public static final String ENTE_SELEZIONATO = "ente_riferimento_pratiche_protocollo";
    public static final String ID_UTENTE_CONNESSO = "id_connected_user";
    public static final String PRATICA_APERTURA_PROTOCOLLO = "cross.pratica.apertura.protocollo.enabled";
    public static final String PRATICA_APERTURA_MANUALE = "cross.pratica.apertura.manuale.enabled";
    public static final String PRATICA_APERTURA_COMUNICA = "cross.pratica.apertura.comunica.enabled";
    public static final String PRATICA_APERTURA_SUAP_FVG = "cross.pratica.apertura.suapfvg.enabled";
    public static final String PRATICA_APERTURA_PROTOCOLLO_RICERCA = "cross.pratica.apertura.protocollo.ricerca.enabled";
    public static final String PRATICA_COMUNICAZIONI_INGRESSO = "cross.pratica.comunicazioniingresso";
    public static final String PRATICA_ABILITA_MESSAGGI = "cross.pratica.messaggi.enabled";
    public static final String PRATICA_DIRITTI_SEGRETERIA = "cross.pratica.diritti.segreteria.enabled";
    public static final String TASKLIST_ENABLED = "cross.menu.tasklist.enabled";
    public static final String CDS_ENABLED = "cross.menu.cds.enabled";

    public static final String ABILITAZIONE_REGISTRO_IMPRESE = "ricerca.registroImprese.abilitato";
    //Aggiunto 13/01/2016
    public static final String ABILITA_ASSEGNAZIONE_PRATICHE = "abititaAssegnazionePratiche";
    
    public static final String DOCUMENTI_PLUGIN_ID = "documenti.plugin.id";
    public static final String PROTOCOLLO_PLUGIN_ID = "protocollo.plugin.id";
    public static final String PRATICA_PLUGIN_ID = "pratica.plugin.id";
    public static final String PROTOCOLLO_MODALITA_CARICAMENTO = "protocollo.modalita.caricamento";
    public static final String ANAGRAFE_PERSONA_FISICA_PLUGIN_ID = "anagrafe.fisica.plugin.id";
    public static final String ANAGRAFE_PERSONA_GIURIDICA_PLUGIN_ID = "anagrafe.giuridica.plugin.id";

    public static final String CUSTOM_VIEW_PLUGIN_ID = "views.plugin.id";
    public static final String REPORTER_CLIENT_URL = "reporter.client.url";
    public static final String CLEAR_CLIENT_ENABLED = "clear.client.enabled";
    public static final String CLEAR_CLIENT_URL = "clear.client.url";
    public static final String CLEAR_CLIENT_EVENTO_APERTURA = "clear.client.evento.apertura";

    public static final String REGISTRO_IMPRESE_RICHIESTA_ISCRIZIONE_URL = "registro.imprese.richiesta.iscrizione.client.url";
    public static final String REGISTRO_IMPRESE_COMUNICAZIONE_SUAP_URL = "registro.imprese.comunicazione.suap.client.url";

    public static final String WS_RICEZIONE_PRATICHE_CROSS = "path.ws.cross.ricezione";

    public static final String GESTIONE_PRATICHE_COLUMN_MODEL = "gestione.pratiche.column.model";

    public static final String EVENTO_RICEZIONE_ALLEGA_TUTTO = "evento.ric.mail.tutti.allegati";

    public static final String MAIL_REPLAY_TO = "mail.replyTo";
    public static final String MAIL_FOLDER = "mail.folder";
    public static final String MAIL_HOST = "mail.host";
    public static final String MAIL_IMAP_PORT = "mail.imap.port";
    public static final String MAIL_IMAP_AUTH = "mail.imap.auth";
    public static final String MAIL_USERNAME = "mail.username";
    public static final String MAIL_PASSWORD = "mail.password";
    public static final String MAIL_IMAP_START_SSL_ENABLE = "mail.imap.startssl.enable";
    public static final String MAIL_IMAP_START_TLS_ENABLE = "mail.imap.starttls.enable";
    public static final String MAIL_MIME_CHARSET = "mail.mime.charset";
    public static final String MAIL_DEBUG = "mail.debug";
    public static final String MAIL_IMAP_SSL_ENABLE = "mail.imap.ssl.enable";
    public static final String MAIL_IMAP_HOST = "mail.imap.host";
    public static final String MAIL_STORE_PROTOCOL = "mail.store.protocol";
    public static final String MAIL_CONFIG_CARTELLA_CROSS = "mail.config.cartellaCross";
    public static final String MAIL_CONFIG_RICERCA_LETTE = "mail.config.ricercaLette";
    public static final String MAIL_CONFIG_CARTELLA_LETTE = "mail.config.cartellaLette";

    public static final String MAIL_SMTP_PORT = "mail.smtp.port";
    public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    public static final String MAIL_SMTP_START_SSL_ENABLE = "mail.smtp.startssl.enable";
    public static final String MAIL_SMTP_START_TLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    /*Modifiche per tls 1.2*/
    public static final String MAIL_SMTP_SSL_PROTOCOLS = "mail.smtp.ssl.protocols";
    public static final String MAIL_SMTP_DSN_NOTIFY = "mail.smtp.dsn.notify";
    public static final String MAIL_SMTP_SSL_ENABLE = "mail.smtp.ssl.enable";
    public static final String MAIL_MITTENTE = "mail.mittente";

    public static final String MAIL_SMTP_CONNECTION_TIMEOUT = "mail.smtp.connectiontimeout";
    public static final String MAIL_SMTP_WRITE_TIMEOUT = "mail.smtp.writetimeout";
    public static final String MAIL_SMTP_TIMEOUT = "mail.smtp.timeout";
    public static final String MAIL_SMTP_SSL_TRUST = "mail.smtp.ssl.trust";
    
    public static final String MAIL_SMTP_SOCKET_FACTORY_PORT = "mail.smtp.socketFactory.port";
	public static final String MAIL_SMTP_SOCKET_FACTORY_CLASS = "mail.smtp.socketFactory.class";
	public static final String ESTRAZIONI_CILA__COLUMN_MODEL = "estrazioni.cila";
	
	public static final String ATTACHMENTS_FOLDER = "attachment.tmp.folder";
	public static final String DOCUMENTS_FOLDER = "documenti.fs.folder";

}
