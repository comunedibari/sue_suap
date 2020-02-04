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
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.ServletException;

import it.people.IValidationErrors;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.ProcedimentoUnicoDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.DefinizioneCampoFormula;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OneriBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OneriBeanComparator;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoUnicoException;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.TreeRenderer;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.process.SurfDirection;
import it.people.util.MessageBundleHelper;
import it.people.wrappers.IRequestWrapper;

public class CalcoloOneriShowStep extends BaseStep {
	
	public void service(AbstractPplProcess process, IRequestWrapper request) {
		try {
			if (initialise(process,request)) {
				logger.debug("CalcoloOneriShowStep - service method");
				checkRecoveryBookmark(process, request);
				ProcessData dataForm = (ProcessData) process.getData();
				resetError(dataForm);
				
				String clickButtonSave = (String) request.getAttribute("navigation.button.save");
				if ( (getSurfDirection(process)==SurfDirection.forward) && (clickButtonSave==null) ){
				
					if (!dataForm.isOneriCalcolatiPresent()){
// PC - Reiterazione domanda inizio
/////// PC -----                                             dataForm.setOneriAnticipati(new TreeSet(new OneriBeanComparator()));                                            
// PC - Reiterazione domanda fine                                            
                                            
	                    setStep(process, request, getCurrentActivityIndex(process), getCurrentStepIndex(process) + 1);
	                    return;
					} else {
					
						ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
		                String[] oneriSelezionati = dataForm.getOneriVec();
		
		                if (oneriSelezionati != null && oneriSelezionati.length > 0) {
		                    
		                    ArrayList root = (ArrayList) dataForm.getListaAlberoOneri();
		                    
		                    if(dataForm.getOneriAnticipati() == null)
		                        dataForm.setOneriAnticipati(new TreeSet(new OneriBeanComparator()));
                                 
		                    TreeSet listaOneriAnticipati = new TreeSet(new OneriBeanComparator());
		                    for (Iterator iterator = dataForm.getOneriAnticipati().iterator(); iterator.hasNext();) {
								OneriBean onere = (OneriBean) iterator.next();
								if (onere.getOneriFormula().equalsIgnoreCase("") && onere.getCampoFormula().equalsIgnoreCase("")){
									listaOneriAnticipati.add(onere);
								}
							}
		                    dataForm.setOneriAnticipati(listaOneriAnticipati);
		                    
		                    List answer = delegate.completeOneri(root, oneriSelezionati, dataForm);
		
		                    String oneriScelti = TreeRenderer.renderCompileOneri(root, oneriSelezionati, dataForm);
		
		                    request.setAttribute("oneriScelti", oneriScelti);
		                }
					}
				} else { // sto andando all'indietro
					if (!dataForm.isOneriCalcolatiPresent()){
	                    setStep(process, request, getCurrentActivityIndex(process), getCurrentStepIndex(process)-1);
	                    return;
					} else {
						ArrayList root = (ArrayList) dataForm.getListaAlberoOneri();
						String[] oneriSelezionati = dataForm.getOneriVec();
	                    String oneriScelti = TreeRenderer.renderCompileOneri(root, oneriSelezionati, dataForm);
	                    request.setAttribute("oneriScelti", oneriScelti);
					}
				}
				
			} else {
				throw new ProcedimentoUnicoException("Sessione scaduta");
			}
			logger.debug("CalcoloOneriShowStep - service method END");
        } catch (Exception e) {
        	gestioneEccezioni(process,5,e);
        }
	}
	
	
	public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException,ServletException {
		try {
			logger.debug("CalcoloOneriShowStep - loopBack method");

			if(propertyName.equalsIgnoreCase("bookmark")){
	        	gestioneBookmark(process, request);
			}
			
			logger.debug("CalcoloOneriShowStep - loopBack method END");
		} catch (Exception e) {
			gestioneEccezioni(process,5,e);
		}
	}
	
