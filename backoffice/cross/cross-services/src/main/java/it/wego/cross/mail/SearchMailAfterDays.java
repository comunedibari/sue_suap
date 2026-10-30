package it.wego.cross.mail;

import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.search.SearchTerm;

public class SearchMailAfterDays extends SearchTerm {
 	private static final long serialVersionUID = 1L;
	private Date afterDate;
     
    public SearchMailAfterDays(Date afterDate) {
        this.afterDate = afterDate;
    }
     
    @Override
    public boolean match(Message message) {
        try {
            if (message.getSentDate().after(afterDate)) {
                return true;
            }
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return false;
    }
 
}