package it.wego.cross.cross_beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UBICAZIONE{
	@JsonProperty("PIANO") 
	public String pIANO;
	
	@JsonProperty("LONGITUDINE") 
	public Object lONGITUDINE;
	
	@JsonProperty("NUMERO CIVICO") 
	public String nUMERO_CIVICO;
	
	@JsonProperty("SCALA") 
	public String sCALA;
	
	@JsonProperty("INDIRIZZO DELLA PRATICA") 
	public String iNDIRIZZO_DELLA_PRATICA;
	
	@JsonProperty("LATITUDINE") 
	public Object lATITUDINE;
	
	@JsonProperty("INTERNO") 
	public String iNTERNO;
	
	@JsonProperty("FRAZIONE_LOCALITÀ") 
	public String fRAZIONE_LOCALITÀ;
	
	public String getpIANO() {
		return pIANO;
	}

	public void setpIANO(String pIANO) {
		this.pIANO = pIANO;
	}

	public Object getlONGITUDINE() {
		return lONGITUDINE;
	}

	public void setlONGITUDINE(Object lONGITUDINE) {
		this.lONGITUDINE = lONGITUDINE;
	}

	public String getnUMERO_CIVICO() {
		return nUMERO_CIVICO;
	}

	public void setnUMERO_CIVICO(String nUMERO_CIVICO) {
		this.nUMERO_CIVICO = nUMERO_CIVICO;
	}

	public String getsCALA() {
		return sCALA;
	}

	public void setsCALA(String sCALA) {
		this.sCALA = sCALA;
	}

	public String getiNDIRIZZO_DELLA_PRATICA() {
		return iNDIRIZZO_DELLA_PRATICA;
	}

	public void setiNDIRIZZO_DELLA_PRATICA(String iNDIRIZZO_DELLA_PRATICA) {
		this.iNDIRIZZO_DELLA_PRATICA = iNDIRIZZO_DELLA_PRATICA;
	}

	public Object getlATITUDINE() {
		return lATITUDINE;
	}

	public void setlATITUDINE(Object lATITUDINE) {
		this.lATITUDINE = lATITUDINE;
	}

	public String getiNTERNO() {
		return iNTERNO;
	}

	public void setiNTERNO(String iNTERNO) {
		this.iNTERNO = iNTERNO;
	}

	public String getfRAZIONE_LOCALITÀ() {
		return fRAZIONE_LOCALITÀ;
	}

	public void setfRAZIONE_LOCALITÀ(String fRAZIONE_LOCALITÀ) {
		this.fRAZIONE_LOCALITÀ = fRAZIONE_LOCALITÀ;
	}
	
}