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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.people.console.dto.ExtendedAvailableService;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 23/ott/2011 14.57.55
 *
 */
public class FEServicesMassiveChange implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -982062503454964648L;

	private String[] selectedNodesToShow;

	private String errorSelectedNodesToShow;

	private String errorSelectedServices;
	
	private List<String> selectedNodes = new ArrayList<String>();

	private Map<Long, List<ExtendedAvailableService>> availableServices = new HashMap<Long, List<ExtendedAvailableService>>();

	private List<String> selectedServicesPackages = new ArrayList<String>();

	private List<String> selectedAreas = new ArrayList<String>();
	
	private Map<Long, List<ExtendedAvailableService>> selectedServices = new HashMap<Long, List<ExtendedAvailableService>>();
	
	private FEServiceMassiveParametersModifiable feServiceMassiveParametersModifiable;
	
	private int page;
	
	private int wizardPages;
	
	public FEServicesMassiveChange(final int wizardPages) {
		this.setPage(1);
		this.setWizardPages(wizardPages);
		this.setFeServiceMassiveParametersModifiable(new FEServiceMassiveParametersModifiable());
	}

	/**
	 * @return the selectedNodesToShow
	 */
	public final String[] getSelectedNodesToShow() {
		return this.selectedNodesToShow;
	}


	/**
	 * @param selectedNodesToShow the selectedNodesToShow to set
	 */
	public final void setSelectedNodesToShow(String[] selectedNodesToShow) {
		this.selectedNodesToShow = selectedNodesToShow;
	}


	/**
	 * @return the selectedNodes
	 */
	public final List<String> getSelectedNodes() {
		return this.selectedNodes;
	}

	/**
	 * @param selectedNodes the selectedNodes to set
	 */
	public final void setSelectedNodes(List<String> selectedNodes) {
		this.selectedNodes = selectedNodes;
	}

	/**
	 * @return the selectedServices
	 */
	public final Map<Long, List<ExtendedAvailableService>> getAvailableServices() {
		return availableServices;
	}

	/**
	 * @param selectedServices the selectedServices to set
	 */
	public final void setAvailableServices(Map<Long, List<ExtendedAvailableService>> availableServices) {
		this.availableServices = availableServices;
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
	 * @return the feServiceMassiveParametersModifiable
	 */
	public final FEServiceMassiveParametersModifiable getFeServiceMassiveParametersModifiable() {
		return this.feServiceMassiveParametersModifiable;
	}

	/**
	 * @param feServiceMassiveParametersModifiable the feServiceMassiveParametersModifiable to set
	 */
	public final void setFeServiceMassiveParametersModifiable(
			FEServiceMassiveParametersModifiable feServiceMassiveParametersModifiable) {
		this.feServiceMassiveParametersModifiable = feServiceMassiveParametersModifiable;
	}

	/**
	 * @return the errorSelectedNodesToShow
	 */
	public final String getErrorSelectedNodesToShow() {
		return this.errorSelectedNodesToShow;
	}

	/**
	 * @param errorSelectedNodesToShow the errorSelectedNodesToShow to set
	 */
	public final void setErrorSelectedNodesToShow(String errorSelectedNodesToShow) {
		this.errorSelectedNodesToShow = errorSelectedNodesToShow;
	}

	/**
	 * @return the errorSelectedServices
	 */
	public final String getErrorSelectedServices() {
		return this.errorSelectedServices;
	}

	/**
	 * @param errorSelectedServices the errorSelectedServices to set
	 */
	public final void setErrorSelectedServices(String errorSelectedServices) {
		this.errorSelectedServices = errorSelectedServices;
	}

	/**
	 * @return the selectedServices
	 */
	public final Map<Long, List<ExtendedAvailableService>> getSelectedServices() {
		return this.selectedServices;
	}

	/**
	 * @param selectedServices the selectedServices to set
	 */
	public final void setSelectedServices(
			Map<Long, List<ExtendedAvailableService>> selectedServices) {
		this.selectedServices = selectedServices;
	}

	/**
	 * @param selectedService
	 */
	public final void addSelectedService(ExtendedAvailableService selectedService) {
		
		Long nodeId = new Long(selectedService.getNodeId());
		List<ExtendedAvailableService> nodeServices = this.getSelectedServices().get(nodeId);
		if (nodeServices == null) {
			nodeServices = new ArrayList<ExtendedAvailableService>();
		}
		nodeServices.add(selectedService);
		this.getSelectedServices().put(nodeId, nodeServices);
		
	}

	/**
	 * 
	 */
	public final void clearSelectedServices() {
		this.getSelectedServices().clear();
	}
	
}
