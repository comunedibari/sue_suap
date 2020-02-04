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
package it.people.console.beservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import java.util.Collections;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import it.people.console.system.MessageSourceAwareClass;
import it.people.console.dto.ExtendedAvailableService;
import it.people.console.dto.UnavailableBEServiceDTO;
import it.people.console.persistence.IPersistenceBroker;

import it.people.console.persistence.exceptions.PersistenceBrokerException;

/**
 * This Bean checks for BE service availability checking ?WSDL response
 * 
 * @author Andrea Piemontese - Engineering Ingegneria Informatica S.p.A.
 * @created 09/ott/2012 15:33:05
 *
 */
@Service
public class BeServicesChecker extends MessageSourceAwareClass {
	
	@Autowired
	private IPersistenceBroker persistenceBroker;
	
//	private static BeServicesChecker instance;
	
	private static List <UnavailableBEServiceDTO> unavailableBeServices = Collections
			.synchronizedList(new ArrayList<UnavailableBEServiceDTO>());

	public BeServicesChecker() {
			
	}
	
//	@PostConstruct
//	private void initInstance() {
//		instance = this;
//	}
//	
//	public static BeServicesChecker getIntance() {
//		return instance;
//	}

	/**
	 * get the list of unavailable BeServices.
	 * This function check each BE node URL
	 * @return
	 */
	public List <UnavailableBEServiceDTO> updateBeServicesAvailability() {
		
		List <UnavailableBEServiceDTO> unreachableBE = new ArrayList <UnavailableBEServiceDTO>();
		
		try {
			Map<String, List<ExtendedAvailableService>> beServicesGroupedByURL = persistenceBroker.getRegisteredBEServicesGroupByURL();
			
			for (Map.Entry <String, List<ExtendedAvailableService>> entry : beServicesGroupedByURL.entrySet()) {
				
				//Test URL of service
				if (!isWebServiceReachable(entry.getKey())) {
					
					List<ExtendedAvailableService> affectedServices = entry.getValue();
					
					//Create a bean for the unavailable service
					UnavailableBEServiceDTO serviceDown = new UnavailableBEServiceDTO(); 
					
					serviceDown.setBackEndURL(entry.getKey());
					if (!affectedServices.isEmpty()) {
						serviceDown.setLogicalServiceName(affectedServices.get(0).getServiceName());
					}
					
					//Get all nodes affected by the unavailable service
					Iterator <ExtendedAvailableService> affectedServicesIter = affectedServices.iterator();
					while (affectedServicesIter.hasNext()) {
						ExtendedAvailableService current = affectedServicesIter.next();
						serviceDown.getAffectedNodes().add(current.getCommuneKey());
						serviceDown.setLogicalServiceName(current.getServiceName());
					}
					
					unreachableBE.add(serviceDown);
				}
				
				//Update sync local list of unavailableBeServices
				synchronized(unavailableBeServices) {
					
					unavailableBeServices.clear();
					unavailableBeServices.addAll(unreachableBE);
				}
			}
		
			logger.debug("Found " + unavailableBeServices.size() + " BE services unreachable or down.");

		} catch (PersistenceBrokerException e) {
			logger.error("Exception retrieving registered BE services", e);
		}
		
		return unavailableBeServices;
	}

	/**
	 * Checks id URL is reachable trying to retrieve the WSDL from URL.
	 * @param wsUrl full url of web service (without ?WSDL ending)
	 * @return
	 */
	private boolean isWebServiceReachable(String wsUrl) {
		
		boolean result = false;
		InputStream content = null;
		BufferedReader reader = null;
		URL url = null;
		
		try {
			url = new URL(wsUrl +"?WSDL");
			content = url.openConnection().getInputStream();
			reader = new BufferedReader(new InputStreamReader(content));
			
			if (reader != null) {
				int linesToRead = 10;
				String line = reader.readLine();
			
				while ((line != null) && (linesToRead >= 0) && (!result)) {
				
					if (line.contains("<wsdl:definitions") || line.contains("<definitions")) {
						result = true;
					}
					line = reader.readLine();
					linesToRead -=1;
				}
			}
			
		} catch (MalformedURLException e) {
			logger.debug("Malformed BE Service URL: "+ url);

		} catch (IOException e) {
			logger.debug("IO Exception opening BE Service URL: "+ url);
		} catch (Exception e) {
			logger.debug("Exception opening BE Service URL: "+ url);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) { 
					//do nothing
				}
			}
			if (content != null) {
				try {
					content.close();
				} catch (IOException e) { 
					//do nothing
				}
			}
		}
		return result;
	}

	/**
	 * @return the unavailableBeServices
	 */
	public List<UnavailableBEServiceDTO> getUnavailableBeServices() {
		return unavailableBeServices;
	}
	
	
	
	
}
