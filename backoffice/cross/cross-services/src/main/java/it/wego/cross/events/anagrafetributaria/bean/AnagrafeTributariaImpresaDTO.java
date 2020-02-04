/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.anagrafetributaria.bean;

import java.io.Serializable;

/**
 *
 * @author Gabriele
 */
public class AnagrafeTributariaImpresaDTO implements Serializable {

    private Integer idAnagrafica;
    private Integer idRecapitoSedeLegale;

    public Integer getIdAnagrafica() {
        return idAnagrafica;
    }

    public void setIdAnagrafica(Integer idAnagrafica) {
        this.idAnagrafica = idAnagrafica;
    }

    public Integer getIdRecapitoSedeLegale() {
        return idRecapitoSedeLegale;
    }

    public void setIdRecapitoSedeLegale(Integer idRecapitoSedeLegale) {
        this.idRecapitoSedeLegale = idRecapitoSedeLegale;
    }
}
