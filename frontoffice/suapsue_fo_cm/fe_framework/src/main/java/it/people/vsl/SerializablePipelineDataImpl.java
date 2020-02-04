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
package it.people.vsl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         15/ott/2012 22:03:00
 */
public class SerializablePipelineDataImpl implements SerializablePipelineData,
	java.io.Serializable {

    private static final long serialVersionUID = 291807024166596294L;

    private Map<String, Object> m_attributes;

    /**
	 * 
	 */
    public SerializablePipelineDataImpl() {

	m_attributes = new HashMap<String, Object>();

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.people.vsl.SerializablePipelineData#setAttribute(java.lang.String,
     * java.lang.Object)
     */
    public void setAttribute(String name, Object value) {

	if (name == null || "".equals(name)) {
	    return;
	}

	m_attributes.put(name, value);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.people.vsl.SerializablePipelineData#getAttribute(java.lang.String)
     */
    public Object getAttribute(String name) {

	if (name == null || "".equals(name)) {
	    return null;
	}

	return m_attributes.get(name);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.people.vsl.SerializablePipelineData#removeAttribute(java.lang.String)
     */
    public void removeAttribute(String name) {

	if (name == null || "".equals(name)) {
	    return;
	}

	m_attributes.remove(name);

    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.vsl.SerializablePipelineData#getKeySet()
     */
    public Set<String> getKeySet() {
	return this.m_attributes.keySet();
    }

}
