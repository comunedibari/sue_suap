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
package it.people.console.system.mail;

import java.net.ConnectException;
import java.util.Date;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.people.console.config.ConsoleConfiguration;
import it.people.console.dto.IMailAttachment;
import it.people.console.system.MessageSourceAwareClass;
import it.people.console.utils.Constants;
import it.people.console.utils.MailSessionUtils;
import it.people.console.utils.StringUtils;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 15/lug/2011 14.59.23
 *
 */
public class MailSender extends MessageSourceAwareClass implements TransportListener {
	private static Logger logger = LoggerFactory.getLogger(MailSender.class);
	private boolean isSuccessfull = false;
	
	public boolean sendMail(HttpServletRequest request, InternetAddress recipientEMailAddress, 
			String subject, String body, 
			IMailAttachment[] attachments) {

    	ConsoleConfiguration consoleConfiguration = (ConsoleConfiguration)request.getSession().getServletContext().getAttribute(Constants.System.SERVLET_CONTEXT_CONSOLE_CONFIGURATION_PROPERTIES);
		
    	String bodyFooter = this.getProperty("certificate.send.mail.subject.footer");
    	if (!StringUtils.isEmptyString(bodyFooter)) {
    		body += bodyFooter;
    	}
    	
		MimeMessage mimeMessage = null;
		
		try {
			Session mailSession = MailSessionUtils.getMailTransportSession(consoleConfiguration);

			mimeMessage = new MimeMessage(mailSession);

			mimeMessage.addHeader("MIME-Version","1.0");
			mimeMessage.addHeader("Content-Type","text/html");
			mimeMessage.addHeader("charset","UTF-8");

			mimeMessage.setFrom(new InternetAddress(consoleConfiguration.getMailTransportFrom()));

			mimeMessage.addRecipient(Message.RecipientType.TO, recipientEMailAddress);
			mimeMessage.setSubject(subject);

			mimeMessage.setSentDate(new Date());

			if (attachments != null && attachments.length > 0) {

				Multipart multipart = new MimeMultipart();
				
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(body, "text/html");
				multipart.addBodyPart(messageBodyPart);
				
				for(int attachmentIndex = 0;  attachmentIndex < attachments.length; attachmentIndex++) {

					IMailAttachment mailAttachment = attachments[attachmentIndex];
					byte[] attachmentBytes = new byte[mailAttachment.getAttachment().available()];
					mailAttachment.getAttachment().read(attachmentBytes);

					messageBodyPart = new MimeBodyPart();        
					DataHandler  dataHandler=new DataHandler(new ByteArrayDataSource(attachmentBytes, 
							mailAttachment.getMimeType()));
					messageBodyPart.setDataHandler(dataHandler);
					messageBodyPart.setFileName(mailAttachment.getName());
					multipart.addBodyPart(messageBodyPart);

				}

				mimeMessage.setContent(multipart);
				
			}
			else {
				mimeMessage.setText(body);
			}

			mimeMessage.saveChanges();

			if (consoleConfiguration.isMailTransportAuth()) {
				Transport transport = mailSession.getTransport();
				transport.addTransportListener(this);
				transport.connect(consoleConfiguration.getMailTransportHost(), 
						consoleConfiguration.getMailTransportUsername(), 
						consoleConfiguration.getMailTransportPassword());
				transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
				transport.close();
			}
			else {
				Transport transport = mailSession.getTransport();
				transport.addTransportListener(this);
				transport.connect();
				transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
				transport.close();
			}

			if (logger.isDebugEnabled()) {
				logger.debug("MESSAGE-ID: " + mimeMessage.getMessageID());
			}
			
		} catch (MessagingException e) {
			logger.error("", e);
			this.setSuccessfull(false);
		} catch (ConnectException e) {
			logger.error("", e);
			this.setSuccessfull(false);
		} catch (Exception e) {
			logger.error("", e);
			this.setSuccessfull(false);
		}

		return this.isSuccessfull();

	}

	/* (non-Javadoc)
	 * @see javax.mail.event.TransportListener#messageDelivered(javax.mail.event.TransportEvent)
	 */
	public void messageDelivered(TransportEvent transportEvent) {

		if (logger.isDebugEnabled()) {
			logger.debug("Delivered transport event raised...");
		}
		
		this.setSuccessfull(true);

	}

	/* (non-Javadoc)
	 * @see javax.mail.event.TransportListener#messageNotDelivered(javax.mail.event.TransportEvent)
	 */
	public void messageNotDelivered(TransportEvent transportEvent) {

		if (logger.isDebugEnabled()) {
			logger.debug("Not delivered transport event raised...");
		}
		
		this.setSuccessfull(false);
		
	}

	/* (non-Javadoc)
	 * @see javax.mail.event.TransportListener#messagePartiallyDelivered(javax.mail.event.TransportEvent)
	 */
	public void messagePartiallyDelivered(TransportEvent transportEvent) {

		if (logger.isDebugEnabled()) {
			logger.debug("Message partially delivered transport event raised...");
		}
		
		this.setSuccessfull(true);
		
	}

	/**
	 * @return the isSuccessfull
	 */
	private boolean isSuccessfull() {
		return this.isSuccessfull;
	}

	/**
	 * @param isSuccessfull the isSuccessfull to set
	 */
	private void setSuccessfull(boolean isSuccessfull) {
		this.isSuccessfull = isSuccessfull;
	}
	
}
