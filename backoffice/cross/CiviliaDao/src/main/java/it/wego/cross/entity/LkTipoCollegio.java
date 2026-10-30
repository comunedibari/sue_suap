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
@Table(name = "lk_tipo_collegio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkTipoCollegio.findAll", query = "SELECT l FROM LkTipoCollegio l"),
    @NamedQuery(name = "LkTipoCollegio.findByIdTipoCollegio", query = "SELECT l FROM LkTipoCollegio l WHERE l.idTipoCollegio = :idTipoCollegio"),
    @NamedQuery(name = "LkTipoCollegio.findByDescrizione", query = "SELECT l FROM LkTipoCollegio l WHERE l.descrizione = :descrizione"),
    @NamedQuery(name = "LkTipoCollegio.findByCodCollegio", query = "SELECT l FROM LkTipoCollegio l WHERE l.codCollegio = :codCollegio")})
public class LkTipoCollegio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_collegio")
    private Integer idTipoCollegio;
    @Column(name = "descrizione")
    private String descrizione;
    @Column(name = "cod_collegio")
    private String codCollegio;
    @OneToMany(mappedBy = "idTipoCollegio")
    private List<Anagrafica> anagraficaList;

    public LkTipoCollegio() {
    }

    public LkTipoCollegio(Integer idTipoCollegio) {
        this.idTipoCollegio = idTipoCollegio;
    }

    public Integer getIdTipoCollegio() {
        return idTipoCollegio;
    }

    public void setIdTipoCollegio(Integer idTipoCollegio) {
        this.idTipoCollegio = idTipoCollegio;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCodCollegio() {
        return codCollegio;
    }

    public void setCodCollegio(String codCollegio) {
        this.codCollegio = codCollegio;
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
        hash += (idTipoCollegio != null ? idTipoCollegio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkTipoCollegio)) {
            return false;
        }
        LkTipoCollegio other = (LkTipoCollegio) object;
        if ((this.idTipoCollegio == null && other.idTipoCollegio != null) || (this.idTipoCollegio != null && !this.idTipoCollegio.equals(other.idTipoCollegio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkTipoCollegio[ idTipoCollegio=" + idTipoCollegio + " ]";
    }
    
}
