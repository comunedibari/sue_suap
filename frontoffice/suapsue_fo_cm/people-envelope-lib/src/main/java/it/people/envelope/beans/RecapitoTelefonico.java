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
package it.people.envelope.beans;

public class RecapitoTelefonico {

	public static final int TIPO_RECAPITOTELEFONICO_NUMEROTELEFONICOESTERO=0;
	public static final int TIPO_RECAPITOTELEFONICO_NUMEROTELEFONICONAZIONALE=1;
	public static final int TIPO_RECAPITOTELEFONICO_UNDEFINED=-1;
  
  public static final String MODALITA_TELEFONATA = "Telefonata";
  public static final String MODALITA_SMS = "SMS";
  public static final String MODALITA_FAX = "Fax";
	
	protected String modalita;
	protected NumeroTelefonicoNazionale numeroTelefonicoNazionale;
	protected NumeroTelefonicoEstero numeroTelefonicoEstero;
	protected int tipoRecapitoTelefonico;
	
	/**
	 * @param modalita
	 */
	public RecapitoTelefonico(String modalita) {
   clearTipoRecapitoTelefonico();
   this.modalita = modalita;
  }
  
  /**
   * @param modalita
   * @param numTelNazionale
   */
  public RecapitoTelefonico(String modalita, NumeroTelefonicoNazionale numTelNazionale) {
    this(modalita);
    this.numeroTelefonicoNazionale = numTelNazionale;
    tipoRecapitoTelefonico = TIPO_RECAPITOTELEFONICO_NUMEROTELEFONICONAZIONALE;
   }
  
  /**
   * @param modalita
   * @param numTelEstero
   */
  public RecapitoTelefonico(String modalita, NumeroTelefonicoEstero numTelEstero) {
    this(modalita);
    this.numeroTelefonicoEstero = numTelEstero;
    tipoRecapitoTelefonico = TIPO_RECAPITOTELEFONICO_NUMEROTELEFONICOESTERO;
   }

	protected void clearTipoRecapitoTelefonico() {
		this.tipoRecapitoTelefonico = TIPO_RECAPITOTELEFONICO_UNDEFINED;
		this.numeroTelefonicoEstero = null;
		this.numeroTelefonicoNazionale = null;
	}
	
	
	/**
	 * @return Returns the modalita.
	 */
	public String getModalita() {
		return modalita;
	}


	/**
	 * @param modalita The modalita to set.
	 */
	public void setModalita(String modalita) {
		this.modalita = modalita;
	}


	/**
	 * @return Returns the numeroTelefonicoEstero.
	 */
	public NumeroTelefonicoEstero getNumeroTelefonicoEstero() {
		return numeroTelefonicoEstero;
	}


	/**
	 * @param numeroTelefonicoEstero The numeroTelefonicoEstero to set.
	 */
	public void setNumeroTelefonicoEstero(
			NumeroTelefonicoEstero numeroTelefonicoEstero) {
		clearTipoRecapitoTelefonico();
		this.numeroTelefonicoEstero = numeroTelefonicoEstero;
		tipoRecapitoTelefonico = TIPO_RECAPITOTELEFONICO_NUMEROTELEFONICOESTERO;
	}


	/**
	 * @return Returns the numeroTelefonicoNazionale.
	 */
	public NumeroTelefonicoNazionale getNumeroTelefonicoNazionale() {
		return numeroTelefonicoNazionale;
	}


	/**
	 * @param numeroTelefonicoNazionale The numeroTelefonicoNazionale to set.
	 */
	public void setNumeroTelefonicoNazionale(
			NumeroTelefonicoNazionale numeroTelefonicoNazionale) {
		clearTipoRecapitoTelefonico();
		this.numeroTelefonicoNazionale = numeroTelefonicoNazionale;
		tipoRecapitoTelefonico = TIPO_RECAPITOTELEFONICO_NUMEROTELEFONICONAZIONALE;
	}


	protected int getTipoNumeroTelefonico() {
        return (numeroTelefonicoNazionale != null) ? TIPO_RECAPITOTELEFONICO_NUMEROTELEFONICONAZIONALE : TIPO_RECAPITOTELEFONICO_NUMEROTELEFONICOESTERO;

	}
	
	/**
	 * @return true se il Recapito telefonico corrisponde ad un numero telefonico estero, 
   * false altrimenti
	 */
	public boolean isNumeroTelefonicoEstero() {
		return tipoRecapitoTelefonico == TIPO_RECAPITOTELEFONICO_NUMEROTELEFONICOESTERO;
	}
	
  /**
   * @return true se il Recapito telefonico corrisponde ad un numero telefonico nazionale, 
   * false altrimenti
   */
	public boolean isNumeroTelefonicoNazionale() {
		return tipoRecapitoTelefonico == TIPO_RECAPITOTELEFONICO_NUMEROTELEFONICONAZIONALE;
	}

  /**
   * Returns <code>true</code> if this <code>RecapitoTelefonico</code> is the same as the o argument.
   *
   * @return <code>true</code> if this <code>RecapitoTelefonico</code> is the same as the o argument.
   */
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (o.getClass() != getClass()) {
      return false;
    }
    RecapitoTelefonico castedObj = (RecapitoTelefonico) o;
    return ((this.modalita == null ? castedObj.modalita == null : this.modalita
      .equals(castedObj.modalita))
      && (this.numeroTelefonicoNazionale == null
        ? castedObj.numeroTelefonicoNazionale == null
        : this.numeroTelefonicoNazionale
          .equals(castedObj.numeroTelefonicoNazionale))
      && (this.numeroTelefonicoEstero == null
        ? castedObj.numeroTelefonicoEstero == null
        : this.numeroTelefonicoEstero.equals(castedObj.numeroTelefonicoEstero)) && (this.tipoRecapitoTelefonico == castedObj.tipoRecapitoTelefonico));
  }

  /**
   * 
   * @return The string representation of this object
   */
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("[RecapitoTelefonico:");
    buffer.append(" modalita: ");
    buffer.append(modalita);
    buffer.append(" numeroTelefonicoNazionale: ");
    buffer.append(numeroTelefonicoNazionale);
    buffer.append(" numeroTelefonicoEstero: ");
    buffer.append(numeroTelefonicoEstero);
    buffer.append(" tipoRecapitoTelefonico: ");
    buffer.append(tipoRecapitoTelefonico);
    buffer.append("]");
    return buffer.toString();
  }

	
}
