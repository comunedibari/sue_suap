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
package it.people.console.domain;

import java.io.Serializable;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 29/gen/2011 09.37.22
 *
 */
public class TripleElement<V, L, K> implements Serializable {

	private static final long serialVersionUID = -8785844993061925064L;

	private V value;
	
	private L label;
	
	private K key;
	
	public TripleElement(final V value, final L label, final K key) {
		this.setValue(value);
		this.setLabel(label);
		this.setKey(key);
	}

	/**
	 * @return the value
	 */
	public final V getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public final void setValue(V value) {
		this.value = value;
	}

	/**
	 * @return the label
	 */
	public final L getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public final void setLabel(L label) {
		this.label = label;
	}

	/**
	 * @return the key
	 */
	public final K getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public final void setKey(K key) {
		this.key = key;
	}

}
