package it.wego.cross.cross_dms.sql;

import com.fasterxml.jackson.annotation.JsonProperty;

public class dmsREFERENTI {

	private int id_anagrafica;
	
	@JsonProperty("NOME") 
    public Object nOME;
    @JsonProperty("RUOLO") 
    public Object rUOLO;
    @JsonProperty("COGNOME_DENOMINAZIONE") 
    public Object cOGNOME_DENOMINAZIONE;
    @JsonProperty("PARTITA IVA") 
    public Object pARTITA_IVA;
    @JsonProperty("CODICE FISCALE") 
    public Object cODICE_FISCALE;

    
	public int getId_anagrafica() {
		return id_anagrafica;
	}
	public void setId_anagrafica(int id_anagrafica) {
		this.id_anagrafica = id_anagrafica;
	}

	public Object getnOME() {
		return nOME;
	}
	public void setnOME(Object nOME) {
		this.nOME = nOME;
	}
	public Object getrUOLO() {
		return rUOLO;
	}
	public void setrUOLO(Object rUOLO) {
		this.rUOLO = rUOLO;
	}
	public Object getcOGNOME_DENOMINAZIONE() {
		return cOGNOME_DENOMINAZIONE;
	}
	public void setcOGNOME_DENOMINAZIONE(Object cOGNOME_DENOMINAZIONE) {
		this.cOGNOME_DENOMINAZIONE = cOGNOME_DENOMINAZIONE;
	}
	public Object getpARTITA_IVA() {
		return pARTITA_IVA;
	}
	public void setpARTITA_IVA(Object pARTITA_IVA) {
		this.pARTITA_IVA = pARTITA_IVA;
	}
	public Object getcODICE_FISCALE() {
		return cODICE_FISCALE;
	}
	public void setcODICE_FISCALE(Object cODICE_FISCALE) {
		this.cODICE_FISCALE = cODICE_FISCALE;
	}
}
