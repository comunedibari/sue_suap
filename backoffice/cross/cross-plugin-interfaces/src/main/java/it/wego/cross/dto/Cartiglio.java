package it.wego.cross.dto;

import java.util.Date;
import java.util.List;

public class Cartiglio {

	private String codiceFrontOffice;
	private Integer codiceInterno;
	private String pulsanteScaricaPratica;
	private String pulsanteAssegnazione;
	private String tipoPratica;
	private String codTipoPratica;
	private Integer numeroPratica;
	private Integer annoPratica;
	private String numeroFascicolo;
	private Integer annoFascicolo;
	private String numeroProtocollo;
	private Date dataProtocollo;
	private String numeroProtocolloSuap;
	private Date dataProtocolloSuap;
	private Date dataRicezione;
	private it.wego.cross.dto.dozer.AnagraficaDTO procuratore;
	private it.wego.cross.dto.dozer.AnagraficaDTO assegnatario;
	private String stato;
	private List<String> tipologiaIntervento;
	private String oggettoIntervento;
	
	private Integer idUtente;
	private Integer idModello;
	private Integer idPratica;
	private Integer idPraticaEvento;
	private boolean civilia;
	private Boolean isSuapData;
	
	public String getCodiceFrontOffice() {
		return codiceFrontOffice;
	}
	public void setCodiceFrontOffice(String codiceFrontOffice) {
		this.codiceFrontOffice = codiceFrontOffice;
	}
	public Integer getCodiceInterno() {
		return codiceInterno;
	}
	public void setCodiceInterno(Integer codiceInterno) {
		this.codiceInterno = codiceInterno;
	}
	public String getPulsanteScaricaPratica() {
		return pulsanteScaricaPratica;
	}
	public void setPulsanteScaricaPratica(String pulsanteScaricaPratica) {
		this.pulsanteScaricaPratica = pulsanteScaricaPratica;
	}
	public String getPulsanteAssegnazione() {
		return pulsanteAssegnazione;
	}
	public void setPulsanteAssegnazione(String pulsanteAssegnazione) {
		this.pulsanteAssegnazione = pulsanteAssegnazione;
	}
	public String getTipoPratica() {
		return tipoPratica;
	}
	public void setTipoPratica(String tipoPratica) {
		this.tipoPratica = tipoPratica;
	}
	public Integer getNumeroPratica() {
		return numeroPratica;
	}
	public void setNumeroPratica(Integer numeroPratica) {
		this.numeroPratica = numeroPratica;
	}
	public Integer getAnnoPratica() {
		return annoPratica;
	}
	public void setAnnoPratica(Integer annoPratica) {
		this.annoPratica = annoPratica;
	}
	public String getNumeroFascicolo() {
		return numeroFascicolo;
	}
	public void setNumeroFascicolo(String numeroFascicolo) {
		this.numeroFascicolo = numeroFascicolo;
	}
	public Integer getAnnoFascicolo() {
		return annoFascicolo;
	}
	public void setAnnoFascicolo(Integer annoFascicolo) {
		this.annoFascicolo = annoFascicolo;
	}
	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}
	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}
	public Date getDataProtocollo() {
		return dataProtocollo;
	}
	public void setDataProtocollo(Date dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}
	public String getNumeroProtocolloSuap() {
		return numeroProtocolloSuap;
	}
	public void setNumeroProtocolloSuap(String numeroProtocolloSuap) {
		this.numeroProtocolloSuap = numeroProtocolloSuap;
	}
	public Date getDataProtocolloSuap() {
		return dataProtocolloSuap;
	}
	public void setDataProtocolloSuap(Date dataProtocolloSuap) {
		this.dataProtocolloSuap = dataProtocolloSuap;
	}
	public Date getDataRicezione() {
		return dataRicezione;
	}
	public void setDataRicezione(Date dataRicezione) {
		this.dataRicezione = dataRicezione;
	}
	public it.wego.cross.dto.dozer.AnagraficaDTO getProcuratore() {
		return procuratore;
	}
	public void setProcuratore(it.wego.cross.dto.dozer.AnagraficaDTO procuratore) {
		this.procuratore = procuratore;
	}
	public it.wego.cross.dto.dozer.AnagraficaDTO getAssegnatario() {
		return assegnatario;
	}
	public void setAssegnatario(it.wego.cross.dto.dozer.AnagraficaDTO assegnatario) {
		this.assegnatario = assegnatario;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public List<String> getTipologiaIntervento() {
		return tipologiaIntervento;
	}
	public void setTipologiaIntervento(List<String> tipologiaIntervento) {
		this.tipologiaIntervento = tipologiaIntervento;
	}
	public String getOggettoIntervento() {
		return oggettoIntervento;
	}
	public void setOggettoIntervento(String oggettoIntervento) {
		this.oggettoIntervento = oggettoIntervento;
	}
	public Integer getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(Integer idUtente) {
		this.idUtente = idUtente;
	}
	public Integer getIdModello() {
		return idModello;
	}
	public void setIdModello(Integer idModello) {
		this.idModello = idModello;
	}
	public Integer getIdPratica() {
		return idPratica;
	}
	public void setIdPratica(Integer idPratica) {
		this.idPratica = idPratica;
	}
	public Integer getIdPraticaEvento() {
		return idPraticaEvento;
	}
	public void setIdPraticaEvento(Integer idPraticaEvento) {
		this.idPraticaEvento = idPraticaEvento;
	}
	public boolean isCivilia() {
		return civilia;
	}
	public void setCivilia(boolean civilia) {
		this.civilia = civilia;
	}
	
	public boolean showSuapData() {
		return isSuapData;
	}
	public void setSuapData(boolean isSuapData) {
		this.isSuapData = isSuapData;
	}
	public String getCodTipoPratica() {
		return codTipoPratica;
	}
	public void setCodTipoPratica(String codTipoPratica) {
		this.codTipoPratica = codTipoPratica;
	}

	
}
