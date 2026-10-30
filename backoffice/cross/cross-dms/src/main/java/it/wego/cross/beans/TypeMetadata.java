package it.wego.cross.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.wego.cross.cross_dms.utilities.myUtility;

//TypeMetadata.java

public class TypeMetadata {
	@JsonProperty("BUSTA")
	private String busta;	

	@JsonProperty("DATI CATASTALI")
	private Object[] datiCatastali;

	@JsonProperty("FASCICOLO")
	private String fascicolo;

	@JsonProperty("REFERENTI")
	private Referenti[] referenti;
	
	@JsonProperty("TIPIZZAZIONE")
	private Object[] tipizzazione;
/*	
	@JsonProperty("TORNATA")
	private String tornata;
*/
	@JsonProperty("DATA_1")
	private Object[] data1;

	@JsonProperty("DATA_2")
	private Object[] data2;
	
	@JsonProperty("DATA_3")
	private Object[] data3;
	
	@JsonProperty("SETTORE")
	private String settore;
	
	@JsonProperty("EDFICIO")
	private Object[] edificio;
	
	@JsonProperty("DATA_4")
	private Object[] data4;
	
	@JsonProperty("ID")
	private String id;
	
	@JsonProperty("ABUSIVISMO")
	private Object[] abusivismo;

	@JsonProperty("PROTOCOLLO IN ARRIVO")
	private Object[] protocolloInArrivo;
	
	@JsonProperty("OGGETTO")
	private Oggetto[] oggetto;
	
	@JsonProperty("DATA_5")
	private Object[] data5;
	
	@JsonProperty("CAMPO_5")
	private Object[] campo5;

	@JsonProperty("CAMPO_4")
	private Object[] campo4;
	
	@JsonProperty("CAMPO_3")
	private Object[] campo3;
	
	@JsonProperty("CAMPO_2")
	private Object[] campo2;

	@JsonProperty("CAMPO_1")
	private Object[] campo1;
	
	@JsonProperty("ANNO")
	private long anno;
	
	@JsonProperty("TIPO PRATICA")
	private String tipoPratica;

	@JsonProperty("PROVVEDIMENTO FINALE")
	private Object[] provvedimentoFinale;
	
	@JsonProperty("STRUMENTI ATTUATIVI")
	private Object[] strumentiAttuativi;
	
	@JsonProperty("NUMERO")
	private String numero;
	
	@JsonProperty("AZIONE UTENTE")
	private AzioneUtente[] azioneUtente;
	
	@JsonProperty("UBICAZIONE")
	private Ubicazione[] ubicazione;

	public TypeMetadata() {
		super();

		Object[] obj = new Object[1];
		Object obj_el = new Object();
		
//		Object[] obj = null;
//		Object obj_el = null;

		this.busta = "";
		this.datiCatastali = obj;
		this.fascicolo = "";
		this.tipizzazione = obj;
//		this.tornata = myUtility.getLocalTime2()+"Z";
//		this.tornata = "";
		this.data1 = obj;
		this.data2 = obj;
		this.data3 = obj;
		this.settore = "";
		this.edificio = obj;
		this.data4 = obj;
		this.id = "";
		this.abusivismo = obj;
		this.protocolloInArrivo = obj;
		this.data5 = obj;
		this.campo5 = obj;
		this.campo4 = obj;
		this.campo3 = obj;
		this.campo2 = obj;
		this.campo1 = obj;
		this.anno = 0;
		this.tipoPratica = "";
		this.provvedimentoFinale = obj;
		this.strumentiAttuativi = obj;
		this.numero = "";
	}
	
	public String getBusta() { return busta; }
	public void setBusta(String value) { this.busta = value; }

	public Object[] getDatiCatastali() { return datiCatastali; }
	public void setDatiCatastali(Object[] value) { this.datiCatastali = value; }

	public String getFascicolo() { return fascicolo; }
	public void setFascicolo(String value) { this.fascicolo = value; }

	public Referenti[] getReferenti() { return referenti; }
	public void setReferenti(Referenti[] value) { this.referenti = value; }

	public Object[] getTipizzazione() { return tipizzazione; }
	public void setTipizzazione(Object[] value) { this.tipizzazione = value; }

	/*
	public String getTornata() { return tornata; }
	public void setTornata(String value) { this.tornata = value; }
*/
	public Object[] getData1() { return data1; }
	public void setData1(Object[] value) { this.data1 = value; }

	public Object[] getData2() { return data2; }
	public void setData2(Object[] value) { this.data2 = value; }

	public Object[] getData3() { return data3; }
	public void setData3(Object[] value) { this.data3 = value; }

	public String getSettore() { return settore; }
	public void setSettore(String value) { this.settore = value; }

	public Object[] getEdificio() { return edificio; }
	public void setEdificio(Object[] value) { this.edificio = value; }

	public Object[] getData4() { return data4; }
	public void setData4(Object[] value) { this.data4 = value; }

	public String getID() { return id; }
	public void setID(String value) { this.id = value; }

	public Object[] getAbusivismo() { return abusivismo; }
	public void setAbusivismo(Object[] value) { this.abusivismo = value; }

	public Object[] getProtocolloInArrivo() { return protocolloInArrivo; }
	public void setProtocolloInArrivo(Object[] value) { this.protocolloInArrivo = value; }

	public Oggetto[] getOggetto() { return oggetto; }
	public void setOggetto(Oggetto[] value) { this.oggetto = value; }

	public Object[] getData5() { return data5; }
	public void setData5(Object[] value) { this.data5 = value; }

	public Object[] getCampo5() { return campo5; }
	public void setCampo5(Object[] value) { this.campo5 = value; }

	public Object[] getCampo4() { return campo4; }
	public void setCampo4(Object[] value) { this.campo4 = value; }

	public Object[] getCampo3() { return campo3; }
	public void setCampo3(Object[] value) { this.campo3 = value; }

	public Object[] getCampo2() { return campo2; }
	public void setCampo2(Object[] value) { this.campo2 = value; }

	public Object[] getCampo1() { return campo1; }
	public void setCampo1(Object[] value) { this.campo1 = value; }

	public long getAnno() { return anno; }
	public void setAnno(long value) { this.anno = value; }

	public String getTipoPratica() { return tipoPratica; }
	public void setTipoPratica(String value) { this.tipoPratica = value; }

	public Object[] getProvvedimentoFinale() { return provvedimentoFinale; }
	public void setProvvedimentoFinale(Object[] value) { this.provvedimentoFinale = value; }

	public Object[] getStrumentiAttuativi() { return strumentiAttuativi; }
	public void setStrumentiAttuativi(Object[] value) { this.strumentiAttuativi = value; }

	public String getNumero() { return numero; }
	public void setNumero(String value) { this.numero = value; }

	public AzioneUtente[] getAzioneUtente() { return azioneUtente; }
	public void setAzioneUtente(AzioneUtente[] value) { this.azioneUtente = value; }

	public Ubicazione[] getUbicazione() { return ubicazione; }
	public void setUbicazione(Ubicazione[] value) { this.ubicazione = value; }
}
