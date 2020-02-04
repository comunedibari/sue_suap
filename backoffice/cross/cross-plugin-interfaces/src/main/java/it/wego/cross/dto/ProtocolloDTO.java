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
public class ProtocolloDTO implements Serializable {

    //^^CS AGGIUNTA
    private Integer IdPratica;
    private Integer IdProtocollo;
    private String fascicolo;
    private String protocollo;
    private String oggetto;
    private String registro;
    private Date dataProtocollazione;
    private Date dataRicezione;
    private Integer anno;

    public String getFascicolo() {
        return fascicolo;
    }

    public void setFascicolo(String fascicolo) {
        this.fascicolo = fascicolo;
    }

    public String getProtocollo() {
        return protocollo;
    }

    public void setProtocollo(String protocollo) {
        this.protocollo = protocollo;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public Date getDataProtocollazione() {
        return dataProtocollazione;
    }

    public void setDataProtocollazione(Date dataProtocollazione) {
        this.dataProtocollazione = dataProtocollazione;
    }

    public Date getDataRicezione() {
        return dataRicezione;
    }

    public void setDataRicezione(Date dataRicezione) {
        this.dataRicezione = dataRicezione;
    }

    public Integer getAnno() {
        return anno;
    }

    public void setAnno(Integer anno) {
        this.anno = anno;
    }

    public Integer getIdPratica() {
        return IdPratica;
    }

    public void setIdPratica(Integer IdPratica) {
        this.IdPratica = IdPratica;
    }

    public Integer getIdProtocollo() {
        return IdProtocollo;
    }

    public void setIdProtocollo(Integer IdProtocollo) {
        this.IdProtocollo = IdProtocollo;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }
    
}
