package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the pratica_operazione database table.
 * 
 */
@Embeddable
public class PraticaOperazionePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_pratica")
	private int idPratica;

	@Column(name="id_operazione")
	private int idOperazione;

	public PraticaOperazionePK() {
	}
	public int getIdPratica() {
		return this.idPratica;
	}
	public void setIdPratica(int idPratica) {
		this.idPratica = idPratica;
	}
	public int getIdOperazione() {
		return this.idOperazione;
	}
	public void setIdOperazione(int idOperazione) {
		this.idOperazione = idOperazione;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PraticaOperazionePK)) {
			return false;
		}
		PraticaOperazionePK castOther = (PraticaOperazionePK)other;
		return 
			(this.idPratica == castOther.idPratica)
			&& (this.idOperazione == castOther.idOperazione);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idPratica;
		hash = hash * prime + this.idOperazione;
		
		return hash;
	}
}