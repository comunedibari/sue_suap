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

public class EstremiTitolare {
    public static final int TIPO_TITOLARE_PERSONA_FISICA = 0;
    public static final int TIPO_TITOLARE_PERSONA_GIURIDICA = 1;
    public static final int TIPO_TITOLARE_PERSONA_GIURIDICA_UNDEFINED = -1;

    protected EstremiPersonaFisica estremiPersonaFisica = null;
    protected EstremiPersonaGiuridica estremiPersonaGiuridica = null;
    
    protected int tipoEstremiTitolare = TIPO_TITOLARE_PERSONA_GIURIDICA_UNDEFINED;
    
    /**
     * Costruttore da invocare quando il titolare � una persona fisica.<br>
     * Creando l'oggetto con questo costruttore, il metodo <code>getEstremiPersonaFisica</code> 
     * ritorner� la persona fisica passata, mentre il metodo <code>getEstremiPersonaGiuridica</code> 
     * ritoner� <code>null</code>.<br>
     * Il metodo <code>getTipoTitolare</code> ritorner� <code>TIPO_TITOLARE_PERSONA_FISICA</code>.
     * 
     * @param estremiPersonaFisica La persona fisica Titolare della richiesta/visura.
     */
    public EstremiTitolare(EstremiPersonaFisica estremiPersonaFisica) {
    	clearTipoEstremiTitolare();
        this.estremiPersonaFisica = estremiPersonaFisica;
        tipoEstremiTitolare = TIPO_TITOLARE_PERSONA_FISICA;
        
    }
    
    
    /**
     * Costruttore da invocare quando il titolare � una persona giuridica.<br>
     * Creando l'oggetto con questo costruttore, il metodo <code>getEstremiPersonaGiuridica</code> ritorner� 
     * la persona giuridica passata, mentre il metodo <code>getEstremiPersonaFisica</code> ritoner� <code>null</code>.<br>
     * Il metodo <code>getTipoTitolare</code> ritorner� <code>TIPO_TITOLARE_PERSONA_GIURIDICA</code>.
     * 
     * @param estremiPersonaGiuridica La persona giuridica Titolare della richiesta/visura.
     */
    public EstremiTitolare(EstremiPersonaGiuridica estremiPersonaGiuridica) {
    	clearTipoEstremiTitolare();
        this.estremiPersonaGiuridica = estremiPersonaGiuridica;
        tipoEstremiTitolare = TIPO_TITOLARE_PERSONA_GIURIDICA;
    }
    
    
    public EstremiPersonaFisica getEstremiPersonaFisica() {
        return estremiPersonaFisica;
    }

    public EstremiPersonaGiuridica getEstremiPersonaGiuridica() {
        return estremiPersonaGiuridica;
    }
   
    
    /**
     * Ritorna il tipo Titolare.<br>
     * 
     * @return Il tipo Titolare. 
     *         Valori possibili sono: 
     *         <code>TIPO_TITOLARE_PERSONA_FISICA</code> e 
     *         <code>TIPO_TITOLARE_PERSONA_GIURIDICA</code>
     *         
     * @see it.people.envelope.beans.EstremiTitolare#TIPO_TITOLARE_PERSONA_FISICA
     * @see it.people.envelope.beans.EstremiTitolare#TIPO_TITOLARE_PERSONA_GIURIDICA        
     */
    public int getTipoTitolare() {
        //return (estremiPersonaFisica != null) ? TIPO_TITOLARE_PERSONA_FISICA : TIPO_TITOLARE_PERSONA_GIURIDICA;
    	return tipoEstremiTitolare;
    }
    
    protected void clearTipoEstremiTitolare() {
    	this.tipoEstremiTitolare = TIPO_TITOLARE_PERSONA_GIURIDICA_UNDEFINED;
    	this.estremiPersonaFisica = null;
    	this.estremiPersonaGiuridica = null;
    }
    
    
    /**
     * @return true se il titolare � una persona fisica, false altrimenti
     */
    public boolean isEstremiPersonaFisica() {
    	//return (estremiPersonaFisica != null);
    	return tipoEstremiTitolare== TIPO_TITOLARE_PERSONA_FISICA;
    }
    
    /**
     * @return true se il titolare � una persona giuridica, false altrimenti
     */
   public boolean isEstremiPersonaGiuridica() {
    	//return (estremiPersonaGiuridica != null);
    	return tipoEstremiTitolare == TIPO_TITOLARE_PERSONA_GIURIDICA;
    }


    /**
     * Returns <code>true</code> if this <code>EstremiTitolare</code> is the same as the o argument.
     *
     * @return <code>true</code> if this <code>EstremiTitolare</code> is the same as the o argument.
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
      EstremiTitolare castedObj = (EstremiTitolare) o;
      return ((this.estremiPersonaFisica == null
        ? castedObj.estremiPersonaFisica == null
        : this.estremiPersonaFisica.equals(castedObj.estremiPersonaFisica))
        && (this.estremiPersonaGiuridica == null
          ? castedObj.estremiPersonaGiuridica == null
          : this.estremiPersonaGiuridica
            .equals(castedObj.estremiPersonaGiuridica)) && (this.tipoEstremiTitolare == castedObj.tipoEstremiTitolare));
    }


    /**
     * 
     * @return The string representation of this object
     */
    public String toString() {
      StringBuffer buffer = new StringBuffer();
      buffer.append("[EstremiTitolare:");
      buffer.append(" estremiPersonaFisica: ");
      buffer.append(estremiPersonaFisica);
      buffer.append(" estremiPersonaGiuridica: ");
      buffer.append(estremiPersonaGiuridica);
      buffer.append(" tipoEstremiTitolare: ");
      buffer.append(tipoEstremiTitolare);
      buffer.append("]");
      return buffer.toString();
    }

}
