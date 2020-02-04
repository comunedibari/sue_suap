/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.comunicazione;

import java.io.Serializable;

/**
 *
 * @author giuseppe
 */
public class AnagraficaMinifiedDTO implements Serializable {

    private String nome;
    private String cognome;
    private String ragioneSociale;
    private String codiceFiscale;
    private String partitaIVA;
    private String ruolo;
    private Integer idRuolo;
    private Integer idAnagrafica;
    private String tipoAnagrafica;
    private String varianteAnagrafica;
    private Boolean isDittaIndividuale;

    public Integer getIdAnagrafica() {
        return idAnagrafica;
    }

    public void setIdAnagrafica(Integer idAnagrafica) {
        this.idAnagrafica = idAnagrafica;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getRagioneSociale() {
        return ragioneSociale;
    }

    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getPartitaIVA() {
        return partitaIVA;
    }

    public void setPartitaIVA(String partitaIVA) {
        this.partitaIVA = partitaIVA;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public String getTipoAnagrafica() {
        return tipoAnagrafica;
    }

    public void setTipoAnagrafica(String tipoAnagrafica) {
        this.tipoAnagrafica = tipoAnagrafica;
    }

    public String getVarianteAnagrafica() {
        return varianteAnagrafica;
    }

    public void setVarianteAnagrafica(String varianteAnagrafica) {
        this.varianteAnagrafica = varianteAnagrafica;
    }

    public Boolean getIsDittaIndividuale() {
        return isDittaIndividuale;
    }

    public void setIsDittaIndividuale(Boolean isDittaIndividuale) {
        this.isDittaIndividuale = isDittaIndividuale;
    }

    public Integer getIdRuolo() {
        return idRuolo;
    }

    public void setIdRuolo(Integer idRuolo) {
        this.idRuolo = idRuolo;
    }
}
