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
public class TipoIndirizzoDTO implements Serializable {

    private Integer idTipoIndirizzo;
    private String codTipoIndirizzo;
    private String descTipoIndirizzo;

    public Integer getIdTipoIndirizzo() {
        return idTipoIndirizzo;
    }

    public void setIdTipoIndirizzo(Integer idTipoIndirizzo) {
        this.idTipoIndirizzo = idTipoIndirizzo;
    }

    public String getDescTipoIndirizzo() {
        return descTipoIndirizzo;
    }

    public void setDescTipoIndirizzo(String descTipoIndirizzo) {
        this.descTipoIndirizzo = descTipoIndirizzo;
    }

    public String getCodTipoIndirizzo() {
        return codTipoIndirizzo;
    }

    public void setCodTipoIndirizzo(String codTipoIndirizzo) {
        this.codTipoIndirizzo = codTipoIndirizzo;
    }
}
