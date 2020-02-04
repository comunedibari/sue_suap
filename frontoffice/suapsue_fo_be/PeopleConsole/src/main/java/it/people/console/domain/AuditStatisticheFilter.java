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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import it.people.console.dto.ReportSettingsDTO;

/**
 * @author Luca Barbieri - Pradac Informatica S.r.l.
 * @created 14/lug/2011 09.41.31
 * 
 */
public class AuditStatisticheFilter extends AbstractBaseBean implements Clearable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6592124005251907345L;
	private String from;
	private String to;

	private char initialLetter;

	private String error;

	private ReportSettingsDTO reportSettings;

	public AuditStatisticheFilter() {
		this.setReportSettings(new ReportSettingsDTO());
		this.from = "";
		this.to = "";
	}

	public AuditStatisticheFilter(char initialLetter) {
		this.setReportSettings(new ReportSettingsDTO());
		this.from = "";
		this.to = "";
		this.initialLetter = initialLetter;
	}

	/***/
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setInitialLetter(char initialLetter) {
		this.initialLetter = initialLetter;
	}

	public void setInitialLetter(String initialLetter) {
		this.initialLetter = 
			new Character(initialLetter.substring(0, 1).toCharArray()[0]);
	}

	public char getInitialLetter() {
		return initialLetter;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @return the reportSettings
	 */
	public final ReportSettingsDTO getReportSettings() {
		return this.reportSettings;
	}

	/**
	 * @param reportSettings
	 *            the reportSettings to set
	 */
	public final void setReportSettings(ReportSettingsDTO reportSettings) {
		this.reportSettings = reportSettings;
	}

	public void setToDate(Calendar calendar) {

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		this.to = formatter.format(calendar.getTime());
	}

	public void setFromDate(Calendar calendar) {

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		this.from = formatter.format(calendar.getTime());
	}

	public Calendar getToDate() {
		return getDate(this.to);
	}

	public Calendar getFromDate() {
		return getDate(this.from);
	}

	protected Calendar getDate(String inputFrom) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date date = (Date) formatter.parse(inputFrom);
			Calendar from = Calendar.getInstance();
			from.setTime(date);
			return from;
		} catch (Exception ex) {
			return null;
		}
	}

	/***
	 * /* (non-Javadoc)
	 * 
	 * @see it.people.console.domain.Clearable#clear()
	 */
	public void clear() {
		this.from = null;
		this.to = null;

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

}
