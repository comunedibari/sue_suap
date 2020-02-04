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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * Insert the type's description here. Creation date: (31/05/2002 9.36.22)
 */
public final class ApplicationProperty implements Property, Cloneable {
    private final static int DEFAULT_SUFFIX = ApplicationProperties.DEFAULT_SUFFIX;
    private ApplicationProperties m_obj_container = null;
    private final String m_str_name;
    private Map m_obj_values = new TreeMap();
    private Object m_obj_defaultValue = null;
    private PropertyParser m_obj_parser = null;
    private boolean m_b_mandatory = false;
    private static Logger logger = Logger.getLogger(ApplicationProperty.class);

    /**
     * ApplicationProperty constructor comment.
     */
    protected ApplicationProperty(ApplicationProperties p_obj_container,
	    String p_str_name, String p_str_defaultValue, boolean p_b_mandatory) {

	super();

	if (p_str_name == null)
	    throw new NullPointerException(
		    "Unable to create an ApplicationProperty with null Name!");
	if (p_obj_container == null)
	    throw new NullPointerException(
		    "Null container in ApplicationProperty instance creation!");

	m_str_name = p_str_name;
	m_b_mandatory = p_b_mandatory;

	try {
	    setDefaultValue(p_str_defaultValue);
	} catch (PropertyParseException ppe) {
	    throw new IllegalArgumentException("Invalid default value '"
		    + m_str_name + "' -> " + ppe.getMessage());
	}

	m_obj_container = p_obj_container;
	m_obj_container.addProperty(this);

    }

    /**
     * ApplicationProperty constructor comment.
     */
    protected ApplicationProperty(ApplicationProperties p_obj_container,
	    String p_str_name, String p_str_defaultValue,
	    boolean p_b_mandatory, PropertyParser p_obj_parser) {

	super();

	if (p_str_name == null)
	    throw new NullPointerException(
		    "Unable to create an ApplicationProperty with null Name!");
	if (p_obj_container == null)
	    throw new NullPointerException(
		    "Null container in ApplicationProperty instance creation!");

	m_str_name = p_str_name;
	m_obj_parser = p_obj_parser;
	m_b_mandatory = p_b_mandatory;

	try {
	    setDefaultValue(p_str_defaultValue);
	} catch (PropertyParseException ppe) {
	    throw new IllegalArgumentException(
		    "Invalid default value for property '" + m_str_name
			    + "' -> " + ppe.getMessage());
	}

	m_obj_container = p_obj_container;
	m_obj_container.addProperty(this);

    }

    /**
     * ApplicationProperty constructor comment.
     */
    protected ApplicationProperty(ApplicationProperties p_obj_container,
	    String p_str_name, boolean p_b_mandatory) {

	super();

	if (p_str_name == null)
	    throw new NullPointerException(
		    "Unable to create an ApplicationProperty with null Name!");
	if (p_obj_container == null)
	    throw new NullPointerException(
		    "Null container in ApplicationProperty instance creation!");

	m_str_name = p_str_name;
	m_b_mandatory = p_b_mandatory;

	m_obj_container = p_obj_container;
	m_obj_container.addProperty(this);

    }

    /**
     * ApplicationProperty constructor comment.
     */
    protected ApplicationProperty(ApplicationProperties p_obj_container,
	    String p_str_name, boolean p_b_mandatory,
	    PropertyParser p_obj_parser) {

	super();

	if (p_str_name == null)
	    throw new NullPointerException(
		    "Unable to create an ApplicationProperty with null Name!");
	if (p_obj_container == null)
	    throw new NullPointerException(
		    "Null container in ApplicationProperty instance creation!");

	m_str_name = p_str_name;
	m_obj_parser = p_obj_parser;
	m_b_mandatory = p_b_mandatory;

	m_obj_container = p_obj_container;
	m_obj_container.addProperty(this);

    }

