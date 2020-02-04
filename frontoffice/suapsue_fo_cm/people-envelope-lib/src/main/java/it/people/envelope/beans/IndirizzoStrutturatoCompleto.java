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

public class IndirizzoStrutturatoCompleto {
	protected IndirizzoStrutturato indirizzoStrutturato;
	protected String cap;
	protected Comune comune;
	protected String frazioneComunale;
	/**
	 * @return Returns the cap.
	 */
	public String getCap() {
		return cap;
	}
	/**
	 * @param cap The cap to set.
	 */
	public void setCap(String cap) {
		this.cap = cap;
	}
	/**
	 * @return Returns the comune.
	 */
	public Comune getComune() {
		return comune;
	}
	/**
	 * @param comune The comune to set.
	 */
	public void setComune(Comune comune) {
		this.comune = comune;
	}
	/**
	 * @return Returns the frazioneComunale.
	 */
	public String getFrazioneComunale() {
		return frazioneComunale;
	}
	/**
	 * @param frazioneComunale The frazioneComunale to set.
	 */
	public void setFrazioneComunale(String frazioneComunale) {
		this.frazioneComunale = frazioneComunale;
	}
	/**
	 * @return Returns the indirizzoStrutturato.
	 */
	public IndirizzoStrutturato getIndirizzoStrutturato() {
		return indirizzoStrutturato;
	}
	/**
	 * @param indirizzoStrutturato The indirizzoStrutturato to set.
	 */
	public void setIndirizzoStrutturato(IndirizzoStrutturato indirizzoStrutturato) {
		this.indirizzoStrutturato = indirizzoStrutturato;
	}
	
  /**
   * 
   * @return The string representation of this object
   */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[IndirizzoStrutturatoCompleto:");
		buffer.append(" indirizzoStrutturato: ");
		buffer.append(indirizzoStrutturato);
		buffer.append(" cap: ");
		buffer.append(cap);
		buffer.append(" comune: ");
		buffer.append(comune);
		buffer.append(" frazioneComunale: ");
		buffer.append(frazioneComunale);
		buffer.append("]");
		return buffer.toString();
	}
  /**
   * Returns <code>true</code> if this <code>IndirizzoStrutturatoCompleto</code> is the same as the o argument.
   *
   * @return <code>true</code> if this <code>IndirizzoStrutturatoCompleto</code> is the same as the o argument.
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
    IndirizzoStrutturatoCompleto castedObj = (IndirizzoStrutturatoCompleto) o;
    return ((this.indirizzoStrutturato == null
      ? castedObj.indirizzoStrutturato == null
      : this.indirizzoStrutturato.equals(castedObj.indirizzoStrutturato))
      && (this.cap == null ? castedObj.cap == null : this.cap
        .equals(castedObj.cap))
      && (this.comune == null ? castedObj.comune == null : this.comune
        .equals(castedObj.comune)) && (this.frazioneComunale == null
      ? castedObj.frazioneComunale == null
      : this.frazioneComunale.equals(castedObj.frazioneComunale)));
  }
	
	
}
