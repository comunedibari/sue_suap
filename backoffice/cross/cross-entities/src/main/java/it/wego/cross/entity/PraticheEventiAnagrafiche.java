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
@Table(name = "pratiche_eventi_anagrafiche")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PraticheEventiAnagrafiche.findAll", query = "SELECT p FROM PraticheEventiAnagrafiche p"),
    @NamedQuery(name = "PraticheEventiAnagrafiche.findByIdPraticaEvento", query = "SELECT p FROM PraticheEventiAnagrafiche p WHERE p.praticheEventiAnagrafichePK.idPraticaEvento = :idPraticaEvento"),
    @NamedQuery(name = "PraticheEventiAnagrafiche.findByIdAnagrafica", query = "SELECT p FROM PraticheEventiAnagrafiche p WHERE p.praticheEventiAnagrafichePK.idAnagrafica = :idAnagrafica")})
public class PraticheEventiAnagrafiche implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PraticheEventiAnagrafichePK praticheEventiAnagrafichePK;
    @JoinColumn(name = "id_recapito", referencedColumnName = "id_recapito")
    @ManyToOne
    private Recapiti idRecapito;
    @JoinColumn(name = "id_anagrafica", referencedColumnName = "id_anagrafica", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Anagrafica anagrafica;
    @JoinColumn(name = "id_pratica_evento", referencedColumnName = "id_pratica_evento", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PraticheEventi praticheEventi;

    public PraticheEventiAnagrafiche() {
    }

    public PraticheEventiAnagrafiche(PraticheEventiAnagrafichePK praticheEventiAnagrafichePK) {
        this.praticheEventiAnagrafichePK = praticheEventiAnagrafichePK;
    }

    public PraticheEventiAnagrafiche(int idPraticaEvento, int idAnagrafica) {
        this.praticheEventiAnagrafichePK = new PraticheEventiAnagrafichePK(idPraticaEvento, idAnagrafica);
    }

    public PraticheEventiAnagrafichePK getPraticheEventiAnagrafichePK() {
        return praticheEventiAnagrafichePK;
    }

    public void setPraticheEventiAnagrafichePK(PraticheEventiAnagrafichePK praticheEventiAnagrafichePK) {
        this.praticheEventiAnagrafichePK = praticheEventiAnagrafichePK;
    }

    public Recapiti getIdRecapito() {
        return idRecapito;
    }

    public void setIdRecapito(Recapiti idRecapito) {
        this.idRecapito = idRecapito;
    }

    public Anagrafica getAnagrafica() {
        return anagrafica;
    }

    public void setAnagrafica(Anagrafica anagrafica) {
        this.anagrafica = anagrafica;
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
        hash += (praticheEventiAnagrafichePK != null ? praticheEventiAnagrafichePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PraticheEventiAnagrafiche)) {
            return false;
        }
        PraticheEventiAnagrafiche other = (PraticheEventiAnagrafiche) object;
        if ((this.praticheEventiAnagrafichePK == null && other.praticheEventiAnagrafichePK != null) || (this.praticheEventiAnagrafichePK != null && !this.praticheEventiAnagrafichePK.equals(other.praticheEventiAnagrafichePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.PraticheEventiAnagrafiche[ praticheEventiAnagrafichePK=" + praticheEventiAnagrafichePK + " ]";
    }
    
}
