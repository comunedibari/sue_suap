/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.webservices.cxf.interoperability;

import java.util.Date;

/**
 *
 * @author giuseppe
 */
public class Evento {

    private Integer idEvento;
    private Integer idPratica;
    private String identificativoPratica;
    private String numeroProtocollo;
    private Date dataProtocollo;
    private String codiceEnte;
    private String codiceEvento;
    private String descrizioneEvento;
    private Comunicazione comunicazione;
    private Allegati allegati;
    private Soggetti soggetto;
    private Date dataEvento;

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public String getIdentificativoPratica() {
        return identificativoPratica;
    }

    public void setIdentificativoPratica(String identificativoPratica) {
        this.identificativoPratica = identificativoPratica;
    }

    public String getNumeroProtocollo() {
        return numeroProtocollo;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {
        this.numeroProtocollo = numeroProtocollo;
    }

    public Date getDataProtocollo() {
        return dataProtocollo;
    }

    public void setDataProtocollo(Date dataProtocollo) {
        this.dataProtocollo = dataProtocollo;
    }

    public String getCodiceEnte() {
        return codiceEnte;
    }

    public void setCodiceEnte(String codiceEnte) {
        this.codiceEnte = codiceEnte;
    }

    public String getCodiceEvento() {
        return codiceEvento;
    }

    public void setCodiceEvento(String codiceEvento) {
        this.codiceEvento = codiceEvento;
    }

    public String getDescrizioneEvento() {
        return descrizioneEvento;
    }

    public void setDescrizioneEvento(String descrizioneEvento) {
        this.descrizioneEvento = descrizioneEvento;
    }

    public Comunicazione getComunicazione() {
        return comunicazione;
    }

    public void setComunicazione(Comunicazione comunicazione) {
        this.comunicazione = comunicazione;
    }

    public Allegati getAllegati() {
        return allegati;
    }

    public void setAllegati(Allegati allegati) {
        this.allegati = allegati;
    }

    public Soggetti getSoggetto() {
        return soggetto;
    }

    public void setSoggetto(Soggetti soggetto) {
        this.soggetto = soggetto;
    }

    public Date getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(Date dataEvento) {
        this.dataEvento = dataEvento;
    }
}
