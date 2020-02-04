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
 * EMailSender.java
 *
 * Created on 16 marzo 2005, 16.43
 */

package it.people.util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Category;

/**
 * Utility class per l'invio di mail ad un determinato indirizzo. La classe
 * utilizza i parametri di configurazione del portale per ottenere indirizzo del
 * server SMTP, i parametri di connessione e eventuali username e password per
 * la connessione al server SMTP stesso.
 * 
 * @author manelli
 */
public class EMailSender {

    private Category cat = Category.getInstance(EMailSender.class.getName());

    private String destinationmail = "SendTo";
    private String sender_userid = "sconosciuto";
    private String sender_password = "sconosciuto";
    private boolean useAuth = false;
    private String host = "";
    private String port = "25";
    private String sender;

    // --------------------
    // Start Modifica 2006-04-27 per connnessione a server SMTP via SSL
    // Attributi aggiunti per gestione trasporto via SSL (per connessione via
    // PEC)
    private boolean useSSL = false;
    private boolean useTLS = false;

    // End Modifica 2006-04-27 per connnessione a server SMTP via SSL
    // --------------------

    /**
     * Crea una nuova istanza di EMailSender
     * 
     * @deprecated il costruttore � deprecato in quanto non consente di
     *             leggere le propriet� specifiche per l'ente nel caso del
     *             multiente
     */
    public EMailSender() {
	this.host = (String) PeopleProperties.SMTP_MAIL_SERVICEURL.getValue();
	this.sender_userid = (String) PeopleProperties.SMTP_MAIL_USERNAME
		.getValue();
	this.sender_password = (String) PeopleProperties.SMTP_MAIL_PASSWORD
		.getValue();
	this.useAuth = ((String) PeopleProperties.SMTP_MAIL_USEAUTH.getValue())
		.equals("true");
	this.sender = (String) PeopleProperties.SMTP_MAIL_SENDER.getValue();

	// --------------------
	// Start Modifica 2006-04-27 per connnessione a server SMTP via SSL
	if ((String) PeopleProperties.SMTP_MAIL_SERVICEPORT.getValue() != null)
	    this.port = (String) PeopleProperties.SMTP_MAIL_SERVICEPORT
		    .getValue();

	if ((String) PeopleProperties.SMTP_MAIL_USESSL.getValue() != null)
	    this.useSSL = ((String) PeopleProperties.SMTP_MAIL_USESSL
		    .getValue()).equals("true");

	if ((String) PeopleProperties.SMTP_MAIL_USETLS.getValue() != null)
	    this.useTLS = ((String) PeopleProperties.SMTP_MAIL_USETLS
		    .getValue()).equals("true");

	// End Modifica 2006-04-27 per connnessione a server SMTP via SSL
	// --------------------
    }

    /**
     * Crea una nuova istanza di EMailSender. Il costruttore prevede il supporto
     * al multiente
     * 
     * @param communeId
     *            codice istat dell'ente per il quale leggere le impostazioni di
     *            invio
     */
    public EMailSender(String communeId) {
	this.host = (String) PeopleProperties.SMTP_MAIL_SERVICEURL
		.getValue(communeId);
	this.sender_userid = (String) PeopleProperties.SMTP_MAIL_USERNAME
		.getValue(communeId);
	this.sender_password = (String) PeopleProperties.SMTP_MAIL_PASSWORD
		.getValue(communeId);
	this.useAuth = ((String) PeopleProperties.SMTP_MAIL_USEAUTH
		.getValue(communeId)).equals("true");
	this.sender = (String) PeopleProperties.SMTP_MAIL_SENDER
		.getValue(communeId);

	// Start Modifica 2006-04-27 per connnessione a server SMTP via SSL
	if ((String) PeopleProperties.SMTP_MAIL_SERVICEPORT.getValue(communeId) != null)
	    this.port = (String) PeopleProperties.SMTP_MAIL_SERVICEPORT
		    .getValue(communeId);

	if ((String) PeopleProperties.SMTP_MAIL_USESSL.getValue(communeId) != null)
	    this.useSSL = ((String) PeopleProperties.SMTP_MAIL_USESSL
		    .getValue(communeId)).equals("true");

	if ((String) PeopleProperties.SMTP_MAIL_USETLS.getValue() != null)
	    this.useTLS = ((String) PeopleProperties.SMTP_MAIL_USETLS
		    .getValue(communeId)).equals("true");

	// End Modifica 2006-04-27 per connnessione a server SMTP via SSL
    }

    /**
     * Invia una mail ad un determinato indirizzo
     * 
     * @param address
     *            l'EMail da contattare
     * @param subject
     *            l'oggetto della mail
     * @param body
     *            il corpo della mail
     */
    public void sendMail(String address, String subject, String body)
	    throws AddressException, MessagingException {

	// --------------------
	// Start Modifica 2006-04-27 per connnessione a server SMTP via SSL
	Session session = MailSenderHelper.getMailSession(host, port, useAuth,
		sender_userid, sender_password, useSSL, useTLS);
	// End Modifica 2006-04-27 per connnessione a server SMTP via SSL
	// --------------------

	// Define message
	MimeMessage message = new MimeMessage(session);

	// message.setSubject("test");
	message.addHeader("MIME-Version", "1.0");
	message.addHeader("Content-Type", "text/html");
	message.addHeader("charset", "UTF-8");

	// Il mittente � � impostato a livello di properties.
	// In questo caso, al contrario dei mail inviati al
	// termine di una pratica non ha senso usare come mittente
	// l'indirizzo di mail del cittadino.

	try {
	    message.setFrom(new InternetAddress(this.sender));
	} catch (MessagingException mex) {
	    cat.error("Problema nel settare il mittente del mail:\n" + mex);
	    throw mex;
	}

	message.addRecipient(Message.RecipientType.TO, new InternetAddress(
		address));
	message.setSubject(subject);

	message.setContent(body, "text/html");
	message.saveChanges();

	// --------------------
	// Start Modifica 2006-04-27 per connnessione a server SMTP via SSL
	Transport tr = session.getTransport();
	tr.connect(host, sender_userid, sender_password);
	tr.sendMessage(message, message.getAllRecipients());
	tr.close();
	// End Modifica 2006-04-27 per connnessione a server SMTP via SSL
	// --------------------
    }

}
