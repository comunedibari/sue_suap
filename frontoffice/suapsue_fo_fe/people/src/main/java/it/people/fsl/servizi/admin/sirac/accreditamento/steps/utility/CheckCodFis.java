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


public class CheckCodFis {
  // Permette di verificare la correttezza formale di un codice fiscale
  // Non ne garantisce l'esistenza
  public static boolean isValid(String CodFis) {
       boolean check=false;
       CodFis=CodFis.toUpperCase();
       
       if (CodFis.length() == 16){
          /*
           * creazione della matrice con i caratteri dell'alfabeto
           */
          char[] Carattere = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
                         'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                         'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0',
                         '1', '2', '3', '4', '5', '6', '7', '8', '9'};
          /*
           * creazione della matrice con i valori attribuiti ai caratteri
           * dispari, corrispondenti alla matrice di caratteri
           */
          int[] ValoriDispari = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4,
                            18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22,
                            25, 24, 23, 1, 0, 5, 7, 9, 13, 15, 17, 19,
                            21};
          /*
           * creazione della matrice con i valori attribuiti ai caratteri
           * pari, corrispondenti alla matrice di caratteri
           */
          int[] ValoriPari = new int[36];
          for (int i = 0; i < 26; i++){
             ValoriPari[i] = i;
          }
          for (int i = 26; i < 36; i++){
             ValoriPari[i] = i - 26;
          }
          // conversione della stringa da esaminare ad una matrice di caratteri
          char[] caratteriCF = CodFis.toCharArray();
          int valore = 0;
          for (int i = 0; i < caratteriCF.length - 1; i++){
             /*
              * somma delle posizioni pari in base ai valori
              * corrispondenti contenuti nell'array ValoriPari
              * (tranne l'ultimo carattere che � quello di controllo)
              */
             if ((i+1) % 2 == 0){
                for (int j = 0; j < Carattere.length; j++){

                   if (caratteriCF[i] == Carattere[j]){
                      valore += ValoriPari[j];
                   }
                }
                /*
                 * somma delle posizioni dispari in base ai valori
                 * corrispondenti contenuti nell'array ValoriDispari
                 */
             }else{
                for (int j = 0; j < Carattere.length; j++){
                   if (caratteriCF[i] == Carattere[j]){
                      valore += ValoriDispari[j];
                   }
                }
             }
          }
          /*
           * ottenimento del resto della divisione per 26 e
           * valutazione del carattere di controllo (ultimo carattere)
           */
          valore %= 26;
          for (int i = 0; i < 26; i++){
             /*
              * verifica che il valore dell'ultimo carattere corrisponda
              * al valore ottenuto attraverso l'algoritmo di somma precedente
              */
             if (caratteriCF[caratteriCF.length - 1] == Carattere[i]){
                if (valore == i){
                  return true;
                }else{
                   return false;
                }
             }
          }
         return false;

       }else{
          return false;
       }

    }



   }


