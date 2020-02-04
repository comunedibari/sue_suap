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
package it.people.console.beans;

import java.util.ArrayList;
import java.util.List;

import it.people.console.beans.support.IFilter;
import it.people.console.beans.support.IFilters;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 03/dic/2010 10.19.26
 *
 */
public class ColumnsFilters implements IFilters {

	private List<IFilter> filters = null;
	
	public ColumnsFilters(List<IFilter> filters) {
		this.setFilters(filters);
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.beans.support.IFilters#getFilters()
	 */
	public List<IFilter> getFilters() {
		if (filters == null) {
			filters = new ArrayList<IFilter>();
		}
		return filters;
	}

	public void setFilters(List<IFilter> filters) {
		this.filters = filters;
	}
	
	public void addFilter(IFilter filter) {
		this.getFilters().add(filter);
	}
	
}
