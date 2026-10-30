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
public class PraticheEventiAnagrafichePK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_pratica_evento")
    private int idPraticaEvento;
    @Basic(optional = false)
    @Column(name = "id_anagrafica")
    private int idAnagrafica;

    public PraticheEventiAnagrafichePK() {
    }

    public PraticheEventiAnagrafichePK(int idPraticaEvento, int idAnagrafica) {
        this.idPraticaEvento = idPraticaEvento;
        this.idAnagrafica = idAnagrafica;
    }

    public int getIdPraticaEvento() {
        return idPraticaEvento;
    }

    public void setIdPraticaEvento(int idPraticaEvento) {
        this.idPraticaEvento = idPraticaEvento;
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
        hash += (int) idPraticaEvento;
        hash += (int) idAnagrafica;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PraticheEventiAnagrafichePK)) {
            return false;
        }
        PraticheEventiAnagrafichePK other = (PraticheEventiAnagrafichePK) object;
        if (this.idPraticaEvento != other.idPraticaEvento) {
            return false;
        }
        if (this.idAnagrafica != other.idAnagrafica) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.PraticheEventiAnagrafichePK[ idPraticaEvento=" + idPraticaEvento + ", idAnagrafica=" + idAnagrafica + " ]";
    }
    
}
