/**
 * 
 */
package it.people.vsl.transport;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.activation.DataHandler;
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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.people.City;
import it.people.db.DBConnector;
import it.people.exceptions.PeopleDBException;
import it.people.process.AbstractPplProcess;
import it.people.process.PplData;
import it.people.process.data.AbstractData;
import it.people.util.FormatterFunction;
import it.people.util.FormattersUtil;
import it.people.util.MailSenderHelper;
import it.people.util.PeopleProperties;
import it.people.util.VelocityUtils;
import it.people.util.dto.VelocityModelObject;
import it.people.util.dto.VelocityValue;
import it.people.vsl.PipelineData;
import it.people.vsl.PipelineDataHolder;
import it.people.vsl.PipelineDataImpl;
import it.people.vsl.SerializablePipelineData;
import it.people.vsl.exception.FormatterException;
import it.people.vsl.exception.SendException;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         08/ott/2012 16:43:46
 */
public class SendPplAdminMail extends TransportLayer {

    public static final String RECIPIENTS_E_MAIL_ADDRESSES = "RECIPIENTS_E_MAIL_ADDRESSES";

    private static Logger logger = LogManager.getLogger(SendPplAdminMail.class);

    private PplMailType mailType;

    private City commune;

    private AbstractPplProcess process;

    /**
     * @param mailType
     * @param commune
     * @param pipelineDataHolder
     */
    public SendPplAdminMail(PplMailType mailType, City commune,
	    PipelineDataHolder pipelineDataHolder) {
	this.setMailType(mailType);
	this.setCommune(commune);
	this.setProcess((AbstractPplProcess) pipelineDataHolder.getPlineData()
		.getAttribute(PipelineDataImpl.EDITABLEPROCESS_PARAMNAME));
    }

    /**
     * @param mailType
     * @param commune
     * @param process
     */
    public SendPplAdminMail(PplMailType mailType, City commune,
	    AbstractPplProcess process) {
	this.setMailType(mailType);
	this.setCommune(commune);
	this.setProcess(process);
    }

    /**
     * @return the mailType
     */
    private PplMailType getMailType() {
	return this.mailType;
    }

    /**
     * @param mailType
     *            the mailType to set
     */
    private void setMailType(PplMailType mailType) {
	this.mailType = mailType;
    }

    /**
     * @return the commune
     */
    private City getCommune() {
	return this.commune;
    }

    /**
     * @param commune
     *            the commune to set
     */
    private void setCommune(City commune) {
	this.commune = commune;
    }

    /**
     * @return the process
     */
    private AbstractPplProcess getProcess() {
	return this.process;
    }

