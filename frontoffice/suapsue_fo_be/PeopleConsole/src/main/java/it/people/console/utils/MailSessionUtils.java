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
package it.people.console.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import it.people.console.config.ConsoleConfiguration;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica - Genova
 * 01/lug/2011 22.03.04
 */
public class MailSessionUtils {

	/**
	 * @param userName
	 * @param password
	 * @return
	 */
	public static Authenticator getAuthenticator( final String userName, final String password ) {
		Authenticator auth = new Authenticator() { 
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName,password);
			} 
		};

		return auth;
	}
	
	/**
	 * @param senderConfiguration
	 * @param isAuthenticationRequired
	 * @return
	 * @throws Exception
	 */
	public static Session getMailTransportSession(ConsoleConfiguration consoleConfiguration) throws Exception {
		
    	Properties properties = getMailTransportConfigurationProperties(consoleConfiguration);
    	
		if ( consoleConfiguration.isMailTransportAuth() ) {
			return Session.getInstance(properties, getAuthenticator(consoleConfiguration.getMailTransportUsername(), 
					consoleConfiguration.getMailTransportPassword()));
		}
		else {
			return Session.getInstance(properties);
		}
		
	}
	
	/**
	 * @param jndiSession
	 * @return
	 * @throws Exception
	 */
	public static Properties getMailTransportConfigurationProperties(ConsoleConfiguration consoleConfiguration) throws Exception {

		String transport_protocol = Constants.Mail.PROTOCOL_SMTP;
		
		Properties props = new Properties();
		
		if ( consoleConfiguration.getMailTransportProtocol() == null )
			throw new Exception( "Parametro obbligatorio mail.transport.protocol non definito nella configurazione." );
		
		if ( consoleConfiguration.getMailTransportHost() == null )
			throw new Exception( "Parametro obbligatorio mail.smtp.host non definito nella configurazione." );

		if ( consoleConfiguration.getMailTransportFrom() == null )
			throw new Exception( "Parametro obbligatorio mail.from non definito nella configurazione." );

		if (consoleConfiguration.isMailTransportUsessl()) {
			transport_protocol = Constants.Mail.PROTOCOL_SMTPS;
		} else {
			transport_protocol = Constants.Mail.PROTOCOL_SMTP;
		}

		props.put("mail.transport.protocol", transport_protocol);
		props.put("mail." + transport_protocol + ".host", consoleConfiguration.getMailTransportHost());

		if (consoleConfiguration.getMailTransportPort() > 0 ) {
			props.put("mail." + transport_protocol + ".port", consoleConfiguration.getMailTransportPort());
		} else {
			props.put("mail." + transport_protocol + ".port", Constants.Mail.SMTP_DEFAULT_PORT);
		}

		if (consoleConfiguration.isMailTransportUsetls()) {
			props.put("mail.smtp.starttls.enable", "true");
		}

		if (consoleConfiguration.isMailTransportAuth()) {
			props.put("mail." + transport_protocol + ".auth", "true");

			props.put("mail.user", consoleConfiguration.getMailTransportUsername());

			props.put("User", consoleConfiguration.getMailTransportUsername());
			props.put("Password", consoleConfiguration.getMailTransportPassword());
			
			if ( consoleConfiguration.getMailTransportUsername() == null || consoleConfiguration.getMailTransportPassword() == null )
				throw new Exception( "Abilitando l'autenticazione ( mail.auth = true|S ) sono obbligatori i parametri username e password non definit nella configurazione." ); 

		}
		
		return props;

	}
	
	
}