    public static ApplicationProperty createApplicationProperty(
	    ApplicationProperties p_obj_container, String p_str_name,
	    String p_str_defaultValue, boolean p_b_mandatory) {
	ApplicationProperty property = (ApplicationProperty) p_obj_container
		.getProperty(p_str_name);
	if (property != null)
	    return property;

	return new ApplicationProperty(p_obj_container, p_str_name,
		p_str_defaultValue, p_b_mandatory);
    }

    /**
     * Crea una nuova Application Property
     * 
     * @param p_obj_container
     *            contenitore della property
     * @param p_str_name
     *            nome della property
     * @param p_str_defaultValue
     *            valore di default della property
     * @param p_b_mandatory
     * @param p_obj_parser
     *            parser per la property
     * @return l'istanza della property
     */
    public static ApplicationProperty createApplicationProperty(
	    ApplicationProperties p_obj_container, String p_str_name,
	    String p_str_defaultValue, boolean p_b_mandatory,
	    PropertyParser p_obj_parser) {
	ApplicationProperty property = (ApplicationProperty) p_obj_container
		.getProperty(p_str_name);
	if (property != null)
	    return property;

	return new ApplicationProperty(p_obj_container, p_str_name,
		p_str_defaultValue, p_b_mandatory, p_obj_parser);
    }

    public static ApplicationProperty createApplicationProperty(
	    ApplicationProperties p_obj_container, String p_str_name,
	    boolean p_b_mandatory, PropertyParser p_obj_parser) {
	ApplicationProperty property = (ApplicationProperty) p_obj_container
		.getProperty(p_str_name);
	if (property != null)
	    return property;

	return new ApplicationProperty(p_obj_container, p_str_name,
		p_b_mandatory, p_obj_parser);
    }

    public static ApplicationProperty createApplicationProperty(
	    ApplicationProperties p_obj_container, String p_str_name,
	    boolean p_b_mandatory) {
	ApplicationProperty property = (ApplicationProperty) p_obj_container
		.getProperty(p_str_name);
	if (property != null)
	    return property;

	return new ApplicationProperty(p_obj_container, p_str_name,
		p_b_mandatory);
    }

    public void copyTo(PropertyRegistry p_obj_registry) {
	try {
	    ApplicationProperty property = (ApplicationProperty) clone();
	    p_obj_registry.addProperty(property);
	} catch (Exception ex) {
	    logger.error(ex);
	}
    }

    /**
     * Insert the method's description here. Creation date: (03/06/2002
     * 10.27.15)
     * 
     * @return java.lang.Object
     */

    protected final Object clone() throws CloneNotSupportedException {
	ApplicationProperty property = (ApplicationProperty) super.clone();
	property.m_obj_values = new TreeMap();
	Set keys = m_obj_values.keySet();
	for (Iterator iter = keys.iterator(); iter.hasNext();) {
	    try {
		Integer key = (Integer) iter.next();
		property.setValue(key.intValue(),
			getValueString(key.intValue()));
	    } catch (Exception ex) {
		throw new CloneNotSupportedException();
	    }
	}
	// property.m_obj_container = null;
	return property;
    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002
     * 11.36.02)
     * 
     * @return boolean
     * @param p_obj_object
     *            java.lang.Object
     */
    public boolean equals(Object p_obj_object) {
	ApplicationProperty property = (ApplicationProperty) p_obj_object;
	return m_str_name.equals(property.getName());
    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002 9.36.22)
     * 
     * @return java.lang.Object
     */
    public Object getDefaultValue() {
	return m_obj_defaultValue;
    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002 9.36.22)
     * 
     * @return java.lang.Object
     */
    public String getDefaultValueString() throws PropertyFormatException {
	if (m_obj_parser != null)
	    return m_obj_parser.format(m_obj_defaultValue);
	else
	    return m_obj_defaultValue.toString();
    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002 9.36.22)
     * 
     * @return java.lang.String
     */
    public String getName() {
	return m_str_name;
    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002 9.36.22)
     * 
     * @return it.people.propertymgr.PropertyValidator
     */
    public PropertyParser getParser() {
	return m_obj_parser;
    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002 9.36.22)
     */
    private int getSuffix() {
	return m_obj_container.getSuffix();
    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002 9.36.22)
     */
    public int getSuffix(String p_str_propertyKey) {

	if (p_str_propertyKey == null)
	    return -1;

	int index = p_str_propertyKey.lastIndexOf('.');

	if (index == -1 || index > (p_str_propertyKey.length() - 1))
	    return -1;

	String suffix = p_str_propertyKey.substring(index + 1);

	try {
	    return Integer.parseInt(suffix.trim());
	} catch (NumberFormatException nfe) {
	    return -1;
	}
    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002 9.36.22)
     * 
     * @return java.lang.String
     */
    public String getSuffixedName() {
	return getSuffixedName(getSuffix());
    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002 9.36.22)
     * 
     * @return java.lang.String
     */
    protected String getSuffixedName(int p_i_suffix) {
	return (p_i_suffix == ApplicationProperty.DEFAULT_SUFFIX) ? getName()
		: getName() + "." + p_i_suffix;
    }

