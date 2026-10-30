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
public class StatoDTO {

    private Integer idStato;
    private String descrizione;
    private String codIstat;
    private Date dataInizio;
    private Date dataFine;
    private String codStato;

    public Integer getIdStato() {
        return idStato;
    }

    public void setIdStato(Integer idStato) {
        this.idStato = idStato;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCodIstat() {
        return codIstat;
    }

    public void setCodIstat(String codIstat) {
        this.codIstat = codIstat;
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

    public String getCodStato() {
        return codStato;
    }

    public void setCodStato(String codStato) {
        this.codStato = codStato;
    }
}
