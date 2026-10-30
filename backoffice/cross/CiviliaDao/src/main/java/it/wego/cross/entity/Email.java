/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "email")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Email.findAll", query = "SELECT e FROM Email e"),
    @NamedQuery(name = "Email.findByIdEmail", query = "SELECT e FROM Email e WHERE e.idEmail = :idEmail"),
    @NamedQuery(name = "Email.findByDataInserimento", query = "SELECT e FROM Email e WHERE e.dataInserimento = :dataInserimento"),
    @NamedQuery(name = "Email.findByDataAggiornamento", query = "SELECT e FROM Email e WHERE e.dataAggiornamento = :dataAggiornamento"),
    @NamedQuery(name = "Email.findByIdMessaggio", query = "SELECT e FROM Email e WHERE e.idMessaggio = :idMessaggio"),
    @NamedQuery(name = "Email.findByIdMessaggioPec", query = "SELECT e FROM Email e WHERE e.idMessaggioPec = :idMessaggioPec"),
    @NamedQuery(name = "Email.findByEmailDestinatario", query = "SELECT e FROM Email e WHERE e.emailDestinatario = :emailDestinatario"),
    @NamedQuery(name = "Email.findByGruppo", query = "SELECT e FROM Email e WHERE e.gruppo = :gruppo")})
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_email")
    private Integer idEmail;
    @Basic(optional = false)
    @Column(name = "data_inserimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInserimento;
    @Column(name = "data_aggiornamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAggiornamento;
    @Lob
    @Column(name = "path_eml")
    private String pathEml;
    @Column(name = "id_messaggio")
    private String idMessaggio;
    @Column(name = "id_messaggio_pec")
    private String idMessaggioPec;
    @Basic(optional = false)
    @Column(name = "email_destinatario")
    private String emailDestinatario;
    @Lob
    @Column(name = "corpo_email")
    private String corpoEmail;
    @Lob
    @Column(name = "oggetto_email")
    private String oggettoEmail;
    @Lob
    @Column(name = "oggetto_risposta")
    private String oggettoRisposta;
    @Lob
    @Column(name = "corpo_risposta")
    private String corpoRisposta;
    @Column(name = "gruppo")
    private Integer gruppo;
    @JoinColumn(name = "stato", referencedColumnName = "id_stati_mail")
    @ManyToOne
    private LkStatiMail stato;
    @JoinColumn(name = "id_pratica_evento", referencedColumnName = "id_pratica_evento")
    @ManyToOne(optional = false)
    private PraticheEventi idPraticaEvento;
    @Column(name = "tipo_destinazione")
    private String tipoDestinazione;

    public Email() {
    }

    public Email(Integer idEmail) {
        this.idEmail = idEmail;
    }

    public Email(Integer idEmail, Date dataInserimento, String emailDestinatario) {
        this.idEmail = idEmail;
        this.dataInserimento = dataInserimento;
        this.emailDestinatario = emailDestinatario;
    }

    public Integer getIdEmail() {
        return idEmail;
    }

    public void setIdEmail(Integer idEmail) {
        this.idEmail = idEmail;
    }

    public Date getDataInserimento() {
        return dataInserimento;
    }

    public void setDataInserimento(Date dataInserimento) {
        this.dataInserimento = dataInserimento;
    }

    public Date getDataAggiornamento() {
        return dataAggiornamento;
    }

    public void setDataAggiornamento(Date dataAggiornamento) {
        this.dataAggiornamento = dataAggiornamento;
    }

    public String getPathEml() {
        return pathEml;
    }

    public void setPathEml(String pathEml) {
        this.pathEml = pathEml;
    }

    public String getIdMessaggio() {
        return idMessaggio;
    }

    public void setIdMessaggio(String idMessaggio) {
        this.idMessaggio = idMessaggio;
    }

    public String getIdMessaggioPec() {
        return idMessaggioPec;
    }

    public void setIdMessaggioPec(String idMessaggioPec) {
        this.idMessaggioPec = idMessaggioPec;
    }

    public String getEmailDestinatario() {
        return emailDestinatario;
    }

    public void setEmailDestinatario(String emailDestinatario) {
        this.emailDestinatario = emailDestinatario;
    }

    public String getCorpoEmail() {
        return corpoEmail;
    }

    public void setCorpoEmail(String corpoEmail) {
        this.corpoEmail = corpoEmail;
    }

    public String getOggettoEmail() {
        return oggettoEmail;
    }

    public void setOggettoEmail(String oggettoEmail) {
        this.oggettoEmail = oggettoEmail;
    }

    public String getOggettoRisposta() {
        return oggettoRisposta;
    }

    public void setOggettoRisposta(String oggettoRisposta) {
        this.oggettoRisposta = oggettoRisposta;
    }

    public String getCorpoRisposta() {
        return corpoRisposta;
    }

    public void setCorpoRisposta(String corpoRisposta) {
        this.corpoRisposta = corpoRisposta;
    }

    public Integer getGruppo() {
        return gruppo;
    }

    public void setGruppo(Integer gruppo) {
        this.gruppo = gruppo;
    }

    public LkStatiMail getStato() {
        return stato;
    }

    public void setStato(LkStatiMail stato) {
        this.stato = stato;
    }

    public PraticheEventi getIdPraticaEvento() {
        return idPraticaEvento;
    }

    public void setIdPraticaEvento(PraticheEventi idPraticaEvento) {
        this.idPraticaEvento = idPraticaEvento;
    }

    public String getTipoDestinazione() {
        return tipoDestinazione;
    }

    public void setTipoDestinazione(String tipoDestinazione) {
        this.tipoDestinazione = tipoDestinazione;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmail != null ? idEmail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Email)) {
            return false;
        }
        Email other = (Email) object;
        if ((this.idEmail == null && other.idEmail != null) || (this.idEmail != null && !this.idEmail.equals(other.idEmail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.Email[ idEmail=" + idEmail + " ]";
    }

}
