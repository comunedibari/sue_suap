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
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

public class TemplateSyntaxTool {

    private static Log log = LogFactory.getLog(TemplateSyntaxTool.class);
    private String rootXmlName;
    private String startTag;
    private String startEndTag;
    private String startFieldTag;
    private String startAsTag;
    private String endTag;
    private String endEndTag;
    private String putCommand;
    private String repeatCommand;
    private String imageCommand;
    private String htmlCommand;
    private String assignCommand;
    private String ifCommand;
    private String elseCommand;
    private String repeatPrefix;
    private String putTagJod;
    private String repeatTagJod;
    private String imageTagJod;
    private String htmlTagJod;
    private String assignTagJod;
    private String ifTagJod;
    private String elseTagJod;
    private String repeatEndTagJod;
    private String ifEndTagJod;
    private String fieldsSeparatorJod;
    private int repeatLevel;
    private int ifLevel;
    private List asFields = new ArrayList();

    /*
     * Crea un nuovo file content.xml ODT con solo sintassi JodReports
     * a partire da un file content.xml ODT con solo sintassi Template.
     *
     * @param contentXml    file content.xml di un ODT file contenente i template Tags
     * @param properties    parametri di conversione
     * @param jodFilePath   path del nuovo file creato con sintassi JodReports
     */
    public void templateSyntaxToJodSyntax(File contentXml, Properties properties,
            String jodFilePath) throws Exception {

        log.debug("Scrivo su: " + jodFilePath);

        String contentXmlTemplate = this.convertFileIntoString(contentXml);
        String contentXmlJod = this.templateSyntaxToJodSyntax(contentXmlTemplate, properties);

        OutputStream fout = new FileOutputStream(jodFilePath);
        OutputStream bout = new BufferedOutputStream(fout);
        OutputStreamWriter out = new OutputStreamWriter(bout, "UTF-8");
        out.write(contentXmlJod);
        out.flush();
        out.close();



    }

    ;

    /*
     * Crea una stringa di tipo content.xml ODT con solo sintassi JodReports
     * a partire da una stringa di tipo content.xml ODT con solo sintassi Template.
     *
     * @param contentXml    stringa rappresentante il file content.xml di un ODT file contenente i template Tags
     * @param properties    parametri di conversione
     *
     * @return String       stringa rappresentante il file content.xml di un ODT file contenente i jodReports Tags
     */
    public String templateSyntaxToJodSyntax(String contentXml, Properties properties) throws Exception {

        log.debug("CONTENT_XML:\n" + contentXml.substring(0, Math.min(contentXml.length(), 1000)));

        StringBuffer contentJod = new StringBuffer(contentXml.length());

        this.init(properties);

        String tag;

        repeatLevel = 0;

        int startTagLength = startTag.length(); //length in characters of "[template:" in [template:repeat...]
        int startTagPos = 0;    //position of "[template:" in [template:repeat...]
        int startTagKeyPos;     //position of "put", "repeat", ... in [template:repeat...]
        int startEndTagPos = 0; //position of "]" in [template:repeat...]

        int endTagLength = endTag.length(); //length in characters of "[/template:"  in [/template:repeat]
        int endTagPos;          //position of "[/template:"  in [/template:repeat]
        int endTagKeyPos;       //position of "repeat" in [/template:repeat]
        int endEndTagPos = 0;   //posistion of "]" in "[/template:repeat]

        String templateCommand;
        String jodCommand;

        startTagPos = contentXml.indexOf(startTag, 0);      //position of first start tag "[template:"
        endTagPos = contentXml.indexOf(endTag, 0);        //position of first end tag   "[/template:"

        int pos = 0;

        while (startTagPos >= 0 || endTagPos >= 0) {

            if (startTagPos > 0
                    && (endTagPos == -1 || startTagPos < endTagPos)) {
                startTagKeyPos = startTagPos + startTagLength;
                startEndTagPos = contentXml.indexOf(startEndTag, startTagPos);
                if (startEndTagPos >= 0) {
                    templateCommand = contentXml.substring(startTagKeyPos, startEndTagPos);
                    jodCommand = this.parseAndConvertCommandStart(templateCommand);

                    //replace the templateCommand with jodCommand
                    contentJod.append(contentXml.substring(pos, startTagPos));
                    contentJod.append(jodCommand);
                    pos = startEndTagPos + 1;
                } else {
                    throw new Exception("custom tag di apertura non chiuso correttamente in posizione "
                            + startTagPos + " del file content.xml\n"
                            + contentXml);
                }
                startTagPos = contentXml.indexOf(startTag, startEndTagPos);
            } else {
                if (endTagPos > 0
                        && (startTagPos == -1 || endTagPos < startTagPos)) {
                    endTagKeyPos = endTagPos + endTagLength;
                    endEndTagPos = contentXml.indexOf(endEndTag, endTagPos);
                    if (endEndTagPos >= 0) {
                        templateCommand = contentXml.substring(endTagKeyPos, endEndTagPos);
                        jodCommand = this.parseAndConvertCommandEnd(templateCommand);

                        //replace the templateCommand with jodCommand
                        contentJod.append(contentXml.substring(pos, endTagPos));
                        contentJod.append(jodCommand);
                        pos = endEndTagPos + 1;
                    } else {
                        throw new Exception("custom tag di chiusura non chiuso correttamente in posizione "
                                + endTagPos + " del file content.xml\n"
                                + contentXml);
                    }
                }
                endTagPos = contentXml.indexOf(endTag, endEndTagPos);
            }
        }

        if (pos < contentXml.length()) {
            contentJod.append(contentXml.substring(pos));
        }

        log.debug("CONTENT_JOD:\n" + contentJod.substring(0, Math.min(contentJod.length(), 1000)));

        return contentJod.toString();
    }

