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

public class ContenutoBusta {

    /**
     * Identifica un contenuto di tipo tracciato xml (valore: 0)
     */
    public static final int TIPO_CONTENUTO_CONTENUTOXML = 0;
    /**
     * Identifica un contenuto di tipo Eccezione (valore: 1)
     */
    public static final int TIPO_CONTENUTO_ECCEZIONE = 1; 
    /**
     * Identifica un contenuto di tipo non definito (ad esempio quando il contenuto � null) (valore: -1)
     */
    public static final int TIPO_CONTENUTO_UNDEFINED = -1;
    
    protected String contenutoXML;
    protected Eccezione eccezione;
    protected int tipoContenuto = TIPO_CONTENUTO_UNDEFINED;
    
//    /**
//     * Crea un nuovo bean ContenutoBusta cn contenuto null e di tipo TIPO_CONTENUTO_UNDEFINED.<br>
//     */
//    public ContenutoBusta() {
//      setNil();
//    }
    /**
     * Crea un nuovo bean ContenutoBusta di tipo TIPO_CONTENUTO_CONTENUTOXML 
     * con contenuto impostato sulla base del parametro specificato.<br>
     * 
     * @param contenutoXML - Stringa con il tracciato xml da associare al ContenutoBusta.
     * @throws Exception - Se il contenuto xml � null
     */
    public ContenutoBusta(String contenutoXML) throws Exception {
      if (contenutoXML== null) 
//        throw new Exception("ContenutoBusta(String)::Contenuto busta non valido::" + contenutoXML);
        setContenutoXML("");
      else
      	setContenutoXML(contenutoXML);
    }

    /**
     * Crea un nuovo bean ContenutoBusta di tipo TIPO_CONTENUTO_CONTENUTOXML 
     * con contenuto impostato sulla base del byte array specificato.
     * 
     * @param contenutoXMLBytes -  Byte array con il tracciato xml da associare al ContenutoBusta
     * @throws Exception - Se il byte array con il contenuto xml � null
     */
    public ContenutoBusta(byte[] contenutoXMLBytes) throws Exception {
      if (contenutoXMLBytes== null) 
//        throw new Exception("ContenutoBusta(byte[])::Contenuto busta non valido::" + contenutoXMLBytes);
      	setContenutoXML("");
      else    		
      	setContenutoXML(new String(contenutoXMLBytes));
    }

    /**
     * Crea un nuovo bean ContenutoBusta di tipo TIPO_CONTENUTO_ECCEZIONE 
     * e con contenuto impostato sulla base dell'Eccezione specificata come parametro.
     * @param eccezione - eccezione da associare al ContenutoBusta
     * @throws Exception - Se l'oggetto eccezione � null
     */
    public ContenutoBusta(Eccezione eccezione) throws Exception {
      if (eccezione== null) 
        throw new Exception("ContenutoBusta(Eccezione)::Contenuto busta non valido::" + eccezione);
      setEccezione(eccezione);
    }
    
    protected void clear() {
    	this.contenutoXML = null;
      this.eccezione = null;
      this.tipoContenuto = TIPO_CONTENUTO_UNDEFINED;
    }
    
	/**
   * Restituisce la stringa con il contenuto xml associato all'oggetto ContenutoBusta
	 * @return - Contenuto xml associato all'oggetto ContenutoBusta
	 */
	public String getContenutoXML() {
		return contenutoXML;
	}
	/**
   * Imposta il contenuto xml associato all'oggetto ContenutoBusta 
   * e imposta il tipo del contenuto a TIPO_CONTENUTO_CONTENUTOXML.
	 * @param contenutoXML - Stringa con il contenuto xml associato all'oggetto ContenutoBusta
	 */
	public void setContenutoXML(String contenutoXML) {
		clear();
		this.contenutoXML = contenutoXML;
		tipoContenuto = TIPO_CONTENUTO_CONTENUTOXML;
	}
	/**
   * Restituisce l'Eccezione associata all'oggetto ContenutoBusta (null se l'oggetto non contiene un'eccezione)
	 * @return Eccezione associata all'oggetto ContenutoBusta (null se l'oggetto non contiene un'eccezione)
	 */
	public Eccezione getEccezione() {
		return eccezione;
	}
  /**
   * Imposta l'Eccezione associata all'oggetto ContenutoBusta 
   * e imposta il tipo del contenuto a TIPO_CONTENUTO_ECCEZIONE.
	 * @param eccezione
	 */
	public void setEccezione(Eccezione eccezione) {
		clear();
		this.eccezione = eccezione;
		tipoContenuto = TIPO_CONTENUTO_ECCEZIONE;
	}
  
