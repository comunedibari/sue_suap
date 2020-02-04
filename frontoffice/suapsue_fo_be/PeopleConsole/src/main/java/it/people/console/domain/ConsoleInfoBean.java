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
import java.util.ArrayList;
import java.util.List;

import it.people.console.dto.UnavailableBEServiceDTO;

/**
 * This bean contains updated messages and info showed up in People Console	 
 * 
 * @author Andrea Piemontese - Engineering Ingegneria Informatica S.p.A.
 * @created 19/ott/2012 10:27:24
 *
 */
public class ConsoleInfoBean implements Serializable {

	private static final long serialVersionUID = 905624210894939670L;

	private List <UnavailableBEServiceDTO> beServicesDown = new ArrayList <UnavailableBEServiceDTO> ();
	
	public ConsoleInfoBean() {

	}

	/**
	 * @return the beServicesDown
	 */
	public List <UnavailableBEServiceDTO> getBeServicesDown() {
		return beServicesDown;
	}

	/**
	 * @param beServicesDown the beServicesDown to set
	 */
	public void setBeServicesDown(List <UnavailableBEServiceDTO> beServicesDown) {
		this.beServicesDown = beServicesDown;
	}

	

	
	
}
