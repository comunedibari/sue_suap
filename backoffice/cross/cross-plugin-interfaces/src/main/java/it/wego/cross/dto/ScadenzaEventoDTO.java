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
public class ScadenzaEventoDTO implements Serializable {

    private Integer idEvento;
    private String idAnaScadenza;
    private String idStatoScadenza;
    private Integer terminiScadenza;
    private String scriptScadenza;
    private String flgVisualizzaScadenza;

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getIdAnaScadenza() {
        return idAnaScadenza;
    }

    public void setIdAnaScadenza(String idAnaScadenza) {
        this.idAnaScadenza = idAnaScadenza;
    }

    public String getIdStatoScadenza() {
        return idStatoScadenza;
    }

    public void setIdStatoScadenza(String idStatoScadenza) {
        this.idStatoScadenza = idStatoScadenza;
    }

    public Integer getTerminiScadenza() {
        return terminiScadenza;
    }

    public void setTerminiScadenza(Integer terminiScadenza) {
        this.terminiScadenza = terminiScadenza;
    }

    public String getScriptScadenza() {
        return scriptScadenza;
    }

    public void setScriptScadenza(String scriptScadenza) {
        this.scriptScadenza = scriptScadenza;
    }

    public String getFlgVisualizzaScadenza() {
        return flgVisualizzaScadenza;
    }

    public void setFlgVisualizzaScadenza(String flgVisualizzaScadenza) {
        this.flgVisualizzaScadenza = flgVisualizzaScadenza;
    }
}
