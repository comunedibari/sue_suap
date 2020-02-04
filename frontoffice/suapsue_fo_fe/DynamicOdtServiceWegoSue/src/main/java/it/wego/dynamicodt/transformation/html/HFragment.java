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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.StringTokenizer;

public class HFragment {

    public static int TYPE_STRING_ALONE = 1;     //  hello word
    public static int TYPE_STRING_AND_INPUT = 2;     //  hello word <input type="...">
    public static int TYPE_INPUT_ALONE = 3;     //  <input type="...">
    public static int TYPE_INPUT_AND_STRING = 4;     //  <input type="...">hello word
    public static int TYPE_TABLES = 5;     //  <table...>...</table><table>...</table>...<table>...</table>
    private static Log log = LogFactory.getLog(HFragment.class);
    private static final String[] TYPE_VALUES = { //only for develop/debug
        "StringaSolo",
        "StringaEInput",
        "InputSolo",
        "InputEStringa",
        "Tabelle"
    };
    private int type;
    private String originalValue;
    private String stringValue;
    private String inputType;
    private List htables;
    private boolean inputSelected;

    public HFragment() {
        type = TYPE_STRING_ALONE;
        originalValue = "";
        stringValue = "";
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setOriginalValue(String originalValue) {
        this.originalValue = originalValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = this.cleanValue(stringValue);
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public void setInputSelected(boolean inputSelected) {
        this.inputSelected = inputSelected;
    }

    public void setHtables(List htables) {
        this.htables = htables;
    }

    public int getType() {
        return (this.type);
    }

    public String getOriginalValue() {
        return (this.originalValue);
    }

    public String getStringValue() {
        return (this.stringValue);
    }

    public String getInputType() {
        return (this.inputType);
    }

    public boolean getInputSelected() {
        return (this.inputSelected);
    }

    public List getHtables() {
        if (htables == null) {
            htables = new ArrayList();
        }
        return (this.htables);
    }

    public String getTypeValue() {
        return TYPE_VALUES[this.type - 1];
    }

    public static HFragment createFragment(String htmlFragment,
            String tableNamePrefix,
            Map tables,
            String[] imagesNamesForRadioCheck) {

        HFragment hfragment = new HFragment();

        hfragment.setOriginalValue(htmlFragment);

        //htmlFragment = parseFragmentClean(htmlFragment);

        htmlFragment = htmlFragment.trim();
        htmlFragment = htmlFragment.replaceAll("\n", "");

        int startTagPos;
        int startCloseTagPos;
        int endTagPos;
        String stringValue;
        String value;
        if (htmlFragment.length() > 0) {
            if (htmlFragment.charAt(0) == '<') {
                if (false) {
                } else if (htmlFragment.startsWith("<input")) {
                    //TYPE_INPUT_ALONE or TYPE_INPUT_AND_STRING
                    startCloseTagPos = htmlFragment.indexOf(">");
                    if (startCloseTagPos == -1) {
                        log.info("HTML PARSER: frammento html iniziante con input ignorato perche' contiene un tag <b non chiuso." + htmlFragment);
                    } else {
                        int inputTypePos = htmlFragment.indexOf(" type=\"");
                        if (inputTypePos >= 0 && inputTypePos < startCloseTagPos) {
                            int inputTypeOpenPos = inputTypePos + " type=\"".length();
                            int inputTypeClosePos = htmlFragment.indexOf("\"", inputTypeOpenPos);
                            if (inputTypeClosePos < startCloseTagPos) {
                                String inputType = htmlFragment.substring(inputTypeOpenPos, inputTypeClosePos);
                                if (htmlFragment.length() > (startCloseTagPos + 1)) {
                                    //TYPE_INPUT_AND_STRING                                   
                                    stringValue = htmlFragment.substring(startCloseTagPos + 1);
                                    hfragment.setType(HFragment.TYPE_INPUT_AND_STRING);
                                    hfragment.setInputType(inputType);
                                    hfragment.setStringValue(stringValue);
                                } else {
                                    //TYPE_INPUT_ALONE
                                    hfragment.setType(HFragment.TYPE_INPUT_ALONE);
                                    hfragment.setInputType(inputType);
                                }
                                int checkedPos = htmlFragment.indexOf(" checked");
                                if (checkedPos >= 0 && checkedPos < startCloseTagPos) {
                                    hfragment.setInputSelected(true);
                                }

                            } else {
                                log.info("HTML PARSER: frammento html iniziante con input ignorato perche' manca chiusura attributo type." + htmlFragment);
                            }
                        } else {
                            log.info("HTML PARSER: frammento html iniziante con input ignorato perche' manca attributo type." + htmlFragment);
                        }
                    }
                } else if (htmlFragment.startsWith("<img")) {
                    //TYPE_INPUT_ALONE 
                    startCloseTagPos = htmlFragment.indexOf(">");
                    if (startCloseTagPos == -1) {
                        log.info("HTML PARSER: frammento html iniziante con img ignorato perche' contiene un tag <img non chiuso." + htmlFragment);
                    } else {
                        String tagValue = htmlFragment.substring(0, startCloseTagPos);
                        tagValue = tagValue.toLowerCase();
                        int selectedPos;
                        selectedPos = tagValue.indexOf(imagesNamesForRadioCheck[HtmlTableParser.INPUT_RADIO_CHECK_YES_INDEX]);
                        if (selectedPos >= 0) {
                            hfragment.setType(HFragment.TYPE_INPUT_ALONE);
                            hfragment.setInputType("check");
                            hfragment.setInputSelected(true);
                        } else {
                            selectedPos = tagValue.indexOf(imagesNamesForRadioCheck[HtmlTableParser.INPUT_RADIO_CHECK_NO_INDEX]);
                            if (selectedPos >= 0) {
                                hfragment.setType(HFragment.TYPE_INPUT_ALONE);
                                hfragment.setInputType("check");
                                hfragment.setInputSelected(false);
                            } else {
                                selectedPos = tagValue.indexOf(imagesNamesForRadioCheck[HtmlTableParser.INPUT_RADIO_RADIO_YES_INDEX]);
                                if (selectedPos >= 0) {
                                    hfragment.setType(HFragment.TYPE_INPUT_ALONE);
                                    hfragment.setInputType("radio");
                                    hfragment.setInputSelected(true);
                                } else {
                                    selectedPos = tagValue.indexOf(imagesNamesForRadioCheck[HtmlTableParser.INPUT_RADIO_RADIO_NO_INDEX]);
                                    if (selectedPos >= 0) {
                                        hfragment.setType(HFragment.TYPE_INPUT_ALONE);
                                        hfragment.setInputType("radio");
                                        hfragment.setInputSelected(false);
                                    } else {
                                        log.info("HTML PARSER: frammento img ignorato perche' si riferisce ad una immagine non rinconosciuta" + htmlFragment + ")");
                                    }
                                }
                            }
                        }
                    }
                } else {
                    hfragment.setStringValue(htmlFragment);
//                    log.info("HTML PARSER: frammento html ignorato perche' inizia con un tag non gestito" + htmlFragment + ")");
                }
            } else if (htmlFragment.startsWith(tableNamePrefix)) {
                //TYPE_TABLES                
                if (htmlFragment.indexOf("<") == -1) {
                    List fragmentTables = hfragment.getHtables();
                    String tableName;
                    StringTokenizer st = new StringTokenizer(htmlFragment, " ");
                    hfragment.setType(HFragment.TYPE_TABLES);
                    while (st.hasMoreTokens()) {
                        tableName = st.nextToken();
                        if (tableName.startsWith(tableNamePrefix)) {
                            HTable htable = (HTable) tables.get(tableName);
                            if (htable != null) {
                                fragmentTables.add(htable);
                            } else {
                                log.info("HTML PARSER: frammento html con tabelle ignorato perche' ci sono tabelle inesistenti." + htmlFragment + "," + tableName);
                            }
                        } else {
                            log.info("HTML PARSER: frammento html con tabelle ignorato perche' ci sono anche altri token." + htmlFragment);
                        }
                    }
                } else {
                    log.info("HTML PARSER: frammento html con tabelle ignorato perche' ci sono anche altri tag." + htmlFragment);
                }
            } else {
                //TYPE_STRING_ALONE or TYPE_STRING_AND_INPUT
                startTagPos = htmlFragment.indexOf("<input");
                if (startTagPos == -1) {
                    //TYPE_STRING_ALONE
                    stringValue = htmlFragment;
                    hfragment.setType(HFragment.TYPE_STRING_ALONE);
                    hfragment.setStringValue(stringValue);
                } else {
                    //TYPE_STRING_AND_INPUT
                    startCloseTagPos = htmlFragment.indexOf(">");
                    if (startCloseTagPos == -1) {
                        log.info("HTML PARSER: frammento html contenente input ignorato perche' contiene un tag <input non chiuso." + htmlFragment);
                    } else {
                        int inputTypePos = htmlFragment.indexOf(" type=\"");
                        if (inputTypePos >= 0 && inputTypePos < startCloseTagPos) {
                            int inputTypeOpenPos = inputTypePos + " type=\"".length();
                            int inputTypeClosePos = htmlFragment.indexOf("\"", inputTypeOpenPos);
                            if (inputTypeClosePos < startCloseTagPos) {
                                String inputType = htmlFragment.substring(inputTypeOpenPos, inputTypeClosePos);
                                if (htmlFragment.length() > (startCloseTagPos + 1)) {
                                    log.info("HTML PARSER: frammento html contenente input ignorato perche' ha una stringa anche dopo la chiusura." + htmlFragment);
                                } else {
                                    //TYPE_STRING_AND_INPUT
                                    stringValue = htmlFragment.substring(0, startTagPos).trim();
                                    hfragment.setType(HFragment.TYPE_STRING_AND_INPUT);
                                    hfragment.setInputType(inputType);
                                    hfragment.setStringValue(stringValue);
                                }
                                int checkedPos = htmlFragment.indexOf(" checked");
                                if (checkedPos >= 0 && checkedPos < startCloseTagPos) {
                                    hfragment.setInputSelected(true);
                                }
                            } else {
                                log.info("HTML PARSER: frammento html contenente input ignorato perche' manca chiusura attributo type." + htmlFragment);
                            }
                        } else {
                            log.info("HTML PARSER: frammento html contenenteinput ignorato perche' manca attributo type." + htmlFragment);
                        }
                    }
                }
            }
        }

        return hfragment;
    }
    /*
    //pulisco tutto l'html fragment degli elementi e valori inutili o in ogni caso da non analizzare
    private static  String      parseFragmentClean(String htmlFragment) {

    String  cleaned = htmlFragment;

    //elimino tutti gli spazi a sinistra e a destra
    htmlFragment.trim();

    //elimino tutti i \n
    cleaned = cleaned.replaceAll("\n", "");

    //elimino tutti i tag <label e <span
    cleaned = removeTag(cleaned, "label");
    cleaned = removeTag(cleaned, "span");

    return  cleaned;
    }    
     */

    //elimina tutte le occorrenze di un tag (senza eliminare anche tutto il suo contenunto)
    private static String removeTag(String htmlFragment, String tagCleaned) {

        StringBuffer removed = new StringBuffer(htmlFragment);

        int tagStartPos;
        int tagStartClosePos;
        tagStartPos = removed.indexOf("<" + tagCleaned);
        while (tagStartPos >= 0) {
            tagStartClosePos = removed.indexOf(">", tagStartPos);
            if (tagStartClosePos >= 0) {
                removed.replace(tagStartPos, tagStartClosePos + 1, "");
                tagStartPos = removed.indexOf("<" + tagCleaned, tagStartPos);
            } else {
                log.info("HTML PARSER: contenuto html ignorato perche' manca ultimo carattere tag rimosso (" + htmlFragment + "," + tagCleaned + ")");
            }
        }

        int tagEndPos;                  //pos of </tagcleaned
        int tagEndCloseLastCharPos;     //pos of >
        String tagEnd = "</" + tagCleaned + ">";
        tagEndPos = removed.indexOf(tagEnd);
        while (tagEndPos >= 0) {
            tagEndCloseLastCharPos = tagEndPos + tagEnd.length();
            removed.replace(tagEndPos, tagEndCloseLastCharPos, "");
            tagEndPos = removed.indexOf(tagEnd, tagEndPos);
        }

        return removed.toString();
    }

    private String cleanValue(String value) {

        String cleaned = value;

        if (value.indexOf("<") == -1) {
            cleaned = cleaned.trim();
            cleaned = cleaned.replaceAll("&amp;nbsp;", " ");

            //cleaned = cleaned.replaceAll("&amp;",   "&");
        } else {
//            log.info("HTML PARSER FRAGMENT: valore html string ignorato perche' contiene un tag non gestito(" + value + ")");
//            cleaned = "";
            cleaned = cleaned.trim();
            cleaned = cleaned.replaceAll("<b>", "&lt;b&gt;");
            cleaned = cleaned.replaceAll("</b>", "&lt;/b&gt;");
            cleaned = cleaned.replaceAll("<", "&lt;");
            cleaned = cleaned.replaceAll(">", "&gt;");
//            cleaned = cleaned.replaceAll("&lt;b&gt;", "<b>");
//            cleaned = cleaned.replaceAll("&lt;/b&gt;", "</b>");

        }
        return cleaned;
    }

    public String toString() {

        String sep = ",";

        StringBuffer buffer = new StringBuffer("HFragment");
        buffer.append("{");
        buffer.append("type = ");
        buffer.append(type);
        buffer.append(sep);
        buffer.append("typeValue = ");
        buffer.append(this.getTypeValue());
        buffer.append(sep);
        buffer.append("stringValue = ");
        buffer.append(stringValue);
        buffer.append(sep);
        buffer.append("inputType = ");
        buffer.append(inputType);
        buffer.append(sep);
        buffer.append("inputSelected = ");
        buffer.append(inputSelected);
        buffer.append(sep);
        buffer.append("htables = ");
        buffer.append(htables);
        //buffer.append(sep);
        //buffer.append("originalValue = ");
        //buffer.append(originalValue);
        buffer.append("}");

        return buffer.toString();
    }

    public static void main(String[] args) throws Exception {

        String html;

        HFragment hfragment;

        String[] imagesNamesForRadioCheck = new String[4];
        imagesNamesForRadioCheck[HtmlTableParser.INPUT_RADIO_CHECK_YES_INDEX] = "checkno.gif";
        imagesNamesForRadioCheck[HtmlTableParser.INPUT_RADIO_CHECK_NO_INDEX] = "checksi.gif";
        imagesNamesForRadioCheck[HtmlTableParser.INPUT_RADIO_RADIO_YES_INDEX] = "radiono.gif";
        imagesNamesForRadioCheck[HtmlTableParser.INPUT_RADIO_RADIO_NO_INDEX] = "radiosi.gif";

        String tablePrefix = "$D";
        Map tables = new HashMap();
        tables.put("$D1", new HTable());
        tables.put("$D2", new HTable());
        tables.put("$D3", new HTable());

        html = "";
        //html += "  hello world   ";    
        //html += "  hello world   ";    
        //html += "<b>hello</b>world";    
        //html += "hello<b>world</b>";    
        //html += "<input type=\"radio\">hello world";    
        //html += "<input type=\"radio\">";    
        html += "hello world<input type=\"radio\">";
        //html += "$D1 $D2 $D3";

        hfragment = HFragment.createFragment(html, tablePrefix, tables, imagesNamesForRadioCheck);

//        System.out.println(hfragment);
    }
}
