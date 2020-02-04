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
@Table(name = "lk_cittadinanza")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkCittadinanza.findAll", query = "SELECT l FROM LkCittadinanza l"),
    @NamedQuery(name = "LkCittadinanza.findByIdCittadinanza", query = "SELECT l FROM LkCittadinanza l WHERE l.idCittadinanza = :idCittadinanza"),
    @NamedQuery(name = "LkCittadinanza.findByDescrizione", query = "SELECT l FROM LkCittadinanza l WHERE l.descrizione = :descrizione"),
    @NamedQuery(name = "LkCittadinanza.findByCodCittadinanza", query = "SELECT l FROM LkCittadinanza l WHERE l.codCittadinanza = :codCittadinanza")})
public class LkCittadinanza implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cittadinanza")
    private Integer idCittadinanza;
    @Basic(optional = false)
    @Column(name = "descrizione")
    private String descrizione;
    @Basic(optional = false)
    @Column(name = "cod_cittadinanza")
    private String codCittadinanza;
    @OneToMany(mappedBy = "idCittadinanza")
    private List<Anagrafica> anagraficaList;

    public LkCittadinanza() {
    }

    public LkCittadinanza(Integer idCittadinanza) {
        this.idCittadinanza = idCittadinanza;
    }

    public LkCittadinanza(Integer idCittadinanza, String descrizione, String codCittadinanza) {
        this.idCittadinanza = idCittadinanza;
        this.descrizione = descrizione;
        this.codCittadinanza = codCittadinanza;
    }

    public Integer getIdCittadinanza() {
        return idCittadinanza;
    }

    public void setIdCittadinanza(Integer idCittadinanza) {
        this.idCittadinanza = idCittadinanza;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCodCittadinanza() {
        return codCittadinanza;
    }

    public void setCodCittadinanza(String codCittadinanza) {
        this.codCittadinanza = codCittadinanza;
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
        hash += (idCittadinanza != null ? idCittadinanza.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkCittadinanza)) {
            return false;
        }
        LkCittadinanza other = (LkCittadinanza) object;
        if ((this.idCittadinanza == null && other.idCittadinanza != null) || (this.idCittadinanza != null && !this.idCittadinanza.equals(other.idCittadinanza))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkCittadinanza[ idCittadinanza=" + idCittadinanza + " ]";
    }
    
}
