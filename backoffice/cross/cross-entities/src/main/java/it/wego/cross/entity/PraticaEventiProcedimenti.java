/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "pratica_eventi_procedimenti")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PraticaEventiProcedimenti.findAll", query = "SELECT p FROM PraticaEventiProcedimenti p"),
    @NamedQuery(name = "PraticaEventiProcedimenti.findByIdPraticaEvento", query = "SELECT p FROM PraticaEventiProcedimenti p WHERE p.praticaEventiProcedimentiPK.idPraticaEvento = :idPraticaEvento"),
    @NamedQuery(name = "PraticaEventiProcedimenti.findByIdProcEnte", query = "SELECT p FROM PraticaEventiProcedimenti p WHERE p.praticaEventiProcedimentiPK.idProcEnte = :idProcEnte")})
public class PraticaEventiProcedimenti implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PraticaEventiProcedimentiPK praticaEventiProcedimentiPK;
    @JoinColumn(name = "id_proc_ente", referencedColumnName = "id_proc_ente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ProcedimentiEnti procedimentiEnti;
    @JoinColumn(name = "id_pratica_evento", referencedColumnName = "id_pratica_evento", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PraticheEventi praticheEventi;

    public PraticaEventiProcedimenti() {
    }

    public PraticaEventiProcedimenti(PraticaEventiProcedimentiPK praticaEventiProcedimentiPK) {
        this.praticaEventiProcedimentiPK = praticaEventiProcedimentiPK;
    }

    public PraticaEventiProcedimenti(int idPraticaEvento, int idProcEnte) {
        this.praticaEventiProcedimentiPK = new PraticaEventiProcedimentiPK(idPraticaEvento, idProcEnte);
    }

    public PraticaEventiProcedimentiPK getPraticaEventiProcedimentiPK() {
        return praticaEventiProcedimentiPK;
    }

    public void setPraticaEventiProcedimentiPK(PraticaEventiProcedimentiPK praticaEventiProcedimentiPK) {
        this.praticaEventiProcedimentiPK = praticaEventiProcedimentiPK;
    }

    public ProcedimentiEnti getProcedimentiEnti() {
        return procedimentiEnti;
    }

    public void setProcedimentiEnti(ProcedimentiEnti procedimentiEnti) {
        this.procedimentiEnti = procedimentiEnti;
    }

    public PraticheEventi getPraticheEventi() {
        return praticheEventi;
    }

    public void setPraticheEventi(PraticheEventi praticheEventi) {
        this.praticheEventi = praticheEventi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (praticaEventiProcedimentiPK != null ? praticaEventiProcedimentiPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PraticaEventiProcedimenti)) {
            return false;
        }
        PraticaEventiProcedimenti other = (PraticaEventiProcedimenti) object;
        if ((this.praticaEventiProcedimentiPK == null && other.praticaEventiProcedimentiPK != null) || (this.praticaEventiProcedimentiPK != null && !this.praticaEventiProcedimentiPK.equals(other.praticaEventiProcedimentiPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.PraticaEventiProcedimenti[ praticaEventiProcedimentiPK=" + praticaEventiProcedimentiPK + " ]";
    }
    
}
