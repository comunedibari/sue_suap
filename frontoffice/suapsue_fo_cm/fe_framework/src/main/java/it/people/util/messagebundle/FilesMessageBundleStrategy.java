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

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;

import it.people.core.Logger;

/**
 * @author Riccardo Foraf� - Engineering Ingegneria Informatica - Genova
 *         31/lug/2011 12.31.20
 */
public class FilesMessageBundleStrategy extends AbstractMessageBundleStrategy
	implements IMessageBundleHelper {

    /**
     * @param bundleBaseName
     */
    public FilesMessageBundleStrategy(String bundleBaseName) {
	super(bundleBaseName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.people.util.messagebundle.IMessageBundleHelper#getAllFrameworkBundles
     * (javax.servlet.ServletContext)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List getAllFrameworkBundles(ServletContext servletContext) {

	String resourcePath = servletContext
		.getRealPath("WEB-INF/classes/it/people/resources");
	File file = new File(resourcePath);
	String[] bundlesNames = file.list(new FilenameFilter() {
	    public boolean accept(File file, String name) {
		return name.startsWith("FormLabels");
	    }
	});

	for (int i = 0; i < bundlesNames.length; i++) {
	    bundlesNames[i] = bundleBaseName
		    + "."
		    + bundlesNames[i].substring(0,
			    bundlesNames[i].lastIndexOf('.'));
	}

	// I bundle sono ritornati senza essere salvati nella mappa per
	// evitare di cambiare le priorit�
	ArrayList bundlesList = new ArrayList();
	for (int i = 0; i < bundlesNames.length; i++) {
	    ResourceBundle resourceBundle = PropertyResourceBundle
		    .getBundle(bundlesNames[i]);
	    if (resourceBundle != null)
		bundlesList.add(resourceBundle);
	}
	return bundlesList;

    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.util.messagebundle.IMessageBundleHelper#
     * internalResourceBundleInstantiation(java.lang.String)
     */
    public ResourceBundle internalResourceBundleInstantiation(String bundleName)
	    throws IOException {

	InputStream is = this
		.getClass()
		.getClassLoader()
		.getResourceAsStream(
			bundleName.replace('.', '/') + ".properties");

	if (is == null) {
	    Logger.debug("bundle non trovato");
	    return null;
	}

	InputStreamReader inputStreamReader = new InputStreamReader(is);

	return new PropertyResourceBundle(inputStreamReader);

    }

}
