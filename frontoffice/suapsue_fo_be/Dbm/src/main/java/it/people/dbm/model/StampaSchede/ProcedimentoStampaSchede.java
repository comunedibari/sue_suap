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

public class ProcedimentoStampaSchede implements Serializable {

    private String codiceProcedimento;
    private String titoloProcedimento;
    private int tipoProcedimento;
    private String testoTipoProcedimento;
    private String bollo;
    private String testoBollo;
    private String codiceCud;
    private int terminiEvasione;

    public ProcedimentoStampaSchede() {
        this.codiceCud = "";
        this.codiceProcedimento = "";
        this.titoloProcedimento = "";
        this.bollo = "";
        this.tipoProcedimento = 0;
        this.terminiEvasione = 0;
    }

    public String getBollo() {
        return bollo;
    }

    public void setBollo(String bollo) {
        this.bollo = bollo;
    }

    public boolean isBollo() {
        if (!"".equals(bollo) && "N".equalsIgnoreCase(bollo)) {
            return false;
        } else {
            return true;
        }
    }

    public String getCodiceCud() {
        return codiceCud;
    }

    public void setCodiceCud(String codiceCud) {
        this.codiceCud = codiceCud;
    }

    public String getCodiceProcedimento() {
        return codiceProcedimento;
    }

    public void setCodiceProcedimento(String codiceProcedimento) {
        this.codiceProcedimento = codiceProcedimento;
    }

    public int getTerminiEvasione() {
        return terminiEvasione;
    }

    public void setTerminiEvasione(int terminiEvasione) {
        this.terminiEvasione = terminiEvasione;
    }

    public int getTipoProcedimento() {
        return tipoProcedimento;
    }

    public void setTipoProcedimento(int tipoProcedimento) {
        this.tipoProcedimento = tipoProcedimento;
    }

    public String getTitoloProcedimento() {
        return titoloProcedimento;
    }

    public void setTitoloProcedimento(String titoloProcedimento) {
        this.titoloProcedimento = titoloProcedimento;
    }

    public String getTestoBollo() {
        return testoBollo;
    }

    public void setTestoBollo(String testoBollo) {
        this.testoBollo = testoBollo;
    }

    public String getTestoTipoProcedimento() {
        return testoTipoProcedimento;
    }

    public void setTestoTipoProcedimento(String testoTipoProcedimento) {
        this.testoTipoProcedimento = testoTipoProcedimento;
    }
}
