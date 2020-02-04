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
package it.people.util.attach;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import it.people.process.AbstractPplProcess;
import it.people.process.common.entity.Attachment;
import it.people.util.PeopleProperties;

public class UtilityAttach {

    private String codiceCommune;
    private String idPratica;
    private String urlAllegatiService;
    private static String metodoGetFile = "getFile";
    private static String metodoGetListaFile = "getListaAllegati";
    private static String metodoPutFile = "putFile";

    public UtilityAttach(AbstractPplProcess pplProcess,
	    HttpServletRequest request) {
	try {
	    this.codiceCommune = pplProcess.getCommune().getKey();
	    this.idPratica = pplProcess.getIdentificatoreProcedimento();
	    this.urlAllegatiService = "null";
	    if (request != null && request.getSession() != null
		    && request.getSession().getServletContext() != null) {
		this.urlAllegatiService = request.getSession()
			.getServletContext()
			.getInitParameter("UrlRemoteAttachFile");
	    }
	} catch (Exception e) {
	}
    }

    public String getMetaInfoFile(Attachment attach) {
	String path = attach.getPath();
	String codFile = "";
	int idx = path.lastIndexOf(File.separator);

	if (idx != -1) {
	    String nome = path.substring(idx + 1, path.length());
	    if (nome.indexOf("_") != -1) {
		codFile = nome.substring(0, (nome.indexOf("_")));
	    }
	}
	String ret = this.codiceCommune + "||" + this.idPratica + "||"
		+ codFile + "||" + this.urlAllegatiService + "||"
		+ this.metodoGetFile;
	return ret;
    }

}
