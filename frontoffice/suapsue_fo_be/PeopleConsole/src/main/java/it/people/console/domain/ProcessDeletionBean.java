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
 * This model bean conatains data for processes to delete (a.k.a. "Pratiche" to delete) 
 * 
 * @author Andrea Piemontese - Engineering Ingegneria Informatica S.p.A.
 * @created 12/set/2012 11:08:38
 *
 */
public class ProcessDeletionBean extends AbstractBaseBean implements Clearable {
	
	private static final long serialVersionUID = -3332331459560661810L;

	private String error;
	private String selectionError;
	
	//Date filters
	private Integer dateFilterType;
	
	private String from; 
	private String to;
	private int olderThanDays;
	
	//Nodes (enti) filters
	private String[] selectedNodes;
	
	//Users filters
	private String[] selectedUsers;


	//Type filters
	private boolean onlyNotSubmittable;
	private boolean onlyPending;
	private boolean onlySubmitted;
	
	
	public ProcessDeletionBean() {
		//init
		setFromDate(Calendar.getInstance());
		setToDate(Calendar.getInstance());
		
		setSelectedNodes(new String[] {""});
		setSelectedUsers(new String[] {""});
	}

	//Copy constructor
	public ProcessDeletionBean(ProcessDeletionBean other)  {
		
		this.error = other.error;
		this.olderThanDays = other.olderThanDays;
		this.onlyNotSubmittable = other.onlyNotSubmittable;
		this.onlyPending = other.onlyPending;
		this.onlySubmitted = other.onlySubmitted;
		this.selectedNodes = other.selectedNodes.clone();
		this.selectedUsers = other.selectedUsers.clone();
		this.from = other.from;
		this.to = other.to;
		this.dateFilterType = other.dateFilterType;
		
		//Copy pagedListHolder
		this.setPagedListHolders(other.getPagedListHolders());
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


	/**
	 * @return the selectedUsers
	 */
	public String[] getSelectedUsers() {
		return selectedUsers;
	}

	/**
	 * @param selectedUsers the selectedUsers to set
	 */
	public void setSelectedUsers(String[] selectedUsers) {
		this.selectedUsers = selectedUsers;
	}

	/**
	 * @return the olderThanDays
	 */
	public int getOlderThanDays() {
		return olderThanDays;
	}

	/**
	 * @param olderThanDays the olderThanDays to set
	 */
	public void setOlderThanDays(int olderThanDays) {
		this.olderThanDays = olderThanDays;
	}

	/**
	 * @return the onlyNotSubmittable
	 */
	public boolean isOnlyNotSubmittable() {
		return onlyNotSubmittable;
	}

	/**
	 * @param onlyNotSubmittable the onlyNotSubmittable to set
	 */
	public void setOnlyNotSubmittable(boolean onlyNotSubmittable) {
		this.onlyNotSubmittable = onlyNotSubmittable;
	}

	/**
	 * @return the onlyPending
	 */
	public boolean isOnlyPending() {
		return onlyPending;
	}

	/**
	 * @param onlyPending the onlyPending to set
	 */
	public void setOnlyPending(boolean onlyPending) {
		this.onlyPending = onlyPending;
	}

	/**
	 * @return the onlySubmitted
	 */
	public boolean isOnlySubmitted() {
		return onlySubmitted;
	}

	/**
	 * @param onlySubmitted the onlySubmitted to set
	 */
	public void setOnlySubmitted(boolean onlySubmitted) {
		this.onlySubmitted = onlySubmitted;
	}

	public void setError(String error) {
		this.error = error;
	}


    /**
	 * @return the selectionError
	 */
	public String getSelectionError() {
		return selectionError;
	}

	/**
	 * @param selectionError the selectionError to set
	 */
	public void setSelectionError(String selectionError) {
		this.selectionError = selectionError;
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
	public String[] getSelectedNodes() {
		return selectedNodes;
	}
	/**
	 * @param selectedEnti the selectedEnti to set
	 */
	public void setSelectedNodes(String[] selectedNodes) {
		this.selectedNodes = selectedNodes;
	}
	
	/**
	 * @return the dateFilterType
	 */
	public Integer getDateFilterType() {
		return dateFilterType;
	}

	/**
	 * @param dateFilterType the dateFilterType to set
	 */
	public void setDateFilterType(Integer dateFilterType) {
		this.dateFilterType = dateFilterType;
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
    	}
    	catch(Exception ex) {
    		return null;
    	}
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.people.console.domain.Clearable#clear()
	 */
	public void clear() {
		this.setSelectedNodes(null);
		this.setSelectedUsers(null);
		this.setError(null);
	    this.setFrom(null);
	    this.setTo(null);
	    this.setDateFilterType(null);

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

}
