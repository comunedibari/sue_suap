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
package it.people.vsl;

import it.people.City;
import it.people.process.AbstractPplProcess;
import it.people.process.SubmittedProcessState;
import it.people.process.data.AbstractData;
import it.people.util.ActivityLogger;
import it.people.util.DataHolderStatus;
import it.people.util.MessageBundleHelper;
import it.people.util.PeopleProperties;
import it.people.util.ProcessUtils;
import it.people.util.VelocityUtils;
import it.people.util.dto.VelocityModelObject;
import it.people.vsl.exception.SendException;
import it.people.vsl.exception.UnsupportedTransportLayerException;
import it.people.vsl.transport.PplMailType;
import it.people.vsl.transport.SendMail;
import it.people.vsl.transport.SendPplAdminMail;
import it.people.vsl.transport.TransportLayer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Category;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Sep 16, 2003 Time: 7:09:40 PM To
 * change this template use Options | File Templates. <br>
 * <br>
 * Questo hadler invia email usando per ogni comune per ogni processo il
 * trasporto appropriato
 */
public class ProcessSenderHandler extends PipelineHandlerImpl {

    private static Logger logger = LogManager
	    .getLogger(ProcessSenderHandler.class);
    private int m_maxPerRun = 5;
    private static final int DEAFULT_MAX_PER_RUN = 5;

    public String getName() {
	return "Invio Documenti";
    }

    public ProcessSenderHandler(int p_maxPerRun) {
	setMaxPerRun(p_maxPerRun);
    }

    /**
     * Il metodo scorre tutti gli holder passati in ingersso ed elabora prima
     * tutti quelli che sono gia' in stato working (gia' elaborati almeo una
     * volta) poi quelli in stato assegnato (prima volta che vengono elaborati).
     * 
     * @param holders
     *            Collezione degli holder da elaborare
     */
    public void process(Collection holders) {
	setFree(false);
	try {
	    boolean executeOne = false;
	    Iterator myIterator = holders.iterator();
	    // primo giro: elabora eventuali data holder che sono gia' in stato
	    // di working
	    while (myIterator.hasNext()) {
		PipelineDataHolder currentItem = (PipelineDataHolder) myIterator
			.next();
		if (!currentItem.isInvalid()
			&& DataHolderStatus.WORKING.equals(currentItem
				.getStatus())) {
		    process(currentItem);
		    executeOne = true;
		}
	    }

	    myIterator = holders.iterator();
	    while (!executeOne && myIterator.hasNext()) {
		PipelineDataHolder currentItem = (PipelineDataHolder) myIterator
			.next();
		if (!currentItem.isInvalid()
			&& DataHolderStatus.ASSIGNED.equals(currentItem
				.getStatus())) {
		    currentItem.getPlineData().setAttribute("mailStatus",
			    new Integer(0));
		    process(currentItem);
		    executeOne = true;
		}
	    }
	} catch (Exception ex) {
	    logger.error(ex);
	}
	setFree(true);
    }

