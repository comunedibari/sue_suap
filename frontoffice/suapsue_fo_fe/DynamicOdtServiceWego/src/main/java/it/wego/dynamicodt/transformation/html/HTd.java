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

public class HTd {

    private HFragment hfragment;
    private int colspan;
    private String backgroundColor;

    public void setHFragment(HFragment hfragment) {
        this.hfragment = hfragment;
    }

    public void setColspan(int colspan) {
        this.colspan = colspan;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public HFragment getHFragment() {
        return (this.hfragment);
    }

    public int getColspan() {
        return this.colspan;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public String toString() {

        String sep = ",";

        StringBuffer buffer = new StringBuffer("HTd");
        buffer.append("{");
        buffer.append("hfragment = ");
        buffer.append(hfragment);
        buffer.append(sep);
        buffer.append("colspan = ");
        buffer.append(colspan);
        buffer.append(sep);
        buffer.append("backgroundColor = ");
        buffer.append(backgroundColor);
        buffer.append("}");

        return buffer.toString();
    }
}
