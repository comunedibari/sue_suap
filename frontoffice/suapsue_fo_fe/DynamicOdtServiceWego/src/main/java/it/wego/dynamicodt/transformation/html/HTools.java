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
package it.wego.dynamicodt.transformation.html;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HTools {

    private static Log log = LogFactory.getLog(HTools.class);

    //metodo copiato da StringUtils.replaceAll(...)
    /**
     * Replaces all the occurences of a substring found 
     * within a StringBuffer by a 
     * replacement string
     *
     * @param buffer the StringBuffer in where the replace will take place
     * @param find the substring to find and replace
     * @param replacement the replacement string for all occurences of find
     * @return the original StringBuffer where all the
     * occurences of find are replaced by replacement
     */
    public static StringBuffer replaceAll(StringBuffer buffer, String find, String replacement) {

        int bufidx = buffer.length() - 1;
        int offset = find.length();
        while (bufidx > -1) {
            int findidx = offset - 1;
            while (findidx > -1) {
                if (bufidx == -1) {
                    //Done
                    return buffer;
                }
                if (buffer.charAt(bufidx) == find.charAt(findidx)) {
                    findidx--; //Look for next char
                    bufidx--;
                } else {
                    findidx = offset - 1; //Start looking again
                    bufidx--;
                    if (bufidx == -1) {
                        //Done
                        return buffer;
                    }
                    continue;
                }
            }
            //Found
            //System.out.println( "replacing from " + (bufidx + 1) +
            //                    " to " + (bufidx + 1 + offset ) +
            //                    " with '" + replacement + "'" );
            buffer.replace(bufidx + 1,
                    bufidx + 1 + offset,
                    replacement);
            //start looking again
        }
        //No more matches
        return buffer;

    }

    /* public static StringBuffer replaceAllWithJolly(StringBuffer buffer, String find, String replacement)
    {

    int bufidx = buffer.length() - 1;
    int offset = find.length();
    while( bufidx > -1 ) {
    int findidx = offset -1;
    while( findidx > -1 ) {
    if( bufidx == -1 ) {
    //Done
    return buffer;
    }
    if(( buffer.charAt( bufidx ) == find.charAt( findidx ) )||(find.charAt( findidx )=='?')) {
    findidx--; //Look for next char
    bufidx--;
    } else {
    findidx = offset - 1; //Start looking again
    bufidx--;
    if( bufidx == -1 ) {
    //Done
    return buffer;
    }
    continue;
    }
    }
    //Found
    //System.out.println( "replacing from " + (bufidx + 1) +
    //                    " to " + (bufidx + 1 + offset ) +
    //                    " with '" + replacement + "'" );
    buffer.replace( bufidx+1,
    bufidx+1+offset,
    replacement);
    //start looking again
    }
    //No more matches
    return buffer;

    }*/
    /*public static StringBuffer replaceAllWithAllJolly(StringBuffer buffer, String find, String replacement)
    {

    int bufidx = buffer.length() - 1;
    int offset = find.length();
    while( bufidx > -1 ) {
    int findidx = offset -1;
    while( findidx > -1 ) {
    if( bufidx == -1 ) {
    //Done
    return buffer;
    }

    if(( buffer.charAt( bufidx ) == find.charAt( findidx ) )||(find.charAt( findidx )=='?')) {
    findidx--; //Look for next char
    bufidx--;
    } else {
    findidx = offset - 1; //Start looking again
    bufidx--;
    if( bufidx == -1 ) {
    //Done
    return buffer;
    }
    continue;
    }
    }
    //Found
    //System.out.println( "replacing from " + (bufidx + 1) +
    //                    " to " + (bufidx + 1 + offset ) +
    //                    " with '" + replacement + "'" );
    buffer.replace( bufidx+1,
    bufidx+1+offset,
    replacement);
    //start looking again
    }
    //No more matches
    return buffer;

    }*/
    public static boolean replaceAllWithJollyDotAllMultiline(StringBuffer buffer, String find, String replacement) {
        boolean change = false;

        Pattern p = Pattern.compile(find, Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);

        Matcher m = p.matcher(buffer);

        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            change = true;
            m.appendReplacement(sb, replacement);
            //log.debug("\n\n!!!"+sb.toString()+"!!!\n\n");
        }
        m.appendTail(sb);

        buffer.replace(0, buffer.length(), sb.toString());

        return change;
    }

    public static boolean replaceAllWithJolly(StringBuffer buffer, String find, String replacement) {
        boolean change = false;

        Pattern p = Pattern.compile(find);

        Matcher m = p.matcher(buffer);

        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            change = true;
            m.appendReplacement(sb, replacement);
            //log.debug("\n\n!!!"+sb.toString()+"!!!\n\n");
        }
        m.appendTail(sb);

        buffer.replace(0, buffer.length(), sb.toString());

        return change;

    }

    //remove all leading and traling space around a tag
    public static StringBuffer tagTrim(StringBuffer html, String tag) {

        char c;
        int spacePos;
        int spaceLeftNum;
        int spaceRightNum;
        int htmlLength;
        int startPos;
        int endPos;
        int tagPos = html.indexOf(tag);
        while (tagPos >= 0) {
            spaceLeftNum = 0;
            for (int i = tagPos - 1; i >= 0; i--) {
                c = html.charAt(i);
                if (Character.isWhitespace(c)) {
                    spaceLeftNum++;
                } else {
                    i = 0;
                }
            }
            htmlLength = html.length();
            spaceRightNum = 0;
            for (int i = tagPos + tag.length(); i < htmlLength; i++) {
                c = html.charAt(i);
                if (Character.isWhitespace(c)) {
                    spaceRightNum++;
                } else {
                    i = htmlLength;
                }
            }
            if (spaceLeftNum > 0 || spaceRightNum > 0) {
                startPos = tagPos - spaceLeftNum;
                endPos = tagPos + tag.length() + spaceRightNum;
                html.replace(startPos, endPos, tag);
                tagPos = startPos;
            }
            tagPos = html.indexOf(tag, tagPos + tag.length());
        }

        return html;
    }

    //carica gli attributi di un tag a partire dal tagValue
    //il tagValue e' tutto quello che è compreso tra "<tag" e ">",
    //gli attributi sono salvato in una mappa la cui chiave e' il nome dell'attributo
    //ed il valore della mappa e' l'eventuale valore dell'attributo 
    //(sempre di tipo String, senza doppi apici, se l'attributo non ha valore il valore della mappa e' null).
    //es. in <table> il tagValue è una stringa vuota,
    //    in <table style="color:#ccccc" border="1"> e' "style="color:#ccccc" border="1""
    //    e' gli attributi sono 2 (style con valore color:#ccccc, e border con valore 1)
    //NOTA: il metodo funziona solo se non c'e' alcuno spaziotra ai lati del carattere =
    public static Map loadAttributesFromTag(String tagValue) {

        Map attributes = new HashMap();

        StringTokenizer st = new StringTokenizer(tagValue.trim(), " ");
        String token;
        int equalPos;
        String key;
        String value;
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            equalPos = token.indexOf("=");
            if (equalPos >= 0) {
                key = token.substring(0, equalPos).trim();
                if (equalPos < (token.length() - 1)) {
                    value = token.substring(equalPos + 1).trim();
                } else {
                    value = null;
                }
            } else {
                //vengono gestiti anche gli attributi non xthml (esempio: disabled)
                key = token;
                value = null;
            }
            attributes.put(key, value);
        }

        return attributes;
    }

    //elimina tutte le occorrenze di un tag (senza eliminare anche tutto il suo contenunto)
    public static void removeTag(StringBuffer html, String tagCleaned) {

        int tagStartPos;
        int tagStartClosePos;
        tagStartPos = html.indexOf("<" + tagCleaned);
        while (tagStartPos >= 0) {
            tagStartClosePos = html.indexOf(">", tagStartPos);
            if (tagStartClosePos >= 0) {
                html.replace(tagStartPos, tagStartClosePos + 1, "");
                tagStartPos = html.indexOf("<" + tagCleaned, tagStartPos);
            } else {
                log.info("HTML TOOLS: contenuto html ignorato in remove tag perche' manca ultimo carattere tag rimosso (" + html + "," + tagCleaned + ")");
            }
        }

        int tagEndPos;                  //pos of </tagcleaned
        int tagEndCloseLastCharPos;     //pos of >
        String tagEnd = "</" + tagCleaned + ">";
        tagEndPos = html.indexOf(tagEnd);
        while (tagEndPos >= 0) {
            tagEndCloseLastCharPos = tagEndPos + tagEnd.length();
            html.replace(tagEndPos, tagEndCloseLastCharPos, "");
            tagEndPos = html.indexOf(tagEnd, tagEndPos);
        }
    }

    //rimuovi gli spazi a sinistra e a destra
    public static void trim(StringBuffer html) {

        if (html.length() > 0) {
            if (html.charAt(0) == ' ' || html.charAt(html.length() - 1) == ' ') {
                String s = html.toString().trim();
                html.setLength(0);
                html.append(s);
            }
        }
    }

    public static String convertFileIntoString(File file) throws Exception {

        String result;

        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            //br = new BufferedReader(new FileReader(file));
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

            String line = null;
            while ((line = br.readLine()) != null) { //while not at the end of the file stream do
                sb.append(line);
                sb.append("\n");
            }//next line
            result = sb.toString();
        } finally {
            if (br != null) {
                br.close();
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {

        StringBuffer s = new StringBuffer("a\nb");
        System.out.println("(" + s + ")");
        System.out.println(s.toString().replaceAll("\n", ""));
    }
}
