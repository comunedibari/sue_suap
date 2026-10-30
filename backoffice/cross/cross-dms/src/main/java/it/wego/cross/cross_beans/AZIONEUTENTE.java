package it.wego.cross.cross_beans;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AZIONEUTENTE{
	@JsonProperty("UTENZA") 
	public String uTENZA;
	
	@JsonProperty("DATA AZIONE") 
	public Date dATA_AZIONE;
	
	@JsonProperty("AZIONE") 
	public String aZIONE;
	
	public String getuTENZA() {
		return uTENZA;
	}

	public void setuTENZA(String uTENZA) {
		this.uTENZA = uTENZA;
	}

	public Date getdATA_AZIONE() {
		return dATA_AZIONE;
	}

	public void setdATA_AZIONE(Date dATA_AZIONE) {
		this.dATA_AZIONE = dATA_AZIONE;
	}

	public String getaZIONE() {
		return aZIONE;
	}

	public void setaZIONE(String aZIONE) {
		this.aZIONE = aZIONE;
	}

}
