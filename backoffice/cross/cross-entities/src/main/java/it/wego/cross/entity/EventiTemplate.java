/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "eventi_template")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventiTemplate.findAll", query = "SELECT e FROM EventiTemplate e"),
    @NamedQuery(name = "EventiTemplate.findByIdEventoTemplate", query = "SELECT e FROM EventiTemplate e WHERE e.idEventoTemplate = :idEventoTemplate")})
public class EventiTemplate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_evento_template")
    private Integer idEventoTemplate;
    @JoinColumn(name = "id_template", referencedColumnName = "id_template")
    @ManyToOne(optional = false)
    private Templates idTemplate;
    @JoinColumn(name = "id_procedimento", referencedColumnName = "id_proc")
    @ManyToOne
    private Procedimenti idProcedimento;
    @JoinColumn(name = "id_evento", referencedColumnName = "id_evento")
    @ManyToOne(optional = false)
    private ProcessiEventi idEvento;
    @JoinColumn(name = "id_ente", referencedColumnName = "id_ente")
    @ManyToOne(optional = false)
    private Enti idEnte;

    public EventiTemplate() {
    }

    public EventiTemplate(Integer idEventoTemplate) {
        this.idEventoTemplate = idEventoTemplate;
    }

    public Integer getIdEventoTemplate() {
        return idEventoTemplate;
    }

    public void setIdEventoTemplate(Integer idEventoTemplate) {
        this.idEventoTemplate = idEventoTemplate;
    }

    public Templates getIdTemplate() {
        return idTemplate;
    }

    public void setIdTemplate(Templates idTemplate) {
        this.idTemplate = idTemplate;
    }

    public Procedimenti getIdProcedimento() {
        return idProcedimento;
    }

    public void setIdProcedimento(Procedimenti idProcedimento) {
        this.idProcedimento = idProcedimento;
    }

    public ProcessiEventi getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(ProcessiEventi idEvento) {
        this.idEvento = idEvento;
    }

    public Enti getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(Enti idEnte) {
        this.idEnte = idEnte;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEventoTemplate != null ? idEventoTemplate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventiTemplate)) {
            return false;
        }
        EventiTemplate other = (EventiTemplate) object;
        if ((this.idEventoTemplate == null && other.idEventoTemplate != null) || (this.idEventoTemplate != null && !this.idEventoTemplate.equals(other.idEventoTemplate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.EventiTemplate[ idEventoTemplate=" + idEventoTemplate + " ]";
    }
    
}
