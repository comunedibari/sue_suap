/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

/**
 *
 * @author Giuseppe
 */
public class RecapitoAnagraficaDTO {

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
