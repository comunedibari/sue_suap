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
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 23/ott/2011 22.09.01
 *
 */
public class FEServiceMassiveParametersModifiable extends AbstractBaseBean implements Clearable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7104007717280185474L;

	private String serviceName;

	private String beforeUpdateServiceName;
	
	private Integer logLevel;
	
	private Integer serviceStatus;
	
	private Integer processSignEnabled;
	
	private Integer attachmentsInCitizenReceipt;
	
	private Integer activationType;
	
	private Integer sendmailtoowner;

	private ProcessActivationType processActivationType = new ProcessActivationType();
	
	public void finalize() throws Throwable {
		super.finalize();
	}

	public FEServiceMassiveParametersModifiable(){

	}

	public final String getServiceName() {
		return serviceName;
	}

	public final void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public final Integer getLogLevel() {
		return logLevel;
	}

	public final void setLogLevel(Integer logLevel) {
		this.logLevel = logLevel;
	}

	public final Integer getServiceStatus() {
		return serviceStatus;
	}

	public final void setServiceStatus(Integer serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	/**
	 * @return the processSignEnabled
	 */
	public final Integer getProcessSignEnabled() {
		return processSignEnabled;
	}

	/**
	 * @param processSignEnabled the processSignEnabled to set
	 */
	public final void setProcessSignEnabled(Integer processSignEnabled) {
		this.processSignEnabled = processSignEnabled;
	}

	/**
	 * @return the attachmentsInCitizenReceipt
	 */
	public final Integer getAttachmentsInCitizenReceipt() {
		return attachmentsInCitizenReceipt;
	}

	/**
	 * @param attachmentsInCitizenReceipt the attachmentsInCitizenReceipt to set
	 */
	public final void setAttachmentsInCitizenReceipt(Integer attachmentsInCitizenReceipt) {
		this.attachmentsInCitizenReceipt = attachmentsInCitizenReceipt;
	}

	public final ProcessActivationType getProcessActivationType() {
		return processActivationType;
	}

	public final void setProcessActivationType(
			ProcessActivationType processActivationType) {
		this.processActivationType = processActivationType;
		this.setActivationType(processActivationType.getActivationType().getCode());
	}

	/**
	 * @return the activationType
	 */
	public final Integer getActivationType() {
		return activationType;
	}

	/**
	 * @param activationType the activationType to set
	 */
	public final void setActivationType(Integer activationType) {
		this.activationType = activationType;
		this.getProcessActivationType().setActivationType(ProcessActivationTypeEnumeration.findActivationType(activationType));
	}

	/**
	 * @return the sendmailtoowner
	 */
	public final Integer getSendmailtoowner() {
		return this.sendmailtoowner;
	}

	/**
	 * @param sendmailtoowner the sendmailtoowner to set
	 */
	public final void setSendmailtoowner(Integer sendmailtoowner) {
		this.sendmailtoowner = sendmailtoowner;
	}

	/**
	 * @return the beforeUpdateServiceName
	 */
	public final String getBeforeUpdateServiceName() {
		return this.beforeUpdateServiceName;
	}

	/**
	 * @param beforeUpdateServiceName the beforeUpdateServiceName to set
	 */
	public final void setBeforeUpdateServiceName(String beforeUpdateServiceName) {
		this.beforeUpdateServiceName = beforeUpdateServiceName;
	}

	/* (non-Javadoc)
	 * @see it.people.console.domain.Clearable#clear()
	 */
	public void clear() {
		this.setServiceName(null);
		this.setLogLevel(null);
		this.setServiceStatus(null);
		this.setProcessSignEnabled(null);
		this.setAttachmentsInCitizenReceipt(null);
		this.setProcessActivationType(null);
		this.setActivationType(null);
		this.setSendmailtoowner(null);
		this.setBeforeUpdateServiceName(null);
	}

}
