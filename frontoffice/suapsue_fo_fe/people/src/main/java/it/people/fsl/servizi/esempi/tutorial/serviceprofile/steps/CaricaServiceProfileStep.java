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
 * Created on 12-gen-2006
 *
 */
package it.people.fsl.servizi.esempi.tutorial.serviceprofile.steps;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.xmlbeans.XmlException;

import it.people.Step;
import it.people.core.ServiceProfileStore;
import it.people.core.ServiceProfileStoreManager;
import it.people.core.persistence.exception.peopleException;
import it.people.fsl.servizi.esempi.tutorial.serviceprofile.model.ProcessData;
import it.people.process.AbstractPplProcess;
import it.people.sirac.serviceprofile.xml.PeopleServiceProfile;
import it.people.sirac.serviceprofile.xml.PeopleServiceProfileDocument;
import it.people.sirac.serviceprofile.xml.Service;
import it.people.wrappers.IRequestWrapper;

/**
 * @author FabMi
 *
 */
public class CaricaServiceProfileStep extends Step {
	/* (non-Javadoc)
	 * @see it.people.IStep#loopBack(it.people.process.AbstractPplProcess, it.people.wrappers.IRequestWrapper, java.lang.String, int)
	 */
	public void loopBack(AbstractPplProcess process, IRequestWrapper request,
			String propertyName, int index) throws IOException,
			ServletException {

		ProcessData data = (ProcessData)process.getData();
		try {		
			PeopleServiceProfile serviceProfile = 
				caricaServiceProfile(data.getServiceName(), data.getBookmarkId());
			
			if (serviceProfile == null) {
				process.error("Service Profile inesistente");
				return;
			}
				
			
			Service service = serviceProfile.getService();
			
			data.setServiceCategory(service.getCategory());
			data.setServiceCategory(service.getSubcategory());
			data.setStrongAuthentication(service.getSecurityProfile().getStrongAuthentication());
			data.setWeakAuthentication(service.getSecurityProfile().getWeakAuthentication());			
		} catch(XmlException ex) {
			process.warn("Xml non valido");
		} catch(peopleException ex) {
		    process.warn("Impossibile caricare il service profile");
		} catch(Exception ex) {
		    process.warn("Impossibile caricare il service profile");
		}
	}
	
	protected PeopleServiceProfile caricaServiceProfile(String processName, String bookmarkId) 
	throws XmlException, peopleException {		
		ServiceProfileStore serviceProfileStore = 
			ServiceProfileStoreManager.getInstance().get(processName, bookmarkId);

		if (serviceProfileStore == null)
			return null;
		
		String xmlProfile = serviceProfileStore.getProfile();
		
		PeopleServiceProfileDocument serviceProfileDocument = 
			PeopleServiceProfileDocument.Factory.parse(xmlProfile);
		
		return serviceProfileDocument.getPeopleServiceProfile();
	}
}
