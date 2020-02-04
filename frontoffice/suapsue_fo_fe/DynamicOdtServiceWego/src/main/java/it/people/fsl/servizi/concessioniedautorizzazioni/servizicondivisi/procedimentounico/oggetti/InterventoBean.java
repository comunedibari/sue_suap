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

import java.util.Set;
import java.util.TreeSet;

/**
 * @author federicog
 * 
 * InterventoBean.java
 * 
 * @date 22-set-2005
 * 
 */
public class InterventoBean extends BaseBean {
    private String comuneValidita = null;
    private boolean checked;
    private String codiceInterventoFiglio;
    private String descInterventoFiglio;
    private String codiceOperazioneAttivante;
    private String testoCondizione;
    private Set interventiConCondizioneUguale;
    
    /**
     * @return Returns the codiceOperazioneAttivante.
     */
    public String getCodiceOperazioneAttivante() {
        return codiceOperazioneAttivante;
    }

    /**
     * @param codiceOperazioneAttivante The codiceOperazioneAttivante to set.
     */
    public void setCodiceOperazioneAttivante(String codiceOperazioneAttivante) {
        this.codiceOperazioneAttivante = codiceOperazioneAttivante;
    }

    public InterventoBean() {
		super();
		comuneValidita = testoCondizione = "";
		checked = false;
        interventiConCondizioneUguale = new TreeSet(new BaseBeanComparator());
	}
    
    public String getComuneValidita() {
		return comuneValidita;
	}

	public void setComuneValidita(String comuneValidita) {
		this.comuneValidita = comuneValidita;
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
     * @return Returns the codiceInterventoFiglio.
     */
    public String getCodiceInterventoFiglio() {
        return codiceInterventoFiglio;
    }

    /**
     * @param codiceInterventoFiglio The codiceInterventoFiglio to set.
     */
    public void setCodiceInterventoFiglio(String codiceInterventoFiglio) {
        this.codiceInterventoFiglio = codiceInterventoFiglio;
    }

    /**
     * @return Returns the descInterventoFiglio.
     */
    public String getDescInterventoFiglio() {
        return descInterventoFiglio;
    }

    /**
     * @param descInterventoFiglio The descInterventoFiglio to set.
     */
    public void setDescInterventoFiglio(String descInterventoFiglio) {
        this.descInterventoFiglio = descInterventoFiglio;
    }


    /**
     * @return Returns the testoCondizione.
     */
    public String getTestoCondizione() {
        return testoCondizione;
    }

    /**
     * @param testoCondizione The testoCondizione to set.
     */
    public void setTestoCondizione(String testoCondizione) {
        this.testoCondizione = testoCondizione;
    }

    public Set getInterventiConCondizioneUguale() {
        return interventiConCondizioneUguale;
    }

    public void setInterventiConCondizioneUguale(Set interventiConCondizioneUguale) {
        this.interventiConCondizioneUguale = interventiConCondizioneUguale;
    }
    
    public void addInterventiConCondizioneUguale(InterventoBean bean) {
        try {
            interventiConCondizioneUguale.add(bean);
        } catch (Exception e) {
        }

    }
}