    /**
     * 
     * @param itemToProcess
     *            Singolo holder da elaborare
     */
    private void process(PipelineDataHolder itemToProcess) {
	itemToProcess.setStatusWorking();

	StringBuilder temporaryDumpFile = null;

	Boolean isResendObject = (Boolean) itemToProcess.getPlineData()
		.getAttribute(PipelineDataImpl.IS_RESEND);
	boolean isResend = (isResendObject == null) ? false : isResendObject
		.booleanValue();
	if (logger.isDebugEnabled() && isResend) {
	    logger.debug("Processing resend pipeline data.");
	}

	if (!isResend) {
	    // Save xml dump in a temporary folder end file name
	    temporaryDumpFile = new StringBuilder(
		    StringUtils.defaultString(ProcessUtils
			    .dumpProcessDataXml(itemToProcess)));
	    if (temporaryDumpFile.length() == 0) {
		logger.warn("Unable to execute dump of XML data. See previous error(s).");
	    }

	}

	City commune = (City) itemToProcess.getPlineData().getAttribute(
		PipelineDataImpl.COMMUNE_PARAMNAME);

	PipelineData data = itemToProcess.getPlineData();

	TransportLayer specializedSender = null;

	HashMap params = new HashMap();
	HashMap outParams = new HashMap();

	try {

	    if (!isResend) {
		PendingProcessHelper.updateOffLineSignData(itemToProcess);
	    }

	    specializedSender = TransportLayerFactory.getInstance()
		    .getTransportLayer(commune, getPipeline().getProcessName());

	    params = specializedSender.pipeline2transportData(data);
	    setMailSenderBackend(params, data);

	    // Invio Mail al funzionario
	    setMailSubject(params, data, "mail.funzionario.subject");
	    params.put(TransportLayer.REPLAYTO_PARAM, (String) data
		    .getAttribute(PipelineDataImpl.USER_MAILADDRESS_PARAMNAME));
	    // nel e-mail inviato al funzionario sono sempre inclusi gli
	    // attachment
	    params.put(TransportLayer.SEND_RECEIPT_WITH_MAIL_ATTACHMENT,
		    Boolean.TRUE);

	    specializedSender.send(params, outParams);
	    params.remove(TransportLayer.REPLAYTO_PARAM);

	    specializedSender.transportData2pipeline(data, outParams);

	    // String trackingNumber = (String)
	    // outParams.get(PipelineDataImpl.TRANSPORT_TRACKINGNUMBER_PARAMNAME);
	    CreateSubmittedProcessHelper.setTrackingNumber(itemToProcess);
	    CreateSubmittedProcessHelper.addProcessHistory(itemToProcess,
		    SubmittedProcessState.SUBMITTED);
	    if (!isResend) {
		CreateSubmittedProcessHelper
			.addAttachmentsStatisticData(itemToProcess);
	    }
	    if (isResend) {
		logger.debug("Clearing submitted process dump data.");
		PipelineDataDumpManager
			.clearPipelineDataDumpBySubmittedProcessOid(new Long(
				String.valueOf(data
					.getAttribute(PipelineDataImpl.PROCESS_OID))));
		logger.debug("Clearing submitted process send errore flag.");
		UpdateSubmittedProcessHelper.setSendError(itemToProcess,
			new Boolean(false));
	    }

	    // Eng-28072011->
	    // Verifica se la mail debba essere inviata al cittadino
	    // Per evitare problemi l'impostazione di default � quella
	    // normale,
	    // quindi viene spedita la mail.
	    boolean sendMailToOwner = true;
	    if (data.getAttribute(PipelineDataImpl.SEND_RECEIPT_MAIL) != null) {
		sendMailToOwner = (Boolean) data
			.getAttribute(PipelineDataImpl.SEND_RECEIPT_MAIL);
	    }
	    // <--Eng-28072011

	    // Invia la Mail (non cert.) al cittadino che ha compilato il modulo
	    if (itemToProcess.getPlineData().getAttribute(
		    PipelineDataImpl.USER_MAILADDRESS_PARAMNAME) != null
		    && sendMailToOwner) {

		AbstractPplProcess process = (AbstractPplProcess) data
			.getAttribute(PipelineDataImpl.EDITABLEPROCESS_PARAMNAME);

		VelocityUtils velocityUtils = new VelocityUtils(
			prepareModelObject(data), prepareRawModelObjects(data,
				params), process.getCommune().getKey(),
			process.getProcessName());

		String subject = velocityUtils.mergeTemplate(
			PplMailType.sendNotifyUser,
			VelocityUtils.MailPart.subject);
		String body = velocityUtils
			.mergeTemplate(PplMailType.sendNotifyUser,
				VelocityUtils.MailPart.body);

		params.put(TransportLayer.SUBJECT_PARAM, subject);
		params.put(TransportLayer.MESSAGEBODY_PARAM, body);

		// setMailSubject(params, data, "mail.cittadino.subject");
		String userMailAddress = (String) data
			.getAttribute(PipelineDataImpl.USER_MAILADDRESS_PARAMNAME);
		TransportLayer sender = new SendMail(commune, getPipeline()
			.getProcessName(), userMailAddress);

		// Nel e-mail inviato al cittadino possono non essere inclusi
		// gli allegati
		params.put(
			TransportLayer.SEND_RECEIPT_WITH_MAIL_ATTACHMENT,
			(Boolean) data
				.getAttribute(PipelineDataImpl.RECEIPT_MAIL_ATTACHMENT));
		setMailSender(params, itemToProcess);
		sender.send(params, new HashMap());
	    }
	    CreateSubmittedProcessHelper.setSendError(itemToProcess,
		    new Boolean(false));
	    itemToProcess.setStatusCompleted();

	} catch (SendException sEx) {

	    // Errore nella Send
	    ActivityLogger.getInstance().log(itemToProcess,
		    "ERRORE NELLA SEND", ActivityLogger.ERROR);

	    ActivityLogger.getInstance()
		    .log(itemToProcess, "Esecuzione del backup della pratica.",
			    ActivityLogger.INFO);

	    boolean pipelineDataDumped = false;
	    if (!isResend) {
		pipelineDataDumped = dumpPipelineData(specializedSender, data,
			commune);
	    }

	    CreateSubmittedProcessHelper.setSendError(itemToProcess,
		    new Boolean(true));
	    CreateSubmittedProcessHelper
		    .addProcessHistory(
			    itemToProcess,
			    SubmittedProcessState.SEND_ERROR,
			    String.valueOf(outParams
				    .get(TransportLayer.DELIVERY_STATUS_ERROR_DESCRIPTION)));
	    ActivityLogger.getInstance().log(
		    itemToProcess,
		    "Esecuzione del backup della pratica "
			    + (pipelineDataDumped ? "completato."
				    : "non riuscito."), ActivityLogger.INFO);
	    try {
		TransportLayer pplAdminMailSender = new SendPplAdminMail(
			PplMailType.sendError, commune, itemToProcess);
		TransportLayer pplUserNotifyMailSender = new SendPplAdminMail(
			PplMailType.sendErrorNotifyUser, commune, itemToProcess);
		ActivityLogger
			.getInstance()
			.log(itemToProcess,
				"Invio dell'e-mail di notifica errore invio pratica agli amministratori.",
				ActivityLogger.INFO);
		HashMap<String, String> inParams = new HashMap<String, String>();
		inParams.put(
			TransportLayer.DELIVERY_STATUS_ERROR_DESCRIPTION,
			String.valueOf(outParams
				.get(TransportLayer.DELIVERY_STATUS_ERROR_DESCRIPTION)));
		pplAdminMailSender.send(inParams, new HashMap());
		ActivityLogger
			.getInstance()
			.log(itemToProcess,
				"Invio dell'e-mail di notifica errore invio pratica all'utente.",
				ActivityLogger.INFO);
		pplUserNotifyMailSender.send(new HashMap(), new HashMap());
		if (!pipelineDataDumped && !isResend) {
		    pplAdminMailSender = new SendPplAdminMail(
			    PplMailType.notSendProcessDumpError, commune,
			    itemToProcess);
		    ActivityLogger
			    .getInstance()
			    .log(itemToProcess,
				    "Invio dell'e-mail di notifica errore backup pratica agli amministratori.",
				    ActivityLogger.INFO);
		    pplAdminMailSender.send(new HashMap(), new HashMap());
		}
	    } catch (SendException e) {
		ActivityLogger
			.getInstance()
			.log(itemToProcess,
				"ERRORE NELL'INVIO DELLE E-MAIL DI ALERT ERRORE INVIO PRATICA",
				ActivityLogger.ERROR);
		logger.error(e);
	    }

	    logger.error(sEx);
	    itemToProcess.setStatusAborted();
	} catch (UnsupportedTransportLayerException utlEx) {
	    logger.error(utlEx);
	    itemToProcess.setStatusCompleted();
	}

	if (temporaryDumpFile != null && temporaryDumpFile.length() > 0) {
	    if (!ProcessUtils.clearProcessDataXmlDump(temporaryDumpFile
		    .toString())) {
		logger.warn("Unable to delete temporary xml dump file '"
			+ temporaryDumpFile.toString() + "'");
	    } else {
		if (logger.isInfoEnabled()) {
		    logger.info("Temporary xml dump file '"
			    + temporaryDumpFile.toString() + "' deleted.");
		}
	    }
	}

    }

