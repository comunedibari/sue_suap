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
import java.util.ArrayList;

public class InterventoBean extends BaseBean implements Serializable {

    private static final long serialVersionUID = -1575182117955834343L;
    private String comuneValidita;
    private boolean checked;
    private String codiceInterventoFiglio;
    private String descInterventoFiglio;
    private String codiceOperazioneAttivante;
    private String codiceCondizione;
    private String testoCondizione;
    private String codiceNormativaVisualizzata;
    ArrayList listaCodiciAllegati;
    ArrayList listaCodiciNormative;
    // PC - ordina allegati
    private Integer ordinamento;
    // PC - ordina allegati
    //private Set interventiConCondizioneUguale;

    public InterventoBean() {
        this.comuneValidita = null;
        this.checked = false;
        this.codiceInterventoFiglio = "";
        this.descInterventoFiglio = "";
        this.codiceOperazioneAttivante = "";
        this.testoCondizione = "";
        this.codiceNormativaVisualizzata = "";
        this.listaCodiciAllegati = new ArrayList();
        this.listaCodiciNormative = new ArrayList();
        this.codiceCondizione = "";
        // PC - ordina allegati
        this.ordinamento = 0;
        // PC - ordina allegati
    }

    public String getComuneValidita() {
        return comuneValidita;
    }

    public void setComuneValidita(String comuneValidita) {
        this.comuneValidita = comuneValidita;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getCodiceInterventoFiglio() {
        return codiceInterventoFiglio;
    }

    public void setCodiceInterventoFiglio(String codiceInterventoFiglio) {
        this.codiceInterventoFiglio = codiceInterventoFiglio;
    }

    public String getDescInterventoFiglio() {
        return descInterventoFiglio;
    }

    public void setDescInterventoFiglio(String descInterventoFiglio) {
        this.descInterventoFiglio = descInterventoFiglio;
    }

    public String getCodiceOperazioneAttivante() {
        return codiceOperazioneAttivante;
    }

    public void setCodiceOperazioneAttivante(String codiceOperazioneAttivante) {
        this.codiceOperazioneAttivante = codiceOperazioneAttivante;
    }

    public String getTestoCondizione() {
        return testoCondizione;
    }

    public void setTestoCondizione(String testoCondizione) {
        this.testoCondizione = testoCondizione;
    }

    public ArrayList getListaCodiciAllegati() {
        return listaCodiciAllegati;
    }

    public void setListaCodiciAllegati(ArrayList listaCodiciAllegati) {
        this.listaCodiciAllegati = listaCodiciAllegati;
    }

    public void addListaCodiciAllegati(String codiceAllegato) {
        this.listaCodiciAllegati.add(codiceAllegato);
    }

    public ArrayList getListaCodiciNormative() {
        return listaCodiciNormative;
    }

    public void setListaCodiciNormative(ArrayList listaCodiciNormative) {
        this.listaCodiciNormative = listaCodiciNormative;
    }

    public void addListaCodiciNormative(String codiceNormativa) {
        this.listaCodiciNormative.add(codiceNormativa);
    }

    public String getCodiceCondizione() {
        return codiceCondizione;
    }

    public void setCodiceCondizione(String codiceCondizione) {
        this.codiceCondizione = codiceCondizione;
    }

    public String getCodiceNormativaVisualizzata() {
        return codiceNormativaVisualizzata;
    }

    public void setCodiceNormativaVisualizzata(String codiceNormativaVisualizzata) {
        this.codiceNormativaVisualizzata = codiceNormativaVisualizzata;
    }
    // PC - ordina allegati

    public Integer getOrdinamento() {
        return ordinamento;
    }

    public void setOrdinamento(Integer ordinamento) {
        this.ordinamento = ordinamento;
    }
    // PC - ordina allegati
}
