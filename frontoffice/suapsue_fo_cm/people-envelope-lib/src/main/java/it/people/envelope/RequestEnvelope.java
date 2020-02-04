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

import java.util.ArrayList;
import java.util.Arrays;

import it.people.envelope.beans.ContenutoBusta;
import it.people.envelope.beans.CredenzialiUtenteCertificate;
import it.people.envelope.beans.EstremiOperatore;
import it.people.envelope.beans.EstremiRichiedente;
import it.people.envelope.beans.EstremiTitolare;
import it.people.envelope.beans.IdentificatoreDiRichiesta;
import it.people.envelope.beans.InformazioniDiValidazione;
import it.people.envelope.beans.Recapito;

/**
 * Implementazione di una busta di richiesta 
 * Revisioni:
 * 04/06/2007 - Aggiunti metodi getSoggettoDelegato() e setSoggettoDelegato()
 *
 * @see it.people.envelope.AbstractEnvelope
 * @see it.people.envelope.IRequestEnvelope
 */
public class RequestEnvelope extends AbstractEnvelope implements IRequestEnvelope {

	protected ContenutoBusta contenutoBusta;
	protected String nomeServizio;
	protected String contestoServizio;
	protected EstremiRichiedente estremiRichiedente;
	protected EstremiTitolare estremiTitolare;
	protected EstremiOperatore estremiOperatore;
	protected IdentificatoreDiRichiesta identificatoreDiRichiesta;
	protected InformazioniDiValidazione informazioniDiValidazione;
	protected CredenzialiUtenteCertificate mittente;
	protected String codiceDestinatario;
	protected ArrayList recapiti;
  //protected IdentificatoreDiProtocollo protocolloInUscita;
	
	protected EstremiTitolare estremiSoggettoDelegato;
	protected boolean forceSkipCheckDelega = false;
	
  //---------------------------------------------------------------------
  //   Costruttori
  public RequestEnvelope() {
    recapiti = new ArrayList();
  }
	//---------------------------------------------------------------------
	//   Metodi Interfaccia IEnvelope
	
	/**
	 * @see it.people.envelope.IRequestEnvelope#getCodiceDestinatario()
	 */
	public String getCodiceDestinatario() {
		return codiceDestinatario;
	}

  /**
	 * @see it.people.envelope.IRequestEnvelope#getContenuto()
	 */
	public ContenutoBusta getContenuto() {
		return contenutoBusta;
	}

  /**
	 * @see it.people.envelope.IRequestEnvelope#getContestoServizio()
	 */
	public String getContestoServizio() {
		return contestoServizio;
	}

  /**
	 * @see it.people.envelope.IRequestEnvelope#getEstremiRichiedente()
	 */
	public EstremiRichiedente getEstremiRichiedente() {
		return estremiRichiedente;
	}

  /**
	 * @see it.people.envelope.IRequestEnvelope#getEstremiTitolare()
	 */
	public EstremiTitolare getEstremiTitolare() {
		return estremiTitolare;
	}

	/**
	 * @see it.people.envelope.IRequestEnvelope#getEstremiOperatore()
	 */
	public EstremiOperatore getEstremiOperatore() {
		return estremiOperatore;
	}
	
  /**
	 * @see it.people.envelope.IRequestEnvelope#getIdentificatoreDiRichiesta()
	 */
	public IdentificatoreDiRichiesta getIdentificatoreDiRichiesta() {
		return identificatoreDiRichiesta;
	}

  /**
	 * @see it.people.envelope.IRequestEnvelope#getInformazioniDiValidazione()
	 */
	public InformazioniDiValidazione getInformazioniDiValidazione() {
		return informazioniDiValidazione;
	}

  /**
	 * @see it.people.envelope.IRequestEnvelope#getMittente()
	 */
	public CredenzialiUtenteCertificate getMittente() {
		return mittente;
	}

  /**
	 * @see it.people.envelope.IRequestEnvelope#getNomeServizio()
	 */
	public String getNomeServizio() {
		return nomeServizio;
	}

  /**
	 * @see it.people.envelope.IRequestEnvelope#getRecapiti()
	 */
	public Recapito[] getRecapiti() {
    if (recapiti.size()==0) return null;
    Recapito[] recapitoArray = (Recapito[])recapiti.toArray(new Recapito[recapiti.size()]);
		return recapitoArray;
	}

