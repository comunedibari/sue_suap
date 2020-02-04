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

public class NormativaStampaSchede implements Serializable {

    private int codiceIntervento;
    private String codiceRiferimento;
    private String articoloRiferimento;
    private String codiceTipoRiferimento;
    private String nomeRiferimento;
    private String titoloRiferimento;
    private String tipoRiferimento;
    private String nomeFile;

    public NormativaStampaSchede() {
        this.codiceIntervento = 0;
        this.codiceRiferimento = "";
        this.articoloRiferimento = "";
        this.codiceTipoRiferimento = "";
        this.nomeRiferimento = "";
        this.tipoRiferimento = "";
        this.tipoRiferimento = "";
        this.nomeFile = "";
    }

    public String getArticoloRiferimento() {
        return articoloRiferimento;
    }

    public void setArticoloRiferimento(String articoloRiferimento) {
        this.articoloRiferimento = articoloRiferimento;
    }

    public int getCodiceIntervento() {
        return codiceIntervento;
    }

    public void setCodiceIntervento(int codiceIntervento) {
        this.codiceIntervento = codiceIntervento;
    }

    public String getCodiceRiferimento() {
        return codiceRiferimento;
    }

    public void setCodiceRiferimento(String codiceRiferimento) {
        this.codiceRiferimento = codiceRiferimento;
    }

    public String getCodiceTipoRiferimento() {
        return codiceTipoRiferimento;
    }

    public void setCodiceTipoRiferimento(String codiceTipoRiferimento) {
        this.codiceTipoRiferimento = codiceTipoRiferimento;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    public String getNomeRiferimento() {
        return nomeRiferimento;
    }

    public void setNomeRiferimento(String nomeRiferimento) {
        this.nomeRiferimento = nomeRiferimento;
    }

    public String getTipoRiferimento() {
        return tipoRiferimento;
    }

    public void setTipoRiferimento(String tipoRiferimento) {
        this.tipoRiferimento = tipoRiferimento;
    }

    public String getTitoloRiferimento() {
        return titoloRiferimento;
    }

    public void setTitoloRiferimento(String titoloRiferimento) {
        this.titoloRiferimento = titoloRiferimento;
    }
}
