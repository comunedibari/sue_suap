package it.sirac.admin.sqlmap.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailSender
{
	private static Logger logger = LoggerFactory.getLogger(MailSender.class);
  protected InternetAddress originator = null;
  protected List destinations = null;
  protected String subject = null;
  protected List msgText = null;
  protected String smtphost = null;
  protected static final String PROTOCOL_SMTP = "smtp";
  protected static final String PROTOCOL_SMTPS = "smtps";
  protected String transport_protocol = "smtp";
  protected String smtpPort = "25";
  protected boolean useAuth = false;
  protected String userName = null;
  protected String password = null;
  protected boolean useSSL = false;
  protected boolean useTLS = false;
  protected boolean debug = false;
  
  public MailSender()
  {
    this.destinations = new ArrayList();
    this.msgText = new ArrayList();
  }
  
  public void initialize(Properties props)
    throws Exception
  {
    setOriginator(props.getProperty("mail.originator"));
    addDestination(props.getProperty("mail.destination"), "TO");
    setSubject(props.getProperty("mail.subject"));
    addMsgText(props.getProperty("mail.message"));
    setSMTPhost(props.getProperty("smtp.server.host"));
    setSMTPPort(props.getProperty("smtp.server.port"));
    setUserName(props.getProperty("smtp.server.username"));
    setPassword(props.getProperty("smtp.server.password"));
    setUseAuth(Boolean.valueOf(props.getProperty("smtp.server.useAuth")).booleanValue());
    setUseSSL(Boolean.valueOf(props.getProperty("smtp.server.useSSL")).booleanValue(), Boolean.valueOf(props.getProperty("smtp.server.useTLS")).booleanValue());
    
    setDebug(Boolean.valueOf(props.getProperty("mail.session.debug")).booleanValue());
  }
  
  public void setOriginator(String originator)
    throws Exception
  {
    try
    {
      this.originator = new InternetAddress(originator);
    }
    catch (AddressException e)
    {
      logger.error("MailSender::setOriginator::Exception: " + e.getMessage());
      throw new Exception("MailSender::setOriginator::Exception: " + e.getMessage());
    }
  }
  
  public void addDestination(String destination, String type)
    throws Exception
  {
    HashMap curMap = new HashMap();
    try
    {
      InternetAddress address = new InternetAddress(destination);
      curMap.put("address", address);
      curMap.put("type", type);
      this.destinations.add(curMap);
    }
    catch (AddressException e)
    {
      logger.error("MailSender::addDestination::Exception: " + e.getMessage());
      throw new Exception("MailSender::addDestination::Exception: " + e.getMessage());
    }
  }
  
  public void setSubject(String subject)
  {
    this.subject = subject;
  }
  
  public void addMsgText(String text)
  {
    this.msgText.add(text);
  }
  
  public void setSMTPhost(String smtphost)
  {
    this.smtphost = smtphost;
  }
  
  public void setSMTPPort(String port)
  {
    this.smtpPort = port;
  }
  
  public void setUseSSL(boolean useSSL, boolean useTLS)
  {
    this.useSSL = useSSL;
    this.useTLS = useTLS;
  }
  
  public void setDebug(boolean debug)
  {
    this.debug = debug;
  }
  
  public void setUserName(String userName)
  {
    this.userName = userName;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public void setUseAuth(boolean _useAuth)
  {
    this.useAuth = _useAuth;
  }
  
  public boolean isUseAuth()
  {
    return this.useAuth;
  }
  
  public boolean isUseSSL()
  {
    return this.useSSL;
  }
  
  public boolean isUseTLS()
  {
    return this.useTLS;
  }
  
  public void sendMail()
    throws Exception
  {
    Session session = MailSenderHelper.getMailSession(this.smtphost, this.smtpPort, this.useAuth, this.userName, this.password, this.useSSL, this.useTLS);
    
    Boolean b = new Boolean(this.debug);
    if (this.debug) {
      session.getProperties().put("mail.debug", b.toString());
    }
    session.setDebug(this.debug);
    Properties p = session.getProperties();
    logger.debug("Mail Session Propertes:");
    logger.debug(p.toString());
    try
    {
      Message msg = new MimeMessage(session);
      
      msg.setFrom(this.originator);
      for (int i = 0; i < this.destinations.size(); i++)
      {
        HashMap curMap = (HashMap)this.destinations.get(i);
        String type = (String)curMap.get("type");
        InternetAddress address = (InternetAddress)curMap.get("address");
        logger.info("Invio e-mail a " + address);
        if (type.equalsIgnoreCase("to")) {
          msg.addRecipient(Message.RecipientType.TO, address);
        } else if (type.equalsIgnoreCase("cc")) {
          msg.addRecipient(Message.RecipientType.CC, address);
        } else if (type.equalsIgnoreCase("bcc")) {
          msg.addRecipient(Message.RecipientType.BCC, address);
        }
      }
      msg.setSubject(this.subject);
      

      msg.setSentDate(new Date());
      
      StringBuffer textBuffer = new StringBuffer();
      for (int i = 0; i < this.msgText.size(); i++)
      {
        textBuffer.append((String)this.msgText.get(i));
        textBuffer.append("\n");
      }
      msg.setText(textBuffer.toString());
      
      msg.saveChanges();
      
      Transport tr = session.getTransport();
      tr.connect(this.smtphost, this.userName, this.password);
      tr.sendMessage(msg, msg.getAllRecipients());
      tr.close();
      
      logger.info("Mail sent!");
    }
    catch (MessagingException mex)
    {
      logger.error("MailSender::sendMail::Exception");
      Exception ex = mex;
      do
      {
        if ((ex instanceof SendFailedException))
        {
          SendFailedException sfex = (SendFailedException)ex;
          Address[] invalid = sfex.getInvalidAddresses();
          if (invalid != null)
          {
            logger.error(" ** Invalid Addresses");
            if (invalid != null) {
              for (int i = 0; i < invalid.length; i++) {
                logger.error(" " + invalid[i]);
              }
            }
          }
          Address[] validUnsent = sfex.getValidUnsentAddresses();
          if (validUnsent != null)
          {
            logger.error(" ** ValidUnsent Addresses");
            if (validUnsent != null) {
              for (int i = 0; i < validUnsent.length; i++) {
                logger.error(" " + validUnsent[i]);
              }
            }
          }
          Address[] validSent = sfex.getValidSentAddresses();
          if (validSent != null)
          {
            logger.error(" ** ValidSent Addresses");
            if (validSent != null) {
              for (int i = 0; i < validSent.length; i++) {
                logger.error(" " + validSent[i]);
              }
            }
          }
        }
        System.out.println();
        if ((ex instanceof MessagingException)) {
          ex = ((MessagingException)ex).getNextException();
        } else {
          ex = null;
        }
      } while (ex != null);
      logger.error("", mex);
      throw new Exception("MailSender::sendMail::Exception: " + mex.getMessage());
    }
  }
}
