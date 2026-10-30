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


@Entity
@Table(name = "lk_tipo_pratica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkTipoPratica.findAllSorted", query = "SELECT l FROM LkTipoPratica l ORDER BY l.descrizione ASC"),
    @NamedQuery(name = "LkTipoPratica.findAll", query = "SELECT l FROM LkTipoPratica l"),
    @NamedQuery(name = "LkTipoPratica.findByIdTipoPratica", query = "SELECT l FROM LkTipoPratica l WHERE l.idTipoPratica = :idTipoPratica"),
    @NamedQuery(name = "LkTipoPratica.findByDescrizione", query = "SELECT l FROM LkTipoPratica l WHERE l.descrizione = :descrizione"),
    @NamedQuery(name = "LkTipoPratica.findByDescrizioneCross", query = "SELECT l FROM LkTipoPratica l WHERE l.descrizioneCross = :descrizioneCross"),
    @NamedQuery(name = "LkTipoPratica.findByType", query = "SELECT l FROM LkTipoPratica l WHERE l.tipoPratica = :tipoPratica"),
    @NamedQuery(name = "LkTipoPratica.findSortedByFlags", 
				query = "SELECT l FROM LkTipoPratica l WHERE :search_all=true or l.visibilitaTutti= 'S' ORDER BY l.descrizione ASC")
})
public class LkTipoPratica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_pratica")
    private Integer idTipoPratica;
    @Column(name = "tipo_pratica")
    private String tipoPratica;
    @Column(name = "descrizione")
    private String descrizione;
    @Column(name = "descrizione_cross")
    private String descrizioneCross;
//    @OneToMany(mappedBy = "tipoPratica")
//    private List<Pratica> praticaList;
    @Column(name = "visibilita_tutti", length=1)
    private String visibilitaTutti;
    
    public LkTipoPratica() {}
    
    public Integer getIdTipoPratica() {
		return idTipoPratica;
	}

	public void setIdTipoPratica(Integer idTipoPratica) {
		this.idTipoPratica = idTipoPratica;
	}

	public String getTipoPratica() {
		return tipoPratica;
	}

	public void setTipoPratica(String tipoPratica) {
		this.tipoPratica = tipoPratica;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizioneCross() {
		return descrizioneCross;
	}

	public void setDescrizioneCross(String descrizioneCross) {
		this.descrizioneCross = descrizioneCross;
	}

	public String getVisibilitaTutti() {
		return visibilitaTutti;
	}

	public void setVisibilitaTutti(String visibilitaTutti) {
		this.visibilitaTutti = visibilitaTutti;
	}
	
//	@XmlTransient
//    public List<Pratica> getPraticaList() {
//        return praticaList;
//    }
//
//    public void setPraticaList(List<Pratica> praticaList) {
//        this.praticaList = praticaList;
//    }

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoPratica != null ? idTipoPratica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof LkTipoPratica)) {
            return false;
        }
        LkTipoPratica other = (LkTipoPratica) object;
        return !((this.idTipoPratica == null && other.idTipoPratica != null) || (this.idTipoPratica != null && !this.idTipoPratica.equals(other.idTipoPratica)));
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkTipoPratica[ id_tipo_pratica=" + idTipoPratica + " ]";
    }
}
