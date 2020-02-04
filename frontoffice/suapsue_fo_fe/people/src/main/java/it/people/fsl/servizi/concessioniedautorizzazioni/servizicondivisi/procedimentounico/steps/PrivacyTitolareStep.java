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
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoUnicoException;
import it.people.fsl.servizi.oggetticondivisi.profili.ProfiloPersonaFisica;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.wrappers.IRequestWrapper;

public class PrivacyTitolareStep extends BaseStep{

	public void service(AbstractPplProcess process, IRequestWrapper request) {
        try {
//            if(initialise(process, request)) {
//                ProcessData dataForm = (ProcessData)process.getData();
//                boolean isIntermediazione = false;
//                if(!process.getContext().getUser().isAnonymous()) {
//                    ProfiloPersonaFisica operatore = dataForm.getProfiloOperatore();
//                    ProfiloPersonaFisica titolarePagamento = dataForm.getTitolarePagamento();
//                    if(titolarePagamento != null) {
//                        isIntermediazione = !operatore.getCodiceFiscale().trim().equalsIgnoreCase(titolarePagamento.getCodiceFiscale().trim());
//                    } else {
//                        isIntermediazione = !operatore.getCodiceFiscale().trim().equalsIgnoreCase(dataForm.getProfiloRichiedente().getCodiceFiscale().trim());
//                    }
//                }
//                dataForm.setShowInformativaPrivacyTitolare(isIntermediazione);
//                if(!isIntermediazione) {
//                    setStep(process, request, 3, 1);
//                }
//            } else {
//                throw new ProcedimentoUnicoException("Sessione scaduta");
//            }
//            if(logger.isDebugEnabled()) {
//                logger.debug("PrivacyTitolareStep - service method END");
//            }
        	
            setStep(process, request, 3, 1);        	
        	
        } catch(Exception e) {
            gestioneEccezioni(process, 0, e);
        }
                
	}
	
	public boolean logicalValidate(AbstractPplProcess process,IRequestWrapper request,IValidationErrors errors) throws ParserException {
		try {
			logger.debug("PrivacyTitolareStep - logicalValidate method");
			ProcessData data = (ProcessData) process.getData();
			
	        if(!data.isShowInformativaPrivacyTitolare()){
	            errors.add("error.accettazioneprivacy");
	            logger.debug("PrivacyTitolareStep - logicalValidate method END");
	            return false;
	        }
	        logger.debug("PrivacyTitolareStep - logicalValidate method END");
			return true;		
		} catch (Exception e) {
			errors.add("error.generic","Errore interno");
			gestioneEccezioni(process,0,e);
			return false;
		}
	}
}
