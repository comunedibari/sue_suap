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
@Table(name = "dati_catastali")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DatiCatastali.findAll", query = "SELECT d FROM DatiCatastali d"),
    @NamedQuery(name = "DatiCatastali.findByIdImmobile", query = "SELECT d FROM DatiCatastali d WHERE d.idImmobile = :idImmobile"),
    @NamedQuery(name = "DatiCatastali.findBySezione", query = "SELECT d FROM DatiCatastali d WHERE d.sezione = :sezione"),
    @NamedQuery(name = "DatiCatastali.findByFoglio", query = "SELECT d FROM DatiCatastali d WHERE d.foglio = :foglio"),
    @NamedQuery(name = "DatiCatastali.findByMappale", query = "SELECT d FROM DatiCatastali d WHERE d.mappale = :mappale"),
    @NamedQuery(name = "DatiCatastali.findByEstensioneParticella", query = "SELECT d FROM DatiCatastali d WHERE d.estensioneParticella = :estensioneParticella"),
    @NamedQuery(name = "DatiCatastali.findBySubalterno", query = "SELECT d FROM DatiCatastali d WHERE d.subalterno = :subalterno"),
    @NamedQuery(name = "DatiCatastali.findByCodImmobile", query = "SELECT d FROM DatiCatastali d WHERE d.codImmobile = :codImmobile")})
public class DatiCatastali implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_immobile")
    private Integer idImmobile;
    @Column(name = "sezione")
    private String sezione;
    @Column(name = "foglio")
    private String foglio;
    @Column(name = "mappale")
    private String mappale;
    @Column(name = "estensione_particella")
    private String estensioneParticella;
    @Column(name = "subalterno")
    private String subalterno;
    @Column(name = "cod_immobile")
    private String codImmobile;    
    @Column(name = "comune_censuario")
    private String comuneCensuario;
    @JoinColumn(name = "id_tipo_particella", referencedColumnName = "id_tipo_particella")
    @ManyToOne
    private LkTipoParticella idTipoParticella;
    @JoinColumn(name = "id_tipo_unita", referencedColumnName = "id_tipo_unita")
    @ManyToOne
    private LkTipoUnita idTipoUnita;
    @JoinColumn(name = "id_tipo_sistema_catastale", referencedColumnName = "id_tipo_sistema_catastale")
    @ManyToOne(optional = false)
    private LkTipoSistemaCatastale idTipoSistemaCatastale;
    @JoinColumn(name = "id_pratica", referencedColumnName = "id_pratica")
    @ManyToOne(optional = false)
    private Pratica idPratica;
    
    //Aggiunto il 22/06/2016
    @Column(name = "categoria")
    private String categoria;

    public DatiCatastali() {
    }

    public DatiCatastali(Integer idImmobile) {
        this.idImmobile = idImmobile;
    }

    public Integer getIdImmobile() {
        return idImmobile;
    }

    public void setIdImmobile(Integer idImmobile) {
        this.idImmobile = idImmobile;
    }

    public String getSezione() {
        return sezione;
    }

    public void setSezione(String sezione) {
        this.sezione = sezione;
    }

    public String getFoglio() {
        return foglio;
    }

    public void setFoglio(String foglio) {
        this.foglio = foglio;
    }

    public String getMappale() {
        return mappale;
    }

    public void setMappale(String mappale) {
        this.mappale = mappale;
    }

    public String getEstensioneParticella() {
        return estensioneParticella;
    }

    public void setEstensioneParticella(String estensioneParticella) {
        this.estensioneParticella = estensioneParticella;
    }

    public String getSubalterno() {
        return subalterno;
    }

    public void setSubalterno(String subalterno) {
        this.subalterno = subalterno;
    }

    public LkTipoParticella getIdTipoParticella() {
        return idTipoParticella;
    }

    public void setIdTipoParticella(LkTipoParticella idTipoParticella) {
        this.idTipoParticella = idTipoParticella;
    }

    public LkTipoUnita getIdTipoUnita() {
        return idTipoUnita;
    }

    public void setIdTipoUnita(LkTipoUnita idTipoUnita) {
        this.idTipoUnita = idTipoUnita;
    }

    public LkTipoSistemaCatastale getIdTipoSistemaCatastale() {
        return idTipoSistemaCatastale;
    }

    public void setIdTipoSistemaCatastale(LkTipoSistemaCatastale idTipoSistemaCatastale) {
        this.idTipoSistemaCatastale = idTipoSistemaCatastale;
    }

    public Pratica getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Pratica idPratica) {
        this.idPratica = idPratica;
    }

    public String getComuneCensuario() {
        return comuneCensuario;
    }

    public void setComuneCensuario(String comuneCensuario) {
        this.comuneCensuario = comuneCensuario;
    }

    public String getCodImmobile() {
        return codImmobile;
    }

    public void setCodImmobile(String codImmobile) {
        this.codImmobile = codImmobile;
    }
       
    public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idImmobile != null ? idImmobile.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DatiCatastali)) {
            return false;
        }
        DatiCatastali other = (DatiCatastali) object;
        if ((this.idImmobile == null && other.idImmobile != null) || (this.idImmobile != null && !this.idImmobile.equals(other.idImmobile))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.DatiCatastali[ idImmobile=" + idImmobile + " ]";
    }
}
