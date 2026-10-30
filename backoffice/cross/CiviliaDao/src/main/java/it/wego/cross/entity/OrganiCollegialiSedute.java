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
import javax.persistence.CascadeType;
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
@Table(name = "organi_collegiali_sedute")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrganiCollegialiSedute.findAll", query = "SELECT o FROM OrganiCollegialiSedute o"),
    @NamedQuery(name = "OrganiCollegialiSedute.findByIdSeduta", query = "SELECT o FROM OrganiCollegialiSedute o WHERE o.idSeduta = :idSeduta"),
    @NamedQuery(name = "OrganiCollegialiSedute.findByDataPrevista", query = "SELECT o FROM OrganiCollegialiSedute o WHERE o.dataPrevista = :dataPrevista"),
    @NamedQuery(name = "OrganiCollegialiSedute.findByDataConvocazione", query = "SELECT o FROM OrganiCollegialiSedute o WHERE o.dataConvocazione = :dataConvocazione"),
    @NamedQuery(name = "OrganiCollegialiSedute.findByOraConvocazione", query = "SELECT o FROM OrganiCollegialiSedute o WHERE o.oraConvocazione = :oraConvocazione"),
    @NamedQuery(name = "OrganiCollegialiSedute.findByDataInizio", query = "SELECT o FROM OrganiCollegialiSedute o WHERE o.dataInizio = :dataInizio"),
    @NamedQuery(name = "OrganiCollegialiSedute.findByOraInizio", query = "SELECT o FROM OrganiCollegialiSedute o WHERE o.oraInizio = :oraInizio"),
    @NamedQuery(name = "OrganiCollegialiSedute.findByDataConclusione", query = "SELECT o FROM OrganiCollegialiSedute o WHERE o.dataConclusione = :dataConclusione"),
    @NamedQuery(name = "OrganiCollegialiSedute.findByOraConclusione", query = "SELECT o FROM OrganiCollegialiSedute o WHERE o.oraConclusione = :oraConclusione")})
public class OrganiCollegialiSedute implements Serializable {
    @JoinColumn(name = "id_stato_seduta", referencedColumnName = "id_stato_seduta")
    @ManyToOne
    private LkStatiSedute idStatoSeduta;
    @OneToMany(mappedBy = "idSeduta")
    private List<PraticaOrganiCollegiali> praticaOrganiCollegialiList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSeduta")
    private List<OcSedutePratiche> ocSedutePraticheList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_seduta")
    private Integer idSeduta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_prevista")
    @Temporal(TemporalType.DATE)
    private Date dataPrevista;
    @Column(name = "data_convocazione")
    @Temporal(TemporalType.DATE)
    private Date dataConvocazione;
    @Column(name = "ora_convocazione")
    private String oraConvocazione;
    @Column(name = "data_inizio")
    @Temporal(TemporalType.DATE)
    private Date dataInizio;
    @Column(name = "ora_inizio")
    private String oraInizio;
    @Column(name = "data_conclusione")
    @Temporal(TemporalType.DATE)
    private Date dataConclusione;
    @Column(name = "ora_conclusione")
    private String oraConclusione;
    @JoinColumn(name = "id_organo_collegiale", referencedColumnName = "id_organi_collegiali")
    @ManyToOne(optional = false)
    private OrganiCollegiali idOrganoCollegiale;

    public OrganiCollegialiSedute() {
    }

    public OrganiCollegialiSedute(Integer idSeduta) {
        this.idSeduta = idSeduta;
    }

    public OrganiCollegialiSedute(Integer idSeduta, Date dataPrevista) {
        this.idSeduta = idSeduta;
        this.dataPrevista = dataPrevista;
    }

    public Integer getIdSeduta() {
        return idSeduta;
    }

    public void setIdSeduta(Integer idSeduta) {
        this.idSeduta = idSeduta;
    }

    public Date getDataPrevista() {
        return dataPrevista;
    }

    public void setDataPrevista(Date dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    public Date getDataConvocazione() {
        return dataConvocazione;
    }

    public void setDataConvocazione(Date dataConvocazione) {
        this.dataConvocazione = dataConvocazione;
    }

    public String getOraConvocazione() {
        return oraConvocazione;
    }

    public void setOraConvocazione(String oraConvocazione) {
        this.oraConvocazione = oraConvocazione;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public String getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(String oraInizio) {
        this.oraInizio = oraInizio;
    }

    public Date getDataConclusione() {
        return dataConclusione;
    }

    public void setDataConclusione(Date dataConclusione) {
        this.dataConclusione = dataConclusione;
    }

    public String getOraConclusione() {
        return oraConclusione;
    }

    public void setOraConclusione(String oraConclusione) {
        this.oraConclusione = oraConclusione;
    }

    public OrganiCollegiali getIdOrganoCollegiale() {
        return idOrganoCollegiale;
    }

    public void setIdOrganoCollegiale(OrganiCollegiali idOrganoCollegiale) {
        this.idOrganoCollegiale = idOrganoCollegiale;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSeduta != null ? idSeduta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrganiCollegialiSedute)) {
            return false;
        }
        OrganiCollegialiSedute other = (OrganiCollegialiSedute) object;
        if ((this.idSeduta == null && other.idSeduta != null) || (this.idSeduta != null && !this.idSeduta.equals(other.idSeduta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.OrganiCollegialiSedute[ idSeduta=" + idSeduta + " ]";
    }

    @XmlTransient
    @JsonIgnore
    public List<OcSedutePratiche> getOcSedutePraticheList() {
        return ocSedutePraticheList;
    }

    public void setOcSedutePraticheList(List<OcSedutePratiche> ocSedutePraticheList) {
        this.ocSedutePraticheList = ocSedutePraticheList;
    }

    @XmlTransient
    @JsonIgnore
    public List<PraticaOrganiCollegiali> getPraticaOrganiCollegialiList() {
        return praticaOrganiCollegialiList;
    }

    public void setPraticaOrganiCollegialiList(List<PraticaOrganiCollegiali> praticaOrganiCollegialiList) {
        this.praticaOrganiCollegialiList = praticaOrganiCollegialiList;
    }

    public LkStatiSedute getIdStatoSeduta() {
        return idStatoSeduta;
    }

    public void setIdStatoSeduta(LkStatiSedute idStatoSeduta) {
        this.idStatoSeduta = idStatoSeduta;
    }

}
