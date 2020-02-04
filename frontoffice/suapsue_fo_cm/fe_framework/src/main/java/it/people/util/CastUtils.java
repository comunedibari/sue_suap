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
/**
 * 
 */
package it.people.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         09/ott/2012 09:56:29
 */
public class CastUtils {

    private CastUtils() {

    }

    /**
     * @param e
     * @return
     */
    public static String exceptionToString(Exception e) {

	StringWriter writer = new StringWriter();
	PrintWriter printWriter = new PrintWriter(writer);
	e.printStackTrace(printWriter);
	printWriter.flush();
	printWriter.close();
	writer.flush();

	try {
	    writer.close();
	} catch (IOException ioe) {
	    ioe.printStackTrace();
	}

	return writer.toString();

    }

    /**
     * @param e
     * @return
     */
    public static String exceptionToString(Throwable t) {

	StringWriter writer = new StringWriter();
	PrintWriter printWriter = new PrintWriter(writer);
	t.printStackTrace(printWriter);
	printWriter.flush();
	printWriter.close();
	writer.flush();

	try {
	    writer.close();
	} catch (IOException ioe) {
	    ioe.printStackTrace();
	}

	return writer.toString();

    }

}
