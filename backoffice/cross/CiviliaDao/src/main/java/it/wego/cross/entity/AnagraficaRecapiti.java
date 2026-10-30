/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "anagrafica_recapiti")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AnagraficaRecapiti.findAll", query = "SELECT a FROM AnagraficaRecapiti a"),
    @NamedQuery(name = "AnagraficaRecapiti.findByIdAnagraficaRecapito", query = "SELECT a FROM AnagraficaRecapiti a WHERE a.idAnagraficaRecapito = :idAnagraficaRecapito")})
public class AnagraficaRecapiti implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_anagrafica_recapito")
    private Integer idAnagraficaRecapito;
    @JoinColumn(name = "id_tipo_indirizzo", referencedColumnName = "id_tipo_indirizzo")
    @ManyToOne(optional = false)
    private LkTipoIndirizzo idTipoIndirizzo;
    @JoinColumn(name = "id_recapito", referencedColumnName = "id_recapito")
    @ManyToOne(optional = false)
    private Recapiti idRecapito;
    @JoinColumn(name = "id_anagrafica", referencedColumnName = "id_anagrafica")
    @ManyToOne(optional = false)
    private Anagrafica idAnagrafica;

    public AnagraficaRecapiti() {
    }

    public AnagraficaRecapiti(Integer idAnagraficaRecapito) {
        this.idAnagraficaRecapito = idAnagraficaRecapito;
    }

    public Integer getIdAnagraficaRecapito() {
        return idAnagraficaRecapito;
    }

    public void setIdAnagraficaRecapito(Integer idAnagraficaRecapito) {
        this.idAnagraficaRecapito = idAnagraficaRecapito;
    }

    public LkTipoIndirizzo getIdTipoIndirizzo() {
        return idTipoIndirizzo;
    }

    public void setIdTipoIndirizzo(LkTipoIndirizzo idTipoIndirizzo) {
        this.idTipoIndirizzo = idTipoIndirizzo;
    }

    public Recapiti getIdRecapito() {
        return idRecapito;
    }

    public void setIdRecapito(Recapiti idRecapito) {
        this.idRecapito = idRecapito;
    }

    public Anagrafica getIdAnagrafica() {
        return idAnagrafica;
    }

    public void setIdAnagrafica(Anagrafica idAnagrafica) {
        this.idAnagrafica = idAnagrafica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAnagraficaRecapito != null ? idAnagraficaRecapito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnagraficaRecapiti)) {
            return false;
        }
        AnagraficaRecapiti other = (AnagraficaRecapiti) object;
        if ((this.idAnagraficaRecapito == null && other.idAnagraficaRecapito != null) || (this.idAnagraficaRecapito != null && !this.idAnagraficaRecapito.equals(other.idAnagraficaRecapito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.AnagraficaRecapiti[ idAnagraficaRecapito=" + idAnagraficaRecapito + " ]";
    }
    
}
