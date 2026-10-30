/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto;

/**
 *
 * @author piergiorgio
 */
public class AnagraficaRecapitoDTO {

    private AnagraficaDTO anagrafica;
    private RecapitoDTO recapito;

    public AnagraficaDTO getAnagrafica() {
        return anagrafica;
    }

    public void setAnagrafica(AnagraficaDTO anagrafica) {
        this.anagrafica = anagrafica;
    }

    public RecapitoDTO getRecapito() {
        return recapito;
    }

    public void setRecapito(RecapitoDTO recapito) {
        this.recapito = recapito;
    }
}
