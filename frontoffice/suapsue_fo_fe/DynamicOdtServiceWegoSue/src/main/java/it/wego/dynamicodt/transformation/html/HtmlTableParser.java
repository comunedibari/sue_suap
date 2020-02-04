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

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import org.apache.commons.lang.StringEscapeUtils;

public class HtmlTableParser {

    private static Log log = LogFactory.getLog(HtmlTableParser.class);
    private static final String HTML_BACKGROUND_COLOR_OPTION = "background-color:";
    private String tableNamePrefixInternal;
    private String backgroundColorForBold;
    private String[] imagesNamesForRadioCheck;   //nomi delle immagini che identificano un radio o un check box
    public static final int INPUT_RADIO_CHECK_YES_INDEX = 0;
    public static final int INPUT_RADIO_CHECK_NO_INDEX = 1;
    public static final int INPUT_RADIO_RADIO_YES_INDEX = 2;
    public static final int INPUT_RADIO_RADIO_NO_INDEX = 3;
    private Map tables;

    public HFragment parserHtml(String html, Properties properties) throws Exception {

        this.init(properties);
        //html = "<table><tr><td>1</td></tr></table><table><tr><td>2</td></tr></table><table><tr><td>3</td></tr></table><table><tr><td>4</td></tr></table><table><tr><td>5</td></tr></table><table><tr><td>6</td></tr></table><table><tr><td>7</td></tr></table><table><tr><td>8</td></tr></table><table><tr><td>9</td></tr></table><table><tr><td>1</td></tr></table>";

        HFragment hfragment = this.parserHtml(new StringBuffer(html), properties);

        return hfragment;
    }

    private HFragment parserHtml(StringBuffer html, Properties properties) {

        HFragment hfragment = new HFragment();

        HTable htable;
        List fragmentTables;
        String tableName;

        tables = new HashMap(); //key=<String>nome tabella,value=<HTable>,tabella

        this.parserHtmlClean(html);

        int htmlPos = 0;
        int tableStartPos = html.indexOf("<table", 0);
        if (tableStartPos >= 0) {
            if (tableStartPos > 0) {
                String htmlTopIgnored = html.substring(0, tableStartPos);
                log.info("HTML PARSER: frammento html iniziale ignorato perche' esterno al primo tag <table>.("
                        + htmlTopIgnored + ")");
                html.replace(0, tableStartPos, ""); //elimino tutto quello che sta prima del primo tag <table
            }
            int lastTableEndPos = html.lastIndexOf("</table>");
            if (lastTableEndPos >= 0) {
                int lastTableEndClosePos = lastTableEndPos + "</table>".length();
                if (html.length() > lastTableEndClosePos) {
                    String htmlBottomIgnored = html.substring(lastTableEndClosePos);
                    log.info("HTML PARSER: frammento html finale ignorato perche' esterno all'ultimo tag </table>.("
                            + htmlBottomIgnored + ")");
                    html.setLength(lastTableEndClosePos); //elimino tutto quello che sta dopo l'ultmio tag </table>
                }
                this.parserTable(html, 0, 0);
                String tablesNames = html.toString();
                StringTokenizer st = new StringTokenizer(tablesNames, " ");
                hfragment = new HFragment();
                hfragment.setType(HFragment.TYPE_TABLES);
                fragmentTables = hfragment.getHtables();
                while (st.hasMoreTokens()) {
                    tableName = st.nextToken().trim();
                    if (tableName.startsWith(tableNamePrefixInternal)) {
                        htable = (HTable) tables.get(tableName);
                        htable.setInHead(true);
                        if (htable != null) {
                            fragmentTables.add(htable);
                        } else {
                            log.info("HTML PARSER: frammento html iniziale con tabelle ignorato perche' ci sono tabelle inesistenti."
                                    + "tableName(" + tableName + "),html(" + html + ")");
                        }
                    } else {
                        log.info("HTML PARSER: frammento html iniziale con tabelle ignorato perche' ci sono anche altri token."
                                + "tableName(" + tableName + "),html(" + html + ")");
                    }
                }
            } else {
                log.info("HTML PARSER: contenuto html ignorato perche' senza nessun tag </table>.(" + html + ")");
            }
        } else {
            log.info("HTML PARSER: contenuto html ignorato perche' senza nessun tag <table>.(" + html + ")");
        }

        return hfragment;
    }

