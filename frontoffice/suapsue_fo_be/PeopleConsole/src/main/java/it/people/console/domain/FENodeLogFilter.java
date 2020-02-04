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

/**
 * @author Luca Barbieri - Pradac Informatica S.r.l.
 * @created 04/mag/2011 12.41.31
 * 
 */
public class FENodeLogFilter extends AbstractBaseBean implements Clearable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2162724082987646074L;
	private Integer logLevel;
	private Integer serviceId;
    
	private String from;
	private String to;
    
	private String orderBy;
	private String orderType;
	
    private String error;

    public static String ORDER_BY_DATE = "date";
    public static String ORDER_BY_LOG = "log";
    public static String ORDER_BY_SERVICE = "service";
    public static String ORDER_BY_MESSAGE = "message";
    public static String ORDER_ASC = "asc";
    public static String ORDER_DESC = "desc";

	
	public FENodeLogFilter() {

	}

	/***/
	
	public final Integer getLogLevel() {
		return logLevel;
	}

	public final void setLogLevel(Integer logLevel) {
		this.logLevel = logLevel;
	}
	
	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	
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
	
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public void setOrder(String orderBy, String orderType){
		this.orderBy = orderBy;
		this.orderType = orderType;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

	/***/
	
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
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.people.console.domain.Clearable#clear()
	 */
	public void clear() {
		this.logLevel = null;
		this.serviceId = null;
	}

	public void finalize() throws Throwable {
		super.finalize();
	}


}
