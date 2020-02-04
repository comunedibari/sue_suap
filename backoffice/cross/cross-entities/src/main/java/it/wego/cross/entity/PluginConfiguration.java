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
 * @author gabriele
 */
@Entity
@Table(name = "plugin_configuration")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PluginConfiguration.findAll", query = "SELECT c FROM PluginConfiguration c"),
    @NamedQuery(name = "PluginConfiguration.findById", query = "SELECT c FROM PluginConfiguration c WHERE c.id = :id"),
    @NamedQuery(name = "PluginConfiguration.findByName", query = "SELECT c FROM PluginConfiguration c WHERE c.name = :name"),
    @NamedQuery(name = "PluginConfiguration.findByValue", query = "SELECT c FROM PluginConfiguration c WHERE c.value = :value"),
    @NamedQuery(name = "PluginConfiguration.findByNote", query = "SELECT c FROM PluginConfiguration c WHERE c.note = :note")})
public class PluginConfiguration implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "value")
    private String value;
    @Column(name = "note")
    private String note;
    @JoinColumn(name = "id_comune", referencedColumnName = "id_comune")
    @ManyToOne
    private LkComuni idComune;
    @JoinColumn(name = "id_ente", referencedColumnName = "id_ente")
    @ManyToOne
    private Enti idEnte;

    public PluginConfiguration() {
    }

    public PluginConfiguration(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LkComuni getIdComune() {
        return idComune;
    }

    public void setIdComune(LkComuni idComune) {
        this.idComune = idComune;
    }

    public Enti getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(Enti idEnte) {
        this.idEnte = idEnte;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PluginConfiguration)) {
            return false;
        }
        PluginConfiguration other = (PluginConfiguration) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.PluginConfiguration[ id=" + id + " ]";
    }
    
}
