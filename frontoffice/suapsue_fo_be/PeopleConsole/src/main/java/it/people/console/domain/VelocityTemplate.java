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
 * @author Andrea Piemontese
 * @version 1.0
 * @created 12-11-2012
 */
public class VelocityTemplate extends AbstractBaseBean implements Clearable {


	private static final long serialVersionUID = -6816406686670293994L;

	private String communeId;
	private String servicePackage;
	private String serviceName;
	private String frontEndServiceId;
	private String nodeName;
	
	private String templateBody="";
	private String templateBodyMapper="";
	private String templateSubjectMapper="";
	private String templateSubject="";
	
	private String description="";
	private String key;
	

	public VelocityTemplate() {

	}
	
	public void finalize() throws Throwable {
		super.finalize();
	}
	

	/**
	 * @return the communeId
	 */
	public String getCommuneId() {
		return communeId;
	}

	/**
	 * @param communeId the communeId to set
	 */
	public void setCommuneId(String communeId) {
		this.communeId = communeId;
	}

	/**
	 * @return the servicePackage
	 */
	public String getServicePackage() {
		return servicePackage;
	}

	/**
	 * @param servicePackage the servicePackage to set
	 */
	public void setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the nodeName
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * @param nodeName the nodeName to set
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * @return the templateBody
	 */
	public String getTemplateBody() {
		return templateBody;
	}

	/**
	 * @param templateBody the templateBody to set
	 */
	public void setTemplateBody(String templateBody) {
		this.templateBody = templateBody;
	}

	/**
	 * @return the templateBodyMapper
	 */
	public String getTemplateBodyMapper() {
		return templateBodyMapper;
	}

	/**
	 * @param templateBodyMapper the templateBodyMapper to set
	 */
	public void setTemplateBodyMapper(String templateBodyMapper) {
		this.templateBodyMapper = templateBodyMapper;
	}

	/**
	 * @return the templateSubjectMapper
	 */
	public String getTemplateSubjectMapper() {
		return templateSubjectMapper;
	}

	/**
	 * @param templateSubjectMapper the templateSubjectMapper to set
	 */
	public void setTemplateSubjectMapper(String templateSubjectMapper) {
		this.templateSubjectMapper = templateSubjectMapper;
	}

	/**
	 * @return the templateSubject
	 */
	public String getTemplateSubject() {
		return templateSubject;
	}

	/**
	 * @param templateSubject the templateSubject to set
	 */
	public void setTemplateSubject(String templateSubject) {
		this.templateSubject = templateSubject;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the frontEndServiceId
	 */
	public String getFrontEndServiceId() {
		return frontEndServiceId;
	}

	/**
	 * @param frontEndServiceId the frontEndServiceId to set
	 */
	public void setFrontEndServiceId(String frontEndServiceId) {
		this.frontEndServiceId = frontEndServiceId;
	}

	/* (non-Javadoc)
	 * @see it.people.console.domain.Clearable#clear()
	 */
	public void clear() {

		this.setCommuneId(null);
		this.setNodeName(null);
		this.setServiceName(null);
		this.setServicePackage(null);
		
		this.setTemplateBody(null);
		this.setTemplateBodyMapper(null);
		this.setTemplateSubject(null);
		this.setTemplateSubjectMapper(null);
	}

	
}
