/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author piergiorgio
 */
@Entity
@Table(name = "organi_collegiali_commissione")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrganiCollegialiCommissione.findAll", query = "SELECT o FROM OrganiCollegialiCommissione o"),
    @NamedQuery(name = "OrganiCollegialiCommissione.findByIdOrganiCollegiali", query = "SELECT o FROM OrganiCollegialiCommissione o WHERE o.organiCollegialiCommissionePK.idOrganiCollegiali = :idOrganiCollegiali"),
    @NamedQuery(name = "OrganiCollegialiCommissione.findByIdAnagrafica", query = "SELECT o FROM OrganiCollegialiCommissione o WHERE o.organiCollegialiCommissionePK.idAnagrafica = :idAnagrafica")})
public class OrganiCollegialiCommissione implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrganiCollegialiCommissionePK organiCollegialiCommissionePK;
    @JoinColumn(name = "id_ruolo_commissione", referencedColumnName = "id_ruolo_commissione")
    @ManyToOne(optional = false)
    private LkRuoliCommissione idRuoloCommissione;
    @JoinColumn(name = "id_anagrafica", referencedColumnName = "id_anagrafica", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Anagrafica anagrafica;
    @JoinColumn(name = "id_organi_collegiali", referencedColumnName = "id_organi_collegiali", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private OrganiCollegiali organiCollegiali;

    public OrganiCollegialiCommissione() {
    }

    public OrganiCollegialiCommissione(OrganiCollegialiCommissionePK organiCollegialiCommissionePK) {
        this.organiCollegialiCommissionePK = organiCollegialiCommissionePK;
    }

    public OrganiCollegialiCommissione(int idOrganiCollegiali, int idAnagrafica) {
        this.organiCollegialiCommissionePK = new OrganiCollegialiCommissionePK(idOrganiCollegiali, idAnagrafica);
    }

    public OrganiCollegialiCommissionePK getOrganiCollegialiCommissionePK() {
        return organiCollegialiCommissionePK;
    }

    public void setOrganiCollegialiCommissionePK(OrganiCollegialiCommissionePK organiCollegialiCommissionePK) {
        this.organiCollegialiCommissionePK = organiCollegialiCommissionePK;
    }

    public LkRuoliCommissione getIdRuoloCommissione() {
        return idRuoloCommissione;
    }

    public void setIdRuoloCommissione(LkRuoliCommissione idRuoloCommissione) {
        this.idRuoloCommissione = idRuoloCommissione;
    }

    public Anagrafica getAnagrafica() {
        return anagrafica;
    }

    public void setAnagrafica(Anagrafica anagrafica) {
        this.anagrafica = anagrafica;
    }

    public OrganiCollegiali getOrganiCollegiali() {
        return organiCollegiali;
    }

    public void setOrganiCollegiali(OrganiCollegiali organiCollegiali) {
        this.organiCollegiali = organiCollegiali;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (organiCollegialiCommissionePK != null ? organiCollegialiCommissionePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrganiCollegialiCommissione)) {
            return false;
        }
        OrganiCollegialiCommissione other = (OrganiCollegialiCommissione) object;
        if ((this.organiCollegialiCommissionePK == null && other.organiCollegialiCommissionePK != null) || (this.organiCollegialiCommissionePK != null && !this.organiCollegialiCommissionePK.equals(other.organiCollegialiCommissionePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.OrganiCollegialiCommissione[ organiCollegialiCommissionePK=" + organiCollegialiCommissionePK + " ]";
    }
    
}
