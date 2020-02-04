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

import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TreeMap;
import java.util.Set;
import java.util.TreeSet;

import freemarker.template.*;

public class OdtTableGenerator {

    private static Log log = LogFactory.getLog(OdtTableGenerator.class);
    public static int indice = 0;
    private String h2oTemplateDir;
    private String h2oTemplateTableFile;
    private String h2oTemplateStyleFiles;
    private String h2oTemplateFormFiles;
    private String h2oTableNamePrefix;
    private String h2oTableStyleName;
    private String h2oCellStyleName;
    private String h2oCellBoldStyleName;
    private String h2oTableNamePrefixInternal;
    private boolean h2oInputGraphics;
    private Map tablesOdt;
    private Set tablesInHead;
    private List stylesOdt;
    private List formsOdt;
    private Set stylesAlreadyBuilt;

    public OdtTableResult generateOdtTables(String html, OdtTableParameter parameter, Properties properties) throws Exception {

        this.init(properties);

        //Mappa delle tabelle in formato odt
        //Ogni elemento ha per chiave il nome interno della tabella (di tipo String),
        //e come valore ha la stringa Odt generata da Freemarker (di tipo StringBuffer)
        //E' stata scelta una TreeMap in quanto l'ordine e' comodo in fase di stampa di debug,
        //ma poteva anche essere una HashMap
        tablesOdt = new TreeMap();

        //Set dei nomi delle tabelle in testa
        //Ogni elemento e' il nome della tabella (di tipo String)
        //Una tabella si dice "in testa" quando non e' una innerTable,
        //o, ugualmente, quando non e' contenuta in alcuna altra tabella.
        //E' stata scelta una TreeSet in quanto l'ordine e' fondamentale.
        tablesInHead = new TreeSet();

        //Lista degli stili in formato odt
        //Ogni elemento ha per valore ha la stringa Odt generata da Freemarker (di tipo StringBuffer)
        stylesOdt = new ArrayList();

        formsOdt = new ArrayList();

        stylesAlreadyBuilt = new HashSet();

        // Parse html*/
        HtmlTableParser parser = new HtmlTableParser();
        HFragment hfragment = parser.parserHtml(html, properties);

        this.generateTables(hfragment, parameter);
        OdtTableResult result = this.buildResult(tablesOdt, tablesInHead, stylesOdt);

        return result;
    }

    private void generateTables(HFragment hfragment,
            OdtTableParameter parameter)
            throws Exception {

        Map dataModel;
        HTable htable;
        StringWriter odtString;

        Configuration freemarkerConfig = parameter.getFreemarkerConfig();
        Template templateTable = freemarkerConfig.getTemplate(h2oTemplateTableFile);

        List htables = hfragment.getHtables();
        int htablesSize = htables.size();
        for (int i = 0; i < htablesSize; i++) {
            htable = (HTable) htables.get(i);
            if (htable.getInHead()) {
                tablesInHead.add(htable.getName());
            }
            dataModel = this.buildDataModel(htable, parameter);

            /* Merge data-model with template */
            odtString = new StringWriter();
            templateTable.process(dataModel, odtString);
            //odtTables.add(odtString.getBuffer());            
            tablesOdt.put(htable.getName(), odtString.getBuffer());
            odtString.flush();

            if (!parameter.getStylesDefaultAlreadyGenerated()) {
                this.generateStyleDefault(parameter, dataModel);
                this.generateFormDefault(parameter, dataModel);
                parameter.setStylesDefaultAlreadyGenerated(true);
            }
        }
    }

