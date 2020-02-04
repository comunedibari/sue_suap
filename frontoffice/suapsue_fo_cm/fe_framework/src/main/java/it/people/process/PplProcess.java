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
import it.people.core.persistence.exception.peopleException;
import it.people.util.Device;
import it.people.vsl.CompleteProcessHandler;
import it.people.vsl.PipelineHandler;
import it.people.vsl.PipelineImpl;
import it.people.vsl.ProcessSenderHandler;
import it.people.vsl.ProcessStatusHandler;

/**
 * 
 * User: sergio Date: Sep 3, 2003 Time: 1:57:51 PM <br>
 * <br>
 * Questa classe rappresenta il processo 'PplProcess'.
 */
public class PplProcess extends AbstractPplProcess {

    /**
     * Costruttore.
     */
    public PplProcess() {
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
    public PplProcess(PeopleContext context, City unComune, Device device)
	    throws Exception, peopleException {
	initialize(context, unComune, device);
    }

    /**
     * Inizializza il processo.
     * 
     * @param context
     * @param unComune
     * @param device
     * @throws Exception
     * @throws peopleException
     */
    public void initialize(PeopleContext context, City unComune, Device device)
	    throws Exception, peopleException {

	/**
	 * Modifica effettuta 11/11/03 Invoco i metodi che leggono da DB Per
	 * tornare alla situazione precedente commentare i metodi attuali e
	 * decommentare quelli vecchi
	 */
	// createView("it.people.process.PplProcess", unComune, device, false);
	// createPrint("it.people.process.PplProcess", unComune, false);

	// createModel(context, "it.people.process.data.PplProcessData");

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
		new ProcessSenderHandler(2), new ProcessStatusHandler(2) };
    }

}
