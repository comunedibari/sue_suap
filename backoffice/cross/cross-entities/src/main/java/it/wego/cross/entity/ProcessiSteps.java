/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
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
 * @author giuseppe
 */
@Entity
@Table(name = "processi_steps")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProcessiSteps.findAll", query = "SELECT p FROM ProcessiSteps p"),
    @NamedQuery(name = "ProcessiSteps.findByIdEventoTrigger", query = "SELECT p FROM ProcessiSteps p WHERE p.processiStepsPK.idEventoTrigger = :idEventoTrigger"),
    @NamedQuery(name = "ProcessiSteps.findByIdEventoResult", query = "SELECT p FROM ProcessiSteps p WHERE p.processiStepsPK.idEventoResult = :idEventoResult"),
    @NamedQuery(name = "ProcessiSteps.findByTipoOperazione", query = "SELECT p FROM ProcessiSteps p WHERE p.tipoOperazione = :tipoOperazione")})
public class ProcessiSteps implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProcessiStepsPK processiStepsPK;
    @Basic(optional = false)
    @Column(name = "tipo_operazione")
    private String tipoOperazione;
    @JoinColumn(name = "id_evento_trigger", referencedColumnName = "id_evento", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ProcessiEventi idEventoTrigger;
    @JoinColumn(name = "id_evento_result", referencedColumnName = "id_evento", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ProcessiEventi idEventoResult;

    public ProcessiSteps() {
    }

    public ProcessiSteps(ProcessiStepsPK processiStepsPK) {
        this.processiStepsPK = processiStepsPK;
    }

    public ProcessiSteps(ProcessiStepsPK processiStepsPK, String tipoOperazione) {
        this.processiStepsPK = processiStepsPK;
        this.tipoOperazione = tipoOperazione;
    }

    public ProcessiSteps(int idEventoTrigger, int idEventoResult) {
        this.processiStepsPK = new ProcessiStepsPK(idEventoTrigger, idEventoResult);
    }

    public ProcessiStepsPK getProcessiStepsPK() {
        return processiStepsPK;
    }

    public void setProcessiStepsPK(ProcessiStepsPK processiStepsPK) {
        this.processiStepsPK = processiStepsPK;
    }

    public String getTipoOperazione() {
        return tipoOperazione;
    }

    public void setTipoOperazione(String tipoOperazione) {
        this.tipoOperazione = tipoOperazione;
    }

    public ProcessiEventi getIdEventoTrigger() {
        return idEventoTrigger;
    }

    public void setIdEventoTrigger(ProcessiEventi idEventoTrigger) {
        this.idEventoTrigger = idEventoTrigger;
    }

    public ProcessiEventi getIdEventoResult() {
        return idEventoResult;
    }

    public void setIdEventoResult(ProcessiEventi idEventoResult) {
        this.idEventoResult = idEventoResult;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (processiStepsPK != null ? processiStepsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProcessiSteps)) {
            return false;
        }
        ProcessiSteps other = (ProcessiSteps) object;
        if ((this.processiStepsPK == null && other.processiStepsPK != null) || (this.processiStepsPK != null && !this.processiStepsPK.equals(other.processiStepsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.ProcessiSteps[ processiStepsPK=" + processiStepsPK + " ]";
    }
    
}
