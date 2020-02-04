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
 * Implementazione di una busta di risposta
 *
 * @see it.people.envelope.AbstractEnvelope
 * @see it.people.envelope.IResponseEnvelope
 */
public class ResponseEnvelope extends AbstractEnvelope implements IResponseEnvelope {
  
  protected ContenutoBusta contenutoBusta;
  protected String nomeServizio;
  protected IdentificatoreDiRichiesta identificatoreDiRichiesta;
  protected InformazioniDiValidazione informazioniDiValidazione;


  /**
   * @param contenutoBusta
   * @param nomeServizio
   * @param identificatoreDiRichiesta
   * @see it.people.envelope.beans.ContenutoBusta
   * @see it.people.envelope.beans.IdentificatoreDiRichiesta
   */
  public ResponseEnvelope(ContenutoBusta contenutoBusta, String nomeServizio, 
                          IdentificatoreDiRichiesta identificatoreDiRichiesta) {
    
    setContenuto(contenutoBusta);
    setNomeServizio(nomeServizio);
    setIdentificatoreDiRichiesta(identificatoreDiRichiesta);
  }
  
  /**
   * @param contenutoBusta
   * @param nomeServizio
   * @param identificatoreDiRichiesta
   * @param informazioniDiValidazione
   */
  public ResponseEnvelope(ContenutoBusta contenutoBusta, String nomeServizio, 
      IdentificatoreDiRichiesta identificatoreDiRichiesta,
      InformazioniDiValidazione informazioniDiValidazione) {

    this(contenutoBusta, nomeServizio, identificatoreDiRichiesta);
    setInformazioniDiValidazione(informazioniDiValidazione);
}

  /* (non-Javadoc)
   * @see it.people.envelope.IResponseEnvelope#getContenuto()
   */
  public ContenutoBusta getContenuto() {
     return contenutoBusta;
  }

  /* (non-Javadoc)
   * @see it.people.envelope.IResponseEnvelope#getIdentificatoreDiRichiesta()
   */
  public IdentificatoreDiRichiesta getIdentificatoreDiRichiesta() {
    return identificatoreDiRichiesta;
  }

  /* (non-Javadoc)
   * @see it.people.envelope.IResponseEnvelope#getInformazioniDiValidazione()
   */
  public InformazioniDiValidazione getInformazioniDiValidazione() {
    return informazioniDiValidazione;
  }

  /* (non-Javadoc)
   * @see it.people.envelope.IResponseEnvelope#getNomeServizio()
   */
  public String getNomeServizio() {
     return nomeServizio;
  }

  /* (non-Javadoc)
   * @see it.people.envelope.IResponseEnvelope#setContenuto(it.people.envelope.beans.ContenutoBusta)
   */
  public void setContenuto(ContenutoBusta contenuto) {
    this.contenutoBusta = contenuto;
  }

  /* (non-Javadoc)
   * @see it.people.envelope.IResponseEnvelope#setIdentificatoreDiRichiesta(it.people.envelope.beans.IdentificatoreDiRichiesta)
   */
  public void setIdentificatoreDiRichiesta(IdentificatoreDiRichiesta idRichiesta) {
    this.identificatoreDiRichiesta = idRichiesta;
  }

  /* (non-Javadoc)
   * @see it.people.envelope.IResponseEnvelope#setInformazioniDiValidazione(it.people.envelope.beans.InformazioniDiValidazione)
   */
  public void setInformazioniDiValidazione(InformazioniDiValidazione informazionidivalidazione) {
    this.informazioniDiValidazione = informazionidivalidazione;
  }

  /* (non-Javadoc)
   * @see it.people.envelope.IResponseEnvelope#setNomeServizio(java.lang.String)
   */
  public void setNomeServizio(String nomeServizio) {
    this.nomeServizio = nomeServizio;
  }

  /**
   * Returns <code>true</code> if this <code>ResponseEnvelope</code> is the same as the o argument.
   *
   * @return <code>true</code> if this <code>ResponseEnvelope</code> is the same as the o argument.
   */
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!super.equals(o)) {
      return false;
    }
    if (o == null) {
      return false;
    }
    if (o.getClass() != getClass()) {
      return false;
    }
    ResponseEnvelope castedObj = (ResponseEnvelope) o;
    return ((this.contenutoBusta == null
      ? castedObj.contenutoBusta == null
      : this.contenutoBusta.equals(castedObj.contenutoBusta))
      && (this.nomeServizio == null
        ? castedObj.nomeServizio == null
        : this.nomeServizio.equals(castedObj.nomeServizio))
      && (this.identificatoreDiRichiesta == null
        ? castedObj.identificatoreDiRichiesta == null
        : this.identificatoreDiRichiesta
          .equals(castedObj.identificatoreDiRichiesta)) && (this.informazioniDiValidazione == null
      ? castedObj.informazioniDiValidazione == null
      : this.informazioniDiValidazione
        .equals(castedObj.informazioniDiValidazione)));
  }

  /**
   * 
   * @return The string representation of this object
   */
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("[ResponseEnvelope:\n");
    buffer.append(" contenutoBusta: ");
    buffer.append(contenutoBusta + "\n");
    buffer.append(" nomeServizio: ");
    buffer.append(nomeServizio+ "\n");
    buffer.append(" identificatoreDiRichiesta: ");
    buffer.append(identificatoreDiRichiesta+ "\n");
    buffer.append(" informazioniDiValidazione: ");
    buffer.append(informazioniDiValidazione+ "\n");
    buffer.append("]"+ "\n");
    return buffer.toString();
  }
  
}
