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

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import org.apache.log4j.Category;

/**
 * Insert the type's description here. Creation date: (03/05/2002 14.35.21)
 */
public abstract class ApplicationProperties implements PropertyRegistry// ,
								       // Cloneable
{
    public final static int DEFAULT_SUFFIX = -1;
    private List m_obj_children = new ArrayList();
    private List m_obj_properties = new ArrayList();
    /**
     * Contiene le coppie (suffisso, Mappa(nome propriet�, valore))
     */
    private Set m_obj_suffixes = new TreeSet();
    private int m_i_suffix = 0;
    private int m_i_unmodifiableBoundary;

    private Category cat = Category.getInstance(this.getClass().getName());

    /**
     * AbstractApplicationProperty constructor comment.
     */
    public ApplicationProperties(int p_i_suffix) {
	m_obj_suffixes.add(new Integer(p_i_suffix));
	m_i_suffix = p_i_suffix;
	m_i_unmodifiableBoundary = m_obj_properties.size();
    }

    /**
     * Insert the method's description here. Creation date: (26/06/2002
     * 16.16.18)
     */
    public void addChild(ApplicationProperties p_obj_appProperties) {
	if (p_obj_appProperties != null)
	    m_obj_children.add(p_obj_appProperties);
    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002 9.16.05)
     * 
     * @param p_obj_property
     *            it.people.propertymgr.Property
     */
    public final void addProperty(Property p_obj_property) {
	if (p_obj_property != null) {
	    m_obj_properties.add(p_obj_property);
	    p_obj_property.setRegistry(this);
	}
    }

    /**
     * Restituisce la ApplicationProperty dell'istanza corrente di
     * ApplicationProperty che ha nome p_str_name. Creation date: (29/05/2002
     * 16.17.43)
     * 
     * @return it.people.propertymgr.ApplicationProperty
     * @param p_str_name
     *            java.lang.String
     */
    public final Property getProperty(String p_str_name) {
	for (Iterator i = m_obj_properties.iterator(); i.hasNext();) {
	    ApplicationProperty property = (ApplicationProperty) i.next();
	    if (property.getName().equals(p_str_name))
		return property;
	}
	return null;
    }

    /**
     * Insert the method's description here. Creation date: (29/05/2002
     * 16.18.30)
     * 
     * @return int
     */
    public final int getSuffix() {
	return m_i_suffix;
    }

    /**
     * Insert the method's description here. Creation date: (29/05/2002
     * 16.25.00)
     * 
     * @param p_obj_properties
     *            java.util.Properties
     */
    public final void load(Properties p_obj_properties)
	    throws PropertyParseException {
	load(p_obj_properties, true);
    }

    /**
     * Insert the method's description here. Creation date: (29/05/2002
     * 16.25.00)
     * 
     * @param p_obj_properties
     *            java.util.Properties
     */
    public final void load(Properties p_obj_properties, boolean p_b_whole)
	    throws PropertyParseException {
	/*
	 * Ciclo di caricamento delle propriet�:
	 * 
	 * 1. Istanziazione dell'istanza di Default;
	 * 
	 * 2. ciclo di scorrimento delle properties in ingresso; 2.1 Per ogni
	 * item di p_obj_properties confronto il suo nome con il nome delle
	 * propriet� dichiarate come membri pubblici (statici e non) finali
	 * dell'istanza di Default della corrente sottoclasse di
	 * ApplicationProperties:
	 * 
	 * 2.1.1 Se il nome dell'item di p_obj_properties corrente � "uguale"
	 * (a meno del suffisso) al nome di uno dei membri dell'istanza di
	 * Default:
	 * 
	 * 2.1.1.1 Se il suffisso del nome � diverso da -1 (valore di Default)
	 * allora istanzio un nuovo oggetto corrispondente al nuovo prefisso e
	 * valorizzo il membro di quell'oggetto con il valore dell'item di
	 * p_obj_properties corrente 2.1.1.2 Altrimenti valorizzo il membro
	 * dell'oggetto Default con il valore dell'item di p_obj_properties
	 * corrente
	 * 
	 * 2.1.2 Altrimenti proseguo con la prossima iterazione.
	 */

	clearProperties(true);

	Set keys = p_obj_properties.keySet();
	int propsSize = m_obj_properties.size();

	for (Iterator iter = keys.iterator(); iter.hasNext();) {
	    String key = (String) iter.next();

	    for (int i = 0; i < propsSize; i++) {
		ApplicationProperty property = (ApplicationProperty) m_obj_properties
			.get(i);

		if (property.isAssignableFrom(key)) {
		    property.setValue(key, p_obj_properties.getProperty(key));
		    int suffix = property.getSuffix(key);
		    if (p_b_whole || (!p_b_whole && suffix == m_i_suffix))
			m_obj_suffixes.add(new Integer(suffix));
		}
	    }
	}
	for (Iterator iter = m_obj_children.iterator(); iter.hasNext();) {
	    ApplicationProperties properties = (ApplicationProperties) iter
		    .next();
	    properties.load(p_obj_properties, p_b_whole);
	}
    }

    /**
     * Insert the method's description here. Creation date: (30/05/2002
     * 16.23.04)
     */
    private void recalculateSuffixes() {
    }

    /**
     * Insert the method's description here. Creation date: (26/06/2002
     * 16.16.18)
     * 
     * @param p_obj_appProperties
     *            it.people.propertymgr.ApplicationProperties
     */
    public void removeChild(ApplicationProperties p_obj_appProperties) {
	if (p_obj_appProperties != null)
	    m_obj_children.remove(p_obj_appProperties);
    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002 9.16.44)
     * 
     * @param p_obj_property
     *            it.people.propertymgr.ApplicationProperty
     */
    public final void removeProperty(Property p_obj_property) {
	if (p_obj_property != null) {
	    int index = 0;
	    if ((index = m_obj_properties.indexOf(p_obj_property)) != -1
		    && index < m_i_unmodifiableBoundary) {
		throw new IllegalArgumentException(
			"Unable to remove structural properties!");
	    } else
		m_obj_properties.remove(p_obj_property);
	}
    }

    /**
     * Insert the method's description here. Creation date: (05/06/2002
     * 14.50.18)
     * 
     * @return java.util.List
     */
    public List splitByProperty() {
	return new ArrayList(m_obj_properties);
    }

    /**
     * Insert the method's description here. Creation date: (05/06/2002
     * 14.50.18)
     * 
     * @return java.util.List
     */
    public List splitBySuffix() {
	List properties = new ArrayList();
	try {
	    for (Iterator i = m_obj_suffixes.iterator(); i.hasNext();) {
		Integer suffix = ((Integer) i.next());
		final Constructor ctor = this.getClass()
			.getDeclaredConstructor(new Class[] { int.class });
		boolean accessible = ctor.isAccessible();
		AccessController.doPrivileged(new PrivilegedAction() {
		    public Object run() {
			ctor.setAccessible(true);
			return null;
		    }
		});
		ApplicationProperties aps = (ApplicationProperties) ctor
			.newInstance(new Object[] { suffix });
		ctor.setAccessible(accessible);

		for (Iterator iter = m_obj_children.iterator(); iter.hasNext();) {
		    ApplicationProperties registry = (ApplicationProperties) iter
			    .next();
		    if (registry == null)
			continue;
		    List registryList = registry.splitBySuffix();
		    for (Iterator iterReg = registryList.iterator(); iterReg
			    .hasNext();) {
			ApplicationProperties registrySplitted = (ApplicationProperties) iterReg
				.next();
			if (registrySplitted == null)
			    continue;
			if (registrySplitted.getSuffix() == suffix.intValue())
			    aps.addChild(registrySplitted);
		    }
		}

		for (Iterator iter = m_obj_properties.iterator(); iter
			.hasNext();) {
		    Property property = (Property) iter.next();
		    if (property == null)
			continue;
		    property.copyTo(aps);
		}
		properties.add(aps);
	    }
	} catch (Exception cnse) {
	    cat.error(cnse);
	}
	return properties;
    }

    /**
     * Insert the method's description here. Creation date: (29/05/2002
     * 16.25.00)
     * 
     * @param p_obj_properties
     *            java.util.Properties
     */
    public final void store(Properties p_obj_properties)
	    throws PropertyFormatException {
	store(p_obj_properties, true);
    }

    /**
     * Insert the method's description here. Creation date: (29/05/2002
     * 16.25.00)
     * 
     * @param p_obj_properties
     *            java.util.Properties
     */
    public final void store(Properties p_obj_properties, boolean p_b_whole)
	    throws PropertyFormatException {

	recalculateSuffixes();

	if (p_b_whole) {
	    for (Iterator iter = m_obj_properties.iterator(); iter.hasNext();) {
		ApplicationProperty property = (ApplicationProperty) iter
			.next();
		for (Iterator i = m_obj_suffixes.iterator(); i.hasNext();) {
		    int suffix = ((Integer) i.next()).intValue();
		    if (property.getValue(suffix) != null)
			p_obj_properties.put(property.getSuffixedName(suffix),
				property.getValueString(suffix));
		}

	    }
	    for (Iterator iter = m_obj_children.iterator(); iter.hasNext();) {
		ApplicationProperties properties = (ApplicationProperties) iter
			.next();
		properties.store(p_obj_properties);
	    }
	} else {
	    for (Iterator iter = m_obj_properties.iterator(); iter.hasNext();) {
		ApplicationProperty property = (ApplicationProperty) iter
			.next();
		if (property.getValue(m_i_suffix) != null)
		    p_obj_properties.put(property.getSuffixedName(m_i_suffix),
			    property.getValueString(m_i_suffix));
	    }
	}
	for (Iterator iter = m_obj_children.iterator(); iter.hasNext();) {
	    ApplicationProperties ap = (ApplicationProperties) iter.next();
	    if (ap == null)
		continue;
	    ap.store(p_obj_properties, p_b_whole);
	}

    }

    /**
     * Insert the method's description here. Creation date: (29/05/2002
     * 16.25.00)
     */
    public String toString() {

	try {
	    StringBuffer sb = new StringBuffer();

	    recalculateSuffixes();

	    for (Iterator iter = m_obj_properties.iterator(); iter.hasNext();) {

		ApplicationProperty property = (ApplicationProperty) iter
			.next();

		for (Iterator i = m_obj_suffixes.iterator(); i.hasNext();) {
		    int suffix = ((Integer) i.next()).intValue();
		    if (property.getValue(suffix) != null)
			sb.append(property.getSuffixedName(suffix))
				.append(": ")
				.append(property.getValueString(suffix))
				.append("\n");
		}
	    }
	    for (Iterator iter = m_obj_children.iterator(); iter.hasNext();) {
		ApplicationProperties properties = (ApplicationProperties) iter
			.next();
		sb.append("\n").append(properties.getClass().getName())
			.append("\n").append(properties.toString());
	    }
	    return sb.toString();

	} catch (Exception e) {
	    return super.toString();
	}
    }

    private void clearProperties(boolean p_b_deep) {
	for (Iterator iter = m_obj_properties.iterator(); iter.hasNext();) {
	    ApplicationProperty value = (ApplicationProperty) iter.next();
	    if (value == null)
		continue;
	    value.clearValues();
	}
	if (p_b_deep) {
	    for (Iterator iter = m_obj_children.iterator(); iter.hasNext();) {
		ApplicationProperties value = (ApplicationProperties) iter
			.next();
		if (value == null)
		    continue;
		value.clearProperties(p_b_deep);
	    }
	}
    }

    public void load(InputStream p_obj_properties)
	    throws PropertyParseException {
	try {
	    Properties props = new Properties();
	    props.load(p_obj_properties);
	    load(props);
	} catch (Exception ex) {
	    throw new PropertyParseException(ex);
	}
    }

    public void load(InputStream p_obj_properties, boolean p_b_whole)
	    throws PropertyParseException {
	try {
	    Properties props = new Properties();
	    props.load(p_obj_properties);
	    load(props, p_b_whole);
	} catch (Exception ex) {
	    throw new PropertyParseException(ex);
	}
    }

    public void store(OutputStream p_obj_properties, boolean p_b_whole)
	    throws PropertyFormatException {
	try {
	    Properties props = new Properties();
	    store(props, p_b_whole);
	    props.store(p_obj_properties, "");
	} catch (Exception ex) {
	    throw new PropertyFormatException(ex);
	}
    }

    public void store(OutputStream p_obj_properties)
	    throws PropertyFormatException {
	try {
	    Properties props = new Properties();
	    store(props);
	    props.store(p_obj_properties, "");
	} catch (Exception ex) {
	    throw new PropertyFormatException(ex);
	}
    }
}
