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

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Sep 10, 2003 Time: 10:07:47 PM
 * To change this template use Options | File Templates.
 */
public class PplBean extends Observable implements DynaBean {

    // ---------------------------------------------------------- Constructors

    /**
     * Construct a new <code>DynaBean</code> associated with the specified
     * <code>DynaClass</code> instance.
     * 
     * @param dynaClass
     *            The DynaClass we are associated with
     */
    public PplBean(DynaClass dynaClass) {

	super();
	this.dynaClass = dynaClass;

    }

    // ---------------------------------------------------- Instance Variables

    /**
     * The <code>DynaClass</code> "base class" that this DynaBean is associated
     * with.
     */
    protected DynaClass dynaClass = null;

    /**
     * The set of property values for this DynaBean, keyed by property name.
     */
    protected HashMap values = new HashMap();

    // ------------------------------------------------------ DynaBean Methods

    /**
     * Does the specified mapped property contain a value for the specified key
     * value?
     * 
     * @param name
     *            Name of the property to check
     * @param key
     *            Name of the key to check
     * 
     * @exception IllegalArgumentException
     *                if there is no property of the specified name
     */
    public boolean contains(String name, String key) {

	PplProperty descriptor = getDynaProperty(name);
	Object value = values.get(name);
	if (value == null) {
	    throw new NullPointerException("No mapped value for '" + name + "("
		    + key + ")'");
	} else if (value instanceof Map) {
	    return (((Map) value).containsKey(key));
	} else {
	    throw new IllegalArgumentException("Non-mapped property for '"
		    + name + "(" + key + ")'");
	}

    }

    /**
     * Return the value of a simple property with the specified name.
     * 
     * @param name
     *            Name of the property whose value is to be retrieved
     * 
     * @exception IllegalArgumentException
     *                if there is no property of the specified name
     */
    public Object get(String name) {

	// Return any non-null value for the specified property
	Object value = values.get(name);
	if (value != null) {
	    return (value);
	}

	// Return a null value for a non-primitive property
	Class type = getDynaProperty(name).getType();
	if (!type.isPrimitive()) {
	    return (value);
	}

	// Manufacture default values for primitive properties
	if (type == Boolean.TYPE) {
	    return (Boolean.FALSE);
	} else if (type == Byte.TYPE) {
	    return (new Byte((byte) 0));
	} else if (type == Character.TYPE) {
	    return (new Character((char) 0));
	} else if (type == Double.TYPE) {
	    return (new Double((double) 0.0));
	} else if (type == Float.TYPE) {
	    return (new Float((float) 0.0));
	} else if (type == Integer.TYPE) {
	    return (new Integer((int) 0));
	} else if (type == Long.TYPE) {
	    return (new Long((int) 0));
	} else if (type == Short.TYPE) {
	    return (new Short((short) 0));
	} else {
	    return (null);
	}

    }

    /**
     * Return the value of an indexed property with the specified name.
     * 
     * @param name
     *            Name of the property whose value is to be retrieved
     * @param index
     *            Index of the value to be retrieved
     * 
     * @exception IllegalArgumentException
     *                if there is no property of the specified name
     * @exception IllegalArgumentException
     *                if the specified property exists, but is not indexed
     * @exception IndexOutOfBoundsException
     *                if the specified index is outside the range of the
     *                underlying property
     * @exception NullPointerException
     *                if no array or List has been initialized for this property
     */
    public Object get(String name, int index) {

	PplProperty descriptor = getDynaProperty(name);
	Object value = values.get(name);
	if (value == null) {
	    throw new NullPointerException("No indexed value for '" + name
		    + "[" + index + "]'");
	} else if (value.getClass().isArray()) {
	    return (Array.get(value, index));
	} else if (value instanceof List) {
	    return ((List) value).get(index);
	} else {
	    throw new IllegalArgumentException("Non-indexed property for '"
		    + name + "[" + index + "]'");
	}

    }

    /**
     * Return the value of a mapped property with the specified name, or
     * <code>null</code> if there is no value for the specified key.
     * 
     * @param name
     *            Name of the property whose value is to be retrieved
     * @param key
     *            Key of the value to be retrieved
     * 
     * @exception IllegalArgumentException
     *                if there is no property of the specified name
     * @exception IllegalArgumentException
     *                if the specified property exists, but is not mapped
     */
    public Object get(String name, String key) {

	PplProperty descriptor = getDynaProperty(name);
	Object value = values.get(name);
	if (value == null) {
	    throw new NullPointerException("No mapped value for '" + name + "("
		    + key + ")'");
	} else if (value instanceof Map) {
	    return (((Map) value).get(key));
	} else {
	    throw new IllegalArgumentException("Non-mapped property for '"
		    + name + "(" + key + ")'");
	}

    }

    /**
     * Return the <code>DynaClass</code> instance that describes the set of
     * properties available for this DynaBean.
     */
    public DynaClass getDynaClass() {

	return (this.dynaClass);

    }

    /**
     * Remove any existing value for the specified key on the specified mapped
     * property.
     * 
     * @param name
     *            Name of the property for which a value is to be removed
     * @param key
     *            Key of the value to be removed
     * 
     * @exception IllegalArgumentException
     *                if there is no property of the specified name
     */
    public void remove(String name, String key) {

	PplProperty descriptor = getDynaProperty(name);
	Object value = values.get(name);
	if (value == null) {
	    throw new NullPointerException("No mapped value for '" + name + "("
		    + key + ")'");
	} else if (value instanceof Map) {
	    ((Map) value).remove(key);
	} else {
	    throw new IllegalArgumentException("Non-mapped property for '"
		    + name + "(" + key + ")'");
	}

    }

