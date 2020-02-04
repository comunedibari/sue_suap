/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto;

/**
 *
 * @author giuseppe
 */
public class AnagraficaDaCollegareDTO {

    private Integer idAnagrafica;
    private Integer ruolo;
    private Integer qualificaTecnico;
    private Integer qualificaRichiedente;
    private String dittaIndividuale;
    private String tipologia;
    private String nome;
    private String cognome;
    private String denominazione;
    private String variante;
    private String codiceFiscale;
    private String partitaIva;

    public Integer getIdAnagrafica() {
        return idAnagrafica;
    }

    public void setIdAnagrafica(Integer idAnagrafica) {
        this.idAnagrafica = idAnagrafica;
    }

    public Integer getRuolo() {
        return ruolo;
    }

    public void setRuolo(Integer ruolo) {
        this.ruolo = ruolo;
    }

    public Integer getQualificaTecnico() {
        return qualificaTecnico;
    }

    public void setQualificaTecnico(Integer qualificaTecnico) {
        this.qualificaTecnico = qualificaTecnico;
    }

    public Integer getQualificaRichiedente() {
        return qualificaRichiedente;
    }

    public void setQualificaRichiedente(Integer qualificaRichiedente) {
        this.qualificaRichiedente = qualificaRichiedente;
    }

    public String getDittaIndividuale() {
        return dittaIndividuale;
    }

    public void setDittaIndividuale(String dittaIndividuale) {
        this.dittaIndividuale = dittaIndividuale;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
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

    public String getVariante() {
        return variante;
    }

    public void setVariante(String variante) {
        this.variante = variante;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }
}
