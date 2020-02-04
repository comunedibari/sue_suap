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
package it.people.util.table.updater;

import java.io.File;
import java.io.FileFilter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Andrea Piemontese Engineering Ingegneria Informatica 08/06/2012
 */
public class TableValuesFileFilter implements FileFilter {

    private static Logger logger = LogManager
	    .getLogger(TableValuesFileFilter.class);

    /*
     * (non-Javadoc)
     * 
     * @see java.io.FileFilter#accept(java.io.File)
     */
    @Override
    public boolean accept(File pathname) {

	if (logger.isDebugEnabled()) {
	    logger.debug("Path name: " + pathname);
	    logger.debug("File name: " + pathname.getName());
	}

	return pathname.getName().endsWith(".dat")
		&& isTableValuePath(pathname);
    }

    /**
     * Controlla che il path contiene "risorse/tabelle" tipico dei tablevalues
     * 
     * @param pathname
     *            path completo
     * @return boolean
     */
    private boolean isTableValuePath(File pathname) {

	String tableValueFilePattern = "risorse"
		+ System.getProperty("file.separator") + "tabelle";
	String path = pathname.getPath().toLowerCase();

	return (path.indexOf(tableValueFilePattern) > 0);
    }

}
