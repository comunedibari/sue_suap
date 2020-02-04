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


public class Recapito {

  public static final int TIPO_RECAPITO_EMAIL=0;
  public static final int TIPO_RECAPITO_POSTALE=1;
  public static final int TIPO_RECAPITO_TELEFONICO=2;
  public static final int TIPO_RECAPITO_POSTALE_TESTUALE=3;
   
  public static final int TIPO_RECAPITO_UNDEFINED=-1;
  
  public static final String PRIORITA_RECAPITO_ALTERNATIVO = "Alternativo";
  public static final String PRIORITA_RECAPITO_PRINCIPALE = "Principale";
  
  public static final String TIPO_ISTITUZIONALE = "Istituzionale"; 
  public static final String TIPO_PROFESSIONALE = "Professionale";
  public static final String TIPO_PERSONALE = "Personale";
  
  protected String tipo = TIPO_PERSONALE;	// opzionale. Valori possibili: Istituzionale, Professionale, Personale
  protected String priorita = PRIORITA_RECAPITO_PRINCIPALE; // valori possibili: "Principale", "Alternativo"

  protected ArrayList note; //opzionale
  
  protected String referente = null;
  
  protected RecapitoPostale recapitoPostale;
  protected String recapitoPostaleTestuale;
  protected RecapitoTelefonico recapitoTelefonico;
  protected String indirizzoEmail;
  


  protected int tipoRecapito; // Valori possibili: vedere costanti definite nella classe

  
  public Recapito(String referente, String priorita) {
    this.note = new ArrayList();
    this.referente = referente;
    this.priorita = priorita;
    tipoRecapito = TIPO_RECAPITO_UNDEFINED;
  }
  
  public Recapito(String referente, String priorita, String tipo, Nota[] note) {
    this(referente, priorita);
    this.tipo = tipo;
    this.note = new ArrayList(Arrays.asList(note));
  }

	/**
	 * @return Array di oggetti Nota[] contenuti nel recapito.
	 */
	public Nota[] getNotaArray() {
    if (note.size()==0) return null;
    Nota[] notaArray = (Nota[])note.toArray(new Nota[note.size()]);
		return notaArray;
	}
	
	
	/**
	 * @param note Array di oggetti Nota da inserire nel recapito.
	 */
	public void setNotaArray(Nota[] note) {
		this.note = new ArrayList(Arrays.asList(note));
	}
  
  public void addNota(Nota nota) {
    if (note==null) note = new ArrayList();
    note.add(nota);
  }
	
	
	/**
	 * @return Returns the priorita.
	 */
	public String getPriorita() {
		return priorita;
	}
	
	
	/**
	 * @param priorita The priorita to set.
	 */
	public void setPriorita(String priorita) {
		this.priorita = priorita;
	}
	
	
	/**
	 * @return Returns the referente.
	 */
	public String getReferente() {
		return referente;
	}
	
	
	/**
	 * @param referente The referente to set.
	 */
	public void setReferente(String referente) {
		this.referente = referente;
	}
	
	
	/**
	 * @return Returns the tipo.
	 */
	public String getTipo() {
		return tipo;
	}
	
	
	/**
	 * @param tipo The tipo to set.
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	/**
	 * @return Returns the tipoRecapito.
	 */
	public int getTipoRecapito() {
		return tipoRecapito;
	}
	
	
	/**
	 * @param tipoRecapito The tipoRecapito to set.
	 */
	protected void setTipoRecapito(int tipoRecapito) {
		this.tipoRecapito = tipoRecapito;
	}

	

	/**
	 * @return Returns the indirizzoEmail.
	 */
	public String getIndirizzoEmail() {
		return indirizzoEmail;
	}


	/**
	 * @param indirizzoEmail The indirizzoEmail to set.
	 */
	public void setIndirizzoEmail(String indirizzoEmail) {
		clearTipoRecapito();
		this.indirizzoEmail = indirizzoEmail;
		setTipoRecapito(TIPO_RECAPITO_EMAIL);
	}


	/**
	 * @return Returns the recapitoPostale.
	 */
	public RecapitoPostale getRecapitoPostale() {
		return recapitoPostale;
	}


	/**
	 * @param recapitoPostale The recapitoPostale to set.
	 */
	public void setRecapitoPostale(RecapitoPostale recapitoPostale) {
		clearTipoRecapito();
		this.recapitoPostale = recapitoPostale;
		setTipoRecapito(TIPO_RECAPITO_POSTALE);
	}


