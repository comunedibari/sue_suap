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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "processi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Processi.findAll", query = "SELECT p FROM Processi p"),
    @NamedQuery(name = "Processi.findByIdProcesso", query = "SELECT p FROM Processi p WHERE p.idProcesso = :idProcesso"),
    @NamedQuery(name = "Processi.findByCodProcesso", query = "SELECT p FROM Processi p WHERE p.codProcesso = :codProcesso"),
    @NamedQuery(name = "Processi.findByDesProcesso", query = "SELECT p FROM Processi p WHERE p.desProcesso = :desProcesso")})
public class Processi implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_processo")
    private Integer idProcesso;
    @Basic(optional = false)
    @Column(name = "cod_processo")
    private String codProcesso;
    @Column(name = "des_processo")
    private String desProcesso;
    @OneToMany(mappedBy = "idProcesso")
    private List<ProcedimentiEnti> procedimentiEntiList;
    @OneToMany(mappedBy = "idProcesso")
    private List<Pratica> praticaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProcesso")
    private List<ProcessiEventi> processiEventiList;

    public Processi() {
    }

    public Processi(Integer idProcesso) {
        this.idProcesso = idProcesso;
    }

    public Processi(Integer idProcesso, String codProcesso) {
        this.idProcesso = idProcesso;
        this.codProcesso = codProcesso;
    }

    public Integer getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Integer idProcesso) {
        this.idProcesso = idProcesso;
    }

    public String getCodProcesso() {
        return codProcesso;
    }

    public void setCodProcesso(String codProcesso) {
        this.codProcesso = codProcesso;
    }

    public String getDesProcesso() {
        return desProcesso;
    }

    public void setDesProcesso(String desProcesso) {
        this.desProcesso = desProcesso;
    }

    @XmlTransient
    public List<ProcedimentiEnti> getProcedimentiEntiList() {
        return procedimentiEntiList;
    }

    public void setProcedimentiEntiList(List<ProcedimentiEnti> procedimentiEntiList) {
        this.procedimentiEntiList = procedimentiEntiList;
    }

    @XmlTransient
    public List<Pratica> getPraticaList() {
        return praticaList;
    }

    public void setPraticaList(List<Pratica> praticaList) {
        this.praticaList = praticaList;
    }

    @XmlTransient
    public List<ProcessiEventi> getProcessiEventiList() {
        return processiEventiList;
    }

    public void setProcessiEventiList(List<ProcessiEventi> processiEventiList) {
        this.processiEventiList = processiEventiList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProcesso != null ? idProcesso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Processi)) {
            return false;
        }
        Processi other = (Processi) object;
        if ((this.idProcesso == null && other.idProcesso != null) || (this.idProcesso != null && !this.idProcesso.equals(other.idProcesso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.Processi[ idProcesso=" + idProcesso + " ]";
    }
    
}
