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
 * Creato il 10-lug-2006 da Cedaf s.r.l.
 *
 */
package it.people.filters.AdminAuthentication;

import java.util.Hashtable;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 */
public class WebConfigReader implements AdminConfigReader {
    /**
     * Legge la configurazione degli amministratori dal file xml
     * 
     * @param initParameter
     *            HashMap dei parametri di configurazione, accetta come
     *            parametri: key = 'xmlConfigReaderFileName' � il nome del
     *            file xml contenente la configurazione
     */
    public AdminConfig get(Hashtable initParameter) throws AdminConfigException {

	AdminConfig adminConfig = new AdminConfig();
	AdminConfigElement adminConfigElement = new AdminConfigElement();
	adminConfig.add(adminConfigElement);
	for (int i = 0; i < AdminConfigElement.PARAMNAMES.length; i++) {
	    String paramName = AdminConfigElement.PARAMNAMES[i];
	    String paramValue = (String) initParameter.get(paramName);
	    if (paramValue != null)
		adminConfigElement.putUserParameter(paramName, paramValue);
	}
	return adminConfig;
    }
}
