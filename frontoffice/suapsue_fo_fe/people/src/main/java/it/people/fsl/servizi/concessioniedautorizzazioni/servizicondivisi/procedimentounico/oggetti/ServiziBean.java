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
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti;

import java.io.Serializable;

/**
 * @author massimof
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ServiziBean extends BaseBean implements Serializable
{
    private int codiceServizio;
    private String codiceComune;
    private String codiceEventoVita;
    private String bookmarkPointer;
    private String nome;
    private int maxCodServizi;
    private String checkStatus;
    private String checkAltriParametri;
    
    public ServiziBean(){
    	
    }
    
    /**
     * @return Returns the maxCodServizi.
     */
    public int getMaxCodServizi() {
        return maxCodServizi;
    }
    /**
     * @param maxCodServizi The maxCodServizi to set.
     */
    public void setMaxCodServizi(int maxCodServizi) {
        this.maxCodServizi = maxCodServizi;
    }
    /**
     * @return Returns the bookmarkPointer.
     */
    public String getBookmarkPointer() {
        return bookmarkPointer;
    }
    /**
     * @param bookmarkPointer The bookmarkPointer to set.
     */
    public void setBookmarkPointer(String bookmarkPointer) {
        this.bookmarkPointer = bookmarkPointer;
    }
    /**
     * @return Returns the codiceComune.
     */
    public String getCodiceComune() {
        return codiceComune;
    }
    /**
     * @param codiceComune The codiceComune to set.
     */
    public void setCodiceComune(String codiceComune) {
        this.codiceComune = codiceComune;
    }
    /**
     * @return Returns the codiceEventoVita.
     */
    public String getCodiceEventoVita() {
        return codiceEventoVita;
    }
    /**
     * @param codiceEventoVita The codiceEventoVita to set.
     */
    public void setCodiceEventoVita(String codiceEventoVita) {
        this.codiceEventoVita = codiceEventoVita;
    }
    /**
     * @return Returns the codiceServizio.
     */
    public int getCodiceServizio() {
        return codiceServizio;
    }
    /**
     * @param codiceServizio The codiceServizio to set.
     */
    public void setCodiceServizio(int codiceServizio) {
        this.codiceServizio = codiceServizio;
    }
    /**
     * @param string
     */
    public void setNome(String nome) {
       this.nome = nome;
        
    }
    /**
     * @return Returns the nome.
     */
    public String getNome() {
        return nome;
    }
    /**
     * @return Returns the checkStaus.
     */
    public String getCheckStatus() {
        return checkStatus;
    }
    /**
     * @param checkStaus The checkStaus to set.
     */
    public void setCheckStatus(String checkStaus) {
        this.checkStatus = checkStaus;
    }

	public String getCheckAltriParametri() {
		return checkAltriParametri;
	}

	public void setCheckAltriParametri(String checkAltriParametri) {
		this.checkAltriParametri = checkAltriParametri;
	}
}
