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
@Table(name = "processi_eventi_anagrafica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProcessiEventiAnagrafica.findAll", query = "SELECT p FROM ProcessiEventiAnagrafica p"),
    @NamedQuery(name = "ProcessiEventiAnagrafica.findByIdEvento", query = "SELECT p FROM ProcessiEventiAnagrafica p WHERE p.processiEventiAnagraficaPK.idEvento = :idEvento"),
    @NamedQuery(name = "ProcessiEventiAnagrafica.findByIdAnagrafica", query = "SELECT p FROM ProcessiEventiAnagrafica p WHERE p.processiEventiAnagraficaPK.idAnagrafica = :idAnagrafica")})
public class ProcessiEventiAnagrafica implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProcessiEventiAnagraficaPK processiEventiAnagraficaPK;
    @JoinColumn(name = "id_anagrafica", referencedColumnName = "id_anagrafica", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Anagrafica anagrafica;
    @JoinColumn(name = "id_evento", referencedColumnName = "id_evento", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ProcessiEventi processiEventi;

    public ProcessiEventiAnagrafica() {
    }

    public ProcessiEventiAnagrafica(ProcessiEventiAnagraficaPK processiEventiAnagraficaPK) {
        this.processiEventiAnagraficaPK = processiEventiAnagraficaPK;
    }

    public ProcessiEventiAnagrafica(int idEvento, int idAnagrafica) {
        this.processiEventiAnagraficaPK = new ProcessiEventiAnagraficaPK(idEvento, idAnagrafica);
    }

    public ProcessiEventiAnagraficaPK getProcessiEventiAnagraficaPK() {
        return processiEventiAnagraficaPK;
    }

    public void setProcessiEventiAnagraficaPK(ProcessiEventiAnagraficaPK processiEventiAnagraficaPK) {
        this.processiEventiAnagraficaPK = processiEventiAnagraficaPK;
    }

    public Anagrafica getAnagrafica() {
        return anagrafica;
    }

    public void setAnagrafica(Anagrafica anagrafica) {
        this.anagrafica = anagrafica;
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
        hash += (processiEventiAnagraficaPK != null ? processiEventiAnagraficaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProcessiEventiAnagrafica)) {
            return false;
        }
        ProcessiEventiAnagrafica other = (ProcessiEventiAnagrafica) object;
        if ((this.processiEventiAnagraficaPK == null && other.processiEventiAnagraficaPK != null) || (this.processiEventiAnagraficaPK != null && !this.processiEventiAnagraficaPK.equals(other.processiEventiAnagraficaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.ProcessiEventiAnagrafica[ processiEventiAnagraficaPK=" + processiEventiAnagraficaPK + " ]";
    }
    
}
