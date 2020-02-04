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
package it.idp.people.admin.sqlmap.sirac.qualifiche;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import it.idp.people.admin.faces.Manager;
import it.idp.people.admin.sqlmap.common.FilterBean;

public class QualificheFilterBean extends FilterBean {
	
	private String fromQualifica;

	private String toQualifica;

	private String typeQualifica;

	private String fromDescr;

	private String toDescr;

	private String typeDescr;

	private String fromTipologia;

	private String toTipologia;

	private String typeTipologia;

	public void filter() {
		Manager tableManager = (Manager) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("tableManager");
		if (tableManager != null) {
			QualificheDAO qualificheDAO = (QualificheDAO) tableManager.getTableAction();

			String whereClause = "";
			
			if (typeQualifica.equals("0") && typeDescr.equals("0") && typeTipologia.equals("0")) {
				return;
			}

			String QualificaClause = textFilterBuilder("id_qualifica",
					typeQualifica, fromQualifica, toQualifica);
			String DescrClause = textFilterBuilder("descrizione", typeDescr,
					fromDescr, toDescr);
			String TipologiaClause = textFilterBuilder("tipo_qualifica",
					typeTipologia, fromTipologia, toTipologia);

			if (QualificaClause != null && !QualificaClause.equals("")) {
				if (whereClause.equals("")) {
					whereClause += QualificaClause;
				} else {
					whereClause += " AND " + QualificaClause;
				}
			}
			if (DescrClause != null && !DescrClause.equals("")) {
				if (whereClause.equals("")) {
					whereClause += DescrClause;
				} else {
					whereClause += " AND " + DescrClause;
				}
			}
			if (TipologiaClause != null && !TipologiaClause.equals("")) {
				if (whereClause.equals("")) {
					whereClause += TipologiaClause;
				} else {
					whereClause += " AND " + TipologiaClause;
				}
			}

			qualificheDAO.setWhereClause(whereClause);
		}
	}

	public String cancel() {

		Manager tableManager = (Manager) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("tableManager");
		if (tableManager != null) {
			QualificheDAO qualificheDAO = (QualificheDAO) tableManager
					.getTableAction();
			qualificheDAO.setWhereClause(null);
		}
		return "reset";
	}

	public void typeQualificaChanged(ValueChangeEvent e) {
		typeQualifica = (String) e.getNewValue();
	}

	public void typeDescrChanged(ValueChangeEvent e) {
		typeDescr = (String) e.getNewValue();
	}

	public void typeTipologiaChanged(ValueChangeEvent e) {
		typeTipologia = (String) e.getNewValue();
	}

	public String getFromDescr() {
		return fromDescr;
	}

	public void setFromDescr(String fromDescr) {
		this.fromDescr = fromDescr;
	}

	public String getFromQualifica() {
		return fromQualifica;
	}

	public void setFromQualifica(String fromQualifica) {
		this.fromQualifica = fromQualifica;
	}

	public String getFromTipologia() {
		return fromTipologia;
	}

	public void setFromTipologia(String fromTipologia) {
		this.fromTipologia = fromTipologia;
	}

	public String getToDescr() {
		return toDescr;
	}

	public void setToDescr(String toDescr) {
		this.toDescr = toDescr;
	}

	public String getToQualifica() {
		return toQualifica;
	}

	public void setToQualifica(String toQualifica) {
		this.toQualifica = toQualifica;
	}

	public String getToTipologia() {
		return toTipologia;
	}

	public void setToTipologia(String toTipologia) {
		this.toTipologia = toTipologia;
	}

	public String getTypeDescr() {
		return typeDescr;
	}

	public void setTypeDescr(String typeDescr) {
		this.typeDescr = typeDescr;
	}

	public String getTypeQualifica() {
		return typeQualifica;
	}

	public void setTypeQualifica(String typeQualifica) {
		this.typeQualifica = typeQualifica;
	}

	public String getTypeTipologia() {
		return typeTipologia;
	}

	public void setTypeTipologia(String typeTipologia) {
		this.typeTipologia = typeTipologia;
	}

}
