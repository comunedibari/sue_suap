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

import java.util.Map;

/**
 * @author Luca Barbieri- Engineering Ingegneria Informatica S.p.A.
 * @created 13/lug/2011 15.13.29
 * 
 */
public class AuditStatistiche extends AbstractBaseBean implements Clearable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6343249162652087710L;
	private String nomeNodo;
	private String communeid;
	private Map<String,AuditStatistica> statistiche;
//	private List<AuditStatistica> statistiche;

	public String getNomeNodo() {
		return nomeNodo;
	}

	public void setNomeNodo(String nomeNodo) {
		this.nomeNodo = nomeNodo;
	}

	public String getCommuneid() {
		return communeid;
	}

	public void setCommuneid(String communeid) {
		this.communeid = communeid;
	}

//	public List<AuditStatistica> getStatistiche() {
//		return statistiche;
//	}
//
//	public void setStatistiche(List<AuditStatistica> statistiche) {
//		this.statistiche = statistiche;
//	}
	public Map<String,AuditStatistica> getStatistiche() {
		return statistiche;
	}
	
	public void setStatistiche(Map<String,AuditStatistica> statistiche) {
		this.statistiche = statistiche;
	}

	/* (non-Javadoc)
	 * @see it.people.console.domain.Clearable#clear()
	 */
	public void clear() {
		this.nomeNodo = null;
		this.communeid = null;
		this.statistiche = null;

	}

}
