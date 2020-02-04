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
package it.people.propertymgr;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Insert the type's description here. Creation date: (31/05/2002 17.32.20)
 * 
 * @author: alberto gasparini
 */
public class PropertyFormatException extends Exception {
    private java.lang.Exception m_obj_exception;

    /**
     * PropertyParseException constructor comment.
     */
    public PropertyFormatException() {
	super();
    }

    /**
     * PropertyParseException constructor comment.
     * 
     * @param s
     *            java.lang.String
     */
    public PropertyFormatException(Exception e) {
	super();
	m_obj_exception = e;
    }

    /**
     * PropertyParseException constructor comment.
     * 
     * @param s
     *            java.lang.String
     */
    public PropertyFormatException(String s) {
	super(s);
    }

    /**
     * Insert the method's description here. Creation date: (04/06/2002
     * 10.38.34)
     */
    public void printStackTrace() {
	if (m_obj_exception != null) {
	    System.err.print(this.toString() + " -> ");
	    m_obj_exception.printStackTrace();
	} else
	    super.printStackTrace();
    }

    /**
     * Insert the method's description here. Creation date: (04/06/2002
     * 10.38.34)
     */
    public void printStackTrace(PrintStream p_obj_stream) {
	if (m_obj_exception != null) {
	    p_obj_stream.print(this.toString() + " -> ");
	    m_obj_exception.printStackTrace(p_obj_stream);
	} else
	    super.printStackTrace(p_obj_stream);
    }

    /**
     * Insert the method's description here. Creation date: (04/06/2002
     * 10.38.34)
     */
    public void printStackTrace(PrintWriter p_obj_writer) {
	if (m_obj_exception != null) {
	    p_obj_writer.print(this.toString() + " -> ");
	    m_obj_exception.printStackTrace(p_obj_writer);
	} else
	    super.printStackTrace(p_obj_writer);
    }
}
