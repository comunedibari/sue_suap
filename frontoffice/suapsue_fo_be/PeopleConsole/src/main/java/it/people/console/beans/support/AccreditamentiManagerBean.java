/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 *  
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * 
 * For convenience a plain text copy of the English version
 * of the Licence can be found in the file LICENCE.txt in
 * the top-level directory of this software distribution.
 * 
 * You may obtain a copy of the Licence in any of 22 European
 * Languages at:
 * 
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.console.beans.support;

/**
 * @author Luca Barbieri - Pradac Informatica S.r.l.
 * @created 04/lug/2011 15.49.13
 * 
 */
public class AccreditamentiManagerBean {

	private int id;
	private String tipoQualifica;
	private String idQualifica;
	private String nomeEnte;
	private String idComune;
	private String taxcodeUtente;

	private String taxcodeIntermediario;
	private String vatnumberIntermediario;
	private String e_addressIntermediario;
	private String tipologiaIntermediario;
	private String sedeLegale;
	private String statoAccreditamento;
	private boolean attivo;
	private boolean deleted;
	private String auto_certificazione_filename;
	
	private String detailPagelink;
	private boolean hasRappresentanteLegale;
	
	private String rappLegale_first_name;
	private String rappLegale_last_name;
	private String rappLegale_tax_code;
	private String rappLegale_address;
	private String rappLegale_e_address;


	
	
	public AccreditamentiManagerBean() {
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getTipoQualifica() {
		return tipoQualifica;
	}

	public void setTipoQualifica(String tipoQualifica) {
		this.tipoQualifica = tipoQualifica;
	}

	public void setIdQualifica(String idQualifica) {
		this.idQualifica = idQualifica;
	}

	public String getIdQualifica() {
		return idQualifica;
	}

	public String getNomeEnte() {
		return nomeEnte;
	}

	public void setNomeEnte(String nomeEnte) {
		this.nomeEnte = nomeEnte;
	}

	public void setIdComune(String idComune) {
		this.idComune = idComune;
	}

	public String getIdComune() {
		return idComune;
	}

	public String getTaxcodeUtente() {
		return taxcodeUtente;
	}

	public void setTaxcodeUtente(String taxcodeUtente) {
		this.taxcodeUtente = taxcodeUtente;
	}

	public String getTaxcodeIntermediario() {
		return taxcodeIntermediario;
	}

	public void setTaxcodeIntermediario(String taxcodeIntermediario) {
		this.taxcodeIntermediario = taxcodeIntermediario;
	}

	public String getVatnumberIntermediario() {
		return vatnumberIntermediario;
	}

	public void setVatnumberIntermediario(String vatnumberIntermediario) {
		this.vatnumberIntermediario = vatnumberIntermediario;
	}

	public String getE_addressIntermediario() {
		return e_addressIntermediario;
	}

	public void setE_addressIntermediario(String e_addressIntermediario) {
		this.e_addressIntermediario = e_addressIntermediario;
	}

	public String getTipologiaIntermediario() {
		return tipologiaIntermediario;
	}

	public void setTipologiaIntermediario(String tipologiaIntermediario) {
		this.tipologiaIntermediario = tipologiaIntermediario;
	}

	public String getSedeLegale() {
		return sedeLegale;
	}

	public void setSedeLegale(String sedeLegale) {
		this.sedeLegale = sedeLegale;
	}

	public String getStatoAccreditamento() {
		return statoAccreditamento;
	}

	public void setStatoAccreditamento(String statoAccreditamento) {
		this.statoAccreditamento = statoAccreditamento;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setAuto_certificazione_filename(
			String auto_certificazione_filename) {
		this.auto_certificazione_filename = auto_certificazione_filename;
	}

	public String getAuto_certificazione_filename() {
		return auto_certificazione_filename;
	}

	public String getDetailPagelink() {
		return detailPagelink;
	}

	public void setDetailPagelink(String detailPagelink) {
		this.detailPagelink = detailPagelink;
	}

	public void setHasRappresentanteLegale(boolean hasRappresentanteLegale) {
		this.hasRappresentanteLegale = hasRappresentanteLegale;
	}

	public boolean isHasRappresentanteLegale() {
		return hasRappresentanteLegale;
	}

	public String getRappLegale_first_name() {
		return rappLegale_first_name;
	}

	public void setRappLegale_first_name(String rappLegale_first_name) {
		this.rappLegale_first_name = rappLegale_first_name;
	}

	public String getRappLegale_last_name() {
		return rappLegale_last_name;
	}

	public void setRappLegale_last_name(String rappLegale_last_name) {
		this.rappLegale_last_name = rappLegale_last_name;
	}

	public String getRappLegale_tax_code() {
		return rappLegale_tax_code;
	}

	public void setRappLegale_tax_code(String rappLegale_tax_code) {
		this.rappLegale_tax_code = rappLegale_tax_code;
	}

	public String getRappLegale_address() {
		return rappLegale_address;
	}

	public void setRappLegale_address(String rappLegale_address) {
		this.rappLegale_address = rappLegale_address;
	}

	public String getRappLegale_e_address() {
		return rappLegale_e_address;
	}

	public void setRappLegale_e_address(String rappLegale_e_address) {
		this.rappLegale_e_address = rappLegale_e_address;
	}

}
