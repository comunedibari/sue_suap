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
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.gruppoinit.commons.DBCPManager;
import it.people.IActivity;
import it.people.IStep;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.ProcedimentoUnicoDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ComuneBean;
import it.people.process.AbstractPplProcess;
import it.people.util.ServiceParameters;
import it.people.wrappers.IRequestWrapper;

public class ManagerWorkflow {

    private Log log = LogFactory.getLog(this.getClass());

    protected void showJsp(AbstractPplProcess process, String path, boolean completePath) {
        String htmlPath = "/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/view/default/html/";
        process.getView().getCurrentActivity().getCurrentStep().setJspPath((completePath ? "" : htmlPath) + path);
        log.debug("you are going here --> " + process.getView().getCurrentActivity().getCurrentStep().getJspPath());
    }

    public void setStep(AbstractPplProcess pplProcess, int index) {
        pplProcess.getView().getCurrentActivity().setCurrentStepIndex(index);
    }

    public void updateWorkflow1(HttpSession session, IRequestWrapper request, AbstractPplProcess process, ProcessData dataForm, DBCPManager db, String language) {
        try {
            ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
            String byPassStepPrivacyString = params.get("byPassStepPrivacy");
            boolean byPassStepPrivacy = false;
            if (byPassStepPrivacyString != null && byPassStepPrivacyString.equalsIgnoreCase("true")) {
                byPassStepPrivacy = true;
                IActivity firstActivity = process.getView().getActivityById("0");
                IStep s = firstActivity.getStepById("1");
                s.service(process, request);
                firstActivity.getStepList().remove(0);
                String[] stepOrder = firstActivity.getStepOrder();
                String newStepOrder = "";
                for (int i = 0; i < stepOrder.length; i++) {
                    if (i != 0) {
                        if (i == (stepOrder.length - 1)) {
                            newStepOrder += stepOrder[i];
                        } else {
                            newStepOrder += stepOrder[i] + ",";
                        }
                    }
                }
                firstActivity.setStepOrder(newStepOrder);
            }
            // String byPassStepSceltaComuneString = params.get("byPassStepSceltaComune");
            if (((String) request.getParameter("codEnte") != null) || (dataForm.getComuneSelezionato() != null && dataForm.getComuneSelezionato().getCodEnte() != null && !dataForm.getComuneSelezionato().getCodEnte().equalsIgnoreCase(""))
                    || (session.getAttribute("byPassStepSceltaComune") != null)) {
                ComuneBean comuneSelezionato = null;
                if ((String) request.getParameter("codEnte") != null || session.getAttribute("byPassStepSceltaComune") != null) {
                    ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
                    if (session.getAttribute("byPassStepSceltaComune") != null) {
                        comuneSelezionato = delegate.getDettaglioComune((String) session.getAttribute("byPassStepSceltaComune"));
                    } else {
                        comuneSelezionato = delegate.getDettaglioComune((String) request.getParameter("codEnte"));
                        session.setAttribute("byPassStepSceltaComune", (String) request.getParameter("codEnte"));
                    }
                } else {
                    comuneSelezionato = dataForm.getComuneSelezionato();
                }
                if (comuneSelezionato != null) {
                    dataForm.setComuneSelezionato(comuneSelezionato);
                    request.setAttribute("forward", "true");
                    IActivity firstActivity = process.getView().getActivityById("0");
                    if (byPassStepPrivacy == true) {
                        IStep s = firstActivity.getStepById("2");
                        s.service(process, request);
                        firstActivity.getStepList().remove(0);
                        String[] stepOrder = firstActivity.getStepOrder();
                        String newStepOrder = "";
                        for (int i = 0; i < stepOrder.length; i++) {
                            if (i != 0) {
                                if (i == (stepOrder.length - 1)) {
                                    newStepOrder += stepOrder[i];
                                } else {
                                    newStepOrder += stepOrder[i] + ",";
                                }
                            }
                        }
                        firstActivity.setStepOrder(newStepOrder);
                    } else {
                    	// CCD - Modifica 10.02.2012 - Inserita l'eliminazione dello step di scelta comune solo se presente.
                    	// Prima veniva rimosso l'elemento 1 dell'array, ma non è detto che corrisponda allo step di 
                    	// selezione del comune
                		int stepSceltaComuneIndex = 0;
                    	if (firstActivity.getStepById("1") != null) {
                    		for (int index = 0; index < firstActivity.getStepList().size(); index++) {
                    			IStep step = (IStep)firstActivity.getStepList().get(index);
                    			if (step.getId().equalsIgnoreCase("1")) {
                    				stepSceltaComuneIndex = index;
                    				break;
                    			}
                    		}
                    	}
//                    	IStep removedStep = (IStep)firstActivity.getStepList().get(1);
//                    	String removedStepId = removedStep.getId();
                    	if (session.getAttribute("byPassStepSceltaComune") != null && stepSceltaComuneIndex > 0) {
                    		firstActivity.getStepList().remove(stepSceltaComuneIndex);
                    	}
//                    	 && !stepOrder[i].equalsIgnoreCase(removedStepId)
                        String[] stepOrder = firstActivity.getStepOrder();
                        String newStepOrder = "";
                        for (int i = 0; i < stepOrder.length; i++) {
                            if (i != 1) {
                                if (i == (stepOrder.length - 1)) {
                                    newStepOrder += stepOrder[i];
                                } else {
                                    newStepOrder += stepOrder[i] + ",";
                                }
                            }
                        }
                        firstActivity.setStepOrder(newStepOrder);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    public void updateWorkflowOLD(HttpSession session, IRequestWrapper request, AbstractPplProcess process, ProcessData dataForm, DBCPManager db, String language) {
        try {
            ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
            String byPassStepPrivacyString = params.get("byPassStepPrivacy");
            boolean byPassStepPrivacy = false;
            if (byPassStepPrivacyString != null && byPassStepPrivacyString.equalsIgnoreCase("true")) {
                byPassStepPrivacy = true;
                IActivity firstActivity = process.getView().getActivityById("0");
                //firstActivity.setCurrentStepIndex(1);
                //firstActivity.setId("1");
                firstActivity.getStepList().remove(0);
                String[] stepOrder = firstActivity.getStepOrder();
                String newStepOrder = "";
                for (int i = 0; i < stepOrder.length; i++) {
                    if (i != 0) {
                        if (i == (stepOrder.length - 1)) {
                            newStepOrder += stepOrder[i];
                        } else {
                            newStepOrder += stepOrder[i] + ",";
                        }
                    }
                }
                firstActivity.setStepOrder(newStepOrder);
                IStep s = firstActivity.getStepById("1");
                s.service(process, request);
                // setStep(process, 1);
                showJsp(process, "sceltaComune.jsp", false);
                firstActivity.setCurrentStepIndex(0);
                firstActivity.setId("1");
                int i = 0;


//				IStep s0 = firstActivity.getStepById("0");
//				s0.setState(it.people.StepState.INACTIVE);
//				IStep s = firstActivity.getStepById("1");
//				s.service(process, request);
//				setStep(process, 1);
//				showJsp(process, "sceltaComune.jsp", false);
//				firstActivity.getStepList().remove(0);
//				String[] stepOrder = firstActivity.getStepOrder();
//				String newStepOrder = "";
//	            for (int i = 0; i < stepOrder.length; i++) {
//		        	if (i!=0){
//			            if (i == (stepOrder.length - 1)) {
//			                newStepOrder += stepOrder[i];
//			            } else {
//			                newStepOrder += stepOrder[i] + ",";
//			            }
//		        	}
//	        	}
//	            firstActivity.setStepOrder(newStepOrder);
            }
// PC - accesso su comune
//          String byPassStepSceltaComuneString = params.get("byPassStepSceltaComune");
//	    if (byPassStepSceltaComuneString!=null && byPassStepSceltaComuneString.equalsIgnoreCase("true") && ((String)request.getParameter("codEnte")!=null)){
            if ((String) request.getParameter("codEnte") != null) {
// PC - accesso su comune
                ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
                ComuneBean comuneSelezionato = delegate.getDettaglioComune((String) request.getParameter("codEnte"));
                if (comuneSelezionato != null) {
// PC - accesso su comune
                    session.setAttribute("byPassStepSceltaComune", (String) request.getParameter("codEnte"));
// PC - accesso su comune
                    dataForm.setComuneSelezionato(comuneSelezionato);
                    request.setAttribute("forward", "true");
                    IActivity firstActivity = process.getView().getActivityById("0");
                    if (byPassStepPrivacy == true) {
                        IStep s = firstActivity.getStepById("2");
                        s.service(process, request);
                        firstActivity.getStepList().remove(0);
                        String[] stepOrder = firstActivity.getStepOrder();
                        String newStepOrder = "";
                        for (int i = 0; i < stepOrder.length; i++) {
                            if (i != 0) {
                                if (i == (stepOrder.length - 1)) {
                                    newStepOrder += stepOrder[i];
                                } else {
                                    newStepOrder += stepOrder[i] + ",";
                                }
                            }
                        }
                        firstActivity.setStepOrder(newStepOrder);
                    } else {
                        firstActivity.getStepList().remove(1);
                        String[] stepOrder = firstActivity.getStepOrder();
                        String newStepOrder = "";
                        for (int i = 0; i < stepOrder.length; i++) {
                            if (i != 1) {
                                if (i == (stepOrder.length - 1)) {
                                    newStepOrder += stepOrder[i];
                                } else {
                                    newStepOrder += stepOrder[i] + ",";
                                }
                            }
                        }
                        firstActivity.setStepOrder(newStepOrder);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }
}
