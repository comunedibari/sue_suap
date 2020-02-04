/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.reporter.transformation;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFamily;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

import freemarker.ext.dom.NodeModel;
import freemarker.template.TemplateExceptionHandler;
import it.reporter.util.ConfigUtil;
import it.reporter.util.CreaMapQuery;
import it.reporter.util.DbConnection;
import it.reporter.util.IOUtil;
import it.reporter.util.ManageOdt;
import it.reporter.util.OutTypeModel;
import it.reporter.util.OutputType;
import it.reporter.util.PreprocessorXslt;
import it.reporter.util.QueryModel;
import it.reporter.util.ReporterTemplateExceptionHandler;
import it.reporter.xsd.data.DocumentRoot;
import it.reporter.xsd.inputParameters.InputParametersRoot;
import it.reporter.xsd.ootputType.OutputTypeRoot;
import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.DocumentTemplateFactory;
import net.sf.jooreports.templates.UnzippedDocumentTemplate;

/**
 *
 * @author Piergiorgio
 */
public class ReportGenerator {

    public static String TMPDIR = null;
    private static Logger log = LoggerFactory.getLogger(ReportGenerator.class.getName());

    /**
     * Creates a new instance of ReportGenerator
     */
    public ReportGenerator() {
        ConfigUtil cu = new ConfigUtil();
        // TMPDIR = System.getProperty("user.home") + System.getProperty("file.separator") + cu.getInstance().getProperty("directoryTemporanea");
        TMPDIR = cu.getInstance().getProperty("directoryTemporanea");

    }

