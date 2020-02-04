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

import java.util.ArrayList;
import java.util.Arrays;

public class Eccezione {
	
  public static final String TIPO_ECCEZIONE_ALTRO = "Altro";
  public static final String TIPO_ECCEZIONE_COMUNICAZIONE = "Comunicazione";
  public static final String TIPO_ECCEZIONE_APPLICATIVA ="Applicativa";
  public static final String TIPO_ECCEZIONE_INFRASTRUTTURALE = "Infrastrutturale";
  
  protected String tipo;
	protected long codice;
  protected ArrayList note; // opzionale
	//protected Nota[] notaArray;	
  
	protected IdentificatoreDiRichiesta identificatoreDiRichiesta;
  
  
  public Eccezione(String _tipo, long _codice, IdentificatoreDiRichiesta _idRichiesta) {
    this.note = new ArrayList();
    tipo = _tipo;
    codice = _codice;
    identificatoreDiRichiesta = _idRichiesta;
  }
  public boolean validate() {
    return (tipo != null)&& (identificatoreDiRichiesta != null);
  }
	
	public long getCodice() {
		return codice;
	}
	public void setCodice(long codice) {
		this.codice = codice;
	}
	public IdentificatoreDiRichiesta getIdentificatoreDiRichiesta() {
		return identificatoreDiRichiesta;
	}
	public void setIdentificatoreDiRichiesta(
			IdentificatoreDiRichiesta identificatoreDiRichiesta) {
		this.identificatoreDiRichiesta = identificatoreDiRichiesta;
	}
  /**
   * @return Array di oggetti Nota presenti nell'eccezione.
   */
  public Nota[] getNotaArray() {
    if (note.size()==0) return null;
    Nota[] notaArray = (Nota[])note.toArray(new Nota[note.size()]);
    return notaArray;
  }
  
  /**
   * @param note Array di oggetti Nota da inserire nell'eccezione.
   */
  public void setNotaArray(Nota[] note) {
    this.note = new ArrayList(Arrays.asList(note));
  }
  
  public void addNota(Nota nota) {
    if (note==null) note = new ArrayList();
    note.add(nota);
  }
  
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
  /**
   * 
   * @return The string representation of this object
   */
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("[Eccezione:");
    buffer.append(" tipo: ");
    buffer.append(tipo);
    buffer.append(" codice: ");
    buffer.append(codice);
    buffer.append(" note: ");
    buffer.append(note);
    buffer.append(" identificatoreDiRichiesta: ");
    buffer.append(identificatoreDiRichiesta);
    buffer.append("]");
    return buffer.toString();
  }
  /**
   * Returns <code>true</code> if this <code>Eccezione</code> is the same as the o argument.
   *
   * @return <code>true</code> if this <code>Eccezione</code> is the same as the o argument.
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
    Eccezione castedObj = (Eccezione) o;
    return ((this.tipo == null ? castedObj.tipo == null : this.tipo
      .equals(castedObj.tipo))
      && (this.codice == castedObj.codice)
      && (this.note == null ? castedObj.note == null : this.note
        .equals(castedObj.note)) && (this.identificatoreDiRichiesta == null
      ? castedObj.identificatoreDiRichiesta == null
      : this.identificatoreDiRichiesta
        .equals(castedObj.identificatoreDiRichiesta)));
  }
	
	
	
	

}