    protected void setMailSubject(HashMap params, PipelineData data,
	    String subjectKey) {
	String communeId = ((City) data
		.getAttribute(PipelineDataImpl.COMMUNE_PARAMNAME)).getKey();
	String peopleProtocolId = (String) data
		.getAttribute(PipelineDataImpl.PEOPLE_PROTOCOLL_ID_PARAMNAME);

	String[] messageParams = new String[] { peopleProtocolId };
	String subject = MessageBundleHelper.message(subjectKey, messageParams,
		"", communeId, null);
	params.put(TransportLayer.SUBJECT_PARAM, subject);
    }

    // imposta l'indirizzo del mittente:
    // se configurato imposta il parametro letto da SMTP_MAIL_SENDER_BACKEND,
    // altrimenti non modifica le impostazioni originali
    private void setMailSenderBackend(HashMap params, PipelineData data) {
	String communeId = ((City) data
		.getAttribute(PipelineDataImpl.COMMUNE_PARAMNAME)).getKey();

	// verifica se � correttamente configurato il sender per
	// l'email di backend per l'installazione People
	String sender = null;
	try {
	    sender = PeopleProperties.SMTP_MAIL_SENDER_BACKEND
		    .getValueString(communeId);
	} catch (Exception ex) {
	}

	if (sender != null)
	    // purtroppo il nome � fuorviante, comunque se impostato il
	    // RECIPIENT
	    // questo ha la precedenza sul SENDER
	    params.put(TransportLayer.RECIPIENT, sender);
    }

