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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.people.feservice.beans.AvailableService;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 30/gen/2011 22.43.34
 *
 */
public class FENodeToNodeCopy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9067494192380113547L;

	private int selectedFromNodeId;
	
	private String selectedFromNodeName;

	private String selectedFromNodeKey;
	
	private int selectedToNodeId;
	
	private String invalidChosedNodes;
	
	private String selectedToNodeName;

	private String selectedToNodeKey;
	
	private List<AvailableService> availableServices = new ArrayList<AvailableService>();

	private List<AvailableService> alreadyRegisteredServices = new ArrayList<AvailableService>();
	
	private List<String> selectedServicesPackages = new ArrayList<String>();

	private List<String> selectedAreas = new ArrayList<String>();
	
	private String beServicesUrl = "";
	
	private Map<String, String> selectedServicesBEUrl = new HashMap<String, String>();

	private Map<String, String> selectedAreasBEUrl = new HashMap<String, String>();

	private Map<String, String> selectedServicesNamePrefix = new HashMap<String, String>();

	private Map<String, String> selectedServicesNameSuffix = new HashMap<String, String>();

	private Map<String, String> selectedAreasServicesNamePrefix = new HashMap<String, String>();

	private Map<String, String> selectedAreasServicesNameSuffix = new HashMap<String, String>();
	
	private Map<String, String> selectedServicesLogicalNamePrefix = new HashMap<String, String>();

	private Map<String, String> selectedServicesLogicalNameSuffix = new HashMap<String, String>();

	private Map<String, String> selectedAreasServicesLogicalNamePrefix = new HashMap<String, String>();

	private Map<String, String> selectedAreasServicesLogicalNameSuffix = new HashMap<String, String>();
	
	private int page;
	
	private int wizardPages;
	
	private boolean toNewNode;
	
	private FENode newFeNode = new FENode();
	
	public FENodeToNodeCopy(final int wizardPages) {
		this.setPage(1);
		this.setWizardPages(wizardPages);
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
	
	public final void addAvailableServices(String _package, AvailableService availableService) {
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
	 * @return the selectedAreas
	 */
	public final List<String> getSelectedAreas() {
		if (this.selectedAreas == null) {
			this.selectedAreas = new ArrayList<String>();
		}
		return selectedAreas;
	}

	/**
	 * @param selectedAreas the selectedAreas to set
	 */
	public final void setSelectedAreas(List<String> selectedAreas) {
		this.selectedAreas = selectedAreas;
	}
	
	public final void addSelectedAreas(String selectedArea) {
		this.getSelectedAreas().add(selectedArea);
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
	 * @return the selectedFromNodeId
	 */
	public final int getSelectedFromNodeId() {
		return selectedFromNodeId;
	}

	/**
	 * @param selectedFromNodeId the selectedFromNodeId to set
	 */
	public final void setSelectedFromNodeId(int selectedFromNodeId) {
		this.selectedFromNodeId = selectedFromNodeId;
	}

	/**
	 * @return the selectedFromNodeName
	 */
	public final String getSelectedFromNodeName() {
		return selectedFromNodeName;
	}

	/**
	 * @param selectedFromNodeName the selectedFromNodeName to set
	 */
	public final void setSelectedFromNodeName(String selectedFromNodeName) {
		this.selectedFromNodeName = selectedFromNodeName;
	}

	/**
	 * @return the selectedToNodeId
	 */
	public final int getSelectedToNodeId() {
		return selectedToNodeId;
	}

	/**
	 * @param selectedToNodeId the selectedToNodeId to set
	 */
	public final void setSelectedToNodeId(int selectedToNodeId) {
		this.selectedToNodeId = selectedToNodeId;
	}

	/**
	 * @return the selectedToNodeName
	 */
	public final String getSelectedToNodeName() {
		return selectedToNodeName;
	}

	/**
	 * @param selectedToNodeName the selectedToNodeName to set
	 */
	public final void setSelectedToNodeName(String selectedToNodeName) {
		this.selectedToNodeName = selectedToNodeName;
	}

	/**
	 * @return the invalidChosedNodes
	 */
	public final String getInvalidChosedNodes() {
		return invalidChosedNodes;
	}

	/**
	 * @param invalidChosedNodes the invalidChosedNodes to set
	 */
	public final void setInvalidChosedNodes(String invalidChosedNodes) {
		this.invalidChosedNodes = invalidChosedNodes;
	}

	/**
	 * @return the beServicesUrl
	 */
	public final String getBeServicesUrl() {
		return beServicesUrl;
	}

	/**
	 * @param beServicesUrl the beServicesUrl to set
	 */
	public final void setBeServicesUrl(String beServicesUrl) {
		this.beServicesUrl = beServicesUrl;
	}

	/**
	 * @return the newFeNode
	 */
	public final FENode getNewFeNode() {
		return newFeNode;
	}

	/**
	 * @param newFeNode the newFeNode to set
	 */
	public final void setNewFeNode(FENode newFeNode) {
		this.newFeNode = newFeNode;
	}

	/**
	 * @return the toNewNode
	 */
	public final boolean isToNewNode() {
		return toNewNode;
	}

	/**
	 * @param toNewNode the toNewNode to set
	 */
	public final void setToNewNode(boolean toNewNode) {
		this.toNewNode = toNewNode;
	}
	
	public Collection<AvailableService> getAvailableServiceCollection() {
		Collections.sort(this.getAvailableServices(), new AvailableServicesListOrdererByActivity());
		return this.getAvailableServices();
	}

	/**
	 * @return the selectedFromNodeKey
	 */
	public final String getSelectedFromNodeKey() {
		return selectedFromNodeKey;
	}

	/**
	 * @param selectedFromNodeKey the selectedFromNodeKey to set
	 */
	public final void setSelectedFromNodeKey(String selectedFromNodeKey) {
		this.selectedFromNodeKey = selectedFromNodeKey;
	}

	/**
	 * @return the selectedToNodeKey
	 */
	public final String getSelectedToNodeKey() {
		return selectedToNodeKey;
	}

	/**
	 * @param selectedToNodeKey the selectedToNodeKey to set
	 */
	public final void setSelectedToNodeKey(String selectedToNodeKey) {
		this.selectedToNodeKey = selectedToNodeKey;
	}

	/**
	 * @return the selectedServicesBEUrl
	 */
	public final Map<String, String> getSelectedServicesBEUrl() {
		return this.selectedServicesBEUrl;
	}

	/**
	 * @param selectedServicesBEUrl the selectedServicesBEUrl to set
	 */
	public final void setSelectedServicesBEUrl(
			Map<String, String> selectedServicesBEUrl) {
		this.selectedServicesBEUrl = selectedServicesBEUrl;
	}

	/**
	 * @return the selectedAreasBEUrl
	 */
	public final Map<String, String> getSelectedAreasBEUrl() {
		return this.selectedAreasBEUrl;
	}

	/**
	 * @param selectedAreasBEUrl the selectedAreasBEUrl to set
	 */
	public final void setSelectedAreasBEUrl(Map<String, String> selectedAreasBEUrl) {
		this.selectedAreasBEUrl = selectedAreasBEUrl;
	}

	/**
	 * @return the selectedServicesNamePrefix
	 */
	public final Map<String, String> getSelectedServicesNamePrefix() {
		return this.selectedServicesNamePrefix;
	}

	/**
	 * @param selectedServicesNamePrefix the selectedServicesNamePrefix to set
	 */
	public final void setSelectedServicesNamePrefix(
			Map<String, String> selectedServicesNamePrefix) {
		this.selectedServicesNamePrefix = selectedServicesNamePrefix;
	}

	/**
	 * @return the selectedServicesNameSuffix
	 */
	public final Map<String, String> getSelectedServicesNameSuffix() {
		return this.selectedServicesNameSuffix;
	}

	/**
	 * @param selectedServicesNameSuffix the selectedServicesNameSuffix to set
	 */
	public final void setSelectedServicesNameSuffix(
			Map<String, String> selectedServicesNameSuffix) {
		this.selectedServicesNameSuffix = selectedServicesNameSuffix;
	}

	/**
	 * @return the selectedAreasServicesNamePrefix
	 */
	public final Map<String, String> getSelectedAreasServicesNamePrefix() {
		return this.selectedAreasServicesNamePrefix;
	}

	/**
	 * @param selectedAreasServicesNamePrefix the selectedAreasServicesNamePrefix to set
	 */
	public final void setSelectedAreasServicesNamePrefix(
			Map<String, String> selectedAreasServicesNamePrefix) {
		this.selectedAreasServicesNamePrefix = selectedAreasServicesNamePrefix;
	}

	/**
	 * @return the selectedAreasServicesNameSuffix
	 */
	public final Map<String, String> getSelectedAreasServicesNameSuffix() {
		return this.selectedAreasServicesNameSuffix;
	}

	/**
	 * @param selectedAreasServicesNameSuffix the selectedAreasServicesNameSuffix to set
	 */
	public final void setSelectedAreasServicesNameSuffix(
			Map<String, String> selectedAreasServicesNameSuffix) {
		this.selectedAreasServicesNameSuffix = selectedAreasServicesNameSuffix;
	}

	/**
	 * @return the selectedServicesLogicalNamePrefix
	 */
	public final Map<String, String> getSelectedServicesLogicalNamePrefix() {
		return this.selectedServicesLogicalNamePrefix;
	}

	/**
	 * @param selectedServicesLogicalNamePrefix the selectedServicesLogicalNamePrefix to set
	 */
	public final void setSelectedServicesLogicalNamePrefix(
			Map<String, String> selectedServicesLogicalNamePrefix) {
		this.selectedServicesLogicalNamePrefix = selectedServicesLogicalNamePrefix;
	}

	/**
	 * @return the selectedServicesLogicalNameSuffix
	 */
	public final Map<String, String> getSelectedServicesLogicalNameSuffix() {
		return this.selectedServicesLogicalNameSuffix;
	}

	/**
	 * @param selectedServicesLogicalNameSuffix the selectedServicesLogicalNameSuffix to set
	 */
	public final void setSelectedServicesLogicalNameSuffix(
			Map<String, String> selectedServicesLogicalNameSuffix) {
		this.selectedServicesLogicalNameSuffix = selectedServicesLogicalNameSuffix;
	}

	/**
	 * @return the selectedAreasServicesLogicalNamePrefix
	 */
	public final Map<String, String> getSelectedAreasServicesLogicalNamePrefix() {
		return this.selectedAreasServicesLogicalNamePrefix;
	}

	/**
	 * @param selectedAreasServicesLogicalNamePrefix the selectedAreasServicesLogicalNamePrefix to set
	 */
	public final void setSelectedAreasServicesLogicalNamePrefix(
			Map<String, String> selectedAreasServicesLogicalNamePrefix) {
		this.selectedAreasServicesLogicalNamePrefix = selectedAreasServicesLogicalNamePrefix;
	}

	/**
	 * @return the selectedAreasServicesLogicalNameSuffix
	 */
	public final Map<String, String> getSelectedAreasServicesLogicalNameSuffix() {
		return this.selectedAreasServicesLogicalNameSuffix;
	}

	/**
	 * @param selectedAreasServicesLogicalNameSuffix the selectedAreasServicesLogicalNameSuffix to set
	 */
	public final void setSelectedAreasServicesLogicalNameSuffix(
			Map<String, String> selectedAreasServicesLogicalNameSuffix) {
		this.selectedAreasServicesLogicalNameSuffix = selectedAreasServicesLogicalNameSuffix;
	}

}
