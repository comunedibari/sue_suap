/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author giuseppe
 */
public class ComunicazioneGenovaDTO implements Serializable {

    @NotNull
    private String tipoPraticaEdilizia;
    @NotNull
    @Size(max = 5)
    private String numeroPraticaEdilizia;
    @NotNull
    @Size(max = 4, min = 4)
    private String annoPraticaEdilizia;
    @NotNull
    @Size(max = 10)
    private Date dataProtocolloGenerale;
    @NotNull
    @Size(max = 15)
    private String numeroProtocolloGenerale;
    private String codiceEvento;
    private String codiceEsito;
    @NotNull
    @Size(max = 10)
    private Date dataInizio;
    @Size(max = 10)
    private Date dataFine;
    @Size(max = 1024)
    private String note;
    private List<AllegatoComunicazioneGenovaDTO> allegati;

    public String getAnnoPraticaEdilizia() {
        return annoPraticaEdilizia;
    }

    public void setAnnoPraticaEdilizia(String annoPraticaEdilizia) {
        this.annoPraticaEdilizia = annoPraticaEdilizia;
    }

    public String getTipoPraticaEdilizia() {
        return tipoPraticaEdilizia;
    }

    public void setTipoPraticaEdilizia(String tipoPraticaEdilizia) {
        this.tipoPraticaEdilizia = tipoPraticaEdilizia;
    }

    public String getNumeroPraticaEdilizia() {
        return numeroPraticaEdilizia;
    }

    public void setNumeroPraticaEdilizia(String numeroPraticaEdilizia) {
        this.numeroPraticaEdilizia = numeroPraticaEdilizia;
    }

    public Date getDataProtocolloGenerale() {
        return dataProtocolloGenerale;
    }

    public void setDataProtocolloGenerale(Date dataProtocolloGenerale) {
        this.dataProtocolloGenerale = dataProtocolloGenerale;
    }

    public String getNumeroProtocolloGenerale() {
        return numeroProtocolloGenerale;
    }

    public void setNumeroProtocolloGenerale(String numeroProtocolloGenerale) {
        this.numeroProtocolloGenerale = numeroProtocolloGenerale;
    }

    public String getCodiceEvento() {
        return codiceEvento;
    }

    public void setCodiceEvento(String codiceEvento) {
        this.codiceEvento = codiceEvento;
    }

    public String getCodiceEsito() {
        return codiceEsito;
    }

    public void setCodiceEsito(String codiceEsito) {
        this.codiceEsito = codiceEsito;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<AllegatoComunicazioneGenovaDTO> getAllegati() {
        return allegati;
    }

    public void setAllegati(List<AllegatoComunicazioneGenovaDTO> allegati) {
        this.allegati = allegati;
    }
}
