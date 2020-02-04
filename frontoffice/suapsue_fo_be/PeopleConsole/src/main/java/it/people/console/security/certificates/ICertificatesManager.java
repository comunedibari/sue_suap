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
package it.people.console.security.certificates;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 20/giu/2011 19.04.30
 *
 */
public interface ICertificatesManager {

	/**
	 * @param alias
	 * @param password
	 * @param commonName
	 * @param organisationUnit
	 * @param organizaziontName
	 * @param locality
	 * @param stateName
	 * @param country
	 * @param eMail
	 * @param validity
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws InvalidKeySpecException
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 * @throws SignatureException
	 * @throws CertificateException
	 * @throws KeyStoreException
	 * @throws IOException
	 */
	public ByteArrayOutputStream generateCertificate(String alias, String password, String commonName, 
			String organisationUnit, String organizaziontName, String locality, String stateName, 
			String country, String eMail, int validity, String[] communeId) throws NoSuchAlgorithmException, 
			NoSuchProviderException, InvalidKeySpecException, InvalidKeyException, SecurityException, 
			SignatureException, CertificateException, KeyStoreException, IOException;
	
}