    /*
     * @param templateCommand the template templateCommand of start tag.
     *                templateCommand is "put field=a.b.c" without "[template:" and without "]"
     *                or templateCommand is "repeat field=a.b.c as n1" without "[template:" and without "]"
     */
    private String parseAndConvertCommandStart(String templateCommand) throws Exception {

        log.debug("START:\t\t\t\t\t\t\t\t\t(" + templateCommand + ")");
        //templateCommand.replaceAll("?", "\"");

        String key = null;           //key is "put", "repeat", "image", "html"...
        String fieldCommand;         //fieldCommand is "field=a.b.c"
        String fieldName = null;     //fieldName is "a.b.c"
        int equalPos;
        List options = new ArrayList();

        int index = 0;
        StringTokenizer st = new StringTokenizer(templateCommand, " ,");
        while (st.hasMoreTokens()) {
            index++;
            switch (index) {
                case (1):
                    key = this.cleanToken(st.nextToken());
                    break;
                case (2):
                    fieldCommand = this.cleanToken(st.nextToken());
                    if (fieldCommand.startsWith(startFieldTag)) {
                        fieldName = fieldCommand.substring(startFieldTag.length());
                    } else {
                        throw new Exception("custom tag senza " + startFieldTag + " in " + templateCommand);
                    }
                    break;
                default:
                    options.add(this.cleanToken(st.nextToken()));
            }
        }

        String jodCommand = convertCommandStart(key, fieldName, options);

        return jodCommand;
    }

