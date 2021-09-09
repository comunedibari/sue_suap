/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import com.google.common.collect.Iterables;
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
 * @author giuseppe
 */
@Entity
@Table(name = "procedimenti_enti")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProcedimentiEnti.findAll", query = "SELECT p FROM ProcedimentiEnti p"),
    @NamedQuery(name = "ProcedimentiEnti.findByIdProcEnte", query = "SELECT p FROM ProcedimentiEnti p WHERE p.idProcEnte = :idProcEnte")})
public class ProcedimentiEnti implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "procedimentiEnti")
    private List<UtenteRuoloProcedimento> utenteRuoloProcedimentoList;
    @ManyToMany(mappedBy = "procedimentiEntiList")
    private List<UtenteRuoloEnte> utenteRuoloEnteList;
    @JoinTable(name = "ugm_ugd_proc", joinColumns = {
        @JoinColumn(name = "id_ugd_proc", referencedColumnName = "id_proc_ente")}, inverseJoinColumns = {
        @JoinColumn(name = "id_ugm", referencedColumnName = "id_ente")})
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_proc_ente")
    private Integer idProcEnte;
    @Column(name = "responsabile_procedimento")
    private String responsabileProcedimento;
    @JoinColumn(name = "id_processo", referencedColumnName = "id_processo")
    @ManyToOne
    private Processi idProcesso;
    @JoinColumn(name = "id_proc", referencedColumnName = "id_proc")
    @ManyToOne(optional = false)
    private Procedimenti idProc;
    @JoinColumn(name = "id_ente", referencedColumnName = "id_ente")
    @ManyToOne(optional = false)
    private Enti idEnte;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProcEnte")
    private List<Pratica> praticaList;
    @ManyToMany(mappedBy = "endoProcedimentiList")
    private List<Enti> entiList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "procedimentiEnti")
    private List<PraticaEventiProcedimenti> praticaEventiProcedimentiList;
    @JoinColumn(name = "id_ufficio", referencedColumnName = "id_ente")
    @ManyToOne(optional = false)
    private Enti idUfficio;
    
    public ProcedimentiEnti() {
    }

    public ProcedimentiEnti(Integer idProcEnte) {
        this.idProcEnte = idProcEnte;
    }

    public Integer getIdProcEnte() {
        return idProcEnte;
    }

    public void setIdProcEnte(Integer idProcEnte) {
        this.idProcEnte = idProcEnte;
    }

    public Processi getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Processi idProcesso) {
        this.idProcesso = idProcesso;
    }

    public Procedimenti getIdProc() {
        return idProc;
    }

    public void setIdProc(Procedimenti idProc) {
        this.idProc = idProc;
    }

    public Enti getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(Enti idEnte) {
        this.idEnte = idEnte;
    }
    
    public Enti getIdUfficio() {
        return idUfficio;
    }

    public void setIdUfficio(Enti idUfficio) {
        this.idUfficio = idUfficio;
    }


    @XmlTransient
    public List<Pratica> getPraticaList() {
        return praticaList;
    }

    public void setPraticaList(List<Pratica> praticaList) {
        this.praticaList = praticaList;
    }

    @XmlTransient
    public List<Enti> getEntiList() {
        return entiList;
    }

    public void setEntiList(List<Enti> entiList) {
        this.entiList = entiList;
    }

    @XmlTransient
    public List<PraticaEventiProcedimenti> getPraticaEventiProcedimentiList() {
        return praticaEventiProcedimentiList;
    }

    public void setPraticaEventiProcedimentiList(List<PraticaEventiProcedimenti> praticaEventiProcedimentiList) {
        this.praticaEventiProcedimentiList = praticaEventiProcedimentiList;
    }

    public String getResponsabileProcedimento() {
        return responsabileProcedimento;
    }

    public void setResponsabileProcedimento(String responsabileProcedimento) {
        this.responsabileProcedimento = responsabileProcedimento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProcEnte != null ? idProcEnte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProcedimentiEnti)) {
            return false;
        }
        ProcedimentiEnti other = (ProcedimentiEnti) object;
        if ((this.idProcEnte == null && other.idProcEnte != null) || (this.idProcEnte != null && !this.idProcEnte.equals(other.idProcEnte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.ProcedimentiEnti[ idProcEnte=" + idProcEnte + " ]";
    }

    @XmlTransient
    @JsonIgnore
    public List<UtenteRuoloEnte> getUtenteRuoloEnteList() {
        return utenteRuoloEnteList;
    }

    public void setUtenteRuoloEnteList(List<UtenteRuoloEnte> utenteRuoloEnteList) {
        this.utenteRuoloEnteList = utenteRuoloEnteList;
    }

    @XmlTransient
    @JsonIgnore
    public List<UtenteRuoloProcedimento> getUtenteRuoloProcedimentoList() {
        return utenteRuoloProcedimentoList;
    }

    public void setUtenteRuoloProcedimentoList(List<UtenteRuoloProcedimento> utenteRuoloProcedimentoList) {
        this.utenteRuoloProcedimentoList = utenteRuoloProcedimentoList;
    }

}
