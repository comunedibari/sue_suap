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
 * Creato il 26-giu-2006 da Cedaf s.r.l.
 *
 */
package it.people.layout.multicommune;

import it.people.util.CachedMap;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 */
public class MultiCommuneCss {
    protected static CachedMap pathMap = new CachedMap(CachedMap.MINUTE, 5);
    protected HttpSession session;

    public MultiCommuneCss(HttpServletRequest request) {
	this.session = request.getSession();
    }

    /**
     * Ritorna true se il css � sul filesystem, false altrimenti
     * 
     * @param cssUrl
     * @return
     */
    public boolean cssExist(String cssUrl) {
	// la chiave � rappresentata dal path vero e proprio del css
	Boolean value = (Boolean) pathMap.get(cssUrl);
	if (value == null) {
	    // ricerca il file sul filesystem
	    value = new Boolean(fileExist(cssUrl));
	    pathMap.put(cssUrl, value);
	}

	return value.booleanValue();
    }

    protected boolean fileExist(String url) {
	String realPath = this.session.getServletContext().getRealPath(url);
	File file = new File(realPath);
	return file.exists();
    }
}
