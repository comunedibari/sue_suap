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
 * @author Andrea Piemontese - Engineering Ingegneria Informatica S.p.A.
 * @created 20/lug/2012
 *
 */
public class MonitoringIndicator extends AbstractBaseBean implements Clearable {
	
	
	private static final long serialVersionUID = 1L;
	
	
	//Last selected values Date
	private String lastFrom; 
	private String lastTo; 
	
	private String[] lastSelectedEnti;
	private String[] lastSelectedAttivita;
	
	
	//Date
	private String from; 
	private String to; 
	
	private String[] selectedEnti;
	private String[] selectedAttivita;
    
	private String error;
	
	//Office Hours for query
	private java.util.Date officeMorningOpeningTime;
	private java.util.Date officeMorningClosingTime;
	
	private java.util.Date officeAfternoonOpeningTime;
	private java.util.Date officeAfternoonClosingTime;
	
	public MonitoringIndicator() {
		//init
		setFromDate(Calendar.getInstance());
		setToDate(Calendar.getInstance());
		
		setLastFrom(from);
		setLastTo(to);
		
		setSelectedEnti(new String[] {" "});
		setSelectedAttivita(new String[] {" "});
		
		setLastSelectedEnti(new String[] {" "});
		setLastSelectedAttivita(new String[] {" "});
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

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}


    public void setToDate(Calendar calendar) {

    	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); 
    	this.to = formatter.format(calendar.getTime()); 
    }

    public void setFromDate(Calendar calendar) {
    	
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); 
		this.from = formatter.format(calendar.getTime()); 
    }
    
    /**
	 * @return the selectedEnti
	 */
	public String[] getSelectedEnti() {
		return selectedEnti;
	}
	/**
	 * @param selectedEnti the selectedEnti to set
	 */
	public void setSelectedEnti(String[] selectedEnti) {
		this.selectedEnti = selectedEnti;
	}
	
	public Calendar getToDate() {
    	return getDate(this.to);
    }
    
    public Calendar getFromDate() {
    	return getDate(this.from);
    }
    
	public Calendar getLastToDate() {
    	return getDate(this.lastTo);
    }
    
    public Calendar getLastFromDate() {
    	return getDate(this.lastFrom);
    }
    
    
        
    protected Calendar getDate(String inputFrom) {
    	try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date date = (Date) formatter.parse(inputFrom);
			Calendar from = Calendar.getInstance();
			from.setTime(date);
    		return from;
    	}
    	catch(Exception ex) {
    		return null;
    	}
    }
    
    /**
	 * @return the selectedSoluzioni
	 */
	public String[] getSelectedAttivita() {
		return selectedAttivita;
	}
	/**
	 * @param selectedSoluzioni the selectedSoluzioni to set
	 */
	public void setSelectedAttivita(String[] selectedAttivita) {
		this.selectedAttivita = selectedAttivita;
	}
	/**
	 * @return the officeMorningOpeningTime
	 */
	public java.util.Date getOfficeMorningOpeningTime() {
		return officeMorningOpeningTime;
	}


	/**
	 * @param officeMorningOpeningTime the officeMorningOpeningTime to set
	 */
	public void setOfficeMorningOpeningTime(java.util.Date officeMorningOpeningTime) {
		this.officeMorningOpeningTime = officeMorningOpeningTime;
	}


	/**
	 * @return the officeMorningClosingTime
	 */
	public java.util.Date getOfficeMorningClosingTime() {
		return officeMorningClosingTime;
	}


	/**
	 * @param officeMorningClosingTime the officeMorningClosingTime to set
	 */
	public void setOfficeMorningClosingTime(java.util.Date officeMorningClosingTime) {
		this.officeMorningClosingTime = officeMorningClosingTime;
	}


	/**
	 * @return the officeAfternoonOpeningTime
	 */
	public java.util.Date getOfficeAfternoonOpeningTime() {
		return officeAfternoonOpeningTime;
	}


	/**
	 * @param officeAfternoonOpeningTime the officeAfternoonOpeningTime to set
	 */
	public void setOfficeAfternoonOpeningTime(
			java.util.Date officeAfternoonOpeningTime) {
		this.officeAfternoonOpeningTime = officeAfternoonOpeningTime;
	}


	/**
	 * @return the officeAfternoonClosingTime
	 */
	public java.util.Date getOfficeAfternoonClosingTime() {
		return officeAfternoonClosingTime;
	}


	/**
	 * @param officeAfternoonClosingTime the officeAfternoonClosingTime to set
	 */
	public void setOfficeAfternoonClosingTime(
			java.util.Date officeAfternoonClosingTime) {
		this.officeAfternoonClosingTime = officeAfternoonClosingTime;
	}


	/**
	 * @return the lastFrom
	 */
	public String getLastFrom() {
		return lastFrom;
	}




	/**
	 * @param lastFrom the lastFrom to set
	 */
	public void setLastFrom(String lastFrom) {
		this.lastFrom = lastFrom;
	}




	/**
	 * @return the lastTo
	 */
	public String getLastTo() {
		return lastTo;
	}




	/**
	 * @param lastTo the lastTo to set
	 */
	public void setLastTo(String lastTo) {
		this.lastTo = lastTo;
	}




	/**
	 * @return the lastSelectedEnti
	 */
	public String[] getLastSelectedEnti() {
		return lastSelectedEnti;
	}




	/**
	 * @param lastSelectedEnti the lastSelectedEnti to set
	 */
	public void setLastSelectedEnti(String[] lastSelectedEnti) {
		this.lastSelectedEnti = lastSelectedEnti;
	}




	/**
	 * @return the lastSelectedAttivita
	 */
	public String[] getLastSelectedAttivita() {
		return lastSelectedAttivita;
	}




	/**
	 * @param lastSelectedAttivita the lastSelectedAttivita to set
	 */
	public void setLastSelectedAttivita(String[] lastSelectedAttivita) {
		this.lastSelectedAttivita = lastSelectedAttivita;
	}




	/***
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.people.console.domain.Clearable#clear()
	 */
	public void clear() {
		this.setSelectedEnti(null);
		this.setSelectedAttivita(null);
	    this.setFrom(null);
	    this.setTo(null);
	    this.setOfficeAfternoonClosingTime(null);
	    this.setOfficeAfternoonOpeningTime(null);
	    this.setOfficeMorningOpeningTime(null);
	    this.setOfficeMorningClosingTime(null);
	    
	    //Clear last correct selection
	    this.setLastFrom(null);
	    this.setLastTo(null);
	    this.setLastSelectedAttivita(null);
	    this.setLastSelectedEnti(null);
	    
	}

	public void finalize() throws Throwable {
		super.finalize();
	}

}
