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

import it.gruppoinit.commons.DBCPManager;
import it.gruppoinit.commons.Utilities;
import it.people.IValidationErrors;
import it.people.content.ContentImpl;
import it.people.core.ContentManager;
import it.people.core.PeopleContext;
import it.people.core.ProcessManager;
import it.people.core.persistence.exception.peopleException;
import it.people.envelope.EnvelopeFactory_modellazioneb116_envelopeb002;
import it.people.envelope.IEnvelopeFactory;
import it.people.envelope.IRequestEnvelope;
import it.people.envelope.beans.ContenutoBusta;
import it.people.envelope.util.EnvelopeHelper;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.BookmarkDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.ProcedimentoUnicoDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.InterventoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoUnicoException;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.RiepilogoFirmatoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SportelloBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.Bean2XML;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.ManagerDocumentiDinamici;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.process.common.entity.Attachment;
import it.people.process.common.entity.SignedAttachment;
import it.people.process.common.entity.SignedSummaryAttachment;
import it.people.process.common.entity.UnsignedSummaryAttachment;
import it.people.process.data.AbstractData;
import it.people.process.sign.entity.SignedInfo;
import it.people.sirac.core.SiracHelper;
import it.people.util.PeopleProperties;
import it.people.util.ProcessUtils;
import it.people.util.ServiceParameters;
import it.people.vsl.PipelineData;
import it.people.vsl.PipelineDataImpl;
import it.people.vsl.PipelineFactory;
import it.people.wrappers.IRequestWrapper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;

import org.apache.xerces.utils.Base64;

public class InvioMultiploStep extends BaseStep {

    public void service(AbstractPplProcess process, IRequestWrapper request) {
        try {
            if (initialise(process, request)) {
                logger.debug("InvioMultiploStep - service method");
                checkRecoveryBookmark(process, request);
                ProcessData dataForm = (ProcessData) process.getData();
                resetError(dataForm);

                if (!checkValiditaRiepiloghi(dataForm)) {
                    request.setAttribute("ERRORE_RIEPILOGO_FIRMA", "error");
                    dataForm.getDatiTemporanei().setIndiceSportello(1);
                    Set chiaviSportello = dataForm.getListaSportelli().keySet();
                    for (Iterator iterator = chiaviSportello.iterator(); iterator.hasNext();) {
                        String chiaveSportello = (String) iterator.next();
                        SportelloBean sportello = (SportelloBean) dataForm.getListaSportelli().get(chiaveSportello);
//						sportello.setSInfo(null);
//						sportello.setRiepilogoNonFirmato(null);
                        sportello.setListaAllegati(new ArrayList());
                    }
                    // recuperaRiepilogo(request, process, primoSportello);
                    setStep(process, request, getCurrentActivityIndex(process), 0);
                    // showJsp(process, path, completePath)
                    return;
                }

                dataForm.setCredenzialiBase64(SiracHelper.getCredenzialiCertificateUtenteAutenticatoBase64(request.getUnwrappedRequest()));
                process.getView().setBottomSaveBarEnabled(false);

            } else {
                throw new ProcedimentoUnicoException("Sessione scaduta");
            }
            logger.debug("InvioMultiploStep - service method END");
        } catch (Exception e) {
            gestioneEccezioni(process, 5, e);
        }
    }

