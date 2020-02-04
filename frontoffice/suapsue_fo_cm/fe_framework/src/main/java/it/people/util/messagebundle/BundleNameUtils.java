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
package it.people.util.messagebundle;

import java.util.regex.Pattern;

/**
 * @author Riccardo Foraf� - Engineering Ingegneria Informatica - Genova
 *         24/set/2011 10.49.02
 */
public class BundleNameUtils {

    /**
     * @param bundleName
     * @return
     */
    public static UnpackedBundleName getUnpackedBundleName(
	    final String bundleName) {

	String framework = null;
	String process = null;
	String nodeId = null;
	String locale = null;
	boolean isFramework = bundleName.startsWith("it.people.resources");

	if (!isFramework) {
	    process = bundleName.substring(0,
		    bundleName.indexOf(".risorse.messaggi"));
	}

	String nodeIdLocaleRegExp = "^.+_[0-9]+_[a-zA-Z]{2}$";
	String localeRegExp = "^.+[a-zA-Z]+_[a-zA-Z]{2}$";
	String nodeIdRegExp = "^.+[a-zA-Z]+_[0-9]+$";
	if (Pattern.matches(nodeIdLocaleRegExp, bundleName)) {
	    int localeUnderscorePosition = bundleName.lastIndexOf('_');
	    int nodeIdUnderscorePosition = bundleName.substring(0,
		    localeUnderscorePosition - 1).lastIndexOf('_');
	    nodeId = bundleName.substring(nodeIdUnderscorePosition + 1,
		    localeUnderscorePosition);
	    locale = bundleName.substring(localeUnderscorePosition + 1);
	    if (isFramework) {
		framework = bundleName.substring(0, nodeIdUnderscorePosition);
	    }
	} else if (Pattern.matches(localeRegExp, bundleName)) {
	    int localeUnderscorePosition = bundleName.lastIndexOf('_');
	    locale = bundleName.substring(localeUnderscorePosition + 1);
	    if (isFramework) {
		framework = bundleName.substring(0, localeUnderscorePosition);
	    }
	} else if (Pattern.matches(nodeIdRegExp, bundleName)) {
	    int nodeIdUnderscorePosition = bundleName.lastIndexOf('_');
	    nodeId = bundleName.substring(nodeIdUnderscorePosition + 1);
	    if (isFramework) {
		framework = bundleName.substring(0, nodeIdUnderscorePosition);
	    }
	} else {
	    if (isFramework) {
		framework = bundleName;
	    }
	}

	return new UnpackedBundleName(framework, process, nodeId, locale);

    }

}
