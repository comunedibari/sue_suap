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

import it.people.envelope.beans.*;

/**
 * Interfaccia per la gestione di una busta di richiesta 
 * Revisioni:
 * 04/06/2007 - Aggiunti metodi getSoggettoDelegato() e setSoggettoDelegato()
 *
 */
public interface IRequestEnvelope extends IEnvelope {

  /**
   * Restituisce il contenuto della busta di richiesta.
   * Se la busta non ha contenuto restituisce null.
   * @return Bean ContenutoBusta con il contenuto della busta di richiesta. 
   * @see it.people.envelope.beans.ContenutoBusta
   */
  public ContenutoBusta getContenuto();
  
  /**
   * Restituisce il codice dell'amministrazione destinataria della richiesta. 
   * @return Codice Amministrazione destinataria della richiesta.
   */
  public String getCodiceDestinatario();
  
  
  /**
   * Restituisce il codice identificativo del servizio associato alla busta di richiesta. 
   * @return codice identificativo del servizio associato alla busta di richiesta.
   */
  public String getNomeServizio();
  
  /**
   * Restituisce il codice identificativo del servizio all'interno del quale viene effettuata la richiesta.<br>
   * Esempio: per identificare una visura catastale effettuata nell'ambito di una istanza di rimborso ICI<br>
   * il nomeServizio sar� ad esempio 'visuracatastale', mentre il contestoServizio sar� 'istanzadirimborsoici'. 
   * @return codice identificativo del contesto del servizio associato alla richiesta.
   */
  public String getContestoServizio();
  
  /**
   * Restituisce le informazioni di validazione associate alla busta di richiesta.
   * @return informazioni di validazione associate alla busta di richiesta.
   * @see it.people.envelope.beans.InformazioniDiValidazione
   */
  public InformazioniDiValidazione getInformazioniDiValidazione();
  
  /**
   * Restituisce l'identificatore della richiesta di servizio associata alla busta.<br>
   * La struttura dell'identificatore � quella definita dalla modellazione People.
   * @return identificatore della richiesta di servizio associata alla busta.
   * @see it.people.envelope.beans.IdentificatoreDiRichiesta
   */
  public IdentificatoreDiRichiesta getIdentificatoreDiRichiesta();
  
  /**
   * Restituisce le credenziali del mittente della richiesta.
   * @return credenziali del mittente della busta di richiesta.
   * @see it.people.envelope.beans.CredenzialiUtenteCertificate
   */
  public CredenzialiUtenteCertificate getMittente();
  
  /**
   * Fornisce i Recapiti del richiedente 
   * @return Array contenente gli oggetti Recapito del richiedente indicati nella busta di richiesta
   * @see it.people.envelope.beans.Recapito
   */
  public Recapito[] getRecapiti();
  
  /**
   * Restituisce gli estremi del richiedente (Estremi persona fisica: Nome, Cognome, Codice Fiscale, Domicilio Elettronico) indicato nella busta di richiesta
   * @return EstremiRichiedente
   * @see it.people.envelope.beans.EstremiTitolare
   * @see it.people.envelope.beans.EstremiPersonaFisica
   */
  public EstremiRichiedente getEstremiRichiedente();
  
  /**
   * Restituisce gli estremi del titolare (EstremiPersonaFisica o EstremiPersonaGiuridica) indicato nella busta di richiesta
   * @return EstremiTitolare
   * @see it.people.envelope.beans.EstremiRichiedente
   * @see it.people.envelope.beans.EstremiPersonaFisica
   * @see it.people.envelope.beans.EstremiPersonaGiuridica
   */
  public EstremiTitolare getEstremiTitolare();

  /**
   * Restituisce gli estremi dell'operatore (Estremi persona fisica: Nome, Cognome, Codice Fiscale, Domicilio Elettronico) indicato nella busta di richiesta
   * @return EstremiOperatore
   * @see it.people.envelope.beans.EstremiOperatore
   * @see it.people.envelope.beans.EstremiPersonaFisica
   */
  public EstremiOperatore getEstremiOperatore();
  
  /**
   * Restituisce gli estremi del soggetto delegato (EstremiPersonaFisica o EstremiPersonaGiuridica) indicato nella busta di richiesta
   * Aggiunto il 4/06/2007
   * @return EstremiTitolare
   * @see it.people.envelope.beans.EstremiRichiedente
   * @see it.people.envelope.beans.EstremiPersonaFisica
   * @see it.people.envelope.beans.EstremiPersonaGiuridica
   */
  public EstremiTitolare getEstremiSoggettoDelegato();
  //public IdentificatoreDiProtocollo getProtocolloInUscita();
  
  /**
   * Restituisce il flag boolean che indica se la verifica dell'esistenza di una delega per il richiedente da parte del titolare 
   * deve essere saltata. Aggiunto il 16/11/2007
   * @return flag boolean
   */
  public boolean getForceSkipCheckDelega();
  
  /**
   * Imposta il contenuto della busta di richiesta.<br>
   * E' possibile non valorizzare il campo contenuto del bean (no xml e no eccezione) 
   * in una busta di richiesta o di risposta.<br> 
   * Al momento della creazione del contenuto dell'envelope bean basta specificare null come contenuto nella setContenuto().<br>
   * Il bean con un contenuto null pu� essere parsato ottenendo come risoltato una busta xml con elemento Contenuto vuoto.
   * L'xml creato non valida rispetto allo schema della busta (visto che il contenuto non � opzionale), <br>
   * ma pu� essere parsato ottenendo un bean IEnvelope completo di tutte le altre parti.
   * Per aggiungere un contenuto basta crearlo, aggiungerlo al bean invocando il metodo setContenuto() e ricreare l'xml.
   * @param contenuto - contenuto della busta di richiesta.
   * @see it.people.envelope.beans.ContenutoBusta
   */
  public void setContenuto(ContenutoBusta contenuto);
  
