/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.Nullable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "pratica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pratica.findAll", query = "SELECT p FROM Pratica p"),
    @NamedQuery(name = "Pratica.findByIdPratica", query = "SELECT p FROM Pratica p WHERE p.idPratica = :idPratica"),
    @NamedQuery(name = "Pratica.findByIdentificativoPratica", query = "SELECT p FROM Pratica p WHERE p.identificativoPratica = :identificativoPratica"),
    @NamedQuery(name = "Pratica.findByOggettoPratica", query = "SELECT p FROM Pratica p WHERE p.oggettoPratica = :oggettoPratica"),
    @NamedQuery(name = "Pratica.findByProtocollo", query = "SELECT p FROM Pratica p WHERE p.protocollo = :protocollo"),
    @NamedQuery(name = "Pratica.findByCodRegistro", query = "SELECT p FROM Pratica p WHERE p.codRegistro = :codRegistro"),
    @NamedQuery(name = "Pratica.findByAnnoRiferimento", query = "SELECT p FROM Pratica p WHERE p.annoRiferimento = :annoRiferimento"),
    @NamedQuery(name = "Pratica.findByDataApertura", query = "SELECT p FROM Pratica p WHERE p.dataApertura = :dataApertura"),
    @NamedQuery(name = "Pratica.findByDataChiusura", query = "SELECT p FROM Pratica p WHERE p.dataChiusura = :dataChiusura"),
    @NamedQuery(name = "Pratica.findByDataRicezione", query = "SELECT p FROM Pratica p WHERE p.dataRicezione = :dataRicezione"),
    @NamedQuery(name = "Pratica.findByResponsabileProcedimento", query = "SELECT p FROM Pratica p WHERE p.responsabileProcedimento = :responsabileProcedimento"),
    @NamedQuery(name = "Pratica.findByDataProtocollazione", query = "SELECT p FROM Pratica p WHERE p.dataProtocollazione = :dataProtocollazione"),
    @NamedQuery(name = "Pratica.findByGiorniSospensione", query = "SELECT p FROM Pratica p WHERE p.giorniSospensione = :giorniSospensione"),
    @NamedQuery(name = "Pratica.findByIdentificativoEsterno", query = "SELECT p FROM Pratica p WHERE p.identificativoEsterno = :identificativoEsterno")})
