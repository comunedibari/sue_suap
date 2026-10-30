/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "enti")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Enti.findAll", query = "SELECT e FROM Enti e"),
    @NamedQuery(name = "Enti.findByIdEnte", query = "SELECT e FROM Enti e WHERE e.idEnte = :idEnte"),
    @NamedQuery(name = "Enti.findByCodEnte", query = "SELECT e FROM Enti e WHERE e.codEnte = :codEnte"),
    @NamedQuery(name = "Enti.findByCodiceFiscale", query = "SELECT e FROM Enti e WHERE e.codiceFiscale = :codiceFiscale"),
    @NamedQuery(name = "Enti.findByPartitaIva", query = "SELECT e FROM Enti e WHERE e.partitaIva = :partitaIva"),
    @NamedQuery(name = "Enti.findByDescrizione", query = "SELECT e FROM Enti e WHERE e.descrizione = :descrizione"),
    @NamedQuery(name = "Enti.findByIndirizzo", query = "SELECT e FROM Enti e WHERE e.indirizzo = :indirizzo"),
    @NamedQuery(name = "Enti.findByCap", query = "SELECT e FROM Enti e WHERE e.cap = :cap"),
    @NamedQuery(name = "Enti.findByCitta", query = "SELECT e FROM Enti e WHERE e.citta = :citta"),
    @NamedQuery(name = "Enti.findByProvincia", query = "SELECT e FROM Enti e WHERE e.provincia = :provincia"),
    @NamedQuery(name = "Enti.findByTelefono", query = "SELECT e FROM Enti e WHERE e.telefono = :telefono"),
    @NamedQuery(name = "Enti.findByFax", query = "SELECT e FROM Enti e WHERE e.fax = :fax"),
    @NamedQuery(name = "Enti.findByEmail", query = "SELECT e FROM Enti e WHERE e.email = :email"),
    @NamedQuery(name = "Enti.findByPec", query = "SELECT e FROM Enti e WHERE e.pec = :pec"),
    @NamedQuery(name = "Enti.findByCodiceIstat", query = "SELECT e FROM Enti e WHERE e.codiceIstat = :codiceIstat"),
    @NamedQuery(name = "Enti.findByCodiceCatastale", query = "SELECT e FROM Enti e WHERE e.codiceCatastale = :codiceCatastale"),
    @NamedQuery(name = "Enti.findByCodiceAoo", query = "SELECT e FROM Enti e WHERE e.codiceAoo = :codiceAoo"),
    @NamedQuery(name = "Enti.findByTipoEnte", query = "SELECT e FROM Enti e WHERE e.tipoEnte = :tipoEnte"),
    @NamedQuery(name = "Enti.findByCodEnteEsterno", query = "SELECT e FROM Enti e WHERE e.codEnteEsterno = :codEnteEsterno"),
    @NamedQuery(name = "Enti.findByUnitaOrganizzativa", query = "SELECT e FROM Enti e WHERE e.unitaOrganizzativa = :unitaOrganizzativa"),
    @NamedQuery(name = "Enti.findByCodiceAmministrazione", query = "SELECT e FROM Enti e WHERE e.codiceAmministrazione = :codiceAmministrazione"),
    @NamedQuery(name = "Enti.findByIdentificativoSuap", query = "SELECT e FROM Enti e WHERE e.identificativoSuap = :identificativoSuap")})
