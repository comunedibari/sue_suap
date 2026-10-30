/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans;

import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.Recapiti;

/**
 *
 * @author giuseppe
 */
public class AnagraficaRecapito {

    private Anagrafica anagrafica;
    private Recapiti recapito;

    public AnagraficaRecapito() {
    }

    public AnagraficaRecapito(Anagrafica anagrafica, Recapiti recapito) {
        this.anagrafica = anagrafica;
        this.recapito = recapito;
    }

    public Anagrafica getAnagrafica() {
        return anagrafica;
    }

    public void setAnagrafica(Anagrafica anagrafica) {
        this.anagrafica = anagrafica;
    }

    public Recapiti getRecapito() {
        return recapito;
    }

    public void setRecapito(Recapiti recapito) {
        this.recapito = recapito;
    }
}
