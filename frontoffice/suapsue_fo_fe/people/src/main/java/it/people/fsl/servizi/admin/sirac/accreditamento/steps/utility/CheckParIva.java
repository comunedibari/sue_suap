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
package it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility;

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
