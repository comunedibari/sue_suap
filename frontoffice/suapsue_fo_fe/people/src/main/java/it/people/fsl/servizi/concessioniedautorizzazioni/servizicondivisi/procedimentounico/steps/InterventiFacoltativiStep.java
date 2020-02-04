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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;

import it.people.IValidationErrors;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.ProcedimentoUnicoDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.BaseBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.InterventoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OperazioneBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoUnicoException;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.process.SurfDirection;
import it.people.wrappers.IRequestWrapper;

public class InterventiFacoltativiStep extends BaseStep {
	
	public void service(AbstractPplProcess process, IRequestWrapper request) {
		try {
			if (initialise(process,request)) {
				logger.debug("InterventiFacoltativiStep - service method");
				checkRecoveryBookmark(process, request);
				ProcessData dataForm = (ProcessData) process.getData();
				resetError(dataForm);
				
				String clickButtonSave = (String) request.getAttribute("navigation.button.save");
				if ( (getSurfDirection(process)==SurfDirection.forward) && (clickButtonSave==null) ){
					String method = (String)request.getParameter("method");
					if (method==null || (method!=null && !method.equalsIgnoreCase("Annulla"))){
// PC - Reiterazione domanda inizio                                             
//						dataForm.setInterventiFacoltativi(new ArrayList());
// PC - Reiterazione domanda fine                                                
					}
				}
				ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
// PC - Reiterazione domanda inizio             
//				ArrayList interventiFacoltativi = (ArrayList) delegate.getInterventiFacoltativi(dataForm.getComuneSelezionato().getCodEnte(),dataForm.getInterventi());
                                ArrayList interventiFacoltativi = (ArrayList) delegate.getInterventiFacoltativi(dataForm.getComuneSelezionato().getCodEnte(),dataForm.getInterventi(),dataForm);

//				if (dataForm.getInterventiFacoltativi()!=null && dataForm.getInterventiFacoltativi().size()>0){
//					for (Iterator iterator = dataForm.getInterventiFacoltativi().iterator(); iterator.hasNext();) {
//						InterventoBean intervFacSel = (InterventoBean) iterator.next();
//						for (Iterator iterator2 = interventiFacoltativi.iterator(); iterator2.hasNext();) {
//							InterventoBean intFac = (InterventoBean) iterator2.next();
//							if (intervFacSel.getCodice().equalsIgnoreCase(intFac.getCodice())){
//								intFac.setChecked(true);
//							}
//						}
//					}
//				}
				//dataForm.setInterventiFacoltativi(interventiFacoltativi);
                                dataForm.setInterventiFacoltativi(interventiFacoltativi);
// PC - Reiterazione domanda fine  				
				if (interventiFacoltativi==null || interventiFacoltativi.size()==0){
					if (getSurfDirection(process)==SurfDirection.forward){
						goToStep(process, request, 1);
						logger.debug("InterventiFacoltativiStep - service method END");
						return;
					} else if (getSurfDirection(process)==SurfDirection.backward){
						goToStep(process, request, -1);
						logger.debug("InterventiFacoltativiStep - service method END");
						return;
					}
				} else {
					request.setAttribute("interventiFacoltativi", interventiFacoltativi);
				}
			} else {
				throw new ProcedimentoUnicoException("Sessione scaduta");
			}
			logger.debug("InterventiFacoltativiStep - service method END");
        } catch (Exception e) {
        	gestioneEccezioni(process,4,e);
        }
	}				


