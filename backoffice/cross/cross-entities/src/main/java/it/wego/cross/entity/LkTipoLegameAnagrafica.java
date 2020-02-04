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
@Table(name = "lk_tipo_legame_anagrafica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkTipoLegameAnagrafica.findAll", query = "SELECT l FROM LkTipoLegameAnagrafica l"),
    @NamedQuery(name = "LkTipoLegameAnagrafica.findById", query = "SELECT l FROM LkTipoLegameAnagrafica l WHERE l.id = :id"),
    @NamedQuery(name = "LkTipoLegameAnagrafica.findByDescrizione", query = "SELECT l FROM LkTipoLegameAnagrafica l WHERE l.descrizione = :descrizione")})
public class LkTipoLegameAnagrafica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "descrizione")
    private String descrizione;
    @OneToMany(mappedBy = "idTipoRelazione")
    private List<RelazioniAnagrafiche> relazioniAnagraficheList;

    public LkTipoLegameAnagrafica() {
    }

    public LkTipoLegameAnagrafica(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @XmlTransient
    public List<RelazioniAnagrafiche> getRelazioniAnagraficheList() {
        return relazioniAnagraficheList;
    }

    public void setRelazioniAnagraficheList(List<RelazioniAnagrafiche> relazioniAnagraficheList) {
        this.relazioniAnagraficheList = relazioniAnagraficheList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkTipoLegameAnagrafica)) {
            return false;
        }
        LkTipoLegameAnagrafica other = (LkTipoLegameAnagrafica) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkTipoLegameAnagrafica[ id=" + id + " ]";
    }
    
}