    /**
     * Insert the method's description here. Creation date: (05/06/2002
     * 10.18.05)
     * 
     * @return java.util.List
     */
    public List getSuffixes() {
	return new ArrayList(m_obj_values.keySet());
    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002 9.36.22)
     * 
     * @return java.lang.Object
     */
    public Object getValue() {
	int suffix = getSuffix();
	Object value = getValue(suffix);

	if (value == null) {
	    Object returnValue = null;
	    if (suffix != ApplicationProperty.DEFAULT_SUFFIX) {
		if (m_obj_container != null)
		    returnValue = getValue(ApplicationProperty.DEFAULT_SUFFIX);
		else
		    returnValue = getDefaultValue();
	    } else
		returnValue = getDefaultValue();

	    if (returnValue == null)
		logger.info("Nessun valore trovato per la property '"
			+ this.m_str_name + "'");

	    return returnValue;
	} else
	    return value;
    }

    /**
     * Ritorna il valore della variabile relativamente all'ente del quale �
     * passato il codice. Se per l'ente non � definito un valore specifico �
     * ritornato il valore di default.
     * 
     * @param communeId
     *            codice istat dell'ente il quale leggere la variabile
     */
    public Object getValue(String communeId) {
	int suffix = 0;
	try {
	    suffix = Integer.parseInt(communeId);
	} catch (NumberFormatException nfex) {
	    // suffisso non valido
	    return null;
	}

	Object value = getValue(suffix);

	if (value == null)
	    value = getValue();

	return value;
    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002 9.36.22)
     * 
     * @return java.lang.Object
     */
    protected Object getValue(int p_i_suffix) {
	return m_obj_values.get(new Integer(p_i_suffix));
    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002 9.36.22)
     * 
     * @return java.lang.Object
     */
    public List getValues() {
	return new ArrayList(m_obj_values.entrySet());
    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002 9.36.22)
     * 
     * @return java.lang.Object
     */
    public String getValueString() throws PropertyFormatException {
	if (m_obj_parser != null)
	    return m_obj_parser.format(getValue());
	else {
	    Object obj = getValue();
	    return (obj == null) ? null : obj.toString();
	}

    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002 9.36.22)
     * 
     * @return java.lang.Object
     */
    protected String getValueString(int p_i_suffix)
	    throws PropertyFormatException {
	if (m_obj_parser != null)
	    return m_obj_parser.format(getValue(p_i_suffix));
	else {
	    Object obj = getValue(p_i_suffix);
	    return (obj == null) ? null : obj.toString();
	}
    }

