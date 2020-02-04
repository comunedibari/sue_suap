package it.exprivia.pal.avbari.suapsue.dto;

import java.io.Serializable;

public class SportelloEnte implements Serializable {
	
	private static final long serialVersionUID = 5707559463133643507L;
	
	
	protected Long id;
	
	protected TipoPratica tipo;
	
	protected String denominazione;
	
	protected Integer codSportello;
	
	protected Integer codComune;
	
	protected boolean flagAttivo;
	
	protected String url;
	
	protected Integer comuneEgov;
	
	protected String urlPagina;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public TipoPratica getTipo() {
		return tipo;
	}
	public void setTipo(TipoPratica tipo) {
		this.tipo = tipo;
	}
	
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
	public Integer getCodSportello() {
		return codSportello;
	}
	public void setCodSportello(Integer codSportello) {
		this.codSportello = codSportello;
	}		
	public Integer getCodComune() {
		return codComune;
	}
	public void setCodComune(Integer codComune) {
		this.codComune = codComune;
	}
		
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
				
	public Integer getComuneEgov() {
		return comuneEgov;
	}
	public void setComuneEgov(Integer comuneEgov) {
		this.comuneEgov = comuneEgov;
	}
	
	public String getUrlPagina() {
		return urlPagina;
	}
	public void setUrlPagina(String urlPagina) {
		this.urlPagina = urlPagina;
	}
	public boolean isFlagAttivo() {
		return flagAttivo;
	}
	public void setFlagAttivo(boolean flagAttivo) {
		this.flagAttivo = flagAttivo;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SportelloEnte [id=");
		builder.append(id);
		builder.append(", tipo=");
		builder.append(tipo);
		builder.append(", denominazione=");
		builder.append(denominazione);
		builder.append(", codSportello=");
		builder.append(codSportello);
		builder.append(", codComune=");
		builder.append(codComune);
		builder.append(", flag_attivo=");
		builder.append(flagAttivo);
		builder.append(", url=");
		builder.append(url);
		builder.append(comuneEgov);
		builder.append(urlPagina);
		builder.append("]");
		return builder.toString();
	}
}