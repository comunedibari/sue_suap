/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author piergiorgio
 */
@Entity
@Table(name = "processi_eventi_enti")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProcessiEventiEnti.findAll", query = "SELECT p FROM ProcessiEventiEnti p"),
    @NamedQuery(name = "ProcessiEventiEnti.findByIdEvento", query = "SELECT p FROM ProcessiEventiEnti p WHERE p.processiEventiEntiPK.idEvento = :idEvento"),
    @NamedQuery(name = "ProcessiEventiEnti.findByIdEnte", query = "SELECT p FROM ProcessiEventiEnti p WHERE p.processiEventiEntiPK.idEnte = :idEnte")})
public class ProcessiEventiEnti implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProcessiEventiEntiPK processiEventiEntiPK;
    @JoinColumn(name = "id_ente", referencedColumnName = "id_ente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Enti enti;
    @JoinColumn(name = "id_evento", referencedColumnName = "id_evento", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ProcessiEventi processiEventi;

    public ProcessiEventiEnti() {
    }

    public ProcessiEventiEnti(ProcessiEventiEntiPK processiEventiEntiPK) {
        this.processiEventiEntiPK = processiEventiEntiPK;
    }

    public ProcessiEventiEnti(int idEvento, int idEnte) {
        this.processiEventiEntiPK = new ProcessiEventiEntiPK(idEvento, idEnte);
    }

    public ProcessiEventiEntiPK getProcessiEventiEntiPK() {
        return processiEventiEntiPK;
    }

    public void setProcessiEventiEntiPK(ProcessiEventiEntiPK processiEventiEntiPK) {
        this.processiEventiEntiPK = processiEventiEntiPK;
    }

    public Enti getEnti() {
        return enti;
    }

    public void setEnti(Enti enti) {
        this.enti = enti;
    }

    public ProcessiEventi getProcessiEventi() {
        return processiEventi;
    }

    public void setProcessiEventi(ProcessiEventi processiEventi) {
        this.processiEventi = processiEventi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (processiEventiEntiPK != null ? processiEventiEntiPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProcessiEventiEnti)) {
            return false;
        }
        ProcessiEventiEnti other = (ProcessiEventiEnti) object;
        if ((this.processiEventiEntiPK == null && other.processiEventiEntiPK != null) || (this.processiEventiEntiPK != null && !this.processiEventiEntiPK.equals(other.processiEventiEntiPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.ProcessiEventiEnti[ processiEventiEntiPK=" + processiEventiEntiPK + " ]";
    }
    
}
