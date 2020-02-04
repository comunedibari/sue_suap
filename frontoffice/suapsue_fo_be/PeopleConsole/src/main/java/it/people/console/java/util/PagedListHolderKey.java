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
package it.people.console.java.util;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 16/gen/2011 11.41.02
 *
 */
public class PagedListHolderKey implements Serializable {

	private static final long serialVersionUID = 233784845760496499L;

	private Timestamp timestamp;
	
	private String pagedListId;
	
	private Integer ordinal;
	
	public PagedListHolderKey(final Timestamp timestamp, final String pagedListId) {
		this.setTimestamp(timestamp);
		this.setPagedListId(pagedListId);
	}

	/**
	 * @return the timestamp
	 */
	public final Timestamp getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public final void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the pagedListId
	 */
	public final String getPagedListId() {
		return pagedListId;
	}

	/**
	 * @param pagedListId the pagedListId to set
	 */
	public final void setPagedListId(String pagedListId) {
		this.pagedListId = pagedListId;
	}
	
	/**
	 * @return the ordinal
	 */
	public final Integer getOrdinal() {
		return this.ordinal;
	}

	/**
	 * @param ordinal the ordinal to set
	 */
	public final void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[timestamp = " + this.getTimestamp() 
			+ "; pagedListId = " + this.getPagedListId()
			+ "; ordinal = " + this.getOrdinal() + "]" ;
	}
	
}
