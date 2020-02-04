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
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
/*

  Licenza:	    Licenza Progetto PEOPLE
  Fornitore:    CEFRIEL
  Autori:       M. Pianciamore, P. Selvini

  Questo codice sorgente � protetto dalla licenza valida nell'ambito del
  progetto PEOPLE. La propriet� intellettuale di questo codice � e rester�
  esclusiva di "CEFRIEL Societ� Consortile a Responsabilit� Limitata" con
  sede legale in via Renato Fucini 2, 20133 Milano (MI).

  Disclaimer:

  COVERED CODE IS PROVIDED UNDER THIS LICENSE ON AN "AS IS" BASIS, WITHOUT
  WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, WITHOUT 
  LIMITATION, WARRANTIES THAT THE COVERED CODE IS FREE OF DEFECTS, MERCHANTABLE,
  FIT FOR A PARTICULAR PURPOSE OR NON-INFRINGING. THE ENTIRE RISK AS TO THE
  QUALITY AND PERFORMANCE OF THE COVERED CODE IS WITH YOU. SHOULD ANY COVERED
  CODE PROVE DEFECTIVE IN ANY RESPECT, YOU (NOT THE INITIAL DEVELOPER OR ANY
  OTHER CONTRIBUTOR) ASSUME THE COST OF ANY NECESSARY SERVICING, REPAIR OR
  CORRECTION.
    
*/
package it.idp.people.admin.sqlmap.util;

/**
 * <p>Title: Class CheckParIva</p>
 * <p>Description: classe per il controllo formale della partita iva</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Codice originariamente fornito da Engineering  al progetto People nell'ambito dei servizi fiscali</p>
 * @author Vincenzo Tresoldi
 * @version 1.0
 */

public class CheckParIva {
  /**
   * Ritorna true se la partita iva � formalmente corretta
   * @Long param piva
   * @return
   */
  public static boolean isValid(Long piva){
    return isValid(piva.toString());
  }

  /**
   * Ritorna true se la partita iva � formalmente corretta
   * @String param piva
   * @return
   */
  public static boolean isValid(String piva){
    String strZero="00000000000";
    int strlen=piva.length();
    if (strlen<strZero.length()){
	    strZero=strZero.substring(strlen);
	    piva=strZero+piva;
    }
    for (int i=0;i<piva.length();i++){
      if(!(Character.isDigit(piva.charAt(i)))){
        return false;
      }
    }
    if (piva.length() == 11){
            int i = 0;
            int tot = 0;
            /*
             * somma delle cifre dispari, tranne l'ultima
             */
            while (i < 10){
               tot += Integer.parseInt(piva.substring(i,i+1));
               i += 2;
            }
            int pari = 0;
            i = 1;
            /*
             * somma (al totale delle cifre dispari) delle cifre pari
             * moltiplicate per 2
             * se la cifra moltiplicata � maggiore o uguale a 10
             * si sommano separatamente
             * le decine e le unit�
             * esempi
             * 3: rimane 3
             * 13: si somma 1 + 4 = 5
             * 10: si somma 1 + 0 = 1
             */
            while (i < 11){
               pari = (Integer.parseInt(piva.substring(i,i+1))) * 2;
               pari = (pari / 10) + (pari % 10);
               tot += pari;
               i += 2;
            }
            /*
             * ultima cifra della partita iva
             * cifra di controllo
             */
            int control = Integer.parseInt(piva.substring(10,11));
            /*
             * verifica:
             * (resto(somma) = 0 E cifra di controllo = 0)
             * oppure
             * (10 - resto(somma) = cifra di controllo)
             */
            if (((tot % 10) == 0 && (control == 0))
               || ((10 - (tot % 10)) == control)){
               return true;
            }else{
               return false;
            }
         }else{
            return false;
         }

  }
}
