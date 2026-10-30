package it.wego.cross.cross_beans;
//import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
//import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root[] root = om.readValue(myJsonString, Root[].class); */

import java.util.ArrayList;
import java.util.Date;

public class ANAGRAFICA{
	public ArrayList<String> data_perdita_cittadinanza_italiana;
	public String flag_adozione;
	public ArrayList<String> data_acquisizione_cittadinanza_italiana;
	public String variazione_cognome;
	public String variazione_nome;
	public Date data_nascita;
	public ArrayList<String> cittadinanza;
	public String luogo_nascita;
	public String cognome;
	public String ora_nascita;
	public String sesso;
	public String nome;
	public String altri_nomi;
	public String particella_nobiliare;

	public ArrayList<String> getData_perdita_cittadinanza_italiana() {
		return data_perdita_cittadinanza_italiana;
	}
	public void setData_perdita_cittadinanza_italiana(ArrayList<String> data_perdita_cittadinanza_italiana) {
		this.data_perdita_cittadinanza_italiana = data_perdita_cittadinanza_italiana;
	}
	public String getFlag_adozione() {
		return flag_adozione;
	}
	public void setFlag_adozione(String flag_adozione) {
		this.flag_adozione = flag_adozione;
	}
	public ArrayList<String> getData_acquisizione_cittadinanza_italiana() {
		return data_acquisizione_cittadinanza_italiana;
	}
	public void setData_acquisizione_cittadinanza_italiana(ArrayList<String> data_acquisizione_cittadinanza_italiana) {
		this.data_acquisizione_cittadinanza_italiana = data_acquisizione_cittadinanza_italiana;
	}
	public String getVariazione_cognome() {
		return variazione_cognome;
	}
	public void setVariazione_cognome(String variazione_cognome) {
		this.variazione_cognome = variazione_cognome;
	}
	public String getVariazione_nome() {
		return variazione_nome;
	}
	public void setVariazione_nome(String variazione_nome) {
		this.variazione_nome = variazione_nome;
	}
	public Date getData_nascita() {
		return data_nascita;
	}
	public void setData_nascita(Date data_nascita) {
		this.data_nascita = data_nascita;
	}
	public ArrayList<String> getCittadinanza() {
		return cittadinanza;
	}
	public void setCittadinanza(ArrayList<String> cittadinanza) {
		this.cittadinanza = cittadinanza;
	}
	public String getLuogo_nascita() {
		return luogo_nascita;
	}
	public void setLuogo_nascita(String luogo_nascita) {
		this.luogo_nascita = luogo_nascita;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getOra_nascita() {
		return ora_nascita;
	}
	public void setOra_nascita(String ora_nascita) {
		this.ora_nascita = ora_nascita;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getAltri_nomi() {
		return altri_nomi;
	}
	public void setAltri_nomi(String altri_nomi) {
		this.altri_nomi = altri_nomi;
	}
	public String getParticella_nobiliare() {
		return particella_nobiliare;
	}
	public void setParticella_nobiliare(String particella_nobiliare) {
		this.particella_nobiliare = particella_nobiliare;
	}
}

