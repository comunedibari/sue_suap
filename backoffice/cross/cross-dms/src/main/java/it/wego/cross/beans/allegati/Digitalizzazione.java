package it.wego.cross.beans.allegati;

//Digitalizzazione.java
public class Digitalizzazione {
	private String flagIndirizzoDifferente;
	private String flagRichiedenteDifferente;
	private String flagPraticaMancante;
	private String note;
	private String nomeFile;

	public String getFlagIndirizzoDifferente() { return flagIndirizzoDifferente; }
	public void setFlagIndirizzoDifferente(String value) { this.flagIndirizzoDifferente = value; }

	public String getFlagRichiedenteDifferente() { return flagRichiedenteDifferente; }
	public void setFlagRichiedenteDifferente(String value) { this.flagRichiedenteDifferente = value; }

	public String getFlagPraticaMancante() { return flagPraticaMancante; }
	public void setFlagPraticaMancante(String value) { this.flagPraticaMancante = value; }

	public String getNote() { return note; }
	public void setNote(String value) { this.note = value; }

	public String getNomeFile() { return nomeFile; }
	public void setNomeFile(String value) { this.nomeFile = value; }
}
