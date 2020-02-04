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
@Table(name = "lk_tipo_qualifica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkTipoQualifica.findAll", query = "SELECT l FROM LkTipoQualifica l"),
    @NamedQuery(name = "LkTipoQualifica.findByIdTipoQualifica", query = "SELECT l FROM LkTipoQualifica l WHERE l.idTipoQualifica = :idTipoQualifica"),
    @NamedQuery(name = "LkTipoQualifica.findByDescrizione", query = "SELECT l FROM LkTipoQualifica l WHERE l.descrizione = :descrizione"),
    @NamedQuery(name = "LkTipoQualifica.findByCodQualifica", query = "SELECT l FROM LkTipoQualifica l WHERE l.codQualifica = :codQualifica"),
    @NamedQuery(name = "LkTipoQualifica.findByCondizione", query = "SELECT l FROM LkTipoQualifica l WHERE l.condizione = :condizione")})
public class LkTipoQualifica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_qualifica")
    private Integer idTipoQualifica;
    @Column(name = "descrizione")
    private String descrizione;
    @Column(name = "cod_qualifica")
    private String codQualifica;
    @Column(name = "condizione")
    private String condizione;
    @OneToMany(mappedBy = "idTipoQualifica")
    private List<PraticaAnagrafica> praticaAnagraficaList;

    public LkTipoQualifica() {
    }

    public LkTipoQualifica(Integer idTipoQualifica) {
        this.idTipoQualifica = idTipoQualifica;
    }

    public Integer getIdTipoQualifica() {
        return idTipoQualifica;
    }

    public void setIdTipoQualifica(Integer idTipoQualifica) {
        this.idTipoQualifica = idTipoQualifica;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCodQualifica() {
        return codQualifica;
    }

    public void setCodQualifica(String codQualifica) {
        this.codQualifica = codQualifica;
    }

    public String getCondizione() {
        return condizione;
    }

    public void setCondizione(String condizione) {
        this.condizione = condizione;
    }

    @XmlTransient
    public List<PraticaAnagrafica> getPraticaAnagraficaList() {
        return praticaAnagraficaList;
    }

    public void setPraticaAnagraficaList(List<PraticaAnagrafica> praticaAnagraficaList) {
        this.praticaAnagraficaList = praticaAnagraficaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoQualifica != null ? idTipoQualifica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkTipoQualifica)) {
            return false;
        }
        LkTipoQualifica other = (LkTipoQualifica) object;
        if ((this.idTipoQualifica == null && other.idTipoQualifica != null) || (this.idTipoQualifica != null && !this.idTipoQualifica.equals(other.idTipoQualifica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkTipoQualifica[ idTipoQualifica=" + idTipoQualifica + " ]";
    }
    
}
