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
package it.people.core;

import it.people.process.AbstractPplProcess;
import it.people.util.DataHolderStatus;
import it.people.vsl.Pipeline;
import it.people.vsl.PipelineDataHolder;
import it.people.vsl.PipelineDataImpl;
import it.people.vsl.PipelineFactory;

import java.util.ArrayList;

/**
 * User: sergio Date: Sep 22, 2003 Time: 4:43:19 PM <br>
 * <br>
 * Manager per le ricerche dei PipelineDataHolder.
 */
public class PipelineDataHolderSearchManager {
    private static PipelineDataHolderSearchManager ourInstance;

    public synchronized static PipelineDataHolderSearchManager getInstance() {
	if (ourInstance == null) {
	    ourInstance = new PipelineDataHolderSearchManager();
	}
	return ourInstance;
    }

    private PipelineDataHolderSearchManager() {
    }

    /**
     * 
     * @param context
     *            PeopleContext
     * @return Restituisce tutti gli oggetti PipelineDataHolder dell'utente in
     *         esame che non sono in stato completato
     */
    public PipelineDataHolder[] get(PeopleContext context) {
	Pipeline[] pls = PipelineFactory.getInstance().getPipeline();
	ArrayList resultQueue = new ArrayList();
	int count = 0;
	for (int k = 0; k < pls.length; k++) {
	    ArrayList queue = pls[k].getQueue();

	    for (int i = 0; i < queue.size(); i++) {
		PipelineDataHolder pdh = (PipelineDataHolder) queue.get(i);
		if (!DataHolderStatus.COMPLETED.equals(pdh.getStatus())
			&& context.getUser().equals(pdh.getUser())) {
		    resultQueue.add(pdh);
		    count++;
		}
	    }
	}
	return (PipelineDataHolder[]) resultQueue
		.toArray(new PipelineDataHolder[count]);
    }

    /**
     * 
     * @param context
     *            PeopleContext
     * @param processClass
     *            Classe del processo da esaminare
     * @return Restituisce tutti gli oggetti PipelineDataHolder dell'utente in
     *         esame che non sono in stato completato per la pipeline che
     *         elabora il processo passato come parametro di ingersso.
     */
    // COMMENTATO PERCHE' APPARENTEMENTE NON USATO
    // E' STATO CAMBIATA LA GESTIONE DEL CLASS, ATTUALMENTE NON E' UNA CLASS MA
    // IL NOME DEP PROCESSO
    // public PipelineDataHolder[] get(PeopleContext context, Class
    // processClass) {
    // ArrayList resultQueue = new ArrayList();
    // int count = 0;
    //
    // if (processClass != null) {
    // Pipeline pl = PipelineFactory.getInstance().getPipeline(processClass);
    // if (pl != null) {
    // ArrayList queue = pl.getQueue();
    //
    // for (int i = 0; i < queue.size(); i++) {
    // PipelineDataHolder pdh = (PipelineDataHolder) queue.get(i);
    // if (!DataHolderStatus.COMPLETED.equals(pdh.getStatus()) &&
    // context.getUser().equals(pdh.getUser())) {
    // resultQueue.add(pdh);
    // count++;
    // }
    // }
    // }
    // }
    // return (PipelineDataHolder[]) resultQueue.toArray(new
    // PipelineDataHolder[count]);
    // }

    /**
     * 
     * @param context
     *            PeopleContext
     * @param process
     *            Processo in esame
     * @return Restituisce un vettore di oggetti PipelineDataHolder filtrando in
     *         base al processo, all'utente ed allo stato dell'holder (holder in
     *         stato non completo)
     */
    public PipelineDataHolder[] get(PeopleContext context,
	    AbstractPplProcess process) {
	ArrayList resultQueue = new ArrayList();

	int count = 0;
	if (process != null && process.getOid() != null) {

	    Pipeline pl = PipelineFactory.getInstance().getPipelineForName(
		    process.getCommune().getKey(), process.getProcessName());
	    if (pl != null) {
		ArrayList queue = pl.getQueue();

		for (int i = 0; i < queue.size(); i++) {
		    PipelineDataHolder pdh = (PipelineDataHolder) queue.get(i);
		    if (!DataHolderStatus.COMPLETED.equals(pdh.getStatus())
			    && context.getUser().equals(pdh.getUser())
			    && process
				    .getOid()
				    .equals(pdh
					    .getPlineData()
					    .getAttribute(
						    PipelineDataImpl.EDITABLEPROCESS_ID_PARAMNAME))) {
			resultQueue.add(pdh);
			count++;
		    }
		}
	    }
	}
	return (PipelineDataHolder[]) resultQueue
		.toArray(new PipelineDataHolder[count]);
    }

    /**
     * 
     * @param context
     *            PeopleContext
     * @param key
     *            Processo in esame
     * @return Restituisce un vettore di oggetti PipelineDataHolder filtrando in
     *         base alla chiave, all'utente ed allo stato dell'holder (holder in
     *         stato non completo)
     */
    public PipelineDataHolder[] get(PeopleContext context, String key) {
	Pipeline[] pls = PipelineFactory.getInstance().getPipeline();
	ArrayList resultQueue = new ArrayList();
	int count = 0;
	for (int k = 0; k < pls.length; k++) {
	    ArrayList queue = pls[k].getQueue();

	    for (int i = 0; i < queue.size(); i++) {
		PipelineDataHolder pdh = (PipelineDataHolder) queue.get(i);
		if (key.equals(pdh.getKey())) {
		    if (!DataHolderStatus.COMPLETED.equals(pdh.getStatus())
			    && context.getUser().equals(pdh.getUser()))
			resultQueue.add(pdh);
		    i = queue.size();
		}
	    }
	}
	return (PipelineDataHolder[]) resultQueue
		.toArray(new PipelineDataHolder[count]);
    }
}
