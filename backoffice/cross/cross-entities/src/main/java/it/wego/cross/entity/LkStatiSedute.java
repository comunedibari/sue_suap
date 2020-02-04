/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "lk_stati_sedute")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkStatiSedute.findAll", query = "SELECT l FROM LkStatiSedute l"),
    @NamedQuery(name = "LkStatiSedute.findByIdStatoSeduta", query = "SELECT l FROM LkStatiSedute l WHERE l.idStatoSeduta = :idStatoSeduta"),
    @NamedQuery(name = "LkStatiSedute.findByDescrizione", query = "SELECT l FROM LkStatiSedute l WHERE l.descrizione = :descrizione"),
    @NamedQuery(name = "LkStatiSedute.findByCodStatoSeduta", query = "SELECT l FROM LkStatiSedute l WHERE l.codStatoSeduta = :codStatoSeduta")})
public class LkStatiSedute implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_stato_seduta")
    private Integer idStatoSeduta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descrizione")
    private String descrizione;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "cod_stato_seduta")
    private String codStatoSeduta;
    @OneToMany(mappedBy = "idStatoSeduta")
    private List<OrganiCollegialiSedute> organiCollegialiSeduteList;

    public LkStatiSedute() {
    }

    public LkStatiSedute(Integer idStatoSeduta) {
        this.idStatoSeduta = idStatoSeduta;
    }

    public LkStatiSedute(Integer idStatoSeduta, String descrizione, String codStatoSeduta) {
        this.idStatoSeduta = idStatoSeduta;
        this.descrizione = descrizione;
        this.codStatoSeduta = codStatoSeduta;
    }

    public Integer getIdStatoSeduta() {
        return idStatoSeduta;
    }

    public void setIdStatoSeduta(Integer idStatoSeduta) {
        this.idStatoSeduta = idStatoSeduta;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCodStatoSeduta() {
        return codStatoSeduta;
    }

    public void setCodStatoSeduta(String codStatoSeduta) {
        this.codStatoSeduta = codStatoSeduta;
    }

    @XmlTransient
    @JsonIgnore
    public List<OrganiCollegialiSedute> getOrganiCollegialiSeduteList() {
        return organiCollegialiSeduteList;
    }

    public void setOrganiCollegialiSeduteList(List<OrganiCollegialiSedute> organiCollegialiSeduteList) {
        this.organiCollegialiSeduteList = organiCollegialiSeduteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idStatoSeduta != null ? idStatoSeduta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkStatiSedute)) {
            return false;
        }
        LkStatiSedute other = (LkStatiSedute) object;
        if ((this.idStatoSeduta == null && other.idStatoSeduta != null) || (this.idStatoSeduta != null && !this.idStatoSeduta.equals(other.idStatoSeduta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkStatiSedute[ idStatoSeduta=" + idStatoSeduta + " ]";
    }
    
}
