package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the lk_tipo_pratica_processi database table.
 * 
 */
@Embeddable
public class LkTipoPraticaProcessiPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_tipo_pratica", insertable=false, updatable=false)
	private int idTipoPratica;

	@Column(name="id_processo", insertable=false, updatable=false)
	private int idProcesso;

	public LkTipoPraticaProcessiPK() {
	}
	public int getIdTipoPratica() {
		return this.idTipoPratica;
	}
	public void setIdTipoPratica(int idTipoPratica) {
		this.idTipoPratica = idTipoPratica;
	}
	public int getIdProcesso() {
		return this.idProcesso;
	}
	public void setIdProcesso(int idProcesso) {
		this.idProcesso = idProcesso;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LkTipoPraticaProcessiPK)) {
			return false;
		}
		LkTipoPraticaProcessiPK castOther = (LkTipoPraticaProcessiPK)other;
		return 
			(this.idTipoPratica == castOther.idTipoPratica)
			&& (this.idProcesso == castOther.idProcesso);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idTipoPratica;
		hash = hash * prime + this.idProcesso;
		
		return hash;
	}
}