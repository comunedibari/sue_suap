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
package it.people.util;

import it.people.process.AbstractPplProcess;
import it.people.vsl.PipelineDataHolder;

import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.log4j.Category;
import org.apache.log4j.Level;

public class ActivityLogger {

    private static ActivityLogger ourInstance;
    private Category errorCat = Category.getInstance(ActivityLogger.class
	    .getName());

    public static final int FATAL = Level.FATAL_INT;
    public static final int ERROR = Level.ERROR_INT;
    public static final int WARN = Level.WARN_INT;
    public static final int INFO = Level.INFO_INT;
    public static final int DEBUG = Level.DEBUG_INT;

    public static synchronized ActivityLogger getInstance() {
	if (ourInstance == null) {
	    ourInstance = new ActivityLogger();
	}
	return ourInstance;
    }

    private ActivityLogger() {
    }

    /**
     * La mappa deve contenere delle stringe. La chiave verr? usata come
     * etichetta, il valore come messaggio (es: oid: 123456).
     * 
     * @param mappa
     *            Mappa contenente le stringe da scrivere sul file di log
     * @param comuneId
     *            Id del Comune
     * @param message
     *            Messaggio iniziale
     * @param errorLevel
     *            Liveelo di errore
     */
    public void log(LinkedHashMap mappa, String comuneId, String message,
	    int errorLevel) {
	try {
	    if (comuneId == null)
		comuneId = "000000";

	    Category cat = Category.getInstance(comuneId);

	    Iterator iter = mappa.keySet().iterator();
	    while (iter.hasNext()) {
		String key = (String) iter.next();
		message = message + " -- " + key + ": "
			+ (String) mappa.get(key);
	    }

	    genericLog(cat, message, errorLevel);

	} catch (Exception e) {
	    errorCat.error(e);
	}

    }

    /**
     * 
     * @param clazz
     * @param message
     * @param errorLevel
     */
    public void log(Class clazz, String message, int errorLevel) {
	try {
	    Category cat = Category.getInstance(clazz);
	    genericLog(cat, message, errorLevel);
	} catch (Exception e) {
	    errorCat.error(e);
	}
    }

    /**
     * 
     * @param comuneId
     *            Id del comune
     * @param userId
     *            Utente
     * @param processName
     *            Nome processo
     * @param processOid
     *            Oid del processo
     * @param message
     *            Messaggio
     * @param errorLevel
     *            Livello di errore
     */
    public void log(String comuneId, String userId, String processName,
	    String processOid, String message, int errorLevel) {
	try {
	    if (comuneId == null)
		comuneId = "000000";

	    Category cat = Category.getInstance(comuneId);
	    String messaggio = message + " -- " + "processo: " + processName
		    + " -- " + "oid: " + processOid + " -- " + "owner:"
		    + userId + " -- ";

	    genericLog(cat, messaggio, errorLevel);
	} catch (Exception e) {
	    errorCat.error(e);
	}
    }

    /**
     * 
     * @param comuneId
     *            Id del comune
     * @param userId
     *            Utente
     * @param processName
     *            Nome processo
     * @param processOid
     *            Oid del processo
     * @param message
     *            Messaggio
     * @param errorLevel
     *            Livello di errore
     */
    public void log(String comuneId, String userId, String processName,
	    Long processOid, String message, int errorLevel) {
	try {
	    if (comuneId == null)
		comuneId = "000000";

	    Category cat = Category.getInstance(comuneId);
	    String messaggio = message + " -- " + "processo: " + processName
		    + " -- " + "oid: " + processOid + " -- " + "owner:"
		    + userId + " -- ";

	    genericLog(cat, messaggio, errorLevel);
	} catch (Exception e) {
	    errorCat.error(e);
	}
    }

    /**
     * 
     * @param process
     *            Processo da loggare
     * @param message
     *            Messaggio da scrivere
     * @param errorLevel
     *            Livello di errore.
     */
    public void log(AbstractPplProcess process, String message, int errorLevel) {
	try {
	    Category cat = Category.getInstance(process.getCommune().getOid());

	    String messaggio = message
		    + " -- "
		    +
		    // "processo: " + process.getCurrentContent() + " -- " +
		    "processo: " + process.getProcessName() + " -- " + "oid: "
		    + process.getOid() + " -- " + "owner:" + process.getOwner()
		    + " -- ";

	    genericLog(cat, messaggio, errorLevel);
	} catch (Exception e) {
	    errorCat.error(e);
	}
    }

    /**
     * 
     * @param p_itemToProcess
     * @param message
     * @param errorLevel
     */
    public void log(PipelineDataHolder p_itemToProcess, String message,
	    int errorLevel) {
	try {

	    Category cat = Category.getInstance("RMS_Sender");

	    genericLog(cat, message, errorLevel);
	} catch (Exception e) {
	    errorCat.error(e);
	}
    }

    public void genericLog(Category cat, String message, int errorLevel) {
	if (errorLevel == DEBUG) {
	    cat.debug(message);
	} else if (errorLevel == INFO) {
	    cat.info(message);
	} else if (errorLevel == WARN) {
	    cat.warn(message);
	} else if (errorLevel == ERROR) {
	    cat.error(message);
	} else if (errorLevel == FATAL) {
	    cat.fatal(message);
	}
    }
}
