package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the ruoli_etichetta_civilia_cross database table.
 * 
 */
@Entity
@Table(name="ruoli_etichetta_civilia_cross")
@NamedQuery(name="RuoliEtichettaCiviliaCross.findAll", query="SELECT r FROM RuoliEtichettaCiviliaCross r")
public class RuoliEtichettaCiviliaCross implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="data_inserimento")
	private Timestamp dataInserimento;

	@Column(name="id_ruolo_cross")
	private int idRuoloCross;

	@Column(name="ruolo_cross")
	private String ruoloCross;

	@Column(name="ruolo_etichetta_civilia_cross")
	private String ruoloEtichettaCiviliaCross;

	public RuoliEtichettaCiviliaCross() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getDataInserimento() {
		return this.dataInserimento;
	}

	public void setDataInserimento(Timestamp dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public int getIdRuoloCross() {
		return this.idRuoloCross;
	}

	public void setIdRuoloCross(int idRuoloCross) {
		this.idRuoloCross = idRuoloCross;
	}

	public String getRuoloCross() {
		return this.ruoloCross;
	}

	public void setRuoloCross(String ruoloCross) {
		this.ruoloCross = ruoloCross;
	}

	public String getRuoloEtichettaCiviliaCross() {
		return this.ruoloEtichettaCiviliaCross;
	}

	public void setRuoloEtichettaCiviliaCross(String ruoloEtichettaCiviliaCross) {
		this.ruoloEtichettaCiviliaCross = ruoloEtichettaCiviliaCross;
	}

}