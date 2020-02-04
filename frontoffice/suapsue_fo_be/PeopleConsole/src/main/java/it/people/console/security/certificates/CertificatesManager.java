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
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import org.bouncycastle.asn1.DERBMPString;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.bouncycastle.x509.extension.AuthorityKeyIdentifierStructure;
import org.bouncycastle.x509.extension.SubjectKeyIdentifierStructure;

import it.people.console.utils.StringUtils;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 19/giu/2011 22.14.09
 *
 */
public class CertificatesManager implements ICertificatesManager {

//	validity
//	common name cn
//	organisation unit ou
//	organisation name o
//	locality name l
//	state name st
//	country c
//	email e	
	
	private static final String SIGNER_COUNTRY = "IT";
	private static final String SIGNER_ORGANIZATION_NAME = "RER";
	private static final String SIGNER_ORGANIZATION_UNIT = "CCD";
	private static final String SIGNER_ORGANIZATION_EMAIL_ADDRESS = "ccd-rer@eng.it";
	private static final String SIGNER_ORGANIZATION_LOCALITY = "BO";
	private static final String SIGNER_ORGANIZATION_STATE_NAME = "BO";

	static X509V3CertificateGenerator  v3CertGen = new X509V3CertificateGenerator();
	
	/* (non-Javadoc)
	 * @see it.people.console.security.certificates.ICertificateManager#generateCertificate(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)
	 */
	public ByteArrayOutputStream generateCertificate(String alias, String password, String commonName, String organisationUnit, 
			String organizaziontName, String locality, String stateName, String country, 
			String eMail, int validity, String[] communeId) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, 
			InvalidKeyException, SecurityException, SignatureException, CertificateException, KeyStoreException, IOException {

		this.installProvider();
		
		KeyFactory keyFactory = this.getKeyFactory();
		
		PrivateKey caPrivateKey = this.getCAPrivateKey(keyFactory);
		PublicKey caPublicKey = this.getCAPublicKey(keyFactory);
		PublicKey publicKey = this.getPublicKey(keyFactory);
		PrivateKey privateKey = this.getPrivateKey(keyFactory);

		X509Certificate x509Certificate = buildCertificate(alias, commonName, organisationUnit, organizaziontName, locality, stateName, 
				country, eMail, validity, caPrivateKey, caPublicKey, publicKey, communeId);
		
		return exportCertificate(x509Certificate, alias, password, privateKey);
		
	}
	
	private void installProvider() {

		Security.addProvider(new BouncyCastleProvider());
		
	}
	
