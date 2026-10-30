package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the pratica_operazione database table.
 * 
 */
@Entity
@Table(name="pratica_operazione")
@NamedQuery(name="PraticaOperazione.findAll", query="SELECT p FROM PraticaOperazione p")
public class PraticaOperazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PraticaOperazionePK id;

	@JoinColumn(name = "id_pratica", referencedColumnName = "id_pratica", insertable=false, updatable=false)
    @ManyToOne(optional = false)
	private Pratica idPratica;
	
	@JoinColumn(name = "id_operazione", referencedColumnName = "id", insertable=false, updatable=false)
    @OneToOne(optional = false)
	private Operazione operazione;
	
	public PraticaOperazione() {}
	
	public PraticaOperazione(int idPratica, int idOperazione) {
		id = new PraticaOperazionePK();
		id.setIdOperazione(idOperazione);
		id.setIdPratica(idPratica);
	}

	public PraticaOperazionePK getId() {
		return this.id;
	}

	public void setId(PraticaOperazionePK id) {
		this.id = id;
	}

	public Pratica getPratica() {
		return this.idPratica;
	}

	public void setPratica(Pratica pratica) {
		this.idPratica = pratica;
	}

	public Operazione getOperazione() {
		return operazione;
	}

	public void setOperazione(Operazione operazione) {
		this.operazione = operazione;
	}
}