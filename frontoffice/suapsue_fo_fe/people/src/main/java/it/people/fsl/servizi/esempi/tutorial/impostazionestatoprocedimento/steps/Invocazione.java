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
 * Created on 27-feb-2006
 */
package it.people.fsl.servizi.esempi.tutorial.impostazionestatoprocedimento.steps;

import it.people.IValidationErrors;
import it.people.Step;
import it.people.core.exception.ServiceException;
import it.people.core.persistence.exception.peopleException;
import it.people.fsl.servizi.esempi.tutorial.impostazionestatoprocedimento.model.ProcessData;
import it.people.process.AbstractPplProcess;
import it.people.process.GenericProcess;
import it.people.vsl.PipelineData;
import it.people.vsl.PipelineDataImpl;
import it.people.vsl.exception.SendException;
import it.people.wrappers.IRequestWrapper;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 */
public class Invocazione extends Step {
	public void defineControl(AbstractPplProcess process, IRequestWrapper request) {
		IValidationErrors errors = process.getValidationErrors();				
		ProcessData processData = (ProcessData)process.getData();
		
		try {
			// Invoca il back-end			
			String message = process.callService("SUBMIT_PROCESS", getMessage(processData));
			processData.setRispostaWebService(message);
		} catch(SendException ex) {
			process.error("Errore nell'invocazione del back-end");
			errors.add("error.webService");
		} catch(ServiceException ex) {
			process.error("Errore nell'invocazione del back-end");
			errors.add("error.webService");
		}
		
		try {
			// Cambia lo stato del procedimento a submitted
			
			((GenericProcess) process).setAsSubmitted();
		} catch(peopleException ex) {
			errors.add("error.changeState");
			process.error("Errore nel cambiamento dello stato in submitted:\n " + ex.toString());
		}
		
	}
	
	protected String getMessage(ProcessData processData) {
		PipelineData data = new PipelineDataImpl();
		processData.exportToPipeline(data);
		return (String) data.getAttribute(PipelineDataImpl.XML_PROCESSDATA_PARAMNAME);
	}

}
