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

/**
 *
 * @author piergiorgio
 */
@Embeddable
public class PraticaPraticaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_pratica_a")
    private int idPraticaA;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_pratica_b")
    private int idPraticaB;

    public PraticaPraticaPK() {
    }

    public PraticaPraticaPK(int idPraticaA, int idPraticaB) {
        this.idPraticaA = idPraticaA;
        this.idPraticaB = idPraticaB;
    }

    public int getIdPraticaA() {
        return idPraticaA;
    }

    public void setIdPraticaA(int idPraticaA) {
        this.idPraticaA = idPraticaA;
    }

    public int getIdPraticaB() {
        return idPraticaB;
    }

    public void setIdPraticaB(int idPraticaB) {
        this.idPraticaB = idPraticaB;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPraticaA;
        hash += (int) idPraticaB;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PraticaPraticaPK)) {
            return false;
        }
        PraticaPraticaPK other = (PraticaPraticaPK) object;
        if (this.idPraticaA != other.idPraticaA) {
            return false;
        }
        if (this.idPraticaB != other.idPraticaB) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.PraticaPraticaPK[ idPraticaA=" + idPraticaA + ", idPraticaB=" + idPraticaB + " ]";
    }
    
}