    /**
     * Set the value of a simple property with the specified name.
     * 
     * @param name
     *            Name of the property whose value is to be set
     * @param value
     *            Value to which this property is to be set
     * 
     * @exception org.apache.commons.beanutils.ConversionException
     *                if the specified value cannot be converted to the type
     *                required for this property
     * @exception IllegalArgumentException
     *                if there is no property of the specified name
     * @exception NullPointerException
     *                if an attempt is made to set a primitive property to null
     */
    public void set(String name, Object value) {

	PplProperty descriptor = getDynaProperty(name);
	if (value == null) {
	    if (descriptor.getType().isPrimitive()) {
		throw new NullPointerException("Primitive value for '" + name
			+ "'");
	    }
	} else if (!isAssignable(descriptor.getType(), value.getClass())) {
	    throw new ConversionException("Cannot assign value of type '"
		    + value.getClass().getName() + "' to property '" + name
		    + "' of type '" + descriptor.getType().getName() + "'");
	}
	values.put(name, value);
	setChanged();
	notifyObservers(name);
    }

    /**
     * Set the value of an indexed property with the specified name.
     * 
     * @param name
     *            Name of the property whose value is to be set
     * @param index
     *            Index of the property to be set
     * @param value
     *            Value to which this property is to be set
     * 
     * @exception ConversionException
     *                if the specified value cannot be converted to the type
     *                required for this property
     * @exception IllegalArgumentException
     *                if there is no property of the specified name
     * @exception IllegalArgumentException
     *                if the specified property exists, but is not indexed
     * @exception IndexOutOfBoundsException
     *                if the specified index is outside the range of the
     *                underlying property
     */
    public void set(String name, int index, Object value) {

	DynaProperty descriptor = getDynaProperty(name);
	Object prop = values.get(name);
	if (prop == null) {
	    throw new NullPointerException("No indexed value for '" + name
		    + "[" + index + "]'");
	} else if (prop.getClass().isArray()) {
	    Array.set(prop, index, value);
	    setChanged();
	} else if (prop instanceof List) {
	    try {
		((List) prop).set(index, value);
		setChanged();
	    } catch (ClassCastException e) {
		throw new ConversionException(e.getMessage());
	    }
	} else {
	    throw new IllegalArgumentException("Non-indexed property for '"
		    + name + "[" + index + "]'");
	}
	notifyObservers(name);

    }

    public void add(String name, Object value) {

	Object prop = values.get(name);
	if (prop == null) {
	    throw new NullPointerException("No Container for '" + name);
	} else if (prop.getClass().isArray()) {
	    Array.set(prop, Array.getLength(prop), value);
	    setChanged();
	} else if (prop instanceof List) {
	    try {
		((List) prop).add(value);
		setChanged();
	    } catch (ClassCastException e) {
		throw new ConversionException(e.getMessage());
	    }
	} else {
	    throw new IllegalArgumentException("Non-indexed property for '"
		    + name);
	}
	notifyObservers(name);

    }

    /**
     * Set the value of a mapped property with the specified name.
     * 
     * @param name
     *            Name of the property whose value is to be set
     * @param key
     *            Key of the property to be set
     * @param value
     *            Value to which this property is to be set
     * 
     * @exception ConversionException
     *                if the specified value cannot be converted to the type
     *                required for this property
     * @exception IllegalArgumentException
     *                if there is no property of the specified name
     * @exception IllegalArgumentException
     *                if the specified property exists, but is not mapped
     */
    public void set(String name, String key, Object value) {

	DynaProperty descriptor = getDynaProperty(name);
	Object prop = values.get(name);
	if (prop == null) {
	    throw new NullPointerException("No mapped value for '" + name + "("
		    + key + ")'");
	} else if (prop instanceof Map) {
	    ((Map) prop).put(key, value);
	    setChanged();
	} else {
	    throw new IllegalArgumentException("Non-mapped property for '"
		    + name + "(" + key + ")'");
	}
	notifyObservers(name);

    }

    // ------------------------------------------------------ Protected Methods

    /**
     * Return the property descriptor for the specified property name.
     * 
     * @param name
     *            Name of the property for which to retrieve the descriptor
     * 
     * @exception IllegalArgumentException
     *                if this is not a valid property name for our DynaClass
     */
    protected PplProperty getDynaProperty(String name) {

	PplProperty descriptor = (PplProperty) getDynaClass().getDynaProperty(
		name);
	if (descriptor == null) {
	    throw new IllegalArgumentException("Invalid property name '" + name
		    + "'");
	}
	return (descriptor);

    }

    /**
     * Is an object of the source class assignable to the destination class?
     * 
     * @param dest
     *            Destination class
     * @param source
     *            Source class
     */
    protected boolean isAssignable(Class dest, Class source) {

	if (dest.isAssignableFrom(source)
		|| ((dest == Boolean.TYPE) && (source == Boolean.class))
		|| ((dest == Byte.TYPE) && (source == Byte.class))
		|| ((dest == Character.TYPE) && (source == Character.class))
		|| ((dest == Double.TYPE) && (source == Double.class))
		|| ((dest == Float.TYPE) && (source == Float.class))
		|| ((dest == Integer.TYPE) && (source == Integer.class))
		|| ((dest == Long.TYPE) && (source == Long.class))
		|| ((dest == Short.TYPE) && (source == Short.class))) {
	    return (true);
	} else {
	    return (false);
	}

    }

}
