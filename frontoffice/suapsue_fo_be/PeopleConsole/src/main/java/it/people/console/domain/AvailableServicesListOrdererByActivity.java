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
import java.util.Comparator;

import it.people.feservice.beans.AvailableService;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 08/feb/2011 18.47.47
 *
 */
public class AvailableServicesListOrdererByActivity implements Comparator<AvailableService>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8729752035044640349L;

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(AvailableService o1, AvailableService o2) {
		
		int result = o1.getActivity().compareToIgnoreCase(o2.getActivity());
		
//		result = (result == 0) ? o1.getSubActivity().compareToIgnoreCase(o2.getSubActivity()) : 0;
//
//		result = (result == 0) ? o1.getActivity().compareToIgnoreCase(o2.getActivity()) : 0;
		
		return result;
		
	}

}
