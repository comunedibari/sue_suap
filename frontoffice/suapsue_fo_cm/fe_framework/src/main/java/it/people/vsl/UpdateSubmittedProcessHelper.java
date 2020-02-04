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

import it.people.core.PeopleContext;
import it.people.core.SubmittedProcessManager;
import it.people.core.persistence.exception.peopleException;
import it.people.process.AbstractPplProcess;
import it.people.process.SubmittedProcess;
import it.people.process.SubmittedProcessHistory;
import it.people.process.SubmittedProcessInformation;
import it.people.process.SubmittedProcessState;
import it.people.process.data.AbstractData;
import it.people.util.info.ProcessInfoEnum;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Category;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         19/ott/2012 09:43:24
 */
public class UpdateSubmittedProcessHelper {

    private UpdateSubmittedProcessHelper() {

    }

    /**
     * @param p_itemToProcess
     * @param status
     */
    public static void addProcessHistory(PipelineDataHolder p_itemToProcess,
	    SubmittedProcessState status) {
	SubmittedProcess sp = null;
	try {

	    PeopleContext pc = PeopleContext.create(p_itemToProcess.getUser());
	    sp = SubmittedProcessManager.getInstance().get(
		    pc,
		    (Long) p_itemToProcess.getPlineData().getAttribute(
			    PipelineDataImpl.PROCESS_OID));

	    if (sp != null) {
		SubmittedProcessHistory sph = new SubmittedProcessHistory();
		sph.setTransactionTime(new Timestamp(new Date().getTime()));
		sph.setState(status);

		// AddHistory
		sp.addHistoryStates(sph);

		SubmittedProcessManager.getInstance().set(pc, sp);
	    }
	} catch (peopleException ex1) {
	    Category.getInstance(CreateSubmittedProcessHelper.class.getName())
		    .error(ex1.getMessage(), ex1);
	}

    }

    /**
     * @param pc
     * @param sp
     * @param sps
     */
    public static void addProcessHistory(PeopleContext pc, SubmittedProcess sp,
	    SubmittedProcessState sps) {
	try {
	    SubmittedProcessHistory sph = new SubmittedProcessHistory();
	    sph.setTransactionTime(new Timestamp(new Date().getTime()));
	    sph.setState(sps);

	    // addHistory sph
	    sp.addHistoryStates(sph);

	    // set sp
	    SubmittedProcessManager.getInstance().set(pc, sp);
	} catch (peopleException ex1) {
	    Category.getInstance(CreateSubmittedProcessHelper.class.getName())
		    .error(ex1.getMessage(), ex1);
	}

    }

    /**
     * @param p_itemToProcess
     * @param status
     */
    public static void addProcessHistory(PipelineDataHolder p_itemToProcess,
	    SubmittedProcessState status, String errorInfo) {
	SubmittedProcess sp = null;
	try {

	    PeopleContext pc = PeopleContext.create(p_itemToProcess.getUser());
	    sp = SubmittedProcessManager.getInstance().get(
		    pc,
		    (Long) p_itemToProcess.getPlineData().getAttribute(
			    PipelineDataImpl.PROCESS_OID));

	    if (sp != null) {
		SubmittedProcessHistory sph = new SubmittedProcessHistory();
		sph.setTransactionTime(new Timestamp(new Date().getTime()));
		sph.setState(status);
		sph.setErrorInfo(errorInfo);

		// AddHistory
		sp.addHistoryStates(sph);

		SubmittedProcessManager.getInstance().set(pc, sp);
	    }
	} catch (peopleException ex1) {
	    Category.getInstance(CreateSubmittedProcessHelper.class.getName())
		    .error(ex1.getMessage(), ex1);
	}

    }

