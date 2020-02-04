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

public class HInput {

    private Object type;

    public void setType(Object type) {
        if (type instanceof String) {
            type = ((String) type).trim();
        }
        this.type = type;
    }

    public Object getType() {
        return (this.type);
    }

    public String toString() {

        String sep = ",";

        StringBuffer buffer = new StringBuffer("HInput");
        buffer.append("{");
        buffer.append("type = ");
        buffer.append(type);
        buffer.append("}");

        return buffer.toString();
    }
}