    public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException, ServletException {
        try {
            if (propertyName != null && !propertyName.equalsIgnoreCase("invio")) {
                setStep(process, request, getCurrentActivityIndex(process), 1);
                return;
            }
            logger.debug("InvioMultiploStep - loopBack method");
//			Iterator itt = ((ProcessData)process.getData()).getAllegati().iterator();
//			while(itt.hasNext()){
//				Attachment attach = (Attachment) itt.next();
//				attach.setName(attach.getDescrizione());
//			}
            process.getView().setBottomSaveBarEnabled(false);
            if (process.isInizialized()) {
                ((ProcessData) process.getData()).getDatiTemporanei().setHtmlRiepilogo("");
                // blocco inserito per ovviare a problemi di serializzazione del process data (tolgo momentaneamente il riepilogo dal processData - sportello)
                HashMap mappaRiepiloghiTMP = new HashMap();
                Set listaKeySportelliTMP = ((ProcessData) process.getData()).getListaSportelli().keySet();
                for (Iterator iterator = listaKeySportelliTMP.iterator(); iterator.hasNext();) {
                    String codiceSportello = (String) iterator.next();
                    SportelloBean sportello = (SportelloBean) ((ProcessData) process.getData()).getListaSportelli().get(codiceSportello);
                    mappaRiepiloghiTMP.put(codiceSportello, sportello.getListaAllegati());
                    sportello.setListaAllegati(null);
                }
                // fine blocco
                PeopleContext pplContext = PeopleContext.create(request.getUnwrappedRequest());
                try {
                    ProcessManager.getInstance().set(pplContext, process);
                } catch (peopleException e) {
                    throw new ProcedimentoUnicoException("Errore nel salvataggio temporaneo per l'invio");
                }
                ((ProcessData) process.getData()).getDatiTemporanei().setIndiceSportello(1);
                // blocco inserito per ovviare a problemi di serializzazione del process data (rimetto il riepilogo nel processData- sportello)
                listaKeySportelliTMP = ((ProcessData) process.getData()).getListaSportelli().keySet();
                for (Iterator iterator = listaKeySportelliTMP.iterator(); iterator.hasNext();) {
                    String codiceSportello = (String) iterator.next();
                    SportelloBean sportello = (SportelloBean) ((ProcessData) process.getData()).getListaSportelli().get(codiceSportello);
                    ArrayList listaRiepiloghi = (ArrayList) mappaRiepiloghiTMP.get(codiceSportello);
                    sportello.setListaAllegati(listaRiepiloghi);
                }
                // fine blocco
                PipelineData pd = null;
                Set listaKeySportelli = ((ProcessData) process.getData()).getListaSportelli().keySet();
                for (Iterator iterator = listaKeySportelli.iterator(); iterator.hasNext();) {
                    String codiceSportello = (String) iterator.next();
                    String metaData = "";
                    String nomeCompleto = "";
                    // repupero PDF
                    // metto dentro lista
                    ManagerDocumentiDinamici mdd = new ManagerDocumentiDinamici();
// PC - Stampa bozza inizio                     
//                    byte[] pdfTemp = mdd.invokeDocDynModuloPDF(process, codiceSportello, request.getUnwrappedRequest().getSession(), request.getUnwrappedRequest(), false);
                    byte[] pdfTemp = mdd.invokeDocDynModuloPDF(process, codiceSportello, request.getUnwrappedRequest().getSession(), request.getUnwrappedRequest(), false, false);                    
// PC - Stampa bozza fine                     
                    SportelloBean sportello = (SportelloBean) ((ProcessData) process.getData()).getListaSportelli().get(codiceSportello);

                    if (((ProcessData) process.getData()).getIdBookmark() != null
                            && !((ProcessData) process.getData()).getIdBookmark().equalsIgnoreCase("")) {
                        if (this.db == null) {
                            session = request.getUnwrappedRequest().getSession();
                            this.db = (DBCPManager) session.getAttribute("DB");
                            if (this.db == null) {
                                ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
                                String jndi = params.get("dbjndi");
                                this.db = new DBCPManager(jndi);
                            }
                        }
                        if (this.db != null) {
                            BookmarkDAO bookmarkDao = new BookmarkDAO(db, "it");
                            sportello = bookmarkDao.updateDatiSportello(sportello, (ProcessData) process.getData());
                        }
                    } else {
                        if (this.db == null) {
                            session = request.getUnwrappedRequest().getSession();
                            this.db = (DBCPManager) session.getAttribute("DB");
                            if (this.db == null) {
                                ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
                                String jndi = params.get("dbjndi");
                                this.db = new DBCPManager(jndi);
                            }
                        }
                        if (this.db != null) {
                            ProcedimentoUnicoDAO procedimentoUnicoDao = new ProcedimentoUnicoDAO(db, "it");
                            sportello = procedimentoUnicoDao.updateDatiSportello(sportello, (ProcessData) process.getData());
                        }
                    }

                    //byte[] pdfTemp = riepilogoPDF("C:\\Temp\\"+"pippo"+((SportelloBean) ((ProcessData)process.getData()).getListaSportelli().get(codiceSportello)).getIdx()+".pdf");
                    String path = PeopleProperties.UPLOAD_DIRECTORY.getValueString(process.getCommune().getKey());
                    String nome = "riepilogo_" + ((ProcessData) process.getData()).getIdentificatorePeople().getIdentificatoreProcedimento() + "_" + ((ProcessData) process.getData()).getDatiTemporanei().getIndiceSportello() + ".pdf";
//					String nuovaGestioneFile = request.getUnwrappedRequest().getSession().getServletContext().getInitParameter("remoteAttachFile");

                    /*
                     nomeCompleto = path+System.getProperty("file.separator")+nome;
					
                     if (nuovaGestioneFile!=null && nuovaGestioneFile.equalsIgnoreCase("true")){
                     File rootDirNodo = new File(path+File.separator+process.getCommune().getKey());
                     if (!rootDirNodo.exists()){
                     rootDirNodo.mkdir();
                     }
                     File rootDirPratica = new File(path+File.separator+process.getCommune().getKey()+File.separator+process.getIdentificatoreProcedimento());
                     if (!rootDirPratica.exists()){
                     rootDirPratica.mkdirs();
                     }
                     if (((ProcessData)process.getData()).getListaSportelli().size()>1){
                     File rootDirSubPratica = new File(path+File.separator+process.getCommune().getKey()+File.separator+process.getIdentificatoreProcedimento()+File.separator+String.valueOf(sportello.getIdx()));
                     if (!rootDirSubPratica.exists()){
                     rootDirSubPratica.mkdirs();
                     }
                     path = path+File.separator+process.getCommune().getKey()+File.separator+process.getIdentificatoreProcedimento()+File.separator+String.valueOf(sportello.getIdx());
                     } else {
                     path = path+File.separator+process.getCommune().getKey()+File.separator+process.getIdentificatoreProcedimento();
                     }
                     String uniqueID = Long.toString((new Date()).getTime());
                     nomeCompleto = path+File.separator+uniqueID+"_"+nome;
                     String urlAllegatiService = "";
                     if (request!=null && request.getUnwrappedRequest().getSession()!=null && request.getUnwrappedRequest().getSession().getServletContext()!=null) {
                     urlAllegatiService = request.getUnwrappedRequest().getSession().getServletContext().getInitParameter("UrlRemoteAttachFile");
                     }
                     String idProc = process.getIdentificatoreProcedimento();
                     if (((ProcessData)process.getData()).getListaSportelli().size()>1){
                     idProc = idProc+"/"+String.valueOf(sportello.getIdx());
                     }
                     metaData = process.getCommune().getKey()+"||"+idProc+"||"+uniqueID+"||"+urlAllegatiService+"||"+"getFile";
                     }
					
                     FileOutputStream fileoutputstream = new FileOutputStream(nomeCompleto);
                     fileoutputstream.write(pdfTemp);
                     fileoutputstream.close();
                     Attachment all2 = new Attachment();
                     all2.setName("riepilogo.pdf");
                     //byte[] temp = riepilogoPDF("C:\\People2_0_2\\Tomcat_5_0_28\\temp\\attachments\\"+"pippo"+((SportelloBean) ((ProcessData)process.getData()).getListaSportelli().get(codiceSportello)).getIdx()+".pdf");
                     String b64 = new String(org.apache.xerces.utils.Base64.encode(pdfTemp));
                     all2.setDescrizione(nome);
                     nuovaGestioneFile = request.getUnwrappedRequest().getSession().getServletContext().getInitParameter("remoteAttachFile");
                     if (nuovaGestioneFile!=null && nuovaGestioneFile.equalsIgnoreCase("true")){
                     all2.setData(metaData);
                     } else {
                     all2.setData(b64);
                     }
                     all2.setFileLenght(pdfTemp.length);
                     all2.setPath(nomeCompleto);
                     se scommentate questa parte commentata ricordarsi di scommentare anche le righe 296-302
                     */
                    if (sportello.getFlgPu().equalsIgnoreCase("S") && sportello.getFlgSu().equalsIgnoreCase("S")) {
                        String processDataAttuale = "";
                        HashMap mappaAllegatiXSportello = new HashMap();

                        Set set = ((ProcessData) process.getData()).getListaSportelli().keySet();
                        for (Iterator iterator2 = set.iterator(); iterator2.hasNext();) {
                            String keySportello = (String) iterator2.next();
                            SportelloBean sportel = (SportelloBean) ((ProcessData) process.getData()).getListaSportelli().get(keySportello);
                            if (sportel.getListaAllegati() != null) {
                                mappaAllegatiXSportello.put(keySportello, sportel.getListaAllegati());
                                sportel.setListaAllegati(null);
                            }
                        }

                        HashMap mappaPDFSpacchettati = new HashMap();

                        processDataAttuale = Bean2XML.marshallPplData(process.getData(), request.getUnwrappedRequest().getCharacterEncoding());

                        for (Iterator iterator2 = ((ProcessData) process.getData()).getListaProcedimenti().keySet().iterator(); iterator2.hasNext();) {
                            ProcedimentoBean proc = (ProcedimentoBean) ((ProcessData) process.getData()).getListaProcedimenti().get((String) iterator2.next());
                            if (proc.getCodiceSportello().equalsIgnoreCase(codiceSportello)) {
                                ProcessData ppl2 = ((ProcessData) Bean2XML.unmarshall(ProcessData.class, processDataAttuale, request.getUnwrappedRequest().getCharacterEncoding()));
                                ripulisciProcessData(ppl2, proc, codiceSportello);
// PC - Stampa bozza inizio                                 
//                                pdfTemp = mdd.invokeDocDynModuloPDF(process, codiceSportello, request.getUnwrappedRequest().getSession(), ppl2, request.getUnwrappedRequest(), false);                                
                                pdfTemp = mdd.invokeDocDynModuloPDF(process, codiceSportello, request.getUnwrappedRequest().getSession(), ppl2, request.getUnwrappedRequest(), false, false);
// PC - Stampa bozza fine                                

                                path = PeopleProperties.UPLOAD_DIRECTORY.getValueString(process.getCommune().getKey());
                                nome = "riepilogoSpacchettato_" + ((ProcessData) process.getData()).getIdentificatorePeople().getIdentificatoreProcedimento() + "_" + ((ProcessData) process.getData()).getDatiTemporanei().getIndiceSportello() + "_" + "modello_" + proc.getCodiceProcedimento() + ".pdf";
                                nomeCompleto = path + System.getProperty("file.separator") + nome;

//								nuovaGestioneFile = request.getUnwrappedRequest().getSession().getServletContext().getInitParameter("remoteAttachFile");
//					        	if (nuovaGestioneFile!=null && nuovaGestioneFile.equalsIgnoreCase("true")){
                                if (!process.isEmbedAttachmentInXml()) {
                                    File rootDirNodo = new File(path + File.separator + process.getCommune().getKey());
                                    if (!rootDirNodo.exists()) {
                                        rootDirNodo.mkdir();
                                    }
                                    File rootDirPratica = new File(path + File.separator + process.getCommune().getKey() + File.separator + process.getIdentificatoreProcedimento());
                                    if (!rootDirPratica.exists()) {
                                        rootDirPratica.mkdirs();
                                    }
                                    if (((ProcessData) process.getData()).getListaSportelli().size() > 1) {
                                        File rootDirSubPratica = new File(path + File.separator + process.getCommune().getKey() + File.separator + process.getIdentificatoreProcedimento() + File.separator + String.valueOf(sportello.getIdx()));
                                        if (!rootDirSubPratica.exists()) {
                                            rootDirSubPratica.mkdirs();
                                        }
                                        path = path + File.separator + process.getCommune().getKey() + File.separator + process.getIdentificatoreProcedimento() + File.separator + String.valueOf(sportello.getIdx());
                                    } else {
                                        path = path + File.separator + process.getCommune().getKey() + File.separator + process.getIdentificatoreProcedimento();
                                    }
                                    String uniqueID = Long.toString((new Date()).getTime());
                                    nomeCompleto = path + File.separator + uniqueID + "_" + nome;
                                    String urlAllegatiService = "";
                                    if (request != null && request.getUnwrappedRequest().getSession() != null && request.getUnwrappedRequest().getSession().getServletContext() != null) {
                                        urlAllegatiService = request.getUnwrappedRequest().getSession().getServletContext().getInitParameter("UrlRemoteAttachFile");
                                    }
                                    String idProc = process.getIdentificatoreProcedimento();
                                    if (((ProcessData) process.getData()).getListaSportelli().size() > 1) {
                                        idProc = idProc + "/" + String.valueOf(sportello.getIdx());
                                    }
                                    metaData = process.getCommune().getKey() + "||" + idProc + "||" + uniqueID + "||" + urlAllegatiService + "||" + "getFile";
                                }

                                FileOutputStream fileoutputstream = new FileOutputStream(nomeCompleto);
                                fileoutputstream.write(pdfTemp);
                                fileoutputstream.close();
                                Attachment all = new Attachment();
                                all.setName("modello_" + proc.getCodiceProcedimento() + ".pdf");
                                String b64 = new String(org.apache.xerces.utils.Base64.encode(pdfTemp), request.getUnwrappedRequest().getCharacterEncoding());
                                all.setDescrizione("Riepilogo : " + Utilities.NVL(proc.getNome()));
//								if (nuovaGestioneFile!=null && nuovaGestioneFile.equalsIgnoreCase("true")){
                                if (!process.isEmbedAttachmentInXml()) {
                                    all.setData(metaData);
                                } else {
                                    all.setData(b64);
                                }
                                all.setFileLenght(pdfTemp.length);
                                all.setPath(nomeCompleto);
                                sportello.addListaRiepiloghiSpacchettati(all);
                            }
                        }
//						ArrayList listaPDFSpacchettati = sportello.getListaRiepiloghiSpacchettati();
//						process.setData((ProcessData) Bean2XML.unmarshall(ProcessData.class, processDataAttuale));
                        if (mappaAllegatiXSportello.size() > 0) {
                            set = ((ProcessData) process.getData()).getListaSportelli().keySet();
                            for (Iterator iterator2 = set.iterator(); iterator2.hasNext();) {
                                String keySportello = (String) iterator2.next();
                                SportelloBean sportel = (SportelloBean) ((ProcessData) process.getData()).getListaSportelli().get(keySportello);
                                ArrayList listaAll = (ArrayList) mappaAllegatiXSportello.get(keySportello);
                                sportel.setListaAllegati(listaAll);
                            }
                        }
                    }
                    for (Iterator iterator2 = ((ProcessData) process.getData()).getAllegati().iterator(); iterator2.hasNext();) {
                        Attachment att = (Attachment) iterator2.next();
                        sportello.addListaAllegati(att);
                    }
                    /*
                     if (all2==null){
                     System.out.println("Riepilogo.PDF is NULL");
                     } else {
                     sportello.addListaAllegati(all2);
                     }
                     */
                    pd = getPipelineDataPerDestinatario(sportello, process, request, pplContext);
                    ((ProcessData) process.getData()).exportToPipeline(pd);

                    // < CCD - L'operazione di imbustamento viene già effettuata all'interno del metodo getPipelineDataPerDestinatario, 
                    // ma successivamente viene richiamato il metodo exportToPipeline che ripristina l'xml non imbustato.
                    // estraggo il tracciato dalla pipeline, lo imbusto e lo reinserisco
                    String tracciatoRichiesta = (String) pd.getAttribute(PipelineDataImpl.XML_PROCESSDATA_PARAMNAME);

                    IRequestEnvelope envelopeBean = EnvelopeHelper.createEnvelopeFromPplProcessAndSession(process, request.getUnwrappedRequest().getSession());

                    envelopeBean.setContenuto(new ContenutoBusta(tracciatoRichiesta));
                    envelopeBean.setNomeServizio("unknown (" + envelopeBean.getContestoServizio() + ")");

                    IEnvelopeFactory envelopeFactory
                            = new EnvelopeFactory_modellazioneb116_envelopeb002();

                    String envelopeXML = envelopeFactory.createEnvelopeXmlText(envelopeBean);

// PC - Salvataggio xml da inviare - sia senza che con envelope - inizio    
                    String pathAppoggio = PeopleProperties.UPLOAD_DIRECTORY.getValueString(process.getCommune().getKey());
                    pathAppoggio = pathAppoggio + File.separator + "monitoraggio";

                    File file = new File(pathAppoggio);
                    boolean exists = file.exists();
                    if (!exists) {
                        boolean success = (new File(pathAppoggio)).mkdirs();
                    }
                    String nomeFile = pathAppoggio + File.separator + process.getIdentificatoreProcedimento() + ".xml";
                    FileOutputStream fileoutputstream = new FileOutputStream(nomeFile);
                    fileoutputstream.write(tracciatoRichiesta.getBytes());
                    fileoutputstream.close();
                    nomeFile = pathAppoggio + File.separator + process.getIdentificatoreProcedimento() + "_envelope.xml";
                    fileoutputstream = new FileOutputStream(nomeFile);
                    fileoutputstream.write(envelopeXML.getBytes());
                    fileoutputstream.close();
// PC - Salvataggio xml da inviare - sia senza che con envelope - fine

                    pd.setAttribute(PipelineDataImpl.XML_PROCESSDATA_PARAMNAME, envelopeXML);
                    pd.setAttribute(PipelineDataImpl.IS_DELEGATED, ProcessUtils.isDelegate(request.getUnwrappedRequest()));

                    // CCD > 
                    PipelineFactory.getInstance().getPipelineForName(process.getCommune().getOid(), process.getProcessName()).put(pplContext, pd);
                    ((ProcessData) process.getData()).getDatiTemporanei().setIndiceSportello(((ProcessData) process.getData()).getDatiTemporanei().getIndiceSportello() + 1);
                }
                setStep(process, request, getCurrentActivityIndex(process), getCurrentStepIndex(process) + 1);
            } else {
                throw new ProcedimentoUnicoException("Sessione scaduta");
            }
            logger.debug("InvioMultiploStep - loopBack method END");
        } catch (Exception e) {
            gestioneEccezioni(process, 5, e);
        }
    }