    /*
     * If the templateCommand is:
     * [template:assign field=a.b.c n] the keyword is "assign", fieldName is "a.b.c", options[0] is "n"
     * @param key           keyword of templateCommand ("put", "repeat", "image", "html"...)
     * @param fieldName     name of the field ("a.b.c"...)
     * @param options       options of the commmand
     */
    private String convertCommandStart(String key, String fieldName, List options) throws Exception {

        log.debug("tokens key(" + key + "),fieldName(" + fieldName + "),options(" + options + ")");

        //caso else
        if (fieldName == null) {
            fieldName = "";
        }

        String jodCommand = null;    //jodCommand is "[#list...]" or "${...}"
        String firstPartOfFieldName; //if fieldName is "a.b.c" the firstPartOfFieldName is "a"
        String asField = null;
        String ifField = null;

        if (options.size() >= 2) {
            asField = (String) options.get(1);
        } else if (options.size() == 1 && ((String) options.get(0)).startsWith("value=")) {
            ifField = ((String) options.get(0)).substring("value=".length());
        }



        //if fieldName starts with a prefix contained in asFields the fieldName remains unchanged,
        //otherwise the rootXmlName and fieldsSeparatoJod are added.
        //
        int separatorPos = fieldName.indexOf(fieldsSeparatorJod);
        if (separatorPos == -1) {
            firstPartOfFieldName = fieldName;
        } else {
            firstPartOfFieldName = fieldName.substring(0, separatorPos);
        }
        String fieldNameComplete = fieldName;
        if (!asFields.contains(firstPartOfFieldName)) {
            if (rootXmlName != null
                    && rootXmlName.trim().length() > 0) {
                fieldNameComplete = rootXmlName + fieldsSeparatorJod + fieldName;
            }
        }
        fieldNameComplete = this.clearFieldName(fieldNameComplete);

        if (false) {
        } else if (key.equals(putCommand)) {
            //jodCommand = String.format(this.putTagJod, fieldNameComplete);
            jodCommand = StringUtils.replace(this.putTagJod, "%s", fieldNameComplete);
        } else if (key.equals(repeatCommand)) {
            if (asField != null) {
                repeatLevel++;
                asFields.add(asField);
                //jodCommand = String.format(this.repeatTagJod, fieldNameComplete, asField);
                jodCommand = StringUtils.replaceEach(this.repeatTagJod, new String[]{"%s", "%v"}, new String[]{fieldNameComplete, asField});
            } else {
                throw new Exception("Comando repeat senza nome campo da assegnare."
                        + key + "," + fieldName + "," + options);
            }
        } else if (key.equals(ifCommand)) {
            if (ifField != null) {
                ifLevel++;
                /*log.debug("if ifField: "+ifField);
                jodCommand = StringUtils.replaceEach(this.ifTagJod,new String[]{"%s", "%v"}, new String[]{fieldNameComplete, ifField});
                 */
                log.debug("if ifField: " + ifField);
                ifField = ifField.replaceAll("\u201d", "&quot;");
                ifField = ifField.replaceAll("\u201e", "&quot;");
                String ifFieldCondition = StringUtils.replace(ifField, "FF", fieldNameComplete + "[0]");

                jodCommand = StringUtils.replace(this.ifTagJod, "%s", ifFieldCondition);
            } else {
                throw new Exception("Comando if senza parametro di destra."
                        + key + "," + fieldName + "," + options);
            }
        } else if (key.equals(elseCommand)) {
            //jodCommand = String.format(this.imageTagJod, fieldNameComplete);
            jodCommand = elseTagJod;
        } else if (key.equals(imageCommand)) {
            //jodCommand = String.format(this.imageTagJod, fieldNameComplete);
            jodCommand = StringUtils.replace(this.imageTagJod, "%s", fieldNameComplete);
        } else if (key.equals(htmlCommand)) {
            //jodCommand = String.format(this.htmlTagJod, fieldNameComplete);
            jodCommand = StringUtils.replace(this.htmlTagJod, "%s", fieldNameComplete);
        } else if (key.equals(assignCommand)) {
            if (asField != null) {
                //jodCommand = String.format(this.assignTagJod, asField, fieldNameComplete);
                jodCommand = StringUtils.replaceEach(this.repeatTagJod, new String[]{"%s", "%v"}, new String[]{asField, fieldNameComplete});
            } else {
                throw new Exception("Comando assign senza nome campo da assegnare."
                        + key + "," + fieldName + "," + options);
            }
        } else {
            throw new Exception("comando di apertura inesistente(" + key + ")"
                    + key + "," + fieldName + "," + options);
        }

        log.debug("jodCommand:\t\t\t\t\t\t\t\t\t(" + jodCommand + ")");

        return jodCommand;
    }

