/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto;

import java.io.Serializable;

/**
 *
 * @author giuseppe
 */
public class StepDTO implements Serializable {

    private Integer idEventoTrigger;
    private String codEventoTrigger;
    private String desEventoTrigger;
    private Integer idEventoResult;
    private String codEventoResult;
    private String desEventoResult;
    private String operazione;

    public Integer getIdEventoTrigger() {
        return idEventoTrigger;
    }

    public void setIdEventoTrigger(Integer idEventoTrigger) {
        this.idEventoTrigger = idEventoTrigger;
    }

    public String getCodEventoTrigger() {
        return codEventoTrigger;
    }

    public void setCodEventoTrigger(String codEventoTrigger) {
        this.codEventoTrigger = codEventoTrigger;
    }

    public String getDesEventoTrigger() {
        return desEventoTrigger;
    }

    public void setDesEventoTrigger(String desEventoTrigger) {
        this.desEventoTrigger = desEventoTrigger;
    }

    public Integer getIdEventoResult() {
        return idEventoResult;
    }

    public void setIdEventoResult(Integer idEventoResult) {
        this.idEventoResult = idEventoResult;
    }

    public String getCodEventoResult() {
        return codEventoResult;
    }

    public void setCodEventoResult(String codEventoResult) {
        this.codEventoResult = codEventoResult;
    }

    public String getDesEventoResult() {
        return desEventoResult;
    }

    public void setDesEventoResult(String desEventoResult) {
        this.desEventoResult = desEventoResult;
    }

    public String getOperazione() {
        return operazione;
    }

    public void setOperazione(String operazione) {
        this.operazione = operazione;
    }
}
