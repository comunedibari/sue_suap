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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Sep 10, 2003 Time: 10:02:58 PM
 * To change this template use Options | File Templates.
 */
public class PplClass implements DynaClass {

    // ----------------------------------------------------------- Constructors

    /**
     * Construct a new BasicDynaClass with default parameters.
     */
    public PplClass() {

	this(null, null, null);

    }

    /**
     * Construct a new BasicDynaClass with the specified parameters.
     * 
     * @param name
     *            Name of this DynaBean class
     * @param dynaBeanClass
     *            The implementation class for new instances
     */
    public PplClass(String name, Class dynaBeanClass) {

	this(name, dynaBeanClass, null);

    }

    /**
     * Construct a new BasicDynaClass with the specified parameters.
     * 
     * @param name
     *            Name of this DynaBean class
     * @param dynaBeanClass
     *            The implementation class for new intances
     * @param properties
     *            Property descriptors for the supported properties
     */
    public PplClass(String name, Class dynaBeanClass, PplProperty properties[]) {

	super();
	if (name != null)
	    this.name = name;
	if (dynaBeanClass == null)
	    dynaBeanClass = PplBean.class;
	setDynaBeanClass(dynaBeanClass);
	if (properties != null)
	    setProperties(properties);

    }

    /**
     * Construct a new BasicDynaClass with the specified parameters.
     * 
     * @param name
     *            Name of this DynaBean class
     * @param dynaBeanClass
     *            The implementation class for new intances
     * @param properties
     *            Property descriptors for the supported properties
     */
    public PplClass(String name, Class dynaBeanClass, PplProperty properties[],
	    Class[] constrTypes) {

	super();
	if (constrTypes != null)
	    constructorTypes = constrTypes;

	if (name != null)
	    this.name = name;
	if (dynaBeanClass == null)
	    dynaBeanClass = PplBean.class;
	setDynaBeanClass(dynaBeanClass);
	if (properties != null)
	    setProperties(properties);

    }

    // ----------------------------------------------------- Instance Variables

    /**
     * The constructor of the <code>dynaBeanClass</code> that we will use for
     * creating new instances.
     */
    protected Constructor constructor = null;

    /**
     * The method signature of the constructor we will use to create new
     * DynaBean instances.
     */
    protected Class constructorTypes[] = { DynaClass.class };

    /**
     * The argument values to be passed to the constructore we will use to
     * create new DynaBean instances.
     */
    protected Object constructorValues[] = { this };

    /**
     * The <code>DynaBean</code> implementation class we will use for creating
     * new instances.
     */
    protected Class dynaBeanClass = PplBean.class;

    /**
     * The "name" of this DynaBean class.
     */
    protected String name = this.getClass().getName();

    /**
     * The set of dynamic properties that are part of this DynaClass.
     */
    protected DynaProperty properties[] = new DynaProperty[0];

    /**
     * The set of dynamic properties that are part of this DynaClass, keyed by
     * the property name. Individual descriptor instances will be the same
     * instances as those in the <code>properties</code> list.
     */
    protected HashMap propertiesMap = new HashMap();

    // ------------------------------------------------------ DynaClass Methods

    /**
     * Return the name of this DynaClass (analogous to the
     * <code>getName()</code> method of <code>java.lang.Class</code), which
     * allows the same <code>DynaClass</code> implementation class to support
     * different dynamic classes, with different sets of properties.
     */
    public String getName() {

	return (this.name);

    }

    /**
     * Return a property descriptor for the specified property, if it exists;
     * otherwise, return <code>null</code>.
     * 
     * @param name
     *            Name of the dynamic property for which a descriptor is
     *            requested
     * 
     * @exception IllegalArgumentException
     *                if no property name is specified
     */
    public DynaProperty getDynaProperty(String name) {

	if (name == null) {
	    throw new IllegalArgumentException("No property name specified");
	}
	return ((DynaProperty) propertiesMap.get(name));

    }

    /**
     * <p>
     * Return an array of <code>ProperyDescriptors</code> for the properties
     * currently defined in this DynaClass. If no properties are defined, a
     * zero-length array will be returned.
     * </p>
     */
    public DynaProperty[] getDynaProperties() {

	return (properties);

    }

    /**
     * Instantiate and return a new DynaBean instance, associated with this
     * DynaClass.
     * 
     * @exception IllegalAccessException
     *                if the Class or the appropriate constructor is not
     *                accessible
     * @exception InstantiationException
     *                if this Class represents an abstract class, an array
     *                class, a primitive type, or void; or if instantiation
     *                fails for some other reason
     */
    public DynaBean newInstance() throws IllegalAccessException,
	    InstantiationException {

	try {
	    return ((DynaBean) constructor.newInstance(constructorValues));
	} catch (InvocationTargetException e) {
	    throw new InstantiationException(e.getTargetException()
		    .getMessage());
	}

    }

    public DynaBean newInstance(Object[] costrValues)
	    throws IllegalAccessException, InstantiationException {

	try {
	    return ((DynaBean) constructor.newInstance(costrValues));
	} catch (InvocationTargetException e) {
	    throw new InstantiationException(e.getTargetException()
		    .getMessage());
	}

    }

    // --------------------------------------------------------- Public Methods

    /**
     * Return the Class object we will use to create new instances in the
     * <code>newInstance()</code> method. This Class <strong>MUST</strong>
     * implement the <code>DynaBean</code> interface.
     */
    public Class getDynaBeanClass() {

	return (this.dynaBeanClass);

    }

    // ------------------------------------------------------ Protected Methods

    /**
     * Set the Class object we will use to create new instances in the
     * <code>newInstance()</code> method. This Class <strong>MUST</strong>
     * implement the <code>DynaBean</code> interface.
     * 
     * @param dynaBeanClass
     *            The new Class object
     * 
     * @exception IllegalArgumentException
     *                if the specified Class does not implement the
     *                <code>DynaBean</code> interface
     */
    protected void setDynaBeanClass(Class dynaBeanClass) {

	// Validate the argument type specified
	if (dynaBeanClass.isInterface())
	    throw new IllegalArgumentException("Class "
		    + dynaBeanClass.getName() + " is an interface, not a class");
	if (!DynaBean.class.isAssignableFrom(dynaBeanClass))
	    throw new IllegalArgumentException("Class "
		    + dynaBeanClass.getName() + " does not implement DynaBean");

	// Identify the Constructor we will use in newInstance()
	try {
	    this.constructor = dynaBeanClass.getConstructor(constructorTypes);
	} catch (NoSuchMethodException e) {
	    throw new IllegalArgumentException("Class "
		    + dynaBeanClass.getName()
		    + " does not have an appropriate constructor");
	}
	this.dynaBeanClass = dynaBeanClass;

    }

    /**
     * Set the list of dynamic properties supported by this DynaClass.
     * 
     * @param properties
     *            List of dynamic properties to be supported
     */
    protected void setProperties(DynaProperty properties[]) {

	this.properties = properties;
	propertiesMap.clear();
	for (int i = 0; i < properties.length; i++) {
	    propertiesMap.put(properties[i].getName(), properties[i]);
	}

    }

}
