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
package it.people.dbm.utility;

import java.util.Comparator;

import net.sf.json.JSONObject;

/**
 *
 * @author Piergiorgio
 */
public class Comparatore implements Comparator<JSONObject> {

    @Override
    public int compare(JSONObject c1, JSONObject c2) {
        if (((String) c1.get("nome")).compareTo((String) c2.get("nome")) == 0) {
            if (((Integer) c1.get("riga"))==((Integer) c2.get("riga"))) {
                if(((Integer) c1.get("posizione"))>((Integer) c2.get("posizione"))) {
                    return 1;
                } else {
                    return -1;
                }
            } else {
                if (((Integer) c1.get("riga"))>((Integer) c2.get("riga"))) {
                    return 1;
                } else {
                    return -1;
                }
            }
        } else {
            return ((String) c1.get("nome")).compareTo((String) c2.get("nome"));
        }
    }
}
