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

import java.util.List;

import it.people.console.dto.PageNavigationSettingsDTO;

/**
 * @author Luca Barbieri - Pradac Informatica S.r.l.
 * @created 16/nov/2011 12.41.31
 * 
 */
public class AccreditamentiQualificheFilter extends AbstractBaseBean implements Clearable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5623229606538564030L;

	private AccreditamentiQualifica qualifica;
	
	private List<String> qualifiche;

	private String error;

	private int res_count;
	private int actualPage;
	private int totalPages;
	
	private int pageSize;
	private PageNavigationSettingsDTO pageNavigationSettings;

	
	public AccreditamentiQualificheFilter() {
		this.setQualifica(new AccreditamentiQualifica());
		this.setPageNavigationSettings(new PageNavigationSettingsDTO());
	}

	/***/

	public void setQualifica(AccreditamentiQualifica qualifica) {
		this.qualifica = qualifica;
	}

	public AccreditamentiQualifica getQualifica() {
		return qualifica;
	}
	
	public void setQualifiche(List<String> qualifiche) {
		this.qualifiche = qualifiche;
	}

	public List<String> getQualifiche() {
		return qualifiche;
	}

	/***/
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}


	
	public void setPages(int start, int dim){
		if (dim == 0) {
			this.setActualPage(1);
			this.setTotalPages(1);
		} else {
			this.setActualPage((start/dim)+1);
		
			int intero = this.res_count/dim;
			float decimale = (float)this.res_count/(float)dim;
			if(decimale-intero>0) {
				this.setTotalPages(((int)this.res_count/dim)+1);
			} else {
				this.setTotalPages(((int)this.res_count/dim));
			}
		}
	}


    /***
	 * (non-Javadoc)
	 * 
	 * @see it.people.console.domain.Clearable#clear()
	 */
	public void clear() {
		this.qualifiche = null;
	    this.res_count = 0;
	}

	public void finalize() throws Throwable {
		super.finalize();
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

	public PageNavigationSettingsDTO getPageNavigationSettings() {
		return pageNavigationSettings;
	}
	public void setPageNavigationSettings(
			PageNavigationSettingsDTO pageNavigationSettings) {
		this.pageNavigationSettings = pageNavigationSettings;
	}




}
