package it.wego.cross.cross_dms.sql;

import java.sql.Date;

public class CrossFilterPratiche {
	private final static String SEP_FIELD_FILTER = ",";
//	private Integer annoRiferimento;
	private String annoRiferimento;	
	private String numeroProtocollo;   
//	private Date dataInizio;
//	private Date dataFine;
	private String dataInizio;
	private String dataFine;
	private String foglio;
	private String mappale;
	private String subalterno;
	private String numeroFascicolo;
	private String oggetto;
	private String statoPratica;
	private String identificativoPratica;
	private String nome;
	private String cognome;
	private String denominazione;
	private String partitaIva;
	private String codiceFiscale;
	private String indirizzo;
	private String civico;
	
	private int cntFilter; 

	public CrossFilterPratiche() {
		this.cntFilter = 0;
		this.annoRiferimento = "";
		this.numeroProtocollo = "";
		this.dataInizio = "";
		this.dataFine = "";
		this.foglio = "";
		this.mappale = "";
		this.subalterno = "";
		this.numeroFascicolo = "";
		this.oggetto = "";
		this.statoPratica = "";
		this.identificativoPratica = "";
		this.nome = "";
		this.cognome = "";
		this.denominazione = "";
		this.partitaIva = "";
		this.codiceFiscale = "";
		this.indirizzo = "";
		this.civico = "";
	}
	
	public CrossFilterPratiche(String annoRiferimento, String numeroProtocollo, String dataInizio, String dataFine,
			String foglio, String mappale, String subalterno, String numeroFascicolo, String oggetto,
			String statoPratica, String identificativoPratica, String nome, String cognome, String denominazione,
			String partitaIva, String codiceFiscale, String indirizzo, String civico) {
		super();
		
		this.cntFilter = 0;
		this.annoRiferimento = annoRiferimento;
		this.numeroProtocollo = numeroProtocollo;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.foglio = foglio;
		this.mappale = mappale;
		this.subalterno = subalterno;
		this.numeroFascicolo = numeroFascicolo;
		this.oggetto = oggetto;
		this.statoPratica = statoPratica;
		this.identificativoPratica = identificativoPratica;
		this.nome = nome;
		this.cognome = cognome;
		this.denominazione = denominazione;
		this.partitaIva = partitaIva;
		this.codiceFiscale = codiceFiscale;
		this.indirizzo = indirizzo;
		this.civico = civico;
	}
	
	public String to_json()
	{
		String result = "{"
		+ this.annoRiferimento
		+ this.numeroProtocollo
		+ this.dataInizio 
		+ this.dataFine
		+ this.foglio
		+ this.mappale
		+ this.subalterno
		+ this.numeroFascicolo
		+ this.oggetto
		+ this.statoPratica
		+ this.identificativoPratica
		+ this.nome
		+ this.cognome
		+ this.denominazione
		+ this.partitaIva
		+ this.codiceFiscale
		+ this.indirizzo
		+ this.civico
		+ "}";
		return result;
	}
	
