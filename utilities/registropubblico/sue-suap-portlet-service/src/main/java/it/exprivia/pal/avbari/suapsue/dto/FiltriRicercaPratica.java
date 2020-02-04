package it.exprivia.pal.avbari.suapsue.dto;

import java.io.Serializable;
import java.util.Date;

public class FiltriRicercaPratica implements Serializable {
	
	private static final long serialVersionUID = 4741152121958513314L;
	
	protected Long idSportello;
	
	protected Date dataInizio;
	
	protected Date dataFine;
	
	
	public Long getIdSportello() {
		return idSportello;
	}
	public void setIdSportello(Long idEnte) {
		this.idSportello = idEnte;
	}
	
	public Date getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}
	
	public Date getDataFine() {
		return dataFine;
	}
	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FiltriRicercaPratica [idSportello=");
		builder.append(idSportello);
		builder.append(", dataInizio=");
		builder.append(dataInizio);
		builder.append(", dataFine=");
		builder.append(dataFine);
		builder.append("]");
		return builder.toString();
	}
}