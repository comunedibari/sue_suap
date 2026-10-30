/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author piergiorgio
 */
@Embeddable
public class ProcessiEventiAnagraficaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_evento")
    private int idEvento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_anagrafica")
    private int idAnagrafica;

    public ProcessiEventiAnagraficaPK() {
    }

    public ProcessiEventiAnagraficaPK(int idEvento, int idAnagrafica) {
        this.idEvento = idEvento;
        this.idAnagrafica = idAnagrafica;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public int getIdAnagrafica() {
        return idAnagrafica;
    }

    public void setIdAnagrafica(int idAnagrafica) {
        this.idAnagrafica = idAnagrafica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idEvento;
        hash += (int) idAnagrafica;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProcessiEventiAnagraficaPK)) {
            return false;
        }
        ProcessiEventiAnagraficaPK other = (ProcessiEventiAnagraficaPK) object;
        if (this.idEvento != other.idEvento) {
            return false;
        }
        if (this.idAnagrafica != other.idAnagrafica) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.ProcessiEventiAnagraficaPK[ idEvento=" + idEvento + ", idAnagrafica=" + idAnagrafica + " ]";
    }

}