    private void parserHtmlClean(StringBuffer html) {

        log.debug("PRE PRE-PARSING: " + html.toString());

        HTools.trim(html);
        HTools.replaceAllWithJolly(html, "(<b><img src=)(.*?)(/>&nbsp;)", "");
        HTools.replaceAllWithJolly(html, "(<a href=)(.*?)(</b></a></b>)", "");

//       String htmlString = StringEscapeUtils.unescapeHtml(html.toString());

//        htmlString = StringEscapeUtils.unescapeHtml(htmlString);
////        log.debug("ESCAPING : "+htmlString);

//        html.delete(0, html.length());
//        html.append(htmlString);

        // HTools.replaceAllWithJolly(html, "&lt;", "<");
        // HTools.replaceAllWithJolly(html, "&gt;", ">");

        HTools.replaceAllWithJolly(html, "&", "&amp;");

        HTools.replaceAllWithJolly(html, "</table><table>", "");
        HTools.replaceAllWithJolly(html, "style=\"color:red;font-size:120%;\" ", "");

        String tdStartBold = "<td style=\"background-color:#e6e6e6\">";
        HTools.replaceAllWithJolly(html, "<td[ ]*colspan=[0-9\"]*>", "<td>");
        HTools.replaceAllWithJolly(html, "<label[^\\<]*>", "");
        HTools.replaceAllWithJolly(html, "</label>", "");
        HTools.replaceAllWithJolly(html, "<span[^\\<]*>", "");
        HTools.replaceAllWithJolly(html, "</span>", "");
        HTools.replaceAllWithJolly(html, "(<i>)([^<]*)(</i>)", "");



        HTools.replaceAllWithJolly(html, "(<td[^>]*>)([^<]*)(<b>)([^<]*)(</b>)(<br/>)(.*?)(</td>)", "$1$2</td><td><table class=\"Paragrafo\"><TAGB>$4</TAGB>$6$7</table></td>");

        HTools.replaceAllWithJolly(html, "(<br/>)([^\\<]*)", "<TAG>$2</TAG>");

        //piergiorgio HTools.replaceAllWithJolly(html, "<TAG></TAG></table>","</table>");
        HTools.replaceAllWithJolly(html, "<TAG></TAG>", "<TAG>__EMPTY__!!</TAG>");

        HTools.replaceAllWithJolly(html, "(<td[^\\<]*>)(<b>)([^\\<]*)(</b>)(</td>)", tdStartBold + "$3" + "</td>");

//        //HTools.replaceAllWithJolly(html, "(<td[^\\<]*>)([^\\<]*)(<b>)([^\\<]*)(</b>)([^\\<]*)(</td>)", "$1$2</td>"+tdStartBold+"$4</td><td>$6</td>");
//        HTools.replaceAllWithJolly(html, "(<td[^\\<]*>)([^\\<]*)(<b>)([^\\<]*)(</b>)([^\\<]*)(</td>)", tdStartBold+"$2</td>"+"<td>"+"$4</td><td>$6</td>");
        HTools.replaceAllWithJolly(html, "<td.*?>(.*?)<b>(.*?)</b>(.*?)</td>", tdStartBold + "$1</td>" + "<td>" + "$2</td><td>$3</td>");

        //while(HTools.replaceAllWithJolly(html, "<(\\w+)[^>]*>\\s*</\\1\\s*>", "")) ;

        //piergiorgio HTools.replaceAllWithJolly(html, "<TAG>__EMPTY__!!</TAG>", "<TAG></TAG>");

        HTools.replaceAllWithJolly(html, "<table/>", "<table></table>");
        HTools.replaceAllWithJolly(html, "<tr/>", "<tr></tr>");
        HTools.replaceAllWithJolly(html, "<td/>", "<td></td>");

        HTools.replaceAllWithJolly(html, "<tr", "<tr><td><table class=\"fakeTable\"><tr");
        HTools.replaceAllWithJolly(html, "</tr>", "</tr></table></td></tr>");

        HTools.replaceAllWithJolly(html, "(<TAGB>)(.*?)(</TAGB>)", "<tr><td style=\"background-color:#e6e6e6\">$2</td></tr>");

        HTools.replaceAllWithJolly(html, "(<TAG>)(.*?)(</TAG>)", "<tr><td>$2</td></tr>");

        while (HTools.replaceAllWithJolly(html, "<(\\w+)[^>]*>\\s*</\\1\\s*>", ""));

        //piergiorgio
        //HTools.replaceAllWithJolly(html, "__EMPTY__!!", "");
        //piergiorgio
        log.debug("POST PRE-PARSING: " + html.toString());
    }

