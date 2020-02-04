package it.wego.cross.dto;

import java.util.Date;

import it.people.fsl.servizi.oggetticondivisi.Utente;
import it.wego.cross.xml.Pratica;

public class RisultatoCaricamentoPraticheDTO {
	
	private int idRisultatoCaricamento;

	private Date dataCaricamento;

	private String descrizioneErrore;

		
	private String esitoCaricamento;

	private Integer idUtente;
	
	private Integer idPratica;
	    
	private String identificativoPratica;
	
	private String nomeFileCaricato;
	
	public int getIdRisultatoCaricamento() {
		return this.idRisultatoCaricamento;
	}

	public void setIdRisultatoCaricamento(int idRisultatoCaricamento) {
		this.idRisultatoCaricamento = idRisultatoCaricamento;
	}

	public Date getDataCaricamento() {
		return this.dataCaricamento;
	}

	public void setDataCaricamento(Date dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}

	public String getDescrizioneErrore() {
		return this.descrizioneErrore;
	}

	public void setDescrizioneErrore(String descrizioneErrore) {
		this.descrizioneErrore = descrizioneErrore;
	}

	public String getEsitoCaricamento() {
		return this.esitoCaricamento;
	}

	public void setEsitoCaricamento(String esitoCaricamento) {
		this.esitoCaricamento = esitoCaricamento;
	}

	
	public String getIdentifcativoPratica() {
		return this.identificativoPratica;
	}

	public void setIdentificativoPratica(String identificativoPratica) {
		this.identificativoPratica = identificativoPratica;
	}
	public Integer getIdUtente() {
	        return idUtente;
	}

	public void setIdUtente(Integer idUtente) {
	        this.idUtente = idUtente;
	}

	public Integer getIdPratica() {
	        return idPratica;
	}

	public void setIdPratica(Integer idPratica) {
	        this.idPratica = idPratica;
	}

	public String getNomeFileCaricato() {
		return nomeFileCaricato;
	}

	public void setNomeFileCaricato(String nomeFileCaricato) {
		this.nomeFileCaricato = nomeFileCaricato;
	}


	
	

}
