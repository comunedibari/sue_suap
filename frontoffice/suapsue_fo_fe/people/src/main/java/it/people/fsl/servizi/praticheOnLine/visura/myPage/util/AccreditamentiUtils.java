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
package it.people.fsl.servizi.praticheOnLine.visura.myPage.util;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.core.SiracHelper;
import it.people.sirac.services.accr.IAccreditamentoClientAdapter;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 * 20/lug/2012 15:10:38
 */
public class AccreditamentiUtils {

	private static final String CATEGORY_ASSOCIATION_REPRESENTATIVE_CODE = "RCT";
	private static final String CATEGORY_ASSOCIATION_AGENT_CODE = "OAC";
	
	public static final boolean hasCategoryAssociationProfile(final HttpServletRequest request, final String taxCode, final String municipalityId) throws Exception {

		String IAccreditamentoURLString = SiracHelper.getIAccreditamentoServiceURLString(request.getSession().getServletContext());

		IAccreditamentoClientAdapter accr = new IAccreditamentoClientAdapter(IAccreditamentoURLString);
		
		return accr.esisteQualifica(taxCode, municipalityId, new String[] {CATEGORY_ASSOCIATION_REPRESENTATIVE_CODE, CATEGORY_ASSOCIATION_AGENT_CODE});

	}

	public static final Vector<String> getCategoriesAssociationCodes(final HttpServletRequest request, final String taxCode, final String municipalityId) throws Exception {

		Vector<String> result = new Vector<String>();
		
		String IAccreditamentoURLString = SiracHelper.getIAccreditamentoServiceURLString(request.getSession().getServletContext());

		IAccreditamentoClientAdapter accr = new IAccreditamentoClientAdapter(IAccreditamentoURLString);
		
		Accreditamento[] accreditations = accr.getAccreditamenti(taxCode, municipalityId);
		if (accreditations != null && accreditations.length > 0) {
			
			for(int index = 0; index < accreditations.length; index++) {
				Accreditamento accreditation = accreditations[index];
				if (accreditation.getQualifica().getIdQualifica().equalsIgnoreCase(CATEGORY_ASSOCIATION_REPRESENTATIVE_CODE) || 
						accreditation.getQualifica().getIdQualifica().equalsIgnoreCase(CATEGORY_ASSOCIATION_AGENT_CODE)) {
					if (!result.contains(accreditation.getProfilo().getCodiceFiscaleIntermediario())) {
						result.add(accreditation.getProfilo().getCodiceFiscaleIntermediario());
					}
				}
			}
			
		}
		
		return result;
		
	}
	
}
