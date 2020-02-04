/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author giuseppe
 */
public class ScadenzaDTO implements Serializable {

    private Integer pratica;
    private Integer idScadenza;
    private Integer idEvento;
    private String identificativo;
    private String idAnaScadenza;
    private String oggetto;
    private String descrizione;
    private String stato;
    private String codStatoScadenza;
    private String statoScadenza;
    private Date dataFineScadenza;
    private Date dataRicezione;
    private Date dataInizioScadenza;
    private Date dataScadenza;
    private Date dataScadenzaCalcolata;
    private Long giornirestanti;
    private Integer termini;
    private String protocollo;
    private String statoPratica;
    private String utenteInCarico;
    private String scadenzaDaChiudere;
    private String desEnte;

    public ScadenzaDTO() {
    }

    public ScadenzaDTO(String idAnaScadenza, String descrizione, Integer termini) {
        this.idAnaScadenza = idAnaScadenza;
        this.descrizione = descrizione;
        this.termini = termini;
    }

    public String getDesEnte() {
        return desEnte;
    }

    public void setDesEnte(String desEnte) {
        this.desEnte = desEnte;
    }

    public Integer getIdScadenza() {
        return idScadenza;
    }

    public void setIdScadenza(Integer idScadenza) {
        this.idScadenza = idScadenza;
    }

    public String getIdAnaScadenza() {
        return idAnaScadenza;
    }

    public void setIdAnaScadenza(String idAnaScadenza) {
        this.idAnaScadenza = idAnaScadenza;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getStatoScadenza() {
        return statoScadenza;
    }

    public void setStatoScadenza(String statoScadenza) {
        this.statoScadenza = statoScadenza;
    }

    public Date getDataFineScadenza() {
        return dataFineScadenza;
    }

    public void setDataFineScadenza(Date dataFineScadenza) {
        this.dataFineScadenza = dataFineScadenza;
    }

    public Integer getPratica() {
        return pratica;
    }

    public void setPratica(Integer pratica) {
        this.pratica = pratica;
    }

    public String getIdentificativo() {
        return identificativo;
    }

    public void setIdentificativo(String identificativo) {
        this.identificativo = identificativo;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public Date getDataRicezione() {
        return dataRicezione;
    }

    public void setDataRicezione(Date dataRicezione) {
        this.dataRicezione = dataRicezione;
    }

    public Long getGiornirestanti() {
        return giornirestanti;
    }

    public void setGiornirestanti(Long giornirestanti) {
        this.giornirestanti = giornirestanti;
    }

    public String getProtocollo() {
        return protocollo;
    }

    public void setProtocollo(String protocollo) {
        this.protocollo = protocollo;
    }

    public String getStatoPratica() {
        return statoPratica;
    }

    public void setStatoPratica(String statoPratica) {
        this.statoPratica = statoPratica;
    }

    public String getUtenteInCarico() {
        return utenteInCarico;
    }

    public void setUtenteInCarico(String utenteInCarico) {
        this.utenteInCarico = utenteInCarico;
    }

    public String getScadenzaDaChiudere() {
        return scadenzaDaChiudere;
    }

    public void setScadenzaDaChiudere(String scadenzaDaChiudere) {
        this.scadenzaDaChiudere = scadenzaDaChiudere;
    }

    public Date getDataInizioScadenza() {
        return dataInizioScadenza;
    }

    public void setDataInizioScadenza(Date dataInizioScadenza) {
        this.dataInizioScadenza = dataInizioScadenza;
    }

    public Date getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public Integer getTermini() {
        return termini;
    }

    public void setTermini(Integer termini) {
        this.termini = termini;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Date getDataScadenzaCalcolata() {
        return dataScadenzaCalcolata;
    }

    public void setDataScadenzaCalcolata(Date dataScadenzaCalcolata) {
        this.dataScadenzaCalcolata = dataScadenzaCalcolata;
    }

    public String getCodStatoScadenza() {
        return codStatoScadenza;
    }

    public void setCodStatoScadenza(String codStatoScadenza) {
        this.codStatoScadenza = codStatoScadenza;
    }

}
