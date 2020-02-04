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
public class FEServiceDTO extends BaseBean {

	private String serviceName;
	private String municipality;
	private String area;
	private String subArea;
	private String _package;
	private String process;
	private String logLevel;
	private String serviceStatus;
	private String processSignEnabled;
	private String attachmentInCitizenReceipt;
	private String processActivationType;

	public FEServiceDTO(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	
	public final String getServiceName() {
		return serviceName;
	}
	
	public final void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public final String getMunicipality() {
		return municipality;
	}
	
	public final void setMunicipality(String municipality) {
		this.municipality = municipality;
	}
	
	public final String getArea() {
		return area;
	}
	
	public final void setArea(String area) {
		this.area = area;
	}
	
	public final String getSubArea() {
		return subArea;
	}
	
	public final void setSubArea(String subArea) {
		this.subArea = subArea;
	}
	
	public final String get_package() {
		return _package;
	}
	
	public final void set_package(String package1) {
		_package = package1;
	}
	
	public final String getProcess() {
		return process;
	}
	
	public final void setProcess(String process) {
		this.process = process;
	}
	
	public final String getLogLevel() {
		return logLevel;
	}
	
	public final void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	
	public final String getServiceStatus() {
		return serviceStatus;
	}
	
	public final void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	
	public final String getProcessSignEnabled() {
		return processSignEnabled;
	}
	
	public final void setProcessSignEnabled(String processSignEnabled) {
		this.processSignEnabled = processSignEnabled;
	}
	
	public final String getAttachmentInCitizenReceipt() {
		return attachmentInCitizenReceipt;
	}
	
	public final void setAttachmentInCitizenReceipt(
			String attachmentInCitizenReceipt) {
		this.attachmentInCitizenReceipt = attachmentInCitizenReceipt;
	}
	
	public final String getProcessActivationType() {
		return processActivationType;
	}
	
	public final void setProcessActivationType(String processActivationType) {
		this.processActivationType = processActivationType;
	}

}
