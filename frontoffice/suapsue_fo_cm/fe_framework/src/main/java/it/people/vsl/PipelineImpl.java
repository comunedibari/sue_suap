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
import it.people.core.persistence.exception.peopleException;
import it.people.util.DataHolderStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Category;

/**
 * 
 * User: acuffaro Date: 12-set-2003 Time: 14.17.16
 * 
 */
public class PipelineImpl extends Thread implements Pipeline {

    // log
    private Category cat = Category.getInstance(PipelineImpl.class.getName());

    private String processName;

    /**
     *
     */
    protected PipelineImpl() {
	super.start();
	m_handlers = new ArrayList();
	m_plDataQueue = new ArrayList();
    }

    /**
     * 
     * @param processClass
     */
    public PipelineImpl(String processName) {
	this();
	this.processName = processName;
    }

    /**
     *
     */
    public void run() {
	while (true) {
	    try {
		Thread.sleep(20000);
	    } catch (InterruptedException ie) {
		cat.error(ie);
	    }
	    try {
		doRun();
	    } catch (peopleException pex) {
		cat.error(pex);
	    }
	    cleanDataQueue();
	}
    }

    /**
     *
     */
    public void savePipeline() {
    }

    /**
     *
     */
    public void loadPipeline() {
    }

    /**
     * Aggiunge un handler alla pipeline
     * 
     * @param p_PLHandler
     *            Handler
     */
    public void addHandler(PipelineHandler p_PLHandler) {
	p_PLHandler.setPipeline(this);
	m_handlers.add(p_PLHandler);
    }

    /**
     * 
     * @param position
     */
    public void removeHandler(int position) {
    }

    // public void isEmpty(){}

    /**
     * Inserisce l'oggetto PipelineData dentro un PipelineDataHolder, aggiunge
     * l'holder alla coda della pipeline e restituisce la chiave dell'holder
     * stesso (per poterlo recuperare).
     * 
     * @param context
     *            PeopleContext
     * @param p_PLData
     *            PipelineData
     * @return Restituisce la chiave che identifica l'holder
     */
    public String put(PeopleContext context, PipelineData p_PLData) {
	PipelineDataHolder myHolder = wrapPipelineData(context, p_PLData);
	addToDataQueue(myHolder);
	return myHolder.getKey();
    }

    private void addToDataQueue(PipelineDataHolder p_myHolder) {
	// todo gestire errore se p_myHolder ? NULL
	if (p_myHolder != null)
	    m_plDataQueue.add(p_myHolder);
    }

    /**
     * 
     * @param context
     *            PeopleContext
     * @param p_PLData
     *            PipelineData
     * @return Restituisce un oggetto PipelineDataHolder contenente la
     *         PipelineData
     */
    private PipelineDataHolder wrapPipelineData(PeopleContext context,
	    PipelineData p_PLData) {
	// todo gestire errore se p_myHolder ? NULL
	PipelineDataHolder myHolder = new PipelineDataHolder(context, p_PLData);
	myHolder.assignTo(0);
	myHolder.setStatusAssigned();
	return myHolder;
    }

    public int getQueueLength() {
	return m_plDataQueue.size();
    }

    public ArrayList getQueue() {
	return new ArrayList(m_plDataQueue);
    }

    public int getPipelineLength() {
	return m_handlers.size();
    }

    public String getProcessName() {
	return processName;
    }

    public void setProcessName(String processName) {
	this.processName = processName;
    }

    /**
     * Permette di processare uno specifico holder.
     * 
     * @param p_obj_request
     *            Request
     * @param p_obj_response
     *            Response
     * @param key
     *            Chiave dell' holder da processare
     * @throws peopleException
     */
    public void process(HttpServletRequest p_obj_request,
	    HttpServletResponse p_obj_response, String key)
	    throws peopleException {
	Collection holders = buildHoldersList(key);
	if (holders.size() > 0) {
	    try {
		PipelineDataHolder pdH = (PipelineDataHolder) holders
			.iterator().next();
		pdH.getPlineData().setAttribute(
			PipelineDataImpl.HTTP_REQUEST_PARAMNAME, p_obj_request);
		pdH.getPlineData().setAttribute(
			PipelineDataImpl.HTTP_RESPONSE_PARAMNAME,
			p_obj_response);
		int currentHandler = pdH.getAssigned();
		if (!pdH.isInvalid()) {
		    if (currentHandler >= 0
			    && currentHandler < m_handlers.size()) {
			PipelineHandler plHandler = (PipelineHandler) m_handlers
				.get(currentHandler);

			if (plHandler.isGuiHandler()) {
			    plHandler.process(holders);
			    moveToNextHandler(holders);
			}
		    } else {
			// todo Eliminare l'holder
		    }
		}
	    } catch (Exception holderEx) {
		cat.error(holderEx);
		throw new peopleException(holderEx.getMessage());
	    }
	}
    }

