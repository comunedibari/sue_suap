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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import noNamespace.DocumentRootDocument.DocumentRoot.DichiarazioniStatiche;

import org.apache.xerces.utils.Base64;

import it.gruppoinit.commons.Utilities;
import it.people.ActivityState;
import it.people.IValidationErrors;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AllegatoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AnagraficaBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.DichiarazioniStaticheBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.DocumentoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.HrefCampiBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.InterventoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ModelloUnicoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.NormativaBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoUnicoException;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.RiepilogoFirmatoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SportelloBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.BuilderHtml;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.ControlloFirme;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.Costant;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.ManagerDocumentiDinamici;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.process.PplData;
import it.people.process.common.entity.Attachment;
import it.people.process.common.entity.SignedAttachment;
import it.people.process.common.entity.SignedSummaryAttachment;
import it.people.process.common.entity.UnsignedSummaryAttachment;
import it.people.process.data.AbstractData;
import it.people.process.sign.ConcreteSign;
import it.people.process.sign.IPdfSignStep;
import it.people.process.sign.entity.PDFBytesSigningData;
import it.people.process.sign.entity.PrintedSigningData;
import it.people.process.sign.entity.SignedData;
import it.people.process.sign.entity.SignedInfo;
import it.people.process.sign.entity.SigningData;
import it.people.security.SignResponseManager;
import it.people.security.Exception.SignException;
import it.people.util.MessageBundleHelper;
import it.people.util.PeopleProperties;
import it.people.util.ServiceParameters;
import it.people.vsl.PendingProcessHelper;
import it.people.wrappers.HttpServletRequestDelegateWrapper;
import it.people.wrappers.IRequestWrapper;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FirmaMultiplaStep extends BaseStep implements IPdfSignStep {

    public void service(AbstractPplProcess process, IRequestWrapper request) {
        try {
            if (initialise(process, request)) {
                logger.debug("FirmaMultiplaStep - service method");
                checkRecoveryBookmark(process, request);
                ProcessData dataForm = (ProcessData) process.getData();
                resetError(dataForm);
                SportelloBean primoSportello = null;
                Set chiaviSportello = dataForm.getListaSportelli().keySet();
                int indice = 1;
                for (Iterator iterator = chiaviSportello.iterator(); iterator.hasNext();) {
                    String chiaveSportello = (String) iterator.next();
                    SportelloBean sportello = (SportelloBean) dataForm.getListaSportelli().get(chiaveSportello);
                    sportello.setIdx(indice);
                    if (indice == 1) {
                        primoSportello = sportello;
                    }
                    indice++;
                }
                String tmp = "";
                if (dataForm.getFirmaBookmark().equalsIgnoreCase(Costant.conFirmaLabel) && !dataForm.getTipoBookmark().equalsIgnoreCase(Costant.bookmarkTypeLivello2Label)) {

                    tmp = recuperaRiepilogoDinamico(request, process, primoSportello);
                    if (tmp == null || tmp.equalsIgnoreCase("KO")) {
                        request.setAttribute("dynamicDocument", "");
                    }
                }

                process.setOffLineSign(primoSportello.isOffLineSign());

                //if (!(dataForm.getFirmaBookmark().equalsIgnoreCase(Costant.conFirmaLabel) && !dataForm.getTipoBookmark().equalsIgnoreCase(Costant.bookmarkTypeLivello2Label)) || tmp.equalsIgnoreCase("KO")) {
                //recuperaRiepilogoDinamico(request, process, primoSportello);
                //request.setAttribute("dynamicDocument", "");
                ArrayList listaProcedimentiSettore = buildModelloUnicoBean(dataForm, (ArrayList) primoSportello.getCodProcedimenti());
                String oggettoPratica = buildHtmlOggetto(dataForm);
                String oggettoPraticaText = this.buildTextOggetto(dataForm);
                dataForm.setOggettoPratica(oggettoPraticaText);
                ArrayList listaAnagrafica = buildAnagrafica(dataForm);
                dataForm.getDatiTemporanei().setIndiceSportello(1);
                request.setAttribute("sportello", primoSportello);
                request.setAttribute("listaProcedimenti", listaProcedimentiSettore);
                request.setAttribute("oggettoPratica", oggettoPratica);
                request.setAttribute("oggettoPraticaText", oggettoPraticaText);
                request.setAttribute("oggettoPraticaTitoloHref", getOggettoFromTitoloHref(dataForm));
                request.setAttribute("anagrafica", listaAnagrafica);
                process.getView().setBottomNavigationBarEnabled(false);
                //}
                if (this.isSignEnabled(request, dataForm)) {
                    if (process.getView().findSummaryActivity() == null) {
                        process.setStatus("S_SIGN_CUSTOM_SUMMARY_ACTIVITY");
                    }
//					process.setSignEnabled(true);
                }

            } else {
                throw new ProcedimentoUnicoException("Sessione scaduta");
            }
            logger.debug("FirmaMultiplaStep - service method END");
        } catch (Exception e) {
            gestioneEccezioni(process, 5, e);
        }
    }

    private String recuperaRiepilogoDinamico(IRequestWrapper request, AbstractPplProcess process, SportelloBean sportello) throws Exception {
        if (session == null) {
            session = request.getUnwrappedRequest().getSession();
        }
        ProcessData dataForm = (ProcessData) process.getData();
        //ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
        //String dynamicDocument = Utilities.NVL(params.get("layoutDinamico"),"");
        //boolean isDynamic = Utilities.checked(dynamicDocument);
        String doc = "";
        //if (isDynamic){
        ManagerDocumentiDinamici mdd = new ManagerDocumentiDinamici();
        // doc = mdd.invokeDocDynModuloHTML(process,sportello.getCodiceSportello(),session);
// PC - Stampa bozza inizio        
//        byte[] docPDF = mdd.invokeDocDynModuloPDF(process,sportello.getCodiceSportello(),session, request.getUnwrappedRequest(), false);
        byte[] docPDF = mdd.invokeDocDynModuloPDF(process, sportello.getCodiceSportello(), session, request.getUnwrappedRequest(), false, false);
// PC - Stampa bozza fine        
        process.getView().setBottomNavigationBarEnabled(false);
        //}
        //if ( /*(!isDynamic) ||*/ (doc.equalsIgnoreCase("")) || (doc.equalsIgnoreCase(new String(org.apache.commons.codec.binary.Base64.encodeBase64("DYNDOC-ERROR-404".getBytes())   ))  ) ){
        if ( /*(!isDynamic) ||*/(docPDF == null) || (docPDF.length == 0)) {
            return "KO";
//        	request.setAttribute("dynamicDocument", "");
////			ArrayList listaProcedimentiSettore = buildModelloUnicoBean(dataForm, (ArrayList) sportello.getCodProcedimenti());
////			ArrayList listaSezioniDinamiche = buildSezioniDinamiche(dataForm, (ArrayList)sportello.getCodProcedimenti() );
//			dataForm.getDatiTemporanei().setIndiceSportello(1);
//			request.setAttribute("sportello",sportello);
////			request.setAttribute("listaProcedimenti", listaProcedimentiSettore);
////			request.setAttribute("listaHref", listaSezioniDinamiche);
//			process.getView().setBottomNavigationBarEnabled(false);
        } else {

            String ris = "";
            try {
//        		ris = new String(org.apache.commons.codec.binary.Base64.decodeBase64(doc.getBytes()));
                ris = new String(org.apache.commons.codec.binary.Base64.encodeBase64(docPDF));
            } catch (Exception e) {
            }

            request.setAttribute("sportello", sportello);
            request.setAttribute("dynamicDocument", "OK");
            dataForm.getDatiTemporanei().setHtmlRiepilogo(ris);
            dataForm.getDatiTemporanei().setIndiceSportello(1);

            return "OK";
        }
    }

    public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException, ServletException {
        if (propertyName == null) {
            propertyName = "";
        }
        try {
            logger.debug("FirmaMultiplaStep - loopBack method");
            ProcessData dataForm = (ProcessData) process.getData();
            if (propertyName.equalsIgnoreCase("abortMultipleSign")) {
//                for(Iterator allegatiIter = dataForm.getAllegati().iterator(); allegatiIter.hasNext();){
//                    Object obj  = allegatiIter.next();
//                    //rimuovo il riepilogo firmato digitalmente
//                    if(obj instanceof SignedSummaryAttachment || obj instanceof UnsignedSummaryAttachment){
//                        allegatiIter.remove();
//                    }
//                }
                request.setAttribute("back", "back");
                // rimuovo i riepiloghi (sia che siano firmati che non firmati)
                Set chiaviSportelli = ((ProcessData) process.getData()).getListaSportelli().keySet();
                boolean trovato = false;
                Iterator it = chiaviSportelli.iterator();
                while (it.hasNext() && !trovato) {
                    String chiaveSportelli = (String) it.next();
                    SportelloBean sportello = (SportelloBean) ((ProcessData) process.getData()).getListaSportelli().get(chiaveSportelli);
                    for (Iterator iterator = sportello.getListaAllegati().iterator(); iterator.hasNext();) {
                        Attachment attach = (Attachment) iterator.next();
                        String filePath = attach.getPath();
                        File f = new File(filePath);
                        f.delete();
                    }

                    sportello.setListaAllegati(new ArrayList());

//        			sportello.setSInfo(null);
//        			sportello.setRiepilogoNonFirmato(null);
                }

                ActivityState as = process.getView().getActivityById("2").getState();
                if (as.equals(ActivityState.ACTIVE)) {
                    setStep(process, request, 2, 1);
                } else {
                    if (dataForm.getTipoBookmark().equalsIgnoreCase(Costant.bookmarkTypeLivello2Label)) {
                        setStep(process, request, 1, 0);
                    } else {
                        setStep(process, request, 1, 1);
                    }
                }

                process.setOffLineSignDownloadedDocumentHash(null);
                process.setWaitingForOffLineSignedDocument(false);

                process.getView().setBottomNavigationBarEnabled(true);
                process.setStatus("S_EDIT");
                process.setSignEnabled(false);
            } else if (propertyName.equals("multipleSign")) {
// PC - controllo firma inizio                   
                int idx = dataForm.getDatiTemporanei().getIndiceSportello();
                SportelloBean sportello = null;
                Set chiaviSettore = dataForm.getListaSportelli().keySet();
                boolean trovato = false;
                Iterator it = chiaviSettore.iterator();
                while (it.hasNext() && !trovato) {
                    String chiaveSettore = (String) it.next();
                    sportello = (SportelloBean) dataForm.getListaSportelli().get(chiaveSettore);
                    if (idx == sportello.getIdx()) {
                        trovato = true;
                    }
                }

                ControlloFirme cf = new ControlloFirme();
                boolean controllo = cf.ControlloFirme(process, request, sportello);

                if (!controllo) {
                    process.getView().loopBack(process, request, null, 0);
                } else {
// PC - controllo firma fine   
                    String queryString = request.getUnwrappedRequest().getQueryString();
                    if (queryString != null && queryString.contains("onlinesign=true")) {
                        process.setOffLineSignDownloadedDocumentHash(null);
                        process.setWaitingForOffLineSignedDocument(false);
                        PendingProcessHelper.updateOffLineSignData(process);
                    }
                    ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
                    String param = params.get("checkboxPresaVisione");
//	        	String s = (String) request.getParameter("presaVisionePratica");
                    String s = (String) request.getParameter("data.datiTemporanei.presaVisionePDF");
                    if (s == null && param != null && param.equalsIgnoreCase("TRUE")) { // non ho spuntato il checkbox di presa visione pratica PDF
// PC - controllo firma inizio                        
//                        int idx = dataForm.getDatiTemporanei().getIndiceSportello();
//                        SportelloBean sportello = null;
//                        Set chiaviSettore = dataForm.getListaSportelli().keySet();
//                        boolean trovato = false;
//                        Iterator it = chiaviSettore.iterator();
//                        while (it.hasNext() && !trovato) {
//                            String chiaveSettore = (String) it.next();
//                            sportello = (SportelloBean) dataForm.getListaSportelli().get(chiaveSettore);
//                            if (idx == sportello.getIdx()) {
//                                trovato = true;
//                            }
//
// PC - controllo firma fine                        
                    
                        request.setAttribute("dynamicDocument", "");
                        request.setAttribute("AcceptInvalid", "true");
                        ArrayList listaProcedimentiSettore = buildModelloUnicoBean(dataForm, (ArrayList) sportello.getCodProcedimenti());
                        String oggettoPratica = buildHtmlOggetto(dataForm);
                        String oggettoPraticaText = this.buildTextOggetto(dataForm);
                        ArrayList listaAnagrafica = buildAnagrafica(dataForm);
                        request.setAttribute("sportello", sportello);
                        request.setAttribute("listaProcedimenti", listaProcedimentiSettore);
                        request.setAttribute("oggettoPratica", oggettoPratica);
                        request.setAttribute("oggettoPraticaText", oggettoPraticaText);
                        request.setAttribute("oggettoPraticaTitoloHref", getOggettoFromTitoloHref(dataForm));
                        request.setAttribute("anagrafica", listaAnagrafica);
                        process.getView().setBottomNavigationBarEnabled(false);

                    } else {
                        if (isDone(process, request, dataForm.getDatiTemporanei().getIndiceSportello())) {
                            process.getView().setBottomSaveBarEnabled(false);
                            process.getView().setBottomNavigationBarEnabled(false);
                            setStep(process, getCurrentStepIndex(process) + 1);
                            logger.info("fine firma riepiloghi");
                        }

                    }
// PC - controllo firma inizio                       
                }
// PC - controllo firma fine                
            } else if (propertyName.equals("avantiSenzaFirma")) {
                ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
                String param = params.get("checkboxPresaVisione");
                String s = (String) request.getParameter("data.datiTemporanei.presaVisionePDF");
                if (s == null && param != null && param.equalsIgnoreCase("TRUE")) { // non ho spuntato il checkbox di presa visione pratica PDF
                    int idx = dataForm.getDatiTemporanei().getIndiceSportello();
                    SportelloBean sportello = null;
                    Set chiaviSettore = dataForm.getListaSportelli().keySet();
                    boolean trovato = false;
                    Iterator it = chiaviSettore.iterator();
                    while (it.hasNext() && !trovato) {
                        String chiaveSettore = (String) it.next();
                        sportello = (SportelloBean) dataForm.getListaSportelli().get(chiaveSettore);
                        if (idx == sportello.getIdx()) {
                            trovato = true;
                        }
                    }
                    request.setAttribute("dynamicDocument", "");
                    request.setAttribute("AcceptInvalid", "true");
                    ArrayList listaProcedimentiSettore = buildModelloUnicoBean(dataForm, (ArrayList) sportello.getCodProcedimenti());
                    String oggettoPratica = buildHtmlOggetto(dataForm);
                    String oggettoPraticaText = this.buildTextOggetto(dataForm);
                    ArrayList listaAnagrafica = buildAnagrafica(dataForm);
                    request.setAttribute("sportello", sportello);
                    request.setAttribute("listaProcedimenti", listaProcedimentiSettore);
                    request.setAttribute("oggettoPraticaText", oggettoPraticaText);
                    request.setAttribute("oggettoPratica", oggettoPratica);
                    request.setAttribute("oggettoPraticaTitoloHref", getOggettoFromTitoloHref(dataForm));
                    request.setAttribute("anagrafica", listaAnagrafica);
                    process.getView().setBottomNavigationBarEnabled(false);
                } else {
                    if (isDone(process, request, false, dataForm.getDatiTemporanei().getIndiceSportello())) {
                        process.getView().setBottomSaveBarEnabled(false);
                        process.getView().setBottomNavigationBarEnabled(false);
                        setStep(process, getCurrentStepIndex(process) + 1);
                        logger.info("fine visualizzazione riepiloghi");
                    }
                }
            } else if (propertyName.equals("terminaServizio")) {
                //if (isDone(process, request, false,dataForm.getDatiTemporanei().getIndiceSportello())) {
                process.getView().setBottomSaveBarEnabled(false);
                process.getView().setBottomNavigationBarEnabled(false);
                setStep(process, getCurrentStepIndex(process) + 1);
                logger.info("fine visualizzazione riepiloghi");
                //}
            } else {
                this.service(process, request);
            }

            logger.debug("FirmaMultiplaStep - loopBack method END");
        } catch (Exception e) {
            gestioneEccezioni(process, 5, e);
        }
    }

    public boolean logicalValidate(AbstractPplProcess process, IRequestWrapper request, IValidationErrors errors) throws ParserException {
        ProcessData dataForm = (ProcessData) process.getData();
        try {
            logger.debug("FirmaMultiplaStep - logicalValidate method");
            logger.debug("FirmaMultiplaStep - logicalValidate method END");
            return true;
        } catch (Exception e) {
            errors.add("error.generic", "Errore interno");
            gestioneEccezioni(process, 5, e);
            dataForm.setInternalError(true);
            return false;
        }
    }

    private ArrayList buildModelloUnicoBean(ProcessData dataForm, ArrayList listaProcedimentiDelloSportello) {
        // recupero info 
        ArrayList listaProc = new ArrayList();
//		Set setProcedimenti = dataForm.getListaProcedimenti().keySet();
        for (Iterator iterator = listaProcedimentiDelloSportello.iterator(); iterator.hasNext();) {
            String keyProc = (String) iterator.next();
            ProcedimentoBean procedimento = (ProcedimentoBean) dataForm.getListaProcedimenti().get(keyProc);
            ModelloUnicoBean mub = new ModelloUnicoBean();
            mub.setCodice(procedimento.getCodice());
            mub.setDescrizione(procedimento.getNome());
            mub.setEnte(procedimento.getEnte());

            for (Iterator iterator2 = procedimento.getCodInterventi().iterator(); iterator2.hasNext();) {
                String codInt = (String) iterator2.next();
                for (Iterator iterator3 = dataForm.getInterventi().iterator(); iterator3.hasNext();) {
                    InterventoBean interv = (InterventoBean) iterator3.next();
                    if (interv.getCodice() != null && codInt != null && interv.getCodice().equalsIgnoreCase(codInt)) {
                        mub.addListaIntervento(interv.getDescrizione());
                        for (Iterator iterator4 = interv.getListaCodiciNormative().iterator(); iterator4.hasNext();) {
                            String codNormativa = (String) iterator4.next();
                            NormativaBean n = (NormativaBean) dataForm.getListaNormative().get(codNormativa);
                            if (n != null) {
                                mub.addListaNormative(codNormativa, n);
                            }
                        }
                        for (Iterator iterator4 = interv.getListaCodiciAllegati().iterator(); iterator4.hasNext();) {
                            String codAllegato = (String) iterator4.next();
                            DocumentoBean a = (DocumentoBean) dataForm.getListaDocRichiesti().get(codAllegato);
                            if (a != null) {
                                AllegatoBean ab = (AllegatoBean) dataForm.getListaAllegati().get(codAllegato);
                                mub.addListaDocumenti(ab);
                            }
                        }
                    }
                }
                for (Iterator iterator3 = dataForm.getInterventiFacoltativi().iterator(); iterator3.hasNext();) {
                    InterventoBean interv = (InterventoBean) iterator3.next();
                    if (interv.getCodice() != null && codInt != null && interv.getCodice().equalsIgnoreCase(codInt)) {
                        mub.addListaIntervento(interv.getDescrizione());
                        for (Iterator iterator4 = interv.getListaCodiciNormative().iterator(); iterator4.hasNext();) {
                            String codNormativa = (String) iterator4.next();
                            NormativaBean n = (NormativaBean) dataForm.getListaNormative().get(codNormativa);
                            if (n != null) {
                                mub.addListaNormative(codNormativa, n);
                            }
                        }
                        for (Iterator iterator4 = interv.getListaCodiciAllegati().iterator(); iterator4.hasNext();) {
                            String codAllegato = (String) iterator4.next();
                            DocumentoBean a = (DocumentoBean) dataForm.getListaDocRichiesti().get(codAllegato);
                            if (a != null) {
                                AllegatoBean ab = (AllegatoBean) dataForm.getListaAllegati().get(codAllegato);
                                mub.addListaDocumenti(ab);
                            }
                        }
                    }
                }
                Set sett = mub.getListaNormative().keySet();
                for (Iterator iterator3 = sett.iterator(); iterator3.hasNext();) {
                    String keyNormativa = (String) iterator3.next();
                    NormativaBean norm = (NormativaBean) mub.getListaNormative().get(keyNormativa);
                    mub.addListaTmp(norm);
                }
            }
            listaProc.add(mub);
        }
        return listaProc;
    }

    private String buildTextOggetto(ProcessData dataForm) {
        String oggettoPratica = "";
        BuilderHtml bh = new BuilderHtml();
        oggettoPratica = bh.buildTextOggetto(dataForm);
        return oggettoPratica;
    }

    private String buildHtmlOggetto(ProcessData dataForm) {
        String oggettoPratica = "";
        BuilderHtml bh = new BuilderHtml();
        oggettoPratica = bh.buildHtmlOggetto(dataForm);
        return oggettoPratica;
    }

    private String getOggettoFromTitoloHref(ProcessData dataForm) {
        String oggettoPratica = "";
        BuilderHtml bh = new BuilderHtml();
        oggettoPratica = bh.buildOggettoFromTitoloHref(dataForm);
        return oggettoPratica;
    }

    private boolean isDone(AbstractPplProcess process, IRequestWrapper request, int idxSportello) throws Exception {
        return isDone(process, request, true, idxSportello);
    }

    private boolean isDone(AbstractPplProcess process, IRequestWrapper request, boolean saveSD, int idxSportello) throws Exception {
        HttpSession session = request.getUnwrappedRequest().getSession();
        ProcessData data = (ProcessData) process.getData();
//        ProcessData dataPerDestinatario  = null;
//        Integer index = (Integer) session.getAttribute("indexRiepilogo");
        boolean ret = true;
        if (idxSportello == 0) {
            logger.debug("Impossibile recuperare dalla sessione l'indice del riepilogo corrente.");
            return true;
        }
        int idx = idxSportello;
        int totale = data.getListaSportelli().size();
        logger.debug("Firmato il riepilogo " + (idx) + " di " + totale);

        //dataPerDestinatario = (ProcessData) data.getProcessDataPerDestinatario().get(idx);
        if (saveSD) {
            saveSignedData(data, request, process, idxSportello);
        } else {
            saveUnsignedData(data, request, process, idx);
        }
        if (idx == totale) {
            ret = true;
        } else {
//            dataPerDestinatario = (ProcessData) data.getProcessDataPerDestinatario().get(idx);
//            session.setAttribute("dataPerDestinatario", dataPerDestinatario);
//            session.setAttribute("indexRiepilogo", new Integer(idx));
            idxSportello++;
            data.getDatiTemporanei().setIndiceSportello(data.getDatiTemporanei().getIndiceSportello() + 1);
            // TODO chiamata al WS per recuperare l'HTML
            SportelloBean sportello = null;
            Set chiaviSettore = data.getListaSportelli().keySet();
            boolean trovato = false;
            Iterator it = chiaviSettore.iterator();
            while (it.hasNext() && !trovato) {
                String chiaveSettore = (String) it.next();
                sportello = (SportelloBean) data.getListaSportelli().get(chiaveSettore);
                if (idxSportello == sportello.getIdx()) {
                    trovato = true;
                }
            }
//            ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
//            String dynamicDocument = Utilities.NVL(params.get("layoutDinamico"),"");
//            boolean isDynamic = Utilities.checked(dynamicDocument);
            String doc = "";

            String tmp = "";
            if (data.getFirmaBookmark().equalsIgnoreCase(Costant.conFirmaLabel) && !data.getTipoBookmark().equalsIgnoreCase(Costant.bookmarkTypeLivello2Label)) {
                ManagerDocumentiDinamici mdd = new ManagerDocumentiDinamici();

                // tmp = mdd.invokeDocDynModuloHTML(process,sportello.getCodiceSportello(),session);
// PC - Stampa bozza inizio                        
//        		byte[] tmpByte = mdd.invokeDocDynModuloPDF(process,sportello.getCodiceSportello(),session, request.getUnwrappedRequest(), false);
                byte[] tmpByte = mdd.invokeDocDynModuloPDF(process, sportello.getCodiceSportello(), session, request.getUnwrappedRequest(), false, false);
// PC - Stampa bozza fine                        
                // if ((!Utilities.isset(tmp)) || tmp.equalsIgnoreCase("KO")){
                if ((tmpByte == null) || (tmpByte.length == 0)) {
                    request.setAttribute("dynamicDocument", "");
                    request.setAttribute("sportello", sportello);
                } else {
                    String ris = "";
                    try {
                        // ris = new String(org.apache.commons.codec.binary.Base64.decodeBase64(doc.getBytes()));
                        ris = new String(org.apache.commons.codec.binary.Base64.encodeBase64(tmpByte));
                    } catch (Exception e) {
                    }
                    request.setAttribute("sportello", sportello);
                    data.getDatiTemporanei().setHtmlRiepilogo(ris);
                    request.setAttribute("dynamicDocument", "OK");
                }
            }

            //if (!(data.getFirmaBookmark().equalsIgnoreCase(Costant.conFirmaLabel) && !data.getTipoBookmark().equalsIgnoreCase(Costant.bookmarkTypeLivello2Label)) || tmp.equalsIgnoreCase("KO")) {
            //recuperaRiepilogoDinamico(request, process, primoSportello);
            //	request.setAttribute("dynamicDocument", "");
            ArrayList listaProcedimentiSettore = buildModelloUnicoBean(data, (ArrayList) sportello.getCodProcedimenti());
            String oggettoPratica = buildHtmlOggetto(data);
            ArrayList listaAnagrafica = buildAnagrafica(data);
            request.setAttribute("sportello", sportello);
            request.setAttribute("listaProcedimenti", listaProcedimentiSettore);
            request.setAttribute("oggettoPratica", oggettoPratica);
            request.setAttribute("anagrafica", listaAnagrafica);
            //}
//            
//            
////            if (isDynamic){
//	        	ManagerDocumentiDinamici mdd = new ManagerDocumentiDinamici();
//	        	doc = mdd.invokeDocDynModuloHTML(process,sportello.getCodiceSportello(),session);
////            }
//            if ( /*(!isDynamic) || */(!Utilities.isset(doc)) || (doc.equalsIgnoreCase(new String(org.apache.commons.codec.binary.Base64.encodeBase64("DYNDOC-ERROR-404".getBytes())   ))  ) ){
//            	request.setAttribute("dynamicDocument", "");
////				ArrayList listaProcedimentiSettore = buildModelloUnicoBean(data, (ArrayList) sportello.getCodProcedimenti());
////				ArrayList listaSezioniDinamiche = buildSezioniDinamiche(data, (ArrayList)sportello.getCodProcedimenti() );
//				request.setAttribute("sportello",sportello);
////				request.setAttribute("listaProcedimenti", listaProcedimentiSettore);
////				request.setAttribute("listaHref", listaSezioniDinamiche);
//            } else {
//            	String ris="";
//            	try {
//            		ris = new String(org.apache.commons.codec.binary.Base64.decodeBase64(doc.getBytes()));
//            	} catch (Exception e){}
//            	request.setAttribute("sportello",sportello);
//            	data.getDatiTemporanei().setHtmlRiepilogo(ris);
//            	request.setAttribute("dynamicDocument", "OK");
//            }

            ret = false;
        }
        logger.debug("firmati tutti i riepiloghi " + ret);
        return ret;
    }

    private void saveSignedData(PplData data, IRequestWrapper request, AbstractPplProcess pplProcess, int idxSportello) {
        SignResponseManager srmgr = null;
        logger.debug("recupero le informazioni di firma");
        SignedInfo sInfo = null;

        if (pplProcess.isOffLineSign() && pplProcess.isWaitingForOffLineSignedDocument() && pplProcess.getOffLineSignDownloadedDocumentHash() != null
                && !pplProcess.getOffLineSignDownloadedDocumentHash().equalsIgnoreCase("")) {
            srmgr = new SignResponseManager(pplProcess);
        } else {
            srmgr = new SignResponseManager(request.getUnwrappedRequest());
        }

        try {
            sInfo = srmgr.process();
        } catch (SignException e) {
            logger.error(e);
        }
        if (sInfo == null) {
            logger.error("impossibile recuperare le informazioni di firma");
        }

//        ControlloFirme cf = new ControlloFirme();
//
//        try {
//            HashMap listaFirme = cf.ControlloFirme(sInfo.getPkcs7Data());
//            String offLineSignFileUploadLabel = MessageBundleHelper.message("label.offLineSign.uploadSignedDocument", null, pplProcess.getProcessName(), pplProcess.getCommune().getKey(),
//                    pplProcess.getContext().getLocale());
//            pplProcess.addServiceError(MessageBundleHelper.message("errors.offLineSign.documentNotDownloaded", new Object[]{offLineSignFileUploadLabel},
//                    pplProcess.getProcessName(), pplProcess.getCommune().getKey(), pplProcess.getContext().getLocale()));
//            return false;
//        } catch (Exception ex) {
//
//            Logger.getLogger(FirmaMultiplaStep.class.getName()).log(Level.SEVERE, null, ex);
//        }
        String metaData = "";
        String name = getUniqueName();
        SignedData sd = new SignedData();
        String fullname = "";
        try {
            String path = PeopleProperties.UPLOAD_DIRECTORY.getValueString(pplProcess.getCommune().getKey());
            fullname = path + File.separator + name /* + ".html.p7m"*/;

//			String nuovaGestioneFile = request.getUnwrappedRequest().getSession().getServletContext().getInitParameter("remoteAttachFile");
//        	if (nuovaGestioneFile!=null && nuovaGestioneFile.equalsIgnoreCase("true")){
            if (!pplProcess.isEmbedAttachmentInXml()) {
                File rootDirNodo = new File(path + File.separator + pplProcess.getCommune().getKey());
                if (!rootDirNodo.exists()) {
                    rootDirNodo.mkdir();
                }
                File rootDirPratica = new File(path + File.separator + pplProcess.getCommune().getKey() + File.separator + pplProcess.getIdentificatoreProcedimento());
                if (!rootDirPratica.exists()) {
                    rootDirPratica.mkdirs();
                }
                if (((ProcessData) pplProcess.getData()).getListaSportelli().size() > 1) {
                    File rootDirSubPratica = new File(path + File.separator + pplProcess.getCommune().getKey() + File.separator + pplProcess.getIdentificatoreProcedimento() + File.separator + String.valueOf(idxSportello));
                    if (!rootDirSubPratica.exists()) {
                        rootDirSubPratica.mkdirs();
                    }
                    path = path + File.separator + pplProcess.getCommune().getKey() + File.separator + pplProcess.getIdentificatoreProcedimento() + File.separator + String.valueOf(idxSportello);
                } else {
                    path = path + File.separator + pplProcess.getCommune().getKey() + File.separator + pplProcess.getIdentificatoreProcedimento();
                }
                String uniqueID = name;
                fullname = path + File.separator + uniqueID + "_" + RIEPILOGO_FIRMATO;
                String urlAllegatiService = "";
                if (request != null && request.getUnwrappedRequest().getSession() != null && request.getUnwrappedRequest().getSession().getServletContext() != null) {
                    urlAllegatiService = request.getUnwrappedRequest().getSession().getServletContext().getInitParameter("UrlRemoteAttachFile");
                }
                String idProc = pplProcess.getIdentificatoreProcedimento();
                if (((ProcessData) pplProcess.getData()).getListaSportelli().size() > 1) {
                    idProc = idProc + "/" + idxSportello;
                }
                metaData = pplProcess.getCommune().getKey() + "||" + idProc + "||" + uniqueID + "||" + urlAllegatiService + "||" + "getFile";
                //unsignedAttachment.setData(metaData);
            }

            sd.setPath(fullname);

            File f = new File(fullname);
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(sInfo.getPkcs7Data());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SignedSummaryAttachment signedAttachment = null;
        try {
            signedAttachment = new SignedSummaryAttachment(RIEPILOGO_FIRMATO, fullname/*PeopleProperties.UPLOAD_DIRECTORY.getValueString()
                     + System.getProperty("file.separator")
                     + RIEPILOGO_FIRMATO*/, sInfo.getEncodedSing());
        } catch (Exception e) {
            logger.error(e);
        }
        signedAttachment.setDescrizione(RIEPILOGO_FIRMATO);

//		String nuovaGestioneFile = request.getUnwrappedRequest().getSession().getServletContext().getInitParameter("remoteAttachFile");
//    	if (nuovaGestioneFile!=null && nuovaGestioneFile.equalsIgnoreCase("true")){
        if (!pplProcess.isEmbedAttachmentInXml()) {
            signedAttachment.setData(metaData);
        } else {
            signedAttachment.setData(new String(Base64.encode(sInfo.getPkcs7Data())));
        }

        signedAttachment.setFileLenght(sInfo.getPkcs7Data().length);
//        System.out.println("riepilogo : ");
//        System.out.println(new String(sInfo.getData()));
//        System.out.println("end.");
        signedAttachment.setSign(new String(Base64.encode(sInfo.getData())));
        //((AbstractData) data).addAllegati(signedAttachment);
        logger.debug("aggiunto agli allegati per destinatario il riepilogo firmato (SignedAttachment)");

        Set chiaviSettore = ((ProcessData) pplProcess.getData()).getListaSportelli().keySet();
        boolean trovato = false;
        SportelloBean sportello = null;
        Iterator it = chiaviSettore.iterator();
        while (it.hasNext() && !trovato) {
            String chiaveSettore = (String) it.next();
            sportello = (SportelloBean) ((ProcessData) pplProcess.getData()).getListaSportelli().get(chiaveSettore);
            if (sportello.getIdx() == idxSportello) {
                trovato = true;
//				RiepilogoFirmatoBean rfb = new RiepilogoFirmatoBean();
//				rfb.setM_dataString(new String(Base64.encode(sInfo.getData())));
//				rfb.setM_pkcs7DataString(new String(Base64.encode(sInfo.getPkcs7Data())));
//				rfb.setM_signString(new String(Base64.encode(sInfo.getSign())));
//				rfb.setM_userDN(sInfo.getUserDN());
//				sportello.setSInfo(rfb);
            }
        }

        if (trovato) {
            sportello.addListaAllegati(signedAttachment);
        } else { // se non trovo lo sportello lo metto nel contenitore globale degli allegati (questo caso per� non si dovrebbe verificare mai!)
            ((AbstractData) data).addAllegati(signedAttachment);
        }
        logger.debug("aggiunto l'oggetto SignedInfo al processData.");
    }

    private String getUniqueName() {
        return Long.toString((new Date()).getTime());
    }

    private void saveUnsignedData(PplData data, IRequestWrapper request, AbstractPplProcess pplProcess, int idxSportello) {
        UnsignedSummaryAttachment unsignedAttachment = null;
        try {
            unsignedAttachment = new UnsignedSummaryAttachment("Allegato non firmato");
        } catch (Exception e) {
            logger.error(e);
        }

        // INIZIO MODIFICA
        Set cs = ((ProcessData) pplProcess.getData()).getListaSportelli().keySet();
        boolean trov = false;
        Iterator it = cs.iterator();
        SportelloBean sport = null;
        while (it.hasNext() && !trov) {
            String chiaveSettore = (String) it.next();
            sport = (SportelloBean) ((ProcessData) pplProcess.getData()).getListaSportelli().get(chiaveSettore);
            if (sport.getIdx() == idxSportello) {
                trov = true;
                //sportello.setRiepilogoNonFirmato(unsignedAttachment);
            }
        }
        if (trov) {
            try {
                ManagerDocumentiDinamici mdd = new ManagerDocumentiDinamici();
// PC - Stampa bozza inizio                        
//	        	byte[] pdfTemp = mdd.invokeDocDynModuloPDF(pplProcess, sport.getCodiceSportello(),request.getUnwrappedRequest().getSession(), request.getUnwrappedRequest(), false);
                byte[] pdfTemp = mdd.invokeDocDynModuloPDF(pplProcess, sport.getCodiceSportello(), request.getUnwrappedRequest().getSession(), request.getUnwrappedRequest(), false, false);
// PC - Stampa bozza fine                        
                //byte[] pdfTemp = org.apache.commons.codec.binary.Base64.decodeBase64(((ProcessData)pplProcess.getData()).getDatiTemporanei().getHtmlRiepilogo().getBytes());
                unsignedAttachment.setDescrizione("riepilogo.pdf");
                unsignedAttachment.setName("riepilogo.pdf");
                unsignedAttachment.setFileLenght(pdfTemp.length);
                String path = PeopleProperties.UPLOAD_DIRECTORY.getValueString(pplProcess.getCommune().getKey());
                String nome = "riepilogo_" + ((ProcessData) pplProcess.getData()).getIdentificatorePeople().getIdentificatoreProcedimento() + "_" + ((ProcessData) pplProcess.getData()).getDatiTemporanei().getIndiceSportello() + ".pdf";
                String nomeCompleto = path + System.getProperty("file.separator") + nome;
//				String nuovaGestioneFile = request.getUnwrappedRequest().getSession().getServletContext().getInitParameter("remoteAttachFile");
                String metaData = "";
//	        	if (nuovaGestioneFile!=null && nuovaGestioneFile.equalsIgnoreCase("true")){
                if (!pplProcess.isEmbedAttachmentInXml()) {
                    File rootDirNodo = new File(path + File.separator + pplProcess.getCommune().getKey());
                    if (!rootDirNodo.exists()) {
                        rootDirNodo.mkdir();
                    }
                    File rootDirPratica = new File(path + File.separator + pplProcess.getCommune().getKey() + File.separator + pplProcess.getIdentificatoreProcedimento());
                    if (!rootDirPratica.exists()) {
                        rootDirPratica.mkdirs();
                    }
                    if (((ProcessData) pplProcess.getData()).getListaSportelli().size() > 1) {
                        File rootDirSubPratica = new File(path + File.separator + pplProcess.getCommune().getKey() + File.separator + pplProcess.getIdentificatoreProcedimento() + File.separator + String.valueOf(sport.getIdx()));
                        if (!rootDirSubPratica.exists()) {
                            rootDirSubPratica.mkdirs();
                        }
                        path = path + File.separator + pplProcess.getCommune().getKey() + File.separator + pplProcess.getIdentificatoreProcedimento() + File.separator + String.valueOf(sport.getIdx());
                    } else {
                        path = path + File.separator + pplProcess.getCommune().getKey() + File.separator + pplProcess.getIdentificatoreProcedimento();
                    }
                    String uniqueID = Long.toString((new Date()).getTime());
                    nomeCompleto = path + File.separator + uniqueID + "_" + nome;
                    String urlAllegatiService = "";
                    if (request != null && request.getUnwrappedRequest().getSession() != null && request.getUnwrappedRequest().getSession().getServletContext() != null) {
                        urlAllegatiService = request.getUnwrappedRequest().getSession().getServletContext().getInitParameter("UrlRemoteAttachFile");
                    }
                    String idProc = pplProcess.getIdentificatoreProcedimento();
                    if (((ProcessData) pplProcess.getData()).getListaSportelli().size() > 1) {
                        idProc = idProc + "/" + String.valueOf(sport.getIdx());
                    }
                    metaData = pplProcess.getCommune().getKey() + "||" + idProc + "||" + uniqueID + "||" + urlAllegatiService + "||" + "getFile";
                }
                FileOutputStream fileoutputstream = new FileOutputStream(nomeCompleto);
                fileoutputstream.write(pdfTemp);
                fileoutputstream.close();
                String b64 = new String(org.apache.xerces.utils.Base64.encode(pdfTemp));
//				nuovaGestioneFile = request.getUnwrappedRequest().getSession().getServletContext().getInitParameter("remoteAttachFile");
//	        	if (nuovaGestioneFile!=null && nuovaGestioneFile.equalsIgnoreCase("true")){
                if (!pplProcess.isEmbedAttachmentInXml()) {
                    unsignedAttachment.setData(metaData);
                } else {
                    unsignedAttachment.setData(b64);
                }
                unsignedAttachment.setPath(nomeCompleto);
                sport.addListaAllegati(unsignedAttachment);
            } catch (Exception e) {
            }
        }

        /*        
         unsignedAttachment.setDescrizione("riepilogo.html");
         unsignedAttachment.setName("riepilogo.html");
         String unsignedData = "";
         ArrayList listaAnagrafiche = buildAnagrafica((ProcessData)pplProcess.getData());
         unsignedData = recuperaRiepilogoStatico(request,pplProcess,idxSportello,listaAnagrafiche);
         unsignedAttachment.setData(unsignedData);  
         unsignedAttachment.setFileLenght(unsignedData.getBytes().length);
        
        
         String name = getUniqueName();
         String fullname = "";
         try {
         String path = PeopleProperties.UPLOAD_DIRECTORY.getValueString(pplProcess.getCommune().getKey());
         fullname = path + File.separator + name;
         String nuovaGestioneFile = request.getUnwrappedRequest().getSession().getServletContext().getInitParameter("remoteAttachFile");
         if (nuovaGestioneFile!=null && nuovaGestioneFile.equalsIgnoreCase("true")){
         File rootDirNodo = new File(path+File.separator+pplProcess.getCommune().getKey());
         if (!rootDirNodo.exists()){
         rootDirNodo.mkdir();
         }
         File rootDirPratica = new File(path+File.separator+pplProcess.getCommune().getKey()+File.separator+pplProcess.getIdentificatoreProcedimento());
         if (!rootDirPratica.exists()){
         rootDirPratica.mkdirs();
         }
         if (((ProcessData)pplProcess.getData()).getListaSportelli().size()>1){
         File rootDirSubPratica = new File(path+File.separator+pplProcess.getCommune().getKey()+File.separator+pplProcess.getIdentificatoreProcedimento()+File.separator+String.valueOf(idxSportello));
         if (!rootDirSubPratica.exists()){
         rootDirSubPratica.mkdirs();
         }
         path = path+File.separator+pplProcess.getCommune().getKey()+File.separator+pplProcess.getIdentificatoreProcedimento()+File.separator+String.valueOf(idxSportello);
         } else {
         path = path+File.separator+pplProcess.getCommune().getKey()+File.separator+pplProcess.getIdentificatoreProcedimento();
         }
         String uniqueID = getUniqueName();
         fullname = path+File.separator+uniqueID+"_"+unsignedAttachment.getName();
         String urlAllegatiService = "";
         if (request!=null && request.getUnwrappedRequest().getSession()!=null && request.getUnwrappedRequest().getSession().getServletContext()!=null) {
         urlAllegatiService = request.getUnwrappedRequest().getSession().getServletContext().getInitParameter("UrlRemoteAttachFile");
         }
         String idProc = pplProcess.getIdentificatoreProcedimento();
         if (((ProcessData)pplProcess.getData()).getListaSportelli().size()>1){
         idProc = idProc+"/"+idxSportello;
         }
         String metaData = pplProcess.getCommune().getKey()+"||"+idProc+"||"+uniqueID+"||"+urlAllegatiService+"||"+"getFile";
         unsignedAttachment.setData(metaData);
               
         } 
         File f = new File(fullname);
         FileOutputStream fos = new FileOutputStream(f);
         fos.write(unsignedData.getBytes());
         fos.close();
         } catch (Exception ex) {
         ex.printStackTrace();
         }
        
         try{
         unsignedAttachment.setPath(fullname);
         } catch (Exception e) {
         logger.error(e);
         }

         Set chiaviSettore = ((ProcessData)pplProcess.getData()).getListaSportelli().keySet();
         boolean trovato=false;
         Iterator it = chiaviSettore.iterator();
         SportelloBean sportello=null;
         while (it.hasNext() && !trovato){
         String chiaveSettore = (String) it.next();
         sportello = (SportelloBean) ((ProcessData)pplProcess.getData()).getListaSportelli().get(chiaveSettore);
         if (sportello.getIdx() == idxSportello) {
         trovato=true;
         //sportello.setRiepilogoNonFirmato(unsignedAttachment);
         }
         }
         if (trovato){
         sportello.addListaAllegati(unsignedAttachment);
         } else { // se non trovo lo sportello lo metto nel contenitore globale degli allegati (questo caso per� non si dovrebbe verificare mai!)
         ((AbstractData) data).addAllegati(unsignedAttachment);
         }
         */
        logger.debug("aggiunto agli allegati per destinatario il riepilogo non firmato (UnsignedAttachment)");
        //((ProcessData) data).setunSignedInfo(sInfo);
        logger.debug("aggiunto l'oggetto SignedInfo al processData.");
    }

    /* (non-Javadoc)
     * @see it.people.process.sign.IPdfSignStep#getSigningData(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public SigningData getSigningData(AbstractPplProcess pplProcess, HttpServletRequest request,
            HttpServletResponse response) {

        ProcessData dataForm = (ProcessData) pplProcess.getData();
        return new PDFBytesSigningData(dataForm.getDatiTemporanei().getHtmlRiepilogo().getBytes());

    }

    private boolean isSignEnabled(IRequestWrapper request, ProcessData dataForm) {

        String idBookmark = request.getUnwrappedRequest().getParameter("idBookmark");
        if (idBookmark != null) {
            return dataForm.getFirmaBookmark().equalsIgnoreCase(Costant.conFirmaLabel) && dataForm.getTipoBookmark().equalsIgnoreCase(Costant.bookmarkTypeCompleteLabel);
        } else {
            return dataForm.getDatiTemporanei().getParametriPU().isAbilitaFirma() && dataForm.getDatiTemporanei().getParametriPU().getTipo().equalsIgnoreCase(Costant.bookmarkTypeCompleteLabel);
        }

    }

}
