/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto;

import java.util.Date;

/**
 *
 * @author GabrieleM
 */
public class PraticheEventiRidDTO {

    private Integer idPraticaEvento;
    private Date dataEvento;
    private String descrizioneEvento;
    private String protocollo;
    private Date dataProtocollo;
    private String verso;
    private String denominazioneUtente;
    private String statoMail;
    private Integer idStatoMail;
    private String codStatoMail;
    private String soggetti;

    public Integer getIdPraticaEvento() {
        return idPraticaEvento;
    }

    public void setIdPraticaEvento(Integer idPraticaEvento) {
        this.idPraticaEvento = idPraticaEvento;
    }

    public Date getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(Date dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getDescrizioneEvento() {
        return descrizioneEvento;
    }

    public void setDescrizioneEvento(String descrizioneEvento) {
        this.descrizioneEvento = descrizioneEvento;
    }

    public String getProtocollo() {
        return protocollo;
    }

    public void setProtocollo(String protocollo) {
        this.protocollo = protocollo;
    }

    public Date getDataProtocollo() {
        return dataProtocollo;
    }

    public void setDataProtocollo(Date dataProtocollo) {
        this.dataProtocollo = dataProtocollo;
    }

    public String getVerso() {
        return verso;
    }

    public void setVerso(String verso) {
        this.verso = verso;
    }

    public String getDenominazioneUtente() {
        return denominazioneUtente;
    }

    public void setDenominazioneUtente(String denominazioneUtente) {
        this.denominazioneUtente = denominazioneUtente;
    }

    public String getStatoMail() {
        return statoMail;
    }

    public void setStatoMail(String statoMail) {
        this.statoMail = statoMail;
    }

    public Integer getIdStatoMail() {
        return idStatoMail;
    }

    public void setIdStatoMail(Integer idStatoMail) {
        this.idStatoMail = idStatoMail;
    }

    public String getCodStatoMail() {
        return codStatoMail;
    }

    public void setCodStatoMail(String codStatoMail) {
        this.codStatoMail = codStatoMail;
    }

    public String getSoggetti() {
        return soggetti;
    }

    public void setSoggetti(String soggetti) {
        this.soggetti = soggetti;
    }

}
