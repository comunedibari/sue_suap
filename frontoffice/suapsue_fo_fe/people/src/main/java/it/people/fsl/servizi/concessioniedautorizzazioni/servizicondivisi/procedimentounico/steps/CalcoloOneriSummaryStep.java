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
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;

import it.people.IValidationErrors;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.ProcedimentoUnicoDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.BeanDescriptionComparator;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OneriBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoUnicoException;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.RiepilogoOneri;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.Costant;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.process.SurfDirection;
import it.people.wrappers.IRequestWrapper;

public class CalcoloOneriSummaryStep extends BaseStep {
	
	public void service(AbstractPplProcess process, IRequestWrapper request) {
		try {
			if (initialise(process,request)) {
				logger.debug("CalcoloOneriSummaryStep - service method");
				checkRecoveryBookmark(process, request);
				ProcessData dataForm = (ProcessData) process.getData();
				resetError(dataForm);
				String clickButtonSave = (String) request.getAttribute("navigation.button.save");
				if ( (getSurfDirection(process)==SurfDirection.forward) && (clickButtonSave==null) ){
					if (!dataForm.isOneriAnticipatiPresent()){
						dataForm.setAttestatoPagamentoObbligatorio(false);
						dataForm.setEsitoPagamento(null);
						setStep(process, request, getCurrentActivityIndex(process)+1, 0);
						return;
					} else {
						ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
		                delegate.calcolaOneri(dataForm.getOneriAnticipati());
		                RiepilogoOneri oneri = calcolaTotale(dataForm.getOneriAnticipati());
		                session.setAttribute("riepilogoOneri", oneri);
		                dataForm.setRiepilogoOneri(oneri);
		                boolean forzaPagamento = dataForm.getTipoPagamentoBookmark().equalsIgnoreCase(Costant.forzaPagamentoLabel);
		                boolean pagamentoOpzionale = dataForm.getTipoPagamentoBookmark().equalsIgnoreCase(Costant.pagamentoOpzionaleLabel);
		                if (forzaPagamento /*|| pagamentoOpzionale*/) {
		                	if (oneri.getTotale()==0) {
								dataForm.setEsitoPagamento(null);
		                		dataForm.setAttivaPagamenti(false);
								dataForm.setAttestatoPagamentoObbligatorio(false);
		                	} else {
		                		dataForm.setAttivaPagamenti(!dataForm.isAttestatoPagamentoObbligatorio());
		                	}
		                } else if (pagamentoOpzionale) {
		                	if (oneri.getTotale()==0 && dataForm.isAttivaPagamenti()) {
								dataForm.setEsitoPagamento(null);
		                		dataForm.setAttivaPagamenti(false);
								dataForm.setAttestatoPagamentoObbligatorio(false);
		                	}
		                }
		                
		                
					}
				} else {
					if (!dataForm.isOneriAnticipatiPresent()){
						dataForm.setEsitoPagamento(null);
						dataForm.setAttestatoPagamentoObbligatorio(false);
						setStep(process, request, getCurrentActivityIndex(process), getCurrentStepIndex(process)-1 );
						return;
					} else {
						session.setAttribute("riepilogoOneri", dataForm.getRiepilogoOneri());
					}
				}
				
			} else {
				throw new ProcedimentoUnicoException("Sessione scaduta");
			}
			logger.debug("CalcoloOneriSummaryStep - service method END");
        } catch (Exception e) {
        	gestioneEccezioni(process,5,e);
        }
	}
	
	
	public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException,ServletException {
		try {
			logger.debug("CalcoloOneriSummaryStep - loopBack method");

			if(propertyName.equalsIgnoreCase("bookmark")){
	        	gestioneBookmark(process, request);
			}
			
			logger.debug("CalcoloOneriSummaryStep - loopBack method END");
		} catch (Exception e) {
			gestioneEccezioni(process,5,e);
		}
	}
	
	public boolean logicalValidate(AbstractPplProcess process,IRequestWrapper request,IValidationErrors errors) throws ParserException {		
		ProcessData dataForm = (ProcessData) process.getData();
		try {
			logger.debug("CalcoloOneriSummaryStep - logicalValidate method");
			
			
			
			logger.debug("CalcoloOneriSummaryStep - logicalValidate method END");
			return true;
		} catch (Exception e) {
			errors.add("error.generic","Errore interno");
        	gestioneEccezioni(process,5,e);
        	dataForm.setInternalError(true);
        	return false;
        }
	}
	
    public RiepilogoOneri calcolaTotale(Set oneriAnticipati) {
        TreeSet padriSet = new TreeSet(new BeanDescriptionComparator());
        RiepilogoOneri answer = new RiepilogoOneri();

        //

        Iterator oneriIt = oneriAnticipati.iterator();

        String temp = "";
        boolean firstLap = false;
        double importo = 0;
        double totale = 0;
        while (oneriIt.hasNext()) {
            OneriBean bb = new OneriBean();
            OneriBean arBean = (OneriBean) oneriIt.next();

            bb.setDescrizione(arBean.getDescrizioneAntenato());
            padriSet.add(bb);
            totale += arBean.getImporto();
        }

        Iterator it = padriSet.iterator();
        while (it.hasNext()) {
            OneriBean beanPadre = (OneriBean) it.next();
            String padre = beanPadre.getDescrizione();
            importo = 0;
            oneriIt = oneriAnticipati.iterator();
            while (oneriIt.hasNext()) {
                OneriBean arBean = (OneriBean) oneriIt.next();
                if (padre.equalsIgnoreCase(arBean.getDescrizioneAntenato())) {
                    importo += arBean.getImporto();
                }
            }
            beanPadre.setImporto(importo);
            answer.getOneriBean().add(beanPadre);
        }

        answer.setTotale(totale);
        return answer;
    }

}
