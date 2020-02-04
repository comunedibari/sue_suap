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
 * Created on 20-feb-2006
 *
 */
package it.people.fsl.servizi.esempi.tutorial.demo.steps;

import it.people.IValidationErrors;
import it.people.Step;
import it.people.fsl.servizi.esempi.tutorial.demo.model.ProcessData;
import it.people.fsl.servizi.esempi.tutorial.demo.model.Utente;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.wrappers.IRequestWrapper;

import java.io.IOException;

import javax.servlet.ServletException;

/**
 * @author FabMi
 *
 */
public class ValidaElencoSemplice extends Step {
	public void loopBack(AbstractPplProcess process, IRequestWrapper request,
			String propertyName, int index) throws IOException,
			ServletException {

		if (request.getPplUser().isAnonymous())
			System.out.println("Utente anonimo");		
		
		System.out.println("property = '" + propertyName + "'");
		System.out.println("index = '" + index + "'");		
		
		if (propertyName.equalsIgnoreCase("utenti")) {
			ProcessData data = (ProcessData) process.getData(); 
			Utente utente = (Utente) data.getUtenti().get(index);
			utente.setNome(utente.getNome().toUpperCase());
			utente.setCognome(utente.getCognome().toUpperCase());
		}
	}
	
	
	/* (non-Javadoc)
	 * @see it.people.IStep#logicalValidate(it.people.process.AbstractPplProcess, it.people.wrappers.IRequestWrapper, it.people.IValidationErrors)
	 */
	public boolean logicalValidate(AbstractPplProcess process,
			IRequestWrapper request, IValidationErrors errors)
			throws ParserException {

		if (request.getPplUser().isAnonymous())
			System.out.println("Utente anonimo");
		
		return true;
	}

	/* (non-Javadoc)
	 * @see it.people.IStep#service(it.people.process.AbstractPplProcess, it.people.wrappers.IRequestWrapper)
	 */
	public void service(AbstractPplProcess process, IRequestWrapper request)
			throws IOException, ServletException {
		
		if (request.getPplUser().isAnonymous())
			System.out.println("Utente anonimo");
		
	}
	
    public void defineControl(AbstractPplProcess process, IRequestWrapper request) {
        try {
            process.sendMail("People - Prova messaggio", "Messaggio di prova inviato dal framework People per il servizio di demo.");
        } catch(Exception ex) {
            process.warn("Errore nell'invio del messaggio.\n" + ex.getMessage());
        }
    }
}
