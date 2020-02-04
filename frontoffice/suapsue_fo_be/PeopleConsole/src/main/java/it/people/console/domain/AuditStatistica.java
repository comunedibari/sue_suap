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

import it.people.feservice.beans.AuditStatisticheBean;

/**
 * @author Luca Barbieri- Pradac Informatica S.r.l
 * @created 13/lug/2011 15.13.29
 * 
 */
public class AuditStatistica extends AbstractBaseBean implements Clearable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3982747258806242713L;
	private String nomeStatistica;
	private AuditStatisticheBean[] risultati;

	public String getNomeStatistica() {
		return nomeStatistica;
	}

	public void setNomeStatistica(String nomeStatistica) {
		this.nomeStatistica = nomeStatistica;
	}

	public AuditStatisticheBean[] getRisultati() {
		return risultati;
	}

	public void setRisultati(AuditStatisticheBean[] risultati) {
		this.risultati = risultati;
	}

	/* (non-Javadoc)
	 * @see it.people.console.domain.Clearable#clear()
	 */
	public void clear() {
		this.nomeStatistica = null;
		this.risultati = null;

	}

}
