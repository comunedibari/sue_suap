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
public class RuoloAnagraficaDTO implements Serializable {

    private String desTipoRuolo;
    private Integer idTipoRuolo;

    public String getDesTipoRuolo() {
        return desTipoRuolo;
    }

    public void setDesTipoRuolo(String desTipoRuolo) {
        this.desTipoRuolo = desTipoRuolo;
    }

    public Integer getIdTipoRuolo() {
        return idTipoRuolo;
    }

    public void setIdTipoRuolo(Integer idTipoRuolo) {
        this.idTipoRuolo = idTipoRuolo;
    }
}
