package it.wego.cross.mailservices.util;

import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.search.SearchTerm;
 
public class SentDateSearchTerm extends SearchTerm {
    private Date afterDate;
     
    public SentDateSearchTerm(Date afterDate) {
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