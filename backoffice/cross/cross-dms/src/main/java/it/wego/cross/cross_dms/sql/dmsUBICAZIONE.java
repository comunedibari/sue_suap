package it.wego.cross.cross_dms.sql;

import com.fasterxml.jackson.annotation.JsonProperty;

public class dmsUBICAZIONE {

	@JsonProperty("LATITUDINE") 
    public Object lATITUDINE;
    @JsonProperty("NUMERO CIVICO") 
    public Object nUMERO_CIVICO;
    @JsonProperty("FRAZIONE_LOCALITA") 
    public Object fRAZIONE_LOCALITA;
    @JsonProperty("LONGITUDINE") 
    public Object lONGITUDINE;
    @JsonProperty("SCALA") 
    public Object sCALA;
    @JsonProperty("INTERNO") 
    public Object iNTERNO;
    @JsonProperty("INDIRIZZO DELLA PRATICA") 
    public Object iNDIRIZZO_DELLA_PRATICA;
    @JsonProperty("PIANO") 
    public Object pIANO;
    @JsonProperty("INDIRIZZO ATTUALE") 
    public Object iNDIRIZZO_ATTUALE;
    
    public Object getlATITUDINE() {
		return lATITUDINE;
	}
	public void setlATITUDINE(Object lATITUDINE) {
		this.lATITUDINE = lATITUDINE;
	}
	public Object getnUMERO_CIVICO() {
		return nUMERO_CIVICO;
	}
	public void setnUMERO_CIVICO(Object nUMERO_CIVICO) {
		this.nUMERO_CIVICO = nUMERO_CIVICO;
	}
	public Object getfRAZIONE_LOCALITA() {
		return fRAZIONE_LOCALITA;
	}
	public void setfRAZIONE_LOCALITA(Object fRAZIONE_LOCALITA) {
		this.fRAZIONE_LOCALITA = fRAZIONE_LOCALITA;
	}
	public Object getlONGITUDINE() {
		return lONGITUDINE;
	}
	public void setlONGITUDINE(Object lONGITUDINE) {
		this.lONGITUDINE = lONGITUDINE;
	}
	public Object getsCALA() {
		return sCALA;
	}
	public void setsCALA(Object sCALA) {
		this.sCALA = sCALA;
	}
	public Object getiNTERNO() {
		return iNTERNO;
	}
	public void setiNTERNO(Object iNTERNO) {
		this.iNTERNO = iNTERNO;
	}
	public Object getiNDIRIZZO_DELLA_PRATICA() {
		return iNDIRIZZO_DELLA_PRATICA;
	}
	public void setiNDIRIZZO_DELLA_PRATICA(Object iNDIRIZZO_DELLA_PRATICA) {
		this.iNDIRIZZO_DELLA_PRATICA = iNDIRIZZO_DELLA_PRATICA;
	}
	public Object getpIANO() {
		return pIANO;
	}
	public void setpIANO(Object pIANO) {
		this.pIANO = pIANO;
	}
	public Object getiNDIRIZZO_ATTUALE() {
		return iNDIRIZZO_ATTUALE;
	}
	public void setiNDIRIZZO_ATTUALE(Object iNDIRIZZO_ATTUALE) {
		this.iNDIRIZZO_ATTUALE = iNDIRIZZO_ATTUALE;
	}	
}

