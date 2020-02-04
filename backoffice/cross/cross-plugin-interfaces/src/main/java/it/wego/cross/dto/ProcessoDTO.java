package it.wego.cross.dto;

import java.io.Serializable;

/**
 * @author CS
 */
public class ProcessoDTO extends AbstractDTO implements Serializable {

    private Integer idProcesso;
    private Integer idEnte;
    private String codProcesso;
    private String desProcesso;
    private Boolean selezionato;

    public Boolean getSelezionato() {
        return selezionato;
    }

    public void setSelezionato(Boolean selezionato) {
        this.selezionato = selezionato;
    }

    public Integer getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Integer idProcesso) {
        this.idProcesso = idProcesso;
    }

    public String getCodProcesso() {
        return codProcesso;
    }

    public void setCodProcesso(String codProcesso) {
        this.codProcesso = codProcesso;
    }

    public String getDesProcesso() {
        return desProcesso;
    }

    public void setDesProcesso(String desProcesso) {
        this.desProcesso = desProcesso;
    }

    public Integer getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(Integer idEnte) {
        this.idEnte = idEnte;
    }
}
