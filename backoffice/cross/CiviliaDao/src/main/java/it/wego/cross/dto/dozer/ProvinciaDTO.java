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
public class ProvinciaDTO {

    private Integer idProvincie;
    private String descrizione;
    private String codCatastale;
    private Date dataFineValidita;
    private String flgInfocamere;

    public Integer getIdProvincie() {
        return idProvincie;
    }

    public void setIdProvincie(Integer idProvincie) {
        this.idProvincie = idProvincie;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCodCatastale() {
        return codCatastale;
    }

    public void setCodCatastale(String codCatastale) {
        this.codCatastale = codCatastale;
    }

    public Date getDataFineValidita() {
        return dataFineValidita;
    }

    public void setDataFineValidita(Date dataFineValidita) {
        this.dataFineValidita = dataFineValidita;
    }

    public String getFlgInfocamere() {
        return flgInfocamere;
    }

    public void setFlgInfocamere(String flgInfocamere) {
        this.flgInfocamere = flgInfocamere;
    }
}
