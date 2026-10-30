package it.wego.cross.mailservices.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.RecipientStringTerm;
import javax.mail.search.SearchTerm;

public class FieldSearchTerm extends SearchTerm {
	private static final long serialVersionUID = 1L;
	public static final int BEFORE_DAYS = 10;
	private String fromEmail;
	private Address[] toEmail;

	private Date afterDate; 

	public boolean match(Message message) {
		try {
			Address[] fromAddress = message.getFrom();
			if (fromAddress != null && fromAddress.length > 0) {
				if (fromAddress[0].toString().contains(fromEmail)) {
					return true;
				}
			}
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}

		return false;
	}

	public boolean matchTO(Message message, String toSend) {
		Address[] toAddress;
		try {
			toAddress = message.getRecipients(RecipientType.TO);
			if (toAddress != null && toAddress.length > 0) {
				if (toAddress[0].toString().contains(toSend)) {
					return true;
				}
			}
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public Message[] matchAfterDate(com.sun.mail.imap.IMAPFolder inbox) {
		Message messages[] = null;
		try {
			//Date today = new Date();               
			SimpleDateFormat formattedDate = new SimpleDateFormat("yyyyMMdd");            
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, (BEFORE_DAYS*-1));  // number of days to add      
			String beforeTenDays = (String)(formattedDate.format(c.getTime()));
			System.out.println("FieldSearchTerm::matchAfterDate date is " + beforeTenDays);			
			
			SearchTerm term = null;

			Calendar cal = null;
			cal = Calendar.getInstance();
			Date minDate = new Date(cal.getTimeInMillis());   //get today date

			cal.add(Calendar.DAY_OF_MONTH, (BEFORE_DAYS*-1));              
			Date maxDate = new Date(cal.getTimeInMillis());   
			ReceivedDateTerm minDateTerm = new ReceivedDateTerm(ComparisonTerm.GE, minDate);
			ReceivedDateTerm maxDateTerm = new ReceivedDateTerm(ComparisonTerm.LE, maxDate);

			term = new AndTerm(term, minDateTerm);            //concat the search terms
			term = new AndTerm(term, maxDateTerm);

			//Message messages[] = folderInbox.search(term);    //search on the imap server
			messages = inbox.search(term); 
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return messages;
	}
	public SearchTerm to(final String toAddress) {
		final SearchTerm toTerm = new RecipientStringTerm(RecipientType.TO, toAddress);
		return toTerm;
	}

	public SearchTerm cc(final String ccAddress) {
		final SearchTerm ccTerm = new RecipientStringTerm(RecipientType.CC, ccAddress);
		//concat(toTerm);
		return ccTerm;
	}

	public boolean matchTo(Message message) {
		try {
			Address[] to = new Address[0];  
			to = this.toEmail;
			
			if (message.getRecipients(RecipientType.TO).equals(to)) {
				return true;
			} 
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @return the toEmail
	 */
	public Address[] getToEmail() {
		return toEmail;
	}

	/**
	 * @param toEmail the toEmail to set
	 */
	public void setToEmail(Address[] toEmail) {
		this.toEmail = toEmail;
	}

	/**
	 * @return the fromEmail
	 */
	public String getFromEmail() {
		return fromEmail;
	}

	/**
	 * @param fromEmail the fromEmail to set
	 */
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}



	/**
	 * @return the afterDate
	 */
	public Date getAfterDate() {
		return afterDate;
	}



	/**
	 * @param afterDate the afterDate to set
	 */
	public void setAfterDate(Date afterDate) {
		this.afterDate = afterDate;
	}
	
	public static void main(String[] args) {    	
	
		SimpleDateFormat formattedDate = new SimpleDateFormat("yyyyMMdd");            
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, (BEFORE_DAYS*-1));  // number of days to add      
		String beforeTenDays = (String)(formattedDate.format(c.getTime()));
		System.out.println("matchAfterDate date is " + beforeTenDays);
		
	}
}