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


public class Nota {
	
	/**
	 * Allegato alla nota (opzionale)
	 */
	protected Allegato allegato; //opzionale
	/**
	 * Array of String contenente le descrizioni associate alla nota (opzionale)
	 */
	protected String[] descrizioneArray; //opzionale
	/**
	 * URI DocBook associato alla nota (opzionale)
	 */
	protected String URI_DocBook; //opzionale
	
	/**
	 * @return Restituisce l'allegato alla nota.
	 */
	public Allegato getAllegato() {
		return allegato;
	}
	/**
	 * @param allegato Imposta l'allegato alla nota.
	 */
	public void setAllegato(Allegato allegato) {
		this.allegato = allegato;
	}
	/**
	 * @return Restituisce l'array di descrizioni associate alla nota.
	 */
	public String[] getDescrizioneArray() {
		return descrizioneArray;
	}
	/**
	 * @param descrizione Imposta l'array di descrizioni associato alla nota.
	 */
	public void setDescrizioneArray(String[] descrizione) {
		this.descrizioneArray = descrizione;
	}
	/**
	 * @return Restituisce l'URI DocBook associato alla nota.
	 */
	public String getURI_DocBook() {
		return URI_DocBook;
	}
	/**
	 * @param docBook Imposta l'URI DocBook associato alla nota.
	 */
	public void setURI_DocBook(String docBook) {
		URI_DocBook = docBook;
	}
  
  /**
   * @return true se alla nota � associato un allegato, false altrimenti.
   */
  public boolean isSetAllegato() {
    return (allegato != null);
  }
  
  /**
   * @return true se alla nota � associato una descrizione, false altrimenti.
   */
  public boolean isSetDescrizioneArray() {
    return (descrizioneArray != null);
  }
  
  /**
   * @return true se alla nota � associato un URI DocBook, false altrimenti.
   */
public boolean isSetURIDocBook() {
    return (URI_DocBook != null);
  }
	
/**
 * 
 * @return The string representation of this object
 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[Nota:");
		buffer.append(" allegato: ");
		buffer.append(allegato);
		buffer.append(" { ");
		for (int i0 = 0; descrizioneArray != null && i0 < descrizioneArray.length; i0++) {
			buffer.append(" descrizione[" + i0 + "]: ");
			buffer.append(descrizioneArray[i0]);
		}
		buffer.append(" } ");
		buffer.append(" URI_DocBook: ");
		buffer.append(URI_DocBook);
		buffer.append("]");
		return buffer.toString();
	}
  /**
   * Returns <code>true</code> if this <code>Nota</code> is the same as the o argument.
   *
   * @return <code>true</code> if this <code>Nota</code> is the same as the o argument.
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
    Nota castedObj = (Nota) o;
    return ((this.allegato == null ? castedObj.allegato == null : this.allegato
      .equals(castedObj.allegato))
      && java.util.Arrays.equals(
        this.descrizioneArray,
        castedObj.descrizioneArray) && (this.URI_DocBook == null
      ? castedObj.URI_DocBook == null
      : this.URI_DocBook.equals(castedObj.URI_DocBook)));
  }
	
	

}
