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

import it.gruppoinit.commons.Utilities;
import it.people.IValidationErrors;
import it.people.backend.client.PeopleException;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.ProcedimentoUnicoDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.BaseBeanRows;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.Input;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OperazioneBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoUnicoException;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean;
import it.people.java.util.LinkedHashMap;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.process.SurfDirection;
import it.people.wrappers.IRequestWrapper;

public class SceltaOperazioniStep extends BaseStep {

	public void service(AbstractPplProcess process, IRequestWrapper request) {
		try {
			if (initialise(process,request)) {
				logger.debug("SceltaOperazioniStep - service method");
				checkRecoveryBookmark(process, request);
				ProcessData dataForm = (ProcessData) process.getData();
				resetError(dataForm);
				String clickButtonSave = (String) request.getAttribute("navigation.button.save");
				if ( (getSurfDirection(process)==SurfDirection.forward) && (clickButtonSave==null) ){
					String method = (String)request.getParameter("method");
					if (method==null || (method!=null && !method.equalsIgnoreCase("Annulla"))){
//						dataForm.setAlberoOperazioni(new ArrayList());
//						dataForm.setFineSceltaOp(false);
					}
				}
// PC - Reiterazione domanda inizio 
// tolto				if (dataForm.getAlberoOperazioni()==null || dataForm.getAlberoOperazioni().size()==0){
                                if (dataForm.getLivelloSceltaOp()==-1){       
// tolto                                        dataForm.setAlberoOperazioni(new ArrayList());                                    
// PC - Reiterazione domanda fine                                     
					dataForm.setLivelloSceltaOp(1);
                                            
					
					
					ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
					ArrayList alberoOperazioni = new ArrayList(); 
					
// PC - Reiterazione domanda inizio					
                                    ArrayList operazioniOld = new ArrayList();
                                    for (Iterator it = dataForm.getAlberoOperazioni().iterator(); it.hasNext();) {
                                        operazioniOld.add(it.next());
                                    }

                                    delegate.calcolaAlberoOperazioni(dataForm.getComuneSelezionato().getCodEnte(), dataForm.getComuneSelezionato().getTipAggregazione(), alberoOperazioni, dataForm.getSettoreScelto().getCodice());
                                    
                                    int index = 0;
                                    for (Iterator it = alberoOperazioni.iterator(); it.hasNext();) {
                                        OperazioneBean o = (OperazioneBean) it.next();
                                        for (Iterator iter = operazioniOld.iterator(); iter.hasNext();) {
                                            OperazioneBean oo = (OperazioneBean) iter.next();
                                            if (oo.getCodiceOperazione().equals(o.getCodiceOperazione())) {
                                                alberoOperazioni.set(index, oo);
                                            }
                                        }
                                        index++;
                                    } 
// PC - Reiterazione domanda fine                                         
					dataForm.setAlberoOperazioni(alberoOperazioni);

					findChildren(dataForm.getAlberoOperazioni());
					
					// se c'è una sola operazione la preseleziono
					int cont=0;
					Iterator itt = alberoOperazioni.iterator();
					OperazioneBean op = null;
					OperazioneBean op2 = null;
					while (itt.hasNext() && cont<2){
						op = (OperazioneBean) itt.next();
						if (op.getProfondita()==1){
							op2 = op;
							cont++;
						}
					}
					if (cont==1){
						op2.setSelezionato(true);
					}
					// fine preseleziono
				} else {
					ArrayList opSelezionate = new ArrayList();
					if (dataForm.isFineSceltaOp()) {
						// ero arrivato alla fine delle scelte
						dataForm.setFineSceltaOp(true);
						opSelezionate = new ArrayList();
						for (Iterator iterator = dataForm.getAlberoOperazioni().iterator(); iterator.hasNext();) {
							OperazioneBean operazione = (OperazioneBean) iterator.next();
							if (operazione.isSelezionato() && (operazione.getListaCodiciFigli()==null || operazione.getListaCodiciFigli().size()==0) ){
								opSelezionate.add(operazione);
							}
						}
						request.setAttribute("listaOperazioni", opSelezionate);
					} else if (dataForm.getLivelloSceltaOp()>1) {
						opSelezionate = new ArrayList();
						for (Iterator iterator = dataForm.getAlberoOperazioni().iterator(); iterator.hasNext();) {
							OperazioneBean operazione = (OperazioneBean) iterator.next();
							if (operazione.isSelezionato() && operazione.getProfondita()==(dataForm.getLivelloSceltaOp()-1) && operazione.getListaCodiciFigli()!=null && operazione.getListaCodiciFigli().size()>0 ){
								BaseBeanRows operazioneBean = new BaseBeanRows();
								operazioneBean.setCodice(operazione.getCodiceOperazione());
								operazioneBean.setDescrizione(operazione.getDescrizioneOperazione());
								opSelezionate.add(operazioneBean);
							}
						}
						for (Iterator iteratorK = opSelezionate.iterator(); iteratorK.hasNext();) {
							BaseBeanRows operazione = null;
							try {
								operazione = (BaseBeanRows) iteratorK.next();
							} catch (Exception e) {
								logger.error("Errore nella gestione dell'albero delle operazioni");
								throw e;
							}
							ArrayList listaNodiFigli = new ArrayList();
							for (Iterator iterator2 = dataForm.getAlberoOperazioni().iterator(); iterator2.hasNext();) {
								OperazioneBean oper = (OperazioneBean) iterator2.next();
								if (oper.getCodicePadre()!=null && oper.getCodicePadre().equalsIgnoreCase(operazione.getCodice())){
									listaNodiFigli.add(oper);
								}
							}
							operazione.setRows(listaNodiFigli);
						}
						request.setAttribute("listaFigli", opSelezionate);
					}			
				}
			} else {
				throw new ProcedimentoUnicoException("Sessione scaduta");
			}
			logger.debug("SceltaOperazioniStep - service method END");
        } catch (Exception e) {
        	gestioneEccezioni(process,3,e);
        }
	}
 		
		
	
