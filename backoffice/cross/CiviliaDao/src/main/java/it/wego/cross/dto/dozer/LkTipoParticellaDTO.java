/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

/**
 *
 * @author piergiorgio
 */
public class LkTipoParticellaDTO {

    private Integer idTipoParticella;
    private String descrizione;
    private String codTipoParticella;

    public Integer getIdTipoParticella() {
        return idTipoParticella;
    }

    public void setIdTipoParticella(Integer idTipoParticella) {
        this.idTipoParticella = idTipoParticella;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCodTipoParticella() {
        return codTipoParticella;
    }

    public void setCodTipoParticella(String codTipoParticella) {
        this.codTipoParticella = codTipoParticella;
    }

}
