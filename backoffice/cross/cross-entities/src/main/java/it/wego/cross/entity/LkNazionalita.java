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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "lk_nazionalita")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkNazionalita.findAll", query = "SELECT l FROM LkNazionalita l"),
    @NamedQuery(name = "LkNazionalita.findByIdNazionalita", query = "SELECT l FROM LkNazionalita l WHERE l.idNazionalita = :idNazionalita"),
    @NamedQuery(name = "LkNazionalita.findByCodNazionalita", query = "SELECT l FROM LkNazionalita l WHERE l.codNazionalita = :codNazionalita"),
    @NamedQuery(name = "LkNazionalita.findByDescrizione", query = "SELECT l FROM LkNazionalita l WHERE l.descrizione = :descrizione")})
public class LkNazionalita implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_nazionalita")
    private Integer idNazionalita;
    @Basic(optional = false)
    @Column(name = "cod_nazionalita")
    private String codNazionalita;
    @Basic(optional = false)
    @Column(name = "descrizione")
    private String descrizione;
    @OneToMany(mappedBy = "idNazionalita")
    private List<Anagrafica> anagraficaList;

    public LkNazionalita() {
    }

    public LkNazionalita(Integer idNazionalita) {
        this.idNazionalita = idNazionalita;
    }

    public LkNazionalita(Integer idNazionalita, String codNazionalita, String descrizione) {
        this.idNazionalita = idNazionalita;
        this.codNazionalita = codNazionalita;
        this.descrizione = descrizione;
    }

    public Integer getIdNazionalita() {
        return idNazionalita;
    }

    public void setIdNazionalita(Integer idNazionalita) {
        this.idNazionalita = idNazionalita;
    }

    public String getCodNazionalita() {
        return codNazionalita;
    }

    public void setCodNazionalita(String codNazionalita) {
        this.codNazionalita = codNazionalita;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @XmlTransient
    public List<Anagrafica> getAnagraficaList() {
        return anagraficaList;
    }

    public void setAnagraficaList(List<Anagrafica> anagraficaList) {
        this.anagraficaList = anagraficaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNazionalita != null ? idNazionalita.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkNazionalita)) {
            return false;
        }
        LkNazionalita other = (LkNazionalita) object;
        if ((this.idNazionalita == null && other.idNazionalita != null) || (this.idNazionalita != null && !this.idNazionalita.equals(other.idNazionalita))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkNazionalita[ idNazionalita=" + idNazionalita + " ]";
    }
    
}
