package it.wego.cross.cross_dms.sql;

import com.fasterxml.jackson.annotation.JsonProperty;

public class dmsPRATICHECOLLEGATE {

	@JsonProperty("DATA") 
    public Object dATA;
    @JsonProperty("ID_GRUPPO_PRATICHE_COLLEGATE") 
    public Object iD_GRUPPO_PRATICHE_COLLEGATE;
    @JsonProperty("COLLEGAMENTO CIVILIA") 
    public Object cOLLEGAMENTO_CIVILIA;
    @JsonProperty("COLLEGAMENTO FISICO") 
    public Object cOLLEGAMENTO_FISICO;

    public Object getdATA() {
		return dATA;
	}
	public void setdATA(Object dATA) {
		this.dATA = dATA;
	}
	public Object getiD_GRUPPO_PRATICHE_COLLEGATE() {
		return iD_GRUPPO_PRATICHE_COLLEGATE;
	}
	public void setiD_GRUPPO_PRATICHE_COLLEGATE(Object iD_GRUPPO_PRATICHE_COLLEGATE) {
		this.iD_GRUPPO_PRATICHE_COLLEGATE = iD_GRUPPO_PRATICHE_COLLEGATE;
	}
	public Object getcOLLEGAMENTO_CIVILIA() {
		return cOLLEGAMENTO_CIVILIA;
	}
	public void setcOLLEGAMENTO_CIVILIA(Object cOLLEGAMENTO_CIVILIA) {
		this.cOLLEGAMENTO_CIVILIA = cOLLEGAMENTO_CIVILIA;
	}
	public Object getcOLLEGAMENTO_FISICO() {
		return cOLLEGAMENTO_FISICO;
	}
	public void setcOLLEGAMENTO_FISICO(Object cOLLEGAMENTO_FISICO) {
		this.cOLLEGAMENTO_FISICO = cOLLEGAMENTO_FISICO;
	}
}
