package it.wego.cross.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class EstrazioniPdcDTO extends AbstractDTO implements Serializable{
	
	@NotNull(message = "{error.idPratica}")
	private Integer id_Pratica;
	@NotNull(message = "{error.identificativoPratica}")
    @NotEmpty(message = "{error.identificativoPratica}")
	private String identificativo_pratica;
	private String stato;
	@NotNull(message = "{error.protocolloManualeNUll}")
    @NotEmpty(message = "{error.protocolloManualeNUll}")
    private String protocollo;
	private String in_carico_a;
	private Date data_ricezione;
	private Date data_protocollazione;
	private Date data_avvio_pre_diniego;
	private Date data_rice_contrDeduz_pre_diniego;
	private Date data_rich_integr_art20 ;
	private Date data_rice_integr_art20 ;
	private Date data_rich_adempimenti;
	private Date data_rice_adempimenti ;
	private Date data_rilascio;
	private Date data_diniego_def;
	private Date data_chiusura;
	private String istruttore;
	
	public Integer getId_Pratica() {
		return id_Pratica;
	}
	public void setId_Pratica(Integer id_Pratica) {
		this.id_Pratica = id_Pratica;
	}
	public String getIdentificativo_pratica() {
		return identificativo_pratica;
	}
	public void setIdentificativo_pratica(String identificativo_pratica) {
		this.identificativo_pratica = identificativo_pratica;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getProtocollo() {
		return protocollo;
	}
	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}
	public Date getData_ricezione() {
		return data_ricezione;
	}
	public void setData_ricezione(Date data_ricezione) {
		this.data_ricezione = data_ricezione;
	}
	public Date getData_protocollazione() {
		return data_protocollazione;
	}
	public void setData_protocollazione(Date data_protocollazione) {
		this.data_protocollazione = data_protocollazione;
	}
	public Date getData_avvio_pre_diniego() {
		return data_avvio_pre_diniego;
	}
	public void setData_avvio_pre_diniego(Date data_avvio_pre_diniego) {
		this.data_avvio_pre_diniego = data_avvio_pre_diniego;
	}
	public Date getData_rice_contrDeduz_pre_diniego() {
		return data_rice_contrDeduz_pre_diniego;
	}
	public void setData_rice_contrDeduz_pre_diniego(Date data_rice_contrDeduz_pre_diniego) {
		this.data_rice_contrDeduz_pre_diniego = data_rice_contrDeduz_pre_diniego;
	}
	public Date getData_rich_integr_art20() {
		return data_rich_integr_art20;
	}
	public void setData_rich_integr_art20(Date data_rich_integr_art20) {
		this.data_rich_integr_art20 = data_rich_integr_art20;
	}
	public Date getData_rice_integr_art20() {
		return data_rice_integr_art20;
	}
	public void setData_rice_integr_art20(Date data_rice_integr_art20) {
		this.data_rice_integr_art20 = data_rice_integr_art20;
	}
	public Date getData_rich_adempimenti() {
		return data_rich_adempimenti;
	}
	public void setData_rich_adempimenti(Date data_rich_adempimenti) {
		this.data_rich_adempimenti = data_rich_adempimenti;
	}
	public Date getData_rice_adempimenti() {
		return data_rice_adempimenti;
	}
	public void setData_rice_adempimenti(Date data_rice_adempimenti) {
		this.data_rice_adempimenti = data_rice_adempimenti;
	}
	public Date getData_rilascio() {
		return data_rilascio;
	}
	public void setData_rilascio(Date data_rilascio) {
		this.data_rilascio = data_rilascio;
	}
	public Date getData_diniego_def() {
		return data_diniego_def;
	}
	public void setData_diniego_def(Date data_diniego_def) {
		this.data_diniego_def = data_diniego_def;
	}
	public Date getData_chiusura() {
		return data_chiusura;
	}
	public void setData_chiusura(Date data_chiusura) {
		this.data_chiusura = data_chiusura;
	}
	public String getIn_carico_a() {
		return in_carico_a;
	}
	public void setIn_carico_a(String in_carico_a) {
		this.in_carico_a = in_carico_a;
	}
	public String getIstruttore() {
		return istruttore;
	}
	public void setIstruttore(String istruttore) {
		this.istruttore = istruttore;
	}
	
	
}

