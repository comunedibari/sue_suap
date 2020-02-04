package it.wego.cross.service;


import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.sun.mail.imap.IMAPFolder;

import it.wego.cross.actions.EmailAction;
import it.wego.cross.actions.WorkflowAction;
import it.wego.cross.constants.ConfigurationConstants;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.constants.StatiEmail;
import it.wego.cross.dao.MailDao;
import it.wego.cross.dto.EmailDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Configuration;
import it.wego.cross.entity.Email;
import it.wego.cross.entity.LkStatiMail;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.events.notification.NotificationEngine;
import it.wego.cross.mail.EmailConfig;
import it.wego.cross.mail.GestoreMail;
import it.wego.cross.serializer.EmailSerializer;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;

import org.activiti.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 *
 * @author CS
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private GestoreMail gestoreMail;
    @Autowired
    private MailDao emailDao;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private ConfigurationService configuration;
    @Autowired
    private EmailSerializer emailSerializer;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private WorkflowAction workflowAction;
    @Autowired
    private EmailAction mailAction;
    @Autowired
    private NotificationEngine notificationEngine;
    @Autowired
    private MessageSource messageSource;
    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class.getName());

    @Override
    public void salvaMailInDb(PraticheEventi evento, List<String> to, String subject, String content) throws Exception {
        Preconditions.checkNotNull(to, "mancano i destinatari per l'invio della mail");
        Set<String> destinatari = new HashSet<String>(to);
        Integer gruppoID = 0;

        for (String destinatario : destinatari) {
            String message = content;
            Email emailDB = new Email();
            emailDB.setIdPraticaEvento(evento);
            emailDB.setDataInserimento(new Date());
            emailDB.setEmailDestinatario(destinatario);
            emailDB.setCorpoEmail(message);
            emailDB.setOggettoEmail(subject);
            emailDB.setGruppo(gruppoID);
            LkStatiMail statoEmail;
            if (!Utils.e(destinatario)) {
                statoEmail = emailDao.findstatoByCodStato(StatiEmail.DA_PROTOCOLLARE);
            } else {
                statoEmail = emailDao.findstatoByCodStato(StatiEmail.SPEDIZIONE_MANUALE);
            }
            emailDB.setStato(statoEmail);
            emailDB.setTipoDestinazione(StatiEmail.SCONOSCIUTA);
            emailDao.insert(emailDB);
            emailDao.flush();
            aggiornaStato(emailDB);
        }
    }

    @Override
    public void gestioneEmail() throws Exception {
        List<Configuration> mailConfig = configuration.findByName("mail.imap.host");
        for (Configuration config : mailConfig) {
        	try {
        		  Log.APP.info("Connessione all server mail " + config.getValue());
                  EmailConfig emailConfig = null;
                  Integer idEnte = null;
                  Integer idComune = null;
                  if (config.getIdEnte() != null) {
                      idEnte = config.getIdEnte().getIdEnte();
                  }
                  if (config.getIdComune() != null) {
                      idComune = config.getIdComune().getIdComune();
                  }
                  String leggiEmail = configuration.getCachedConfiguration(ConfigurationConstants.LEGGI_EMAIL, idEnte, idComune);
                  if (leggiEmail != null && leggiEmail.equalsIgnoreCase("S")) {
                      emailConfig = getImapProprieties(idEnte, idComune);
                      Session session = Session.getDefaultInstance(emailConfig.getProperties(), null);
                      Store store = gestoreMail.apriConnessione(session, emailConfig.getProperties());

                      //Recupera tutte le email in arrivo
                      Message[] messaggi = gestoreMail.getNuoveEmail(store, emailConfig);
                      if (messaggi.length > 0) {
                          for (Message email : messaggi) {
                              gestioneEmail(email, emailConfig, session);
                          }
                      }

                      gestoreMail.chiudiConnessione(store, emailConfig);
                  }
				
			} catch (Exception e) {
				Log.APP.error(String.format("Errore nella connessione al server mail: %s", config.getValue()), e);
			}
          
        }
    }

    @Override
    public void gestioneEmail(Message email, EmailConfig emailConfig, Session session) {
        try {
            String[] messageIdsError = gestoreMail.getDeliveryStatus(email, email.getContent(), session);
            boolean spostaMail = false;
            if (messageIdsError == null) {
                // non in presenza di un Mail Delivery
                LkStatiMail stato = getStatoEmail(email.getSubject());
                List<String> messageids = new ArrayList<String>();
                // id del messaggio inviato
                String[] idEmailOrigine = email.getHeader("X-Riferimento-Message-ID");
                // id del messaggio pec
                String[] idEmail = email.getHeader("Message-ID");
                if (idEmailOrigine != null && idEmailOrigine.length > 0) {
                    messageids.addAll(Arrays.asList(idEmailOrigine));
                    // cerco le mail che hanno l'id messaggio corrispondente
                    List<Email> emailsDB = emailDao.findByMessageIds(messageids);
                    if (emailsDB != null && emailsDB.size() > 0) {
                        Log.EMAIL.info("EMAIL: Trovate " + emailsDB.size() + " email con i seguenti message ID:");
                        for (String messageid : messageids) {
                            Log.EMAIL.info("- " + messageid);
                        }
                        for (Email emailDB : emailsDB) {
                            Log.EMAIL.info("EMAIL: L'email con oggetto" + email.getSubject() + " e' in risposta alla email " + emailDB.getIdMessaggio() + " (id: " + emailDB.getIdEmail() + ")");
                            if (emailDB.getStato().getCodice().equals(StatiEmail.INVIATA)) {
                                if (stato.getCodice().equals(StatiEmail.PRESO_IN_CARICO_DA_SERVER_PEC)) {
                                    emailDB.setIdMessaggioPec(idEmail[0]);
                                    emailDB.setCorpoRisposta(gestoreMail.getCorpoEmail(email));
                                    emailDB.setOggettoRisposta(email.getSubject());
                                    emailDB.setDataAggiornamento(email.getReceivedDate());
                                    emailDB.setTipoDestinazione(getTipoDestinazione(gestoreMail.getCorpoEmail(email)));
                                    if (emailDB.getTipoDestinazione().equals(StatiEmail.POSTA_ORDINARIA)) {
                                        emailDB.setStato(emailDao.findstatoByCodStato(StatiEmail.CONFERMATA));
                                    } else {
                                        emailDB.setStato(stato);
                                    }
                                    mailAction.aggiornaMail(emailDB, email, emailConfig);
                                    spostaMail = true;
                                }
                            } else if (emailDB.getStato().getCodice().equals(StatiEmail.PRESO_IN_CARICO_DA_SERVER_PEC)) {
                                if (stato.getCodice().equals(StatiEmail.CONFERMATA)) {
                                    emailDB.setStato(stato);
                                    emailDB.setCorpoRisposta(gestoreMail.getCorpoEmail(email));
                                    emailDB.setOggettoRisposta(email.getSubject());
                                    emailDB.setDataAggiornamento(email.getReceivedDate());
                                    mailAction.aggiornaMail(emailDB, email, emailConfig);
                                    spostaMail = true;
                                } else if (stato.getCodice().equals(StatiEmail.ERRORE_SERVER)) {
                                    emailDB.setStato(stato);
                                    emailDB.setCorpoRisposta(gestoreMail.getCorpoEmail(email));
                                    emailDB.setOggettoRisposta(email.getSubject());
                                    emailDB.setDataAggiornamento(email.getReceivedDate());
                                    mailAction.aggiornaMail(emailDB, email, emailConfig);
//                                    createNotificationTask(emailDB);
                                    spostaMail = true;
                                }
                            } else if (emailDB.getStato().getCodice().equals(StatiEmail.ERRORE_SERVER)
                                    || emailDB.getStato().getCodice().equals(StatiEmail.ERRORE_GENERICO)) {
                                // gestisce gli errori di mancata consegna per superamento 12/24 ore
                                if (email.getSubject().toUpperCase().startsWith(StatiEmail.MANCATA_CONSEGNA_TEMPO_MASSIMO)) {
                                    emailDB.setCorpoRisposta(gestoreMail.getCorpoEmail(email));
                                    emailDB.setOggettoRisposta(email.getSubject());
                                    emailDB.setDataAggiornamento(email.getReceivedDate());
                                    mailAction.aggiornaMail(emailDB, email, emailConfig);
//                                    createNotificationTask(emailDB);
                                    spostaMail = true;
                                }
                            }
                        }
                    }
                }
            } else {
                List<String> messageids = new ArrayList<String>();
                messageids.addAll(Arrays.asList(messageIdsError));
                if (!messageids.isEmpty()) {
                    List<Email> emailsDB = emailDao.findByMessageIds(messageids);
                    for (Email emailDB : emailsDB) {
                        String[] idEmail = email.getHeader("Message-ID");
                        emailDB.setIdMessaggioPec(idEmail[0]);
                        emailDB.setCorpoRisposta(gestoreMail.getCorpoEmail(email));
                        emailDB.setOggettoRisposta(email.getSubject());
                        emailDB.setDataAggiornamento(email.getReceivedDate());
                        emailDB.setStato(emailDao.findstatoByCodStato(StatiEmail.ERRORE_GENERICO));
                        mailAction.aggiornaMail(emailDB, email, emailConfig);
//                        createNotificationTask(emailDB);
                        spostaMail = true;
                    }
                }
            }
            if (spostaMail) {
                Log.APP.info("Sposto l'email \"" + email.getSubject() + "\" in " + emailConfig.getCartellaCross());
                gestoreMail.spostaEmails((IMAPFolder) email.getFolder(), emailConfig.getCross(), (Message[]) Arrays.asList(email).toArray());

            } else {
                Log.APP.info("Mail  \"" + email.getSubject() + "\" senza Message-ID, non la tratto");
            }
        } catch (Exception ex) {
            Log.APP.error("Errore reperendo le email da " + emailConfig.getProperties().getProperty(SessionConstants.MAIL_USERNAME) + " ", ex);
        }
    }

    @Override
    public void aggiornaStato(Email emailDB) throws Exception {
        Log.EMAIL.info("EMAIL: Aggiorno lo stato della email sia sul singolo evento che a livello di pratica");
        boolean tuttoOk = false;
        PraticheEventi evento = emailDB.getIdPraticaEvento();
        Log.EMAIL.info("EMAIL: Recupero l'elenco delle email spedite nello specifico evento");
        LkStatiMail raggruppatoreStato = null;

        for (Email emailEventoDB : evento.getEmailList()) {
            if (emailEventoDB.getStato() != null) {
                if (emailEventoDB.getStato().getCodice().equals(StatiEmail.ERRORE_SERVER) || emailEventoDB.getStato().getCodice().equals(StatiEmail.ERRORE_GENERICO)) {
                    raggruppatoreStato = emailDao.findstatoByCodStato(StatiEmail.ERRORE_GENERICO);
                    break;
                } else if (!emailEventoDB.getStato().getCodice().equals(StatiEmail.CONFERMATA)) {
                    if (raggruppatoreStato == null || !raggruppatoreStato.getCodice().equals(StatiEmail.ERRORE_GENERICO)) {
                        raggruppatoreStato = emailDao.findstatoByCodStato(StatiEmail.STATO_GENERICO);
                    }
                } else {
                    if (raggruppatoreStato == null || !raggruppatoreStato.getCodice().equals(StatiEmail.ERRORE_GENERICO) || !raggruppatoreStato.getCodice().equals(StatiEmail.STATO_GENERICO)) {
                        raggruppatoreStato = emailDao.findstatoByCodStato(StatiEmail.CONFERMATA);
                    }
                }
            }

        }
        evento.setStatoMail(raggruppatoreStato);
        praticheService.updatePraticaEvento(evento);
//        praticaDao.update(evento);

        raggruppatoreStato = null;

        for (PraticheEventi pe : evento.getIdPratica().getPraticheEventiList()) {
            if (pe.getStatoMail() != null) {
                if (pe.getStatoMail().getCodice().equals(StatiEmail.ERRORE_SERVER) || pe.getStatoMail().getCodice().equals(StatiEmail.ERRORE_GENERICO)) {
                    raggruppatoreStato = emailDao.findstatoByCodStato(StatiEmail.ERRORE_GENERICO);
                    break;
                } else if (!pe.getStatoMail().getCodice().equals(StatiEmail.CONFERMATA)) {
                    if (raggruppatoreStato == null || !raggruppatoreStato.getCodice().equals(StatiEmail.ERRORE_GENERICO)) {
                        raggruppatoreStato = emailDao.findstatoByCodStato(StatiEmail.STATO_GENERICO);
                    }
                } else {
                    if (raggruppatoreStato == null || !raggruppatoreStato.getCodice().equals(StatiEmail.ERRORE_GENERICO) || !raggruppatoreStato.getCodice().equals(StatiEmail.STATO_GENERICO)) {
                        raggruppatoreStato = emailDao.findstatoByCodStato(StatiEmail.CONFERMATA);
                    }
                }
            }
        }
        Pratica pratica = evento.getIdPratica();
        pratica.setStatoEmail(raggruppatoreStato);
        praticheService.aggiornaPratica(pratica);
//        praticaDao.update(evento.getIdPratica());
    }

    private LkStatiMail getStatoEmail(String oggetto) {
        LkStatiMail stato = null;
        if (oggetto.toUpperCase().startsWith(StatiEmail.ACCETTAZIONE)) {
            stato = emailDao.findstatoByCodStato(StatiEmail.PRESO_IN_CARICO_DA_SERVER_PEC);
        }
        if (oggetto.toUpperCase().startsWith(StatiEmail.CONSEGNA)) {
            stato = emailDao.findstatoByCodStato(StatiEmail.CONFERMATA);
        }
        if (oggetto.toUpperCase().startsWith(StatiEmail.MANCATA_CONSEGNA)) {
            stato = emailDao.findstatoByCodStato(StatiEmail.ERRORE_SERVER);
        }
        if (stato == null) {
            stato = emailDao.findstatoByCodStato(StatiEmail.ERRORE_GENERICO);
        }
        return stato;
    }

    @Override
    public void cambiaStato(Integer email, String nuovoStato) throws Exception {
        Email emailDB = emailDao.findByIdEmail(email);
        emailDB.setStato(emailDao.findstatoByCodStato(nuovoStato));
        aggiornaStato(emailDB);
    }

    @Override
    public void invioSingolaEmail(Email email) throws Exception {
        EmailConfig emailConfig = getSmtpProprieties(email.getIdPraticaEvento().getIdPratica().getIdProcEnte().getIdEnte().getIdEnte(), email.getIdPraticaEvento().getIdPratica().getIdComune().getIdComune());
        MimeMessage msg = gestoreMail.sendMail(email.getPathEml(), emailConfig);
        email.setIdMessaggio(msg.getMessageID());
        email.setStato(emailDao.findstatoByCodStato(StatiEmail.INVIATA));
        updateEmail(email);
    }

    @Override
    public EmailConfig getSmtpProprieties(Integer idEnte, Integer idComune) throws Exception {
        EmailConfig emailConfig = new EmailConfig();
        Properties props = new Properties();
        setProperty(props, SessionConstants.MAIL_HOST, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_SMTP_PORT, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_SMTP_AUTH, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_USERNAME, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_PASSWORD, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_SMTP_START_SSL_ENABLE, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_SMTP_START_TLS_ENABLE, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_TRANSPORT_PROTOCOL, idEnte, idComune);
        /*Modifiche per tls 1.2*/
        setProperty(props, SessionConstants.MAIL_SMTP_SSL_PROTOCOLS, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_MIME_CHARSET, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_SMTP_DSN_NOTIFY, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_DEBUG, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_SMTP_SSL_ENABLE, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_MITTENTE, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_REPLAY_TO, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_SMTP_CONNECTION_TIMEOUT, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_SMTP_WRITE_TIMEOUT, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_SMTP_TIMEOUT, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_SMTP_SSL_TRUST, idEnte, idComune);
        String mailFolder = configuration.getCachedConfiguration(SessionConstants.MAIL_FOLDER, idEnte, idComune);
        //String mailFolder ="C:\\SUESUAP\\cfg\\apache-tomcat-8.0.48_8081_bo\\cross_files\\email";
        File mailFolderPath = new File(mailFolder);
        if( !mailFolderPath.exists() )
        {
        	boolean directoryCreate = mailFolderPath.mkdirs();
        	if( logger.isInfoEnabled() )
        	{
        		logger.info("DIRECTORY {} NON ESISTENTE CREATA CON SUCCESSO ? ", mailFolder, directoryCreate);
        	}
        }
        if (Utils.e(mailFolder)) {
            Log.EMAIL.error("Impostare la cartella di salvataggio delle email su filesystem");
            throw new Exception("Imposttare la cartella IMAP di cross");
        }
        emailConfig.setProperties(props);
        emailConfig.setMailPathFS(mailFolder);
        emailConfig.setIdComune(idComune);
        emailConfig.setIdEnte(idEnte);
        return emailConfig;
    }
    
    @Override
    public EmailConfig getSmtpProprietiesNoEnte() throws Exception {
        EmailConfig emailConfig = new EmailConfig();
        Properties props = new Properties();
        setProperty(props, SessionConstants.MAIL_HOST, null, null);
        setProperty(props, SessionConstants.MAIL_SMTP_PORT, null, null);
        setProperty(props, SessionConstants.MAIL_SMTP_AUTH,null, null);
        setProperty(props, SessionConstants.MAIL_USERNAME, null, null);
        setProperty(props, SessionConstants.MAIL_PASSWORD, null, null);      
        setProperty(props, SessionConstants.MAIL_MITTENTE, null, null);
        setProperty(props, SessionConstants.MAIL_REPLAY_TO, null, null);
        setProperty(props, SessionConstants. MAIL_DEBUG, null, null);
        setProperty(props, SessionConstants.MAIL_SMTP_SOCKET_FACTORY_PORT,null, null);
        setProperty(props, SessionConstants.MAIL_SMTP_SOCKET_FACTORY_CLASS,null, null);
               
        emailConfig.setProperties(props);
     
        return emailConfig;
        }

    private EmailConfig getImapProprieties(Integer idEnte, Integer idComune) throws Exception {
        EmailConfig emailConfig = new EmailConfig();
        Properties props = new Properties();
        setProperty(props, SessionConstants.MAIL_HOST, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_IMAP_PORT, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_IMAP_AUTH, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_USERNAME, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_PASSWORD, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_IMAP_START_SSL_ENABLE, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_IMAP_START_TLS_ENABLE, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_MIME_CHARSET, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_DEBUG, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_IMAP_SSL_ENABLE, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_IMAP_HOST, idEnte, idComune);
        setProperty(props, SessionConstants.MAIL_STORE_PROTOCOL, idEnte, idComune);
        
        //Aggiunto 3/8/2016
        props.setProperty("mail.smtp.socketFactory.class", "it.wego.cross.mail.DummySSLSocketFactory");
        
        /* SAL 4 - R13*/
        props.setProperty("mail.pop3.ssl.enable", "false");
        props.setProperty("mail.pop3.starttls.enable", "true"); 
        props.setProperty("mail.pop3.starttls.required", "true"); 
        /* Fine SAL 4 - R13*/
        
        emailConfig.setProperties(props);
        String cartellaCross = configuration.getCachedConfiguration(SessionConstants.MAIL_CONFIG_CARTELLA_CROSS, idEnte, idComune);
        String ricercaLette = configuration.getCachedConfiguration(SessionConstants.MAIL_CONFIG_RICERCA_LETTE, idEnte, idComune);
        String cartellaLette = configuration.getCachedConfiguration(SessionConstants.MAIL_CONFIG_CARTELLA_LETTE, idEnte, idComune);
        if (Utils.e(cartellaCross)) {
            Log.EMAIL.error("Impostare la cartella IMAP di cross");
            throw new Exception("Imposttare la cartella IMAP di cross");
        }

        if (Utils.e(ricercaLette) || (!ricercaLette.equals("true") && !ricercaLette.equals("false"))) {
            String s = "Impostare la configurazione mail ricercaLette";
            Log.EMAIL.error(s);
            throw new Exception(s);
        }

        if (Utils.e(cartellaLette)) {
            String s = "Impostare la configurazione mail cartellaLette";
            Log.EMAIL.error(s);
            throw new Exception(s);
        }
        emailConfig.setRicercaLette(ricercaLette);
        emailConfig.setCartellaCross(cartellaCross);
        emailConfig.setCartellaLette(cartellaLette);
        emailConfig.setIdComune(idComune);
        emailConfig.setIdEnte(idEnte);
        return emailConfig;
    }

    private void setProperty(Properties props, String key, Integer idEnte, Integer idComune) {
        try {
            String prop = configuration.getCachedConfiguration(key, idEnte, idComune);
            if (!Strings.isNullOrEmpty(prop)) {
                Log.APP.info(key + ":" + prop);
                props.setProperty(key, prop);
            }
        } catch (Exception ex) {
            Log.APP.error("ERRORE: PROPRIETA MAIL " + key + " inesistente \n", ex);
        }
    }

    @Override
    public Long countMailNonInviate(Filter filter, boolean isSuperuser) {
        return emailDao.countMailNonInviate(filter, isSuperuser);
    }

    @Override
    public List<EmailDTO> getMailNonInviate(Filter filter, boolean isSuperuser) {
        List<Email> emails = emailDao.findMailNonInviate(filter, isSuperuser);
        List<EmailDTO> dtos = new ArrayList<EmailDTO>();
        if (emails != null && !emails.isEmpty()) {
            for (Email email : emails) {
                EmailDTO dto = emailSerializer.serialize(email);
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public List<Email> getEmailNonInviate() {
        return emailDao.findEmailDaSpedire();
    }

//    @Override
//    public void markMailToResend(Integer emailId) throws Exception {
//        Email mail = emailDao.findByIdEmail(emailId);
//        LkStatiMail mailDaInviare = lookupDao.findStatoMailByCodice("S");
//        mail.setStato(mailDaInviare);
//        emailDao.update(mail);
//    }
    @Override
    public LkStatiMail findStatoMailByCodice(String codiceStatoEmail) {
        LkStatiMail stato = emailDao.findstatoByCodStato(codiceStatoEmail);
        return stato;
    }

    @Override
    public Email findByIdEmail(Integer idEmail) {
        Email email = emailDao.findByIdEmail(idEmail);
        return email;
    }

    @Override
    public void updateEmail(Email email) throws Exception {
        emailDao.update(email);
        aggiornaStato(email);
    }

    private String getTipoDestinazione(Object content) {
        String ret = StatiEmail.SCONOSCIUTA;
        if (content instanceof String) {
            String body = (String) content;
            if (body.contains(StatiEmail.STRING_POSTA_CERTIFICATA)) {
                ret = StatiEmail.POSTA_CERTIFICATA;
            }
            if (body.contains(StatiEmail.STRING_POSTA_ORDINARIA)) {
                ret = StatiEmail.POSTA_ORDINARIA;
            }
        }
        return ret;
    }

    private void createNotificationTask(Email email) throws Exception {
        Log.WORKFLOW.info("Crea notifica di errore per email con ID EMAIL: " + email.getIdEmail());
        List<String> candidateUsers = workflowAction.getCandidateUsers(email.getIdPraticaEvento(), true);
        String notifica = messageSource.getMessage("pratica.email.notifica", Arrays.asList(email.getOggettoEmail(), email.getEmailDestinatario()).toArray(), Locale.getDefault());
        notificationEngine.createNotification(email.getIdPraticaEvento(), candidateUsers, notifica);
    }
}