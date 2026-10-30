package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The persistent class for the lk_tipo_pratica_processi database table.
 * 
 */
@Entity
@Table(name="lk_tipo_pratica_processi")
@XmlRootElement
@NamedQueries({
@NamedQuery(name="LkTipoPraticaProcessi.findAll", query="SELECT l FROM LkTipoPraticaProcessi l"),
@NamedQuery(name = "LkTipoPraticaProcessi.findByIdTipoPraticaProcessiById", query = "SELECT l FROM LkTipoPraticaProcessi l WHERE l.idProcesso = :idProcesso")
})

public class LkTipoPraticaProcessi implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LkTipoPraticaProcessiPK id;

	public LkTipoPraticaProcessi() {
	}

	public LkTipoPraticaProcessiPK getId() {
		return this.id;
	}

	public void setId(LkTipoPraticaProcessiPK id) {
		this.id = id;
	}

}