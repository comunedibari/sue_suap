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
package it.people.vsl.transport;

import it.people.City;
import it.people.core.PplUser;
import it.people.logger.Logger;
import it.people.process.common.entity.Attachment;
import it.people.process.common.entity.SignedSummaryAttachment;
import it.people.process.common.entity.UnsignedSummaryAttachment;
import it.people.process.sign.entity.SignedData;
import it.people.util.MailSenderHelper;
import it.people.util.PeopleProperties;
import it.people.vsl.PipelineData;
import it.people.vsl.PipelineDataImpl;
import it.people.vsl.SerializablePipelineData;
import it.people.vsl.exception.SendException;

import java.util.HashMap;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail extends TransportLayer {
    private static org.apache.log4j.Logger systemLogger = org.apache.log4j.Logger
	    .getLogger(SendMail.class);
    private String destinationAddress = "SendTo";
    protected static final String ATTACHMENT_LIST_ATTACHMENT_NAME = "ElencoAllegati.txt";
    protected static final String UTF8_ENCODING_NAME = "UTF-8";

    public SendMail(City commune, String processName, String destinationAddress) {
	if (destinationAddress != null && destinationAddress.length() > 0) {
	    this.destinationAddress = destinationAddress;
	}
	this.setComune(commune);
	this.realServiceName = processName;
    }

    /**
     * @deprecated utilizzare SendMail(City commune, String processName, String
     *             destinationAddress)
     * @param destinationAddress
     */
    public SendMail(String destinationAddress) {
	if (destinationAddress != null && destinationAddress.length() > 0) {
	    this.destinationAddress = destinationAddress;
	}
    }

    public HashMap pipeline2transportData(PipelineData data) {
	// Default subject, � ridefinito nelle mail inviate al cittadino ed al
	// funzionario
	String subject = "Pratica People: "
		+ data.getAttribute(PipelineDataImpl.PEOPLE_PROTOCOLL_ID_PARAMNAME);

	HashMap map = new HashMap();
	map.put(TransportLayer.SUBJECT_PARAM, subject);
	map.put(TransportLayer.MESSAGEBODY_PARAM, createMessageBody(data));
	map.put(TransportLayer.ATTACHMENT_PARAM,
		(List) data.getAttribute(PipelineDataImpl.ATTACHMENTS));
	map.put(TransportLayer.SIGNED_ATTACHMENT_PARAM, (List) data
		.getAttribute(PipelineDataImpl.SIGNED_ATTACHMENT_NAME));
	map.put(TransportLayer.SENDER_USER_NAME, ((PplUser) data
		.getAttribute(PipelineDataImpl.USER_PARAMNAME)).getEMail());
	return map;
    }

    public void send(HashMap inParameter, HashMap outParameters) {
	String hostAddress = "";
	String senderUserName = "";
	String senderUserPassword = "";
	boolean useAuth = false;
	String port = "";
	boolean useSSL = false;
	boolean useTLS = false;

	String peopleProtocolId = "";
	String errorMessageHeader = "";
	City commune = this.getComune();
	Logger logger = null;

	try {
	    peopleProtocolId = (String) inParameter
		    .get(TransportLayer.PEOPLE_PROTOCOLL_ID_PARAMNAME);
	    errorMessageHeader = "Errore nell'invio della pratica con identificativo '"
		    + peopleProtocolId + "'";
	} catch (Exception ex) {
	    systemLogger.error(
		    "Impossibile determinare l'identificativo della pratica.",
		    ex);
	    return;
	}

	try {
	    logger = Logger.getLogger(realServiceName, commune);
	} catch (Exception ex) {
	    systemLogger.warn(errorMessageHeader
		    + ", impossibile istanziare il logger.", ex);
	}

	try {
	    if (commune != null) {
		// la lettura dei parametri di configurazione avviene in base
		// all'ente di riferimento
		String communeId = commune.getKey();

		hostAddress = PeopleProperties.SMTP_MAIL_SERVICEURL
			.getValueString(communeId);
		senderUserName = PeopleProperties.SMTP_MAIL_USERNAME
			.getValueString(communeId);
		senderUserPassword = PeopleProperties.SMTP_MAIL_PASSWORD
			.getValueString(communeId);

		useAuth = ((String) PeopleProperties.SMTP_MAIL_USEAUTH
			.getValue(communeId)).equals("true");

		if ((String) PeopleProperties.SMTP_MAIL_SERVICEPORT
			.getValue(communeId) != null)
		    port = (String) PeopleProperties.SMTP_MAIL_SERVICEPORT
			    .getValue(communeId);

		// Attributi aggiunti per gestione trasporto via SSL (per
		// connessione via PEC)
		if ((String) PeopleProperties.SMTP_MAIL_USESSL
			.getValue(communeId) != null)
		    useSSL = ((String) PeopleProperties.SMTP_MAIL_USESSL
			    .getValue(communeId)).equals("true");

		if ((String) PeopleProperties.SMTP_MAIL_USETLS
			.getValue(communeId) != null)
		    useTLS = ((String) PeopleProperties.SMTP_MAIL_USETLS
			    .getValue(communeId)).equals("true");
	    } else {
		// � stato utilizzato il costruttore deprecato senza
		// l'indicazione
		// del comune, si usano i parametri di default.

		hostAddress = PeopleProperties.SMTP_MAIL_SERVICEURL
			.getValueString();
		senderUserName = PeopleProperties.SMTP_MAIL_USERNAME
			.getValueString();
		senderUserPassword = PeopleProperties.SMTP_MAIL_PASSWORD
			.getValueString();

		useAuth = ((String) PeopleProperties.SMTP_MAIL_USEAUTH
			.getValue()).equals("true");

		if ((String) PeopleProperties.SMTP_MAIL_SERVICEPORT.getValue() != null)
		    port = (String) PeopleProperties.SMTP_MAIL_SERVICEPORT
			    .getValue();

		// Attributi aggiunti per gestione trasporto via SSL (per
		// connessione via PEC)
		if ((String) PeopleProperties.SMTP_MAIL_USESSL.getValue() != null)
		    useSSL = ((String) PeopleProperties.SMTP_MAIL_USESSL
			    .getValue()).equals("true");

		if ((String) PeopleProperties.SMTP_MAIL_USETLS.getValue() != null)
		    useTLS = ((String) PeopleProperties.SMTP_MAIL_USETLS
			    .getValue()).equals("true");
	    }
	} catch (Exception ex) {
	    logError(
		    logger,
		    ex,
		    errorMessageHeader
			    + ", impossibile determinare i parametri di configurazione della posta.");
	    return;
	}

	try {

	    // Start Modifica 2006-04-27 per connessione a server SMTP via SSL
	    Session session = MailSenderHelper.getMailSession(hostAddress,
		    port, useAuth, senderUserName, senderUserPassword, useSSL,
		    useTLS);
	    // End Modifica 2006-04-27 per connessione a server SMTP via SSL

	    // Define message
	    MimeMessage message = new MimeMessage(session);

	    if (inParameter.get(TransportLayer.RECIPIENT) != null) {
		message.setFrom(new InternetAddress((String) inParameter
			.get(TransportLayer.RECIPIENT)));
	    } else {
		message.setFrom(new InternetAddress((String) inParameter
			.get(TransportLayer.SENDER_USER_NAME)));
	    }

	    // Imposta se richiesto il replay-to
	    String replayTo = (String) inParameter
		    .get(TransportLayer.REPLAYTO_PARAM);
	    if (replayTo != null) {
		message.setReplyTo(new Address[] { new InternetAddress(replayTo) });
	    }

	    if (inParameter.get(TransportLayer.USER_MAILADDRESS) == null) {
		message.addRecipient(Message.RecipientType.TO,
			new InternetAddress(destinationAddress));
		systemLogger.debug("Sending to " + destinationAddress);
	    } else {
		message.addRecipient(
			Message.RecipientType.TO,
			new InternetAddress((String) inParameter
				.get(TransportLayer.USER_MAILADDRESS)));
		systemLogger.debug("Sending to "
			+ (String) inParameter
				.get(TransportLayer.USER_MAILADDRESS));
	    }

	    if (inParameter.get(TransportLayer.SUBJECT_PARAM) == null)
		throw new Exception("Nessun subject impostato per l'email");

	    message.setSubject((String) inParameter
		    .get(TransportLayer.SUBJECT_PARAM));

	    List attachments = (List) inParameter
		    .get(TransportLayer.ATTACHMENT_PARAM);
	    List signedAttachments = (List) inParameter
		    .get(TransportLayer.SIGNED_ATTACHMENT_PARAM);

	    String recepit = (String) inParameter
		    .get(TransportLayer.ATTACHMENT_DATA);
	    String htmlContent = (String) inParameter
		    .get(TransportLayer.MESSAGEBODY_PARAM);

	    // Crea il contenitore di tutti gli allegati
	    // anche il messaggio originale � un allegato
	    // in particolare � il primo allegato
	    Multipart multipart = new MimeMultipart();
	    message.setContent(multipart);

	    addContent(htmlContent, multipart);
	    addReceipt(recepit, multipart);

	    boolean onlySummary = !((Boolean) inParameter
		    .get(TransportLayer.SEND_RECEIPT_WITH_MAIL_ATTACHMENT))
		    .booleanValue();
	    addAttachment(attachments, multipart, onlySummary);
	    addSignedAttachment(signedAttachments, multipart, onlySummary);

	    // Send message
	    message.saveChanges();

	    // --------------------
	    // Start Modifica 2006-04-27 per connessione a server SMTP via SSL
	    Transport tr = session.getTransport();
	    tr.connect(hostAddress, senderUserName, senderUserPassword);
	    tr.sendMessage(message, message.getAllRecipients());
	    tr.close();
	    // End Modifica 2006-04-27 per connessione a server SMTP via SSL
	    // --------------------

	} catch (AddressException adex) {
	    logError(logger, adex, errorMessageHeader
		    + ", formato dell'indirizzo non valido.");
	} catch (Exception ex) {
	    logError(logger, ex, errorMessageHeader + ".");
	}
    }

    protected void logError(Logger logger, Exception ex, String errorMessage) {
	systemLogger.error(errorMessage, ex);
	if (logger != null)
	    logger.error(errorMessage + "\n" + ex.toString());
    }

    /**
     * Aggiunge la ricevuta
     * 
     * @param recepit
     * @param multipart
     * @throws MessagingException
     */
    private void addReceipt(String recepit, Multipart multipart)
	    throws MessagingException {
	if (recepit != null) {
	    MimeBodyPart messageBodyPart = new MimeBodyPart();
	    DataSource ds = new StringDataSource("Ricevuta.txt.p7m", recepit);
	    messageBodyPart.setDataHandler(new DataHandler(ds));
	    messageBodyPart.setFileName(ds.getName());
	    multipart.addBodyPart(messageBodyPart);
	}
    }

    private void addContent(String htmlContent, Multipart multipart)
	    throws MessagingException {
	MimeBodyPart messageBodyPart = new MimeBodyPart();
	messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(
		htmlContent, "text/html")));
	multipart.addBodyPart(messageBodyPart);
    }

    /**
     * Inserisce gli allegati firmati
     * 
     * @param signedAttachments
     *            allegati firmati
     * @param multipart
     * @throws MessagingException
     */
    private void addSignedAttachment(List signedAttachments,
	    Multipart multipart, boolean onlySummary) throws MessagingException {
	// Attachment Firmati
	if (signedAttachments != null) {
	    SignedData att = null;
	    for (int i = 0; i < signedAttachments.size(); i++) {
		att = (SignedData) signedAttachments.get(i);
		if (att != null && !onlySummary) {
		    String filename = att.getPath();

		    // Part two is attachment
		    systemLogger.debug("File in esame = " + filename);
		    MimeBodyPart messageBodyPart = new MimeBodyPart();
		    DataSource source = new FileDataSource(filename);
		    messageBodyPart.setDataHandler(new DataHandler(source));
		    messageBodyPart.setFileName(att.getFriendlyName());
		    multipart.addBodyPart(messageBodyPart);
		}
	    }
	}
    }

    /**
     * Inserisce gli allegati non firmati
     * 
     * @param attachments
     *            allegati non firmati
     * @param multipart
     * @throws MessagingException
     */
    private void addAttachment(List attachments, Multipart multipart,
	    boolean onlySummary) throws MessagingException {
	// Attachment Non Firmati
	if (attachments != null) {
	    String elencoAllegatiContent = "";
	    boolean includedAttachmentList = false;

	    Attachment attachment = null;
	    for (int i = 0; i < attachments.size(); i++) {
		attachment = (Attachment) attachments.get(i);

		if (attachment != null) {
		    if (attachment instanceof SignedSummaryAttachment) {
			// Il riepilogo firmato va incluso
			addAttachment(attachment, multipart);
		    } else if (!onlySummary
			    && !(attachment instanceof UnsignedSummaryAttachment)) {
			// Gli allegati sono inviati al cittadino
			addAttachment(attachment, multipart);
		    } else if (onlySummary
			    && !(attachment instanceof UnsignedSummaryAttachment)) {
			// Solo l'elenco degli allegati � inviato al cittadino
			elencoAllegatiContent += attachment.getName() + "\n";
			includedAttachmentList = true;
		    }
		}
	    }

	    if (onlySummary && includedAttachmentList) {
		// Aggiunge l'elenco degli allegati
		addAttachmentListAttachment(elencoAllegatiContent, multipart);
	    }
	}
    }

    private void addAttachmentListAttachment(String content, Multipart multipart)
	    throws MessagingException {
	systemLogger.debug("Aggiunta riepilogo elenco allegati");
	MimeBodyPart messageBodyPart = new MimeBodyPart();
	messageBodyPart.setText(content, UTF8_ENCODING_NAME);
	messageBodyPart.setFileName(ATTACHMENT_LIST_ATTACHMENT_NAME);
	multipart.addBodyPart(messageBodyPart);
    }

    private void addAttachment(Attachment attachment, Multipart multipart)
	    throws MessagingException {
	String filename = attachment.getPath();

	// Part two is attachment
	systemLogger.debug("File in esame = " + filename);
	MimeBodyPart messageBodyPart = new MimeBodyPart();
	DataSource source = new FileDataSource(filename);
	messageBodyPart.setDataHandler(new DataHandler(source));

	messageBodyPart.setFileName(attachment.getName());
	multipart.addBodyPart(messageBodyPart);
    }

    public void transportData2pipeline(PipelineData data, HashMap params) {

    }

    public void chekStatus(HashMap inParameter, HashMap outParameters)
	    throws SendException {
	outParameters.put(TransportLayer.DELIVERY_ACCEPTANCE_STATUS, "3");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.people.vsl.transport.TransportLayer#pipelineData2SerializablePipelineData
     * (it.people.vsl.PipelineData)
     */
    @Override
    public SerializablePipelineData pipelineData2SerializablePipelineData(
	    PipelineData data) {
	// TODO Verifiy if a SendMail ia recoverable transport
	return null;
    }

}
