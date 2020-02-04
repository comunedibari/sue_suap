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
 * formTest.java
 *
 * Created on 26 dicembre 2004, 14.46
 */

package it.people.fsl.servizi.esempi.tutorial.serviziotutorial1.steps;

import it.people.IValidationErrors;
import it.people.Step;
import it.people.core.PplUserData;
import it.people.fsl.servizi.esempi.tutorial.serviziotutorial1.model.Donna;
import it.people.fsl.servizi.esempi.tutorial.serviziotutorial1.model.ProcessData;
import it.people.fsl.servizi.esempi.tutorial.serviziotutorial1.model.Uomo;
import it.people.fsl.servizi.oggetticondivisi.tipibase.Data;
import it.people.logger.Logger;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.wrappers.IRequestWrapper;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletException;

/**
 *
 * @author zero
 */
public class Allegati extends Step {
    
    /** 
     * Valida la consistenza LOGICA dei parametri inseriti. La validazione
     * Stretta del formato dei parametri � definibile tramite il validation.xml
     * di struts (si faccia riferimento alla Guida allo sviluppo di un servizio).
     */
    public boolean logicalValidate(AbstractPplProcess process,
            IRequestWrapper request,
            IValidationErrors errors) throws ParserException {
        process.debug("Allegati - invocato logicalValidate(AbstractPplProcess process, IRequestWrapper request, IValidationErrors errors)");
        List allegati = ((ProcessData)process.getData()).getAllegati();
        System.out.println("Allegati - logicalValidate(...) - numero allegati = " + allegati.size());
        return true;
    }   
    
    
	/* (non-Javadoc)
	 * @see it.people.IStep#service(it.people.process.AbstractPplProcess, it.people.wrappers.IRequestWrapper)
	 */
	public void service(AbstractPplProcess process, IRequestWrapper request)
			throws IOException, ServletException {	
        process.debug("Allegati - invocato service(AbstractPplProcess process, IRequestWrapper request)");	    
	}

    public void defineControl(AbstractPplProcess process, IRequestWrapper request) {
        process.debug("Allegati - invocato defineControl(AbstractPplProcess process, IRequestWrapper request)");
    }
}
    
