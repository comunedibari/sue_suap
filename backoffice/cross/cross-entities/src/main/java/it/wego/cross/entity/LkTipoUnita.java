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
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "lk_tipo_unita")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkTipoUnita.findAll", query = "SELECT l FROM LkTipoUnita l"),
    @NamedQuery(name = "LkTipoUnita.findByIdTipoUnita", query = "SELECT l FROM LkTipoUnita l WHERE l.idTipoUnita = :idTipoUnita"),
    @NamedQuery(name = "LkTipoUnita.findByDescrizione", query = "SELECT l FROM LkTipoUnita l WHERE l.descrizione = :descrizione"),
    @NamedQuery(name = "LkTipoUnita.findByCodTipoUnita", query = "SELECT l FROM LkTipoUnita l WHERE l.codTipoUnita = :codTipoUnita")})
public class LkTipoUnita implements Serializable {
    @OneToMany(mappedBy = "idTipoUnita")
    private List<DatiCatastali> datiCatastaliList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_unita")
    private Integer idTipoUnita;
    @Basic(optional = false)
    @Column(name = "descrizione")
    private String descrizione;
    @Column(name = "cod_tipo_unita")
    private String codTipoUnita;

    public LkTipoUnita() {
    }

    public LkTipoUnita(Integer idTipoUnita) {
        this.idTipoUnita = idTipoUnita;
    }

    public LkTipoUnita(Integer idTipoUnita, String descrizione) {
        this.idTipoUnita = idTipoUnita;
        this.descrizione = descrizione;
    }

    public Integer getIdTipoUnita() {
        return idTipoUnita;
    }

    public void setIdTipoUnita(Integer idTipoUnita) {
        this.idTipoUnita = idTipoUnita;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCodTipoUnita() {
        return codTipoUnita;
    }

    public void setCodTipoUnita(String codTipoUnita) {
        this.codTipoUnita = codTipoUnita;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoUnita != null ? idTipoUnita.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkTipoUnita)) {
            return false;
        }
        LkTipoUnita other = (LkTipoUnita) object;
        if ((this.idTipoUnita == null && other.idTipoUnita != null) || (this.idTipoUnita != null && !this.idTipoUnita.equals(other.idTipoUnita))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkTipoUnita[ idTipoUnita=" + idTipoUnita + " ]";
    }

    @XmlTransient
    @JsonIgnore
    public List<DatiCatastali> getDatiCatastaliList() {
        return datiCatastaliList;
    }

    public void setDatiCatastaliList(List<DatiCatastali> datiCatastaliList) {
        this.datiCatastaliList = datiCatastaliList;
    }

}
