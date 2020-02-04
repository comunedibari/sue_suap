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

import it.people.console.security.exceptions.CryptoUtilsException;
import it.people.console.security.exceptions.SignCorruptionException;
import it.people.console.utils.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.cert.Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 16/giu/2011 15.13.00
 *
 */
public class CryptoUtils implements ICryptoUtils {
	
	private static KeyStore keyStore = null;
	private static final String KEYSTORE_ALIAS = "audit.people.console";
	private static final String KEYSTORE_PASSWORD = "okx3HZds";
	private static final String P12_FILE_NAME = "it/people/console/security/keys/audit_people_console.p12";
	private static final String SECRET_KEY_FILE_NAME = "/it/people/console/security/keys/secret.key";
	
	private static final String NULL_KEYSTORE = "Impossibile accedere al key store.";
	private static final String PRIVATE_KEY_EXCEPTION = "Impossibile ottenere la chiave privata dal key store.";
	
	public static final int AES_Key_Size = 256;
	
	/**
	 * @throws CryptoUtilsException 
	 * 
	 */
	public CryptoUtils() throws CryptoUtilsException {
		CryptoUtils.keyStore = this.getP12KeyStore(P12_FILE_NAME, KEYSTORE_PASSWORD);
		if (CryptoUtils.keyStore == null) {
			throw new SecurityException(NULL_KEYSTORE);
		}
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.security.ICryptoUtils#getEncryptedData(java.lang.String)
	 */
	public String getEncryptedData(String xmlIn) throws IOException, GeneralSecurityException, ClassNotFoundException, CryptoUtilsException {
		
		Base64 base64 = new Base64();
		
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xmlIn.getBytes());
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
        crypt(byteArrayInputStream, byteArrayOutputStream, cipher);
        
        byteArrayInputStream.close();
        
        String signedData = base64.encode(byteArrayOutputStream.toByteArray());
        
        byteArrayOutputStream.close();
        
        String sign = this.signData(signedData);

        CryptoObject cryptoObject = new CryptoObject(sign, signedData);
        
        byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream flussoOutput = new ObjectOutputStream(byteArrayOutputStream);
		flussoOutput.writeObject(cryptoObject);
		flussoOutput.close();
        
        
		return base64.encode(byteArrayOutputStream.toByteArray());
		
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.security.ICryptoUtils#getDecryptedData(java.lang.String)
	 */
	public String getDecryptedData(String cryptoObjectString) throws IOException, 
		GeneralSecurityException, ClassNotFoundException, SignCorruptionException, CryptoUtilsException {
		
		String result = "";
		
		Base64 base64 = new Base64();
		
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(base64.decode(cryptoObjectString));
		ObjectInputStream flussoInput = new ObjectInputStream(byteArrayInputStream);
		CryptoObject cryptoObject = (CryptoObject)flussoInput.readObject();
		flussoInput.close();

        if (!this.verifySignedData(cryptoObject.getSign(), cryptoObject.getSignedData())) {
        	throw new SignCorruptionException();
        }
				
		byteArrayInputStream = new ByteArrayInputStream(base64.decode(cryptoObject.getSignedData()));
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey());
        crypt(byteArrayInputStream, byteArrayOutputStream, cipher);
        
        byteArrayInputStream.close();
        
        byteArrayOutputStream.flush();
        
        result = byteArrayOutputStream.toString();
                
        byteArrayOutputStream.close();
        
		return result;
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.security.ICryptoUtils#validRootPassword(java.lang.String, java.lang.String)
	 */
	public boolean validRootPassword(String passwordHash, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		return (this.shaBase64(password).equals(passwordHash)) ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.security.ICryptoUtils#getNewCryptedPassword(java.lang.String)
	 */
	public String getNewCryptedPassword(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		return this.shaBase64(password);
	}

	/* (non-Javadoc)
	 * @see it.people.console.security.ICryptoUtils#getSealedObject(java.lang.String, java.io.Serializable)
	 */
	public SealedObject getSealedObject(String password, Serializable object) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, IOException {
	    // Create Key
	    byte key[] = password.getBytes();
	    DESKeySpec desKeySpec = new DESKeySpec(key);
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	    SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

	    // Create Cipher
	    Cipher desCipher =  Cipher.getInstance("DES/ECB/PKCS5Padding");
	    desCipher.init(Cipher.ENCRYPT_MODE, secretKey);

	    // Seal object
	    
	    SealedObject sealedObject = new SealedObject(object, desCipher);
		
	    return sealedObject;
	    
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.security.ICryptoUtils#getUnsealedObject(java.lang.String, javax.crypto.SealedObject)
	 */
	public Object getUnsealedObject(String password, SealedObject object) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IOException, ClassNotFoundException {

	    // Create Key
	    byte key[] = password.getBytes();
	    DESKeySpec desKeySpec = new DESKeySpec(key);
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	    SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

	    // Create Cipher
	    Cipher desCipher =  Cipher.getInstance("DES/ECB/PKCS5Padding");
		
	    // Change cipher mode
	    desCipher.init(Cipher.DECRYPT_MODE, secretKey);

	    byte key1[] = password.getBytes();
	    DESKeySpec desKeySpec1 = new DESKeySpec(key1);
	    SecretKeyFactory keyFactory1 = SecretKeyFactory.getInstance("DES");
	    SecretKey secretKey1 = keyFactory1.generateSecret(desKeySpec1);
	    
	    // Unseal object
	    return object.getObject(secretKey1);
		
	}
	
	/**
	 * @param dataToSign
	 * @return
	 * @throws CryptoUtilsException 
	 */
	private String signData(String dataToSign) throws CryptoUtilsException {
		String providerName = "";
		String algorithm = "SHA1withRSA";
		PrivateKey privateKey = this.getPrivateKey(keyStore, KEYSTORE_ALIAS, KEYSTORE_PASSWORD);
		if (privateKey == null) {
			throw new SecurityException(PRIVATE_KEY_EXCEPTION);
		}
		return this.signData(algorithm, dataToSign, privateKey, providerName);
	}

	/**
	 * @param algorithm
	 * @param dataToSign
	 * @param privateKey
	 * @param providerName
	 * @return
	 */
	private String signData(String algorithm, String dataToSign, PrivateKey privateKey, String providerName) {
		Signature dsa = null;
		String result = "";
		try {
			if (providerName.equalsIgnoreCase("")) {
				dsa = Signature.getInstance(algorithm);
			}
			else {
				dsa = Signature.getInstance(algorithm, providerName);
			}
			dsa.initSign(privateKey);
			dsa.update(new Base64().decode(dataToSign));
			byte[] signedData = dsa.sign();
			result = new Base64().encode(signedData);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @param dataSign
	 * @param signedData
	 * @return
	 * @throws CryptoUtilsException 
	 */
	private boolean verifySignedData(String dataSign, String signedData) throws CryptoUtilsException {
		return this.verifySignedData("SHA1withRSA", dataSign, signedData, 
				this.getPublicKey(keyStore, KEYSTORE_ALIAS), "");
	}
	
	/**
	 * @param algorithm
	 * @param dataSign
	 * @param signedData
	 * @param publicKey
	 * @param providerName
	 * @return
	 */
	private boolean verifySignedData(String algorithm, String dataSign, String signedData, PublicKey publicKey, String providerName) {
		Signature dsa = null;
		boolean result = false;
		try {
			if (providerName.equalsIgnoreCase("")) {
				dsa = Signature.getInstance(algorithm);
			}
			else {
				dsa = Signature.getInstance(algorithm, providerName);
			}
			dsa.initVerify(publicKey);
			dsa.update(new Base64().decode(signedData));
			result = dsa.verify(new Base64().decode(dataSign));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * @param p12FileName
	 * @param p12Password
	 * @return
	 * @throws CryptoUtilsException 
	 */
	private KeyStore getP12KeyStore(String p12FileName, String p12Password) throws CryptoUtilsException {
		InputStream p12InputStream = this.getClass().getClassLoader().getResourceAsStream(p12FileName);

		char[] password = p12Password.toCharArray();
		KeyStore keyStore = null;
		try {
			keyStore = KeyStore.getInstance("PKCS12");
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		
		try {
			keyStore.load(p12InputStream, password);
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
		return keyStore;
	}

	/**
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public void makeSecretKey() throws NoSuchAlgorithmException, IOException {

        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        SecureRandom random = new SecureRandom();
        keygen.init(random);
        SecretKey key = keygen.generateKey();
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("c:/secret.key"));
        out.writeObject(key);
        out.close();
		
	}

	/**
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private Key getSecretKey() throws IOException, ClassNotFoundException {
		
		InputStream inputStream = this.getClass().getResourceAsStream(SECRET_KEY_FILE_NAME);
        ObjectInputStream keyIn = new ObjectInputStream(inputStream);
        Key secretKey = (Key) keyIn.readObject();
        keyIn.close();
		
        return secretKey;
        
	}
	
	/**
	 * @param keyStore
	 * @param keyStoreAlias
	 * @return
	 * @throws CryptoUtilsException
	 */
	private PublicKey getPublicKey(KeyStore keyStore, String keyStoreAlias) throws CryptoUtilsException {
		PublicKey publicKey = null;
		Certificate certificate = null;
		try {
			certificate = keyStore.getCertificate(keyStoreAlias);
		} catch (KeyStoreException e) {
			throw new CryptoUtilsException(e);
		}
		if (certificate != null) {
			publicKey = certificate.getPublicKey();
		}
		return publicKey;
	}

	/**
	 * @param keyStore
	 * @param keyStoreAlias
	 * @param p12Password
	 * @return
	 * @throws CryptoUtilsException
	 */
	private PrivateKey getPrivateKey(KeyStore keyStore, String keyStoreAlias, String p12Password) throws CryptoUtilsException {
		KeyStore.PasswordProtection pp = new KeyStore.PasswordProtection(p12Password.toCharArray());
		KeyStore.PrivateKeyEntry privateKeyEntry = null;
		PrivateKey privateKey = null;
		try {
			privateKeyEntry = (KeyStore.PrivateKeyEntry)keyStore.getEntry(keyStoreAlias, pp);
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoUtilsException(e);
		} catch (UnrecoverableEntryException e) {
			throw new CryptoUtilsException(e);
		} catch (KeyStoreException e) {
			throw new CryptoUtilsException(e);
		}
		if (privateKeyEntry != null) {
			privateKey = privateKeyEntry.getPrivateKey();
		}
		return privateKey;
	}
	
	/**
	 * @param in
	 * @param out
	 * @param cipher
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	private void crypt(InputStream in, OutputStream out, Cipher cipher) throws IOException, GeneralSecurityException {
		int blockSize = cipher.getBlockSize();
		int outputSize = cipher.getOutputSize(blockSize);
		byte[] inBytes = new byte[blockSize];
		byte[] outBytes = new byte[outputSize];

		int inLength = 0;
		;
		boolean more = true;
		while (more)
		{
			inLength = in.read(inBytes);
			if (inLength == blockSize)
			{
				int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
				out.write(outBytes, 0, outLength);
			}
			else more = false;
		}
		if (inLength > 0) outBytes = cipher.doFinal(inBytes, 0, inLength);
		else outBytes = cipher.doFinal();
		out.write(outBytes);
	}

	/**
	 * @param instring
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	private String shaBase64(String instring) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		byte[] inbyte = instring.getBytes();
		MessageDigest md = MessageDigest.getInstance( "SHA" );
		md.update(inbyte);
		byte[] digest = md.digest();
		Base64 b64 = new Base64();
		String oustring = b64.encode(digest);
		return oustring;
	}
	
}
