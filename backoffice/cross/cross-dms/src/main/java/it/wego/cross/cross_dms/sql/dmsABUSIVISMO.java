package it.wego.cross.cross_dms.sql;

import com.fasterxml.jackson.annotation.JsonProperty;

public class dmsABUSIVISMO {

	@JsonProperty("ORDINANZA_NR") 
    public Object oRDINANZA_NR;
    @JsonProperty("VERBALE_ANNO") 
    public Object vERBALE_ANNO;
    @JsonProperty("VERBALE_NR") 
    public Object vERBALE_NR;
    @JsonProperty("DELIBERA_ANNO") 
    public Object dELIBERA_ANNO;
    @JsonProperty("ORDINANZA_ANNO") 
    public Object oRDINANZA_ANNO;
    @JsonProperty("DELIBERA_NR") 
    public Object dELIBERA_NR;
    
    public Object getoRDINANZA_NR() {
		return oRDINANZA_NR;
	}
	public void setoRDINANZA_NR(Object oRDINANZA_NR) {
		this.oRDINANZA_NR = oRDINANZA_NR;
	}
	public Object getvERBALE_ANNO() {
		return vERBALE_ANNO;
	}
	public void setvERBALE_ANNO(Object vERBALE_ANNO) {
		this.vERBALE_ANNO = vERBALE_ANNO;
	}
	public Object getvERBALE_NR() {
		return vERBALE_NR;
	}
	public void setvERBALE_NR(Object vERBALE_NR) {
		this.vERBALE_NR = vERBALE_NR;
	}
	public Object getdELIBERA_ANNO() {
		return dELIBERA_ANNO;
	}
	public void setdELIBERA_ANNO(Object dELIBERA_ANNO) {
		this.dELIBERA_ANNO = dELIBERA_ANNO;
	}
	public Object getoRDINANZA_ANNO() {
		return oRDINANZA_ANNO;
	}
	public void setoRDINANZA_ANNO(Object oRDINANZA_ANNO) {
		this.oRDINANZA_ANNO = oRDINANZA_ANNO;
	}
	public Object getdELIBERA_NR() {
		return dELIBERA_NR;
	}
	public void setdELIBERA_NR(Object dELIBERA_NR) {
		this.dELIBERA_NR = dELIBERA_NR;
	}

}