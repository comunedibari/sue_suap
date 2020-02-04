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
@Table(name = "lk_forme_giuridiche")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkFormeGiuridiche.findAll", query = "SELECT l FROM LkFormeGiuridiche l"),
    @NamedQuery(name = "LkFormeGiuridiche.findByIdFormeGiuridiche", query = "SELECT l FROM LkFormeGiuridiche l WHERE l.idFormeGiuridiche = :idFormeGiuridiche"),
    @NamedQuery(name = "LkFormeGiuridiche.findByCodFormaGiuridica", query = "SELECT l FROM LkFormeGiuridiche l WHERE l.codFormaGiuridica = :codFormaGiuridica"),
    @NamedQuery(name = "LkFormeGiuridiche.findByDescrizione", query = "SELECT l FROM LkFormeGiuridiche l WHERE l.descrizione = :descrizione")})
public class LkFormeGiuridiche implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_forme_giuridiche")
    private Integer idFormeGiuridiche;
    @Basic(optional = false)
    @Column(name = "cod_forma_giuridica")
    private String codFormaGiuridica;
    @Basic(optional = false)
    @Column(name = "descrizione")
    private String descrizione;
    @OneToMany(mappedBy = "idFormaGiuridica")
    private List<Anagrafica> anagraficaList;

    public LkFormeGiuridiche() {
    }

    public LkFormeGiuridiche(Integer idFormeGiuridiche) {
        this.idFormeGiuridiche = idFormeGiuridiche;
    }

    public LkFormeGiuridiche(Integer idFormeGiuridiche, String codFormaGiuridica, String descrizione) {
        this.idFormeGiuridiche = idFormeGiuridiche;
        this.codFormaGiuridica = codFormaGiuridica;
        this.descrizione = descrizione;
    }

    public Integer getIdFormeGiuridiche() {
        return idFormeGiuridiche;
    }

    public void setIdFormeGiuridiche(Integer idFormeGiuridiche) {
        this.idFormeGiuridiche = idFormeGiuridiche;
    }

    public String getCodFormaGiuridica() {
        return codFormaGiuridica;
    }

    public void setCodFormaGiuridica(String codFormaGiuridica) {
        this.codFormaGiuridica = codFormaGiuridica;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @XmlTransient
    public List<Anagrafica> getAnagraficaList() {
        return anagraficaList;
    }

    public void setAnagraficaList(List<Anagrafica> anagraficaList) {
        this.anagraficaList = anagraficaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFormeGiuridiche != null ? idFormeGiuridiche.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkFormeGiuridiche)) {
            return false;
        }
        LkFormeGiuridiche other = (LkFormeGiuridiche) object;
        if ((this.idFormeGiuridiche == null && other.idFormeGiuridiche != null) || (this.idFormeGiuridiche != null && !this.idFormeGiuridiche.equals(other.idFormeGiuridiche))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkFormeGiuridiche[ idFormeGiuridiche=" + idFormeGiuridiche + " ]";
    }
    
}