	private void findChildren(ArrayList alberoOperazioni) {
		for (Iterator iterator = alberoOperazioni.iterator(); iterator.hasNext();) {
			OperazioneBean op = (OperazioneBean) iterator.next();
			for (Iterator iterator2 = alberoOperazioni.iterator(); iterator2.hasNext();) {
				OperazioneBean op2 = (OperazioneBean) iterator2.next();
				if ( op2.getCodicePadre()!=null && op2.getCodicePadre().equalsIgnoreCase(op.getCodiceOperazione())){
					op.addListaCodiciFigli(op2.getCodiceOperazione());
				}
			}
		}
	}


	public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException,ServletException {
		try {
			logger.debug("SceltaOperazioniStep - loopBack method");
			ProcessData dataForm = (ProcessData) process.getData();
			boolean nextSubStep = false;
			ArrayList opSelezionate = new ArrayList();
			if(propertyName == null)
                propertyName = request.getParameter("pagina");
			if (propertyName!=null && propertyName.equalsIgnoreCase("sceltaOperazioni.jsp")){
				if (dataForm.getLivelloSceltaOp()>1){

					for (Iterator iterator = dataForm.getAlberoOperazioni().iterator(); iterator.hasNext();) {
						OperazioneBean operazione = (OperazioneBean) iterator.next();
						if (operazione.isSelezionato() && operazione.getProfondita()==(dataForm.getLivelloSceltaOp()-1) && operazione.getListaCodiciFigli()!=null && operazione.getListaCodiciFigli().size()>0 ){
							BaseBeanRows operazioneBean = new BaseBeanRows();
							operazioneBean.setCodice(operazione.getCodiceOperazione());
							operazioneBean.setDescrizione(operazione.getDescrizioneOperazione());
							opSelezionate.add(operazioneBean);
						}
					}
					for (Iterator iteratorK = opSelezionate.iterator(); iteratorK.hasNext();) {
						BaseBeanRows operazione = null;
						operazione = (BaseBeanRows) iteratorK.next();
						ArrayList listaNodiFigli = new ArrayList();
						for (Iterator iterator2 = dataForm.getAlberoOperazioni().iterator(); iterator2.hasNext();) {
							OperazioneBean oper = (OperazioneBean) iterator2.next();
							if (oper.getCodicePadre()!=null && oper.getCodicePadre().equalsIgnoreCase(operazione.getCodice())){
								listaNodiFigli.add(oper);
							}
						}
						operazione.setRows(listaNodiFigli);
					}
					request.setAttribute("listaFigli", opSelezionate);
				}
				showJsp(process, propertyName, false);
			} else if (propertyName!=null && propertyName.equalsIgnoreCase("avanti")){
	
				dataForm.setLivelloSceltaOp(dataForm.getLivelloSceltaOp()+1);
						
				for (Iterator iterator = dataForm.getAlberoOperazioni().iterator(); iterator.hasNext();) {
					OperazioneBean operazione = (OperazioneBean) iterator.next();
					if(operazione.getRaggruppamentoCheck()==null || operazione.getRaggruppamentoCheck().equalsIgnoreCase("")){
						String ret = (String)request.getParameter(operazione.getCodiceOperazione());
						if ( ((operazione.getSino().equalsIgnoreCase("N") && ret!=null)) || ((operazione.getSino().equalsIgnoreCase("S") && (ret!=null) && (ret.equalsIgnoreCase("SI")) ) ) ) {
							operazione.setSelezionato(true);
							if (operazione.getListaCodiciFigli()!=null && operazione.getListaCodiciFigli().size()>0){
								BaseBeanRows operazioneBean = new BaseBeanRows();
								operazioneBean.setCodice(operazione.getCodiceOperazione());
								operazioneBean.setDescrizione(operazione.getDescrizioneOperazione());
								opSelezionate.add(operazioneBean);
								nextSubStep=true;
							}
						} 
					}
					else{ // si tratta di un radiobutton
						if (request.getParameter(operazione.getRaggruppamentoCheck()) != null){
							if (operazione.getCodiceOperazione().equalsIgnoreCase(request.getParameter(operazione.getRaggruppamentoCheck()))) {
								operazione.setSelezionato(true);
								if (operazione.getListaCodiciFigli()!=null && operazione.getListaCodiciFigli().size()>0){
									BaseBeanRows operazioneBean = new BaseBeanRows();
									operazioneBean.setCodice(operazione.getCodiceOperazione());
									operazioneBean.setDescrizione(operazione.getDescrizioneOperazione());
									opSelezionate.add(operazioneBean);
									nextSubStep=true;
								}
							}
						}						
					}
				}
				if (nextSubStep){
		
					for (Iterator iteratorK = opSelezionate.iterator(); iteratorK.hasNext();) {
						BaseBeanRows operazione = null;
						try {
							operazione = (BaseBeanRows) iteratorK.next();
						} catch (Exception e) {
							logger.error("Errore");
						}
						ArrayList listaNodiFigli = new ArrayList();
						for (Iterator iterator2 = dataForm.getAlberoOperazioni().iterator(); iterator2.hasNext();) {
							OperazioneBean oper = (OperazioneBean) iterator2.next();
							if (oper.getCodicePadre()!=null && oper.getCodicePadre().equalsIgnoreCase(operazione.getCodice())){
								listaNodiFigli.add(oper);
							}
						}
						operazione.setRows(listaNodiFigli);
					}
					request.setAttribute("listaFigli", opSelezionate);
				} else {
					// sono arrivato alla fine delle scelte
					dataForm.setFineSceltaOp(true);
					opSelezionate = new ArrayList();
					for (Iterator iterator = dataForm.getAlberoOperazioni().iterator(); iterator.hasNext();) {
						OperazioneBean operazione = (OperazioneBean) iterator.next();
						if (operazione.isSelezionato() && (operazione.getListaCodiciFigli()==null || operazione.getListaCodiciFigli().size()==0) ){
							opSelezionate.add(operazione);
						}
					}
					request.setAttribute("listaOperazioni", opSelezionate);
				}
			} else if(propertyName.equalsIgnoreCase("bookmark")) {
				gestioneBookmark(process, request);
			} else if(propertyName.equalsIgnoreCase("nextStep")) {
				if (initialise(process,request)) {
					logger.info("SceltaOperazioniStep - Calcolo interventi prima di passare allo step successivo");
					resetDati(process);
					ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
					dataForm.setInterventi(new ArrayList());
					delegate.calcolaInterventi(dataForm);
					delegate.calcolaAllegatiPerInterventi(dataForm.getInterventi(),dataForm.getListaAllegati(),dataForm.getComuneSelezionato().getCodEnte());
					delegate.calcolaNormativePerInterventi(dataForm.getComuneSelezionato().getCodEnte(),dataForm.getInterventi(),dataForm.getListaNormative());
					String pythonModule="";
					pythonModule = delegate.calcolaProcedimenti(dataForm,false);
					delegate.calcolaSportelli(pythonModule, dataForm);
				}
				setStep(process, request, getCurrentActivityIndex(process), getCurrentStepIndex(process)+1);
			} else if(propertyName.equalsIgnoreCase("normativa.jsp")) {
				
				for (Iterator iterator = dataForm.getAlberoOperazioni().iterator(); iterator.hasNext();) {
					OperazioneBean operazione = (OperazioneBean) iterator.next();
					if (request.getParameter(operazione.getCodiceOperazione()) != null){
						operazione.setSelezionato(true);
					}
				}
				String idx = request.getParameter("index");
				//enableBottomNavigationBar(process, false);
                logger.debug(process.getView().getCurrentActivity().getCurrentStep().getJspPath());
                //String codRif = request.getParameter("codRif");
                ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
                request.setAttribute("listaDocumenti", delegate.getDocumentiNormative(String.valueOf(idx)));
                request.setAttribute("OP", "true");
                showJsp(process, propertyName, false);
			} else { // sto tornando indietro sull'albero delle operazioni
				dataForm.setFineSceltaOp(false);
				dataForm.setLivelloSceltaOp(dataForm.getLivelloSceltaOp()-1);
				if (dataForm.getLivelloSceltaOp()>1){
					opSelezionate = new ArrayList();
					for (Iterator iterator = dataForm.getAlberoOperazioni().iterator(); iterator.hasNext();) {
						OperazioneBean operazione = (OperazioneBean) iterator.next();			
						if (operazione.isSelezionato() && operazione.getProfondita()==(dataForm.getLivelloSceltaOp()-1) && operazione.getListaCodiciFigli()!=null && operazione.getListaCodiciFigli().size()>0 ){
							BaseBeanRows operazioneBean = new BaseBeanRows();
							operazioneBean.setCodice(operazione.getCodiceOperazione());
							operazioneBean.setDescrizione(operazione.getDescrizioneOperazione());
							opSelezionate.add(operazioneBean);
						}
					}
					for (Iterator iteratorK = opSelezionate.iterator(); iteratorK.hasNext();) {
						BaseBeanRows operazione = null;
						operazione = (BaseBeanRows) iteratorK.next();
						ArrayList listaNodiFigli = new ArrayList();
						for (Iterator iterator2 = dataForm.getAlberoOperazioni().iterator(); iterator2.hasNext();) {
							OperazioneBean oper = (OperazioneBean) iterator2.next();
							if (oper.getCodicePadre()!=null && oper.getCodicePadre().equalsIgnoreCase(operazione.getCodice())){
								listaNodiFigli.add(oper);
							}
						}
						operazione.setRows(listaNodiFigli);
					}
					request.setAttribute("listaFigli", opSelezionate);
				}
				
// PC - Reiterazione domanda inizio    
// resetto le scelte del livello da cui provengo                                
//				for (Iterator iterator = dataForm.getAlberoOperazioni().iterator(); iterator.hasNext();) {
//					OperazioneBean operazione = (OperazioneBean) iterator.next();
//					if (operazione.getProfondita()==(dataForm.getLivelloSceltaOp()+1)){
//						operazione.setSelezionato(false);
//						// RAVENNA
//						if (operazione.getSino()!=null){
//							operazione.setValueSiNo(null);
//						}
//					}
//				}
// PC - Reiterazione domanda fine                                
			}
			logger.debug("SceltaOperazioniStep - loopBack method END");
		} catch (Exception e) {
        	gestioneEccezioni(process,3,e);
        }

	}

