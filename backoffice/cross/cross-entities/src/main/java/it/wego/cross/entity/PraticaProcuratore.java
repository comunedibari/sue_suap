package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the pratica_procuratore database table.
 * 
 */
@Entity
@Table(name="pratica_procuratore")
@NamedQueries({
	@NamedQuery(name="PraticaProcuratore.findAll", query="SELECT p FROM PraticaProcuratore p"),
	@NamedQuery(name="PraticaProcuratore.findActiveTechnicianAgent", query="SELECT p FROM PraticaProcuratore p WHERE p.idPratica = :idPratica AND p.dataFineIncarico IS NULL")
})
public class PraticaProcuratore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_fine_incarico")
	private Date dataFineIncarico;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_inizio_incarico")
	private Date dataInizioIncarico;

	@JoinColumn(name = "id_anagrafica", referencedColumnName = "id_anagrafica")
    @ManyToOne(optional = false)
    private Anagrafica anagrafica;

	@Column(name="id_pratica")
	private int idPratica;

	public PraticaProcuratore() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDataFineIncarico() {
		return this.dataFineIncarico;
	}

	public void setDataFineIncarico(Date dataFineIncarico) {
		this.dataFineIncarico = dataFineIncarico;
	}

	public Date getDataInizioIncarico() {
		return this.dataInizioIncarico;
	}

	public void setDataInizioIncarico(Date dataInizioIncarico) {
		this.dataInizioIncarico = dataInizioIncarico;
	}

	public Anagrafica getAnagrafica() {
		return anagrafica;
	}

	public void setAnagrafica(Anagrafica anagrafica) {
		this.anagrafica = anagrafica;
	}

	public int getIdPratica() {
		return this.idPratica;
	}

	public void setIdPratica(int idPratica) {
		this.idPratica = idPratica;
	}

}