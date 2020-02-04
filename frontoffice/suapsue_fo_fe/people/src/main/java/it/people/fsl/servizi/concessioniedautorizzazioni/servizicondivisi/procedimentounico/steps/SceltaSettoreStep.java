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

import it.people.IActivity;
import it.people.IValidationErrors;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.ProcedimentoUnicoDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OperazioneBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoUnicoException;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SettoreBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean;
import it.people.java.util.LinkedHashMap;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.process.SurfDirection;
import it.people.util.ServiceParameters;
import it.people.wrappers.IRequestWrapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class SceltaSettoreStep extends BaseStep {

    public void service(AbstractPplProcess process, IRequestWrapper request) {
        try {
            if (initialise(process,request)) {
                logger.debug("SceltaSettoreStep - service method");
                checkRecoveryBookmark(process, request);
                ProcessData dataForm = (ProcessData) process.getData();
                resetError(dataForm);
                String clickButtonSave = (String) request.getAttribute("navigation.button.save");
                String forward = (String) request.getAttribute("forward");
              
                
                if (((getSurfDirection(process) == SurfDirection.forward) || forward != null) && (clickButtonSave == null) && (dataForm.getSettoreScelto() == null)) {
                    String method = (String) request.getParameter("method");
                    if (method == null || (method != null && !method.equalsIgnoreCase("Annulla"))) {
                        dataForm.setSettoreScelto(null);
                        ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
                        ArrayList alberoSettori = new ArrayList();
                        delegate.calcolaAlberoSettori2(dataForm.getComuneSelezionato().getCodEnte(), alberoSettori);
                        findChildren(alberoSettori);

                        dataForm.setAlberoSettori(alberoSettori);
                        dataForm.setLivelloSceltaSettore(1);
                        dataForm.setTmp("");
                        if (alberoSettori != null && alberoSettori.size() == 1) {
                            ((SettoreBean) alberoSettori.get(0)).setSelezionato(true);
                            dataForm.setTmp(((SettoreBean) alberoSettori.get(0)).getCodiceRamo());
                        }
                    }
                } else {
                    ArrayList listaNodiFigli = new ArrayList();
                    for (Iterator iterator = dataForm.getSettoreScelto().getListaCodiciFigli().iterator(); iterator.hasNext();) {
                        String settoreFiglio = (String) iterator.next();
                        for (Iterator iterator2 = dataForm.getAlberoSettori().iterator(); iterator2.hasNext();) {
                            SettoreBean settore = (SettoreBean) iterator2.next();
                            if (settore.getCodiceRamo().equalsIgnoreCase(settoreFiglio)) {
                                listaNodiFigli.add(settore);
                            }
                        }
                    }
                    request.setAttribute("listaFigli", listaNodiFigli);
                }
            } else {
                throw new ProcedimentoUnicoException("Sessione scaduta");
            }
            logger.debug("SceltaSettoreStep - service method END");
        } catch (Exception e) {
            gestioneEccezioni(process, 2, e);
        }
    }

    private void findChildren(ArrayList listaSettori) {
        for (Iterator iterator = listaSettori.iterator(); iterator.hasNext();) {
            SettoreBean settore = (SettoreBean) iterator.next();
            for (Iterator iterator2 = listaSettori.iterator(); iterator2.hasNext();) {
                SettoreBean sett = (SettoreBean) iterator2.next();
                if (settore.getCodiceRamo().equalsIgnoreCase(sett.getCodiceRamoPadre())) {
                    settore.addListaCodiciFigli(sett.getCodiceRamo());
                }
            }
        }
    }

    public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException, ServletException {

        try {
            logger.debug("SceltaSettoreStep - loopBack method");
            ProcessData dataForm = (ProcessData) process.getData();
            SettoreBean nodoTmp = null;
            if (propertyName == null) {
                propertyName = request.getParameter("pagina");
            }
            if (propertyName != null && propertyName.equalsIgnoreCase("sceltaSettore.jsp")) {
                if (dataForm.getLivelloSceltaSettore() > 1) {
                    for (Iterator iterator = dataForm.getAlberoSettori().iterator(); iterator.hasNext();) {
                        SettoreBean nodo = (SettoreBean) iterator.next();
                        if (nodo.isSelezionato() & nodo.getProfondita() == (dataForm.getLivelloSceltaSettore() - 1)) {
                            nodoTmp = nodo;
                        }
                    }
                    dataForm.setSettoreScelto(nodoTmp);
                    dataForm.setTmp("");
                    ArrayList listaNodiFigli = new ArrayList();
                    for (Iterator iterator = nodoTmp.getListaCodiciFigli().iterator(); iterator.hasNext();) {
                        String settoreFiglio = (String) iterator.next();
                        for (Iterator iterator2 = dataForm.getAlberoSettori().iterator(); iterator2.hasNext();) {
                            SettoreBean settore = (SettoreBean) iterator2.next();
                            if (settore.getCodiceRamo().equalsIgnoreCase(settoreFiglio)) {
                                listaNodiFigli.add(settore);
                            }
                        }
                    }
                    if (listaNodiFigli != null && listaNodiFigli.size() == 1) {  // se ho un solo ramo lo preseleziono
                        ((SettoreBean) listaNodiFigli.get(0)).setSelezionato(true);
                        dataForm.setTmp(((SettoreBean) listaNodiFigli.get(0)).getCodiceRamo());
                    }
                    request.setAttribute("listaFigli", listaNodiFigli);
                }
                showJsp(process, propertyName, false);
            } else if (propertyName != null && propertyName.equalsIgnoreCase("avanti")) {//ho cliccato sul pulsante "avanza nella scelta del settore"
                dataForm.setLivelloSceltaSettore(dataForm.getLivelloSceltaSettore() + 1);
                // siamo sulla root
                if (dataForm.getSettoreScelto() == null || dataForm.getSettoreScelto().getCodiceRamo() == null || dataForm.getSettoreScelto().getCodiceRamo().equalsIgnoreCase("")) {
                    for (Iterator iterator = dataForm.getAlberoSettori().iterator(); iterator.hasNext();) {
                        SettoreBean nodo = (SettoreBean) iterator.next();
                        if (nodo.getCodiceRamoPadre() == null || nodo.getCodiceRamoPadre().equalsIgnoreCase("")) {
                            if (nodo.getCodiceRamo().equalsIgnoreCase(dataForm.getTmp())) {
                                nodoTmp = nodo;
                                nodoTmp.setSelezionato(true);
                            } else {
                                nodo.setSelezionato(false);
                            }
                        }
                    }
                    dataForm.setSettoreScelto(nodoTmp);
                    dataForm.setTmp("");
                    ArrayList listaNodiFigli = new ArrayList();
                    for (Iterator iterator = nodoTmp.getListaCodiciFigli().iterator(); iterator.hasNext();) {
                        String settoreFiglio = (String) iterator.next();
                        for (Iterator iterator2 = dataForm.getAlberoSettori().iterator(); iterator2.hasNext();) {
                            SettoreBean settore = (SettoreBean) iterator2.next();
                            if (settore.getCodiceRamo().equalsIgnoreCase(settoreFiglio)) {
                                listaNodiFigli.add(settore);
                            }
                        }
                    }
                    if (listaNodiFigli != null && listaNodiFigli.size() == 1) {  // se ho un solo ramo lo preseleziono
                        ((SettoreBean) listaNodiFigli.get(0)).setSelezionato(true);
                        dataForm.setTmp(((SettoreBean) listaNodiFigli.get(0)).getCodiceRamo());
                    }
                    request.setAttribute("listaFigli", listaNodiFigli);

                } else {
                    for (Iterator iterator = dataForm.getSettoreScelto().getListaCodiciFigli().iterator(); iterator.hasNext();) {
                        String codiceNodoFiglio = (String) iterator.next();
                        for (Iterator iterator2 = dataForm.getAlberoSettori().iterator(); iterator2.hasNext();) {
                            SettoreBean settore = (SettoreBean) iterator2.next();
                            if (settore.getCodiceRamo().equalsIgnoreCase(codiceNodoFiglio)) {
                                if (settore.getCodiceRamo().equalsIgnoreCase(dataForm.getTmp())) {
                                    nodoTmp = settore;
                                    nodoTmp.setSelezionato(true);
                                } else {
                                    settore.setSelezionato(false);
                                }
                            }
                        }
                    }
                    dataForm.setSettoreScelto(nodoTmp);
                    dataForm.setTmp("");

                    ArrayList listaNodiFigli = new ArrayList();
                    for (Iterator iterator = nodoTmp.getListaCodiciFigli().iterator(); iterator.hasNext();) {
                        String settoreFiglio = (String) iterator.next();
                        for (Iterator iterator2 = dataForm.getAlberoSettori().iterator(); iterator2.hasNext();) {
                            SettoreBean settore = (SettoreBean) iterator2.next();
                            if (settore.getCodiceRamo().equalsIgnoreCase(settoreFiglio)) {
                                listaNodiFigli.add(settore);
                            }
                        }
                    }
                    request.setAttribute("listaFigli", listaNodiFigli);
                    if (listaNodiFigli != null && listaNodiFigli.size() == 1) { // se ho un solo ramo lo preseleziono
                        ((SettoreBean) listaNodiFigli.get(0)).setSelezionato(true);
                        dataForm.setTmp(((SettoreBean) listaNodiFigli.get(0)).getCodiceRamo());
                    }
                }

            } else if (propertyName != null && propertyName.equalsIgnoreCase("indietro")) {
                //ho cliccato sul pulsante "indietro nella scelta dell'albero dei settori"
                dataForm.setLivelloSceltaSettore(dataForm.getLivelloSceltaSettore() - 1);
                ArrayList listaNodiFigli = new ArrayList();
                if (dataForm.getSettoreScelto().getCodiceRamoPadre() != null && (!dataForm.getSettoreScelto().getCodiceRamoPadre().equalsIgnoreCase(""))) {
                    String codiceSettorePadre = dataForm.getSettoreScelto().getCodiceRamoPadre();

                    for (Iterator iterator = dataForm.getAlberoSettori().iterator(); iterator.hasNext();) {
                        SettoreBean settore = (SettoreBean) iterator.next();
                        if (settore.getCodiceRamo().equalsIgnoreCase(dataForm.getSettoreScelto().getCodiceRamo())) {
                            settore.setSelezionato(false);
                        }
                    }
                    for (Iterator iterator2 = dataForm.getAlberoSettori().iterator(); iterator2.hasNext();) {
                        SettoreBean settore = (SettoreBean) iterator2.next();
                        if (((settore.getCodiceRamoPadre() != null) && (codiceSettorePadre != null) && settore.getCodiceRamoPadre().equalsIgnoreCase(codiceSettorePadre)) || (settore.getCodiceRamoPadre() == null && codiceSettorePadre == null)) {
                            listaNodiFigli.add(settore);
                        }
                    }
                    request.setAttribute("listaFigli", listaNodiFigli);
                }
                dataForm.setTmp(dataForm.getSettoreScelto().getCodiceRamo());
                if (dataForm.getSettoreScelto().getCodiceRamoPadre() == null || dataForm.getSettoreScelto().getCodiceRamoPadre().equalsIgnoreCase("")) {
                    dataForm.setSettoreScelto(null);
                } else {
                    for (Iterator iterator = dataForm.getAlberoSettori().iterator(); iterator.hasNext();) {
                        SettoreBean settore = (SettoreBean) iterator.next();
                        if (settore.getCodiceRamo().equalsIgnoreCase(dataForm.getSettoreScelto().getCodiceRamoPadre())) {
                            dataForm.setSettoreScelto(settore);
                        }
                    }
                }

            } else if (propertyName != null && propertyName.equalsIgnoreCase("nextStep")) {
                setStep(process, request, getCurrentActivityIndex(process), getCurrentStepIndex(process) + 1);
            } else if (propertyName != null && propertyName.equalsIgnoreCase("normativa.jsp")) {
                String idx = request.getParameter("index");
                logger.debug(process.getView().getCurrentActivity().getCurrentStep().getJspPath());
                ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
                request.setAttribute("listaDocumenti", delegate.getDocumentiNormative(String.valueOf(idx)));
                request.setAttribute("SE", "true");
                showJsp(process, propertyName, false);
            } else if (propertyName.equalsIgnoreCase("bookmark")) {
                gestioneBookmark(process, request);
            }
            logger.debug("SceltaSettoreStep - loopBack method END");
        } catch (Exception e) {
            gestioneEccezioni(process, 2, e);
        }

    }

    public boolean logicalValidate(AbstractPplProcess process, IRequestWrapper request, IValidationErrors errors) throws ParserException {
        ProcessData dataForm = (ProcessData) process.getData();
        try {
            logger.debug("SceltaSettoreStep - logicalValidate method");
            String tmp = (String) request.getParameter("navigation.button.save");
            if (tmp != null) {
                request.setAttribute("navigation.button.save", "Y");
            }
            String isLoopbackString = (String) request.getUnwrappedRequest().getParameter("navigation.button.loopbackValidate$avanti");
            boolean isLoopBack = (isLoopbackString != null && !isLoopbackString.equalsIgnoreCase(""));
            boolean error = false;
            if (!isLoopBack) {
                // significa che ho cliccato sul pulsante "Avanza nella scelta settore"
                if ((dataForm.getSettoreScelto() == null) || (dataForm.getSettoreScelto().getListaCodiciFigli().size() > 0)) {
                    // controllo che ho selezionato almeno un settore e che il settore selezionato non abbia ulterioriori sottosettori
                    errors.add("error.sceltaSettoreNonTerminata");
                    error = true;
                }
            } else {
                // significa che ho cliccato sul pulsante "Continua" per passare allo step successivo
                if (dataForm.getTmp() == null || dataForm.getTmp().equalsIgnoreCase("")) {
                    errors.add("error.sceltaSettoreNulla");
                    error = true;
                }
            }
            if (error) {
                if (dataForm.getSettoreScelto() != null) {
                    ArrayList listaNodiFigli = new ArrayList();
                    for (Iterator iterator = dataForm.getAlberoSettori().iterator(); iterator.hasNext();) {
                        SettoreBean settore = (SettoreBean) iterator.next();
                        if ((settore.getCodiceRamoPadre() != null) && (settore.getCodiceRamoPadre().equalsIgnoreCase(dataForm.getSettoreScelto().getCodiceRamo()))) {
                            listaNodiFigli.add(settore);
                        }
                    }
                    request.setAttribute("listaFigli", listaNodiFigli);
                }
                return false;
            }
            resetDati(process);
            return true;
        } catch (Exception e) {
            errors.add("error.generic", "Errore interno");
            gestioneEccezioni(process, 2, e);
            dataForm.setInternalError(true);
            return false;
        }
    }

    private void resetDati(AbstractPplProcess process) {
        ProcessData dataForm = (ProcessData) process.getData();
// PC - Reiterazione domanda inizio         
//        dataForm.setAlberoOperazioni(new ArrayList());
// PC - Reiterazione domanda fine         
        dataForm.setInterventi(new ArrayList());
// PC - Reiterazione domanda inizio         
//        dataForm.setInterventiFacoltativi(new ArrayList());
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
        dataForm.setFineSceltaOp(false);
// PC - Reiterazione domanda inizio  
// tolto  dataForm.setLivelloSceltaOp(1);        
        dataForm.setLivelloSceltaOp(-1);
// PC - Reiterazione domanda fine         

    }
}
