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
 * Created on 7-mag-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.people.util;

import it.people.util.messagebundle.FilesMessageBundleStrategy;
import it.people.util.messagebundle.IMessageBundleHelper;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Zoppello
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MessageBundleHelper {

    private static Logger logger = LogManager
	    .getLogger(MessageBundleHelper.class);

    private static final String bundleBaseName = "it.people.resources";
    // protected static Map m_bundles = null;
    protected static CachedMap m_bundles = null;
    protected static IMessageBundleHelper strategy = null;
    private static boolean initiated = false;
    private static MessageBundleHelper instance = null;

    private static final int MESSAGE_BUNDLES_DEFAULT_EXPIRATION_UNIT = CachedMap.NEVER;
    private static final int MESSAGE_BUNDLES_DEFAULT_EXPIRATION_VALUE = 0;

    private static int messageBundlesExpirationUnit = MESSAGE_BUNDLES_DEFAULT_EXPIRATION_UNIT;
    private static int messageBundlesExpirationValue = MESSAGE_BUNDLES_DEFAULT_EXPIRATION_VALUE;

    public static void init() {
	synchronized (MessageBundleHelper.class) {
	    if (logger.isDebugEnabled()) {
		logger.debug("Initializing strategy and cached map...");
	    }
	    if (instance == null) {
		try {
		    // m_bundles = new HashMap();

		    if (logger.isDebugEnabled()) {
			logger.debug("Setting cache expiration parameters...");
		    }
		    initExpirationSettings(
			    PeopleProperties.MESSAGE_BUNDLES_EXPIRATION_UNIT
				    .getValueString(),
			    PeopleProperties.MESSAGE_BUNDLES_EXPIRATION_VALUE
				    .getValueString());

		    m_bundles = new CachedMap(messageBundlesExpirationUnit,
			    messageBundlesExpirationValue);
		    initiated = true;
		    // Eng:31072011->
		    String strategyClassName = "";
		    try {
			strategyClassName = PeopleProperties.MESSAGE_BUNDLES_LOADER_CLASS
				.getValueString();
			if (logger.isDebugEnabled()) {
			    logger.debug("Strategy class = "
				    + strategyClassName);
			}
			Class<?> strategyClass = Class
				.forName(strategyClassName);
			if (strategyClass.getSuperclass() == it.people.util.messagebundle.AbstractMessageBundleStrategy.class) {
			    Constructor<?> constructor = strategyClass
				    .getConstructor(String.class);
			    strategy = (IMessageBundleHelper) constructor
				    .newInstance(bundleBaseName);
			} else {
			    strategy = (IMessageBundleHelper) Class.forName(
				    strategyClassName).newInstance();
			}
		    } catch (Exception ignore) {
		    }
		    if (strategy == null) {
			if (logger.isDebugEnabled()) {
			    logger.debug("Switching to default strategy class FilesMessageBundleStrategy.");
			}
			strategy = new FilesMessageBundleStrategy(
				bundleBaseName);
		    }
		    // <-Eng:31072011
		} // try
		catch (Exception ex) {
		}
	    }
	}
    }

    /**
     * Ritorna il messaggio identificato da <key> nel bundle <bundle> e
     * sostituisce i parametri con <args> Se @bundle non contiene il messaggio
     * restituisce null.
     * 
     * @param bundle
     * @param messageKey
     * @param args
     * @return the string with message if key exist in bundle null otherwise
     */
    public static String message(ResourceBundle bundle, String messageKey,
	    Object[] args) {
	if (!initiated)
	    init();
	String message = null;
	String messageFormat = null;
	try {

	    messageFormat = bundle.getString(messageKey);

	    message = MessageFormat.format(messageFormat, args);
	} catch (Exception ex) {
	    // Non ho trovato la stringa
	}
	return message;
    }

    /**
     * Istanzia il bundle identificato da bundleName
     * 
     * @param bundleName
     * @return il bundle identificato da bundleName o null
     * @deprecated
     */
    public static ResourceBundle istantiateBundle(String bundleName) {
	if (!initiated)
	    init();
	ResourceBundle bundle = null;
	// try {

	/*
	 * Non utilizza il meccanismo di cache proprio di ResourceBundle
	 * perch� non � garantito che l'implementazione faccia cache della
	 * risorsa:
	 * 
	 * "Implementations of getBundle may cache instantiated resource bundles
	 * and return the same resource bundle instance multiple times."
	 * 
	 * tratto da
	 * http://java.sun.com/j2se/1.4.2/docs/api/java/util/ResourceBundle
	 * .html#
	 * getBundle(java.lang.String,%20java.util.Locale,%20java.lang.ClassLoader
	 * )
	 */

	bundle = (ResourceBundle) m_bundles.get(bundleName);
	if (bundle == null) {

	    try {
		// // Crea il nuovo bundle
		// Class clazz =
		// Class.forName("it.people.util.MessageBundleHelper");
		// InputStream is =
		// clazz.getClassLoader().getResourceAsStream(bundleName.replace('.',
		// '/')+".properties");
		//
		// if (is == null) {
		// Logger.debug("bundle non trovato");
		// return null;
		// }
		//
		// bundle = new PropertyResourceBundle(is);
		bundle = strategy
			.internalResourceBundleInstantiation(bundleName);
		// } catch(ClassNotFoundException cnfEx) {
		// Logger.debug("Errore nel caricamento del bundle: " +
		// cnfEx.getMessage());
	    } catch (IOException ioEx) {
		logger.error("Errore nel caricamento del bundle: "
			+ ioEx.getMessage());
	    } catch (Exception ex) {
		logger.error("Errore nel caricamento del bundle: "
			+ ex.getMessage());
	    }

	    // Non utilizza il getBundle perch� questo effettua la ricerca
	    // in base al locale di default.
	    // bundle = PropertyResourceBundle.getBundle(bundleName);

	    if (bundle != null) {
		m_bundles.put(bundleName, bundle);
		if (logger.isDebugEnabled()) {
		    logger.debug("Bundle  [" + bundleName
			    + "] istanziato e messo nella cache");
		}
	    }
	} else {
	    if (logger.isDebugEnabled()) {
		logger.debug("Bundle  [" + bundleName
			+ "] recuperato dalla cache");
	    }
	}

	/*
	 * } catch (MissingResourceException mre) { Logger.debug("Bundle [" +
	 * bundleName + "] Non trovato"); }
	 */

	return bundle;
    }

    /**
     * Istanzia il bundle identificato da bundleName. L'istanziazione considera
     * anche la lingua dell'utente estratta dal locale
     * 
     * In particolare l'ordine di caricamento � il seguente: * bundleName +
     * "_" + language1 + "_" + country1 + "_" + variant1 * bundleName + "_" +
     * language1 + "_" + country1 * bundleName + "_" + language1 * bundleName +
     * "_" + language2 + "_" + country2 + "_" + variant2 * bundleName + "_" +
     * language2 + "_" + country2 * bundleName + "_" + language2 * bundleName
     * 
     * @param bundleName
     * @param locale
     * @return il bundle identificato da bundleName o null
     */
    /*
     * public static ResourceBundle istantiateBundle(String bundleName, Locale
     * locale) { if (!initiated) init();
     * 
     * ResourceBundle bundle = null; try { // E' inutile utilizzare un
     * meccanismo di cache in quanto // le implementazioni di ReaourceBundle
     * gi� prevedono meccanismi di cache: // "Implementations of getBundle may
     * cache instantiated resource bundles // and return the same resource
     * bundle instance multiple times." // tratto da //
     * http://java.sun.com/j2se/
     * 1.4.2/docs/api/java/util/ResourceBundle.html#getBundle
     * (java.lang.String,%20java.util.Locale,%20java.lang.ClassLoader)
     * 
     * bundle = PropertyResourceBundle.getBundle(bundleName);
     * 
     * } catch (MissingResourceException mre) { Logger.debug("Bundle [" +
     * bundleName + "] Non trovato"); }
     * 
     * return bundle; }
     */

    /**
     * Ritorna i nomi dei bundles su cui sono cercati i messaggi.
     * 
     * @param processName
     *            nome processo
     * @param comuneKey
     *            chiave comune
     * @param locale
     *            locale dell'utente
     * @return array dei nomi dei bundle
     */
    public static String[] getBundlesName(String processName, String comuneKey,
	    Locale locale) {
	ArrayList bundlesName = new ArrayList(5);

	String language = null;
	if (locale != null)
	    language = locale.getLanguage();

	if (comuneKey != null) {
	    // Messaggi del servizio localizzati per comune e per lingua
	    // es.
	    // it.people.fsl.servizi.esempi.tutorial.serviziotutorial1.risorse.messaggi_040007_it
	    // N.B la localizzazione per lingua � gestita in automatico da
	    // ResourceBundle.getBundle()
	    if (language != null)
		bundlesName.add(processName + ".risorse.messaggi_" + comuneKey
			+ "_" + language);

	    // Messaggi del servizio localizzati per comune
	    // es.
	    // it.people.fsl.servizi.esempi.tutorial.serviziotutorial1.risorse.messaggi_040007
	    bundlesName.add(processName + ".risorse.messaggi_" + comuneKey);
	}

	// Messaggi del servizio localizzati per lingua
	// es.
	// it.people.fsl.servizi.esempi.tutorial.serviziotutorial1.risorse.messaggi_it
	if (language != null)
	    bundlesName.add(processName + ".risorse.messaggi_" + language);

	// Messaggi del servizio
	// es.
	// it.people.fsl.servizi.esempi.tutorial.serviziotutorial1.risorse.messaggi
	bundlesName.add(processName + ".risorse.messaggi");

	// Messaggi del comune
	bundlesName.addAll(Arrays.asList(getBundlesName(comuneKey, locale)));

	return (String[]) bundlesName.toArray(new String[0]);
    }

    public static String[] getBundlesName(String comuneKey, Locale locale) {
	ArrayList bundlesName = new ArrayList(2);

	String language = null;
	if (locale != null)
	    language = locale.getLanguage();

	if (comuneKey != null) {
	    // Messaggi del framework localizzati per comune e lingua
	    // it.people.resources.FormLabels
	    if (language != null)
		bundlesName.add(bundleBaseName + ".FormLabels_" + comuneKey
			+ "_" + language);

	    // Messaggi del framework localizzati per comune
	    // it.people.resources.FormLabels
	    bundlesName.add(bundleBaseName + ".FormLabels_" + comuneKey);
	}

	// Messaggi del framework localizzati per lingua
	// it.people.resources.FormLabels
	if (language != null)
	    bundlesName.add(bundleBaseName + ".FormLabels_" + language);

	// Messaggi del framework
	// it.people.resources.FormLabels
	bundlesName.add(bundleBaseName + ".FormLabels");

	return (String[]) bundlesName.toArray(new String[0]);
    }

    /**
     * Ritorna un messaggio collegato alla chiave passata.
     * 
     * @param key
     *            chiave del messaggio
     * @param args
     *            parametri da inserire nel messaggio
     * @param comuneKey
     *            chiave del comune
     * @param locale
     *            locale dell'utente
     * @return
     */
    public static String message(String key, Object[] args, String comuneKey,
	    Locale locale) {

	return message(key, args, null, comuneKey, locale);
    }

    /**
     * Ritorna un messaggio collegato alla chiave passata. Da utilizzare nello
     * svolgimento della pratica. N.B. Utilizzabile dai servizi di front-end
     * 
     * @param key
     *            chiave del messaggio
     * @param args
     *            parametri da inserire nel messaggio
     * @param processName
     *            nome del processo
     * @param comuneKey
     *            chiave del comune
     * @param locale
     *            locale dell'utente
     * @return
     */
    public static String message(String key, Object[] args, String processName,
	    String comuneKey, Locale locale) {

	if (!initiated)
	    init();

	String returnMessage = null;
	String[] bundlesNames = null;

	if (processName != null)
	    bundlesNames = getBundlesName(processName, comuneKey, locale);
	else
	    bundlesNames = getBundlesName(comuneKey, locale);

	for (int i = 0; i < bundlesNames.length; i++) {
	    ResourceBundle resourceBundle = istantiateBundle(bundlesNames[i]);
	    if (resourceBundle != null) {
		returnMessage = message(resourceBundle, key, args);
		if (returnMessage != null) {
		    if (logger.isDebugEnabled()) {
			logger.debug("Messaggio con chiave [" + key
				+ "] trovato nel Bundle [" + bundlesNames[i]
				+ "] Valore [" + returnMessage + "]");
		    }
		    return returnMessage;
		}
	    }
	}

	if (returnMessage == null) {
	    String logMessage = "Messaggio con chiave [" + key
		    + "] Non trovato in nessuno dei bundle [";
	    int i;
	    for (i = 0; i < bundlesNames.length - 1; i++)
		logMessage += bundlesNames[i];
	    logMessage += bundlesNames[i] + "]";
	    if (logger.isDebugEnabled()) {
		logger.debug(logMessage);
	    }
	}

	return returnMessage;
    }

    /**
     * Ritorna un List dei ResourceBundle di messaggi per il comune dato
     * 
     * @param comuneKey
     *            chiave del comune
     * @param locale
     *            locale dell'utente
     * @return List di ResourceBundle di messaggi
     */
    public static List getBundles(String comuneKey, Locale locale) {
	return getBundles(getBundlesName(comuneKey, locale));
    }

    /**
     * Ritorna un List dei ResourceBundle di messaggi per il servizio ed il
     * comune dati
     * 
     * @param comuneKey
     *            chiave del comune
     * @param locale
     *            locale dell'utente
     * @return List di ResourceBundle di messaggi
     */
    public static List getBundles(String processName, String comuneKey,
	    Locale locale) {
	return getBundles(getBundlesName(processName, comuneKey, locale));
    }

    /**
     * Ritorna un List di tutti i ResourceBundle associati al framework, sono
     * esclusi quelli dei servizi.
     * 
     * @param servletContext
     *            context dell'applicazione
     * @return List di ResourceBundle di messaggi
     */
    public static List getAllFrameworkBundles(ServletContext servletContext) {

	// Eng:31072011->
	// String resourcePath =
	// servletContext.getRealPath("WEB-INF/classes/it/people/resources");
	// File file = new File(resourcePath);
	// String[] bundlesNames = file.list(new FilenameFilter() {
	// public boolean accept(File file, String name) {
	// return name.startsWith("FormLabels");
	// }
	// });
	//
	// for (int i = 0; i < bundlesNames.length; i++) {
	// bundlesNames[i] = bundleBaseName + "." + bundlesNames[i].substring(0,
	// bundlesNames[i].lastIndexOf('.'));
	// }
	//
	// // I bundle sono ritornati senza essere salvati nella mappa per
	// // evitare di cambiare le priorit�
	// ArrayList bundlesList = new ArrayList();
	// for(int i = 0; i < bundlesNames.length; i++) {
	// ResourceBundle resourceBundle =
	// PropertyResourceBundle.getBundle(bundlesNames[i]);
	// if (resourceBundle != null)
	// bundlesList.add(resourceBundle);
	// }
	// return bundlesList;

	// <-Eng:31072011
	String strategyClassName = "";
	try {
	    strategyClassName = PeopleProperties.MESSAGE_BUNDLES_LOADER_CLASS
		    .getValueString();
	    Class<?> strategyClass = Class.forName(strategyClassName);
	    if (strategyClass.getSuperclass() == it.people.util.messagebundle.AbstractMessageBundleStrategy.class) {
		Constructor<?> constructor = strategyClass
			.getConstructor(String.class);
		strategy = (IMessageBundleHelper) constructor
			.newInstance(bundleBaseName);
	    } else {
		strategy = (IMessageBundleHelper) Class.forName(
			strategyClassName).newInstance();
	    }
	} catch (Exception ignore) {
	}
	if (strategy == null) {
	    strategy = new FilesMessageBundleStrategy(bundleBaseName);
	}

	return strategy.getAllFrameworkBundles(servletContext);
	// <-Eng:31072011

    }

    protected static List getBundles(String[] bundlesNames) {
	ArrayList bundlesList = new ArrayList();
	for (int i = 0; i < bundlesNames.length; i++) {
	    ResourceBundle resourceBundle = istantiateBundle(bundlesNames[i]);
	    if (resourceBundle != null)
		bundlesList.add(resourceBundle);
	}
	return bundlesList;
    }

    /**
     * @param unit
     * @param value
     */
    private static void initExpirationSettings(String unit, String value) {

	messageBundlesExpirationUnit = setMessageBundlesExpirationUnit(unit);
	messageBundlesExpirationValue = setMessageBundlesExpirationValue(value);

	if (messageBundlesExpirationValue <= 0) {
	    messageBundlesExpirationUnit = MESSAGE_BUNDLES_DEFAULT_EXPIRATION_UNIT;
	}

    }

    /**
     * @param unit
     * @return
     */
    private static int setMessageBundlesExpirationUnit(String unit) {

	if (logger.isDebugEnabled()) {
	    logger.debug("Required message bundles expiration unit = " + unit);
	}

	int result = MESSAGE_BUNDLES_DEFAULT_EXPIRATION_UNIT;

	if (unit.equalsIgnoreCase("SECOND")) {
	    if (logger.isDebugEnabled()) {
		logger.debug("Message bundles expiration unit set to SECONDS.");
	    }
	    result = CachedMap.SECOND;
	}
	if (unit.equalsIgnoreCase("MINUTE")) {
	    if (logger.isDebugEnabled()) {
		logger.debug("Message bundles expiration unit set to MINUTES.");
	    }
	    result = CachedMap.MINUTE;
	}
	if (unit.equalsIgnoreCase("HOUR")) {
	    if (logger.isDebugEnabled()) {
		logger.debug("Message bundles expiration unit set to HOURS.");
	    }
	    result = CachedMap.HOUR;
	}

	return result;

    }

    /**
     * @param value
     * @return
     */
    private static int setMessageBundlesExpirationValue(String value) {

	int result = MESSAGE_BUNDLES_DEFAULT_EXPIRATION_VALUE;

	try {
	    result = Integer.parseInt(value);
	} catch (NumberFormatException e) {
	    result = -1;
	}

	return result;

    }

}
