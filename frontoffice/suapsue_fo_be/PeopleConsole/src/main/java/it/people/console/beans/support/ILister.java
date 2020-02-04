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
package it.people.console.beans.support;

import java.util.List;
import java.util.Vector;

/**
 * @author Riccardo Forafo'
 * @version 1.0
 * @created 29-nov-2010 21:54:59
 */
public interface ILister {

	public void setListSize(final int listSize);

	public int getListSize();

	public List<?> getList();

	public int getPage();

	public int getPageCount();

	public boolean isFirstPage();

	public boolean isLastPage();

	public void nextPage();

	public void previousPage();

	public void setPage(final int pageNumber);

	public void setOrderingColumn(final String columnName);

	public String getOrderingColumn();

	public void setOrderingType(final String orderingType);

	public String getOrderingType();

}
