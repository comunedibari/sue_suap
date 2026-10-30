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
public class PraticaProtocolloDTO implements Serializable {

    private Integer idProtocollo;
    private String nFascicolo;
    private String codRegistro;
    private String nProtocollo;
    private String oggetto;
    private String enteRiferimento;
    private String identificativoPratica;
    private Date dataRicezione;
    private Date dataRiferimento;
    private Date dataFascicolo;
    private Date dataPresaInCaricoCross;
    private String stato;
    private String destinatario;
    private String mittente;
    private String desUtentePresaInCarico;
    private Integer idUtentePresaInCarico;
    private Integer annoRiferimento;
    private Integer annoFascicolo;
    private Integer idPratica;
    private String tipoDocumento;
    private String descPraticaProtocollo;

    public Integer getIdProtocollo() {
        return idProtocollo;
    }

    public void setIdProtocollo(Integer idProtocollo) {
        this.idProtocollo = idProtocollo;
    }

    public String getnFascicolo() {
        return nFascicolo;
    }

    public void setnFascicolo(String nFascicolo) {
        this.nFascicolo = nFascicolo;
    }

    public String getCodRegistro() {
        return codRegistro;
    }

    public void setCodRegistro(String codRegistro) {
        this.codRegistro = codRegistro;
    }

    public String getnProtocollo() {
        return nProtocollo;
    }

    public void setnProtocollo(String nProtocollo) {
        this.nProtocollo = nProtocollo;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getEnteRiferimento() {
        return enteRiferimento;
    }

    public void setEnteRiferimento(String enteRiferimento) {
        this.enteRiferimento = enteRiferimento;
    }

    public String getIdentificativoPratica() {
        return identificativoPratica;
    }

    public void setIdentificativoPratica(String identificativoPratica) {
        this.identificativoPratica = identificativoPratica;
    }

    public Date getDataRicezione() {
        return dataRicezione;
    }

    public void setDataRicezione(Date dataRicezione) {
        this.dataRicezione = dataRicezione;
    }

    public Date getDataRiferimento() {
        return dataRiferimento;
    }

    public void setDataRiferimento(Date dataRiferimento) {
        this.dataRiferimento = dataRiferimento;
    }

    public Date getDataPresaInCaricoCross() {
        return dataPresaInCaricoCross;
    }

    public void setDataPresaInCaricoCross(Date dataPresaInCaricoCross) {
        this.dataPresaInCaricoCross = dataPresaInCaricoCross;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getMittente() {
        return mittente;
    }

    public void setMittente(String mittente) {
        this.mittente = mittente;
    }

    public String getDesUtentePresaInCarico() {
        return desUtentePresaInCarico;
    }

    public void setDesUtentePresaInCarico(String desUtentePresaInCarico) {
        this.desUtentePresaInCarico = desUtentePresaInCarico;
    }

    public Integer getIdUtentePresaInCarico() {
        return idUtentePresaInCarico;
    }

    public void setIdUtentePresaInCarico(Integer idUtentePresaInCarico) {
        this.idUtentePresaInCarico = idUtentePresaInCarico;
    }

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public Integer getAnnoRiferimento() {
        return annoRiferimento;
    }

    public void setAnnoRiferimento(Integer annoRiferimento) {
        this.annoRiferimento = annoRiferimento;
    }

    public Integer getAnnoFascicolo() {
        return annoFascicolo;
    }

    public void setAnnoFascicolo(Integer annoFascicolo) {
        this.annoFascicolo = annoFascicolo;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDescPraticaProtocollo() {
        return descPraticaProtocollo;
    }

    public void setDescPraticaProtocollo(String descPraticaProtocollo) {
        this.descPraticaProtocollo = descPraticaProtocollo;
    }

    public Date getDataFascicolo() {
        return dataFascicolo;
    }

    public void setDataFascicolo(Date dataFascicolo) {
        this.dataFascicolo = dataFascicolo;
    }

}
