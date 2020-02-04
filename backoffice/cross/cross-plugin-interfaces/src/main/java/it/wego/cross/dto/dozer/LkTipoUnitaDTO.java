/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

/**
 *
 * @author piergiorgio
 */
public class LkTipoUnitaDTO {

    private Integer idTipoUnita;
    private String descrizione;
    private String codTipoUnita;

    public Integer getIdTipoUnita() {
        return idTipoUnita;
    }

    public void setIdTipoUnita(Integer idTipoUnita) {
        this.idTipoUnita = idTipoUnita;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCodTipoUnita() {
        return codTipoUnita;
    }

    public void setCodTipoUnita(String codTipoUnita) {
        this.codTipoUnita = codTipoUnita;
    }

}
