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
package it.people.util;

import it.people.process.AbstractPplProcess;
import it.people.process.print.ConcretePrint;

import java.io.StringWriter;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UnsignedPrintModule {
    public UnsignedPrintModule() {
    }

    public static String getUnsignedData(AbstractPplProcess p_process,
	    HttpServletRequest p_request, HttpServletResponse p_response) {

	StringWriter sw = new StringWriter();
	StringBuffer sb = new StringBuffer();
	ConcretePrint cp = p_process.getPrint(MimeType.HTML);
	Collection htmlPrintersNames = cp.getPrintersNames();

	Iterator printerIterator = htmlPrintersNames.iterator();
	while (printerIterator.hasNext()) {
	    cp.print((String) printerIterator.next(), sw, p_request, p_response);
	    sb.append(sw.toString());
	}
	return sb.toString();

    }

}