public class Pratica implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPratica")
    private List<PraticaOrganiCollegiali> praticaOrganiCollegialiList;

    public static final String RICEZIONE_PRATICA = "RIC";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pratica")
    private Integer idPratica;
    @Column(name = "identificativo_pratica")
    private String identificativoPratica;
    @Column(name = "oggetto_pratica")
    private String oggettoPratica;
    @Column(name = "protocollo")
    private String protocollo;
    @Column(name = "cod_registro")
    private String codRegistro;
    @Column(name = "anno_riferimento")
    private Integer annoRiferimento;
    @Column(name = "data_apertura")
    @Temporal(TemporalType.DATE)
    private Date dataApertura;
    @Column(name = "data_chiusura")
    @Temporal(TemporalType.DATE)
    private Date dataChiusura;
    @Basic(optional = false)
    @Column(name = "data_ricezione")
    @Temporal(TemporalType.DATE)
    private Date dataRicezione;
    @Column(name = "responsabile_procedimento")
    private String responsabileProcedimento;
    @Column(name = "data_protocollazione")
    @Temporal(TemporalType.DATE)
    private Date dataProtocollazione;
    @Basic(optional = false)
    @Column(name = "giorni_sospensione")
    private int giorniSospensione;
    @OneToMany(mappedBy = "idPratica")
    private List<Errori> erroriList;
    @OneToMany(mappedBy = "idPratica")
    private List<Messaggio> messaggioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pratica")
    private List<PraticaProcedimenti> praticaProcedimentiList;
    @OneToMany(mappedBy = "idPratica")
    private List<Comunicazione> comunicazioneList;
    @OneToMany(mappedBy = "idPratica")
    private List<PraticheProtocollo> praticheProtocolloList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pratica")
    private PraticheDirittiSegreteria praticheDirittiSegreteria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPratica")
    private List<Scadenze> scadenzeList;
    @JoinColumn(name = "stato_email", referencedColumnName = "id_stati_mail")
    @ManyToOne
    private LkStatiMail statoEmail;
    @JoinColumn(name = "id_staging", referencedColumnName = "id_staging")
    @ManyToOne
    private Staging idStaging;
    @JoinColumn(name = "id_processo", referencedColumnName = "id_processo")
    @ManyToOne
    private Processi idProcesso;
    @JoinColumn(name = "id_comune", referencedColumnName = "id_comune")
    @ManyToOne
    private LkComuni idComune;
    @JoinColumn(name = "id_stato_pratica", referencedColumnName = "id_stato_pratica")
    @ManyToOne
    private LkStatoPratica idStatoPratica;
    @OneToMany(mappedBy = "idPraticaPadre")
    private List<Pratica> praticaList;
    @JoinColumn(name = "id_pratica_padre", referencedColumnName = "id_pratica")
    @ManyToOne
    private Pratica idPraticaPadre;
    @JoinColumn(name = "id_utente", referencedColumnName = "id_utente")
    @ManyToOne
    private Utente idUtente;
    @JoinColumn(name = "id_recapito", referencedColumnName = "id_recapito")
    @ManyToOne
    private Recapiti idRecapito;
    @JoinColumn(name = "id_modello", referencedColumnName = "id")
    @ManyToOne
    private Allegati idModello;
    @JoinColumn(name = "id_proc_ente", referencedColumnName = "id_proc_ente")
    @ManyToOne(optional = false)
    private ProcedimentiEnti idProcEnte;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPratica")
    private List<NotePratica> notePraticaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pratica")
    private List<PraticaAnagrafica> praticaAnagraficaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPratica")
    private List<DatiCatastali> datiCatastaliList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPratica")
    private List<IndirizziIntervento> indirizziInterventoList;
    @OneToMany(mappedBy = "idPratica")
    private List<PraticheEventi> praticheEventiList;
    @Column(name = "ufficio_riferimento")
    private String ufficioRiferimento;
    @Column(name = "identificativo_esterno")
    private String identificativoEsterno;
    @Column(name = "integrazione")
    private String integrazione;
    @Column(name = "data_prot_suap")
    @Temporal(TemporalType.DATE)
    private Date data_prot_suap;
    @Column(name = "prot_suap")
    private String prot_suap;
    @JoinColumn(name = "id_tipo_procedimento_suap", referencedColumnName = "id_tipo_procedimento_suap")
    @ManyToOne
    private LkTipoProcedimentoSuap idTipoProcedimentoSuap;
    @JoinColumn(name = "id_tipo_intervento_suap", referencedColumnName = "id_tipo_intervento_suap")
    @ManyToOne
    private LkTipoInterventoSuap idTipoInterventoSuap;

    @JoinTable(name = "pratica_pratica", joinColumns = {
        @JoinColumn(name = "id_pratica_a", referencedColumnName = "id_pratica", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "id_pratica_b", referencedColumnName = "id_pratica", nullable = false)})
    @ManyToMany
    private List<Pratica> praticheAssociate;

    public Pratica() {
    }

    public Pratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public Pratica(Integer idPratica, Date dataRicezione, int giorniSospensione) {
        this.idPratica = idPratica;
        this.dataRicezione = dataRicezione;
        this.giorniSospensione = giorniSospensione;
    }

    public Integer getIdEnteProcEnteInteger() {
        return getIdProcEnte().getIdEnte().getIdEnte();
    }

    public Integer getIdComuneInteger() {
        return getIdComune().getIdComune();
    }

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public String getIdentificativoPratica() {
        return identificativoPratica;
    }

    public void setIdentificativoPratica(String identificativoPratica) {
        this.identificativoPratica = identificativoPratica;
    }

    public String getOggettoPratica() {
        return oggettoPratica;
    }

    public void setOggettoPratica(String oggettoPratica) {
        this.oggettoPratica = oggettoPratica;
    }

    public String getProtocollo() {
        return protocollo;
    }

    public void setProtocollo(String protocollo) {
        this.protocollo = protocollo;
    }

    public String getCodRegistro() {
        return codRegistro;
    }

    public void setCodRegistro(String codRegistro) {
        this.codRegistro = codRegistro;
    }

    public Integer getAnnoRiferimento() {
        return annoRiferimento;
    }

    public void setAnnoRiferimento(Integer annoRiferimento) {
        this.annoRiferimento = annoRiferimento;
    }

    public Date getDataApertura() {
        return dataApertura;
    }

    public void setDataApertura(Date dataApertura) {
        this.dataApertura = dataApertura;
    }

    public Date getDataChiusura() {
        return dataChiusura;
    }

    public void setDataChiusura(Date dataChiusura) {
        this.dataChiusura = dataChiusura;
    }

    public Date getDataRicezione() {
        return dataRicezione;
    }

    public void setDataRicezione(Date dataRicezione) {
        this.dataRicezione = dataRicezione;
    }

    public String getResponsabileProcedimento() {
        return responsabileProcedimento;
    }

    public void setResponsabileProcedimento(String responsabileProcedimento) {
        this.responsabileProcedimento = responsabileProcedimento;
    }

    public Date getDataProtocollazione() {
        return dataProtocollazione;
    }

    public void setDataProtocollazione(Date dataProtocollazione) {
        this.dataProtocollazione = dataProtocollazione;
    }

    public int getGiorniSospensione() {
        return giorniSospensione;
    }

    public void setGiorniSospensione(int giorniSospensione) {
        this.giorniSospensione = giorniSospensione;
    }

    @XmlTransient
    public List<Errori> getErroriList() {
        return erroriList;
    }

    public void setErroriList(List<Errori> erroriList) {
        this.erroriList = erroriList;
    }

    @XmlTransient
    public List<Messaggio> getMessaggioList() {
        return messaggioList;
    }

    public void setMessaggioList(List<Messaggio> messaggioList) {
        this.messaggioList = messaggioList;
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

    public PraticheDirittiSegreteria getDirittiSegreteria() {
        return praticheDirittiSegreteria;
    }

    public void setDirittiSegreteria(PraticheDirittiSegreteria praticheDirittiSegreteria) {
        this.praticheDirittiSegreteria = praticheDirittiSegreteria;
    }

    @XmlTransient
    public List<Scadenze> getScadenzeList() {
        return scadenzeList;
    }

    public void setScadenzeList(List<Scadenze> scadenzeList) {
        this.scadenzeList = scadenzeList;
    }

    public LkStatiMail getStatoEmail() {
        return statoEmail;
    }

    public void setStatoEmail(LkStatiMail statoEmail) {
        this.statoEmail = statoEmail;
    }

    public Staging getIdStaging() {
        return idStaging;
    }

    public void setIdStaging(Staging idStaging) {
        this.idStaging = idStaging;
    }

    public Processi getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Processi idProcesso) {
        this.idProcesso = idProcesso;
    }

    public LkComuni getIdComune() {
        return idComune;
    }

    public void setIdComune(LkComuni idComune) {
        this.idComune = idComune;
    }

    public LkStatoPratica getIdStatoPratica() {
        return idStatoPratica;
    }

    public void setIdStatoPratica(LkStatoPratica idStatoPratica) {
        this.idStatoPratica = idStatoPratica;
    }

    @XmlTransient
    public List<Pratica> getPraticaList() {
        return praticaList;
    }

    public void setPraticaList(List<Pratica> praticaList) {
        this.praticaList = praticaList;
    }

    public Pratica getIdPraticaPadre() {
        return idPraticaPadre;
    }

    public void setIdPraticaPadre(Pratica idPraticaPadre) {
        this.idPraticaPadre = idPraticaPadre;
    }

    public Utente getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Utente idUtente) {
        this.idUtente = idUtente;
    }

    public Recapiti getIdRecapito() {
        return idRecapito;
    }

    public void setIdRecapito(Recapiti idRecapito) {
        this.idRecapito = idRecapito;
    }

    public Allegati getIdModello() {
        return idModello;
    }

    public void setIdModello(Allegati idModello) {
        this.idModello = idModello;
    }

    public ProcedimentiEnti getIdProcEnte() {
        return idProcEnte;
    }

    public void setIdProcEnte(ProcedimentiEnti idProcEnte) {
        this.idProcEnte = idProcEnte;
    }

    @XmlTransient
    public List<NotePratica> getNotePraticaList() {
        return notePraticaList;
    }

    public void setNotePraticaList(List<NotePratica> notePraticaList) {
        this.notePraticaList = notePraticaList;
    }

    @XmlTransient
    public List<PraticaAnagrafica> getPraticaAnagraficaList() {
        return praticaAnagraficaList;
    }

    public void setPraticaAnagraficaList(List<PraticaAnagrafica> praticaAnagraficaList) {
        this.praticaAnagraficaList = praticaAnagraficaList;
    }

    @XmlTransient
    public List<DatiCatastali> getDatiCatastaliList() {
        return datiCatastaliList;
    }

    public void setDatiCatastaliList(List<DatiCatastali> datiCatastaliList) {
        this.datiCatastaliList = datiCatastaliList;
    }

    @XmlTransient
    public List<IndirizziIntervento> getIndirizziInterventoList() {
        return indirizziInterventoList;
    }

    public void setIndirizziInterventoList(List<IndirizziIntervento> indirizziInterventoList) {
        this.indirizziInterventoList = indirizziInterventoList;
    }

    public String getUfficioRiferimento() {
        return ufficioRiferimento;
    }

    public void setUfficioRiferimento(String ufficioRiferimento) {
        this.ufficioRiferimento = ufficioRiferimento;
    }

    @XmlTransient
    public List<PraticheEventi> getPraticheEventiList() {
        return praticheEventiList;
    }

    public void setPraticheEventiList(List<PraticheEventi> praticheEventiList) {
        this.praticheEventiList = praticheEventiList;
    }

    public String getIdentificativoEsterno() {
        return identificativoEsterno;
    }

    public void setIdentificativoEsterno(String identificativoEsterno) {
        this.identificativoEsterno = identificativoEsterno;
    }

    public List<Pratica> getPraticheAssociate() {
        return praticheAssociate;
    }

    public void setPraticheAssociate(List<Pratica> praticheAssociate) {
        this.praticheAssociate = praticheAssociate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPratica != null ? idPratica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pratica)) {
            return false;
        }
        Pratica other = (Pratica) object;
        if ((this.idPratica == null && other.idPratica != null) || (this.idPratica != null && !this.idPratica.equals(other.idPratica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.Pratica[ idPratica=" + idPratica + " ]";
    }

    public @Nullable
    PraticheEventi getEventoRicezione() {
        return Iterables.getOnlyElement(Iterables.filter(getPraticheEventiList(), new Predicate<PraticheEventi>() {

            @Override
            public boolean apply(PraticheEventi evento) {
                return evento.getIdEvento().getCodEvento().equalsIgnoreCase(RICEZIONE_PRATICA);
            }
        }), null);
    }

    public @Nullable
    String getProtocolloDaEventoRicezione() {
        PraticheEventi eventoRicezione = getEventoRicezione();
        return eventoRicezione == null ? null : eventoRicezione.getProtocollo();
    }

    public @Nullable
    Date getDataProtocolloDaEventoRicezione() {
        PraticheEventi eventoRicezione = getEventoRicezione();
        return eventoRicezione == null ? null : eventoRicezione.getDataProtocollo();
    }

    @XmlTransient
    @JsonIgnore
    public List<PraticaOrganiCollegiali> getPraticaOrganiCollegialiList() {
        return praticaOrganiCollegialiList;
    }

    public void setPraticaOrganiCollegialiList(List<PraticaOrganiCollegiali> praticaOrganiCollegialiList) {
        this.praticaOrganiCollegialiList = praticaOrganiCollegialiList;
    }

	public String getIntegrazione() {
		return integrazione;
	}

	public void setIntegrazione(String integrazione) {
		this.integrazione = integrazione;
	}

	public Date getData_prot_suap() {
		return data_prot_suap;
	}

	public void setData_prot_suap(Date data_prot_suap) {
		this.data_prot_suap = data_prot_suap;
	}

	public String getProt_suap() {
		return prot_suap;
	}

	public void setProt_suap(String prot_suap) {
		this.prot_suap = prot_suap;
	}

	public LkTipoProcedimentoSuap getIdTipoProcedimentoSuap() {
		return idTipoProcedimentoSuap;
	}

	public void setIdTipoProcedimentoSuap(LkTipoProcedimentoSuap idTipoProcedimentoSuap) {
		this.idTipoProcedimentoSuap = idTipoProcedimentoSuap;
	}

	public LkTipoInterventoSuap getIdTipoInterventoSuap() {
		return idTipoInterventoSuap;
	}

	public void setIdTipoInterventoSuap(LkTipoInterventoSuap idTipoInterventoSuap) {
		this.idTipoInterventoSuap = idTipoInterventoSuap;
	}
    
    

}
