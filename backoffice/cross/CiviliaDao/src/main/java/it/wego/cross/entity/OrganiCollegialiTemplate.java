/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author piergiorgio
 */
@Entity
@Table(name = "organi_collegiali_template")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrganiCollegialiTemplate.findAll", query = "SELECT o FROM OrganiCollegialiTemplate o"),
    @NamedQuery(name = "OrganiCollegialiTemplate.findByIdOrganiCollegialiTemplate", query = "SELECT o FROM OrganiCollegialiTemplate o WHERE o.idOrganiCollegialiTemplate = :idOrganiCollegialiTemplate"),
    @NamedQuery(name = "OrganiCollegialiTemplate.findByTipologiaTemplate", query = "SELECT o FROM OrganiCollegialiTemplate o WHERE o.tipologiaTemplate = :tipologiaTemplate")})
public class OrganiCollegialiTemplate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_organi_collegiali_template")
    private Integer idOrganiCollegialiTemplate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tipologia_template")
    private String tipologiaTemplate;
    @JoinColumn(name = "id_template", referencedColumnName = "id_template")
    @ManyToOne
    private Templates idTemplate;
    @JoinColumn(name = "id_organi_collegiali", referencedColumnName = "id_organi_collegiali")
    @ManyToOne(optional = false)
    private OrganiCollegiali idOrganiCollegiali;

    public OrganiCollegialiTemplate() {
    }

    public OrganiCollegialiTemplate(Integer idOrganiCollegialiTemplate) {
        this.idOrganiCollegialiTemplate = idOrganiCollegialiTemplate;
    }

    public OrganiCollegialiTemplate(Integer idOrganiCollegialiTemplate, String tipologiaTemplate) {
        this.idOrganiCollegialiTemplate = idOrganiCollegialiTemplate;
        this.tipologiaTemplate = tipologiaTemplate;
    }

    public Integer getIdOrganiCollegialiTemplate() {
        return idOrganiCollegialiTemplate;
    }

    public void setIdOrganiCollegialiTemplate(Integer idOrganiCollegialiTemplate) {
        this.idOrganiCollegialiTemplate = idOrganiCollegialiTemplate;
    }

    public String getTipologiaTemplate() {
        return tipologiaTemplate;
    }

    public void setTipologiaTemplate(String tipologiaTemplate) {
        this.tipologiaTemplate = tipologiaTemplate;
    }

    public Templates getIdTemplate() {
        return idTemplate;
    }

    public void setIdTemplate(Templates idTemplate) {
        this.idTemplate = idTemplate;
    }

    public OrganiCollegiali getIdOrganiCollegiali() {
        return idOrganiCollegiali;
    }

    public void setIdOrganiCollegiali(OrganiCollegiali idOrganiCollegiali) {
        this.idOrganiCollegiali = idOrganiCollegiali;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrganiCollegialiTemplate != null ? idOrganiCollegialiTemplate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrganiCollegialiTemplate)) {
            return false;
        }
        OrganiCollegialiTemplate other = (OrganiCollegialiTemplate) object;
        if ((this.idOrganiCollegialiTemplate == null && other.idOrganiCollegialiTemplate != null) || (this.idOrganiCollegialiTemplate != null && !this.idOrganiCollegialiTemplate.equals(other.idOrganiCollegialiTemplate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.OrganiCollegialiTemplate[ idOrganiCollegialiTemplate=" + idOrganiCollegialiTemplate + " ]";
    }
    
}
