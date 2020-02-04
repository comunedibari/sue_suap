/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author piergiorgio
 */
@Entity
@Table(name = "pratica_organi_collegiali")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PraticaOrganiCollegiali.findAll", query = "SELECT p FROM PraticaOrganiCollegiali p"),
    @NamedQuery(name = "PraticaOrganiCollegiali.findByIdPraticaOrganiCollegiali", query = "SELECT p FROM PraticaOrganiCollegiali p WHERE p.idPraticaOrganiCollegiali = :idPraticaOrganiCollegiali"),
    @NamedQuery(name = "PraticaOrganiCollegiali.findByDataRichiesta", query = "SELECT p FROM PraticaOrganiCollegiali p WHERE p.dataRichiesta = :dataRichiesta")})
public class PraticaOrganiCollegiali implements Serializable {
    @JoinColumn(name = "id_seduta", referencedColumnName = "id_seduta")
    @ManyToOne
    private OrganiCollegialiSedute idSeduta;
    @OneToMany(mappedBy = "idPraticaOrganiCollegiali")
    private List<OcSedutePratiche> ocSedutePraticheList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pratica_organi_collegiali")
    private Integer idPraticaOrganiCollegiali;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_richiesta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRichiesta;
    @JoinColumn(name = "id_stato_pratica_organi_collegiali", referencedColumnName = "id_stato_pratica_organi_collegiali")
    @ManyToOne(optional = false)
    private LkStatiPraticaOrganiCollegiali idStatoPraticaOrganiCollegiali;
    @JoinColumn(name = "id_organi_collegiali", referencedColumnName = "id_organi_collegiali")
    @ManyToOne(optional = false)
    private OrganiCollegiali idOrganiCollegiali;
    @JoinColumn(name = "id_pratica", referencedColumnName = "id_pratica")
    @ManyToOne(optional = false)
    private Pratica idPratica;
  

    public PraticaOrganiCollegiali() {
    }

    public PraticaOrganiCollegiali(Integer idPraticaOrganiCollegiali) {
        this.idPraticaOrganiCollegiali = idPraticaOrganiCollegiali;
    }

    public PraticaOrganiCollegiali(Integer idPraticaOrganiCollegiali, Date dataRichiesta) {
        this.idPraticaOrganiCollegiali = idPraticaOrganiCollegiali;
        this.dataRichiesta = dataRichiesta;
    }

    public Integer getIdPraticaOrganiCollegiali() {
        return idPraticaOrganiCollegiali;
    }

    public void setIdPraticaOrganiCollegiali(Integer idPraticaOrganiCollegiali) {
        this.idPraticaOrganiCollegiali = idPraticaOrganiCollegiali;
    }

    public Date getDataRichiesta() {
        return dataRichiesta;
    }

    public void setDataRichiesta(Date dataRichiesta) {
        this.dataRichiesta = dataRichiesta;
    }

    public LkStatiPraticaOrganiCollegiali getIdStatoPraticaOrganiCollegiali() {
        return idStatoPraticaOrganiCollegiali;
    }

    public void setIdStatoPraticaOrganiCollegiali(LkStatiPraticaOrganiCollegiali idStatoPraticaOrganiCollegiali) {
        this.idStatoPraticaOrganiCollegiali = idStatoPraticaOrganiCollegiali;
    }

    public OrganiCollegiali getIdOrganiCollegiali() {
        return idOrganiCollegiali;
    }

    public void setIdOrganiCollegiali(OrganiCollegiali idOrganiCollegiali) {
        this.idOrganiCollegiali = idOrganiCollegiali;
    }

    public Pratica getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Pratica idPratica) {
        this.idPratica = idPratica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPraticaOrganiCollegiali != null ? idPraticaOrganiCollegiali.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PraticaOrganiCollegiali)) {
            return false;
        }
        PraticaOrganiCollegiali other = (PraticaOrganiCollegiali) object;
        if ((this.idPraticaOrganiCollegiali == null && other.idPraticaOrganiCollegiali != null) || (this.idPraticaOrganiCollegiali != null && !this.idPraticaOrganiCollegiali.equals(other.idPraticaOrganiCollegiali))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.PraticaOrganiCollegiali[ idPraticaOrganiCollegiali=" + idPraticaOrganiCollegiali + " ]";
    }

    @XmlTransient
    @JsonIgnore
    public List<OcSedutePratiche> getOcSedutePraticheList() {
        return ocSedutePraticheList;
    }

    public void setOcSedutePraticheList(List<OcSedutePratiche> ocSedutePraticheList) {
        this.ocSedutePraticheList = ocSedutePraticheList;
    }

    public OrganiCollegialiSedute getIdSeduta() {
        return idSeduta;
    }

    public void setIdSeduta(OrganiCollegialiSedute idSeduta) {
        this.idSeduta = idSeduta;
    }

}
