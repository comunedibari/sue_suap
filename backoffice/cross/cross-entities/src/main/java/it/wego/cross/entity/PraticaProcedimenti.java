/*
 * To change this template, choose Tools | Templates
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
 * @author giuseppe
 */
@Entity
@Table(name = "pratica_procedimenti")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PraticaProcedimenti.findAll", query = "SELECT p FROM PraticaProcedimenti p"),
    @NamedQuery(name = "PraticaProcedimenti.findByIdPratica", query = "SELECT p FROM PraticaProcedimenti p WHERE p.praticaProcedimentiPK.idPratica = :idPratica"),
    @NamedQuery(name = "PraticaProcedimenti.findByIdProcedimento", query = "SELECT p FROM PraticaProcedimenti p WHERE p.praticaProcedimentiPK.idProcedimento = :idProcedimento"),
    @NamedQuery(name = "PraticaProcedimenti.findByIdEnte", query = "SELECT p FROM PraticaProcedimenti p WHERE p.praticaProcedimentiPK.idEnte = :idEnte")})
public class PraticaProcedimenti implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PraticaProcedimentiPK praticaProcedimentiPK;
    @JoinColumn(name = "id_procedimento", referencedColumnName = "id_proc", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Procedimenti procedimenti;
    @JoinColumn(name = "id_pratica", referencedColumnName = "id_pratica", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pratica pratica;
    @JoinColumn(name = "id_ente", referencedColumnName = "id_ente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Enti enti;

    public PraticaProcedimenti() {
    }

    public PraticaProcedimenti(PraticaProcedimentiPK praticaProcedimentiPK) {
        this.praticaProcedimentiPK = praticaProcedimentiPK;
    }

    public PraticaProcedimenti(int idPratica, int idProcedimento, int idEnte) {
        this.praticaProcedimentiPK = new PraticaProcedimentiPK(idPratica, idProcedimento, idEnte);
    }

    public PraticaProcedimentiPK getPraticaProcedimentiPK() {
        return praticaProcedimentiPK;
    }

    public void setPraticaProcedimentiPK(PraticaProcedimentiPK praticaProcedimentiPK) {
        this.praticaProcedimentiPK = praticaProcedimentiPK;
    }

    public Procedimenti getProcedimenti() {
        return procedimenti;
    }

    public void setProcedimenti(Procedimenti procedimenti) {
        this.procedimenti = procedimenti;
    }

    public Pratica getPratica() {
        return pratica;
    }

    public void setPratica(Pratica pratica) {
        this.pratica = pratica;
    }

    public Enti getEnti() {
        return enti;
    }

    public void setEnti(Enti enti) {
        this.enti = enti;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (praticaProcedimentiPK != null ? praticaProcedimentiPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PraticaProcedimenti)) {
            return false;
        }
        PraticaProcedimenti other = (PraticaProcedimenti) object;
        if ((this.praticaProcedimentiPK == null && other.praticaProcedimentiPK != null) || (this.praticaProcedimentiPK != null && !this.praticaProcedimentiPK.equals(other.praticaProcedimentiPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.PraticaProcedimenti[ praticaProcedimentiPK=" + praticaProcedimentiPK + " ]";
    }
    
}