    // Imposta l'indirizzo del mittente:
    // se configurato imposta il parametro letto da SMTP_MAIL_SENDER ,
    // diversamente imposta come mittente l'indirizzo del cittadino.
    private void setMailSender(HashMap params, PipelineDataHolder itemToProcess) {
	String communeId = ((City) itemToProcess.getPlineData().getAttribute(
		PipelineDataImpl.COMMUNE_PARAMNAME)).getKey();

	// verifica se � correttamente configurato il sender per
	// l'installazione People
	String sender = null;
	try {
	    sender = PeopleProperties.SMTP_MAIL_SENDER
		    .getValueString(communeId);
	} catch (Exception ex) {
	}
	if (sender == null) {
	    sender = (String) itemToProcess.getPlineData().getAttribute(
		    PipelineDataImpl.USER_MAILADDRESS_PARAMNAME);
	    logger.warn("Problema con l'invio del mail di ricevuta al cittadino: impossibile leggere il parametro SMTP_MAIL_SENDER sar� utilizzato come mittente l'indirizzo del cittadino.");
	}
	// purtroppo il nome � fuorviante, comunque se impostato il RECIPIENT
	// questo ha la precedenza sul SENDER
	params.put(TransportLayer.RECIPIENT, sender);
    }

    private HashMap createStreamAttachments(PipelineData pd) {
	HashMap streamAttachs = new HashMap();
	Object signedDocument = pd
		.getAttribute(PipelineDataImpl.SIGNED_PRINTPAGE_NAME);
	if (signedDocument != null && signedDocument instanceof byte[]) {
	    String attachName = (String) (pd
		    .getAttribute(PipelineDataImpl.PROCESS_TITLE) != null ? pd
		    .getAttribute(PipelineDataImpl.PROCESS_TITLE)
		    : getPipeline().getProcessName().toString());
	    attachName += ".txt.p7m";
	    streamAttachs.put(attachName, signedDocument);
	}
	return streamAttachs;
    }

    public boolean isGuiHandler() {
	return false;
    }

    public int getMaxPerRun() {
	return m_maxPerRun;
    }

    public void setMaxPerRun(int p_maxPerRun) {
	if (p_maxPerRun > 0)
	    m_maxPerRun = p_maxPerRun;
	else
	    m_maxPerRun = DEAFULT_MAX_PER_RUN;
    }

