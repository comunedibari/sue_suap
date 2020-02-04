/*
 * ManageOdt.java
 *
 * Created on April 2, 2008, 3:01 PM
 *
 * Urlandus srl
 */
package it.reporter.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jdom.Text;
import org.jopendocument.dom.ODSingleXMLDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import it.reporter.transformation.ReportGenerator;

/**
 *
 * @author marcob
 */
public class ManageOdt {

    private static Logger log = LoggerFactory.getLogger(ManageOdt.class.getName());
    private File odtFile = null;

    /**
     * Creates a new instance of ManageOdt
     */
    public ManageOdt(File odtFile) {

        log.debug("Creo ManageOdt su " + odtFile.getAbsolutePath());
        this.odtFile = odtFile;
    }

    public void unzipOdt() throws Exception {
        Zip zu = new Zip();
        //zu.setDirectory(getPathUnzip());
        zu.unzip(new FileInputStream(odtFile), getPathUnzip());

    }

    public void deleteOdtDir() throws Exception {
        //log.debug("cancello: "+getPathUnzip().getAbsolutePath());
        IOUtil io = new IOUtil();
        io.deleteDirectory(getPathUnzip());
    }

    public File getContentXml() {
        String contentPath = getPathUnzip().getAbsolutePath() + File.separator + "content.xml";
        File content = new File(contentPath);
        return content;
    }

    public File getStylesXml() {
        String contentPath = getPathUnzip().getAbsolutePath() + File.separator + "styles.xml";
        File content = new File(contentPath);
        return content;
    }

    public File getManifestXml() {
        String manifestPath = getPathUnzip().getAbsolutePath() + File.separator + "META-INF" + File.separator + "manifest.xml";
        File manifest = new File(manifestPath);
        return manifest;
    }

    public File getPathUnzip() {
        String tmpOdtName = odtFile.getName();

        String tmpDirOdtName = "dir" + tmpOdtName.substring(0, tmpOdtName.length() - 4);
        File templateDirZip = new File(ReportGenerator.TMPDIR + File.separator + tmpDirOdtName);
        templateDirZip.mkdir();
        return templateDirZip;
    }

    public void replaceTag() throws Exception {
//        File contentXml = getContentXml();
//        String contentXmlTemplate = this.convertFileIntoString(contentXml);
//
//        String contentXmlJod = contentXmlTemplate.replaceAll("^[#image(.*?)^]", "[#---image $1---]");
//
//        OutputStream fout = new FileOutputStream(contentXml);
//        OutputStream bout = new BufferedOutputStream(fout);
//        OutputStreamWriter out = new OutputStreamWriter(bout, "UTF-8");
//        out.write(contentXmlJod);
//        out.flush();
//        out.close();
//
//        File stylesXml = getStylesXml();
//        if (stylesXml.exists()) {
//            String stylesXmlTemplate = this.convertFileIntoString(stylesXml);
//
//            String stylesXmlJod = stylesXmlTemplate.replaceAll("^[#image(.*?)^]", "[#---image $1---]");
//
//            fout = new FileOutputStream(stylesXml);
//            bout = new BufferedOutputStream(fout);
//            out = new OutputStreamWriter(bout, "UTF-8");
//            out.write(stylesXmlJod);
//            out.flush();
//            out.close();
//        }
    }

