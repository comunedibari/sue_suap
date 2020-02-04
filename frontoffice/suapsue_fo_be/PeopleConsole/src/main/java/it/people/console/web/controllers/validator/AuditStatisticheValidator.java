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

import it.people.console.domain.AuditStatisticheFilter;
import it.people.console.validation.MessageSourceAwareValidator;


/**
 * @author Luca Barbieri - Pradac Informatica S.r.l.
 * @created 14/lug/2011 12.03.00
 *
 */
public class AuditStatisticheValidator extends MessageSourceAwareValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return clazz == AuditStatisticheFilter.class;
	}
	

	public void validate(Object command, Errors errors) {
		
		AuditStatisticheFilter filter = (AuditStatisticheFilter) command;
		
		/** From */
		
		Calendar fromDate = filter.getFromDate();
		String errorMsg = "error.statistiche.dateFromError";
		boolean valideDateFrom = true;
		
		// campi vuoti 
//		boolean emptyDateFrom = filter.getFromDay().equals("")
//			&& filter.getFromMonth().equals("")
//			&& filter.getFromYear().equals("");
		boolean emptyDateFrom = filter.getFrom().equals("");
		
		//caratteri non numerici 
		if (fromDate == null) {
			if (!emptyDateFrom) {
			
				errors.rejectValue("error", errorMsg); 
				valideDateFrom = false;
			}
		} else {
			// data corretta
//			String fromDay = filter.getFromDay();
//			String fromMonth = filter.getFromMonth();
//			String fromYear = filter.getFromYear();
//			valideDateFrom = checkDate(errors, errorMsg, fromDay, fromMonth, fromYear);
		}
		// data non futura e non vuota
		if(valideDateFrom && !emptyDateFrom){
			if(fromDate.compareTo(Calendar.getInstance())>0){
				errors.rejectValue("error", errorMsg);
				valideDateFrom=false;
			}
		}
		
		
		/** To */
		
		Calendar toDate = filter.getToDate();
		errorMsg = "error.statistiche.dateToError";
		boolean valideDateTo = true;
		
		// campi vuoti 
//		boolean emptyDateTo = filter.getToDay().equals("")
//			&& filter.getToMonth().equals("")
//			&& filter.getToYear().equals("");
		boolean emptyDateTo = filter.getTo().equals("");
		
		// caratteri non numerici 
		if (toDate == null){
			if (!emptyDateTo) {
			errors.rejectValue("error", errorMsg); 
			valideDateTo = false;
			}
		}
		else {
			// data corretta
//			String toDay = filter.getToDay();
//			String toMonth = filter.getToMonth();
//			String toYear = filter.getToYear();
//			valideDateTo = checkDate(errors, errorMsg, toDay, toMonth, toYear);
		}
		// data non futura e non vuota
//		if(valideDateTo && !emptyDateTo){
//			if(toDate.compareTo(Calendar.getInstance())>0){
//				errors.rejectValue("error", "error.statistiche.dateToFutureError");
//				valideDateTo=false;
//			} 
//		}
				
		
		// DateFrom non successiva a DateTo e non vuota
		if(valideDateFrom&&valideDateTo && !(emptyDateFrom||emptyDateTo)){
			if(fromDate.compareTo(toDate)>0)
				errors.rejectValue("error", "error.statistiche.dateLaterError"); 
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
