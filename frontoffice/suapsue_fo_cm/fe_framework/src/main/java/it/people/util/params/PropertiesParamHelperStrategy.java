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
 * Created on 5-lug-2004
 *
 */
package it.people.util.params;

import it.people.core.Logger;
import it.people.process.AbstractPplProcess;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Zoppello
 * 
 */
public class PropertiesParamHelperStrategy implements IParamHelper {

    private Map propertiesMap = null;

    public PropertiesParamHelperStrategy() {
	propertiesMap = new HashMap();
    }

    public Properties getProperties(String resourceIdentifier) {
	if (propertiesMap.containsKey(resourceIdentifier)) {
	    Logger.debug("********** RESOURCE [" + resourceIdentifier
		    + "]  is Cached");
	    return (Properties) propertiesMap.get(resourceIdentifier);
	} else {
	    String realFileName = resourceIdentifier;

	    InputStream input = null;
	    Logger.debug("********** LOADING FROM CLASSPATH PROPERTY CALLED "
		    + realFileName);
	    input = getClass().getResourceAsStream(realFileName);
	    Properties prop = null;
	    if (input != null) {
		try {
		    prop = new Properties();
		    prop.load(input);
		} catch (IOException e) {

		    return null;
		}
		Logger.debug("********** LOADING OK *****************");
		Logger.debug("CACHING [" + resourceIdentifier + "] ");
		propertiesMap.put(resourceIdentifier, prop);
		return prop;
	    } else {
		Logger.debug("********** LOAD [" + realFileName + "] FAILED ");
		return null;

	    }

	}
    }

    public boolean exist(String paramName, Properties prop) {
	if (prop == null) {
	    return false;
	} else {
	    return prop.containsKey(paramName);
	}
    }

    public String get(String paramName, Properties prop) {
	if (exist(paramName, prop))
	    return prop.getProperty(paramName);
	else
	    return null;

    }

    /**
     * @see it.people.util.params.IParamHelper#getParameter(java.lang.String)
     */
    public String getParameter(AbstractPplProcess process, String paramName)
	    throws ParamNotFoundException {
	// 1) <nomeprocesso>_params_<codice comune>.properties

	// String viewName =
	// process.getProcessWorkflow().getView().getName().toLowerCase();
	String codiceComune = process.getCommune().getOid();
	String processName = process.getProcessName();
	String processBasePath = "/" + processName.replace('.', '/');
	Logger.debug(" Getting Parameter [" + paramName + "] for process ["
		+ process.getProcessName() + "] and Comune [" + codiceComune
		+ "]");
	String propertyFileName = processBasePath + "/risorse/parametri_"
		+ codiceComune + ".properties";
	Logger.debug(" Try in resource [" + propertyFileName + "]");

	Properties prop = getProperties(propertyFileName);
	if (exist(paramName, prop)) {
	    Logger.debug(" ===> Founded ");
	    return get(paramName, prop);
	}

	// non � corretto cancellare il contenuto delle properties
	// perch� altrimenti il file di properties risulta caricato
	// ma il suo contenuto � sempre vuoto e non � pi� possibile
	// accedere ai sui elementi.
	// if (prop != null) prop.clear();

	propertyFileName = processBasePath + "/risorse/parametri.properties";
	Logger.debug(" Try in resource [" + propertyFileName + "]");

	prop = getProperties(propertyFileName);
	if (exist(paramName, prop)) {
	    Logger.debug(" ===> Founded ");
	    return get(paramName, prop);
	}

	// non � corretto cancellare il contenuto delle properties
	// perch� altrimenti il file di properties risulta caricato
	// ma il suo contenuto � sempre vuoto e non � pi� possibile
	// accedere ai sui elementi.
	// if (prop != null) prop.clear();

	// 3) params_<codice comune>.properties
	propertyFileName = "/parametri/parametri_" + codiceComune
		+ ".properties";
	Logger.debug(" Try in resource [" + propertyFileName + "]");
	prop = getProperties(propertyFileName);
	if (exist(paramName, prop)) {
	    Logger.debug(" ===> Founded ");
	    return get(paramName, prop);
	}

	// non � corretto cancellare il contenuto delle properties
	// perch� altrimenti il file di properties risulta caricato
	// ma il suo contenuto � sempre vuoto e non � pi� possibile
	// accedere ai sui elementi.
	// if (prop != null) prop.clear();
	// 4) params.properties
	propertyFileName = "/parametri/parametri.properties";

	Logger.debug(" Try in resource [" + propertyFileName + "]");
	prop = getProperties(propertyFileName);
	if (exist(paramName, prop)) {
	    Logger.debug(" ===> Founded ");
	    return get(paramName, prop);
	}

	Logger.debug(" Parameter Not Found throw an exception ");
	throw new ParamNotFoundException();
    }

    /**
     * @see it.people.util.params.IParamHelper#paramExist(java.lang.String)
     */
    public boolean paramExist(AbstractPplProcess process, String paramName) {

	String processName = process.getProcessName();

	String processBasePath = "/" + processName.replace('.', '/');

	String codiceComune = process.getCommune().getOid();

	String propertyFileName1 = processBasePath + "/parametri_"
		+ codiceComune + ".properties";
	String propertyFileName2 = processBasePath + "/parametri.properties";
	String propertyFileName3 = "/parametri/parametri_" + codiceComune
		+ ".properties";
	String propertyFileName4 = "/parametri/parametri.properties";
	;

	return (exist(paramName, getProperties(propertyFileName1))
		|| exist(paramName, getProperties(propertyFileName2))
		|| exist(paramName, getProperties(propertyFileName3)) || exist(
		    paramName, getProperties(propertyFileName4)));
    }

}
