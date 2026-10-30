/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author giuseppe
 */
@Embeddable
public class ProcessiStepsPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_evento_trigger")
    private int idEventoTrigger;
    @Basic(optional = false)
    @Column(name = "id_evento_result")
    private int idEventoResult;

    public ProcessiStepsPK() {
    }

    public ProcessiStepsPK(int idEventoTrigger, int idEventoResult) {
        this.idEventoTrigger = idEventoTrigger;
        this.idEventoResult = idEventoResult;
    }

    public int getIdEventoTrigger() {
        return idEventoTrigger;
    }

    public void setIdEventoTrigger(int idEventoTrigger) {
        this.idEventoTrigger = idEventoTrigger;
    }

    public int getIdEventoResult() {
        return idEventoResult;
    }

    public void setIdEventoResult(int idEventoResult) {
        this.idEventoResult = idEventoResult;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idEventoTrigger;
        hash += (int) idEventoResult;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProcessiStepsPK)) {
            return false;
        }
        ProcessiStepsPK other = (ProcessiStepsPK) object;
        if (this.idEventoTrigger != other.idEventoTrigger) {
            return false;
        }
        if (this.idEventoResult != other.idEventoResult) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.ProcessiStepsPK[ idEventoTrigger=" + idEventoTrigger + ", idEventoResult=" + idEventoResult + " ]";
    }
    
}
