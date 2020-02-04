/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "procedimenti_testi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProcedimentiTesti.findAll", query = "SELECT p FROM ProcedimentiTesti p"),
    @NamedQuery(name = "ProcedimentiTesti.findByIdProc", query = "SELECT p FROM ProcedimentiTesti p WHERE p.procedimentiTestiPK.idProc = :idProc"),
    @NamedQuery(name = "ProcedimentiTesti.findByIdLang", query = "SELECT p FROM ProcedimentiTesti p WHERE p.procedimentiTestiPK.idLang = :idLang"),
    @NamedQuery(name = "ProcedimentiTesti.findByIdLangAndIdProc", query = "SELECT p FROM ProcedimentiTesti p WHERE p.procedimentiTestiPK.idLang = :idLang AND p.procedimentiTestiPK.idProc = :idProc"),
    @NamedQuery(name = "ProcedimentiTesti.findByDesProc", query = "SELECT p FROM ProcedimentiTesti p WHERE p.desProc = :desProc")})
public class ProcedimentiTesti implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProcedimentiTestiPK procedimentiTestiPK;
    @Basic(optional = false)
    @Column(name = "des_proc")
    private String desProc;
    @JoinColumn(name = "id_lang", referencedColumnName = "id_lang", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Lingue lingue;
    @JoinColumn(name = "id_proc", referencedColumnName = "id_proc", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Procedimenti procedimenti;

    public ProcedimentiTesti() {
    }

    public ProcedimentiTesti(ProcedimentiTestiPK procedimentiTestiPK) {
        this.procedimentiTestiPK = procedimentiTestiPK;
    }

    public ProcedimentiTesti(ProcedimentiTestiPK procedimentiTestiPK, String desProc) {
        this.procedimentiTestiPK = procedimentiTestiPK;
        this.desProc = desProc;
    }

    public ProcedimentiTesti(int idProc, int idLang) {
        this.procedimentiTestiPK = new ProcedimentiTestiPK(idProc, idLang);
    }

    public ProcedimentiTestiPK getProcedimentiTestiPK() {
        return procedimentiTestiPK;
    }

    public void setProcedimentiTestiPK(ProcedimentiTestiPK procedimentiTestiPK) {
        this.procedimentiTestiPK = procedimentiTestiPK;
    }

    public String getDesProc() {
        return desProc;
    }

    public void setDesProc(String desProc) {
        this.desProc = desProc;
    }

    public Lingue getLingue() {
        return lingue;
    }

    public void setLingue(Lingue lingue) {
        this.lingue = lingue;
    }

    public Procedimenti getProcedimenti() {
        return procedimenti;
    }

    public void setProcedimenti(Procedimenti procedimenti) {
        this.procedimenti = procedimenti;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (procedimentiTestiPK != null ? procedimentiTestiPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProcedimentiTesti)) {
            return false;
        }
        ProcedimentiTesti other = (ProcedimentiTesti) object;
        if ((this.procedimentiTestiPK == null && other.procedimentiTestiPK != null) || (this.procedimentiTestiPK != null && !this.procedimentiTestiPK.equals(other.procedimentiTestiPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.ProcedimentiTesti[ procedimentiTestiPK=" + procedimentiTestiPK + " ]";
    }
    
}
