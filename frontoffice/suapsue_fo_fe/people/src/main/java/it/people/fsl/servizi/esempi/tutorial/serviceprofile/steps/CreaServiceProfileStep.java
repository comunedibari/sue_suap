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
 * Created on 20-dic-2005
 *
 */
package it.people.fsl.servizi.esempi.tutorial.serviceprofile.steps;

import java.io.IOException;

import javax.servlet.ServletException;

import it.people.Step;
import it.people.core.ServiceProfileStore;
import it.people.core.ServiceProfileStoreManager;
import it.people.core.persistence.exception.peopleException;
import it.people.fsl.servizi.esempi.tutorial.serviceprofile.model.ProcessData;
import it.people.process.AbstractPplProcess;
import it.people.sirac.serviceprofile.xml.AccessoIntermediari;
import it.people.sirac.serviceprofile.xml.AccessoUtentePeopleRegistrato;
import it.people.sirac.serviceprofile.xml.AuthorizationProfile;
import it.people.sirac.serviceprofile.xml.PeopleServiceProfile;
import it.people.sirac.serviceprofile.xml.PeopleServiceProfileDocument;
import it.people.sirac.serviceprofile.xml.SecurityProfile;
import it.people.sirac.serviceprofile.xml.Service;
import it.people.wrappers.IRequestWrapper;

/**
 * @author FabMi
 *
 */
public class CreaServiceProfileStep extends Step {
	
	public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) 
		throws IOException, ServletException {		
		try {
			String serviceProfileXML = createServiceProfileXML(process);
			process.debug(serviceProfileXML);
			saveServiceProfileXML(process, serviceProfileXML);
			//TODO: verificare come mostrare il messaggio di salvataggio avvenuto
		} catch(peopleException ex) {
			//TODO: verificare come mostrare l'errore
			process.error("errore nel salvataggio del service profile: " + ex.getMessage());
		} catch (Exception ex) {
			//TODO: verificare come mostrare l'errore
			process.error("errore nella creazione del service profile: " + ex.getMessage());			
		}
	}
	
	public void service(AbstractPplProcess process, IRequestWrapper request)
		throws IOException, ServletException {
	}
	
	/**
	 * Salva la serializzazione in xml del service profile su DB
	 * @param serviceProfileXML xml del service profile
	 * @throws peopleException
	 */
	protected void saveServiceProfileXML(AbstractPplProcess process, String serviceProfileXML)
	throws peopleException {
		ProcessData data = (ProcessData)process.getData();
		
		// Crea il manager per la gestione del ServiceProfileStore
		// il ServiceProfileStore � l'oggetto contenente la serializzazione
		// del service profile su DB.
		ServiceProfileStoreManager spsManager = ServiceProfileStoreManager.getInstance();
		
		// Crea un nuovo ServiceProfileStore, passandogli il nome del servizio
		// l'identificativo del bookmark e l'xml serializzazione del service profile
		ServiceProfileStore serviceProfileStore = spsManager.create(
				data.getServiceName(),
				data.getBookmarkId(),
				serviceProfileXML);
		
		if (serviceProfileStore != null) {
			// Salva il ServiceProfileStore
			try {
				ServiceProfileStoreManager.getInstance().set(serviceProfileStore);
			} catch(peopleException ex) {
				process.error("Impossibile salvare il service profile");
			}
		} else
			process.error("Xml non valido");
	}
	
	/**
	 * Crea l'xml per un service profile
	 * @return il service profile serializzato in xml
	 */
	protected String createServiceProfileXML(AbstractPplProcess process) 
	throws Exception {
		ProcessData data = (ProcessData)process.getData();
		
		// Crea i nodi principali
		PeopleServiceProfileDocument serviceProfileDoc = PeopleServiceProfileDocument.Factory.newInstance();
		PeopleServiceProfile serviceProfile = serviceProfileDoc.addNewPeopleServiceProfile();
		Service service = serviceProfile.addNewService();
		
		service.setCategory(data.getServiceCategory());
		service.setSubcategory(data.getServiceSubcategory());
		service.setName(data.getServiceName());
		
		// Nodo Security 
		SecurityProfile securityProfile = service.addNewSecurityProfile();
		securityProfile.setStrongAuthentication(data.isStrongAuthentication());
		securityProfile.setWeakAuthentication(data.isWeakAuthentication());
		
		// Nodo Authorization
		AuthorizationProfile authorizationProfile = service.addNewAuthorizationProfile();

		// N.B. la configurazione di accesso agli intermediari
		//      pu� essere dettagliata per ogni singola categoria 
		//	    di intermediario, quella presentata in questo step
		//		� solo una traccia all'utilizzo di xmlbeans per la
		//		creazione di un service profile valido.
		
		AccessoIntermediari accessoIntermediari	= authorizationProfile.addNewAccessoIntermediari();
		accessoIntermediari.setEnabled(data.isAbilitaIntermediari());
		accessoIntermediari.setAll(true);
		AccessoUtentePeopleRegistrato accessoUtentePeopleRegistrato = authorizationProfile.addNewAccessoUtentePeopleRegistrato();
		accessoUtentePeopleRegistrato.setEnabled(data.isAbilitaUtenteRegistrato());
		
		// mostra l'xml
		System.out.print(serviceProfileDoc.toString());
		
		if (!serviceProfileDoc.validate())
			throw new Exception("L'xml non � conforme allo schema.");
		return serviceProfileDoc.toString();
	}
}
