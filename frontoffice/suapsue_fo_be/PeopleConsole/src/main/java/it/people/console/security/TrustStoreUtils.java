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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.provider.X509CertParser;
import org.bouncycastle.jce.provider.X509CertificateObject;
import org.bouncycastle.x509.util.StreamParsingException;
import org.springframework.context.NoSuchMessageException;
import org.springframework.validation.ObjectError;

import it.people.console.security.exceptions.CryptoUtilsException;
import it.people.console.security.exceptions.TrustStoreException;
import it.people.console.system.MessageSourceAwareClass;
import it.people.console.utils.StringUtils;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 19/giu/2011 12.26.30
 *
 */
public class TrustStoreUtils extends MessageSourceAwareClass implements ITrustStoreUtils {

	private static final String TRUST_STORE_PASSWORD = "jfdk43ufj56gf";
	private static final String TRUST_STORE_FILE_NAME = "it/people/console/security/stores/people_console_truststore";
	private static final String TRUST_STORE_FILE_NAME_WRITABLE = "people_console_truststore";
	private static final String TRUST_STORE_TYPE = "JCEKS";

	private static final String EXPIRED_CERTIFICATE_MESSAGE_KEY = "error.certificate.expired";
	private static final String UNTRUSTED_CERTIFICATE_MESSAGE_KEY = "error.certificate.untrusted";
	
