/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "lingue_testi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LingueTesti.findAll", query = "SELECT l FROM LingueTesti l"),
    @NamedQuery(name = "LingueTesti.findByIdLang", query = "SELECT l FROM LingueTesti l WHERE l.idLang = :idLang"),
    @NamedQuery(name = "LingueTesti.findByDesLang", query = "SELECT l FROM LingueTesti l WHERE l.desLang = :desLang")})
public class LingueTesti implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_lang")
    private Integer idLang;
    @Basic(optional = false)
    @Column(name = "des_lang")
    private String desLang;
    @JoinColumn(name = "id_lang", referencedColumnName = "id_lang", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Lingue lingue;

    public LingueTesti() {
    }

    public LingueTesti(Integer idLang) {
        this.idLang = idLang;
    }

    public LingueTesti(Integer idLang, String desLang) {
        this.idLang = idLang;
        this.desLang = desLang;
    }

    public Integer getIdLang() {
        return idLang;
    }

    public void setIdLang(Integer idLang) {
        this.idLang = idLang;
    }

    public String getDesLang() {
        return desLang;
    }

    public void setDesLang(String desLang) {
        this.desLang = desLang;
    }

    public Lingue getLingue() {
        return lingue;
    }

    public void setLingue(Lingue lingue) {
        this.lingue = lingue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLang != null ? idLang.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LingueTesti)) {
            return false;
        }
        LingueTesti other = (LingueTesti) object;
        if ((this.idLang == null && other.idLang != null) || (this.idLang != null && !this.idLang.equals(other.idLang))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LingueTesti[ idLang=" + idLang + " ]";
    }
    
}
