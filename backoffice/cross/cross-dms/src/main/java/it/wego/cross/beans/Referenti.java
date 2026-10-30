package it.wego.cross.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

//Referenti.java
public class Referenti {
	@JsonProperty("CODICE FISCALE")
	private String codiceFiscale;
	
	@JsonProperty("RUOLO")
	private String ruolo;
	
	@JsonProperty("PARTITA IVA")
	private String partitaIva;
	
	@JsonProperty("NOME")
	private String nome;

	@JsonProperty("COGNOME_DENOMINAZIONE")
	private String cognomeDenominazione;

	public String getCodiceFiscale() { return codiceFiscale; }
	public void setCodiceFiscale(String value) { this.codiceFiscale = value; }

	public String getRuolo() { return ruolo; }
	public void setRuolo(String value) { this.ruolo = value; }

	public String getPartitaIva() { return partitaIva; }
	public void setPartitaIva(String value) { this.partitaIva = value; }

	public String getNome() { return nome; }
	public void setNome(String value) { this.nome = value; }

	public String getCognomeDenominazione() { return cognomeDenominazione; }
	public void setCognomeDenominazione(String value) { this.cognomeDenominazione = value; }
}
