/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.anagrafetributaria.bean;

import java.io.Serializable;

/**
 *
 * @author giuseppe
 */
public class IndirizzoRichiestaDTO implements Serializable {

    private Integer idRecapito;
    private String descrizione;

    public Integer getIdRecapito() {
        return idRecapito;
    }

    public void setIdRecapito(Integer idRecapito) {
        this.idRecapito = idRecapito;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
