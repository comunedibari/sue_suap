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
public class RelazioniAnagrafichePK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_ana_1")
    private int idAna1;
    @Basic(optional = false)
    @Column(name = "id_ana_2")
    private int idAna2;

    public RelazioniAnagrafichePK() {
    }

    public RelazioniAnagrafichePK(int idAna1, int idAna2) {
        this.idAna1 = idAna1;
        this.idAna2 = idAna2;
    }

    public int getIdAna1() {
        return idAna1;
    }

    public void setIdAna1(int idAna1) {
        this.idAna1 = idAna1;
    }

    public int getIdAna2() {
        return idAna2;
    }

    public void setIdAna2(int idAna2) {
        this.idAna2 = idAna2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idAna1;
        hash += (int) idAna2;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelazioniAnagrafichePK)) {
            return false;
        }
        RelazioniAnagrafichePK other = (RelazioniAnagrafichePK) object;
        if (this.idAna1 != other.idAna1) {
            return false;
        }
        if (this.idAna2 != other.idAna2) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.RelazioniAnagrafichePK[ idAna1=" + idAna1 + ", idAna2=" + idAna2 + " ]";
    }
    
}