    private void parserTable(StringBuffer html, int pos, int tableIndex) {

        tableIndex++;

        int tableStartPos = html.indexOf("<table", pos + 1);
        if (tableStartPos >= 0) {
            this.parserTable(html, tableStartPos, tableIndex);
        }

        int tableEndPos = html.indexOf("</table>", pos);

        if (tableEndPos >= 0) {
            int tableEndPosLastChar = tableEndPos + "</table>".length();
            String htmlTable = html.substring(pos, tableEndPosLastChar);
            HTable htableSingle = this.parserTableSingle(htmlTable, tableIndex);

            int startClassPos = htmlTable.indexOf("class=\"fakeTable\"");
            if (startClassPos != -1) {
                htableSingle.setFakeTable(false);
            } else {
                htableSingle.setFakeTable(true);
            }
            startClassPos = htmlTable.indexOf("class=\"Paragrafo\"");
            if (startClassPos != -1) {
                htableSingle.setParagrafo(true);
                htableSingle.setParagrafoBackGround("#e6e6e6");
            }

            tables.put(htableSingle.getName(), htableSingle);
            html.replace(pos, tableEndPosLastChar, htableSingle.getName() + " "); //sostituisco la tabella interna di un TD con il nome della tabella
            //seguito da space
        } else {
            log.info("HTML PARSER: contenuto html ignorato perche' manca tag di chiusura </table>.(" + html + ")");
        }
    }

    private HTable parserTableSingle(String htmlTable, int tableIndex) {
        log.debug("table single(" + htmlTable + ")");
        //System.out.println ("table single(" + htmlTable + ")");

        HTable htable = new HTable();
        htable.setName(tableNamePrefixInternal + tableIndex);

        int trEndPos;
        int trEndPosLastChar;
        int trStartPos = htmlTable.indexOf("<tr");
        while (trStartPos >= 0) {
            trEndPos = htmlTable.indexOf("</tr>", trStartPos);
            if (trEndPos >= 0) {
                trEndPosLastChar = trEndPos + "</tr>".length();
                String htmlTr = htmlTable.substring(trStartPos, trEndPosLastChar);
                parserTableTr(htable, htmlTr);
                trStartPos = htmlTable.indexOf("<tr>", trEndPosLastChar);
            } else {
                log.info("HTML PARSER: contenuto html ignorato perche' manca tag di chiusura </tr>.(" + htmlTable + ")");
                trStartPos = -1;
            }
        }

        htable.adjust();

        return htable;
    }

