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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import it.people.console.dto.PageNavigationSettingsDTO;
import it.people.console.dto.ReportSettingsDTO;

/**
 * @author Luca Barbieri - Pradac Informatica S.r.l.
 * @created 04/mag/2011 12.41.31
 * 
 */
public class AuditConversationFilter extends AbstractBaseBean implements Clearable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6362268498423327586L;
	private int tipoUtente;
	private String taxCode;
	private String firstName;
	private String lastName;
	private String businessName;
	private String firstNameNp;
	private String lastNameNp;
	
	private String serviceName;
	private int serviceId;
	private String attivita;
	
	private String from; 
	private String to; 
    
	private String error;

	private int res_count;
	private int actualPage;
	private int totalPages;
	
	private int pageSize;
	
	private ReportSettingsDTO reportSettings;
	private PageNavigationSettingsDTO pageNavigationSettings;
	
	public AuditConversationFilter() {
		this.setReportSettings(new ReportSettingsDTO());
		this.setPageNavigationSettings(new PageNavigationSettingsDTO());
	}
	/***/

	public void setTipoUtente(int tipoUtente) {
		this.tipoUtente = tipoUtente;
	}

	public int getTipoUtente() {
		return tipoUtente;
	}

	
	public final String getTaxCode() {
		return taxCode;
	}

	public final void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getFirstNameNp() {
		return firstNameNp;
	}
	public void setFirstNameNp(String firstNameNp) {
		this.firstNameNp = firstNameNp;
	}
	public String getLastNameNp() {
		return lastNameNp;
	}
	public void setLastNameNp(String lastNameNp) {
		this.lastNameNp = lastNameNp;
	}
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public int getServiceId() {
		return serviceId;
	}
	
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public void setAttivita(String attivita) {
		this.attivita = attivita;
	}
	public String getAttivita() {
		return attivita;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}

	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @param res_count the res_count to set
	 */
	public void setRes_count(int res_count) {
		this.res_count = res_count;
	}

	/**
	 * @return the res_count
	 */
	public int getRes_count() {
		return res_count;
	}
	
	public void setPages(int start, int dim){
		if (dim == 0) {
			this.setActualPage(1);
			this.setTotalPages(1);
		} else {
			this.setActualPage((start / dim) + 1);

			int intero = this.res_count / dim;
			float decimale = (float) this.res_count / (float) dim;
			if (decimale - intero > 0) {
				this.setTotalPages(((int) this.res_count / dim) + 1);
			} else {
				this.setTotalPages(((int) this.res_count / dim));
			}
		}
	}

	/***/
	
    public void setToDate(Calendar calendar) {

    	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); 
    	this.to = formatter.format(calendar.getTime()); 
    }

    public void setFromDate(Calendar calendar) {
    	
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); 
		this.from = formatter.format(calendar.getTime()); 
    }
    
    public Calendar getToDate() {
    	return getDate(this.to);
    }
    
    public Calendar getFromDate() {
    	return getDate(this.from);
    }
        

    protected Calendar getDate(String inputFrom) {
    	try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date date = (Date) formatter.parse(inputFrom);
			Calendar from = Calendar.getInstance();
			from.setTime(date);
    		return from;
    	}
    	catch(Exception ex) {
    		return null;
    	}
    }
    
    /***
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.people.console.domain.Clearable#clear()
	 */
	public void clear() {
		this.tipoUtente = 0;
		this.taxCode = null;
		this.firstName = null;
		this.lastName = null;
		this.businessName = null;
		this.firstNameNp = null;
		this.lastNameNp = null;
		this.serviceName = null;
		this.serviceId = 0;
		this.attivita = null;
	    this.from = null;
	    this.to = null;
	    this.res_count = 0;
	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * @param totalPages the totalPages to set
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * @return the totalPages
	 */
	public int getTotalPages() {
		return totalPages;
	}

	/**
	 * @param actualPage the actualPage to set
	 */
	public void setActualPage(int actualPage) {
		this.actualPage = actualPage;
	}

	/**
	 * @return the actualPage
	 */
	public int getActualPage() {
		return actualPage;
	}

	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
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
	public PageNavigationSettingsDTO getPageNavigationSettings() {
		return pageNavigationSettings;
	}
	public void setPageNavigationSettings(
			PageNavigationSettingsDTO pageNavigationSettings) {
		this.pageNavigationSettings = pageNavigationSettings;
	}	

}
