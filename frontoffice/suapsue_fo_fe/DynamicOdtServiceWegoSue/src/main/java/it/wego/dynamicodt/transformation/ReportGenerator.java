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
package it.wego.dynamicodt.transformation;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFamily;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.itextpdf.text.pdf.ICC_Profile;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfBoolean;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfICCBased;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.PdfWriter;

import freemarker.ext.dom.NodeModel;
import freemarker.template.Configuration;
import it.wego.dynamicodt.transformation.util.ConfigUtil;
import it.wego.dynamicodt.transformation.util.HtmlSyntaxToolEmbed;
import it.wego.dynamicodt.transformation.util.IOUtil;
import it.wego.dynamicodt.transformation.util.ManageOdt;
import it.wego.dynamicodt.transformation.util.PdfUtils;



import it.wego.dynamicodt.transformation.people.PraticaManager;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.net.ConnectException;
import java.nio.channels.FileChannel;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.UnzippedDocumentTemplate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.InputSource;

/**
 * 
 * @author marcob
 */
public class ReportGenerator {

    public static String TMPDIR = null;
//    public static String encoding = "iso-8859-1";
    private static Log log = LogFactory.getLog(ReportGenerator.class);

    /** Creates a new instance of ReportGenerator */
    public ReportGenerator() {
        ConfigUtil cu = new ConfigUtil();
        TMPDIR = cu.getInstance().getProperty("directoryTemporanea");
//        encoding = cu.getInstance().getProperty("encoding");
    }

