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
/*

  Licenza:	    Licenza Progetto PEOPLE
  Fornitore:    CEFRIEL
  Autori:       M. Pianciamore, P. Selvini

  Questo codice sorgente � protetto dalla licenza valida nell'ambito del
  progetto PEOPLE. La propriet� intellettuale di questo codice � e rester�
  esclusiva di "CEFRIEL Societ� Consortile a Responsabilit� Limitata" con
  sede legale in via Renato Fucini 2, 20133 Milano (MI).

  Disclaimer:

  COVERED CODE IS PROVIDED UNDER THIS LICENSE ON AN "AS IS" BASIS, WITHOUT
  WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, WITHOUT 
  LIMITATION, WARRANTIES THAT THE COVERED CODE IS FREE OF DEFECTS, MERCHANTABLE,
  FIT FOR A PARTICULAR PURPOSE OR NON-INFRINGING. THE ENTIRE RISK AS TO THE
  QUALITY AND PERFORMANCE OF THE COVERED CODE IS WITH YOU. SHOULD ANY COVERED
  CODE PROVE DEFECTIVE IN ANY RESPECT, YOU (NOT THE INITIAL DEVELOPER OR ANY
  OTHER CONTRIBUTOR) ASSUME THE COST OF ANY NECESSARY SERVICING, REPAIR OR
  CORRECTION.
    
*/
package it.idp.people.admin.sqlmap.capeople.userkeystoredata;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.PolicyInformation;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.jce.X509KeyUsage;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;

public class KeystoreGenerator {

	private static Logger logger = LoggerFactory.getLogger(KeystoreGenerator.class);
  
  public static KeyStore createP12(String userID, KeyStore caKeyStore, String caKeyStoreAlias, String caKeyStorePassword, String keystorePassword, String domicioElettronico, String nome, String cognome) {
    
    try{
//      logger.debug("KeystoreGenerator.createP12()");
//      logger.debug("Username: " + userID);
//      logger.debug("caKeystorePwd: " + caKeyStorePassword);
//      logger.debug("caKeystoreAlias: " + caKeyStoreAlias);
//      logger.debug("keystorePassword: " + keystorePassword);
      
      Security.addProvider(new BouncyCastleProvider());
      
      PrivateKey privKeyCA = (PrivateKey)caKeyStore.getKey(caKeyStoreAlias, caKeyStorePassword.toCharArray());
      PublicKey pubKeyCA = caKeyStore.getCertificate(caKeyStoreAlias).getPublicKey();
      X509Certificate ca_cert = (X509Certificate)caKeyStore.getCertificate(caKeyStoreAlias);
      
      X509V3CertificateGenerator certificateGenerator = new X509V3CertificateGenerator();
      KeyPairGenerator kpgen = KeyPairGenerator.getInstance("RSA");
      kpgen.initialize(1024);
      KeyPair kp = kpgen.generateKeyPair();
      PrivateKey privKey = kp.getPrivate();
      PublicKey pubKey = kp.getPublic();

      // calcola le date di inizio e fine validit� del certificato
      // la data di inizio � la data attuale (new Date())
      // la data di fine � calcolata mediante Calendar, ad un anno da oggi
      Date dateFrom = new Date();
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.DATE, 365);
      Date dateTo = cal.getTime();
      int suffixStart = userID.indexOf("@");
      String codiceFiscale = "";
      if(suffixStart>0){
        codiceFiscale = userID.substring(0, suffixStart);
      } else {
        codiceFiscale = userID;
      }
      X509Principal subject = new X509Principal("cn=" + cognome + "/" + nome + "/" + codiceFiscale + "/000000");
      certificateGenerator.setIssuerDN(ca_cert.getIssuerX500Principal());
      certificateGenerator.setSubjectDN(subject);
      certificateGenerator.setSerialNumber(BigInteger.valueOf(1));
      certificateGenerator.setNotBefore(dateFrom);
      certificateGenerator.setNotAfter(dateTo);
      certificateGenerator.setPublicKey(pubKey);
      certificateGenerator.setSignatureAlgorithm("SHA1withRSA");
      
      // aggiunta estensione critica: Key Usage
      int keyUsage = X509KeyUsage.digitalSignature | X509KeyUsage.keyEncipherment | X509KeyUsage.dataEncipherment; 
      X509KeyUsage x509KeyUsage = new X509KeyUsage(keyUsage);
      certificateGenerator.addExtension("2.5.29.15", true, x509KeyUsage);
      
      // aggiunta estensione non critica: Extended Key Usage
      Vector extKeyPurposes = new Vector();
      extKeyPurposes.add(KeyPurposeId.id_kp_clientAuth);
      extKeyPurposes.add(KeyPurposeId.id_kp_emailProtection);
      ExtendedKeyUsage extKeyUsage = new ExtendedKeyUsage(extKeyPurposes);
      certificateGenerator.addExtension("2.5.29.37", false, extKeyUsage);
      
      // aggiunta estensione non critica: Subject Alternative Names
      GeneralNames subjectAltNames = new GeneralNames(new GeneralName(GeneralName.rfc822Name, domicioElettronico));
      certificateGenerator.addExtension(X509Extensions.SubjectAlternativeName.getId(), false, subjectAltNames);
      
      // aggiunta estensione non critica: CRL Distribution Point
      GeneralName gn = new GeneralName(6, new DERIA5String("http://ca-people1/remotesign.crl"));
      GeneralNames gns = new GeneralNames(new DERSequence(gn));
      DistributionPointName dpn = new DistributionPointName(0, gns);
      DistributionPoint crlDistributionPoint = new DistributionPoint(dpn, null, null);
      certificateGenerator.addExtension(X509Extensions.CRLDistributionPoints.getId(), 
                                        false, 
                                        new DERSequence(crlDistributionPoint));
      
      // aggiunta estensione non critica: Subject Key Identifier
      SubjectPublicKeyInfo spki = new SubjectPublicKeyInfo((ASN1Sequence) new ASN1InputStream(
          new ByteArrayInputStream(pubKey.getEncoded())).readObject());
      SubjectKeyIdentifier ski = new SubjectKeyIdentifier(spki);
      certificateGenerator.addExtension(X509Extensions.SubjectKeyIdentifier.getId(), false, ski);
      
      // aggiunta estensione non critica: Authority Key Identifier      
      SubjectPublicKeyInfo apki = new SubjectPublicKeyInfo((ASN1Sequence) new ASN1InputStream(
          new ByteArrayInputStream(pubKeyCA.getEncoded())).readObject());
      AuthorityKeyIdentifier aki = new AuthorityKeyIdentifier(apki);
      certificateGenerator.addExtension(X509Extensions.AuthorityKeyIdentifier.getId(), false, aki);
      
      // aggiunta estensione non critica: Certificate Policies
      PolicyInformation pi = new PolicyInformation(new DERObjectIdentifier("1.3.6.1.5.5.7.2.1"));
      certificateGenerator.addExtension(X509Extensions.CertificatePolicies.getId(), false, new DERSequence(pi));
      
      // generazione certificato
      X509Certificate cert = certificateGenerator.generateX509Certificate(privKeyCA);
            
      // creazione keystore di tipo PKCS12
      // inserimento della catena root_cert + cert e protezione con password fornita
      KeyStore keystore = KeyStore.getInstance("PKCS12");
      keystore.load(null, keystorePassword.toCharArray());
      Certificate[] chain = new Certificate[]{ cert, ca_cert};
      keystore.setKeyEntry(codiceFiscale, privKey, keystorePassword.toCharArray(), chain);

      return keystore;

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }  	
}
