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
public class PraticaEventiProcedimentiPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_pratica_evento")
    private int idPraticaEvento;
    @Basic(optional = false)
    @Column(name = "id_proc_ente")
    private int idProcEnte;

    public PraticaEventiProcedimentiPK() {
    }

    public PraticaEventiProcedimentiPK(int idPraticaEvento, int idProcEnte) {
        this.idPraticaEvento = idPraticaEvento;
        this.idProcEnte = idProcEnte;
    }

    public int getIdPraticaEvento() {
        return idPraticaEvento;
    }

    public void setIdPraticaEvento(int idPraticaEvento) {
        this.idPraticaEvento = idPraticaEvento;
    }

    public int getIdProcEnte() {
        return idProcEnte;
    }

    public void setIdProcEnte(int idProcEnte) {
        this.idProcEnte = idProcEnte;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPraticaEvento;
        hash += (int) idProcEnte;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PraticaEventiProcedimentiPK)) {
            return false;
        }
        PraticaEventiProcedimentiPK other = (PraticaEventiProcedimentiPK) object;
        if (this.idPraticaEvento != other.idPraticaEvento) {
            return false;
        }
        if (this.idProcEnte != other.idProcEnte) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.PraticaEventoProcedimentiPK[ idPraticaEvento=" + idPraticaEvento + ", idProcEnte=" + idProcEnte  + " ]";
    }
    
}
