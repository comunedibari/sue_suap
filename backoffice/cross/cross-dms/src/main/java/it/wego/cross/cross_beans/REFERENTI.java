package it.wego.cross.cross_beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class REFERENTI{
	@JsonProperty("CODICE FISCALE") 
	public String cODICE_FISCALE;
	
	@JsonProperty("RUOLO") 
	public String rUOLO;
	
	@JsonProperty("PARTITA IVA") 
	public String pARTITA_IVA;
	
	@JsonProperty("NOME") 
	public String nOME;
	
	@JsonProperty("COGNOME_DENOMINAZIONE") 
	public String cOGNOME_DENOMINAZIONE;

	public String getcODICE_FISCALE() {
		return cODICE_FISCALE;
	}

	public void setcODICE_FISCALE(String cODICE_FISCALE) {
		this.cODICE_FISCALE = cODICE_FISCALE;
	}

	public String getrUOLO() {
		return rUOLO;
	}

	public void setrUOLO(String rUOLO) {
		this.rUOLO = rUOLO;
	}

	public String getpARTITA_IVA() {
		return pARTITA_IVA;
	}

	public void setpARTITA_IVA(String pARTITA_IVA) {
		this.pARTITA_IVA = pARTITA_IVA;
	}

	public String getnOME() {
		return nOME;
	}

	public void setnOME(String nOME) {
		this.nOME = nOME;
	}

	public String getcOGNOME_DENOMINAZIONE() {
		return cOGNOME_DENOMINAZIONE;
	}

	public void setcOGNOME_DENOMINAZIONE(String cOGNOME_DENOMINAZIONE) {
		this.cOGNOME_DENOMINAZIONE = cOGNOME_DENOMINAZIONE;
	}
}
