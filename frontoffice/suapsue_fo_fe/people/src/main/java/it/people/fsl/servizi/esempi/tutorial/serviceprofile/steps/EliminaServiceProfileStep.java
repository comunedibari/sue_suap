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
 * Created on 20-dic-2005
 *
 */
package it.people.fsl.servizi.esempi.tutorial.serviceprofile.steps;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;

import it.people.IValidationErrors;
import it.people.Step;
import it.people.core.ServiceProfileStoreManager;
import it.people.fsl.servizi.esempi.tutorial.serviceprofile.model.ProcessData;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.wrappers.IRequestWrapper;

/**
 * @author FabMi
 *
 */
public class EliminaServiceProfileStep extends Step {
	
	public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) 
		throws IOException, ServletException {		
		printQueryParameter("loopback", request);
		deleteServiceProfileXML(process);
	}
	
	public void service(AbstractPplProcess process, IRequestWrapper request)
		throws IOException, ServletException {
		printQueryParameter("service", request);
	}
		
	public boolean logicalValidate(AbstractPplProcess process,
			IRequestWrapper request, IValidationErrors errors)
			throws ParserException {
		printQueryParameter("logicalValidate", request);
		return true;
	}
	
	/**
	 * Crea l'xml per un service profile
	 * @return il service profile serializzato in xml
	 */
	protected void deleteServiceProfileXML(AbstractPplProcess process) {
		ProcessData data = (ProcessData)process.getData();
		
		// Crea il manager per la gestione del ServiceProfileStore
		// il ServiceProfileStore � l'oggetto contenente la serializzazione
		// del service profile su DB.
		ServiceProfileStoreManager spsManager = ServiceProfileStoreManager.getInstance();
		spsManager.delete(data.getServiceName(), data.getBookmarkId());
	}
	
	protected void printQueryParameter(String intestazione, IRequestWrapper request) {
		System.out.println("--------------------------------------------");
		System.out.println(intestazione);
		System.out.println("Parametri ricevuti:");
		Enumeration parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String name = (String) parameterNames.nextElement();
			String value = (String) request.getParameter(name);
			System.out.print("name = '" + name + "'");
			System.out.println(" value = '" + value + "'");
		}		
	}
}
