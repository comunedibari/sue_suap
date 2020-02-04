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
package it.people.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class MailSenderHelper {

    protected static final String PROTOCOL_SMTP = "smtp";
    protected static final String PROTOCOL_SMTPS = "smtps";

    protected static String transport_protocol = PROTOCOL_SMTP;

    protected static final String SMTP_DEFAULT_PORT = "25";

    protected static String smtpPort = SMTP_DEFAULT_PORT;

    public static final Session getMailSession(String smtpHost,
	    String smtpPort, boolean useAuth, final String userName,
	    final String password, boolean useSSL, boolean useTLS /*
								   * , boolean
								   * debug
								   */) {
	Properties props = new Properties();

	if (useSSL) {
	    transport_protocol = PROTOCOL_SMTPS;

	} else {
	    transport_protocol = PROTOCOL_SMTP;
	}

	props.put("mail.transport.protocol", transport_protocol);

	props.put("mail." + transport_protocol + ".host", smtpHost);

	if (smtpPort != null) {
	    props.put("mail." + transport_protocol + ".port", smtpPort);
	} else {
	    props.put("mail." + transport_protocol + ".port", SMTP_DEFAULT_PORT);
	}

	if (useTLS) {
	    props.put("mail.smtp.starttls.enable", "true");
	}

	Session session = null;

	Authenticator auth = null;

	if (useAuth) {
	    props.put("mail." + transport_protocol + ".auth", "true");

	    auth = new Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
		    return new PasswordAuthentication(userName, password);
		}
	    };
	    // auth = new MailPasswordAuthenticator(userName, password);
	}
	session = Session.getInstance(props, auth);

	// Boolean b = new Boolean(debug);
	// if (debug)
	// props.put("mail.debug",b.toString());
	//
	// session.setDebug(debug);
	// Properties p = session.getProperties();
	// p.list(System.out);

	return session;

    }

}

class MailPasswordAuthenticator extends Authenticator {
    String user;
    String pw;

    public MailPasswordAuthenticator(String username, String password) {
	super();
	this.user = username;
	this.pw = password;
    }

    public PasswordAuthentication getPasswordAuthentication() {
	return new PasswordAuthentication(user, pw);
    }
}
