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
 * Created on Jul 14, 2004
 *
 */
package it.people.process.workflow.xml;

import it.people.City;
import it.people.IProcess;
import junit.framework.TestCase;

/**
 * @author thweb4
 * 
 */
public class testWKF extends TestCase {

    public void testGetProcess() {
	try {
	    XMLWorkFlow w = new XMLWorkFlow();
	    City c = new City();
	    c.setName("pippo");
	    c.setKey("10048");
	    IProcess p = w.getProcess(null,
		    "it.people.fsl.servizi.fiscali.ici.richiestadirimborsoici",
		    c);

	    int kkkkk = 0;
	} catch (Exception e) {
	}
    }

}
