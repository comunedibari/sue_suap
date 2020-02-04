/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans;

import java.io.Serializable;

/**
 *
 * @author giuseppe
 */
public class EGrammataFascicoloBean implements Serializable {

    private String idFascicolo;
    private String annoFascicolo;
    private String numeroFascicolo;
    private String numeroSottoFascicolo;

    public String getIdFascicolo() {
        return idFascicolo;
    }

    public void setIdFascicolo(String idFascicolo) {
        this.idFascicolo = idFascicolo;
    }

    public String getAnnoFascicolo() {
        return annoFascicolo;
    }

    public void setAnnoFascicolo(String annoFascicolo) {
        this.annoFascicolo = annoFascicolo;
    }

    public String getNumeroFascicolo() {
        return numeroFascicolo;
    }

    public void setNumeroFascicolo(String numeroFascicolo) {
        this.numeroFascicolo = numeroFascicolo;
    }

    public String getNumeroSottoFascicolo() {
        return numeroSottoFascicolo;
    }

    public void setNumeroSottoFascicolo(String numeroSottoFascicolo) {
        this.numeroSottoFascicolo = numeroSottoFascicolo;
    }
}
