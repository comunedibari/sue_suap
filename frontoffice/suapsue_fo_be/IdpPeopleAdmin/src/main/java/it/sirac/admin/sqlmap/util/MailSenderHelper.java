package it.sirac.admin.sqlmap.util;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class MailSenderHelper
{
  protected static final String PROTOCOL_SMTP = "smtp";
  protected static final String PROTOCOL_SMTPS = "smtps";
  protected static String transport_protocol = "smtp";
  protected static final String SMTP_DEFAULT_PORT = "25";
  protected static String smtpPort = "25";
  
  public static final Session getMailSession(String smtpHost, String smtpPort, boolean useAuth, final String userName, final String password, boolean useSSL, boolean useTLS)
  {
    Properties props = new Properties();
    
    if (useSSL) {
      transport_protocol = "smtps";
    } else {
      transport_protocol = "smtp";
    }
    props.put("mail.transport.protocol", transport_protocol);
    
    props.put("mail." + transport_protocol + ".host", smtpHost);
    if (smtpPort != null) {
      props.put("mail." + transport_protocol + ".port", smtpPort);
    } else {
      props.put("mail." + transport_protocol + ".port", "25");
    }
    if (useTLS) {
      props.put("mail.smtp.starttls.enable", "true");
    }
    Session session = null;
    
    Authenticator auth = null;
    if (useAuth)
    {
      props.put("mail." + transport_protocol + ".auth", "true");
      
      auth = new Authenticator()
      {
        protected PasswordAuthentication getPasswordAuthentication()
        {
          return new PasswordAuthentication(userName, password);
        }
      };
    }
    session = Session.getInstance(props, auth);
    
    return session;
  }
}
