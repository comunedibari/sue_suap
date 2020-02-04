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

import com.jayniac.jsimage.JSImage;
import java.io.BufferedOutputStream;
import java.io.StringWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class HtmlSyntaxToolEmbed {

    private static Log log = LogFactory.getLog(HtmlSyntaxToolEmbed.class);
    private static String JS = null;

    static {
        try {
            InputStream is = HtmlSyntaxToolEmbed.class.getClassLoader().getResourceAsStream("jsImage.js");
            int x = is.available();
            byte b[] = new byte[x];
            is.read(b);
            JS = new String(b);
        } catch (Exception e) {
            JS = "";
            log.error("JS Error", e);
        }
    }

    /*
     * Crea un nuovo file content.xml ODT con solo sintassi JodReports
     * a partire da un file content.xml ODT con solo sintassi Template.
     *
     * @param contentXml    file content.xml di un ODT file contenente i template Tags
     * @param properties    parametri di conversione
     * @param jodFilePath   path del nuovo file creato con sintassi JodReports
     */
    public static void embedImage(File htmlFile, File imageDir, String htmlOutFilePath) throws Exception {

        log.debug("Scrivo su: " + htmlOutFilePath);

        String contentHtml = convertFileIntoString(htmlFile);
        String contentHtmlOut = embedImage(contentHtml, imageDir);

        OutputStream fout = new FileOutputStream(htmlOutFilePath);
        OutputStream bout = new BufferedOutputStream(fout);
        OutputStreamWriter out = new OutputStreamWriter(bout, "UTF-8");
        out.write(contentHtmlOut);
        out.flush();
        out.close();



    }

    public static byte[] embedImage(File htmlFile, File imageDir) throws Exception {



        String contentHtml = convertFileIntoString(htmlFile);
        String contentHtmlOut = embedImage(contentHtml, imageDir);
        return contentHtmlOut.getBytes();


    }

    /*
     *
     */
    public static String embedImage(String html, File imagedir) throws Exception {
        String htmlw = html.toLowerCase();
        //html = html.toLowerCase();

        StringBuffer output = new StringBuffer(html.length());

        StringBuffer jsPart1 = new StringBuffer();
        StringBuffer jsPart2 = new StringBuffer();

        jsPart2.append("function displayImages() {\n");
        //jsPart2.append("var container;");
        //jsPart2.append("container  = document.getElementById( \"imageContainer\" );");
        //jsPart2.append("Img2.display( container );
        //jsPart2.append("}");

        log.debug("html input: " + html);
        int startIdx = 0;

        int headPos = htmlw.indexOf("<head");
        headPos = htmlw.indexOf(">", headPos) + 1;

        output.append(html.substring(startIdx, headPos));
        output.append(JS);
        startIdx = headPos;


        int bodyPos = htmlw.indexOf("<body");
        bodyPos = htmlw.indexOf(">", bodyPos);

        output.append(html.substring(startIdx, bodyPos));
        output.append(" onload=\"displayImages()\"");

        startIdx = bodyPos;

        int lastImgPos = htmlw.indexOf("<img");


        String basePath = imagedir.getAbsolutePath();

        while (lastImgPos != -1) {
//            log.debug("lastImgPos: "+lastImgPos);
            int posStartImgTag = htmlw.indexOf("src=\"", lastImgPos);
            int posStart = posStartImgTag + 5;
            int posEnd = htmlw.indexOf("\"", posStart);
            int posEndImgTag = htmlw.indexOf(">", posEnd) + 1;

            String imageName = html.substring(posStart, posEnd);
            File image = new File(basePath + File.separator + imageName);
//            log.debug("PrimaJS :"+basePath+File.separator+imageName);
            String imgForJs = getImageJS(image);
//            log.debug("DopoJS :"+imgForJs);

            String varImageName = getVarName(image);
            String timestamp = String.valueOf(System.currentTimeMillis());
            String varDivName = "divid_" + varImageName + "_" + timestamp;
            String varContainerName = "divid_" + varImageName + "_" + timestamp;

            String div = "<div id=\"" + varDivName + "\"></div>";


            jsPart1.append(imgForJs);
            jsPart1.append("\n");
            jsPart2.append("var " + varContainerName + ";\n");
            jsPart2.append(varContainerName + " = document.getElementById( \"" + varDivName + "\" );\n");
            jsPart2.append(varImageName + ".display( " + varContainerName + " );\n");

            image.delete();
            output.append(html.substring(startIdx, lastImgPos));
            output.append(div);
            startIdx = posEndImgTag;
            lastImgPos = htmlw.indexOf("<img", posEnd);
        }

        jsPart2.append("}\n");

//        log.debug("jsPart1 -> "+jsPart1);
//        log.debug("jsPart2 -> "+jsPart2);

        output.append(html.substring(startIdx));

        //log.debug("output -> "+output);

        StringBuffer outputMerged = new StringBuffer(output.length() + jsPart1.length() + jsPart2.length());

        String placeHolder = "//replaceDynamicJs";

        int insertPoint = output.indexOf(placeHolder);

        outputMerged.append(output.substring(0, insertPoint));
        outputMerged.append(jsPart1);
        outputMerged.append(jsPart2);
        outputMerged.append(output.substring(insertPoint + placeHolder.length(), output.length()));
//        outputMerged.append("<script type=\"text/javascript\">displayImages();</script>");

        return outputMerged.toString();

    }

    private static String getVarName(File image) {
        String name = image.getName();
        return name.substring(0, name.indexOf("."));
    }

    private static String getImageJS(File inFileName) {
        String result;
        try {
            // instantiate our image
            JSImage jsImg = new JSImage(inFileName);



            // set up all the options
            jsImg.setJSVariableName(getVarName(inFileName));
            jsImg.setCreateColorPalette(true);
            jsImg.setBitsPerPixel(8);
            jsImg.setNumClipBits(0);

            // print a little info
            log.debug("Encoding " + jsImg.getWidth() + "x" + jsImg.getHeight() + " image " + inFileName.getAbsolutePath());

            StringWriter out = new StringWriter();


            // encode and print the image as a JSImage
            jsImg.serialize(out);

            result = out.toString();

            // close
            out.close();



        } catch (Exception e) {
            log.error("Error", e);
            result = "";
        }

        return result;
    }

    private static String convertFileIntoString(File file) throws Exception {

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