    private void ripulisciProcessData(ProcessData ppl2, ProcedimentoBean procedimento, String codiceSportello) {
        // rimuovo eventuali altri sportelli presenti nel processData 
        Set listaKeySportelli = ppl2.getListaSportelli().keySet();
        for (Iterator iterator = listaKeySportelli.iterator(); iterator.hasNext();) {
            String codSport = (String) iterator.next();
            if (codSport != null && !codiceSportello.equalsIgnoreCase(codSport)) {
                ppl2.getListaSportelli().remove(codSport);
            }
        }

        HashMap newHasMapProcedimenti = new HashMap();
        newHasMapProcedimenti.put(procedimento.getCodiceProcedimento(), procedimento);
        ppl2.setListaProcedimenti(newHasMapProcedimenti);

//		Set listaKeyProcedimenti = ppl2.getListaProcedimenti().keySet();
//		for (Iterator iterator = listaKeyProcedimenti.iterator(); iterator.hasNext();) {
//			String codProc = (String) iterator.next();
//			if (codProc!=null && !codProc.equalsIgnoreCase(procedimento.getCodiceProcedimento())){
//				ppl2.getListaProcedimenti().remove(codProc);
//			}
//		}
        // ripulisco lista interventi
        ArrayList nuovaListaInterventi = new ArrayList();
        for (Iterator iterator = procedimento.getCodInterventi().iterator(); iterator.hasNext();) {
            String codInterv = (String) iterator.next();
            boolean trovato = false;
            Iterator it = ppl2.getInterventi().iterator();
            while (it.hasNext() && !trovato) {
                InterventoBean inter = (InterventoBean) it.next();
                if (codInterv.equalsIgnoreCase(inter.getCodice())) {
                    trovato = true;
                    nuovaListaInterventi.add(inter);

                }
            }
            if (!trovato) {
                it = ppl2.getInterventiFacoltativi().iterator();
                while (it.hasNext() && !trovato) {
                    InterventoBean inter = (InterventoBean) it.next();
                    if (codInterv.equalsIgnoreCase(inter.getCodice())) {
                        trovato = true;
                        nuovaListaInterventi.add(inter);

                    }
                }
            }
        }
        ppl2.setInterventi(nuovaListaInterventi);
// PC - Reiterazione domanda inizio         
//        ppl2.setInterventiFacoltativi(new ArrayList());
// PC - Reiterazione domanda fine        

    }