    /**
     * @param pc
     * @param sp
     * @param sps
     */
    public static void addProcessHistory(PeopleContext pc, SubmittedProcess sp,
	    SubmittedProcessState sps, String errorInfo) {
	try {
	    SubmittedProcessHistory sph = new SubmittedProcessHistory();
	    sph.setTransactionTime(new Timestamp(new Date().getTime()));
	    sph.setState(sps);
	    sph.setErrorInfo(errorInfo);

	    // addHistory sph
	    sp.addHistoryStates(sph);

	    // set sp
	    SubmittedProcessManager.getInstance().set(pc, sp);
	} catch (peopleException ex1) {
	    Category.getInstance(CreateSubmittedProcessHelper.class.getName())
		    .error(ex1.getMessage(), ex1);
	}

    }

    public static void addProcessInfo(PipelineDataHolder p_itemToProcess,
	    SubmittedProcessInformation info) {
	SubmittedProcess sp = null;
	try {

	    PeopleContext pc = PeopleContext.create(p_itemToProcess.getUser());
	    sp = SubmittedProcessManager.getInstance().get(
		    pc,
		    (Long) p_itemToProcess.getPlineData().getAttribute(
			    PipelineDataImpl.PROCESS_OID));

	    if (sp != null) {
		sp.setProcessInformation(info.getKey(), info);

		SubmittedProcessManager.getInstance().set(pc, sp);
	    }
	} catch (peopleException ex1) {
	    Category.getInstance(CreateSubmittedProcessHelper.class.getName())
		    .error(ex1.getMessage(), ex1);
	}

    }

    public static void addProcessInfo(PeopleContext pc, SubmittedProcess sp,
	    SubmittedProcessInformation info) {
	try {
	    sp.setProcessInformation(info.getKey(), info);

	    // set sp
	    SubmittedProcessManager.getInstance().set(pc, sp);
	} catch (peopleException ex1) {
	    Category.getInstance(CreateSubmittedProcessHelper.class.getName())
		    .error(ex1.getMessage(), ex1);
	}

    }

    public static void setProcessState(PeopleContext pc, SubmittedProcess sp,
	    SubmittedProcessState sps) {
	try {
	    SubmittedProcessHistory[] sph;

	    sph = sp.getHistoryState();

	    if (sph != null) {
		sph[0].setState(sps);
		sph[0].setTransactionTime(new Timestamp(new Date().getTime()));
		sp.setHistoryState(new SubmittedProcessHistory[] { sph[0] });
		SubmittedProcessManager.getInstance().set(pc, sp);
	    } else {
		sph = new SubmittedProcessHistory[1];
		sph[0] = new SubmittedProcessHistory();
		sph[0].setTransactionTime(new Timestamp(new Date().getTime()));
		sph[0].setState(sps);

		// addHistory sph
		sp.setHistoryState(new SubmittedProcessHistory[] { sph[0] });
		// sp.addHistoryStates(sph);

		// set sp
		SubmittedProcessManager.getInstance().set(pc, sp);
	    }
	} catch (peopleException ex1) {
	    Category.getInstance(CreateSubmittedProcessHelper.class.getName())
		    .error(ex1.getMessage(), ex1);
	}

    }

    /**
     * Setta il Transport Tracking Number del processo inviato.
     * 
     * @param p_itemToProcess
     *            la pipeline data da aggiornare.
     */
    public static void setTrackingNumber(PipelineDataHolder p_itemToProcess) {
	SubmittedProcess sp = null;
	try {
	    String p_trackingNumber = (String) p_itemToProcess
		    .getPlineData()
		    .getAttribute(
			    PipelineDataImpl.TRANSPORT_TRACKINGNUMBER_PARAMNAME);
	    if (p_trackingNumber == null || p_trackingNumber.length() == 0)
		p_trackingNumber = "0";

	    PeopleContext pc = PeopleContext.create(p_itemToProcess.getUser());
	    sp = SubmittedProcessManager.getInstance().get(
		    pc,
		    (Long) p_itemToProcess.getPlineData().getAttribute(
			    PipelineDataImpl.PROCESS_OID));

	    if (sp != null) {
		// TrackingNumber
		sp.setTransportTrackingNumber(p_trackingNumber);

		SubmittedProcessManager.getInstance().set(pc, sp);
	    }
	} catch (peopleException ex1) {
	    Category.getInstance(CreateSubmittedProcessHelper.class.getName())
		    .error(ex1.getMessage(), ex1);
	}
    }

