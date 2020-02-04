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
public class RichiedenteDTO implements Serializable {

    private Integer idAnagrafica;
    private String descrizione;
    private Integer idQualifica;

    public Integer getIdAnagrafica() {
        return idAnagrafica;
    }

    public void setIdAnagrafica(Integer idAnagrafica) {
        this.idAnagrafica = idAnagrafica;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Integer getIdQualifica() {
        return idQualifica;
    }

    public void setIdQualifica(Integer idQualifica) {
        this.idQualifica = idQualifica;
    }

}
