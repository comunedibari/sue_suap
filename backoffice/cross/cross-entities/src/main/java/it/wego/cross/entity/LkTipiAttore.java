/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "lk_tipi_attore")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkTipiAttore.findAll", query = "SELECT l FROM LkTipiAttore l"),
    @NamedQuery(name = "LkTipiAttore.findByIdTipoAttore", query = "SELECT l FROM LkTipiAttore l WHERE l.idTipoAttore = :idTipoAttore"),
    @NamedQuery(name = "LkTipiAttore.findByDesTipoAttore", query = "SELECT l FROM LkTipiAttore l WHERE l.desTipoAttore = :desTipoAttore")})
public class LkTipiAttore implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_tipo_attore")
    private String idTipoAttore;
    @Column(name = "des_tipo_attore")
    private String desTipoAttore;
    @OneToMany(mappedBy = "idTipoMittente")
    private List<ProcessiEventi> processiEventiList;
    @OneToMany(mappedBy = "idTipoDestinatario")
    private List<ProcessiEventi> processiEventiList1;

    public LkTipiAttore() {
    }

    public LkTipiAttore(String idTipoAttore) {
        this.idTipoAttore = idTipoAttore;
    }

    public String getIdTipoAttore() {
        return idTipoAttore;
    }

    public void setIdTipoAttore(String idTipoAttore) {
        this.idTipoAttore = idTipoAttore;
    }

    public String getDesTipoAttore() {
        return desTipoAttore;
    }

    public void setDesTipoAttore(String desTipoAttore) {
        this.desTipoAttore = desTipoAttore;
    }

    @XmlTransient
    public List<ProcessiEventi> getProcessiEventiList() {
        return processiEventiList;
    }

    public void setProcessiEventiList(List<ProcessiEventi> processiEventiList) {
        this.processiEventiList = processiEventiList;
    }

    @XmlTransient
    public List<ProcessiEventi> getProcessiEventiList1() {
        return processiEventiList1;
    }

    public void setProcessiEventiList1(List<ProcessiEventi> processiEventiList1) {
        this.processiEventiList1 = processiEventiList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoAttore != null ? idTipoAttore.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkTipiAttore)) {
            return false;
        }
        LkTipiAttore other = (LkTipiAttore) object;
        if ((this.idTipoAttore == null && other.idTipoAttore != null) || (this.idTipoAttore != null && !this.idTipoAttore.equals(other.idTipoAttore))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkTipiAttore[ idTipoAttore=" + idTipoAttore + " ]";
    }
    
}