    private Map buildDataModel(HTable htable,
            OdtTableParameter parameter)
            throws Exception {

        List htrs = htable.getHTrs();
        int htrsNumber = htrs.size();
        List htds;
        int htdsNumber;
        HTr htr;
        HTd htd;
        HFragment hfragment;
        String value;

        Map dataModel = new HashMap();
        Map rows;
        Map cols;
        Map colData = new HashMap();
        SimpleSequence row;
        SimpleSequence col;

        int tableIndex = parameter.getTableIndex();
        tableIndex++;
        String tableName = h2oTableNamePrefix + tableIndex;
        ;
        parameter.setTableIndex(tableIndex);
        parameter.setTableName(tableName);

        dataModel.put("nomeTabella", tableName);
        dataModel.put("nomeTabellaInterna", htable.getName());
        dataModel.put("stileTabella", h2oTableStyleName);

        dataModel.put("stileCella", h2oCellStyleName);
        if (htable.isFakeTable()) {
            dataModel.put("stileCella", "DydotCellaStileFake");
        }
        if (htable.isParagrafo()) {
            dataModel.put("stileCella", "Paragrafo");
            dataModel.put("bgCella", htable.getParagrafoBackGround());
        }

        dataModel.put("stileBoldCella", h2oCellBoldStyleName);
        dataModel.put("numeroColonne", htable.getNumberColumnsMax() + "");
        dataModel.put("colonneRipetute", htable.getRepeteadColumns() + "");

        String outputType = parameter.getOutputType();

        if (outputType.toLowerCase().compareTo("html") == 0) {
            log.debug("OUTPUTTYPE: html ->" + outputType);
            dataModel.put("inputGrafici", false + "");
        } else {
            log.debug("OUTPUTTYPE: altro -> " + outputType);
            dataModel.put("inputGrafici", true + "");
        }

        if (!htable.getRepeteadColumns()) {
            SimpleSequence widthColumnsSequence = new SimpleSequence();
            dataModel.put("larghezzeRelativeColonne", widthColumnsSequence);
            List widthColumns = htable.getRelativeColumnsWidth();
            Integer widthColumn;
            for (int i = 0; i < widthColumns.size(); i++) {
                widthColumn = (Integer) widthColumns.get(i);
                widthColumnsSequence.add(widthColumn.toString());
            }
            this.generateStyleColumns(parameter, widthColumns);
        }

        rows = new HashMap();
        dataModel.put("table", rows);
        row = new SimpleSequence();
        rows.put("tr", row);

        List htables;

        for (int htrsIndex = 0; htrsIndex < htrsNumber; htrsIndex++) {

            cols = new HashMap();
            col = new SimpleSequence();
            cols.put("td", col);
            row.add(cols);

            htr = (HTr) htrs.get(htrsIndex);
            htds = htr.getHTds();
            htdsNumber = htds.size();
            boolean isNextToInput = false;
            for (int htdsIndex = 0; htdsIndex < htdsNumber; htdsIndex++) {
                htd = (HTd) htds.get(htdsIndex);
                hfragment = htd.getHFragment();
                colData = new HashMap();
                col.add(colData);
                colData.put("valore", hfragment.getStringValue());
                colData.put("coloreBackground", htd.getBackgroundColor());
                colData.put("colspan", htd.getColspan() + "");
                if (hfragment.getType() == HFragment.TYPE_INPUT_ALONE) {
                    isNextToInput = true;
                    colData.put("tipoInput", hfragment.getInputType());
                    colData.put("indice", String.valueOf(OdtTableGenerator.indice));
                    OdtTableGenerator.indice++;
                    colData.put("inputSelezionato", hfragment.getInputSelected() + "");
                }
                if (isNextToInput) {
                    isNextToInput = false;
                }

                htables = hfragment.getHtables();
                if (htables.size() > 0) {
                    String tablesNameInternal = "";
                    HTable innerTable;
                    for (int htablesIndex = 0; htablesIndex < htables.size(); htablesIndex++) {
                        innerTable = (HTable) htables.get(htablesIndex);
                        tablesNameInternal += (innerTable.getName() + " ");
                    }
                    colData.put("nomeTabelleInterne", tablesNameInternal);
                    this.generateTables(hfragment, parameter);
                }
            }
        }

        return dataModel;
    }

