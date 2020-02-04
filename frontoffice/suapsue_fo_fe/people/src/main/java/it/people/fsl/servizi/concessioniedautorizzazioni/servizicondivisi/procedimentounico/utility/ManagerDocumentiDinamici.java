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
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility;

import it.gruppoinit.commons.DBCPManager;
import it.gruppoinit.commons.Utilities;
import it.people.core.exception.ServiceException;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.ProcedimentoUnicoDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AllegatoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AnagraficaBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.BaseBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.DestinatarioBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.DichiarazioniStaticheBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.DocumentoFisicoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.HrefCampiBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.InterventoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.NormativaBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OnereBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OneriBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OperazioneBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SportelloBean;
import it.people.java.util.LinkedHashMap;
import it.people.process.AbstractPplProcess;
import it.people.process.common.entity.Attachment;
import it.people.process.common.entity.SignedAttachment;
import it.people.util.ProcessUtils;
import it.people.util.ServiceParameters;
import it.people.vsl.exception.SendException;
import it.people.wrappers.IRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import noNamespace.Allegato;
import noNamespace.CampoAnagraficaType;
import noNamespace.CampoHrefType;
import noNamespace.DocumentRootDocument;
import noNamespace.DocumentRootDocument.DocumentRoot;
import noNamespace.DocumentRootDocument.DocumentRoot.AllegatiFacoltativi;
import noNamespace.DocumentRootDocument.DocumentRoot.AllegatiFacoltativi.AllegatoFacoltativo;
import noNamespace.DocumentRootDocument.DocumentRoot.AllegatiFisici;
import noNamespace.DocumentRootDocument.DocumentRoot.AllegatiFisici.AllegatoFisico;
import noNamespace.DocumentRootDocument.DocumentRoot.AllegatiSelezionatiTotali;
import noNamespace.DocumentRootDocument.DocumentRoot.Anagrafica;
import noNamespace.DocumentRootDocument.DocumentRoot.DescrizioneSettore;
import noNamespace.DocumentRootDocument.DocumentRoot.DichiarazioniDinamiche;
import noNamespace.DocumentRootDocument.DocumentRoot.DichiarazioniDinamiche.DichiarazioneDinamica;
import noNamespace.DocumentRootDocument.DocumentRoot.DichiarazioniDinamiche.DichiarazioneDinamica.Campi;
import noNamespace.DocumentRootDocument.DocumentRoot.DichiarazioniStatiche;
import noNamespace.DocumentRootDocument.DocumentRoot.InterventiSelezionati;
import noNamespace.DocumentRootDocument.DocumentRoot.InterventiSelezionati.InterventoSelezionato;
import noNamespace.DocumentRootDocument.DocumentRoot.Oggetto;
import noNamespace.DocumentRootDocument.DocumentRoot.OperazioniIndividuate;
import noNamespace.DocumentRootDocument.DocumentRoot.OperazioniIndividuate.OperazioneIndividuata;
import noNamespace.DocumentRootDocument.DocumentRoot.OperazioniNonRichiesteEsplicitamente;
import noNamespace.DocumentRootDocument.DocumentRoot.Procedimenti;
import noNamespace.DocumentRootDocument.DocumentRoot.Procedimenti.Procedimento;
import noNamespace.DocumentRootDocument.DocumentRoot.Procedimenti.Procedimento.AllegatiTotali;
import noNamespace.DocumentRootDocument.DocumentRoot.Procedimenti.Procedimento.Destinatario;
import noNamespace.DocumentRootDocument.DocumentRoot.Procedimenti.Procedimento.Ente;
import noNamespace.DocumentRootDocument.DocumentRoot.Procedimenti.Procedimento.Interventi;
import noNamespace.DocumentRootDocument.DocumentRoot.Procedimenti.Procedimento.Interventi.Intervento;
import noNamespace.DocumentRootDocument.DocumentRoot.Procedimenti.Procedimento.Interventi.Intervento.Allegati;
import noNamespace.DocumentRootDocument.DocumentRoot.Procedimenti.Procedimento.Normative;
import noNamespace.DocumentRootDocument.DocumentRoot.Procedimenti.Procedimento.Normative.Normativa;
import noNamespace.DocumentRootDocument.DocumentRoot.Procedimenti.Procedimento.OneriAnticipati;
import noNamespace.DocumentRootDocument.DocumentRoot.Procedimenti.Procedimento.OneriAnticipati.OnereAnticipato;
import noNamespace.DocumentRootDocument.DocumentRoot.Procedimenti.Procedimento.OneriPosticipati;
import noNamespace.DocumentRootDocument.DocumentRoot.Procedimenti.Procedimento.OneriPosticipati.OnerePosticipato;
import noNamespace.DocumentRootDocument.DocumentRoot.RiepilogoOneri;
import noNamespace.DocumentRootDocument.DocumentRoot.Sportello;
import noNamespace.EsitoPagamento;
import noNamespace.Indirizzo;
import noNamespace.OpzioneComboType;
import noNamespace.OpzioniComboType;
import noNamespace.RappresentanteLegaleProfiloAccreditamento;
import noNamespace.RootDocument;
import noNamespace.RootDocument.Root;
import noNamespace.impl.DocumentRootDocumentImpl;
import noNamespace.impl.DocumentRootDocumentImpl.DocumentRootImpl.BolloImpl;
import noNamespace.impl.DocumentRootDocumentImpl.DocumentRootImpl.ModuloBiancoImpl;
import noNamespace.impl.DocumentRootDocumentImpl.DocumentRootImpl.SportelloImpl.FirmaImpl;
import noNamespace.impl.DocumentRootDocumentImpl.DocumentRootImpl.StampaBozzaImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlbeans.XmlOptions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ManagerDocumentiDinamici {

    public final Log logger = LogFactory.getLog(this.getClass());

//    public byte[] invokeDocDynModuloBiancoPDF(AbstractPplProcess process, String codSportello, HttpSession session) {
//        byte[] ret = null;
//        String output = "";
//        try {
//
//            ProcessData dataForm = (ProcessData) process.getData();
//            //String inputWS="<?xml version=\"1.0\" encoding=\"UTF-8\"?><Root><tipo>PDF</tipo></Root>";
//
//            // TODO crazione xml con le info del processData
//            String s = dynamicDocument(dataForm, codSportello, "PDF", true, session, false, null);
//
//            logger.debug("XML Input \n" + s);
//            try {
//                logger.debug("inizio invocazione web service Documenti Dinamici");
//                output = process.callService("DOC_DINAMICI", s);
//                ret = org.apache.commons.codec.binary.Base64.decodeBase64(output.getBytes());
//                logger.debug("fine invocazione web service Documenti Dinamici");
//            } catch (ServiceException e) {
//                logger.error("dynamicDocument - ServiceException");
//                e.printStackTrace();
//            } catch (SendException e) {
//                logger.error("dynamicDocument - SendException");
//                e.printStackTrace();
//            }
//            logger.debug("XML Output \n" + output);
//            // ret = Utilities.getBytesFromFile("C:/test/java-hibernate.pdf");
//        } catch (Exception e) {
//        }
//        return ret;
//    }
// PC - Stampa bozza inizio  
//    public byte[] invokeDocDynModuloPDF(AbstractPplProcess process, String codSportello, HttpSession session, HttpServletRequest request, boolean isWhite) {
//        return invokeDocDynModuloPDF(process, codSportello, session, null, request, isWhite);
//    }
    public byte[] invokeDocDynModuloPDF(AbstractPplProcess process, String codSportello, HttpSession session, HttpServletRequest request, boolean isWhite, boolean isBozza) {
        return invokeDocDynModuloPDF(process, codSportello, session, null, request, isWhite, isBozza);
    }
// PC - Stampa bozza fine    
// PC - Stampa bozza inizio  
//    public byte[] invokeDocDynModuloPDF(AbstractPplProcess process, String codSportello, HttpSession session, ProcessData dataForm, HttpServletRequest request, boolean isWhite) {

    public byte[] invokeDocDynModuloPDF(AbstractPplProcess process, String codSportello, HttpSession session, ProcessData dataForm, HttpServletRequest request, boolean isWhite, boolean isBozza) {
// PC - Stampa bozza fine         
        byte[] ret = null;
        String output = "";
        try {
            //String inputWS="<?xml version=\"1.0\" encoding=\"UTF-8\"?><Root><tipo>PDF</tipo></Root>";

            // TODO Quando viene utilizzato dataForm i dati che contiene non sono gli stessi di process.getData: mancano i dati di pagamento
            if (process.getData() != null && dataForm != null) {
                dataForm.setEsitoPagamento(((ProcessData) process.getData()).getEsitoPagamento());
                dataForm.getDatiTemporanei().setModalitaPagamento(((ProcessData) process.getData()).getDatiTemporanei().getModalitaPagamento());
            }

            // TODO crazione xml con le info del processData
// PC - Stampa bozza inizio             
//            String s = dynamicDocument(process, (dataForm == null ? ((ProcessData) process.getData()) : dataForm), codSportello, "PDF", isWhite, session, false, null, request);
            String s = dynamicDocument(process, (dataForm == null ? ((ProcessData) process.getData()) : dataForm), codSportello, "PDF", isWhite, isBozza, session, false, null, request);
// PC - Stampa bozza fine              

            logger.debug("XML Input \n" + s);
            try {
                logger.debug("inizio invocazione web service Documenti Dinamici");
                logger.info("AbstractPplProcess: " + process.getClass() + ", " + process.getClass().getName());
                output = process.callService("DOC_DINAMICI", s);
                ret = org.apache.commons.codec.binary.Base64.decodeBase64(output.getBytes(request.getCharacterEncoding()));
                logger.debug("fine invocazione web service Documenti Dinamici");
            } catch (ServiceException e) {
                logger.error("dynamicDocument - ServiceException", e);
                e.printStackTrace();
            } catch (SendException e) {
                logger.error("dynamicDocument - SendException", e);
                e.printStackTrace();
            } catch (Throwable e) {
            	logger.error("dynamicDocument - Throwable", e);
                e.printStackTrace();
            }
            logger.debug("XML Output \n" + output);
            // ret = Utilities.getBytesFromFile("C:/test/java-hibernate.pdf");
        } catch (Throwable e) {
        	logger.error("dynamicDocument - SendException", e);
            e.printStackTrace();
        }
        return ret;
    }

//    public String invokeDocDynModuloBiancoHTML(AbstractPplProcess process, String codSportello, HttpSession session) {
//        String output = "";
//        try {
//            String s = dynamicDocument((ProcessData) process.getData(), codSportello, "HTML", true, session, false, null);
//            logger.debug("XML Input \n" + s);
//            try {
//                logger.debug("inizio invocazione web service Documenti Dinamici");
//                output = process.callService("DOC_DINAMICI", s);
//                logger.debug("fine invocazione web service Documenti Dinamici");
//            } catch (ServiceException e) {
//                logger.error("dynamicDocument - ServiceException");
//                e.printStackTrace();
//            } catch (SendException e) {
//                logger.error("dynamicDocument - SendException");
//                e.printStackTrace();
//            }
//            logger.debug("XML Output \n" + output);
//        } catch (Exception e) {
//        }
//        return output;
//    }
//    public String invokeDocDynModuloHTML(AbstractPplProcess process, String codSportello, HttpSession session) {
//        String output = "";
//        try {
//            String s = dynamicDocument((ProcessData) process.getData(), codSportello, "HTML", false, session, false, null);
//            logger.debug("XML Input \n" + s);
//            try {
//                logger.debug("inizio invocazione web service Documenti Dinamici");
//                output = process.callService("DOC_DINAMICI", s);
//                logger.debug("fine invocazione web service Documenti Dinamici");
//            } catch (ServiceException e) {
//                logger.error("dynamicDocument - ServiceException");
//                e.printStackTrace();
//            } catch (SendException e) {
//                logger.error("dynamicDocument - SendException");
//                e.printStackTrace();
//            }
//            logger.debug("XML Output \n" + output);
//        } catch (Exception e) {
//        }
//        return output;
//     }
    public byte[] invokeDocDynModuloProcuraSpecialePDF(AbstractPplProcess process, String codSportello, HttpSession session, ProcessData dataForm, String codFiscaleProcuratore, HttpServletRequest request) {
        byte[] ret = null;
        String output = "";

        try {
// PC - Stampa bozza inizio             
//            String s = dynamicDocument(process, (dataForm == null ? ((ProcessData) process.getData()) : dataForm), codSportello, "PDF", false, session, true, codFiscaleProcuratore, request);
            String s = dynamicDocument(process, (dataForm == null ? ((ProcessData) process.getData()) : dataForm), codSportello, "PDF", false, false, session, true, codFiscaleProcuratore, request);
// PC - Stampa bozza fine              

            logger.debug("XML Input \n" + s);
            try {
                logger.debug("inizio invocazione web service Documenti Dinamici");
                output = process.callService("DOC_DINAMICI", s);
                ret = org.apache.commons.codec.binary.Base64.decodeBase64(output.getBytes());
                logger.debug("fine invocazione web service Documenti Dinamici");
            } catch (ServiceException e) {
                logger.error("dynamicDocument - ServiceException");
                e.printStackTrace();
            } catch (SendException e) {
                logger.error("dynamicDocument - SendException");
                e.printStackTrace();
            }
            logger.debug("XML Output \n" + output);
            // ret = Utilities.getBytesFromFile("C:/test/java-hibernate.pdf");
        } catch (Throwable e) {
			logger.error(e);
		}
        
        return ret;
    }

// PC - Stampa bozza inizio      
//    private String dynamicDocument(AbstractPplProcess process, ProcessData dataForm, String codSportello, String typeDocument, boolean isWhite, HttpSession session, boolean procuraSpeciale, String codFiscaleProcuratore, HttpServletRequest request) {
    private String dynamicDocument(AbstractPplProcess process, ProcessData dataForm, String codSportello, String typeDocument, boolean isWhite, boolean isBozza, HttpSession session, boolean procuraSpeciale, String codFiscaleProcuratore, HttpServletRequest request) {
// PC - Stampa bozza fine          

        logger.debug("dynamicDocument is invoked");
        //String output = ""; 
        //ProcessData dataForm = (ProcessData) process.getData();
        DocumentRootDocument xmlDoc = DocumentRootDocument.Factory.newInstance();
        DocumentRoot docRoot = xmlDoc.addNewDocumentRoot();

        // identificatoreProcedimentoPraticaSpacchettata (OBBLIGATORIO)
        SportelloBean sportello = (SportelloBean) dataForm.getListaSportelli().get(codSportello);
        int idx = sportello.getIdx();
        if (isWhite) {
            docRoot.setIdentificatoreProcedimentoPraticaSpacchettata("");
        } else {
            docRoot.setIdentificatoreProcedimentoPraticaSpacchettata(dataForm.getIdentificatorePeople().getIdentificatoreProcedimento() + "/" + idx);
        }

        //docRoot.setIdentificatoreProcedimentoPraticaSpacchettata(Utilities.NVL(processDataPerDestinatario.getIdentificatorePeople().getIdentificatoreProcedimento(),)+stringIdx
        //docRoot.setIdentificatoreProcedimentoPraticaSpacchettata(Utilities.NVL(processDataPerDestinatario.getIdentificatoreUnivoco().getCodiceSistema().getCodiceIdentificativoOperazione(),""));
        if (procuraSpeciale) {
            docRoot.setAzioneTemplate("ProcuraSpeciale");
        }
        // idBookmark (OBBLIGATORIO)
        docRoot.setIdBookmark(Utilities.NVL(dataForm.getIdBookmark(), ""));

        // lingua (OBBLIGATORIO)
        docRoot.setLanguage(dataForm.getLanguage());

        // ModuloBianco (OBBLIGATORIO)
        docRoot.setModuloBianco(isWhite ? ModuloBiancoImpl.S : ModuloBiancoImpl.N);

        compilaSezioneSportello(dataForm, codSportello, docRoot);

        // descrizioneSettore (OBBLIGATORIO)
        // docRoot.setDescrizioneSettore(dataForm.getSettoreScelto().getDescrizione());
        compilaSezioneSettore(dataForm, docRoot);

        // Operazioni individuate (OBBLIGATORIO)
        compilaSezioneOperazioniIndividuate(dataForm, docRoot);

        compilaSezioneOperazioniNonRichiesteEsplicitamente(dataForm, docRoot);

        // Dichiarazioni statiche (NON OBBLIGATORIO)
        compilaSezioneDichiarazioniStatiche(dataForm, codSportello, docRoot);

        // Interventi selezionati (OBBLIGATORIO)
        compilaSezioneInterventiSelezionati(dataForm, codSportello, docRoot);

        // Procedimenti (OBBLIGATORIO)
        compilaSezioneProcedimenti(dataForm, codSportello, docRoot);

        // Oggetto pratica
        if (sportello.isFlgOggettoRiepilogo()) {
            compilaSezioneOggettoPratica(dataForm, docRoot);
        }
        // DichiarazioniDinamiche (NON OBBLIGATORIO)
        compilaSezioneDichiarazioniDinamiche(dataForm, codSportello, docRoot, isWhite);

        // Anagrafica (OBBLIGATORIO)
        compilaSezioneAnagrafica(dataForm, codSportello, docRoot, procuraSpeciale, codFiscaleProcuratore);

        // Associazione di categoria (OPZIONALE)
        compilaSezioneAssociazioneDiCategoria(dataForm, codSportello, docRoot);

        // Comune (OBBLIGATORIO)
        compilaSezioneComune(dataForm, docRoot);

        // Bollo (NON OBBLIGATORIO)
        compilaSezioneBollo(dataForm, dataForm, codSportello, docRoot);

        // Riepilogo Oneri (NON OBBLIGATORIO)
        compilaSezioneRiepilogoOneri(dataForm, docRoot);

        //Riepilogo allegati fisicamente iseriti
        compilaSezioneAllegatiFisici(process, dataForm, docRoot, request);

        // Allegati selezionati (NON OBBLIGATORIO)
        //TODO
        //compilaSezioneAllegatiSelezionati(processDataGlobale, processDataPerDestinatario, docRoot);
        // Allegati selezionati totali (NON OBBLIGATORIO)
        compilaSezioneAllegatiSelezionatiTotali(dataForm, codSportello, docRoot);

// PC - tieni traccia delle scelte su allegati facoltativi - inizio        
        compilaScelteAllegatiFacoltativi(dataForm, codSportello, docRoot);
// PC - tieni traccia delle scelte su allegati facoltativi - fine  

        compilaSezioneProfili(dataForm, codSportello, docRoot, request);
// PC - Stampa bozza inizio
        docRoot.setStampaBozza(isBozza ? StampaBozzaImpl.S : StampaBozzaImpl.N);
// PC - Stampa bozza fine        

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        try {
            XmlOptions options = new XmlOptions();
            options.setCharacterEncoding(request.getCharacterEncoding());
            options.setSavePrettyPrint();
            xmlDoc.save(result, options);
        } catch (IOException e) {
            logger.error("dynamicDocument - IOException\n" + e.getMessage());
            e.printStackTrace();
        }

        logger.debug("XML prima della trasformazione XSL (inizio)----->");
        XmlOptions options = new XmlOptions();
        options.setCharacterEncoding(request.getCharacterEncoding());
        options.setSavePrettyPrint();
        String temp = xmlDoc.xmlText(options);
        logger.debug(temp);
        logger.debug("XML prima della trasformazione XSL (fine)----->");
        long timestamp = Calendar.getInstance().getTimeInMillis();

        String s = result.toString();
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(s.getBytes(request.getCharacterEncoding()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String xmlOutput = "";
        OutputStream os = new ByteArrayOutputStream();

        // applico filtro XSL per normalizzare i dati per il Web service dei modelli dinamici
        try {
            OutputStreamWriter osWriter = new OutputStreamWriter(os, request.getCharacterEncoding());
            ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
            String absPathToService = params.get("absPathToService");
            // absPathToService è caricato a partire dal campo del db
            logger.info("dynamicDocument absPathToService: " + absPathToService);
            String resourcePath = absPathToService + System.getProperty("file.separator") + "risorse" + System.getProperty("file.separator");
            logger.info("dynamicDocument resourcePath: " + resourcePath);
            TransformerFactory tFactory = TransformerFactory.newInstance();
//            File templateIn = new File(resourcePath + "xsltModelliDinamici.xsl");

            String communeXslFileName = (new StringBuilder("xsltModelliDinamici_")).append(process.getCommune().getOid()).append(".xsl").toString();
            File templateIn = new File((new StringBuilder(String.valueOf(resourcePath))).append(communeXslFileName).toString());
            if (!templateIn.exists()) {
                templateIn = new File((new StringBuilder(String.valueOf(resourcePath))).
                        append("xsltModelliDinamici.xsl").toString());
            }

            Transformer transformer = tFactory.newTransformer(new StreamSource(templateIn));
            // InputStream is = new StringBufferInputStream(s);

            transformer.setOutputProperty(OutputKeys.ENCODING, request.getCharacterEncoding());
            transformer.transform(new StreamSource(bais), new StreamResult(osWriter));

            xmlOutput = ((ByteArrayOutputStream) os).toString(request.getCharacterEncoding());
// PC - controllo firme inizio  
            ControlloFirme cfirme = new ControlloFirme();
            cfirme.cercaFirme(dataForm, xmlOutput, request);
// PC - controllo firme fine            

        } catch (Exception e) {
            logger.error("dynamicDocument - IOException\n", e);
            e.printStackTrace();
        }
        logger.debug("XML dopo la trasformazione XSL (inizio)----->");
        logger.debug(xmlOutput);
        logger.debug("XML dopo la trasformazione XSL (fine)----->");

        String b64 = null;

        try {
            b64 = new String(org.apache.commons.codec.binary.Base64.encodeBase64(((ByteArrayOutputStream) os).toByteArray()), request.getCharacterEncoding());
        } catch (Exception e) {
            logger.error("dynamicDocument", e);
            e.printStackTrace();
        }

        String decodedB64 = null;
        try {
            decodedB64 = new String(org.apache.commons.codec.binary.Base64.decodeBase64(b64.getBytes(request.getCharacterEncoding())), request.getCharacterEncoding());
        } catch (Exception e) {
            logger.error("dynamicDocument", e);
            e.printStackTrace();
        }

//try {
//
//	File fileTestAeCResultXml = new File("/Users/Ricky/fileTestAeCResultXml");
//	FileOutputStream fileTestAeCResultXmlOs = new FileOutputStream(fileTestAeCResultXml);
//	fileTestAeCResultXmlOs.write(result.toString().getBytes());
//	fileTestAeCResultXmlOs.flush();
//	fileTestAeCResultXmlOs.close();
//
//	File fileTestAeCBaistXml = new File("/Users/Ricky/fileTestAeCBaisXml");
//	FileOutputStream fileTestAeCBaisXmlOs = new FileOutputStream(fileTestAeCBaistXml);
//	fileTestAeCBaisXmlOs.write(bais.toString().getBytes());
//	fileTestAeCBaisXmlOs.flush();
//	fileTestAeCBaisXmlOs.close();
//	
//	File fileTestAeCTempXml = new File("/Users/Ricky/fileTestAeCTempXml");
//	FileOutputStream fileTestAeCTempXmlOs = new FileOutputStream(fileTestAeCTempXml);
//	fileTestAeCTempXmlOs.write(temp.getBytes());
//	fileTestAeCTempXmlOs.flush();
//	fileTestAeCTempXmlOs.close();
//	
//	File fileTestAeCPreXml = new File("/Users/Ricky/fileTestAeCPreXml");
//	FileOutputStream fileTestAeCPreXmlOs = new FileOutputStream(fileTestAeCPreXml);
//	fileTestAeCPreXmlOs.write(s.getBytes());
//	fileTestAeCPreXmlOs.flush();
//	fileTestAeCPreXmlOs.close();
//
//	File fileTestAeCXmlOut = new File("/Users/Ricky/fileTestAeCOutXml");
//	FileOutputStream fileTestAeCXmlOutOs = new FileOutputStream(fileTestAeCXmlOut);
//	fileTestAeCXmlOutOs.write(xmlOutput.getBytes());
//	fileTestAeCXmlOutOs.flush();
//	fileTestAeCXmlOutOs.close();
//	
//	File fileTestAeCPostXml = new File("/Users/Ricky/fileTestAeCPostXml");
//	FileOutputStream fileTestAeCPostXmlOs = new FileOutputStream(fileTestAeCPostXml);
//	fileTestAeCPostXmlOs.write(b64.getBytes());
//	fileTestAeCPostXmlOs.flush();
//	fileTestAeCPostXmlOs.close();
//
//	File fileTestAeCDecodedXml = new File("/Users/Ricky/fileTestAeCDecodeXml");
//	FileOutputStream fileTestAeCDecodeXmlOs = new FileOutputStream(fileTestAeCDecodedXml);
//	fileTestAeCDecodeXmlOs.write(decodedB64.getBytes());
//	fileTestAeCDecodeXmlOs.flush();
//	fileTestAeCDecodeXmlOs.close();
//	
//} catch (Exception e) {
//	
//}
        RootDocument rootDocument = RootDocument.Factory.newInstance();

        Root root = rootDocument.addNewRoot();
        root.setXmldata(b64);
        root.setOutputtype(typeDocument);
        String inputWS = rootDocument.xmlText();
        return inputWS;

    }

    private void compilaSezioneSportello(ProcessData dataForm, String codSportello, DocumentRoot docRoot) {
        // Dati dello sportello (OBBLIGATORIO)
        SportelloBean sportelloPD = (SportelloBean) dataForm.getListaSportelli().get(codSportello);
        logger.debug("dinamicDocument-compilaSezioneSportello");
        Sportello sportello = docRoot.addNewSportello();
        sportello.setCodiceSportello(sportelloPD.getCodiceSportello()); // OBBLIGATORIO
        sportello.setDescrizioneSportello(sportelloPD.getDescrizioneSportello()); // OBBLIGATORIO
        sportello.setPec(Utilities.NVL(sportelloPD.getPec(), " ")); // OBBLIGATORIO
        sportello.setRup(Utilities.NVL(sportelloPD.getRup(), "")); // OBBLIGATORIO

        Indirizzo indirizzoSportello = sportello.addNewIndirizzo(); // OBBLIGATORIO
        indirizzoSportello.setCap(Utilities.NVL(sportelloPD.getCap(), "")); // OBBLIGATORIO
        indirizzoSportello.setCitta(Utilities.NVL(sportelloPD.getCitta(), "")); // OBBLIGATORIO
        String destinatari = Utilities.NVL(sportelloPD.getEmail(), "");
        String emailDest = "";
        String token[] = destinatari.split(";");
        if (token != null) {
            if (token.length == 1) {
                emailDest = token[0];
            } else {
                if (token[0].equals("")) {
                    emailDest = "";
                } else {
                    emailDest = token[0];
                }
            }
        }
        indirizzoSportello.setEmail(emailDest);
        indirizzoSportello.setFax(Utilities.NVL(sportelloPD.getFax(), ""));
        indirizzoSportello.setNumero("");
        indirizzoSportello.setProvincia(Utilities.NVL(sportelloPD.getProvincia(), "")); // OBBLIGATORIO
        //indirizzoSportello.setStato(Utilities.NVL(sportelloPD.getStato(),""));
        indirizzoSportello.setStato("");
        indirizzoSportello.setTelefono(Utilities.NVL(sportelloPD.getTelefono(), ""));
        indirizzoSportello.setVia(Utilities.NVL(sportelloPD.getIndirizzo(), "")); // OBBLIGATORIO
// PC - controllo firme inizio        
        sportello.setFirma(FirmaImpl.N);
        if (dataForm.getFirmaBookmark().equalsIgnoreCase(Costant.conFirmaLabel) && !dataForm.getTipoBookmark().equalsIgnoreCase(Costant.bookmarkTypeLivello2Label)) {
                if (sportelloPD.isOnLineSign() || sportelloPD.isOffLineSign()) {
                    sportello.setFirma(FirmaImpl.S);
                }
            }
// PC - controllo firme inizio        
    }

    public void compilaSezioneSettore(ProcessData dataForm, DocumentRoot docRoot) {
        logger.debug("dinamicDocument-compilaSezioneSettore");
        DescrizioneSettore settore = docRoot.addNewDescrizioneSettore();
        settore.setCodice(dataForm.getSettoreScelto().getCodice());
        settore.setDescrizione(dataForm.getSettoreScelto().getDescrizione());
    }

    private void compilaSezioneOperazioniIndividuate(ProcessData dataForm, DocumentRoot docRoot) {
        // Dati delle OperazioniIndividuate (OBBLIGATORIO)
        logger.debug("dinamicDocument-compilaSezioneOperazioniIndividuate");
        OperazioniIndividuate operazioniIndividuate = docRoot.addNewOperazioniIndividuate();
        for (Iterator iterator = dataForm.getAlberoOperazioni().iterator(); iterator.hasNext();) {
            OperazioneBean operazione = (OperazioneBean) iterator.next();
            if (operazione.isSelezionato() && (operazione.getListaCodiciFigli() == null || operazione.getListaCodiciFigli().size() == 0)) {
                OperazioneIndividuata operazioneIndividuata = operazioniIndividuate.addNewOperazioneIndividuata();
                operazioneIndividuata.setCodice(Utilities.NVL(operazione.getCodice(), ""));  // OBBLIGATORIO
                operazioneIndividuata.setDescrizione(Utilities.NVL(operazione.getDescrizioneOperazione(), "")); // OBBLIGATORIO
            }
        }
    }

    private void compilaSezioneOperazioniNonRichiesteEsplicitamente(ProcessData dataForm, DocumentRoot docRoot) {
        logger.debug("dinamicDocument-compilaSezioneOperazioniNonRichiesteEsplicitamente");
        boolean trovato = false;
        Iterator it = dataForm.getAlberoOperazioni().iterator();
        while (it.hasNext() && !trovato) {
            OperazioneBean operazione = (OperazioneBean) it.next();
            if (operazione.getSino() != null && operazione.getSino().equalsIgnoreCase("S")) {
                if (operazione.getValueSiNo() != null && operazione.getValueSiNo().equalsIgnoreCase("NO")) {
                    trovato = true;
                }
            }
        }
        if (trovato) {
            OperazioniNonRichiesteEsplicitamente opNonRichieste = docRoot.addNewOperazioniNonRichiesteEsplicitamente();
            it = dataForm.getAlberoOperazioni().iterator();
            while (it.hasNext()) {
                OperazioneBean operazione = (OperazioneBean) it.next();
                if (operazione.getSino() != null && operazione.getSino().equalsIgnoreCase("S")) {
                    if (operazione.getValueSiNo() != null && operazione.getValueSiNo().equalsIgnoreCase("NO")) {
                        noNamespace.DocumentRootDocument.DocumentRoot.OperazioniNonRichiesteEsplicitamente.OperazioneIndividuata op = opNonRichieste.addNewOperazioneIndividuata();
                        op.setCodice(Utilities.NVL(operazione.getCodice(), ""));
                        op.setDescrizione(Utilities.NVL(operazione.getDescrizioneOperazione(), ""));
                    }
                }
            }
        }
    }

    private void compilaSezioneDichiarazioniStatiche(ProcessData dataForm, String codSportello, DocumentRoot docRoot) {
        // Dati delle Dichiarazione statiche (NON OBBLIGATORIO)
        HashMap listaDichiarazioniStatiche = new HashMap();
        logger.debug("dinamicDocument-compilaSezioneDichiarazioniStatiche");
        SportelloBean sportelloPD = (SportelloBean) dataForm.getListaSportelli().get(codSportello);
        for (Iterator iterator = sportelloPD.getCodProcedimenti().iterator(); iterator.hasNext();) {
            String codProc = (String) iterator.next();
            ProcedimentoBean procedimentoPD = (ProcedimentoBean) dataForm.getListaProcedimenti().get(codProc);
            if (procedimentoPD != null) {
                for (Iterator iterator2 = procedimentoPD.getCodInterventi().iterator(); iterator2.hasNext();) {
                    String codInt = (String) iterator2.next();
                    boolean trovato = false;
                    Iterator it = dataForm.getInterventi().iterator();
                    while (it.hasNext() && !trovato) {
                        InterventoBean interventoPD = (InterventoBean) it.next();
                        if (interventoPD.getCodice() != null && interventoPD.getCodice().equalsIgnoreCase(codInt)) {
                            trovato = true;
                            for (Iterator iterator3 = interventoPD.getListaCodiciAllegati().iterator(); iterator3.hasNext();) {
                                String codAll = (String) iterator3.next();
                                AllegatoBean allegatoPD = (AllegatoBean) dataForm.getListaAllegati().get(codAll);
                                if (allegatoPD != null && allegatoPD.getNomeFile() == null && allegatoPD.getFlagDic() != null && allegatoPD.getFlagDic().equalsIgnoreCase("S") && allegatoPD.getHref() == null) {
                                    listaDichiarazioniStatiche.put(codAll, allegatoPD);
                                }
                            }
                        }
                    }
                    if (!trovato) {
                        it = dataForm.getInterventiFacoltativi().iterator();
                        while (it.hasNext() && !trovato) {
                            InterventoBean interventoPD = (InterventoBean) it.next();
                            if (interventoPD.getCodice() != null && interventoPD.getCodice().equalsIgnoreCase(codInt)) {
                                trovato = true;
                                for (Iterator iterator3 = interventoPD.getListaCodiciAllegati().iterator(); iterator3.hasNext();) {
                                    String codAll = (String) iterator3.next();
                                    AllegatoBean allegatoPD = (AllegatoBean) dataForm.getListaAllegati().get(codAll);
                                    if (allegatoPD != null && allegatoPD.getNomeFile() == null && allegatoPD.getFlagDic() != null && allegatoPD.getFlagDic().equalsIgnoreCase("S") && allegatoPD.getHref() == null) {
                                        listaDichiarazioniStatiche.put(codAll, allegatoPD);
                                    }
                                }
                            }
                        }
                    }

                }
            }

        }
        int numDichiarazioneStatiche = listaDichiarazioniStatiche.size();

        if (numDichiarazioneStatiche > 0) {
            DichiarazioniStatiche dichiarazioniStatiche = docRoot.addNewDichiarazioniStatiche();
            // PC - ordinamento dichiarazioni
            ArrayList s = listaDichiarazioniSorted(listaDichiarazioniStatiche);
            // Set s = listaDichiarazioniStatiche.keySet();
            // PC - ordinamento dichiarazioni
            for (Iterator iterator = s.iterator(); iterator.hasNext();) {
                String cod = (String) iterator.next();
                DichiarazioniStaticheBean dichiarazioneStaticaPD = (DichiarazioniStaticheBean) dataForm.getListaDichiarazioniStatiche().get(cod);
                noNamespace.DocumentRootDocument.DocumentRoot.DichiarazioniStatiche.DichiarazioneStatica dichiarazioneStatica = dichiarazioniStatiche.addNewDichiarazioneStatica();
                dichiarazioneStatica.setCodice(Utilities.NVL(dichiarazioneStaticaPD.getCodice(), "")); // TODO codice is NULL !!!! // OBBLIGATORIO
                dichiarazioneStatica.setTitolo(dichiarazioneStaticaPD.getTitolo()); // OBBLIGATORIO
                dichiarazioneStatica.setDescrizione(dichiarazioneStaticaPD.getDescrizione()); // OBBLIGATORIO
            } // fine ciclo
        }
    }

    private void compilaSezioneInterventiSelezionati(ProcessData dataForm, String codSportello, DocumentRoot docRoot) {
        // Dati degli Interventi Selezionati (OBBLIGATORIO)
        logger.debug("dinamicDocument-compilaSezioneInterventiSelezionati");
        InterventiSelezionati interventiSelezionati = docRoot.addNewInterventiSelezionati();
        SportelloBean sportelloPD = (SportelloBean) dataForm.getListaSportelli().get(codSportello);
        for (Iterator iterator = sportelloPD.getCodProcedimenti().iterator(); iterator.hasNext();) {
            String codProc = (String) iterator.next();
            ProcedimentoBean procedimentoPD = (ProcedimentoBean) dataForm.getListaProcedimenti().get(codProc);
            if (procedimentoPD != null) {
                for (Iterator iterator2 = procedimentoPD.getCodInterventi().iterator(); iterator2.hasNext();) {
                    String codInt = (String) iterator2.next();
                    boolean trovato = false;
                    Iterator it = dataForm.getInterventi().iterator();
                    while (it.hasNext() && !trovato) {
                        InterventoBean interventoPD = (InterventoBean) it.next();
                        if (interventoPD.getCodice() != null && interventoPD.getCodice().equalsIgnoreCase(codInt)) {
                            trovato = true;
                            InterventoSelezionato interventoSelezionato = interventiSelezionati.addNewInterventoSelezionato();
                            interventoSelezionato.setCodice(interventoPD.getCodice()); // OBBLIGATORIO
                            interventoSelezionato.setDescrizione(interventoPD.getDescrizione()); // OBBLIGATORIO
                        }
                    }
                    if (!trovato) {
                        it = dataForm.getInterventiFacoltativi().iterator();
                        while (it.hasNext() && !trovato) {
                            InterventoBean interventoPD = (InterventoBean) it.next();
                            if (interventoPD.getCodice() != null && interventoPD.getCodice().equalsIgnoreCase(codInt)) {
                                trovato = true;
                                InterventoSelezionato interventoSelezionato = interventiSelezionati.addNewInterventoSelezionato();
                                interventoSelezionato.setCodice(interventoPD.getCodice()); // OBBLIGATORIO
                                interventoSelezionato.setDescrizione(interventoPD.getDescrizione()); // OBBLIGATORIO
                            }
                        }
                    }
                }
            }
        }
    }

    private void compilaSezioneProcedimenti(ProcessData dataForm, String codSportello, DocumentRoot docRoot) {
        logger.debug("dinamicDocument-compilaSezioneProcedimenti");
        SportelloBean sportelloPD = (SportelloBean) dataForm.getListaSportelli().get(codSportello);
        Procedimenti procedimenti = docRoot.addNewProcedimenti();
        int numProcedimenti = 0;
        if (sportelloPD.getCodProcedimenti() != null) {
            numProcedimenti = sportelloPD.getCodProcedimenti().size();
        }
        if (numProcedimenti > 0) {
            for (Iterator iterator = sportelloPD.getCodProcedimenti().iterator(); iterator.hasNext();) {
                String codProc = (String) iterator.next();
                ProcedimentoBean procedimentoPD = (ProcedimentoBean) dataForm.getListaProcedimenti().get(codProc);
                if (procedimentoPD != null) {
                    Procedimento procedimento = procedimenti.addNewProcedimento();

                    // sezione normative del procedimento
                    HashMap mappaNormativeDelProcedimento = new HashMap();
                    ArrayList listaInterventi = procedimentoPD.getCodInterventi();
                    for (Iterator iterator2 = listaInterventi.iterator(); iterator2.hasNext();) {
                        String codInt = (String) iterator2.next();
                        boolean trovato = false;
                        InterventoBean interventoPD = null;
                        Iterator it = dataForm.getInterventi().iterator();
                        while (it.hasNext() && !trovato) {
                            interventoPD = (InterventoBean) it.next();
                            if (interventoPD.getCodice() != null && interventoPD.getCodice().equalsIgnoreCase(codInt)) {
                                trovato = true;

                            }
                        }
                        it = dataForm.getInterventiFacoltativi().iterator();
                        while (it.hasNext() && !trovato) {
                            interventoPD = (InterventoBean) it.next();
                            if (interventoPD.getCodice() != null && interventoPD.getCodice().equalsIgnoreCase(codInt)) {
                                trovato = true;
                            }
                        }

                        if (trovato) {
                            for (Iterator iterator3 = interventoPD.getListaCodiciNormative().iterator(); iterator3.hasNext();) {
                                String codNorm = (String) iterator3.next();
                                mappaNormativeDelProcedimento.put(codNorm, codNorm);
                            }
                        }
                    }
                    Set setCodNormative = mappaNormativeDelProcedimento.keySet();
                    if (setCodNormative.size() > 0) {
                        Normative normative = procedimento.addNewNormative();
                        for (Iterator iterator2 = setCodNormative.iterator(); iterator2.hasNext();) {
                            String codNorm = (String) iterator2.next();
                            NormativaBean normativaPD = (NormativaBean) dataForm.getListaNormative().get(codNorm);
                            if (normativaPD != null) {
                                Normativa normativa = normative.addNewNormativa();
                                normativa.setCodRif(Utilities.NVL(normativaPD.getCodRif(), ""));
                                normativa.setNomeRiferimento(normativaPD.getNomeRiferimento());
                                normativa.setTitoloRiferimento(normativaPD.getTitoloRiferimento());
                            }
                        }
                    }
                    // fine sezione normative procedimento

                    procedimento.setCodice(procedimentoPD.getCodiceProcedimento());
                    Ente enteProcedimento = procedimento.addNewEnte();
                    enteProcedimento.setCodice(Utilities.NVL(procedimentoPD.getCodiceEnte(), ""));
                    enteProcedimento.setDescrizione(Utilities.NVL(procedimentoPD.getEnte(), ""));
                    enteProcedimento.setCodiceClasseEnte(Utilities.NVL(procedimentoPD.getCodiceClasseEnte(), ""));

                    // PC - Dati destinatari per Documento Dinamico - inizio
                    DestinatarioBean destinatario = (DestinatarioBean) dataForm.getListaDestinatari().get(procedimentoPD.getCodDest());
                    Destinatario destinatarioProcedimento = procedimento.addNewDestinatario();
                    destinatarioProcedimento.setIntestazione(Utilities.NVL(destinatario.getIntestazione(), ""));
                    destinatarioProcedimento.setCodice(Utilities.NVL(destinatario.getCod_dest(), ""));
                    destinatarioProcedimento.setNome(Utilities.NVL(destinatario.getNome_dest(), ""));
                    destinatarioProcedimento.setCitta(Utilities.NVL(destinatario.getCitta(), ""));
                    destinatarioProcedimento.setTelefono(Utilities.NVL(destinatario.getTelefono(), ""));
                    destinatarioProcedimento.setIndirizzo(Utilities.NVL(destinatario.getVia(), ""));
                    destinatarioProcedimento.setEmail(Utilities.NVL(destinatario.getEmail(), ""));
                    // PC - Dati destinatari per Documento Dinamico - fine

                    // sezione interventi del procedimento
                    HashMap listaAllegatiPerProcedimento = new HashMap();
                    if ((procedimentoPD.getCodInterventi() != null) && (procedimentoPD.getCodInterventi().size() > 0)) {
                        Interventi interventi = procedimento.addNewInterventi();
                        for (Iterator iterator2 = procedimentoPD.getCodInterventi().iterator(); iterator2.hasNext();) {
                            String codInt = (String) iterator2.next();
                            boolean trovato = false;
                            Iterator it = dataForm.getInterventi().iterator();
                            while (it.hasNext() && !trovato) {
                                InterventoBean interventoPD = (InterventoBean) it.next();
                                if (interventoPD.getCodice() != null && interventoPD.getCodice().equalsIgnoreCase(codInt)) {
                                    trovato = true;
                                    Intervento intervento = interventi.addNewIntervento();
                                    intervento.setCodice(interventoPD.getCodice());
                                    intervento.setDescrizione(interventoPD.getDescrizione());
                                    if ((interventoPD.getListaCodiciAllegati() != null) && (interventoPD.getListaCodiciAllegati().size() > 0)) {
                                        Allegati allegati = intervento.addNewAllegati();

                                        ArrayList al = ordinaHashMap(interventoPD.getListaCodiciAllegati(), dataForm.getListaAllegati());
                                        // for (Iterator iterator3 = interventoPD.getListaCodiciAllegati().iterator(); iterator3.hasNext();) {
                                        for (Iterator iterator3 = al.iterator(); iterator3.hasNext();) {
                                            // PC - ordinamento allegati
                                            String codAll = (String) iterator3.next();
                                            if (codAll != null && !codAll.equalsIgnoreCase("") && !codAll.equalsIgnoreCase("ALLPAG")) {
                                                AllegatoBean allegatoPD = (AllegatoBean) dataForm.getListaAllegati().get(codAll);
                                                Allegato allegato = allegati.addNewAllegato();
                                                allegato.setCodice(allegatoPD.getCodice());
                                                allegato.setCopie(allegatoPD.getCopie());
                                                allegato.setTipoAutocertificazione(allegatoPD.getFlagAutocertificazione());
                                                allegato.setTitolo(allegatoPD.getTitolo());
                                                allegato.setFlgDichiarazione(allegatoPD.getFlagDic());
                                                listaAllegatiPerProcedimento.put(allegatoPD.getCodice(), allegatoPD);
                                            }
                                        }
                                    }
                                }
                            }
                            if (!trovato) {
                                it = dataForm.getInterventiFacoltativi().iterator();
                                while (it.hasNext() && !trovato) {
                                    InterventoBean interventoPD = (InterventoBean) it.next();
                                    if (interventoPD.getCodice() != null && interventoPD.getCodice().equalsIgnoreCase(codInt)) {
                                        trovato = true;
                                        Intervento intervento = interventi.addNewIntervento();
                                        intervento.setCodice(interventoPD.getCodice());
                                        intervento.setDescrizione(interventoPD.getDescrizione());
                                        if ((interventoPD.getListaCodiciAllegati() != null) && (interventoPD.getListaCodiciAllegati().size() > 0)) {
                                            Allegati allegati = intervento.addNewAllegati();
                                            // PC - ordinamento allegati
                                            ArrayList al = ordinaHashMap(interventoPD.getListaCodiciAllegati(), dataForm.getListaAllegati());
                                            // for (Iterator iterator3 = interventoPD.getListaCodiciAllegati().iterator(); iterator3.hasNext();) {
                                            for (Iterator iterator3 = al.iterator(); iterator3.hasNext();) {
                                                // PC - ordinamento allegati
                                                String codAll = (String) iterator3.next();
                                                if (codAll != null && !codAll.equalsIgnoreCase("") && !codAll.equalsIgnoreCase("ALLPAG")) {
                                                    AllegatoBean allegatoPD = (AllegatoBean) dataForm.getListaAllegati().get(codAll);
                                                    Allegato allegato = allegati.addNewAllegato();
                                                    allegato.setCodice(allegatoPD.getCodice());
                                                    allegato.setCopie(allegatoPD.getCopie());
                                                    allegato.setTipoAutocertificazione(allegatoPD.getFlagAutocertificazione());
                                                    allegato.setTitolo(allegatoPD.getTitolo());
                                                    allegato.setFlgDichiarazione(allegatoPD.getFlagDic());
                                                    listaAllegatiPerProcedimento.put(allegatoPD.getCodice(), allegatoPD);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }// fine sezione interventi del procedimento

                    procedimento.setDescrizione(procedimentoPD.getNome());

                    // Oneri dovuti del procedimento
                    procedimento.setOneriDovuti(Utilities.formatNumber(procedimentoPD.getOneriDovuti(), Locale.ITALY, "#0.00##"));

                    // Termini evasione del procedimento
                    procedimento.setTerminiEvasione(procedimentoPD.getTerminiEvasione());

                    // ONERI ANTICIPATI / POSTICIPATI
                    if (procedimentoPD.getListaCodiciOneri().size() > 0) {
                        OneriAnticipati oneriAnticipati = null;
                        OneriPosticipati oneriPosticipati = null;
                        boolean firstAnticipato = true;
                        boolean firstPosticipato = true;
                        for (Iterator iterator2 = procedimentoPD.getListaCodiciOneri().iterator(); iterator2.hasNext();) {
                            String codOnere = (String) iterator2.next();
                            boolean trovato = false;
                            Iterator itOneri = dataForm.getOneriAnticipati().iterator();
                            while (itOneri.hasNext() && !trovato) {
                                OneriBean oneriPD = (OneriBean) itOneri.next();
                                if (oneriPD.getCodice().equalsIgnoreCase(codOnere)) {
                                    trovato = true;
                                    if (firstAnticipato) {
                                        oneriAnticipati = procedimento.addNewOneriAnticipati();
                                        firstAnticipato = false;
                                    }
                                    OnereAnticipato onereAnticipato = oneriAnticipati.addNewOnereAnticipato();
                                    onereAnticipato.setCodice(Utilities.NVL(oneriPD.getCodice(), "")); // TODO risulta essere nullo !!!!
                                    onereAnticipato.setDescrizione(Utilities.NVL(oneriPD.getDescrizione(), ""));
                                    onereAnticipato.setDescrizioneDestinatario(Utilities.NVL(oneriPD.getDesDestinatario(), ""));
                                    onereAnticipato.setImporto(Utilities.formatNumber(oneriPD.getImporto(), Locale.ITALY, "#0.00##"));
                                    onereAnticipato.setNota(Utilities.NVL(oneriPD.getNota(), ""));
                                }
                            }
                            if (!trovato) {
                                itOneri = dataForm.getOneriAnticipati().iterator();
                                while (itOneri.hasNext() && !trovato) {
                                    OneriBean oneriPD = (OneriBean) itOneri.next();
                                    if (oneriPD.getCodice().equalsIgnoreCase(codOnere)) {
                                        trovato = true;
                                        if (firstPosticipato) {
                                            oneriPosticipati = procedimento.addNewOneriPosticipati();
                                            firstPosticipato = false;
                                        }
                                        OnerePosticipato onerePosticipato = oneriPosticipati.addNewOnerePosticipato();
                                        onerePosticipato.setCodice(Utilities.NVL(oneriPD.getCodice(), "")); // TODO risulta essere nullo !!!!
                                        onerePosticipato.setDescrizione(Utilities.NVL(oneriPD.getDescrizione(), ""));
                                        onerePosticipato.setDescrizioneDestinatario(Utilities.NVL(oneriPD.getDesDestinatario(), ""));
                                        onerePosticipato.setImporto(Utilities.formatNumber(oneriPD.getImporto(), Locale.ITALY, "#0.00##"));
                                        onerePosticipato.setNota(Utilities.NVL(oneriPD.getNota(), ""));
                                    }
                                }
                            }

                        }
                    }
                    // fine ONERI ANTICIPATI / POSTICIPATI

                    // tipo Procedimento
                    procedimento.setTipoProcedimento(String.valueOf(procedimentoPD.getTipo()));

                    // allegatiTitoli del Procedimento
                    AllegatiTotali allegatiTotali = procedimento.addNewAllegatiTotali();
                    if ((listaAllegatiPerProcedimento != null) && (listaAllegatiPerProcedimento.size() > 0)) {

                        ArrayList s = ordinaHashMap(new ArrayList(listaAllegatiPerProcedimento.keySet()), dataForm.getListaAllegati());

                        for (Iterator iterator2 = s.iterator(); iterator2.hasNext();) {
                            String codAll = (String) iterator2.next();
                            AllegatoBean allegatoBeanPD = (AllegatoBean) dataForm.getListaAllegati().get(codAll);
                            Allegato allegato = allegatiTotali.addNewAllegato();
                            allegato.setCodice(allegatoBeanPD.getCodice());
                            allegato.setCopie(allegatoBeanPD.getCopie());
                            allegato.setTipoAutocertificazione(allegatoBeanPD.getFlagAutocertificazione());
                            allegato.setTitolo(allegatoBeanPD.getTitolo());
                            allegato.setFlgDichiarazione(allegatoBeanPD.getFlagDic());
                        }
                    } // fine allegati Titoli del Procedimento
                }
            }
        }
    }

    private void compilaSezioneOggettoPratica(ProcessData dataForm, DocumentRoot docRoot) {
        logger.debug("dinamicDocument-compilaSezioneOggettoPratica");
        if (dataForm.getOggettoIstanza() != null) {
            Oggetto oggetto = docRoot.addNewOggetto();
            oggetto.setDescrizione(Utilities.NVL(dataForm.getOggettoIstanza().getDescrizione(), ""));
            oggetto.setHref(Utilities.NVL(dataForm.getOggettoIstanza().getHref(), ""));
            oggetto.setHtml(Utilities.NVL(dataForm.getOggettoIstanza().getHtml(), ""));
            oggetto.setPiedeHref(Utilities.NVL(dataForm.getOggettoIstanza().getPiedeHref(), ""));
            oggetto.setRowCount(String.valueOf(dataForm.getOggettoIstanza().getRowCount()));
            oggetto.setTdCount(String.valueOf(dataForm.getOggettoIstanza().getTdCount()));
            oggetto.setTitolo(Utilities.NVL(dataForm.getOggettoIstanza().getTitolo(), ""));
            if (dataForm.getOggettoIstanza().getCampi() != null && dataForm.getOggettoIstanza().getCampi().size() > 0) {
                noNamespace.DocumentRootDocument.DocumentRoot.Oggetto.Campi listaCampi = oggetto.addNewCampi();
                for (Iterator iterator = dataForm.getOggettoIstanza().getCampi().iterator(); iterator.hasNext();) {
                    HrefCampiBean hrefCampoBean = (HrefCampiBean) iterator.next();
                    CampoHrefType campo = listaCampi.addNewCampo();
                    campo.setContatore(hrefCampoBean.getContatore());
                    campo.setControllo(Utilities.NVL(hrefCampoBean.getControllo(), ""));
                    campo.setDecimali(String.valueOf(hrefCampoBean.getDecimali()));
                    campo.setDescrizione(hrefCampoBean.getDescrizione());
                    campo.setEdit(hrefCampoBean.getEdit());
                    campo.setLunghezza(String.valueOf(hrefCampoBean.getLunghezza()));
                    campo.setNome(hrefCampoBean.getNome());
                    campo.setNumCampo(String.valueOf(hrefCampoBean.getNumCampo()));
                    if (hrefCampoBean.getOpzioniCombo() != null && hrefCampoBean.getOpzioniCombo().size() > 0) {
                        OpzioniComboType opzioniCombo = campo.addNewOpzioniCombo();
                        for (Iterator iterator3 = hrefCampoBean.getOpzioniCombo().iterator(); iterator3.hasNext();) {
                            BaseBean opz = (BaseBean) iterator3.next();
                            OpzioneComboType opz_ = opzioniCombo.addNewOpzioneCombo();
                            opz_.setCodice(opz.getCodice());
                            opz_.setEtichetta(opz.getDescrizione());
                        }
                    }

                    campo.setPosizione(String.valueOf(hrefCampoBean.getPosizione()));
                    campo.setRiga(String.valueOf(hrefCampoBean.getRiga()));
                    String tipo = hrefCampoBean.getTipo();
                    campo.setTipo((tipo.equalsIgnoreCase("A") ? "I" : tipo));
                    campo.setTpControllo(hrefCampoBean.getTp_controllo());
                    campo.setValore(Utilities.NVL(hrefCampoBean.getValore(), ""));
                    if (hrefCampoBean.getOpzioniCombo() != null && hrefCampoBean.getOpzioniCombo().size() > 0 && Utilities.isset(hrefCampoBean.getValoreUtente())) {
                        for (Iterator iterator3 = hrefCampoBean.getOpzioniCombo().iterator(); iterator3.hasNext();) {
                            BaseBean opzioni = (BaseBean) iterator3.next();
                            if (opzioni.getCodice().equalsIgnoreCase(hrefCampoBean.getValoreUtente())) {
                                campo.setValoreUtente(opzioni.getDescrizione());
                            }
                        }
                    } else {
                        campo.setValoreUtente(hrefCampoBean.getValoreUtente());
                    }
                    campo.setCampoCollegato(Utilities.NVL(hrefCampoBean.getCampo_collegato(), ""));
                    campo.setCampoDati(Utilities.NVL(hrefCampoBean.getCampo_dati(), ""));
                    campo.setCampoKey(Utilities.NVL(hrefCampoBean.getCampo_key(), ""));
                    campo.setCampoXmlMod(Utilities.NVL(hrefCampoBean.getCampo_xml_mod(), ""));
                    campo.setNomeXsd(Utilities.NVL(hrefCampoBean.getNome_xsd(), ""));
                    campo.setRaggruppamentoCheck(Utilities.NVL(hrefCampoBean.getRaggruppamento_check(), ""));
                    campo.setValCampoCollegato(Utilities.NVL(hrefCampoBean.getVal_campo_collegato(), ""));
                    campo.setWebServ(Utilities.NVL(hrefCampoBean.getWeb_serv(), ""));
                }
            }
        }
    }

    private void compilaSezioneDichiarazioniDinamiche(ProcessData dataForm, String codSportello, DocumentRoot docRoot, boolean isWhite) {
        logger.debug("dinamicDocument-compilaSezioneDichiarazioniDinamiche");
        DichiarazioniDinamiche dichiarazioniDinamiche = docRoot.addNewDichiarazioniDinamiche();

        // Dati delle Dichiarazione statiche (NON OBBLIGATORIO)
        HashMap listaDichiarazioniDinamiche = new HashMap();
        SportelloBean sportelloPD = (SportelloBean) dataForm.getListaSportelli().get(codSportello);
        for (Iterator iterator = sportelloPD.getCodProcedimenti().iterator(); iterator.hasNext();) {
            String codProc = (String) iterator.next();
            ProcedimentoBean procedimentoPD = (ProcedimentoBean) dataForm.getListaProcedimenti().get(codProc);
            if (procedimentoPD != null) {
                for (Iterator iterator2 = procedimentoPD.getCodInterventi().iterator(); iterator2.hasNext();) {
                    String codInt = (String) iterator2.next();
                    boolean trovato = false;
                    Iterator it = dataForm.getInterventi().iterator();
                    while (it.hasNext() && !trovato) {
                        InterventoBean interventoPD = (InterventoBean) it.next();
                        if (interventoPD.getCodice() != null && interventoPD.getCodice().equalsIgnoreCase(codInt)) {
                            trovato = true;
                            for (Iterator iterator3 = interventoPD.getListaCodiciAllegati().iterator(); iterator3.hasNext();) {
                                String codAll = (String) iterator3.next();
                                AllegatoBean allegatoPD = (AllegatoBean) dataForm.getListaAllegati().get(codAll);
                                if (allegatoPD.getHref() != null && !allegatoPD.getHref().equalsIgnoreCase("")) {
                                    // PC - ordinamento allegati
                                    // listaDichiarazioniDinamiche.put(allegatoPD.getCodice(), allegatoPD.getHref());
                                    listaDichiarazioniDinamiche.put(allegatoPD.getCodice(), allegatoPD);
                                    // PC - ordinamento allegati
                                }
                            }
                        }
                    }
                    if (!trovato) {
                        it = dataForm.getInterventiFacoltativi().iterator();
                        while (it.hasNext() && !trovato) {
                            InterventoBean interventoPD = (InterventoBean) it.next();
                            if (interventoPD.getCodice() != null && interventoPD.getCodice().equalsIgnoreCase(codInt)) {
                                trovato = true;
                                for (Iterator iterator3 = interventoPD.getListaCodiciAllegati().iterator(); iterator3.hasNext();) {
                                    String codAll = (String) iterator3.next();
                                    AllegatoBean allegatoPD = (AllegatoBean) dataForm.getListaAllegati().get(codAll);
                                    if (allegatoPD.getHref() != null && !allegatoPD.getHref().equalsIgnoreCase("")) {
                                        // PC - ordinamento allegati
                                        // listaDichiarazioniDinamiche.put(allegatoPD.getCodice(), allegatoPD.getHref());
                                        listaDichiarazioniDinamiche.put(allegatoPD.getCodice(), allegatoPD);
                                        // PC - ordinamento allegati
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        int numDichirazioniDinamiche = listaDichiarazioniDinamiche.size();

        if (numDichirazioniDinamiche > 0) {
            // PC - ordinamento dichiarazioni
            ArrayList set = listaDichiarazioniSorted(listaDichiarazioniDinamiche);
            // Set set = listaDichiarazioniDinamiche.keySet();
            // Set set = lhm.keySet();
            // PC - ordinamento dichiarazioni
            for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                String cod = (String) iterator.next();
                // PC - ordinamento allegati
                // String codHref = (String) listaDichiarazioniDinamiche.get(cod);
                AllegatoBean alb = (AllegatoBean) listaDichiarazioniDinamiche.get(cod);
                String codHref = alb.getHref();
                // PC - ordinamento allegati
                SezioneCompilabileBean sezioneCompilabilePD = (SezioneCompilabileBean) dataForm.getListaHref().get(codHref);
                DichiarazioneDinamica dichiarazioneDinamica = dichiarazioniDinamiche.addNewDichiarazioneDinamica();
                dichiarazioneDinamica.setCodiceDocumento(Utilities.NVL(cod, "")); // ???? href o codice?
                if (isWhite) {
                    dichiarazioneDinamica.setHtml("");
                } else {
                    dichiarazioneDinamica.setHtml(sezioneCompilabilePD.getHtml());
                }

                dichiarazioneDinamica.setPiedeHref(Utilities.NVL(sezioneCompilabilePD.getPiedeHref(), ""));
                dichiarazioneDinamica.setTitolo(sezioneCompilabilePD.getTitolo());
                dichiarazioneDinamica.setDescrizione(Utilities.NVL(sezioneCompilabilePD.getDescrizione(), ""));
                dichiarazioneDinamica.setHref(sezioneCompilabilePD.getHref());
                dichiarazioneDinamica.setRowCount(Integer.toString(Utilities.NVL(sezioneCompilabilePD.getRowCount())));
                dichiarazioneDinamica.setTdCount(Integer.toString(Utilities.NVL(sezioneCompilabilePD.getTdCount())));
                Campi campi = dichiarazioneDinamica.addNewCampi();
                for (Iterator iterator2 = sezioneCompilabilePD.getCampi().iterator(); iterator2.hasNext();) {
                    HrefCampiBean hrefCampoBean = (HrefCampiBean) iterator2.next();
                    CampoHrefType campo = campi.addNewCampo();
                    campo.setContatore(hrefCampoBean.getContatore());
                    campo.setControllo(Utilities.NVL(hrefCampoBean.getControllo(), ""));
                    campo.setDecimali(String.valueOf(hrefCampoBean.getDecimali()));
                    campo.setDescrizione(hrefCampoBean.getDescrizione());
                    campo.setEdit(hrefCampoBean.getEdit());
                    campo.setLunghezza(String.valueOf(hrefCampoBean.getLunghezza()));
                    campo.setNome(hrefCampoBean.getNome());
                    campo.setNumCampo(String.valueOf(hrefCampoBean.getNumCampo()));

                    if (hrefCampoBean.getOpzioniCombo() != null && hrefCampoBean.getOpzioniCombo().size() > 0) {
                        OpzioniComboType opzioniCombo = campo.addNewOpzioniCombo();
                        for (Iterator iterator3 = hrefCampoBean.getOpzioniCombo().iterator(); iterator3.hasNext();) {
                            BaseBean opz = (BaseBean) iterator3.next();
                            OpzioneComboType opz_ = opzioniCombo.addNewOpzioneCombo();
                            opz_.setCodice(opz.getCodice());
                            opz_.setEtichetta(opz.getDescrizione());
                        }
                    }

                    campo.setPosizione(String.valueOf(hrefCampoBean.getPosizione()));
                    campo.setRiga(String.valueOf(hrefCampoBean.getRiga()));
                    String tipo = hrefCampoBean.getTipo();
                    campo.setTipo((tipo.equalsIgnoreCase("A") ? "I" : tipo));
                    campo.setTpControllo(hrefCampoBean.getTp_controllo());
                    campo.setValore(Utilities.NVL(hrefCampoBean.getValore(), ""));
                    if (hrefCampoBean.getOpzioniCombo() != null && hrefCampoBean.getOpzioniCombo().size() > 0 && Utilities.isset(hrefCampoBean.getValoreUtente())) {
                        for (Iterator iterator3 = hrefCampoBean.getOpzioniCombo().iterator(); iterator3.hasNext();) {
                            BaseBean opzioni = (BaseBean) iterator3.next();
                            if (opzioni.getCodice().equalsIgnoreCase(hrefCampoBean.getValoreUtente())) {
                                campo.setValoreUtente(opzioni.getDescrizione());
                            }
                        }
                    } else {
                        campo.setValoreUtente(hrefCampoBean.getValoreUtente());
                    }
                    campo.setCampoCollegato(Utilities.NVL(hrefCampoBean.getCampo_collegato(), ""));
                    campo.setCampoDati(Utilities.NVL(hrefCampoBean.getCampo_dati(), ""));
                    campo.setCampoKey(Utilities.NVL(hrefCampoBean.getCampo_key(), ""));
                    campo.setCampoXmlMod(Utilities.NVL(hrefCampoBean.getCampo_xml_mod(), ""));
                    campo.setNomeXsd(Utilities.NVL(hrefCampoBean.getNome_xsd(), ""));
                    campo.setRaggruppamentoCheck(Utilities.NVL(hrefCampoBean.getRaggruppamento_check(), ""));
                    campo.setValCampoCollegato(Utilities.NVL(hrefCampoBean.getVal_campo_collegato(), ""));
                    campo.setWebServ(Utilities.NVL(hrefCampoBean.getWeb_serv(), ""));
// PC - controllo firme inizio
                    campo.setMolteplicita(String.valueOf(hrefCampoBean.getMolteplicita()));
// PC - controllo firme inizio                    
                }
            }
        }
    }

    public void compilaSezioneAnagrafica(ProcessData dataForm, String codSportello, DocumentRoot docRoot, boolean isProcuraSPeciale, String codFiscaleProcuratoreSpeciale) {
        AnagraficaBean anagraficaPD = dataForm.getAnagrafica();
        String ret = getCampoDinamicoAnagrafica(dataForm.getAnagrafica().getListaCampi(), "ANAG_CODFISC_DICHIARANTE");
        if (!isProcuraSPeciale || (ret.equalsIgnoreCase(codFiscaleProcuratoreSpeciale))) {
            Anagrafica anagrafica = docRoot.addNewAnagrafica();
            for (Iterator iterator = anagraficaPD.getListaCampi().iterator(); iterator.hasNext();) {
                HrefCampiBean campoAnagPD = (HrefCampiBean) iterator.next();
                CampoAnagraficaType campo = anagrafica.addNewCampoAnagrafica();
                campo.setCampoCollegato(Utilities.NVL(campoAnagPD.getCampo_collegato(), ""));
                campo.setCampoXmlMod(Utilities.NVL(campoAnagPD.getCampo_xml_mod(), ""));
                campo.setContatore(Utilities.NVL(campoAnagPD.getContatore(), ""));
                campo.setControllo(Utilities.NVL(campoAnagPD.getControllo(), ""));
                campo.setDecimali(String.valueOf(campoAnagPD.getDecimali()));
                campo.setDescrizione(Utilities.NVL(campoAnagPD.getDescrizione(), ""));
                campo.setEdit(Utilities.NVL(campoAnagPD.getEdit(), ""));
                campo.setLivello(String.valueOf(campoAnagPD.getLivello()));
                campo.setLunghezza(String.valueOf(campoAnagPD.getLunghezza()));
                campo.setMolteplicita(String.valueOf(campoAnagPD.getMolteplicita()));
                campo.setNome(String.valueOf(campoAnagPD.getNome()));
                campo.setPosizione(String.valueOf(campoAnagPD.getPosizione()));
                campo.setRaggruppamentoCheck(Utilities.NVL(campoAnagPD.getRaggruppamento_check(), ""));
                campo.setRiga(String.valueOf(campoAnagPD.getRiga()));
                campo.setTipo(Utilities.NVL(campoAnagPD.getTipo(), ""));
                campo.setValCampoCollegato(Utilities.NVL(campoAnagPD.getVal_campo_collegato(), ""));
                campo.setValore(Utilities.NVL(campoAnagPD.getValore(), ""));
                campo.setValoreUtente(Utilities.NVL(campoAnagPD.getValoreUtente(), ""));
                campo.setAzione(Utilities.NVL(campoAnagPD.getAzione(), ""));
                if (campoAnagPD.getOpzioniCombo() != null && campoAnagPD.getOpzioniCombo().size() > 0) {
                    OpzioniComboType listaOpz = campo.addNewOpzioniCombo();
                    for (Iterator iterator2 = campoAnagPD.getOpzioniCombo().iterator(); iterator2.hasNext();) {
                        BaseBean bbPD = (BaseBean) iterator2.next();
                        OpzioneComboType opz = listaOpz.addNewOpzioneCombo();
                        opz.setCodice(Utilities.NVL(bbPD.getCodice(), ""));
                        opz.setEtichetta(Utilities.NVL(bbPD.getDescrizione(), ""));
                    }
                }
            }
        }
        // altri dichiaranti
        for (Iterator iterator = dataForm.getAltriRichiedenti().iterator(); iterator.hasNext();) {
            AnagraficaBean altroDichiarantePD = (AnagraficaBean) iterator.next();
            ret = getCampoDinamicoAnagrafica(altroDichiarantePD.getListaCampi(), "ANAG_CODFISC_DICHIARANTE");
            if (!isProcuraSPeciale || (ret.equalsIgnoreCase(codFiscaleProcuratoreSpeciale))) {
                Anagrafica altraAnagrafica = docRoot.addNewAnagrafica();
                for (Iterator iterator2 = altroDichiarantePD.getListaCampi().iterator(); iterator2.hasNext();) {
                    HrefCampiBean campoAnagPD = (HrefCampiBean) iterator2.next();
                    CampoAnagraficaType campo = altraAnagrafica.addNewCampoAnagrafica();
                    campo.setCampoCollegato(Utilities.NVL(campoAnagPD.getCampo_collegato(), ""));
                    campo.setCampoXmlMod(Utilities.NVL(campoAnagPD.getCampo_xml_mod(), ""));
                    campo.setContatore(Utilities.NVL(campoAnagPD.getContatore(), ""));
                    campo.setControllo(Utilities.NVL(campoAnagPD.getControllo(), ""));
                    campo.setDecimali(String.valueOf(campoAnagPD.getDecimali()));
                    campo.setDescrizione(Utilities.NVL(campoAnagPD.getDescrizione(), ""));
                    campo.setEdit(Utilities.NVL(campoAnagPD.getEdit(), ""));
                    campo.setLivello(String.valueOf(campoAnagPD.getLivello()));
                    campo.setLunghezza(String.valueOf(campoAnagPD.getLunghezza()));
                    campo.setMolteplicita(String.valueOf(campoAnagPD.getMolteplicita()));
                    campo.setNome(String.valueOf(campoAnagPD.getNome()));
                    campo.setPosizione(String.valueOf(campoAnagPD.getPosizione()));
                    campo.setRaggruppamentoCheck(Utilities.NVL(campoAnagPD.getRaggruppamento_check(), ""));
                    campo.setRiga(String.valueOf(campoAnagPD.getRiga()));
                    campo.setTipo(Utilities.NVL(campoAnagPD.getTipo(), ""));
                    campo.setValCampoCollegato(Utilities.NVL(campoAnagPD.getVal_campo_collegato(), ""));
                    campo.setValore(Utilities.NVL(campoAnagPD.getValore(), ""));
                    campo.setValoreUtente(Utilities.NVL(campoAnagPD.getValoreUtente(), ""));
                    campo.setAzione(Utilities.NVL(campoAnagPD.getAzione(), ""));
                    if (campoAnagPD.getOpzioniCombo() != null && campoAnagPD.getOpzioniCombo().size() > 0) {
                        OpzioniComboType listaOpz = campo.addNewOpzioniCombo();
                        for (Iterator iterator3 = campoAnagPD.getOpzioniCombo().iterator(); iterator3.hasNext();) {
                            BaseBean bbPD = (BaseBean) iterator3.next();
                            OpzioneComboType opz = listaOpz.addNewOpzioneCombo();
                            opz.setCodice(Utilities.NVL(bbPD.getCodice(), ""));
                            opz.setEtichetta(Utilities.NVL(bbPD.getDescrizione(), ""));
                        }
                    }
                }
            }
        }
    }

    private void compilaSezioneAssociazioneDiCategoria(ProcessData dataForm, String codSportello, DocumentRoot docRoot) {
        logger.debug("dinamicDocument-compilaSezioneAssociazioneDiCategoria");

    }

    private void compilaSezioneComune(ProcessData dataForm, DocumentRoot docRoot) {
        logger.debug("dinamicDocument-compilaSezioneComune");
        noNamespace.DocumentRootDocument.DocumentRoot.Comune comune = docRoot.addNewComune();
        comune.setCodice(dataForm.getComuneSelezionato().getCodEnte()); // OBBLIGATORIO
        comune.setDescrizione(Utilities.NVL(dataForm.getComuneSelezionato().getDescrizione(), "")); // TODO is null!!!
        Indirizzo indirizzoComune = comune.addNewIndirizzo(); 	// OBBLIGATORIO
        indirizzoComune.setCap(Utilities.NVL(dataForm.getComuneSelezionato().getCap(), ""));
        indirizzoComune.setCitta(Utilities.NVL(dataForm.getComuneSelezionato().getCitta(), ""));
        indirizzoComune.setEmail(Utilities.NVL(dataForm.getComuneSelezionato().getEmail(), ""));
        indirizzoComune.setFax(Utilities.NVL(dataForm.getComuneSelezionato().getFax(), ""));
        indirizzoComune.setNumero(Utilities.NVL(dataForm.getComuneSelezionato().getNumero(), ""));
        indirizzoComune.setProvincia(Utilities.NVL(dataForm.getComuneSelezionato().getProvincia(), ""));
        indirizzoComune.setStato(Utilities.NVL(dataForm.getComuneSelezionato().getStato(), ""));
        indirizzoComune.setTelefono(Utilities.NVL(dataForm.getComuneSelezionato().getTelefono(), ""));
        indirizzoComune.setVia(Utilities.NVL(dataForm.getComuneSelezionato().getVia(), ""));
    }

    private void compilaSezioneBollo(ProcessData dataForm, ProcessData dataForm2, String codSportello, DocumentRoot docRoot) {
        String bollo = "N";
        logger.debug("dinamicDocument-compilaSezioneProcedimenti");
        SportelloBean sportelloPD = (SportelloBean) dataForm.getListaSportelli().get(codSportello);
        // Procedimenti procedimenti = docRoot.addNewProcedimenti();
        int numProcedimenti = 0;
        if (sportelloPD.getCodProcedimenti() != null) {
            numProcedimenti = sportelloPD.getCodProcedimenti().size();
        }
        if (numProcedimenti > 0) {
            for (Iterator iterator = sportelloPD.getCodProcedimenti().iterator(); iterator.hasNext();) {
                String codProc = (String) iterator.next();
                ProcedimentoBean procedimentoPD = (ProcedimentoBean) dataForm.getListaProcedimenti().get(codProc);
                if (procedimentoPD != null) {
                    if (procedimentoPD.getFlg_bollo() != null && procedimentoPD.getFlg_bollo().equalsIgnoreCase("S")) {
                        bollo = "S";
                    }
                }
            }
        }
        if (bollo.equalsIgnoreCase("S")) {
            docRoot.setBollo(BolloImpl.S);
        } else {
            docRoot.setBollo(BolloImpl.N);
        }
    }

    private void compilaSezioneRiepilogoOneri(ProcessData dataForm, DocumentRoot docRoot) {
        logger.debug("dinamicDocument-compilaSezioneRiepilogoOneri");
        if (dataForm.getEsitoPagamento() != null && !dataForm.isAttestatoPagamentoObbligatorio() && dataForm.getRiepilogoOneriPagati() != null && dataForm.getRiepilogoOneriPagati().getTotale() > 0) {
            try {
                RiepilogoOneri riepilogoOneri = docRoot.addNewRiepilogoOneri();
                EsitoPagamento esitoPagamento = riepilogoOneri.addNewEsitoPagamento();
                esitoPagamento.setPortaleID("PeopleFwk");
                esitoPagamento.setNumeroOperazione(dataForm.getEsitoPagamento().getNumeroOperazione());
                esitoPagamento.setIDOrdine(Utilities.NVL(dataForm.getEsitoPagamento().getIDOrdine(), ""));
                if (dataForm.getEsitoPagamento().getDataOrdine() != null) {
                    Date dataOra = dataForm.getEsitoPagamento().getDataOrdine();
                    String dataOraOrdine = (new SimpleDateFormat("dd/MM/yyyy")).format(dataOra);
                    esitoPagamento.setDataOraOrdine(dataOraOrdine);
                } else {
                    esitoPagamento.setDataOraOrdine("");
                }
                esitoPagamento.setIDTransazione(dataForm.getEsitoPagamento().getIDTransazione());
                if (dataForm.getEsitoPagamento().getDataTransazione() != null) {
                    Date dataOra = dataForm.getEsitoPagamento().getDataTransazione();
                    String dataOraTransazione = (new SimpleDateFormat("dd/MM/yyyy")).format(dataOra);
                    esitoPagamento.setDataOraTransazione(dataOraTransazione);
                } else {
                    esitoPagamento.setDataOraTransazione("");
                }
                esitoPagamento.setSistemaPagamento(Utilities.NVL(dataForm.getEsitoPagamento().getSistemaPagamento(), ""));
                esitoPagamento.setSistemaPagamentoD(Utilities.NVL(dataForm.getEsitoPagamento().getDescrizioneSistemaPagamento(), ""));
                esitoPagamento.setCircuitoAutorizzativo(Utilities.NVL(dataForm.getEsitoPagamento().getCircuitoAutorizzativo(), ""));
                esitoPagamento.setCircuitoAutorizzativoD(Utilities.NVL(dataForm.getEsitoPagamento().getDescrizioneCircuitoAutorizzativo(), ""));
                esitoPagamento.setCircuitoSelezionato(Utilities.NVL(dataForm.getEsitoPagamento().getCircuitoSelezionato(), ""));
                esitoPagamento.setCircuitoSelezionatoD(Utilities.NVL(dataForm.getEsitoPagamento().getDescrizioneCircuitoSelezionato(), ""));
                double dTransato = (double) dataForm.getEsitoPagamento().getImportoPagato();
                esitoPagamento.setImportoTransato(String.valueOf(dTransato / 100));
                //System.out.println("######Importo pagato: "+String.valueOf(dTransato/100));
                esitoPagamento.setImportoAutorizzato("");
                double dCommissioni = (double) dataForm.getEsitoPagamento().getImportoCommissioni();
                esitoPagamento.setImportoCommissioni(String.valueOf(dCommissioni / 100));
                //System.out.println("######Importo commissioni: "+String.valueOf(String.valueOf(dCommissioni/100)));
                esitoPagamento.setEsito(Utilities.NVL(dataForm.getEsitoPagamento().getEsito(), ""));
                // Modifica come richiesto da Lucia Bellucci il 22/12/2008
                //esitoPagamento.setEsitoD(Utilities.NVL(processDataGlobale.getEsitoPagamento().getDescrizioneEsito(),""));
                if (esitoPagamento.getEsito().equalsIgnoreCase("OK")) {
                    esitoPagamento.setEsitoD("Pagamento effettuato con successo");
                } else {
                    esitoPagamento.setEsitoD(Utilities.NVL(dataForm.getEsitoPagamento().getDescrizioneEsito(), ""));
                }
                if (dataForm.getEsitoPagamento().getDataAutorizzazione() != null) {
                    Date dataOra = dataForm.getEsitoPagamento().getDataAutorizzazione();
                    String dataOraAutoriz = (new SimpleDateFormat("dd/MM/yyyy")).format(dataOra);
                    esitoPagamento.setDataOra(dataOraAutoriz);
                } else {
                    esitoPagamento.setDataOra("");
                }
                esitoPagamento.setAutorizzazione(Utilities.NVL(dataForm.getEsitoPagamento().getNumeroAutorizzazione(), ""));
                esitoPagamento.setDatiSpecifici(Utilities.NVL(dataForm.getEsitoPagamento().getDatiSpecifici(), ""));
                try {
                    riepilogoOneri.setTotale(Double.toString(dataForm.getDatiTemporanei().getTotalePagato()));
                } catch (Exception ex) {
                    riepilogoOneri.setTotale(String.valueOf(dataForm.getEsitoPagamento().getImportoPagato()));
                }
                riepilogoOneri.setModalitaPagamento(dataForm.getDatiTemporanei().getModalitaPagamento());
                if (dataForm.getRiepilogoOneriPagati() != null) {
                    noNamespace.DocumentRootDocument.DocumentRoot.RiepilogoOneri.OneriPagati oneriPagati = riepilogoOneri.addNewOneriPagati();
                    oneriPagati.setTotale(dataForm.getRiepilogoOneriPagati().getTotale());
                    if (dataForm.getRiepilogoOneriPagati().getOneri() != null && !dataForm.getRiepilogoOneriPagati().getOneri().isEmpty()) {
                        noNamespace.DocumentRootDocument.DocumentRoot.RiepilogoOneri.OneriPagati.Onere onere;
                        OnereBean onereBean;
                        for (Iterator oneriIterator = dataForm.getRiepilogoOneriPagati().getOneri().iterator(); oneriIterator.hasNext(); onere.setRiversamentoAutomatico(onereBean.isRiversamentoAutomatico())) {
                            onere = oneriPagati.addNewOnere();
                            onereBean = (OnereBean) oneriIterator.next();
                            if (onereBean.getCodice() != null) {
                                onere.setCodice(onereBean.getCodice());
                            }
                            if (onereBean.getAeCodiceUtente() != null) {
                                onere.setAeCodiceUtente(onereBean.getAeCodiceUtente());
                            }
                            if (onereBean.getAeCodiceEnte() != null) {
                                onere.setAeCodiceEnte(onereBean.getAeCodiceEnte());
                            }
                            if (onereBean.getAeCodiceUfficio() != null) {
                                onere.setAeCodiceUfficio(onereBean.getAeCodiceUfficio());
                            }
                            if (onereBean.getAeTipoUfficio() != null) {
                                onere.setAeTipoUfficio(onereBean.getAeTipoUfficio());
                            }
                            if (onereBean.getCodiceDestinatario() != null) {
                                onere.setCodiceDestinatario(onereBean.getCodiceDestinatario());
                            }
                            if (onereBean.getDescrizione() != null) {
                                onere.setDescrizione(onereBean.getDescrizione());
                            } else {
                                onere.setDescrizione("");
                            }
                            if (onereBean.getDescrizioneDestinatario() != null) {
                                onere.setDescrizioneDestinatario(onereBean.getDescrizioneDestinatario());
                            }
                            onere.setImporto(onereBean.getImporto());
                        }

                    }
                }
            } catch (Exception e) {
            }
        }

        if (dataForm.isAttestatoPagamentoObbligatorio()) {
            noNamespace.DocumentRootDocument.DocumentRoot.RiepilogoOneri riepilogoOneri = docRoot.addNewRiepilogoOneri();
            try {
                if (dataForm.getRiepilogoOneriPagati() != null) {
                    riepilogoOneri.setTotale(Double.toString(dataForm.getRiepilogoOneriPagati().getTotale()));
                    noNamespace.DocumentRootDocument.DocumentRoot.RiepilogoOneri.OneriPagati oneriPagati = riepilogoOneri.addNewOneriPagati();
                    oneriPagati.setTotale(dataForm.getRiepilogoOneriPagati().getTotale());
                    if (dataForm.getRiepilogoOneriPagati().getOneri() != null && !dataForm.getRiepilogoOneriPagati().getOneri().isEmpty()) {
                        noNamespace.DocumentRootDocument.DocumentRoot.RiepilogoOneri.OneriPagati.Onere onere;
                        OnereBean onereBean;
                        for (Iterator oneriIterator = dataForm.getRiepilogoOneriPagati().getOneri().iterator(); oneriIterator.hasNext(); onere.setRiversamentoAutomatico(onereBean.isRiversamentoAutomatico())) {
                            onere = oneriPagati.addNewOnere();
                            onereBean = (OnereBean) oneriIterator.next();
                            if (onereBean.getCodice() != null) {
                                onere.setCodice(onereBean.getCodice());
                            }
                            if (onereBean.getAeCodiceUtente() != null) {
                                onere.setAeCodiceUtente(onereBean.getAeCodiceUtente());
                            }
                            if (onereBean.getAeCodiceEnte() != null) {
                                onere.setAeCodiceEnte(onereBean.getAeCodiceEnte());
                            }
                            if (onereBean.getAeCodiceUfficio() != null) {
                                onere.setAeCodiceUfficio(onereBean.getAeCodiceUfficio());
                            }
                            if (onereBean.getAeTipoUfficio() != null) {
                                onere.setAeTipoUfficio(onereBean.getAeTipoUfficio());
                            }
                            if (onereBean.getCodiceDestinatario() != null) {
                                onere.setCodiceDestinatario(onereBean.getCodiceDestinatario());
                            }
                            if (onereBean.getDescrizione() != null) {
                                onere.setDescrizione(onereBean.getDescrizione());
                            } else {
                                onere.setDescrizione("");
                            }
                            if (onereBean.getDescrizioneDestinatario() != null) {
                                onere.setDescrizioneDestinatario(onereBean.getDescrizioneDestinatario());
                            }
                            onere.setImporto(onereBean.getImporto());
                        }

                    }
                }
            } catch (Exception ex) {
                riepilogoOneri.setTotale("");
            }
            riepilogoOneri.setModalitaPagamento(dataForm.getDatiTemporanei().getModalitaPagamento());
        }

    }

    private void compilaSezioneAllegatiSelezionatiTotali(ProcessData dataForm, String codSportello, DocumentRoot docRoot) {
        logger.debug("dinamicDocument-compilaSezioneAllegatiSelezionatiTotali");
        AllegatiSelezionatiTotali allegatiSelezionatiTotali = docRoot.addNewAllegatiSelezionatiTotali();
        int numAllegatiSelezionatiTotali = 0;
        if (dataForm.getListaAllegati() != null) {
            numAllegatiSelezionatiTotali = dataForm.getListaAllegati().size();
        }
        if (numAllegatiSelezionatiTotali > 0) {

            ArrayList set = ordinaHashMap(new ArrayList(dataForm.getListaAllegati().keySet()), dataForm.getListaAllegati());

            for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                String codAll = (String) iterator.next();
                AllegatoBean allegatoPD = (AllegatoBean) dataForm.getListaAllegati().get(codAll);
                Allegato allegato = allegatiSelezionatiTotali.addNewAllegato();
                allegato.setCodice(allegatoPD.getCodice());
                allegato.setCopie(allegatoPD.getCopie());
                allegato.setTipoAutocertificazione(allegatoPD.getFlagAutocertificazione());
                allegato.setTitolo(allegatoPD.getTitolo());
                allegato.setFlgDichiarazione(Utilities.NVL(allegatoPD.getFlagDic(), ""));
            }
        }
    }

    private void compilaSezioneProfili(ProcessData dataForm, String codSportello, noNamespace.DocumentRootDocument.DocumentRoot docRoot, HttpServletRequest request) {
        noNamespace.DocumentRootDocument.DocumentRoot.DatiAccreditamento datiAccreditamento = docRoot.addNewDatiAccreditamento();
        boolean isDelegate = ProcessUtils.isDelegate(request).booleanValue();
        datiAccreditamento.setQualificaUtente(isDelegate ? noNamespace.DocumentRootDocument.DocumentRoot.DatiAccreditamento.QualificaUtente.N : noNamespace.DocumentRootDocument.DocumentRoot.DatiAccreditamento.QualificaUtente.S);
        if (dataForm.getProfiloOperatore() != null && isDelegate) {
            noNamespace.ProfiloPersonaFisica profiloOperatore = datiAccreditamento.addNewProfiloOperatore();
            profiloOperatore.setNome(StringUtils.defaultString(dataForm.getProfiloOperatore().getNome()));
            profiloOperatore.setCognome(StringUtils.defaultString(dataForm.getProfiloOperatore().getCognome()));
            profiloOperatore.setCodiceFiscale(StringUtils.defaultString(dataForm.getProfiloOperatore().getCodiceFiscale()));
            profiloOperatore.setDataNascita(StringUtils.defaultString(dataForm.getProfiloOperatore().getDataNascitaString()));
            profiloOperatore.setEmail(StringUtils.defaultString(dataForm.getProfiloOperatore().getDomicilioElettronico()));
            profiloOperatore.setResidenza(StringUtils.defaultString(dataForm.getProfiloOperatore().getIndirizzoResidenza()));
            profiloOperatore.setLuogoNascita(StringUtils.defaultString(dataForm.getProfiloOperatore().getLuogoNascita()));
            profiloOperatore.setProvinciaNascita(StringUtils.defaultString(dataForm.getProfiloOperatore().getProvinciaNascita()));
            profiloOperatore.setSesso(StringUtils.defaultString(dataForm.getProfiloOperatore().getSesso()));
            if (dataForm.getAccreditamentoCorrente() != null) {
                noNamespace.AccreditamentoCorrente accreditamentoCorrente = datiAccreditamento.addNewAccreditamentoCorrente();
                accreditamentoCorrente.setIdComune(StringUtils.defaultString(dataForm.getAccreditamentoCorrente().getIdComune()));
                if (dataForm.getAccreditamentoCorrente().getProfilo() != null) {
                    noNamespace.ProfiloAccreditamento profilo = accreditamentoCorrente.addNewProfilo();
                    profilo.setCodiceFiscaleIntermediario(StringUtils.defaultString(dataForm.getAccreditamentoCorrente().getProfilo().getCodiceFiscaleIntermediario()));
                    profilo.setPartitaIvaIntermediario(StringUtils.defaultString(dataForm.getAccreditamentoCorrente().getProfilo().getPartitaIvaIntermediario()));
                    profilo.setDescrizione(StringUtils.defaultString(dataForm.getAccreditamentoCorrente().getProfilo().getDescrizione()));
                    profilo.setEmail(StringUtils.defaultString(dataForm.getAccreditamentoCorrente().getProfilo().getDomicilioElettronico()));
                    profilo.setDenominazione(StringUtils.defaultString(dataForm.getAccreditamentoCorrente().getProfilo().getDenominazione()));
                    profilo.setSedeLegale(StringUtils.defaultString(dataForm.getAccreditamentoCorrente().getProfilo().getSedeLegale()));
                    if (dataForm.getAccreditamentoCorrente().getQualifica().getHasRappresentanteLegale() & (dataForm.getAccreditamentoCorrente().getProfilo().getRappresentanteLegale() != null)) {
                        RappresentanteLegaleProfiloAccreditamento rappresentanteLegale = profilo.addNewRappresentanteLegale();
                        rappresentanteLegale.setNome(StringUtils.defaultString(dataForm.getAccreditamentoCorrente().getProfilo().getRappresentanteLegale().getNome()));
                        rappresentanteLegale.setCognome(StringUtils.defaultString(dataForm.getAccreditamentoCorrente().getProfilo().getRappresentanteLegale().getCognome()));
                        rappresentanteLegale.setCodiceFiscale(StringUtils.defaultString(dataForm.getAccreditamentoCorrente().getProfilo().getRappresentanteLegale().getCodiceFiscale()));
                        rappresentanteLegale.setEmail(StringUtils.defaultString(dataForm.getAccreditamentoCorrente().getProfilo().getRappresentanteLegale().getDomicilioElettronico()));
                        rappresentanteLegale.setSesso(StringUtils.defaultString(dataForm.getAccreditamentoCorrente().getProfilo().getRappresentanteLegale().getSesso()));
                        if (dataForm.getAccreditamentoCorrente().getProfilo().getRappresentanteLegale().getDataNascita() != null) {
                            try {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                rappresentanteLegale.setDataNascita(dateFormat.format(dataForm.getAccreditamentoCorrente().getProfilo().getRappresentanteLegale().getDataNascita()));
                            } catch (Exception e) {
                                rappresentanteLegale.setDataNascita("");
                            }
                        } else {
                            rappresentanteLegale.setDataNascita("");
                        }
                        rappresentanteLegale.setLuogoNascita(StringUtils.defaultString(dataForm.getAccreditamentoCorrente().getProfilo().getRappresentanteLegale().getLuogoNascita()));
                        rappresentanteLegale.setProvinciaNascita(StringUtils.defaultString(dataForm.getAccreditamentoCorrente().getProfilo().getRappresentanteLegale().getProvinciaNascita()));
                        rappresentanteLegale.setIndirizzoResidenza(StringUtils.defaultString(dataForm.getAccreditamentoCorrente().getProfilo().getRappresentanteLegale().getIndirizzoResidenza()));
                        rappresentanteLegale.setCodiceFiscaleIntermediario(StringUtils.defaultString(dataForm.getAccreditamentoCorrente().getProfilo().getRappresentanteLegale().getCodiceFiscaleIntermediario()));
                        rappresentanteLegale.setPartitaIvaIntermediario(StringUtils.defaultString(dataForm.getAccreditamentoCorrente().getProfilo().getRappresentanteLegale().getPartitaIvaIntermediario()));
                    }
                }
                if (dataForm.getAccreditamentoCorrente().getQualifica() != null) {
                    noNamespace.Qualifica qualifica = accreditamentoCorrente.addNewQualifica();
                    qualifica.setIdQualifica(StringUtils.defaultString(dataForm.getAccreditamentoCorrente().getQualifica().getIdQualifica()));
                    qualifica.setTipoQualifica(StringUtils.defaultString(dataForm.getAccreditamentoCorrente().getQualifica().getTipoQualifica()));
                    qualifica.setIncludeRappresentanteLegale(String.valueOf(dataForm.getAccreditamentoCorrente().getQualifica().getHasRappresentanteLegale()));
                    qualifica.setDescrizione(StringUtils.defaultString(dataForm.getAccreditamentoCorrente().getQualifica().getDescrizione()));
                }
            }
        }
    }

    private String getCampoDinamicoAnagrafica(ArrayList listaCampi, String campoXmlMod) {
        boolean trovato = false;
        String ret = "";
        Iterator it = listaCampi.iterator();
        while (it.hasNext() && !trovato) {
            HrefCampiBean campoAnagrafica = (HrefCampiBean) it.next();
            if (campoAnagrafica.getCampo_xml_mod() != null && campoAnagrafica.getCampo_xml_mod().equalsIgnoreCase(campoXmlMod)) {
                trovato = true;
                ret = campoAnagrafica.getValoreUtente();
            }
        }
        return ret;
    }

    public ArrayList ordinaHashMap(ArrayList al, Map hm) {
        ArrayList ret = new ArrayList();
        Map at = new LinkedHashMap();
        for (Iterator ita = al.iterator(); ita.hasNext();) {
            String codAll = (String) ita.next();
            if (codAll != null && !codAll.equalsIgnoreCase("") && !codAll.equalsIgnoreCase("ALLPAG")) {
                AllegatoBean allegatoPD = (AllegatoBean) hm.get(codAll);
                at.put(codAll, allegatoPD);
            }
        }
        ret = listaDichiarazioniSorted(at);
        return ret;
    }

    public ArrayList listaDichiarazioniSorted(Map listaDichiarazioni) {

        ArrayList list = new ArrayList(listaDichiarazioni.values());
        ArrayList lhm = new ArrayList();
        Comparator customCompare = new Comparator() {

            public int compare(Object o1, Object o2) {
                AllegatoBean p1 = (AllegatoBean) o1;
                AllegatoBean p2 = (AllegatoBean) o2;
                int value = p1.getOrdinamentoIntervento().compareTo(p2.getOrdinamentoIntervento());
                if (value == 0) {
                    value = p1.getOrdinamento().compareTo(p2.getOrdinamento());
                }
                return value;
            }
        };
        Collections.sort(list, customCompare);
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            AllegatoBean all = (AllegatoBean) iterator.next();
            lhm.add(cercaKey(listaDichiarazioni, all));
        }
        return lhm;
    }

    public String cercaKey(Map map, AllegatoBean value) {
        String ret = null;
        Set ref = map.keySet();
        Iterator it = ref.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (map.get(o).equals(value)) {
                ret = (String) o;
            }
        }
        return ret;
    }
// PC - ordinamento allegati

    private void compilaSezioneAllegatiFisici(AbstractPplProcess process, ProcessData dataForm, DocumentRoot docRoot, HttpServletRequest request) {
        logger.debug("dinamicDocument-compilaAllegatiFisici");
        if (dataForm.getAllegati() != null && dataForm.getAllegati().size() > 0) {
            ServiceParameters params = (ServiceParameters) request.getSession().getAttribute("serviceParameters");
            String absPathToService = params.get("absPathToService");
            String resourcePath = absPathToService + System.getProperty("file.separator") + "risorse" + System.getProperty("file.separator");
            Properties props[] = UtilProperties.getProperties(resourcePath, "messaggi", process.getCommune().getOid());
            AllegatiFisici allegatiFisici = docRoot.addNewAllegatiFisici();
            for (Iterator iterator = dataForm.getAllegati().iterator(); iterator.hasNext();) {
                Attachment temp = (Attachment) iterator.next();

                String descrizione = ((Attachment) temp).getDescrizione();
                String nomeFile = ((Attachment) temp).getName();
                if (descrizione.startsWith("FREE_")) {
                    descrizione = UtilProperties.getPropertyKey(props[0], props[1], props[2], "testo.allegatoFacoltativo");

                } else {
                    if (dataForm.getListaAllegati().containsKey(descrizione)) {
                        descrizione = ((AllegatoBean) dataForm.getListaAllegati().get(descrizione)).getTitolo();
                    }
                }
                AllegatoFisico allegatoFisico = allegatiFisici.addNewAllegatoFisico();
                allegatoFisico.setDescrizione(descrizione);
                allegatoFisico.setNomeFile(nomeFile);
            }
        }
    }

// PC - tieni traccia delle scelte su allegati facoltativi - inizio
    private void compilaScelteAllegatiFacoltativi(ProcessData dataForm, String codSportello, DocumentRoot docRoot) {
        docRoot.getAllegatiFacoltativi();
        ArrayList tmpCondizione = new ArrayList();
        if (dataForm.getListaAllegatiFacoltativi() != null && dataForm.getListaAllegatiFacoltativi().size() > 0) {
            AllegatiFacoltativi allegatiFacoltativi = docRoot.addNewAllegatiFacoltativi();
            Set set = dataForm.getListaAllegatiFacoltativi().keySet();
            for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();
                AllegatoBean allegato = (AllegatoBean) dataForm.getListaAllegatiFacoltativi().get(key);
                if (!tmpCondizione.contains(key)) {
                    tmpCondizione.add(key);
                    AllegatoFacoltativo allegatoFacoltativo = allegatiFacoltativi.addNewAllegatoFacoltativo();
                    allegatoFacoltativo.setCodice(allegato.getCodiceCondizione());
                    allegatoFacoltativo.setDescrizione(allegato.getTestoCondizione());
                    allegatoFacoltativo.setSelezionato(allegato.isChecked() ? "S" : "N");
                    noNamespace.DocumentRootDocument.DocumentRoot.AllegatiFacoltativi.AllegatoFacoltativo.Allegati allegati = allegatoFacoltativo.addNewAllegati();
                    Allegato all = allegati.addNewAllegato();
                    all.setCodice(allegato.getCodice());
                    all.setChecked(allegato.isChecked() ? "S" : "N");
                    all.setCodiceCondizione(allegato.getCodiceCondizione());
                    all.setCopie(allegato.getCopie());
                    all.setFlgDichiarazione(allegato.getFlagDic());
                    all.setTestoCondizione(allegato.getTestoCondizione());
                    all.setTipoAutocertificazione(allegato.getFlagAutocertificazione());
                    all.setTitolo(allegato.getTitolo());
                } else {
                    for (int i = 0; i < allegatiFacoltativi.getAllegatoFacoltativoArray().length; i++) {
                        if (allegatiFacoltativi.getAllegatoFacoltativoArray(i).getCodice().equals(key)) {
                            AllegatoFacoltativo allegatoFacoltativo = allegatiFacoltativi.getAllegatoFacoltativoArray(i);
                            noNamespace.DocumentRootDocument.DocumentRoot.AllegatiFacoltativi.AllegatoFacoltativo.Allegati allegati = allegatoFacoltativo.addNewAllegati();
                            Allegato all = allegati.addNewAllegato();
                            all.setCodice(allegato.getCodice());
                            all.setChecked(allegato.isChecked() ? "S" : "N");
                            all.setCodiceCondizione(allegato.getCodiceCondizione());
                            all.setCopie(allegato.getCopie());
                            all.setFlgDichiarazione(allegato.getFlagDic());
                            all.setTestoCondizione(allegato.getTestoCondizione());
                            all.setTipoAutocertificazione(allegato.getFlagAutocertificazione());
                            all.setTitolo(allegato.getTitolo());
                        }
                    }

                }
            }
        }
    }
// PC - tieni traccia delle scelte su allegati facoltativi - fine    

}
