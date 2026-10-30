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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "processi_eventi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProcessiEventi.findAll", query = "SELECT p FROM ProcessiEventi p"),
    @NamedQuery(name = "ProcessiEventi.findByIdEvento", query = "SELECT p FROM ProcessiEventi p WHERE p.idEvento = :idEvento"),
    @NamedQuery(name = "ProcessiEventi.findByCodEvento", query = "SELECT p FROM ProcessiEventi p WHERE p.codEvento = :codEvento"),
    @NamedQuery(name = "ProcessiEventi.findByDesEvento", query = "SELECT p FROM ProcessiEventi p WHERE p.desEvento = :desEvento"),
    @NamedQuery(name = "ProcessiEventi.findByScriptScadenzaEvento", query = "SELECT p FROM ProcessiEventi p WHERE p.scriptScadenzaEvento = :scriptScadenzaEvento"),
    @NamedQuery(name = "ProcessiEventi.findByVerso", query = "SELECT p FROM ProcessiEventi p WHERE p.verso = :verso"),
    @NamedQuery(name = "ProcessiEventi.findByFlgPortale", query = "SELECT p FROM ProcessiEventi p WHERE p.flgPortale = :flgPortale"),
    @NamedQuery(name = "ProcessiEventi.findByFlgMail", query = "SELECT p FROM ProcessiEventi p WHERE p.flgMail = :flgMail"),
    @NamedQuery(name = "ProcessiEventi.findByFlgAllMail", query = "SELECT p FROM ProcessiEventi p WHERE p.flgAllMail = :flgAllMail"),
    @NamedQuery(name = "ProcessiEventi.findByFlgProtocollazione", query = "SELECT p FROM ProcessiEventi p WHERE p.flgProtocollazione = :flgProtocollazione"),
    @NamedQuery(name = "ProcessiEventi.findByFlgRicevuta", query = "SELECT p FROM ProcessiEventi p WHERE p.flgRicevuta = :flgRicevuta"),
    @NamedQuery(name = "ProcessiEventi.findByFlgDestinatari", query = "SELECT p FROM ProcessiEventi p WHERE p.flgDestinatari = :flgDestinatari"),
    @NamedQuery(name = "ProcessiEventi.findByFlgFirmato", query = "SELECT p FROM ProcessiEventi p WHERE p.flgFirmato = :flgFirmato"),
    @NamedQuery(name = "ProcessiEventi.findByFlgApriSottopratica", query = "SELECT p FROM ProcessiEventi p WHERE p.flgApriSottopratica = :flgApriSottopratica"),
    @NamedQuery(name = "ProcessiEventi.findByFlgDestinatariSoloEnti", query = "SELECT p FROM ProcessiEventi p WHERE p.flgDestinatariSoloEnti = :flgDestinatariSoloEnti"),
    @NamedQuery(name = "ProcessiEventi.findByFlgVisualizzaProcedimenti", query = "SELECT p FROM ProcessiEventi p WHERE p.flgVisualizzaProcedimenti = :flgVisualizzaProcedimenti"),
    @NamedQuery(name = "ProcessiEventi.findByIdScriptEvento", query = "SELECT p FROM ProcessiEventi p WHERE p.idScriptEvento = :idScriptEvento"),
    @NamedQuery(name = "ProcessiEventi.findByIdScriptProtocollo", query = "SELECT p FROM ProcessiEventi p WHERE p.idScriptProtocollo = :idScriptProtocollo"),
    @NamedQuery(name = "ProcessiEventi.findByMaxDestinatari", query = "SELECT p FROM ProcessiEventi p WHERE p.maxDestinatari = :maxDestinatari"),
    @NamedQuery(name = "ProcessiEventi.findByOggettoEmail", query = "SELECT p FROM ProcessiEventi p WHERE p.oggettoEmail = :oggettoEmail"),
    @NamedQuery(name = "ProcessiEventi.findByFunzioneApplicativa", query = "SELECT p FROM ProcessiEventi p WHERE p.funzioneApplicativa = :funzioneApplicativa"),
    @NamedQuery(name = "ProcessiEventi.findByFlgAutomatico", query = "SELECT p FROM ProcessiEventi p WHERE p.flgAutomatico = :flgAutomatico")})
