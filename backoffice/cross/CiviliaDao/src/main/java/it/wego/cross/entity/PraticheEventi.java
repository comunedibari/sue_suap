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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "pratiche_eventi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PraticheEventi.findAll", query = "SELECT p FROM PraticheEventi p"),
    @NamedQuery(name = "PraticheEventi.findByIdPraticaEvento", query = "SELECT p FROM PraticheEventi p WHERE p.idPraticaEvento = :idPraticaEvento"),
    @NamedQuery(name = "PraticheEventi.findByDataEvento", query = "SELECT p FROM PraticheEventi p WHERE p.dataEvento = :dataEvento"),
    @NamedQuery(name = "PraticheEventi.findByOggetto", query = "SELECT p FROM PraticheEventi p WHERE p.oggetto = :oggetto"),
    @NamedQuery(name = "PraticheEventi.findByNote", query = "SELECT p FROM PraticheEventi p WHERE p.note = :note"),
    @NamedQuery(name = "PraticheEventi.findByProtocollo", query = "SELECT p FROM PraticheEventi p WHERE p.protocollo = :protocollo"),
    @NamedQuery(name = "PraticheEventi.findByVerso", query = "SELECT p FROM PraticheEventi p WHERE p.verso = :verso"),
    @NamedQuery(name = "PraticheEventi.findByVisibilitaCross", query = "SELECT p FROM PraticheEventi p WHERE p.visibilitaCross = :visibilitaCross"),
    @NamedQuery(name = "PraticheEventi.findByVisibilitaUtente", query = "SELECT p FROM PraticheEventi p WHERE p.visibilitaUtente = :visibilitaUtente"),
    @NamedQuery(name = "PraticheEventi.findByPraticaEventoRef", query = "SELECT p FROM PraticheEventi p WHERE p.praticaEventoRef = :praticaEventoRef")})
