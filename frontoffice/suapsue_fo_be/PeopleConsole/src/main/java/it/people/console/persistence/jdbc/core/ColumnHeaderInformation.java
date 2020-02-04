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
package it.people.console.persistence.jdbc.core;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 17/gen/2011 18.26.15
 *
 */
public class ColumnHeaderInformation {

	private String name;
	
	private String label;
	
	private SortingTypes sortingType = null;
	
	public ColumnHeaderInformation(final String name, final String label) {
		this.setName(name);
		this.setLabel(label);
	}

	/**
	 * @param name the name to set
	 */
	private void setName(String name) {
		this.name = name;
	}

	/**
	 * @param label the label to set
	 */
	private void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @return the label
	 */
	public final String getLabel() {
		return label;
	}

	/**
	 * @return the sortingType
	 */
	public final SortingTypes getSortingType() {
		return sortingType;
	}

	/**
	 * @param sortingType the sortingType to set
	 */
	public final void setSortingType(SortingTypes sortingType) {
		this.sortingType = sortingType;
	}


	public enum SortingTypes {
		
		asc("asc"), desc("desc");
		
		private String sortType;
		
		private SortingTypes(String sortType) {
			this.setSortType(sortType);
		}

		/**
		 * @param sortType the sortType to set
		 */
		private void setSortType(String sortType) {
			this.sortType = sortType;
		}

		/**
		 * @return the sortType
		 */
		public final String getSortType() {
			return sortType;
		}
		
	}
	
}