	/**
	 * @param certificate
	 * @param alias
	 * @param password
	 * @param privateKey
	 * @return
	 * @throws KeyStoreException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	private ByteArrayOutputStream exportCertificate(X509Certificate certificate, String alias, 
			String password, PrivateKey privateKey) throws KeyStoreException, NoSuchProviderException, 
			NoSuchAlgorithmException, CertificateException, IOException {
		
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		
		KeyStore store = KeyStore.getInstance("PKCS12", "BC");
		store.load(null, null);
		store.setKeyEntry(alias, privateKey, null, new Certificate[] {(Certificate)certificate});

		store.store(result, password.toCharArray());
		
		return result;
		
	}
	
	/**
	 * @param alias
	 * @param commonName
	 * @param organisationUnit
	 * @param organizaziontName
	 * @param locality
	 * @param stateName
	 * @param country
	 * @param eMail
	 * @param validity
	 * @param caPrivateKey
	 * @param caPublicKey
	 * @param publicKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws InvalidKeyException
	 * @throws InvalidKeySpecException
	 * @throws SecurityException
	 * @throws SignatureException
	 * @throws CertificateException
	 */
	private X509Certificate buildCertificate(String alias, String commonName, String organisationUnit, 
			String organizaziontName, String locality, String stateName, String country, 
			String eMail, int validity, PrivateKey caPrivateKey, PublicKey caPublicKey, 
			PublicKey publicKey, String[] communeId) throws NoSuchAlgorithmException, 
			NoSuchProviderException, InvalidKeyException, 
			InvalidKeySpecException, SecurityException, SignatureException, CertificateException {
		
		//
		// signers name table.
		//
		Hashtable<DERObjectIdentifier, String> signerAttributes = new Hashtable<DERObjectIdentifier, String>();
		Vector<DERObjectIdentifier> signerAttributesOrder = new Vector<DERObjectIdentifier>();

		signerAttributes.put(X509Principal.C, SIGNER_COUNTRY);
		signerAttributes.put(X509Principal.O, SIGNER_ORGANIZATION_NAME);
		signerAttributes.put(X509Principal.OU, SIGNER_ORGANIZATION_UNIT);
		signerAttributes.put(X509Principal.EmailAddress, SIGNER_ORGANIZATION_EMAIL_ADDRESS);
		signerAttributes.put(X509Principal.L, SIGNER_ORGANIZATION_LOCALITY);
		signerAttributes.put(X509Principal.ST, SIGNER_ORGANIZATION_STATE_NAME);
		signerAttributes.put(X509Principal.SERIALNUMBER, this.stringArrayToString(communeId));

		signerAttributesOrder.addElement(X509Principal.C);
		signerAttributesOrder.addElement(X509Principal.O);
		signerAttributesOrder.addElement(X509Principal.OU);
		signerAttributesOrder.addElement(X509Principal.EmailAddress);
		signerAttributesOrder.addElement(X509Principal.L);
		signerAttributesOrder.addElement(X509Principal.ST);
		signerAttributesOrder.addElement(X509Principal.SERIALNUMBER);

		//
		// subject name table.
		//
		Hashtable<DERObjectIdentifier, String> subjectAttributes = new Hashtable<DERObjectIdentifier, String>();
		Vector<DERObjectIdentifier> subjectAttributesOrder = new Vector<DERObjectIdentifier>();

		subjectAttributes.put(X509Principal.CN, commonName);
		if (!StringUtils.isEmptyString(organisationUnit)) {
			subjectAttributes.put(X509Principal.OU, organisationUnit);
		}
		if (!StringUtils.isEmptyString(organizaziontName)) {
			subjectAttributes.put(X509Principal.O, organizaziontName);
		}
		if (!StringUtils.isEmptyString(locality)) {
			subjectAttributes.put(X509Principal.L, locality);
		}
		if (!StringUtils.isEmptyString(stateName)) {
			subjectAttributes.put(X509Principal.ST, stateName);
		}
		if (!StringUtils.isEmptyString(country)) {
			subjectAttributes.put(X509Principal.C, country);
		}
		subjectAttributes.put(X509Principal.EmailAddress, eMail);

		subjectAttributesOrder.addElement(X509Principal.CN);
		if (!StringUtils.isEmptyString(organisationUnit)) {
			subjectAttributesOrder.addElement(X509Principal.OU);
		}
		if (!StringUtils.isEmptyString(organizaziontName)) {
			subjectAttributesOrder.addElement(X509Principal.O);
		}
		if (!StringUtils.isEmptyString(locality)) {
			subjectAttributesOrder.addElement(X509Principal.L);
		}
		if (!StringUtils.isEmptyString(stateName)) {
			subjectAttributesOrder.addElement(X509Principal.ST);
		}
		if (!StringUtils.isEmptyString(country)) {
			subjectAttributesOrder.addElement(X509Principal.C);
		}
		subjectAttributesOrder.addElement(X509Principal.EmailAddress);

		//
		// create the certificate - version 3
		//
		v3CertGen.reset();

		v3CertGen.setSerialNumber(BigInteger.valueOf(3));
		v3CertGen.setIssuerDN(new X509Principal(signerAttributesOrder, signerAttributes));
		
		Calendar calendar = Calendar.getInstance();
		Date validFrom = calendar.getTime();

		calendar.add(Calendar.DAY_OF_MONTH, validity);
		
		Date expire = calendar.getTime();
		
		v3CertGen.setNotBefore(validFrom);
		v3CertGen.setNotAfter(expire);
		v3CertGen.setSubjectDN(new X509Principal(subjectAttributesOrder, subjectAttributes));
		v3CertGen.setPublicKey(publicKey);
		v3CertGen.setSignatureAlgorithm("SHA512WithRSAEncryption");

		//
		// add the extensions
		//
		v3CertGen.addExtension(
				X509Extensions.SubjectKeyIdentifier,
				false,
				new SubjectKeyIdentifierStructure(publicKey));

		v3CertGen.addExtension(
				X509Extensions.AuthorityKeyIdentifier,
				false,
				new AuthorityKeyIdentifierStructure(caPublicKey));

		v3CertGen.addExtension(
				it.people.console.security.certificates.PKCSObjectIdentifiers.commune_id,
				true,
				this.stringArrayToString(communeId).getBytes());
		
		X509Certificate cert = v3CertGen.generateX509Certificate(caPrivateKey);

		cert.checkValidity(new Date());

		cert.verify(caPublicKey);

		PKCS12BagAttributeCarrier   bagAttr = (PKCS12BagAttributeCarrier)cert;
		
		bagAttr.setBagAttribute(
				PKCSObjectIdentifiers.pkcs_9_at_friendlyName,
				new DERBMPString(alias));
		bagAttr.setBagAttribute(
				PKCSObjectIdentifiers.pkcs_9_at_localKeyId,
				new SubjectKeyIdentifierStructure(publicKey));

		return cert;

	}

