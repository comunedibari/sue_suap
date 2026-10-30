/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "lk_comuni")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkComuni.findAll", query = "SELECT l FROM LkComuni l"),
    @NamedQuery(name = "LkComuni.findByIdComune", query = "SELECT l FROM LkComuni l WHERE l.idComune = :idComune"),
    @NamedQuery(name = "LkComuni.findByDescrizione", query = "SELECT l FROM LkComuni l WHERE l.descrizione = :descrizione"),
    @NamedQuery(name = "LkComuni.findByCodCatastale", query = "SELECT l FROM LkComuni l WHERE l.codCatastale = :codCatastale"),
    @NamedQuery(name = "LkComuni.findByDataFineValidita", query = "SELECT l FROM LkComuni l WHERE l.dataFineValidita = :dataFineValidita")})
public class LkComuni implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_comune")
    private Integer idComune;
    @Basic(optional = false)
    @Column(name = "descrizione")
    private String descrizione;
    @Basic(optional = false)
    @Column(name = "cod_catastale")
    private String codCatastale;
    @Basic(optional = false)
    @Column(name = "data_fine_validita")
    @Temporal(TemporalType.DATE)
    private Date dataFineValidita;
    @Basic(optional = false)
    @Column(name = "flg_tavolare")
    private String flgTavolare;    
    @ManyToMany(mappedBy = "lkComuniList")
    private List<Enti> entiList;
    @OneToMany(mappedBy = "idComuneNascita")
    private List<Anagrafica> anagraficaList;
    @OneToMany(mappedBy = "idComune")
    private List<Pratica> praticaList;
    @JoinColumn(name = "id_stato", referencedColumnName = "id_stato")
    @ManyToOne
    private LkStati idStato;
    @JoinColumn(name = "id_provincia", referencedColumnName = "id_provincie")
    @ManyToOne
    private LkProvincie idProvincia;
    @OneToMany(mappedBy = "idComune")
    private List<Configuration> configurationList;
    @OneToMany(mappedBy = "idComune")
    private List<Recapiti> recapitiList;
    public LkComuni() {
    }

    public LkComuni(Integer idComune) {
        this.idComune = idComune;
    }

    public LkComuni(Integer idComune, String descrizione, String codCatastale, Date dataFineValidita) {
        this.idComune = idComune;
        this.descrizione = descrizione;
        this.codCatastale = codCatastale;
        this.dataFineValidita = dataFineValidita;
    }

    public Integer getIdComune() {
        return idComune;
    }

    public void setIdComune(Integer idComune) {
        this.idComune = idComune;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCodCatastale() {
        return codCatastale;
    }

    public void setCodCatastale(String codCatastale) {
        this.codCatastale = codCatastale;
    }

    public Date getDataFineValidita() {
        return dataFineValidita;
    }

    public void setDataFineValidita(Date dataFineValidita) {
        this.dataFineValidita = dataFineValidita;
    }

    @XmlTransient
    public List<Enti> getEntiList() {
        return entiList;
    }

    public void setEntiList(List<Enti> entiList) {
        this.entiList = entiList;
    }

    @XmlTransient
    public List<Anagrafica> getAnagraficaList() {
        return anagraficaList;
    }

    public void setAnagraficaList(List<Anagrafica> anagraficaList) {
        this.anagraficaList = anagraficaList;
    }

    @XmlTransient
    public List<Pratica> getPraticaList() {
        return praticaList;
    }

    public void setPraticaList(List<Pratica> praticaList) {
        this.praticaList = praticaList;
    }

    public LkStati getIdStato() {
        return idStato;
    }

    public void setIdStato(LkStati idStato) {
        this.idStato = idStato;
    }

    public LkProvincie getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(LkProvincie idProvincia) {
        this.idProvincia = idProvincia;
    }

    @XmlTransient
    public List<Configuration> getConfigurationList() {
        return configurationList;
    }

    public void setConfigurationList(List<Configuration> configurationList) {
        this.configurationList = configurationList;
    }

    @XmlTransient
    public List<Recapiti> getRecapitiList() {
        return recapitiList;
    }

    public void setRecapitiList(List<Recapiti> recapitiList) {
        this.recapitiList = recapitiList;
    }

    public String getFlgTavolare() {
        return flgTavolare;
    }

    public void setFlgTavolare(String flgTavolare) {
        this.flgTavolare = flgTavolare;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComune != null ? idComune.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkComuni)) {
            return false;
        }
        LkComuni other = (LkComuni) object;
        if ((this.idComune == null && other.idComune != null) || (this.idComune != null && !this.idComune.equals(other.idComune))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkComuni[ idComune=" + idComune + " ]";
    }
   
}
