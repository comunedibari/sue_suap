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
/*
 * Created on 18-apr-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.fsl.servizi.esempi.tutorial.serviceprofile.model;

import it.people.core.PeopleContext;
import it.people.process.AbstractPplProcess;
import it.people.process.data.AbstractData;
import it.people.vsl.PipelineData;

/**
 * @author fabmi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProcessData extends AbstractData {
	protected String serviceCategory;
	protected String serviceSubcategory;
	protected String serviceName;
	protected String bookmarkId;
	protected boolean strongAuthentication;
	protected boolean weakAuthentication;
	protected boolean abilitaIntermediari;
	protected boolean abilitaUtenteRegistrato;
	
	/**
	 * @return Returns the bookmarkId.
	 */
	public String getBookmarkId() {
		return bookmarkId;
	}
	/**
	 * @param bookmarkId The bookmarkId to set.
	 */
	public void setBookmarkId(String bookmarkId) {
		this.bookmarkId = bookmarkId;
	}
	/**
	 * @return Returns the abilitaIntermediari.
	 */
	public boolean isAbilitaIntermediari() {
		return abilitaIntermediari;
	}
	/**
	 * @param abilitaIntermediari The abilitaIntermediari to set.
	 */
	public void setAbilitaIntermediari(boolean abilitaIntermediari) {
		this.abilitaIntermediari = abilitaIntermediari;
	}
	/**
	 * @return Returns the abilitaUtenteRegistrato.
	 */
	public boolean isAbilitaUtenteRegistrato() {
		return abilitaUtenteRegistrato;
	}
	/**
	 * @param abilitaUtenteRegistrato The abilitaUtenteRegistrato to set.
	 */
	public void setAbilitaUtenteRegistrato(boolean abilitaUtenteRegistrato) {
		this.abilitaUtenteRegistrato = abilitaUtenteRegistrato;
	}
	/**
	 * @return Returns the serviceCategory.
	 */
	public String getServiceCategory() {
		return serviceCategory;
	}
	/**
	 * @param serviceCategory The serviceCategory to set.
	 */
	public void setServiceCategory(String serviceCategory) {
		this.serviceCategory = serviceCategory;
	}
	/**
	 * @return Returns the serviceName.
	 */
	public String getServiceName() {
		return serviceName;
	}
	/**
	 * @param serviceName The serviceName to set.
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	/**
	 * @return Returns the serviceSubcategory.
	 */
	public String getServiceSubcategory() {
		return serviceSubcategory;
	}
	/**
	 * @param serviceSubcategory The serviceSubcategory to set.
	 */
	public void setServiceSubcategory(String serviceSubcategory) {
		this.serviceSubcategory = serviceSubcategory;
	}
	/**
	 * @return Returns the strongAuthentication.
	 */
	public boolean isStrongAuthentication() {
		return strongAuthentication;
	}
	/**
	 * @param strongAuthentication The strongAuthentication to set.
	 */
	public void setStrongAuthentication(boolean strongAuthentication) {
		this.strongAuthentication = strongAuthentication;
	}
	/**
	 * @return Returns the weakAuthentication.
	 */
	public boolean isWeakAuthentication() {
		return weakAuthentication;
	}
	/**
	 * @param weakAuthentication The weakAuthentication to set.
	 */
	public void setWeakAuthentication(boolean weakAuthentication) {
		this.weakAuthentication = weakAuthentication;
	}
	protected void doDefineValidators() {
	}

	public void exportToPipeline(PipelineData pd) {
	}

	public void initialize(PeopleContext context, AbstractPplProcess pplProcess) {
		this.serviceCategory = "demo";
		this.serviceSubcategory = "cedaf";		
		this.serviceName = "it.people.fsl.servizi.test.cedaf.serviceprofile";
		this.bookmarkId = "1";
		this.strongAuthentication = true;
		this.weakAuthentication = false;
		this.abilitaIntermediari = false;
		this.abilitaUtenteRegistrato = true;
	}
	
	
	/* (non-Javadoc)
	 * @see it.people.process.PplData#validate()
	 */
	public boolean validate() {
		return false;
	}
}
