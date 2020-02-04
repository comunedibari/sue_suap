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
package it.people.util.table;

import it.people.City;
import it.people.util.CachedMap;
import it.people.util.PeopleProperties;

import java.util.Collection;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Zoppello
 * 
 */
public class TableHelper implements ITableHelper {

    private static Logger logger = LogManager.getLogger(TableHelper.class);

    private ITableStrategy strategy = null;
    private String defaultCharset = null;

    // Caching map
    protected static CachedMap tableValuesCache = null;

    private static final int TABLEVALUES_DEFAULT_EXPIRATION_UNIT = CachedMap.MINUTE;
    private static final int TABLEVALUES_DEFAULT_EXPIRATION_VALUE = 5;

    private static int expirationUnit = TABLEVALUES_DEFAULT_EXPIRATION_UNIT;
    private static int expirationValue = TABLEVALUES_DEFAULT_EXPIRATION_VALUE;

    private TableHelper(ITableStrategy strategy, String defaultCharset) {
	this.strategy = strategy;
	this.defaultCharset = defaultCharset;

	// Set strategy charset also..
	this.strategy.setCharset(this.defaultCharset);
    }

    private static TableHelper instance = null;

    /**
     * @deprecated utilizzare getInstance(City commune)
     * @return
     */
    public static TableHelper getInstance() {
	return getInstance(null);
    }

    public static TableHelper getInstance(City commune) {
	ITableStrategy initStrategy = null;
	if (instance == null) {
	    synchronized (TableHelper.class) {
		if (instance == null) {
		    try {
			String tableLoaderClass = "";
			if (commune == null)
			    tableLoaderClass = PeopleProperties.TABLE_LOADER_CLASS
				    .getValueString();
			else
			    tableLoaderClass = PeopleProperties.TABLE_LOADER_CLASS
				    .getValueString(commune.getKey());

			// Load charset from DB property
			String tableLoaderCharset = null;
			if (commune == null)
			    tableLoaderCharset = PeopleProperties.TABLE_LOADER_DEFAULT_CHARSET
				    .getValueString();
			else
			    tableLoaderCharset = PeopleProperties.TABLE_LOADER_DEFAULT_CHARSET
				    .getValueString(commune.getKey());

			initStrategy = (ITableStrategy) Class.forName(
				tableLoaderClass).newInstance();
			if (initStrategy == null)
			    initStrategy = new TextTableStrategy();
			if (tableLoaderCharset != null
				&& tableLoaderCharset.length() > 0) {
			    instance = new TableHelper(initStrategy,
				    tableLoaderCharset);
			} else {
			    instance = new TableHelper(initStrategy, null);
			}

			// Init Cache map
			initExpirationSettings(
				PeopleProperties.MESSAGE_BUNDLES_EXPIRATION_UNIT
					.getValueString(),
				PeopleProperties.MESSAGE_BUNDLES_EXPIRATION_VALUE
					.getValueString());
			tableValuesCache = new CachedMap(expirationUnit,
				expirationValue);
			logger.debug("Tablevalues cache initialized..");

		    } // try
		    catch (Exception ex) {
		    }
		} // if (_instance == null)
	    } // synchronized()
	} // if (_instance == null)
	return instance;
    }

    /**
     * @see it.people.util.params.ITableHelper#getTableValues(it.people.process.AbstractPplProcess,
     *      java.lang.String)
     */
    public Collection getTableValues(String processName, String codiceComune,
	    String tableId) throws TableNotFoundException {

	Collection result = null;

	// Check if cached, if not use strategy
	TableValueCachedMapKey mapKey = createValueCacheKey(processName,
		codiceComune, tableId);
	result = (Collection) tableValuesCache.get(mapKey);

	// Use Strategy if not in cache
	if (result == null) {

	    result = strategy
		    .getTableValues(processName, codiceComune, tableId);

	    // Add to cache
	    tableValuesCache.put(mapKey, result);
	}

	return result;
    }

    /**
     * Create a UnpackedTableValueName key to store the tablevalue in a
     * cachedMap
     * 
     * @param processName
     * @param codiceComune
     * @param tableId
     * @return
     */
    private TableValueCachedMapKey createValueCacheKey(String processName,
	    String codiceComune, String tableId) {

	return new TableValueCachedMapKey(tableId, processName, codiceComune);
    }

    /**
     * SetCharset for TableHelper and cascade on TableStratgy
     */
    public void setCharset(String charset) {

	this.defaultCharset = charset;
	// Set strategy charset also..
	strategy.setCharset(charset);
    }

    /**
     * @param unit
     * @param value
     */
    private static void initExpirationSettings(String unit, String value) {

	expirationUnit = setTableValuesExpirationUnit(unit);
	expirationValue = setTableValuesExpirationValue(value);

	if (expirationValue <= 0) {
	    expirationUnit = TABLEVALUES_DEFAULT_EXPIRATION_UNIT;
	}
    }

    /**
     * @param unit
     * @return
     */
    private static int setTableValuesExpirationUnit(String unit) {

	int result = TABLEVALUES_DEFAULT_EXPIRATION_UNIT;

	if (unit.equalsIgnoreCase("SECOND")) {
	    result = CachedMap.SECOND;
	}
	if (unit.equalsIgnoreCase("MINUTE")) {
	    result = CachedMap.MINUTE;
	}
	if (unit.equalsIgnoreCase("HOUR")) {
	    result = CachedMap.HOUR;
	}

	return result;
    }

    /**
     * @param value
     * @return
     */
    private static int setTableValuesExpirationValue(String value) {

	int result = TABLEVALUES_DEFAULT_EXPIRATION_VALUE;

	try {
	    result = Integer.parseInt(value);
	} catch (NumberFormatException e) {
	    result = -1;
	}
	return result;
    }

}
