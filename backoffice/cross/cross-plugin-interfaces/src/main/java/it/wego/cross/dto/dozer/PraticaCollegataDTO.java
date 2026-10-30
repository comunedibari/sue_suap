/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import it.wego.cross.dto.Cartiglio;


/**
 *
 * @author Gabriele
 */
public class PraticaCollegataDTO implements Serializable {

    private Integer idPratica;
    private String identificativoPratica;
    private String oggettoPratica;
    private String esistenzaStradario;
    private String tipoPratica;
    private String numeroPratica;
    private String anno;
    private String numeroProtocollo;
    private String descrizioneStato;
    private Cartiglio cartiglio;
    private String inCarico;
	public Integer getIdPratica() {
		return idPratica;
	}
	public void setIdPratica(Integer idPratica) {
		this.idPratica = idPratica;
	}
	public String getIdentificativoPratica() {
		return identificativoPratica;
	}
	public void setIdentificativoPratica(String identificativoPratica) {
		this.identificativoPratica = identificativoPratica;
	}
	public String getOggettoPratica() {
		return oggettoPratica;
	}
	public void setOggettoPratica(String oggettoPratica) {
		this.oggettoPratica = oggettoPratica;
	}
	public String getEsistenzaStradario() {
		return esistenzaStradario;
	}
	public void setEsistenzaStradario(String esistenzaStradario) {
		this.esistenzaStradario = esistenzaStradario;
	}
	public String getTipoPratica() {
		return tipoPratica;
	}
	public void setTipoPratica(String tipoPratica) {
		this.tipoPratica = tipoPratica;
	}
	public String getNumeroPratica() {
		return numeroPratica;
	}
	public void setNumeroPratica(String numeroPratica) {
		this.numeroPratica = numeroPratica;
	}
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}
	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}
	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}
	public String getDescrizioneStato() {
		return descrizioneStato;
	}
	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}
	public Cartiglio getCartiglio() {
		return cartiglio;
	}
	public void setCartiglio(Cartiglio cartiglio) {
		this.cartiglio = cartiglio;
	}
	public String getInCarico() {
		return inCarico;
	}
	public void setInCarico(String inCarico) {
		this.inCarico = inCarico;
	}
}