    /**
     * Ritorna il valore della variabile relativamente all'ente del quale �
     * passato il codice. Se per l'ente non � definito un valore specifico �
     * ritornato il valore di default.
     * 
     * @param communeId
     *            codice istat dell'ente il quale leggere la variabile
     */
    public String getValueString(String communeId)
	    throws PropertyFormatException {
	int suffix = 0;
	try {
	    suffix = Integer.parseInt(communeId);
	} catch (NumberFormatException nfex) {
	    // suffisso non valido
	    return null;
	}

	String value = getValueString(suffix);
	if (value == null) {
	    // non esiste un valore specifico per il comune
	    logger.info("Per la property con name = '"
		    + this.m_str_name
		    + "' non esiste un valore specifico per il comune con id = '"
		    + communeId + "'");
	    value = getValueString();
	}
	return value;
    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002 9.36.22)
     * 
     * @return boolean
     * @param p_str_propertyName
     *            java.lang.String
     */
    public boolean isAssignableFrom(String p_str_propertyName) {

	if (!p_str_propertyName.startsWith(m_str_name))
	    return false;

	if (p_str_propertyName.equals(m_str_name))
	    return true;
	else {
	    String tail = p_str_propertyName.substring(m_str_name.length());
	    if (!tail.startsWith("."))
		return false;

	    String suffix = tail.substring(1);
	    try {
		if (Integer.parseInt(suffix.trim()) >= 0)
		    return true;
		else
		    return false;
	    } catch (NumberFormatException nfe) {
		return false;
	    }
	}
    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002 9.36.22)
     * 
     * @param p_obj_appProperties
     *            it.people.propertymgr.ApplicationProperties
     */
    public void setRegistry(PropertyRegistry p_obj_appProperties) {
	m_obj_container = (ApplicationProperties) p_obj_appProperties;
    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002 9.36.22)
     * 
     * @param p_str_propertyValue
     *            java.lang.String
     */
    public void setValue(String p_str_propertyValue)
	    throws PropertyParseException {
	if (m_obj_parser != null)
	    m_obj_values.put(new Integer(getSuffix()),
		    m_obj_parser.parse(p_str_propertyValue));
	else
	    m_obj_values.put(new Integer(getSuffix()), p_str_propertyValue);
    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002 9.36.22)
     * 
     * @param p_str_propertyValue
     *            java.lang.String
     */
    public void setValue(String p_str_propertyKey, String p_str_propertyValue)
	    throws PropertyParseException {

	int suffix = getSuffix(p_str_propertyKey);

	setValue(suffix, p_str_propertyValue);

    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002 9.36.22)
     * 
     * @param p_str_propertyValue
     *            java.lang.String
     */
    private void setValue(int p_i_suffix, String p_str_propertyValue)
	    throws PropertyParseException {

	if (m_obj_parser != null)
	    m_obj_values.put(new Integer(p_i_suffix),
		    m_obj_parser.parse(p_str_propertyValue));
	else
	    m_obj_values.put(new Integer(p_i_suffix), p_str_propertyValue);

    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002 9.36.22)
     */
    protected void clearValues() {
	m_obj_values.clear();
    }

    /**
     * Insert the method's description here. Creation date: (05/06/2002
     * 16.48.18)
     * 
     * @return java.lang.String
     */
    public String toString() {
	try {
	    return super.toString() + " {\n" + "name: " + getName() + "\n"
		    + "suffix: " + getSuffix() + "\n" + "value: "
		    + getValueString() + "\n" + "default value: "
		    + getDefaultValueString() + "\n" + "all values: "
		    + m_obj_values + "\n" + "parser: " + getParser() + "\n}";

	} catch (Exception e) {
	    return super.toString();
	}
    }

    /**
     * Insert the method's description here. Creation date: (26/06/2002
     * 14.00.22)
     * 
     * @return boolean
     */
    public boolean isMandatory() {
	return m_b_mandatory;
    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002 9.36.22)
     * 
     * @param p_str_propertyValue
     *            java.lang.String
     */
    private void setDefaultValue(String p_str_propertyValue)
	    throws PropertyParseException {
	if (m_obj_parser != null)
	    m_obj_defaultValue = m_obj_parser.parse(p_str_propertyValue);
	else
	    m_obj_defaultValue = p_str_propertyValue;
    }

}
