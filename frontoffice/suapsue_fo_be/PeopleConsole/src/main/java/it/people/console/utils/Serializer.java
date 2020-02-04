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

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;

import it.people.console.system.MessageSourceAwareClass;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 24/giu/2011 17.18.15
 *
 */
public class Serializer extends MessageSourceAwareClass {

	/**
	 * @param fileProps
	 * @param object
	 * @throws IOException
	 * @throws InvalidClassException
	 * @throws NotSerializableException
	 */
	public static void serialize(String[] fileProps, Object object) throws IOException, 
		InvalidClassException , NotSerializableException {
		String repositoryPath = fileProps[0];
		String path =  repositoryPath + fileProps[1];
		FileOutputStream fileOutput = new FileOutputStream(path.replace("%20", " "));
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutput);
		objectOutputStream.writeObject(object);
		objectOutputStream.close();
	}
	
	/**
	 * @param fileProps
	 * @return
	 * @throws IOException
	 * @throws StreamCorruptedException
	 * @throws ClassNotFoundException
	 */
	public static Object deserialize(String[] fileProps) throws IOException, StreamCorruptedException, 
		ClassNotFoundException {
		String repositoryPath = fileProps[0];
		String path = repositoryPath + fileProps[1];
		FileInputStream fileInput = new FileInputStream(path.replace("%20", " "));
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInput);
		Object object = objectInputStream.readObject();
		objectInputStream.close();
		return object;
	}
	
}
