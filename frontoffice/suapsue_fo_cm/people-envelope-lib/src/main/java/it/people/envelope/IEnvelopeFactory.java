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

import it.people.envelope.exceptions.InvalidEnvelopeException;
import it.people.envelope.exceptions.NotAnEnvelopeException;


/**
 * Interfaccia per la creazione di una busta di richiesta o risposta partendo da un bean IRequestEnvelope o IResponseEnvelope<br>
 * e per la creazione di un IRequestEnvelope o IResponseEnvelope partendo da una busta di richiesta o di risposta.<br> 
 *
 * @author M. Pianciamore
 *
 */
public interface IEnvelopeFactory {

  /** 
   *   
   * Crea un envelope bean di richiesta o di risposta partendo dal tracciato xml della busta
   * Il tipo di bean creata dipende dal tipo del tracciato xml:<br>
   * <ul>
   * <li>Nel caso di un envelope di richiesta verr� creato un bean di richiesta che implementa l'interfaccia IRequestEnvelope.<br>
   * Si assume che il tracciato xml della richiesta vera e propria sia inserito nella busta (nell'elemento Contenuto) in formato Base64.<br>
   * <li>Nel caso di un envelope risposta verr� creato un bean che implementa l'interfaccia IResponseEnvelope.<br>
   * </ul>
   * Il tracciato xml della busta deve essere conforme alla build della modellazione e dell'envelope indicati nella descrizione della classe
   * factory concreta istanziata.<br>
   * @param envelopeXmlText Stringa contenente il tracciato xml della busta di richiesta o di risposta
   * @return Envelope bean di richiesta o risposta
   * @throws Exception se il tracciato xml nella stringa passata come parametro non individua una busta di richiesta o di risposta
   */
  public IEnvelope createEnvelopeBean(String envelopeXmlText) throws InvalidEnvelopeException, NotAnEnvelopeException, Exception;
  /**
   * Crea un envelope xml di richiesta o di risposta partendo da un bean IEnvelope
   * Il tipo di busta creata dipende dall'interfaccia implementata dal bean:<br>
   * <ul>
   * <li>Nel caso di un bean di richiesta (oggetto che implementa l'interfaccia IRequestEnvelope)<br>
   *      verr� creata una busta di richiesta con il tracciato xml della richiesta vera e propria inserito nella busta nell'elemento contenuto in formato Base64.</li>
   * <li>Nel caso di un bean di risposta (oggetto che implementa l'interfaccia IResponseEnvelope) verr� creata una busta di risposta</li><br>
   * </ul>     
   * L'envelope creato � conforme alla build della modellazione e dell'envelope indicati nella descrizione della classe factory concreta utilizzata.<br>
   * 
   * @param envelopeBean - Envelope bean da cui partire per la costruzione dell'envelope xml
   * @return Envelope xml costruito partendo dall'envelope bean passato come parametro 
   * @throws Exception - Se si verifica un'eccezione durante la costruzione dell'envelope xml
   */
  public String createEnvelopeXmlText(IEnvelope envelopeBean) throws Exception;
}
