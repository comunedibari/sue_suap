/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.registroimprese;

import java.io.Serializable;

/**
 *
 * @author giuseppe
 */
public class ErroreRIDTO implements Serializable {

    private String codice;
    private String valore;

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = valore;
    }
}
