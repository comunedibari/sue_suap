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

import it.people.console.domain.exceptions.PagedListHoldersCacheException;
import it.people.console.java.util.PagedListHolderKey;
import it.people.console.java.util.PagedListHoldersTreeMap;
import it.people.console.persistence.beans.support.ILazyPagedListHolder;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 16/gen/2011 14.33.34
 *
 */
public class AbstractBaseBean implements IPagedListHolderBean, Serializable {

	private static final long serialVersionUID = -5966704623534493687L;
	
	private PagedListHoldersTreeMap pagedListHolders = new PagedListHoldersTreeMap();

	public void setPagedListHolders(PagedListHoldersTreeMap pagedListHolders) {
		this.pagedListHolders = pagedListHolders;
	}
	
	public PagedListHoldersTreeMap getPagedListHolders() {
		return this.pagedListHolders;
	}

	public ILazyPagedListHolder getPagedListHolder(String key) {
		return this.getPagedListHolders().get(key);
	}

	public void addPagedListHolder(ILazyPagedListHolder pagedListHolder) throws PagedListHoldersCacheException {
		if (this.getPagedListHolders().containsKey(pagedListHolder.getPagedListId())) {
			throw new PagedListHoldersCacheException("Trying to add already cached paged list holder (id = '" + 
					pagedListHolder.getPagedListId() + "').");
		}
		this.getPagedListHolders().put(pagedListHolder);
	}

	public void updatePagedListHolder(ILazyPagedListHolder pagedListHolder) throws PagedListHoldersCacheException {
		PagedListHolderKey holderKey = this.getPagedListHolders().remove(pagedListHolder);
		this.getPagedListHolders().update(pagedListHolder, holderKey);
	}
	
}
