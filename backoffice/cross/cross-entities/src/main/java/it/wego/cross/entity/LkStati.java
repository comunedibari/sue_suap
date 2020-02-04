/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "lk_stati")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkStati.findAll", query = "SELECT l FROM LkStati l"),
    @NamedQuery(name = "LkStati.findByIdStato", query = "SELECT l FROM LkStati l WHERE l.idStato = :idStato"),
    @NamedQuery(name = "LkStati.findByDescrizione", query = "SELECT l FROM LkStati l WHERE l.descrizione = :descrizione"),
    @NamedQuery(name = "LkStati.findByCodIstat", query = "SELECT l FROM LkStati l WHERE l.codIstat = :codIstat"),
    @NamedQuery(name = "LkStati.findByDataInizio", query = "SELECT l FROM LkStati l WHERE l.dataInizio = :dataInizio"),
    @NamedQuery(name = "LkStati.findByDataFine", query = "SELECT l FROM LkStati l WHERE l.dataFine = :dataFine"),
    @NamedQuery(name = "LkStati.findByCodStato", query = "SELECT l FROM LkStati l WHERE l.codStato = :codStato")})
public class LkStati implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_stato")
    private Integer idStato;
    @Basic(optional = false)
    @Column(name = "descrizione")
    private String descrizione;
    @Basic(optional = false)
    @Column(name = "cod_istat")
    private String codIstat;
    @Basic(optional = false)
    @Column(name = "data_inizio")
    @Temporal(TemporalType.DATE)
    private Date dataInizio;
    @Basic(optional = false)
    @Column(name = "data_fine")
    @Temporal(TemporalType.DATE)
    private Date dataFine;
    @Column(name = "cod_stato")
    private String codStato;
    @OneToMany(mappedBy = "idStato")
    private List<LkComuni> lkComuniList;

    public LkStati() {
    }

    public LkStati(Integer idStato) {
        this.idStato = idStato;
    }

    public LkStati(Integer idStato, String descrizione, String codIstat, Date dataInizio, Date dataFine) {
        this.idStato = idStato;
        this.descrizione = descrizione;
        this.codIstat = codIstat;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public Integer getIdStato() {
        return idStato;
    }

    public void setIdStato(Integer idStato) {
        this.idStato = idStato;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCodIstat() {
        return codIstat;
    }

    public void setCodIstat(String codIstat) {
        this.codIstat = codIstat;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public String getCodStato() {
        return codStato;
    }

    public void setCodStato(String codStato) {
        this.codStato = codStato;
    }

    @XmlTransient
    public List<LkComuni> getLkComuniList() {
        return lkComuniList;
    }

    public void setLkComuniList(List<LkComuni> lkComuniList) {
        this.lkComuniList = lkComuniList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idStato != null ? idStato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkStati)) {
            return false;
        }
        LkStati other = (LkStati) object;
        if ((this.idStato == null && other.idStato != null) || (this.idStato != null && !this.idStato.equals(other.idStato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkStati[ idStato=" + idStato + " ]";
    }
    
}