	/**
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
	private KeyFactory getKeyFactory() throws NoSuchAlgorithmException, NoSuchProviderException {
		
		return KeyFactory.getInstance("RSA", "BC");
		
	}
	
	/**
	 * @param keyFactory
	 * @return
	 * @throws InvalidKeySpecException
	 */
	private PrivateKey getPrivateKey(KeyFactory keyFactory) throws InvalidKeySpecException {

		RSAPrivateCrtKeySpec intPrivKeySpec = new RSAPrivateCrtKeySpec(
				new BigInteger("8de0d113c5e736969c8d2b047a243f8fe18edad64cde9e842d3669230ca486f7cfdde1f8eec54d1905fff04acc85e61093e180cadc6cea407f193d44bb0e9449b8dbb49784cd9e36260c39e06a947299978c6ed8300724e887198cfede20f3fbde658fa2bd078be946a392bd349f2b49c486e20c405588e306706c9017308e69", 16),
				new BigInteger("ffff", 16),
				new BigInteger("7deb1b194a85bcfd29cf871411468adbc987650903e3bacc8338c449ca7b32efd39ffc33bc84412fcd7df18d23ce9d7c25ea910b1ae9985373e0273b4dca7f2e0db3b7314056ac67fd277f8f89cf2fd73c34c6ca69f9ba477143d2b0e2445548aa0b4a8473095182631da46844c356f5e5c7522eb54b5a33f11d730ead9c0cff", 16),
				new BigInteger("ef4cede573cea47f83699b814de4302edb60eefe426c52e17bd7870ec7c6b7a24fe55282ebb73775f369157726fcfb988def2b40350bdca9e5b418340288f649", 16),
				new BigInteger("97c7737d1b9a0088c3c7b528539247fd2a1593e7e01cef18848755be82f4a45aa093276cb0cbf118cb41117540a78f3fc471ba5d69f0042274defc9161265721", 16),
				new BigInteger("6c641094e24d172728b8da3c2777e69adfd0839085be7e38c7c4a2dd00b1ae969f2ec9d23e7e37090fcd449a40af0ed463fe1c612d6810d6b4f58b7bfa31eb5f", 16),
				new BigInteger("70b7123e8e69dfa76feb1236d0a686144b00e9232ed52b73847e74ef3af71fb45ccb24261f40d27f98101e230cf27b977a5d5f1f15f6cf48d5cb1da2a3a3b87f", 16),
				new BigInteger("e38f5750d97e270996a286df2e653fd26c242106436f5bab0f4c7a9e654ce02665d5a281f2c412456f2d1fa26586ef04a9adac9004ca7f913162cb28e13bf40d", 16));
		
		return keyFactory.generatePrivate(intPrivKeySpec);
		
	}
	
	/**
	 * @param keyFactory
	 * @return
	 * @throws InvalidKeySpecException
	 */
	private PublicKey getInternalPublicKey(KeyFactory keyFactory) throws InvalidKeySpecException {
		
		RSAPublicKeySpec intPubKeySpec = new RSAPublicKeySpec(
				new BigInteger("8de0d113c5e736969c8d2b047a243f8fe18edad64cde9e842d3669230ca486f7cfdde1f8eec54d1905fff04acc85e61093e180cadc6cea407f193d44bb0e9449b8dbb49784cd9e36260c39e06a947299978c6ed8300724e887198cfede20f3fbde658fa2bd078be946a392bd349f2b49c486e20c405588e306706c9017308e69", 16),
				new BigInteger("ffff", 16));
		
		return keyFactory.generatePublic(intPubKeySpec);
		
	}
	
