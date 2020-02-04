/*
 * To change this template, choose Tools | Templates
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "anagrafica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Anagrafica.findAll", query = "SELECT a FROM Anagrafica a"),
    @NamedQuery(name = "Anagrafica.findByIdAnagrafica", query = "SELECT a FROM Anagrafica a WHERE a.idAnagrafica = :idAnagrafica"),
    @NamedQuery(name = "Anagrafica.findByTipoAnagrafica", query = "SELECT a FROM Anagrafica a WHERE a.tipoAnagrafica = :tipoAnagrafica"),
    @NamedQuery(name = "Anagrafica.findByCognome", query = "SELECT a FROM Anagrafica a WHERE a.cognome = :cognome"),
    @NamedQuery(name = "Anagrafica.findByNome", query = "SELECT a FROM Anagrafica a WHERE a.nome = :nome"),
    @NamedQuery(name = "Anagrafica.findByCodiceFiscale", query = "SELECT a FROM Anagrafica a WHERE a.codiceFiscale = :codiceFiscale"),
    @NamedQuery(name = "Anagrafica.findByDenominazione", query = "SELECT a FROM Anagrafica a WHERE a.denominazione = :denominazione"),
    @NamedQuery(name = "Anagrafica.findByPartitaIva", query = "SELECT a FROM Anagrafica a WHERE a.partitaIva = :partitaIva"),
    @NamedQuery(name = "Anagrafica.findByFlgIndividuale", query = "SELECT a FROM Anagrafica a WHERE a.flgIndividuale = :flgIndividuale"),
    @NamedQuery(name = "Anagrafica.findByDataNascita", query = "SELECT a FROM Anagrafica a WHERE a.dataNascita = :dataNascita"),
    @NamedQuery(name = "Anagrafica.findByLocalitaNascita", query = "SELECT a FROM Anagrafica a WHERE a.localitaNascita = :localitaNascita"),
    @NamedQuery(name = "Anagrafica.findBySesso", query = "SELECT a FROM Anagrafica a WHERE a.sesso = :sesso"),
    @NamedQuery(name = "Anagrafica.findByNumeroIscrizione", query = "SELECT a FROM Anagrafica a WHERE a.numeroIscrizione = :numeroIscrizione"),
    @NamedQuery(name = "Anagrafica.findByDataIscrizione", query = "SELECT a FROM Anagrafica a WHERE a.dataIscrizione = :dataIscrizione"),
    @NamedQuery(name = "Anagrafica.findByFlgAttesaIscrizioneRi", query = "SELECT a FROM Anagrafica a WHERE a.flgAttesaIscrizioneRi = :flgAttesaIscrizioneRi"),
    @NamedQuery(name = "Anagrafica.findByFlgObbligoIscrizioneRi", query = "SELECT a FROM Anagrafica a WHERE a.flgObbligoIscrizioneRi = :flgObbligoIscrizioneRi"),
    @NamedQuery(name = "Anagrafica.findByDataIscrizioneRi", query = "SELECT a FROM Anagrafica a WHERE a.dataIscrizioneRi = :dataIscrizioneRi"),
    @NamedQuery(name = "Anagrafica.findByNIscrizioneRi", query = "SELECT a FROM Anagrafica a WHERE a.nIscrizioneRi = :nIscrizioneRi"),
    @NamedQuery(name = "Anagrafica.findByFlgAttesaIscrizioneRea", query = "SELECT a FROM Anagrafica a WHERE a.flgAttesaIscrizioneRea = :flgAttesaIscrizioneRea"),
    @NamedQuery(name = "Anagrafica.findByDataIscrizioneRea", query = "SELECT a FROM Anagrafica a WHERE a.dataIscrizioneRea = :dataIscrizioneRea"),
    @NamedQuery(name = "Anagrafica.findByNIscrizioneRea", query = "SELECT a FROM Anagrafica a WHERE a.nIscrizioneRea = :nIscrizioneRea"),
    @NamedQuery(name = "Anagrafica.findByVarianteAnagrafica", query = "SELECT a FROM Anagrafica a WHERE a.varianteAnagrafica = :varianteAnagrafica")})
public class Anagrafica implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "anagrafica")
    private List<OcPraticaCommissione> ocPraticaCommissioneList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "anagrafica")
    private List<OrganiCollegialiCommissione> organiCollegialiCommissioneList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_anagrafica")
    private Integer idAnagrafica;
    @Basic(optional = false)
    @Column(name = "tipo_anagrafica")
    private Character tipoAnagrafica; 
    @Basic(optional = false)
    @Column(name = "flg_individuale")
    private Character flgIndividuale;    
    @Column(name = "cognome")
    private String cognome;
    @Column(name = "nome")
    private String nome;
    @Column(name = "codice_fiscale")
    private String codiceFiscale;
    @Column(name = "denominazione")
    private String denominazione;
    @Column(name = "partita_iva")
    private String partitaIva;
    @Column(name = "data_nascita")
    @Temporal(TemporalType.DATE)
    private Date dataNascita;
    @Column(name = "localita_nascita")
    private String localitaNascita;
    @Column(name = "sesso")
    private Character sesso;
    @Column(name = "numero_iscrizione")
    private String numeroIscrizione;
    @Column(name = "data_iscrizione")
    @Temporal(TemporalType.DATE)
    private Date dataIscrizione;
    @Column(name = "flg_attesa_iscrizione_ri")
    private Character flgAttesaIscrizioneRi;
    @Column(name = "flg_obbligo_iscrizione_ri")
    private Character flgObbligoIscrizioneRi;
    @Column(name = "data_iscrizione_ri")
    @Temporal(TemporalType.DATE)
    private Date dataIscrizioneRi;
    @Column(name = "n_iscrizione_ri")
    private String nIscrizioneRi;
    @Column(name = "flg_attesa_iscrizione_rea")
    private Character flgAttesaIscrizioneRea;
    @Column(name = "data_iscrizione_rea")
    @Temporal(TemporalType.DATE)
    private Date dataIscrizioneRea;
    @Column(name = "n_iscrizione_rea")
    private String nIscrizioneRea;
    @Column(name = "variante_anagrafica")
    private String varianteAnagrafica;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "anagrafica")
    private List<RelazioniAnagrafiche> relazioniAnagraficheList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "anagrafica1")
    private List<RelazioniAnagrafiche> relazioniAnagraficheList1;
    @JoinColumn(name = "id_forma_giuridica", referencedColumnName = "id_forme_giuridiche")
    @ManyToOne
    private LkFormeGiuridiche idFormaGiuridica;
    @JoinColumn(name = "id_nazionalita", referencedColumnName = "id_nazionalita")
    @ManyToOne
    private LkNazionalita idNazionalita;
    @JoinColumn(name = "id_provincia_iscrizione", referencedColumnName = "id_provincie")
    @ManyToOne
    private LkProvincie idProvinciaIscrizione;
    @JoinColumn(name = "id_provincia_cciaa", referencedColumnName = "id_provincie")
    @ManyToOne
    private LkProvincie idProvinciaCciaa;
    @JoinColumn(name = "id_tipo_collegio", referencedColumnName = "id_tipo_collegio")
    @ManyToOne
    private LkTipoCollegio idTipoCollegio;
    @JoinColumn(name = "id_comune_nascita", referencedColumnName = "id_comune")
    @ManyToOne
    private LkComuni idComuneNascita;
    @JoinColumn(name = "id_cittadinanza", referencedColumnName = "id_cittadinanza")
    @ManyToOne
    private LkCittadinanza idCittadinanza;
    @OneToMany(mappedBy = "idMittenteAnagrafica")
    private List<PraticheProtocollo> praticheProtocolloList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "anagrafica")
    private List<PraticaAnagrafica> praticaAnagraficaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAnagrafica")
    private List<AnagraficaRecapiti> anagraficaRecapitiList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "anagrafica")
    private List<PraticheEventiAnagrafiche> praticheEventiAnagraficheList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "anagrafica")
    private List<ProcessiEventiAnagrafica> processiEventiAnagraficaList;    

    @Transient
    private boolean anagraficaDaEnte = false;
    
    public Anagrafica() {
    }

    public Anagrafica(Integer idAnagrafica) {
        this.idAnagrafica = idAnagrafica;
    }

    public Anagrafica(Integer idAnagrafica, char tipoAnagrafica, char flgIndividuale) {
        this.idAnagrafica = idAnagrafica;
        this.tipoAnagrafica = tipoAnagrafica;
        this.flgIndividuale = flgIndividuale;
    }

    public Integer getIdAnagrafica() {
        return idAnagrafica;
    }

    public void setIdAnagrafica(Integer idAnagrafica) {
        this.idAnagrafica = idAnagrafica;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getLocalitaNascita() {
        return localitaNascita;
    }

    public void setLocalitaNascita(String localitaNascita) {
        this.localitaNascita = localitaNascita;
    }

    public Character getSesso() {
        return sesso;
    }

    public void setSesso(Character sesso) {
        this.sesso = sesso;
    }

    public String getNumeroIscrizione() {
        return numeroIscrizione;
    }

    public void setNumeroIscrizione(String numeroIscrizione) {
        this.numeroIscrizione = numeroIscrizione;
    }

    public Date getDataIscrizione() {
        return dataIscrizione;
    }

    public void setDataIscrizione(Date dataIscrizione) {
        this.dataIscrizione = dataIscrizione;
    }

    public Character getFlgAttesaIscrizioneRi() {
        return flgAttesaIscrizioneRi;
    }

    public void setFlgAttesaIscrizioneRi(Character flgAttesaIscrizioneRi) {
        this.flgAttesaIscrizioneRi = flgAttesaIscrizioneRi;
    }

    public Character getFlgObbligoIscrizioneRi() {
        return flgObbligoIscrizioneRi;
    }

    public void setFlgObbligoIscrizioneRi(Character flgObbligoIscrizioneRi) {
        this.flgObbligoIscrizioneRi = flgObbligoIscrizioneRi;
    }

    public Date getDataIscrizioneRi() {
        return dataIscrizioneRi;
    }

    public void setDataIscrizioneRi(Date dataIscrizioneRi) {
        this.dataIscrizioneRi = dataIscrizioneRi;
    }

    public String getNIscrizioneRi() {
        return nIscrizioneRi;
    }

    public void setNIscrizioneRi(String nIscrizioneRi) {
        this.nIscrizioneRi = nIscrizioneRi;
    }

    public Character getFlgAttesaIscrizioneRea() {
        return flgAttesaIscrizioneRea;
    }

    public void setFlgAttesaIscrizioneRea(Character flgAttesaIscrizioneRea) {
        this.flgAttesaIscrizioneRea = flgAttesaIscrizioneRea;
    }

    public Date getDataIscrizioneRea() {
        return dataIscrizioneRea;
    }

    public void setDataIscrizioneRea(Date dataIscrizioneRea) {
        this.dataIscrizioneRea = dataIscrizioneRea;
    }

    public String getNIscrizioneRea() {
        return nIscrizioneRea;
    }

    public void setNIscrizioneRea(String nIscrizioneRea) {
        this.nIscrizioneRea = nIscrizioneRea;
    }

    public String getVarianteAnagrafica() {
        return varianteAnagrafica;
    }

    public void setVarianteAnagrafica(String varianteAnagrafica) {
        this.varianteAnagrafica = varianteAnagrafica;
    }

    @XmlTransient
    public List<RelazioniAnagrafiche> getRelazioniAnagraficheList() {
        return relazioniAnagraficheList;
    }

    public void setRelazioniAnagraficheList(List<RelazioniAnagrafiche> relazioniAnagraficheList) {
        this.relazioniAnagraficheList = relazioniAnagraficheList;
    }

    @XmlTransient
    public List<RelazioniAnagrafiche> getRelazioniAnagraficheList1() {
        return relazioniAnagraficheList1;
    }

    public void setRelazioniAnagraficheList1(List<RelazioniAnagrafiche> relazioniAnagraficheList1) {
        this.relazioniAnagraficheList1 = relazioniAnagraficheList1;
    }

    public LkFormeGiuridiche getIdFormaGiuridica() {
        return idFormaGiuridica;
    }

    public void setIdFormaGiuridica(LkFormeGiuridiche idFormaGiuridica) {
        this.idFormaGiuridica = idFormaGiuridica;
    }

    public LkNazionalita getIdNazionalita() {
        return idNazionalita;
    }

    public void setIdNazionalita(LkNazionalita idNazionalita) {
        this.idNazionalita = idNazionalita;
    }

    public LkProvincie getIdProvinciaIscrizione() {
        return idProvinciaIscrizione;
    }

    public void setIdProvinciaIscrizione(LkProvincie idProvinciaIscrizione) {
        this.idProvinciaIscrizione = idProvinciaIscrizione;
    }

    public LkProvincie getIdProvinciaCciaa() {
        return idProvinciaCciaa;
    }

    public void setIdProvinciaCciaa(LkProvincie idProvinciaCciaa) {
        this.idProvinciaCciaa = idProvinciaCciaa;
    }

    public LkTipoCollegio getIdTipoCollegio() {
        return idTipoCollegio;
    }

    public void setIdTipoCollegio(LkTipoCollegio idTipoCollegio) {
        this.idTipoCollegio = idTipoCollegio;
    }

    public LkComuni getIdComuneNascita() {
        return idComuneNascita;
    }

    public void setIdComuneNascita(LkComuni idComuneNascita) {
        this.idComuneNascita = idComuneNascita;
    }

    public LkCittadinanza getIdCittadinanza() {
        return idCittadinanza;
    }

    public void setIdCittadinanza(LkCittadinanza idCittadinanza) {
        this.idCittadinanza = idCittadinanza;
    }

    @XmlTransient
    public List<PraticheProtocollo> getPraticheProtocolloList() {
        return praticheProtocolloList;
    }

    public void setPraticheProtocolloList(List<PraticheProtocollo> praticheProtocolloList) {
        this.praticheProtocolloList = praticheProtocolloList;
    }

    @XmlTransient
    public List<PraticaAnagrafica> getPraticaAnagraficaList() {
        return praticaAnagraficaList;
    }

    public void setPraticaAnagraficaList(List<PraticaAnagrafica> praticaAnagraficaList) {
        this.praticaAnagraficaList = praticaAnagraficaList;
    }

    @XmlTransient
    public List<AnagraficaRecapiti> getAnagraficaRecapitiList() {
        return anagraficaRecapitiList;
    }

    public void setAnagraficaRecapitiList(List<AnagraficaRecapiti> anagraficaRecapitiList) {
        this.anagraficaRecapitiList = anagraficaRecapitiList;
    }

    @XmlTransient
    public List<PraticheEventiAnagrafiche> getPraticheEventiAnagraficheList() {
        return praticheEventiAnagraficheList;
    }

    public void setPraticheEventiAnagraficheList(List<PraticheEventiAnagrafiche> praticheEventiAnagraficheList) {
        this.praticheEventiAnagraficheList = praticheEventiAnagraficheList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAnagrafica != null ? idAnagrafica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Anagrafica)) {
            return false;
        }
        Anagrafica other = (Anagrafica) object;
        if ((this.idAnagrafica == null && other.idAnagrafica != null) || (this.idAnagrafica != null && !this.idAnagrafica.equals(other.idAnagrafica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.Anagrafica[ idAnagrafica=" + idAnagrafica + " ]";
    }

    public Character getTipoAnagrafica() {
        return tipoAnagrafica;
    }

    public void setTipoAnagrafica(Character tipoAnagrafica) {
        this.tipoAnagrafica = tipoAnagrafica;
    }

    public Character getFlgIndividuale() {
        return flgIndividuale;
    }

    public void setFlgIndividuale(Character flgIndividuale) {
        this.flgIndividuale = flgIndividuale;
    }

    @XmlTransient
    @JsonIgnore
    public List<ProcessiEventiAnagrafica> getProcessiEventiAnagraficaList() {
        return processiEventiAnagraficaList;
    }

    public void setProcessiEventiAnagraficaList(List<ProcessiEventiAnagrafica> processiEventiAnagraficaList) {
        this.processiEventiAnagraficaList = processiEventiAnagraficaList;
    }

    public String getnIscrizioneRi() {
        return nIscrizioneRi;
    }

    public void setnIscrizioneRi(String nIscrizioneRi) {
        this.nIscrizioneRi = nIscrizioneRi;
    }

    public String getnIscrizioneRea() {
        return nIscrizioneRea;
    }

    public void setnIscrizioneRea(String nIscrizioneRea) {
        this.nIscrizioneRea = nIscrizioneRea;
    }

    public boolean isAnagraficaDaEnte() {
        return anagraficaDaEnte;
    }

    public void setAnagraficaDaEnte(boolean anagraficaDaEnte) {
        this.anagraficaDaEnte = anagraficaDaEnte;
    }

    @XmlTransient
    @JsonIgnore
    public List<OrganiCollegialiCommissione> getOrganiCollegialiCommissioneList() {
        return organiCollegialiCommissioneList;
    }

    public void setOrganiCollegialiCommissioneList(List<OrganiCollegialiCommissione> organiCollegialiCommissioneList) {
        this.organiCollegialiCommissioneList = organiCollegialiCommissioneList;
    }

    @XmlTransient
    @JsonIgnore
    public List<OcPraticaCommissione> getOcPraticaCommissioneList() {
        return ocPraticaCommissioneList;
    }

    public void setOcPraticaCommissioneList(List<OcPraticaCommissione> ocPraticaCommissioneList) {
        this.ocPraticaCommissioneList = ocPraticaCommissioneList;
    }
    
}
