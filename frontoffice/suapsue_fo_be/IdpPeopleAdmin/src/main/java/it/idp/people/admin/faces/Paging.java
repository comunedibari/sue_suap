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
/*

  Licenza:	    Licenza Progetto PEOPLE
  Fornitore:    CEFRIEL
  Autori:       M. Pianciamore, P. Selvini

  Questo codice sorgente � protetto dalla licenza valida nell'ambito del
  progetto PEOPLE. La propriet� intellettuale di questo codice � e rester�
  esclusiva di "CEFRIEL Societ� Consortile a Responsabilit� Limitata" con
  sede legale in via Renato Fucini 2, 20133 Milano (MI).

  Disclaimer:

  COVERED CODE IS PROVIDED UNDER THIS LICENSE ON AN "AS IS" BASIS, WITHOUT
  WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, WITHOUT 
  LIMITATION, WARRANTIES THAT THE COVERED CODE IS FREE OF DEFECTS, MERCHANTABLE,
  FIT FOR A PARTICULAR PURPOSE OR NON-INFRINGING. THE ENTIRE RISK AS TO THE
  QUALITY AND PERFORMANCE OF THE COVERED CODE IS WITH YOU. SHOULD ANY COVERED
  CODE PROVE DEFECTIVE IN ANY RESPECT, YOU (NOT THE INITIAL DEVELOPER OR ANY
  OTHER CONTRIBUTOR) ASSUME THE COST OF ANY NECESSARY SERVICING, REPAIR OR
  CORRECTION.
    
*/
package it.idp.people.admin.faces;

import it.idp.people.admin.sqlmap.common.IdpPeopleAdminConstants;

public class Paging 
{
	private int rowsCount = 0;
	private int pageCount = 0;
	private int rowsPerPage = 10;
	private int firstRowIndex = 0;
	private int pageIndex = 1;
	
	public int getPageIndex() {
			return (firstRowIndex / rowsPerPage) + 1;
	}

	public void setPageIndex(int pageIndex) {
		if(pageIndex<=0)
			return;
		if(pageIndex>pageCount)
			return;
		this.pageIndex = (pageIndex-1);
		firstRowIndex = rowsPerPage * this.pageIndex;
		if(firstRowIndex >= rowsCount) {
			firstRowIndex = rowsCount - rowsPerPage;
			if(firstRowIndex < 0) {
				firstRowIndex = 0;
			}
		}
	}
	
	public void setPageCount(int pageCount) {
		if(pageCount<=0)
			return;
		this.pageCount = pageCount;		
	}
	
	public int getPageCount() {
		pageCount = new Double(Math.floor(rowsCount/rowsPerPage)).intValue();
		if(rowsCount%rowsPerPage > 0){
			pageCount = pageCount + 1;
		}
		return pageCount;
	}	
	
	public int getrowsPerPage() {
		return rowsPerPage;
	}
	
	public void setrowsPerPage(int rowsPerPage) {
		if(rowsPerPage<=0)
			return;
		this.rowsPerPage = rowsPerPage;
	}
	
	public int getFirstRowIndex() {
		return firstRowIndex;
	}
	
	public String scrollFirst() {
		firstRowIndex = 0;
		return IdpPeopleAdminConstants.RETURN_STATUS_SUCCESS;
	}
	
	public String scrollPrevious() {
		firstRowIndex -= rowsPerPage;
		if(firstRowIndex<0)
		{
			firstRowIndex = 0;
		}
		return IdpPeopleAdminConstants.RETURN_STATUS_SUCCESS;
	}
	
	public String scrollNext() {
		firstRowIndex += rowsPerPage;
		if(firstRowIndex >= rowsCount) {
			firstRowIndex = rowsCount - rowsPerPage;
			if(firstRowIndex < 0) {
				firstRowIndex = 0;
			}
		}
		return IdpPeopleAdminConstants.RETURN_STATUS_SUCCESS;
	}
	
	public String scrollLast() {
		firstRowIndex = rowsCount - rowsPerPage;
		if(firstRowIndex < 0) {
			firstRowIndex = 0;
		}
		return IdpPeopleAdminConstants.RETURN_STATUS_SUCCESS;
	}
	
	public boolean isScrollFirstDisabled() {
		return firstRowIndex==0;
	}
	
	public boolean isScrollLastDisabled() {
		return firstRowIndex >= rowsCount - rowsPerPage;
	}

	public int getRowsCount(){
		return rowsCount;
	}
	
	public void setRowsCount(int rowsCount) {
		this.rowsCount = rowsCount;
	}
}