public class ProcessiEventi implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "processiEventi")
    private List<ProcessiEventiEnti> processiEventiEntiList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "processiEventi")
    private List<ProcessiEventiAnagrafica> processiEventiAnagraficaList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_evento")
    private Integer idEvento;
    @Basic(optional = false)
    @Column(name = "cod_evento")
    private String codEvento;
    @Column(name = "des_evento")
    private String desEvento;
    @Column(name = "script_scadenza_evento")
    private String scriptScadenzaEvento;
    @Column(name = "verso")
    private Character verso;
    @Basic(optional = false)
    @Column(name = "flg_portale")
    private String flgPortale;
    @Basic(optional = false)
    @Column(name = "flg_mail")
    private String flgMail;
    @Basic(optional = false)
    @Column(name = "flg_all_mail")
    private String flgAllMail;
    @Column(name = "flg_protocollazione")
    private String flgProtocollazione;
    @Basic(optional = false)
    @Column(name = "flg_ricevuta")
    private String flgRicevuta;
    @Basic(optional = false)
    @Column(name = "flg_destinatari")
    private String flgDestinatari;
    @Basic(optional = false)
    @Column(name = "flg_firmato")
    private String flgFirmato;
    @Column(name = "flg_apri_sottopratica")
    private String flgApriSottopratica;
    @Column(name = "flg_destinatari_solo_enti")
    private String flgDestinatariSoloEnti;
    @Column(name = "flg_visualizza_procedimenti")
    private String flgVisualizzaProcedimenti;
    @Column(name = "id_script_evento")
    private String idScriptEvento;
    @Column(name = "id_script_protocollo")
    private String idScriptProtocollo;
    @Column(name = "max_destinatari")
    private Integer maxDestinatari;
    @Column(name = "oggetto_email")
    private String oggettoEmail;
    @Lob
    @Column(name = "corpo_email")
    private String corpoEmail;
    @Basic(optional = false)
    @Column(name = "funzione_applicativa")
    private String funzioneApplicativa;
    @Column(name = "flg_automatico")
    private String flgAutomatico;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEvento")
    private List<EventiTemplate> eventiTemplateList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEventoTrigger")
    private List<ProcessiSteps> idEventoTriggerList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEventoResult")
    private List<ProcessiSteps> idEventoResultList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "processiEventi")
    private List<ProcessiEventiScadenze> processiEventiScadenzeList;
    @JoinColumn(name = "id_processo", referencedColumnName = "id_processo")
    @ManyToOne(optional = false)
    private Processi idProcesso;
    @JoinColumn(name = "id_procedimento_riferimento", referencedColumnName = "id_proc")
    @ManyToOne
    private Procedimenti idProcedimentoRiferimento;
    @JoinColumn(name = "id_tipo_mittente", referencedColumnName = "id_tipo_attore")
    @ManyToOne
    private LkTipiAttore idTipoMittente;
    @JoinColumn(name = "id_tipo_destinatario", referencedColumnName = "id_tipo_attore")
    @ManyToOne
    private LkTipiAttore idTipoDestinatario;
    @JoinColumn(name = "stato_post", referencedColumnName = "id_stato_pratica")
    @ManyToOne
    private LkStatoPratica statoPost;
    @OneToMany(mappedBy = "idEvento")
    private List<PraticheEventi> praticheEventiList;
    @Column(name = "forza_chiusura_scadenze")
    private String forzaChiusuraScadenze;

    public ProcessiEventi() {
    }

    public ProcessiEventi(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public ProcessiEventi(Integer idEvento, String codEvento, String flgPortale, String flgMail, String flgAllMail, String flgRicevuta, String flgDestinatari, String flgFirmato, String funzioneApplicativa) {
        this.idEvento = idEvento;
        this.codEvento = codEvento;
        this.flgPortale = flgPortale;
        this.flgMail = flgMail;
        this.flgAllMail = flgAllMail;
        this.flgRicevuta = flgRicevuta;
        this.flgDestinatari = flgDestinatari;
        this.flgFirmato = flgFirmato;
        this.funzioneApplicativa = funzioneApplicativa;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getCodEvento() {
        return codEvento;
    }

    public void setCodEvento(String codEvento) {
        this.codEvento = codEvento;
    }

    public String getDesEvento() {
        return desEvento;
    }

    public void setDesEvento(String desEvento) {
        this.desEvento = desEvento;
    }

    public String getScriptScadenzaEvento() {
        return scriptScadenzaEvento;
    }

    public void setScriptScadenzaEvento(String scriptScadenzaEvento) {
        this.scriptScadenzaEvento = scriptScadenzaEvento;
    }

    public Character getVerso() {
        return verso;
    }

    public void setVerso(Character verso) {
        this.verso = verso;
    }

    public String getFlgPortale() {
        return flgPortale;
    }

    public void setFlgPortale(String flgPortale) {
        this.flgPortale = flgPortale;
    }

    public String getFlgMail() {
        return flgMail;
    }

    public void setFlgMail(String flgMail) {
        this.flgMail = flgMail;
    }

    public String getFlgAllMail() {
        return flgAllMail;
    }

    public void setFlgAllMail(String flgAllMail) {
        this.flgAllMail = flgAllMail;
    }

    public String getFlgProtocollazione() {
        return flgProtocollazione;
    }

    public void setFlgProtocollazione(String flgProtocollazione) {
        this.flgProtocollazione = flgProtocollazione;
    }

    public String getFlgRicevuta() {
        return flgRicevuta;
    }

    public void setFlgRicevuta(String flgRicevuta) {
        this.flgRicevuta = flgRicevuta;
    }

    public String getFlgDestinatari() {
        return flgDestinatari;
    }

    public void setFlgDestinatari(String flgDestinatari) {
        this.flgDestinatari = flgDestinatari;
    }

    public String getFlgFirmato() {
        return flgFirmato;
    }

    public void setFlgFirmato(String flgFirmato) {
        this.flgFirmato = flgFirmato;
    }

    public String getFlgApriSottopratica() {
        return flgApriSottopratica;
    }

    public void setFlgApriSottopratica(String flgApriSottopratica) {
        this.flgApriSottopratica = flgApriSottopratica;
    }

    public String getFlgDestinatariSoloEnti() {
        return flgDestinatariSoloEnti;
    }

    public void setFlgDestinatariSoloEnti(String flgDestinatariSoloEnti) {
        this.flgDestinatariSoloEnti = flgDestinatariSoloEnti;
    }

    public String getFlgVisualizzaProcedimenti() {
        return flgVisualizzaProcedimenti;
    }

    public void setFlgVisualizzaProcedimenti(String flgVisualizzaProcedimenti) {
        this.flgVisualizzaProcedimenti = flgVisualizzaProcedimenti;
    }

    public String getIdScriptEvento() {
        return idScriptEvento;
    }

    public void setIdScriptEvento(String idScriptEvento) {
        this.idScriptEvento = idScriptEvento;
    }

    public String getIdScriptProtocollo() {
        return idScriptProtocollo;
    }

    public void setIdScriptProtocollo(String idScriptProtocollo) {
        this.idScriptProtocollo = idScriptProtocollo;
    }

    public Integer getMaxDestinatari() {
        return maxDestinatari;
    }

    public void setMaxDestinatari(Integer maxDestinatari) {
        this.maxDestinatari = maxDestinatari;
    }

    public String getOggettoEmail() {
        return oggettoEmail;
    }

    public void setOggettoEmail(String oggettoEmail) {
        this.oggettoEmail = oggettoEmail;
    }

    public String getCorpoEmail() {
        return corpoEmail;
    }

    public void setCorpoEmail(String corpoEmail) {
        this.corpoEmail = corpoEmail;
    }

    public String getFunzioneApplicativa() {
        return funzioneApplicativa;
    }

    public void setFunzioneApplicativa(String funzioneApplicativa) {
        this.funzioneApplicativa = funzioneApplicativa;
    }

    public String getFlgAutomatico() {
        return flgAutomatico;
    }

    public void setFlgAutomatico(String flgAutomatico) {
        this.flgAutomatico = flgAutomatico;
    }

    @XmlTransient
    public List<EventiTemplate> getEventiTemplateList() {
        return eventiTemplateList;
    }

    public void setEventiTemplateList(List<EventiTemplate> eventiTemplateList) {
        this.eventiTemplateList = eventiTemplateList;
    }

    public List<ProcessiSteps> getIdEventoTriggerList() {
        return idEventoTriggerList;
    }

    public void setIdEventoTriggerList(List<ProcessiSteps> idEventoTriggerList) {
        this.idEventoTriggerList = idEventoTriggerList;
    }

    public List<ProcessiSteps> getIdEventoResultList() {
        return idEventoResultList;
    }

    public void setIdEventoResultList(List<ProcessiSteps> idEventoResultList) {
        this.idEventoResultList = idEventoResultList;
    }

    @XmlTransient
    public List<ProcessiEventiScadenze> getProcessiEventiScadenzeList() {
        return processiEventiScadenzeList;
    }

    public void setProcessiEventiScadenzeList(List<ProcessiEventiScadenze> processiEventiScadenzeList) {
        this.processiEventiScadenzeList = processiEventiScadenzeList;
    }

    public Processi getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Processi idProcesso) {
        this.idProcesso = idProcesso;
    }

    public Procedimenti getIdProcedimentoRiferimento() {
        return idProcedimentoRiferimento;
    }

    public void setIdProcedimentoRiferimento(Procedimenti idProcedimentoRiferimento) {
        this.idProcedimentoRiferimento = idProcedimentoRiferimento;
    }

    public LkTipiAttore getIdTipoMittente() {
        return idTipoMittente;
    }

    public void setIdTipoMittente(LkTipiAttore idTipoMittente) {
        this.idTipoMittente = idTipoMittente;
    }

    public LkTipiAttore getIdTipoDestinatario() {
        return idTipoDestinatario;
    }

    public void setIdTipoDestinatario(LkTipiAttore idTipoDestinatario) {
        this.idTipoDestinatario = idTipoDestinatario;
    }

    public LkStatoPratica getStatoPost() {
        return statoPost;
    }

    public void setStatoPost(LkStatoPratica statoPost) {
        this.statoPost = statoPost;
    }

    @XmlTransient
    public List<PraticheEventi> getPraticheEventiList() {
        return praticheEventiList;
    }

    public void setPraticheEventiList(List<PraticheEventi> praticheEventiList) {
        this.praticheEventiList = praticheEventiList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvento != null ? idEvento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProcessiEventi)) {
            return false;
        }
        ProcessiEventi other = (ProcessiEventi) object;
        if ((this.idEvento == null && other.idEvento != null) || (this.idEvento != null && !this.idEvento.equals(other.idEvento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.ProcessiEventi[ idEvento=" + idEvento + " ]";
    }

    @XmlTransient
    @JsonIgnore
    public List<ProcessiEventiEnti> getProcessiEventiEntiList() {
        return processiEventiEntiList;
    }

    public void setProcessiEventiEntiList(List<ProcessiEventiEnti> processiEventiEntiList) {
        this.processiEventiEntiList = processiEventiEntiList;
    }

    @XmlTransient
    @JsonIgnore
    public List<ProcessiEventiAnagrafica> getProcessiEventiAnagraficaList() {
        return processiEventiAnagraficaList;
    }

    public void setProcessiEventiAnagraficaList(List<ProcessiEventiAnagrafica> processiEventiAnagraficaList) {
        this.processiEventiAnagraficaList = processiEventiAnagraficaList;
    }

    public String getForzaChiusuraScadenze() {
        return forzaChiusuraScadenze;
    }

    public void setForzaChiusuraScadenze(String forzaChiusuraScadenze) {
        this.forzaChiusuraScadenze = forzaChiusuraScadenze;
    }
    
}
