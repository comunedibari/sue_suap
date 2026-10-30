/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.search;

/**
 *
 * @author giuseppe
 */
public class TipoCollegioDTO {

    private Integer idTipoCollegio;
    private String descrizione;
    private String codCollegio;

    public Integer getIdTipoCollegio() {
        return idTipoCollegio;
    }

    public void setIdTipoCollegio(Integer idTipoCollegio) {
        this.idTipoCollegio = idTipoCollegio;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCodCollegio() {
        return codCollegio;
    }

    public void setCodCollegio(String codCollegio) {
        this.codCollegio = codCollegio;
    }
}
