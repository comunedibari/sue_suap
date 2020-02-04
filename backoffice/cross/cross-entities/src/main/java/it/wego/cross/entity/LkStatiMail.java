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
@Table(name = "lk_stati_mail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkStatiMail.findAll", query = "SELECT l FROM LkStatiMail l"),
    @NamedQuery(name = "LkStatiMail.findByIdStatiMail", query = "SELECT l FROM LkStatiMail l WHERE l.idStatiMail = :idStatiMail"),
    @NamedQuery(name = "LkStatiMail.findByDescrizione", query = "SELECT l FROM LkStatiMail l WHERE l.descrizione = :descrizione"),
    @NamedQuery(name = "LkStatiMail.findByCodice", query = "SELECT l FROM LkStatiMail l WHERE l.codice = :codice")})
public class LkStatiMail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_stati_mail")
    private Integer idStatiMail;
    @Column(name = "descrizione")
    private String descrizione;
    @Column(name = "codice")
    private String codice;
    @OneToMany(mappedBy = "stato")
    private List<Email> emailList;
    @OneToMany(mappedBy = "statoEmail")
    private List<Pratica> praticaList;
    @OneToMany(mappedBy = "statoMail")
    private List<PraticheEventi> praticheEventiList;

    public LkStatiMail() {
    }

    public LkStatiMail(Integer idStatiMail) {
        this.idStatiMail = idStatiMail;
    }

    public Integer getIdStatiMail() {
        return idStatiMail;
    }

    public void setIdStatiMail(Integer idStatiMail) {
        this.idStatiMail = idStatiMail;
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

    @XmlTransient
    public List<Email> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<Email> emailList) {
        this.emailList = emailList;
    }

    @XmlTransient
    public List<Pratica> getPraticaList() {
        return praticaList;
    }

    public void setPraticaList(List<Pratica> praticaList) {
        this.praticaList = praticaList;
    }

    @XmlTransient
    public List<PraticheEventi> getPraticheEventiList() {
        return praticheEventiList;
    }

    public void setPraticheEventiList(List<PraticheEventi> praticheEventiList) {
        this.praticheEventiList = praticheEventiList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idStatiMail != null ? idStatiMail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkStatiMail)) {
            return false;
        }
        LkStatiMail other = (LkStatiMail) object;
        if ((this.idStatiMail == null && other.idStatiMail != null) || (this.idStatiMail != null && !this.idStatiMail.equals(other.idStatiMail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkStatiMail[ idStatiMail=" + idStatiMail + " ]";
    }
    
}
