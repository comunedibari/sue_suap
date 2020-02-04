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

/**
 * @author Riccardo Forafo'
 * @version 1.0
 * @created 02-dic-2010 15:17:29
 */
public class BEService extends AbstractBaseBean implements Clearable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1832836100936133504L;

	private Long id;
	
	private Long selectedNodeId;
	
	private String logicalServiceName;
	
	private String backEndURL;
	
	private boolean transportEnvelopeEnabled = false;
	
	private boolean delegationControlForbidden = false;
	
	private String error;
	
	
	private String nodeId;

	private String nodeName;

	private String commune;

	private String communeKey;	
	
	public BEService(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	public final Long getId() {
		return this.id;
	}
	
	public final void setId(Long serviceId) {
		this.id = serviceId;
	}
	
	public final String getLogicalServiceName() {
		return logicalServiceName;
	}

	public final void setLogicalServiceName(String logicalServiceName) {
		this.logicalServiceName = logicalServiceName;
	}

	public final String getBackEndURL() {
		return backEndURL;
	}

	public final void setBackEndURL(String backEndURL) {
		this.backEndURL = backEndURL;
	}

	public final boolean isTransportEnvelopeEnabled() {
		return transportEnvelopeEnabled;
	}

	public final void setTransportEnvelopeEnabled(boolean transportEnvelopeEnabled) {
		this.transportEnvelopeEnabled = transportEnvelopeEnabled;
	}

	public final boolean isDelegationControlForbidden() {
		return delegationControlForbidden;
	}

	public final void setDelegationControlForbidden(
			boolean delegationControlForbidden) {
		this.delegationControlForbidden = delegationControlForbidden;
	}

	/**
	 * @return the selectedNodeId
	 */
	public final Long getSelectedNodeId() {
		return selectedNodeId;
	}

	/**
	 * @param selectedNodeId the selectedNodeId to set
	 */
	public final void setSelectedNodeId(Long selectedNodeId) {
		this.selectedNodeId = selectedNodeId;
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
	 * @return the nodeName
	 */
	public final String getNodeName() {
		return this.nodeName;
	}

	/**
	 * @param nodeName the nodeName to set
	 */
	public final void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * @return the nodeId
	 */
	public final String getNodeId() {
		return this.nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public final void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * @return the commune
	 */
	public final String getCommune() {
		return this.commune;
	}

	/**
	 * @param commune the commune to set
	 */
	public final void setCommune(String commune) {
		this.commune = commune;
	}

	/**
	 * @return the communeKey
	 */
	public final String getCommuneKey() {
		return this.communeKey;
	}

	/**
	 * @param communeKey the communeKey to set
	 */
	public final void setCommuneKey(String communeKey) {
		this.communeKey = communeKey;
	}

	/* (non-Javadoc)
	 * @see it.people.console.domain.Clearable#clear()
	 */
	public void clear() {
		this.setSelectedNodeId(null);
		this.setBackEndURL(null);
		this.setId(null);
		this.setDelegationControlForbidden(new Boolean(null));
		this.setLogicalServiceName(null);
		this.setTransportEnvelopeEnabled(new Boolean(null));
		this.setNodeName(null);
		this.setNodeId(null);
		this.setCommune(null);
		this.setCommuneKey(null);
	}
	
}
