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
import java.util.Date;
import java.util.List;

import org.apache.log4j.Category;

public class CreateSubmittedProcessHelper {

    public CreateSubmittedProcessHelper() {
    }

    /**
     * @param p_dh
     * @return
     */
    public static SubmittedProcess createSubmittedProcess(
	    PipelineDataHolder p_dh) {

	SubmittedProcess sp = SubmittedProcessManager.getInstance().create(
		PeopleContext.create(p_dh.getUser()));
	sp.setUser(p_dh.getUser());
	sp.setTransportTrackingNumber((String) p_dh.getPlineData()
		.getAttribute(
			PipelineDataImpl.TRANSPORT_TRACKINGNUMBER_PARAMNAME));
	sp.setPeopleProtocollId((String) p_dh.getPlineData().getAttribute(
		PipelineDataImpl.PEOPLE_PROTOCOLL_ID_PARAMNAME));
	sp.setEditableProcessId((Long) p_dh.getPlineData().getAttribute(
		PipelineDataImpl.EDITABLEPROCESS_ID_PARAMNAME));
	sp.setCommune((City) p_dh.getPlineData().getAttribute(
		PipelineDataImpl.COMMUNE_PARAMNAME));
	sp.setCompleted(new Boolean(false));

	AbstractPplProcess process = (AbstractPplProcess) p_dh.getPlineData()
		.getAttribute(PipelineDataImpl.EDITABLEPROCESS_PARAMNAME);
	if (process != null) {
	    sp.setSignRequired(process.isSignEnabled());
	    sp.setSignOnLineEnabled(process.isOnLineSign());
	    sp.setSignOffLineEnabled(process.isOffLineSign());
	    sp.setOnLineSigned(process.isSignEnabled()
		    && (process.getOffLineSignDownloadedDocumentHash() == null || (process
			    .getOffLineSignDownloadedDocumentHash() != null && process
			    .getOffLineSignDownloadedDocumentHash()
			    .equalsIgnoreCase(""))));
	    sp.setOffLineSigned(!sp.isOnLineSigned());

	}

	sp.setProcessInformation(new SubmittedProcessInformation[] {});

	return sp;
    }

    public static SubmittedProcess createSubmittedProcess(
	    PeopleContext context, AbstractPplProcess process) {

	AbstractData ad = (AbstractData) process.getData();

	SubmittedProcess sp = SubmittedProcessManager.getInstance().create(
		context);
	sp.setUser(context.getUser());
	// ETNO verificare il tracking number
	sp.setTransportTrackingNumber("0");
	sp.setPeopleProtocollId(ad.getIdentificatorePeople()
		.getIdentificatoreProcedimento());
	sp.setEditableProcessId(process.getOid());
	sp.setCommune(process.getCommune());
	sp.setCompleted(new Boolean(false));

	if (process != null) {
	    sp.setSignRequired(process.isSignEnabled());
	    sp.setSignOnLineEnabled(process.isOnLineSign());
	    sp.setSignOffLineEnabled(process.isOffLineSign());
	    sp.setOnLineSigned(process.isSignEnabled()
		    && (process.getOffLineSignDownloadedDocumentHash() == null || (process
			    .getOffLineSignDownloadedDocumentHash() != null && process
			    .getOffLineSignDownloadedDocumentHash()
			    .equalsIgnoreCase(""))));
	    sp.setOffLineSigned(!sp.isOnLineSigned());
	}

	sp.setProcessInformation(new SubmittedProcessInformation[] {});

	return sp;
    }

    /**
     * @param p_itemToProcess
     * @param status
     */
    public static void addProcessHistory(PipelineDataHolder p_itemToProcess,
	    SubmittedProcessState status) {
	UpdateSubmittedProcessHelper.addProcessHistory(p_itemToProcess, status);
	// SubmittedProcess sp = null;
	// try {
	//
	// PeopleContext pc = PeopleContext.create(p_itemToProcess.getUser());
	// sp = SubmittedProcessManager.getInstance().get(pc,
	// (Long)
	// p_itemToProcess.getPlineData().getAttribute(PipelineDataImpl.PROCESS_OID));
	//
	// if (sp != null) {
	// SubmittedProcessHistory sph = new SubmittedProcessHistory();
	// sph.setTransactionTime(new Timestamp(new Date().getTime()));
	// sph.setState(status);
	//
	// //AddHistory
	// sp.addHistoryStates(sph);
	//
	// SubmittedProcessManager.getInstance().set(pc, sp);
	// }
	// } catch (peopleException ex1) {
	// Category.getInstance(CreateSubmittedProcessHelper.class.getName()).error(ex1.getMessage(),
	// ex1);
	// }

    }

