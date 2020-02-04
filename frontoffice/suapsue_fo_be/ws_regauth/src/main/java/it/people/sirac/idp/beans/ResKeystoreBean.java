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

package it.people.sirac.idp.beans;

public class ResKeystoreBean implements java.io.Serializable {

  private java.lang.String esito;      // Esito dell'operazione= "OK", "FAILED"
  private java.lang.String timestamp;  //
  private java.lang.String codiceFiscale;   // Codice fiscale dell'utente
  private java.lang.String errorCode;    // codice di errore
  private java.lang.String messaggio;  // Messaggio descrittivo dell'errore
  private java.lang.String pin; // pin dell'utente
  private java.lang.String keystoreB64; // keystore dell'utente in formato Base64

  public ResKeystoreBean() {
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getCodiceFiscale() {
    return codiceFiscale;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public String getEsito() {
    return esito;
  }
  
  public String getPin() {
    return pin;
  }

  public String getMessaggio() {
    return messaggio;
  }
  
  public String getKeystoreB64() {
    return keystoreB64;
  }

  public void setKeystoreB64(String keystoreB64) {
    this.keystoreB64 = keystoreB64;
  }

  public void setMessaggio(String messaggio) {
    this.messaggio = messaggio;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public void setEsito(String esito) {
    this.esito = esito;
  }

  public void setPin(java.lang.String pin) {
    this.pin = pin;
  }
  
  /**
   * 
   * @return 
   * @author 
   */
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("ResKeystoreBean[");
    buffer.append(" \ncodiceFiscale = ").append(codiceFiscale);
    buffer.append(" \nerrorCode = ").append(errorCode);
    buffer.append(" \nesito = ").append(esito);
    buffer.append(" \nmessaggio = ").append(messaggio);
    buffer.append(" \ntimestamp = ").append(timestamp);
    buffer.append(" \npin = ").append(pin);
    buffer.append("]");
    return buffer.toString();
  }



}
