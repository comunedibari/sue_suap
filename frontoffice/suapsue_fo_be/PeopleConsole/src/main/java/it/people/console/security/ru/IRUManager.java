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

import it.people.console.beans.Root;
import it.people.console.security.exceptions.CryptoUtilsException;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 24/giu/2011 23.15.32
 *
 */
public interface IRUManager {

	/**
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param eMail
	 * @throws StreamCorruptedException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws CryptoUtilsException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws IllegalBlockSizeException
	 */
	public void updateRootUser(String password, String firstName, String lastName, String eMail) throws StreamCorruptedException, 
		InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, CryptoUtilsException, 
		IOException, ClassNotFoundException, IllegalBlockSizeException;

	/**
	 * @param password
	 * @return
	 * @throws CryptoUtilsException
	 * @throws StreamCorruptedException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 */
	public Root getRootUser(String password) throws CryptoUtilsException, StreamCorruptedException, 
		IOException, ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException, 
		InvalidKeySpecException, NoSuchPaddingException;

	/**
	 * @param password
	 * @param root
	 * @throws CryptoUtilsException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws IOException
	 */
	public void writeRootUser(String password, Root root) throws CryptoUtilsException, InvalidKeyException, 
		NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, IOException;
	
}
