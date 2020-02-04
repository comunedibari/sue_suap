package it.wego.cross.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "avvisi")
@NamedQueries({
	@NamedQuery(name = "Avviso.findAll", query = "SELECT av FROM Avviso av ORDER BY av.scadenza asc"),
	@NamedQuery(name = "Avviso.findByIdAvvisi", query = "SELECT av FROM Avviso av WHERE av.idAvviso = :idAvviso"),
	@NamedQuery(name = "Avviso.findNonScaduti", query = "SELECT av FROM Avviso  av WHERE av.scadenza >= :scadenza ORDER BY av.scadenza asc")	
})
public class Avviso  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idAvviso")
	private Integer idAvviso;

	@Column(name = "testo")
	private String testo;

	@Column(name = "scadenza")
	@Temporal(TemporalType.DATE)
	private Date scadenza;

	public Integer getIdAvviso() {
		return idAvviso;
	}

	public void setIdAvviso(Integer idAvviso) {
		this.idAvviso = idAvviso;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public Date getScadenza() {
		return scadenza;
	}

	public void setScadenza(Date scadenza) {
		this.scadenza = scadenza;
	}



}
