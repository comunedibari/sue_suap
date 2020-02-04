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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Riccardo Foraf� - Engineering Ingegneria Informatica - Genova
 *         16/ago/2011 08.16.28
 */
public class DbTableStrategy implements ITableHelper {

    private static Logger logger = LogManager.getLogger(DbTableStrategy.class);
    private String defaultCharset = null;
    private TextTableStrategy delegate;

    /**
     * Costruttore
     */
    public DbTableStrategy() {
	super();
	delegate = new TextTableStrategy();
    }

    /**
     * @param process
     * @param tableId
     * @return
     * @throws TableNotFoundException
     *             -------------------------------
     * @see it.people.util.table.ITableHelper#getTableValues(it.people.process.AbstractPplProcess,
     *      java.lang.String)
     */
    public Collection getTableValues(String processName, String codiceComune,
	    String tableId) throws TableNotFoundException {

	Collection result = null;

	boolean isValidNodeId = !StringUtils.isBlank(codiceComune);

	if (logger.isDebugEnabled()) {
	    logger.debug("Process name: '" + processName + "'...");
	    logger.debug("Node id: '" + codiceComune + "'...");
	    if (isValidNodeId) {
		logger.debug("Getting TABLE Values [" + tableId
			+ "] for process [" + processName + "] and node id ["
			+ codiceComune + "]");
	    } else {
		logger.debug("Getting TABLE Values [" + tableId
			+ "] for process [" + processName + "]");
	    }
	}

	// Connection connection = null;
	// PreparedStatement preparedStatement = null;
	// ResultSet resultSet = null;

	if (logger.isDebugEnabled()) {
	    logger.debug("Values not found, switch to delegate...");
	}
	result = delegate.getTableValues(processName, codiceComune, tableId);
	if (result != null) {
	    return result;
	}

	if (logger.isDebugEnabled()) {
	    logger.debug("Values not found nor with db table strategy nor with delegate, throw an excpetion...");
	}

	throw new TableNotFoundException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.util.table.ITableHelper#setCharset(java.lang.String)
     */
    public void setCharset(String charset) {

	this.defaultCharset = charset;

    }

}
