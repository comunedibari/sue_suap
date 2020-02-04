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
package it.idp.people.admin.sqlmap.sirac.accreditamenti;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import it.idp.people.admin.faces.Manager;
import it.idp.people.admin.sqlmap.common.FilterBean;

public class AccreditamentiFilterBean extends FilterBean 
{
	private String fromCF;
	private String toCF;
	private String typeCF;
	private String fromQualifica;
	private String toQualifica;
	private String typeQualifica;
	private String fromComune;
	private String toComune;
	private String typeComune;
	
	
	public void filter()
	{
		Manager tableManager = (Manager)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tableManager");
		if(tableManager!=null)
		{			
			AccreditamentiDAO accreditamentiDAO = (AccreditamentiDAO)tableManager.getTableAction();
			
			String whereClause = "attivo = 1";
			
			if (typeCF.equals("0") && typeQualifica.equals("0") && typeComune.equals("0")) {
				return;
			}
			
			String CFClause = textFilterBuilder("codice_fiscale", typeCF, fromCF, toCF);
			String QualificaClause = textFilterBuilder("id_qualifica", typeQualifica, fromQualifica, toQualifica);
			String ComuneClause = textFilterBuilder("id_comune", typeComune, fromComune, toComune);
			
			if (CFClause != null && !CFClause.equals("")) 
			{
				whereClause += " AND " + CFClause;
			}
			if (QualificaClause != null && !QualificaClause.equals("")) 
			{
				whereClause += " AND " + QualificaClause;
			}
			if (ComuneClause != null && !ComuneClause.equals("")) 
			{
				whereClause += " AND " + ComuneClause;
			}
			
			accreditamentiDAO.setWhereClause(whereClause);
		}
	}
	public String cancel()
	{
		
		Manager tableManager = (Manager)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tableManager");
		if(tableManager!=null)
		{
			AccreditamentiDAO accreditamentiDAO = (AccreditamentiDAO)tableManager.getTableAction();
			accreditamentiDAO.setWhereClause(null);
		}
		return "reset";
	}

	public void typeQualificaChanged(ValueChangeEvent e) 	{
		typeQualifica =(String)e.getNewValue();		
	}
	
	public void typeCFChanged(ValueChangeEvent e) 	{
		typeCF =(String)e.getNewValue();		
	}
	
	public void typeComuneChanged(ValueChangeEvent e) 	{
		typeComune =(String)e.getNewValue();		
	}
	
	public String getFromCF() {
		return fromCF;
	}
	
	public void setFromCF(String fromCF) {
		this.fromCF = fromCF;
	}
	
	public String getFromComune() {
		return fromComune;
	}
	
	public void setFromComune(String fromComune) {
		this.fromComune = fromComune;
	}
	
	public String getFromQualifica() {
		return fromQualifica;
	}
	
	public void setFromQualifica(String fromQualifica) {
		this.fromQualifica = fromQualifica;
	}
	
	public String getToCF() {
		return toCF;
	}
	
	public void setToCF(String toCF) {
		this.toCF = toCF;
	}
	
	public String getToComune() {
		return toComune;
	}
	
	public void setToComune(String toComune) {
		this.toComune = toComune;
	}
	
	public String getToQualifica() {
		return toQualifica;
	}
	
	public void setToQualifica(String toQualifica) {
		this.toQualifica = toQualifica;
	}
	
	public String getTypeCF() {
		return typeCF;
	}
	
	public void setTypeCF(String typeCF) {
		this.typeCF = typeCF;
	}
	
	public String getTypeComune() {
		return typeComune;
	}
	
	public void setTypeComune(String typeComune) {
		this.typeComune = typeComune;
	}
	
	public String getTypeQualifica() {
		return typeQualifica;
	}
	
	public void setTypeQualifica(String typeQualifica) {
		this.typeQualifica = typeQualifica;
	}
	
	
}