  /**
   * Imposta a null il contenuto dell'oggetto ContenutoBusta 
   * e imposta il tipo del contenuto a TIPO_CONTENUTO_UNDEFINED.<br>
   * Una successiva chiamata al metodo isNil() restituir� true.
   */
//  public void setNil() {
//    clear();
//  }

    /**
     * Ritorna il tipo del contenuto associato all'oggetto ContenutoBusta.<br>
     * 
     * @return Il tipo del contenuto. 
     *         Valori possibili sono: 
     *         <code>TIPO_CONTENUTO_CONTENUTOXML (valore: 0)</code> e 
     *         <code>TIPO_CONTENUTO_ECCEZIONE (valore: 1)</code>
     *         <code>TIPO_CONTENUTO_UNDEFINED (valore: -1)</code>
     *         
     * @see ContenutoBusta#TIPO_CONTENUTO_CONTENUTOXML
     * @see ContenutoBusta#TIPO_CONTENUTO_ECCEZIONE   
     * @see ContenutoBusta#TIPO_CONTENUTO_UNDEFINED       
     */
    protected int getTipoContenuto() {
        //return (isContenutoXml() ? TIPO_CONTENUTO_CONTENUTOXML : TIPO_CONTENUTO_ECCEZIONE);
       return tipoContenuto;
    }
    
    /**
     * Verifica se l'oggetto ha una Eccezione associata (non null) e se il tipo contenuto � TIPO_CONTENUTO_ECCEZIONE 
     * @return true se il ContenutoBusta � di tipo TIPO_CONTENUTO_ECCEZIONE, false altrimenti 
     */
    public boolean isEccezione() {
    	//return (eccezione != null);
      return (tipoContenuto == TIPO_CONTENUTO_ECCEZIONE && eccezione != null);
    }

    /**
     * Verifica se l'oggetto ha un contenuto xml (non null) associato e se il tipo contenuto � TIPO_CONTENUTO_CONTENUTOXML
     * @return true se l'oggetto ha un contenuto xml (non null) associato e se il tipo contenuto � TIPO_CONTENUTO_CONTENUTOXML 
     */
    public boolean isContenutoXml() {
    	//return (contenutoXML != null);
      return (tipoContenuto == TIPO_CONTENUTO_CONTENUTOXML && contenutoXML != null);
    }
    
    /**
     * @return true se l'oggetto non ha associato un contenuto xml o una eccezione 
     */
//    public boolean isNil() {
//      return (!isEccezione() && !isContenutoXml() && contenutoXML==null && eccezione==null );
//    }

    /**
     * 
     * @return The string representation of this object
     */
    public String toString() {
      StringBuffer buffer = new StringBuffer();
      buffer.append("[ContenutoBusta:");
      buffer.append(" contenutoXML: ");
      buffer.append(contenutoXML);
      buffer.append(" eccezione: ");
      buffer.append(eccezione);
      buffer.append(" tipoContenuto: ");
      buffer.append(tipoContenuto);
      buffer.append("]");
      return buffer.toString();
    }

    /**
     * Returns <code>true</code> if this <code>ContenutoBusta</code> is the same as the o argument.
     *
     * @return <code>true</code> if this <code>ContenutoBusta</code> is the same as the o argument.
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
      ContenutoBusta castedObj = (ContenutoBusta) o;
      return ((this.contenutoXML == null
        ? castedObj.contenutoXML == null
        : this.contenutoXML.equals(castedObj.contenutoXML))
        && (this.eccezione == null ? castedObj.eccezione == null : this.eccezione
          .equals(castedObj.eccezione)) && (this.tipoContenuto == castedObj.tipoContenuto));
    }



}
