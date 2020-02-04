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
package it.people.console.web.controllers.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import it.people.console.system.AbstractLogger;
import it.people.console.utils.Constants;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 17/lug/2011 10.03.53
 *
 */
public class ActionsViewsCache extends AbstractLogger implements IActionsViewsCache {

	private static Map<String, String> cache = null;
	
	/**
	 * @throws IOException
	 */
	public ActionsViewsCache() throws IOException {
		
		if (getCache() == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Initializing views actions map cache...");
			}
			setCache(initCache());
		}
		
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	private Map<String, String> initCache() throws IOException {
		
		Map<String, String> result = new HashMap<String, String>();
		Properties properties = new Properties();
		if (logger.isDebugEnabled()) {
			logger.debug("Loading properties from " + Constants.ControllerUtils.CONTROLLERS_PROPERTIES_FILE + " file...");
		}
		properties.load(this.getClass().getResourceAsStream(Constants.ControllerUtils.CONTROLLERS_PROPERTIES_FILE));
		Set<Object> keys = properties.keySet();
		Iterator<Object> keysIterator = keys.iterator();
		while(keysIterator.hasNext()) {
			String key = String.valueOf(keysIterator.next());
			if (logger.isDebugEnabled()) {
				logger.debug("Verifying if key " + key + " is an action key...");
			}
			if (key.endsWith(".action")) {
				String controller = key.substring(0, key.length() - ".action".length());
				String view = properties.getProperty(controller + ".view");
				String action = properties.getProperty(key);
				if (logger.isDebugEnabled()) {
					logger.debug("Key is an action key and the associated controller is = " + controller);
					logger.debug("View key is = " + controller + ".view");
					logger.debug("Adding to cache: ");
					logger.debug("\n\tAction: " + action);
					logger.debug("\n\tView: " + view);
				}
				result.put(action, view);
			}
		}
		
		return result;
		
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.web.controllers.utils.IActionsViewsCache#getView(java.lang.String)
	 */
	public String getView(String action) {
		return String.valueOf(getCache().get(action));
	}

	/**
	 * @return the cache
	 */
	private static Map<String, String> getCache() {
		return cache;
	}

	/**
	 * @param cache the cache to set
	 */
	private static void setCache(Map<String, String> cache) {
		ActionsViewsCache.cache = cache;
	}

}
