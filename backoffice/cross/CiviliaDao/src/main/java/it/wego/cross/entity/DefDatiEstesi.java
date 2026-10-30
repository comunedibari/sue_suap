/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author piergiorgio
 */
@Entity
@Table(name = "def_dati_estesi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DefDatiEstesi.findAll", query = "SELECT d FROM DefDatiEstesi d"),
    @NamedQuery(name = "DefDatiEstesi.findByIdDatiEstesi", query = "SELECT d FROM DefDatiEstesi d WHERE d.idDatiEstesi = :idDatiEstesi"),
    @NamedQuery(name = "DefDatiEstesi.findByIdIstanza", query = "SELECT d FROM DefDatiEstesi d WHERE d.idIstanza = :idIstanza"),
    @NamedQuery(name = "DefDatiEstesi.findByCodValue", query = "SELECT d FROM DefDatiEstesi d WHERE d.codValue = :codValue"),
    @NamedQuery(name = "DefDatiEstesi.findByValue", query = "SELECT d FROM DefDatiEstesi d WHERE d.value = :value")})
public class DefDatiEstesi implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_dati_estesi")
    private Integer idDatiEstesi;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "id_istanza")
    private String idIstanza;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "cod_value")
    private String codValue;
    @Size(max = 255)
    @Column(name = "value")
    private String value;
    @JoinColumn(name = "id_tipo_oggetto", referencedColumnName = "id_tipo_oggetto")
    @ManyToOne(optional = false)
    private LkTipoOggetto idTipoOggetto;

    public DefDatiEstesi() {
    }

    public DefDatiEstesi(Integer idDatiEstesi) {
        this.idDatiEstesi = idDatiEstesi;
    }

    public DefDatiEstesi(Integer idDatiEstesi, String idIstanza, String codValue) {
        this.idDatiEstesi = idDatiEstesi;
        this.idIstanza = idIstanza;
        this.codValue = codValue;
    }

    public Integer getIdDatiEstesi() {
        return idDatiEstesi;
    }

    public void setIdDatiEstesi(Integer idDatiEstesi) {
        this.idDatiEstesi = idDatiEstesi;
    }

    public String getIdIstanza() {
        return idIstanza;
    }

    public void setIdIstanza(String idIstanza) {
        this.idIstanza = idIstanza;
    }

    public String getCodValue() {
        return codValue;
    }

    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LkTipoOggetto getIdTipoOggetto() {
        return idTipoOggetto;
    }

    public void setIdTipoOggetto(LkTipoOggetto idTipoOggetto) {
        this.idTipoOggetto = idTipoOggetto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDatiEstesi != null ? idDatiEstesi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DefDatiEstesi)) {
            return false;
        }
        DefDatiEstesi other = (DefDatiEstesi) object;
        if ((this.idDatiEstesi == null && other.idDatiEstesi != null) || (this.idDatiEstesi != null && !this.idDatiEstesi.equals(other.idDatiEstesi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.DefDatiEstesi[ idDatiEstesi=" + idDatiEstesi + " ]";
    }
    
}
