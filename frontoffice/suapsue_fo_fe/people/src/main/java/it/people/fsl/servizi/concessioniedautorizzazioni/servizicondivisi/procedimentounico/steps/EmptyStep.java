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
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.steps;

import java.io.IOException;

import javax.servlet.ServletException;

import it.people.IValidationErrors;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoUnicoException;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.wrappers.IRequestWrapper;

public class EmptyStep extends BaseStep {
	
	public void service(AbstractPplProcess process, IRequestWrapper request) {
		try {
			if (initialise(process,request)) {
				logger.debug("EmptyStep - service method");
				ProcessData dataForm = (ProcessData) process.getData();
				resetError(dataForm);
				
				
				
			} else {
				throw new ProcedimentoUnicoException("Sessione scaduta");
			}
			logger.debug("EmptyStep - service method END");
        } catch (Exception e) {
        	gestioneEccezioni(process,5,e);
        }
	}
	
	
	public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException,ServletException {
		try {
			logger.debug("EmptyStep - loopBack method");

			
			
			logger.debug("EmptyStep - loopBack method END");
		} catch (Exception e) {
			gestioneEccezioni(process,5,e);
		}
	}
	
	public boolean logicalValidate(AbstractPplProcess process,IRequestWrapper request,IValidationErrors errors) throws ParserException {		
		ProcessData dataForm = (ProcessData) process.getData();
		try {
			logger.debug("EmptyStep - logicalValidate method");
			
			
			
			logger.debug("EmptyStep - logicalValidate method END");
			return true;
		} catch (Exception e) {
        	gestioneEccezioni(process,5,e);
        	dataForm.setInternalError(true);
        	return false;
        }
	}

}