public class PraticheEventi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pratica_evento")
    private Integer idPraticaEvento;
    @Column(name = "data_evento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEvento;
    @Column(name = "oggetto")
    private String oggetto;
    @Lob
    @Column(name = "comunicazione")
    private String comunicazione;
    @Column(name = "note")
    private String note;
    @Column(name = "protocollo")
    private String protocollo;
    @Column(name = "data_protocollo")
    @Temporal(TemporalType.DATE)
    private Date dataProtocollo;
    @Column(name = "verso")
    private String verso;
    @Column(name = "visibilita_cross")
    private String visibilitaCross;
    @Column(name = "visibilita_utente")
    private String visibilitaUtente;
    @Column(name = "descrizione_evento")
    private String descrizioneEvento;
//    @ManyToMany(mappedBy = "praticheEventiList")
//    private List<Allegati> allegatiList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "praticheEventi")
    private List<PraticheEventiAllegati> praticheEventiAllegatiList;
    @JoinTable(name = "pratiche_eventi_enti", joinColumns = {
        @JoinColumn(name = "id_pratica_evento", referencedColumnName = "id_pratica_evento")}, inverseJoinColumns = {
        @JoinColumn(name = "id_ente", referencedColumnName = "id_ente")})
    @ManyToMany
    private List<Enti> entiList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPraticaEvento")
    private List<Comunicazione> comunicazioneList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPraticaEvento")
    private List<Email> emailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPraticaEvento")
    private List<Scadenze> scadenzeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "praticheEventi")
    private List<PraticheEventiAnagrafiche> praticheEventiAnagraficheList;
    @JoinColumn(name = "id_utente", referencedColumnName = "id_utente")
    @ManyToOne
    private Utente idUtente;
    @JoinColumn(name = "id_evento", referencedColumnName = "id_evento")
    @ManyToOne
    private ProcessiEventi idEvento;
    @JoinColumn(name = "id_pratica", referencedColumnName = "id_pratica")
    @ManyToOne
    private Pratica idPratica;
    @JoinColumn(name = "id_recapito_notifica", referencedColumnName = "id_recapito")
    @ManyToOne
    private Recapiti idRecapitoNotifica;
    @JoinColumn(name = "stato_mail", referencedColumnName = "id_stati_mail")
    @ManyToOne
    private LkStatiMail statoMail;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "praticheEventi")
    private List<PraticaEventiProcedimenti> praticaEventiProcedimentiList;
    @JoinColumn(name = "pratica_evento_ref", referencedColumnName = "id_pratica_evento")
    @ManyToOne
    private PraticheEventi praticaEventoRef;

    public PraticheEventi() {
    }

    public PraticheEventi(Integer idPraticaEvento) {
        this.idPraticaEvento = idPraticaEvento;
    }

    public Integer getIdPraticaEvento() {
        return idPraticaEvento;
    }

    public void setIdPraticaEvento(Integer idPraticaEvento) {
        this.idPraticaEvento = idPraticaEvento;
    }

    public Date getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(Date dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getComunicazione() {
        return comunicazione;
    }

    public void setComunicazione(String comunicazione) {
        this.comunicazione = comunicazione;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getProtocollo() {
        return protocollo;
    }

    public void setProtocollo(String protocollo) {
        this.protocollo = protocollo;
    }

    public Date getDataProtocollo() {
        return dataProtocollo;
    }

    public void setDataProtocollo(Date dataProtocollo) {
        this.dataProtocollo = dataProtocollo;
    }

    public String getVerso() {
        return verso;
    }

    public void setVerso(String verso) {
        this.verso = verso;
    }

    public String getVisibilitaCross() {
        return visibilitaCross;
    }

    public void setVisibilitaCross(String visibilitaCross) {
        this.visibilitaCross = visibilitaCross;
    }

    public String getVisibilitaUtente() {
        return visibilitaUtente;
    }

    public void setVisibilitaUtente(String visibilitaUtente) {
        this.visibilitaUtente = visibilitaUtente;
    }

//    @XmlTransient
//    public List<Allegati> getAllegatiList() {
//        return allegatiList;
//    }
//
//    public void setAllegatiList(List<Allegati> allegatiList) {
//        this.allegatiList = allegatiList;
//    }
    @XmlTransient
    public List<PraticheEventiAllegati> getPraticheEventiAllegatiList() {
        return praticheEventiAllegatiList;
    }

    public void setPraticheEventiAllegatiList(List<PraticheEventiAllegati> praticheEventiAllegatiList) {
        this.praticheEventiAllegatiList = praticheEventiAllegatiList;
    }

    @XmlTransient
    public List<Enti> getEntiList() {
        return entiList;
    }

    public void setEntiList(List<Enti> entiList) {
        this.entiList = entiList;
    }

    @XmlTransient
    public List<Comunicazione> getComunicazioneList() {
        return comunicazioneList;
    }

    public void setComunicazioneList(List<Comunicazione> comunicazioneList) {
        this.comunicazioneList = comunicazioneList;
    }

    @XmlTransient
    public List<Email> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<Email> emailList) {
        this.emailList = emailList;
    }

    @XmlTransient
    public List<Scadenze> getScadenzeList() {
        return scadenzeList;
    }

    public void setScadenzeList(List<Scadenze> scadenzeList) {
        this.scadenzeList = scadenzeList;
    }

    @XmlTransient
    public List<PraticheEventiAnagrafiche> getPraticheEventiAnagraficheList() {
        return praticheEventiAnagraficheList;
    }

    public void setPraticheEventiAnagraficheList(List<PraticheEventiAnagrafiche> praticheEventiAnagraficheList) {
        this.praticheEventiAnagraficheList = praticheEventiAnagraficheList;
    }

    public Utente getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Utente idUtente) {
        this.idUtente = idUtente;
    }

    public ProcessiEventi getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(ProcessiEventi idEvento) {
        this.idEvento = idEvento;
    }

    public Pratica getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Pratica idPratica) {
        this.idPratica = idPratica;
    }

    public Recapiti getIdRecapitoNotifica() {
        return idRecapitoNotifica;
    }

    public void setIdRecapitoNotifica(Recapiti idRecapitoNotifica) {
        this.idRecapitoNotifica = idRecapitoNotifica;
    }

    public LkStatiMail getStatoMail() {
        return statoMail;
    }

    public void setStatoMail(LkStatiMail statoMail) {
        this.statoMail = statoMail;
    }

    public String getDescrizioneEvento() {
        return descrizioneEvento;
    }

    public void setDescrizioneEvento(String descrizioneEvento) {
        this.descrizioneEvento = descrizioneEvento;
    }

    public PraticheEventi getPraticaEventoRef() {
        return praticaEventoRef;
    }

    public void setPraticaEventoRef(PraticheEventi praticaEventoRef) {
        this.praticaEventoRef = praticaEventoRef;
    }

    @XmlTransient
    public List<PraticaEventiProcedimenti> getPraticaEventiProcedimentiList() {
        return praticaEventiProcedimentiList;
    }

    public void setPraticaEventiProcedimentiList(List<PraticaEventiProcedimenti> praticaEventiProcedimentiList) {
        this.praticaEventiProcedimentiList = praticaEventiProcedimentiList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPraticaEvento != null ? idPraticaEvento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PraticheEventi)) {
            return false;
        }
        PraticheEventi other = (PraticheEventi) object;
        if ((this.idPraticaEvento == null && other.idPraticaEvento != null) || (this.idPraticaEvento != null && !this.idPraticaEvento.equals(other.idPraticaEvento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.PraticheEventi[ idPraticaEvento=" + idPraticaEvento + " ]";
    }
}
