package it.wego.cross.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

//Oggetto.java

public class Oggetto {
	@JsonProperty("DESCRIZIONE INTERVENTO")
	private String descrizioneIntervento;
	
	@JsonProperty("TIPOLOGIA INTERVENTO")
	private String tipologiaDiIntervento;

	@JsonProperty("DATA DI PRESENTAZIONE")
	private String dataDiPresentazione;

	public Oggetto() {
		super();
		this.descrizioneIntervento = "";
		this.tipologiaDiIntervento = "";
		this.dataDiPresentazione = "";
	}
	
	public String getDescrizioneIntervento() { return descrizioneIntervento; }
	public void setDescrizioneIntervento(String value) { this.descrizioneIntervento = value; }

	public String getTipologiaDiIntervento() { return tipologiaDiIntervento; }
	public void setTipologiaDiIntervento(String value) { this.tipologiaDiIntervento = value; }

	public String getDataDiPresentazione() { return dataDiPresentazione; }
	public void setDataDiPresentazione(String value) { this.dataDiPresentazione = value; }
	
}
