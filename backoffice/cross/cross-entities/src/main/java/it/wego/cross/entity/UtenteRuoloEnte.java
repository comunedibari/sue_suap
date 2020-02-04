/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author piergiorgio
 */
@Entity
@Table(name = "utente_ruolo_ente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UtenteRuoloEnte.findAll", query = "SELECT u FROM UtenteRuoloEnte u"),
    @NamedQuery(name = "UtenteRuoloEnte.findByIdUtenteRuoloEnte", query = "SELECT u FROM UtenteRuoloEnte u WHERE u.idUtenteRuoloEnte = :idUtenteRuoloEnte")})
public class UtenteRuoloEnte implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "utenteRuoloEnte")
    private List<UtenteRuoloProcedimento> utenteRuoloProcedimentoList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_utente_ruolo_ente")
    private Integer idUtenteRuoloEnte;
    @JoinColumn(name = "cod_permesso", referencedColumnName = "cod_permesso")
    @ManyToOne(optional = false)
    private Permessi codPermesso;
    @JoinColumn(name = "id_ente", referencedColumnName = "id_ente")
    @ManyToOne(optional = false)
    private Enti idEnte;
    @JoinColumn(name = "id_utente", referencedColumnName = "id_utente")
    @ManyToOne(optional = false)
    private Utente idUtente;
    @JoinTable(name = "utente_ruolo_procedimento", joinColumns = {
        @JoinColumn(name = "id_utente_ruolo_ente", referencedColumnName = "id_utente_ruolo_ente")}, inverseJoinColumns = {
        @JoinColumn(name = "id_proc_ente", referencedColumnName = "id_proc_ente")})
    @ManyToMany
    private List<ProcedimentiEnti> procedimentiEntiList;    

    public UtenteRuoloEnte() {
    }

    public UtenteRuoloEnte(Integer idUtenteRuoloEnte) {
        this.idUtenteRuoloEnte = idUtenteRuoloEnte;
    }

    public Integer getIdUtenteRuoloEnte() {
        return idUtenteRuoloEnte;
    }

    public void setIdUtenteRuoloEnte(Integer idUtenteRuoloEnte) {
        this.idUtenteRuoloEnte = idUtenteRuoloEnte;
    }

    public Permessi getCodPermesso() {
        return codPermesso;
    }

    public void setCodPermesso(Permessi codPermesso) {
        this.codPermesso = codPermesso;
    }

    public Enti getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(Enti idEnte) {
        this.idEnte = idEnte;
    }

    public Utente getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Utente idUtente) {
        this.idUtente = idUtente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUtenteRuoloEnte != null ? idUtenteRuoloEnte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UtenteRuoloEnte)) {
            return false;
        }
        UtenteRuoloEnte other = (UtenteRuoloEnte) object;
        if ((this.idUtenteRuoloEnte == null && other.idUtenteRuoloEnte != null) || (this.idUtenteRuoloEnte != null && !this.idUtenteRuoloEnte.equals(other.idUtenteRuoloEnte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.UtenteRuoloEnte[ idUtenteRuoloEnte=" + idUtenteRuoloEnte + " ]";
    }

    @XmlTransient
    @JsonIgnore
    public List<UtenteRuoloProcedimento> getUtenteRuoloProcedimentoList() {
        return utenteRuoloProcedimentoList;
    }

    public void setUtenteRuoloProcedimentoList(List<UtenteRuoloProcedimento> utenteRuoloProcedimentoList) {
        this.utenteRuoloProcedimentoList = utenteRuoloProcedimentoList;
    }

    public List<ProcedimentiEnti> getProcedimentiEntiList() {
        return procedimentiEntiList;
    }

    public void setProcedimentiEntiList(List<ProcedimentiEnti> procedimentiEntiList) {
        this.procedimentiEntiList = procedimentiEntiList;
    }
    
}
