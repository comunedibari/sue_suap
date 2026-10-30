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
public class InterventoDTO implements Serializable {

    private String descrizione;
    @NotNull(message = "error.comunica.interventi.id")
    private Integer id;
    private Integer idEnte;
    private String descrizioneEnte;

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(Integer idEnte) {
        this.idEnte = idEnte;
    }

    public String getDescrizioneEnte() {
        return descrizioneEnte;
    }

    public void setDescrizioneEnte(String descrizioneEnte) {
        this.descrizioneEnte = descrizioneEnte;
    }
}
