/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author giuseppe
 */
@Embeddable
public class PraticaAnagraficaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_pratica")
    private int idPratica;
    @Basic(optional = false)
    @Column(name = "id_anagrafica")
    private int idAnagrafica;
    @Basic(optional = false)
    @Column(name = "id_tipo_ruolo")
    private int idTipoRuolo;

    public PraticaAnagraficaPK() {
    }

    public PraticaAnagraficaPK(int idPratica, int idAnagrafica, int idTipoRuolo) {
        this.idPratica = idPratica;
        this.idAnagrafica = idAnagrafica;
        this.idTipoRuolo = idTipoRuolo;
    }

    public int getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(int idPratica) {
        this.idPratica = idPratica;
    }

    public int getIdAnagrafica() {
        return idAnagrafica;
    }

    public void setIdAnagrafica(int idAnagrafica) {
        this.idAnagrafica = idAnagrafica;
    }

    public int getIdTipoRuolo() {
        return idTipoRuolo;
    }

    public void setIdTipoRuolo(int idTipoRuolo) {
        this.idTipoRuolo = idTipoRuolo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPratica;
        hash += (int) idAnagrafica;
        hash += (int) idTipoRuolo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PraticaAnagraficaPK)) {
            return false;
        }
        PraticaAnagraficaPK other = (PraticaAnagraficaPK) object;
        if (this.idPratica != other.idPratica) {
            return false;
        }
        if (this.idAnagrafica != other.idAnagrafica) {
            return false;
        }
        if (this.idTipoRuolo != other.idTipoRuolo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.PraticaAnagraficaPK[ idPratica=" + idPratica + ", idAnagrafica=" + idAnagrafica + ", idTipoRuolo=" + idTipoRuolo + " ]";
    }
    
}
