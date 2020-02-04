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
 * Created on 26-mag-04
 * Author Bernabei
 */
package it.people.vsl.transport;

import it.people.City;
import it.people.backend.client.WebServiceConnector;
import it.people.core.PplUser;
import it.people.core.SignedDataManager;
import it.people.core.exception.ServiceException;
import it.people.core.persistence.exception.peopleException;
import it.people.process.AbstractPplProcess;
import it.people.process.common.entity.Attachment;
import it.people.process.sign.entity.SignedData;
import it.people.util.ActivityLogger;
import it.people.util.CastUtils;
import it.people.util.VelocityUtils;
import it.people.vsl.PipelineData;
import it.people.vsl.PipelineDataImpl;
import it.people.vsl.SerializablePipelineData;
import it.people.vsl.SerializablePipelineDataImpl;
import it.people.vsl.exception.SendException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Category;

/**
 * @author Bernabei
 * 
 *         Classe che invoca un Web Service all'interno del VSL
 */
public class WebServiceCall extends TransportLayer {

    private Category cat = Category.getInstance(WebServiceCall.class.getName());

    /**
     * Nome del Servizio invocato tramite Web Service
     */
    private String serviceName = null;

    /**
     * @param serviceName
     *            il nome del servizio che deve essere invocato
     */
    public WebServiceCall(String serviceName) throws SendException {
	this.serviceName = serviceName;
    }

    public HashMap pipeline2transportData(PipelineData data) {

	String subject = "Pratica People: "
		+ data.getAttribute(PipelineDataImpl.PEOPLE_PROTOCOLL_ID_PARAMNAME);

	HashMap map = new HashMap();

	map.put("pratica_html", createMessageBody(data));

	map.put(TransportLayer.XML_DATA, (String) data
		.getAttribute(PipelineDataImpl.XML_PROCESSDATA_PARAMNAME));
	map.put(TransportLayer.SENDER_USER_NAME,
		((PplUser) data.getAttribute(PipelineDataImpl.USER_PARAMNAME)));
	map.put(TransportLayer.CITY,
		((City) data.getAttribute(PipelineDataImpl.COMMUNE_PARAMNAME)));
	map.put(TransportLayer.SUBJECT_PARAM, subject);
	map.put(TransportLayer.ATTACHMENT_PARAM,
		(List) data.getAttribute(PipelineDataImpl.ATTACHMENTS));
	map.put(TransportLayer.SIGNED_ATTACHMENT_PARAM, (List) data
		.getAttribute(PipelineDataImpl.SIGNED_ATTACHMENT_NAME));
	map.put(TransportLayer.MESSAGEBODY_PARAM, createMessageBody(data));
	map.put(TransportLayer.PROCESS_NAME, realServiceName);
	map.put(TransportLayer.PROCESS_ID, (Long) data
		.getAttribute(PipelineDataImpl.EDITABLEPROCESS_ID_PARAMNAME));
	map.put(TransportLayer.PEOPLE_PROTOCOLL_ID_PARAMNAME, (String) data
		.getAttribute(PipelineDataImpl.PEOPLE_PROTOCOLL_ID_PARAMNAME));
	return map;
    }

    /**
     * verifica la presenza di documenti firmati e prepara una Collection di
     * Attachments per inviarli in allegato al Web Service
     * 
     * @param processId
     * @return
     * @throws SendException
     */
    private List leggiDocumentiFirmati(Long processId) throws SendException {
	List fileFirmati = null;
	SignedDataManager sdm = SignedDataManager.getInstance();
	try {
	    fileFirmati = sdm.get(processId);
	} catch (peopleException e) {
	    cat.error(e);
	    throw new SendException();
	}
	return fileFirmati;
    }

    private List converti(List signedDoc) {
	List attachments = new ArrayList();
	if (signedDoc != null) {
	    Iterator iter = signedDoc.iterator();
	    while (iter.hasNext()) {
		SignedData docFirmato = (SignedData) iter.next();
		Attachment allegato = new Attachment();
		allegato.setDescrizione(docFirmato.getFriendlyName());
		allegato.setName(docFirmato.getFriendlyName());
		allegato.setPath(docFirmato.getPath());
		attachments.add(allegato);
	    }
	}
	return attachments;
    }