    private String clearFieldName(String fieldNameComplete) throws Exception {

        String fieldCleared = fieldNameComplete;

        int tagPosEnd;
        int tagPos = fieldCleared.indexOf("<", 0);
        while (tagPos >= 0) {
            tagPosEnd = fieldCleared.indexOf(">", 0);
            if (tagPosEnd > 0) {
                if (tagPosEnd < (fieldCleared.length() - 1)) {
                    fieldCleared = fieldCleared.substring(0, tagPos)
                            + fieldCleared.substring(tagPosEnd + 1);
                } else {
                    fieldCleared = fieldCleared.substring(0, tagPos);
                }
            } else {
                throw new Exception("nome campo in opzione field contenente un tag che inizia con < e non e' chiuso("
                        + fieldNameComplete + ")");
            }
            tagPos = fieldCleared.indexOf("<", tagPos);
        }

        return fieldCleared;
    }

    /*
     * @param templateCommand the template templateCommand of end tag.
     *                templateCommand is "repeat" without "[template:" and without "]"
     */
    private String parseAndConvertCommandEnd(String templateCommand) throws Exception {

        log.debug("END  :\t\t\t\t\t\t\t\t\t(" + templateCommand + ")");

        return convertCommandEnd(templateCommand);
    }

    /*
     * If the templateCommand is:
     * [/template:repeat] the keyword is "assign"
     * @param key           keyword of templateCommand ("repeat")
     */
    private String convertCommandEnd(String key) throws Exception {

        String jodCommand = null;

        if (false) {
        } else if (key.equals(repeatCommand)) {
            if (repeatLevel <= 0) {
                throw new Exception("comando di chiusura senza comando di apertura(" + key + ")");
            }
            repeatLevel--;
            asFields.remove(asFields.size() - 1);
            jodCommand = repeatEndTagJod;
        } else if (key.equals(ifCommand)) {
            if (ifLevel <= 0) {
                throw new Exception("comando di chiusura senza comando di apertura(" + key + ")");
            }
            ifLevel--;
            //asFields.remove(asFields.size()-1);
            jodCommand = ifEndTagJod;
        } else {
            throw new Exception("comando di chiusura inesistente(" + key + ")");
        }

        log.debug("jodCommand:\t\t\t\t\t\t\t\t\t(" + jodCommand + ")");

        return jodCommand;
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

    private String cleanToken(String token) {

        String tokenCleaned = token;

        if (token.startsWith("<text:s/>")) {
            tokenCleaned = token.substring("<text:s/>".length());
        }

        return tokenCleaned;
    }

    private void init(Properties properties) throws Exception {

        if (properties == null) {
            properties = new Properties();
        }
        rootXmlName = properties.getProperty("nomeRootXml", "dydot").trim();

        startTag = properties.getProperty("inizioTag", "[template:").trim();
        startEndTag = properties.getProperty("inizioFineTag", "]").trim();
        startFieldTag = properties.getProperty("inizioCampoTag", "field=").trim();
        startAsTag = properties.getProperty("inizioComeTag", "as").trim();
        endTag = properties.getProperty("fineTag ", "[/template:").trim();
        endEndTag = properties.getProperty("fineFineTag", "]").trim();

        putCommand = properties.getProperty("comandoPut", "put").trim();
        repeatCommand = properties.getProperty("comandoRepeat", "repeat").trim();
        imageCommand = properties.getProperty("comandoImage", "image").trim();
        htmlCommand = properties.getProperty("comandoHtml", "html").trim();
        assignCommand = properties.getProperty("comandoAssign", "assign").trim();
        ifCommand = properties.getProperty("comandoIf", "if").trim();
        elseCommand = properties.getProperty("comandoElse", "else").trim();

        putTagJod = properties.getProperty("jodTagPut", "${%s}").trim();
        repeatTagJod = properties.getProperty("jodTagRepeat", "[#list %s as %v]").trim();
        imageTagJod = properties.getProperty("jodTagImage", "[#--image--]${%s}").trim();
        htmlTagJod = properties.getProperty("jodTagHtml", "[#--html--]${%s}").trim();
        assignTagJod = properties.getProperty("jodTagAssign", "[#assign %s=%v]").trim();
        ifTagJod = properties.getProperty("jodTagIf", "[#if %s=%v]").trim();
        elseTagJod = properties.getProperty("jodTagElse", "[#else]").trim();
        repeatEndTagJod = properties.getProperty("jodEndTagRepeat", "[/#list]").trim();
        ifEndTagJod = properties.getProperty("jodEndTagIf", "[/#if]").trim();
        fieldsSeparatorJod = properties.getProperty("jodSeparatore", ".").trim();
    }

    public static void main(String[] args) throws Exception {

        String xmlString = "";
        xmlString += "<myRoot xmlns:text=\"a.b.c\">";
        xmlString += "<text:p text:style-name=\"Standard\">[template:put field=name]</text:p>\n";
        xmlString += "<text:p text:style-name=\"Standard\">[template:image field=name]</text:p>\n";
        xmlString += "<text:p text:style-name=\"Standard\">[template:html field=name]</text:p>\n";
        xmlString += "<text:p text:style-name=\"Standard\">[template:assign field=name as n]</text:p>\n";
        xmlString += "<text:p text:style-name=\"Standard\">[template:put field=person.name]</text:p>\n";
        xmlString += "<text:p text:style-name=\"Standard\">[template:put field=a.b.c]</text:p>\n";
        xmlString += "<text:p text:style-name=\"Standard\">[template:repeat field=names.name as n1]</text:p>\n";
        xmlString += "<text:p text:style-name=\"Standard\">[template:image field=name]</text:p>\n";
        xmlString += "<text:p text:style-name=\"Standard\">[template:image field=n1.age]</text:p>\n";
        xmlString += "<text:p text:style-name=\"Standard\">[template:image field=n.age]</text:p>\n";
        xmlString += "<text:p text:style-name=\"Standard\">[template:repeat field=name as n2]</text:p>\n";
        xmlString += "<text:p text:style-name=\"Standard\">[template:image field=a.b.c]</text:p>\n";
        xmlString += "<text:p text:style-name=\"Standard\">[template:image field=n1.age]</text:p>\n";
        xmlString += "<text:p text:style-name=\"Standard\">[template:image field=n2.age]</text:p>\n";
        xmlString += "<text:p text:style-name=\"Standard\">[/template:repeat]</text:p>\n";
        xmlString += "<text:p text:style-name=\"Standard\">[template:html field=n1.name]</text:p>\n";
        xmlString += "<text:p text:style-name=\"Standard\">[template:repeat field=n1.skills as n3]</text:p>\n";
        xmlString += "<text:p text:style-name=\"Standard\">[/template:repeat]</text:p>\n";
        xmlString += "<text:p text:style-name=\"Standard\">[/template:repeat]</text:p>\n";
        xmlString += "<text:p text:style-name=\"Standard\">[template:repeat field=name as n]</text:p>\n";
        xmlString += "<text:p text:style-name=\"Standard\">[/template:repeat]</text:p>\n";
        xmlString += "<text:p text:style-name=\"Standard\">[template:put field=name]</text:p>\n";
        xmlString += "</myRoot>";

        xmlString = "";
        xmlString += "<text:p text:style-name=\"Standard\">[template:put field=x.ente.descrizio<text:soft-page-break/>ne]</text:p>\n";


        File xmlFile = new File("../resources/content.xml");

        TemplateSyntaxTool test = new TemplateSyntaxTool();
        boolean testFromFile = false;
        String xmlConverted;

        if (testFromFile) {
            test.templateSyntaxToJodSyntax(xmlFile, null, "../resources/contentNew.xml");
        } else {
            xmlConverted = test.templateSyntaxToJodSyntax(xmlString, null);
            System.out.println("outConverted=\n(" + xmlConverted + ")\n");
        }
    }
}
