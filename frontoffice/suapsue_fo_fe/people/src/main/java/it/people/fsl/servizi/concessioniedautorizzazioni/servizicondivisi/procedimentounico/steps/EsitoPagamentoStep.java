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
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.RiepilogoOneriPagati;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.Bean2XML;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.Costant;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.util.payment.EsitoPagamento;
import it.people.wrappers.IRequestWrapper;

import java.io.IOException;

import javax.servlet.ServletException;

public class EsitoPagamentoStep extends BaseStep {

	public void service(AbstractPplProcess process, IRequestWrapper request) {
		try {
			if (initialise(process,request)) {
				logger.debug("EsitoPagamentoStep - service method");
				ProcessData dataForm = (ProcessData) process.getData();
				resetError(dataForm);
				
	            EsitoPagamento esitoPag = (EsitoPagamento) request.getSessionAttribute("EsitoPagamento");
	            if (((dataForm.getEsitoPagamento()==null) || (dataForm.getEsitoPagamento().getIDTransazione().equalsIgnoreCase(""))) && (esitoPag!=null)) {
	            		dataForm.setEsitoPagamento(new EsitoPagamento(esitoPag.marshall()));
	                    if(dataForm.getEsitoPagamento() != null && dataForm.getEsitoPagamento().getDatiSpecifici() != null)
	                        dataForm.setRiepilogoOneriPagati((RiepilogoOneriPagati)Bean2XML.unmarshallObject(dataForm.getEsitoPagamento().getDatiSpecifici(), RiepilogoOneriPagati.class, request.getUnwrappedRequest().getCharacterEncoding()));
	                    else
	                        dataForm.setRiepilogoOneriPagati(new RiepilogoOneriPagati());
	            } else {
	            	esitoPag = dataForm.getEsitoPagamento();
	            }

	            dataForm.getDatiTemporanei().setTotalePagato(dataForm.getEsitoPagamento().getImportoPagato()/100.0);
	            dataForm.getDatiTemporanei().setTotalePagatoCommissioni(dataForm.getEsitoPagamento().getImportoCommissioni()/100.0);
	            
	            if (esitoPag != null
	                && esitoPag.getEsito() != null
	                && esitoPag.getEsito().equalsIgnoreCase(EsitoPagamento.ES_PAGAMENTO_OK)) {
	                dataForm.getDatiTemporanei().setPagamentoEffettuato(true);
	                dataForm.getDatiTemporanei().setModalitaPagamento(Costant.MODALITA_PAGAMENTO_ON_LINE);
	            }
				
				
				
			} else {
				throw new ProcedimentoUnicoException("Sessione scaduta");
			}
			logger.debug("EsitoPagamentoStep - service method END");
        } catch (Exception e) {
        	gestioneEccezioni(process,5,e);
        }
	}
	
	
	public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException,ServletException {
		try {
			logger.debug("EsitoPagamentoStep - loopBack method");

			
			
			logger.debug("EsitoPagamentoStep - loopBack method END");
		} catch (Exception e) {
			gestioneEccezioni(process,5,e);
		}
	}
	
	public boolean logicalValidate(AbstractPplProcess process,IRequestWrapper request,IValidationErrors errors) throws ParserException {		
		ProcessData dataForm = (ProcessData) process.getData();
		try {
			logger.debug("EsitoPagamentoStep - logicalValidate method");
			
			
			
			logger.debug("EsitoPagamentoStep - logicalValidate method END");
			return true;
		} catch (Exception e) {
			errors.add("error.generic","Errore interno");
        	gestioneEccezioni(process,5,e);
        	dataForm.setInternalError(true);
        	return false;
        }
	}
	
	
}
