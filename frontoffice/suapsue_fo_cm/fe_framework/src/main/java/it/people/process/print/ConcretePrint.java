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
package it.people.process.print;

import it.people.City;
import it.people.process.AbstractPplProcess;
import it.people.util.MimeType;

import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * User: sergio Date: Sep 29, 2003 Time: 1:46:14 PM <br>
 * <br>
 * Classe astratta che definisce le caratteristiche che devono avere le classi
 * per la stampa dei processi.
 * 
 */
public abstract class ConcretePrint {
    private AbstractPplProcess m_parent;
    private String m_mimeType;
    private HashMap m_printers;
    protected String m_actualPrinterPath;
    private String m_printPage = null;

    /**
     * Costruttore.
     */
    public ConcretePrint() {
	m_parent = null;
	m_mimeType = null;
	m_actualPrinterPath = null;
	m_printers = new HashMap();
    }

    /**
     * Costruttore.
     * 
     * @param parent
     *            processo padre
     */
    public ConcretePrint(AbstractPplProcess parent) {
	this();
	m_parent = parent;
    }

    /**
     * Tutte le sottoclassi vengono inizzializzate usando questo metodo
     * 
     * @param comune
     *            Codice del comune
     * @param type
     *            MIME type
     */
    public abstract void initialize(City comune, MimeType type);

    public void setPplProcess(AbstractPplProcess parent) {
	m_parent = parent;
    }

    public AbstractPplProcess getParent() {
	return m_parent;
    }

    public void setParent(AbstractPplProcess p_parent) {
	m_parent = p_parent;
    }

    public String getMimeType() {
	return m_mimeType;
    }

    public void setMimeType(String p_mimeType) {
	m_mimeType = p_mimeType;
    }

    public void setPrintPage(String page) {
	m_printPage = page;
    }

    public String getPrintPage() {
	return m_printPage;
    }

    /**
     * Permette di aggiungere un nuovo printer
     * 
     * @param p_name
     *            Nome del printer
     * @param jspPath
     *            jsp da eseguire
     */
    public void addPrinter(String p_name, String jspPath) {
	m_printers.put(p_name, jspPath);
    }

    public Collection getPrintersNames() {
	return m_printers.keySet();
    }

    public String getJspPath(String p_name) {
	return ((String) m_printers.get(p_name));
    }

    /**
     * Esegue la funzione doPrint con il primo printer che trova
     * 
     * @param writer
     *            writer
     * @param request
     *            request
     * @param response
     *            response
     */
    public void print(Writer writer, HttpServletRequest request,
	    HttpServletResponse response) {
	if (request == null || response == null || writer == null)
	    return;
	/**
	 * Prendo il primo valore della map
	 */
	if (!m_printers.isEmpty()) {
	    m_actualPrinterPath = ((String) m_printers.values().iterator()
		    .next());
	    doPrint(writer, request, response);
	}
    }

    /**
     * Esegue la funzione doPrint con il printer specificato in ingresso
     * 
     * @param name
     *            Nome del printer da usare
     * @param writer
     *            writer
     * @param request
     *            request
     * @param response
     *            response
     */
    public void print(String name, Writer writer, HttpServletRequest request,
	    HttpServletResponse response) {
	if (request == null || response == null || writer == null)
	    return;
	m_actualPrinterPath = getJspPath(name);
	doPrint(writer, request, response);
    }

    /**
     * Le sottoclassi devono specificare dentro questo metodo come eseguire il
     * print
     * 
     * @param writer
     *            writer
     * @param request
     *            request
     * @param response
     *            response
     */
    protected abstract void doPrint(Writer writer, HttpServletRequest request,
	    HttpServletResponse response);
}
