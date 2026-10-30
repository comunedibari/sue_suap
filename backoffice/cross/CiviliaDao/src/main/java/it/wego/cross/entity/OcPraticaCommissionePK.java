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
public class OcPraticaCommissionePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_seduta_pratica")
    private int idSedutaPratica;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_anagrafica")
    private int idAnagrafica;

    public OcPraticaCommissionePK() {
    }

    public OcPraticaCommissionePK(int idSedutaPratica, int idAnagrafica) {
        this.idSedutaPratica = idSedutaPratica;
        this.idAnagrafica = idAnagrafica;
    }

    public int getIdSedutaPratica() {
        return idSedutaPratica;
    }

    public void setIdSedutaPratica(int idSedutaPratica) {
        this.idSedutaPratica = idSedutaPratica;
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
        hash += (int) idSedutaPratica;
        hash += (int) idAnagrafica;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OcPraticaCommissionePK)) {
            return false;
        }
        OcPraticaCommissionePK other = (OcPraticaCommissionePK) object;
        if (this.idSedutaPratica != other.idSedutaPratica) {
            return false;
        }
        if (this.idAnagrafica != other.idAnagrafica) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.OcPraticaCommissionePK[ idSedutaPratica=" + idSedutaPratica + ", idAnagrafica=" + idAnagrafica + " ]";
    }
    
}
