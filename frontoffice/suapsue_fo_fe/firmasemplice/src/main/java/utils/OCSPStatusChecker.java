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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Vector;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
import org.bouncycastle.asn1.x509.X509Extension;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.ocsp.BasicOCSPResp;
import org.bouncycastle.ocsp.CertificateID;
import org.bouncycastle.ocsp.OCSPException;
import org.bouncycastle.ocsp.OCSPReq;
import org.bouncycastle.ocsp.OCSPReqGenerator;
import org.bouncycastle.ocsp.OCSPResp;
import org.bouncycastle.ocsp.RevokedStatus;

/**
 *
 * @author gdemarco
 */
public class OCSPStatusChecker {

    private static Logger logger = LoggerFactory.getLogger(OCSPStatusChecker.class);

    public OCSPStatus check(X509Certificate trustedCA, X509Certificate checkCerts) throws CertificateException, InvalidAlgorithmParameterException, NoSuchAlgorithmException {

        ASN1InputStream aIn = null;
        DataOutputStream dataOut = null;
        InputStream is = null;
        try {

            // cerco di leggere il campo del certificato con AuthorityInfoAccess con ObjectID 1.3.6.1.5.5.7.1.1
            byte[] bytes = checkCerts.getExtensionValue(X509Extensions.AuthorityInfoAccess.getId());
            aIn = new ASN1InputStream(new ByteArrayInputStream(bytes));
            ASN1OctetString octs = (ASN1OctetString) aIn.readObject();
            aIn = new ASN1InputStream(new ByteArrayInputStream(octs.getOctets()));
            DERObject obj = aIn.readObject();

            ASN1Sequence objSeq = (ASN1Sequence) obj;
            objSeq = (ASN1Sequence) objSeq.getObjectAt(0);
            ASN1TaggedObject tagged = (ASN1TaggedObject) objSeq.getObjectAt(1);

            DERTaggedObject taggedObject = (DERTaggedObject) tagged;
            String AuthorityURL = new String(ASN1OctetString.getInstance(taggedObject, false).getOctets());

            // eseguo la verifica

            // genero la richiesta per il servizio OCSP
            OCSPReq ocspRequest = null;
            try {
                ocspRequest = generateOcspRequest(trustedCA, checkCerts.getSerialNumber());
            }
            catch (Exception oCSPException) {
                logger.error("Errore nella generazione della richesta da inviare al server ocsp.", oCSPException);
                return OCSPStatus.UNKNOWN;
            }
            byte[] array = ocspRequest.getEncoded();

            URL url = new URL(AuthorityURL);

            HttpURLConnection url_con = (HttpURLConnection)url.openConnection();
            url_con.setRequestProperty("Content-Type", "application/ocsp-request");
            url_con.setRequestProperty("Accept", "application/ocsp-response");
            url_con.setDoOutput(true);

            OutputStream out = url_con.getOutputStream();

            dataOut = new DataOutputStream(new BufferedOutputStream(out));
            dataOut.write(array);
            dataOut.flush();
            dataOut.close();

            // leggo la risposta del server
            is = (InputStream) url_con.getContent();
            OCSPResp ocspResponse = new OCSPResp(is);

            BasicOCSPResp basicOcspResp= (BasicOCSPResp) ocspResponse.getResponseObject();
            if (logger.isInfoEnabled()) {
            	logger.info("Verifica completata.");
            }
            
            if (basicOcspResp.getResponses()[0].getCertStatus() == null) {
                if (logger.isInfoEnabled()) {
                	logger.info("Il certificato è valido.");
                }
                return OCSPStatus.GOOD;
            }
            else if(basicOcspResp.getResponses()[0].getCertStatus() instanceof RevokedStatus) {
                if (logger.isInfoEnabled()) {
                	logger.info("Il certificato NON è valido.");
                }
                return OCSPStatus.REVOKED;
            }
            else {
                if (logger.isInfoEnabled()) {
                	logger.info("Errore nell'interpretazione dello stato del certificato.");
                }
                return OCSPStatus.UNKNOWN;
            }
        }

        catch (Exception ex) {
            logger.error("Errore nella vertifica del certificato.", ex);
            return OCSPStatus.UNKNOWN;
        }
        finally {
            try {
                aIn.close();
            }
            catch (Exception ex) {
            }
            try {
                dataOut.close();
            }
            catch (Exception ex) {
            }
            try {
                is.close();
            }
            catch (Exception ex) {
            }
        }

    }


    private OCSPReq generateOcspRequest(X509Certificate issuerCert, BigInteger serialNumber) throws OCSPException {

    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        // Load a new generator for the request
        OCSPReqGenerator ocspRequestGenerator = new OCSPReqGenerator();

        // Generate the id for the certificate we are looking for
        CertificateID id = null;
//        id = new CertificateID(CertificateID.HASH_SHA1, issuerCert, null);
        id = new CertificateID(CertificateID.HASH_SHA1, issuerCert, serialNumber);

        // Add the certificate ID to the generator
        ocspRequestGenerator.addRequest(id);

        BigInteger nonce = BigInteger.valueOf(System.currentTimeMillis());
        Vector oids = new Vector();
        Vector values = new Vector();

        oids.add(OCSPObjectIdentifiers.id_pkix_ocsp_nonce);
        values.add(new X509Extension(false, new DEROctetString(nonce.toByteArray())));

        ocspRequestGenerator.setRequestExtensions(new
        X509Extensions(oids, values));
        return ocspRequestGenerator.generate();
    }

    public static X509Certificate readCert(String fileName) throws FileNotFoundException, CertificateException, NoSuchProviderException {
        BufferedInputStream bis = null;
        X509Certificate cert = null;
        try {
            InputStream is = new FileInputStream(fileName);
            bis = new BufferedInputStream(is);
            CertificateFactory cf = CertificateFactory.getInstance("X.509", "SUN");
            cert = (X509Certificate) cf.generateCertificate(bis);
        }
        finally {
            try {
                bis.close();
            }
            catch (IOException ex) {
                logger.error("", ex);
                return null;
            }
        }
        return cert;
    }

}
