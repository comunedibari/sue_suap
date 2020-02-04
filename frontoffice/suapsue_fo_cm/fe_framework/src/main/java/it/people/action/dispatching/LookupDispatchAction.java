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
 * Creato il 26-mag-2006 da Cedaf s.r.l.
 *
 */
package it.people.action.dispatching;

import it.people.util.MessageBundleHelper;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.MessageResourcesConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.MessageResources;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 */
public abstract class LookupDispatchAction extends
	org.apache.struts.actions.LookupDispatchAction {

    protected String getLookupMapName(HttpServletRequest request,
	    String keyName, ActionMapping mapping) throws ServletException {

	// Ricerco la chiave all'interno dei messageResources dei comuni
	// Based on this request's Locale get the lookupMap
	Map lookupMap = null;

	synchronized (localeMap) {
	    Locale userLocale = this.getLocale(request);
	    lookupMap = (Map) this.localeMap.get(userLocale);

	    if (lookupMap == null) {
		lookupMap = this.initLookupMapCommune(request, userLocale);
		this.localeMap.put(userLocale, lookupMap);
	    }
	}

	// ricerca standard
	return super.getLookupMapName(request, keyName, mapping);
    }

    /**
     * La ricerca deve essere estesa al file di messaggi dei vari comuni
     */
    private Map initLookupMapCommune(HttpServletRequest request,
	    Locale userLocale) {
	Map lookupMap = new HashMap();
	this.keyMethodMap = this.getKeyMethodMap();

	ModuleConfig moduleConfig = (ModuleConfig) request
		.getAttribute(Globals.MODULE_KEY);

	MessageResourcesConfig[] mrc = moduleConfig
		.findMessageResourcesConfigs();

	// Look through all module's MessageResources
	for (int i = 0; i < mrc.length; i++) {
	    MessageResources resources = this.getResources(request,
		    mrc[i].getKey());

	    // Look for key in MessageResources
	    Iterator iter = this.keyMethodMap.keySet().iterator();
	    while (iter.hasNext()) {
		String key = (String) iter.next();
		String text = resources.getMessage(userLocale, key);

		// Found key and haven't added to Map yet, so add the text
		if ((text != null) && !lookupMap.containsKey(text)) {
		    lookupMap.put(text, key);
		}
	    }
	}

	// Ricerca nei file di messaggi del framework
	List bundlesList = MessageBundleHelper.getAllFrameworkBundles(request
		.getSession().getServletContext());
	for (Iterator iter = bundlesList.iterator(); iter.hasNext();) {
	    ResourceBundle resources = (ResourceBundle) iter.next();
	    Enumeration enumeration = resources.getKeys();
	    while (enumeration.hasMoreElements()) {
		String key = (String) enumeration.nextElement();
		String text = resources.getString(key);

		// Found key and haven't added to Map yet, so add the text
		if ((text != null) && !lookupMap.containsKey(text)) {
		    lookupMap.put(text, key);
		}
	    }
	}

	return lookupMap;
    }
}