    /**
     * @param process
     *            the process to set
     */
    private void setProcess(AbstractPplProcess process) {
	this.process = process;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.people.vsl.transport.TransportLayer#pipeline2transportData(it.people
     * .vsl.PipelineData)
     */
    @Override
    public HashMap pipeline2transportData(PipelineData data) {
	// Not used by this transport
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.people.vsl.transport.TransportLayer#transportData2pipeline(it.people
     * .vsl.PipelineData, java.util.HashMap)
     */
    @Override
    public void transportData2pipeline(PipelineData data, HashMap params) {
	// Not used by this transport
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.vsl.transport.TransportLayer#send(java.util.HashMap,
     * java.util.HashMap)
     */
    @Override
    public void send(HashMap inParameter, HashMap outParameters)
	    throws SendException {

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
	it.people.logger.Logger pplLogger = null;

	// Based on mail type sarch for administrator(s) e-mail address(es)
	Vector<InternetAddress> addressesValues = null;
	if (!this.getMailType().isForAdmins() && inParameter != null
		&& !inParameter.isEmpty()
		&& inParameter.get(RECIPIENTS_E_MAIL_ADDRESSES) != null) {
	    addressesValues = getRecipientAddresses(inParameter
		    .get(RECIPIENTS_E_MAIL_ADDRESSES));
	} else {
	    addressesValues = getRecipientAddresses(this.getMailType());
	}
	if (addressesValues != null && !addressesValues.isEmpty()) {

	    VelocityUtils velocityUtils = new VelocityUtils(
		    prepareModelObject(), prepareRawModelObjects(inParameter),
		    this.getProcess().getCommune().getKey(), this.getProcess()
			    .getProcessName());

	    String subject = velocityUtils.mergeTemplate(this.getMailType(),
		    VelocityUtils.MailPart.subject);
	    String body = velocityUtils.mergeTemplate(this.getMailType(),
		    VelocityUtils.MailPart.body);

	    if (this.getMailType().equals(PplMailType.sendError)
		    || this.getMailType().equals(PplMailType.generalError)) {
		if (inParameter
			.get(TransportLayer.DELIVERY_STATUS_ERROR_DESCRIPTION) != null) {
		    body += "\n\n\n"
			    + String.valueOf(inParameter
				    .get(TransportLayer.DELIVERY_STATUS_ERROR_DESCRIPTION));
		}
	    }

	    try {
		peopleProtocolId = (String) this.getProcess()
			.getIdentificatoreProcedimento();
		errorMessageHeader = "Errore nell'invio della pratica con identificativo '"
			+ peopleProtocolId + "'";
	    } catch (Exception ex) {
		logger.error(
			"Impossibile determinare l'identificativo della pratica.",
			ex);
		return;
	    }

	    try {
		pplLogger = it.people.logger.Logger.getLogger(realServiceName,
			commune);
	    } catch (Exception ex) {
		logger.warn(errorMessageHeader
			+ ", impossibile istanziare il logger.", ex);
	    }

	    try {
		if (commune != null) {
		    // la lettura dei parametri di configurazione avviene in
		    // base
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

		    if ((String) PeopleProperties.SMTP_MAIL_SERVICEPORT
			    .getValue() != null)
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
			pplLogger,
			ex,
			errorMessageHeader
				+ ", impossibile determinare i parametri di configurazione della posta.");
		return;
	    }

	    try {

		// Start Modifica 2006-04-27 per connessione a server SMTP via
		// SSL
		Session session = MailSenderHelper.getMailSession(hostAddress,
			port, useAuth, senderUserName, senderUserPassword,
			useSSL, useTLS);
		// End Modifica 2006-04-27 per connessione a server SMTP via SSL

		// Define message
		MimeMessage message = new MimeMessage(session);

		if (PeopleProperties.SMTP_MAIL_SENDER.getValue() != null) {
		    message.setFrom(new InternetAddress(
			    (String) PeopleProperties.SMTP_MAIL_SENDER
				    .getValue()));
		}

		for (InternetAddress address : addressesValues) {
		    message.addRecipient(Message.RecipientType.TO, address);
		    logger.debug("Sending to " + address);
		}

		message.setSubject(subject);

		// List attachments = (List)
		// inParameter.get(TransportLayer.ATTACHMENT_PARAM);
		// List signedAttachments = (List)
		// inParameter.get(TransportLayer.SIGNED_ATTACHMENT_PARAM);

		// String recepit = (String)
		// inParameter.get(TransportLayer.ATTACHMENT_DATA);
		String htmlContent = body;

		// Crea il contenitore di tutti gli allegati
		// anche il messaggio originale � un allegato
		// in particolare � il primo allegato
		Multipart multipart = new MimeMultipart();
		message.setContent(multipart);

		addContent(htmlContent, multipart);
		// addReceipt(recepit, multipart);
		//
		// boolean onlySummary = !((Boolean)
		// inParameter.get(TransportLayer.SEND_RECEIPT_WITH_MAIL_ATTACHMENT)).booleanValue();
		// addAttachment(attachments, multipart, onlySummary);
		// addSignedAttachment(signedAttachments, multipart,
		// onlySummary);

		// Send message
		message.saveChanges();

		// --------------------
		// Start Modifica 2006-04-27 per connessione a server SMTP via
		// SSL
		Transport tr = session.getTransport();
		tr.connect(hostAddress, senderUserName, senderUserPassword);
		tr.sendMessage(message, message.getAllRecipients());
		tr.close();
		// End Modifica 2006-04-27 per connessione a server SMTP via SSL
		// --------------------

	    } catch (AddressException adex) {
		logError(pplLogger, adex, errorMessageHeader
			+ ", formato dell'indirizzo non valido.");
	    } catch (Exception ex) {
		logError(pplLogger, ex, errorMessageHeader + ".");
	    }

	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.vsl.transport.TransportLayer#chekStatus(java.util.HashMap,
     * java.util.HashMap)
     */
    @Override
    public void chekStatus(HashMap inParameter, HashMap outParameters)
	    throws SendException {
	// TODO Auto-generated method stub

    }

    private void logError(it.people.logger.Logger pplLogger, Exception ex,
	    String errorMessage) {
	logger.error(errorMessage, ex);
	if (pplLogger != null)
	    pplLogger.error(errorMessage + "\n" + ex.toString());
    }

    private Map<String, VelocityModelObject> prepareModelObject() {

	Map<String, VelocityModelObject> result = new HashMap<String, VelocityModelObject>();

	result.put("process", new VelocityModelObject("process", this
		.getProcess().getClass(), this.getProcess()));
	result.put("commune", new VelocityModelObject("commune", this
		.getCommune().getClass(), this.getCommune()));

	AbstractData pplData = (AbstractData) this.getProcess().getData();
	if (pplData != null) {
	    if (pplData.getAccreditamentoCorrente() != null) {
		result.put("accreditamentoCorrente", new VelocityModelObject(
			"accreditamentoCorrente", pplData
				.getAccreditamentoCorrente().getClass(),
			pplData.getAccreditamentoCorrente()));
	    }
	    if (pplData.getProfiloOperatore() != null) {
		result.put("profiloOperatore", new VelocityModelObject(
			"profiloOperatore", pplData.getProfiloOperatore()
				.getClass(), pplData.getProfiloOperatore()));
	    }
	    if (pplData.getProfiloRichiedente() != null) {
		result.put("profiloRichiedente", new VelocityModelObject(
			"profiloRichiedente", pplData.getProfiloRichiedente()
				.getClass(), pplData.getProfiloRichiedente()));
	    }
	    if (pplData.getProfiloTitolare() != null) {
		result.put("profiloTitolare", new VelocityModelObject(
			"profiloTitolare", pplData.getProfiloTitolare()
				.getClass(), pplData.getProfiloTitolare()));
	    }
	    if (pplData.getRichiedente() != null) {
		result.put("richiedente", new VelocityModelObject(
			"richiedente", pplData.getRichiedente().getClass(),
			pplData.getRichiedente()));
	    }
	    if (pplData.getTitolare() != null) {
		result.put(
			"titolare",
			new VelocityModelObject("titolare", pplData
				.getTitolare().getClass(), pplData
				.getTitolare()));
	    }
	}

	return result;

    }

    private Map<String, Object> prepareRawModelObjects(HashMap inParameter) {

	Map<String, Object> result = new HashMap<String, Object>();
	result.put("rawProcess", this.getProcess());
	result.put("rawCommune", this.getCommune());

	AbstractData pplData = (AbstractData) this.getProcess().getData();
	if (pplData != null) {
	    if (pplData.getAccreditamentoCorrente() != null) {
		result.put("rawAccreditamentoCorrente",
			pplData.getAccreditamentoCorrente());
	    }
	    if (pplData.getProfiloOperatore() != null) {
		result.put("rawProfiloOperatore", pplData.getProfiloOperatore());
	    }
	    if (pplData.getProfiloRichiedente() != null) {
		result.put("rawProfiloRichiedente",
			pplData.getProfiloRichiedente());
	    }
	    if (pplData.getProfiloTitolare() != null) {
		result.put("rawProfiloTitolare", pplData.getProfiloTitolare());
	    }
	    if (pplData.getRichiedente() != null) {
		result.put("rawRichiedente", pplData.getRichiedente());
	    }
	    if (pplData.getTitolare() != null) {
		result.put("rawTitolare", pplData.getTitolare());
	    }
	}

	if (inParameter != null && !inParameter.isEmpty()) {
	    Iterator keysIterator = inParameter.keySet().iterator();
	    while (keysIterator.hasNext()) {
		String key = String.valueOf(keysIterator.next());
		Object value = inParameter.get(key);
		result.put(key, value);
	    }
	}

	return result;

    }

    private void addContent(String htmlContent, Multipart multipart)
	    throws MessagingException {
	MimeBodyPart messageBodyPart = new MimeBodyPart();
	messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(
		htmlContent, "text/html")));
	multipart.addBodyPart(messageBodyPart);
    }

    private Vector<InternetAddress> getRecipientAddresses(PplMailType mailType) {

	Vector<InternetAddress> result = null;

	if (mailType.isForAdmins()) {
	    Vector<InternetAddress> defaultRecipientAddresses = getDefaultAdminRecipientAddresses(mailType);
	    Vector<InternetAddress> pplAdminRecipientAddresses = getPplAdminAddresses(
		    this.getCommune(), mailType);

	    if (defaultRecipientAddresses != null) {
		result = defaultRecipientAddresses;
		if (pplAdminRecipientAddresses != null) {
		    Iterator<InternetAddress> pplAdminRecipientAddressesIterator = pplAdminRecipientAddresses
			    .iterator();
		    while (pplAdminRecipientAddressesIterator.hasNext()) {
			result.add(pplAdminRecipientAddressesIterator.next());
		    }
		}
	    } else {
		if (pplAdminRecipientAddresses != null) {
		    result = pplAdminRecipientAddresses;
		}
	    }
	} else {
	    result = getUserEmailAddress(this.getProcess());
	}

	return result;

    }

    @SuppressWarnings("rawtypes")
    private Vector<InternetAddress> getRecipientAddresses(
	    Object recipientsEMailAddresses) {

	Vector<InternetAddress> result = null;

	if (recipientsEMailAddresses instanceof Vector) {
	    result = new Vector<InternetAddress>();
	    for (Object address : (Vector) recipientsEMailAddresses) {
		StringBuilder stringBuilder = new StringBuilder().append(String
			.valueOf(address));
		try {
		    result.add(new InternetAddress(stringBuilder.toString()));
		} catch (AddressException e) {
		    logger.warn(
			    "Invali address for recipient "
				    + stringBuilder.toString(), e);
		}
	    }
	}

	return result;

    }

    private Vector<InternetAddress> getUserEmailAddress(
	    AbstractPplProcess process) {

	Vector<InternetAddress> result = null;

	AbstractData data = (AbstractData) process.getData();
	if (data != null
		&& data.getProfiloRichiedente() != null
		&& data.getProfiloRichiedente().getDomicilioElettronico() != null) {
	    String address = data.getProfiloRichiedente()
		    .getDomicilioElettronico();
	    try {
		InternetAddress internetAddress = new InternetAddress(address,
			true);
		if (result == null) {
		    result = new Vector<InternetAddress>();
		}
		if (!result.contains(address)) {
		    result.add(internetAddress);
		}
	    } catch (AddressException e) {
		logger.error("Invalid user e-mail address.", e);
	    }
	}

	return result;

    }

    private Vector<InternetAddress> getDefaultAdminRecipientAddresses(
	    PplMailType mailType) {

	Vector<InternetAddress> result = null;

	List pplPropertiesAddresses = null;
	switch (mailType) {
	case generalError:
	    pplPropertiesAddresses = PeopleProperties.PEOPLE_GENERALERROR_NOTIFYPPLADMIN_EMAILADDRESS_VALUE
		    .getValues();
	    break;
	case sendError:
	    pplPropertiesAddresses = PeopleProperties.PEOPLE_SENDERROR_NOTIFYPPLADMIN_EMAILADDRESS_VALUE
		    .getValues();
	    break;
	case notSendProcessDumpError:
	    pplPropertiesAddresses = PeopleProperties.PEOPLE_SENDERROR_NOTIFYPPLADMIN_EMAILADDRESS_VALUE
		    .getValues();
	    break;
	case userSignalledBug:
	    pplPropertiesAddresses = PeopleProperties.PEOPLE_BUGNOTIFIEDBYUSER_NOTIFYPPLADMIN_EMAILADDRESS_VALUE
		    .getValues();
	    break;
	case userSuggestion:
	    pplPropertiesAddresses = PeopleProperties.PEOPLE_SUGGESTIONBYUSER_NOTIFYPPLADMIN_EMAILADDRESS_VALUE
		    .getValues();
	    break;
	case newAccreditation:
	    pplPropertiesAddresses = PeopleProperties.PEOPLE_NEWACCREDITATION_NOTIFYPPLADMIN_EMAILADDRESS_VALUE
		    .getValues();
	    break;
	default:
	    break;
	}

	if (pplPropertiesAddresses != null) {

	    Iterator<Map.Entry> sendErrorAddressesIterator = pplPropertiesAddresses
		    .iterator();
	    while (sendErrorAddressesIterator.hasNext()) {
		Map.Entry addressMap = sendErrorAddressesIterator.next();
		String address = String.valueOf(addressMap.getValue());
		if (!StringUtils.isEmpty(address)) {
		    if (result == null) {
			result = new Vector<InternetAddress>();
		    }
		    if (!result.contains(address)) {
			try {
			    InternetAddress internetAddress = new InternetAddress(
				    address, true);
			    if (!result.contains(address)) {
				result.add(internetAddress);
			    }
			} catch (AddressException e) {
			    logger.error("Invalid admin e-mail address.", e);
			}
		    }
		}
	    }

	}

	return result;

    }

    private Vector<InternetAddress> getPplAdminAddresses(City commune,
	    PplMailType mailType) {

	Vector<InternetAddress> result = null;
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;
	Properties queries = new Properties();
	int receiverTypeFlagPosition = -1;

	switch (mailType) {
	case generalError:
	    receiverTypeFlagPosition = 1;
	    break;
	case sendError:
	    receiverTypeFlagPosition = 2;
	    break;
	case notSendProcessDumpError:
	    receiverTypeFlagPosition = 2;
	    break;
	case userSignalledBug:
	    receiverTypeFlagPosition = 3;
	    break;
	case userSuggestion:
	    receiverTypeFlagPosition = 4;
	    break;
	case newAccreditation:
	    receiverTypeFlagPosition = 5;
	    break;
	default:
	    break;
	}

	try {
	    queries.loadFromXML(SendPplAdminMail.class
		    .getResourceAsStream("/it/people/resources/Queries.xml"));
	    connection = DBConnector.getInstance()
		    .connect(DBConnector.LEGACYDB);
	    String query = queries.getProperty("pplAdminsEMailAddresses.query");
	    statement = connection.createStatement();
	    resultSet = statement.executeQuery(query.replace(
		    "?",
		    new StringBuilder().append("'").append(commune.getKey())
			    .append("'")).replace(
		    "#",
		    new StringBuilder().append("'")
			    .append(String.valueOf(receiverTypeFlagPosition))
			    .append("'")));
	    while (resultSet.next()) {
		if (result == null) {
		    result = new Vector<InternetAddress>();
		}
		String address = resultSet.getString(1);
		try {
		    InternetAddress internetAddress = new InternetAddress(
			    address, true);
		    if (!result.contains(address)) {
			result.add(internetAddress);
		    }
		} catch (AddressException e) {
		    logger.error("Invalid admin e-mail address.", e);
		}
	    }
	} catch (InvalidPropertiesFormatException e) {
	    logger.error("Unable to read Query XML file: \n" + e);
	} catch (IOException e) {
	    logger.error("Unable to read Query XML file: \n" + e);
	} catch (SQLException e) {
	    logger.error("Unable to get ppl admin e-mail addresses: \n" + e);
	} catch (PeopleDBException e) {
	    logger.error("Unable to get ppl admin e-mail addresses: \n" + e);
	} finally {
	    try {
		if (resultSet != null) {
		    resultSet.close();
		}
		if (statement != null) {
		    statement.close();
		}
		if (connection != null) {
		    connection.close();
		}
	    } catch (SQLException ignore) {

	    }
	}

	return result;

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