	/**
	 * @return Returns the recapitoTelefonico.
	 */
	public RecapitoTelefonico getRecapitoTelefonico() {
		return recapitoTelefonico;
	}


	/**
	 * @param recapitoTelefonico The recapitoTelefonico to set.
	 */
	public void setRecapitoTelefonico(RecapitoTelefonico recapitoTelefonico) {
		clearTipoRecapito();
		this.recapitoTelefonico = recapitoTelefonico;
		setTipoRecapito(TIPO_RECAPITO_TELEFONICO);
	}
	
	
	
	/**
	 * @return Returns the recapitoPostaleTestuale.
	 */
	public String getRecapitoPostaleTestuale() {
		return recapitoPostaleTestuale;
	}


	/**
	 * @param recapitoPostaleTestuale The recapitoPostaleTestuale to set.
	 */
	public void setRecapitoPostaleTestuale(String recapitoPostaleTestuale) {
		clearTipoRecapito();
		this.recapitoPostaleTestuale = recapitoPostaleTestuale;
		setTipoRecapito(TIPO_RECAPITO_POSTALE_TESTUALE);
	}


	public boolean isRecapitoIndirizzoEmail() {
		return (tipoRecapito==TIPO_RECAPITO_EMAIL);
	}

	public boolean isRecapitoPostale() {
		return (tipoRecapito==TIPO_RECAPITO_POSTALE);
	}

	public boolean isRecapitoPostaleTestuale() {
		return (tipoRecapito==TIPO_RECAPITO_POSTALE_TESTUALE);
	}

	public boolean isRecapitoTelefonico() {
		return (tipoRecapito==TIPO_RECAPITO_TELEFONICO);
	}
	
	public void clearTipoRecapito() {
		tipoRecapito = TIPO_RECAPITO_UNDEFINED;
		this.recapitoPostale = null;
		this.recapitoTelefonico = null;
		this.indirizzoEmail = null;
    this.recapitoPostaleTestuale = null;
	}

  /**
   * 
   * @return The string representation of this object
   */
    public String toString() {
      StringBuffer buffer = new StringBuffer();
      buffer.append("Recapito[");
      buffer.append("indirizzoEmail = ").append(indirizzoEmail);
      buffer.append(" note = ").append(note);
      buffer.append(" priorita = ").append(priorita);
      buffer.append(" recapitoPostale = ").append(recapitoPostale);
      buffer.append(" recapitoPostaleTestuale = ")
          .append(recapitoPostaleTestuale);
      buffer.append(" recapitoTelefonico = ").append(recapitoTelefonico);
      buffer.append(" referente = ").append(referente);
      buffer.append(" tipo = ").append(tipo);
      buffer.append(" tipoRecapito = ").append(tipoRecapito);
      buffer.append("]");
      return buffer.toString();
    }

  /**
   * Returns <code>true</code> if this <code>Recapito</code> is the same as the o argument.
   *
   * @return <code>true</code> if this <code>Recapito</code> is the same as the o argument.
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
    Recapito castedObj = (Recapito) o;
    return ((this.tipo == null ? castedObj.tipo == null : this.tipo
      .equals(castedObj.tipo))
      && (this.priorita == null ? castedObj.priorita == null : this.priorita
        .equals(castedObj.priorita))
      && (this.note == null ? castedObj.note == null : this.note
        .equals(castedObj.note))
      && (this.referente == null ? castedObj.referente == null : this.referente
        .equals(castedObj.referente))
      && (this.recapitoPostale == null
        ? castedObj.recapitoPostale == null
        : this.recapitoPostale.equals(castedObj.recapitoPostale))
      && (this.recapitoPostaleTestuale == null
        ? castedObj.recapitoPostaleTestuale == null
        : this.recapitoPostaleTestuale
          .equals(castedObj.recapitoPostaleTestuale))
      && (this.recapitoTelefonico == null
        ? castedObj.recapitoTelefonico == null
        : this.recapitoTelefonico.equals(castedObj.recapitoTelefonico))
      && (this.indirizzoEmail == null
        ? castedObj.indirizzoEmail == null
        : this.indirizzoEmail.equals(castedObj.indirizzoEmail)) && (this.tipoRecapito == castedObj.tipoRecapito));
  }

 





}
