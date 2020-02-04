package it.wego.cross.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class EstrazioniAgibDTO extends AbstractDTO implements Serializable{
	
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
	private Date data_ric_integrazione;
	private Date data_Integrazione;
	private Date data_parere_positivo;
	private Date data_parere_contrario;
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
	public String getIn_carico_a() {
		return in_carico_a;
	}
	public void setIn_carico_a(String in_carico_a) {
		this.in_carico_a = in_carico_a;
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
	public Date getData_ric_integrazione() {
		return data_ric_integrazione;
	}
	public void setData_ric_integrazione(Date data_ric_integrazione) {
		this.data_ric_integrazione = data_ric_integrazione;
	}
	public Date getData_Integrazione() {
		return data_Integrazione;
	}
	public void setData_Integrazione(Date data_Integrazione) {
		this.data_Integrazione = data_Integrazione;
	}
	public Date getData_parere_positivo() {
		return data_parere_positivo;
	}
	public void setData_parere_positivo(Date data_parere_positivo) {
		this.data_parere_positivo = data_parere_positivo;
	}
	public Date getData_parere_contrario() {
		return data_parere_contrario;
	}
	public void setData_parere_contrario(Date data_parere_contrario) {
		this.data_parere_contrario = data_parere_contrario;
	}
	public Date getData_chiusura() {
		return data_chiusura;
	}
	public void setData_chiusura(Date data_chiusura) {
		this.data_chiusura = data_chiusura;
	}
	public String getIstruttore() {
		return istruttore;
	}
	public void setIstruttore(String istruttore) {
		this.istruttore = istruttore;
	}
	
	
}

