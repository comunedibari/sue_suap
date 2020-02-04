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
import java.util.List;
import javax.servlet.ServletException;
import it.people.IValidationErrors;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoUnicoException;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.TreeRenderer;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.process.SurfDirection;
import it.people.wrappers.IRequestWrapper;

public class SceltaOneriStep extends BaseStep {
	
	public void service(AbstractPplProcess process, IRequestWrapper request) {
		try {
			if (initialise(process,request)) {
				logger.debug("SceltaOneriStep - service method");
				checkRecoveryBookmark(process, request);
				ProcessData dataForm = (ProcessData) process.getData();
				resetError(dataForm);
				
				String clickButtonSave = (String) request.getAttribute("navigation.button.save");
				if ( (getSurfDirection(process)==SurfDirection.forward) && (clickButtonSave==null) ){
				
					if (!dataForm.isOneriCalcolatiPresent()){
	                    setStep(process, request, getCurrentActivityIndex(process), getCurrentStepIndex(process) + 1);
	                    return;
					} else {
						dataForm.getDatiTemporanei().setListaMappeCheckPerRoot(new ArrayList() );
		                String alberoOneri = TreeRenderer.renderOneriTree(dataForm.getListaAlberoOneri(), dataForm, request);
		                request.setAttribute("alberoOneri", alberoOneri);
					}
				} else {
					if (!dataForm.isOneriCalcolatiPresent()){
	                    setStep(process, request, getCurrentActivityIndex(process), getCurrentStepIndex(process) - 1);
	                    return;
					} else {
		                String alberoOneri = TreeRenderer.renderOneriTree(dataForm.getListaAlberoOneri(), dataForm, request);
		                request.setAttribute("alberoOneri", alberoOneri);
					}
				}
                
			} else {
				throw new ProcedimentoUnicoException("Sessione scaduta");
			}
			logger.debug("SceltaOneriStep - service method END");
        } catch (Exception e) {
        	gestioneEccezioni(process,5,e);
        }
	}
	
	
	public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException,ServletException {
		try {
			logger.debug("SceltaOneriStep - loopBack method");

			if(propertyName.equalsIgnoreCase("bookmark")){
	        	gestioneBookmark(process, request);
			}
			
			logger.debug("SceltaOneriStep - loopBack method END");
		} catch (Exception e) {
			gestioneEccezioni(process,5,e);
		}
	}
	
	public boolean logicalValidate(AbstractPplProcess process,IRequestWrapper request,IValidationErrors errors) throws ParserException {		
		ProcessData dataForm = (ProcessData) process.getData();
		try {
			logger.debug("SceltaOneriStep - logicalValidate method");
			String[] oneriSelezionati = dataForm.getOneriVec();
			if (oneriSelezionati==null || oneriSelezionati.length==0){
				errors.add("error.nessunaselezione");
				service(process, request);
				return false;
			}
			
	        HashMap listaCodiciOneriSelezionati = getCodiciOneriSelezionati(dataForm.getOneriVec());
	        List listaDiListeCheckbox = dataForm.getDatiTemporanei().getListaMappeCheckPerRoot();
	        Iterator it1 = listaDiListeCheckbox.iterator();
	        //boolean controllo = false;
	        while(it1.hasNext()){
	            List listaCheckbox = (ArrayList)it1.next();
	            Iterator it2 = listaCheckbox.iterator();
	            boolean controlloTmp = false;
	            while(it2.hasNext()){
	                String codCheck = (String)it2.next();
	                if(listaCodiciOneriSelezionati.get(codCheck) != null){
	                    controlloTmp = true;
	                    break;
	                }
	            }
	            if (getSurfDirection(process)==SurfDirection.backward) {
	                if(!controlloTmp){
	                    errors.add("error.generic", "effettuare almeno una selezione per ciascun gruppo di oneri");
	                    service(process, request);
	                    return false;
	                }
	            }
	            else if(!controlloTmp){
	                errors.add("error.generic", "effettuare almeno una selezione per ciascun gruppo di oneri");
	                service(process, request);
	                return false;
	            }
	        }
			
			logger.debug("SceltaOneriStep - logicalValidate method END");
			return true;
		} catch (Exception e) {
			errors.add("error.generic","Errore interno");
        	gestioneEccezioni(process,5,e);
        	dataForm.setInternalError(true);
        	return false;
        }
	}
	
    private HashMap getCodiciOneriSelezionati(String[] oneriVec){
        HashMap lista = new HashMap();
        for(int i=0; i<oneriVec.length; i++){
            String onereSelezionato = oneriVec[i];
            int pos = onereSelezionato.lastIndexOf('|');
            lista.put(onereSelezionato.substring(pos+1, onereSelezionato.length()), "S");
        }
        return lista;
    }

}
