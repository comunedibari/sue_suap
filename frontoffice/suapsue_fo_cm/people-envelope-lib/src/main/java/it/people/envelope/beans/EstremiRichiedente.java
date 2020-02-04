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

public class EstremiRichiedente extends EstremiPersonaFisica {
	
	public EstremiRichiedente(	String nome, String cognome, String codiceFiscale) 
		throws Exception {
		super(nome, cognome, codiceFiscale);
	}

	public EstremiRichiedente(	String nome, String cognome, String codiceFiscale, String eMail) 
			throws Exception {
			super(nome, cognome, codiceFiscale, eMail);
		}
	
  public EstremiPersonaFisica getEstremiPersonaFisica() throws Exception {
    return new EstremiPersonaFisica(nome, cognome, codiceFiscale, eMail);
  }

  /**
   * 
   * @return The string representation of this object
   */
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("[EstremiRichiedente:");
    buffer.append(" nome: ");
    buffer.append(nome);
    buffer.append(" cognome: ");
    buffer.append(cognome);
    buffer.append(" codiceFiscale: ");
    buffer.append(codiceFiscale);
    buffer.append(" eMail: ");
    buffer.append(eMail);
    buffer.append("]");
    return buffer.toString();
  }

  /**
   * Returns <code>true</code> if this <code>EstremiRichiedente</code> is the same as the o argument.
   *
   * @return <code>true</code> if this <code>EstremiRichiedente</code> is the same as the o argument.
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
//    EstremiRichiedente castedObj = (EstremiRichiedente) o;
    return true;
  }
}
