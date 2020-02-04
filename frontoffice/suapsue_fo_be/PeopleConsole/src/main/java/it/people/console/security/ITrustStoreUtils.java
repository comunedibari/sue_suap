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
package it.people.console.security;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.Vector;

import org.bouncycastle.x509.util.StreamParsingException;
import org.springframework.validation.ObjectError;

import it.people.console.security.exceptions.CryptoUtilsException;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 19/giu/2011 12.27.52
 *
 */
public interface ITrustStoreUtils {

	public Vector<ObjectError> isTrustedCertificate(String p12FileName, String p12CertificatePassword, 
			String requestAttribute) throws CertificateException, KeyStoreException, 
			NoSuchAlgorithmException, IOException;
	
	public Vector<ObjectError> isTrustedCertificate(InputStream p12CertificateInputStream, 
			String p12CertificatePassword, String requestAttribute) throws CertificateException, 
			KeyStoreException, NoSuchAlgorithmException, IOException;

	public boolean isValidP12Password(String p12FileName, String p12Password) throws CryptoUtilsException;

	public void addCertificatetoTrustStore(String trustStorePassword, String alias, Certificate certificate) 
		throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, URISyntaxException;

	public Vector<String> certificateCommuneRegistrationVector(String alias) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, StreamParsingException;
	
	public String certificateCommuneRegistration(String alias) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, StreamParsingException;
	
	public Date certificateExpireTime(String alias) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException;
	
}