    /**
     * @param pc
     * @param sp
     * @param sps
     */
    public static void addProcessHistory(PeopleContext pc, SubmittedProcess sp,
	    SubmittedProcessState sps) {
	UpdateSubmittedProcessHelper.addProcessHistory(pc, sp, sps);
	// try {
	// SubmittedProcessHistory sph = new SubmittedProcessHistory();
	// sph.setTransactionTime(new Timestamp(new Date().getTime()));
	// sph.setState(sps);
	//
	// //addHistory sph
	// sp.addHistoryStates(sph);
	//
	// //set sp
	// SubmittedProcessManager.getInstance().set(pc, sp);
	// } catch (peopleException ex1) {
	// Category.getInstance(CreateSubmittedProcessHelper.class.getName()).error(ex1.getMessage(),
	// ex1);
	// }

    }

    /**
     * @param p_itemToProcess
     * @param status
     * @param errorInfo
     */
    public static void addProcessHistory(PipelineDataHolder p_itemToProcess,
	    SubmittedProcessState status, String errorInfo) {

	UpdateSubmittedProcessHelper.addProcessHistory(p_itemToProcess, status,
		errorInfo);

    }

    /**
     * @param pc
     * @param sp
     * @param sps
     * @param errorInfo
     */
    public static void addProcessHistory(PeopleContext pc, SubmittedProcess sp,
	    SubmittedProcessState sps, String errorInfo) {

	UpdateSubmittedProcessHelper.addProcessHistory(pc, sp, sps, errorInfo);

    }

    public static void addProcessInfo(PipelineDataHolder p_itemToProcess,
	    SubmittedProcessInformation info) {
	UpdateSubmittedProcessHelper.addProcessInfo(p_itemToProcess, info);
	// SubmittedProcess sp = null;
	// try {
	//
	// PeopleContext pc = PeopleContext.create(p_itemToProcess.getUser());
	// sp = SubmittedProcessManager.getInstance().get(pc,
	// (Long)
	// p_itemToProcess.getPlineData().getAttribute(PipelineDataImpl.PROCESS_OID));
	//
	// if (sp != null) {
	// sp.setProcessInformation(info.getKey(), info);
	//
	// SubmittedProcessManager.getInstance().set(pc, sp);
	// }
	// } catch (peopleException ex1) {
	// Category.getInstance(CreateSubmittedProcessHelper.class.getName()).error(ex1.getMessage(),
	// ex1);
	// }

    }

    public static void addProcessInfo(PeopleContext pc, SubmittedProcess sp,
	    SubmittedProcessInformation info) {
	UpdateSubmittedProcessHelper.addProcessInfo(pc, sp, info);
	// try {
	// sp.setProcessInformation(info.getKey(), info);
	//
	// //set sp
	// SubmittedProcessManager.getInstance().set(pc, sp);
	// } catch (peopleException ex1) {
	// Category.getInstance(CreateSubmittedProcessHelper.class.getName()).error(ex1.getMessage(),
	// ex1);
	// }

    }

    public static void setProcessState(PeopleContext pc, SubmittedProcess sp,
	    SubmittedProcessState sps) {
	UpdateSubmittedProcessHelper.setProcessState(pc, sp, sps);
	// try {
	// SubmittedProcessHistory[] sph;
	//
	// sph = sp.getHistoryState();
	//
	// if (sph != null) {
	// sph[0].setState(sps);
	// sph[0].setTransactionTime(new Timestamp(new Date().getTime()));
	// sp.setHistoryState(new SubmittedProcessHistory[] { sph[0] });
	// SubmittedProcessManager.getInstance().set(pc, sp);
	// } else {
	// sph = new SubmittedProcessHistory[1];
	// sph[0] = new SubmittedProcessHistory();
	// sph[0].setTransactionTime(new Timestamp(new Date().getTime()));
	// sph[0].setState(sps);
	//
	// //addHistory sph
	// sp.setHistoryState(new SubmittedProcessHistory[] { sph[0] });
	// //sp.addHistoryStates(sph);
	//
	// //set sp
	// SubmittedProcessManager.getInstance().set(pc, sp);
	// }
	// } catch (peopleException ex1) {
	// Category.getInstance(CreateSubmittedProcessHelper.class.getName()).error(ex1.getMessage(),
	// ex1);
	// }

    }