	public boolean logicalValidate(AbstractPplProcess process,IRequestWrapper request,IValidationErrors errors) throws ParserException {		
		ProcessData dataForm = (ProcessData) process.getData();
		try {
			logger.debug("SceltaOperazioniStep - logicalValidate method");
			String tmp = (String) request.getParameter("navigation.button.save");
			if (tmp!=null) {request.setAttribute("navigation.button.save","Y");}
			String isLoopbackString = (String) request.getUnwrappedRequest().getParameter("navigation.button.loopbackValidate$avanti");
			boolean isLoopBack = (isLoopbackString!=null && !isLoopbackString.equalsIgnoreCase(""));
			boolean trovato=false;
			boolean trovato2=true;
			//boolean trovato3=true;
			if (!isLoopBack) {
				// significa che ho cliccato sul pulsante "Continua" per passare allo step successivo
				if (!dataForm.isFineSceltaOp()) {
					errors.add("error.sceltaSettoreNonTerminata");
	
					for (Iterator iterator = dataForm.getAlberoOperazioni().iterator(); iterator.hasNext();) {
						OperazioneBean operazione = (OperazioneBean) iterator.next();
						if (request.getParameter(operazione.getCodiceOperazione()) != null){
							operazione.setSelezionato(true);
						}
					}
					if (dataForm.getLivelloSceltaOp()>1){
						ArrayList opSelezionate = new ArrayList();
						for (Iterator iterator = dataForm.getAlberoOperazioni().iterator(); iterator.hasNext();) {
							OperazioneBean operazione = (OperazioneBean) iterator.next();			
							if (operazione.isSelezionato() && operazione.getProfondita()==(dataForm.getLivelloSceltaOp()-1) && operazione.getListaCodiciFigli()!=null && operazione.getListaCodiciFigli().size()>0 ){
								BaseBeanRows operazioneBean = new BaseBeanRows();
								operazioneBean.setCodice(operazione.getCodiceOperazione());
								operazioneBean.setDescrizione(operazione.getDescrizioneOperazione());
								opSelezionate.add(operazioneBean);
							}
						}
						for (Iterator iteratorK = opSelezionate.iterator(); iteratorK.hasNext();) {
							BaseBeanRows operazione = null;
							operazione = (BaseBeanRows) iteratorK.next();
							ArrayList listaNodiFigli = new ArrayList();
							for (Iterator iterator2 = dataForm.getAlberoOperazioni().iterator(); iterator2.hasNext();) {
								OperazioneBean oper = (OperazioneBean) iterator2.next();
								if (oper.getCodicePadre()!=null && oper.getCodicePadre().equalsIgnoreCase(operazione.getCodice())){
									listaNodiFigli.add(oper);
								}
							}
							operazione.setRows(listaNodiFigli);
						}
						request.setAttribute("listaFigli", opSelezionate);
					}
					
					return false;
				} else {
					// calcola gli interventi associati alle operazioni selezionate e passo allo step successivo
					if (initialise(process,request)) {
						logger.info("SceltaOperazioniStep - Calcolo interventi prima di passare allo step successivo");
						resetDati(process);
						ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
						dataForm.setInterventi(new ArrayList());
						delegate.calcolaInterventi(dataForm);
						delegate.calcolaAllegatiPerInterventi(dataForm.getInterventi(),dataForm.getListaAllegati(),dataForm.getComuneSelezionato().getCodEnte());
						delegate.calcolaNormativePerInterventi(dataForm.getComuneSelezionato().getCodEnte(),dataForm.getInterventi(),dataForm.getListaNormative());
						String pythonModule="";
						pythonModule = delegate.calcolaProcedimenti(dataForm,false);
						delegate.calcolaSportelli(pythonModule, dataForm);
					}
				}
			} else {
				// significa che ho cliccato sul pulsante "Avanza nella scelta operazione" 
				for (Iterator iterator = dataForm.getAlberoOperazioni().iterator(); iterator.hasNext();) {
					OperazioneBean op = (OperazioneBean) iterator.next();
					if (op.getProfondita()==dataForm.getLivelloSceltaOp()){
						if (op.getRaggruppamentoCheck()==null || op.getRaggruppamentoCheck().equalsIgnoreCase("")){
							if (op.getSino().equalsIgnoreCase("N")){
								if ( request.getParameter(op.getCodiceOperazione()) != null){
									trovato=true;
									op.setSelezionato(true);
								} else {
									op.setSelezionato(false);
								}
							} else {
								// RAVENNA
								if (padreIsChecked(process,op)){
									String ret = (String)request.getParameter(op.getCodiceOperazione());
									op.setValueSiNo(ret);
									if (ret!=null && ret.equalsIgnoreCase("SI")){
										op.setSelezionato(true);
										trovato=true;
									} else {
										op.setSelezionato(false);
										if (ret==null){trovato2=false;}
									}
								}
							}
						} else {
							if ( request.getParameter(op.getRaggruppamentoCheck()) != null && request.getParameter(op.getRaggruppamentoCheck()).equalsIgnoreCase(op.getCodiceOperazione()) ){
								trovato=true;
								op.setSelezionato(true);
							} else {
								op.setSelezionato(false);
							}
						}
					}
// PC - Reiterazione domanda inizio
// pulisci le scelte a valle di quelle non selezionate
                                        loopPulisciCascata(dataForm,op);

// PC - Reiterazione domanda fine                                        
				}
				if (!(trovato&&trovato2 )){
					// rimetto in request la lista dei nodi già presentata
					if (dataForm.getLivelloSceltaOp()>1){
						ArrayList opSelezionate = new ArrayList();
						for (Iterator iterator = dataForm.getAlberoOperazioni().iterator(); iterator.hasNext();) {
							OperazioneBean operazione = (OperazioneBean) iterator.next();
							if (operazione.isSelezionato() && operazione.getProfondita()==(dataForm.getLivelloSceltaOp()-1) && operazione.getListaCodiciFigli()!=null && operazione.getListaCodiciFigli().size()>0 ){
								BaseBeanRows operazioneBean = new BaseBeanRows();
								operazioneBean.setCodice(operazione.getCodiceOperazione());
								operazioneBean.setDescrizione(operazione.getDescrizioneOperazione());
								opSelezionate.add(operazioneBean);
							}
						}
						for (Iterator iteratorK = opSelezionate.iterator(); iteratorK.hasNext();) {
							BaseBeanRows operazione = null;
							operazione = (BaseBeanRows) iteratorK.next();
							ArrayList listaNodiFigli = new ArrayList();
							for (Iterator iterator2 = dataForm.getAlberoOperazioni().iterator(); iterator2.hasNext();) {
								OperazioneBean oper = (OperazioneBean) iterator2.next();
								if (oper.getCodicePadre()!=null && oper.getCodicePadre().equalsIgnoreCase(operazione.getCodice())){
									listaNodiFigli.add(oper);
								}
							}
							operazione.setRows(listaNodiFigli);
						}
						request.setAttribute("listaFigli", opSelezionate);
					}
					errors.add("operazione.sceltaoperazioniNonCompleta");
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			errors.add("error.generic","Errore interno");
        	gestioneEccezioni(process,3,e);
        	dataForm.setInternalError(true);
        }
		return false;
	}



	private boolean padreIsChecked(AbstractPplProcess process,OperazioneBean op) {
		ProcessData dataForm = (ProcessData) process.getData();
		if (op.getCodicePadre()==null) {
			return true; 
		}
		Iterator it = dataForm.getAlberoOperazioni().iterator();
		boolean trovato = false;
		OperazioneBean operazione = null;
		while (it.hasNext() && !trovato){
			operazione = (OperazioneBean) it.next();
			if (Utilities.isset(operazione.getCodiceOperazione()) && operazione.getCodiceOperazione().equalsIgnoreCase(op.getCodicePadre()) ){
				trovato =true;
			}
		}
		if (!trovato){
			return false;
		} else {
			if (operazione.isSelezionato()){
				return true;
			} else {
				return false;
			}
		}		
	}
	
	private void resetDati(AbstractPplProcess process){
		ProcessData dataForm = (ProcessData) process.getData();
		dataForm.setInterventi(new ArrayList());
// PC - Reiterazione domanda inizio                 
//		dataForm.setInterventiFacoltativi(new ArrayList());
// PC - Reiterazione domanda fine                 
		dataForm.setListaAllegati(new LinkedHashMap());
		dataForm.setListaProcedimenti(new HashMap());
		dataForm.setListaSportelli(new HashMap());
		dataForm.setListaNormative(new HashMap());
// PC - Reiterazione domanda inizio                
//		dataForm.setListaHref(new HashMap());
// PC - Reiterazione domanda fine                
                // PC - ordinamento allegati
                dataForm.setListaHrefOrdered(new ArrayList());
                // PC - ordinamento allegati
		dataForm.setListaDichiarazioniStatiche(new LinkedHashMap());
		dataForm.setListaModulistica(new LinkedHashMap());
		dataForm.setListaDocRichiesti(new LinkedHashMap());
		dataForm.setOggettoIstanza(new SezioneCompilabileBean());
	}
// PC - Reiterazione domanda inizio 
    private void loopPulisciCascata(ProcessData dataForm, OperazioneBean op) {
        if (!op.isSelezionato()) {
            for (Iterator it = dataForm.getAlberoOperazioni().iterator(); it.hasNext();){
                OperazioneBean o = (OperazioneBean) it.next();
                if (op.getCodiceOperazione().equals(o.getCodicePadre())) {
                    o.setSelezionato(false);
                    loopPulisciCascata(dataForm, o);
                }
            }
        }
    }
// PC - Reiterazione domanda fine      

}
