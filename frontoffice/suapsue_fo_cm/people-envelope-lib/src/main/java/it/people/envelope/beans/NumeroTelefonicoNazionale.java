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

public class NumeroTelefonicoNazionale {

	protected String prefisso;
	protected String numero;
  
  public NumeroTelefonicoNazionale(String prefisso, String numero) {
    this.prefisso = prefisso;
    this.numero = numero;
  }

	/**
	 * @return Returns the numero.
	 */
	public String getNumero() {
		return numero;
	}
	/**
	 * @param numero The numero to set.
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}
	/**
	 * @return Returns the prefisso.
	 */
	public String getPrefisso() {
		return prefisso;
	}
	/**
	 * @param prefisso The prefisso to set.
	 */
	public void setPrefisso(String prefisso) {
		this.prefisso = prefisso;
	}
  /**
   * 
   * @return The string representation of this object
   */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[NumeroTelefonicoNazionale:");
		buffer.append(" prefisso: ");
		buffer.append(prefisso);
		buffer.append(" numero: ");
		buffer.append(numero);
		buffer.append("]");
		return buffer.toString();
	}

  /**
   * Returns <code>true</code> if this <code>NumeroTelefonicoNazionale</code> is the same as the o argument.
   *
   * @return <code>true</code> if this <code>NumeroTelefonicoNazionale</code> is the same as the o argument.
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
    NumeroTelefonicoNazionale castedObj = (NumeroTelefonicoNazionale) o;
    return ((this.prefisso == null ? castedObj.prefisso == null : this.prefisso
      .equals(castedObj.prefisso)) && (this.numero == null
      ? castedObj.numero == null
      : this.numero.equals(castedObj.numero)));
  }

	
	
}
