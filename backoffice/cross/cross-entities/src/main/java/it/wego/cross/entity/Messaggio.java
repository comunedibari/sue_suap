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
@Table(name = "messaggio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Messaggio.findAll", query = "SELECT m FROM Messaggio m"),
    @NamedQuery(name = "Messaggio.findByIdMessaggio", query = "SELECT m FROM Messaggio m WHERE m.idMessaggio = :idMessaggio"),
    @NamedQuery(name = "Messaggio.findByDataMessaggio", query = "SELECT m FROM Messaggio m WHERE m.dataMessaggio = :dataMessaggio"),
    @NamedQuery(name = "Messaggio.findByStatus", query = "SELECT m FROM Messaggio m WHERE m.status = :status")})
public class Messaggio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_messaggio")
    private Integer idMessaggio;
    @Basic(optional = false)
    @Lob
    @Column(name = "testo")
    private String testo;
    @Basic(optional = false)
    @Column(name = "data_messaggio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataMessaggio;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "id_utente_mittente", referencedColumnName = "id_utente")
    @ManyToOne(optional = false)
    private Utente idUtenteMittente;
    @JoinColumn(name = "id_utente_destinatario", referencedColumnName = "id_utente")
    @ManyToOne(optional = false)
    private Utente idUtenteDestinatario;
    @JoinColumn(name = "id_pratica", referencedColumnName = "id_pratica")
    @ManyToOne
    private Pratica idPratica;

    public Messaggio() {
    }

    public Messaggio(Integer idMessaggio) {
        this.idMessaggio = idMessaggio;
    }

    public Messaggio(Integer idMessaggio, String testo, Date dataMessaggio, String status) {
        this.idMessaggio = idMessaggio;
        this.testo = testo;
        this.dataMessaggio = dataMessaggio;
        this.status = status;
    }

    public Integer getIdMessaggio() {
        return idMessaggio;
    }

    public void setIdMessaggio(Integer idMessaggio) {
        this.idMessaggio = idMessaggio;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public Date getDataMessaggio() {
        return dataMessaggio;
    }

    public void setDataMessaggio(Date dataMessaggio) {
        this.dataMessaggio = dataMessaggio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Utente getIdUtenteMittente() {
        return idUtenteMittente;
    }

    public void setIdUtenteMittente(Utente idUtenteMittente) {
        this.idUtenteMittente = idUtenteMittente;
    }

    public Utente getIdUtenteDestinatario() {
        return idUtenteDestinatario;
    }

    public void setIdUtenteDestinatario(Utente idUtenteDestinatario) {
        this.idUtenteDestinatario = idUtenteDestinatario;
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
        hash += (idMessaggio != null ? idMessaggio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Messaggio)) {
            return false;
        }
        Messaggio other = (Messaggio) object;
        if ((this.idMessaggio == null && other.idMessaggio != null) || (this.idMessaggio != null && !this.idMessaggio.equals(other.idMessaggio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.Messaggio[ idMessaggio=" + idMessaggio + " ]";
    }
    
}
