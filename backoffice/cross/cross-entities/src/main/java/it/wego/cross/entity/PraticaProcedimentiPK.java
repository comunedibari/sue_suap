/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author giuseppe
 */
@Embeddable
public class PraticaProcedimentiPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_pratica")
    private int idPratica;
    @Basic(optional = false)
    @Column(name = "id_procedimento")
    private int idProcedimento;
    @Basic(optional = false)
    @Column(name = "id_ente")
    private int idEnte;
    @Basic(optional = false)
    @Column(name = "id_endoprocedimento_suap")
    private int idEndoprocedimentoSuap;

    public PraticaProcedimentiPK() {
    	this(-1,-1,-1,-1);
    }

    public PraticaProcedimentiPK(int idPratica, int idProcedimento, int idEnte) {
    	this(idPratica, idProcedimento, idEnte, -1);
    }

    public PraticaProcedimentiPK(int idPratica, int idProcedimento, int idEnte, int idEndoprocedimentoSuap) {
    	this.idPratica = idPratica;
    	this.idProcedimento = idProcedimento;
    	this.idEnte = idEnte;
    	this.idEndoprocedimentoSuap = idEndoprocedimentoSuap;
    }

    public int getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(int idPratica) {
        this.idPratica = idPratica;
    }

    public int getIdProcedimento() {
        return idProcedimento;
    }

    public void setIdProcedimento(int idProcedimento) {
        this.idProcedimento = idProcedimento;
    }

    public int getIdEnte() {
        return idEnte;
    }

    public int getIdEndoprocedimentoSuap() {
		return idEndoprocedimentoSuap;
	}

	public void setIdEndoprocedimentoSuap(int idEndoprocedimentoSuap) {
		this.idEndoprocedimentoSuap = idEndoprocedimentoSuap;
	}

	public void setIdEnte(int idEnte) {
        this.idEnte = idEnte;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPratica;
        hash += (int) idProcedimento;
        hash += (int) idEnte; 
        hash += (int) idEndoprocedimentoSuap; 
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PraticaProcedimentiPK)) {
            return false;
        }
        PraticaProcedimentiPK other = (PraticaProcedimentiPK) object;
        if (this.idPratica != other.idPratica) {
            return false;
        }
        if (this.idProcedimento != other.idProcedimento) {
            return false;
        }
        if (this.idEnte != other.idEnte) {
            return false;
        }
        if (this.idEndoprocedimentoSuap != other.idEndoprocedimentoSuap) {
        	return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "PraticaProcedimentiPK [idPratica=" + idPratica + ", idProcedimento=" + idProcedimento + ", idEnte="
				+ idEnte + ", idEndoprocedimentoSuap=" + idEndoprocedimentoSuap + "]";
	}    
}
