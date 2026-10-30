package it.wego.cross.cross_beans;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TypeMetadata{
	public String ufficio;
	public String note;
	public int anno;
	public int numero;

	@JsonProperty("ANAGRAFICA PADRE") 
	public ANAGRAFICAPADRE aNAGRAFICA_PADRE;
	
	public int parte;
	
	@JsonProperty("ANAGRAFICA CONIUGE") 
	public ANAGRAFICACONIUGE aNAGRAFICA_CONIUGE;
	
	@JsonProperty("ANAGRAFICA") 
	public ANAGRAFICA aNAGRAFICA;
	
	@JsonProperty("ANAGRAFICA MADRE") 
	public ANAGRAFICAMADRE aNAGRAFICA_MADRE;
	
	public int volume;
	public String serie;
	
	@JsonProperty("AZIONE UTENTE") 
	public ArrayList<AZIONEUTENTE> aZIONE_UTENTE;
	
	public String flag_anomalia;
	
	@JsonProperty("BUSTA") 
	public String bUSTA;
	
	@JsonProperty("DATI CATASTALI") 
	public ArrayList<Object> dATI_CATASTALI;
	
	@JsonProperty("FASCICOLO") 
	public String fASCICOLO;
	
	@JsonProperty("REFERENTI") 
	public ArrayList<REFERENTI> rEFERENTI;
	
	@JsonProperty("TIPIZZAZIONE") 
	public ArrayList<Object> tIPIZZAZIONE;
	
	@JsonProperty("TORNATA") 
	public Object tORNATA;
	
	@JsonProperty("DATA_1") 
	public ArrayList<Object> dATA_1;
	
	@JsonProperty("DATA_2") 
	public ArrayList<Object> dATA_2;
	
	@JsonProperty("DATA_3") 
	public ArrayList<Object> dATA_3;
	
	@JsonProperty("SETTORE") 
	public String sETTORE;
	
	@JsonProperty("EDIFICIO") 
	public ArrayList<Object> eDIFICIO;
	
	@JsonProperty("DATA_4") 
	public ArrayList<Object> dATA_4;
	
	@JsonProperty("ID") 
	public String iD;
	
	@JsonProperty("ABUSIVISMO") 
	public ArrayList<Object> aBUSIVISMO;
	
	@JsonProperty("PROTOCOLLO IN ARRIVO") 
	public ArrayList<Object> pROTOCOLLO_IN_ARRIVO;
	
	@JsonProperty("OGGETTO") 
	public ArrayList<OGGETTO> oGGETTO;
	
	@JsonProperty("DATA_5") 
	public ArrayList<Object> dATA_5;
	
	@JsonProperty("CAMPO_5") 
	public ArrayList<Object> cAMPO_5;
	
	@JsonProperty("CAMPO_4") 
	public ArrayList<Object> cAMPO_4;
	
	@JsonProperty("CAMPO_3") 
	public ArrayList<Object> cAMPO_3;
	
	@JsonProperty("CAMPO_2") 
	public ArrayList<Object> cAMPO_2;
	
	@JsonProperty("CAMPO_1") 
	public ArrayList<Object> cAMPO_1;
	
	@JsonProperty("ANNO") 
	public int aNNO;
	
	@JsonProperty("TIPO PRATICA") 
	public String tIPO_PRATICA;
	
	@JsonProperty("PROVVEDIMENTO FINALE") 
	public ArrayList<Object> pROVVEDIMENTO_FINALE;
	
	@JsonProperty("STRUMENTI ATTUATIVI") 
	public ArrayList<Object> sTRUMENTI_ATTUATIVI;
	
	@JsonProperty("NUMERO") 
	public String nUMERO;
	
	@JsonProperty("UBICAZIONE") 
	public ArrayList<UBICAZIONE> uBICAZIONE;
	
	public Date data_atto;

	public String getUfficio() {
		return ufficio;
	}

	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public ANAGRAFICAPADRE getaNAGRAFICA_PADRE() {
		return aNAGRAFICA_PADRE;
	}

	public void setaNAGRAFICA_PADRE(ANAGRAFICAPADRE aNAGRAFICA_PADRE) {
		this.aNAGRAFICA_PADRE = aNAGRAFICA_PADRE;
	}

	public int getParte() {
		return parte;
	}

	public void setParte(int parte) {
		this.parte = parte;
	}

	public ANAGRAFICACONIUGE getaNAGRAFICA_CONIUGE() {
		return aNAGRAFICA_CONIUGE;
	}

	public void setaNAGRAFICA_CONIUGE(ANAGRAFICACONIUGE aNAGRAFICA_CONIUGE) {
		this.aNAGRAFICA_CONIUGE = aNAGRAFICA_CONIUGE;
	}

	public ANAGRAFICA getaNAGRAFICA() {
		return aNAGRAFICA;
	}

	public void setaNAGRAFICA(ANAGRAFICA aNAGRAFICA) {
		this.aNAGRAFICA = aNAGRAFICA;
	}

	public ANAGRAFICAMADRE getaNAGRAFICA_MADRE() {
		return aNAGRAFICA_MADRE;
	}

	public void setaNAGRAFICA_MADRE(ANAGRAFICAMADRE aNAGRAFICA_MADRE) {
		this.aNAGRAFICA_MADRE = aNAGRAFICA_MADRE;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public ArrayList<AZIONEUTENTE> getaZIONE_UTENTE() {
		return aZIONE_UTENTE;
	}

	public void setaZIONE_UTENTE(ArrayList<AZIONEUTENTE> aZIONE_UTENTE) {
		this.aZIONE_UTENTE = aZIONE_UTENTE;
	}

	public String getFlag_anomalia() {
		return flag_anomalia;
	}

	public void setFlag_anomalia(String flag_anomalia) {
		this.flag_anomalia = flag_anomalia;
	}

	public String getbUSTA() {
		return bUSTA;
	}

	public void setbUSTA(String bUSTA) {
		this.bUSTA = bUSTA;
	}

	public ArrayList<Object> getdATI_CATASTALI() {
		return dATI_CATASTALI;
	}

	public void setdATI_CATASTALI(ArrayList<Object> dATI_CATASTALI) {
		this.dATI_CATASTALI = dATI_CATASTALI;
	}

	public String getfASCICOLO() {
		return fASCICOLO;
	}

	public void setfASCICOLO(String fASCICOLO) {
		this.fASCICOLO = fASCICOLO;
	}

	public ArrayList<REFERENTI> getrEFERENTI() {
		return rEFERENTI;
	}

	public void setrEFERENTI(ArrayList<REFERENTI> rEFERENTI) {
		this.rEFERENTI = rEFERENTI;
	}

	public ArrayList<Object> gettIPIZZAZIONE() {
		return tIPIZZAZIONE;
	}

	public void settIPIZZAZIONE(ArrayList<Object> tIPIZZAZIONE) {
		this.tIPIZZAZIONE = tIPIZZAZIONE;
	}

	public Object gettORNATA() {
		return tORNATA;
	}

	public void settORNATA(Object tORNATA) {
		this.tORNATA = tORNATA;
	}

	public ArrayList<Object> getdATA_1() {
		return dATA_1;
	}

	public void setdATA_1(ArrayList<Object> dATA_1) {
		this.dATA_1 = dATA_1;
	}

	public ArrayList<Object> getdATA_2() {
		return dATA_2;
	}

	public void setdATA_2(ArrayList<Object> dATA_2) {
		this.dATA_2 = dATA_2;
	}

	public ArrayList<Object> getdATA_3() {
		return dATA_3;
	}

	public void setdATA_3(ArrayList<Object> dATA_3) {
		this.dATA_3 = dATA_3;
	}

	public String getsETTORE() {
		return sETTORE;
	}

	public void setsETTORE(String sETTORE) {
		this.sETTORE = sETTORE;
	}

	public ArrayList<Object> geteDIFICIO() {
		return eDIFICIO;
	}

	public void seteDIFICIO(ArrayList<Object> eDIFICIO) {
		this.eDIFICIO = eDIFICIO;
	}

	public ArrayList<Object> getdATA_4() {
		return dATA_4;
	}

	public void setdATA_4(ArrayList<Object> dATA_4) {
		this.dATA_4 = dATA_4;
	}

	public String getiD() {
		return iD;
	}

	public void setiD(String iD) {
		this.iD = iD;
	}

	public ArrayList<Object> getaBUSIVISMO() {
		return aBUSIVISMO;
	}

	public void setaBUSIVISMO(ArrayList<Object> aBUSIVISMO) {
		this.aBUSIVISMO = aBUSIVISMO;
	}

	public ArrayList<Object> getpROTOCOLLO_IN_ARRIVO() {
		return pROTOCOLLO_IN_ARRIVO;
	}

	public void setpROTOCOLLO_IN_ARRIVO(ArrayList<Object> pROTOCOLLO_IN_ARRIVO) {
		this.pROTOCOLLO_IN_ARRIVO = pROTOCOLLO_IN_ARRIVO;
	}

	public ArrayList<OGGETTO> getoGGETTO() {
		return oGGETTO;
	}

	public void setoGGETTO(ArrayList<OGGETTO> oGGETTO) {
		this.oGGETTO = oGGETTO;
	}

	public ArrayList<Object> getdATA_5() {
		return dATA_5;
	}

	public void setdATA_5(ArrayList<Object> dATA_5) {
		this.dATA_5 = dATA_5;
	}

	public ArrayList<Object> getcAMPO_5() {
		return cAMPO_5;
	}

	public void setcAMPO_5(ArrayList<Object> cAMPO_5) {
		this.cAMPO_5 = cAMPO_5;
	}

	public ArrayList<Object> getcAMPO_4() {
		return cAMPO_4;
	}

	public void setcAMPO_4(ArrayList<Object> cAMPO_4) {
		this.cAMPO_4 = cAMPO_4;
	}

	public ArrayList<Object> getcAMPO_3() {
		return cAMPO_3;
	}

	public void setcAMPO_3(ArrayList<Object> cAMPO_3) {
		this.cAMPO_3 = cAMPO_3;
	}

	public ArrayList<Object> getcAMPO_2() {
		return cAMPO_2;
	}

	public void setcAMPO_2(ArrayList<Object> cAMPO_2) {
		this.cAMPO_2 = cAMPO_2;
	}

	public ArrayList<Object> getcAMPO_1() {
		return cAMPO_1;
	}

	public void setcAMPO_1(ArrayList<Object> cAMPO_1) {
		this.cAMPO_1 = cAMPO_1;
	}

	public int getaNNO() {
		return aNNO;
	}

	public void setaNNO(int aNNO) {
		this.aNNO = aNNO;
	}

	public String gettIPO_PRATICA() {
		return tIPO_PRATICA;
	}

	public void settIPO_PRATICA(String tIPO_PRATICA) {
		this.tIPO_PRATICA = tIPO_PRATICA;
	}

	public ArrayList<Object> getpROVVEDIMENTO_FINALE() {
		return pROVVEDIMENTO_FINALE;
	}

	public void setpROVVEDIMENTO_FINALE(ArrayList<Object> pROVVEDIMENTO_FINALE) {
		this.pROVVEDIMENTO_FINALE = pROVVEDIMENTO_FINALE;
	}

	public ArrayList<Object> getsTRUMENTI_ATTUATIVI() {
		return sTRUMENTI_ATTUATIVI;
	}

	public void setsTRUMENTI_ATTUATIVI(ArrayList<Object> sTRUMENTI_ATTUATIVI) {
		this.sTRUMENTI_ATTUATIVI = sTRUMENTI_ATTUATIVI;
	}

	public String getnUMERO() {
		return nUMERO;
	}

	public void setnUMERO(String nUMERO) {
		this.nUMERO = nUMERO;
	}

	public ArrayList<UBICAZIONE> getuBICAZIONE() {
		return uBICAZIONE;
	}

	public void setuBICAZIONE(ArrayList<UBICAZIONE> uBICAZIONE) {
		this.uBICAZIONE = uBICAZIONE;
	}

	public Date getData_atto() {
		return data_atto;
	}

	public void setData_atto(Date data_atto) {
		this.data_atto = data_atto;
	}

}