	// Esempio http://.../api/v1/documents?filter={"Nome":{"eq":"Mario"}}&offset=0&limit=20&sort=+Cognome,+Nome	
	public String getAnnoRiferimento() {
		return annoRiferimento;
	}
	public void setAnnoRiferimento(String annoRiferimento) {
		String wAnnoRiferimento = "\"Anno\":{\"ci\":\"" + annoRiferimento + "\"}";
		if(this.cntFilter > 0) {wAnnoRiferimento = SEP_FIELD_FILTER + wAnnoRiferimento;}		
		this.annoRiferimento = wAnnoRiferimento;
		this.cntFilter++;
	}
	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}
	public void setNumeroProtocollo(String numeroProtocollo) {
		String wNumeroProtocollo = "\"NumeroProtocollo\":{\"ci\":\"" + numeroProtocollo + "\"}";
		if(this.cntFilter > 0) {wNumeroProtocollo = SEP_FIELD_FILTER + wNumeroProtocollo;}		
		this.numeroProtocollo = wNumeroProtocollo;
		this.cntFilter++;
	}
	public String getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(String dataInizio) {
		String wDataInizio = "\"Datainizio\":{\"ci\":\"" + dataInizio + "\"}";
		if(this.cntFilter > 0) {wDataInizio = SEP_FIELD_FILTER + wDataInizio;}		
		this.dataInizio = wDataInizio;
		this.cntFilter++;
	}
	public String getDataFine() {
		return dataFine;
	}
	public void setDataFine(String dataFine) {
		String wDataFine = "\"Datafine\":{\"ci\":\"" + dataFine + "\"}";
		if(this.cntFilter > 0) {wDataFine = SEP_FIELD_FILTER + wDataFine;}		
		this.dataFine = wDataFine;
		this.cntFilter++;
	}
	public String getFoglio() {
		return foglio;
	}
	public void setFoglio(String foglio) {
		String wFoglio = "\"Foglio\":{\"ci\":\"" + foglio + "\"}";
		if(this.cntFilter > 0) {wFoglio = SEP_FIELD_FILTER + wFoglio;}		
		this.foglio = wFoglio;
		this.cntFilter++;
	}
	public String getMappale() {
		return mappale;
	}
	public void setMappale(String mappale) {
		String wMappale = "\"Mappale\":{\"ci\":\"" + mappale + "\"}";
		if(this.cntFilter > 0) {wMappale = SEP_FIELD_FILTER + wMappale;}		
		this.mappale = wMappale;
		this.cntFilter++;
	}
	public String getSubalterno() {
		return subalterno;
	}
	public void setSubalterno(String subalterno) {
		String wSubalterno = "\"Subalterno\":{\"ci\":\"" + subalterno + "\"}";
		if(this.cntFilter > 0) {wSubalterno = SEP_FIELD_FILTER + wSubalterno;}		
		this.subalterno = wSubalterno;
		this.cntFilter++;
	}
	public String getNumeroFascicolo() {
		return numeroFascicolo;
	}
	public void setNumeroFascicolo(String numeroFascicolo) {
		String wNumeroFascicolo = "\"Numerofascicolo\":{\"ci\":\"" + numeroFascicolo + "\"}";
		if(this.cntFilter > 0) {wNumeroFascicolo = SEP_FIELD_FILTER + wNumeroFascicolo;}		
		this.numeroFascicolo = wNumeroFascicolo;
		this.cntFilter++;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		String wOggetto = "\"Oggetto\":{\"ci\":\"" + oggetto + "\"}";
		if(this.cntFilter > 0) {wOggetto = SEP_FIELD_FILTER + wOggetto;}		
		this.oggetto = wOggetto;
		this.cntFilter++;
	}
	public String getStatoPratica() {
		return statoPratica;
	}
	public void setStatoPratica(String statoPratica) {
		String wStatoPratica = "\"StatoPratica\":{\"ci\":\"" + statoPratica + "\"}";
		if(this.cntFilter > 0) {wStatoPratica = SEP_FIELD_FILTER + wStatoPratica;}		
		this.statoPratica = wStatoPratica;
		this.cntFilter++;
	}
	public String getIdentificativoPratica() {
		return identificativoPratica;
	}
	public void setIdentificativoPratica(String identificativoPratica) {
		String wIdentificativoPratica = "\"IdentificativoPratica\":{\"ci\":\"" + identificativoPratica + "\"}";
		if(this.cntFilter > 0) {wIdentificativoPratica = SEP_FIELD_FILTER + wIdentificativoPratica;}		
		this.identificativoPratica = wIdentificativoPratica;
		this.cntFilter++;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		String wNome = "\"Nome\":{\"ci\":\"" + nome + "\"}";
		if(this.cntFilter > 0) {wNome = SEP_FIELD_FILTER + wNome;}		
		this.nome = wNome;
		this.cntFilter++;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		String wCognome = "\"Cognome\":{\"ci\":\"" + cognome + "\"}";
		if(this.cntFilter > 0) {wCognome = SEP_FIELD_FILTER + wCognome;}		
		this.cognome = wCognome;
		this.cntFilter++;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		String wDenominazione = "\"Denominazione\":{\"ci\":\"" + denominazione + "\"}";
		if(this.cntFilter > 0) {wDenominazione = SEP_FIELD_FILTER + wDenominazione;}		
		this.denominazione = wDenominazione;
		this.cntFilter++;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		String wPartitaIva = "\"PartitaIva\":{\"ci\":\"" + partitaIva + "\"}";
		if(this.cntFilter > 0) {wPartitaIva = SEP_FIELD_FILTER + wPartitaIva;}		
		this.partitaIva = wPartitaIva;
		this.cntFilter++;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		String wCodiceFiscale = "\"CodiceFiscale\":{\"ci\":\"" + codiceFiscale + "\"}";
		if(this.cntFilter > 0) {wCodiceFiscale = SEP_FIELD_FILTER + wCodiceFiscale;}		
		this.codiceFiscale = wCodiceFiscale;
		this.cntFilter++;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		String wIndirizzo = "\"Indirizzo\":{\"ci\":\"" + indirizzo + "\"}";
		if(this.cntFilter > 0) {wIndirizzo = SEP_FIELD_FILTER + wIndirizzo;}		
		this.indirizzo = wIndirizzo;
		this.cntFilter++;
	}
	public String getCivico() {
		return civico;
	}
	public void setCivico(String civico) {
		String wCivico = "\"Civico\":{\"ci\":\"" + civico + "\"}";
		if(this.cntFilter > 0) {wCivico = SEP_FIELD_FILTER + wCivico;}		
		this.civico = wCivico;
		this.cntFilter++;

	}
}
