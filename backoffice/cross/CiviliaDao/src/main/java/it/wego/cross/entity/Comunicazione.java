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
@Table(name = "comunicazione")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comunicazione.findAll", query = "SELECT c FROM Comunicazione c"),
    @NamedQuery(name = "Comunicazione.findByIdComunicazione", query = "SELECT c FROM Comunicazione c WHERE c.idComunicazione = :idComunicazione"),
    @NamedQuery(name = "Comunicazione.findByDataComunicazione", query = "SELECT c FROM Comunicazione c WHERE c.dataComunicazione = :dataComunicazione"),
    @NamedQuery(name = "Comunicazione.findByStatus", query = "SELECT c FROM Comunicazione c WHERE c.status = :status")})
public class Comunicazione implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_comunicazione")
    private Integer idComunicazione;
    @Basic(optional = false)
    @Column(name = "data_comunicazione")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataComunicazione;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "id_pratica_evento", referencedColumnName = "id_pratica_evento")
    @ManyToOne(optional = false)
    private PraticheEventi idPraticaEvento;
    @JoinColumn(name = "id_pratica", referencedColumnName = "id_pratica")
    @ManyToOne
    private Pratica idPratica;
    @JoinColumn(name = "id_ente", referencedColumnName = "id_ente")
    @ManyToOne(optional = false)
    private Enti idEnte;

    public Comunicazione() {
    }

    public Comunicazione(Integer idComunicazione) {
        this.idComunicazione = idComunicazione;
    }

    public Comunicazione(Integer idComunicazione, Date dataComunicazione, String status) {
        this.idComunicazione = idComunicazione;
        this.dataComunicazione = dataComunicazione;
        this.status = status;
    }

    public Integer getIdComunicazione() {
        return idComunicazione;
    }

    public void setIdComunicazione(Integer idComunicazione) {
        this.idComunicazione = idComunicazione;
    }

    public Date getDataComunicazione() {
        return dataComunicazione;
    }

    public void setDataComunicazione(Date dataComunicazione) {
        this.dataComunicazione = dataComunicazione;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PraticheEventi getIdPraticaEvento() {
        return idPraticaEvento;
    }

    public void setIdPraticaEvento(PraticheEventi idPraticaEvento) {
        this.idPraticaEvento = idPraticaEvento;
    }

    public Pratica getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Pratica idPratica) {
        this.idPratica = idPratica;
    }

    public Enti getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(Enti idEnte) {
        this.idEnte = idEnte;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComunicazione != null ? idComunicazione.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comunicazione)) {
            return false;
        }
        Comunicazione other = (Comunicazione) object;
        if ((this.idComunicazione == null && other.idComunicazione != null) || (this.idComunicazione != null && !this.idComunicazione.equals(other.idComunicazione))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.Comunicazione[ idComunicazione=" + idComunicazione + " ]";
    }
    
}
