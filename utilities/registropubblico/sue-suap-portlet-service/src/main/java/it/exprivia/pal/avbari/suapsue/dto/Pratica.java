package it.exprivia.pal.avbari.suapsue.dto;

import java.io.Serializable;
import java.math.BigInteger;

public class Pratica implements Serializable {
	
	private static final long serialVersionUID = -8338762077414757188L;
	
	protected BigInteger idPratica;
	
	protected String identificativoPratica;
	
	protected BigInteger idEnte;
	
	protected String descrizioneEnte;
	
	protected BigInteger idComune;
	
	protected String descrizioneComune;
	
	protected String oggetto;
	
	protected String dataRicezione;
	
	protected String codiceStatoPratica;
	
	protected String descrizioneStatoPratica;
	
	protected String ubicazione;
	
	
	public BigInteger getIdPratica() {
		return idPratica;
	}
	public void setIdPratica(BigInteger idPratica) {
		this.idPratica = idPratica;
	}
	
	public String getIdentificativoPratica() {
		return identificativoPratica;
	}
	public void setIdentificativoPratica(String identificativoPratica) {
		this.identificativoPratica = identificativoPratica;
	}
	
	public BigInteger getIdEnte() {
		return idEnte;
	}
	public void setIdEnte(BigInteger idEnte) {
		this.idEnte = idEnte;
	}
	
	public String getDescrizioneEnte() {
		return descrizioneEnte;
	}
	public void setDescrizioneEnte(String descrizioneEnte) {
		this.descrizioneEnte = descrizioneEnte;
	}
	
	public BigInteger getIdComune() {
		return idComune;
	}
	public void setIdComune(BigInteger idComune) {
		this.idComune = idComune;
	}
	
	public String getDescrizioneComune() {
		return descrizioneComune;
	}
	public void setDescrizioneComune(String descrizioneComune) {
		this.descrizioneComune = descrizioneComune;
	}
	
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	
	public String getDataRicezione() {
		return dataRicezione;
	}
	public void setDataRicezione(String dataRicezione) {
		this.dataRicezione = dataRicezione;
	}
	
	public String getCodiceStatoPratica() {
		return codiceStatoPratica;
	}
	public void setCodiceStatoPratica(String codiceStatoPratica) {
		this.codiceStatoPratica = codiceStatoPratica;
	}
	
	public String getDescrizioneStatoPratica() {
		return descrizioneStatoPratica;
	}
	public void setDescrizioneStatoPratica(String descrizioneStatoPratica) {
		this.descrizioneStatoPratica = descrizioneStatoPratica;
	}
	
	public String getUbicazione() {
		return ubicazione;
	}
	public void setUbicazione(String ubicazione) {
		this.ubicazione = ubicazione;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pratica [idPratica=");
		builder.append(idPratica);
		builder.append(", identificativoPratica=");
		builder.append(identificativoPratica);
		builder.append(", idEnte=");
		builder.append(idEnte);
		builder.append(", descrizioneEnte=");
		builder.append(descrizioneEnte);
		builder.append(", idComune=");
		builder.append(idComune);
		builder.append(", descrizioneComune=");
		builder.append(descrizioneComune);
		builder.append(", oggetto=");
		builder.append(oggetto);
		builder.append(", dataRicezione=");
		builder.append(dataRicezione);
		builder.append(", codiceStatoPratica=");
		builder.append(codiceStatoPratica);
		builder.append(", descrizioneStatoPratica=");
		builder.append(descrizioneStatoPratica);
		builder.append(", ubicazione=");
		builder.append(ubicazione);
		builder.append("]");
		return builder.toString();
	}
}