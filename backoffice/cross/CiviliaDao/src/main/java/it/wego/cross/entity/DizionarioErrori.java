/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "dizionario_errori")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DizionarioErrori.findAll", query = "SELECT d FROM DizionarioErrori d"),
    @NamedQuery(name = "DizionarioErrori.findByCodErrore", query = "SELECT d FROM DizionarioErrori d WHERE d.codErrore = :codErrore"),
    @NamedQuery(name = "DizionarioErrori.findByDescrizione", query = "SELECT d FROM DizionarioErrori d WHERE d.descrizione = :descrizione")})
public class DizionarioErrori implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_errore")
    private String codErrore;
    @Basic(optional = false)
    @Column(name = "descrizione")
    private String descrizione;
    @Lob
    @Column(name = "note")
    private String note;
    @OneToMany(mappedBy = "codErrore")
    private List<Errori> erroriList;

    public DizionarioErrori() {
    }

    public DizionarioErrori(String codErrore) {
        this.codErrore = codErrore;
    }

    public DizionarioErrori(String codErrore, String descrizione) {
        this.codErrore = codErrore;
        this.descrizione = descrizione;
    }

    public String getCodErrore() {
        return codErrore;
    }

    public void setCodErrore(String codErrore) {
        this.codErrore = codErrore;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @XmlTransient
    public List<Errori> getErroriList() {
        return erroriList;
    }

    public void setErroriList(List<Errori> erroriList) {
        this.erroriList = erroriList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codErrore != null ? codErrore.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DizionarioErrori)) {
            return false;
        }
        DizionarioErrori other = (DizionarioErrori) object;
        if ((this.codErrore == null && other.codErrore != null) || (this.codErrore != null && !this.codErrore.equals(other.codErrore))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.DizionarioErrori[ codErrore=" + codErrore + " ]";
    }
    
}