    private void parserTableTr(HTable htable, String htmlTr) {
        log.debug("tr(" + htmlTr + ")");
        //System.out.println ("tr(" + htmlTr + ")");

        HTr htr = new HTr();
        List htrs = htable.getHTrs();
        htrs.add(htr);

        int tdEndPos;
        int tdEndPosLastChar;
        int tdStartPos = htmlTr.indexOf("<td");
        while (tdStartPos >= 0) {
            int tdStartClosePos;
            tdStartClosePos = htmlTr.indexOf(">", tdStartPos);
            if (tdStartClosePos >= 0) {
                tdEndPos = htmlTr.indexOf("</td>", tdStartClosePos);
                if (tdEndPos >= 0) {
                    tdEndPosLastChar = tdEndPos + "</td>".length();
                    String htmlTd = htmlTr.substring(tdStartPos, tdEndPosLastChar);
                    parserTableTd(htable, htr, htmlTd);
                    tdStartPos = htmlTr.indexOf("<td", tdEndPosLastChar);
                } else {
                    log.info("HTML PARSER: contenuto html ignorato perche' manca tag di chiusura </td>.(" + htmlTr + ")");
                    tdStartPos = -1;
                }
            } else {
                log.info("HTML PARSER: contenuto html ignorato perche' manca carattere > di chiusura tag <td.(" + htmlTr + ")");
                tdStartPos = -1;
            }
        }
    }

    private void parserTableTd(HTable htable, HTr htr, String htmlTd) {
        log.debug("td(" + htmlTd + ")");
        //System.out.println ("td(" + htmlTd + ")");

        String tdValue;
        String tableName;
        String bcolor;

        HTd htd = new HTd();
        htr.getHTds().add(htd);

        int tdStartClosePos = htmlTd.indexOf(">");
        if (tdStartClosePos >= 0) {
            if (htmlTd.endsWith("</td>")) {
                tdValue = htmlTd.substring(tdStartClosePos + 1, htmlTd.length() - "</td>".length());
                log.debug("tdValue(" + tdValue + ")");
                HFragment hfragment = HFragment.createFragment(tdValue, tableNamePrefixInternal, tables,
                        imagesNamesForRadioCheck);
                htd.setHFragment(hfragment);

                //setto il colore di background
                int bcolorPos = htmlTd.indexOf(HTML_BACKGROUND_COLOR_OPTION);
                if (bcolorPos > 0) {
                    int bcolorValueStartPos = bcolorPos + HTML_BACKGROUND_COLOR_OPTION.length();
                    int bcolorValueEndPos = bcolorValueStartPos + 7;          //assumo che il colore sia nel formato #xxxxxx
                    if (bcolorValueStartPos < (htmlTd.length() - 1)
                            && bcolorValueEndPos < (htmlTd.length() - 1)) {
                        bcolor = htmlTd.substring(bcolorValueStartPos, bcolorValueEndPos);
                        htd.setBackgroundColor(bcolor);
                    } else {
                        log.warn("HTML PARSER: attributo background color ignorato perche' non del formato #xxxxxx.(" + htmlTd + ")");
                    }
                }
                int colspanPos = htmlTd.indexOf(" colspan=\"");
                if (colspanPos > 0) {
                    int colspanValueStartPos = colspanPos + " colspan=\"".length();
                    int colspanValueEndPos = htmlTd.indexOf('"', colspanValueStartPos);
                    if (colspanValueEndPos > 0) {
                        String colspan = htmlTd.substring(colspanValueStartPos, colspanValueEndPos);
                        int colspanValue = new Integer(colspan).intValue();
                        if (colspanValue > 0) {
                            htd.setColspan(colspanValue);
                        } else {
                            log.warn("HTML PARSER: attributo colspan ignorato perche' non con valore numerico.(" + htmlTd + ")");
                        }
                    } else {
                        log.warn("HTML PARSER: attributo colspan ignorato perche' non del formato colspan=\"n\".(" + htmlTd + ")");
                    }
                }
            } else {
                log.warn("HTML PARSER: contenuto html ignorato perche' manca illogicamente tag di chiusura </td>.(" + htmlTd + ")");
            }
        } else {
            log.info("HTML PARSER: contenuto html ignorato perche' manca ultimo carattere tag di apertura </td.(" + htmlTd + ")");
        }
    }