    private String convertFileIntoString(File file) throws Exception {

        String result;

        StringBuffer sb;
        BufferedReader br = null;
        try {
            //br = new BufferedReader(new FileReader(file));
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            sb = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) { //while not at the end of the file stream do
                sb.append(line);
            }//next line
            result = sb.toString();
        } finally {
            if (br != null) {
                br.close();
            }
        }
        return result;
    }

    public void signExtraTag() {
        try {
            File file = getContentXml();
            signExtraTag(file);
            file = getStylesXml();
            signExtraTag(file);
        } catch (Exception e) {
            log.error("Errore", e);
        }
    }

    public void signExtraTag(File file) {
        try {
            if (file.exists()) {
                // Create a factory
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                // Use the factory to create a builder
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(file);
                // Get a list of all elements in the document
                NodeList list = doc.getElementsByTagName("*");

                for (int i = 0; i < list.getLength(); i++) {
                    // Get element
                    Element element = (Element) list.item(i);

                    if (element.hasChildNodes()
                            && element.getChildNodes().getLength() == 1
                            && element.getFirstChild().getNodeType() == Node.TEXT_NODE) {
                        String nodeValue = element.getFirstChild().getNodeValue();

// todo
//                        log.debug("nodeValue" + nodeValue);
//
//                        if (nodeValue.indexOf("[#list") != -1
//                                || nodeValue.indexOf("[/#list]") != -1) {
//                            element.setAttribute("toRemove", "true");
//                            log.debug("setto toRemove per " + nodeValue);
//                        }
//
//                        if (nodeValue.indexOf("[#if") != -1
//                                || nodeValue.indexOf("[/#if]") != -1) {
//                            element.setAttribute("toRemove", "true");
//                            log.debug("setto toRemove per " + nodeValue);
//                        }
//
//                        if (nodeValue.indexOf("[#assign") != -1) {
//                            element.setAttribute("toRemove", "true");
//                            log.debug("setto toRemove per " + nodeValue);
//                        }
//
//                        if (nodeValue.indexOf("[#else") != -1) {
//                            element.setAttribute("toRemove", "true");
//                            log.debug("setto toRemove per " + nodeValue);
//                        }
//
//
//                        if (nodeValue.indexOf("[#---image ") != -1) {
//                            element.setAttribute("toImage", "true");
//
//                        }
// todo
                    }
                }

                Source source = new DOMSource(doc);

                // Prepare the output file
                Result result = new StreamResult(file);

                // Write the DOM document to the file
                Transformer xformer = TransformerFactory.newInstance().newTransformer();
                xformer.transform(source, result);

            } else {
                log.debug("File not found! (SignExtraTag): " + file.getName());
            }
        } catch (Exception e) {
            log.error("Errore", e);
        }

    }

    public void removeExtraTag() {
        try {
            File file = getContentXml();
            removeExtraTag(file);
            file = getStylesXml();
            removeExtraTag(file);
        } catch (Exception e) {
            log.error("Error", e);
        }
    }

    public void removeExtraTag(File file) {
        try {
            if (file.exists()) {
                // Create a factory
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                // Use the factory to create a builder
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(file);
                // Get a list of all elements in the document
                NodeList list = doc.getElementsByTagName("*");
                for (int i = 0; i < list.getLength(); i++) {
                    // Get element
                    Element element = (Element) list.item(i);

                    log.debug("element: " + element.getNodeName() + " valore = " + element.getTextContent());
                    if (element.hasAttribute("toRemove")) {
                        element.getParentNode().removeChild(element);
                        i--;
                    } else if (element.getChildNodes().getLength() == 1 && element.getChildNodes().item(0).getNodeType() == Element.TEXT_NODE) {
                        //Verifico che è un image
//                        if (element.getTextContent() != null && element.getTextContent().length() > 6 && element.getTextContent().substring(0, 6).equals("Image=") && element.getNextSibling() == null) {
                        if (element.getTextContent() != null && element.getTextContent().length() > 6 && element.getTextContent().substring(0, 6).equals("Image=")) {
                            String nomeImg = element.getTextContent().substring(6);
                            PreProcessorUtil pp = new PreProcessorUtil();
                            NodeList figli = element.getChildNodes();
                            Node elementImg = null;
                            for (int ii = 0; ii < figli.getLength(); ii++) {
                                Node myElement = figli.item(ii);

                                String valueImg = myElement.getNodeValue();

                                if (valueImg != null) {
                                    elementImg = myElement;
                                    break;
                                }
                            }

                            DocumentFragment fragment = pp.buildImage(getPathUnzip(), nomeImg, doc);

                            if (elementImg != null) {
                                elementImg.setNodeValue("");
                            }

                            if (fragment != null) {
                                element.appendChild(fragment);
                            }
                        }
                    }

                }

                Source source = new DOMSource(doc);

                // Prepare the output file
                Result result = new StreamResult(file);

                // Write the DOM document to the file
                Transformer xformer = TransformerFactory.newInstance().newTransformer();
                xformer.transform(source, result);

            } else {
                log.debug("File not found! (RemoveExtraTag) : " + file.getName());
            }


        } catch (Exception e) {
            log.error("Error", e);
        }
    }

    public void regenerateOdtFile() throws Exception {
        // Check that the directory is a directory, and get its contents
        File d = getPathUnzip();


        Zip.zipDir(d, odtFile);
    }

    public void copyImage(File tmpFile) throws IOException, IllegalArgumentException, ParserConfigurationException, SAXException, TransformerConfigurationException, TransformerException {
        File file = getManifestXml();
        if (file.exists()) {

            // Create a factory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Use the factory to create a builder
            DocumentBuilder builder = factory.newDocumentBuilder();

            builder.setEntityResolver(new EntityResolver() {

                @Override
                public InputSource resolveEntity(String publicId, String systemId)
                        throws SAXException, IOException {
                    if (systemId.contains("Manifest.dtd")) {
                        return new InputSource(new StringReader(""));
                    } else {
                        return null;
                    }
                }
            });

            Document doc = builder.parse(file);
            Node node = doc.getDocumentElement();
            Element link = doc.createElement("manifest:file-entry");
            link.setAttribute("manifest:media-type", "");
            link.setAttribute("manifest:full-path", "Pictures/");
            node.appendChild(link);

            File pictureFolder = new File(getPathUnzip() + File.separator + "Pictures");

            pictureFolder.mkdir();
            String[] images = tmpFile.list();
            String pathFrom = tmpFile.getPath();
            String pathTo = pictureFolder.getPath();
            String filename;
            File inpFile;
            File outFile;
            if (images != null) {
                for (int i = 0; i < images.length; i++) {
                    // Get filename of file or directory
                    filename = images[i];
                    inpFile = new File(pathFrom + File.separator + filename);
                    outFile = new File(pathTo + File.separator + filename);
                    copy(inpFile, outFile);
                    InputStream is = new BufferedInputStream(new FileInputStream(inpFile));
                    String mimeType = URLConnection.guessContentTypeFromStream(is);

                    link = doc.createElement("manifest:file-entry");
                    link.setAttribute("manifest:media-type", mimeType);
                    link.setAttribute("manifest:full-path", "Pictures/" + filename);
                    node.appendChild(link);

                }
            }
            // elimino cartella temporanea immagini
            IOUtil io = new IOUtil();
            if (!log.isDebugEnabled()) {
                io.deleteDirectory(tmpFile);
            }
            Source source = new DOMSource(doc);
            Result result = new StreamResult(file);
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            log.debug("xformer.transform - source : " + source.toString() + " result : " + result.toString());
            xformer.transform(source, result);
        }

    }

    void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    public void includiFilesInTemplate(HashMap<String, String> listaFile) throws Exception {
        String nomeFile;
        String file;
        File fileFisico;
        if (listaFile != null) {

            ODSingleXMLDocument p1 = ODSingleXMLDocument.createFromFile(odtFile);
            org.jdom.Element body = p1.getBody();

            Map.Entry entry;
            for (Iterator it = listaFile.entrySet().iterator(); it.hasNext();) {
                entry = (Map.Entry) it.next();
                nomeFile = (String) entry.getKey();
                file = (String) entry.getValue();

                String ricerca = "[#includeDocument " + nomeFile + "]";
                org.jdom.Element elemento = getElement(body, ricerca);
                while (elemento != null) {
                    fileFisico = new File(file);
                    ODSingleXMLDocument p2;
                    try {
                        p2 = ODSingleXMLDocument.createFromFile(fileFisico);
                    } catch (Exception e) {
                        log.error("Errore import documento da FileSystem");
                        throw e;
                    }
                    p1.replace(elemento, p2);
                    elemento = getElement(body, ricerca);
                }

            }
            String ricerca = "[#includeDocument";
            org.jdom.Element elemento = getElement(body, ricerca);
            while (elemento != null) {
                elemento.setText("");
                elemento = getElement(body, ricerca);
            }
            //odtFile.delete();
            p1.saveAs(odtFile);

        }
    }

    public void includiFilesBlobInTemplate(File tmpDirectoryDocument) throws Exception {
        String nomeTotale;
        String file;
        File fileFisico;


        ODSingleXMLDocument p1 = ODSingleXMLDocument.createFromFile(odtFile);
        org.jdom.Element body = p1.getBody();

        String ricerca = "Document=";
        org.jdom.Element elemento = getElement(body, ricerca);
        while (elemento != null) {
            nomeTotale = elemento.getText();
            file = nomeTotale.split(ricerca)[1];
            fileFisico = new File(tmpDirectoryDocument.getPath() + File.separator + file);
            ODSingleXMLDocument p2;
            try {
                p2 = ODSingleXMLDocument.createFromFile(fileFisico);
            } catch (Exception e) {
                log.error("Errore import documento da DB");
                throw e;
            }
            p1.replace(elemento, p2);
            elemento = getElement(body, ricerca);
        }



        elemento = getElement(body, ricerca);
        while (elemento != null) {
            elemento.setText("");
            elemento = getElement(body, ricerca);
        }
        //odtFile.delete();
        p1.saveAs(odtFile);
    }

    public void rimpiazzaDefault(HashMap<String, String> listaDefault) throws Exception {
        String segnaposto;
        String defaultValue;

        if (listaDefault != null) {

            ODSingleXMLDocument p1 = ODSingleXMLDocument.createFromFile(odtFile);
            org.jdom.Element body = p1.getBody();

            Map.Entry entry;
            for (Iterator it = listaDefault.entrySet().iterator(); it.hasNext();) {
                entry = (Map.Entry) it.next();
                segnaposto = (String) entry.getKey();
                defaultValue = (String) entry.getValue();

                String ricerca = segnaposto;
                org.jdom.Element elemento = getElement(body, ricerca);
                while (elemento != null) {
                    elemento.setText(defaultValue);
                    elemento = getElement(body, ricerca);
                }

            }
            p1.saveAs(odtFile);
        }
    }

    private org.jdom.Element getElement(org.jdom.Element el, String searchString) {
        List contents = el.getContent();
        Object loopObj;
        org.jdom.Element recursiveEl;
        CharSequence ricerca = searchString;
        for (int i = 0; i < contents.size(); i++) {
            loopObj = contents.get(i);
            if ((loopObj instanceof Text) && (((Text) loopObj).getValue().contains(ricerca))) {
                return el;
            } else if ((loopObj instanceof org.jdom.Element) && (((org.jdom.Element) loopObj).getContent() != null)) {
                recursiveEl = getElement((org.jdom.Element) loopObj, searchString);
                if (recursiveEl != null) {
                    return recursiveEl;
                }
            }
        }
        return null;
    }
}
