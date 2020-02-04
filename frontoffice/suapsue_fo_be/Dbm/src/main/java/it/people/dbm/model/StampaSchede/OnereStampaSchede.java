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
import java.math.BigDecimal;

public class OnereStampaSchede implements Serializable{

    private int codiceIntervento;
    private String codiceOneri;
    private String codiceDocumentoOnere;
    private BigDecimal importoAcconto;
    private String codicePadre;
    private String codiceCud;
    private String descrizioneOneri;
    private String note;
    private String descrizioneDocumentoOnere;
    private String nomeFile;
    private String codiceEnte;
    private String descrizioneEnte;

    public OnereStampaSchede() {
        this.codiceIntervento = 0;
        this.codiceOneri = "";
        this.codiceDocumentoOnere = "";
        this.importoAcconto = BigDecimal.ZERO;
        this.codicePadre = "";
        this.codiceCud = "";
        this.descrizioneOneri = "";
        this.note = "";
        this.descrizioneDocumentoOnere = "";
        this.nomeFile = "";
        this.codiceEnte = "";
        this.descrizioneEnte = "";
    }

    public String getCodiceCud() {
        return codiceCud;
    }

    public void setCodiceCud(String codiceCud) {
        this.codiceCud = codiceCud;
    }

    public String getCodiceDocumentoOnere() {
        return codiceDocumentoOnere;
    }

    public void setCodiceDocumentoOnere(String codiceDocumentoOnere) {
        this.codiceDocumentoOnere = codiceDocumentoOnere;
    }

    public String getCodiceEnte() {
        return codiceEnte;
    }

    public void setCodiceEnte(String codiceEnte) {
        this.codiceEnte = codiceEnte;
    }

    public String getCodiceOneri() {
        return codiceOneri;
    }

    public void setCodiceOneri(String codiceOneri) {
        this.codiceOneri = codiceOneri;
    }

    public String getCodicePadre() {
        return codicePadre;
    }

    public void setCodicePadre(String codicePadre) {
        this.codicePadre = codicePadre;
    }

    public String getDescrizioneDocumentoOnere() {
        return descrizioneDocumentoOnere;
    }

    public void setDescrizioneDocumentoOnere(String descrizioneDocumentoOnere) {
        this.descrizioneDocumentoOnere = descrizioneDocumentoOnere;
    }

    public String getDescrizioneEnte() {
        return descrizioneEnte;
    }

    public void setDescrizioneEnte(String descrizioneEnte) {
        this.descrizioneEnte = descrizioneEnte;
    }

    public String getDescrizioneOneri() {
        return descrizioneOneri;
    }

    public void setDescrizioneOneri(String descrizioneOneri) {
        this.descrizioneOneri = descrizioneOneri;
    }

    public BigDecimal getImportoAcconto() {
        return importoAcconto;
    }

    public void setImportoAcconto(BigDecimal importoAcconto) {
        this.importoAcconto = importoAcconto;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getCodiceIntervento() {
        return codiceIntervento;
    }

    public void setCodiceIntervento(int codiceIntervento) {
        this.codiceIntervento = codiceIntervento;
    }
    
}
