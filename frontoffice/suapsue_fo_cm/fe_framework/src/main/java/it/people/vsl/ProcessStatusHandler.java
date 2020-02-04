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
import it.people.process.SubmittedProcessState;
import it.people.util.DataHolderStatus;
import it.people.vsl.exception.SendException;
import it.people.vsl.transport.TransportLayer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Category;

/**
 * User: sergio Date: Sep 16, 2003 Time: 7:09:40 PM <br>
 * <br>
 * Questo handler controlla lo stato delle email inviate tramite posta
 * certificata.
 */
public class ProcessStatusHandler extends PipelineHandlerImpl {

    // log
    private Category cat = Category.getInstance(ProcessStatusHandler.class
	    .getName());

    public String getName() {
	return "Verifica stato di consegna dei Documenti";
    }

    /**
     * 
     * @param p_maxPerRun
     */
    public ProcessStatusHandler(int p_maxPerRun) {
	setMaxPerRun(p_maxPerRun);
    }

    /**
     * Il metodo scorre tutti gli holder passati in ingersso ed elabora prima
     * tutti quelli che sono gi? in stato working (gi? elaborati almeo una
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
	    // primo giro: elabora eventuali data holder che sono gi? in stato
	    // di working
	    while (myIterator.hasNext()) {
		PipelineDataHolder currentItem = (PipelineDataHolder) myIterator
			.next();
		if (!currentItem.isInvalid()
			&& DataHolderStatus.WORKING.equals(currentItem
				.getStatus())) {
		    process(currentItem);
		    // executeOne = true;
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
		    // executeOne = true;
		}
	    }
	} catch (Exception ex) {
	    cat.error(ex);
	}
	setFree(true);
    }

    /**
     * 
     * @param p_itemToProcess
     *            Singolo holder da elaborare
     */
    private void process(PipelineDataHolder p_itemToProcess) {
	p_itemToProcess.setStatusWorking();

	City commune = (City) p_itemToProcess.getPlineData().getAttribute(
		PipelineDataImpl.COMMUNE_PARAMNAME);
	try {
	    TransportLayer SpecializedSender = TransportLayerFactory
		    .getInstance().getTransportLayer(commune,
			    getPipeline().getProcessName());

	    HashMap params = new HashMap();
	    HashMap outParams = new HashMap();
	    params = SpecializedSender.pipeline2transportData(p_itemToProcess
		    .getPlineData());

	    // processo la mail
	    String deliveryStatus = "0";
	    try {
		SpecializedSender.chekStatus(params, outParams);
		deliveryStatus = (String) outParams
			.get(TransportLayer.DELIVERY_ACCEPTANCE_STATUS);
	    } catch (SendException sEx) {
		cat.error(sEx);
		p_itemToProcess.setStatusAborted();
	    }

	    if (deliveryStatus.equals("1") || deliveryStatus.equals("2")) {
		/**
		 * Inviamo notifica all'utente
		 */
		outParams.put(
			TransportLayer.USER_MAILADDRESS,
			p_itemToProcess.getPlineData().getAttribute(
				PipelineDataImpl.USER_MAILADDRESS_PARAMNAME));
		if (sendRecepit(outParams)) {

		    SubmittedProcessState status = SubmittedProcessState.INITIALIZING;
		    if (deliveryStatus.equals("1"))
			status = SubmittedProcessState.RECEIVED;
		    else if (deliveryStatus.equals("2"))
			status = SubmittedProcessState.REJECTED;

		    CreateSubmittedProcessHelper.addProcessHistory(
			    p_itemToProcess, status);
		    p_itemToProcess.setStatusCompleted();
		} else
		    p_itemToProcess.setStatusAborted();

	    } else if (deliveryStatus.equals("3")) {
		/**
		 * Stato tre viene restituito dalla send con poste NON
		 * certificata, quindi non avr? mai notizie sullo stato
		 * dell'invio
		 */
		p_itemToProcess.setStatusCompleted();
	    }
	} catch (Exception Ex) {
	    cat.error(Ex);
	    p_itemToProcess.setStatusAborted();
	}
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
	    m_maxPerRun = m_DEAFULT_MAX_PER_RUN;
    }

    /**
     * Invia tramite posta normale una email contenete la ricevuta firmata di
     * accettazione o rifiuto.
     * 
     * @param outParams
     *            Parametri conteneti l'esito della mail.
     */
    private boolean sendRecepit(HashMap outParams) {

	return true;
	/*
	 * String recepit = (String) outParams.get(TransportLayer.RECEPIT);
	 * String destination = (String)
	 * outParams.get(TransportLayer.USER_MAILADDRESS);
	 * 
	 * TransportLayer SpecializedSender = new SendMail(destination);
	 * 
	 * HashMap inmap = new HashMap(); HashMap outmap = new HashMap();
	 * inmap.put(TransportLayer.RECIPIENT,
	 * outParams.get(TransportLayer.RECIPIENT));
	 * inmap.put(TransportLayer.MESSAGEBODY_PARAM,
	 * "Invio della ricevuta firmata di accettazione o rifiuto");
	 * inmap.put(TransportLayer.SUBJECT_PARAM, "Invio ricevuta");
	 * inmap.put(TransportLayer.ATTACHMENT_DATA, recepit);
	 * 
	 * try { //Invio la ricevuta firmata al cittadino.
	 * SpecializedSender.send(inmap, outmap); return true; } catch
	 * (Exception e) { cat.error(e); } return false;
	 */
    }

    private int m_maxPerRun = 5;
    private static final int m_DEAFULT_MAX_PER_RUN = 5;
}