    /**
     * Invoca il web service
     */
    public void send(HashMap inParameter, HashMap outParameters)
	    throws SendException {
	WebServiceConnector connector = new WebServiceConnector();
	PplUser user = (PplUser) inParameter
		.get(TransportLayer.SENDER_USER_NAME);
	String xmlIn = (String) inParameter.get(TransportLayer.XML_DATA);
	City comune = (City) inParameter.get(TransportLayer.CITY);
	String processName = (String) inParameter
		.get(TransportLayer.PROCESS_NAME);
	Long processId = (Long) inParameter.get(TransportLayer.PROCESS_ID);
	String peopleIdString = (String) inParameter
		.get(TransportLayer.PEOPLE_PROTOCOLL_ID_PARAMNAME);
	Long peopleId = new Long(0);

	List attachments = (List) inParameter
		.get(TransportLayer.ATTACHMENT_PARAM);

	if (serviceName != null) {
	    // WEB SERVICE CALL
	    try {
		ActivityLogger.getInstance().log(comune.getOid(),
			user.getUserID(), processName, processId,
			"PRIMA DI INVOCARE IL WEB SERVICE",
			ActivityLogger.DEBUG);

		String address = ""; // l'address � determinato direttamente
				     // dal WebServiceConnector
		String risultato = connector.callWebService(attachments,
			address, serviceName, comune.getOid(),
			user.getUserID(), processName, peopleId, xmlIn);

		ActivityLogger.getInstance().log(comune.getOid(),
			user.getUserID(), processName, peopleIdString,
			"WEB SERVICE INVOCATO CON SUCCESSO",
			ActivityLogger.DEBUG);

		outParameters.put(TransportLayer.DELIVERY_RESULT, risultato);

	    } catch (ServiceException e) {
		ActivityLogger.getInstance().log(comune.getOid(),
			user.getUserID(), processName, processId,
			"ERRORE DURANTE INVOCAZIONE WEB SERVICE",
			ActivityLogger.ERROR);

		outParameters.put(
			TransportLayer.DELIVERY_STATUS_ERROR_DESCRIPTION,
			CastUtils.exceptionToString(e));

		throw new SendException();
	    }
	}
    }

    /**
     * 
     * TODO IMPLEMENTARE I CONTROLLI DELLA RISPOSTA AL WEB SERVICE !!!!!!!!!
     */
    public void chekStatus(HashMap inParameter, HashMap outParameters)
	    throws SendException {
	outParameters.put(TransportLayer.DELIVERY_ACCEPTANCE_STATUS, "3");
    }

    public void transportData2pipeline(PipelineData data, HashMap params) {
	// per le chiamate asincrone non viene riportato nessun risultato...
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

	SerializablePipelineData serializablePipelineData = new SerializablePipelineDataImpl();

	serializablePipelineData
		.setAttribute(
			PipelineDataImpl.PEOPLE_PROTOCOLL_ID_PARAMNAME,
			data.getAttribute(PipelineDataImpl.PEOPLE_PROTOCOLL_ID_PARAMNAME));

	serializablePipelineData.setAttribute(
		PipelineDataImpl.XML_PROCESSDATA_PARAMNAME,
		data.getAttribute(PipelineDataImpl.XML_PROCESSDATA_PARAMNAME));
	serializablePipelineData.setAttribute(PipelineDataImpl.USER_PARAMNAME,
		data.getAttribute(PipelineDataImpl.USER_PARAMNAME));
	serializablePipelineData.setAttribute(
		PipelineDataImpl.COMMUNE_PARAMNAME,
		data.getAttribute(PipelineDataImpl.COMMUNE_PARAMNAME));
	serializablePipelineData.setAttribute(PipelineDataImpl.ATTACHMENTS,
		data.getAttribute(PipelineDataImpl.ATTACHMENTS));
	serializablePipelineData.setAttribute(
		PipelineDataImpl.SIGNED_ATTACHMENT_NAME,
		data.getAttribute(PipelineDataImpl.SIGNED_ATTACHMENT_NAME));
	serializablePipelineData
		.setAttribute(
			PipelineDataImpl.EDITABLEPROCESS_ID_PARAMNAME,
			data.getAttribute(PipelineDataImpl.EDITABLEPROCESS_ID_PARAMNAME));
	serializablePipelineData
		.setAttribute(
			PipelineDataImpl.PEOPLE_PROTOCOLL_ID_PARAMNAME,
			data.getAttribute(PipelineDataImpl.PEOPLE_PROTOCOLL_ID_PARAMNAME));
	serializablePipelineData.setAttribute(
		PipelineDataImpl.SIGNED_PRINTPAGE_NAME,
		data.getAttribute(PipelineDataImpl.SIGNED_PRINTPAGE_NAME));
	serializablePipelineData.setAttribute(
		PipelineDataImpl.PRINTPAGE_PARAMNAME,
		data.getAttribute(PipelineDataImpl.PRINTPAGE_PARAMNAME));
	serializablePipelineData.setAttribute(PipelineDataImpl.PROCESS_OID,
		data.getAttribute(PipelineDataImpl.PROCESS_OID));

	return serializablePipelineData;

    }
}
