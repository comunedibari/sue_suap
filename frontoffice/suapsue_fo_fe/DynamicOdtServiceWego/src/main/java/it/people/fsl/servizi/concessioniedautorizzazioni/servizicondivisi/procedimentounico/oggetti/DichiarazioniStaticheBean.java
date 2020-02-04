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

/**
 * @author massimof
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DichiarazioniStaticheBean extends BaseBean {
    private String titolo;
    private String intervento;
    /**
     * @return Returns the titolo.
     */
    public String getTitolo() {
        return titolo;
    }
    /**
     * @param titolo The titolo to set.
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
    /**
     * @return Returns the intervento.
     */
    public String getIntervento() {
        return intervento;
    }
    /**
     * @param intervento The intervento to set.
     */
    public void setIntervento(String intervento) {
        this.intervento = intervento;
    }
}
