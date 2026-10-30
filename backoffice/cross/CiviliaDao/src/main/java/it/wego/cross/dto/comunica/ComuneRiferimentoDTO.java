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
public class ComuneRiferimentoDTO implements Serializable {

    @NotNull(message = "error.comunica.comuneriferimento.id")
    private Integer id;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
