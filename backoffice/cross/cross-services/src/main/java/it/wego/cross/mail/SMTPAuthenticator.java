/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.mail;

import it.wego.cross.utils.Log;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author giuseppe
 */
public class SMTPAuthenticator extends Authenticator {

    private String username;
    private String password;

    public SMTPAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        Log.EMAIL.info("Using password authenticator with following credentials");
        Log.EMAIL.info("- username: " + username);
        Log.EMAIL.info("- password: " + password);
        PasswordAuthentication passwordAutentication = new PasswordAuthentication(username, password);
        return passwordAutentication;
    }
}
