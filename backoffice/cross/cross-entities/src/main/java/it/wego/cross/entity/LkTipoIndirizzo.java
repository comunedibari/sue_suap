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
@Table(name = "lk_tipo_indirizzo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkTipoIndirizzo.findAll", query = "SELECT l FROM LkTipoIndirizzo l"),
    @NamedQuery(name = "LkTipoIndirizzo.findByIdTipoIndirizzo", query = "SELECT l FROM LkTipoIndirizzo l WHERE l.idTipoIndirizzo = :idTipoIndirizzo"),
    @NamedQuery(name = "LkTipoIndirizzo.findByCodTipoIndirizzo", query = "SELECT l FROM LkTipoIndirizzo l WHERE l.codTipoIndirizzo = :codTipoIndirizzo"),
    @NamedQuery(name = "LkTipoIndirizzo.findByDescrizione", query = "SELECT l FROM LkTipoIndirizzo l WHERE l.descrizione = :descrizione")})
public class LkTipoIndirizzo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_indirizzo")
    private Integer idTipoIndirizzo;
    @Column(name = "cod_tipo_indirizzo")
    private String codTipoIndirizzo;
    @Column(name = "descrizione")
    private String descrizione;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoIndirizzo")
    private List<AnagraficaRecapiti> anagraficaRecapitiList;

    public LkTipoIndirizzo() {
    }

    public LkTipoIndirizzo(Integer idTipoIndirizzo) {
        this.idTipoIndirizzo = idTipoIndirizzo;
    }

    public Integer getIdTipoIndirizzo() {
        return idTipoIndirizzo;
    }

    public void setIdTipoIndirizzo(Integer idTipoIndirizzo) {
        this.idTipoIndirizzo = idTipoIndirizzo;
    }

    public String getCodTipoIndirizzo() {
        return codTipoIndirizzo;
    }

    public void setCodTipoIndirizzo(String codTipoIndirizzo) {
        this.codTipoIndirizzo = codTipoIndirizzo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @XmlTransient
    public List<AnagraficaRecapiti> getAnagraficaRecapitiList() {
        return anagraficaRecapitiList;
    }

    public void setAnagraficaRecapitiList(List<AnagraficaRecapiti> anagraficaRecapitiList) {
        this.anagraficaRecapitiList = anagraficaRecapitiList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoIndirizzo != null ? idTipoIndirizzo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkTipoIndirizzo)) {
            return false;
        }
        LkTipoIndirizzo other = (LkTipoIndirizzo) object;
        if ((this.idTipoIndirizzo == null && other.idTipoIndirizzo != null) || (this.idTipoIndirizzo != null && !this.idTipoIndirizzo.equals(other.idTipoIndirizzo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkTipoIndirizzo[ idTipoIndirizzo=" + idTipoIndirizzo + " ]";
    }
    
}
