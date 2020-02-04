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
package it.people.process;

import it.people.City;
import it.people.core.PeopleContext;
import it.people.core.ProcessManager;
import it.people.core.SubmittedProcessManager;
import it.people.core.persistence.exception.peopleException;
import it.people.util.Device;
import it.people.vsl.CompleteProcessHandler;
import it.people.vsl.CreateSubmittedProcessHelper;
import it.people.vsl.PipelineHandler;
import it.people.vsl.PipelineImpl;
import it.people.vsl.ProcessSenderHandler;
import it.people.vsl.ProcessStatusHandler;

import java.sql.Timestamp;
import java.util.HashMap;

import org.apache.log4j.Category;

/**
 * 
 * User: sergio Date: Oct 7, 2003 Time: 10:28:46 PM <br>
 * <br>
 * Questa classe rappresenta un processo generico.
 */
public class GenericProcess extends AbstractPplProcess {

    private Category cat = Category.getInstance(GenericProcess.class.getName());

    /**
     * Costruttore.
     */
    public GenericProcess() {
	super();
    }

    /**
     * Costruttore.
     * 
     * @param context
     * @param unComune
     * @param device
     * @throws Exception
     * @throws peopleException
     */
    public GenericProcess(PeopleContext context, City unComune, Device device)
	    throws Exception, peopleException {
	this();
	initialize(context, unComune, device);
    }

    /**
     * Inizializzo il processo.
     * 
     * @param context
     * @param unComune
     * @param device
     * @throws Exception
     * @throws peopleException
     */
    public void initialize(PeopleContext context, City unComune, Device device)
	    throws Exception, peopleException {

	super.initialize(context, unComune, device);

	if (m_process == null)
	    throw new Exception("IProcess object is null.");

	createView(unComune, device);
	// se il procedimento di print � definito
	if (m_process.getProcessPrint() != null
		&& !m_process.getProcessPrint().isEmpty()) {
	    createPrint(unComune);
	} else {
	    m_printDelegates = new HashMap();
	}

	// se il procedimento di firma � definito
	if (m_process.getProcessSign() != null
		&& !m_process.getProcessSign().equals("")) {
	    createSign(unComune, device, context);
	} else {
	    m_signDelegate = null;
	}

	createModel(unComune, device, context);

	setCommune(unComune);

	m_initialized = true;

    }

    /**
     * Restituisce l'implementazione della pipeline.
     */
    public static Class getPipelineImpl() {
	return PipelineImpl.class;
    }

    /**
     * Restituisce gli Hendler associati al processo.
     */
    public static PipelineHandler[] getPipelineHandlers() {
	return new PipelineHandler[] { new CompleteProcessHandler(),
		new ProcessSenderHandler(2), new ProcessStatusHandler(2)

	// todo lasthandler il processo generico invia mail certificate? LC

	};
    }

    /**
     * Imposta lo stato del procedimento a inviato.
     * 
     * @param context
     * @throws peopleException
     */
    public void setAsSubmitted() throws peopleException {
	this.setSubmittedState(new SubmittedProcessState[] {
	/*
	 * lo stato INITIALIZING e' aggiunto di default alla creazione del
	 * submittedProcess
	 */
	/* SubmittedProcessState.INITIALIZING, */
	SubmittedProcessState.SUBMITTED });
    }

    /**
     * Permette di modificare lo stato di un process a submitted, aggiunge lo
     * stato alla history del submitted process.
     * 
     * @param state
     *            stato per il submitted process
     */
    public void setSubmittedState(SubmittedProcessState[] states)
	    throws peopleException {

	if (cat.isDebugEnabled()) {
	    String message = "PipelineLessProcess.setSubmittedState() richiamato per gli stati [";
	    for (int i = 0; i < states.length; i++) {
		message += states[i].getLabel() + " ";
	    }
	    message += "]";
	    cat.debug(message);
	}

	// Se non � ancora passato nello stato SENT cambia lo stato
	this.setSent(Boolean.TRUE);

	// Salva il process
	ProcessManager.getInstance().set(this.getContext(), this);

	// Ricerca il submitted process collegato
	SubmittedProcess submittedProcess = SubmittedProcessManager
		.getInstance().getFromPending(this.getContext(), this.getOid());

	if (submittedProcess == null) {
	    // il submitted process non esiste lo crea
	    submittedProcess = CreateSubmittedProcessHelper
		    .createSubmittedProcess(this.getContext(), this);
	}

	// aggiunge gli stati passati se non sono gi� presenti
	for (int i = 0; i < states.length; i++) {
	    addHistoryState(submittedProcess, states[i]);
	}

	// Salva il submittedProcess
	SubmittedProcessManager.getInstance().set(this.getContext(),
		submittedProcess);
    }

    protected void addHistoryState(SubmittedProcess submittedProcess,
	    SubmittedProcessState state) {
	// Verifica se lo stato � gi� stato creato
	boolean foundState = false;
	SubmittedProcessHistory[] history = submittedProcess.getHistoryState();
	for (int i = 0; i < history.length; i++) {
	    if (history[i].getState().equals(state)) {
		foundState = true;
		break;
	    }
	}

	// Se non trova lo stato lo aggiunge all'history
	if (!foundState) {
	    SubmittedProcessHistory newHistory = new SubmittedProcessHistory(
		    state);

	    // Essendo transaction time chiave dell'history
	    // devo assicurarmi di non inserirne di uguali
	    // N.B. MySql salva il timestamp con la precisione di un secondo

	    if (history.length > 0) {
		long lastSeconds = (history[history.length - 1]
			.getTransactionTime().getTime()) / 1000;
		long newerSeconds = (newHistory.getTransactionTime().getTime()) / 1000;
		if (lastSeconds == newerSeconds) {
		    newHistory.setTransactionTime(new Timestamp(
			    (newerSeconds + 1) * 1000));
		}
	    }
	    submittedProcess.addHistoryStates(newHistory);
	}
    }
}
