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
package it.idp.people.admin.sqlmap.capeople.userdata;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import it.idp.people.admin.faces.Manager;
import it.idp.people.admin.sqlmap.common.FilterBean;

public class UserdataFilterBean extends FilterBean
{
	private String fromNome;
	private String fromCognome;
	private String toNome;
	private String toCognome;
	private String typeNome;
	private String typeCognome;
		
	public void filter() {
		Manager tableManager = (Manager)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tableManager");
		if(tableManager!=null) {			
			UserdataDAO comuniDAO = (UserdataDAO)tableManager.getTableAction();
			
			String whereClause = "";
			
			if (typeNome.equals("0") && typeCognome.equals("0")) {
				return;
			}
			
			String nomeClause = textFilterBuilder("nome", typeNome, fromNome, toNome + "zzz");
			String cognomeClause = textFilterBuilder("cognome", typeCognome, fromCognome, toCognome + "zzz");
			
			whereClause += nomeClause;
			if(nomeClause != null && cognomeClause != null && !nomeClause.equals("") && !cognomeClause.equals(""))
			{
				whereClause += " AND ";
			}
			whereClause += cognomeClause;
			
			comuniDAO.setWhereClause(whereClause);
		}
	}
	public String cancel() {
		fromNome = "";
		fromCognome = "";
		toNome = "";
		toCognome = "";
		typeNome = "";
		typeCognome = "";
		Manager tableManager = (Manager)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tableManager");
		if(tableManager!=null) {
			UserdataDAO comuniDAO = (UserdataDAO)tableManager.getTableAction();
			comuniDAO.setWhereClause(null);
		}
		return "reset";
	}
	
	public void typeCognomeChanged(ValueChangeEvent e) {
		typeCognome =(String)e.getNewValue();		
	}
	
	public void typeNomeChanged(ValueChangeEvent e) {
		typeNome = (String)e.getNewValue();
	}
	
	public String getFromCognome() {
		return fromCognome;
	}
	
	public void setFromCognome(String fromCognome) {
		this.fromCognome = fromCognome;
	}
	
	public String getFromNome() {
		return fromNome;
	}
	
	public void setFromNome(String fromNome) {
		this.fromNome = fromNome;
	}
	
	public String getToCognome() {
		return toCognome;
	}
	
	public void setToCognome(String toCognome) {
		this.toCognome = toCognome;
	}
	
	public String getToNome() {
		return toNome;
	}
	
	public void setToNome(String toNome) {
		this.toNome = toNome;
	}
	
	public String getTypeCognome() {
		return typeCognome;
	}
	
	public void setTypeCognome(String typeCognome) {
		this.typeCognome = typeCognome;
	}
	
	public String getTypeNome() {
		return typeNome;
	}
	
	public void setTypeNome(String typeNome) {
		this.typeNome = typeNome;
	}
	
}
