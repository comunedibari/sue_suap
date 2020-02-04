/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

/**
 *
 * @author Gabriele
 */
public class ProcessoDTO {

    private Integer idProcesso;
    private String codProcesso;
    private String desProcesso;

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
}
