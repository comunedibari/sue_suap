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

public class GeneraCodFis  {

/*
  public void test()throws Exception{
    boolean valido=false;
    String Cognome="GRAZIANO";
    String Nome="GIUSEPPA";
    String Sex="F";
    String DataNas="19061014";
    long CodCom=7303;
    String codfis=Genera(Cognome,Nome,Sex,DataNas,CodCom);
    if (codfis.equals("GRZGPP06R54G273D")){
     // valido=true;
    }
  }
*/
  /**
   * Genera il codice fiscale di un soggetto attribuendo i parametri elencati
   * @param Cognome
   * @param Nome
   * @param Sex
   * @param DataNas
   * @param CodCom (codice comune sulla base dati)
   * @return il codice fiscale generato
   * @throws Exception
   */

  public String Genera(String Cognome, String Nome, String Sex, String DataNas, String strCodCom) throws Exception{
    String CodGen=null;
    if(Cognome==null||Cognome.trim().equalsIgnoreCase("")){
      return"";
    }
    if(Nome==null||Nome.trim().equalsIgnoreCase("")){
      return"";
    }
    if(Sex==null||Sex.trim().equalsIgnoreCase("")){
      return"";
    }
    if(DataNas==null||DataNas.trim().equalsIgnoreCase("")){
      return"";
    }
    if(strCodCom==null||strCodCom.trim().equalsIgnoreCase("")){
      return"";
    }
    Cognome = epuraStringa(Cognome);
    Nome = epuraStringa(Nome);
    CodGen=getCognome(Cognome.toUpperCase())+getNome(Nome.toUpperCase())+getData(DataNas,Sex)+strCodCom;
    char Controllo=getCarControllo(CodGen);
    CodGen+=Controllo;
    return CodGen;


  }
  //Ritorna la stringa di elaborazione del cognome
 private String getCognome(String str){
   String tmp = "";

    tmp += getConsonanti(str);
    if (tmp.length() > 2) {
      return tmp.substring(0,3);
      }
    tmp += getVocali(str);
    if (tmp.length() <3){
     tmp +="X";
     }
   return tmp.substring(0,3);

 }

 //Ritorna la stringa di elaborazione del Nome

  private String getNome(String str){
    String ris = "";
    String tmp = "";
    tmp += getConsonanti(str);
    if (tmp.length() > 3) {
       ris += tmp.charAt(0);
       ris += tmp.charAt(2);
       ris += tmp.charAt(3);
       return ris;
       }
    if (tmp.length() == 3) {
      return tmp;
      }
    tmp += getVocali(str);
    if (tmp.length()<3){
      tmp +="X";
        }
   return tmp.substring(0,3);

  }

//Ritorna la stringa relativa alla data e al sesso.
 private String getData(String Data,String Sesso) {
   String risultato="";
   String gg=Data.substring(6,8);
   String mm=Data.substring(4,6);
   String yy=Data.substring(2,4);
   int ggint=0;
   risultato = "";
   risultato += yy;
   risultato += letteraMese(mm);
   if (Sesso.equals("M")) {
          risultato += gg;
     }
   else {
     ggint = Integer.parseInt(gg);
     ggint+=40;
     gg=String.valueOf(ggint);
     risultato += gg;
     }
   return risultato;

 }



  //controlla se un carattere � una vocale
  private char check_carattere(char c){
                        if (c == 'A') {
                               return 0;
                             }
                             if (c == 'E') {
                               return 0;
                             }
                             if (c == 'I') {
                               return 0;
                             }
                             if (c == 'O') {
                               return 0;
                             }
                             if (c == 'U') {
                               return 0;
                             }
                             if (c == ' '){
                               return 0;
                             }
                             return c;

  }

  //ritorna l'insieme di consonanti
  private String getConsonanti(String str){
    String risultato = "";
    char c;
    for (int i = 0 ; i < str.length(); i++) {
                               c = check_carattere(str.charAt(i));
                               if (c != 0) {
                                 risultato += c;
                               }
                             }
    return risultato;

  }

  //ritorna l'insieme di vocali
  private String getVocali(String str){
    String risultato = "";
    char c;
    char tmp;
   for (int i = 0; i < str.length(); i++) {
    c = str.charAt(i);
    tmp = check_carattere(str.charAt(i));
    if (tmp == 0 && str.charAt(i)!=' ') {
      risultato += c;
      }
    }
    return risultato;

   }

//restituisce il carattere di controllo

 private char getCarControllo(String strCf){
   String str=strCf.toUpperCase();
   char[] Carattere = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
                            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

   int som=0,res=0;
   char tmp;
   som=check_dispari(str.charAt(14));
   for (int i=0;i<14;i=i+2){
   som=som+ check_dispari(str.charAt(i))+check_pari(str.charAt(i+1));
   }
   res=som%26;
   tmp=Carattere[res];
   return tmp;

 }

