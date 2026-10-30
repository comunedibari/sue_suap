package it.wego.cross.cross_dms.sql;

import com.fasterxml.jackson.annotation.JsonProperty;

public class dmsPROVVEDIMENTOFINALE {

    @JsonProperty("PROTOCOLLO DI USCITA") 
    public Object pROTOCOLLO_DI_USCITA;
	@JsonProperty("ANNO") 
    public Object aNNO;
    @JsonProperty("NUMERO") 
    public Object nUMERO;
    @JsonProperty("DATA") 
    public Object dATA;
    @JsonProperty("ESITO") 
    public Object eSITO;
    
    public Object getpROTOCOLLO_DI_USCITA() {
		return pROTOCOLLO_DI_USCITA;
	}
	public void setpROTOCOLLO_DI_USCITA(Object pROTOCOLLO_DI_USCITA) {
		this.pROTOCOLLO_DI_USCITA = pROTOCOLLO_DI_USCITA;
	}
	public Object getaNNO() {
		return aNNO;
	}
	public void setaNNO(Object aNNO) {
		this.aNNO = aNNO;
	}
	public Object getnUMERO() {
		return nUMERO;
	}
	public void setnUMERO(Object nUMERO) {
		this.nUMERO = nUMERO;
	}
	public Object getdATA() {
		return dATA;
	}
	public void setdATA(Object dATA) {
		this.dATA = dATA;
	}
	public Object geteSITO() {
		return eSITO;
	}
	public void seteSITO(Object eSITO) {
		this.eSITO = eSITO;
	}
}