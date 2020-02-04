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

import it.people.IValidationErrors;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.wrappers.IRequestWrapper;

import java.io.IOException;

import javax.servlet.ServletException;

public class RiepilogoStepOLD extends BaseStep{

	
	public void service(AbstractPplProcess process, IRequestWrapper request) {
		if (initialise(process,request)) {
			System.out.println("SettingsStep");
		}
		ProcessData dataForm = (ProcessData) process.getData();
		System.out.println("fine SettingsStep (service)");
	}
	
	public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException,
	ServletException {
		System.out.println("loopback method");
	}
	
	public boolean logicalValidate(
			AbstractPplProcess process,
	        IRequestWrapper request,
			IValidationErrors errors)
		throws ParserException {
		
		boolean isLoopback = request.getUnwrappedRequest().getServletPath().equalsIgnoreCase("/loopBackValidate.do");
		
		int i=3;
		if (i==3) {
			errors.add("error.scrivereok");
			return false;
		}
		//ProcessData data = (ProcessData) process.getData();
//		if (!"OK".equals(data.getProva())) {
//			errors.add("error.scrivereok");
//			return false;
//		}
		
		return true;		
	}
	
}
