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

import java.math.BigDecimal;

public class IndirizzoStrutturato {
	
	public static final int TIPO_CIVICOESTERNOCHILOMETRICO=0;
	public static final int TIPO_CIVICOESTERNONUMERICO=1;
	public static final int TIPO_CIVICOESTERNO_UNDEFINED=-1;

	protected Toponimo toponimo;
	
	protected int tipoCivicoesterno = TIPO_CIVICOESTERNO_UNDEFINED;
	protected BigDecimal civicoEsternoChilometrico;
	protected CivicoEsternoNumerico civicoEsternoNumerico;
  
  
  public IndirizzoStrutturato(BigDecimal civicoEsternoChilometrico, Toponimo toponimo) {
    setCivicoEsternoChilometrico(civicoEsternoChilometrico);
    setToponimo(toponimo);
  }

  public IndirizzoStrutturato(CivicoEsternoNumerico civicoEsternoNumerico, Toponimo toponimo) {
    setCivicoEsternoNumerico(civicoEsternoNumerico);
    setToponimo(toponimo);
  }

	
	/**
	 * @return Returns the civicoEsternoChilometrico.
	 */
	public BigDecimal getCivicoEsternoChilometrico() {
		return civicoEsternoChilometrico;
	}


	/**
	 * @param civicoEsternoChilometrico The civicoEsternoChilometrico to set.
	 */
	public void setCivicoEsternoChilometrico(BigDecimal civicoEsternoChilometrico) {
		clearCivico();
		this.civicoEsternoChilometrico = civicoEsternoChilometrico;
		tipoCivicoesterno = TIPO_CIVICOESTERNOCHILOMETRICO;
	}


	/**
	 * @return Returns the civicoEsternoNumerico.
	 */
	public CivicoEsternoNumerico getCivicoEsternoNumerico() {
		return civicoEsternoNumerico;
	}


	/**
	 * @param civicoEsternoNumerico The civicoEsternoNumerico to set.
	 */
	public void setCivicoEsternoNumerico(CivicoEsternoNumerico civicoEsternoNumerico) {
		clearCivico();
		this.civicoEsternoNumerico = civicoEsternoNumerico;
		tipoCivicoesterno = TIPO_CIVICOESTERNONUMERICO;
	}


	/**
	 * @return Returns the toponimo.
	 */
	public Toponimo getToponimo() {
		return toponimo;
	}


	/**
	 * @param toponimo The toponimo to set.
	 */
	public void setToponimo(Toponimo toponimo) {
		this.toponimo = toponimo;
	}
	
	protected void clearCivico() {
		civicoEsternoChilometrico = null;
		civicoEsternoNumerico = null;
		tipoCivicoesterno = TIPO_CIVICOESTERNO_UNDEFINED;
	}

	public int getTipoCivicoEsterno() {
		//return (civicoEsternoChilometrico != null) ? TIPO_CIVICOESTERNOCHILOMETRICO : TIPO_CIVICOESTERNONUMERICO;
		return tipoCivicoesterno;
	}
	
	public boolean isTipoCivicoEsternoChilometrico() {
		return (tipoCivicoesterno==TIPO_CIVICOESTERNOCHILOMETRICO);
	}

	public boolean isTipoCivicoEsternoNumerico() {
		return (tipoCivicoesterno==TIPO_CIVICOESTERNONUMERICO);
	}

  /**
   * 
   * @return The string representation of this object
   */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[IndirizzoStrutturato:");
		buffer.append(" toponimo: ");
		buffer.append(toponimo);
		buffer.append(" civicoEsternoChilometrico: ");
		buffer.append(civicoEsternoChilometrico);
		buffer.append(" civicoEsternoNumerico: ");
		buffer.append(civicoEsternoNumerico);
		buffer.append("]");
		return buffer.toString();
	}


  /**
   * Returns <code>true</code> if this <code>IndirizzoStrutturato</code> is the same as the o argument.
   *
   * @return <code>true</code> if this <code>IndirizzoStrutturato</code> is the same as the o argument.
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
    IndirizzoStrutturato castedObj = (IndirizzoStrutturato) o;
    return ((this.toponimo == null ? castedObj.toponimo == null : this.toponimo
      .equals(castedObj.toponimo))
      && (this.tipoCivicoesterno == castedObj.tipoCivicoesterno)
      && (this.civicoEsternoChilometrico == null
        ? castedObj.civicoEsternoChilometrico == null
        : this.civicoEsternoChilometrico
          .equals(castedObj.civicoEsternoChilometrico)) && (this.civicoEsternoNumerico == null
      ? castedObj.civicoEsternoNumerico == null
      : this.civicoEsternoNumerico.equals(castedObj.civicoEsternoNumerico)));
  }
	

}
