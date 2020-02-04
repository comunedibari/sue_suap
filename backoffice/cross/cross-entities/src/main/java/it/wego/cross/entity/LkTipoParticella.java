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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "lk_tipo_particella")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkTipoParticella.findAll", query = "SELECT l FROM LkTipoParticella l"),
    @NamedQuery(name = "LkTipoParticella.findByIdTipoParticella", query = "SELECT l FROM LkTipoParticella l WHERE l.idTipoParticella = :idTipoParticella"),
    @NamedQuery(name = "LkTipoParticella.findByDescrizione", query = "SELECT l FROM LkTipoParticella l WHERE l.descrizione = :descrizione"),
    @NamedQuery(name = "LkTipoParticella.findByCodTipoParticella", query = "SELECT l FROM LkTipoParticella l WHERE l.codTipoParticella = :codTipoParticella")})
public class LkTipoParticella implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_particella")
    private Integer idTipoParticella;
    @Basic(optional = false)
    @Column(name = "descrizione")
    private String descrizione;
    @Column(name = "cod_tipo_particella")
    private String codTipoParticella;
    @OneToMany(mappedBy = "idTipoParticella")
    private List<DatiCatastali> datiCatastaliList;

    public LkTipoParticella() {
    }

    public LkTipoParticella(Integer idTipoParticella) {
        this.idTipoParticella = idTipoParticella;
    }

    public LkTipoParticella(Integer idTipoParticella, String descrizione) {
        this.idTipoParticella = idTipoParticella;
        this.descrizione = descrizione;
    }

    public Integer getIdTipoParticella() {
        return idTipoParticella;
    }

    public void setIdTipoParticella(Integer idTipoParticella) {
        this.idTipoParticella = idTipoParticella;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCodTipoParticella() {
        return codTipoParticella;
    }

    public void setCodTipoParticella(String codTipoParticella) {
        this.codTipoParticella = codTipoParticella;
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
        hash += (idTipoParticella != null ? idTipoParticella.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkTipoParticella)) {
            return false;
        }
        LkTipoParticella other = (LkTipoParticella) object;
        if ((this.idTipoParticella == null && other.idTipoParticella != null) || (this.idTipoParticella != null && !this.idTipoParticella.equals(other.idTipoParticella))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkTipoParticella[ idTipoParticella=" + idTipoParticella + " ]";
    }
}
