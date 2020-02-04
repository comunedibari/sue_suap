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

public class RelazioneEnteStampaSchede implements Serializable {

    private String codiceComune;
    private String codiceCud;
    private String codiceDestinatario;
    private String codiceSportello;
    private String descrizioneComune;
    private String descrizioneCud;
    private String intestazione;
    private String nomeDestinatario;
    private String codiceEnte;
    private String descrizioneSportello;
    private String descrizioneEnte;

    public RelazioneEnteStampaSchede() {
        codiceComune = "";
        codiceCud = "";
        codiceDestinatario = "";
        codiceSportello = "";
        descrizioneComune = "";
        descrizioneCud = "";
        intestazione = "";
        nomeDestinatario = "";
        codiceEnte = "";
        descrizioneSportello = "";
        descrizioneEnte = "";
    }

    public String getCodiceComune() {
        return codiceComune;
    }

    public void setCodiceComune(String codiceComune) {
        this.codiceComune = codiceComune;
    }

    public String getCodiceCud() {
        return codiceCud;
    }

    public void setCodiceCud(String codiceCud) {
        this.codiceCud = codiceCud;
    }

    public String getCodiceDestinatario() {
        return codiceDestinatario;
    }

    public void setCodiceDestinatario(String codiceDestinatario) {
        this.codiceDestinatario = codiceDestinatario;
    }

    public String getCodiceEnte() {
        return codiceEnte;
    }

    public void setCodiceEnte(String codiceEnte) {
        this.codiceEnte = codiceEnte;
    }

    public String getCodiceSportello() {
        return codiceSportello;
    }

    public void setCodiceSportello(String codiceSportello) {
        this.codiceSportello = codiceSportello;
    }

    public String getDescrizioneComune() {
        return descrizioneComune;
    }

    public void setDescrizioneComune(String descrizioneComune) {
        this.descrizioneComune = descrizioneComune;
    }

    public String getDescrizioneCud() {
        return descrizioneCud;
    }

    public void setDescrizioneCud(String descrizioneCud) {
        this.descrizioneCud = descrizioneCud;
    }

    public String getDescrizioneEnte() {
        return descrizioneEnte;
    }

    public void setDescrizioneEnte(String descrizioneEnte) {
        this.descrizioneEnte = descrizioneEnte;
    }

    public String getDescrizioneSportello() {
        return descrizioneSportello;
    }

    public void setDescrizioneSportello(String descrizioneSportello) {
        this.descrizioneSportello = descrizioneSportello;
    }

    public String getIntestazione() {
        return intestazione;
    }

    public void setIntestazione(String intestazione) {
        this.intestazione = intestazione;
    }

    public String getNomeDestinatario() {
        return nomeDestinatario;
    }

    public void setNomeDestinatario(String nomeDestinatario) {
        this.nomeDestinatario = nomeDestinatario;
    }
}
