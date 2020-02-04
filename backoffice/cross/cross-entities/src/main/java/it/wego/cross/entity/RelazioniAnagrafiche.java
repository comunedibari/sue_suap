/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "relazioni_anagrafiche")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RelazioniAnagrafiche.findAll", query = "SELECT r FROM RelazioniAnagrafiche r"),
    @NamedQuery(name = "RelazioniAnagrafiche.findByIdAna1", query = "SELECT r FROM RelazioniAnagrafiche r WHERE r.relazioniAnagrafichePK.idAna1 = :idAna1"),
    @NamedQuery(name = "RelazioniAnagrafiche.findByIdAna2", query = "SELECT r FROM RelazioniAnagrafiche r WHERE r.relazioniAnagrafichePK.idAna2 = :idAna2"),
    @NamedQuery(name = "RelazioniAnagrafiche.findByDataInizioValidita", query = "SELECT r FROM RelazioniAnagrafiche r WHERE r.dataInizioValidita = :dataInizioValidita")})
public class RelazioniAnagrafiche implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RelazioniAnagrafichePK relazioniAnagrafichePK;
    @Column(name = "data_inizio_validita")
    @Temporal(TemporalType.DATE)
    private Date dataInizioValidita;
    @JoinColumn(name = "id_tipo_relazione", referencedColumnName = "id")
    @ManyToOne
    private LkTipoLegameAnagrafica idTipoRelazione;
    @JoinColumn(name = "id_ana_2", referencedColumnName = "id_anagrafica", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Anagrafica anagrafica;
    @JoinColumn(name = "id_ana_1", referencedColumnName = "id_anagrafica", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Anagrafica anagrafica1;

    public RelazioniAnagrafiche() {
    }

    public RelazioniAnagrafiche(RelazioniAnagrafichePK relazioniAnagrafichePK) {
        this.relazioniAnagrafichePK = relazioniAnagrafichePK;
    }

    public RelazioniAnagrafiche(int idAna1, int idAna2) {
        this.relazioniAnagrafichePK = new RelazioniAnagrafichePK(idAna1, idAna2);
    }

    public RelazioniAnagrafichePK getRelazioniAnagrafichePK() {
        return relazioniAnagrafichePK;
    }

    public void setRelazioniAnagrafichePK(RelazioniAnagrafichePK relazioniAnagrafichePK) {
        this.relazioniAnagrafichePK = relazioniAnagrafichePK;
    }

    public Date getDataInizioValidita() {
        return dataInizioValidita;
    }

    public void setDataInizioValidita(Date dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }

    public LkTipoLegameAnagrafica getIdTipoRelazione() {
        return idTipoRelazione;
    }

    public void setIdTipoRelazione(LkTipoLegameAnagrafica idTipoRelazione) {
        this.idTipoRelazione = idTipoRelazione;
    }

    public Anagrafica getAnagrafica() {
        return anagrafica;
    }

    public void setAnagrafica(Anagrafica anagrafica) {
        this.anagrafica = anagrafica;
    }

    public Anagrafica getAnagrafica1() {
        return anagrafica1;
    }

    public void setAnagrafica1(Anagrafica anagrafica1) {
        this.anagrafica1 = anagrafica1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (relazioniAnagrafichePK != null ? relazioniAnagrafichePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelazioniAnagrafiche)) {
            return false;
        }
        RelazioniAnagrafiche other = (RelazioniAnagrafiche) object;
        if ((this.relazioniAnagrafichePK == null && other.relazioniAnagrafichePK != null) || (this.relazioniAnagrafichePK != null && !this.relazioniAnagrafichePK.equals(other.relazioniAnagrafichePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.RelazioniAnagrafiche[ relazioniAnagrafichePK=" + relazioniAnagrafichePK + " ]";
    }
    
}