    public byte[] generateReport(byte[] originalTemplate, byte[] xmlData, byte[] xmlStaticData, byte[] xmlParams, byte[] outputType) {
        byte[] bytes = null;
        DbConnection dbc = new DbConnection();
        Connection conn = null;
        OutputType otc = new OutputType();
        OutTypeModel outType = null;
        Map<String, QueryModel> mapQuery;
        CreaMapQuery cmq = new CreaMapQuery();
        String xml = null;
        DocumentRoot dr;
        JAXBContext jaxbCtx;
        Unmarshaller unmarshaller;
        InputParametersRoot ipr;
        HashMap<String, String> listaFile = null;
        PreprocessorXslt pXslt = new PreprocessorXslt();
        HashMap<String, String> listaDefault = new HashMap<String, String>();
        try {
//            File dir = new File(TMPDIR);
            System.out.println("TMPDIR :"+TMPDIR);
            String userHomeTemp = TMPDIR;
            File dir = new File(userHomeTemp);

            File templateFile = File.createTempFile("template", ".odt", dir);

            IOUtil io = new IOUtil();
            //
            String pStaticData = "";
            String pData = "";
            String p = "";
            boolean existStaticData = false;
            boolean existData = false;
            if (xmlStaticData != null) {
                pStaticData = new String(xmlStaticData);
                pStaticData = pStaticData.replaceAll("\n", "");
                pStaticData = pStaticData.replaceAll("\r", "");
                if (!pStaticData.equals("")) {
                    existStaticData = true;
                    p = pStaticData;
                }
                System.out.println("Static data :" + p);
            }
            if (xmlData != null) {
                pData = new String(xmlData);
                pData = pData.replaceAll("\n", "");
                pData = pData.replaceAll("\r", "");
                if (!pData.equals("")) {
                    existData = true;
                    p = pData;
                }
                System.out.println("Dati :" + p);
            }
            // applico la trasformazione xsl per eventuali include esterni su tutti gli xml di input
            String xmlParametri = new String(xmlParams);
            xmlParametri = pXslt.PreprocessorXslt(xmlParametri);
            String xmlOutputParametri = new String(outputType);
            xmlOutputParametri = pXslt.PreprocessorXslt(xmlOutputParametri);
            p = pXslt.PreprocessorXslt(p);

            // creo la directory temporanea delle immagini
            File tmpDirectoryImage = File.createTempFile("ImageDir", "", dir);
            io.deleteDirectory(tmpDirectoryImage);
            tmpDirectoryImage.mkdir();
            // creo la directory temporanea delle immagini
            File tmpDirectoryDocument = File.createTempFile("DocumentDir", "", dir);
            io.deleteDirectory(tmpDirectoryDocument);
            tmpDirectoryDocument.mkdir();

            jaxbCtx = JAXBContext.newInstance(OutputTypeRoot.class.getPackage().getName());
            unmarshaller = jaxbCtx.createUnmarshaller();
            OutputTypeRoot ot = (OutputTypeRoot) unmarshaller.unmarshal(new ByteArrayInputStream(xmlOutputParametri.getBytes()));
            outType = otc.getTypeOutput(ot);

            if (existData) {
                jaxbCtx = JAXBContext.newInstance(DocumentRoot.class.getPackage().getName());
                unmarshaller = jaxbCtx.createUnmarshaller();
                dr = (DocumentRoot) unmarshaller.unmarshal(new ByteArrayInputStream(p.getBytes()));
                jaxbCtx = JAXBContext.newInstance(OutputTypeRoot.class.getPackage().getName());
                unmarshaller = jaxbCtx.createUnmarshaller();

                jaxbCtx = JAXBContext.newInstance(InputParametersRoot.class.getPackage().getName());
                unmarshaller = jaxbCtx.createUnmarshaller();
                ipr = (InputParametersRoot) unmarshaller.unmarshal(new ByteArrayInputStream(xmlParametri.getBytes()));

                conn = dbc.getConnection(dr.getConnections());

                mapQuery = cmq.PopolaMapQuery(dr.getQueryes());

                xml = cmq.PopolaXmlQuery(conn, mapQuery, dr.getDefinitions(), ipr, tmpDirectoryImage, tmpDirectoryDocument, listaDefault);

                listaFile = cmq.generaListaFileEsterni(ipr);
            } else {
                if (existStaticData) {
                    xml = p;
                }
            }
            log.debug("directory tmp: " + dir.getAbsolutePath());

            io.generateFileFromByteArray(templateFile, originalTemplate);

            log.debug("directory name /tmp/" + templateFile.getName());

            // 
            ManageOdt mo = new ManageOdt(templateFile);

            // effettuo l'inclusione dei documenti
            mo.includiFilesInTemplate(listaFile);

            // decomprimo l'odt per modificarne il content.xml
            mo.unzipOdt();

            // da template syntax a jod syntax
            mo.replaceTag();

            // segno i paragrafi che contengono jod syntax
            mo.signExtraTag();

            // copio le immagini nella cartella Pictures
            mo.copyImage(tmpDirectoryImage);

            // creo documento da modello e dati xml

            File dataFile = File.createTempFile("data", "tmp", dir);

            io.generateFileFromByteArray(dataFile, xml.getBytes());

            File templateFileDir = mo.getPathUnzip();

            //DocumentTemplate template = new UnzippedDocumentTemplate(templateFileDir, null);

            Object model = NodeModel.parse(dataFile);

            File outputFileTmp = File.createTempFile("outTmp", ".odt", dir);
            DocumentTemplateFactory dtf = new DocumentTemplateFactory();
            freemarker.template.Configuration conf = dtf.getFreemarkerConfiguration();
            conf.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
            conf.setTemplateExceptionHandler(new ReporterTemplateExceptionHandler());
            DocumentTemplate template = new UnzippedDocumentTemplate(templateFileDir, conf);
            template.createDocument(model, new FileOutputStream(outputFileTmp));

            // pulisco documento generato da extra space
            ManageOdt moOutputFile = new ManageOdt(outputFileTmp);
            moOutputFile.unzipOdt();
            moOutputFile.removeExtraTag();
            

            moOutputFile.regenerateOdtFile();
            // inserisco file da blob
            moOutputFile.includiFilesBlobInTemplate(tmpDirectoryDocument);
            // rimpiazza con default
            moOutputFile.rimpiazzaDefault(listaDefault);
            if (!log.isDebugEnabled()) {
                moOutputFile.deleteOdtDir();
            }

            File outputFile = null;

            // se output non odt faccio conversione
            if (!"odt".equalsIgnoreCase(outType.getOutputType())) {
                log.debug("conversione1");

                ConfigUtil cu = new ConfigUtil();
                String ooHost = cu.getInstance().getProperty("openofficehost");
                String ooPortS = cu.getInstance().getProperty("openofficeport");
                int ooPort = 0;
                try {
                    ooPort = Integer.parseInt(ooPortS);
                } catch (Exception e) {
                }

                OpenOfficeConnection connection;
                if (ooPort > 0 && ooHost != null) {
                    log.debug("Uso openoffice in " + ooHost + ":" + ooPort);
                    connection = new SocketOpenOfficeConnection(ooHost, ooPort);
                } else {
                    connection = new SocketOpenOfficeConnection();
                }
                try {
                    log.debug("conversione2");
                    connection.connect();
                } catch (ConnectException connectException) {
                    log.debug("ERROR: connection failed. Please make sure OpenOffice.org is running and listening on port "
                            + SocketOpenOfficeConnection.DEFAULT_PORT
                            + ".");
                }

                outputFile = File.createTempFile("out", "." + outType.getOutputType(), dir);

                try {
                    DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
                    if (outType.getOutputType().equalsIgnoreCase("pdf")) {
                        DocumentFormat df = new DocumentFormat("Portable Document Format", "application/pdf", "pdf");
                        df.setExportFilter(DocumentFamily.TEXT, "writer_pdf_Export");
                        Map pdfFilterData = new HashMap();
                        pdfFilterData.put("SelectPdfVersion", 1);
                        pdfFilterData.put("UseTaggedPDF", Boolean.TRUE);

                        df.setExportOption(DocumentFamily.TEXT, "FilterData", pdfFilterData);

                        converter.convert(outputFileTmp, outputFile, df);
                    } else {
                        converter.convert(outputFileTmp, outputFile);
                    }
                } finally {
                    connection.disconnect();
                }

            } else {
                outputFile = outputFileTmp;
            }

            // preparo array di byte da restituire

            bytes = io.getByteArrayFromFile(outputFile);

            // verifico se scrivere il file in output
            scriviFileInOutput(outputFile, outType);

            // cancello odt di input sia compattato che estratto
            if (!log.isDebugEnabled()) {
                mo.deleteOdtDir();
            }
            if (!log.isDebugEnabled()) {
                templateFile.delete();
            }
            // cancello xml file
            if (!log.isDebugEnabled()) {
                dataFile.delete();
            }
            // cancello file di output temporaneo
            if (!log.isDebugEnabled()) {
                outputFile.delete();
            }
            if (!log.isDebugEnabled()) {
                outputFileTmp.delete();
            }
            if (!log.isDebugEnabled()) {
                io.deleteDirectory(tmpDirectoryImage);
            }
            if (!log.isDebugEnabled()) {
                io.deleteDirectory(tmpDirectoryDocument);
            }
        } catch (Exception e) {
            String p = "";
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                }
            }
        }
        return bytes;
    }

    public static void InputStreamToFile(InputStream in, File out) {
        try {
            OutputStream outStream = new FileOutputStream(out);

            byte buf[] = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                outStream.write(buf, 0, len);
            }
            outStream.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void scriviFileInOutput(File inputFile, OutTypeModel otm) {
        OutputStream out = null;
        InputStream in = null;
        if (otm.getOutputPath() != null && !otm.getOutputPath().equals("")) {
            if (otm.getOutputName() != null && !otm.getOutputName().equals("")) {
                File f2 = new File(otm.getOutputPath() + File.separator + otm.getOutputName() + "." + otm.getOutputType());
                try {
                    out = new FileOutputStream(f2);
                    in = new FileInputStream(inputFile);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }

    }
}
