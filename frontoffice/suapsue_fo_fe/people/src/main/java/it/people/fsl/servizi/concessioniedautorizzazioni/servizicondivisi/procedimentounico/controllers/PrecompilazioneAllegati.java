/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.controllers;
  
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFamily;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import freemarker.ext.dom.NodeModel;
import it.gruppoinit.commons.DBCPManager;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.ProcedimentoUnicoDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.BaseBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.DocumentoFisicoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.HrefCampiBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.InterventoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.ManagerDocumentiDinamici;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.UtilProperties;
import it.people.process.AbstractPplProcess;
import it.people.util.ServiceParameters;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.DocumentTemplateFactory;
import net.sf.jooreports.templates.ZippedDocumentTemplate;
import noNamespace.DocumentRootDocument;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.apache.xmlbeans.XmlOptions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
 
/**  
 *
 * @author piergiorgio
 */
public class PrecompilazioneAllegati {

    public DocumentoFisicoBean PrecompilazioneAllegati(AbstractPplProcess process, HttpServletRequest request, HttpSession session, String language, String codDoc, String codRif) {

        DocumentoFisicoBean dfb = null;
        try {
            DBCPManager db = (DBCPManager) session.getAttribute("DB");
            ProcedimentoUnicoDAO pud = new ProcedimentoUnicoDAO(db, language);
            dfb = pud.getDocumentoFisico(codDoc, codRif);
            if (codDoc != null && !"".equals(codDoc)) {
                if (dfb.getNomeFile().toUpperCase().endsWith(".ODT")) {
                    dfb = elaboraOdt(dfb, process, request, session, codDoc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dfb;
    }

    private DocumentoFisicoBean elaboraOdt(DocumentoFisicoBean dfb, AbstractPplProcess process, HttpServletRequest request, HttpSession session, String codDoc) throws Exception {
        ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
        String absPathToService = params.get("absPathToService");
        String resourcePath = absPathToService + System.getProperty("file.separator") + "risorse" + System.getProperty("file.separator");
        Properties props[] = UtilProperties.getProperties(resourcePath, "precompilazioneallegati", process.getCommune().getOid());
        int[] portsInt = null;
        if (props[1] != null) {
            if (props[1].getProperty("otherPorts") != null) {
                String[] ports = props[1].getProperty("otherPorts").split(",");
                portsInt = new int[ports.length];
                for (int i = 0; i < ports.length; i++) {
                    int x = Integer.parseInt(ports[i].trim());
                    portsInt[i] = x;
                }

            }
            ProcessData dataForm = (ProcessData) process.getData();
            String output = "";
            DocumentRootDocument xmlDoc = DocumentRootDocument.Factory.newInstance();
            DocumentRootDocument.DocumentRoot docRoot = xmlDoc.addNewDocumentRoot();

            docRoot.setLanguage(dataForm.getLanguage());

            ManagerDocumentiDinamici mdd = new ManagerDocumentiDinamici();

            mdd.compilaSezioneSettore(dataForm, docRoot);

            mdd.compilaSezioneAnagrafica(dataForm, null, docRoot, false, null);

            ByteArrayOutputStream result = new ByteArrayOutputStream();

            XmlOptions options = new XmlOptions();
            options.setCharacterEncoding(request.getCharacterEncoding());
            options.setSavePrettyPrint();
            xmlDoc.save(result, options);

            String s = result.toString();
            ByteArrayInputStream bais = new ByteArrayInputStream(s.getBytes(request.getCharacterEncoding()));

            OutputStream os = new ByteArrayOutputStream();

            OutputStreamWriter osWriter = new OutputStreamWriter(os, request.getCharacterEncoding());

            TransformerFactory tFactory = TransformerFactory.newInstance();

            String communeXslFileName = (new StringBuilder("xsltModelliDinamici_")).append(process.getCommune().getOid()).append(".xsl").toString();
            File templateIn = new File((new StringBuilder(String.valueOf(resourcePath))).append(communeXslFileName).toString());
            if (!templateIn.exists()) {
                templateIn = new File((new StringBuilder(String.valueOf(resourcePath))).append("xsltModelliDinamici.xsl").toString());
            }

            Transformer transformer = tFactory.newTransformer(new StreamSource(templateIn));

            transformer.setOutputProperty(OutputKeys.ENCODING, request.getCharacterEncoding());
            transformer.transform(new StreamSource(bais), new StreamResult(osWriter));

            String xmlOutput = ((ByteArrayOutputStream) os).toString(request.getCharacterEncoding());

            String data = parsingDom(xmlOutput.getBytes(request.getCharacterEncoding()), dataForm, request.getCharacterEncoding());
            int blobLength = (int) dfb.getDocumentoFisico().length();
            byte[] b = generateReport(dfb.getDocumentoFisico().getBytes(1, blobLength), data.getBytes(), request.getCharacterEncoding());

            File inputFile = File.createTempFile("tempInput", ".odt");
            inputFile.deleteOnExit();
            FileOutputStream stream = new FileOutputStream(inputFile);
            stream.write(b);
            stream.close();
            File outputFile = File.createTempFile("tempOutput", ".pdf");
            outputFile.deleteOnExit();
            DocumentFormat df = new DocumentFormat("Portable Document Format", "application/pdf", "pdf");
            df.setExportFilter(DocumentFamily.TEXT, "writer_pdf_Export");
            Map pdfOptions = new HashMap();
            pdfOptions.put("Changes", 2);
            df.setExportOption(DocumentFamily.TEXT, "FilterData", pdfOptions);
            OpenOfficeConnection connection;
            connection = new SocketOpenOfficeConnection(props[1].getProperty("defaultHost").trim(), Integer.parseInt(props[1].getProperty("defaultPort").trim()));
            connection.connect();
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            converter.convert(inputFile, outputFile, df);
            connection.disconnect();

            byte[] bFile = new byte[(int) outputFile.length()];
            FileInputStream fis = new FileInputStream(outputFile);
            fis.read(bFile);
            fis.close();
            int pos = dfb.getNomeFile().toUpperCase().lastIndexOf(".ODT");
            dfb.setNomeFile(dfb.getNomeFile().subSequence(0, pos) + ".pdf");
            dfb.setContentType("application/pdf");

            dfb.setDocumentoFisico(new javax.sql.rowset.serial.SerialBlob(bFile));
            inputFile.delete();
            outputFile.delete();
        }

        return dfb;
    }

    private Element compilaDichiarazioniNormalizzate(ProcessData dataForm, Element root, Document doc) throws Exception {
        Element dichiarazioni = doc.createElement("dichiarazioneDinamiche");
        root.appendChild(dichiarazioni);

        for (Iterator it = dataForm.getListaHref().keySet().iterator(); it.hasNext();) {
            String key = (String) it.next();
            leggiHref((SezioneCompilabileBean) dataForm.getListaHref().get(key), doc, dichiarazioni);
        }

        return dichiarazioni;
    }

    private void leggiHref(SezioneCompilabileBean sezioneCompilabileBean, Document doc, Element dichiarazioni) {
        Element href = doc.createElement("dichiarazioneDinamica");
        dichiarazioni.appendChild(href);

        Element el = doc.createElement("href");
        el.appendChild(doc.createTextNode(sezioneCompilabileBean.getHref()));
        href.appendChild(el);

        el = doc.createElement("titolo");
        el.appendChild(doc.createTextNode(sezioneCompilabileBean.getTitolo()));
        href.appendChild(el);

        el = doc.createElement("piede");
        el.appendChild(doc.createTextNode(sezioneCompilabileBean.getPiedeHref()));
        href.appendChild(el);

        Integer molteplicita = sezioneCompilabileBean.getNumSezioniMultiple();

        if (molteplicita.equals(0)) {
            caricaDatiHrefSingolo(sezioneCompilabileBean, href, doc);
        } else {
            caricaDatiHrefMultiplo(sezioneCompilabileBean, href, doc);
        }
    }

    private void caricaDatiHrefSingolo(SezioneCompilabileBean sezioneCompilabileBean, Element href, Document doc) {
        Element campi = doc.createElement("campi");
        href.appendChild(campi);

        for (Iterator it = sezioneCompilabileBean.getCampi().iterator(); it.hasNext();) {
            HrefCampiBean campo = (HrefCampiBean) it.next();
            caricaCampo(sezioneCompilabileBean, campo, campi, doc, 0);
        }
    }

    private void caricaDatiHrefMultiplo(SezioneCompilabileBean sezioneCompilabileBean, Element href, Document doc) {
        caricaHrefMultiploParteSingola(sezioneCompilabileBean, href, doc);
        caricaHrefMultiploParteMultila(sezioneCompilabileBean, href, doc);
    }

    private void caricaCampo(SezioneCompilabileBean sezioneCompilabileBean, HrefCampiBean campo, Element campi, Document doc, int contatore) {
        if (!campo.getTipo().equalsIgnoreCase("T")) {
            if (campo.getTipo().equalsIgnoreCase("I") || campo.getTipo().equalsIgnoreCase("A") || campo.getTipo().equalsIgnoreCase("N")) {
                Element campoDoc = doc.createElement("campo");
                campi.appendChild(campoDoc);
                Element el = doc.createElement("codice");
                el.appendChild(doc.createTextNode(pulisciNome(campo.getNome(), contatore)));
                campoDoc.appendChild(el);

                el = doc.createElement("descrizione");
                el.appendChild(doc.createTextNode(campo.getDescrizione()));
                campoDoc.appendChild(el);

                String valore = "";
                if (campo.getValoreUtente() != null) {
                    valore = campo.getValoreUtente();
                }
                el = doc.createElement("valore");
                el.appendChild(doc.createTextNode(valore));
                campoDoc.appendChild(el);

            } else if (campo.getTipo().equals("L")) {
                Element campoDoc = doc.createElement("campo");
                campi.appendChild(campoDoc);
                Element el = doc.createElement("codice");
                el.appendChild(doc.createTextNode(pulisciNome(campo.getNome(), contatore)));
                campoDoc.appendChild(el);

                el = doc.createElement("descrizione");
                el.appendChild(doc.createTextNode(campo.getDescrizione()));
                campoDoc.appendChild(el);

                el = doc.createElement("valore");
                el.appendChild(doc.createTextNode(cercaValoreListbox(sezioneCompilabileBean, campo)));
                campoDoc.appendChild(el);

            } else if (campo.getTipo().equalsIgnoreCase("C")) {

                Element campoDoc = doc.createElement("campo");
                campi.appendChild(campoDoc);
                Element el = doc.createElement("codice");
                el.appendChild(doc.createTextNode(pulisciNome(campo.getNome(), contatore)));
                campoDoc.appendChild(el);

                el = doc.createElement("descrizione");
                el.appendChild(doc.createTextNode(campo.getDescrizione()));
                campoDoc.appendChild(el);

                el = doc.createElement("valore");
                if (campo.getValoreUtente() != null) {
                    el.appendChild(doc.createTextNode(campo.getValoreUtente()));
                } else {
                    el.appendChild(doc.createTextNode(""));
                }
                campoDoc.appendChild(el);
            } else if (campo.getTipo().equalsIgnoreCase("R")) {
                if (campo.getValoreUtente() != null) {
                    Element campoDoc = doc.createElement("campo");
                    campi.appendChild(campoDoc);
                    Element el = doc.createElement("codice");
                    el.appendChild(doc.createTextNode(pulisciNome(campo.getNome(), contatore)));
                    campoDoc.appendChild(el);

                    el = doc.createElement("descrizione");
                    el.appendChild(doc.createTextNode(campo.getDescrizione()));
                    campoDoc.appendChild(el);

                    el = doc.createElement("valore");
                    el.appendChild(doc.createTextNode(campo.getValoreUtente()));
                    campoDoc.appendChild(el);
                }
            }

        }
    }

    private String cercaValoreListbox(SezioneCompilabileBean h, HrefCampiBean campo) {
        String ret = "";
        String valoreUtente = campo.getValoreUtente();
        List<BaseBean> opzioniCombo = campo.getOpzioniCombo();
        if (valoreUtente != null && !"".equals(valoreUtente)) {
            if (valoreUtente.toUpperCase().contains("ALTRO#")) {
                String[] token = valoreUtente.split("#");
                if (token != null && token.length > 1) {
                    for (Iterator it = h.getCampi().iterator(); it.hasNext();) {
                        HrefCampiBean c = (HrefCampiBean) it.next();
                        if (c.getNome().equalsIgnoreCase(token[1])) {
                            if (c.getValoreUtente() != null) {
                                ret = c.getValoreUtente();
                            }
                            break;
                        }
                    }
                }
            } else {
                for (Iterator it = opzioniCombo.iterator(); it.hasNext();) {
                    BaseBean o = (BaseBean) it.next();
                    if (o.getCodice().equalsIgnoreCase(valoreUtente)) {
                        ret = o.getDescrizione();
                        break;
                    }
                }
            }
        }
        return ret;
    }

    private void caricaHrefMultiploParteSingola(SezioneCompilabileBean sezioneCompilabileBean, Element href, Document doc) {
        Element campi = doc.createElement("campi");
        href.appendChild(campi);
        for (Iterator it = sezioneCompilabileBean.getCampi().iterator(); it.hasNext();) {
            HrefCampiBean campo = (HrefCampiBean) it.next();
            if (campo.getNumCampo() == 0 && campo.getMolteplicita() == 1) {
                caricaCampo(sezioneCompilabileBean, campo, campi, doc, 0);
            }
        }

    }

    private void caricaHrefMultiploParteMultila(SezioneCompilabileBean sezioneCompilabileBean, Element href, Document doc) {
        Element righe = doc.createElement("righe");
        href.appendChild(righe);
        for (int i = 1; i <= sezioneCompilabileBean.getNumSezioniMultiple(); i++) {
            Element riga = doc.createElement("riga");
            righe.appendChild(riga);
            caricaHrefMultiploParteMultilaRiga(sezioneCompilabileBean, i, riga, doc);
        }
    }

    private void caricaHrefMultiploParteMultilaRiga(SezioneCompilabileBean sezioneCompilabileBean, int i, Element riga, Document doc) {
        Element campi = doc.createElement("campi");
        riga.appendChild(campi);
        for (Iterator it = sezioneCompilabileBean.getCampi().iterator(); it.hasNext();) {
            HrefCampiBean campo = (HrefCampiBean) it.next();
            if (campo.getNumCampo() == 1 && campo.getMolteplicita() == i) {
                caricaCampo(sezioneCompilabileBean, campo, campi, doc, i);
            }
        }
    }

    private String parsingDom(byte[] xml, ProcessData dataForm, String encoding) throws Exception {
        InputStream is = new ByteArrayInputStream(xml);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document dom = db.parse(is);
        String xmlOutput = parseDocument(dom, dataForm, encoding);
        return xmlOutput;
    }

    private String parseDocument(Document dom, ProcessData dataForm, String encoding) throws Exception {
        //get the root element
        Element root = dom.getDocumentElement();
        Element newElement = compilaDichiarazioniNormalizzate(dataForm, root, dom);
        if (newElement != null) {
            root.appendChild(newElement);
        }
        newElement = compilaInterventi(dataForm, root, dom);
        dom.normalize();
//        TransformerFactory tFactory = TransformerFactory.newInstance();
//        Transformer transformer = tFactory.newTransformer();
//        transformer.setOutputProperty(OutputKeys.ENCODING, encoding);

//        DOMSource source = new DOMSource(dom);
//        StringWriter writer = new StringWriter();
//        transformer.transform(source, new StreamResult(writer));
//        String output = writer.getBuffer().toString();
//        return output;
        OutputFormat format = new OutputFormat(dom);
        // as a String
        StringWriter stringOut = new StringWriter();
        XMLSerializer serial = new XMLSerializer(stringOut, format);
        serial.serialize(dom);
        return stringOut.toString();
    }

    public byte[] generateReport(byte[] originalTemplate, byte[] xmlData, String encoding) throws Exception {
        byte[] bytes = null;
        String xml = new String(xmlData);
        System.out.println(xml);
        Object model = NodeModel.parse(new InputSource(new StringReader(xml)), true, true);

        DocumentTemplateFactory dtf = new DocumentTemplateFactory();
        freemarker.template.Configuration conf = dtf.getFreemarkerConfiguration();
        DocumentTemplate template = new ZippedDocumentTemplate(new ByteArrayInputStream(originalTemplate), conf);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        template.createDocument(model, os);
        bytes = os.toByteArray();

        return bytes;
    }

    private String pulisciNome(String nome, int contatore) {
        String ret = nome;
        if (contatore > 1) {
            String indice = "_" + String.valueOf(contatore);
            int position = nome.lastIndexOf(indice);
            ret = nome.substring(0, position);
        }
        return ret;
    }

    private Element compilaInterventi(ProcessData dataForm, Element root, Document doc) {
        // Dati degli Interventi Selezionati (OBBLIGATORIO)
        Element interventi = doc.createElement("interventi");
        root.appendChild(interventi);
        for (Iterator it = dataForm.getInterventi().iterator(); it.hasNext();) {
            Element intervento = doc.createElement("intervento");
            interventi.appendChild(intervento);
            InterventoBean interventoPD = (InterventoBean) it.next();
            Element el = doc.createElement("codice");
            el.appendChild(doc.createTextNode(interventoPD.getCodice()));
            intervento.appendChild(el);
            el = doc.createElement("descrizione");
            el.appendChild(doc.createTextNode(interventoPD.getDescrizione()));
            intervento.appendChild(el);
        }
        return interventi;
    }

}
