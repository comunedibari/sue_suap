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
package it.people.util.dto;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         10/ott/2012 09:44:03
 */
public class VelocityModelObject {

    private String objectName;

    private Class<?> objectClass;

    private Object instance;

    /**
     * @param objectName
     * @param objectClass
     * @param instance
     */
    public VelocityModelObject(final String objectName,
	    final Class<?> objectClass, final Object instance) {
	this.setObjectName(objectName);
	this.setObjectClass(objectClass);
	this.setInstance(instance);
    }

    /**
     * @param objectName
     *            the objectName to set
     */
    private void setObjectName(String objectName) {
	this.objectName = objectName;
    }

    /**
     * @param objectClass
     *            the objectClass to set
     */
    private void setObjectClass(Class<?> objectClass) {
	this.objectClass = objectClass;
    }

    /**
     * @param instance
     *            the instance to set
     */
    private void setInstance(Object instance) {
	this.instance = instance;
    }

    /**
     * @return the objectName
     */
    public final String getObjectName() {
	return this.objectName;
    }

    /**
     * @return the objectClass
     */
    public final Class<?> getObjectClass() {
	return this.objectClass;
    }

    /**
     * @return the instance
     */
    public final Object getInstance() {
	return this.instance;
    }

}
