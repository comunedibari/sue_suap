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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "lingue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lingue.findAll", query = "SELECT l FROM Lingue l"),
    @NamedQuery(name = "Lingue.findByIdLang", query = "SELECT l FROM Lingue l WHERE l.idLang = :idLang"),
    @NamedQuery(name = "Lingue.findByCodLang", query = "SELECT l FROM Lingue l WHERE l.codLang = :codLang")})
public class Lingue implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_lang")
    private Integer idLang;
    @Basic(optional = false)
    @Column(name = "cod_lang")
    private String codLang;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "lingue")
    private LingueTesti lingueTesti;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lingue")
    private List<ProcedimentiTesti> procedimentiTestiList;

    public Lingue() {
    }

    public Lingue(Integer idLang) {
        this.idLang = idLang;
    }

    public Lingue(Integer idLang, String codLang) {
        this.idLang = idLang;
        this.codLang = codLang;
    }

    public Integer getIdLang() {
        return idLang;
    }

    public void setIdLang(Integer idLang) {
        this.idLang = idLang;
    }

    public String getCodLang() {
        return codLang;
    }

    public void setCodLang(String codLang) {
        this.codLang = codLang;
    }

    public LingueTesti getLingueTesti() {
        return lingueTesti;
    }

    public void setLingueTesti(LingueTesti lingueTesti) {
        this.lingueTesti = lingueTesti;
    }

    @XmlTransient
    public List<ProcedimentiTesti> getProcedimentiTestiList() {
        return procedimentiTestiList;
    }

    public void setProcedimentiTestiList(List<ProcedimentiTesti> procedimentiTestiList) {
        this.procedimentiTestiList = procedimentiTestiList;
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
        if (!(object instanceof Lingue)) {
            return false;
        }
        Lingue other = (Lingue) object;
        if ((this.idLang == null && other.idLang != null) || (this.idLang != null && !this.idLang.equals(other.idLang))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.Lingue[ idLang=" + idLang + " ]";
    }
    
}
