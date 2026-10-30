package it.wego.cross.beans.allegati;

//Stato.java

public class Stato {
	private String annullato;
	private Object idUtenteCambioStato;
	private Object dataCambioStato;
	private Object motivoCambioStato;

	public String getAnnullato() { return annullato; }
	public void setAnnullato(String value) { this.annullato = value; }

	public Object getIDUtenteCambioStato() { return idUtenteCambioStato; }
	public void setIDUtenteCambioStato(Object value) { this.idUtenteCambioStato = value; }

	public Object getDataCambioStato() { return dataCambioStato; }
	public void setDataCambioStato(Object value) { this.dataCambioStato = value; }

	public Object getMotivoCambioStato() { return motivoCambioStato; }
	public void setMotivoCambioStato(Object value) { this.motivoCambioStato = value; }
}
