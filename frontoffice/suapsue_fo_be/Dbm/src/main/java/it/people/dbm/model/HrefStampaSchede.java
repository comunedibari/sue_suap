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
package it.people.dbm.model;

import java.io.Serializable;

public class HrefStampaSchede implements Serializable{

    private int contatore;
    private String codice;
    private String nome;
    private int riga;
    private int posizione;
    private int tipoRiga;
    private String tipo;
    private String valore;
    private String obbligatorio;
    private String descrizione;
    private String tipoControllo;
    private int lunghezza;
    private int decimali;
    private String editabile;
    private String webServer;
    private String nomeXsd;
    private String chiave;
    private String campoDati;
    private String campoXmlMod;
    private String raggruppamentoCheck;
    private String campoCollegato;
    private String valoreCampoCollegato;

    public HrefStampaSchede() {
        this.contatore = 0;
        this.codice = "";
        this.nome = "";
        this.riga = 0;
        this.posizione = 0;
        this.tipoRiga = 0;
        this.tipo = "";
        this.valore = "";
        this.obbligatorio = "";
        this.descrizione = "";
        this.tipoControllo = "";
        this.lunghezza = 0;
        this.decimali = 0;
        this.editabile = "";
        this.webServer = "";
        this.nomeXsd = "";
        this.chiave = "";
        this.campoDati = "";
        this.campoXmlMod = "";
        this.raggruppamentoCheck = "";
        this.campoCollegato = "";
        this.valoreCampoCollegato = "";
    }

    public String getCampoCollegato() {
        return campoCollegato;
    }

    public void setCampoCollegato(String campoCollegato) {
        this.campoCollegato = campoCollegato;
    }

    public String getCampoDati() {
        return campoDati;
    }

    public void setCampoDati(String campoDati) {
        this.campoDati = campoDati;
    }

    public String getCampoXmlMod() {
        return campoXmlMod;
    }

    public void setCampoXmlMod(String campoXmlMod) {
        this.campoXmlMod = campoXmlMod;
    }

    public boolean isChiave() {
        if (!"".equals(chiave) && "S".equalsIgnoreCase(chiave)) {
            return true;
        } else {
            return false;
        }
    }

    public String getChiave() {
        return this.chiave;
    }

    public void setChiave(String chiave) {
        this.chiave = chiave;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public int getContatore() {
        return contatore;
    }

    public void setContatore(int contatore) {
        this.contatore = contatore;
    }

    public int getDecimali() {
        return decimali;
    }

    public void setDecimali(int decimali) {
        this.decimali = decimali;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public boolean isEditabile() {
        if (!"".equalsIgnoreCase(editabile) && "S".equalsIgnoreCase(editabile)) {
            return true;
        } else {
            return false;
        }
    }

    public String getEditabile() {
        return this.editabile;
    }

    public void setEditabile(String editabile) {
        this.editabile = editabile;
    }

    public int getLunghezza() {
        return lunghezza;
    }

    public void setLunghezza(int lunghezza) {
        this.lunghezza = lunghezza;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeXsd() {
        return nomeXsd;
    }

    public void setNomeXsd(String nomeXsd) {
        this.nomeXsd = nomeXsd;
    }

    public boolean isObbligatorio() {
        if (!"".equalsIgnoreCase(obbligatorio) && "O".equalsIgnoreCase(obbligatorio)) {
            return true;
        } else {
            return false;
        }
    }

    public String getObbligatorio() {
        return this.obbligatorio;
    }

    public void setObbligatorio(String obbligatorio) {
        this.obbligatorio = obbligatorio;
    }

    public int getPosizione() {
        return posizione;
    }

    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    public String getRaggruppamentoCheck() {
        return raggruppamentoCheck;
    }

    public void setRaggruppamentoCheck(String raggruppamentoCheck) {
        this.raggruppamentoCheck = raggruppamentoCheck;
    }

    public int getRiga() {
        return riga;
    }

    public void setRiga(int riga) {
        this.riga = riga;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipoControllo() {
        return tipoControllo;
    }

    public void setTipoControllo(String tipoControllo) {
        this.tipoControllo = tipoControllo;
    }

    public int getTipoRiga() {
        return tipoRiga;
    }

    public void setTipoRiga(int tipoRiga) {
        this.tipoRiga = tipoRiga;
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = valore;
    }

    public String getValoreCampoCollegato() {
        return valoreCampoCollegato;
    }

    public void setValoreCampoCollegato(String valoreCampoCollegato) {
        this.valoreCampoCollegato = valoreCampoCollegato;
    }

    public String getWebServer() {
        return webServer;
    }

    public void setWebServer(String webServer) {
        this.webServer = webServer;
    }
}
