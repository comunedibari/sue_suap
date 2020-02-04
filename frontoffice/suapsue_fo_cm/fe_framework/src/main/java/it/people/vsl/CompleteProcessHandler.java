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
import it.people.core.PplUser;
import it.people.core.ProcessManager;
import it.people.core.SubmittedProcessManager;
import it.people.core.persistence.exception.peopleException;
import it.people.process.AbstractPplProcess;
import it.people.process.SubmittedProcess;
import it.people.propertymgr.PropertyFormatException;
import it.people.util.DataHolderStatus;
import it.people.util.PeopleProperties;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Category;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Oct 9, 2003 Time: 10:09:08 PM To
 * change this template use Options | File Templates. <br>
 * <br>
 * Questo handler modifica lo stato del processo ad inviato (crea inoltre nella
 * base dati un record nella tabella dei processi inviati).
 * 
 */
public class CompleteProcessHandler extends PipelineHandlerImpl {

    private Category cat = Category.getInstance(CompleteProcessHandler.class
	    .getName());

    public String getName() {
	return "Archiviazione Dati Inviati";
    }

    /**
     * Il metodo scorre tutti gli holder passati in ingersso ed elabora prima
     * tutti quelli che sono gi� in stato working (gi� elaborati almeo una
     * volta) poi quelli in stato assegnato (prima volta che vengono elaborati).
     * 
     * @param holders
     *            Collezione degli holder da elaborare
     * @throws peopleException
     */
    public void process(Collection holders) throws peopleException {
	setFree(false);
	try {
	    boolean executeOne = false;
	    Iterator myIterator = holders.iterator();
	    // primo giro: elabora eventuali data holder che sono gi� in stato
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
	    cat.error(ex);
	    // throw new peopleException(ex.getMessage());
	}
	setFree(true);

    }

    /**
     * 
     * @param p_itemToProcess
     *            Singolo holder da elaborare
     * @throws peopleException
     */
    private void process(PipelineDataHolder p_itemToProcess)
	    throws peopleException {
	p_itemToProcess.setStatusWorking();

	Boolean isResendObject = (Boolean) p_itemToProcess.getPlineData()
		.getAttribute(PipelineDataImpl.IS_RESEND);
	boolean isResend = (isResendObject == null) ? false : isResendObject
		.booleanValue();

	if (!isResend) {
	    AbstractPplProcess pplProcess = (AbstractPplProcess) p_itemToProcess
		    .getPlineData().getAttribute(
			    PipelineDataImpl.EDITABLEPROCESS_PARAMNAME);

	    try {
		pplProcess.setSent(new Boolean(true));
		ProcessManager.getInstance().set(
			PeopleContext.create((PplUser) p_itemToProcess
				.getPlineData().getAttribute(
					PipelineDataImpl.USER_PARAMNAME)),
			pplProcess);
	    } catch (Exception ex) {
		cat.error(ex);
		p_itemToProcess.setStatusAborted();
		return;
	    }
	}

	/**
	 * Salviamo l'oggetto submitted process
	 */
	try {
	    if (isResend) {
		UpdateSubmittedProcessHelper
			.updateSubmittedTime(p_itemToProcess);
	    } else {
		SubmittedProcess sp = CreateSubmittedProcessHelper
			.createSubmittedProcess(p_itemToProcess);
		sp.setDelegate(((Boolean) p_itemToProcess.getPlineData()
			.getAttribute(PipelineDataImpl.IS_DELEGATED))
			.booleanValue());
		SubmittedProcessManager.getInstance().set(
			PeopleContext.create(p_itemToProcess.getUser()), sp);
		p_itemToProcess.getPlineData().setAttribute(
			PipelineDataImpl.PROCESS_OID, sp.getOid());
	    }
	    p_itemToProcess.setStatusCompleted();
	} catch (Exception ex) {
	    cat.error(ex);
	    p_itemToProcess.setStatusAborted();
	    return;
	}
	p_itemToProcess.setStatusCompleted();
    }
}
