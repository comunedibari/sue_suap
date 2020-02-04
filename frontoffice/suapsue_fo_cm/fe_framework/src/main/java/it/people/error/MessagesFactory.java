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
package it.people.error;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javax.xml.soap.MessageFactory;
import org.apache.log4j.Category;

public class MessagesFactory {

    private Category cat = Category.getInstance(MessageFactory.class.getName());
    private static MessagesFactory ourInstance;
    private HashMap m_messages;
    private HashMap m_bundles;

    private final String BUNDLE_NAME = "it.people.resources.Messages";
    private final String defaultCommuneKey = "000000";

    /**
     * 
     * @return Restituisce la factory dei messaggi
     */
    public synchronized static MessagesFactory getInstance() {
	if (ourInstance == null) {
	    ourInstance = new MessagesFactory();
	}
	return ourInstance;
    }

    private MessagesFactory() {
	m_messages = new HashMap();
	m_bundles = new HashMap();
    }

    /**
     * 
     * @param communeKey
     *            Chiave del comune
     * @param key
     *            Chiave del messaggio
     * @return Restituisce un oggetto errorMessage contenente il messaggio di
     *         errore
     */
    public errorMessage getErrorMessage(String communeKey, String key) {
	errorMessage eMessage = new errorMessage();
	String sMessage = getMessage(communeKey, key);
	if (sMessage != null) {
	    eMessage.addErrorMessage(sMessage);
	}
	return eMessage;
    }

    /**
     * 
     * @param communeKey
     *            Chiave del comune
     * @param key
     *            Collezione dei messaggi
     * @return Restituisce un oggetto errorMessage contenente i messaggi di
     *         errore
     */

    public errorMessage getErrorMessage(String communeKey, Collection key) {
	errorMessage eMessage = new errorMessage();
	String sMessage = null;
	Iterator iterator = key.iterator();
	while (iterator.hasNext()) {
	    sMessage = getMessage(communeKey, (String) iterator.next());
	    if (sMessage != null) {
		eMessage.addErrorMessage(sMessage);
	    }
	}
	return eMessage;
    }

    private String getMessage(String communeKey, String key) {
	String message = (String) m_messages.get(communeKey + key);
	if (message == null) {
	    message = read(communeKey, key);
	    if (message != null) {
		m_messages.put(communeKey + key, message);
	    }
	}
	return message;
    }

    private String read(String communeKey, String key) {
	ResourceBundle m_bunlde = getBundle(communeKey);
	if (m_bunlde != null) {
	    try {
		return m_bunlde.getString(key);
	    } catch (Exception e) {
		cat.error("Bundle " + BUNDLE_NAME + communeKey + key
			+ " non trovato");
	    }
	}
	return null;
    }

    private ResourceBundle getBundle(String communeKey) {
	ResourceBundle bundle = (ResourceBundle) m_bundles.get(communeKey);
	if (bundle == null) {
	    try {
		bundle = PropertyResourceBundle.getBundle(BUNDLE_NAME
			+ communeKey);
		m_bundles.put(communeKey, bundle);
	    } catch (MissingResourceException mre) {
		try {

		    bundle = PropertyResourceBundle.getBundle(BUNDLE_NAME
			    + defaultCommuneKey);
		    m_bundles.put(defaultCommuneKey, bundle);
		} catch (Exception e) {
		    cat.error("Bundle " + BUNDLE_NAME + defaultCommuneKey
			    + " non trovato");
		}
	    } catch (Exception e) {
		cat.error("Bundle " + BUNDLE_NAME + communeKey + " non trovato");
	    }
	}
	return bundle;
    }

    public void refresh() {
	m_bundles.clear();
	m_messages.clear();
    }

}
