/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "lk_stati_scadenze")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkStatiScadenze.findAll", query = "SELECT l FROM LkStatiScadenze l"),
    @NamedQuery(name = "LkStatiScadenze.findByIdStato", query = "SELECT l FROM LkStatiScadenze l WHERE l.idStato = :idStato"),
    @NamedQuery(name = "LkStatiScadenze.findByDesStatoScadenza", query = "SELECT l FROM LkStatiScadenze l WHERE l.desStatoScadenza = :desStatoScadenza"),
    @NamedQuery(name = "LkStatiScadenze.findByGrpStatoScadenza", query = "SELECT l FROM LkStatiScadenze l WHERE l.grpStatoScadenza = :grpStatoScadenza")})
public class LkStatiScadenze implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_stato")
    private String idStato;
    @Column(name = "des_stato_scadenza")
    private String desStatoScadenza;
    @Column(name = "grp_stato_scadenza")
    private String grpStatoScadenza;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idStato")
    private List<Scadenze> scadenzeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idStatoScadenza")
    private List<ProcessiEventiScadenze> processiEventiScadenzeList;

    public LkStatiScadenze() {
    }

    public LkStatiScadenze(String idStato) {
        this.idStato = idStato;
    }

    public String getIdStato() {
        return idStato;
    }

    public void setIdStato(String idStato) {
        this.idStato = idStato;
    }

    public String getDesStatoScadenza() {
        return desStatoScadenza;
    }

    public void setDesStatoScadenza(String desStatoScadenza) {
        this.desStatoScadenza = desStatoScadenza;
    }

    public String getGrpStatoScadenza() {
        return grpStatoScadenza;
    }

    public void setGrpStatoScadenza(String grpStatoScadenza) {
        this.grpStatoScadenza = grpStatoScadenza;
    }

    @XmlTransient
    public List<Scadenze> getScadenzeList() {
        return scadenzeList;
    }

    public void setScadenzeList(List<Scadenze> scadenzeList) {
        this.scadenzeList = scadenzeList;
    }

    @XmlTransient
    public List<ProcessiEventiScadenze> getProcessiEventiScadenzeList() {
        return processiEventiScadenzeList;
    }

    public void setProcessiEventiScadenzeList(List<ProcessiEventiScadenze> processiEventiScadenzeList) {
        this.processiEventiScadenzeList = processiEventiScadenzeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idStato != null ? idStato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkStatiScadenze)) {
            return false;
        }
        LkStatiScadenze other = (LkStatiScadenze) object;
        if ((this.idStato == null && other.idStato != null) || (this.idStato != null && !this.idStato.equals(other.idStato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkStatiScadenze[ idStato=" + idStato + " ]";
    }
    
}