 // controlla i dispari
 private int check_dispari(char c){
   if (c == 'A') {
                                return 1;
                              }
                              if (c == 'B') {
                                return 0;
                              }
                              if (c == 'C') {
                                return 5;
                              }
                              if (c == 'D') {
                                return 7;
                              }
                              if (c == 'E') {
                                return 9;
                              }
                              if (c == 'F') {
                                return 13;
                              }
                              if (c == 'G') {
                                return 15;
                              }
                              if (c == 'H') {
                                return 17;
                              }
                              if (c == 'I') {
                                return 19;
                              }
                              if (c == 'J') {
                                return 21;
                              }
                              if (c == 'K') {
                                return 2;
                              }
                              if (c == 'L') {
                                return 4;
                              }
                              if (c == 'M') {
                                return 18;
                              }
                              if (c == 'N') {
                                return 20;
                              }
                              if (c == 'O') {
                                return 11;
                              }
                              if (c == 'P') {
                                return 3;
                              }
                              if (c == 'Q') {
                                return 6;
                              }
                              if (c == 'R') {
                                return 8;
                              }
                              if (c == 'S') {
                                return 12;
                              }
                              if (c == 'T') {
                                return 14;
                              }
                              if (c == 'U') {
                                return 16;
                              }
                              if (c == 'V') {
                                return 10;
                              }
                              if (c == 'W') {
                                return 22;
                              }
                              if (c == 'X') {
                                return 25;
                              }
                              if (c == 'Y') {
                                return 24;
                              }
                              if (c == 'Z') {
                                return 23;
                              }
                              if (c == '0') {
                                return 1;
                              }
                              if (c == '1') {
                                return 0;
                              }
                              if (c == '2') {
                                return 5;
                              }
                              if (c == '3') {
                                return 7;
                              }
                              if (c == '4') {
                                return 9;
                              }
                              if (c == '5') {
                                return 13;
                              }
                              if (c == '6') {
                                return 15;
                              }
                              if (c == '7') {
                                return 17;
                              }
                              if (c == '8') {
                                return 19;
                              }
                              if (c == '9') {
                                return 21;
                              }
                              return 0;

 }

 //Controlla le lettere pari
 private int check_pari(char c){
   if (c == 'A') {
                                return 0;
                              }
                              if (c == 'B') {
                                return 1;
                              }
                              if (c == 'C') {
                                return 2;
                              }
                              if (c == 'D') {
                                return 3;
                              }
                              if (c == 'E') {
                                return 4;
                              }
                              if (c == 'F') {
                                return 5;
                              }
                              if (c == 'G') {
                                return 6;
                              }
                              if (c == 'H') {
                                return 7;
                              }
                              if (c == 'I') {
                                return 8;
                              }
                              if (c == 'J') {
                                return 9;
                              }
                              if (c == 'K') {
                                return 10;
                              }
                              if (c == 'L') {
                                return 11;
                              }
                              if (c == 'M') {
                                return 12;
                              }
                              if (c == 'N') {
                                return 13;
                              }
                              if (c == 'O') {
                                return 14;
                              }
                              if (c == 'P') {
                                return 15;
                              }
                              if (c == 'Q') {
                                return 16;
                              }
                              if (c == 'R') {
                                return 17;
                              }
                              if (c == 'S') {
                                return 18;
                              }
                              if (c == 'T') {
                                return 19;
                              }
                              if (c == 'U') {
                                return 20;
                              }
                              if (c == 'V') {
                                return 21;
                              }
                              if (c == 'W') {
                                return 22;
                              }
                              if (c == 'X') {
                                return 23;
                              }
                              if (c == 'Y') {
                                return 24;
                              }
                              if (c == 'Z') {
                                return 25;
                              }
                              if (c == '0') {
                                return 0;
                              }
                              if (c == '1') {
                                return 1;
                              }
                              if (c == '2') {
                                return 2;
                              }
                              if (c == '3') {
                                return 3;
                              }
                              if (c == '4') {
                                return 4;
                              }
                              if (c == '5') {
                                return 5;
                              }
                              if (c == '6') {
                                return 6;
                              }
                              if (c == '7') {
                                return 7;
                              }
                              if (c == '8') {
                                return 8;
                              }
                              if (c == '9') {
                                return 9;
                              }
                             return 0;

 }

//controllo omocodia
 private char check_omocodia(int i){
   switch (i) {
                                case 0 :
                                  return 'L';
                                case 1 :
                                  return 'M';
                                case 2 :
                                  return 'N';
                                case 3 :
                                  return 'P';
                                case 4 :
                                  return 'Q';
                                case 5 :
                                  return 'R';
                                case 6 :
                                  return 'S';
                                case 7 :
                                  return 'T';
                                case 8 :
                                  return 'U';
                                case 9 :
                                  return 'V';
                                default :
                                 return 0;
                             }

 }


private String letteraMese(String mm){
 String res=null;
 if (mm.equals("01"))
  return "A";
 if (mm.equals("02"))
   return "B";
 if (mm.equals("03"))
  return "C";
 if (mm.equals("04"))
  return "D";
 if (mm.equals("05"))
  return "E";
 if (mm.equals("06"))
  return "H";
 if (mm.equals("07"))
  return "L";
 if (mm.equals("08"))
  return "M";
 if (mm.equals("09"))
  return "P";
 if (mm.equals("10"))
  return "R";
 if (mm.equals("11"))
  return "S";
 if (mm.equals("12"))
 return "T";
 return res;
}

  /**
   * Epura la stringa passata in input da tutti i caratteri strani.
   * Inoltre converte i caratteri accentati nei corrispondenti non accentati
   *
   * @param str
   * @return
   */
private String epuraStringa(String str) {

   str.toUpperCase();
   int i = 0;
   int j = 0;

   // Trova in str tutti i caratteri definiti in car e li sostituisce
   // con il corrispondente carattere in subCar
   String car    = "��������������������������������������������������������������";
   String subCar = "ZSZYCYAAAAAACEEEEIIIIDNOOOOOOUUUUYAAAAAACEEEEIIIIONOOOOOUUUUYY";
   for (i = 0; i < str.length(); i++) {
     j = car.indexOf(str.charAt(i));
     if ( j >= 0 ) {
       str = str.replace(str.charAt(i), subCar.charAt(j));
     }
   }

   // Elimina da str tutti i caratteri definiti in delCar
   String delCar = "'�����-_\\/";
   i = 0;
   while ( i < str.length() ) {
     j = delCar.indexOf(str.charAt(i));
     if ( j >= 0 ) {
       str = str.substring(0, i) + str.substring(i+1);
     }
     i++;
   }

  return str;
}



}