  /**
   * Imposta il codice dell'amministrazione destinataria della busta di richiesta.
   * @param codiceDestinatario - codice dell'amministrazione destinataria della busta di richiesta
   */
  public void setCodiceDestinatario(String codiceDestinatario);
  
  /**
   * Imposta il codice identificativo del servizio associato alla busta di richiesta. 
   * @param nomeServizio - il codice identificativo del servizio associato alla busta di richiesta. 
   */
  public void setNomeServizio(String nomeServizio);
  
  /**
   * Imposta il codice identificativo del servizio all'interno del quale viene effettuata la richiesta.<br>
   * Esempio: per identificare una visura catastale effettuata nell'ambito di una istanza di rimborso ICI<br>
   * il nomeServizio sar� ad esempio 'visuracatastale', mentre il contestoServizio sar� 'istanzadirimborsoici'. 
   * @param contestoServizio - codice identificativo del servizio all'interno del quale viene effettuata la richiesta.
   */
  public void setContestoServizio(String contestoServizio);
  
  /**
   * Imposta le informazioni di validazione associate alla busta di richiesta (Opzionale).
   * @param informazionidivalidazione - informazioni di validazione associate alla busta di richiesta.
   * @see it.people.envelope.beans.InformazioniDiValidazione
   */
  public void setInformazioniDiValidazione(InformazioniDiValidazione informazionidivalidazione);
  
  /**
   * Imposta l'identificatore della richiesta di servizio associata alla busta.<br>
   * La struttura dell'identificatore � quella definita dalla modellazione People.
   * @param idRichiesta - identificatore della richiesta di servizio associata alla busta.
   * @see it.people.envelope.beans.IdentificatoreDiRichiesta
   */
  public void setIdentificatoreDiRichiesta(IdentificatoreDiRichiesta idRichiesta);
  
  /**
   * Imposta le credenziali del mittente della richiesta.
   * @param credenzialiMittente - credenziali del mittente della richiesta.
   * @see it.people.envelope.beans.CredenzialiUtenteCertificate
   */
  public void setMittente(CredenzialiUtenteCertificate credenzialiMittente);
  
  /**
   * Imposta i Recapiti del richiedente. Deve essere specificato almeno un recapito nella busta di richiesta.
   * @param recapiti - Array contenente gli oggetti Recapito del richiedente da inserire nella busta di richiesta.
   * @see it.people.envelope.beans.Recapito
   */
  public void setRecapiti(Recapito[] recapiti);
  
  /**
   * Aggiunge un recapito del richiedente all'insieme di recapiti gi� eventualmente impostati nella busta di richiesta. 
   * @param recapito - Recapito da aggiungere
   * @see it.people.envelope.beans.Recapito
   */
  public void addRecapito(Recapito recapito);
  
  /**
   * Imposta gli estremi del richiedente associato alla busta di richiesta (Estremi persona fisica: Nome, Cognome, Codice Fiscale) indicato nella busta di richiesta
   * @param richiedente - Estremi del richiedente associato alla busta di richiesta
   * @see it.people.envelope.beans.EstremiRichiedente
   * @see it.people.envelope.beans.EstremiPersonaFisica
   */
  public void setEstremiRichiedente(EstremiRichiedente richiedente);
  /**
   * Imposta gli estremi del titolare associato alla busta di richiesta (EstremiPersonaFisica o EstremiPersonaGiuridica) indicato nella busta di richiesta
   * @param titolare - estremi del titolare associato alla busta di richiesta
   * @see it.people.envelope.beans.EstremiTitolare
   * @see it.people.envelope.beans.EstremiPersonaFisica
   * @see it.people.envelope.beans.EstremiPersonaGiuridica
   */
  public void setEstremiTitolare(EstremiTitolare titolare);
  //public void setProtocolloInUscita(IdentificatoreDiProtocollo protocolloInUscita);

  /**
   * Imposta gli estremi dell'operatore associato alla busta di richiesta (Estremi persona fisica) indicato nella busta di richiesta
   * @param operatore - estremi dell'operatore associato alla busta di richiesta
   * @see it.people.envelope.beans.EstremiOperatore
   * @see it.people.envelope.beans.EstremiPersonaFisica
   */
  public void setEstremiOperatore(EstremiOperatore operatore);
  
  /**
   * Imposta gli estremi del soggetto delegato associato alla busta di richiesta (EstremiPersonaFisica o EstremiPersonaGiuridica) indicato nella busta di richiesta
   * Aggiunto il 4/06/2007
   * @param delegato - estremi del soggetto delegato associato alla busta di richiesta
   * @see it.people.envelope.beans.EstremiTitolare
   * @see it.people.envelope.beans.EstremiPersonaFisica
   * @see it.people.envelope.beans.EstremiPersonaGiuridica
   */
  public void setEstremiSoggettoDelegato(EstremiTitolare soggettoDelegato);
  //public void setProtocolloInUscita(IdentificatoreDiProtocollo protocolloInUscita);
  
  /**
   * Imposta il flag boolean che indica se la verifica dell'esistenza di una delega per il richiedente da parte del titolare 
   * deve essere saltata. Aggiunto il 16/11/2007
   * @param forceSkipCheckDelega - flag boolean da impostare
   */
  public void setForceSkipCheckDelega(boolean forceSkipCheckDelega);
}
