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
 * Created on 27-apr-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.people.validator;

import it.people.City;
import it.people.Step;
import it.people.core.Logger;
import it.people.process.AbstractPplProcess;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.ValidatorResourcesInitializer;
import org.apache.log4j.Category;

/**
 * @author Zoppello
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ValidatorLoader {

    private String validationRulesFileNames = "/validator/validator-rules.xml";
    private final static String RESOURCE_DELIM = ",";
    private static ValidatorLoader instance = null;
    private Map cache = null;

    private Category cat = Category
	    .getInstance(ValidatorLoader.class.getName());

    private static boolean OLDSTYLELOADER = true;

    public static ValidatorLoader getInstance() {
	if (instance == null) {
	    synchronized (ValidatorLoader.class) {
		if (instance == null) {
		    try {
			instance = new ValidatorLoader();
		    } // try
		    catch (Exception ex) {

		    } // catch(Exception ex) try
		} // if (_instance == null)
	    } // synchronized()
	} // if (_instance == null)
	return instance;
    } // public static

    private ValidatorLoader() {
	cache = new HashMap();
    }

    public ValidatorResources getResourcesForProcess(
	    AbstractPplProcess process, City city) {

	ValidatorResources aValidatorResources = null;

	String processName = process.getProcessName();
	String processBasePath = "/" + processName.replace('.', '/');

	if (cat.isDebugEnabled())
	    cat.debug("ValidatorLoader::getResourcesForProcess processName ["
		    + processName + "] processBasePath [" + processBasePath
		    + "]");

	Logger.debug("ValidatorLoader::getResourcesForProcess processName ["
		+ processName + "] processBasePath [" + processBasePath + "]");

	String fileName = processBasePath + "/risorse/validazione_"
		+ city.getOid() + ".xml";

	if (cache.containsKey(fileName)) {
	    if (cat.isDebugEnabled())
		cat.debug("ValidatorLoader::getResourcesForProcess Resource of file ["
			+ fileName + "] ARE CACHED RETURN");

	    Logger.debug("ValidatorLoader::getResourcesForProcess Resource of file ["
		    + fileName + "] ARE CACHED RETURN");
	    return (ValidatorResources) cache.get(fileName);
	}

	if (cat.isDebugEnabled())
	    cat.debug("ValidatorLoader::getResourcesForProcess try to load file ["
		    + fileName + "]");

	Logger.debug("ValidatorLoader::getResourcesForProcess try to load file ["
		+ fileName + "]");

	InputStream fstream = this.getClass().getResourceAsStream(fileName);

	if (cat.isDebugEnabled())
	    cat.debug("ValidatorLoader::getResourcesForProcess file loaded");

	// Carico il file con le regole generali
	if (cat.isDebugEnabled())
	    cat.debug("ValidatorLoader::getResourcesForProcess loading file '"
		    + validationRulesFileNames + "'");
	InputStream genericRuleStream = this.getClass().getResourceAsStream(
		validationRulesFileNames);

	if (cat.isDebugEnabled()) {
	    if (genericRuleStream != null)
		cat.debug("ValidatorLoader::getResourcesForProcess loaded resources '"
			+ validationRulesFileNames + "'");
	    else
		cat.debug("ValidatorLoader::getResourcesForProcess resources '"
			+ validationRulesFileNames + "' not found");
	}

	ValidatorResources resources = null;

	if (OLDSTYLELOADER)
	    resources = new ValidatorResources();

	BufferedInputStream bis = null;

	if (fstream != null) {
	    //
	    // Esiste un file specifico per quel comune carico quello
	    //
	    if (cat.isDebugEnabled())
		cat.debug("ValidatorLoader::getResourcesForProcess File ["
			+ fileName + "] EXIST IN CLASSPTAH LOADING");

	    Logger.debug("ValidatorLoader::getResourcesForProcess File ["
		    + fileName + "] EXIST IN CLASSPTAH LOADING");
	    bis = new BufferedInputStream(genericRuleStream);
	    try {
		// Modifica Cedaf s.r.l. - Michele Fabbri - 8 febbraio 2006
		// Modifica richiesta dal passaggio a Struts 1.2.8 che utilizza
		// commons-validator 1.1.4, dove ValidatorResourcesInitializer
		// e' deprecato, l'inizializzazione del ValidatorResources e'
		// fatta direttamente nel costruttore.
		if (OLDSTYLELOADER)
		    ValidatorResourcesInitializer.initialize(resources, bis,
			    false);
		else {
		    resources = new ValidatorResources(bis);
		}
	    } catch (Exception e) {
		cat.error("ValidatorLoader::getResourcesForProcess new ValidatorResources: \n"
			+ e);
		Logger.debug(("ValidatorLoader::getResourcesForProcess new ValidatorResources: \n" + e));
	    }

	    bis = null;
	    bis = new BufferedInputStream(fstream);
	    try {
		if (OLDSTYLELOADER)
		    ValidatorResourcesInitializer.initialize(resources, bis,
			    false);
		else {
		    resources = new ValidatorResources(bis);
		}
	    } catch (Exception e) {
		cat.error("ValidatorLoader::getResourcesForProcess new ValidatorResources: \n"
			+ e);
		Logger.debug(("ValidatorLoader::getResourcesForProcess new ValidatorResources: \n" + e));
	    }
	    Logger.debug("ValidatorLoader::getResourcesForProcess Loading of File ["
		    + fileName + "] OK ");

	    // Il process � fatto in automatico nel costruttore new
	    // ValidatorResources();
	    if (OLDSTYLELOADER)
		resources.process();

	    // Metto in cache
	    cache.put(fileName, resources);
	    return resources;

	} else {
	    Logger.debug("ValidatorLoader::getResourcesForProcess File ["
		    + fileName + "] NOT FOUND IN CLASSPATH");

	    fileName = processBasePath + "/risorse/validazione.xml";

	    if (cache.containsKey(fileName)) {
		Logger.debug("ValidatorLoader::getResourcesForProcess Resource of file ["
			+ fileName + "] ARE CACHED RETURN");
		return (ValidatorResources) cache.get(fileName);
	    }

	    Logger.debug("ValidatorLoader::try to load generic file ["
		    + fileName + "]");
	    //
	    // Esiste un file specifico per quel comune carico quello
	    //
	    fstream = getClass().getResourceAsStream(fileName);

	    bis = new BufferedInputStream(genericRuleStream);
	    try {
		if (OLDSTYLELOADER)
		    ValidatorResourcesInitializer.initialize(resources, bis,
			    false);
		else {
		    resources = new ValidatorResources(bis);
		}
	    } catch (Exception e) {
		cat.error("ValidatorLoader::getResourcesForProcess new ValidatorResources: \n"
			+ e);
		Logger.debug(("ValidatorLoader::getResourcesForProcess new ValidatorResources: \n" + e));
	    }

	    bis = null;
	    bis = new BufferedInputStream(fstream);
	    try {
		if (OLDSTYLELOADER)
		    ValidatorResourcesInitializer.initialize(resources, bis,
			    false);
		else {
		    resources = new ValidatorResources(bis);
		}
	    } catch (Exception e) {
		cat.error("ValidatorLoader::getResourcesForProcess new ValidatorResources: \n"
			+ e);
		Logger.debug(("ValidatorLoader::getResourcesForProcess new ValidatorResources: \n" + e));
	    }

	    Logger.debug("ValidatorLoader::try to load generic file loaded ["
		    + fileName + "]");

	    // Il process � fatto in automatico nel costruttore new
	    // ValidatorResources();
	    if (OLDSTYLELOADER)
		resources.process();

	    // Metto in cache
	    cache.put(fileName, resources);
	    return resources;

	}
    }

}
