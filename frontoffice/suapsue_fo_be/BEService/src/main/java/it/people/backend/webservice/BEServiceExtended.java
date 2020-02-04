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
package it.people.backend.webservice;

import it.people.backend.webservice.common.Constants;
import it.people.backend.webservice.common.IProvider;
import it.people.backend.webservice.exceptions.ProviderProcessException;
import it.people.backend.webservice.exceptions.ServicesPropertiesLoadException;
import it.people.backend.webservice.utils.StringUtils;
import it.people.envelope.EnvelopeFactory_modellazioneb116_envelopeb002;
import it.people.envelope.IEnvelopeFactory;
import it.people.envelope.IRequestEnvelope;
import it.people.envelope.exceptions.InvalidEnvelopeException;
import it.people.envelope.exceptions.NotAnEnvelopeException;

import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica
 *
 */
public class BEServiceExtended {

	private static Logger _logger = LoggerFactory.getLogger(BEService.class);

	private static Properties servicesProperties = null;
	
	private static final String SERVICES_PROPERTIES_FILE_NAME = "servicesProperties.xml";
	private static final String DEMO_SERVICE = "demo";
	
	private boolean errorState = false;
	
    public BEServiceExtended() {
// TODO sistemare G5
//		getLogger().setLevel(Level.DEBUG);
		if (getServicesProperties() == null) {
			try {
				if (getLogger().isDebugEnabled()) {
					getLogger().debug("Loading services properties...");
				}
				setServicesProperties(loadProperties());
				if (getLogger().isDebugEnabled()) {
					getLogger().debug("Services properties loaded.");
				}
			} catch (ServicesPropertiesLoadException e) {
				getLogger().error("Unable to load services properties. BE service not available.", e);
				this.setErrorState(true);
			}
		}
    	
    }
    
    public String process(String data) {

		String result = "";
		
		if (!this.isErrorState()) {
			if (getLogger().isDebugEnabled()) {
				getLogger().debug("Invocazione ws people.condivisi.ricercacodiceattivitaistat");
			}
			String service = getInvokedService(data);
			String providerClassName = StringUtils.nullToEmptyString(getServicesProperties().getProperty(service)).trim();

			if (providerClassName.equalsIgnoreCase(Constants.EMPTY_STRING)) {
				providerClassName = getServicesProperties().getProperty(DEMO_SERVICE).trim();
			}
			
			try {
				if (getLogger().isDebugEnabled()) {
					getLogger().debug("Loading provider class '" + providerClassName + "'");
				}
				Class<?> providerClass = Class.forName(providerClassName);
				if (getLogger().isDebugEnabled()) {
					getLogger().debug("Obtaining provider instance...");
				}
				IProvider provider = (IProvider)providerClass.newInstance();

				String xmlData = data;
				if (isEnvelope(data)) {
					if (getLogger().isDebugEnabled()) {
						getLogger().debug("Extracting data from envelope...");
					}
					xmlData = getEnvelope(data).getContenuto().getContenutoXML();
				}
				
				if (getLogger().isDebugEnabled()) {
					getLogger().debug("Calling provider process method...");
				}
				result = provider.process(xmlData);
				if (getLogger().isDebugEnabled()) {
					getLogger().debug("Result from provider process is:\n\n");
					getLogger().debug(result);
					getLogger().debug("\n\n");
				}
			} catch (ClassNotFoundException e) {
				getLogger().error("Error while obtaining provider class.", e);
			} catch (InstantiationException e) {
				getLogger().error("Error while obtaining provider class.", e);
			} catch (IllegalAccessException e) {
				getLogger().error("Error while obtaining provider class.", e);
			} catch (ProviderProcessException e) {
				getLogger().error("Error while processing provider.", e);
			}
		}
		else {
			if (getLogger().isDebugEnabled()) {
				getLogger().debug("BE service is in an error state. No available processing.");
			}
		}

		return result;
		
    	
    }
    

	private String getInvokedService(String xmlIn) {
		
		String result = "";
		
		if (getLogger().isDebugEnabled()) {
			getLogger().debug("retrieving invoked service...");
		}
		
		if (isEnvelope(xmlIn)) {

			if (getLogger().isDebugEnabled()) {
				getLogger().debug("Envelope service...");
			}
			
			result = getEnvelope(xmlIn).getNomeServizio();
			
		}
		else {

			if (getLogger().isDebugEnabled()) {
				getLogger().debug("Not envelope service...");
			}
			
			org.apache.axis.MessageContext messageContext = org.apache.axis.MessageContext.getCurrentContext();

			if (messageContext != null) {
				result = messageContext.getTargetService();
			}
			
		}
				
		if (getLogger().isDebugEnabled()) {
			getLogger().debug("invoked service = " + result);
		}
		
		return result;
				
	}
	
	private static Properties loadProperties() throws ServicesPropertiesLoadException {
		
		Properties result = null;
		
		InputStream servicesPropertiesFile = BEService.class.getResourceAsStream(SERVICES_PROPERTIES_FILE_NAME);
		
		result = new Properties();
		
		try {
			result.loadFromXML(servicesPropertiesFile);
		} catch (InvalidPropertiesFormatException e) {
			throw new ServicesPropertiesLoadException(e);
		} catch (IOException e) {
			throw new ServicesPropertiesLoadException(e);
		}
		
		return result;
		
	}

	private static Logger getLogger() {
		return _logger;
	}

	private static Properties getServicesProperties() {
		return servicesProperties;
	}

	private static void setServicesProperties(Properties servicesProperties) {
		BEServiceExtended.servicesProperties = servicesProperties;
	}

	public final boolean isErrorState() {
		return errorState;
	}

	private void setErrorState(boolean errorState) {
		this.errorState = errorState;
	}

	private IRequestEnvelope getEnvelope(String data) {

		IRequestEnvelope result = null;
		
		try {
	      	IEnvelopeFactory envelopeFactory = new EnvelopeFactory_modellazioneb116_envelopeb002();
	      	result = (IRequestEnvelope)envelopeFactory.createEnvelopeBean(data);
		} catch (InvalidEnvelopeException e) {
			//e.printStackTrace();
		} catch (NotAnEnvelopeException e) {
			//e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
		return result;
		
	}
	
	private boolean isEnvelope(String data) {

		return getEnvelope(data) != null;
		
	}
    
}
