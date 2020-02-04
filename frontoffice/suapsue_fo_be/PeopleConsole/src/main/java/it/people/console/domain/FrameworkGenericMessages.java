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

public class FrameworkGenericMessages extends AbstractBaseBean implements Clearable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8711323794655920115L;

	private String error;
	
	private String selectedServicePackage;
	
	private String selectedServicePackageTablevalue;

	private String selectedFrameworkLanguage;

	private String selectedServicesLanguage;

	private String previousSelectedServicePackage;

	private String previousSelectedFrameworkLanguage;

	private String previousSelectedServicesLanguage;

	private String selectedFrameworkRegisterableLanguage;

	private String selectedTableValuesTableId;
	
	private String selectedServicesRegisterableLanguage;
	
	public FrameworkGenericMessages(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * @return the error
	 */
	public final String getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public final void setError(String error) {
		this.error = error;
	}

	/**
	 * @return the selectedServicePackage
	 */
	public final String getSelectedServicePackage() {
		return this.selectedServicePackage;
	}

	/**
	 * @param selectedServicePackage the selectedServicePackage to set
	 */
	public final void setSelectedServicePackage(String selectedServicePackage) {
		this.selectedServicePackage = selectedServicePackage;
	}

	/**
	 * @return the selectedFrameworkLanguage
	 */
	public final String getSelectedFrameworkLanguage() {
		return this.selectedFrameworkLanguage;
	}

	/**
	 * @param selectedFrameworkLanguage the selectedFrameworkLanguage to set
	 */
	public final void setSelectedFrameworkLanguage(String selectedFrameworkLanguage) {
		this.selectedFrameworkLanguage = selectedFrameworkLanguage;
	}

	/**
	 * @return the selectedServicesLanguage
	 */
	public final String getSelectedServicesLanguage() {
		return this.selectedServicesLanguage;
	}

	/**
	 * @param selectedServicesLanguage the selectedServicesLanguage to set
	 */
	public final void setSelectedServicesLanguage(String selectedServicesLanguage) {
		this.selectedServicesLanguage = selectedServicesLanguage;
	}

	/**
	 * @return the previousSelectedServicePackage
	 */
	public String getPreviousSelectedServicePackage() {
		return previousSelectedServicePackage;
	}
	
	/**
	 * @param previousSelectedServicePackage the previousSelectedServicePackage to set
	 */
	public void setPreviousSelectedServicePackage(
			String previousSelectedServicePackage) {
		this.previousSelectedServicePackage = previousSelectedServicePackage;
	}
	
	/**
	 * @return the previousSelectedFrameworkLanguage
	 */
	public final String getPreviousSelectedFrameworkLanguage() {
		return this.previousSelectedFrameworkLanguage;
	}

	/**
	 * @param previousSelectedFrameworkLanguage the previousSelectedFrameworkLanguage to set
	 */
	public final void setPreviousSelectedFrameworkLanguage(
			String previousSelectedFrameworkLanguage) {
		this.previousSelectedFrameworkLanguage = previousSelectedFrameworkLanguage;
	}

	/**
	 * @return the previousSelectedServicesLanguage
	 */
	public final String getPreviousSelectedServicesLanguage() {
		return this.previousSelectedServicesLanguage;
	}

	/**
	 * @param previousSelectedServicesLanguage the previousSelectedServicesLanguage to set
	 */
	public final void setPreviousSelectedServicesLanguage(
			String previousSelectedServicesLanguage) {
		this.previousSelectedServicesLanguage = previousSelectedServicesLanguage;
	}

	/**
	 * @return the selectedFrameworkRegisterableLanguage
	 */
	public final String getSelectedFrameworkRegisterableLanguage() {
		return this.selectedFrameworkRegisterableLanguage;
	}

	/**
	 * @param selectedFrameworkRegisterableLanguage the selectedFrameworkRegisterableLanguage to set
	 */
	public final void setSelectedFrameworkRegisterableLanguage(
			String selectedFrameworkRegisterableLanguage) {
		this.selectedFrameworkRegisterableLanguage = selectedFrameworkRegisterableLanguage;
	}

	/**
	 * @return the selectedServicesRegisterableLanguage
	 */
	public final String getSelectedServicesRegisterableLanguage() {
		return this.selectedServicesRegisterableLanguage;
	}

	/**
	 * @param selectedServicesRegisterableLanguage the selectedServicesRegisterableLanguage to set
	 */
	public final void setSelectedServicesRegisterableLanguage(
			String selectedServicesRegisterableLanguage) {
		this.selectedServicesRegisterableLanguage = selectedServicesRegisterableLanguage;
	}

	/* (non-Javadoc)
	 * @see it.people.console.domain.Clearable#clear()
	 */
	public void clear() {
		this.setSelectedServicePackage(null);
		this.setSelectedFrameworkLanguage(null);
		this.setSelectedServicesLanguage(null);
		this.setPreviousSelectedServicePackage(null);
		this.setPreviousSelectedFrameworkLanguage(null);
		this.setPreviousSelectedServicesLanguage(null);
		this.setSelectedFrameworkRegisterableLanguage(null);
		this.setSelectedServicesRegisterableLanguage(null);
		this.setSelectedServicePackageTablevalue(null);
		this.setSelectedTableValuesTableId(null);

	}

	/**
	 * @return the selectedServicePackageTablevalues
	 */
	public String getSelectedServicePackageTablevalue() {
		return selectedServicePackageTablevalue;
	}

	/**
	 * @param selectedServicePackageTablevalue the selectedServicePackageTablevalues to set
	 */
	public void setSelectedServicePackageTablevalue(
			String selectedServicePackageTablevalue) {
		this.selectedServicePackageTablevalue = selectedServicePackageTablevalue;
	}

	/**
	 * @return the selectedTableValuesTableId
	 */
	public String getSelectedTableValuesTableId() {
		return selectedTableValuesTableId;
	}

	/**
	 * @param selectedTableValuesTableId the selectedTableValuesTableId to set
	 */
	public void setSelectedTableValuesTableId(String selectedTableValuesTableId) {
		this.selectedTableValuesTableId = selectedTableValuesTableId;
	}

}
