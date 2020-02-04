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

public class CivicoEsternoNumerico {
	protected int numero;
	protected String colore;  // opzionale
	protected String lettera; // in alternativa ad esponente se specificato
	protected String esponente; // in alternativa a lettera se specificato
  
  public static final int TIPO_LETTERA = 0;
  public static final int TIPO_ESPONENTE = 1;
  public static final int TIPO_NOLETTERAOESPONENTE = -1;
  
  
	

  /**
   * @param numero - Numero associato al civico
   * @param lettera - Lettera associata al civico (in alternativa ad esponente se specificato)
   * @param esponente - Esponente associato al civico (in alternativa ad esponente se specificato)
   * @param colore - Colore associato al civico (opzionale)
   * @param tipoLetteraOEsponente Tipo del civico esterno numerico. 
   *  Valori consentiti:<br>
   *    <code>TIPO_LETTERA</code>, 
   *    <code>TIPO_ESPONENTE</code><br>
   *  Qualsiasi altro valore sara' ignorato.
   */
  public CivicoEsternoNumerico(int numero, String lettera, String esponente, String colore, int tipoLetteraOEsponente) {
    
    this.numero = numero;
    if (tipoLetteraOEsponente == TIPO_LETTERA) this.lettera = lettera;
    if (tipoLetteraOEsponente == TIPO_ESPONENTE) this.esponente = esponente;
    if (colore != null) this.colore = colore;
  }

  /**
   * @param numero - Numero associato al civico
   * @param lettera - Lettera associata al civico (in alternativa ad esponente se specificato)
   * @param esponente - Esponente associato al civico (in alternativa ad esponente se specificato)
    * @param tipoLetteraOEsponente Tipo del civico esterno numerico. 
   *  Valori consentiti:<br>
   *    <code>TIPO_LETTERA</code>, 
   *    <code>TIPO_ESPONENTE</code><br>
   *  Qualsiasi altro valore sara' ignorato.
   */
  public CivicoEsternoNumerico(int numero, String lettera, String esponente, int tipoLetteraOEsponente) {
    this(numero, lettera, esponente, null, tipoLetteraOEsponente);
  }

  /**
   * @param numero - Numero associato al civico
   */
  public CivicoEsternoNumerico(int numero) {
    this(numero, null, null, null, TIPO_NOLETTERAOESPONENTE);
  }
	/**
	 * @return Returns the colore.
	 */
	public String getColore() {
		return colore;
	}

	/**
	 * @param colore The colore to set.
	 */
	public void setColore(String colore) {
		this.colore = colore;
	}

	/**
	 * @return Returns the esponente.
	 */
	public String getEsponente() {
		return esponente;
	}

	/**
	 * @param esponente The esponente to set.
	 */
	public void setEsponente(String esponente) {
		this.esponente = esponente;
	}

	/**
	 * @return Returns the lettera.
	 */
	public String getLettera() {
		return lettera;
	}

	/**
	 * @param lettera The lettera to set.
	 */
	public void setLettera(String lettera) {
		this.lettera = lettera;
		
	}

	/**
	 * @return Returns the numero.
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * @param numero The numero to set.
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}

	public boolean isLetteraOEsponente() {
		return (lettera != null || esponente != null);
	}

	public boolean isLettera() {
		return (lettera != null);
	}

	public boolean isEsponente() {
		return (esponente != null);
	}
	
  /**
   * 
   * @return The string representation of this object
   */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[CivicoEsternoNumerico:");
		buffer.append(" numero: ");
		buffer.append(numero);
		buffer.append(" colore: ");
		buffer.append(colore);
		buffer.append(" lettera: ");
		buffer.append(lettera);
		buffer.append(" esponente: ");
		buffer.append(esponente);
		buffer.append("]");
		return buffer.toString();
	}

  /**
   * Returns <code>true</code> if this <code>CivicoEsternoNumerico</code> is the same as the o argument.
   *
   * @return <code>true</code> if this <code>CivicoEsternoNumerico</code> is the same as the o argument.
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
    CivicoEsternoNumerico castedObj = (CivicoEsternoNumerico) o;
    return ((this.numero == castedObj.numero)
      && (this.colore == null ? castedObj.colore == null : this.colore
        .equals(castedObj.colore))
      && (this.lettera == null ? castedObj.lettera == null : this.lettera
        .equals(castedObj.lettera)) && (this.esponente == null
      ? castedObj.esponente == null
      : this.esponente.equals(castedObj.esponente)));
  }
	
}