  /**
	 * @see it.people.envelope.IRequestEnvelope#setCodiceDestinatario(java.lang.String)
	 */
	public void setCodiceDestinatario(String codiceDestinatario) {
		this.codiceDestinatario = codiceDestinatario;
	}

  /**
	 * @see it.people.envelope.IRequestEnvelope#setContenuto(it.people.envelope.beans.ContenutoBusta)
	 */
	public void setContenuto(ContenutoBusta contenuto) {
		this.contenutoBusta = contenuto;
	}

  /**
	 * @see it.people.envelope.IRequestEnvelope#setContestoServizio(java.lang.String)
	 */
	public void setContestoServizio(String contestoServizio) {
		this.contestoServizio = contestoServizio;
	}

  /**
	 * @see it.people.envelope.IRequestEnvelope#setEstremiRichiedente(it.people.envelope.beans.EstremiRichiedente)
	 */
	public void setEstremiRichiedente(EstremiRichiedente richiedente) {
		this.estremiRichiedente = richiedente;
	}

  /**
	 * @see it.people.envelope.IRequestEnvelope#setEstremiTitolare(it.people.envelope.beans.EstremiTitolare)
	 */
	public void setEstremiTitolare(EstremiTitolare titolare) {
		this.estremiTitolare = titolare;
	}

	/**
	 * @see it.people.envelope.IRequestEnvelope#setEstremiOperatore(it.people.envelope.beans.EstremiOperatore)
	 */
	public void setEstremiOperatore(EstremiOperatore operatore) {
		this.estremiOperatore = operatore;
	}
	
  /**
	 * @see it.people.envelope.IRequestEnvelope#setIdentificatoreDiRichiesta(it.people.envelope.beans.IdentificatoreDiRichiesta)
	 */
	public void setIdentificatoreDiRichiesta(IdentificatoreDiRichiesta idRichiesta) {
		this.identificatoreDiRichiesta = idRichiesta;
	}

  /**
	 * @see it.people.envelope.IRequestEnvelope#setInformazioniDiValidazione(it.people.envelope.beans.InformazioniDiValidazione)
	 */
	public void setInformazioniDiValidazione(InformazioniDiValidazione informazionidivalidazione) {
		this.informazioniDiValidazione = informazionidivalidazione;
	}

  /**
	 * @see it.people.envelope.IRequestEnvelope#setMittente(it.people.envelope.beans.CredenzialiUtenteCertificate)
	 */
	public void setMittente(CredenzialiUtenteCertificate credenzialiMittente) {
		this.mittente = credenzialiMittente;
	}

  /**
	 * @see it.people.envelope.IRequestEnvelope#setNomeServizio(java.lang.String)
	 */
	public void setNomeServizio(String nomeServizio) {
		this.nomeServizio = nomeServizio;
	}

  /**
	 * @see it.people.envelope.IRequestEnvelope#setRecapiti(it.people.envelope.beans.Recapito[])
	 */
	public void setRecapiti(Recapito[] recapiti) {
		this.recapiti = new ArrayList(Arrays.asList(recapiti));
	}

  /**
   * @see it.people.envelope.IRequestEnvelope#addRecapito(it.people.envelope.beans.Recapito)
   */
  public void addRecapito(Recapito recapito) {
    if (recapiti==null) recapiti = new ArrayList();
    recapiti.add(recapito);
  }
  
//  public IdentificatoreDiProtocollo getProtocolloInUscita() {
//    return protocolloInUscita;
//  }
  
//  public void setProtocolloInUscita(IdentificatoreDiProtocollo protocolloInUscita) {
//    this.protocolloInUscita = protocolloInUscita;
//  }
  /**
   * @see it.people.envelope.IRequestEnvelope#getEstremiSoggettoDelegato()
   */
  public EstremiTitolare getEstremiSoggettoDelegato() {
  	return estremiSoggettoDelegato;
  }

  /**
   * @see it.people.envelope.IRequestEnvelope#setEstremiSoggettoDelegato(it.people.envelope.beans.EstremiTitolare)
   */
  public void setEstremiSoggettoDelegato(EstremiTitolare soggettoDelegato) {
  	this.estremiSoggettoDelegato = soggettoDelegato;
  	
  }
  
