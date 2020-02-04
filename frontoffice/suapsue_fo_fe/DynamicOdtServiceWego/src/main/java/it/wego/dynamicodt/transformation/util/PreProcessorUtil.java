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
package it.wego.dynamicodt.transformation.util;

import it.wego.dynamicodt.transformation.html.OdtTableGenerator;
import it.wego.dynamicodt.transformation.html.OdtTableParameter;
import it.wego.dynamicodt.transformation.html.OdtTableResult;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.xml.sax.InputSource;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;

/*
 * PreProcessorUtil.java
 *
 * Created on 5 aprile 2008, 7.58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
/**
 *
 * @author monicadb
 */
public class PreProcessorUtil {

    private static Log log = LogFactory.getLog(PreProcessorUtil.class);

    /** Creates a new instance of PreProcessorUtil */
    public PreProcessorUtil() {
    }

    public File templateSyntaxToJodSyntax(File xmlContent) {
        return xmlContent;
    }

    public DocumentFragment buildImage(File odtUnzippedDir, String content, Document xmlContent) throws Exception {
    	
        log.debug("content image: " + content);
        try {

            Random r = new Random();
            StringBuffer buff = new StringBuffer();
            String id = System.currentTimeMillis() + "" + r.nextLong();
            buff.append("<fragment>");
            buff.append("<draw:frame ");
            buff.append("draw:name=\"immagini" + id + "\" ");
            buff.append("text:anchor-type=\"paragraph\" ");



            if (content.indexOf("base64") > -1) {
                content = content.substring(content.indexOf("base64") + 7, content.length());
            }
            byte[] imageData = Base64.decodeBase64(content.getBytes());
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageData));
            img.createGraphics();

            double height = img.getHeight() * 0.0313;
            double width = img.getWidth() * 0.0313;

            //getUtil().addOuterContent("Pictures/"+id, imageData);
            IOUtil io = new IOUtil();

            File pictureFolder = new File(odtUnzippedDir.getPath()
                    + System.getProperty("file.separator") + "Pictures");
            pictureFolder.mkdir();

            File picture = new File(pictureFolder.getAbsolutePath()
                    + System.getProperty("file.separator") + id);


            FileOutputStream fos = new FileOutputStream(picture);
            fos.write(imageData);
            fos.close();
            log.debug("fine scrittura su " + picture.getAbsolutePath());


            buff.append("draw:z-index=\"2\">");
            buff.append("<draw:image xlink:href=\"Pictures/" + id + "\" ");
            buff.append("xlink:type=\"simple\" ");
            buff.append("xlink:show=\"embed\" ");
            buff.append("svg:width=\"" + width + "cm\" ");
            buff.append("svg:height=\"" + height + "cm\" ");
            buff.append("xlink:actuate=\"onLoad\"/>");
            buff.append("</draw:frame>");
            buff.append("</fragment>");

            // Create a DOM builder and parse the fragment
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document d = factory.newDocumentBuilder().parse(
                    new InputSource(new StringReader(buff.toString())));

            // Import the nodes of the new document into doc so that they
            // will be compatible with doc
            Node node = xmlContent.importNode(d.getDocumentElement(), true);

            // Create the document fragment node to hold the new nodes
            DocumentFragment docfrag = xmlContent.createDocumentFragment();

            // Move the nodes into the fragment
            while (node.hasChildNodes()) {
                log.debug("AA " + node.getFirstChild().getNodeName());
                docfrag.appendChild(node.removeChild(node.getFirstChild()));
            }

            // Return the fragment
            return docfrag;
        } catch (Exception e) {
            log.error("Error", e);
            return null;
        }
        
    }

    /*
     * Ritorna una lista di 2 elementi. Ogni elemento e' un arry di DocumentFragment.
     *  L'elemento 0 sono i DocumentFragment di table
     *  L'elemento 1 sono i DocumentFragment di style
     *
     */
    public List buildHtmlTable(String content, Document xmlContent, OdtTableParameter odtTableParameter) throws Exception {

        log.debug("content html: " + content);
        try {
            ConfigUtil cu = new ConfigUtil();

            OdtTableGenerator odtTable = new OdtTableGenerator();
            OdtTableResult result = odtTable.generateOdtTables(content, odtTableParameter, cu.getInstance());
            List tables = result.getOdtTables();
            List styles = result.getOdtStyles();
            List forms = result.getOdtForms();

            log.debug("content resultStr: " + tables.size());
            log.debug("content styleStr: " + styles.size());

            List results = new ArrayList();

            DocumentFragment[] tablesRes = new DocumentFragment[tables.size()];

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            for (int i = 0; i < tables.size(); i++) {
                String tableStr = "<root>" + ((StringBuffer) tables.get(i)).toString() + "</root>";
                log.debug("tablesStr " + tableStr);
                Document table = factory.newDocumentBuilder().parse(
                        new InputSource(new StringReader(tableStr)));

                // Import the nodes of the new document into doc so that they
                // will be compatible with doc
                Node nodeTable = xmlContent.importNode(table.getDocumentElement(), true);

                // Create the document fragment node to hold the new nodes
                DocumentFragment docfragTable = xmlContent.createDocumentFragment();

                // Move the nodes into the fragment
                while (nodeTable.hasChildNodes()) {
                    docfragTable.appendChild(nodeTable.removeChild(nodeTable.getFirstChild()));
                }

                tablesRes[i] = docfragTable;
            }


            results.add(tablesRes);
            //////////////
            // Create a DOM builder and parse the fragment

            DocumentFragment[] stylesRes = new DocumentFragment[styles.size()];


            for (int i = 0; i < styles.size(); i++) {
                String styleStr = "<root>" + ((StringBuffer) styles.get(i)).toString() + "</root>";
                log.debug("styleStr " + styleStr);
                Document style = factory.newDocumentBuilder().parse(
                        new InputSource(new StringReader(styleStr)));

                // Import the nodes of the new document into doc so that they
                // will be compatible with doc
                Node nodeStyle = xmlContent.importNode(style.getDocumentElement(), true);

                // Create the document fragment node to hold the new nodes
                DocumentFragment docfragStyle = xmlContent.createDocumentFragment();

                // Move the nodes into the fragment
                while (nodeStyle.hasChildNodes()) {
                    docfragStyle.appendChild(nodeStyle.removeChild(nodeStyle.getFirstChild()));
                }

                stylesRes[i] = docfragStyle;
            }

            results.add(stylesRes);

            DocumentFragment[] formsRes = new DocumentFragment[forms.size()];


            for (int i = 0; i < forms.size(); i++) {
                String formStr = "<root>" + ((StringBuffer) forms.get(i)).toString() + "</root>";
                log.debug("formstr " + formStr);
                Document style = factory.newDocumentBuilder().parse(
                        new InputSource(new StringReader(formStr)));

                // Import the nodes of the new document into doc so that they
                // will be compatible with doc
                Node nodeStyle = xmlContent.importNode(style.getDocumentElement(), true);

                // Create the document fragment node to hold the new nodes
                DocumentFragment docfragStyle = xmlContent.createDocumentFragment();

                // Move the nodes into the fragment
                while (nodeStyle.hasChildNodes()) {
                    docfragStyle.appendChild(nodeStyle.removeChild(nodeStyle.getFirstChild()));
                }

                formsRes[i] = docfragStyle;
            }

            results.add(formsRes);
            // Return the fragments
            return results;
        } catch (Exception e) {
            log.error("Error", e);
            return null;
        }
    }
}
