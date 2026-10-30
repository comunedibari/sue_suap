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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giuseppe
 */
@Entity
@Table(name = "lk_provincie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LkProvincie.findAll", query = "SELECT l FROM LkProvincie l"),
    @NamedQuery(name = "LkProvincie.findByIdProvincie", query = "SELECT l FROM LkProvincie l WHERE l.idProvincie = :idProvincie"),
    @NamedQuery(name = "LkProvincie.findByDescrizione", query = "SELECT l FROM LkProvincie l WHERE l.descrizione = :descrizione"),
    @NamedQuery(name = "LkProvincie.findByCodCatastale", query = "SELECT l FROM LkProvincie l WHERE l.codCatastale = :codCatastale"),
    @NamedQuery(name = "LkProvincie.findByDataFineValidita", query = "SELECT l FROM LkProvincie l WHERE l.dataFineValidita = :dataFineValidita"),
    @NamedQuery(name = "LkProvincie.findByFlgInfocamere", query = "SELECT l FROM LkProvincie l WHERE l.flgInfocamere = :flgInfocamere")})
public class LkProvincie implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "flg_infocamere")
    private Character flgInfocamere;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_provincie")
    private Integer idProvincie;
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
    @OneToMany(mappedBy = "idProvinciaIscrizione")
    private List<Anagrafica> anagraficaList;
    @OneToMany(mappedBy = "idProvinciaCciaa")
    private List<Anagrafica> anagraficaList1;
    @OneToMany(mappedBy = "idProvincia")
    private List<LkComuni> lkComuniList;

    public LkProvincie() {
    }

    public LkProvincie(Integer idProvincie) {
        this.idProvincie = idProvincie;
    }

    public LkProvincie(Integer idProvincie, String descrizione, String codCatastale, Date dataFineValidita, char flgInfocamere) {
        this.idProvincie = idProvincie;
        this.descrizione = descrizione;
        this.codCatastale = codCatastale;
        this.dataFineValidita = dataFineValidita;
        this.flgInfocamere = flgInfocamere;
    }

    public Integer getIdProvincie() {
        return idProvincie;
    }

    public void setIdProvincie(Integer idProvincie) {
        this.idProvincie = idProvincie;
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
    public List<Anagrafica> getAnagraficaList() {
        return anagraficaList;
    }

    public void setAnagraficaList(List<Anagrafica> anagraficaList) {
        this.anagraficaList = anagraficaList;
    }

    @XmlTransient
    public List<Anagrafica> getAnagraficaList1() {
        return anagraficaList1;
    }

    public void setAnagraficaList1(List<Anagrafica> anagraficaList1) {
        this.anagraficaList1 = anagraficaList1;
    }

    @XmlTransient
    public List<LkComuni> getLkComuniList() {
        return lkComuniList;
    }

    public void setLkComuniList(List<LkComuni> lkComuniList) {
        this.lkComuniList = lkComuniList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProvincie != null ? idProvincie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LkProvincie)) {
            return false;
        }
        LkProvincie other = (LkProvincie) object;
        if ((this.idProvincie == null && other.idProvincie != null) || (this.idProvincie != null && !this.idProvincie.equals(other.idProvincie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.LkProvincie[ idProvincie=" + idProvincie + " ]";
    }

    public Character getFlgInfocamere() {
        return flgInfocamere;
    }

    public void setFlgInfocamere(Character flgInfocamere) {
        this.flgInfocamere = flgInfocamere;
    }
    
}
