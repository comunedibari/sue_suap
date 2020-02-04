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
 * @author federicog
 * 
 * OperazioneBean.java
 * 
 * @date 23-set-2005
 * 
 */
public class OperazioneBean extends BaseBean {
    private String codiceRamo;
    private String descrizioneRamo;
    private boolean checked;
    private String codRif;
    
    

    public OperazioneBean() {
        checked = false;
    }
    /**
     * @return Returns the codiceRamo.
     */
    public String getCodiceRamo() {
        return codiceRamo;
    }
    /**
     * @param codiceRamo The codiceRamo to set.
     */
    public void setCodiceRamo(String codiceRamo) {
        this.codiceRamo = codiceRamo;
    }
    /**
     * @return Returns the descrizioneRamo.
     */
    public String getDescrizioneRamo() {
        return descrizioneRamo;
    }
    /**
     * @param descrizioneRamo The descrizioneRamo to set.
     */
    public void setDescrizioneRamo(String descrizioneRamo) {
        this.descrizioneRamo = descrizioneRamo;
    }
    /**
     * @return Returns the checked.
     */
    public boolean isChecked() {
        return checked;
    }
    /**
     * @param checked The checked to set.
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    /**
     * @return Returns the codRif.
     */
    public String getCodRif() {
        return codRif;
    }
    /**
     * @param codRif The codRif to set.
     */
    public void setCodRif(String codRif) {
        this.codRif = codRif;
    }
}