    private void init(Properties properties) throws Exception {

        if (properties == null) {
            properties = new Properties();
        }
        tableNamePrefixInternal = properties.getProperty("h2oTableNamePrefixInternal", "$$$DYDOT_TABLE_").trim();
        backgroundColorForBold = properties.getProperty("h2oBackgroundColorForBold", "#e6e6e6").trim();
        if (backgroundColorForBold.length() != 7
                || backgroundColorForBold.charAt(0) != '#') {
            log.info("HTML PARSER: il colore di background deve essere nel formato #xxxxxx (es. #e6e6e6). Assunto #e6e6e6.(" + backgroundColorForBold + ")");
            backgroundColorForBold = "#e6e6e6";
        }

        imagesNamesForRadioCheck = new String[4];
        imagesNamesForRadioCheck[INPUT_RADIO_CHECK_YES_INDEX] = properties.getProperty("h2oInputCheckSi", "checksi.gif").trim();
        imagesNamesForRadioCheck[INPUT_RADIO_CHECK_NO_INDEX] = properties.getProperty("h2oInputCheckNo", "checkno.gif").trim();
        imagesNamesForRadioCheck[INPUT_RADIO_RADIO_YES_INDEX] = properties.getProperty("h2oInputRadioSi", "radiosi.gif").trim();
        imagesNamesForRadioCheck[INPUT_RADIO_RADIO_NO_INDEX] = properties.getProperty("h2oInputRadioNo", "radiono.gif").trim();

    }

    public static void main(String[] args) throws Exception {

        String html = "";
        html += "<table width=\"100%\" border=\"1\" cellpadding=\"2\" cellspacing=\"0\">";

        html += "<tr>";
        html += "<td>";
        html += "via:<b>via Roma</b>";
        html += "</td>";
        html += "</tr>";
        html += "<tr>";
        html += "<td>";
        html += "numero civico:<b>14</b>";
        html += "</td>";
        html += "</tr>";

        html += "<tr>";
        html += "<td>";
        html += "1<b>2</b>3<b>4</b>5";
        html += "</td>";
        html += "</tr>";

        html += "<tr>";
        html += "<td>";
        html += "1<b>2</b>3<b>4</b>";
        html += "</td>";
        html += "</tr>";

        html += "<tr>";
        html += "<td>";
        html += "<b>2</b>3<b>4</b>";
        html += "</td>";
        html += "</tr>";

        html += "<tr>";
        html += "<td>";
        html += "<b>2</b><b>3</b>";
        html += "</td>";
        html += "</tr>";

        html += "<tr>";
        html += "<td>";
        html += "<b>2</b>";
        html += "</td>";
        html += "</tr>";

        html += "<tr>";
        html += "<td>";
        html += "1<b>2</b>";
        html += "</td>";
        html += "</tr>";

        html += "<tr>";
        html += "<td>";
        html += "<b>2</b>1";
        html += "</td>";
        html += "</tr>";
        /*
        html += "<tr>";        
        html += "<td>";        
        html += "<table>";
        html += "<tr>";
        html += "<td>AAAINNER1</td>";
        html += "<td>";
        html += "<table>";
        html += "<tr><td>BBBINNER2</td></tr>";
        html += "</table>";
        html += "</td>";
        html += "</tr>";        
        html += "</table>";

        html += "</td>";        

        html += "<td>";        
        html += "<table>";
        html += "<tr>";
        html += "<td>CCCINNER3</td>";
        html += "</tr>";        
        html += "</table>";
        html += "</td>";        
         */
        html += "</table>";

        if (true) {
            java.io.File xmlFile = new java.io.File("../resources/is2.html");
            html = HTools.convertFileIntoString(xmlFile);
        }

        HFragment hfragment;

        HtmlTableParser parser = new HtmlTableParser();

        hfragment = parser.parserHtml(html, null);

//        System.out.println("hfragment(\n" + hfragment + "\n)");
    }
}
