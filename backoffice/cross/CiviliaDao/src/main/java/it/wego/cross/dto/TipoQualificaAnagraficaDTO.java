/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto;

import java.io.Serializable;

/**
 *
 * @author giuseppe
 */
public class TipoQualificaAnagraficaDTO implements Serializable {

    private Integer idTipoQualifica;
    private String desTipoQualifica;

    public Integer getIdTipoQualifica() {
        return idTipoQualifica;
    }

    public void setIdTipoQualifica(Integer idTipoQualifica) {
        this.idTipoQualifica = idTipoQualifica;
    }

    public String getDesTipoQualifica() {
        return desTipoQualifica;
    }

    public void setDesTipoQualifica(String desTipoQualifica) {
        this.desTipoQualifica = desTipoQualifica;
    }
}