    /**
     * Setta il Transport Tracking Number del processo inviato.
     * 
     * @param p_itemToProcess
     *            la pipeline data da aggiornare.
     */
    public static void setTrackingNumber(PipelineDataHolder p_itemToProcess) {
	UpdateSubmittedProcessHelper.setTrackingNumber(p_itemToProcess);
	// SubmittedProcess sp = null;
	// try {
	// String p_trackingNumber = (String)
	// p_itemToProcess.getPlineData().getAttribute(PipelineDataImpl.TRANSPORT_TRACKINGNUMBER_PARAMNAME);
	// if (p_trackingNumber == null || p_trackingNumber.length() == 0)
	// p_trackingNumber = "0";
	//
	// PeopleContext pc = PeopleContext.create(p_itemToProcess.getUser());
	// sp = SubmittedProcessManager.getInstance().get(pc, (Long)
	// p_itemToProcess.getPlineData().getAttribute(PipelineDataImpl.PROCESS_OID));
	//
	// if (sp != null) {
	// //TrackingNumber
	// sp.setTransportTrackingNumber(p_trackingNumber);
	//
	// SubmittedProcessManager.getInstance().set(pc, sp);
	// }
	// } catch (peopleException ex1) {
	// Category.getInstance(CreateSubmittedProcessHelper.class.getName()).error(ex1.getMessage(),
	// ex1);
	// }
    }

    /**
     * Set the send error state
     * 
     * @param p_itemToProcess
     *            la pipeline data da aggiornare.
     */
    public static void setSendError(PipelineDataHolder p_itemToProcess,
	    Boolean sendErrorState) {
	UpdateSubmittedProcessHelper.setSendError(p_itemToProcess,
		sendErrorState);
	// SubmittedProcess sp = null;
	// try {
	//
	// PeopleContext pc = PeopleContext.create(p_itemToProcess.getUser());
	// sp = SubmittedProcessManager.getInstance().get(pc, (Long)
	// p_itemToProcess.getPlineData().getAttribute(PipelineDataImpl.PROCESS_OID));
	//
	// if (sp != null) {
	// //TrackingNumber
	// sp.setSendError(sendErrorState);
	//
	// SubmittedProcessManager.getInstance().set(pc, sp);
	// }
	// } catch (peopleException ex1) {
	// Category.getInstance(CreateSubmittedProcessHelper.class.getName()).error(ex1.getMessage(),
	// ex1);
	// }
    }

    public static void addAttachmentsStatisticData(
	    PipelineDataHolder p_itemToProcess) {
	UpdateSubmittedProcessHelper
		.addAttachmentsStatisticData(p_itemToProcess);
	// SubmittedProcess sp = null;
	// try {
	//
	// PeopleContext pc = PeopleContext.create(p_itemToProcess.getUser());
	// sp = SubmittedProcessManager.getInstance().get(pc,
	// (Long)
	// p_itemToProcess.getPlineData().getAttribute(PipelineDataImpl.PROCESS_OID));
	//
	// AbstractPplProcess process =
	// (AbstractPplProcess)p_itemToProcess.getPlineData().getAttribute(PipelineDataImpl.EDITABLEPROCESS_PARAMNAME);
	//
	// if (sp != null && process != null) {
	//
	// AbstractData data = ((AbstractData)process.getData());
	//
	// if (data != null) {
	//
	// List attachments = data.getAllegati();
	//
	// // Initialize attachments numbers with summary if present.
	// int attachmentsNumber =
	// (p_itemToProcess.getPlineData().getAttribute(PipelineDataImpl.SIGNED_PRINTPAGE_NAME)
	// != null ||
	// p_itemToProcess.getPlineData().getAttribute(PipelineDataImpl.PRINTPAGE_PARAMNAME)
	// != null) ? 1 : 0;
	//
	// attachmentsNumber += (attachments != null) ? attachments.size() : 0;
	//
	// SubmittedProcessInformation submittedProcessInformation = new
	// SubmittedProcessInformation();
	// submittedProcessInformation.setKey(ProcessInfoEnum.totalAttachmentsNumber.getKey());
	// submittedProcessInformation.setMarshalledData(String.valueOf(attachmentsNumber));
	//
	// sp.setProcessInformation(submittedProcessInformation.getKey(),
	// submittedProcessInformation);
	//
	// SubmittedProcessManager.getInstance().set(pc, sp);
	// }
	// }
	// } catch (peopleException ex1) {
	// Category.getInstance(CreateSubmittedProcessHelper.class.getName()).error(ex1.getMessage(),
	// ex1);
	// }
    }

}
