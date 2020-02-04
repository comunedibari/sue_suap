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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Andrea Piemontese - Engineering Ingegneria Informatica 08/06/2012
 */
public class TableValuesNameUtils {

    private static Logger logger = LogManager
	    .getLogger(TableValuesNameUtils.class);

    private static final String ESTENSIONE_CHARSET_FILE = ".cst";
    private static final String ESTENSIONE_FILE = ".dat";

    /**
     * 
     * Unpack the file name: extract the _ encoded informations from the file
     * name
     * 
     * @param datFileName
     * @return
     */
    public static UnpackedTableValueName getUnpackedTableValueName(
	    final String datFileName, final String completeClassPath,
	    final String absolutePath) {

	String process = getProcessNameFromFilePath(absolutePath,
		completeClassPath, datFileName);
	String nodeId = null;
	String locale = null;
	String tableId = null;
	String charset = null;

	if (logger.isDebugEnabled()) {
	    logger.debug("Process name for .dat file = " + process);
	}

	// Remove DAT extension
	String cleanFileName = datFileName;
	if (cleanFileName.endsWith(ESTENSIONE_FILE)) {
	    int datExtIndex = datFileName.lastIndexOf(ESTENSIONE_FILE);
	    cleanFileName = datFileName.substring(0, datExtIndex);
	}

	// Check if .CST charset file is present
	charset = getCharsetFromCharsetFile(getCharsetFilePathFromDatPath(absolutePath));

	String nodeIdLocaleRegExp = "^.+_[0-9]+_[a-zA-Z]{2}$";
	String localeRegExp = "^.+[a-zA-Z]+_[a-zA-Z]{2}$";
	String nodeIdRegExp = "^.+[a-zA-Z]+_[0-9]+$";

	// Controlla codice e locale per primo
	if (Pattern.matches(nodeIdLocaleRegExp, cleanFileName)) {
	    int localeUnderscorePosition = cleanFileName.lastIndexOf('_');
	    int nodeIdUnderscorePosition = cleanFileName.substring(0,
		    localeUnderscorePosition - 1).lastIndexOf('_');
	    nodeId = cleanFileName.substring(nodeIdUnderscorePosition + 1,
		    localeUnderscorePosition);
	    locale = cleanFileName.substring(localeUnderscorePosition + 1);
	    tableId = cleanFileName.substring(0, nodeIdUnderscorePosition);

	    // Controlla solo locale
	} else if (Pattern.matches(localeRegExp, cleanFileName)) {
	    int localeUnderscorePosition = cleanFileName.lastIndexOf('_');
	    locale = cleanFileName.substring(localeUnderscorePosition + 1);
	    tableId = cleanFileName.substring(0, localeUnderscorePosition);

	    // Controlla solo codice ente
	} else if (Pattern.matches(nodeIdRegExp, cleanFileName)) {
	    int nodeIdUnderscorePosition = cleanFileName.lastIndexOf('_');
	    nodeId = cleanFileName.substring(nodeIdUnderscorePosition + 1);
	    tableId = cleanFileName.substring(0, nodeIdUnderscorePosition);

	} else {
	    tableId = cleanFileName;
	}

	if (logger.isDebugEnabled()) {

	    logger.debug("tableId .dat file = " + tableId);
	    logger.debug("nodeId .dat file = " + nodeId);
	    logger.debug("locale .dat file = " + locale);
	    logger.debug("locale carset = " + charset);
	}

	return new UnpackedTableValueName(tableId, process, nodeId, locale,
		charset);
    }

    /**
     * Ritorna il process name in notazione dotted dal percorso file
     * 
     * @param completePath
     * @param completeClassesPath
     * @param fileName
     * @return
     */
    private static String getProcessNameFromFilePath(String completePath,
	    String completeClassesPath, String fileName) {

	// indice inizio nome del file compreso ultimo fileseparator
	int fileNameStartIndex = completePath.length()
		- (fileName.length() + 1);

	String result = completePath.substring(
		completeClassesPath.length() + 1, fileNameStartIndex).replace(
		System.getProperty("file.separator"), ".");

	// Remove Dat directory path
	int datDirIndex = result.lastIndexOf(".risorse.tabelle");
	if (datDirIndex > 0) {
	    result = result.substring(0, datDirIndex);
	}

	return result;
    }

    private static String getCharsetFromCharsetFile(String absoluteFileName) {

	String result = null;
	InputStream input = null;

	// Read from CST files
	try {
	    input = new FileInputStream(absoluteFileName);
	} catch (FileNotFoundException e1) {
	    // Do nothing charset file not exists..
	}

	if (input != null) {
	    try {
		InputStreamReader inputStreamReader = new InputStreamReader(
			input);
		BufferedReader reader = new BufferedReader(inputStreamReader);
		String line = reader.readLine();
		if (line != null) {
		    result = line;
		    logger.debug("Charset file impostato a '" + line + "'.");
		}
	    } catch (FileNotFoundException e) {
		logger.debug("Charset file non trovato", e);
	    } catch (IOException e) {
		logger.error("Errore durante la lettura del charset file", e);
	    }
	}

	return result;
    }

    /**
     * Return .cst file path from .dat file path simply replacing the file
     * extension
     * 
     * @param completePath
     *            the complete .dat file path.
     * @return
     */
    private static String getCharsetFilePathFromDatPath(String completePath) {

	String result = completePath;

	if (result.endsWith(ESTENSIONE_FILE)) {
	    int datExtIndex = result.lastIndexOf(ESTENSIONE_FILE);
	    result = result.substring(0, datExtIndex);
	}

	result = result + ".cst";

	return result;
    }

}
