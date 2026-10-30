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
@Table(name = "permessi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Permessi.findAll", query = "SELECT p FROM Permessi p"),
    @NamedQuery(name = "Permessi.findByCodPermesso", query = "SELECT p FROM Permessi p WHERE p.codPermesso = :codPermesso"),
    @NamedQuery(name = "Permessi.findByDescrizione", query = "SELECT p FROM Permessi p WHERE p.descrizione = :descrizione")})
public class Permessi implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_permesso")
    private String codPermesso;
    @Column(name = "descrizione")
    private String descrizione;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codPermesso")
    private List<UtenteRuoloEnte> utenteRuoloEnteList;

    public Permessi() {
    }

    public Permessi(String codPermesso) {
        this.codPermesso = codPermesso;
    }

    public String getCodPermesso() {
        return codPermesso;
    }

    public void setCodPermesso(String codPermesso) {
        this.codPermesso = codPermesso;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @XmlTransient
    public List<UtenteRuoloEnte> getUtenteRuoloEnteList() {
        return utenteRuoloEnteList;
    }

    public void setUtenteRuoloEnteList(List<UtenteRuoloEnte> utenteRuoloEnteList) {
        this.utenteRuoloEnteList = utenteRuoloEnteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codPermesso != null ? codPermesso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permessi)) {
            return false;
        }
        Permessi other = (Permessi) object;
        if ((this.codPermesso == null && other.codPermesso != null) || (this.codPermesso != null && !this.codPermesso.equals(other.codPermesso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.Permessi[ codPermesso=" + codPermesso + " ]";
    }
    
}
