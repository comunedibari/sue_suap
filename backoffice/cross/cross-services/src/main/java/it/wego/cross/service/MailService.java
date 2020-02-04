package it.wego.cross.service;

import it.wego.cross.dto.EmailDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Email;
import it.wego.cross.entity.LkStatiMail;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.mail.EmailConfig;
import java.util.List;
import javax.mail.Message;
import javax.mail.Session;
import org.springframework.stereotype.Service;

/**
 *
 * @author giuseppe
 */
@Service
public interface MailService {

    public void salvaMailInDb(PraticheEventi evento, List<String> to, String subject, String content) throws Exception;

    public void gestioneEmail(Message message, EmailConfig emailConfig, Session session) throws Exception;

    public void gestioneEmail() throws Exception;

////////    public void rispedisciEmail(EmailDTO idEmail) throws Exception;

    public void cambiaStato(Integer email, String nuovoStato) throws Exception;

    public Long countMailNonInviate(Filter filter, boolean isSuperuser);

    public List<Email> getEmailNonInviate();
    
    public List<EmailDTO> getMailNonInviate(Filter filter, boolean isSuperuser);

    // public void markMailToResend(Integer emailId) throws Exception;
    
    public void invioSingolaEmail(Email eml) throws Exception;
    
    public EmailConfig getSmtpProprieties(Integer idEnte, Integer idComune) throws Exception;
    
    public LkStatiMail findStatoMailByCodice(String codiceStatoEmail);
    
    public Email findByIdEmail(Integer idEmail);
    
    public void updateEmail(Email email) throws Exception;
    
    public void aggiornaStato(Email emailDB) throws Exception;
    
    public EmailConfig getSmtpProprietiesNoEnte() throws Exception;
}
