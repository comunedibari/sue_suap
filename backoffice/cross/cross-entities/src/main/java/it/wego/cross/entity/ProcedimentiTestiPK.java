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
public class ProcedimentiTestiPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_proc")
    private int idProc;
    @Basic(optional = false)
    @Column(name = "id_lang")
    private int idLang;

    public ProcedimentiTestiPK() {
    }

    public ProcedimentiTestiPK(int idProc, int idLang) {
        this.idProc = idProc;
        this.idLang = idLang;
    }

    public int getIdProc() {
        return idProc;
    }

    public void setIdProc(int idProc) {
        this.idProc = idProc;
    }

    public int getIdLang() {
        return idLang;
    }

    public void setIdLang(int idLang) {
        this.idLang = idLang;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idProc;
        hash += (int) idLang;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProcedimentiTestiPK)) {
            return false;
        }
        ProcedimentiTestiPK other = (ProcedimentiTestiPK) object;
        if (this.idProc != other.idProc) {
            return false;
        }
        if (this.idLang != other.idLang) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.ProcedimentiTestiPK[ idProc=" + idProc + ", idLang=" + idLang + " ]";
    }
    
}