    /**
     * Set the send error state
     * 
     * @param p_itemToProcess
     *            la pipeline data da aggiornare.
     */
    public static void setSendError(PipelineDataHolder p_itemToProcess,
	    Boolean sendErrorState) {
	SubmittedProcess sp = null;
	try {

	    PeopleContext pc = PeopleContext.create(p_itemToProcess.getUser());
	    sp = SubmittedProcessManager.getInstance().get(
		    pc,
		    (Long) p_itemToProcess.getPlineData().getAttribute(
			    PipelineDataImpl.PROCESS_OID));

	    if (sp != null) {
		// TrackingNumber
		sp.setSendError(sendErrorState);

		SubmittedProcessManager.getInstance().set(pc, sp);
	    }
	} catch (peopleException ex1) {
	    Category.getInstance(CreateSubmittedProcessHelper.class.getName())
		    .error(ex1.getMessage(), ex1);
	}
    }

    /**
     * <p>
     * Update the submitted date time for the specified submitted process.
     * 
     * @param p_itemToProcess
     */
    public static void updateSubmittedTime(PipelineDataHolder p_itemToProcess) {
	SubmittedProcess sp = null;
	try {

	    PeopleContext pc = PeopleContext.create(p_itemToProcess.getUser());
	    sp = SubmittedProcessManager.getInstance().get(
		    pc,
		    (Long) p_itemToProcess.getPlineData().getAttribute(
			    PipelineDataImpl.PROCESS_OID));

	    if (sp != null) {
		sp.setSubmittedTime(new Timestamp(Calendar.getInstance()
			.getTimeInMillis()));
		SubmittedProcessManager.getInstance().set(pc, sp);
	    }
	} catch (peopleException ex1) {
	    Category.getInstance(CreateSubmittedProcessHelper.class.getName())
		    .error(ex1.getMessage(), ex1);
	}
    }

    public static void addAttachmentsStatisticData(
	    PipelineDataHolder p_itemToProcess) {
	SubmittedProcess sp = null;
	try {

	    PeopleContext pc = PeopleContext.create(p_itemToProcess.getUser());
	    sp = SubmittedProcessManager.getInstance().get(
		    pc,
		    (Long) p_itemToProcess.getPlineData().getAttribute(
			    PipelineDataImpl.PROCESS_OID));

	    AbstractPplProcess process = (AbstractPplProcess) p_itemToProcess
		    .getPlineData().getAttribute(
			    PipelineDataImpl.EDITABLEPROCESS_PARAMNAME);

	    if (sp != null && process != null) {

		AbstractData data = ((AbstractData) process.getData());

		if (data != null) {

		    List attachments = data.getAllegati();

		    // Initialize attachments numbers with summary if present.
		    int attachmentsNumber = (p_itemToProcess.getPlineData()
			    .getAttribute(
				    PipelineDataImpl.SIGNED_PRINTPAGE_NAME) != null || p_itemToProcess
			    .getPlineData().getAttribute(
				    PipelineDataImpl.PRINTPAGE_PARAMNAME) != null) ? 1
			    : 0;

		    attachmentsNumber += (attachments != null) ? attachments
			    .size() : 0;

		    SubmittedProcessInformation submittedProcessInformation = new SubmittedProcessInformation();
		    submittedProcessInformation
			    .setKey(ProcessInfoEnum.totalAttachmentsNumber
				    .getKey());
		    submittedProcessInformation.setMarshalledData(String
			    .valueOf(attachmentsNumber));

		    sp.setProcessInformation(
			    submittedProcessInformation.getKey(),
			    submittedProcessInformation);

		    SubmittedProcessManager.getInstance().set(pc, sp);
		}
	    }
	} catch (peopleException ex1) {
	    Category.getInstance(CreateSubmittedProcessHelper.class.getName())
		    .error(ex1.getMessage(), ex1);
	}
    }

}