	/**
	 * @param keyFactory
	 * @return
	 * @throws InvalidKeySpecException
	 */
	private PublicKey getPublicKey(KeyFactory keyFactory) throws InvalidKeySpecException {

		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(
				new BigInteger("b4a7e46170574f16a97082b22be58b6a2a629798419be12872a4bdba626cfae9900f76abfb12139dce5de56564fab2b6543165a040c606887420e33d91ed7ed7", 16),
				new BigInteger("11", 16));
		
		return keyFactory.generatePublic(pubKeySpec);
		
	}

	/**
	 * @param keyFactory
	 * @return
	 * @throws InvalidKeySpecException
	 */
	private PrivateKey getCAPrivateKey(KeyFactory keyFactory) throws InvalidKeySpecException {

		RSAPrivateCrtKeySpec   caPrivKeySpec = new RSAPrivateCrtKeySpec(
				new BigInteger("b259d2d6e627a768c94be36164c2d9fc79d97aab9253140e5bf17751197731d6f7540d2509e7b9ffee0a70a6e26d56e92d2edd7f85aba85600b69089f35f6bdbf3c298e05842535d9f064e6b0391cb7d306e0a2d20c4dfb4e7b49a9640bdea26c10ad69c3f05007ce2513cee44cfe01998e62b6c3637d3fc0391079b26ee36d5", 16),
				new BigInteger("11", 16),
				new BigInteger("92e08f83cc9920746989ca5034dcb384a094fb9c5a6288fcc4304424ab8f56388f72652d8fafc65a4b9020896f2cde297080f2a540e7b7ce5af0b3446e1258d1dd7f245cf54124b4c6e17da21b90a0ebd22605e6f45c9f136d7a13eaac1c0f7487de8bd6d924972408ebb58af71e76fd7b012a8d0e165f3ae2e5077a8648e619", 16),
				new BigInteger("f75e80839b9b9379f1cf1128f321639757dba514642c206bbbd99f9a4846208b3e93fbbe5e0527cc59b1d4b929d9555853004c7c8b30ee6a213c3d1bb7415d03", 16),
				new BigInteger("b892d9ebdbfc37e397256dd8a5d3123534d1f03726284743ddc6be3a709edb696fc40c7d902ed804c6eee730eee3d5b20bf6bd8d87a296813c87d3b3cc9d7947", 16),
				new BigInteger("1d1a2d3ca8e52068b3094d501c9a842fec37f54db16e9a67070a8b3f53cc03d4257ad252a1a640eadd603724d7bf3737914b544ae332eedf4f34436cac25ceb5", 16),
				new BigInteger("6c929e4e81672fef49d9c825163fec97c4b7ba7acb26c0824638ac22605d7201c94625770984f78a56e6e25904fe7db407099cad9b14588841b94f5ab498dded", 16),
				new BigInteger("dae7651ee69ad1d081ec5e7188ae126f6004ff39556bde90e0b870962fa7b926d070686d8244fe5a9aa709a95686a104614834b0ada4b10f53197a5cb4c97339", 16));
		
		return keyFactory.generatePrivate(caPrivKeySpec);
		
	}

	/**
	 * @param keyFactory
	 * @return
	 * @throws InvalidKeySpecException
	 */
	private PublicKey getCAPublicKey(KeyFactory keyFactory) throws InvalidKeySpecException {

		RSAPublicKeySpec caPubKeySpec = new RSAPublicKeySpec(
				new BigInteger("b259d2d6e627a768c94be36164c2d9fc79d97aab9253140e5bf17751197731d6f7540d2509e7b9ffee0a70a6e26d56e92d2edd7f85aba85600b69089f35f6bdbf3c298e05842535d9f064e6b0391cb7d306e0a2d20c4dfb4e7b49a9640bdea26c10ad69c3f05007ce2513cee44cfe01998e62b6c3637d3fc0391079b26ee36d5", 16),
				new BigInteger("11", 16));
		
		return keyFactory.generatePublic(caPubKeySpec);
		
	}

	/**
	 * @param values
	 * @return
	 */
	private String stringArrayToString(String[] values) {
		
		String buffer = "";
		
		for(int index = 0; index < values.length; index++) {
			buffer += values[index];
			if (index < (values.length - 1)) {
				buffer += ",";
			}
		}
		
		return buffer;
		
	}
	
}
