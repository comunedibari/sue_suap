/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.comunica;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author giuseppe
 */
public class SportelloDTO implements Serializable {

    @NotNull(message = "error.comunica.sportello.id")
    private Integer id;
    private String descrizione;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
