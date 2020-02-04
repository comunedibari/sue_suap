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
 * WSTest.java
 *
 * Created on 27 dicembre 2004, 16.49
 */

package it.people.fsl.servizi.esempi.tutorial.serviziotutorial1.steps;

import it.people.Step;
import it.people.core.exception.ServiceException;
import it.people.fsl.servizi.esempi.tutorial.serviziotutorial1.model.ProcessData;
import it.people.fsl.servizi.esempi.tutorial.serviziotutorial1.oggetti.UtenteDocument;
import it.people.fsl.servizi.esempi.tutorial.serviziotutorial1.util.Constants;
import it.people.process.AbstractPplProcess;
import it.people.util.XmlObjectWrapper;
import it.people.vsl.exception.SendException;
import it.people.wrappers.IRequestWrapper;

import java.io.IOException;

import javax.servlet.ServletException;

/**
 *
 * @author zero
 */
public class WSTest extends Step {
    
    /**
     * Gestisce la chiamata al WebService di Backend
     */
    public void service(AbstractPplProcess process, IRequestWrapper request)
    throws IOException, ServletException {
        
        String beServiceErrorMessage = "Errore nella risposta del Back-end, ritornare nello step in un secondo momento";
        String genericErrorMessage = "Errore generico, ritornare nello step in un secondo momento";
        
        ProcessData myData = (ProcessData) process.getData();

        try {          
            String idOperazione = process.getNewIdentificativoOperazione();
            UtenteDocument ud = UtenteDocument.Factory.newInstance();            
            UtenteDocument.Utente utente = ud.addNewUtente();
            utente.setOperationId(idOperazione);
            utente.setNome(myData.getNome());
            utente.setCognome(myData.getCognome());
            
            String xmlData = XmlObjectWrapper.generateXml(ud);            
            myData.setRispostaWS("");
            String ret = process.callService(Constants.WEBSERVICE_NAME, xmlData);
            
            if (ret == null)
                throw new SendException("Risposta nulla");
            
            myData.setRispostaWS(ret);

        } catch (ServiceException ex) {
            // registra nel log l'errore
            process.error(ex.getMessage());
            myData.setRispostaWS(beServiceErrorMessage);
        } catch (SendException ex) {
            // registra nel log l'errore
            process.error(ex.getMessage());
            myData.setRispostaWS(beServiceErrorMessage);
        } catch (Exception ex) {
            // registra nel log l'errore
            process.error(ex.getMessage());
            myData.setRispostaWS(genericErrorMessage);
        }
    }
}
