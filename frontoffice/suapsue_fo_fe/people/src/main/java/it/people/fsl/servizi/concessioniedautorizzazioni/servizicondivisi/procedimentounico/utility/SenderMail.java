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
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import it.people.util.MailSenderHelper;
import it.people.util.PeopleProperties;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;

public class SenderMail {
	
	private static Logger logger = LoggerFactory.getLogger(SenderMail.class.getName());
	
    private String destinationmail = "SendTo";
    private String sender_userid = "sconosciuto";
    private String sender_password = "sconosciuto";
    private boolean useAuth = false;
    private String host = "";
    private String port = "25";
    private String sender;
	
    //--------------------
    // Start Modifica 2006-04-27 per connessione a server SMTP via SSL
    //Attributi aggiunti per gestione trasporto via SSL (per connessione via PEC)
    private boolean useSSL = false;
    private boolean useTLS = false;
    // End Modifica 2006-04-27 per connessione a server SMTP via SSL
    //--------------------



	public SenderMail() {
        this.host = (String) PeopleProperties.SMTP_MAIL_SERVICEURL.getValue();       
        this.sender_userid = (String) PeopleProperties.SMTP_MAIL_USERNAME.getValue();
        this.sender_password = (String) PeopleProperties.SMTP_MAIL_PASSWORD.getValue();
        this.useAuth = ((String) PeopleProperties.SMTP_MAIL_USEAUTH.getValue()).equals("true");
        this.sender = (String) PeopleProperties.SMTP_MAIL_SENDER.getValue();
        
        //--------------------
        // Start Modifica 2006-04-27 per connessione a server SMTP via SSL
        if ((String) PeopleProperties.SMTP_MAIL_SERVICEPORT.getValue() != null)
            this.port = (String) PeopleProperties.SMTP_MAIL_SERVICEPORT.getValue();
        
        if ((String) PeopleProperties.SMTP_MAIL_USESSL.getValue() != null)
            this.useSSL = ((String) PeopleProperties.SMTP_MAIL_USESSL.getValue()).equals("true");
        
        if ((String) PeopleProperties.SMTP_MAIL_USETLS.getValue() != null)
            this.useTLS = ((String) PeopleProperties.SMTP_MAIL_USETLS.getValue()).equals("true");
        
        // End Modifica 2006-04-27 per connessione a server SMTP via SSL
        //--------------------       
	}

    public void sendMail(String address, String subject, String body,String pathAttach,String nomeFile,String numeroPratica) throws AddressException, MessagingException {

		//--------------------
		// Start Modifica 2006-04-27 per connessione a server SMTP via SSL
		Session session = 
		    MailSenderHelper.getMailSession(host, port, useAuth, sender_userid, sender_password, useSSL, useTLS);
		// End Modifica 2006-04-27 per connessione a server SMTP via SSL
		//--------------------
		
		// Define message
		MimeMessage message = new MimeMessage(session);
		
		//message.setSubject("test");
		message.addHeader("MIME-Version","1.0");
		message.addHeader("Content-Type","text/html");
		message.addHeader("charset","UTF-8");
		
		// Il mittente � � impostato a livello di properties.
		// In questo caso, al contrario dei mail inviati al 
		// termine di una pratica non ha senso usare come mittente 
		// l'indirizzo di mail del cittadino.
		
		try {
			message.setFrom(new InternetAddress(this.sender));
		} catch(MessagingException mex) {
			logger.error("Problema nel settare il mittente del mail:\n" + mex);
			throw mex;
		}
					
		
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(address));        
		message.setSubject(subject+" "+numeroPratica);
		
		/////////////////////////////
		
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(body, "text/html");   
        
        MimeMultipart multipart = new MimeMultipart("related");
        multipart.addBodyPart(messageBodyPart); 
        MimeBodyPart attachFilePart = new MimeBodyPart();
        DataSource source = new FileDataSource(pathAttach);
        attachFilePart.setDataHandler(new DataHandler(source));
        attachFilePart.setFileName(nomeFile);
        // Set the disposition to indicate that this part is an attachment
        attachFilePart.setDisposition(Part.ATTACHMENT);
        multipart.addBodyPart(attachFilePart);    

        message.setContent(multipart);
		
		/////////////////////////////
		
		
		//message.setContent(body, "text/html");
		message.saveChanges();
		
		//--------------------
		// Start Modifica 2006-04-27 per connnessione a server SMTP via SSL
		Transport tr = session.getTransport();
		tr.connect(host,sender_userid, sender_password);
		tr.sendMessage(message, message.getAllRecipients());
		tr.close();
		// End Modifica 2006-04-27 per connnessione a server SMTP via SSL
		//--------------------
    }
}
