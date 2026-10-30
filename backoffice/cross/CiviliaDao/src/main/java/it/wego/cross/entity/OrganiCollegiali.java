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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author piergiorgio
 */
@Entity
@Table(name = "organi_collegiali")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrganiCollegiali.findAll", query = "SELECT o FROM OrganiCollegiali o"),
    @NamedQuery(name = "OrganiCollegiali.findByIdOrganiCollegiali", query = "SELECT o FROM OrganiCollegiali o WHERE o.idOrganiCollegiali = :idOrganiCollegiali")})
public class OrganiCollegiali implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOrganiCollegiali")
    private List<PraticaOrganiCollegiali> praticaOrganiCollegialiList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organiCollegiali")
    private List<OrganiCollegialiCommissione> organiCollegialiCommissioneList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOrganoCollegiale")
    private List<OrganiCollegialiSedute> organiCollegialiSeduteList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_organi_collegiali")
    private Integer idOrganiCollegiali;
    @JoinColumn(name = "id_ente", referencedColumnName = "id_ente")
    @ManyToOne(optional = false)
    private Enti idEnte;
    @Column(name = "des_organo_collegiale")
    private String desOrganoCollegiale;
    @OneToMany(mappedBy = "idOrganiCollegiali")
    private List<OrganiCollegialiTemplate> organiCollegialiTemplateList;    

    public OrganiCollegiali() {
    }

    public OrganiCollegiali(Integer idOrganiCollegiali) {
        this.idOrganiCollegiali = idOrganiCollegiali;
    }

    public Integer getIdOrganiCollegiali() {
        return idOrganiCollegiali;
    }

    public void setIdOrganiCollegiali(Integer idOrganiCollegiali) {
        this.idOrganiCollegiali = idOrganiCollegiali;
    }

    public Enti getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(Enti idEnte) {
        this.idEnte = idEnte;
    }

    public String getDesOrganoCollegiale() {
        return desOrganoCollegiale;
    }

    public void setDesOrganoCollegiale(String desOrganoCollegiale) {
        this.desOrganoCollegiale = desOrganoCollegiale;
    }

    public List<OrganiCollegialiTemplate> getOrganiCollegialiTemplateList() {
        return organiCollegialiTemplateList;
    }

    public void setOrganiCollegialiTemplateList(List<OrganiCollegialiTemplate> organiCollegialiTemplateList) {
        this.organiCollegialiTemplateList = organiCollegialiTemplateList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrganiCollegiali != null ? idOrganiCollegiali.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrganiCollegiali)) {
            return false;
        }
        OrganiCollegiali other = (OrganiCollegiali) object;
        if ((this.idOrganiCollegiali == null && other.idOrganiCollegiali != null) || (this.idOrganiCollegiali != null && !this.idOrganiCollegiali.equals(other.idOrganiCollegiali))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.OrganiCollegiali[ idOrganiCollegiali=" + idOrganiCollegiali + " ]";
    }

    @XmlTransient
    @JsonIgnore
    public List<OrganiCollegialiSedute> getOrganiCollegialiSeduteList() {
        return organiCollegialiSeduteList;
    }

    public void setOrganiCollegialiSeduteList(List<OrganiCollegialiSedute> organiCollegialiSeduteList) {
        this.organiCollegialiSeduteList = organiCollegialiSeduteList;
    }

    @XmlTransient
    @JsonIgnore
    public List<OrganiCollegialiCommissione> getOrganiCollegialiCommissioneList() {
        return organiCollegialiCommissioneList;
    }

    public void setOrganiCollegialiCommissioneList(List<OrganiCollegialiCommissione> organiCollegialiCommissioneList) {
        this.organiCollegialiCommissioneList = organiCollegialiCommissioneList;
    }

    @XmlTransient
    @JsonIgnore
    public List<PraticaOrganiCollegiali> getPraticaOrganiCollegialiList() {
        return praticaOrganiCollegialiList;
    }

    public void setPraticaOrganiCollegialiList(List<PraticaOrganiCollegiali> praticaOrganiCollegialiList) {
        this.praticaOrganiCollegialiList = praticaOrganiCollegialiList;
    }

}
