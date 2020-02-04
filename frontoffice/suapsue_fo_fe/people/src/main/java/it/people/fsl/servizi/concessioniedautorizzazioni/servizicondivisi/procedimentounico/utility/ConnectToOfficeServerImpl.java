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
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.sun.star.beans.XPropertySet;
import com.sun.star.bridge.XUnoUrlResolver;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;

public class ConnectToOfficeServerImpl implements ConnectToOfficeServer {

//    private static final Logger log = LoggerFactory.getLogger(ConnectToOfficeServerImpl.class);

    private XComponentLoader xcomponentloader = null;
    private XComponentContext xComponentContext = null;
    private String port;

    private String host;

    public ConnectToOfficeServerImpl(String host, String port) {
	this.port = port;
	this.host = host;
    }

    /**
     * Connette il servizio openoffice con il componente UnoUrlResolver che risolve il percorso dove si torva il file da
     * convertire
     * */
    public XComponentLoader getConnection() {
	try {
	    String sConnectionString = "uno:socket,host=" + host + ",port=" + port + ";urp;StarOffice.ServiceManager";

	    /*
	     * Bootstraps a component context with the jurt base components registered. Component context to be granted
	     * to a component for running. Arbitrary values can be retrieved from the context.
	     */
	    this.xComponentContext = com.sun.star.comp.helper.Bootstrap.createInitialComponentContext(null);

	    /*
	     * Gets the service manager instance to be used (or null). This method has been added for convenience,
	     * because the service manager is a often used object.
	     */
	    XMultiComponentFactory xMultiComponentFactory = xComponentContext.getServiceManager();

	    /*
	     * Creates an instance of the component UnoUrlResolver which supports the services specified by the factory.
	     */
	    Object objectUrlResolver = xMultiComponentFactory.createInstanceWithContext(
		    "com.sun.star.bridge.UnoUrlResolver", xComponentContext);

	    // Create a new url resolver
	    XUnoUrlResolver xurlresolver = (XUnoUrlResolver) UnoRuntime.queryInterface(XUnoUrlResolver.class,
		    objectUrlResolver);

	    // Resolves an object that is specified as follow:
	    // uno:<connection description>;<protocol description>;<initial
	    // object name>
	    Object objectInitial = xurlresolver.resolve(sConnectionString);

	    // Create a service manager from the initial object
	    xMultiComponentFactory = (XMultiComponentFactory) UnoRuntime.queryInterface(XMultiComponentFactory.class,
		    objectInitial);

	    // Query for the XPropertySet interface.
	    XPropertySet xpropertysetMultiComponentFactory = (XPropertySet) UnoRuntime.queryInterface(
		    XPropertySet.class, xMultiComponentFactory);

	    // Get the default context from the office server.
	    Object objectDefaultContext = xpropertysetMultiComponentFactory.getPropertyValue("DefaultContext");

	    // Query for the interface XComponentContext.
	    xComponentContext = (XComponentContext) UnoRuntime.queryInterface(XComponentContext.class,
		    objectDefaultContext);

	    /*
	     * A desktop environment contains tasks with one or more frames in which components can be loaded. Desktop
	     * is the environment for components which can instanciate within frames.
	     */
	    xcomponentloader = (XComponentLoader) UnoRuntime.queryInterface(XComponentLoader.class,
		    xMultiComponentFactory.createInstanceWithContext("com.sun.star.frame.Desktop", xComponentContext));

	} catch (Exception exception) {
//	    log.error(exception.getMessage());
	    throw new RuntimeException(exception);
	}
	return xcomponentloader;
    }

    public XComponentContext getXComponentContext() {
	return this.xComponentContext;
    }
}
