package it.wego.cross.dto;

import it.wego.cross.entity.LkTipoRuolo;

/**
 *
 * @author CS
 */
public class TipoRuoloDTO {
    Integer idTipoRuolo;
    String descrizione;
    String codRuolo;

    public Integer getIdTipoRuolo() {
        return idTipoRuolo;
    }

    public void setIdTipoRuolo(Integer idTipoRuolo) {
        this.idTipoRuolo = idTipoRuolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCodRuolo() {
        return codRuolo;
    }

    public void setCodRuolo(String codRuolo) {
        this.codRuolo = codRuolo;
    }
    
}
