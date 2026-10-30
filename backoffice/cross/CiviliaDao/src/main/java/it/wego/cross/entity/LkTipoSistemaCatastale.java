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
@Table(name = "lk_tipo_sistema_catastale")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkTipoSistemaCatastale.findAll", query = "SELECT l FROM LkTipoSistemaCatastale l"),
    @NamedQuery(name = "LkTipoSistemaCatastale.findByIdTipoSistemaCatastale", query = "SELECT l FROM LkTipoSistemaCatastale l WHERE l.idTipoSistemaCatastale = :idTipoSistemaCatastale"),
    @NamedQuery(name = "LkTipoSistemaCatastale.findByDescrizione", query = "SELECT l FROM LkTipoSistemaCatastale l WHERE l.descrizione = :descrizione")})
public class LkTipoSistemaCatastale implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_sistema_catastale")
    private Integer idTipoSistemaCatastale;
    @Column(name = "descrizione")
    private String descrizione;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoSistemaCatastale")
    private List<DatiCatastali> datiCatastaliList;

    public LkTipoSistemaCatastale() {
    }

    public LkTipoSistemaCatastale(Integer idTipoSistemaCatastale) {
        this.idTipoSistemaCatastale = idTipoSistemaCatastale;
    }

    public Integer getIdTipoSistemaCatastale() {
        return idTipoSistemaCatastale;
    }

    public void setIdTipoSistemaCatastale(Integer idTipoSistemaCatastale) {
        this.idTipoSistemaCatastale = idTipoSistemaCatastale;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @XmlTransient
    public List<DatiCatastali> getDatiCatastaliList() {
        return datiCatastaliList;
    }

    public void setDatiCatastaliList(List<DatiCatastali> datiCatastaliList) {
        this.datiCatastaliList = datiCatastaliList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoSistemaCatastale != null ? idTipoSistemaCatastale.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkTipoSistemaCatastale)) {
            return false;
        }
        LkTipoSistemaCatastale other = (LkTipoSistemaCatastale) object;
        if ((this.idTipoSistemaCatastale == null && other.idTipoSistemaCatastale != null) || (this.idTipoSistemaCatastale != null && !this.idTipoSistemaCatastale.equals(other.idTipoSistemaCatastale))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkTipoSistemaCatastale[ idTipoSistemaCatastale=" + idTipoSistemaCatastale + " ]";
    }
    
}
