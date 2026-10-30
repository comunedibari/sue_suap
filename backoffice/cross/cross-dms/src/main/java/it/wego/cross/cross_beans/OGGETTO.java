package it.wego.cross.cross_beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OGGETTO{
	@JsonProperty("DESCRIZIONE INTERVENTO") 
	public String dESCRIZIONE_INTERVENTO;
	@JsonProperty("TIPOLOGIA DI INTERVENTO") 
	public String tIPOLOGIA_DI_INTERVENTO;
	@JsonProperty("DATA DI PRESENTAZIONE") 
	public Object dATA_DI_PRESENTAZIONE;
	
	public String getdESCRIZIONE_INTERVENTO() {
		return dESCRIZIONE_INTERVENTO;
	}
	public void setdESCRIZIONE_INTERVENTO(String dESCRIZIONE_INTERVENTO) {
		this.dESCRIZIONE_INTERVENTO = dESCRIZIONE_INTERVENTO;
	}
	public String gettIPOLOGIA_DI_INTERVENTO() {
		return tIPOLOGIA_DI_INTERVENTO;
	}
	public void settIPOLOGIA_DI_INTERVENTO(String tIPOLOGIA_DI_INTERVENTO) {
		this.tIPOLOGIA_DI_INTERVENTO = tIPOLOGIA_DI_INTERVENTO;
	}
	public Object getdATA_DI_PRESENTAZIONE() {
		return dATA_DI_PRESENTAZIONE;
	}
	public void setdATA_DI_PRESENTAZIONE(Object dATA_DI_PRESENTAZIONE) {
		this.dATA_DI_PRESENTAZIONE = dATA_DI_PRESENTAZIONE;
	}

}