    private boolean dumpPipelineData(TransportLayer specializedSender,
	    PipelineData data, City commune) {

	boolean result = true;

	SerializablePipelineData serializablePipelineData = specializedSender
		.pipelineData2SerializablePipelineData(data);
	if (serializablePipelineData != null) {

	    addHandlerData(data, serializablePipelineData);

	    UnsentProcessPipelineData unsentProcessPipelineData = new UnsentProcessPipelineData();
	    unsentProcessPipelineData.setSubmittedProcessOid(new Long(String
		    .valueOf(data.getAttribute(PipelineDataImpl.PROCESS_OID))));
	    unsentProcessPipelineData.setCommune(commune);
	    unsentProcessPipelineData.setInsertedTime(new Timestamp(Calendar
		    .getInstance().getTimeInMillis()));
	    unsentProcessPipelineData.setPipelineData(serializablePipelineData);

	    PipelineDataDumpManager.dumpPipelineData(specializedSender,
		    unsentProcessPipelineData);

	}

	return result;

    }

    private void addHandlerData(PipelineData data,
	    SerializablePipelineData serializablePipelineData) {

	if (serializablePipelineData
		.getAttribute(PipelineDataImpl.COMMUNE_PARAMNAME) == null) {
	    serializablePipelineData.setAttribute(
		    PipelineDataImpl.COMMUNE_PARAMNAME,
		    data.getAttribute(PipelineDataImpl.COMMUNE_PARAMNAME));
	}

	if (serializablePipelineData
		.getAttribute(PipelineDataImpl.TRANSPORT_TRACKINGNUMBER_PARAMNAME) == null) {
	    serializablePipelineData
		    .setAttribute(
			    PipelineDataImpl.TRANSPORT_TRACKINGNUMBER_PARAMNAME,
			    data.getAttribute(PipelineDataImpl.TRANSPORT_TRACKINGNUMBER_PARAMNAME));
	}

	if (serializablePipelineData
		.getAttribute(PipelineDataImpl.SEND_RECEIPT_MAIL) == null) {
	    serializablePipelineData.setAttribute(
		    PipelineDataImpl.SEND_RECEIPT_MAIL,
		    data.getAttribute(PipelineDataImpl.SEND_RECEIPT_MAIL));
	}

	if (serializablePipelineData
		.getAttribute(PipelineDataImpl.USER_MAILADDRESS_PARAMNAME) == null) {
	    serializablePipelineData
		    .setAttribute(
			    PipelineDataImpl.USER_MAILADDRESS_PARAMNAME,
			    data.getAttribute(PipelineDataImpl.USER_MAILADDRESS_PARAMNAME));
	}

	if (serializablePipelineData
		.getAttribute(PipelineDataImpl.RECEIPT_MAIL_ATTACHMENT) == null) {
	    serializablePipelineData
		    .setAttribute(
			    PipelineDataImpl.RECEIPT_MAIL_ATTACHMENT,
			    data.getAttribute(PipelineDataImpl.RECEIPT_MAIL_ATTACHMENT));
	}

    }

    private Map<String, VelocityModelObject> prepareModelObject(
	    PipelineData data) {

	AbstractPplProcess process = (AbstractPplProcess) data
		.getAttribute(PipelineDataImpl.EDITABLEPROCESS_PARAMNAME);

	Map<String, VelocityModelObject> result = new HashMap<String, VelocityModelObject>();

	result.put("process",
		new VelocityModelObject("process", process.getClass(), process));
	result.put(
		"commune",
		new VelocityModelObject("commune", process.getClass(), process
			.getCommune()));

	AbstractData pplData = (AbstractData) process.getData();
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

    private Map<String, Object> prepareRawModelObjects(PipelineData data,
	    HashMap inParameter) {

	AbstractPplProcess process = (AbstractPplProcess) data
		.getAttribute(PipelineDataImpl.EDITABLEPROCESS_PARAMNAME);

	Map<String, Object> result = new HashMap<String, Object>();
	result.put("rawProcess", process);
	result.put("rawCommune", process.getCommune());

	AbstractData pplData = (AbstractData) process.getData();
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

}
