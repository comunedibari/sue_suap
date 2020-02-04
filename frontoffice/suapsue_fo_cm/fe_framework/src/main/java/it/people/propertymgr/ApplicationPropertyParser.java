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

/**
 * Insert the type's description here. Creation date: (04/06/2002 11.04.25)
 * 
 * @author: alberto gasparini
 */
public class ApplicationPropertyParser implements PropertyParser {
    /**
     * ApplicationPropertyParser constructor comment.
     */
    public ApplicationPropertyParser() {
	super();
    }

    /**
     * Insert the method's description here. Creation date: (04/06/2002
     * 11.04.25)
     * 
     * @return java.lang.String
     * @param p_obj_object
     *            java.lang.Object
     */
    protected String doFormat(Object p_obj_object)
	    throws PropertyFormatException {
	return null;
    }

    /**
     * Insert the method's description here. Creation date: (04/06/2002
     * 11.06.57)
     * 
     * @return java.lang.Object
     * @param p_str_value
     *            java.lang.String
     */
    protected Object doParse(String p_str_value) throws PropertyParseException {
	return p_str_value;
    }

    /**
     * Insert the method's description here. Creation date: (04/06/2002
     * 11.04.25)
     * 
     * @return java.lang.String
     * @param p_obj_object
     *            java.lang.Object
     */
    public final String format(Object p_obj_object)
	    throws PropertyFormatException {
	return doFormat(p_obj_object);
    }

    /**
     * Insert the method's description here. Creation date: (05/06/2002
     * 16.56.38)
     * 
     * @return java.lang.String
     */
    public String getDescription() {
	return "";
    }

    /**
     * Insert the method's description here. Creation date: (04/06/2002
     * 11.04.25)
     * 
     * @return java.lang.String
     * @param p_obj_object
     *            java.lang.Object
     */
    protected boolean isValid(Object p_obj_object) {
	return true;
    }

    /**
     * Insert the method's description here. Creation date: (04/06/2002
     * 11.04.25)
     * 
     * @return java.lang.Object
     * @param p_str_value
     *            java.lang.String
     */
    public final Object parse(String p_str_value) throws PropertyParseException {
	Object obj = doParse(p_str_value);

	if (isValid(obj))
	    return obj;
	else
	    throw new PropertyParseException("Invalid value!");
    }

    /**
     * Returns a String that represents the value of this object.
     * 
     * @return a string representation of the receiver
     */
    public String toString() {
	// Insert code to print the receiver here.
	// This implementation forwards the message to super. You may replace or
	// supplement this.
	return super.toString();
    }
}
