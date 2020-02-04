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
package it.idp.people.admin.sqlmap.capeople.comuni;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import it.idp.people.admin.faces.Manager;
import it.idp.people.admin.sqlmap.common.FilterBean;

public class ComuniFilterBean extends FilterBean {
	private String fromComune;

	private String fromCap;

	private String toComune;

	private String toCap;

	private String typeComune;

	private String typeCap;

	public void filter() {
		Manager tableManager = (Manager) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tableManager");
		if (tableManager != null) {
			ComuniDAO comuniDAO = (ComuniDAO) tableManager.getTableAction();

			String whereClause = "";

			if (typeComune.equals("0") && typeCap.equals("0")) {
				return;
			}

			String comuneClause = textFilterBuilder("comune", typeComune, fromComune, toComune + "zzz");
			String capClause = textFilterBuilder("cap", typeCap, fromCap, toCap + "99999");

			whereClause += comuneClause;
			if (comuneClause != null && capClause != null
					&& !comuneClause.equals("") && !capClause.equals("")) {
				whereClause += " AND ";
			}
			whereClause += capClause;

			comuniDAO.setWhereClause(whereClause);
		}
	}

	public String cancel() {
		fromComune = "";
		fromCap = "";
		toComune = "";
		toCap = "";
		typeComune = "";
		typeCap = "";
		Manager tableManager = (Manager) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("tableManager");
		if (tableManager != null) {
			ComuniDAO comuniDAO = (ComuniDAO) tableManager.getTableAction();
			comuniDAO.setWhereClause(null);
		}
		return "reset";
	}

	public void typeComuneChanged(ValueChangeEvent e) {
		typeComune = (String) e.getNewValue();
	}

	public void typeCapChanged(ValueChangeEvent e) {
		typeCap = (String) e.getNewValue();
	}

	public String getTypeComune() {
		return typeComune;
	}

	public String getTypeCap() {
		return typeCap;
	}

	public String getFromCap() {
		return fromCap;
	}

	public void setFromCap(String fromCap) {
		this.fromCap = fromCap;
	}

	public String getFromComune() {
		return fromComune;
	}

	public void setFromComune(String fromComune) {
		this.fromComune = fromComune;
	}

	public String getToCap() {
		return toCap;
	}

	public void setToCap(String toCap) {
		this.toCap = toCap;
	}

	public String getToComune() {
		return toComune;
	}

	public void setToComune(String toComune) {
		this.toComune = toComune;
	}
}
