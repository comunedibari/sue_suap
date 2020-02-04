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

  Licenza:      Licenza Progetto PEOPLE
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
package it.people.envelope;

import it.people.envelope.beans.ContenutoBusta;
import it.people.envelope.beans.IdentificatoreDiRichiesta;
import it.people.envelope.beans.InformazioniDiValidazione;

/**
 * Interfaccia per la gestione di una busta di risposta 
 *
 */
public interface IResponseEnvelope extends IEnvelope {

  /**
   * Restituisce il contenuto della busta di risposta.
   * Se la busta non ha contenuto restituisce null.
   * @return Bean ContenutoBusta con il contenuto della busta di risposta. 
   * @see it.people.envelope.beans.ContenutoBusta
   */
  public ContenutoBusta getContenuto();
  
  /**
   * Restituisce il codice identificativo del servizio associato alla busta di risposta. 
   * @return codice identificativo del servizio associato alla busta di risposta.
   */
  public String getNomeServizio();
  
  /**
   * Restituisce le informazioni di validazione associate alla busta di risposta.
   * @return informazioni di validazione associate alla busta di risposta.
   * @see it.people.envelope.beans.InformazioniDiValidazione
   */
  public InformazioniDiValidazione getInformazioniDiValidazione();
  
  /**
   * Restituisce l'identificatore della richiesta di servizio associata alla busta di risposta.<br>
   * La struttura dell'identificatore � quella definita dalla modellazione People.
   * @return identificatore della richiesta di servizio associata alla busta di risposta.
   * @see it.people.envelope.beans.IdentificatoreDiRichiesta
   */
  public IdentificatoreDiRichiesta getIdentificatoreDiRichiesta();
  
  
  /**
   * Imposta il contenuto della busta di risposta.<br>
   * E' possibile non valorizzare il campo contenuto del bean (no xml e no eccezione) 
   * in una busta di richiesta o di risposta.<br> 
   * Al momento della creazione del contenuto dell'envelope bean basta specificare null come contenuto nella setContenuto().<br>
   * Il bean con un contenuto null pu� essere parsato ottenendo come risoltato una busta xml con elemento Contenuto vuoto.
   * L'xml creato non valida rispetto allo schema della busta (visto che il contenuto non � opzionale), <br>
   * ma pu� essere parsato ottenendo un bean IEnvelope completo di tutte le altre parti.
   * Per aggiungere un contenuto basta crearlo, aggiungerlo al bean invocando il metodo setContenuto() e ricreare l'xml.
   * @param contenuto - contenuto della busta di risposta.
   * @see it.people.envelope.beans.ContenutoBusta
   */
  public void setContenuto(ContenutoBusta contenuto);
  
  /**
   * Imposta il codice identificativo del servizio associato alla busta di risposta. 
   * @param nomeServizio - il codice identificativo del servizio associato alla busta di risposta. 
   */
  public void setNomeServizio(String nomeServizio);
  
  /**
   * Imposta le informazioni di validazione associate alla busta di risposta (Opzionale).
   * @param informazionidivalidazione - informazioni di validazione associate alla busta di risposta.
   * @see it.people.envelope.beans.InformazioniDiValidazione
   */
  public void setInformazioniDiValidazione(InformazioniDiValidazione informazionidivalidazione);
  
  /**
   * Imposta l'identificatore della richiesta di servizio associata alla busta di risposta.<br>
   * La struttura dell'identificatore � quella definita dalla modellazione People.
   * @param idRichiesta - identificatore della richiesta di servizio associata alla busta di risposta.
   * @see it.people.envelope.beans.IdentificatoreDiRichiesta
   */
  public void setIdentificatoreDiRichiesta(IdentificatoreDiRichiesta idRichiesta);

}
