/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author piergiorgio
 */
@Entity
@Table(name = "lk_ruoli_commissione")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkRuoliCommissione.findAll", query = "SELECT l FROM LkRuoliCommissione l"),
    @NamedQuery(name = "LkRuoliCommissione.findByIdRuoloCommissione", query = "SELECT l FROM LkRuoliCommissione l WHERE l.idRuoloCommissione = :idRuoloCommissione"),
    @NamedQuery(name = "LkRuoliCommissione.findByDescrizione", query = "SELECT l FROM LkRuoliCommissione l WHERE l.descrizione = :descrizione")})
public class LkRuoliCommissione implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRuoloCommissione")
    private List<OcPraticaCommissione> ocPraticaCommissioneList;
    @Basic(optional = false)
    @NotNull
    @Column(name = "peso")
    private int peso;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ruolo_commissione")
    private Integer idRuoloCommissione;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descrizione")
    private String descrizione;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRuoloCommissione")
    private List<OrganiCollegialiCommissione> organiCollegialiCommissioneList;

    public LkRuoliCommissione() {
    }

    public LkRuoliCommissione(Integer idRuoloCommissione) {
        this.idRuoloCommissione = idRuoloCommissione;
    }

    public LkRuoliCommissione(Integer idRuoloCommissione, String descrizione) {
        this.idRuoloCommissione = idRuoloCommissione;
        this.descrizione = descrizione;
    }

    public Integer getIdRuoloCommissione() {
        return idRuoloCommissione;
    }

    public void setIdRuoloCommissione(Integer idRuoloCommissione) {
        this.idRuoloCommissione = idRuoloCommissione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @XmlTransient
    @JsonIgnore
    public List<OrganiCollegialiCommissione> getOrganiCollegialiCommissioneList() {
        return organiCollegialiCommissioneList;
    }

    public void setOrganiCollegialiCommissioneList(List<OrganiCollegialiCommissione> organiCollegialiCommissioneList) {
        this.organiCollegialiCommissioneList = organiCollegialiCommissioneList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRuoloCommissione != null ? idRuoloCommissione.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkRuoliCommissione)) {
            return false;
        }
        LkRuoliCommissione other = (LkRuoliCommissione) object;
        if ((this.idRuoloCommissione == null && other.idRuoloCommissione != null) || (this.idRuoloCommissione != null && !this.idRuoloCommissione.equals(other.idRuoloCommissione))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkRuoliCommissione[ idRuoloCommissione=" + idRuoloCommissione + " ]";
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    @XmlTransient
    @JsonIgnore
    public List<OcPraticaCommissione> getOcPraticaCommissioneList() {
        return ocPraticaCommissioneList;
    }

    public void setOcPraticaCommissioneList(List<OcPraticaCommissione> ocPraticaCommissioneList) {
        this.ocPraticaCommissioneList = ocPraticaCommissioneList;
    }


}
