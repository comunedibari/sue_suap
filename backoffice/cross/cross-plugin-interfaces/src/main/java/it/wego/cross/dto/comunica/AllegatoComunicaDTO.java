/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.comunica;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author giuseppe
 */
public class AllegatoComunicaDTO implements Serializable {

    @NotNull(message = "error.comunica.allegati.descrizione")
    private String descrizione;
    @NotNull(message = "error.comunica.allegati.file")
    private MultipartFile file;
    private Integer idAllegato;

    public Integer getIdAllegato() {
        return idAllegato;
    }

    public void setIdAllegato(Integer idAllegato) {
        this.idAllegato = idAllegato;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
