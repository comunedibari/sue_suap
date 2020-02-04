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
@Table(name = "lk_stato_pratica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkStatoPratica.findAll", query = "SELECT l FROM LkStatoPratica l"),
    @NamedQuery(name = "LkStatoPratica.findByIdStatoPratica", query = "SELECT l FROM LkStatoPratica l WHERE l.idStatoPratica = :idStatoPratica"),
    @NamedQuery(name = "LkStatoPratica.findByDescrizione", query = "SELECT l FROM LkStatoPratica l WHERE l.descrizione = :descrizione"),
    @NamedQuery(name = "LkStatoPratica.findByCodice", query = "SELECT l FROM LkStatoPratica l WHERE l.codice = :codice"),
    @NamedQuery(name = "LkStatoPratica.findByGrpStatoPratica", query = "SELECT l FROM LkStatoPratica l WHERE l.grpStatoPratica = :grpStatoPratica")})
public class LkStatoPratica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_stato_pratica")
    private Integer idStatoPratica;
    @Basic(optional = false)
    @Column(name = "descrizione")
    private String descrizione;
    @Column(name = "codice")
    private String codice;
    @Column(name = "grp_stato_pratica")
    private String grpStatoPratica;
    @OneToMany(mappedBy = "idStatoPratica")
    private List<Pratica> praticaList;
    @OneToMany(mappedBy = "statoPost")
    private List<ProcessiEventi> processiEventiList;

    public LkStatoPratica() {
    }

    public LkStatoPratica(Integer idStatoPratica) {
        this.idStatoPratica = idStatoPratica;
    }

    public LkStatoPratica(Integer idStatoPratica, String descrizione) {
        this.idStatoPratica = idStatoPratica;
        this.descrizione = descrizione;
    }

    public Integer getIdStatoPratica() {
        return idStatoPratica;
    }

    public void setIdStatoPratica(Integer idStatoPratica) {
        this.idStatoPratica = idStatoPratica;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getGrpStatoPratica() {
        return grpStatoPratica;
    }

    public void setGrpStatoPratica(String grpStatoPratica) {
        this.grpStatoPratica = grpStatoPratica;
    }

    @XmlTransient
    public List<Pratica> getPraticaList() {
        return praticaList;
    }

    public void setPraticaList(List<Pratica> praticaList) {
        this.praticaList = praticaList;
    }

    @XmlTransient
    public List<ProcessiEventi> getProcessiEventiList() {
        return processiEventiList;
    }

    public void setProcessiEventiList(List<ProcessiEventi> processiEventiList) {
        this.processiEventiList = processiEventiList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idStatoPratica != null ? idStatoPratica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkStatoPratica)) {
            return false;
        }
        LkStatoPratica other = (LkStatoPratica) object;
        if ((this.idStatoPratica == null && other.idStatoPratica != null) || (this.idStatoPratica != null && !this.idStatoPratica.equals(other.idStatoPratica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkStatoPratica[ idStatoPratica=" + idStatoPratica + " ]";
    }

}
