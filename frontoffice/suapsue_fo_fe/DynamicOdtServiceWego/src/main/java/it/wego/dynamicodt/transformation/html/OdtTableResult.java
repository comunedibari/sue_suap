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

import java.util.ArrayList;
import java.util.List;

public class OdtTableResult {

    private List odtStyles;  //Stringhe ODT degli styles (StringBuffer)
    private List odtTables;  //Stringhe ODT delle tabelle (StringBuffer)
    private List odtForms;   //Stringhe ODT delle tabelle (StringBuffer)

    public OdtTableResult() {
        odtStyles = new ArrayList();
        odtTables = new ArrayList();
        odtForms = new ArrayList();
    }

    public void setOdtStyles(List odtStyles) {
        this.odtStyles = odtStyles;
    }

    public void setOdtTables(List odtTables) {
        this.odtTables = odtTables;
    }

    public void setOdtForms(List odtForms) {
        this.odtForms = odtForms;
    }

    public List getOdtStyles() {
        return this.odtStyles;
    }

    public List getOdtTables() {
        return this.odtTables;
    }

    public List getOdtForms() {
        return this.odtForms;
    }

    public String getOdtStylesString() {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < odtStyles.size(); i++) {
            s.append(odtStyles.get(i));
        }
        return s.toString();
    }

    public String getOdtTablesString() {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < odtTables.size(); i++) {
            s.append(odtTables.get(i));
        }
        return s.toString();
    }

    public String getOdtFormsString() {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < odtForms.size(); i++) {
            s.append(odtForms.get(i));
        }
        return s.toString();
    }

    public String toString() {

        String sep = ",";

        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append("\nodtStyles = \n");
        buffer.append(odtStyles);
        buffer.append(sep);
        buffer.append("\nodtTables = \n");
        buffer.append(odtTables);
        buffer.append(sep);
        buffer.append("\nodtForms = \n");
        buffer.append(odtForms);
        buffer.append("}");

        return buffer.toString();
    }
}
