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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "utente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Utente.findAll", query = "SELECT u FROM Utente u"),
    @NamedQuery(name = "Utente.findByIdUtente", query = "SELECT u FROM Utente u WHERE u.idUtente = :idUtente"),
    @NamedQuery(name = "Utente.findByNome", query = "SELECT u FROM Utente u WHERE u.nome = :nome"),
    @NamedQuery(name = "Utente.findByCognome", query = "SELECT u FROM Utente u WHERE u.cognome = :cognome"),
    @NamedQuery(name = "Utente.findByCodiceFiscale", query = "SELECT u FROM Utente u WHERE u.codiceFiscale = :codiceFiscale"),
    @NamedQuery(name = "Utente.findByUsername", query = "SELECT u FROM Utente u WHERE u.username = :username"),
    @NamedQuery(name = "Utente.findByEmail", query = "SELECT u FROM Utente u WHERE u.email = :email"),
    @NamedQuery(name = "Utente.findByTelefono", query = "SELECT u FROM Utente u WHERE u.telefono = :telefono"),
    @NamedQuery(name = "Utente.findByNote", query = "SELECT u FROM Utente u WHERE u.note = :note"),
    @NamedQuery(name = "Utente.findByDataAttivazione", query = "SELECT u FROM Utente u WHERE u.dataAttivazione = :dataAttivazione"),
    @NamedQuery(name = "Utente.findByStatus", query = "SELECT u FROM Utente u WHERE u.status = :status"),
    @NamedQuery(name = "Utente.findBySuperuser", query = "SELECT u FROM Utente u WHERE u.superuser = :superuser")})
public class Utente implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "estrazioni_user")
    private Character estrazioniUser;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "superuser")
    private Character superuser;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "estrazioni_cila_todo")
    private Character estrazioniCilaTodoUser;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_utente")
    private Integer idUtente;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "cognome")
    private String cognome;
    @Basic(optional = false)
    @Column(name = "codice_fiscale")
    private String codiceFiscale;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = true)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "note")
    private String note;
    @Basic(optional = false)
    @Column(name = "data_attivazione")
    @Temporal(TemporalType.DATE)
    private Date dataAttivazione;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "idUtente")
    private List<Errori> erroriList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUtenteMittente")
    private List<Messaggio> messaggiMittente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUtenteDestinatario")
    private List<Messaggio> messaggiDestinatario;
    @OneToMany(mappedBy = "idUtenteApertura")
    private List<Staging> stagingList;
    @OneToMany(mappedBy = "idUtentePresaInCarico")
    private List<PraticheProtocollo> praticheProtocolloList;
    @OneToMany(mappedBy = "idUtente")
    private List<Pratica> praticaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUtente")
    private List<NotePratica> notePraticaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUtente")
    private List<UtenteRuoloEnte> utenteRuoloEnteList;
    @OneToMany(mappedBy = "idUtente")
    private List<PraticheEventi> praticheEventiList;

    public Utente() {
    }

    public Utente(Integer idUtente) {
        this.idUtente = idUtente;
    }

    public Utente(Integer idUtente, String nome, String cognome, String codiceFiscale, String username, String email, Date dataAttivazione, String status, char superuser, String password) {
        this.idUtente = idUtente;
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.username = username;
        this.email = email;
        this.dataAttivazione = dataAttivazione;
        this.status = status;
        this.superuser = superuser;
        this.password = password;
    }

    public Integer getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Integer idUtente) {
        this.idUtente = idUtente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDataAttivazione() {
        return dataAttivazione;
    }

    public void setDataAttivazione(Date dataAttivazione) {
        this.dataAttivazione = dataAttivazione;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @XmlTransient
    public List<Errori> getErroriList() {
        return erroriList;
    }

    public void setErroriList(List<Errori> erroriList) {
        this.erroriList = erroriList;
    }

    @XmlTransient
    public List<Messaggio> getMessaggiMittente() {
        return messaggiMittente;
    }

    public void setMessaggiMittente(List<Messaggio> messaggiMittente) {
        this.messaggiMittente = messaggiMittente;
    }

    @XmlTransient
    public List<Messaggio> getMessaggiDestinatario() {
        return messaggiDestinatario;
    }

    public void setMessaggiDestinatario(List<Messaggio> messaggiDestinatario) {
        this.messaggiDestinatario = messaggiDestinatario;
    }

    @XmlTransient
    public List<Staging> getStagingList() {
        return stagingList;
    }

    public void setStagingList(List<Staging> stagingList) {
        this.stagingList = stagingList;
    }

    @XmlTransient
    public List<PraticheProtocollo> getPraticheProtocolloList() {
        return praticheProtocolloList;
    }

    public void setPraticheProtocolloList(List<PraticheProtocollo> praticheProtocolloList) {
        this.praticheProtocolloList = praticheProtocolloList;
    }

    @XmlTransient
    public List<Pratica> getPraticaList() {
        return praticaList;
    }

    public void setPraticaList(List<Pratica> praticaList) {
        this.praticaList = praticaList;
    }

    @XmlTransient
    public List<NotePratica> getNotePraticaList() {
        return notePraticaList;
    }

    public void setNotePraticaList(List<NotePratica> notePraticaList) {
        this.notePraticaList = notePraticaList;
    }

    @XmlTransient
    public List<UtenteRuoloEnte> getUtenteRuoloEnteList() {
        return utenteRuoloEnteList;
    }

    public void setUtenteRuoloEnteList(List<UtenteRuoloEnte> utenteRuoloEnteList) {
        this.utenteRuoloEnteList = utenteRuoloEnteList;
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
        hash += (idUtente != null ? idUtente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Utente)) {
            return false;
        }
        Utente other = (Utente) object;
        if ((this.idUtente == null && other.idUtente != null) || (this.idUtente != null && !this.idUtente.equals(other.idUtente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.Utente[ idUtente=" + idUtente + " ]";
    }

    public Character getSuperuser() {
        return superuser;
    }

    public void setSuperuser(Character superuser) {
        this.superuser = superuser;
    }

	public Character getEstrazioniUser() {
		return estrazioniUser;
	}

	public void setEstrazioniUser(Character estrazioniUser) {
		this.estrazioniUser = estrazioniUser;
	}

	public Character getEstrazioniCilaTodoUser() {
		return estrazioniCilaTodoUser;
	}

	public void setEstrazioniCilaTodoUser(Character estrazioniCilaTodoUser) {
		this.estrazioniCilaTodoUser = estrazioniCilaTodoUser;
	}
    
	
    
}