	/* (non-Javadoc)
	 * @see it.people.console.security.ITrustStoreUtils#isTrustedCertificate(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Vector<ObjectError> isTrustedCertificate(String p12FileName, String p12CertificatePassword, 
			String requestAttribute) throws CertificateException, KeyStoreException, NoSuchAlgorithmException, IOException {

		File file = new File(p12FileName);
		InputStream p12CertificateInputStream = new FileInputStream(file);
		return this.isTrustedCertificate(p12CertificateInputStream, p12CertificatePassword, requestAttribute);
		
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.security.ITrustStoreUtils#isTrustedCertificate(java.io.InputStream, java.lang.String, java.lang.String)
	 */
	public Vector<ObjectError> isTrustedCertificate(InputStream p12CertificateInputStream, 
			String p12CertificatePassword, String requestAttribute) throws CertificateException, KeyStoreException, NoSuchAlgorithmException, IOException {
		
		Vector<ObjectError> result = new Vector<ObjectError>();
		
		Certificate certificate = getCertificatefromP12(p12CertificateInputStream, p12CertificatePassword);
		X509Certificate x509Certificate = (X509Certificate)certificate;
		String certificateAliasfromTrustStore = this.getTrustStore().getCertificateAlias(certificate);
		if (StringUtils.isEmptyString(certificateAliasfromTrustStore)) {
			String errorMessage = "Certificate is untrusted.";
			try {
				errorMessage = this.getProperty(UNTRUSTED_CERTIFICATE_MESSAGE_KEY);
			} catch(NoSuchMessageException nsme) {
				
			}
			result.add(new ObjectError(requestAttribute, 
					errorMessage));
		}
		
		boolean certificateValidityVerified = true;

		try {
			x509Certificate.checkValidity();
		} catch (CertificateExpiredException cee) {
			certificateValidityVerified = false;
		} catch (CertificateNotYetValidException cnyve) {
			certificateValidityVerified = false;
		}
		
		if (!certificateValidityVerified) {
			String errorMessage = "Certificate is expired.";
			try {
				errorMessage = this.getProperty(EXPIRED_CERTIFICATE_MESSAGE_KEY);
			} catch(NoSuchMessageException nsme) {
				
			}
			result.add(new ObjectError(requestAttribute, 
					errorMessage));
		}
		
		return result;
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.security.ITrustStoreUtils#isValidP12Password(java.lang.String, java.lang.String)
	 */
	public boolean isValidP12Password(String p12FileName, String p12Password) throws CryptoUtilsException {

		boolean result = false;
		
		File file = new File(p12FileName);
		FileInputStream p12InputStream = null;
		try {
			p12InputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new CryptoUtilsException(e);
		}

		char[] password = p12Password.toCharArray();
		KeyStore keyStore = null;
		try {
			keyStore = KeyStore.getInstance("PKCS12");
		} catch (KeyStoreException e) {
			throw new CryptoUtilsException(e);
		}
		
		try {
			keyStore.load(p12InputStream, password);
			result = true;
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoUtilsException(e);
		} catch (CertificateException e) {
			throw new CryptoUtilsException(e);
		} catch (IOException e) {
			throw new CryptoUtilsException(e);
		}
		try {
			if (p12InputStream != null) {
				p12InputStream.close();
			}
		} catch (IOException e) {
			throw new CryptoUtilsException(e);
		}
		return result;
		
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.security.ITrustStoreUtils#addCertificatetoTrustStore(java.lang.String, java.lang.String, java.security.cert.Certificate)
	 */
	public void addCertificatetoTrustStore(String trustStorePassword, String alias, 
			Certificate certificate) throws KeyStoreException, NoSuchAlgorithmException, 
			CertificateException, IOException, URISyntaxException {
	
		if (!trustStorePassword.equalsIgnoreCase(TRUST_STORE_PASSWORD)) {
			throw new TrustStoreException("Invalid password.");
		}

		KeyStore trustStore = this.getTrustStore();
		trustStore.setCertificateEntry(alias, certificate);
		
		File writableTruststore = getWritableTruststoreFile();
		writableTruststore.createNewFile();
		
		File readonlyTruststore = getReadonlyTruststoreFile();
		FileOutputStream fileOutputStream = new FileOutputStream(writableTruststore, false);
		trustStore.store(fileOutputStream, TRUST_STORE_PASSWORD.toCharArray());
		fileOutputStream.close();
		FileUtils.copyFile(writableTruststore, readonlyTruststore);
		FileUtils.forceDelete(writableTruststore);
	}

	/**
	 * @return
	 * @throws URISyntaxException
	 */
	private File getReadonlyTruststoreFile() {
		URL keyStoreURL = this.getClass().getResource("/" + TRUST_STORE_FILE_NAME);
		File readonlyTruststore;
		try {
			readonlyTruststore = new File(keyStoreURL.toURI());
			return readonlyTruststore;
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @return
	 */
	private File getWritableTruststoreFile() {
		String baseConfPath = System.getProperty("catalina.base");
		File writableTruststore = new File(baseConfPath + "/" + TRUST_STORE_FILE_NAME_WRITABLE);
		return writableTruststore;
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.security.ITrustStoreUtils#certificateExpireTime(java.lang.String)
	 */
	public Date certificateExpireTime(String alias) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		
		KeyStore trustStore = this.getTrustStore();
		Certificate certificate = trustStore.getCertificate(alias);
		X509Certificate x509Certificate = (X509Certificate)certificate;
		
		return x509Certificate.getNotAfter();
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.security.ITrustStoreUtils#certificateCommuneRegistrationVector(java.lang.String)
	 */
	public Vector<String> certificateCommuneRegistrationVector(String alias) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, StreamParsingException {
		
		Vector<String> result = new Vector<String>();
		
		String communeRegistration = this.certificateCommuneRegistration(alias);
		if (!StringUtils.isEmptyString(communeRegistration)) {
			StringTokenizer tokenizer = new StringTokenizer(communeRegistration, ",");
			while(tokenizer.hasMoreTokens()) {
				result.add(tokenizer.nextToken());
			}
		}
		
		return result;
		
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.security.ITrustStoreUtils#certificateCommuneRegistration(java.lang.String)
	 */
	public String certificateCommuneRegistration(String alias) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, StreamParsingException {

		Security.addProvider(new BouncyCastleProvider());
		
		KeyStore trustStore = this.getTrustStore();
		Certificate certificate = trustStore.getCertificate(alias);
		
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(certificate.getEncoded());
		X509CertParser x509CertParser = new X509CertParser();
		x509CertParser.engineInit(byteArrayInputStream);
		byteArrayInputStream.close();
		
		X509CertificateObject x509CertificateObject = (X509CertificateObject)x509CertParser.engineRead();
		
		byte[] communeIdExtension = x509CertificateObject.getExtensionValue(it.people.console.security.certificates.PKCSObjectIdentifiers.commune_id.toString());
		
		if (communeIdExtension != null) {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			byteArrayOutputStream.write(communeIdExtension, 2, communeIdExtension.length - 2);
			byteArrayOutputStream.flush();
			byteArrayOutputStream.close();
			return byteArrayOutputStream.toString();
		} else {
			return null;
		}
		
	}
	
	
	/**
	 * @return
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws URISyntaxException 
	 */
	private KeyStore getTrustStore() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {

		KeyStore trustStore = null;
		InputStream jceksInputStream = null;
		File writableTruststoreFile = this.getWritableTruststoreFile();
		File readonlyTruststoreFile = this.getReadonlyTruststoreFile();
		
		if (writableTruststoreFile.exists()) {
			jceksInputStream = new FileInputStream(writableTruststoreFile);
		} else {
			jceksInputStream = new FileInputStream(readonlyTruststoreFile);
		}

		char[] password = TRUST_STORE_PASSWORD.toCharArray();
		trustStore = KeyStore.getInstance(TRUST_STORE_TYPE);
		trustStore.load(jceksInputStream, password);
		jceksInputStream.close();
		
		return trustStore;
		
	}

	/**
	 * @param p12CertificateInputStream
	 * @param p12CertificatePassword
	 * @return
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	private Certificate getCertificatefromP12(InputStream p12CertificateInputStream, String p12CertificatePassword) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		
		Certificate result = null;
		
		char[] password = p12CertificatePassword.toCharArray();
		KeyStore keyStore = null;
		keyStore = KeyStore.getInstance("PKCS12");
		keyStore.load(p12CertificateInputStream, password);
		p12CertificateInputStream.close();
		
		if (!keyStore.getType().equalsIgnoreCase("PKCS12")) {
			throw new KeyStoreException("Key store is not PKCS12 type.");
		}
		int aliasesCount = 0;
		Enumeration<String> aliasesEnumeration = keyStore.aliases();
		while(aliasesEnumeration.hasMoreElements()) {
			aliasesEnumeration.nextElement();
			aliasesCount++;
		}
		if (aliasesCount > 1) {
			throw new KeyStoreException("Key store has more than one alias.");
		}
		
		result = keyStore.getCertificate(keyStore.aliases().nextElement());
		
		return result;
		
	}
	
}

