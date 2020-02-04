/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.actions;

import it.wego.cross.constants.Constants;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.dao.AuthorizationDao;
import it.wego.cross.dao.ConfigurationDao;
import it.wego.cross.dao.EntiDao;
import it.wego.cross.dao.UtentiDao;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.Permessi;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.Utente;
import it.wego.cross.entity.UtenteRuoloEnte;
import it.wego.cross.entity.UtenteRuoloProcedimento;
import it.wego.cross.entity.UtenteRuoloProcedimentoPK;
import it.wego.cross.mail.EmailConfig;
import it.wego.cross.service.MailService;
import it.wego.cross.service.UtentiService;
import it.wego.cross.utils.Log;

import java.util.Date;
import java.util.Locale;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;


/**
 *
 * @author CS
 */
@Component
public class UtentiAction {

    @Autowired
    private UtentiDao utentiDao;
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private AuthorizationDao authorizationDao;
    @Autowired
    private EntiDao entiDao;
    @Autowired
	private ConfigurationDao configurationDao;
	@Autowired
	private MailService mailService;
	@Autowired
	private MessageSource messageSource;

    @Transactional(rollbackFor = Exception.class)
    public void salvaUtente(Utente utente) throws Exception {
        utentiDao.update(utente);
    }

    @Transactional(rollbackFor = Exception.class)
    public void modificaUtente(UtenteDTO utenteDTO) throws Exception {
        Utente utente = utentiService.findUtenteByIdUtente(utenteDTO.getIdUtente());
        utente.setCodiceFiscale(utenteDTO.getCodiceFiscale());
        utente.setUsername(utenteDTO.getUsername());
        utente.setCognome(utenteDTO.getCognome());
        utente.setNome(utenteDTO.getNome());
        utente.setEmail(utenteDTO.getEmail());
        utente.setNome(utenteDTO.getNome());
        utente.setNote(utenteDTO.getNote());
        utente.setTelefono(utenteDTO.getTelefono());
        utente.setSuperuser(utenteDTO.getSuperuser().charAt(0));
        if (!Strings.isNullOrEmpty(utenteDTO.getPassword())) {
            utente.setPassword(utentiService.encodePasswordWithSsha(utenteDTO.getPassword()));
        }
        utentiDao.update(utente);
    }

    @Transactional(rollbackFor = Exception.class)
    public void disabilitaUtente(Integer idUtente) throws Exception {
        Utente utente = utentiDao.findUtenteByIdUtente(idUtente);
        utente.setStatus(Constants.UTENTE_NON_ATTIVO);
        utentiDao.update(utente);
    }

    @Transactional(rollbackFor = Exception.class)
    public void riabilitaUtente(Integer idUtente) throws Exception {
        Utente utente = utentiDao.findUtenteByIdUtente(idUtente);
        utente.setStatus(Constants.UTENTE_ATTIVO);
        utentiDao.update(utente);
    }

