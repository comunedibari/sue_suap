/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author piergiorgio
 */
@Entity
@Table(name = "lk_tipo_oggetto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkTipoOggetto.findAll", query = "SELECT l FROM LkTipoOggetto l"),
    @NamedQuery(name = "LkTipoOggetto.findByIdTipoOggetto", query = "SELECT l FROM LkTipoOggetto l WHERE l.idTipoOggetto = :idTipoOggetto"),
    @NamedQuery(name = "LkTipoOggetto.findByCodTipoOggetto", query = "SELECT l FROM LkTipoOggetto l WHERE l.codTipoOggetto = :codTipoOggetto"),
    @NamedQuery(name = "LkTipoOggetto.findByDesTipoOggetto", query = "SELECT l FROM LkTipoOggetto l WHERE l.desTipoOggetto = :desTipoOggetto")})
public class LkTipoOggetto implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoOggetto")
    private List<DefDatiEstesi> defDatiEstesiList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_oggetto")
    private Integer idTipoOggetto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "cod_tipo_oggetto")
    private String codTipoOggetto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "des_tipo_oggetto")
    private String desTipoOggetto;

    public LkTipoOggetto() {
    }

    public LkTipoOggetto(Integer idTipoOggetto) {
        this.idTipoOggetto = idTipoOggetto;
    }

    public LkTipoOggetto(Integer idTipoOggetto, String codTipoOggetto, String desTipoOggetto) {
        this.idTipoOggetto = idTipoOggetto;
        this.codTipoOggetto = codTipoOggetto;
        this.desTipoOggetto = desTipoOggetto;
    }

    public Integer getIdTipoOggetto() {
        return idTipoOggetto;
    }

    public void setIdTipoOggetto(Integer idTipoOggetto) {
        this.idTipoOggetto = idTipoOggetto;
    }

    public String getCodTipoOggetto() {
        return codTipoOggetto;
    }

    public void setCodTipoOggetto(String codTipoOggetto) {
        this.codTipoOggetto = codTipoOggetto;
    }

    public String getDesTipoOggetto() {
        return desTipoOggetto;
    }

    public void setDesTipoOggetto(String desTipoOggetto) {
        this.desTipoOggetto = desTipoOggetto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoOggetto != null ? idTipoOggetto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkTipoOggetto)) {
            return false;
        }
        LkTipoOggetto other = (LkTipoOggetto) object;
        if ((this.idTipoOggetto == null && other.idTipoOggetto != null) || (this.idTipoOggetto != null && !this.idTipoOggetto.equals(other.idTipoOggetto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkTipoOggetto[ idTipoOggetto=" + idTipoOggetto + " ]";
    }

    @XmlTransient
    @JsonIgnore
    public List<DefDatiEstesi> getDefDatiEstesiList() {
        return defDatiEstesiList;
    }

    public void setDefDatiEstesiList(List<DefDatiEstesi> defDatiEstesiList) {
        this.defDatiEstesiList = defDatiEstesiList;
    }
    
}