    private void generateStyleDefault(OdtTableParameter parameter, Map dataModel) throws Exception {

        Configuration freemarkerConfig = parameter.getFreemarkerConfig();
        StringWriter out;

        String fileName;
        Template templateStyle;

        fileName = h2oTemplateStyleFiles.replaceAll("%s", "Table");
        templateStyle = freemarkerConfig.getTemplate(fileName);
        out = new StringWriter();
        templateStyle.process(dataModel, out);
        stylesOdt.add(out.getBuffer());
        out.flush();

        fileName = h2oTemplateStyleFiles.replaceAll("%s", "Cell");
        templateStyle = freemarkerConfig.getTemplate(fileName);
        out = new StringWriter();
        templateStyle.process(dataModel, out);
        stylesOdt.add(out.getBuffer());
        out.flush();

        fileName = h2oTemplateStyleFiles.replaceAll("%s", "CellBold");
        templateStyle = freemarkerConfig.getTemplate(fileName);
        out = new StringWriter();
        templateStyle.process(dataModel, out);
        stylesOdt.add(out.getBuffer());
        out.flush();

        fileName = h2oTemplateStyleFiles.replaceAll("%s", "Grafico");
        templateStyle = freemarkerConfig.getTemplate(fileName);
        out = new StringWriter();
        templateStyle.process(dataModel, out);
        stylesOdt.add(out.getBuffer());
        out.flush();
    }

    private void generateStyleColumns(OdtTableParameter parameter, List widthColumns) throws Exception {

        String widthColumn;
        for (int i = 0; i < widthColumns.size(); i++) {
            widthColumn = widthColumns.get(i).toString();
            if (stylesAlreadyBuilt.contains(widthColumn)) {
            } else {

                Configuration freemarkerConfig = parameter.getFreemarkerConfig();
                StringWriter out;

                String fileName;
                Template templateForm;

                fileName = h2oTemplateStyleFiles.replaceAll("%s", "Column");
                templateForm = freemarkerConfig.getTemplate(fileName);
                out = new StringWriter();
                Map dm = new HashMap();
                dm.put("larghezza", widthColumn);
                templateForm.process(dm, out);
                stylesOdt.add(out.getBuffer());
                out.flush();

                stylesAlreadyBuilt.add(widthColumn);
            }
        }
    }

    private void generateFormDefault(OdtTableParameter parameter, Map dataModel) throws Exception {

        Configuration freemarkerConfig = parameter.getFreemarkerConfig();
        StringWriter out;

        String fileName;
        Template templateForm;

        fileName = h2oTemplateFormFiles.replaceAll("%s", "Standard");
        templateForm = freemarkerConfig.getTemplate(fileName);
        out = new StringWriter();
        templateForm.process(dataModel, out);
        formsOdt.add(out.getBuffer());
        out.flush();
    }

    private OdtTableResult buildResult(Map tablesOdt, Set tablesInHead, List stylesOdt) {

        OdtTableResult result = new OdtTableResult();

        List odtTables = result.getOdtTables();

        StringBuffer tableOdt;
        StringBuffer tableOdtInHeadWhole;
        String innerTableName;
        String tableName;

        Iterator it = tablesInHead.iterator();
        while (it.hasNext()) {
            tableName = (String) it.next();
            tableOdt = (StringBuffer) tablesOdt.get(tableName);
            tableOdtInHeadWhole = new StringBuffer();
            int tablePos = 0;
            int innerTablePos = tableOdt.indexOf(h2oTableNamePrefixInternal);
            while (innerTablePos >= 0) {
                int innerTableEndPos = tableOdt.indexOf(" ", innerTablePos);
                if (innerTableEndPos > 0) {
                    tableOdtInHeadWhole.append(tableOdt.substring(tablePos, innerTablePos));
                    innerTableName = tableOdt.substring(innerTablePos, innerTableEndPos);
                    buildResultTable(tablesOdt, tablesInHead, stylesOdt,
                            tableOdtInHeadWhole,
                            innerTableName);
                    tablePos = innerTableEndPos;
                    innerTablePos = tableOdt.indexOf(h2oTableNamePrefixInternal, innerTableEndPos);
                } else {
                    log.warn("HTML GENERATOR: tabella head non generata in quanto illogicamente il nome non termina con space."
                            + "tableOdt(" + tableOdt + "),innerTablePos(" + innerTablePos + ")");
                    innerTablePos = -1;
                }
            }
            tableOdtInHeadWhole.append(tableOdt.substring(tablePos));
            odtTables.add(tableOdtInHeadWhole);
        }

        result.setOdtStyles(stylesOdt);
        result.setOdtForms(formsOdt);

        return result;
    }

