/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 *
 * For convenience a plain text copy of the English version of the Licence can
 * be found in the file LICENCE.txt in the top-level directory of this software
 * distribution.
 *
 * You may obtain a copy of the Licence in any of 22 European Languages at:
 *
 * http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations under
 * the Licence.
 *
 */
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.steps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.ServletException;

import it.gruppoinit.commons.Utilities;
import it.people.IValidationErrors;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.ProcedimentoUnicoDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AllegatoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.InterventoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OperazioneBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoUnicoException;
import it.people.java.util.LinkedHashMap;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.process.SurfDirection;
import it.people.wrappers.IRequestWrapper;


public class AllegatiFacoltativiStep extends BaseStep {

    public void service(AbstractPplProcess process, IRequestWrapper request) {
        try {

            if (initialise(process,request)) {
                logger.debug("AllegatiFacoltativiStep - service method");
                checkRecoveryBookmark(process, request);
                ProcessData dataForm = (ProcessData) process.getData();
                resetError(dataForm);

                String clickButtonSave = (String) request.getAttribute("navigation.button.save");
                LinkedHashMap nuovaListaAllegati = new LinkedHashMap();
                HashMap listaAllegFac = new HashMap();
                //if ( (getSurfDirection(process)==SurfDirection.forward) && (clickButtonSave==null) ){
                String method = (String) request.getParameter("method");
                if (method == null || (method != null && !method.equalsIgnoreCase("Annulla"))) {
                    Set set = dataForm.getListaAllegati().keySet();
                    for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                        String codAllegato = (String) iterator.next();
                        AllegatoBean allegato = (AllegatoBean) dataForm.getListaAllegati().get(codAllegato);
                        if (!(allegato.getCodiceCondizione() != null && !allegato.getCodiceCondizione().equalsIgnoreCase(""))) {
                            nuovaListaAllegati.put(codAllegato, allegato);
                        } else {
                            listaAllegFac.put(codAllegato, allegato);
                        }
                    }
                    dataForm.setListaAllegati(nuovaListaAllegati);

                    for (Iterator iterator = listaAllegFac.keySet().iterator(); iterator.hasNext();) {
                        String codAllegatoFac = (String) iterator.next();
                        for (Iterator iterator2 = dataForm.getInterventi().iterator(); iterator2.hasNext();) {
                            InterventoBean intervento = (InterventoBean) iterator2.next();
                            ArrayList listaCodAllegati = new ArrayList();
                            for (Iterator iterator3 = intervento.getListaCodiciAllegati().iterator(); iterator3.hasNext();) {
                                String codAll = (String) iterator3.next();
                                if (!codAll.equalsIgnoreCase(codAllegatoFac)) {
                                    listaCodAllegati.add(codAll);
                                }
                            }
                            intervento.setListaCodiciAllegati(listaCodAllegati);
                        }
                        for (Iterator iterator2 = dataForm.getInterventiFacoltativi().iterator(); iterator2.hasNext();) {
                            InterventoBean intervento = (InterventoBean) iterator2.next();
                            ArrayList listaCodAllegati = new ArrayList();
                            for (Iterator iterator3 = intervento.getListaCodiciAllegati().iterator(); iterator3.hasNext();) {
                                String codAll = (String) iterator3.next();
                                if (!codAll.equalsIgnoreCase(codAllegatoFac)) {
                                    listaCodAllegati.add(codAll);
                                }
                            }
                            intervento.setListaCodiciAllegati(listaCodAllegati);
                        }
                    }


                }
                //}

                ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
                ArrayList listaAllegatiFacoltativi = delegate.getAllegatiFacoltativi(dataForm, nuovaListaAllegati);
                if (listaAllegatiFacoltativi != null && listaAllegatiFacoltativi.size() > 0) {
                    for (Iterator iterator = listaAllegatiFacoltativi.iterator(); iterator.hasNext();) {
                        AllegatoBean allFac = (AllegatoBean) iterator.next();
                        if (listaAllegFac.containsKey(allFac.getCodice())) {
                        	if (!allFac.isIndicatoreOptionAllegati()) {
                        		allFac.setChecked(true);
                        	} else {
                        		allFac.setChecked(((AllegatoBean)listaAllegFac.get(allFac.getCodice())).isChecked());
                        		allFac.setInitialized(((AllegatoBean)listaAllegFac.get(allFac.getCodice())).isInitialized());
                        	}
                        } else {
                        	if (getSurfDirection(process) == SurfDirection.backward && allFac.isIndicatoreOptionAllegati()) {
                        		allFac.setInitialized(true);
                        	}
                        }
                    }
                }

// PC - Reiterazione domanda inizio  
            
                ArrayList tmp = new ArrayList();
                HashMap mappa = new HashMap();
                if (listaAllegatiFacoltativi.size() > 0) {
                    for (Iterator iterator = listaAllegatiFacoltativi.iterator(); iterator.hasNext();) {
                        AllegatoBean allFac = (AllegatoBean) iterator.next();
                        if (!mappa.containsKey(allFac.getCodiceCondizione())) {
                            mappa.put(allFac.getCodiceCondizione(), allFac);
                            tmp.add(allFac);
                        }
                    }
                    listaAllegatiFacoltativi = tmp;
                }
// PC - Reiterazione domanda fine                
// PC - tieni traccia delle scelte su allegati facoltativi - inizio
                HashMap listaFacoltativi = new HashMap();
                if (listaAllegatiFacoltativi.size() > 0) {
                    for (Iterator iterator = listaAllegatiFacoltativi.iterator(); iterator.hasNext();) {
                        AllegatoBean allFac = (AllegatoBean) iterator.next();
                        listaFacoltativi.put(allFac.getCodice(), allFac);
                    }
                }
                dataForm.setListaAllegatiFacoltativi(listaFacoltativi);
// PC - tieni traccia delle scelte su allegati facoltativi - fine

                if (listaAllegatiFacoltativi == null || listaAllegatiFacoltativi.size() == 0) {
                    if (getSurfDirection(process) == SurfDirection.forward) {
                        goToStep(process, request, 1);
                        logger.debug("AllegatiFacoltativiStep - service method END");
                        return;
                    } else if (getSurfDirection(process) == SurfDirection.backward) {
                        goToStep(process, request, -1);
                        logger.debug("AllegatiFacoltativiStep - service method END");
                        return;
                    }
                } else {
                    request.setAttribute("allegatiFacoltativi", listaAllegatiFacoltativi);
                }

            } else {
                throw new ProcedimentoUnicoException("Sessione scaduta");
            }
            logger.debug("AllegatiFacoltativiStep - service method END");
        } catch (Exception e) {
            gestioneEccezioni(process, 5, e);
        }
    }

    public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException, ServletException {
        try {
            logger.debug("AllegatiFacoltativiStep - loopBack method");
            ProcessData dataForm = (ProcessData) process.getData();
            if (propertyName == null) {
                propertyName = request.getParameter("pagina");
            }

            if (propertyName.equalsIgnoreCase("bookmark")) {
                gestioneBookmark(process, request);
            } else if (propertyName.equalsIgnoreCase("normativa.jsp")) {
                ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
// PC - Problema allegati facoltativi inizio            
//               ArrayList listaAllegatiFacoltativi = delegate.getAllegatiFacoltativi(dataForm, new HashMap());                
                ArrayList listaAllegatiFacoltativi = delegate.getAllegatiFacoltativi(dataForm, dataForm.getListaAllegati());
// PC - Problema allegati facoltativi fine               
                
// TODO - ALLEGATI FACOLTATIVI SELEZIONATI                
                if (listaAllegatiFacoltativi != null && listaAllegatiFacoltativi.size() > 0) {
                    for (Iterator iterator = listaAllegatiFacoltativi.iterator(); iterator.hasNext();) {
                        AllegatoBean allFac = (AllegatoBean) iterator.next();
                        if (request.getParameter(allFac.getCodice()) != null) {
                            dataForm.addListaAllegati(allFac.getCodice(), allFac);
                        }
                    }
                }

                String idx = request.getParameter("index");
                //enableBottomNavigationBar(process, false);
                logger.debug(process.getView().getCurrentActivity().getCurrentStep().getJspPath());
                //String codRif = request.getParameter("codRif");
                request.setAttribute("listaDocumenti", delegate.getDocumentiNormative(String.valueOf(idx)));
                request.setAttribute("AF", "true");
                showJsp(process, propertyName, false);
            } else if (propertyName.equalsIgnoreCase("allegatiFacoltativi.jsp")) {
                // ---------------------
                ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
// PC - Problema allegati facoltativi inizio            
//                ArrayList listaAllegatiFacoltativi = delegate.getAllegatiFacoltativi(dataForm, new HashMap());
                ArrayList listaAllegatiFacoltativi = delegate.getAllegatiFacoltativi(dataForm, dataForm.getListaAllegati());
// PC - Problema allegati facoltativi fine                   
                if (listaAllegatiFacoltativi != null && listaAllegatiFacoltativi.size() > 0) {
                    for (Iterator iterator = listaAllegatiFacoltativi.iterator(); iterator.hasNext();) {
                        AllegatoBean allFac = (AllegatoBean) iterator.next();
                        if (dataForm.getListaAllegati().containsKey(allFac.getCodice())) {
                            allFac.setChecked(true);
                            allFac.setInitialized(true);
                        }
                    }
                }
                request.setAttribute("allegatiFacoltativi", listaAllegatiFacoltativi);

                // -----------------------
                showJsp(process, propertyName, false);
            }
            logger.debug("AllegatiFacoltativiStep - loopBack method END");
        } catch (Exception e) {
            gestioneEccezioni(process, 5, e);
        }
    }

    public boolean logicalValidate(AbstractPplProcess process, IRequestWrapper request, IValidationErrors errors) throws ParserException {
        ProcessData dataForm = (ProcessData) process.getData();
        doSave(process, request);
        boolean errore = false;
        try {
            logger.debug("AllegatiFacoltativiStep - logicalValidate method");
            ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
// PC - Problema allegati facoltativi inizio          
//            ArrayList listaAllegatiFacoltativi = delegate.getAllegatiFacoltativi(dataForm, new HashMap());
            ArrayList listaAllegatiFacoltativi = delegate.getAllegatiFacoltativi(dataForm, dataForm.getListaAllegati());
// PC - Problema allegati facoltativi fine                        
            
            ArrayList tmp = new ArrayList();
            HashMap mappa = new HashMap();
            if (listaAllegatiFacoltativi.size() > 0) {
                for (Iterator iterator = listaAllegatiFacoltativi.iterator(); iterator.hasNext();) {
                    AllegatoBean allFac = (AllegatoBean) iterator.next();
                    if (!mappa.containsKey(allFac.getCodiceCondizione() + allFac.getCodice())) {
                        mappa.put(allFac.getCodiceCondizione() + allFac.getCodice(), allFac);
                        tmp.add(allFac);
                    }
                }
                listaAllegatiFacoltativi = tmp;
            }
            
            if (listaAllegatiFacoltativi != null && listaAllegatiFacoltativi.size() > 0) {
                for (Iterator iterator = listaAllegatiFacoltativi.iterator(); iterator.hasNext();) {
                    AllegatoBean allFac = (AllegatoBean) iterator.next();
                    if (!allFac.isIndicatoreOptionAllegati()) {
                        if (request.getParameter(allFac.getCodiceCondizione()) != null) {
                            allFac.setChecked(true);
                            allFac.setInitialized(true);
                            dataForm.addListaAllegati(allFac.getCodice(), allFac);
                            addToIntervento(dataForm, allFac, delegate);
// PC - tieni traccia delle scelte su allegati facoltativi - inizio
                            dataForm.getListaAllegatiFacoltativi().put(allFac.getCodice(), allFac);
// PC - tieni traccia delle scelte su allegati facoltativi - fine                            
                        }
// PC - tieni traccia delle scelte su allegati facoltativi - inizio
                        else {
                            allFac.setInitialized(true);
                            dataForm.getListaAllegatiFacoltativi().put(allFac.getCodice(), allFac);
                        }
// PC - tieni traccia delle scelte su allegati facoltativi - fine                        
                    } else {
                        if (request.getParameter(allFac.getCodiceCondizione()) != null) {
                            allFac.setInitialized(true);
                            allFac.setChecked(request.getParameter(allFac.getCodiceCondizione()).equalsIgnoreCase("s"));
                            if (request.getParameter(allFac.getCodiceCondizione()).equalsIgnoreCase("s")) {

                                dataForm.addListaAllegati(allFac.getCodice(), allFac);
                                addToIntervento(dataForm, allFac, delegate);
// PC - tieni traccia delle scelte su allegati facoltativi - inizio
                                dataForm.getListaAllegatiFacoltativi().put(allFac.getCodice(), allFac);
// PC - tieni traccia delle scelte su allegati facoltativi - fine                                  
                            } 
                            else {
// PC - tieni traccia delle scelte su allegati facoltativi - inizio   
                                dataForm.getListaAllegatiFacoltativi().put(allFac.getCodice(), allFac);
// PC - tieni traccia delle scelte su allegati facoltativi - fine                                
                            }
// PC - tieni traccia delle scelte su allegati facoltativi - inizio                              
//                        } else {
//                            errore = true;
// PC - tieni traccia delle scelte su allegati facoltativi - fine                            
                        }
                    }
                }
            }
// PC - tieni traccia delle scelte su allegati facoltativi - inizio
            if (dataForm.getListaAllegatiFacoltativi() != null && dataForm.getListaAllegatiFacoltativi().size() > 0) {
                Set set = dataForm.getListaAllegatiFacoltativi().keySet();
                for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                    AllegatoBean allFac = (AllegatoBean) dataForm.getListaAllegatiFacoltativi().get((String) iterator.next());
                    if (allFac == null || !allFac.isInitialized()){
                        errore=true;
                        break;
                    }
                }
            }
// PC - tieni traccia delle scelte su allegati facoltativi - fine            
            if (errore) {

// PC - Problema allegati facoltativi inizio 
//                listaAllegatiFacoltativi = delegate.getAllegatiFacoltativi(dataForm, new HashMap());                
//                       listaAllegatiFacoltativi = delegate.getAllegatiFacoltativi(dataForm, dataForm.getListaAllegati());
// PC - Problema allegati facoltativi fine                  
                tmp = new ArrayList();
                mappa = new HashMap();
                if (listaAllegatiFacoltativi.size() > 0) {
                    for (Iterator iterator = listaAllegatiFacoltativi.iterator(); iterator.hasNext();) {
                        AllegatoBean allFac = (AllegatoBean) iterator.next();
// PC - tieni traccia delle scelte su allegati facoltativi - inizio
                        if (dataForm.getListaAllegatiFacoltativi() != null && dataForm.getListaAllegatiFacoltativi().get(allFac.getCodice())!= null && !((AllegatoBean) dataForm.getListaAllegatiFacoltativi().get(allFac.getCodice())).isInitialized()) {
// PC - tieni traccia delle scelte su allegati facoltativi - fine                         
                            if (!mappa.containsKey(allFac.getCodiceCondizione())) {
                                mappa.put(allFac.getCodiceCondizione(), dataForm.getListaAllegatiFacoltativi().get(allFac.getCodice()));
                                tmp.add(dataForm.getListaAllegatiFacoltativi().get(allFac.getCodice()));
                            }
// PC - tieni traccia delle scelte su allegati facoltativi - inizio
                        }
// PC - tieni traccia delle scelte su allegati facoltativi - fine                         
                    }
                    listaAllegatiFacoltativi = tmp;
                }
            	
                errors.add("error.sceltaAllegatiFacoltativi");
                request.setAttribute("allegatiFacoltativi", listaAllegatiFacoltativi);
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            errors.add("error.generic", "Errore interno");
            gestioneEccezioni(process, 5, e);
            dataForm.setInternalError(true);
            return false;
        }
    }

    public void addToIntervento(ProcessData dataForm, AllegatoBean allFac, ProcedimentoUnicoDAO delegate) throws Exception {
        for (Iterator iterator2 = dataForm.getInterventi().iterator(); iterator2.hasNext();) {
            InterventoBean intervento = (InterventoBean) iterator2.next();
            if (delegate.checkValiditaAllegato(intervento.getCodice(), allFac.getCodice(), dataForm.getComuneSelezionato().getCodEnte())) {
                intervento.getListaCodiciAllegati().add(allFac.getCodice());
            }
        }
        for (Iterator iterator2 = dataForm.getInterventiFacoltativi().iterator(); iterator2.hasNext();) {
            InterventoBean intervento = (InterventoBean) iterator2.next();
            if (delegate.checkValiditaAllegato(intervento.getCodice(), allFac.getCodice(), dataForm.getComuneSelezionato().getCodEnte())) {
                intervento.getListaCodiciAllegati().add(allFac.getCodice());
            }
        }
    }
}
