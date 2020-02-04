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
package it.people.dbm.model.StampaSchede;

import java.io.Serializable;

public class InterventoStampaSchede implements Serializable {

    private int codiceIntervento;
    private String titoloIntervento;
    private int codiceInterventoPadre;
    private String codiceCondizione;
    private String testoCondizione;
    private String codiceProcedimento;
    private String titoloProcedimento;
    private String obbligatorio;
    private String testoObbligatorio;

    public InterventoStampaSchede() {
        this.codiceIntervento = 0;
        this.titoloIntervento = "";
        this.codiceInterventoPadre = 0;
        this.codiceCondizione = "";
        this.testoCondizione = "";
        this.codiceProcedimento = "";
        this.titoloProcedimento = "";
        this.obbligatorio = "";
    }

    public int getCodiceIntervento() {
        return codiceIntervento;
    }

    public void setCodiceIntervento(int codiceIntervento) {
        this.codiceIntervento = codiceIntervento;
    }

    public String getCodiceProcedimento() {
        return codiceProcedimento;
    }

    public void setCodiceProcedimento(String codiceProcedimento) {
        this.codiceProcedimento = codiceProcedimento;
    }

    public String getTitoloIntervento() {
        return titoloIntervento;
    }

    public void setTitoloIntervento(String titoloIntervento) {
        this.titoloIntervento = titoloIntervento;
    }

    public boolean isObbligatorio() {
        if (obbligatorio.equalsIgnoreCase("N")) {
            return false;
        } else {
            return true;
        }
    }

    public String getObbligatorio() {
        return this.obbligatorio;
    }

    public void setObbligatorio(String obbligatorio) {
        this.obbligatorio = obbligatorio;
    }

    public int getCodiceInterventoPadre() {
        return codiceInterventoPadre;
    }

    public void setCodiceInterventoPadre(int codiceInterventoPadre) {
        this.codiceInterventoPadre = codiceInterventoPadre;
    }

    public String getCodiceCondizione() {
        return codiceCondizione;
    }

    public void setCodiceCondizione(String codiceCondizione) {
        this.codiceCondizione = codiceCondizione;
    }

    public String getTitoloProcedimento() {
        return titoloProcedimento;
    }

    public void setTitoloProcedimento(String titoloProcedimento) {
        this.titoloProcedimento = titoloProcedimento;
    }

    public String getTestoCondizione() {
        return testoCondizione;
    }

    public void setTestoCondizione(String testoCondizione) {
        this.testoCondizione = testoCondizione;
    }

    public String getTestoObbligatorio() {
        return testoObbligatorio;
    }

    public void setTestoObbligatorio(String testoObbligatorio) {
        this.testoObbligatorio = testoObbligatorio;
    }
}