    private void buildResultTable(Map tablesOdt, Set tablesInHead, List stylesOdt,
            StringBuffer tableOdtInHeadWhole,
            String innerTableName) {

        StringBuffer tableOdt = (StringBuffer) tablesOdt.get(innerTableName);
        if (tableOdt != null) {
            int tablePos = 0;
            int innerTablePos = tableOdt.indexOf(h2oTableNamePrefixInternal);
            while (innerTablePos >= 0) {
                int innerTableEndPos = tableOdt.indexOf(" ", innerTablePos);
                if (innerTableEndPos > 0) {
                    tableOdtInHeadWhole.append(tableOdt.substring(tablePos, innerTablePos));
                    String subinnerTableName = tableOdt.substring(innerTablePos, innerTableEndPos);
                    buildResultTable(tablesOdt, tablesInHead, stylesOdt,
                            tableOdtInHeadWhole,
                            subinnerTableName);
                    tablePos = innerTableEndPos;
                    innerTablePos = tableOdt.indexOf(h2oTableNamePrefixInternal, innerTableEndPos);
                } else {
                    log.warn("HTML GENERATOR: tabella inner non generata in quanto illogicamente il nome non termina con space."
                            + "tableOdt(" + tableOdt + "),innerTablePos(" + innerTablePos + ")");
                    innerTablePos = -1;
                }
            }
            tableOdtInHeadWhole.append(tableOdt.substring(tablePos));
        } else {
            log.warn("HTML GENERATOR: tabella non generata in quanto illogicamente la tabella inner non e' stata trovata."
                    + "tableName(" + innerTableName + "," + tableOdt + ")");
        }
    }

    private void init(Properties properties) throws Exception {

        if (properties == null) {
            properties = new Properties();
        }
        h2oTemplateDir = properties.getProperty("h2oTemplateDir", "../resources/").trim();
        h2oTemplateTableFile = properties.getProperty("h2oTemplateTableFile", "h2oTemplateTable.ftl").trim();
        h2oTemplateStyleFiles = properties.getProperty("h2oTemplateStyleFiles", "h2oTemplateStyle%s.ftl").trim();
        h2oTemplateFormFiles = properties.getProperty("h2oTemplateFormFiles", "h2oTemplateForm%s.ftl").trim();
        h2oTableNamePrefix = properties.getProperty("h2oTableNamePrefix", "DydotTabella").trim();
        h2oTableStyleName = properties.getProperty("h2oTableStyleName", "DydotTabellaStile").trim();
        h2oCellStyleName = properties.getProperty("h2oCellStyleName", "DydotCellaStile").trim();
        h2oCellBoldStyleName = properties.getProperty("h2oCellBoldStyleName", "DydotCellaBoldStile").trim();
        h2oTableNamePrefixInternal = properties.getProperty("h2oTableNamePrefixInternal", "$$$DYDOT_TABLE_").trim();
        h2oInputGraphics = (properties.getProperty("h2oInputGraphics", "true").trim()).equalsIgnoreCase("true");
    }

    public static void main(String[] args) throws Exception {

        String html = "";
        html += "<table width=\"100%\" border=\"1\" cellpadding=\"2\" cellspacing=\"0\">";

        html += "<tr>";
        html += "<td>";
        html += "via:<b>via Lucrezio</b>";
        html += "</td>";
        html += "</tr>";
        html += "<tr>";
        html += "<td>";
        html += "numero civico:<b>15</b>";
        html += "</td>";
        html += "</tr>";
        html += "</table>";

        if (true) {
            java.io.File xmlFile = new java.io.File("../resources/tableMultiple1.html");
            html = HTools.convertFileIntoString(xmlFile);
        }

        OdtTableGenerator generator = new OdtTableGenerator();

        OdtTableParameter parameter = new OdtTableParameter();
        OdtTableResult result;

        parameter.setTemplateDir(new File("../resources"));

        Properties properties = new Properties();
        properties.load(new FileInputStream(new File("../resources/dydot.properties")));

        result = generator.generateOdtTables(html, parameter, properties);

        System.out.println("OdtStyleString=\n" + result.getOdtStylesString());
        System.out.println("\n");
        System.out.println("OdtFormString=\n" + result.getOdtFormsString());
        System.out.println("\n");
        System.out.println("OdtTableString=\n" + result.getOdtTablesString());
    }
}
