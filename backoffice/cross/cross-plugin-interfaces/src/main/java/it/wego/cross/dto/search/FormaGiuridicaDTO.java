/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.search;

import java.io.Serializable;

/**
 *
 * @author giuseppe
 */
public class FormaGiuridicaDTO implements Serializable {

    private Integer idFormaGiuridica;
    private String codFormaGiuridica;
    private String descrizione;

    public Integer getIdFormaGiuridica() {
        return idFormaGiuridica;
    }

    public void setIdFormaGiuridica(Integer idFormaGiuridica) {
        this.idFormaGiuridica = idFormaGiuridica;
    }

    public String getCodFormaGiuridica() {
        return codFormaGiuridica;
    }

    public void setCodFormaGiuridica(String codFormaGiuridica) {
        this.codFormaGiuridica = codFormaGiuridica;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
