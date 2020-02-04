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

import it.people.console.domain.ProcessDeletionBean;
import it.people.console.validation.MessageSourceAwareValidator;


/**
 * @author Andrea Piemontese - Engineering Ingegneria Informatica S.p.A.
 * @created 11/set/2012
 *
 */
@Service
public class ProcessDeletionValidator extends MessageSourceAwareValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return clazz == ProcessDeletionBean.class;
	}
	

	public void validate(Object command, Errors errors) {
		
		ProcessDeletionBean filter = (ProcessDeletionBean) command;

		
		//Check selection of Date Filter type
		if (filter.getDateFilterType() == null) {
			errors.rejectValue("error", "error.processes.dateFilterError"); 
		}
		
		/** From Date validation */
		Calendar fromDate = filter.getFromDate();
		boolean valideDateFrom = true;

		// Check void or not parsable
		if (fromDate == null) {
			errors.rejectValue("error", "error.processes.dateFromError"); 
			valideDateFrom = false;
		} else {
			int fromDay = fromDate.get(Calendar.DAY_OF_MONTH);
			int fromMonth = fromDate.get(Calendar.MONTH);
			int fromYear =  fromDate.get(Calendar.YEAR);
			valideDateFrom = checkDate(errors, "error.processes.dateFromError", fromDay, fromMonth, fromYear);
		}
		// not future date
		if(valideDateFrom){
			if(fromDate.compareTo(Calendar.getInstance()) > 0){
				errors.rejectValue("error", "error.processes.dateFromFutureError");
				valideDateFrom = false;
			}
		}
		
		/** To Date validation */
		Calendar toDate = filter.getToDate();
		boolean valideDateTo = true;
		
		// Check void or not parsable
		if (toDate == null){
			errors.rejectValue("error", "error.processes.dateToError"); 
			valideDateTo = false;
		}
		else {
			 int toDay = toDate.get(Calendar.DAY_OF_MONTH);
			 int toMonth = toDate.get(Calendar.MONTH);
			 int toYear =  toDate.get(Calendar.YEAR);
			 valideDateTo = checkDate(errors, "error.processes.dateToError", toDay, toMonth, toYear);
		}
		
		// not future date
		if(valideDateTo){
			if(toDate.compareTo(Calendar.getInstance()) > 0){
				errors.rejectValue("error", "error.processes.dateToFutureError");
				valideDateTo = false;
			} 
		}
				
		// DateFrom and DateTo order check
		if(valideDateFrom && valideDateTo){
			if(fromDate.compareTo(toDate) > 0)
				errors.rejectValue("error", "error.processes.dateLaterError"); 
		}

		
		//check days
		if (filter.getOlderThanDays() < 0) {
			errors.rejectValue("error", "error.processes.daysFilterError"); 
		}
		
		
		//check if fields are selected
		boolean isSelectedNodes = checkSelected(filter.getSelectedNodes());
		
		
		if (!isSelectedNodes) {
			errors.rejectValue("selectionError", "error.processes.selecteNodesError"); 
		}
		
		boolean isSelectedUsers = checkSelected(filter.getSelectedUsers());

		if (!isSelectedUsers) {
			errors.rejectValue("selectionError", "error.processes.selecteUsersError");
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

	
	/**
	 * Check if selected select Tag
	 * @param errors
	 * @param errorMsg
	 * @param selected
	 * @return
	 */
	private boolean checkSelected(String[] selected) {
		
		if (selected != null) {
			if (selected.length >= 1 && (!selected[0].isEmpty())) {
				return true;
			}
		}
		
		return false;
	}
	

}
