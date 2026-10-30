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
@Table(name = "lk_stati_pratica_organi_collegiali")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkStatiPraticaOrganiCollegiali.findAll", query = "SELECT l FROM LkStatiPraticaOrganiCollegiali l"),
    @NamedQuery(name = "LkStatiPraticaOrganiCollegiali.findByIdStatoPraticaOrganiCollegiali", query = "SELECT l FROM LkStatiPraticaOrganiCollegiali l WHERE l.idStatoPraticaOrganiCollegiali = :idStatoPraticaOrganiCollegiali"),
    @NamedQuery(name = "LkStatiPraticaOrganiCollegiali.findByCodciceStatoPraticaOrganiCollegiali", query = "SELECT l FROM LkStatiPraticaOrganiCollegiali l WHERE l.codice = :codice"),
    @NamedQuery(name = "LkStatiPraticaOrganiCollegiali.findByDesStatoPraticaOrganiCollegiali", query = "SELECT l FROM LkStatiPraticaOrganiCollegiali l WHERE l.desStatoPraticaOrganiCollegiali = :desStatoPraticaOrganiCollegiali")})
public class LkStatiPraticaOrganiCollegiali implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_stato_pratica_organi_collegiali")
    private Integer idStatoPraticaOrganiCollegiali;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "des_stato_pratica_organi_collegiali")
    private String desStatoPraticaOrganiCollegiali;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idStatoPraticaOrganiCollegiali")
    private List<PraticaOrganiCollegiali> praticaOrganiCollegialiList;
    @Column(name = "codice")
    private String codice;

    public LkStatiPraticaOrganiCollegiali() {
    }

    public LkStatiPraticaOrganiCollegiali(Integer idStatoPraticaOrganiCollegiali) {
        this.idStatoPraticaOrganiCollegiali = idStatoPraticaOrganiCollegiali;
    }

    public LkStatiPraticaOrganiCollegiali(Integer idStatoPraticaOrganiCollegiali, String desStatoPraticaOrganiCollegiali) {
        this.idStatoPraticaOrganiCollegiali = idStatoPraticaOrganiCollegiali;
        this.desStatoPraticaOrganiCollegiali = desStatoPraticaOrganiCollegiali;
    }

    public Integer getIdStatoPraticaOrganiCollegiali() {
        return idStatoPraticaOrganiCollegiali;
    }

    public void setIdStatoPraticaOrganiCollegiali(Integer idStatoPraticaOrganiCollegiali) {
        this.idStatoPraticaOrganiCollegiali = idStatoPraticaOrganiCollegiali;
    }

    public String getDesStatoPraticaOrganiCollegiali() {
        return desStatoPraticaOrganiCollegiali;
    }

    public void setDesStatoPraticaOrganiCollegiali(String desStatoPraticaOrganiCollegiali) {
        this.desStatoPraticaOrganiCollegiali = desStatoPraticaOrganiCollegiali;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    @XmlTransient
    @JsonIgnore
    public List<PraticaOrganiCollegiali> getPraticaOrganiCollegialiList() {
        return praticaOrganiCollegialiList;
    }

    public void setPraticaOrganiCollegialiList(List<PraticaOrganiCollegiali> praticaOrganiCollegialiList) {
        this.praticaOrganiCollegialiList = praticaOrganiCollegialiList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idStatoPraticaOrganiCollegiali != null ? idStatoPraticaOrganiCollegiali.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkStatiPraticaOrganiCollegiali)) {
            return false;
        }
        LkStatiPraticaOrganiCollegiali other = (LkStatiPraticaOrganiCollegiali) object;
        if ((this.idStatoPraticaOrganiCollegiali == null && other.idStatoPraticaOrganiCollegiali != null) || (this.idStatoPraticaOrganiCollegiali != null && !this.idStatoPraticaOrganiCollegiali.equals(other.idStatoPraticaOrganiCollegiali))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkStatiPraticaOrganiCollegiali[ idStatoPraticaOrganiCollegiali=" + idStatoPraticaOrganiCollegiali + " ]";
    }

}
