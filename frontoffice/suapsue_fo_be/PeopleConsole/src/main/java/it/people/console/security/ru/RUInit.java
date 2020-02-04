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
package it.people.console.security.ru;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;

import it.people.console.beans.Root;
import it.people.console.security.CryptoUtils;
import it.people.console.security.exceptions.CryptoUtilsException;
import it.people.console.utils.RegExpUtils;
import it.people.console.utils.Serializer;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 24/giu/2011 17.07.54
 *
 */
public class RUInit {

	public static void main(String[] args) throws CryptoUtilsException {
		
		if (args.length == 0) {
			showHelp();
			return;
		}

		CryptoUtils cryptoUtils = new CryptoUtils();
		
		String operazione = args[0];
		
		if (operazione.equalsIgnoreCase("1")) {
			String firstName = args[1];
			String lastName = args[2];
			String eMail = args[3];
			String password = args[4];
			RegExpUtils reu = new RegExpUtils();
			if (!reu.matchEMailPattern(eMail)) {
				System.out.println("Indirizzo e-mail non valido: '" + eMail + "'.");
				return;
			}
									
			try {
				Root root = new Root(firstName, lastName, "root user", eMail, cryptoUtils.getNewCryptedPassword(password));
				RULastLogin ruLastLogin = new RULastLogin();
				SealedObject sealedObject = cryptoUtils.getSealedObject("zukowski", root);
				Serializer.serialize(new String[] {RUInit.class.getClassLoader().getResource("it/people/console/security/ru").getPath() + "/", "ru"}, sealedObject);
				ruLastLogin.updateLastLogin();
				SealedObject llSealedObject = cryptoUtils.getSealedObject("zukowski", ruLastLogin);
				Serializer.serialize(new String[] {RUInit.class.getClassLoader().getResource("it/people/console/security/ru").getPath() + "/", "rull"}, llSealedObject);
			} catch (InvalidClassException e) {
				e.printStackTrace();
			} catch (NotSerializableException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			}
			
		}
		else if (operazione.equalsIgnoreCase("2")) {

			try {
				SealedObject sealedObject = (SealedObject)Serializer.deserialize(new String[] {RUInit.class.getClassLoader().getResource("it/people/console/security/ru").getPath() + "/", "ru"});
				Root root = (Root)cryptoUtils.getUnsealedObject("zukowski", sealedObject);
				showAccountData(root);
			} catch (StreamCorruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			}
			
		}
		else {
			showHelp();
			cryptoUtils = null;
			return;			
		}
		
	}
	
	private static void showHelp() {
		System.out.println();
		System.out.println("---- RUInit Help ----");
		System.out.println();
		System.out.println("Funzionamento:");
		System.out.println();
		System.out.println("\tRUInit <codice operazione> <parametri>");
		System.out.println();
		System.out.println("\tCodice operazione: 1");
		System.out.println("\t\tAggiorna i dati dell'account");
		System.out.println("\t\tParametri: <nome> <cognome> <indirizzo e-mail> <password>");
		System.out.println();
		System.out.println("\tCodice operazione: 2");
		System.out.println("\t\tVisualizza i dati attuali dell'account");
		System.out.println("\t\tParametri: nessuno");
		System.out.println();
	}
	
	private static void showAccountData(Root root) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		System.out.println();
		System.out.println("---- Dati account ----");
		System.out.println();
		System.out.println("\tNome: " + root.getFirstName());
		System.out.println("\tCognome: " + root.getLastName());
		System.out.println("\tDescrizione: " + root.getDescription());
		System.out.println("\tE-mail: " + root.geteMail());
		if (root.getLastLogin() != null) {
			System.out.println("\tUltimo login (dd/mm/yyyy HH:mm:ss): " + simpleDateFormat.format(root.getLastLogin()));
		} else {
			System.out.println("\tUltimo login (dd/mm/yyyy HH:mm:ss): mai eseguito");
		}
		System.out.println();		
	}

}
