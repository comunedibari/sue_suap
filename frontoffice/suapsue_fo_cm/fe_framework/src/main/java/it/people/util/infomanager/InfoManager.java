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
package it.people.util.infomanager;

import java.util.Hashtable;
import java.util.StringTokenizer;

public class InfoManager {
    private BeanParser parser;
    public static Hashtable instances;
    private String resourcePath;

    private InfoManager(String actualPackage) {
	resourcePath = "/" + actualPackage.replace('.', '/')
		+ "/risorse/messaggi.xml";
	this.parser = new BeanParser(resourcePath);
    }

    public static InfoManager getInstance(Class clazz) {
	String packageName = clazz.getPackage().getName();
	String actualPackage = tokenizePackageName(packageName);
	return getInstance(actualPackage);
    }

    public static InfoManager getInstance(String actualPackage) {
	if (instances == null) {
	    instances = new Hashtable();
	}

	InfoManager manager = (InfoManager) instances.get(actualPackage);

	if (manager == null) {
	    InfoManager temp = new InfoManager(actualPackage);
	    instances.put(actualPackage, temp);
	    manager = temp;
	}

	return manager;
    }

    public String get(String messageId) {
	String message = parser.findMessage(messageId);
	return message;
    }

    public String get(String comuneId, String serviceId, String messageId,
	    String lingua) {
	return get(messageId);
    }

    private static String tokenizePackageName(String packageName) {
	String actualPackage = "";
	StringTokenizer tk = new StringTokenizer(packageName, ".");
	for (int j = 0; j < 6; j++) {
	    actualPackage += tk.nextToken() + ".";
	}
	actualPackage += tk.nextToken();
	return actualPackage;
    }
}
