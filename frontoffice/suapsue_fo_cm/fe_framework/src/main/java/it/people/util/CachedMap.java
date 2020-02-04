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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Michele Fabbri - Cedaf s.r.l. Questa classe implementa una cache in
 *         cui gli elementi rimangono validi fino alla scadenza di un
 *         determinato periodo di tempo, scaduto il periodo di tempo gli
 *         elementi sono rimossi
 */
public class CachedMap {

    private static Logger logger = LogManager.getLogger(CachedMap.class);

    protected HashMap map;
    protected int cacheDuration;
    protected int measureUnit;

    public static final int NEVER = Calendar.YEAR;
    public static final int SECOND = Calendar.SECOND;
    public static final int MINUTE = Calendar.MINUTE;
    public static final int HOUR = Calendar.HOUR;

    /**
     * 
     * @param measureUnit
     *            unit� di misura della cache (es. CachedMap.MINUTE)
     * @param cacheDuration
     *            durata della cache nell'unit� di misura indicata
     */
    public CachedMap(int measureUnit, int cacheDuration) {
	this.map = new HashMap();
	this.cacheDuration = cacheDuration;
	this.measureUnit = measureUnit;
    }

    public void put(Object key, Object value) {
	this.map.put(key, new CachedElement(value));
    }

    /**
     * Ritorna l'elemento della mappa se questo non � scaduto. Se l'elemento
     * non � presente ritorna null. Se l'elemento � scaduto ritorna null,
     * gli elementi scaduti sono rimossi dalla mappa.
     * 
     * @param key
     * @return
     */
    public Object get(Object key) {

	if (logger.isDebugEnabled()) {
	    logger.debug("Searching for " + key + " in cached map...");
	}
	CachedElement elem = (CachedElement) this.map.get(key);

	// L'elemento non � in cache
	if (elem == null) {
	    if (logger.isDebugEnabled()) {
		logger.debug(key + " not found in cached map.");
	    }
	    return null;
	} else {
	    if (logger.isDebugEnabled()) {
		logger.debug(key + " found in cached map.");
	    }
	}

	if (logger.isDebugEnabled()) {
	    logger.debug("Checking if element " + key + " is expired...");
	}
	Calendar expiration = Calendar.getInstance();
	expiration.add(this.measureUnit, -this.cacheDuration);
	if (expiration.after(elem.getTime()) && this.measureUnit != NEVER) {
	    // l'elemento � scaduto
	    // � rimosso dalla mappa
	    if (logger.isDebugEnabled()) {
		logger.debug("Element " + key
			+ " expired, removing from map...");
	    }
	    this.map.remove(elem);
	    return null;
	}

	return elem.getValue();
    }

    private class CachedElement {
	protected Calendar time;
	protected Object value;

	public CachedElement(Object value) {
	    this.time = Calendar.getInstance();
	    this.value = value;
	}

	public CachedElement(Calendar time, Object value) {
	    this.time = time;
	    this.value = value;
	}

	public Calendar getTime() {
	    return time;
	}

	public void setTime(Calendar time) {
	    this.time = time;
	}

	public Object getValue() {
	    return value;
	}

	public void setValue(Object value) {
	    this.value = value;
	}
    }
}
