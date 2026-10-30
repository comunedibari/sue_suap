/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer.forms;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Gabriele
 */
public class SueInvioWsDTO extends StandardaFormDTO {

    private Integer idPratica;
    private Integer idEvento;
    private String codiceRegistro;
    private String codiceFascicolo;
    private Integer annoRiferimento;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataFascicolo;
    private String responsabileProcedimento;
    private String ufficioProcedimento;
    private Boolean invioSueWs=Boolean.TRUE;

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getCodiceRegistro() {
        return codiceRegistro;
    }

    public void setCodiceRegistro(String codiceRegistro) {
        this.codiceRegistro = codiceRegistro;
    }

    public String getCodiceFascicolo() {
        return codiceFascicolo;
    }

    public void setCodiceFascicolo(String codiceFascicolo) {
        this.codiceFascicolo = codiceFascicolo;
    }

    public Integer getAnnoRiferimento() {
        return annoRiferimento;
    }

    public void setAnnoRiferimento(Integer annoRiferimento) {
        this.annoRiferimento = annoRiferimento;
    }

    public Date getDataFascicolo() {
        return dataFascicolo;
    }

    public void setDataFascicolo(Date dataFascicolo) {
        this.dataFascicolo = dataFascicolo;
    }

    public String getResponsabileProcedimento() {
        return responsabileProcedimento;
    }

    public void setResponsabileProcedimento(String responsabileProcedimento) {
        this.responsabileProcedimento = responsabileProcedimento;
    }

    public String getUfficioProcedimento() {
        return ufficioProcedimento;
    }

    public void setUfficioProcedimento(String ufficioProcedimento) {
        this.ufficioProcedimento = ufficioProcedimento;
    }

    public Boolean getInvioSueWs() {
        return invioSueWs;
    }

    public void setInvioSueWs(Boolean invioSueWs) {
        this.invioSueWs = invioSueWs;
    }

}
