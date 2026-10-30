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
@Table(name = "lk_dug")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkDug.findAll", query = "SELECT l FROM LkDug l"),
    @NamedQuery(name = "LkDug.findByIdDug", query = "SELECT l FROM LkDug l WHERE l.idDug = :idDug"),
    @NamedQuery(name = "LkDug.findByDescrizione", query = "SELECT l FROM LkDug l WHERE l.descrizione = :descrizione"),
    @NamedQuery(name = "LkDug.findByCodDug", query = "SELECT l FROM LkDug l WHERE l.codDug = :codDug")})
public class LkDug implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_dug")
    private Integer idDug;
    @Basic(optional = false)
    @Column(name = "descrizione")
    private String descrizione;
    @Basic(optional = false)
    @Column(name = "cod_dug")
    private int codDug;
    @OneToMany(mappedBy = "idDug")
    private List<Recapiti> recapitiList;
        @OneToMany(mappedBy = "idDug")
    private List<IndirizziIntervento> indirizziInterventoList;

    public LkDug() {
    }

    public LkDug(Integer idDug) {
        this.idDug = idDug;
    }

    public LkDug(Integer idDug, String descrizione, int codDug) {
        this.idDug = idDug;
        this.descrizione = descrizione;
        this.codDug = codDug;
    }

    public Integer getIdDug() {
        return idDug;
    }

    public void setIdDug(Integer idDug) {
        this.idDug = idDug;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getCodDug() {
        return codDug;
    }

    public void setCodDug(int codDug) {
        this.codDug = codDug;
    }

    @XmlTransient
    public List<Recapiti> getRecapitiList() {
        return recapitiList;
    }

    public void setRecapitiList(List<Recapiti> recapitiList) {
        this.recapitiList = recapitiList;
    }

    @XmlTransient
    public List<IndirizziIntervento> getIndirizziInterventoList() {
        return indirizziInterventoList;
    }

    public void setIndirizziInterventoList(List<IndirizziIntervento> indirizziInterventoList) {
        this.indirizziInterventoList = indirizziInterventoList;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDug != null ? idDug.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkDug)) {
            return false;
        }
        LkDug other = (LkDug) object;
        if ((this.idDug == null && other.idDug != null) || (this.idDug != null && !this.idDug.equals(other.idDug))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkDug[ idDug=" + idDug + " ]";
    }
  
}
