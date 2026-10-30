/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author piergiorgio
 */
@Embeddable
public class OrganiCollegialiCommissionePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_organi_collegiali")
    private int idOrganiCollegiali;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_anagrafica")
    private int idAnagrafica;

    public OrganiCollegialiCommissionePK() {
    }

    public OrganiCollegialiCommissionePK(int idOrganiCollegiali, int idAnagrafica) {
        this.idOrganiCollegiali = idOrganiCollegiali;
        this.idAnagrafica = idAnagrafica;
    }

    public int getIdOrganiCollegiali() {
        return idOrganiCollegiali;
    }

    public void setIdOrganiCollegiali(int idOrganiCollegiali) {
        this.idOrganiCollegiali = idOrganiCollegiali;
    }

    public int getIdAnagrafica() {
        return idAnagrafica;
    }

    public void setIdAnagrafica(int idAnagrafica) {
        this.idAnagrafica = idAnagrafica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idOrganiCollegiali;
        hash += (int) idAnagrafica;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrganiCollegialiCommissionePK)) {
            return false;
        }
        OrganiCollegialiCommissionePK other = (OrganiCollegialiCommissionePK) object;
        if (this.idOrganiCollegiali != other.idOrganiCollegiali) {
            return false;
        }
        if (this.idAnagrafica != other.idAnagrafica) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.OrganiCollegialiCommissionePK[ idOrganiCollegiali=" + idOrganiCollegiali + ", idAnagrafica=" + idAnagrafica + " ]";
    }
    
}
