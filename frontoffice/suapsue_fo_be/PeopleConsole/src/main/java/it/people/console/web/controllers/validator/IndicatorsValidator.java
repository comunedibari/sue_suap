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

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.people.console.domain.MonitoringIndicator;
import it.people.console.validation.MessageSourceAwareValidator;


/**
 * @author Andrea Piemontese - Engineering Ingegneria Informatica S.p.A.
 * @created 19/lug/2012
 *
 */
@Service
public class IndicatorsValidator extends MessageSourceAwareValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return clazz == MonitoringIndicator.class;
	}
	

	public void validate(Object command, Errors errors) {
		
		MonitoringIndicator filter = (MonitoringIndicator) command;

		/** From Date validation */
		Calendar fromDate = filter.getFromDate();
		boolean valideDateFrom = true;

		// campi vuoti e caratteri non numerici 
		if (fromDate == null) {
			errors.rejectValue("error", "error.monitoring.dateFromError"); 
			valideDateFrom = false;
		} else {
			// data corretta
			 int fromDay = fromDate.get(Calendar.DAY_OF_MONTH);
			 int fromMonth = fromDate.get(Calendar.MONTH);
			 int fromYear =  fromDate.get(Calendar.YEAR);
			 valideDateFrom = checkDate(errors, "error.monitoring.dateFromError", fromDay, fromMonth, fromYear);
		}
		// data non futura 
		if(valideDateFrom){
			if(fromDate.compareTo(Calendar.getInstance()) > 0){
				errors.rejectValue("error", "error.monitoring.dateFromFutureError");
				valideDateFrom=false;
			}
		}
		
		/** To Date validation */
		Calendar toDate = filter.getToDate();
		boolean valideDateTo = true;
		
		// Campi vuoti e caratteri non numerici 
		if (toDate == null){
			errors.rejectValue("error", "error.monitoring.dateToError"); 
			valideDateTo = false;
		}
		else {
			//data corretta
			 int toDay = toDate.get(Calendar.DAY_OF_MONTH);
			 int toMonth = toDate.get(Calendar.MONTH);
			 int toYear =  toDate.get(Calendar.YEAR);
			 valideDateTo = checkDate(errors, "error.monitoring.dateToError", toDay, toMonth, toYear);
		}
		
		// data non futura 
		if(valideDateTo){
			if(toDate.compareTo(Calendar.getInstance()) > 0){
				errors.rejectValue("error", "error.monitoring.dateToFutureError");
				valideDateTo = false;
			} 
		}
				
		// DateFrom non successiva a DateTo
		if(valideDateFrom&&valideDateTo){
			if(fromDate.compareTo(toDate) > 0)
				errors.rejectValue("error", "error.monitoring.dateLaterError"); 
		}

	}


	/**
	 * @param errors
	 * @param day
	 * @param month
	 * @param year
	 * @return verifica che la data sia corretta
	 */
	private boolean checkDate(Errors errors, String errorMsg, int day, int month, int year) {
		try {
			
			// int intFromDay = Integer.parseInt(day);
			// int intFromMonth = Integer.parseInt(month);
			// int intFromYear = Integer.parseInt(year);

			/** controlo anni (4 cifre) **/
			if (!(Integer.valueOf(year).toString().length() == 4)) {
				errors.rejectValue("error", errorMsg);
				return false;
			}
			
			/** controllo sui mesi */
			if (month < 0 || month > 11) {
				errors.rejectValue("error", errorMsg);
				return false;
			} else {
				/** controllo sui giorni */
				Calendar cal = new GregorianCalendar(year, month, 1);
				
				@SuppressWarnings("static-access")
				int maxDays = cal.getActualMaximum(cal.DAY_OF_MONTH);
				
				if (day < 1 || day > maxDays) {
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
