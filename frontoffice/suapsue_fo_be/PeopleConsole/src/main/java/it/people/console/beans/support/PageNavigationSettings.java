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

import java.util.ArrayList;
import java.util.Collection;

import it.people.console.domain.PairElement;
import it.people.console.system.MessageSourceAwareClass;

/**
 * @author Luca Barbieri - Pradac Informatica S.r.l. * 
 * @created 07/set/2011 09.20.46
 *
 */
public class PageNavigationSettings extends MessageSourceAwareClass {
	
	private Collection<PairElement<String, String>> resultsForPage = null;

	private static String resultForPageOptions = "pageNavigation.resultForPage";


	public PageNavigationSettings() {
		this.setResultsForPage(initResultsForPage());
	}

	/**
	 * @return the resultsForPage
	 */
	public final Collection<PairElement<String, String>> getResultsForPage() {
		return resultsForPage;
	}

	/**
	 * @param resultsForPage the resultsForPage to set
	 */
	private void setResultsForPage(
			Collection<PairElement<String, String>> resultsForPage) {
		this.resultsForPage = resultsForPage;
	}

	protected Collection<PairElement<String, String>> initResultsForPage() {
		
		Collection<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
		
		String[] options = this.getProperty(resultForPageOptions).split(",");
		for (String option : options) {
			result.add(new PairElement<String, String>(option, option));			
		}
		result.add(new PairElement<String, String>("0", "Tutti"));			

		return result;
		
	}

	
}
