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
@Table(name = "lk_tipo_ruolo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkTipoRuolo.findAll", query = "SELECT l FROM LkTipoRuolo l"),
    @NamedQuery(name = "LkTipoRuolo.findByIdTipoRuolo", query = "SELECT l FROM LkTipoRuolo l WHERE l.idTipoRuolo = :idTipoRuolo"),
    @NamedQuery(name = "LkTipoRuolo.findByDescrizione", query = "SELECT l FROM LkTipoRuolo l WHERE l.descrizione = :descrizione"),
    @NamedQuery(name = "LkTipoRuolo.findByCodRuolo", query = "SELECT l FROM LkTipoRuolo l WHERE l.codRuolo = :codRuolo")})
public class LkTipoRuolo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_ruolo")
    private Integer idTipoRuolo;
    @Column(name = "descrizione")
    private String descrizione;
    @Column(name = "cod_ruolo")
    private String codRuolo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lkTipoRuolo")
    private List<PraticaAnagrafica> praticaAnagraficaList;

    public LkTipoRuolo() {
    }

    public LkTipoRuolo(Integer idTipoRuolo) {
        this.idTipoRuolo = idTipoRuolo;
    }

    public Integer getIdTipoRuolo() {
        return idTipoRuolo;
    }

    public void setIdTipoRuolo(Integer idTipoRuolo) {
        this.idTipoRuolo = idTipoRuolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCodRuolo() {
        return codRuolo;
    }

    public void setCodRuolo(String codRuolo) {
        this.codRuolo = codRuolo;
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
        hash += (idTipoRuolo != null ? idTipoRuolo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkTipoRuolo)) {
            return false;
        }
        LkTipoRuolo other = (LkTipoRuolo) object;
        if ((this.idTipoRuolo == null && other.idTipoRuolo != null) || (this.idTipoRuolo != null && !this.idTipoRuolo.equals(other.idTipoRuolo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkTipoRuolo[ idTipoRuolo=" + idTipoRuolo + " ]";
    }
    
}