	public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException,ServletException {
		logger.debug("InterventiFacoltativiStep - loopBack method");
		try {
			ProcessData dataForm = (ProcessData) process.getData();
			if(propertyName == null){
                propertyName = request.getParameter("pagina");
			}
			if(propertyName.equalsIgnoreCase("bookmark")){
				gestioneBookmark(process, request);
			} else if(propertyName.equalsIgnoreCase("normativa.jsp")) {

				String idx = request.getParameter("index");
				//enableBottomNavigationBar(process, false);
                logger.debug(process.getView().getCurrentActivity().getCurrentStep().getJspPath());
                //String codRif = request.getParameter("codRif");
                ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
                request.setAttribute("listaDocumenti", delegate.getDocumentiNormative(String.valueOf(idx)));
                request.setAttribute("IF", "true");
                showJsp(process, propertyName, false);
			} else if(propertyName.equalsIgnoreCase("interventiFacoltativi.jsp")) {
				ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);

// PC - Reiterazione domanda inizio             
//				ArrayList interventiFacoltativi = (ArrayList) delegate.getInterventiFacoltativi(dataForm.getComuneSelezionato().getCodEnte(),dataForm.getInterventi());
                                ArrayList interventiFacoltativi = (ArrayList) delegate.getInterventiFacoltativi(dataForm.getComuneSelezionato().getCodEnte(),dataForm.getInterventi(),dataForm);
// PC - Reiterazione domanda fine                                 
				if (dataForm.getInterventiFacoltativi()!=null && dataForm.getInterventiFacoltativi().size()>0){
					for (Iterator iterator = dataForm.getInterventiFacoltativi().iterator(); iterator.hasNext();) {
						InterventoBean intervFacSel = (InterventoBean) iterator.next();
						for (Iterator iterator2 = interventiFacoltativi.iterator(); iterator2.hasNext();) {
							InterventoBean intFac = (InterventoBean) iterator2.next();
							if (intervFacSel.getCodice().equalsIgnoreCase(intFac.getCodice())){
								intFac.setChecked(true);
							}
						}
					}
				}
				if (interventiFacoltativi==null || interventiFacoltativi.size()==0){
					if (getSurfDirection(process)==SurfDirection.forward){
						goToStep(process, request, 1);
						logger.debug("InterventiFacoltativiStep - service method END");
						return;
					} else if (getSurfDirection(process)==SurfDirection.backward){
						goToStep(process, request, -1);
						logger.debug("InterventiFacoltativiStep - service method END");
						return;
					}
				} else {
					request.setAttribute("interventiFacoltativi", interventiFacoltativi);
				}
                showJsp(process, propertyName, false);
			} 
			
		} catch (Exception e) {
			gestioneEccezioni(process,2,e);
		}
		logger.debug("InterventiFacoltativiStep - loopBack method END");
	}
	
    public boolean logicalValidate(AbstractPplProcess process, IRequestWrapper request, IValidationErrors errors) throws ParserException {
        ProcessData dataForm = (ProcessData) process.getData();
        doSave(process, request);
        try {
            ArrayList interventiFacoltativiSelezionati = new ArrayList();
            ArrayList codInterventiSel = new ArrayList();
            if (initialise(process, request)) {
                //System.out.println("InterventiFacoltativiStep - logicalValidate");
            }

            ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
// PC - Reiterazione domanda inizio             
//				ArrayList interventiFacoltativi = (ArrayList) delegate.getInterventiFacoltativi(dataForm.getComuneSelezionato().getCodEnte(),dataForm.getInterventi());
            ArrayList interventiFacoltativi = (ArrayList) delegate.getInterventiFacoltativi(dataForm.getComuneSelezionato().getCodEnte(), dataForm.getInterventi(), dataForm);
// PC - Reiterazione domanda fine  
// PC - Reiterazione domanda inizio
//            for (Iterator iterator = interventiFacoltativi.iterator(); iterator.hasNext();) {
//				BaseBean bb = (BaseBean) iterator.next();
//				if (request.getParameter(bb.getCodice()) != null){
//					codInterventiSel.add(bb.getCodice());
//				}
//			}                                
            for (Iterator iterator = interventiFacoltativi.iterator(); iterator.hasNext();) {
                InterventoBean bb = (InterventoBean) iterator.next();
                if (request.getParameter(bb.getCodice()) != null) {
                    bb.setChecked(true);
                    interventiFacoltativiSelezionati.add(bb);
                }
            }
            dataForm.setInterventiFacoltativi(interventiFacoltativiSelezionati);

//            if (codInterventiSel.size()>0){
//            	interventiFacoltativiSelezionati = delegate.getInterventiFacoltativiDettaglio(codInterventiSel);
//            	dataForm.setInterventiFacoltativi(interventiFacoltativiSelezionati);
//            } else {
//            	dataForm.setInterventiFacoltativi(new ArrayList());
//            }
// PC - Reiterazione domanda fine             
            if (interventiFacoltativiSelezionati != null && interventiFacoltativiSelezionati.size() > 0) {
                delegate.calcolaAllegatiPerInterventi(interventiFacoltativiSelezionati, dataForm.getListaAllegati(), dataForm.getComuneSelezionato().getCodEnte());
                delegate.calcolaNormativePerInterventi(dataForm.getComuneSelezionato().getCodEnte(), interventiFacoltativiSelezionati, dataForm.getListaNormative());
                String pythonModule = delegate.calcolaProcedimenti(dataForm, true);
                delegate.calcolaSportelli(pythonModule, dataForm);

            }

            return true;
        } catch (Exception e) {
            errors.add("error.generic", "Errore interno");
            gestioneEccezioni(process, 4, e);
            dataForm.setInternalError(true);
            return false;
        }
    }
}
