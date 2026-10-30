package it.wego.cross.beans;

//AzioneUtente.java

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AzioneUtente {
	@JsonProperty("UTENZA")
	private String utenza;
	
	@JsonProperty("DATA AZIONE")
	private String dataAzione;
	
	@JsonProperty("AZIONE")
	private String azione;

	public String getUtenza() { return utenza; }
	public void setUtenza(String value) { this.utenza = value; }

	public String getDataAzione() { return dataAzione; }
	public void setDataAzione(String value) { this.dataAzione = value; }

	public String getAzione() { return azione; }
	public void setAzione(String value) { this.azione = value; }
}

