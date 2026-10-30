package it.wego.cross.cross_beans;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ANAGRAFICACONIUGE{
	public ArrayList<String> cognome_coniuge;
	@JsonProperty("MATRIMONIO") 
	public MATRIMONIO mATRIMONIO;
	public ArrayList<String> particella_nobiliare_coniuge;
	@JsonProperty("UNIONE CIVILE") 
	public UNIONECIVILE uNIONE_CIVILE;
	@JsonProperty("DIVORZIO") 
	public DIVORZIO dIVORZIO;
	public ArrayList<String> nome_coniuge;
	
	public ArrayList<String> getCognome_coniuge() {
		return cognome_coniuge;
	}
	public void setCognome_coniuge(ArrayList<String> cognome_coniuge) {
		this.cognome_coniuge = cognome_coniuge;
	}
	public MATRIMONIO getmATRIMONIO() {
		return mATRIMONIO;
	}
	public void setmATRIMONIO(MATRIMONIO mATRIMONIO) {
		this.mATRIMONIO = mATRIMONIO;
	}
	public ArrayList<String> getParticella_nobiliare_coniuge() {
		return particella_nobiliare_coniuge;
	}
	public void setParticella_nobiliare_coniuge(ArrayList<String> particella_nobiliare_coniuge) {
		this.particella_nobiliare_coniuge = particella_nobiliare_coniuge;
	}
	public UNIONECIVILE getuNIONE_CIVILE() {
		return uNIONE_CIVILE;
	}
	public void setuNIONE_CIVILE(UNIONECIVILE uNIONE_CIVILE) {
		this.uNIONE_CIVILE = uNIONE_CIVILE;
	}
	public DIVORZIO getdIVORZIO() {
		return dIVORZIO;
	}
	public void setdIVORZIO(DIVORZIO dIVORZIO) {
		this.dIVORZIO = dIVORZIO;
	}
	public ArrayList<String> getNome_coniuge() {
		return nome_coniuge;
	}
	public void setNome_coniuge(ArrayList<String> nome_coniuge) {
		this.nome_coniuge = nome_coniuge;
	}
	
}