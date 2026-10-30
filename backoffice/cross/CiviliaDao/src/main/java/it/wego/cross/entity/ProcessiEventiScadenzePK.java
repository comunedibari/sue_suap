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
public class ProcessiEventiScadenzePK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_evento")
    private int idEvento;
    @Basic(optional = false)
    @Column(name = "id_ana_scadenza")
    private String idAnaScadenza;

    public ProcessiEventiScadenzePK() {
    }

    public ProcessiEventiScadenzePK(int idEvento, String idAnaScadenza) {
        this.idEvento = idEvento;
        this.idAnaScadenza = idAnaScadenza;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getIdAnaScadenza() {
        return idAnaScadenza;
    }

    public void setIdAnaScadenza(String idAnaScadenza) {
        this.idAnaScadenza = idAnaScadenza;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idEvento;
        hash += (idAnaScadenza != null ? idAnaScadenza.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProcessiEventiScadenzePK)) {
            return false;
        }
        ProcessiEventiScadenzePK other = (ProcessiEventiScadenzePK) object;
        if (this.idEvento != other.idEvento) {
            return false;
        }
        if ((this.idAnaScadenza == null && other.idAnaScadenza != null) || (this.idAnaScadenza != null && !this.idAnaScadenza.equals(other.idAnaScadenza))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.ProcessiEventiScadenzePK[ idEvento=" + idEvento + ", idAnaScadenza=" + idAnaScadenza + " ]";
    }
    
}
