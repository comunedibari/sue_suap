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
package it.people.console.web.controllers.validator;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.people.console.domain.AuditConversationFilter;
import it.people.console.validation.MessageSourceAwareValidator;


/**
 * @author Luca Barbieri - Pradac Informatica S.r.l.
 * @created 09/mag/2011 10.23.00
 *
 */
public class FEViewAuditConversationsValidator extends MessageSourceAwareValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return clazz == AuditConversationFilter.class;
	}
	

	public void validate(Object command, Errors errors) {
		
		AuditConversationFilter filter = (AuditConversationFilter) command;

		/** From */
		Calendar fromDate = filter.getFromDate();
		String errorMsg = "error.viewAuditConversations.dateFromError";
		boolean valideDateFrom = true;
		
		// campi vuoti e caratteri non numerici 
		if (fromDate == null) {
			errors.rejectValue("error", errorMsg); 
			valideDateFrom = false;
		} else {
			// data corretta
//			String fromDay = filter.getFromDay();
//			String fromMonth = filter.getFromMonth();
//			String fromYear = filter.getFromYear();
//			valideDateFrom = checkDate(errors, errorMsg, fromDay, fromMonth, fromYear);
		}
		// data non futura 
		if(valideDateFrom){
			if(fromDate.compareTo(Calendar.getInstance())>0){
				errors.rejectValue("error", errorMsg);
				valideDateFrom=false;
			}
		}
		
		
		/** To */
		Calendar toDate = filter.getToDate();
		errorMsg = "error.viewAuditConversations.dateToError";
		boolean valideDateTo = true;
		
		// campi vuoti e caratteri non numerici 
		if (toDate == null){
			errors.rejectValue("error", errorMsg); 
			valideDateTo = false;
		}
		else {
			// data corretta
//			String toDay = filter.getToDay();
//			String toMonth = filter.getToMonth();
//			String toYear = filter.getToYear();
//			valideDateTo = checkDate(errors, errorMsg, toDay, toMonth, toYear);
		}
		// data non futura 
//		if(valideDateTo){
//			if(toDate.compareTo(Calendar.getInstance())>0){
//				errors.rejectValue("error", "error.viewAuditConversations.dateToFutureError");
//				valideDateTo=false;
//			} 
//		}
				
		
		// DateFrom non successiva a DateTo
		if(valideDateFrom&&valideDateTo){
			if(fromDate.compareTo(toDate)>0)
				errors.rejectValue("error", "error.viewAuditConversations.dateLaterError"); 
		}

	}


	/**
	 * @param errors
	 * @param day
	 * @param month
	 * @param year
	 * @return verifica che la data sia corretta
	 */
	private boolean checkDate(Errors errors, String errorMsg, String day, String month, String year) {
		try {
			int intFromDay = Integer.parseInt(day);
			int intFromMonth = Integer.parseInt(month);
			int intFromYear = Integer.parseInt(year);

			/** controllo sui mesi */
			if (intFromMonth < 1 || intFromMonth > 12) {
				errors.rejectValue("error", errorMsg);
				return false;
			} else {
				/** controllo sui giorni */
				Calendar cal = new GregorianCalendar(intFromYear, intFromMonth-1, 1);
				
				@SuppressWarnings("static-access")
				int maxDays = cal.getActualMaximum(cal.DAY_OF_MONTH);
				
				if (intFromDay < 1 || intFromDay > maxDays) {
					errors.rejectValue("error", errorMsg);
					return false;
				}

			}
		} catch (Exception e) {
			errors.rejectValue("error", errorMsg);
			return false;
		}
		
		return true;
	}

}
