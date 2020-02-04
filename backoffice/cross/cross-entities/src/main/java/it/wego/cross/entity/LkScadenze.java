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
@Table(name = "lk_scadenze")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkScadenze.findAll", query = "SELECT l FROM LkScadenze l"),
    @NamedQuery(name = "LkScadenze.findByIdAnaScadenze", query = "SELECT l FROM LkScadenze l WHERE l.idAnaScadenze = :idAnaScadenze"),
    @NamedQuery(name = "LkScadenze.findByDesAnaScadenze", query = "SELECT l FROM LkScadenze l WHERE l.desAnaScadenze = :desAnaScadenze"),
    @NamedQuery(name = "LkScadenze.findByFlgScadenzaPratica", query = "SELECT l FROM LkScadenze l WHERE l.flgScadenzaPratica = :flgScadenzaPratica")})
public class LkScadenze implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_ana_scadenze")
    private String idAnaScadenze;
    @Column(name = "des_ana_scadenze")
    private String desAnaScadenze;
    @Basic(optional = false)
    @Column(name = "flg_scadenza_pratica")
    private String flgScadenzaPratica;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAnaScadenza")
    private List<Scadenze> scadenzeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lkScadenze")
    private List<ProcessiEventiScadenze> processiEventiScadenzeList;

    public LkScadenze() {
    }

    public LkScadenze(String idAnaScadenze) {
        this.idAnaScadenze = idAnaScadenze;
    }

    public LkScadenze(String idAnaScadenze, String flgScadenzaPratica) {
        this.idAnaScadenze = idAnaScadenze;
        this.flgScadenzaPratica = flgScadenzaPratica;
    }

    public String getIdAnaScadenze() {
        return idAnaScadenze;
    }

    public void setIdAnaScadenze(String idAnaScadenze) {
        this.idAnaScadenze = idAnaScadenze;
    }

    public String getDesAnaScadenze() {
        return desAnaScadenze;
    }

    public void setDesAnaScadenze(String desAnaScadenze) {
        this.desAnaScadenze = desAnaScadenze;
    }

    public String getFlgScadenzaPratica() {
        return flgScadenzaPratica;
    }

    public void setFlgScadenzaPratica(String flgScadenzaPratica) {
        this.flgScadenzaPratica = flgScadenzaPratica;
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
        hash += (idAnaScadenze != null ? idAnaScadenze.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkScadenze)) {
            return false;
        }
        LkScadenze other = (LkScadenze) object;
        if ((this.idAnaScadenze == null && other.idAnaScadenze != null) || (this.idAnaScadenze != null && !this.idAnaScadenze.equals(other.idAnaScadenze))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkScadenze[ idAnaScadenze=" + idAnaScadenze + " ]";
    }
    
}
