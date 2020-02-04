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
 * Created on 20-lug-2004
 */
package it.people.util.table;

import it.people.core.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Category;

/**
 * @author Bernabei Angelo Engineering S.p.A.
 * 
 */
public class TextTableStrategy implements ITableStrategy {

    private static final String CODIFICA_CARATTERI = ".cst";
    private static final String ESTENSIONE_FILE = ".dat";
    private static final String SEPARATORE = "|";

    private Category cat = Category.getInstance(TextTableStrategy.class
	    .getName());
    private String defaultCharset = null;

    /**
     * Costruttore
     */
    public TextTableStrategy() {
	super();
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
	cat.debug("ENTRO");
	cat.debug("PROCESSO:" + processName);
	cat.debug("COMUNE:" + codiceComune);
	String processBasePath = "/" + processName.replace('.', '/');

	cat.debug(" Getting TABLE Values [" + tableId + "] for process ["
		+ processName + "] and Comune [" + codiceComune + "]");

	// nome file = tableId_12345.dat
	String fileName = processBasePath + "/risorse/tabelle/" + tableId + "_"
		+ codiceComune + ESTENSIONE_FILE;
	String tableCharsetFileName = processBasePath + "/risorse/tabelle/"
		+ tableId + "_" + codiceComune + CODIFICA_CARATTERI;

	cat.debug(" Try in resource [" + fileName + "]");

	Collection result = null;
	result = getDocument(fileName,
		getCodificaCaratteri(tableCharsetFileName));

	if (result != null) {
	    cat.debug(" OK RETURNING");
	    return result;
	}

	// 2) Cerco se esiste la definizione della combo nel file
	// <process_name>_combo.xml

	fileName = processBasePath + "/risorse/tabelle/" + tableId
		+ ESTENSIONE_FILE;
	tableCharsetFileName = processBasePath + "/risorse/tabelle/" + tableId
		+ CODIFICA_CARATTERI;
	cat.debug(" Try in resource [" + fileName + "]");

	result = getDocument(fileName,
		getCodificaCaratteri(tableCharsetFileName));

	if (result != null) {
	    cat.debug(" OK RETURNING");
	    return result;
	}

	// 3) Cerco se esiste la definizione della combo nel file
	// /combo/<id_comune>_combo.xml
	fileName = "/tabelle/" + tableId + "_" + codiceComune + ESTENSIONE_FILE;
	tableCharsetFileName = "/tabelle/" + tableId + "_" + codiceComune
		+ CODIFICA_CARATTERI;
	cat.debug(" Try in resource [" + fileName + "]");
	result = getDocument(fileName,
		getCodificaCaratteri(tableCharsetFileName));

	if (result != null) {
	    cat.debug(" OK RETURNING");
	    return result;
	}

	cat.debug(" Combo Not Found throw an exception ");

	throw new TableNotFoundException();
    }

    /**
     * cerca il file e se lo trova lo legge e ritorna la collection con i vari
     * oggetti.
     * 
     * @param realFileName
     * @return
     */
    private Collection getDocument(String realFileName, String charset) {

	InputStream input = null;
	cat.debug("********** LOADING FROM CLASSPATH PROPERTY CALLED "
		+ realFileName);

	input = getClass().getResourceAsStream(realFileName);
	Collection ris = new ArrayList();
	if (input != null) {
	    try {

		InputStreamReader inputStreamReader = null;
		if (charset != null && charset.length() > 0) {
		    inputStreamReader = new InputStreamReader(input, charset);
		} else {
		    inputStreamReader = new InputStreamReader(input);
		}
		BufferedReader reader = new BufferedReader(inputStreamReader);
		StringTokenizer token;
		String line;
		String value = null;
		List label = null;
		OptionBean obj = null;
		while ((line = reader.readLine()) != null) {
		    token = new StringTokenizer(line, SEPARATORE);

		    label = new ArrayList();
		    for (int i = 0; token.hasMoreTokens(); i++) {
			if (i == 0) {
			    value = token.nextToken();
			} else {
			    label.add(token.nextToken());
			}
		    }
		    obj = new OptionBean(label, value);
		    ris.add(obj);
		}
		reader.close();
	    } catch (FileNotFoundException e) {
		cat.debug("FILE NON TROVATO", e);
		return null;
	    } catch (IOException e) {
		cat.error("ERRORE DURANTE LA LETTURA DEL FILE", e);
	    }
	    cat.debug("********** LOADING OK *****************");
	    return ris;
	} else {
	    Logger.debug("********** LOAD [" + realFileName + "] FAILED ");
	    return null;
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.util.table.ITableHelper#setCharset(java.lang.String)
     */
    public void setCharset(String charset) {

	this.defaultCharset = charset;

    }

    private String getCodificaCaratteri(String realFileName) {

	String result = null;

	InputStream input = getClass().getResourceAsStream(realFileName);
	if (input != null) {
	    try {
		InputStreamReader inputStreamReader = new InputStreamReader(
			input);
		BufferedReader reader = new BufferedReader(inputStreamReader);
		String line = reader.readLine();
		if (line != null) {
		    result = line;
		    cat.debug("Charset file impostato a '" + line + "'.");
		}
	    } catch (FileNotFoundException e) {
		cat.debug("Charset file non trovato", e);
	    } catch (IOException e) {
		cat.error("Errore durante la lettura del charset file", e);
	    }
	} else {
	    cat.debug("Charset file non trovato; switch su default charset");
	    result = this.defaultCharset;
	}

	return result;
    }

    public static void main(String[] args) {

	TextTableStrategy strategy = new TextTableStrategy();
	try {
	    Collection table = strategy
		    .getTableValues("it", "", "optionGiorni");
	    Iterator iterator = table.iterator();
	    while (iterator.hasNext()) {
		OptionBean optionBean = (OptionBean) iterator.next();
		System.out.println("Label = '" + optionBean.getLabel() + "'");
		System.out.println("Value = '" + optionBean.getValue() + "'\n");
	    }
	} catch (TableNotFoundException e) {
	    e.printStackTrace();
	}

    }

}
