package it.sirac.admin.sqlmap.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

class MailPasswordAuthenticator
  extends Authenticator
{
  String user;
  String pw;
  
  public MailPasswordAuthenticator(String username, String password)
  {
    this.user = username;
    this.pw = password;
  }
  
  public PasswordAuthentication getPasswordAuthentication()
  {
    return new PasswordAuthentication(this.user, this.pw);
  }
}