	public boolean logicalValidate(AbstractPplProcess process,IRequestWrapper request,IValidationErrors errors) throws ParserException {		
		ProcessData dataForm = (ProcessData) process.getData();
		try {
			logger.debug("CalcoloOneriShowStep - logicalValidate method");
			
	        Iterator it = dataForm.getOneriAnticipati().iterator();
	        while (it.hasNext()) {
	            OneriBean bean = (OneriBean) it.next();
	            String daCercare = "F" + bean.getCodice();
	            //if (bean.getCodice().equalsIgnoreCase(oggettoRichiesto)){
	            String valoreOnere = request.getParameter(daCercare);
	            DefinizioneCampoFormula def = bean.getDefinizione();
	            boolean check = false;

	            check = controlla(bean.getDescrizione(), def, errors, valoreOnere, process);
	            if (!(null == valoreOnere || valoreOnere.equals(""))) {
	                if (check) {
	                    bean.setValoreFormula(valoreOnere);
	                }
	            }
	            if(bean.getAltriOneri().size()>0){
	                // Ho a che fare con oneri formula con pi� di un campo formula
	                Iterator itFiglio = bean.getAltriOneri().iterator();
	                while(itFiglio.hasNext()){
	                    OneriBean beanFiglio = (OneriBean)itFiglio.next();
	                    daCercare = "F" + beanFiglio.getCodice()+beanFiglio.getCampoFormula();
	                    valoreOnere = request.getParameter(daCercare);
	                    def = beanFiglio.getDefinizione();
	                    check = false;

	                    check = controlla(beanFiglio.getDescrizione(), def, errors, valoreOnere, process);
	                    if (!(null == valoreOnere || valoreOnere.equals(""))) {
	                        if (check) {
	                            beanFiglio.setValoreFormula(valoreOnere);
	                        }
	                    }
	                }
	            }
	        }

	        if (!errors.isEmpty()) {
	            service(process, request);
	            return false;
	        }
	        //..

	        
			logger.debug("CalcoloOneriShowStep - logicalValidate method END");
			return true;
		} catch (Exception e) {
			errors.add("error.generic","Errore interno");
        	gestioneEccezioni(process,5,e);
        	dataForm.setInternalError(true);
        	return false;
        }
	}
	
	
    private boolean controlla(String onere, DefinizioneCampoFormula def, IValidationErrors errors, String valoreDaControllare, 
    		AbstractPplProcess process) {
    	
        boolean answer = true;
        boolean isNumeric = false;

        if (def.getTipo() != null && def.getTipo().equalsIgnoreCase("I")) {
            isNumeric = true;
            // controllo su intero
            if (null == valoreDaControllare || valoreDaControllare.equals("")) {
                errors.add("error.obbligatorio", onere);
                return false;
            }

            if (valoreDaControllare.length() > def.getParteIntera()) {
                errors.add("error.generic", "Il campo " + onere
                                            + " pu� contenere solamente "
                                            + def.getParteIntera() + " cifre. ");
                return false;
            }
            try {
                Integer.parseInt(valoreDaControllare);
            } catch (Exception e) {
                errors.add("error.generic", "Il campo " + onere
                                            + " deve essere un intero di "
                                            + def.getParteIntera() + " cifre. ");
                return false;
            }

        } else if (def.getTipo() != null && def.getTipo().equalsIgnoreCase("D")) {
            isNumeric = true;
            //controllo su decimale
            if (null == valoreDaControllare || valoreDaControllare.equals("")) {
                errors.add("error.obbligatorio", onere);
                return false;
            }
            if (valoreDaControllare.length() > (def.getParteIntera()
                                                + def.getParteDecimale() + 1)) {
                errors.add("error.generic", "Il campo "
                                            + onere
                                            + " pu� contenere solamente "
                                            + (def.getParteIntera()
                                               + def.getParteDecimale() + 1)
                                            + " cifre. ");
                return false;
            }
            try {
                Double.parseDouble(valoreDaControllare);
            } catch (Exception e) {
                errors.add("error.generic", "Il campo " + onere
                                            + " deve essere un decimale di "
                                            + def.getParteIntera()
                                            + " cifre di parte intera e "
                                            + def.getParteDecimale()
                                            + " di parte decimale. ");
                return false;
            }
        }
        
        if (isNumeric) {
        	boolean valid = true;
        	if (def.getTipo() != null && def.getTipo().equalsIgnoreCase("I")) {
            	if (Integer.parseInt(valoreDaControllare) == 0 && def != null && !def.isAccettaValoreZero()) {
            		valid = false;
            	}
        	}
        	if (def.getTipo() != null && def.getTipo().equalsIgnoreCase("D")) {
            	if (Double.parseDouble(valoreDaControllare) == 0 && def != null && !def.isAccettaValoreZero()) {
            		valid = false;
            	}
        	}
        	if (!valid) {
                errors.add("error.generic", MessageBundleHelper.message("error.onere.valore.zero.non.ammesso", new String[]{onere}, 
        				process.getProcessName(), process.getCommune().getKey(), 
        				process.getContext().getLocale()));
                answer = valid;
        	}
        }

        return answer;
    }


}
