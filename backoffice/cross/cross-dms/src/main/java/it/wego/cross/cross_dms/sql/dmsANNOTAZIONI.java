package it.wego.cross.cross_dms.sql;

import com.fasterxml.jackson.annotation.JsonProperty;

public class dmsANNOTAZIONI {

	@JsonProperty("NOTA") 
    public Object nOTA;
    @JsonProperty("UTENTE") 
    public Object uTENTE;
    @JsonProperty("DATA") 
    public Object dATA;

    public Object getnOTA() {
		return nOTA;
	}
	public void setnOTA(Object nOTA) {
		this.nOTA = nOTA;
	}
	public Object getuTENTE() {
		return uTENTE;
	}
	public void setuTENTE(Object uTENTE) {
		this.uTENTE = uTENTE;
	}
	public Object getdATA() {
		return dATA;
	}
	public void setdATA(Object dATA) {
		this.dATA = dATA;
	}
}
