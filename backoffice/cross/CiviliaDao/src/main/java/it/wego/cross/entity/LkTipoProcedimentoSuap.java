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

@Entity
@Table(name = "lk_tipo_procedimento_suap")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkTipoProcedimentoSuap.findAll", query = "SELECT l FROM LkTipoProcedimentoSuap l"),
    @NamedQuery(name = "LkTipoProcedimentoSuap.findByIdTipoProcedimentoSuap", query = "SELECT l FROM LkTipoProcedimentoSuap l WHERE l.idTipoProcedimentoSuap = :idTipoProcedimentoSuap"),
    
    @NamedQuery(name = "LkTipoProcedimentoSuap.findByDescrizioneTipoProcedimentoSuap", query = "SELECT l FROM LkTipoProcedimentoSuap l WHERE l.descrizioneTipoProcedimentoSuap = :descrizioneTipoProcedimentoSuap")})
public class LkTipoProcedimentoSuap implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_procedimento_suap")
    private Integer idTipoProcedimentoSuap;
    @Column(name = "descrizione_proc")
    private String descrizioneTipoProcedimentoSuap;
    @Column(name = "descrizione_cross")
    private String descrizioneCrossSuap;
    @OneToMany(mappedBy = "idTipoProcedimentoSuap")
    private List<Pratica> praticaList;
    
    @XmlTransient
    public List<Pratica> getPraticaList() {
		return praticaList;
	}

	public void setPraticaList(List<Pratica> praticaList) {
		this.praticaList = praticaList;
	}

	public String getDescrizioneCrossSuap() {
		if(!descrizioneTipoProcedimentoSuap.equals(descrizioneCrossSuap))
			return descrizioneCrossSuap + "("+descrizioneTipoProcedimentoSuap+")";
		else return descrizioneCrossSuap;
	}

	public void setDescrizioneCrossSuap(String descrizioneCrossSuap) {
		this.descrizioneCrossSuap = descrizioneCrossSuap;
	}

	public String getDescrizioneTipoProcedimentoSuap() {
		return descrizioneTipoProcedimentoSuap;
	}

	public void setDescrizioneTipoProcedimentoSuap(String descrizioneTipoProcedimentoSuap) {
		this.descrizioneTipoProcedimentoSuap = descrizioneTipoProcedimentoSuap;
	}

	public LkTipoProcedimentoSuap() {
    }

    public LkTipoProcedimentoSuap(Integer idTipoProcedimentoSuap) {
        this.idTipoProcedimentoSuap = idTipoProcedimentoSuap;
    }

    public Integer getidTipoProcedimentoSuap() {
        return idTipoProcedimentoSuap;
    }

    public void setidTipoProcedimentoSuap(Integer idTipoProcedimentoSuap) {
        this.idTipoProcedimentoSuap = idTipoProcedimentoSuap;
    }

   
           @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkTipoProcedimentoSuap)) {
            return false;
        }
        LkTipoProcedimentoSuap other = (LkTipoProcedimentoSuap) object;
        if ((this.idTipoProcedimentoSuap == null && other.idTipoProcedimentoSuap != null) || (this.idTipoProcedimentoSuap != null && !this.idTipoProcedimentoSuap.equals(other.idTipoProcedimentoSuap))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkTipoProcedimentoSuap[ idTipoProcedimentoSuap=" + idTipoProcedimentoSuap + " ]";
    }
    
}
