/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author piergiorgio
 */
@Entity
@Table(name = "pratica_pratica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PraticaPratica.findAll", query = "SELECT p FROM PraticaPratica p"),
    @NamedQuery(name = "PraticaPratica.findByIdPraticaA", query = "SELECT p FROM PraticaPratica p WHERE p.praticaPraticaPK.idPraticaA = :idPraticaA"),
    @NamedQuery(name = "PraticaPratica.findByIdPraticaB", query = "SELECT p FROM PraticaPratica p WHERE p.praticaPraticaPK.idPraticaB = :idPraticaB")})
public class PraticaPratica implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PraticaPraticaPK praticaPraticaPK;

    public PraticaPratica() {
    }

    public PraticaPratica(PraticaPraticaPK praticaPraticaPK) {
        this.praticaPraticaPK = praticaPraticaPK;
    }

    public PraticaPratica(int idPraticaA, int idPraticaB) {
        this.praticaPraticaPK = new PraticaPraticaPK(idPraticaA, idPraticaB);
    }

    public PraticaPraticaPK getPraticaPraticaPK() {
        return praticaPraticaPK;
    }

    public void setPraticaPraticaPK(PraticaPraticaPK praticaPraticaPK) {
        this.praticaPraticaPK = praticaPraticaPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (praticaPraticaPK != null ? praticaPraticaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PraticaPratica)) {
            return false;
        }
        PraticaPratica other = (PraticaPratica) object;
        if ((this.praticaPraticaPK == null && other.praticaPraticaPK != null) || (this.praticaPraticaPK != null && !this.praticaPraticaPK.equals(other.praticaPraticaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.PraticaPratica[ praticaPraticaPK=" + praticaPraticaPK + " ]";
    }
    
}
