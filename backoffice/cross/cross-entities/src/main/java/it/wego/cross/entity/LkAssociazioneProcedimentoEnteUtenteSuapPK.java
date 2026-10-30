package it.wego.cross.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the lk_associazione_procedimento_ente_utente_suap database table.
 * 
 */
@Embeddable
public class LkAssociazioneProcedimentoEnteUtenteSuapPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column (name="id_ente")
	private int idEnte;

	@Column(name="cod_endoprocedimento_suap")
	private String codEndoprocedimentoSuap;

	public int getIdEnte() {
		return this.idEnte;
	}
	
	public void setIdEnte(int idEnte) {
		this.idEnte = idEnte;
	}
	public String getCodEndoprocedimentoSuap() {
		return this.codEndoprocedimentoSuap;
	}
	public void setCodEndoprocedimentoSuap(String codEndoprocedimentoSuap) {
		this.codEndoprocedimentoSuap = codEndoprocedimentoSuap;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LkAssociazioneProcedimentoEnteUtenteSuapPK)) {
			return false;
		}
		LkAssociazioneProcedimentoEnteUtenteSuapPK castOther = (LkAssociazioneProcedimentoEnteUtenteSuapPK)other;
		return 
			(this.idEnte == castOther.idEnte)
			&& this.codEndoprocedimentoSuap.equals(castOther.codEndoprocedimentoSuap);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idEnte;
		hash = hash * prime + this.codEndoprocedimentoSuap.hashCode();
		
		return hash;
	}
}