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

public class ResRegBean
    implements java.io.Serializable {
  private java.lang.String coda;       // Da non valorizzare
  private java.lang.String codiceFiscale;   // codice fiscale dell'utente
  private java.lang.String esito;      // Esito dell'operazione= "OK", "FAILED"
  private java.lang.String isOm;       // Da non valorizzare
  private java.lang.String messaggio;  // Messaggio descrittivo dell'esito dell'operazione o dell'errore
  private java.lang.String timestamp;
  private java.lang.String userId;    // userID dell'utente interessato (deve essere uguale al codice fiscale)
  private java.lang.String errorCode;     // codice di errore

  public ResRegBean() {
  }

  public java.lang.String getCoda() {
    return coda;
  }

  public void setCoda(java.lang.String coda) {
    this.coda = coda;
  }

  public java.lang.String getCodiceFiscale() {
    return codiceFiscale;
  }

  public void setCodiceFiscale(java.lang.String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  public java.lang.String getEsito() {
    return esito;
  }

  public void setEsito(java.lang.String esito) {
    this.esito = esito;
  }

  public java.lang.String getIsOm() {
    return isOm;
  }

  public void setIsOm(java.lang.String isOm) {
    this.isOm = isOm;
  }

  public java.lang.String getMessaggio() {
    return messaggio;
  }

  public void setMessaggio(java.lang.String messaggio) {
    this.messaggio = messaggio;
  }

  public java.lang.String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(java.lang.String timestamp) {
    this.timestamp = timestamp;
  }

  public java.lang.String getUserId() {
    return userId;
  }

  public void setUserId(java.lang.String userId) {
    this.userId = userId;
  }

  public java.lang.String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(java.lang.String errorCode) {
    this.errorCode = errorCode;
  }


}
