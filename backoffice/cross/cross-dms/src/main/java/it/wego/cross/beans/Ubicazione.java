package it.wego.cross.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

//Ubicazione.java

public class Ubicazione {
	@JsonProperty("PIANO")
	private String piano;

	@JsonProperty("LONGITUDINE")
	private Object longitudine;

	@JsonProperty("NUMERO CIVICO")
	private String numeroCivico;
	
	@JsonProperty("SCALA")
	private String scala;
	
	@JsonProperty("INDIRIZZO DELLA PRATICA")
	private String indirizzoDellaPratica;
	
	@JsonProperty("LATITUDINE")
	private Object latitudine;

	@JsonProperty("INTERNO")
	private String interno;

	@JsonProperty("FRAZIONE_LOCALITÀ")
	private String frazioneLocalita;

	public String getPiano() { return piano; }
	public void setPiano(String value) { this.piano = value; }

	public Object getLongitudine() { return longitudine; }
	public void setLongitudine(Object value) { this.longitudine = value; }

	public String getNumeroCivico() { return numeroCivico; }
	public void setNumeroCivico(String value) { this.numeroCivico = value; }

	public String getScala() { return scala; }
	public void setScala(String value) { this.scala = value; }

	public String getIndirizzoDellaPratica() { return indirizzoDellaPratica; }
	public void setIndirizzoDellaPratica(String value) { this.indirizzoDellaPratica = value; }

	public Object getLatitudine() { return latitudine; }
	public void setLatitudine(Object value) { this.latitudine = value; }

	public String getInterno() { return interno; }
	public void setInterno(String value) { this.interno = value; }

	public String getFrazioneLocalita() {
		return frazioneLocalita;
	}
	public void setFrazioneLocalita(String frazioneLocalita) {
		this.frazioneLocalita = frazioneLocalita;
	}
}