    public boolean logicalValidate(AbstractPplProcess process, IRequestWrapper request, IValidationErrors errors) throws ParserException {
        ProcessData dataForm = (ProcessData) process.getData();
        try {
            logger.debug("InvioMultiploStep - logicalValidate method");

            logger.debug("InvioMultiploStep - logicalValidate method END");
            return true;
        } catch (Exception e) {
            errors.add("error.generic", "Errore interno");
            gestioneEccezioni(process, 5, e);
            dataForm.setInternalError(true);
            return false;
        }
    }

    private PipelineData getPipelineDataPerDestinatario(SportelloBean sportello, AbstractPplProcess pplProcess, IRequestWrapper request, PeopleContext pplContext) throws Exception {
        String peopleProtocolID;
        PipelineData pd = new PipelineDataImpl();

        SignedSummaryAttachment riepilogoFirmato = null;
        UnsignedSummaryAttachment riepilogoNonFirmato = null;
        for (Iterator iterator = sportello.getListaAllegati().iterator(); iterator.hasNext();) {
            Attachment attach = (Attachment) iterator.next();
            if (attach instanceof SignedSummaryAttachment) {
                riepilogoFirmato = (SignedSummaryAttachment) attach;
            } else if (attach instanceof UnsignedSummaryAttachment) {
                riepilogoNonFirmato = (UnsignedSummaryAttachment) attach;
            }
        }

        ContentImpl content = null;
        try {
            content = ContentManager.getInstance().getForProcessName(pplProcess.getProcessName());
        } catch (peopleException e) {
        }
        String processTitle = (content != null ? content.getName() : "");

        pd.setAttribute(PipelineDataImpl.COMMUNE_PARAMNAME, pplProcess.getCommune());
        pd.setAttribute(PipelineDataImpl.USER_PARAMNAME, pplContext.getUser());
        pd.setAttribute(PipelineDataImpl.EDITABLEPROCESS_ID_PARAMNAME, pplProcess.getOid());
        pd.setAttribute(PipelineDataImpl.EDITABLEPROCESS_PARAMNAME, pplProcess);
        pd.setAttribute(PipelineDataImpl.PROCESS_TITLE, processTitle);
        pd.setAttribute(PipelineDataImpl.RECEIPT_MAIL_ATTACHMENT, Boolean.TRUE);

        pd.setAttribute(PipelineDataImpl.SEND_RECEIPT_MAIL, new Boolean(pplProcess.isSendMailToOwner()));

        String emailAddress = "";

        ServiceParameters params = (ServiceParameters) request.getUnwrappedRequest().getSession().getAttribute("serviceParameters");
        String disabilitaMailString = params.get("disabilitaMailDestinatario");

        if (disabilitaMailString != null && disabilitaMailString.equalsIgnoreCase("TRUE")) {
            emailAddress = null;
        } else {
//	        if (pplContext.getUser().isAnonymous()) {
            emailAddress = getCampoDinamicoAnagrafica(((ProcessData) pplProcess.getData()).getAnagrafica().getListaCampi(), "ANAG_EMAIL_DICHIARANTE");
//	        } else {
//	            emailAddress = pplContext.getUser().getEMail();
//	        }
        }
        pd.setAttribute(PipelineDataImpl.USER_MAILADDRESS_PARAMNAME, emailAddress);

        String richiestaNonFirmata = "";
        if (riepilogoFirmato == null) {
            if (riepilogoNonFirmato != null) {
                richiestaNonFirmata = riepilogoNonFirmato.getData();
                pplProcess.setSignEnabled(false);
            }
        } else {
            pplProcess.setSignEnabled(true);
        }
        // Allega il riepilogo firmato 
//        String nuovaGestioneFile = request.getUnwrappedRequest().getSession().getServletContext().getInitParameter("remoteAttachFile");
        if (riepilogoFirmato != null) {
//        	if (nuovaGestioneFile!=null && nuovaGestioneFile.equalsIgnoreCase("true")){
            if (!pplProcess.isEmbedAttachmentInXml()) {
                String ss = riepilogoFirmato.getPath();
                byte[] fd = Utilities.getBytesFromFile(ss);
                // byte[] dd = Base64.decode(fd);
                pd.setAttribute(PipelineDataImpl.SIGNED_PRINTPAGE_NAME, fd);
            } else {
                // byte[] fd = Utilities.getBytesFromFile("C:/riepilogo.html.p7m");
//        		 pd.setAttribute(PipelineDataImpl.SIGNED_PRINTPAGE_NAME, Base64.decode(riepilogoFirmato.getData().getBytes()));
                pd.setAttribute(PipelineDataImpl.SIGNED_PRINTPAGE_NAME, riepilogoFirmato.getData().getBytes());
            }
        } else {
            ArrayList listaAnagrafiche = buildAnagrafica((ProcessData) pplProcess.getData());
            String unsignedData = recuperaRiepilogoStatico(request, pplProcess, sportello.getIdx(), listaAnagrafiche);
//        	if (nuovaGestioneFile!=null && nuovaGestioneFile.equalsIgnoreCase("true")){
            if (!pplProcess.isEmbedAttachmentInXml()) {
                pd.setAttribute(PipelineDataImpl.PRINTPAGE_PARAMNAME, unsignedData);
            } else {
                pd.setAttribute(PipelineDataImpl.PRINTPAGE_PARAMNAME, unsignedData);
            }
        }

        AbstractData data = (AbstractData) pplProcess.getData();
        // Inserimeto dell'identificatore People ricavato dal ProcessData
        peopleProtocolID = data.getIdentificatorePeople().getIdentificatoreProcedimento();
        pd.setAttribute(PipelineDataImpl.PEOPLE_PROTOCOLL_ID_PARAMNAME, peopleProtocolID);

        // estraggo il tracciato dalla pipeline, lo imbusto e lo reinserisco
        String tracciatoRichiesta = null;
        if (riepilogoFirmato != null) {
            tracciatoRichiesta = new String(Base64.decode(riepilogoFirmato.getSign().getBytes()));
            riepilogoFirmato.setSign(null);
        } else {
            // Vuole dire che la firma è stata disabilitata
            tracciatoRichiesta = richiestaNonFirmata;
        }
        logger.debug("tracciato richiesta prima dell'imbustamento: " + tracciatoRichiesta);

        IRequestEnvelope envelopeBean = EnvelopeHelper.createEnvelopeFromPplProcessAndSession(pplProcess, request.getUnwrappedRequest().getSession());
        envelopeBean.setContenuto(new ContenutoBusta(tracciatoRichiesta));
        envelopeBean.setNomeServizio("unknown ("
                + envelopeBean.getContestoServizio() + ")");

        IEnvelopeFactory envelopeFactory = new EnvelopeFactory_modellazioneb116_envelopeb002();

        String envelopeXML = envelopeFactory.createEnvelopeXmlText(envelopeBean);
        pd.setAttribute(PipelineDataImpl.XML_PROCESSDATA_PARAMNAME, envelopeXML);

        // Se esistono allegati li metto nel PD (questi sono gli allegati comuni al procedimento)
        if (sportello != null && sportello.getListaAllegati() != null) {
            pd.setAttribute(PipelineDataImpl.ATTACHMENTS, sportello.getListaAllegati());
        }
        return pd;
    }

    private byte[] riepilogoPDF(String name) {
        byte[] bytes = null;
        try {
            File file = new File(name);
            FileInputStream is = new FileInputStream(file);

            // Get the size of the file
            long length = file.length();

            if (length > Integer.MAX_VALUE) {
                throw new IOException("The file is too big");
            }

            // Create the byte array to hold the data
            bytes = new byte[(int) length];

            // Read in the bytes
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length
                    && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }

            // Ensure all the bytes have been read in
            if (offset < bytes.length) {
                throw new IOException("The file was not completely read: " + file.getName());
            }

            // Close the input stream, all file contents are in the bytes variable
            is.close();
        } catch (Exception e) {

        }
        return bytes;
    }

}
