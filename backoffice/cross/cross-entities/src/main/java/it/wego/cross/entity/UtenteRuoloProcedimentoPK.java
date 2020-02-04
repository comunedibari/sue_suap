/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author piergiorgio
 */
@Embeddable
public class UtenteRuoloProcedimentoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_utente_ruolo_ente")
    private int idUtenteRuoloEnte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_proc_ente")
    private int idProcEnte;

    public UtenteRuoloProcedimentoPK() {
    }

    public UtenteRuoloProcedimentoPK(int idUtenteRuoloEnte, int idProcEnte) {
        this.idUtenteRuoloEnte = idUtenteRuoloEnte;
        this.idProcEnte = idProcEnte;
    }

    public int getIdUtenteRuoloEnte() {
        return idUtenteRuoloEnte;
    }

    public void setIdUtenteRuoloEnte(int idUtenteRuoloEnte) {
        this.idUtenteRuoloEnte = idUtenteRuoloEnte;
    }

    public int getIdProcEnte() {
        return idProcEnte;
    }

    public void setIdProcEnte(int idProcEnte) {
        this.idProcEnte = idProcEnte;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idUtenteRuoloEnte;
        hash += (int) idProcEnte;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UtenteRuoloProcedimentoPK)) {
            return false;
        }
        UtenteRuoloProcedimentoPK other = (UtenteRuoloProcedimentoPK) object;
        if (this.idUtenteRuoloEnte != other.idUtenteRuoloEnte) {
            return false;
        }
        if (this.idProcEnte != other.idProcEnte) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.wego.cross.entity.UtenteRuoloProcedimentoPK[ idUtenteRuoloEnte=" + idUtenteRuoloEnte + ", idProcEnte=" + idProcEnte + " ]";
    }
    
}
