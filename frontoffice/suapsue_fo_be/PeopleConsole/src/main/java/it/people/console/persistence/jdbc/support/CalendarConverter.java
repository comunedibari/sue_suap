package it.people.console.persistence.jdbc.support;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author gguidi - Jun 25, 2013
 *
 */
public class CalendarConverter implements Decodable {
	
	private DateFormat dateFormat;
	
	
	/**
	 * Applica la formattazione standard: <code>dd/MM/yyyy HH:mm:ss</code>
	 * @param dateFormat
	 */
	public CalendarConverter() {
		this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}

	/**
	 * @param dateFormat
	 */
	public CalendarConverter(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	/**
	 * Passare la stringa di formattazione, ie: <code>dd/MM/yyyy HH:mm:ss</code>
	 * @param dateFormat
	 */
	public CalendarConverter(String dateFormat) {
		this.dateFormat = new SimpleDateFormat(dateFormat);
	}
	
	public DateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.jdbc.support.Decodable#decode(java.lang.Object)
	 */
	@Override
	public String decode(Object value) {
		if (value == null
				|| !(value instanceof java.util.Calendar || value instanceof java.util.GregorianCalendar)
				) {
			return " --- ";
		}
		return dateFormat.format(((java.util.Calendar)value).getTime());
	}

}
