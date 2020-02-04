/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.anagrafetributaria.bean;

import java.io.Serializable;

/**
 *
 * @author Gabriele
 */
public class AnagrafeTributariaBeneficiarioDTO implements Serializable {

    private Integer idAnagrafica;
    private String codQualificaBeneficiario;

    public Integer getIdAnagrafica() {
        return idAnagrafica;
    }

    public void setIdAnagrafica(Integer idAnagrafica) {
        this.idAnagrafica = idAnagrafica;
    }

    public String getCodQualificaBeneficiario() {
        return codQualificaBeneficiario;
    }

    public void setCodQualificaBeneficiario(String codQualificaBeneficiario) {
        this.codQualificaBeneficiario = codQualificaBeneficiario;
    }
}
