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
@Table(name = "lk_tipo_intervento_suap")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkTipoInterventoSuap.findAll", query = "SELECT l FROM LkTipoInterventoSuap l"),
    @NamedQuery(name = "LkTipoInterventoSuap.findByIdTipoInterventoSuap", query = "SELECT l FROM LkTipoInterventoSuap l WHERE l.idTipoInterventoSuap = :idTipoInterventoSuap"),
    
    @NamedQuery(name = "LkTipoInterventoSuap.findByDescrizioneTipoInterventoSuap", query = "SELECT l FROM LkTipoInterventoSuap l WHERE l.descrizioneTipoInterventoSuap = :descrizioneTipoInterventoSuap")})
public class LkTipoInterventoSuap implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_intervento_suap")
    private Integer idTipoInterventoSuap;
   
    @Column(name = "descrizione_tipo_intervento")
    private String descrizioneTipoInterventoSuap;
    @OneToMany(mappedBy = "idTipoInterventoSuap")
    private List<Pratica> praticaList;
    @XmlTransient
    public List<Pratica> getPraticaList() {
		return praticaList;
	}

	public void setPraticaList(List<Pratica> praticaList) {
		this.praticaList = praticaList;
	}
    
    public String getDescrizioneTipoInterventoSuap() {
		return descrizioneTipoInterventoSuap;
	}

	public void setDescrizioneTipoInterventoSuap(String descrizioneTipoInterventoSuap) {
		this.descrizioneTipoInterventoSuap = descrizioneTipoInterventoSuap;
	}

	public LkTipoInterventoSuap() {
    }

    public LkTipoInterventoSuap(Integer idTipoInterventoSuap) {
        this.idTipoInterventoSuap = idTipoInterventoSuap;
    }

    public Integer getidTipoInterventoSuap() {
        return idTipoInterventoSuap;
    }

    public void setidTipoInterventoSuap(Integer idTipoInterventoSuap) {
        this.idTipoInterventoSuap = idTipoInterventoSuap;
    }

   
           @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkTipoInterventoSuap)) {
            return false;
        }
        LkTipoInterventoSuap other = (LkTipoInterventoSuap) object;
        if ((this.idTipoInterventoSuap == null && other.idTipoInterventoSuap != null) || (this.idTipoInterventoSuap != null && !this.idTipoInterventoSuap.equals(other.idTipoInterventoSuap))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkTipoInterventoSuap[ idTipoInterventoSuap=" + idTipoInterventoSuap + " ]";
    }
    
}