public class Enti implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEnte")
    private List<OrganiCollegiali> organiCollegialiList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEnte")
    private List<UtenteRuoloEnte> utenteRuoloEnteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "enti")
    private List<ProcessiEventiEnti> processiEventiEntiList;

    public static final String TIPO_ENTE_SUAP = "SUAP";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ente")
    private Integer idEnte;
    @Column(name = "cod_ente")
    private String codEnte;
    @Column(name = "codice_fiscale")
    private String codiceFiscale;
    @Column(name = "partita_iva")
    private String partitaIva;
    @Basic(optional = false)
    @Column(name = "descrizione")
    private String descrizione;
    @Column(name = "indirizzo")
    private String indirizzo;
    @Column(name = "cap")
    private Integer cap;
    @Column(name = "citta")
    private String citta;
    @Column(name = "provincia")
    private String provincia;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "fax")
    private String fax;
    @Column(name = "email")
    private String email;
    @Column(name = "pec")
    private String pec;
    @Column(name = "codice_istat")
    private String codiceIstat;
    @Column(name = "codice_catastale")
    private String codiceCatastale;
    @Column(name = "codice_aoo")
    private String codiceAoo;
    @Column(name = "tipo_ente")
    private String tipoEnte;
    @Column(name = "cod_ente_esterno")
    private String codEnteEsterno;
    @Column(name = "unita_organizzativa")
    private String unitaOrganizzativa;
    @Column(name = "codice_amministrazione")
    private String codiceAmministrazione;
    @Column(name = "identificativo_suap")
    private String identificativoSuap;
    @JoinTable(name = "enti_comuni", joinColumns = {
        @JoinColumn(name = "id_ente", referencedColumnName = "id_ente")}, inverseJoinColumns = {
        @JoinColumn(name = "id_comune", referencedColumnName = "id_comune")})
    @ManyToMany
    private List<LkComuni> lkComuniList;
    @ManyToMany(mappedBy = "entiList")
    private List<PraticheEventi> praticheEventiList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEnte")
    private List<EventiTemplate> eventiTemplateList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEnte")
    private List<ProcedimentiEnti> procedimentiEntiList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEnte")
    private List<Staging> stagingList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "enti")
    private List<PraticaProcedimenti> praticaProcedimentiList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEnte")
    private List<Comunicazione> comunicazioneList;
    @OneToMany(mappedBy = "idMittenteEnte")
    private List<PraticheProtocollo> praticheProtocolloList;
    @OneToMany(mappedBy = "idEntePadre")
    private List<Enti> entiList;
    @JoinColumn(name = "id_ente_padre", referencedColumnName = "id_ente")
    @ManyToOne
    private Enti idEntePadre;
    @OneToMany(mappedBy = "idEnte")
    private List<PluginConfiguration> pluginConfigurationList;
    @OneToMany(mappedBy = "idEnte")
    private List<Configuration> configurationList;
    @JoinTable(name = "ugm_ugd_proc",
            joinColumns = {
                @JoinColumn(name = "id_ugm", referencedColumnName = "id_ente")},
            inverseJoinColumns = {
                @JoinColumn(name = "id_ugd_proc", referencedColumnName = "id_proc_ente")})
    @ManyToMany
    private List<ProcedimentiEnti> endoProcedimentiList;
    
    @Transient
    private boolean enteDaEvento = false;

    public Enti() {
    }

    public Enti(Integer idEnte) {
        this.idEnte = idEnte;
    }

    public Enti(Integer idEnte, String descrizione) {
        this.idEnte = idEnte;
        this.descrizione = descrizione;
    }

    public Integer getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(Integer idEnte) {
        this.idEnte = idEnte;
    }

    public String getCodEnte() {
        return codEnte;
    }

    public void setCodEnte(String codEnte) {
        this.codEnte = codEnte;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public Integer getCap() {
        return cap;
    }

    public void setCap(Integer cap) {
        this.cap = cap;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPec() {
        return pec;
    }

    public void setPec(String pec) {
        this.pec = pec;
    }

    public String getCodiceIstat() {
        return codiceIstat;
    }

    public void setCodiceIstat(String codiceIstat) {
        this.codiceIstat = codiceIstat;
    }

    public String getCodiceCatastale() {
        return codiceCatastale;
    }

    public void setCodiceCatastale(String codiceCatastale) {
        this.codiceCatastale = codiceCatastale;
    }

    public String getCodiceAoo() {
        return codiceAoo;
    }

    public void setCodiceAoo(String codiceAoo) {
        this.codiceAoo = codiceAoo;
    }

    public String getTipoEnte() {
        return tipoEnte;
    }

    public void setTipoEnte(String tipoEnte) {
        this.tipoEnte = tipoEnte;
    }

    public String getCodEnteEsterno() {
        return codEnteEsterno;
    }

    public void setCodEnteEsterno(String codEnteEsterno) {
        this.codEnteEsterno = codEnteEsterno;
    }

    public String getUnitaOrganizzativa() {
        return unitaOrganizzativa;
    }

    public void setUnitaOrganizzativa(String unitaOrganizzativa) {
        this.unitaOrganizzativa = unitaOrganizzativa;
    }

    public String getCodiceAmministrazione() {
        return codiceAmministrazione;
    }

    public void setCodiceAmministrazione(String codiceAmministrazione) {
        this.codiceAmministrazione = codiceAmministrazione;
    }

    public String getIdentificativoSuap() {
        return identificativoSuap;
    }

    public void setIdentificativoSuap(String identificativoSuap) {
        this.identificativoSuap = identificativoSuap;
    }

    @XmlTransient
    public List<LkComuni> getLkComuniList() {
        return lkComuniList;
    }

    public void setLkComuniList(List<LkComuni> lkComuniList) {
        this.lkComuniList = lkComuniList;
    }

    @XmlTransient
    public List<PraticheEventi> getPraticheEventiList() {
        return praticheEventiList;
    }

    public void setPraticheEventiList(List<PraticheEventi> praticheEventiList) {
        this.praticheEventiList = praticheEventiList;
    }

    @XmlTransient
    public List<EventiTemplate> getEventiTemplateList() {
        return eventiTemplateList;
    }

    public void setEventiTemplateList(List<EventiTemplate> eventiTemplateList) {
        this.eventiTemplateList = eventiTemplateList;
    }

    @XmlTransient
    public List<ProcedimentiEnti> getProcedimentiEntiList() {
        return procedimentiEntiList;
    }

    public void setProcedimentiEntiList(List<ProcedimentiEnti> procedimentiEntiList) {
        this.procedimentiEntiList = procedimentiEntiList;
    }

    public List<ProcedimentiEnti> getEndoProcedimentiList() {
        return endoProcedimentiList;
    }

    public void setEndoProcedimentiList(List<ProcedimentiEnti> endoProcedimentiList) {
        this.endoProcedimentiList = endoProcedimentiList;
    }

    @XmlTransient
    public List<Staging> getStagingList() {
        return stagingList;
    }

    public void setStagingList(List<Staging> stagingList) {
        this.stagingList = stagingList;
    }

    @XmlTransient
    public List<PraticaProcedimenti> getPraticaProcedimentiList() {
        return praticaProcedimentiList;
    }

    public void setPraticaProcedimentiList(List<PraticaProcedimenti> praticaProcedimentiList) {
        this.praticaProcedimentiList = praticaProcedimentiList;
    }

    @XmlTransient
    public List<Comunicazione> getComunicazioneList() {
        return comunicazioneList;
    }

    public void setComunicazioneList(List<Comunicazione> comunicazioneList) {
        this.comunicazioneList = comunicazioneList;
    }

    @XmlTransient
    public List<PraticheProtocollo> getPraticheProtocolloList() {
        return praticheProtocolloList;
    }

    public void setPraticheProtocolloList(List<PraticheProtocollo> praticheProtocolloList) {
        this.praticheProtocolloList = praticheProtocolloList;
    }

    @XmlTransient
    public List<Enti> getEntiList() {
        return entiList;
    }

    public void setEntiList(List<Enti> entiList) {
        this.entiList = entiList;
    }

    public Enti getIdEntePadre() {
        return idEntePadre;
    }

    public void setIdEntePadre(Enti idEntePadre) {
        this.idEntePadre = idEntePadre;
    }

    @XmlTransient
    public List<Configuration> getConfigurationList() {
        return configurationList;
    }

    public void setConfigurationList(List<Configuration> configurationList) {
        this.configurationList = configurationList;
    }

    @XmlTransient
    public List<PluginConfiguration> getPluginConfigurationList() {
        return pluginConfigurationList;
    }

    public void setPluginConfigurationList(List<PluginConfiguration> pluginConfigurationList) {
        this.pluginConfigurationList = pluginConfigurationList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEnte != null ? idEnte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Enti)) {
            return false;
        }
        Enti other = (Enti) object;
        if ((this.idEnte == null && other.idEnte != null) || (this.idEnte != null && !this.idEnte.equals(other.idEnte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.Enti[ idEnte=" + idEnte + " ]";
    }

    @XmlTransient
    @JsonIgnore
    public List<ProcessiEventiEnti> getProcessiEventiEntiList() {
        return processiEventiEntiList;
    }

    public void setProcessiEventiEntiList(List<ProcessiEventiEnti> processiEventiEntiList) {
        this.processiEventiEntiList = processiEventiEntiList;
    }

    public boolean isEnteDaEvento() {
        return enteDaEvento;
    }

    public void setEnteDaEvento(boolean enteDaEvento) {
        this.enteDaEvento = enteDaEvento;
    }

    @XmlTransient
    @JsonIgnore
    public List<UtenteRuoloEnte> getUtenteRuoloEnteList() {
        return utenteRuoloEnteList;
    }

    public void setUtenteRuoloEnteList(List<UtenteRuoloEnte> utenteRuoloEnteList) {
        this.utenteRuoloEnteList = utenteRuoloEnteList;
    }

    @XmlTransient
    @JsonIgnore
    public List<OrganiCollegiali> getOrganiCollegialiList() {
        return organiCollegialiList;
    }

    public void setOrganiCollegialiList(List<OrganiCollegiali> organiCollegialiList) {
        this.organiCollegialiList = organiCollegialiList;
    }

}
