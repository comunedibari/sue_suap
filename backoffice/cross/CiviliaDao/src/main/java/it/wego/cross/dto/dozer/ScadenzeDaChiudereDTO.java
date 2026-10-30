/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

import java.util.Date;

/**
 *
 * @author Gabriele
 */
public class ScadenzeDaChiudereDTO {

    private int idScadenza;
    private int idEvento;
    private int idPratica;
    private String idAnaScadenza;
    private String scriptScadenza;
    private Date dataEventoDaChiudere;
    private String note;
    private String numeroProtocollo;
    private Date dataInizioScadenza;
    private Date dataFineScadenza;
    private String desScadenza;
    private String desEventoOrigine;
    private String desEnte;

    public int getIdScadenza() {
        return idScadenza;
    }

    public void setIdScadenza(int idScadenza) {
        this.idScadenza = idScadenza;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public int getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(int idPratica) {
        this.idPratica = idPratica;
    }

    public String getIdAnaScadenza() {
        return idAnaScadenza;
    }

    public void setIdAnaScadenza(String idAnaScadenza) {
        this.idAnaScadenza = idAnaScadenza;
    }

    public String getScriptScadenza() {
        return scriptScadenza;
    }

    public void setScriptScadenza(String scriptScadenza) {
        this.scriptScadenza = scriptScadenza;
    }

    public Date getDataEventoDaChiudere() {
        return dataEventoDaChiudere;
    }

    public void setDataEventoDaChiudere(Date dataEventoDaChiudere) {
        this.dataEventoDaChiudere = dataEventoDaChiudere;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNumeroProtocollo() {
        return numeroProtocollo;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {
        this.numeroProtocollo = numeroProtocollo;
    }

    public Date getDataInizioScadenza() {
        return dataInizioScadenza;
    }

    public void setDataInizioScadenza(Date dataInizioScadenza) {
        this.dataInizioScadenza = dataInizioScadenza;
    }

    public Date getDataFineScadenza() {
        return dataFineScadenza;
    }

    public void setDataFineScadenza(Date dataFineScadenza) {
        this.dataFineScadenza = dataFineScadenza;
    }

    public String getDesScadenza() {
        return desScadenza;
    }

    public void setDesScadenza(String desScadenza) {
        this.desScadenza = desScadenza;
    }

    public String getDesEventoOrigine() {
        return desEventoOrigine;
    }

    public void setDesEventoOrigine(String desEventoOrigine) {
        this.desEventoOrigine = desEventoOrigine;
    }

    public String getDesEnte() {
        return desEnte;
    }

    public void setDesEnte(String desEnte) {
        this.desEnte = desEnte;
    }
}
