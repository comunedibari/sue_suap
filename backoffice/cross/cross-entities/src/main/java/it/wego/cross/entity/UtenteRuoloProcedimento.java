/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author piergiorgio
 */
@Entity
@Table(name = "utente_ruolo_procedimento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UtenteRuoloProcedimento.findAll", query = "SELECT u FROM UtenteRuoloProcedimento u"),
    @NamedQuery(name = "UtenteRuoloProcedimento.findByIdUtenteRuoloEnte", query = "SELECT u FROM UtenteRuoloProcedimento u WHERE u.utenteRuoloProcedimentoPK.idUtenteRuoloEnte = :idUtenteRuoloEnte"),
    @NamedQuery(name = "UtenteRuoloProcedimento.findByIdProcEnte", query = "SELECT u FROM UtenteRuoloProcedimento u WHERE u.utenteRuoloProcedimentoPK.idProcEnte = :idProcEnte")})
public class UtenteRuoloProcedimento implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UtenteRuoloProcedimentoPK utenteRuoloProcedimentoPK;
    @JoinColumn(name = "id_proc_ente", referencedColumnName = "id_proc_ente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ProcedimentiEnti procedimentiEnti;
    @JoinColumn(name = "id_utente_ruolo_ente", referencedColumnName = "id_utente_ruolo_ente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private UtenteRuoloEnte utenteRuoloEnte;

    public UtenteRuoloProcedimento() {
    }

    public UtenteRuoloProcedimento(UtenteRuoloProcedimentoPK utenteRuoloProcedimentoPK) {
        this.utenteRuoloProcedimentoPK = utenteRuoloProcedimentoPK;
    }

    public UtenteRuoloProcedimento(int idUtenteRuoloEnte, int idProcEnte) {
        this.utenteRuoloProcedimentoPK = new UtenteRuoloProcedimentoPK(idUtenteRuoloEnte, idProcEnte);
    }

    public UtenteRuoloProcedimentoPK getUtenteRuoloProcedimentoPK() {
        return utenteRuoloProcedimentoPK;
    }

    public void setUtenteRuoloProcedimentoPK(UtenteRuoloProcedimentoPK utenteRuoloProcedimentoPK) {
        this.utenteRuoloProcedimentoPK = utenteRuoloProcedimentoPK;
    }

    public ProcedimentiEnti getProcedimentiEnti() {
        return procedimentiEnti;
    }

    public void setProcedimentiEnti(ProcedimentiEnti procedimentiEnti) {
        this.procedimentiEnti = procedimentiEnti;
    }

    public UtenteRuoloEnte getUtenteRuoloEnte() {
        return utenteRuoloEnte;
    }

    public void setUtenteRuoloEnte(UtenteRuoloEnte utenteRuoloEnte) {
        this.utenteRuoloEnte = utenteRuoloEnte;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (utenteRuoloProcedimentoPK != null ? utenteRuoloProcedimentoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UtenteRuoloProcedimento)) {
            return false;
        }
        UtenteRuoloProcedimento other = (UtenteRuoloProcedimento) object;
        if ((this.utenteRuoloProcedimentoPK == null && other.utenteRuoloProcedimentoPK != null) || (this.utenteRuoloProcedimentoPK != null && !this.utenteRuoloProcedimentoPK.equals(other.utenteRuoloProcedimentoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.UtenteRuoloProcedimento[ utenteRuoloProcedimentoPK=" + utenteRuoloProcedimentoPK + " ]";
    }
    
}
