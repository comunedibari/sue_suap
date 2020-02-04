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
import java.io.StreamCorruptedException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;

import it.people.console.beans.Root;
import it.people.console.security.CryptoUtils;
import it.people.console.security.exceptions.CryptoUtilsException;
import it.people.console.utils.Serializer;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 24/giu/2011 17.50.05
 *
 */
public class RUManager implements IRUManager {

	/* (non-Javadoc)
	 * @see it.people.console.security.ru.IRUManager#updateRootUser(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public final void updateRootUser(String password, String firstName, String lastName, String eMail) throws StreamCorruptedException, 
		InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, CryptoUtilsException, 
		IOException, ClassNotFoundException, IllegalBlockSizeException {
		Root root = getRootUser(password);
		root.setFirstName(firstName);
		root.setLastName(lastName);
		root.seteMail(eMail);
		writeRootUser(password, root);
	}

	/* (non-Javadoc)
	 * @see it.people.console.security.ru.IRUManager#getRootUser(java.lang.String)
	 */
	public final Root getRootUser(String password) throws CryptoUtilsException, StreamCorruptedException, 
		IOException, ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException, 
		InvalidKeySpecException, NoSuchPaddingException {
		Root root = null;
		RULastLogin ruLastLogin = null;
    	CryptoUtils cryptoUtils = new CryptoUtils();
    	
		SealedObject sealedObject = (SealedObject)Serializer.deserialize(new String[] {RUManager.class.getClassLoader().getResource("it/people/console/security/ru").getPath() + "/", "ru"});
		root = (Root)cryptoUtils.getUnsealedObject(password, sealedObject);
		sealedObject = (SealedObject)Serializer.deserialize(new String[] {RUManager.class.getClassLoader().getResource("it/people/console/security/ru").getPath() + "/", "rull"});
		ruLastLogin = (RULastLogin)cryptoUtils.getUnsealedObject(password, sealedObject);
		root.setLastLogin(ruLastLogin.getLastLogin());
		
		return root;
		
	}	
	
	/* (non-Javadoc)
	 * @see it.people.console.security.ru.IRUManager#writeRootUser(java.lang.String, it.people.console.beans.Root)
	 */
	public final void writeRootUser(String password, Root root) throws CryptoUtilsException, InvalidKeyException, 
		NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, IOException {
		CryptoUtils cryptoUtils = new CryptoUtils();

		SealedObject sealedObject = cryptoUtils.getSealedObject(password, root);
		Serializer.serialize(new String[] {RUInit.class.getClassLoader().getResource("it/people/console/security/ru").getPath() + "/", "ru"}, sealedObject);
		RULastLogin ruLastLogin = new RULastLogin();
		ruLastLogin.updateLastLogin();
		SealedObject llSealedObject = cryptoUtils.getSealedObject("zukowski", ruLastLogin);
		Serializer.serialize(new String[] {RUInit.class.getClassLoader().getResource("it/people/console/security/ru").getPath() + "/", "rull"}, llSealedObject);

	}
	
}
