/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 *  
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * 
 * For convenience a plain text copy of the English version
 * of the Licence can be found in the file LICENCE.txt in
 * the top-level directory of this software distribution.
 * 
 * You may obtain a copy of the Licence in any of 22 European
 * Languages at:
 * 
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
/*

  Licenza:	    Licenza Progetto PEOPLE
  Fornitore:    CEFRIEL
  Autori:       M. Pianciamore, P. Selvini

  Questo codice sorgente � protetto dalla licenza valida nell'ambito del
  progetto PEOPLE. La propriet� intellettuale di questo codice � e rester�
  esclusiva di "CEFRIEL Societ� Consortile a Responsabilit� Limitata" con
  sede legale in via Renato Fucini 2, 20133 Milano (MI).

  Disclaimer:

  COVERED CODE IS PROVIDED UNDER THIS LICENSE ON AN "AS IS" BASIS, WITHOUT
  WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, WITHOUT 
  LIMITATION, WARRANTIES THAT THE COVERED CODE IS FREE OF DEFECTS, MERCHANTABLE,
  FIT FOR A PARTICULAR PURPOSE OR NON-INFRINGING. THE ENTIRE RISK AS TO THE
  QUALITY AND PERFORMANCE OF THE COVERED CODE IS WITH YOU. SHOULD ANY COVERED
  CODE PROVE DEFECTIVE IN ANY RESPECT, YOU (NOT THE INITIAL DEVELOPER OR ANY
  OTHER CONTRIBUTOR) ASSUME THE COST OF ANY NECESSARY SERVICING, REPAIR OR
  CORRECTION.
    
*/
package it.idp.people.admin.sqlmap.util;

