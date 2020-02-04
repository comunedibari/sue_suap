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
public class TipologiaScadenzeDTO implements Serializable {

    private String idAnaScadenza;
    private String desAnaScadenza;
    private String flgScadenzaPratica;

    public String getIdAnaScadenza() {
        return idAnaScadenza;
    }

    public void setIdAnaScadenza(String idAnaScadenza) {
        this.idAnaScadenza = idAnaScadenza;
    }

    public String getDesAnaScadenza() {
        return desAnaScadenza;
    }

    public void setDesAnaScadenza(String desAnaScadenza) {
        this.desAnaScadenza = desAnaScadenza;
    }

    public String getFlgScadenzaPratica() {
        return flgScadenzaPratica;
    }

    public void setFlgScadenzaPratica(String flgScadenzaPRatica) {
        this.flgScadenzaPratica = flgScadenzaPRatica;
    }
}
