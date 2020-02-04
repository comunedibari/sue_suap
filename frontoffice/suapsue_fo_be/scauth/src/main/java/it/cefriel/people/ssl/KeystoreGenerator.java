package it.cefriel.people.ssl;

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
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
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

public class KeystoreGenerator
{
  private static final Logger logger = LoggerFactory.getLogger(KeystoreGenerator.class);
  
  public static KeyStore createP12(String userID, KeyStore caKeyStore, String caKeyStoreAlias, String caKeyStorePassword, String keystorePassword, String domicioElettronico, String nome, String cognome)
  {
    try
    {
      logger.debug("KeystoreGenerator.createP12()");
      logger.debug("Username: " + userID);
      logger.debug("caKeystorePwd: " + caKeyStorePassword);
      logger.debug("caKeystoreAlias: " + caKeyStoreAlias);
      logger.debug("keystorePassword: " + keystorePassword);
      
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
      



      Date dateFrom = new Date();
      Calendar cal = Calendar.getInstance();
      cal.add(5, 365);
      Date dateTo = cal.getTime();
      int suffixStart = userID.indexOf("@");
      String codiceFiscale = "";
      if (suffixStart > 0) {
        codiceFiscale = userID.substring(0, suffixStart);
      } else {
        codiceFiscale = userID;
      }
      X509Principal subject = new X509Principal("cn=" + cognome + "/" + nome + "/" + codiceFiscale + "/000000");
      certificateGenerator.setIssuerDN(ca_cert.getIssuerX500Principal());
      certificateGenerator.setSubjectDN(subject);
      certificateGenerator.setSerialNumber(BigInteger.valueOf(1L));
      certificateGenerator.setNotBefore(dateFrom);
      certificateGenerator.setNotAfter(dateTo);
      certificateGenerator.setPublicKey(pubKey);
      certificateGenerator.setSignatureAlgorithm("SHA1withRSA");
      

      int keyUsage = 176;
      X509KeyUsage x509KeyUsage = new X509KeyUsage(keyUsage);
      certificateGenerator.addExtension("2.5.29.15", true, x509KeyUsage);
      

      Vector extKeyPurposes = new Vector();
      extKeyPurposes.add(KeyPurposeId.id_kp_clientAuth);
      extKeyPurposes.add(KeyPurposeId.id_kp_emailProtection);
      ExtendedKeyUsage extKeyUsage = new ExtendedKeyUsage(extKeyPurposes);
      certificateGenerator.addExtension("2.5.29.37", false, extKeyUsage);
      

      GeneralNames subjectAltNames = new GeneralNames(new GeneralName(1, domicioElettronico));
      certificateGenerator.addExtension(X509Extensions.SubjectAlternativeName.getId(), false, subjectAltNames);
      

      GeneralName gn = new GeneralName(6, new DERIA5String("http://ca-people1/remotesign.crl"));
      GeneralNames gns = new GeneralNames(new DERSequence(gn));
      DistributionPointName dpn = new DistributionPointName(0, gns);
      DistributionPoint crlDistributionPoint = new DistributionPoint(dpn, null, null);
      certificateGenerator.addExtension(X509Extensions.CRLDistributionPoints.getId(), 
        false, 
        new DERSequence(crlDistributionPoint));
      

      SubjectPublicKeyInfo spki = new SubjectPublicKeyInfo(
        (ASN1Sequence)new ASN1InputStream(new ByteArrayInputStream(pubKey.getEncoded())).readObject());
      SubjectKeyIdentifier ski = new SubjectKeyIdentifier(spki);
      certificateGenerator.addExtension(X509Extensions.SubjectKeyIdentifier.getId(), false, ski);
      

      SubjectPublicKeyInfo apki = new SubjectPublicKeyInfo(
        (ASN1Sequence)new ASN1InputStream(new ByteArrayInputStream(pubKeyCA.getEncoded())).readObject());
      AuthorityKeyIdentifier aki = new AuthorityKeyIdentifier(apki);
      certificateGenerator.addExtension(X509Extensions.AuthorityKeyIdentifier.getId(), false, aki);
      

      PolicyInformation pi = new PolicyInformation(new DERObjectIdentifier("1.3.6.1.5.5.7.2.1"));
      certificateGenerator.addExtension(X509Extensions.CertificatePolicies.getId(), false, new DERSequence(pi));
      

      X509Certificate cert = certificateGenerator.generateX509Certificate(privKeyCA);
      


      KeyStore keystore = KeyStore.getInstance("PKCS12");
      keystore.load(null, keystorePassword.toCharArray());
      Certificate[] chain = { cert, ca_cert };
      keystore.setKeyEntry(codiceFiscale, privKey, keystorePassword.toCharArray(), chain);
      
      return keystore;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
}

