package it.wego.cross.cross_dms.sql;

import com.fasterxml.jackson.annotation.JsonProperty;

public class dmsDATICATASTALI {

	@JsonProperty("PARTICELLA") 
    public Object pARTICELLA;
    @JsonProperty("SEZIONE") 
    public Object sEZIONE;
    @JsonProperty("SUBALTERNO") 
    public Object sUBALTERNO;
    @JsonProperty("FOGLIO") 
    public Object fOGLIO;
    
    public Object getpARTICELLA() {
		return pARTICELLA;
	}
	public void setpARTICELLA(Object pARTICELLA) {
		this.pARTICELLA = pARTICELLA;
	}
	public Object getsEZIONE() {
		return sEZIONE;
	}
	public void setsEZIONE(Object sEZIONE) {
		this.sEZIONE = sEZIONE;
	}
	public Object getsUBALTERNO() {
		return sUBALTERNO;
	}
	public void setsUBALTERNO(Object sUBALTERNO) {
		this.sUBALTERNO = sUBALTERNO;
	}
	public Object getfOGLIO() {
		return fOGLIO;
	}
	public void setfOGLIO(Object fOGLIO) {
		this.fOGLIO = fOGLIO;
	}
}