    @Transactional(rollbackFor = Exception.class)
    public void eliminaRuolo(Integer codUtente, Integer idEnte, String permesso) throws Exception {
        // TODO -- attenzione 
        UtenteRuoloEnte ruolo = authorizationDao.getRuoloByKey(codUtente, idEnte, permesso);
        if (ruolo != null) {
            authorizationDao.delete(ruolo);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void salvaRuolo(Integer idUtente, Integer idEnte, String permesso) throws Exception {
        Permessi p = utentiService.findByCodPermesso(permesso);
        Enti e = entiDao.findByIdEnte(idEnte);
        Utente u = utentiDao.findUtenteByIdUtente(idUtente);
        UtenteRuoloEnte ure = new UtenteRuoloEnte();
        ure.setIdEnte(e);
        ure.setCodPermesso(p);
        ure.setIdUtente(u);
        authorizationDao.update(ure);
    }

    @Transactional
    public void rimuoviUtente(Integer idUtente) throws Exception {
        Utente utente = utentiDao.findUtenteByIdUtente(idUtente);
        utentiDao.delete(utente);
    }

    @Transactional
    public void eliminaRuoloProcedimento(UtenteRuoloProcedimento utenteRuoloProcedimento) throws Exception {
        UtenteRuoloProcedimento utenteRuoloProcedimentoToRemove = authorizationDao.referenceUtenteRuoloProcedimentoPK(utenteRuoloProcedimento);
        authorizationDao.delete(utenteRuoloProcedimentoToRemove);
    }

    @Transactional
    public void inserisciRuoloProcedimento(UtenteRuoloEnte utenteRuoloEnte, ProcedimentiEnti procedimentoEnte) throws Exception {
        UtenteRuoloProcedimentoPK urpPK = new UtenteRuoloProcedimentoPK();
        UtenteRuoloProcedimento urp = new UtenteRuoloProcedimento();
        urp.setUtenteRuoloEnte(utenteRuoloEnte);
        urp.setProcedimentiEnti(procedimentoEnte);
        urpPK.setIdUtenteRuoloEnte(utenteRuoloEnte.getIdUtenteRuoloEnte());
        urpPK.setIdProcEnte(procedimentoEnte.getIdProcEnte());
        urp.setUtenteRuoloProcedimentoPK(urpPK);
        authorizationDao.update(urp);
    }
    
    @Transactional(rollbackFor = Exception.class)
	public void recuperaPassword(UtenteDTO utenteRicercato, String oggettoEmail) throws Throwable  {
		String nuovaPassword=RandomStringUtils.randomAlphanumeric(8);

		//Modifica Password Utente
		utenteRicercato.setPassword(nuovaPassword);
		try {
			modificaUtente(utenteRicercato);
			Log.APP.info("Inviata Password modificata per l'utente:"+utenteRicercato.getUsername()+"\n"+ "Nuova password:"+nuovaPassword);

		} catch (Exception e) {
			Log.APP.error("Errore durante la modifica della password per l'utente:"+utenteRicercato.getUsername());
			e.printStackTrace();
		}
		
		//Creo corpo Email
		StringBuilder stringBuilder = new StringBuilder();
//		ResourceBundle rb = ResourceBundle.getBundle("src/main/resource.it.wego.cross.actions.recuperoPassword");
//		String corpoEmail_1="Egregio/a "+utenteRicercato.getNome().toUpperCase()+" "+utenteRicercato.getCognome().toUpperCase()+", ";
//		String corpoEmail_2="la sua nuova password \u00E8: "+nuovaPassword +".\n";
//		String corpoEmail_3="La pu\u00F2 modificare utilizzando la funzionalit\u00E0 di cambio password dell'applicazione.";
		String corpoEmail_1 = messageSource.getMessage("email.msg.egregio", null, Locale.getDefault())+" "+utenteRicercato.getNome().toUpperCase()+" "+utenteRicercato.getCognome().toUpperCase()+", ";		
		String corpoEmail_2= messageSource.getMessage("email.msg.nuova.password", null, Locale.getDefault())+" "+nuovaPassword +".\n";
		String corpoEmail_3=messageSource.getMessage("email.msg.nuova.cambio.password", null, Locale.getDefault());
		stringBuilder.append(corpoEmail_1);
		stringBuilder.append(corpoEmail_2);
		stringBuilder.append(corpoEmail_3);
		String corpoMail=stringBuilder.toString();
		
		//Setto le i parametri per l'invio email
		EmailConfig mailConfig=mailService.getSmtpProprietiesNoEnte();
		final String username=mailConfig.getProperties().getProperty( SessionConstants.MAIL_USERNAME);
		final String password=mailConfig.getProperties().getProperty( SessionConstants.MAIL_PASSWORD);
				
		Session mailSession = Session.getInstance(mailConfig.getProperties(), new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication( username, password);
			}
		});

		//mailSession.setDebug(true); 
		
		//Preparo e invio l'email
		MimeMessage msg = new MimeMessage( mailSession );
		msg.setFrom( new InternetAddress(mailConfig.getProperties().getProperty( SessionConstants.MAIL_MITTENTE)));
		msg.setRecipients( Message.RecipientType.TO,InternetAddress.parse(utenteRicercato.getEmail()) );
		msg.setSentDate( new Date());
		msg.setSubject(oggettoEmail );
		//msg.setText(corpoMail);
		msg.setText(corpoMail, "utf-8");
		
		Transport.send(msg);
		
		Log.APP.info("Inviata nuova password all'indirizzo:"+utenteRicercato.getEmail());

	}

}
