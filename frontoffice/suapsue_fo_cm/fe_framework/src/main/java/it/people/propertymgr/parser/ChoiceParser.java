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
package it.people.propertymgr.parser;

import it.people.propertymgr.ApplicationPropertyParser;
import it.people.propertymgr.PropertyFormatException;
import it.people.propertymgr.PropertyParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Insert the type's description here. Creation date: (31/05/2002 17.44.36)
 * 
 * @author: alberto gasparini
 */
public class ChoiceParser extends ApplicationPropertyParser {
    private List m_obj_results = new ArrayList();
    private Object m_obj_default = null;
    private List m_obj_patterns = new ArrayList();
    public final boolean m_b_hasDefault;

    /**
     * TimeInterval constructor comment.
     */
    public ChoiceParser(byte[] p_obj_limits, String[] p_str_formats) {
	super();

	if (p_obj_limits == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null limits!");
	if (p_str_formats == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null patterns!");
	if (p_obj_limits.length != p_str_formats.length)
	    throw new IllegalArgumentException(
		    "Unable to create a choice parser with limits and patterns arrays having different length!");

	for (int i = 0; i < p_obj_limits.length; i++)
	    m_obj_results.add(new Byte(p_obj_limits[i]));
	m_obj_patterns.addAll(Arrays.asList(p_str_formats));

	m_b_hasDefault = false;
    }

    /**
     * TimeInterval constructor comment.
     */
    public ChoiceParser(byte[] p_obj_limits, String[] p_str_formats,
	    byte p_byte_default) {
	super();

	if (p_obj_limits == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null limits!");
	if (p_str_formats == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null patterns!");
	if (p_obj_limits.length != p_str_formats.length)
	    throw new IllegalArgumentException(
		    "Unable to create a choice parser with limits and patterns arrays having different length!");

	for (int i = 0; i < p_obj_limits.length; i++)
	    m_obj_results.add(new Byte(p_obj_limits[i]));
	m_obj_patterns.addAll(Arrays.asList(p_str_formats));

	m_obj_default = new Byte(p_byte_default);

	m_b_hasDefault = true;
    }

    /**
     * TimeInterval constructor comment.
     */
    public ChoiceParser(char[] p_obj_limits, String[] p_str_formats) {
	super();

	if (p_obj_limits == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null limits!");
	if (p_str_formats == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null patterns!");
	if (p_obj_limits.length != p_str_formats.length)
	    throw new IllegalArgumentException(
		    "Unable to create a choice parser with limits and patterns arrays having different length!");

	for (int i = 0; i < p_obj_limits.length; i++)
	    m_obj_results.add(new Character(p_obj_limits[i]));
	m_obj_patterns.addAll(Arrays.asList(p_str_formats));

	m_b_hasDefault = false;
    }

    /**
     * TimeInterval constructor comment.
     */
    public ChoiceParser(char[] p_obj_limits, String[] p_str_formats,
	    char p_c_default) {
	super();

	if (p_obj_limits == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null limits!");
	if (p_str_formats == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null patterns!");
	if (p_obj_limits.length != p_str_formats.length)
	    throw new IllegalArgumentException(
		    "Unable to create a choice parser with limits and patterns arrays having different length!");

	for (int i = 0; i < p_obj_limits.length; i++)
	    m_obj_results.add(new Character(p_obj_limits[i]));
	m_obj_patterns.addAll(Arrays.asList(p_str_formats));

	m_obj_default = new Character(p_c_default);

	m_b_hasDefault = true;
    }

    /**
     * TimeInterval constructor comment.
     */
    public ChoiceParser(double[] p_obj_limits, String[] p_str_formats) {
	super();

	if (p_obj_limits == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null limits!");
	if (p_str_formats == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null patterns!");
	if (p_obj_limits.length != p_str_formats.length)
	    throw new IllegalArgumentException(
		    "Unable to create a choice parser with limits and patterns arrays having different length!");

	for (int i = 0; i < p_obj_limits.length; i++)
	    m_obj_results.add(new Double(p_obj_limits[i]));
	m_obj_patterns.addAll(Arrays.asList(p_str_formats));

	m_b_hasDefault = false;
    }

    /**
     * TimeInterval constructor comment.
     */
    public ChoiceParser(double[] p_obj_limits, String[] p_str_formats,
	    double p_d_default) {
	super();

	if (p_obj_limits == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null limits!");
	if (p_str_formats == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null patterns!");
	if (p_obj_limits.length != p_str_formats.length)
	    throw new IllegalArgumentException(
		    "Unable to create a choice parser with limits and patterns arrays having different length!");

	for (int i = 0; i < p_obj_limits.length; i++)
	    m_obj_results.add(new Double(p_obj_limits[i]));
	m_obj_patterns.addAll(Arrays.asList(p_str_formats));

	m_obj_default = new Double(p_d_default);

	m_b_hasDefault = true;
    }

    /**
     * TimeInterval constructor comment.
     */
    public ChoiceParser(float[] p_obj_limits, String[] p_str_formats) {
	super();

	if (p_obj_limits == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null limits!");
	if (p_str_formats == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null patterns!");
	if (p_obj_limits.length != p_str_formats.length)
	    throw new IllegalArgumentException(
		    "Unable to create a choice parser with limits and patterns arrays having different length!");

	for (int i = 0; i < p_obj_limits.length; i++)
	    m_obj_results.add(new Float(p_obj_limits[i]));
	m_obj_patterns.addAll(Arrays.asList(p_str_formats));

	m_b_hasDefault = false;
    }

    /**
     * TimeInterval constructor comment.
     */
    public ChoiceParser(float[] p_obj_limits, String[] p_str_formats,
	    float p_f_default) {
	super();

	if (p_obj_limits == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null limits!");
	if (p_str_formats == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null patterns!");
	if (p_obj_limits.length != p_str_formats.length)
	    throw new IllegalArgumentException(
		    "Unable to create a choice parser with limits and patterns arrays having different length!");

	for (int i = 0; i < p_obj_limits.length; i++)
	    m_obj_results.add(new Float(p_obj_limits[i]));
	m_obj_patterns.addAll(Arrays.asList(p_str_formats));

	m_obj_default = new Float(p_f_default);

	m_b_hasDefault = true;
    }

    /**
     * TimeInterval constructor comment.
     */
    public ChoiceParser(int[] p_obj_limits, String[] p_str_formats) {
	super();

	if (p_obj_limits == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null limits!");
	if (p_str_formats == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null patterns!");
	if (p_obj_limits.length != p_str_formats.length)
	    throw new IllegalArgumentException(
		    "Unable to create a choice parser with limits and patterns arrays having different length!");

	for (int i = 0; i < p_obj_limits.length; i++)
	    m_obj_results.add(new Integer(p_obj_limits[i]));
	m_obj_patterns.addAll(Arrays.asList(p_str_formats));

	m_b_hasDefault = false;
    }

    /**
     * TimeInterval constructor comment.
     */
    public ChoiceParser(int[] p_obj_limits, String[] p_str_formats,
	    int p_i_default) {
	super();

	if (p_obj_limits == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null limits!");
	if (p_str_formats == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null patterns!");
	if (p_obj_limits.length != p_str_formats.length)
	    throw new IllegalArgumentException(
		    "Unable to create a choice parser with limits and patterns arrays having different length!");

	for (int i = 0; i < p_obj_limits.length; i++)
	    m_obj_results.add(new Integer(p_obj_limits[i]));
	m_obj_patterns.addAll(Arrays.asList(p_str_formats));

	m_obj_default = new Integer(p_i_default);

	m_b_hasDefault = true;
    }

    /**
     * TimeInterval constructor comment.
     */
    public ChoiceParser(long[] p_obj_limits, String[] p_str_formats) {
	super();

	if (p_obj_limits == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null limits!");
	if (p_str_formats == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null patterns!");
	if (p_obj_limits.length != p_str_formats.length)
	    throw new IllegalArgumentException(
		    "Unable to create a choice parser with limits and patterns arrays having different length!");

	for (int i = 0; i < p_obj_limits.length; i++)
	    m_obj_results.add(new Long(p_obj_limits[i]));
	m_obj_patterns.addAll(Arrays.asList(p_str_formats));

	m_b_hasDefault = false;
    }

    /**
     * TimeInterval constructor comment.
     */
    public ChoiceParser(long[] p_obj_limits, String[] p_str_formats,
	    long p_l_default) {
	super();

	if (p_obj_limits == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null limits!");
	if (p_str_formats == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null patterns!");
	if (p_obj_limits.length != p_str_formats.length)
	    throw new IllegalArgumentException(
		    "Unable to create a choice parser with limits and patterns arrays having different length!");

	for (int i = 0; i < p_obj_limits.length; i++)
	    m_obj_results.add(new Long(p_obj_limits[i]));
	m_obj_patterns.addAll(Arrays.asList(p_str_formats));

	m_obj_default = new Long(p_l_default);

	m_b_hasDefault = true;
    }

    /**
     * TimeInterval constructor comment.
     */
    public ChoiceParser(Object[] p_obj_limits, String[] p_str_formats) {
	super();

	if (p_obj_limits == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null limits!");
	if (p_str_formats == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null patterns!");
	if (p_obj_limits.length != p_str_formats.length)
	    throw new IllegalArgumentException(
		    "Unable to create a choice parser with limits and patterns arrays having different length!");

	m_obj_results.addAll(Arrays.asList(p_obj_limits));
	m_obj_patterns.addAll(Arrays.asList(p_str_formats));

	m_b_hasDefault = false;
    }

    /**
     * TimeInterval constructor comment.
     */
    public ChoiceParser(Object[] p_obj_limits, String[] p_str_formats,
	    Object p_obj_default) {
	super();

	if (p_obj_limits == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null limits!");
	if (p_str_formats == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null patterns!");
	if (p_obj_default == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null default!");
	if (p_obj_limits.length != p_str_formats.length)
	    throw new IllegalArgumentException(
		    "Unable to create a choice parser with limits and patterns arrays having different length!");

	m_obj_results.addAll(Arrays.asList(p_obj_limits));
	m_obj_patterns.addAll(Arrays.asList(p_str_formats));

	m_obj_default = p_obj_default;

	m_b_hasDefault = true;
    }

    /**
     * TimeInterval constructor comment.
     */
    public ChoiceParser(short[] p_obj_limits, String[] p_str_formats) {
	super();

	if (p_obj_limits == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null limits!");
	if (p_str_formats == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null patterns!");
	if (p_obj_limits.length != p_str_formats.length)
	    throw new IllegalArgumentException(
		    "Unable to create a choice parser with limits and patterns arrays having different length!");

	for (int i = 0; i < p_obj_limits.length; i++)
	    m_obj_results.add(new Short(p_obj_limits[i]));
	m_obj_patterns.addAll(Arrays.asList(p_str_formats));

	m_b_hasDefault = false;
    }

    /**
     * TimeInterval constructor comment.
     */
    public ChoiceParser(short[] p_obj_limits, String[] p_str_formats,
	    short p_s_default) {
	super();

	if (p_obj_limits == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null limits!");
	if (p_str_formats == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null patterns!");
	if (p_obj_limits.length != p_str_formats.length)
	    throw new IllegalArgumentException(
		    "Unable to create a choice parser with limits and patterns arrays having different length!");

	for (int i = 0; i < p_obj_limits.length; i++)
	    m_obj_results.add(new Short(p_obj_limits[i]));
	m_obj_patterns.addAll(Arrays.asList(p_str_formats));

	m_obj_default = new Short(p_s_default);

	m_b_hasDefault = true;
    }

    /**
     * TimeInterval constructor comment.
     */
    public ChoiceParser(boolean[] p_obj_limits, String[] p_str_formats) {
	super();

	if (p_obj_limits == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null limits!");
	if (p_str_formats == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null patterns!");
	if (p_obj_limits.length != p_str_formats.length)
	    throw new IllegalArgumentException(
		    "Unable to create a choice parser with limits and patterns arrays having different length!");

	for (int i = 0; i < p_obj_limits.length; i++)
	    m_obj_results.add(new Boolean(p_obj_limits[i]));
	m_obj_patterns.addAll(Arrays.asList(p_str_formats));

	m_b_hasDefault = false;
    }

    /**
     * TimeInterval constructor comment.
     */
    public ChoiceParser(boolean[] p_obj_limits, String[] p_str_formats,
	    boolean p_b_default) {
	super();

	if (p_obj_limits == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null limits!");
	if (p_str_formats == null)
	    throw new NullPointerException(
		    "Unable to create a choice parser with null patterns!");
	if (p_obj_limits.length != p_str_formats.length)
	    throw new IllegalArgumentException(
		    "Unable to create a choice parser with limits and patterns arrays having different length!");

	for (int i = 0; i < p_obj_limits.length; i++)
	    m_obj_results.add(new Boolean(p_obj_limits[i]));
	m_obj_patterns.addAll(Arrays.asList(p_str_formats));

	m_obj_default = new Boolean(p_b_default);

	m_b_hasDefault = true;
    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002
     * 17.44.36)
     * 
     * @return java.lang.String
     * @param p_obj_object
     *            java.lang.Object
     */
    public String doFormat(Object p_obj_object) throws PropertyFormatException {
	int idx = m_obj_results.indexOf(p_obj_object);
	if (idx != -1)
	    return (String) m_obj_patterns.get(idx);
	else
	    throw new PropertyFormatException("Unable to format "
		    + p_obj_object + ": no associated choice found!");
    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002
     * 17.44.36)
     * 
     * @return java.lang.Object
     * @param p_str_value
     *            java.lang.Number (Double)
     */
    public Object doParse(String p_str_value) throws PropertyParseException {
	int idx = m_obj_patterns.indexOf(p_str_value);
	if (idx != -1)
	    return m_obj_results.get(idx);
	else if (m_b_hasDefault)
	    return m_obj_default;
	else
	    throw new PropertyParseException("Unable to parse \"" + p_str_value
		    + "\": no associated choice found!");
    }
}