    public byte[] generateReport(byte[] originalTemplate, byte[] xmlData,
            String outputType, String encoding) {

        byte[] bytes = null;

        try {

            File dir = new File(TMPDIR);

            log.debug("directory tmp: " + dir.getAbsolutePath());

            File templateFile = File.createTempFile("template", ".odt", dir);

            IOUtil io = new IOUtil();
            io.generateFileFromByteArray(templateFile, originalTemplate);

            log.debug("directory name /tmp/" + templateFile.getName());

            // decomprimo l'odt per modificarne il content.xml
            ManageOdt mo = new ManageOdt(templateFile);
            mo.unzipOdt();

            // da template syntax a jod syntax
            mo.replaceTag();
            
            // segno i paragrafi che contengono jod syntax
            mo.signExtraTag();
            log.debug("Post signExtraTag " + new String(xmlData));
            // nuova chiamata per rigenerare gli html
            xmlData = PraticaManager.cleanHtmlFragment(new String(xmlData, encoding), encoding).getBytes(encoding);
            
            log.debug("Post cleanHtmlFragment " + new String(xmlData));
            // creo documento da modello e dati xml
            File dataFile = File.createTempFile("data", "tmp", dir);
            
            log.debug("Pre generateFileFromByteArray " + new String(xmlData));
            io.generateFileFromByteArray(dataFile, xmlData);
            File templateFileDir = mo.getPathUnzip();
            
            freemarker.template.Configuration conf = new freemarker.template.Configuration();
            conf.setDefaultEncoding(encoding);
            conf.setOutputEncoding(encoding);
// PC - problema check inizio             
//            DocumentTemplate template = new UnzippedDocumentTemplate(
//                    templateFileDir, conf);
// PC - problema check fine 

            
            InputSource inputSource = new InputSource();
            inputSource.setEncoding(encoding);
            inputSource.setByteStream(new ByteArrayInputStream(xmlData));
            Object model = NodeModel.parse(dataFile);
            
            log.debug("Pre creo una cartella Pictures " + new String(xmlData));
            
            // Se non presente creo una cartella Pictures
            File pictureFolder = new File(templateFileDir.getPath() + System.getProperty("file.separator") + "WegoPictures");
            pictureFolder.mkdir();
            File fileSource, fileDest;
            String[] imgs = {"checkko.png", "checkok.png", "radioko.png",
                "radiook.png"
            };
            for (int i = 0; i < imgs.length; i++) {

                InputStream is1 = getClass().getClassLoader().getResourceAsStream("images" + System.getProperty("file.separator") + imgs[i]);

                //log.debug("INPUT STREAM OK");
                //log.debug("INPUT STREAM : " + InputStreamToString(is1));

                // URL dirUrl = getClass().getResource("./");

                // log.debug("DIR URL OK");
                // log.debug("DIR URL : "+dirUrl.getPath());

                // URL fileUrl = new URL(dirUrl, "../../data.txt");

                // fileSource = new File(""+imgs[i]);
                fileDest = new File(pictureFolder.getAbsolutePath() + System.getProperty("file.separator") + imgs[i]);
                // copyFile(fileSource, fileDest);
                //copyInputStreamToFile((FileInputStream) is1, fileDest);
                InputStreamToFile(is1, fileDest);
            }
            
            File outputFileTmp = File.createTempFile("outTmp", ".odt", dir);
// PC - problema check inizio  
            // TODO: MODIFICA EFFETTUATA PER COMPILARE GC
//            DocumentTemplate template = new UnzippedDocumentTemplate(
//                    templateFileDir, conf);
            DocumentTemplate template = new UnzippedDocumentTemplate(
                            templateFileDir);
// PC - problema check fine            
            
            template.createDocument(model, new FileOutputStream(outputFileTmp));

            // pulisco documento generato da extra space
            ManageOdt moOutputFile = new ManageOdt(outputFileTmp);
            moOutputFile.setOutputType(outputType);
            moOutputFile.unzipOdt();
            moOutputFile.removeExtraTag();
            moOutputFile.replaceAmp();
            moOutputFile.regenerateOdtFile();
            moOutputFile.deleteOdtDir();
            File outputFile = null;

            // se output non odt faccio conversione
            if (!"odt".equals(outputType)) {
                log.debug("conversione1");

                ConfigUtil cu = new ConfigUtil();
                String ooHost = cu.getInstance().getProperty("openofficehost");
                String ooPortS = cu.getInstance().getProperty("openofficeport");
                int ooPort = 0;
                try {
                    ooPort = Integer.parseInt(ooPortS);
                } catch (Exception e) {
                    log.error("Error parsing ooPort: " + ooPortS, e);
                }

                OpenOfficeConnection connection = null;
                try {
                    if (ooPort > 0 && ooHost != null) {
                        log.debug("Uso openoffice in " + ooHost + ":" + ooPort);
                        connection = new SocketOpenOfficeConnection(ooHost, ooPort);
                    } else {
                        connection = new SocketOpenOfficeConnection();
                    }
                    log.debug("conversione2");
                    connection.connect();
                } catch (Exception connectException) {
                    log.error("ERROR: connection failed. Please make sure OpenOffice.org is running and listening on port " + SocketOpenOfficeConnection.DEFAULT_PORT + ".", connectException);
                }

                outputFile = File.createTempFile("out", "." + outputType, dir);
                try {
                			                
                    DocumentConverter converter = new OpenOfficeDocumentConverter(
                            connection);
                    converter.convert(outputFileTmp, outputFile);

                } finally {
                    connection.disconnect();
                }

            } else {
                outputFile = outputFileTmp;
            }

            // preparo array di byte da restituire
            if (outputType.toLowerCase().compareTo("html") != 0) {
//            if (!"html".equals(outputType)) {
                bytes = io.getByteArrayFromFile(outputFile);

            } else {
                /*
                 * HtmlSyntaxTool htmlS = new HtmlSyntaxTool(); bytes =
                 * htmlS.embedImage(outputFile,dir);
                 */
                log.error("Chiamo Embedded Image");
                bytes = HtmlSyntaxToolEmbed.embedImage(outputFile, dir);
            }

            if (!log.isDebugEnabled()) {
                //Non ho il livello di log a debug, quindi cancello tutti i file temporanei creati
                log.info("Deleting file " + templateFile.getName());
                templateFile.delete();
                log.info("Deleting file " + dataFile.getName());
                dataFile.delete();
                log.info("Deleting file " + outputFile.getName());
                outputFile.delete();
                log.info("Deleting file " + outputFileTmp.getName());
                outputFileTmp.delete();
                //cancello la cartella temporanea
                log.info("Cancellazione della cartella " + templateFileDir.getPath());
                deleteTmpDir(templateFileDir);
            }

        } catch (Exception e) {
            log.error("Error", e);
        }

        PdfUtils pdfUtils = new PdfUtils();
        
        return  pdfUtils.convertToPDFAFormatAndProtect(bytes);
        
    }

    private boolean deleteTmpDir(File directory) {
        if (directory.isDirectory()) {
            String[] files = directory.list();
            for (String file : files) {
                boolean deleted = deleteTmpDir(new File(directory, file));
                if (!deleted) {
                    return false;
                }
            }
        }
        return directory.delete();
    }

    public static void copyFile(File in, File out) {
        FileChannel inChannel = null, outChannel = null;
        try {
            inChannel = new FileInputStream(in).getChannel();
            outChannel = new FileOutputStream(out).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyInputStreamToFile(FileInputStream in, File out) {
        FileChannel inChannel = null, outChannel = null;
        try {
            inChannel = in.getChannel();
            outChannel = new FileOutputStream(out).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static String InputStreamToString(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1;) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }
}