    /**
     * Il metodo scorre tutti gli handler della pipeline e passa ad ognuno di
     * loro gli holders che devono processare.
     * 
     * @throws peopleException
     */
    protected void doRun() throws peopleException {
	// System.out.println(this.getClass() + " doRun started");
	try {
	    for (int i = 0; i < m_handlers.size(); i++) {
		PipelineHandler plHandler = (PipelineHandler) m_handlers.get(i);

		if (!plHandler.isGuiHandler() && plHandler.isFree()) {
		    try {
			Collection holders = buildHoldersList(i);
			if (holders.size() > 0) {
			    plHandler.process(holders);
			    manageAbortedHolder(holders);
			    moveToNextHandler(holders);
			}
		    } catch (Exception holderEx) {
			cat.error(holderEx);
			throw new peopleException(holderEx.getMessage());
		    }
		}
	    }
	} catch (Exception ex) {
	    cat.error(ex);
	    throw new peopleException(ex.getMessage());
	}
    }

    public void write() {
    }

    /*
     * public boolean hasHandlers() { return ( getPipelineLength() != 0); }
     */

    /**
     * 
     * @param handlerIndex
     *            Indice dell'handler
     * @return Restituisce una collezione contenente gli holder che devono
     *         essere processati dall'handler specificato.
     */
    protected Collection buildHoldersList(int handlerIndex) {
	ArrayList holders = new ArrayList();
	for (int i = 0; i < m_plDataQueue.size(); i++) {
	    PipelineDataHolder pdH = (PipelineDataHolder) m_plDataQueue.get(i);
	    if (pdH.getAssigned() == handlerIndex)
		holders.add(pdH);
	}
	return holders;

    }

    /**
     * 
     * @param key
     *            Chiave dell'holder da recuperare
     * @return Restituisce una collezione contenente l'holder corrispondente
     *         alla chiave passata in ingresso
     */
    protected Collection buildHoldersList(String key) {
	ArrayList holders = new ArrayList();
	if (key != null) {
	    for (int i = 0; i < m_plDataQueue.size(); i++) {
		PipelineDataHolder pdH = (PipelineDataHolder) m_plDataQueue
			.get(i);
		if (key.equals(pdH.getKey()))
		    holders.add(pdH);
	    }
	}
	return holders;
    }

    protected void moveToNextHandler(Collection holders) {
	Iterator iter = holders.iterator();
	while (iter.hasNext()) {
	    PipelineDataHolder pdh = (PipelineDataHolder) iter.next();
	    if (pdh.getStatus().equals(DataHolderStatus.COMPLETED)) {
		int currentHandlerIndex = pdh.getAssigned();
		if (currentHandlerIndex < m_handlers.size() - 1) {
		    pdh.nextHandler();
		} else
		    pdh.invalidate();
	    }

	}
    }

    /*
     * ETNO Questa funzione non ha senso: continua a rendere attvi degli holder
     * che in realta' sono stati precedentemente invalidati e fa si che la coda
     * non si svuoti mai.
     */
    protected void manageAbortedHolder(Collection holders) {
	Iterator iter = holders.iterator();
	while (iter.hasNext()) {
	    PipelineDataHolder pdh = (PipelineDataHolder) iter.next();
	    if (pdh.getStatus().equals(DataHolderStatus.ABORTED)) {
		pdh.setStatusAssigned();
		int position = m_plDataQueue.indexOf(pdh);
		if (position >= 0) {
		    m_plDataQueue.remove(position);
		    // m_plDataQueue.add(pdh);
		}
	    }

	}
    }

    protected void cleanDataQueue() {
	// System.out.println(this.getClass() + " cleanDataQueue started");
	try {
	    for (int i = 0; i < m_plDataQueue.size(); i++) {
		PipelineDataHolder pdH = (PipelineDataHolder) m_plDataQueue
			.get(i);
		if (pdH.isInvalid()) {
		    cat.debug(this.getClass() + " cleanDataQueue remove item "
			    + i + " with key " + pdH.getKey());

		    m_plDataQueue.remove(i);
		}
	    }

	} catch (Exception ex) {
	    cat.error(ex);

	}
	// System.out.println(this.getClass() + " cleanDataQueue ended");
    }

    public PipelineHandler[] getHandlers() {
	return (PipelineHandler[]) m_handlers
		.toArray(new PipelineHandler[m_handlers.size()]);
    }

    public Collection getQueueItem(int handlerIndex) {
	return buildHoldersList(handlerIndex);
    }

    private ArrayList m_plDataQueue;
    private ArrayList m_handlers;
}
