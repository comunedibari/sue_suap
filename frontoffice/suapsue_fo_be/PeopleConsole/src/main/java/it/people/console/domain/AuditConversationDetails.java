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
package it.people.console.domain;

import java.util.Date;

import it.people.console.dto.ReportSettingsDTO;

/**
 * @author Luca Barbieri - Pradac Informatica S.r.l.
 * @created 16/giu/2011 12.19.50
 * 
 */
public class AuditConversationDetails extends AbstractBaseBean implements Clearable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7869244768087856050L;
	private Long id;
	private String sessionid;
	private java.util.Calendar pjp_time_stamp;
	private String np_first_name;
	private String np_last_name;
	private String np_tax_code;
	private String np_address;
	private String np_e_address;
	
	private boolean anon_user;
	private String commune_key;

	private String tipo_qualifica;
	private String descr_qualifica;
	private String operatore;
	
	private String richiedente;
	private boolean richiedente_utente;
	private String richiedente_first_name;
	private String richiedente_last_name;
	private String richiedente_tax_code;
	private String richiedente_address;
	private String richiedente_e_address;
	
	private String titolare;
	private boolean titolare_pf;
	private boolean titolare_utente;
	private boolean titolare_richiedente;
	private String titolare_first_name;
	private String titolare_last_name;
	private String titolare_tax_code;
	private String titolare_address;
	private String titolare_e_address;
	
	private String titolare_pg_business_name;
	private String titolare_pg_address;
	private String titolare_pg_tax_code;
	private String titolare_pg_vat_number;
	private String rapprLegale;
	private boolean rapprLegale_utente;
	
	private String c_action;
	private Date c_timestamp_date;
	private String c_message;
	private Long c_includexml;
	
	private String xmlIn;
	private String xmlOut;
	private String error;
	
	private ReportSettingsDTO reportSettings;

	
	public AuditConversationDetails() {

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public java.util.Calendar getPjp_time_stamp() {
		return pjp_time_stamp;
	}

	public void setPjp_time_stamp(java.util.Calendar pjp_time_stamp) {
		this.pjp_time_stamp = pjp_time_stamp;
	}

	public String getNp_first_name() {
		return np_first_name;
	}

	public void setNp_first_name(String np_first_name) {
		this.np_first_name = np_first_name;
	}

	public String getNp_last_name() {
		return np_last_name;
	}

	public void setNp_last_name(String np_last_name) {
		this.np_last_name = np_last_name;
	}

	public String getNp_tax_code() {
		return np_tax_code;
	}

	public void setNp_tax_code(String np_tax_code) {
		this.np_tax_code = np_tax_code;
	}

	public String getNp_address() {
		return np_address;
	}

	public void setNp_address(String np_address) {
		this.np_address = np_address;
	}

	public String getNp_e_address() {
		return np_e_address;
	}

	public void setNp_e_address(String np_e_address) {
		this.np_e_address = np_e_address;
	}

	

	public boolean isAnon_user() {
		return anon_user;
	}

	public void setAnon_user(boolean anon_user) {
		this.anon_user = anon_user;
	}

	public String getCommune_key() {
		return commune_key;
	}

	public void setCommune_key(String commune_key) {
		this.commune_key = commune_key;
	}
	
	public String getTipo_qualifica() {
		return tipo_qualifica;
	}

	public void setTipo_qualifica(String tipo_qualifica) {
		this.tipo_qualifica = tipo_qualifica;
	}

	public String getDescr_qualifica() {
		return descr_qualifica;
	}

	public void setDescr_qualifica(String descr_qualifica) {
		this.descr_qualifica = descr_qualifica;
	}

	public String getOperatore() {
		return operatore;
	}

	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}

	public String getRichiedente() {
		return richiedente;
	}

	public void setRichiedente(String richiedente) {
		this.richiedente = richiedente;
	}

	public boolean isRichiedente_utente() {
		return richiedente_utente;
	}

	public void setRichiedente_utente(boolean richiedente_utente) {
		this.richiedente_utente = richiedente_utente;
	}

	public String getRichiedente_first_name() {
		return richiedente_first_name;
	}

	public void setRichiedente_first_name(String richiedente_first_name) {
		this.richiedente_first_name = richiedente_first_name;
	}

	public String getRichiedente_last_name() {
		return richiedente_last_name;
	}

	public void setRichiedente_last_name(String richiedente_last_name) {
		this.richiedente_last_name = richiedente_last_name;
	}

	public String getRichiedente_tax_code() {
		return richiedente_tax_code;
	}

	public void setRichiedente_tax_code(String richiedente_tax_code) {
		this.richiedente_tax_code = richiedente_tax_code;
	}

	public String getRichiedente_address() {
		return richiedente_address;
	}

	public void setRichiedente_address(String richiedente_address) {
		this.richiedente_address = richiedente_address;
	}

	public String getRichiedente_e_address() {
		return richiedente_e_address;
	}

	public void setRichiedente_e_address(String richiedente_e_address) {
		this.richiedente_e_address = richiedente_e_address;
	}

	public String getTitolare() {
		return titolare;
	}

	public void setTitolare(String titolare) {
		this.titolare = titolare;
	}

	public void setTitolare_pf(boolean titolare_pf) {
		this.titolare_pf = titolare_pf;
	}

	public boolean isTitolare_pf() {
		return titolare_pf;
	}

	public boolean isTitolare_utente() {
		return titolare_utente;
	}
	
	public void setTitolare_utente(boolean titolare_utente) {
		this.titolare_utente = titolare_utente;
	}

	public boolean isTitolare_richiedente() {
		return titolare_richiedente;
	}
	
	public void setTitolare_richiedente(boolean titolare_richiedente) {
		this.titolare_richiedente = titolare_richiedente;
	}
	
	public String getTitolare_first_name() {
		return titolare_first_name;
	}

	public void setTitolare_first_name(String titolare_first_name) {
		this.titolare_first_name = titolare_first_name;
	}

	public String getTitolare_last_name() {
		return titolare_last_name;
	}

	public void setTitolare_last_name(String titolare_last_name) {
		this.titolare_last_name = titolare_last_name;
	}

	public String getTitolare_tax_code() {
		return titolare_tax_code;
	}

	public void setTitolare_tax_code(String titolare_tax_code) {
		this.titolare_tax_code = titolare_tax_code;
	}

	public String getTitolare_address() {
		return titolare_address;
	}

	public void setTitolare_address(String titolare_address) {
		this.titolare_address = titolare_address;
	}

	public String getTitolare_e_address() {
		return titolare_e_address;
	}

	public void setTitolare_e_address(String titolare_e_address) {
		this.titolare_e_address = titolare_e_address;
	}

	public String getTitolare_pg_business_name() {
		return titolare_pg_business_name;
	}

	public void setTitolare_pg_business_name(String titolare_pg_business_name) {
		this.titolare_pg_business_name = titolare_pg_business_name;
	}

	public String getTitolare_pg_address() {
		return titolare_pg_address;
	}

	public void setTitolare_pg_address(String titolare_pg_address) {
		this.titolare_pg_address = titolare_pg_address;
	}

	public String getTitolare_pg_tax_code() {
		return titolare_pg_tax_code;
	}

	public void setTitolare_pg_tax_code(String titolare_pg_tax_code) {
		this.titolare_pg_tax_code = titolare_pg_tax_code;
	}

	public String getTitolare_pg_vat_number() {
		return titolare_pg_vat_number;
	}

	public void setTitolare_pg_vat_number(String titolare_pg_vat_number) {
		this.titolare_pg_vat_number = titolare_pg_vat_number;
	}

	public String getRapprLegale() {
		return rapprLegale;
	}

	public void setRapprLegale(String rapprLegale) {
		this.rapprLegale = rapprLegale;
	}

	public boolean isRapprLegale_utente() {
		return rapprLegale_utente;
	}

	public void setRapprLegale_utente(boolean rapprLegale_utente) {
		this.rapprLegale_utente = rapprLegale_utente;
	}

	public String getC_action() {
		return c_action;
	}
	
	public void setC_action(String c_action) {
		this.c_action = c_action;
	}
	
	public Date getC_timestamp_date() {
		return c_timestamp_date;
	}
	
	public void setC_timestamp_date(Date c_timestamp_date) {
		this.c_timestamp_date = c_timestamp_date;
	}
	public String getC_message() {
		return c_message;
	}

	public void setC_message(String c_message) {
		this.c_message = c_message;
	}

	public Long getC_includexml() {
		return c_includexml;
	}

	public void setC_includexml(Long c_includexml) {
		this.c_includexml = c_includexml;
	}
	
	public String getXmlIn() {
		return xmlIn;
	}

	public void setXmlIn(String xmlIn) {
		this.xmlIn = xmlIn;
	}
	
	public String getXmlOut() {
		return xmlOut;
	}
	
	public void setXmlOut(String xmlOut) {
		this.xmlOut = xmlOut;
	}
	
	public void setError(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

	/* (non-Javadoc)
	 * @see it.people.console.domain.Clearable#clear()
	 */
	public void clear() {
		this.setTipo_qualifica(null);
		this.setDescr_qualifica(null);
		this.setOperatore(null);
		this.setRichiedente(null);
		this.setTitolare(null);
		this.setAnon_user(new Boolean(null));
		this.setCommune_key(null);
		this.setNp_address(null);
		this.setNp_e_address(null);
		this.setNp_first_name(null);
		this.setNp_last_name(null);
		this.setNp_tax_code(null);
		this.setPjp_time_stamp(null);
		this.setSessionid(null);
		this.setId(null);
	}

	/**
	 * @return the reportSettings
	 */
	public final ReportSettingsDTO getReportSettings() {
		return this.reportSettings;
	}

	/**
	 * @param reportSettings the reportSettings to set
	 */
	public final void setReportSettings(ReportSettingsDTO reportSettings) {
		this.reportSettings = reportSettings;
	}
}
