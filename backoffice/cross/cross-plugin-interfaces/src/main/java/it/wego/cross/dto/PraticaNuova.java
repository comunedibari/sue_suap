/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author CS
 */
public class PraticaNuova extends AbstractDTO implements Serializable {

    private String idPratica;
    private Date dataRicezione;
    private String descPratica;
    private String tipoPratica;
    private Integer codiceUtente;
    private String protocollo;
    private String fascicolo;
    private String ente;
    private String inCarico;
    private String oggettoPratica;
    private String statoPratica;
    private Date dataScadenza;
    private String comune;
    private String richiedente;
    private String richiedenteDaXml;
    private String statoEmail;
    private Integer idStatoEmail;
    private String codStatoEmail;
    private String identificativoEsterno;
    private String identificativoPratica;
    private Boolean adminOnProcEnte;
    private Boolean isSameUser;
    private String integrazione;

    public Date getDataRicezione() {
        return dataRicezione;
    }

    public void setDataRicezione(Date dataRicezione) {
        this.dataRicezione = dataRicezione;
    }

    public String getDescPratica() {
        return descPratica;
    }

    public void setDescPratica(String descPratica) {
        this.descPratica = descPratica;
    }

    public String getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(String idPratica) {
        this.idPratica = idPratica;
    }

    public String getTipoPratica() {
        return tipoPratica;
    }

    public void setTipoPratica(String tipoPratica) {
        this.tipoPratica = tipoPratica;
    }

    public Integer getCodiceUtente() {
        return codiceUtente;
    }

    public void setCodiceUtente(Integer codiceUtente) {
        this.codiceUtente = codiceUtente;
    }

    @Deprecated
    public String getProtocollo() {
        return protocollo;
    }

    @Deprecated
    public void setProtocollo(String protocollo) {
        this.protocollo = protocollo;
    }

    public String getEnte() {
        return ente;
    }

    public void setEnte(String ente) {
        this.ente = ente;
    }

    public String getInCarico() {
        return inCarico;
    }

    public void setInCarico(String inCarico) {
        this.inCarico = inCarico;
    }

    public String getOggettoPratica() {
        return oggettoPratica;
    }

    public void setOggettoPratica(String oggettoPratica) {
        this.oggettoPratica = oggettoPratica;
    }

    public String getStatoPratica() {
        return statoPratica;
    }

    public void setStatoPratica(String statoPratica) {
        this.statoPratica = statoPratica;
    }

    public Date getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public String getRichiedente() {
        return richiedente;
    }

    public void setRichiedente(String richiedente) {
        this.richiedente = richiedente;
    }

    public String getStatoEmail() {
        return statoEmail;
    }

    public void setStatoEmail(String statoEmail) {
        this.statoEmail = statoEmail;
    }

    public Integer getIdStatoEmail() {
        return idStatoEmail;
    }

    public void setIdStatoEmail(Integer idStatoEmail) {
        this.idStatoEmail = idStatoEmail;
    }

    public String getCodStatoEmail() {
        return codStatoEmail;
    }

    public void setCodStatoEmail(String codStatoEmail) {
        this.codStatoEmail = codStatoEmail;
    }

    public String getIdentificativoEsterno() {
        return identificativoEsterno;
    }

    public void setIdentificativoEsterno(String identificativoEsterno) {
        this.identificativoEsterno = identificativoEsterno;
    }

    public String getIdentificativoPratica() {
        return identificativoPratica;
    }

    public void setIdentificativoPratica(String identificativoPratica) {
        this.identificativoPratica = identificativoPratica;
    }

    public String getFascicolo() {
        return fascicolo;
    }

    public void setFascicolo(String fascicolo) {
        this.fascicolo = fascicolo;
    }

    public Boolean getAdminOnProcEnte() {
        return adminOnProcEnte;
    }

    public void setAdminOnProcEnte(Boolean adminOnProcEnte) {
        this.adminOnProcEnte = adminOnProcEnte;
    }

    public Boolean getIsSameUser() {
        return isSameUser;
    }

    public void setIsSameUser(Boolean isSameUser) {
        this.isSameUser = isSameUser;
    }

    public String getRichiedenteDaXml() {
        return richiedenteDaXml;
    }

    public void setRichiedenteDaXml(String richiedenteDaXml) {
        this.richiedenteDaXml = richiedenteDaXml;
    }

	public String getIntegrazione() {
		return integrazione;
	}

	public void setIntegrazione(String integrazione) {
		this.integrazione = integrazione;
	}
    
    

}
