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

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 23/ott/2011 14.57.55
 *
 */
public class BEServicesMassiveChange implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6157161940680895782L;

	private String[] selectedNodesToShow;

	private String errorSelectedNodesToShow;

	private String errorSelectedServices;
	
	private List<String> selectedNodes = new ArrayList<String>();

	private Map<Long, List<BEService>> availableServices = new HashMap<Long, List<BEService>>();

	private List<String> selectedServicesId = new ArrayList<String>();
	
	private boolean transportEnvelopeEnabled = false;
	
	private boolean delegationControlForbidden = false;
	
	private String protocol;
	
	private String host;
	
	private String port;
	
	private boolean advancedUrlSubstitution = false;
	
	private String find;
	
	private String replace;

	private int page;
	
	private int wizardPages;
	
	public BEServicesMassiveChange(final int wizardPages) {
		this.setPage(1);
		this.setWizardPages(wizardPages);
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
	public final Map<Long, List<BEService>> getAvailableServices() {
		return availableServices;
	}

	/**
	 * @param selectedServices the selectedServices to set
	 */
	public final void setAvailableServices(Map<Long, List<BEService>> availableServices) {
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
	public final List<String> getSelectedServicesId() {
		if (this.selectedServicesId == null) {
			this.selectedServicesId = new ArrayList<String>();
		}
		return selectedServicesId;
	}

	/**
	 * @param selectedServices the selectedServices to set
	 */
	public final void setSelectedServicesId(List<String> selectedServicesId) {
		this.selectedServicesId = selectedServicesId;
	}
	
	public final void addSelectedServicesId(String selectedServiceId) {
		this.getSelectedServicesId().add(selectedServiceId);
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
	 * @return the transportEnvelopeEnabled
	 */
	public final boolean isTransportEnvelopeEnabled() {
		return this.transportEnvelopeEnabled;
	}

	/**
	 * @param transportEnvelopeEnabled the transportEnvelopeEnabled to set
	 */
	public final void setTransportEnvelopeEnabled(boolean transportEnvelopeEnabled) {
		this.transportEnvelopeEnabled = transportEnvelopeEnabled;
	}

	/**
	 * @return the delegationControlForbidden
	 */
	public final boolean isDelegationControlForbidden() {
		return this.delegationControlForbidden;
	}

	/**
	 * @param delegationControlForbidden the delegationControlForbidden to set
	 */
	public final void setDelegationControlForbidden(
			boolean delegationControlForbidden) {
		this.delegationControlForbidden = delegationControlForbidden;
	}

	/**
	 * @return the protocol
	 */
	public final String getProtocol() {
		return this.protocol;
	}

	/**
	 * @param protocol the protocol to set
	 */
	public final void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * @return the host
	 */
	public final String getHost() {
		return this.host;
	}

	/**
	 * @param host the host to set
	 */
	public final void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public final String getPort() {
		return this.port;
	}

	/**
	 * @param port the port to set
	 */
	public final void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return the find
	 */
	public final String getFind() {
		return this.find;
	}

	/**
	 * @param find the find to set
	 */
	public final void setFind(String find) {
		this.find = find;
	}

	/**
	 * @return the replace
	 */
	public final String getReplace() {
		return this.replace;
	}

	/**
	 * @param replace the replace to set
	 */
	public final void setReplace(String replace) {
		this.replace = replace;
	}

	/**
	 * @return the advancedUrlSubstitution
	 */
	public final boolean isAdvancedUrlSubstitution() {
		return this.advancedUrlSubstitution;
	}

	/**
	 * @param advancedUrlSubstitution the advancedUrlSubstitution to set
	 */
	public final void setAdvancedUrlSubstitution(boolean advancedUrlSubstitution) {
		this.advancedUrlSubstitution = advancedUrlSubstitution;
	}

}
