package it.wego.cross.mailservices.component;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.MessageIDTerm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.wego.cross.mailservices.sql.DaoConfigCross;

@Component
public class MailReaderCrossWeb extends MailReaderCross{

	private static final Logger logger = LoggerFactory.getLogger(MailReaderCrossWeb.class.getName());
	
	public MailReaderCrossWeb() {
		dao = new DaoConfigCross();
	}

	public void elaboraInboxByMessageId(Integer idEnte, String messageId) throws SQLException {
		logger.info("Inside MailReaderCrossWeb.elaboraInboxByMessageId...");

		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

		final String keyword = "CONSEGNA";

		String host = "";
		int port = 0;
		String userName = "";
		String password = "";

		Date today = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_MONTH, -10);
		Date today10 = cal.getTime();

		dao.getConfiguration(idEnte);

		host = dao.getsServerImap(); 
		port = Integer.valueOf(dao.getsServerImapPort()).intValue();
		userName = dao.getsUsernameServerImap();
		password = dao.getsPasswordServerImap();

		logger.debug("Inside MailReaderCrossWeb.elaboraInboxByMessageId, parameter host" + host);
		logger.debug("Inside MailReaderCrossWeb.elaboraInboxByMessageId, parameter port" + port);
		logger.debug("Inside MailReaderCrossWeb.elaboraInboxByMessageId, parameter userName" + userName);
		logger.debug("Inside MailReaderCrossWeb.elaboraInboxByMessageId, parameter password" + password);
		try {
			Message messages[] = null;
			Store store = null;
			Session session = null;

			Date dataDa=null;
			Date data=null;
			Date dataA=null;

			
			dao.loggerDB("ElaboraInboxMassivo messageId di ricerca mail:" + messageId);
			MessageIDTerm st = new MessageIDTerm(messageId);

			if(port == IMAP) {			
				host = dao.getsServerImapWebMail(); 
				port = Integer.valueOf(dao.getsServerImapPort()).intValue();
				userName = dao.getsServerImapWebMailUsername();
				password = dao.getsServerImapWebMailPassowrd();
				MailReaderImap imapConn = new MailReaderImap();
				imapConn.connect(host, userName, password);
				inbox = imapConn.openFolder("INBOX");			
				session = imapConn.getSession();
				store = imapConn.getStore();
				messages = inbox.search(st);				
			} else { //POP3
				host = dao.getsMailHost(); 
				port = Integer.valueOf(dao.getsServerImapPort()).intValue();
				userName = dao.getsUsernameServerImap();
				password = dao.getsPasswordServerImap();
				MailReaderPop3 pop3Conn = new MailReaderPop3();
				inbox = pop3Conn.authenticate(host, userName, password);
				session = pop3Conn.getSession();
				store = pop3Conn.getStore();				
				messages = inbox.getMessages();				
			}

			//messages = inbox.search(andTerm);				
			
			logger.info("No. of Messages : " + messages.length);

			if(messages.length>0) {
				elaboraRicevutePec(idEnte, messages, session);
			}

			inbox.close(true);
			store.close();
			
		} catch (Exception ex) {
			logger.error("Exception arise at the time of read mail", ex);
		}

	}
}