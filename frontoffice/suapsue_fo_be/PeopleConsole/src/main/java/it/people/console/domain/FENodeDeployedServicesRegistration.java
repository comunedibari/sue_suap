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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.people.feservice.beans.AvailableService;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 30/gen/2011 22.43.34
 *
 */
public class FENodeDeployedServicesRegistration implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5696745179398227796L;

	private long selectedNodeId;
	
	private String selectedNodeName;
	
	private List<AvailableService> availableServices = new ArrayList<AvailableService>();

	private List<AvailableService> alreadyRegisteredServices = new ArrayList<AvailableService>();
	
	private List<String> selectedServicesPackages = new ArrayList<String>();
	
	private String backEndURL;

	private String logicalNamesPrefix = "";
	
	private String logicalNamesSuffix = "";
	
	private String servicesPrefix = "";
	
	private String servicesSuffix = "";
	
	private int page;
	
	private int wizardPages;
	
	public FENodeDeployedServicesRegistration(final int wizardPages) {
		this.setPage(1);
		this.setWizardPages(wizardPages);
	}

	/**
	 * @return the selectedNodeId
	 */
	public final long getSelectedNodeId() {
		return selectedNodeId;
	}

	/**
	 * @param selectedNodeId the selectedNodeId to set
	 */
	public final void setSelectedNodeId(long selectedNodeId) {
		this.selectedNodeId = selectedNodeId;
	}

	/**
	 * @return the selectedServices
	 */
	public final List<AvailableService> getAvailableServices() {
		return availableServices;
	}

	/**
	 * @param selectedServices the selectedServices to set
	 */
	public final void setAvailableServices(List<AvailableService> availableServices) {
		this.availableServices = availableServices;
	}
	
	public final void addAvailableServices(AvailableService availableService) {
		this.getAvailableServices().add(availableService);
	}

	/**
	 * @return the wizardPages
	 */
	private int getWizardPages() {
		return wizardPages;
	}

	/**
	 * @param wizardPages the wizardPages to set
	 */
	private void setWizardPages(int wizardPages) {
		this.wizardPages = wizardPages;
	}

	/**
	 * @return the page
	 */
	public final int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public final void setPage(int page) {
		this.page = page;
	}
	
	public final void nextPage() {
		if (this.page < this.getWizardPages()) {
			this.page++;
		}
	}

	public final void previousPage() {
		if (this.page > 1) {
			this.page--;
		}
	}

	/**
	 * @return the selectedNodeName
	 */
	public final String getSelectedNodeName() {
		return selectedNodeName;
	}

	/**
	 * @param selectedNodeName the selectedNodeName to set
	 */
	public final void setSelectedNodeName(String selectedNodeName) {
		this.selectedNodeName = selectedNodeName;
	}

	/**
	 * @param backEndURL the backEndURL to set
	 */	
	public void setBackEndURL(String backEndURL) {
		this.backEndURL = backEndURL;
	}
	
	/**
	 * @return the backEndURL
	 */
	public String getBackEndURL() {
		return backEndURL;
	}
	
	/**
	 * @return the selectedServices
	 */
	public final List<String> getSelectedServicesPackages() {
		if (this.selectedServicesPackages == null) {
			this.selectedServicesPackages = new ArrayList<String>();
		}
		return selectedServicesPackages;
	}

	/**
	 * @param selectedServices the selectedServices to set
	 */
	public final void setSelectedServicesPackages(List<String> selectedServicesPackages) {
		this.selectedServicesPackages = selectedServicesPackages;
	}
	
	public final void addSelectedServicesPackages(String selectedServicePackages) {
		this.getSelectedServicesPackages().add(selectedServicePackages);
	}

	/**
	 * @return the alreadyRegisteredServices
	 */
	public final List<AvailableService> getAlreadyRegisteredServices() {
		return alreadyRegisteredServices;
	}

	/**
	 * @param alreadyRegisteredServices the alreadyRegisteredServices to set
	 */
	public final void setAlreadyRegisteredServices(
			List<AvailableService> alreadyRegisteredServices) {
		this.alreadyRegisteredServices = alreadyRegisteredServices;
	}
	
	public void addAlreadyRegisteredServices(AvailableService alreadyRegisteredService) {
		this.getAlreadyRegisteredServices().add(alreadyRegisteredService);
	}

	/**
	 * @return the servicesSuffix
	 */
	public final String getServicesSuffix() {
		return this.servicesSuffix;
	}

	/**
	 * @param servicesSuffix the servicesSuffix to set
	 */
	public final void setServicesSuffix(String servicesSuffix) {
		this.servicesSuffix = servicesSuffix;
	}

	/**
	 * @return the logicalNamesPrefix
	 */
	public final String getLogicalNamesPrefix() {
		return this.logicalNamesPrefix;
	}

	/**
	 * @param logicalNamesPrefix the logicalNamesPrefix to set
	 */
	public final void setLogicalNamesPrefix(String logicalNamesPrefix) {
		this.logicalNamesPrefix = logicalNamesPrefix;
	}

	/**
	 * @return the logicalNamesSuffix
	 */
	public final String getLogicalNamesSuffix() {
		return this.logicalNamesSuffix;
	}

	/**
	 * @param logicalNamesSuffix the logicalNamesSuffix to set
	 */
	public final void setLogicalNamesSuffix(String logicalNamesSuffix) {
		this.logicalNamesSuffix = logicalNamesSuffix;
	}

	/**
	 * @return the servicesPrefix
	 */
	public final String getServicesPrefix() {
		return this.servicesPrefix;
	}

	/**
	 * @param servicesPrefix the servicesPrefix to set
	 */
	public final void setServicesPrefix(String servicesPrefix) {
		this.servicesPrefix = servicesPrefix;
	}

}
