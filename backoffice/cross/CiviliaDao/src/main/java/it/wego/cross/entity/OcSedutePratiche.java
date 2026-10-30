/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import com.google.common.collect.Lists;
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
import javax.persistence.Lob;
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
@Table(name = "oc_sedute_pratiche")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OcSedutePratiche.findAll", query = "SELECT o FROM OcSedutePratiche o"),
    @NamedQuery(name = "OcSedutePratiche.findByIdSedutaPratica", query = "SELECT o FROM OcSedutePratiche o WHERE o.idSedutaPratica = :idSedutaPratica")})
public class OcSedutePratiche implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ocSedutePratiche")
    private List<OcPraticaCommissione> ocPraticaCommissioneList = Lists.newArrayList();
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_seduta_pratica")
    private Integer idSedutaPratica;
    @JoinColumn(name = "id_pratica_organi_collegiali", referencedColumnName = "id_pratica_organi_collegiali")
    @ManyToOne
    private PraticaOrganiCollegiali idPraticaOrganiCollegiali;
    @JoinColumn(name = "id_seduta", referencedColumnName = "id_seduta")
    @ManyToOne(optional = false)
    private OrganiCollegialiSedute idSeduta;
    @Column(name = "sequenza")
    private Integer sequenza;
    @Lob
    @Column(name = "note")
    private String note;    

    public OcSedutePratiche() {
    }

    public OcSedutePratiche(Integer idSedutaPratica) {
        this.idSedutaPratica = idSedutaPratica;
    }

    public Integer getIdSedutaPratica() {
        return idSedutaPratica;
    }

    public void setIdSedutaPratica(Integer idSedutaPratica) {
        this.idSedutaPratica = idSedutaPratica;
    }

    public PraticaOrganiCollegiali getIdPraticaOrganiCollegiali() {
        return idPraticaOrganiCollegiali;
    }

    public void setIdPraticaOrganiCollegiali(PraticaOrganiCollegiali idPraticaOrganiCollegiali) {
        this.idPraticaOrganiCollegiali = idPraticaOrganiCollegiali;
    }

    public OrganiCollegialiSedute getIdSeduta() {
        return idSeduta;
    }

    public void setIdSeduta(OrganiCollegialiSedute idSeduta) {
        this.idSeduta = idSeduta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSedutaPratica != null ? idSedutaPratica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OcSedutePratiche)) {
            return false;
        }
        OcSedutePratiche other = (OcSedutePratiche) object;
        if ((this.idSedutaPratica == null && other.idSedutaPratica != null) || (this.idSedutaPratica != null && !this.idSedutaPratica.equals(other.idSedutaPratica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.OcSedutePratiche[ idSedutaPratica=" + idSedutaPratica + " ]";
    }

    @XmlTransient
    @JsonIgnore
    public List<OcPraticaCommissione> getOcPraticaCommissioneList() {
        return ocPraticaCommissioneList;
    }

    public void setOcPraticaCommissioneList(List<OcPraticaCommissione> ocPraticaCommissioneList) {
        this.ocPraticaCommissioneList = ocPraticaCommissioneList;
    }

    public Integer getSequenza() {
        return sequenza;
    }

    public void setSequenza(Integer sequenza) {
        this.sequenza = sequenza;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
}
