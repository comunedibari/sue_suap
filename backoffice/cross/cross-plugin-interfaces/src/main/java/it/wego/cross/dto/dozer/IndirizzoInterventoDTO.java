/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

import java.io.Serializable;

/**
 *
 * @author giuseppe
 */
public class IndirizzoInterventoDTO implements Serializable {

    private Integer counter;
    private Integer idIndirizzoIntervento;
    private String localita;
    private String indirizzo;
    private String civico;
    private String cap;
    private String altreInformazioniIndirizzo;
    private String codCivico;
    private String codVia;
    private String internoNumero;
    private String internoLettera;
    private String internoScala;
    private String lettera;
    private String colore;
    private Integer idDug;
    private String desDug; 
    private Integer idPratica;
    private String latitudine;
    private String longitudine;
    private String datoEsteso1;
    private String datoEsteso2;
    private String urlCatasto;
    private String confermato;
    private String piano;
    private String foglio;
    private String mappale;

    public String getFoglio() {
        return foglio;
    }

    public void setFoglio(String foglio) {
        this.foglio = foglio;
    }

    public String getMappale() {
        return mappale;
    }

    public void setMappale(String mappale) {
        this.mappale = mappale;
    }

    public Integer getIdIndirizzoIntervento() {
        return idIndirizzoIntervento;
    }

    public void setIdIndirizzoIntervento(Integer idIndirizzoIntervento) {
        this.idIndirizzoIntervento = idIndirizzoIntervento;
    }

    public String getLocalita() {
        return localita;
    }

    public void setLocalita(String localita) {
        this.localita = localita;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getAltreInformazioniIndirizzo() {
        return altreInformazioniIndirizzo;
    }

    public void setAltreInformazioniIndirizzo(String altreInformazioniIndirizzo) {
        this.altreInformazioniIndirizzo = altreInformazioniIndirizzo;
    }

    public String getCodCivico() {
        return codCivico;
    }

    public void setCodCivico(String codCivico) {
        this.codCivico = codCivico;
    }

    public String getCodVia() {
        return codVia;
    }

    public void setCodVia(String codVia) {
        this.codVia = codVia;
    }

    public String getInternoNumero() {
        return internoNumero;
    }

    public void setInternoNumero(String internoNumero) {
        this.internoNumero = internoNumero;
    }

    public String getInternoLettera() {
        return internoLettera;
    }

    public void setInternoLettera(String internoLettera) {
        this.internoLettera = internoLettera;
    }

    public String getInternoScala() {
        return internoScala;
    }

    public void setInternoScala(String internoScala) {
        this.internoScala = internoScala;
    }

    public String getLettera() {
        return lettera;
    }

    public void setLettera(String lettera) {
        this.lettera = lettera;
    }

    public String getColore() {
        return colore;
    }

    public void setColore(String colore) {
        this.colore = colore;
    }

    public Integer getIdDug() {
        return idDug;
    }

    public void setIdDug(Integer idDug) {
        this.idDug = idDug;
    }

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public String getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(String latitudine) {
        this.latitudine = latitudine;
    }

    public String getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(String longitudine) {
        this.longitudine = longitudine;
    }

    public String getDatoEsteso1() {
        return datoEsteso1;
    }

    public void setDatoEsteso1(String datoEsteso1) {
        this.datoEsteso1 = datoEsteso1;
    }

    public String getDatoEsteso2() {
        return datoEsteso2;
    }

    public void setDatoEsteso2(String datoEsteso2) {
        this.datoEsteso2 = datoEsteso2;
    }

    public String getUrlCatasto() {
        return urlCatasto;
    }

    public void setUrlCatasto(String urlCatasto) {
        this.urlCatasto = urlCatasto;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public String getConfermato() {
        return confermato;
    }

    public void setConfermato(String confermato) {
        this.confermato = confermato;
    }

    public String getDesDug() {
        return desDug;
    }

    public void setDesDug(String desDug) {
        this.desDug = desDug;
    }

    public String getPiano() {
        return piano;
    }

    public void setPiano(String piano) {
        this.piano = piano;
    }
}
