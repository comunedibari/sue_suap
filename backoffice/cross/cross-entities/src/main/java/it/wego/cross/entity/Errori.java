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
@Table(name = "errori")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Errori.findAll", query = "SELECT e FROM Errori e"),
    @NamedQuery(name = "Errori.findByIdErrore", query = "SELECT e FROM Errori e WHERE e.idErrore = :idErrore"),
    @NamedQuery(name = "Errori.findByStatus", query = "SELECT e FROM Errori e WHERE e.status = :status"),
    @NamedQuery(name = "Errori.findByData", query = "SELECT e FROM Errori e WHERE e.data = :data"),
    @NamedQuery(name = "Errori.findByDescrizione", query = "SELECT e FROM Errori e WHERE e.descrizione = :descrizione")})
public class Errori implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_errore")
    private Integer idErrore;
    @Column(name = "status")
    private Character status;
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Column(name = "descrizione")
    private String descrizione;
    @Lob
    @Column(name = "trace")
    private String trace;
    @JoinColumn(name = "id_utente", referencedColumnName = "id_utente")
    @ManyToOne
    private Utente idUtente;
    @JoinColumn(name = "id_pratica", referencedColumnName = "id_pratica")
    @ManyToOne
    private Pratica idPratica;
    @JoinColumn(name = "cod_errore", referencedColumnName = "cod_errore")
    @ManyToOne
    private DizionarioErrori codErrore;

    public Errori() {
    }

    public Errori(Integer idErrore) {
        this.idErrore = idErrore;
    }

    public Integer getIdErrore() {
        return idErrore;
    }

    public void setIdErrore(Integer idErrore) {
        this.idErrore = idErrore;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public Utente getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Utente idUtente) {
        this.idUtente = idUtente;
    }

    public Pratica getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Pratica idPratica) {
        this.idPratica = idPratica;
    }

    public DizionarioErrori getCodErrore() {
        return codErrore;
    }

    public void setCodErrore(DizionarioErrori codErrore) {
        this.codErrore = codErrore;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idErrore != null ? idErrore.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Errori)) {
            return false;
        }
        Errori other = (Errori) object;
        if ((this.idErrore == null && other.idErrore != null) || (this.idErrore != null && !this.idErrore.equals(other.idErrore))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.Errori[ idErrore=" + idErrore + " ]";
    }
    
}
