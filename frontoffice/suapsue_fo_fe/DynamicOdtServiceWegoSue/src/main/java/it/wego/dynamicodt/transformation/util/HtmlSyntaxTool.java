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

import java.io.BufferedOutputStream;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class HtmlSyntaxTool {

    private static Log log = LogFactory.getLog(TemplateSyntaxTool.class);
    public String script_header = "<script type=\"text/javascript\">\n"
            + "var BASE64_DATA = /^data:.*;base64/i;\n"
            + "var base64Path = \"";
    public String script_footer = "\";\nfunction fixBase64(img) {\n"
            + "if (BASE64_DATA.test(img.src)) {\n"
            + "img.src = base64Path + \"?data=\" + img.src.slice(5);\n"
            + "}\n"
            + "};\n"
            + "onload = function() {\n"
            + "for (var i = 0; i < document.images.length; i++) {\n"
            + "fixBase64(document.images[i]);\n"
            + "}\n"
            + "};\n"
            + "</script>";
    public String script = "";

    public HtmlSyntaxTool() {
        ConfigUtil cu = new ConfigUtil();
        String url = cu.getInstance().getProperty("urlDecodeImage");
        script = script_header + url + script_footer;
    }

    /*
     * Crea un nuovo file content.xml ODT con solo sintassi JodReports
     * a partire da un file content.xml ODT con solo sintassi Template.
     *
     * @param contentXml    file content.xml di un ODT file contenente i template Tags
     * @param properties    parametri di conversione
     * @param jodFilePath   path del nuovo file creato con sintassi JodReports
     */
    public void embedImage(File htmlFile, File imageDir, String htmlOutFilePath) throws Exception {

        log.debug("Scrivo su: " + htmlOutFilePath);

        String contentHtml = this.convertFileIntoString(htmlFile);
        String contentHtmlOut = this.embedImage(contentHtml, imageDir);

        OutputStream fout = new FileOutputStream(htmlOutFilePath);
        OutputStream bout = new BufferedOutputStream(fout);
        OutputStreamWriter out = new OutputStreamWriter(bout, "UTF-8");
        out.write(contentHtmlOut);
        out.flush();
        out.close();



    }

    public byte[] embedImage(File htmlFile, File imageDir) throws Exception {



        String contentHtml = this.convertFileIntoString(htmlFile);
        String contentHtmlOut = this.embedImage(contentHtml, imageDir);
        return contentHtmlOut.getBytes();


    }

    public String imageToBase64(File image) {
        try {
            FileInputStream fis = new FileInputStream(image);
            boolean finished = false;
            int available = 0;
            byte[] content = null;
            while (!finished) {
                available = fis.available();
                if (available > 0) {
                    content = new byte[available];
                    int r = fis.read(content, 0, available);
                    finished = true;
                }
            }

            fis.close();
            return new String(Base64.encodeBase64(content));
        } catch (Exception ioe) {
            return null;
        }
    }

    /*
     *
     */
    public String embedImage(String html, File imagedir) throws Exception {

        String htmlw = html.toLowerCase();
        // html = html.toLowerCase();

        StringBuffer output = new StringBuffer(html.length());

        log.debug("html input: " + html);
        int startIdx = 0;

        int headPos = htmlw.indexOf("<head");
        headPos = htmlw.indexOf(">", headPos) + 1;

        output.append(html.substring(startIdx, headPos));
        output.append(script);
        startIdx = headPos;


        /*int bodyPos = html.indexOf("<body");
        bodyPos = html.indexOf(">",bodyPos);

        output.append(html.substring(startIdx,bodyPos));
        output.append(" ");*/


        int lastImgPos = htmlw.indexOf("<img");


        String basePath = imagedir.getAbsolutePath();

        while (lastImgPos != -1) {
            log.debug("lastImgPos: " + lastImgPos);
            int posStart = htmlw.indexOf("src=\"", lastImgPos) + 5;
            int posEnd = htmlw.indexOf("\"", posStart);
            //log.debug("posStart: "+posStart);
            //log.debug("posEnd: "+posEnd);
            String imageName = htmlw.substring(posStart, posEnd);
            File image = new File(basePath + File.separator + imageName);
            log.debug("imageName " + image.getAbsolutePath());
            String imgBase64 = imageToBase64(image);
            image.delete();
            output.append(html.substring(startIdx, posStart));
            output.append("data:image/gif;base64,");
            output.append(imgBase64);
            startIdx = posEnd;
            lastImgPos = htmlw.indexOf("<img", posEnd);
        }

        output.append(html.substring(startIdx));

        log.debug("output -> " + output);

        return output.toString();

    }

    private String convertFileIntoString(File file) throws Exception {

        String result;

        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            //br = new BufferedReader(new FileReader(file));
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));


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
}
