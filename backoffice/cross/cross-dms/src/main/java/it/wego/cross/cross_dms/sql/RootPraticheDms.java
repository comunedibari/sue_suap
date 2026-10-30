package it.wego.cross.cross_dms.sql;

import java.sql.Date;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1

public class RootPraticheDms{
	@JsonProperty("Pratiche") 
	public ArrayList<PraticheDms> pratiche;

	public ArrayList<PraticheDms> getPratiche() {
		return pratiche;
	}

	public void setPratiche(ArrayList<PraticheDms> pratiche) {
		this.pratiche = pratiche;
	}
}

/*
class ABUSIVISMO{
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


class ALLEGATI{
	public ArrayList<Allegato> allegato;

	public ArrayList<Allegato> getAllegato() {
		return allegato;
	}

	public void setAllegato(ArrayList<Allegato> allegato) {
		this.allegato = allegato;
	}
}
*
class Allegato{
	public int id;
	public Object categoria;
	public boolean depositato;
	public String descrizione;
	public String data_arrivo;
	public String nome_file;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Object getCategoria() {
		return categoria;
	}
	public void setCategoria(Object categoria) {
		this.categoria = categoria;
	}
	public boolean isDepositato() {
		return depositato;
	}
	public void setDepositato(boolean depositato) {
		this.depositato = depositato;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getData_arrivo() {
		return data_arrivo;
	}
	public void setData_arrivo(String data_arrivo) {
		this.data_arrivo = data_arrivo;
	}
	public String getNome_file() {
		return nome_file;
	}
	public void setNome_file(String nome_file) {
		this.nome_file = nome_file;
	}
	
}
*
class ANNOTAZIONI{
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
*
class DATICATASTALI{
	@JsonProperty("PARTICELLA") 
	public int pARTICELLA;
	@JsonProperty("SEZIONE") 
	public Object sEZIONE;
	@JsonProperty("SUBALTERNO") 
	public int sUBALTERNO;
	@JsonProperty("FOGLIO") 
	public int fOGLIO;
	
	public int getpARTICELLA() {
		return pARTICELLA;
	}
	public void setpARTICELLA(int pARTICELLA) {
		this.pARTICELLA = pARTICELLA;
	}
	public Object getsEZIONE() {
		return sEZIONE;
	}
	public void setsEZIONE(Object sEZIONE) {
		this.sEZIONE = sEZIONE;
	}
	public int getsUBALTERNO() {
		return sUBALTERNO;
	}
	public void setsUBALTERNO(int sUBALTERNO) {
		this.sUBALTERNO = sUBALTERNO;
	}
	public int getfOGLIO() {
		return fOGLIO;
	}
	public void setfOGLIO(int fOGLIO) {
		this.fOGLIO = fOGLIO;
	}

}
*
class OGGETTO{
	@JsonProperty("TIPOLOGIA DI INTERVENTO") 
	public Object tIPOLOGIA_DI_INTERVENTO;
	@JsonProperty("DESCRIZIONE INTERVENTO")
	public String dESCRIZIONE_INTERVENTO;

	public Object gettIPOLOGIA_DI_INTERVENTO() {
		return tIPOLOGIA_DI_INTERVENTO;
	}
	public void settIPOLOGIA_DI_INTERVENTO(Object tIPOLOGIA_DI_INTERVENTO) {
		this.tIPOLOGIA_DI_INTERVENTO = tIPOLOGIA_DI_INTERVENTO;
	}
	public String getdESCRIZIONE_INTERVENTO() {
		return dESCRIZIONE_INTERVENTO;
	}
	public void setdESCRIZIONE_INTERVENTO(String dESCRIZIONE_INTERVENTO) {
		this.dESCRIZIONE_INTERVENTO = dESCRIZIONE_INTERVENTO;
	}
	 
}
*
*
class PRATICHECOLLEGATE{
	public String getdATA() {
		return dATA;
	}
	public void setdATA(String dATA) {
		this.dATA = dATA;
	}
	public String getiD_GRUPPO_PRATICHE_COLLEGATE() {
		return iD_GRUPPO_PRATICHE_COLLEGATE;
	}
	public void setiD_GRUPPO_PRATICHE_COLLEGATE(String iD_GRUPPO_PRATICHE_COLLEGATE) {
		this.iD_GRUPPO_PRATICHE_COLLEGATE = iD_GRUPPO_PRATICHE_COLLEGATE;
	}
	public String getcOLLEGAMENTO_CIVILIA() {
		return cOLLEGAMENTO_CIVILIA;
	}
	public void setcOLLEGAMENTO_CIVILIA(String cOLLEGAMENTO_CIVILIA) {
		this.cOLLEGAMENTO_CIVILIA = cOLLEGAMENTO_CIVILIA;
	}
	public String getcOLLEGAMENTO_FISICO() {
		return cOLLEGAMENTO_FISICO;
	}
	public void setcOLLEGAMENTO_FISICO(String cOLLEGAMENTO_FISICO) {
		this.cOLLEGAMENTO_FISICO = cOLLEGAMENTO_FISICO;
	}
	@JsonProperty("DATA") 
	public String dATA;
	@JsonProperty("ID_GRUPPO_PRATICHE_COLLEGATE") 
	public String iD_GRUPPO_PRATICHE_COLLEGATE;
	@JsonProperty("COLLEGAMENTO CIVILIA") 
	public String cOLLEGAMENTO_CIVILIA;
	@JsonProperty("COLLEGAMENTO FISICO") 
	public String cOLLEGAMENTO_FISICO;
}
*
class PROVVEDIMENTOFINALE{
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
}
*
class REFERENTI{
	public String getnOME() {
		return nOME;
	}
	public void setnOME(String nOME) {
		this.nOME = nOME;
	}
	public String getrUOLO() {
		return rUOLO;
	}
	public void setrUOLO(String rUOLO) {
		this.rUOLO = rUOLO;
	}
	public String getcOGNOME_DENOMINAZIONE() {
		return cOGNOME_DENOMINAZIONE;
	}
	public void setcOGNOME_DENOMINAZIONE(String cOGNOME_DENOMINAZIONE) {
		this.cOGNOME_DENOMINAZIONE = cOGNOME_DENOMINAZIONE;
	}
	public String getpARTITA_IVA() {
		return pARTITA_IVA;
	}
	public void setpARTITA_IVA(String pARTITA_IVA) {
		this.pARTITA_IVA = pARTITA_IVA;
	}
	public String getcODICE_FISCALE() {
		return cODICE_FISCALE;
	}
	public void setcODICE_FISCALE(String cODICE_FISCALE) {
		this.cODICE_FISCALE = cODICE_FISCALE;
	}
	@JsonProperty("NOME") 
	public String nOME;
	@JsonProperty("RUOLO") 
	public String rUOLO;
	@JsonProperty("COGNOME_DENOMINAZIONE") 
	public String cOGNOME_DENOMINAZIONE;
	@JsonProperty("PARTITA IVA") 
	public String pARTITA_IVA;
	@JsonProperty("CODICE FISCALE") 
	public String cODICE_FISCALE;
}
*
class STRUMENTIATTUATIVI{
	public Object getcONVENZIONE_NOTAIO() {
		return cONVENZIONE_NOTAIO;
	}
	public void setcONVENZIONE_NOTAIO(Object cONVENZIONE_NOTAIO) {
		this.cONVENZIONE_NOTAIO = cONVENZIONE_NOTAIO;
	}
	public Object getcONVENZIONE_REP() {
		return cONVENZIONE_REP;
	}
	public void setcONVENZIONE_REP(Object cONVENZIONE_REP) {
		this.cONVENZIONE_REP = cONVENZIONE_REP;
	}
	public Object getcONVENZIONE_TIPO() {
		return cONVENZIONE_TIPO;
	}
	public void setcONVENZIONE_TIPO(Object cONVENZIONE_TIPO) {
		this.cONVENZIONE_TIPO = cONVENZIONE_TIPO;
	}
	public Object getdELIBERA_NR() {
		return dELIBERA_NR;
	}
	public void setdELIBERA_NR(Object dELIBERA_NR) {
		this.dELIBERA_NR = dELIBERA_NR;
	}
	public Object getdELIBERA_ANNO() {
		return dELIBERA_ANNO;
	}
	public void setdELIBERA_ANNO(Object dELIBERA_ANNO) {
		this.dELIBERA_ANNO = dELIBERA_ANNO;
	}
	public Object getnOTE() {
		return nOTE;
	}
	public void setnOTE(Object nOTE) {
		this.nOTE = nOTE;
	}
	public Object getlOTTO() {
		return lOTTO;
	}
	public void setlOTTO(Object lOTTO) {
		this.lOTTO = lOTTO;
	}
	public Object getdENOMINAZIONE() {
		return dENOMINAZIONE;
	}
	public void setdENOMINAZIONE(Object dENOMINAZIONE) {
		this.dENOMINAZIONE = dENOMINAZIONE;
	}
	public Object getcONVENZIONE_DATA() {
		return cONVENZIONE_DATA;
	}
	public void setcONVENZIONE_DATA(Object cONVENZIONE_DATA) {
		this.cONVENZIONE_DATA = cONVENZIONE_DATA;
	}
	public Object getmAGLIA() {
		return mAGLIA;
	}
	public void setmAGLIA(Object mAGLIA) {
		this.mAGLIA = mAGLIA;
	}
	public Object getcONVENZIONE_RACC() {
		return cONVENZIONE_RACC;
	}
	public void setcONVENZIONE_RACC(Object cONVENZIONE_RACC) {
		this.cONVENZIONE_RACC = cONVENZIONE_RACC;
	}
	public Object getdELIBERA_OGG() {
		return dELIBERA_OGG;
	}
	public void setdELIBERA_OGG(Object dELIBERA_OGG) {
		this.dELIBERA_OGG = dELIBERA_OGG;
	}
	public Object getcOMPARTO() {
		return cOMPARTO;
	}
	public void setcOMPARTO(Object cOMPARTO) {
		this.cOMPARTO = cOMPARTO;
	}
	public Object getdELIBERA_TIPO() {
		return dELIBERA_TIPO;
	}
	public void setdELIBERA_TIPO(Object dELIBERA_TIPO) {
		this.dELIBERA_TIPO = dELIBERA_TIPO;
	}
	@JsonProperty("CONVENZIONE_NOTAIO") 
	public Object cONVENZIONE_NOTAIO;
	@JsonProperty("CONVENZIONE_REP") 
	public Object cONVENZIONE_REP;
	@JsonProperty("CONVENZIONE_TIPO") 
	public Object cONVENZIONE_TIPO;
	@JsonProperty("DELIBERA_NR") 
	public Object dELIBERA_NR;
	@JsonProperty("DELIBERA_ANNO") 
	public Object dELIBERA_ANNO;
	@JsonProperty("NOTE") 
	public Object nOTE;
	@JsonProperty("LOTTO") 
	public Object lOTTO;
	@JsonProperty("DENOMINAZIONE") 
	public Object dENOMINAZIONE;
	@JsonProperty("CONVENZIONE_DATA") 
	public Object cONVENZIONE_DATA;
	@JsonProperty("MAGLIA") 
	public Object mAGLIA;
	@JsonProperty("CONVENZIONE_RACC") 
	public Object cONVENZIONE_RACC;
	@JsonProperty("DELIBERA_OGG") 
	public Object dELIBERA_OGG;
	@JsonProperty("COMPARTO") 
	public Object cOMPARTO;
	@JsonProperty("DELIBERA_TIPO") 
	public Object dELIBERA_TIPO;
}
*
class TypeMetadata{
	public int getaNNO() {
		return aNNO;
	}
	public void setaNNO(int aNNO) {
		this.aNNO = aNNO;
	}
	public String getpROTOCOLLO_DI_ARRIVO() {
		return pROTOCOLLO_DI_ARRIVO;
	}
	public void setpROTOCOLLO_DI_ARRIVO(String pROTOCOLLO_DI_ARRIVO) {
		this.pROTOCOLLO_DI_ARRIVO = pROTOCOLLO_DI_ARRIVO;
	}
	public ArrayList<STRUMENTIATTUATIVI> getsTRUMENTI_ATTUATIVI() {
		return sTRUMENTI_ATTUATIVI;
	}
	public void setsTRUMENTI_ATTUATIVI(ArrayList<STRUMENTIATTUATIVI> sTRUMENTI_ATTUATIVI) {
		this.sTRUMENTI_ATTUATIVI = sTRUMENTI_ATTUATIVI;
	}
	public ArrayList<Object> gettIPIZZAZIONE() {
		return tIPIZZAZIONE;
	}
	public void settIPIZZAZIONE(ArrayList<Object> tIPIZZAZIONE) {
		this.tIPIZZAZIONE = tIPIZZAZIONE;
	}
	public String getdATA_DI_PRESENTAZIONE() {
		return dATA_DI_PRESENTAZIONE;
	}
	public void setdATA_DI_PRESENTAZIONE(String dATA_DI_PRESENTAZIONE) {
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
	public String gettIPO_PRATICA() {
		return tIPO_PRATICA;
	}
	public void settIPO_PRATICA(String tIPO_PRATICA) {
		this.tIPO_PRATICA = tIPO_PRATICA;
	}
	public ArrayList<DATICATASTALI> getdATI_CATASTALI() {
		return dATI_CATASTALI;
	}
	public void setdATI_CATASTALI(ArrayList<DATICATASTALI> dATI_CATASTALI) {
		this.dATI_CATASTALI = dATI_CATASTALI;
	}
	public ArrayList<Object> getdATA_4() {
		return dATA_4;
	}
	public void setdATA_4(ArrayList<Object> dATA_4) {
		this.dATA_4 = dATA_4;
	}
	public ArrayList<REFERENTI> getrEFERENTI() {
		return rEFERENTI;
	}
	public void setrEFERENTI(ArrayList<REFERENTI> rEFERENTI) {
		this.rEFERENTI = rEFERENTI;
	}
	public ArrayList<PROVVEDIMENTOFINALE> getpROVVEDIMENTO_FINALE() {
		return pROVVEDIMENTO_FINALE;
	}
	public void setpROVVEDIMENTO_FINALE(ArrayList<PROVVEDIMENTOFINALE> pROVVEDIMENTO_FINALE) {
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
	public ArrayList<ANNOTAZIONI> getaNNOTAZIONI() {
		return aNNOTAZIONI;
	}
	public void setaNNOTAZIONI(ArrayList<ANNOTAZIONI> aNNOTAZIONI) {
		this.aNNOTAZIONI = aNNOTAZIONI;
	}
	public ArrayList<Object> getdATA_2() {
		return dATA_2;
	}
	public void setdATA_2(ArrayList<Object> dATA_2) {
		this.dATA_2 = dATA_2;
	}
	public ArrayList<PRATICHECOLLEGATE> getpRATICHE_COLLEGATE() {
		return pRATICHE_COLLEGATE;
	}
	public void setpRATICHE_COLLEGATE(ArrayList<PRATICHECOLLEGATE> pRATICHE_COLLEGATE) {
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
	public ArrayList<UBICAZIONE> getuBICAZIONE() {
		return uBICAZIONE;
	}
	public void setuBICAZIONE(ArrayList<UBICAZIONE> uBICAZIONE) {
		this.uBICAZIONE = uBICAZIONE;
	}
	public ArrayList<Object> getcAMPO_3() {
		return cAMPO_3;
	}
	public void setcAMPO_3(ArrayList<Object> cAMPO_3) {
		this.cAMPO_3 = cAMPO_3;
	}
	public ArrayList<OGGETTO> getoGGETTO() {
		return oGGETTO;
	}
	public void setoGGETTO(ArrayList<OGGETTO> oGGETTO) {
		this.oGGETTO = oGGETTO;
	}
	public ArrayList<ABUSIVISMO> getaBUSIVISMO() {
		return aBUSIVISMO;
	}
	public void setaBUSIVISMO(ArrayList<ABUSIVISMO> aBUSIVISMO) {
		this.aBUSIVISMO = aBUSIVISMO;
	}
	public ALLEGATI getaLLEGATI() {
		return aLLEGATI;
	}
	public void setaLLEGATI(ALLEGATI aLLEGATI) {
		this.aLLEGATI = aLLEGATI;
	}
	@JsonProperty("ANNO") 
	public int aNNO;
	@JsonProperty("PROTOCOLLO DI ARRIVO") 
	public String pROTOCOLLO_DI_ARRIVO;
	@JsonProperty("STRUMENTI ATTUATIVI") 
	public ArrayList<STRUMENTIATTUATIVI> sTRUMENTI_ATTUATIVI;
	@JsonProperty("TIPIZZAZIONE") 
	public ArrayList<Object> tIPIZZAZIONE;
	@JsonProperty("DATA DI PRESENTAZIONE") 
	public String dATA_DI_PRESENTAZIONE;
	@JsonProperty("CAMPO_1") 
	public ArrayList<String> cAMPO_1;
	@JsonProperty("CAMPO_5") 
	public ArrayList<String> cAMPO_5;
	@JsonProperty("DATA_5") 
	public ArrayList<Date> dATA_5;
	@JsonProperty("SETTORE") 
	public Object sETTORE;
	@JsonProperty("TIPO PRATICA") 
	public String tIPO_PRATICA;
	@JsonProperty("DATI CATASTALI") 
	public ArrayList<DATICATASTALI> dATI_CATASTALI;
	@JsonProperty("DATA_4") 
	public ArrayList<Object> dATA_4;
	@JsonProperty("REFERENTI") 
	public ArrayList<REFERENTI> rEFERENTI;
	@JsonProperty("PROVVEDIMENTO FINALE") 
	public ArrayList<PROVVEDIMENTOFINALE> pROVVEDIMENTO_FINALE;
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
	public ArrayList<ANNOTAZIONI> aNNOTAZIONI;
	@JsonProperty("DATA_2") 
	public ArrayList<Object> dATA_2;
	@JsonProperty("PRATICHE COLLEGATE") 
	public ArrayList<PRATICHECOLLEGATE> pRATICHE_COLLEGATE;
	@JsonProperty("DATA_1") 
	public ArrayList<Object> dATA_1;
	@JsonProperty("DATA_3") 
	public ArrayList<Object> dATA_3;
	@JsonProperty("CAMPO_2") 
	public ArrayList<Object> cAMPO_2;
	@JsonProperty("FASCICOLO") 
	public Object fASCICOLO;
	@JsonProperty("UBICAZIONE") 
	public ArrayList<UBICAZIONE> uBICAZIONE;
	@JsonProperty("CAMPO_3") 
	public ArrayList<Object> cAMPO_3;
	@JsonProperty("OGGETTO") 
	public ArrayList<OGGETTO> oGGETTO;
	@JsonProperty("ABUSIVISMO") 
	public ArrayList<ABUSIVISMO> aBUSIVISMO;
	@JsonProperty("ALLEGATI") 
	public ALLEGATI aLLEGATI;
}

class UBICAZIONE{
	public Object getlATITUDINE() {
		return lATITUDINE;
	}
	public void setlATITUDINE(Object lATITUDINE) {
		this.lATITUDINE = lATITUDINE;
	}
	public int getnUMERO_CIVICO() {
		return nUMERO_CIVICO;
	}
	public void setnUMERO_CIVICO(int nUMERO_CIVICO) {
		this.nUMERO_CIVICO = nUMERO_CIVICO;
	}
	public String getfRAZIONE_LOCALITA() {
		return fRAZIONE_LOCALITA;
	}
	public void setfRAZIONE_LOCALITA(String fRAZIONE_LOCALITA) {
		this.fRAZIONE_LOCALITA = fRAZIONE_LOCALITA;
	}
	public Object getlONGITUDINE() {
		return lONGITUDINE;
	}
	public void setlONGITUDINE(Object lONGITUDINE) {
		this.lONGITUDINE = lONGITUDINE;
	}
	public String getsCALA() {
		return sCALA;
	}
	public void setsCALA(String sCALA) {
		this.sCALA = sCALA;
	}
	public Object getiNTERNO() {
		return iNTERNO;
	}
	public void setiNTERNO(Object iNTERNO) {
		this.iNTERNO = iNTERNO;
	}
	public String getiNDIRIZZO_DELLA_PRATICA() {
		return iNDIRIZZO_DELLA_PRATICA;
	}
	public void setiNDIRIZZO_DELLA_PRATICA(String iNDIRIZZO_DELLA_PRATICA) {
		this.iNDIRIZZO_DELLA_PRATICA = iNDIRIZZO_DELLA_PRATICA;
	}
	public int getpIANO() {
		return pIANO;
	}
	public void setpIANO(int pIANO) {
		this.pIANO = pIANO;
	}
	public Object getiNDIRIZZO_ATTUALE() {
		return iNDIRIZZO_ATTUALE;
	}
	public void setiNDIRIZZO_ATTUALE(Object iNDIRIZZO_ATTUALE) {
		this.iNDIRIZZO_ATTUALE = iNDIRIZZO_ATTUALE;
	}
	@JsonProperty("LATITUDINE") 
	public Object lATITUDINE;
	@JsonProperty("NUMERO CIVICO") 
	public int nUMERO_CIVICO;
	@JsonProperty("FRAZIONE_LOCALITA") 
	public String fRAZIONE_LOCALITA;
	@JsonProperty("LONGITUDINE") 
	public Object lONGITUDINE;
	@JsonProperty("SCALA") 
	public String sCALA;
	@JsonProperty("INTERNO") 
	public Object iNTERNO;
	@JsonProperty("INDIRIZZO DELLA PRATICA") 
	public String iNDIRIZZO_DELLA_PRATICA;
	@JsonProperty("PIANO") 
	public int pIANO;
	@JsonProperty("INDIRIZZO ATTUALE") 
	public Object iNDIRIZZO_ATTUALE;
}
*/
