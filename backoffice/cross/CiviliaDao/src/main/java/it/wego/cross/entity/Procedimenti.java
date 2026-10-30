/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Nullable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "procedimenti")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Procedimenti.findAll", query = "SELECT p FROM Procedimenti p"),
    @NamedQuery(name = "Procedimenti.findByIdProc", query = "SELECT p FROM Procedimenti p WHERE p.idProc = :idProc"),
    @NamedQuery(name = "Procedimenti.findByCodProc", query = "SELECT p FROM Procedimenti p WHERE p.codProc = :codProc"),
    @NamedQuery(name = "Procedimenti.findByTermini", query = "SELECT p FROM Procedimenti p WHERE p.termini = :termini"),
    @NamedQuery(name = "Procedimenti.findByTipoProc", query = "SELECT p FROM Procedimenti p WHERE p.tipoProc = :tipoProc"),
    @NamedQuery(name = "Procedimenti.findByClassifica", query = "SELECT p FROM Procedimenti p WHERE p.classifica = :classifica"),
    @NamedQuery(name = "Procedimenti.findByPeso", query = "SELECT p FROM Procedimenti p WHERE p.peso = :peso")})
public class Procedimenti implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_proc")
    private Integer idProc;
    @Basic(optional = false)
    @Column(name = "cod_proc")
    private String codProc;
    @Column(name = "termini")
    private Integer termini;
    @Column(name = "tipo_proc")
    private String tipoProc;
    @Column(name = "classifica")
    private String classifica;
    @Column(name = "peso")
    private Integer peso;
    @OneToMany(mappedBy = "idProcedimento")
    private List<EventiTemplate> eventiTemplateList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProc")
    private List<ProcedimentiEnti> procedimentiEntiList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "procedimenti")
    private List<PraticaProcedimenti> praticaProcedimentiList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "procedimenti")
    private List<ProcedimentiTesti> procedimentiTestiList;
    @OneToMany(mappedBy = "idProcedimentoRiferimento")
    private List<ProcessiEventi> processiEventiList;
    
    public Procedimenti() {
    }

    public Procedimenti(Integer idProc) {
        this.idProc = idProc;
    }

    public Procedimenti(Integer idProc, String codProc) {
        this.idProc = idProc;
        this.codProc = codProc;
    }

    public Integer getIdProc() {
        return idProc;
    }

    public void setIdProc(Integer idProc) {
        this.idProc = idProc;
    }

    public String getCodProc() {
        return codProc;
    }

    public void setCodProc(String codProc) {
        this.codProc = codProc;
    }

    public Integer getTermini() {
        return termini;
    }

    public void setTermini(Integer termini) {
        this.termini = termini;
    }

    public String getTipoProc() {
        return tipoProc;
    }

    public void setTipoProc(String tipoProc) {
        this.tipoProc = tipoProc;
    }

    public String getClassifica() {
        return classifica;
    }

    public void setClassifica(String classifica) {
        this.classifica = classifica;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    @XmlTransient
    public List<EventiTemplate> getEventiTemplateList() {
        return eventiTemplateList;
    }

    public void setEventiTemplateList(List<EventiTemplate> eventiTemplateList) {
        this.eventiTemplateList = eventiTemplateList;
    }

    @XmlTransient
    public List<ProcedimentiEnti> getProcedimentiEntiList() {
        return procedimentiEntiList;
    }

    public void setProcedimentiEntiList(List<ProcedimentiEnti> procedimentiEntiList) {
        this.procedimentiEntiList = procedimentiEntiList;
    }

    @XmlTransient
    public List<PraticaProcedimenti> getPraticaProcedimentiList() {
        return praticaProcedimentiList;
    }

    public void setPraticaProcedimentiList(List<PraticaProcedimenti> praticaProcedimentiList) {
        this.praticaProcedimentiList = praticaProcedimentiList;
    }

    @XmlTransient
    public List<ProcedimentiTesti> getProcedimentiTestiList() {
        return procedimentiTestiList;
    }

    public void setProcedimentiTestiList(List<ProcedimentiTesti> procedimentiTestiList) {
        this.procedimentiTestiList = procedimentiTestiList;
    }

    @XmlTransient
    public List<ProcessiEventi> getProcessiEventiList() {
        return processiEventiList;
    }

    public void setProcessiEventiList(List<ProcessiEventi> processiEventiList) {
        this.processiEventiList = processiEventiList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProc != null ? idProc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Procedimenti)) {
            return false;
        }
        Procedimenti other = (Procedimenti) object;
        if ((this.idProc == null && other.idProc != null) || (this.idProc != null && !this.idProc.equals(other.idProc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.Procedimenti[ idProc=" + idProc + " ]";
    }

    public @Nullable
    String getProcedimentiTestiByLang(final String lang) {
        ProcedimentiTesti procedimentiTesti = Iterables.find(getProcedimentiTestiList(), new Predicate<ProcedimentiTesti>() {
            @Override
            public boolean apply(ProcedimentiTesti procedimentiTesti) {
                return Objects.equal(procedimentiTesti.getLingue().getCodLang(), lang);
            }
        }, null);
        return procedimentiTesti == null ? null : procedimentiTesti.getDesProc();
    }
}
