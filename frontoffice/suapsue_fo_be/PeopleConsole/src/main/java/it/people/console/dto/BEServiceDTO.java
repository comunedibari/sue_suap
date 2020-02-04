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
package it.people.console.dto;
import it.people.console.domain.BaseBean;

/**
 * @author Riccardo Forafo'
 * @version 1.0
 * @created 02-dic-2010 15:17:30
 */
public class BEServiceDTO extends BaseBean {

	private String logicalServiceName;
	
	private String backEndURL;
	
	private String transportEnvelopeEnabled;
	
	private String delegationControlForbidden;

	public BEServiceDTO(){

	}

	public void finalize() throws Throwable {
		super.finalize();
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

	public final String getTransportEnvelopeEnabled() {
		return transportEnvelopeEnabled;
	}

	public final void setTransportEnvelopeEnabled(String transportEnvelopeEnabled) {
		this.transportEnvelopeEnabled = transportEnvelopeEnabled;
	}

	public final String getDelegationControlForbidden() {
		return delegationControlForbidden;
	}

	public final void setDelegationControlForbidden(
			String delegationControlForbidden) {
		this.delegationControlForbidden = delegationControlForbidden;
	}

	public void setServiceId(int id) {
		super.setId(new Integer(id));
	}
	
	public int getServiceId() {
		return super.getId().intValue();
	}
	
}
