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
@Table(name = "note_pratica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotePratica.findAll", query = "SELECT n FROM NotePratica n"),
    @NamedQuery(name = "NotePratica.findByIdNotePratica", query = "SELECT n FROM NotePratica n WHERE n.idNotePratica = :idNotePratica"),
    @NamedQuery(name = "NotePratica.findByDataInserimento", query = "SELECT n FROM NotePratica n WHERE n.dataInserimento = :dataInserimento")})
public class NotePratica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_note_pratica")
    private Integer idNotePratica;
    @Basic(optional = false)
    @Column(name = "data_inserimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInserimento;
    @Basic(optional = false)
    @Lob
    @Column(name = "testo")
    private String testo;
    @JoinColumn(name = "id_utente", referencedColumnName = "id_utente")
    @ManyToOne(optional = false)
    private Utente idUtente;
    @JoinColumn(name = "id_pratica", referencedColumnName = "id_pratica")
    @ManyToOne(optional = false)
    private Pratica idPratica;

    public NotePratica() {
    }

    public NotePratica(Integer idNotePratica) {
        this.idNotePratica = idNotePratica;
    }

    public NotePratica(Integer idNotePratica, Date dataInserimento, String testo) {
        this.idNotePratica = idNotePratica;
        this.dataInserimento = dataInserimento;
        this.testo = testo;
    }

    public Integer getIdNotePratica() {
        return idNotePratica;
    }

    public void setIdNotePratica(Integer idNotePratica) {
        this.idNotePratica = idNotePratica;
    }

    public Date getDataInserimento() {
        return dataInserimento;
    }

    public void setDataInserimento(Date dataInserimento) {
        this.dataInserimento = dataInserimento;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNotePratica != null ? idNotePratica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotePratica)) {
            return false;
        }
        NotePratica other = (NotePratica) object;
        if ((this.idNotePratica == null && other.idNotePratica != null) || (this.idNotePratica != null && !this.idNotePratica.equals(other.idNotePratica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.NotePratica[ idNotePratica=" + idNotePratica + " ]";
    }
    
}
