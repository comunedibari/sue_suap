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
 * Creato il 20-giu-2006 da Cedaf s.r.l.
 *
 */
package it.people.layout.multicommune;

import it.people.City;
import it.people.PeopleConstants;
import it.people.util.CachedMap;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Michele Fabbri - Cedaf s.r.l. Questa classe permette di determinare
 *         il path di una risorsa web per il comune indicato, se la risorsa per
 *         il comune non esiste � ritornato il path della stessa risorsa ma
 *         nella posizione di default.
 */
public class MultiCommuneFile {
    protected static CachedMap pathMap = new CachedMap(CachedMap.MINUTE, 5);

    protected HttpSession session;
    protected String rootPath;

    /**
     * Consente di impostare il path dove sar� cercato l'elemento, il path
     * sar� composto nel modo seguente:
     * <tomcat_root>/<people>/<root_path>/<codice_comune>
     * 
     * @param context
     * @param rootPath
     *            path in cui cercare l'elemento (es. '/framework/view/generic')
     */
    public MultiCommuneFile(HttpServletRequest request, String rootPath) {
	this.session = request.getSession();
	this.rootPath = rootPath;
    }

    public MultiCommuneFile(HttpSession session, String rootPath) {
	this.session = session;
	this.rootPath = rootPath;
    }

    /**
     * Ritorna il path dell'elemento indicato, l'elemento � cercato
     * inizialmente nel contensto del comune corrente, se non � presente �
     * ritornato il path del comune di default. La prima ricerca avviene in:
     * 'tomcat_root'/'people'/'root_path'/'nestedPath'/'elementName' l'elemento
     * di default � dato dal path:
     * 'tomcat_root'/'people'/'root_path'/'nestedPath'/'elementName'
     * 
     * @param nestedPath
     *            path innestato (es. '/html' oppure '/css')
     * @param elementName
     *            elemento da cercare (es. 'header.jsp')
     * @return path dell'elemento
     */
    public String getFile(String nestedPath, String elementName) {
	String communeId = ((City) this.session
		.getAttribute(PeopleConstants.SESSION_NAME_COMMUNE)).getKey();
	// la chiave � sempre costruita con il codice comune
	// il path per� potrebbe anche contenere il riferimento
	// alla cartella default.
	String key = this.rootPath + "/" + nestedPath + "/" + elementName;

	String path = (String) pathMap.get(key);

	if (path == null) {

	    // il path non � ancora stato cercato
	    path = getFile(communeId, nestedPath, elementName);

	    // verifico se esiste il file specializzato per comune
	    if (!fileExist(path))
		path = getFile("", nestedPath, elementName);

	    pathMap.put(key, path);
	}

	return path;
    }

    /**
     * Ritorna il path reale dell'elemento indicato, l'elemento � cercato
     * inizialmente nel contensto del comune corrente, se non � presente �
     * ritornato il path del comune di default. La prima ricerca avviene in:
     * 'tomcat_root'/'people'/'root_path'/'nestedPath'/'elementName' l'elemento
     * di default � dato dal path:
     * 'tomcat_root'/'people'/'root_path'/'nestedPath'/'elementName'
     * 
     * @param nestedPath
     *            path innestato (es. '/html' oppure '/css')
     * @param elementName
     *            elemento da cercare (es. 'header.jsp')
     * @return path dell'elemento
     */
    public String getFileRealPath(String nestedPath, String elementName) {
	String communeId = ((City) this.session
		.getAttribute(PeopleConstants.SESSION_NAME_COMMUNE)).getKey();
	// la chiave � sempre costruita con il codice comune
	// il path per� potrebbe anche contenere il riferimento
	// alla cartella default.
	String key = this.rootPath + "/" + nestedPath + "/" + elementName;

	String path = (String) pathMap.get(key);

	if (path == null) {

	    // il path non � ancora stato cercato
	    path = getFile(communeId, nestedPath, elementName);

	    // verifico se esiste il file specializzato per comune
	    if (!fileExist(path))
		path = getFile("", nestedPath, elementName);

	    pathMap.put(key, path);
	}

	return getFileRealPath(path);
    }

    protected String getFile(String whereToSearch, String nestedPath,
	    String elementName) {

	String path = rootPath + nestedPath + "/" + elementName;

	int elementNameExtensionPosition = elementName.indexOf(".");

	if (elementNameExtensionPosition > 0
		&& !whereToSearch.equalsIgnoreCase("")) {
	    String elementNameWithoutExtension = elementName.substring(0,
		    elementNameExtensionPosition);
	    String elementNameExtension = elementName
		    .substring(elementNameExtensionPosition);
	    path = rootPath + nestedPath + "/" + elementNameWithoutExtension
		    + "_" + whereToSearch + elementNameExtension;
	}
	return path;
    }

    protected boolean fileExist(String url) {
	String realPath = this.session.getServletContext().getRealPath(url);
	File file = new File(realPath);
	return file.exists();
    }

    private String getFileRealPath(String url) {
	return this.session.getServletContext().getRealPath(url);
    }

}
