package it.wego.cross.cross_dms.sql;

import java.sql.Date;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TypeMetadata{
	@JsonProperty("ANNO") 
    public int aNNO;
    @JsonProperty("PROTOCOLLO DI ARRIVO") 
    public Object pROTOCOLLO_DI_ARRIVO;
    @JsonProperty("STRUMENTI ATTUATIVI") 
    public ArrayList<dmsSTRUMENTIATTUATIVI> sTRUMENTI_ATTUATIVI;
    @JsonProperty("TIPIZZAZIONE") 
    public ArrayList<Object> tIPIZZAZIONE;
    @JsonProperty("DATA DI PRESENTAZIONE") 
    public Object dATA_DI_PRESENTAZIONE;
    @JsonProperty("CAMPO_1") 
    public ArrayList<String> cAMPO_1;
    @JsonProperty("CAMPO_5") 
    public ArrayList<String> cAMPO_5;
    @JsonProperty("DATA_5") 
    public ArrayList<Date> dATA_5;
    @JsonProperty("SETTORE") 
    public Object sETTORE;
    @JsonProperty("TIPO PRATICA") 
    public Object tIPO_PRATICA;
    @JsonProperty("DATI CATASTALI") 
    public ArrayList<dmsDATICATASTALI> dATI_CATASTALI;
    @JsonProperty("DATA_4") 
    public ArrayList<Object> dATA_4;
    @JsonProperty("REFERENTI") 
    public ArrayList<dmsREFERENTI> rEFERENTI;
    @JsonProperty("PROVVEDIMENTO FINALE") 
    public ArrayList<dmsPROVVEDIMENTOFINALE> pROVVEDIMENTO_FINALE;
    @JsonProperty("BUSTA") 
    public Object bUSTA;
    @JsonProperty("NUMERO") 
    public Object nUMERO;
    @JsonProperty("CAMPO_4") 
    public ArrayList<Object> cAMPO_4;
    @JsonProperty("EDIFICIO") 
    public ArrayList<Object> eDIFICIO;
    @JsonProperty("ID") 
    public Object iD;
    @JsonProperty("TORNATA") 
    public Object tORNATA;
    @JsonProperty("ANNOTAZIONI") 
    public ArrayList<dmsANNOTAZIONI> aNNOTAZIONI;
    @JsonProperty("DATA_2") 
    public ArrayList<Object> dATA_2;
    @JsonProperty("PRATICHE COLLEGATE") 
    public ArrayList<dmsPRATICHECOLLEGATE> pRATICHE_COLLEGATE;
    @JsonProperty("DATA_1") 
    public ArrayList<Object> dATA_1;
    @JsonProperty("DATA_3") 
    public ArrayList<Object> dATA_3;
    @JsonProperty("CAMPO_2") 
    public ArrayList<Object> cAMPO_2;
    @JsonProperty("FASCICOLO") 
    public Object fASCICOLO;
    @JsonProperty("UBICAZIONE") 
    public ArrayList<dmsUBICAZIONE> uBICAZIONE;
    @JsonProperty("CAMPO_3") 
    public ArrayList<Object> cAMPO_3;
    @JsonProperty("OGGETTO") 
    public ArrayList<dmsOGGETTO> oGGETTO;
    @JsonProperty("ABUSIVISMO") 
    public ArrayList<dmsABUSIVISMO> aBUSIVISMO;
    @JsonProperty("ALLEGATI") 
    public dmsALLEGATI aLLEGATI;
        
    public dmsALLEGATI getaLLEGATI() {
		return aLLEGATI;
	}
	public void setaLLEGATI(dmsALLEGATI aLLEGATI) {
		this.aLLEGATI = aLLEGATI;
	}
	public int getaNNO() {
		return aNNO;
	}
	public void setaNNO(int aNNO) {
		this.aNNO = aNNO;
	}
	public Object getpROTOCOLLO_DI_ARRIVO() {
		return pROTOCOLLO_DI_ARRIVO;
	}
	public void setpROTOCOLLO_DI_ARRIVO(Object pROTOCOLLO_DI_ARRIVO) {
		this.pROTOCOLLO_DI_ARRIVO = pROTOCOLLO_DI_ARRIVO;
	}
	public ArrayList<dmsSTRUMENTIATTUATIVI> getsTRUMENTI_ATTUATIVI() {
		return sTRUMENTI_ATTUATIVI;
	}
	public void setsTRUMENTI_ATTUATIVI(ArrayList<dmsSTRUMENTIATTUATIVI> sTRUMENTI_ATTUATIVI) {
		this.sTRUMENTI_ATTUATIVI = sTRUMENTI_ATTUATIVI;
	}
	public ArrayList<Object> gettIPIZZAZIONE() {
		return tIPIZZAZIONE;
	}
	public void settIPIZZAZIONE(ArrayList<Object> tIPIZZAZIONE) {
		this.tIPIZZAZIONE = tIPIZZAZIONE;
	}
	public Object getdATA_DI_PRESENTAZIONE() {
		return dATA_DI_PRESENTAZIONE;
	}
	public void setdATA_DI_PRESENTAZIONE(Object dATA_DI_PRESENTAZIONE) {
		this.dATA_DI_PRESENTAZIONE = dATA_DI_PRESENTAZIONE;
	}
	public ArrayList<String> getcAMPO_1() {
		return cAMPO_1;
	}
	public void setcAMPO_1(ArrayList<String> cAMPO_1) {
		this.cAMPO_1 = cAMPO_1;
	}
	public ArrayList<String> getcAMPO_5() {
		return cAMPO_5;
	}
	public void setcAMPO_5(ArrayList<String> cAMPO_5) {
		this.cAMPO_5 = cAMPO_5;
	}
	public ArrayList<Date> getdATA_5() {
		return dATA_5;
	}
	public void setdATA_5(ArrayList<Date> dATA_5) {
		this.dATA_5 = dATA_5;
	}
	public Object getsETTORE() {
		return sETTORE;
	}
	public void setsETTORE(Object sETTORE) {
		this.sETTORE = sETTORE;
	}
	public Object gettIPO_PRATICA() {
		return tIPO_PRATICA;
	}
	public void settIPO_PRATICA(Object tIPO_PRATICA) {
		this.tIPO_PRATICA = tIPO_PRATICA;
	}
	public ArrayList<dmsDATICATASTALI> getdATI_CATASTALI() {
		return dATI_CATASTALI;
	}
	public void setdATI_CATASTALI(ArrayList<dmsDATICATASTALI> dATI_CATASTALI) {
		this.dATI_CATASTALI = dATI_CATASTALI;
	}
	public ArrayList<Object> getdATA_4() {
		return dATA_4;
	}
	public void setdATA_4(ArrayList<Object> dATA_4) {
		this.dATA_4 = dATA_4;
	}
	public ArrayList<dmsREFERENTI> getrEFERENTI() {
		return rEFERENTI;
	}
	public void setrEFERENTI(ArrayList<dmsREFERENTI> rEFERENTI) {
		this.rEFERENTI = rEFERENTI;
	}
	public ArrayList<dmsPROVVEDIMENTOFINALE> getpROVVEDIMENTO_FINALE() {
		return pROVVEDIMENTO_FINALE;
	}
	public void setpROVVEDIMENTO_FINALE(ArrayList<dmsPROVVEDIMENTOFINALE> pROVVEDIMENTO_FINALE) {
		this.pROVVEDIMENTO_FINALE = pROVVEDIMENTO_FINALE;
	}
	public Object getbUSTA() {
		return bUSTA;
	}
	public void setbUSTA(Object bUSTA) {
		this.bUSTA = bUSTA;
	}
	public Object getnUMERO() {
		return nUMERO;
	}
	public void setnUMERO(Object nUMERO) {
		this.nUMERO = nUMERO;
	}
	public ArrayList<Object> getcAMPO_4() {
		return cAMPO_4;
	}
	public void setcAMPO_4(ArrayList<Object> cAMPO_4) {
		this.cAMPO_4 = cAMPO_4;
	}
	public ArrayList<Object> geteDIFICIO() {
		return eDIFICIO;
	}
	public void seteDIFICIO(ArrayList<Object> eDIFICIO) {
		this.eDIFICIO = eDIFICIO;
	}
	public Object getiD() {
		return iD;
	}
	public void setiD(Object iD) {
		this.iD = iD;
	}
	public Object gettORNATA() {
		return tORNATA;
	}
	public void settORNATA(Object tORNATA) {
		this.tORNATA = tORNATA;
	}
	public ArrayList<dmsANNOTAZIONI> getaNNOTAZIONI() {
		return aNNOTAZIONI;
	}
	public void setaNNOTAZIONI(ArrayList<dmsANNOTAZIONI> aNNOTAZIONI) {
		this.aNNOTAZIONI = aNNOTAZIONI;
	}
	public ArrayList<Object> getdATA_2() {
		return dATA_2;
	}
	public void setdATA_2(ArrayList<Object> dATA_2) {
		this.dATA_2 = dATA_2;
	}
	public ArrayList<dmsPRATICHECOLLEGATE> getpRATICHE_COLLEGATE() {
		return pRATICHE_COLLEGATE;
	}
	public void setpRATICHE_COLLEGATE(ArrayList<dmsPRATICHECOLLEGATE> pRATICHE_COLLEGATE) {
		this.pRATICHE_COLLEGATE = pRATICHE_COLLEGATE;
	}
	public ArrayList<Object> getdATA_1() {
		return dATA_1;
	}
	public void setdATA_1(ArrayList<Object> dATA_1) {
		this.dATA_1 = dATA_1;
	}
	public ArrayList<Object> getdATA_3() {
		return dATA_3;
	}
	public void setdATA_3(ArrayList<Object> dATA_3) {
		this.dATA_3 = dATA_3;
	}
	public ArrayList<Object> getcAMPO_2() {
		return cAMPO_2;
	}
	public void setcAMPO_2(ArrayList<Object> cAMPO_2) {
		this.cAMPO_2 = cAMPO_2;
	}
	public Object getfASCICOLO() {
		return fASCICOLO;
	}
	public void setfASCICOLO(Object fASCICOLO) {
		this.fASCICOLO = fASCICOLO;
	}
	public ArrayList<dmsUBICAZIONE> getuBICAZIONE() {
		return uBICAZIONE;
	}
	public void setuBICAZIONE(ArrayList<dmsUBICAZIONE> uBICAZIONE) {
		this.uBICAZIONE = uBICAZIONE;
	}
	public ArrayList<Object> getcAMPO_3() {
		return cAMPO_3;
	}
	public void setcAMPO_3(ArrayList<Object> cAMPO_3) {
		this.cAMPO_3 = cAMPO_3;
	}
	public ArrayList<dmsOGGETTO> getoGGETTO() {
		return oGGETTO;
	}
	public void setoGGETTO(ArrayList<dmsOGGETTO> oGGETTO) {
		this.oGGETTO = oGGETTO;
	}
	public ArrayList<dmsABUSIVISMO> getaBUSIVISMO() {
		return aBUSIVISMO;
	}
	public void setaBUSIVISMO(ArrayList<dmsABUSIVISMO> aBUSIVISMO) {
		this.aBUSIVISMO = aBUSIVISMO;
	}
}

