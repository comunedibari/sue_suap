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
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility;

import it.people.process.AbstractPplProcess;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 * Oct 25, 2013
 *
 */
public class PplUserUtils {

	/**
	 * @param process
	 * @return
	 */
	public static boolean isAmministratorePeople(AbstractPplProcess process) {
		
		return process.getContext().getUser().isPeopleAdmin();
		
	}

	/**
	 * @param process
	 * @return
	 */
	public static boolean isAmministratoreBookmark(AbstractPplProcess process) {
		
		Boolean buffer = (Boolean)process.getContext().getUser().getExtendedAttribute(Costant.PplUser.AMMINISTRATORE_BOOKMARK);
		if (buffer != null) {
			return buffer.booleanValue();
		} else {
			return false;
		}
		
	}

	/**
	 * @param process
	 * @return
	 */
	public static boolean isAmministratoreProcedimentoUnico(AbstractPplProcess process) {
		
		Boolean buffer = (Boolean)process.getContext().getUser().getExtendedAttribute(Costant.PplUser.AMMINISTRATORE_PROCEDIMENTO_UNICO);
		if (buffer != null) {
			return buffer.booleanValue();
		} else {
			return false;
		}
		
	}

	/**
	 * @param process
	 * @return
	 */
	public static boolean isAmministratoreStampe(AbstractPplProcess process) {
		
		Boolean buffer = (Boolean)process.getContext().getUser().getExtendedAttribute(Costant.PplUser.AMMINISTRATORE_STAMPE);
		if (buffer != null) {
			return buffer.booleanValue();
		} else {
			return false;
		}
		
	}
	
}
