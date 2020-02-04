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

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Sep 29, 2003 Time: 2:34:51 PM To
 * change this template use Options | File Templates.
 */
public class MimeType implements java.io.Serializable {

    private MimeType(String p_str_key, String p_str_name, int p_i_code) {

	if (p_str_name == null)
	    throw new NullPointerException(
		    "Unable to instantiate MimeType with null name!");
	if (p_str_key == null)
	    throw new NullPointerException(
		    "Unable to instantiate MimeType with null key!");

	m_i_type = p_i_code;
	m_str_key = p_str_key;
	m_str_name = p_str_name;
	MimeType.m_valuesByKey.put(p_str_key, this);
	MimeType.m_valuesByCode.put(new Integer(p_i_code), this);
    }

    public static java.util.Collection values() {
	return m_valuesByKey.values();

    }

    public final int getType() {
	return m_i_type;
    }

    public final String getName() {
	return m_str_name;
    }

    public final String getKey() {
	return m_str_key;
    }

    public static final MimeType valueOf(String p_str_name) {
	return (MimeType) m_valuesByKey.get(p_str_name);
    }

    public static final MimeType get(int p_i_code) {
	return (MimeType) m_valuesByCode.get(new Integer(p_i_code));
    }

    public String toString() {
	return m_str_key;
    }

    public static final int HTML_CODE = 1;
    public static final int TEXT_CODE = 2;
    public static final int PDF_CODE = 3;
    /*
     * Aggiunto per la stampa del frammento da firmare completo di doctype, html
     * tag, header tag, content type tag, body tag
     */
    public static final int COMPLETE_HTML_CODE = 4;

    static private HashMap m_valuesByKey = new HashMap();
    static private HashMap m_valuesByCode = new HashMap();
    public static final MimeType HTML = new MimeType("html", "", HTML_CODE);
    public static final MimeType TEXT = new MimeType("text", "", TEXT_CODE);
    public static final MimeType PDF = new MimeType("pdf", "", PDF_CODE);
    /*
     * Aggiunto per la stampa del frammento da firmare completo di doctype, html
     * tag, header tag, content type tag, body tag
     */
    public static final MimeType COMPLETE_HTML = new MimeType("completeHtml",
	    "", COMPLETE_HTML_CODE);

    private int m_i_type;
    private String m_str_name;
    private String m_str_key;

}
