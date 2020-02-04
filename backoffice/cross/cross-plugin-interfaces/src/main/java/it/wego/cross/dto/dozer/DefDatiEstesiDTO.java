package it.wego.cross.dto.dozer;

import it.wego.cross.entity.LkTipoOggetto;

public class DefDatiEstesiDTO {

    private Integer idDatiEstesi;
    private String idIstanza;
    private String codValue;
    private String value;
    private Integer idTipoOggetto;
    private String codTipoOggetto;
    private String desTipoOggetto;

    public Integer getIdDatiEstesi() {
        return idDatiEstesi;
    }

    public void setIdDatiEstesi(Integer idDatiEstesi) {
        this.idDatiEstesi = idDatiEstesi;
    }

    public String getIdIstanza() {
        return idIstanza;
    }

    public void setIdIstanza(String idIstanza) {
        this.idIstanza = idIstanza;
    }

    public String getCodValue() {
        return codValue;
    }

    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getIdTipoOggetto() {
        return idTipoOggetto;
    }

    public void setIdTipoOggetto(Integer idTipoOggetto) {
        this.idTipoOggetto = idTipoOggetto;
    }

    public String getCodTipoOggetto() {
        return codTipoOggetto;
    }

    public void setCodTipoOggetto(String codTipoOggetto) {
        this.codTipoOggetto = codTipoOggetto;
    }

    public String getDesTipoOggetto() {
        return desTipoOggetto;
    }

    public void setDesTipoOggetto(String desTipoOggetto) {
        this.desTipoOggetto = desTipoOggetto;
    }
}
