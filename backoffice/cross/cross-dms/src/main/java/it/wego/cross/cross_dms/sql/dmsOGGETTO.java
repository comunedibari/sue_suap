package it.wego.cross.cross_dms.sql;

import com.fasterxml.jackson.annotation.JsonProperty;

public class dmsOGGETTO {
	@JsonProperty("TIPOLOGIA DI INTERVENTO") 
    public Object tIPOLOGIA_DI_INTERVENTO;
    @JsonProperty("DESCRIZIONE INTERVENTO") 
    public String dESCRIZIONE_INTERVENTO;

    public Object gettIPOLOGIA_DI_INTERVENTO() {
		return tIPOLOGIA_DI_INTERVENTO;
	}
	public void settIPOLOGIA_DI_INTERVENTO(Object tIPOLOGIA_DI_INTERVENTO) {
		this.tIPOLOGIA_DI_INTERVENTO = tIPOLOGIA_DI_INTERVENTO;
	}
	public String getdESCRIZIONE_INTERVENTO() {
		return dESCRIZIONE_INTERVENTO;
	}
	public void setdESCRIZIONE_INTERVENTO(String dESCRIZIONE_INTERVENTO) {
		this.dESCRIZIONE_INTERVENTO = dESCRIZIONE_INTERVENTO;
	}
}
