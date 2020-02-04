/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.anagrafetributaria.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author giuseppe
 */
public class AnagrafeTributariaCommercioListDTO implements Serializable {

    private List<AnagrafeTributariaCommercioDTO> anagrafiche;
    private Integer idPratica;
    private Integer idEvento;

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public List<AnagrafeTributariaCommercioDTO> getAnagrafiche() {
        return anagrafiche;
    }

    public void setAnagrafiche(List<AnagrafeTributariaCommercioDTO> anagrafiche) {
        this.anagrafiche = anagrafiche;
    }
}
