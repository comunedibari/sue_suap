package it.reporter.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

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

    private static Logger log = LoggerFactory.getLogger(PreProcessorUtil.class.getName());

    /** Creates a new instance of PreProcessorUtil */
    public PreProcessorUtil() {
    }

    public File templateSyntaxToJodSyntax(File xmlContent) {
        return xmlContent;
    }

    public DocumentFragment buildImage(File odtUnzippedDir, String content, Document xmlContent) throws Exception {

        log.debug("content image: " + content);
        try {
            File picture = new File(odtUnzippedDir + File.separator + "Pictures" + File.separator + content);
            BufferedInputStream is = new BufferedInputStream(new FileInputStream(picture));
            byte[] imageData = new byte[(int) picture.length()];
            is.read(imageData, 0, (int) picture.length());
            is.close();
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageData));
            img.createGraphics();

            double height = img.getHeight() * 0.0313;
            double width = img.getWidth() * 0.0313;

            StringBuilder buff = new StringBuilder();

            buff.append("<fragment>");
            buff.append("<draw:frame ");
            buff.append("draw:name=\"immagini").append(content).append("\" ");
            buff.append("text:anchor-type=\"paragraph\" ");
            buff.append("svg:width=\"").append(width).append("cm\" ");
            buff.append("svg:height=\"").append(height).append("cm\" ");
            buff.append("draw:z-index=\"2\">");
            buff.append("<draw:image xlink:href=\"Pictures/").append(content).append("\" ");
            buff.append("xlink:type=\"simple\" ");
            buff.append("xlink:show=\"embed\" ");
//            buff.append("svg:width=\"").append(width).append("cm\" ");
//            buff.append("svg:height=\"").append(height).append("cm\" ");
            buff.append("xlink:actuate=\"onLoad\"/>");
            buff.append("</draw:frame>");
            buff.append("</fragment>");

            // Create a DOM builder and parse the fragment
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document d = factory.newDocumentBuilder().parse(new InputSource(new StringReader(buff.toString())));

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
}
