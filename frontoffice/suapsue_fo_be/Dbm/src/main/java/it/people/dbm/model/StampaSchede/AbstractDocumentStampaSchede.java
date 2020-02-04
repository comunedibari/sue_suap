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

public abstract class AbstractDocumentStampaSchede implements Serializable {

    private String codiceDocumento;
    private String codiceIntervento;
    private int copie;
    private int autocertificazione;
    private String codiceCondizione;
    private String obbligatorio;
    private String tipologie;
    private int numeroMassimoPagine;
    private int dimensioneMassima;
    private String dichiarazione;
    private String href;
    private String titoloDocumento;
    private String descrizioneDocumento;
    private String nomeFile;
    private String testoCondizione;

    public AbstractDocumentStampaSchede() {
        this.codiceDocumento = "";
        this.codiceIntervento = "";
        this.copie = 0;
        this.autocertificazione = 0;
        this.codiceCondizione = "";
        this.obbligatorio = "";
        this.tipologie = "";
        this.numeroMassimoPagine = 0;
        this.dimensioneMassima = 0;
        this.dichiarazione = "";
        this.href = "";
        this.titoloDocumento = "";
        this.descrizioneDocumento = "";
        this.nomeFile = "";
        this.testoCondizione = "";
    }

    public boolean isAutocertificazione() {
        if (autocertificazione > 0) {
            return true;
        } else {
            return false;
        }
    }

    public int getAutocertificazion() {
        return this.autocertificazione;
    }

    public void setAutocertificazione(int autocertificazione) {
        this.autocertificazione = autocertificazione;
    }

    public String getCodiceCondizione() {
        return codiceCondizione;
    }

    public void setCodiceCondizione(String codiceCondizione) {
        this.codiceCondizione = codiceCondizione;
    }

    public String getCodiceDocumento() {
        return codiceDocumento;
    }

    public void setCodiceDocumento(String codiceDocumento) {
        this.codiceDocumento = codiceDocumento;
    }

    public String getCodiceIntervento() {
        return codiceIntervento;
    }

    public void setCodiceIntervento(String codiceIntervento) {
        this.codiceIntervento = codiceIntervento;
    }

    public int getCopie() {
        return copie;
    }

    public void setCopie(int copie) {
        this.copie = copie;
    }

    public int getDimensioneMassima() {
        return dimensioneMassima;
    }

    public void setDimensioneMassima(int dimensioneMassima) {
        this.dimensioneMassima = dimensioneMassima;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    public int getNumeroMassimoPagine() {
        return numeroMassimoPagine;
    }

    public void setNumeroMassimoPagine(int numeroMassimoPagine) {
        this.numeroMassimoPagine = numeroMassimoPagine;
    }

    public String getTestoCondizione() {
        return testoCondizione;
    }

    public void setTestoCondizione(String testoCondizione) {
        this.testoCondizione = testoCondizione;
    }

    public String getTipologie() {
        return tipologie;
    }

    public void setTipologie(String tipologie) {
        this.tipologie = tipologie;
    }

    public String getTitoloDocumento() {
        return titoloDocumento;
    }

    public void setTitoloDocumento(String titoloDocumento) {
        this.titoloDocumento = titoloDocumento;
    }

    public boolean isDichiarazione() {
        if (dichiarazione.equalsIgnoreCase("N")) {
            return false;
        } else {
            return true;
        }
    }

    public void setDichiarazione(String dichiarazione) {
        this.dichiarazione = dichiarazione;
    }

    public String getDichiarazione() {
        return this.dichiarazione;
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

    public String getDescrizioneDocumento() {
        return descrizioneDocumento;
    }

    public void setDescrizioneDocumento(String descrizioneDocumento) {
        this.descrizioneDocumento = descrizioneDocumento;
    }
}
