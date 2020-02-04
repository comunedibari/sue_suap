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

import it.people.console.domain.exceptions.PagedListHoldersCacheException;
import it.people.console.java.util.PagedListHoldersTreeMap;
import it.people.console.persistence.beans.support.ILazyPagedListHolder;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 16/gen/2011 14.45.45
 *
 */
public interface IPagedListHolderBean {

	public void setPagedListHolders(PagedListHoldersTreeMap pagedListHolders);
	
	public PagedListHoldersTreeMap getPagedListHolders();

	public ILazyPagedListHolder getPagedListHolder(String key);

	public void addPagedListHolder(ILazyPagedListHolder pagedListHolder) throws PagedListHoldersCacheException;
	
}