import it.idp.people.admin.sqlmap.common.IdpPeopleAdminConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailSender {

	private static Logger logger = LoggerFactory.getLogger(MailSender.class);

	// attributi relativi alla composizione di una mail
	protected InternetAddress originator = null;

	protected List destinations = null;

	protected String subject = null;

	protected List msgText = null;

	protected String smtphost = null;

	protected static final String PROTOCOL_SMTP = "smtp";

	protected static final String PROTOCOL_SMTPS = "smtps";

	protected String transport_protocol = PROTOCOL_SMTP;

	protected String smtpPort = "25";

	protected boolean useAuth = false;

	protected String userName = null;

	protected String password = null;

	protected boolean useSSL = false;

	protected boolean useTLS = false;

	protected boolean debug = false;

	public MailSender() {
		this.destinations = new ArrayList();
		this.msgText = new ArrayList();
	}

	public void initialize(Properties props) throws Exception {

		setOriginator(props.getProperty(IdpPeopleAdminConstants.MAIL_ORIGINATOR));
		addDestination(props.getProperty(IdpPeopleAdminConstants.MAIL_DESTINATION), "TO");
		setSubject(props.getProperty(IdpPeopleAdminConstants.MAIL_SUBJECT));
		addMsgText(props.getProperty(IdpPeopleAdminConstants.MAIL_MESSAGE));
		setSMTPhost(props.getProperty(IdpPeopleAdminConstants.SMTP_SERVER_HOST));
		setSMTPPort(props.getProperty(IdpPeopleAdminConstants.SMTP_SERVER_PORT));
		setUserName(props.getProperty(IdpPeopleAdminConstants.SMTP_SERVER_USERNAME));
		setPassword(props.getProperty(IdpPeopleAdminConstants.SMTP_SERVER_PASSWORD));
		setUseAuth(Boolean.valueOf(props.getProperty(IdpPeopleAdminConstants.SMTP_SERVER_USEAUTH)).booleanValue());
		setUseSSL(Boolean.valueOf(props.getProperty(IdpPeopleAdminConstants.SMTP_SERVER_USESSL)).booleanValue(), 
				  Boolean.valueOf(props.getProperty(IdpPeopleAdminConstants.SMTP_SERVER_USETLS)).booleanValue());
		setDebug(Boolean.valueOf(props.getProperty(IdpPeopleAdminConstants.MAIL_SESSION_DEBUG)).booleanValue());

	}

	public void setOriginator(String originator) throws Exception {
		try {
			this.originator = new InternetAddress(originator);
		} catch (AddressException e) {
			logger.error("MailSender::setOriginator::Exception: " +	e.getMessage());
			throw new Exception("MailSender::setOriginator::Exception: " + e.getMessage());
		}
	}

	public void addDestination(String destination, String type)
			throws Exception {
		HashMap curMap = new HashMap();
		try {
			InternetAddress address = new InternetAddress(destination);
			curMap.put("address", address);
			curMap.put("type", type);
			this.destinations.add(curMap);
		} catch (AddressException e) {
			logger.error("MailSender::addDestination::Exception: " + e.getMessage());
			throw new Exception("MailSender::addDestination::Exception: " + e.getMessage());
		}
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void addMsgText(String text) {
		this.msgText.add(text);
	}

	public void setSMTPhost(String smtphost) {
		this.smtphost = smtphost;
	}

	public void setSMTPPort(String port) {
		this.smtpPort = port;
	}

	public void setUseSSL(boolean useSSL, boolean useTLS) {
		this.useSSL = useSSL;
		this.useTLS = useTLS;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
		// this.useAuth = true;
	}

	public void setUseAuth(boolean _useAuth) {
		this.useAuth = _useAuth;
	}

	public boolean isUseAuth() {
		return useAuth;
	}

	public boolean isUseSSL() {
		return useSSL;
	}

	public boolean isUseTLS() {
		return useTLS;
	}

	public void sendMail() throws Exception {

		Session session = MailSenderHelper.getMailSession(smtphost, smtpPort,
				useAuth, userName, password, useSSL, useTLS);
		Boolean b = new Boolean(this.debug);
		if (this.debug)
			session.getProperties().put("mail.debug", b.toString());

		session.setDebug(debug);
		Properties p = session.getProperties();
		logger.debug("Mail Session Propertes:");
		logger.debug(p.toString());

		try {
			Message msg = new MimeMessage(session);

			msg.setFrom(originator);

			for (int i = 0; i < destinations.size(); i++) {
				HashMap curMap = (HashMap) destinations.get(i);
				String type = (String) curMap.get("type");
				InternetAddress address = (InternetAddress) curMap.get("address");
				logger.info("Invio e-mail a " + address);
				if (type.equalsIgnoreCase("to"))
					msg.addRecipient(Message.RecipientType.TO, address);
				else if (type.equalsIgnoreCase("cc"))
					msg.addRecipient(Message.RecipientType.CC, address);
				else if (type.equalsIgnoreCase("bcc"))
					msg.addRecipient(Message.RecipientType.BCC, address);
			}

			msg.setSubject(subject);

			// imposta la data di spedizione alla data corrente
			msg.setSentDate(new Date());

			StringBuffer textBuffer = new StringBuffer();
			for (int i = 0; i < msgText.size(); i++) {
				textBuffer.append((String) msgText.get(i));
				textBuffer.append("\n");
			}
			msg.setText(textBuffer.toString());

			msg.saveChanges();

			Transport tr = session.getTransport();
			tr.connect(smtphost, userName, password);
			tr.sendMessage(msg, msg.getAllRecipients());
			tr.close();

			logger.info("Mail sent!");

		} catch (MessagingException mex) {

			logger.error("MailSender::sendMail::Exception");
			Exception ex = mex;
			do {
				if (ex instanceof SendFailedException) {
					SendFailedException sfex = (SendFailedException) ex;
					Address[] invalid = sfex.getInvalidAddresses();
					if (invalid != null) {
						logger.error(" ** Invalid Addresses");
						if (invalid != null) {
							for (int i = 0; i < invalid.length; i++)
							logger.error(" " + invalid[i]);
						}
					}
					Address[] validUnsent = sfex.getValidUnsentAddresses();
					if (validUnsent != null) {
						logger.error(" ** ValidUnsent Addresses");
						if (validUnsent != null) {
							for (int i = 0; i < validUnsent.length; i++)
							logger.error(" " + validUnsent[i]);
						}
					}
					Address[] validSent = sfex.getValidSentAddresses();
					if (validSent != null) {
						logger.error(" ** ValidSent Addresses");
						if (validSent != null) {
							for (int i = 0; i < validSent.length; i++)
							logger.error(" " + validSent[i]);
						}
					}
				}
				System.out.println();
				if (ex instanceof MessagingException)
					ex = ((MessagingException) ex).getNextException();
				else
					ex = null;
			} while (ex != null);
			logger.error("", mex);
			throw new Exception("MailSender::sendMail::Exception: "	+ mex.getMessage());
		}
	}

//	public static void main(String[] args) {
//
//		try {
//
//			System.out.println("javax.net.ssl.trustStoreType: "
//					+ System.getProperty("javax.net.ssl.trustStoreType"));
//			System.out.println("javax.net.ssl.trustStore: "
//					+ System.getProperty("javax.net.ssl.trustStore"));
//			System.out.println("javax.net.ssl.trustStorePassword: "
//					+ System.getProperty("javax.net.ssl.trustStorePassword"));
//
//			Properties props = new Properties();
//			String propFile = args[0];
//			try {
//				props.load(new FileInputStream(propFile));
//			} catch (Exception ex) {
//				System.out.println("E' stato specificato come argomento un file non valido: "	+ propFile);
//				System.out.println("Indicare il file di properties contenente i parametri per l'invio mail come argomento sulla riga di comando.");
//				ex.printStackTrace();
//			}
//
//			MailSender mail = new MailSender();
//
//			mail.initialize(props);
//			mail.sendMail();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}

}
