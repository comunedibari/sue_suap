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



public class Allegato {

    private String nomeFile; 
    private String descrizione;
    private byte[] contenuto;
    private String URI;
    
    public static final int TIPO_ALLEGATO_FIRMATO = 0;
    public static final int TIPO_ALLEGATO_NON_FIRMATO = 1;
    public static final int TIPO_ALLEGATO_INCORPORATO = 2;
    public static final int TIPO_ALLEGATO_FILE_ALLEGATO = 3;
    public static final int TIPO_ALLEGATO_SCONOSCIUTO = -1;
    private int tipoAllegato;
    
    
    /**
     * Crea un allegato del tipo specificato.
     * Considera soltanto i parametri rilevanti per il tipo di allegato specificato
     * 
     * @param nomeFile  - nome file 
     * @param descrizione - descrizione allegato
     * @param contenuto - contenuto allegato
     * @param uri - uri allegato
     * @param tipoAllegato - tipo allegato<br>
     *  I valori possibili sono: 
     *         <code>TIPO_ALLEGATO_FIRMATO</code>, 
     *         <code>TIPO_ALLEGATO_NON_FIRMATO</code>, 
     *         <code>TIPO_ALLEGATO_INCORPORATO</code>,
     *         <code>TIPO_ALLEGATO_FILE_ALLEGATO</code>
     *         
     * @throws Exception Se il tipo di allegato non � fra quelli supportati
     */
    public Allegato(
        String nomeFile, 
        String descrizione, 
        byte[] contenuto, String uri, int tipoAllegato)  throws Exception {
      if (tipoAllegato== TIPO_ALLEGATO_FIRMATO || tipoAllegato == TIPO_ALLEGATO_NON_FIRMATO) {
        setNomeFile(nomeFile);
        setDescrizione(descrizione);
        setContenuto(contenuto);
        setTipoAllegato(tipoAllegato);
      } else if (tipoAllegato== TIPO_ALLEGATO_INCORPORATO) {
        setContenuto(contenuto);
        setDescrizione(descrizione);
        setTipoAllegato(tipoAllegato);
      } else if (tipoAllegato == TIPO_ALLEGATO_FILE_ALLEGATO) {
        setURI(uri);
        setDescrizione(descrizione);
        setTipoAllegato(tipoAllegato);
      } else throw new Exception("Allegato::Constructor:: Tipo di allegato non valido: " + tipoAllegato);
    }
    
    
    public byte[] getContenuto() {
        return contenuto;
    }
    public void setContenuto(byte[] contenuto) {
        this.contenuto = contenuto;
    }
    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    public String getNomeFile() {
        return nomeFile;
    }
    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }
    
    /**
	 * @return Returns the uRI (per gli allegati incorporati).
	 */
	public String getURI() {
		return URI;
	}
	/**
	 * @param uri The uRI to set.
	 */
	public void setURI(String uri) {
		URI = uri;
	}
	/**
     * Ritorna il tipo allegato. 
     * 
     * @return Il tipo di allegato. 
     *  I valori possibili sono: 
     *         <code>TIPO_ALLEGATO_FIRMATO</code>, 
     *         <code>TIPO_ALLEGATO_NON_FIRMATO</code>, 
     *         <code>TIPO_ALLEGATO_INCORPORATO</code>,
     *         <code>TIPO_ALLEGATO_FILE_ALLEGATO</code>
     *         
     * 
     * @see it.people.envelope.beans.Allegato#TIPO_ALLEGATO_FIRMATO 
     * @see it.people.envelope.beans.Allegato#TIPO_ALLEGATO_NON_FIRMATO
     * @see it.people.envelope.beans.Allegato#TIPO_ALLEGATO_INCORPORATO
     * @see it.people.envelope.beans.Allegato#TIPO_ALLEGATO_FILE_ALLEGATO
    */
    public int getTipoAllegato() {
        return tipoAllegato;
    }

    
    /**
     * Imposta il Tipo Allegato.
     * 
     * @param tipoAllegato Il Tipo di Allegato. 
     *        I valori possibili sono: 
     *         <code>TIPO_ALLEGATO_FIRMATO</code>, 
     *         <code>TIPO_ALLEGATO_NON_FIRMATO</code>, 
     *         <code>TIPO_ALLEGATO_INCORPORATO</code>,
     *         <code>TIPO_ALLEGATO_FILE_ALLEGATO</code>
     *        
     * @see it.people.envelope.beans.Allegato#TIPO_ALLEGATO_FIRMATO 
     * @see it.people.envelope.beans.Allegato#TIPO_ALLEGATO_NON_FIRMATO
     * @see it.people.envelope.beans.Allegato#TIPO_ALLEGATO_INCORPORATO
     * @see it.people.envelope.beans.Allegato#TIPO_ALLEGATO_FILE_ALLEGATO
     */
    public void setTipoAllegato(int tipoAllegato) {
        this.tipoAllegato = tipoAllegato;
    }
    
    public boolean isAllegatoIncorporato() {
    	return (tipoAllegato==TIPO_ALLEGATO_INCORPORATO);
    }
    
    public boolean isAllegatoNonfirmato() {
    	return (tipoAllegato==TIPO_ALLEGATO_NON_FIRMATO);
    }
    
    public boolean isAllegatoFirmato() {
    	return (tipoAllegato==TIPO_ALLEGATO_FIRMATO);
    }

    public boolean isFileAllegato() {
    	return (tipoAllegato==TIPO_ALLEGATO_FILE_ALLEGATO);
    }


    /**
     * Returns <code>true</code> if this <code>Allegato</code> is the same as the o argument.
     *
     * @return <code>true</code> if this <code>Allegato</code> is the same as the o argument.
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
      Allegato castedObj = (Allegato) o;
      return ((this.nomeFile == null ? castedObj.nomeFile == null : this.nomeFile
        .equals(castedObj.nomeFile))
        && (this.descrizione == null
          ? castedObj.descrizione == null
          : this.descrizione.equals(castedObj.descrizione))
        && java.util.Arrays.equals(this.contenuto, castedObj.contenuto)
        && (this.URI == null ? castedObj.URI == null : this.URI
          .equals(castedObj.URI)) && (this.tipoAllegato == castedObj.tipoAllegato));
    }


    /**
     * 
     * @return The string representation of this object
     */
    public String toString() {
      StringBuffer buffer = new StringBuffer();
      buffer.append("[Allegato:");
      buffer.append(" nomeFile: ");
      buffer.append(nomeFile);
      buffer.append(" descrizione: ");
      buffer.append(descrizione);
      buffer.append(" { ");
      for (int i0 = 0; contenuto != null && i0 < contenuto.length; i0++) {
        buffer.append(" contenuto[" + i0 + "]: ");
        buffer.append(contenuto[i0]);
      }
      buffer.append(" } ");
      buffer.append(" URI: ");
      buffer.append(URI);
      buffer.append(" tipoAllegato: ");
      buffer.append(tipoAllegato);
      buffer.append("]");
      return buffer.toString();
    }
    
    
}
