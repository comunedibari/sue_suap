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

import java.io.File;

import freemarker.template.*;

public class OdtTableParameter {

    private int tableIndex;
    private String tableName;
    private File templateDir;
    private Configuration freemarkerConfig;
    private String outputType;
    private boolean stylesDefaultAlreadyGenerated;

    public OdtTableParameter() {
        tableIndex = 0;
        tableName = "";
        templateDir = null;
        freemarkerConfig = null;
    }

    public void setTableIndex(int tableIndex) {
        this.tableIndex = tableIndex;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setTemplateDir(File templateDir) {
        this.templateDir = templateDir;
    }

    public void setStylesDefaultAlreadyGenerated(boolean stylesDefaultAlreadyGenerated) {
        this.stylesDefaultAlreadyGenerated = stylesDefaultAlreadyGenerated;
    }

    public int getTableIndex() {
        return this.tableIndex;
    }

    public String getTableName() {
        return this.tableName;
    }

    public File getTemplateDir() {
        return this.templateDir;
    }

    public boolean getStylesDefaultAlreadyGenerated() {
        return this.stylesDefaultAlreadyGenerated;
    }

    public Configuration getFreemarkerConfig() throws Exception {
        if (this.freemarkerConfig == null) {
            this.freemarkerConfig = new Configuration();
            freemarkerConfig.setDirectoryForTemplateLoading(this.templateDir);
            freemarkerConfig.setObjectWrapper(new DefaultObjectWrapper());
        }
        return this.freemarkerConfig;
    }

    public String toString() {

        String sep = ",";

        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append("tableIndex = ");
        buffer.append(tableIndex);
        buffer.append(sep);
        buffer.append("tableName = ");
        buffer.append(tableName);
        buffer.append(sep);
        buffer.append("templateDir = ");
        buffer.append(templateDir);
        buffer.append(sep);
        buffer.append("stylesDefaultAlreadyGenerated = ");
        buffer.append(stylesDefaultAlreadyGenerated);
        buffer.append(sep);
        buffer.append("freemarkerConfig = ");
        buffer.append(freemarkerConfig);
        buffer.append("}");

        return buffer.toString();
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }
}
