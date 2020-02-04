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
public class ProcessiEventiEntiPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_evento")
    private int idEvento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_ente")
    private int idEnte;

    public ProcessiEventiEntiPK() {
    }

    public ProcessiEventiEntiPK(int idEvento, int idEnte) {
        this.idEvento = idEvento;
        this.idEnte = idEnte;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public int getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(int idEnte) {
        this.idEnte = idEnte;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idEvento;
        hash += (int) idEnte;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProcessiEventiEntiPK)) {
            return false;
        }
        ProcessiEventiEntiPK other = (ProcessiEventiEntiPK) object;
        if (this.idEvento != other.idEvento) {
            return false;
        }
        if (this.idEnte != other.idEnte) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.ProcessiEventiEntiPK[ idEvento=" + idEvento + ", idEnte=" + idEnte +  " ]";
    }
    
}
