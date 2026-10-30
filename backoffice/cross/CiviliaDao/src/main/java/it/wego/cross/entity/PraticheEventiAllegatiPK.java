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

/**
 *
 * @author Gabriele
 */
@Embeddable
public class PraticheEventiAllegatiPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_pratica_evento")
    private int idPraticaEvento;
    @Basic(optional = false)
    @Column(name = "id_allegato")
    private int idAllegato;

    public PraticheEventiAllegatiPK() {
    }

    public PraticheEventiAllegatiPK(int idPraticaEvento, int idAllegato) {
        this.idPraticaEvento = idPraticaEvento;
        this.idAllegato = idAllegato;
    }

    public int getIdPraticaEvento() {
        return idPraticaEvento;
    }

    public void setIdPraticaEvento(int idPraticaEvento) {
        this.idPraticaEvento = idPraticaEvento;
    }

    public int getIdAllegato() {
        return idAllegato;
    }

    public void setIdAllegato(int idAllegato) {
        this.idAllegato = idAllegato;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPraticaEvento;
        hash += (int) idAllegato;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PraticheEventiAllegatiPK)) {
            return false;
        }
        PraticheEventiAllegatiPK other = (PraticheEventiAllegatiPK) object;
        if (this.idPraticaEvento != other.idPraticaEvento) {
            return false;
        }
        if (this.idAllegato != other.idAllegato) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.PraticheEventiAllegatiPK[ idPraticaEvento=" + idPraticaEvento + ", idAllegato=" + idAllegato + " ]";
    }
    
}
