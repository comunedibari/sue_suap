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
package it.people.console.persistence.jdbc.support;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 09/feb/2011 12.02.56
 *
 */
public class RowsStatusModeler implements IRowsStatusModeler<Integer> {

	private Map<Integer, RowStatusModeler> statusModelersCache;
	
	public RowsStatusModeler() {
		this.setStatusModelersCache(new HashMap<Integer, RowStatusModeler>());
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.jdbc.support.IRowsStatusModeler#getRowStatusModeler(java.lang.Object)
	 */
	public RowStatusModeler getRowStatusModeler(Integer statusKey) {

		RowStatusModeler result = null;
		
		result = this.statusModelersCache.get(statusKey);
		
		return result;
		
	}

	/**
	 * @param statusModelersCache the statusModelersCache to set
	 */
	public final void setStatusModelersCache(
			Map<Integer, RowStatusModeler> statusModelersCache) {
		this.statusModelersCache = statusModelersCache;
	}
	
	/**
	 * @param key
	 * @param rowStatusModeler
	 */
	public final void addStatusModeler(int key, RowStatusModeler rowStatusModeler) {
		this.statusModelersCache.put(new Integer(key), rowStatusModeler);
	}
	
}