  /**
   * @see it.people.envelope.IRequestEnvelope#getForceSkipCheckDelega() 
   */
	public boolean getForceSkipCheckDelega(){
		return forceSkipCheckDelega;
	}
	
  /**
   * @see it.people.envelope.IRequestEnvelope#getForceSkipCheckDelega() 
   */
	public void setForceSkipCheckDelega(boolean forceSkipCheckDelega){
		this.forceSkipCheckDelega = forceSkipCheckDelega;
	}
  
  /**
   * 
   * @return The string representation of this object
   */
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("[RequestEnvelope:\n");
    buffer.append(" contenutoBusta: ");
    buffer.append(contenutoBusta + "\n");
    buffer.append(" nomeServizio: ");
    buffer.append(nomeServizio+ "\n");
    buffer.append(" contestoServizio: ");
    buffer.append(contestoServizio+ "\n");
    buffer.append(" estremiRichiedente: ");
    buffer.append(estremiRichiedente+ "\n");
    buffer.append(" estremiTitolare: ");
    buffer.append(estremiTitolare+ "\n");
    buffer.append(" estremiSoggettoDelegato: ");
    buffer.append(estremiSoggettoDelegato+ "\n");
    buffer.append(" identificatoreDiRichiesta: ");
    buffer.append(identificatoreDiRichiesta+ "\n");
    buffer.append(" informazioniDiValidazione: ");
    buffer.append(informazioniDiValidazione+ "\n");
    buffer.append(" mittente: ");
    buffer.append(mittente+ "\n");
    buffer.append(" codiceDestinatario: ");
    buffer.append(codiceDestinatario+ "\n");
    buffer.append(" recapiti: ");
    buffer.append(recapiti+ "\n");
    buffer.append(" forceSkipCheckDelega: ");
    buffer.append(forceSkipCheckDelega+ "\n");
    //buffer.append(" protocolloInUscita: ");
    //buffer.append(protocolloInUscita+ "\n");
    buffer.append("]\n");
    return buffer.toString();
  }
  /**
   * Returns <code>true</code> if this <code>RequestEnvelope</code> is the same as the o argument.
   *
   * @return <code>true</code> if this <code>RequestEnvelope</code> is the same as the o argument.
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
    RequestEnvelope castedObj = (RequestEnvelope) o;
    return ((this.contenutoBusta == null
      ? castedObj.contenutoBusta == null
      : this.contenutoBusta.equals(castedObj.contenutoBusta))
      && (this.nomeServizio == null
        ? castedObj.nomeServizio == null
        : this.nomeServizio.equals(castedObj.nomeServizio))
      && (this.contestoServizio == null
        ? castedObj.contestoServizio == null
        : this.contestoServizio.equals(castedObj.contestoServizio))
      && (this.estremiRichiedente == null
        ? castedObj.estremiRichiedente == null
        : this.estremiRichiedente.equals(castedObj.estremiRichiedente))
      && (this.estremiTitolare == null
        ? castedObj.estremiTitolare == null
        : this.estremiTitolare.equals(castedObj.estremiTitolare))
      && (this.identificatoreDiRichiesta == null
        ? castedObj.identificatoreDiRichiesta == null
        : this.identificatoreDiRichiesta
          .equals(castedObj.identificatoreDiRichiesta))
      && (this.informazioniDiValidazione == null
        ? castedObj.informazioniDiValidazione == null
        : this.informazioniDiValidazione
          .equals(castedObj.informazioniDiValidazione))
      && (this.mittente == null ? castedObj.mittente == null : this.mittente
        .equals(castedObj.mittente))
      && (this.codiceDestinatario == null
        ? castedObj.codiceDestinatario == null
        : this.codiceDestinatario.equals(castedObj.codiceDestinatario))
      && (this.recapiti == null ? castedObj.recapiti == null : this.recapiti
        .equals(castedObj.recapiti)) 
//      && (this.protocolloInUscita == null
//      ? castedObj.protocolloInUscita == null
//      : this.protocolloInUscita.equals(castedObj.protocolloInUscita))    
    );
  }

}
