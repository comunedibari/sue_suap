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
import java.util.Map;
import java.util.TreeMap;

import it.people.console.dto.BEServiceDTO;
import it.people.console.dto.FENodeDTO;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 20/ago/2011 16.34.21
 *
 */
public class BEServicesMassiveParametersSettings implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2070670669890828506L;

	private Map<Integer, FENodeDTO> nodesList = new TreeMap<Integer, FENodeDTO>();

	private Map<Integer, BEServiceDTO> beServicesList = new TreeMap<Integer, BEServiceDTO>();
	
	private List<String> selectedNodes = new ArrayList<String>();

	private List<String> selectedServices = new ArrayList<String>();
	
	private boolean selectSingleServices = false;
	
	private String newUrlSchema;

	private String newUrlHost;

	private String newUrlPort;
	
	private boolean transportEnvelopeEnabled;
	
	private boolean delegationControlForbidden;
	
	private String operationsLog;
	
	private int page;
	
	private int wizardPages;
	
	public BEServicesMassiveParametersSettings(final int wizardPages) {
		this.setPage(1);
		this.setWizardPages(wizardPages);
		nodesList = new TreeMap<Integer, FENodeDTO>();
		beServicesList = new TreeMap<Integer, BEServiceDTO>();
		selectedNodes = new ArrayList<String>();
		selectedServices = new ArrayList<String>();
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
	 * @return the nodesList
	 */
	public final Map<Integer, FENodeDTO> getNodesList() {
		return this.nodesList;
	}

	/**
	 * @param nodesList the nodesList to set
	 */
	public final void setNodesList(Map<Integer, FENodeDTO> nodesList) {
		this.nodesList = nodesList;
	}

	/**
	 * @return the beServicesList
	 */
	public final Map<Integer, BEServiceDTO> getBeServicesList() {
		return this.beServicesList;
	}

	/**
	 * @param beServicesList the beServicesList to set
	 */
	public final void setBeServicesList(Map<Integer, BEServiceDTO> beServicesList) {
		this.beServicesList = beServicesList;
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
	 * @return the selectSingleServices
	 */
	public final boolean isSelectSingleServices() {
		return this.selectSingleServices;
	}

	/**
	 * @param selectSingleServices the selectSingleServices to set
	 */
	public final void setSelectSingleServices(boolean selectSingleServices) {
		this.selectSingleServices = selectSingleServices;
	}

	/**
	 * @return the selectedServices
	 */
	public final List<String> getSelectedServices() {
		return this.selectedServices;
	}

	/**
	 * @param selectedServices the selectedServices to set
	 */
	public final void setSelectedServices(List<String> selectedServices) {
		this.selectedServices = selectedServices;
	}

	/**
	 * @return the newUrlSchema
	 */
	public final String getNewUrlSchema() {
		return this.newUrlSchema;
	}

	/**
	 * @param newUrlSchema the newUrlSchema to set
	 */
	public final void setNewUrlSchema(String newUrlSchema) {
		this.newUrlSchema = newUrlSchema;
	}

	/**
	 * @return the newUrlHost
	 */
	public final String getNewUrlHost() {
		return this.newUrlHost;
	}

	/**
	 * @param newUrlHost the newUrlHost to set
	 */
	public final void setNewUrlHost(String newUrlHost) {
		this.newUrlHost = newUrlHost;
	}

	/**
	 * @return the newUrlPort
	 */
	public final String getNewUrlPort() {
		return this.newUrlPort;
	}

	/**
	 * @param newUrlPort the newUrlPort to set
	 */
	public final void setNewUrlPort(String newUrlPort) {
		this.newUrlPort = newUrlPort;
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
	 * @return the operationsLog
	 */
	public final String getOperationsLog() {
		return this.operationsLog;
	}

	/**
	 * @param operationsLog the operationsLog to set
	 */
	public final void setOperationsLog(String operationsLog) {
		this.operationsLog = operationsLog;
	}

}
