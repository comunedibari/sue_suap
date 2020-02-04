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

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.cms.CMSSignedData;

//import netscape.javascript.JSObject;

/**
 *
 * @author Giuseppe
 */
public class UtilityFunctions {

    /** Setta java in modo che si fidi di tutti i certificati dei server a cui cerca di collegarsi
     *
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static void trustAllHostNameAndCertificate() throws NoSuchAlgorithmException, KeyManagementException {

        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, java.lang.String str) {
                    }
                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, java.lang.String str) {
                    }
            }
        };
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null,trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

//        HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
//        HttpClient client = new DefaultHttpClient();
//        SchemeRegistry registry = new SchemeRegistry();
//        SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
//        socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
//        registry.register(new Scheme("https", socketFactory, 8443));
//        SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
//        HttpClient http = new DefaultHttpClient(mgr, client.getParams());

//        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
    }
    
 /** indica se il file è un pdf
  *
  * @param file il file da controllare
  * @return "true" se il file è un pdf, "false" altrimenti
  */
  public static boolean isPdf(File file) {
//    PdfStamper tempStamper = null;
    PdfReader tempReader = null;

    if (isP7m(file))
        return false;
    try {
            tempReader = new PdfReader(file.getAbsolutePath());
            return true;
        }
        catch (IOException ex) {
            return false;
        }
        finally {
            try {
                tempReader.close();
            }
            catch (Exception e) {
            }
        }
    }

    /** indica se il file è un p7m
     *
     * @param file il file da controllare
     * @return "true" se il file è un p7m, "false" altrimenti
     */
    public static boolean isP7m(File file) {
    ASN1InputStream asn1 = null;
        try {
            InputStream encodedStream = new FileInputStream(file);
            asn1 = new ASN1InputStream(encodedStream);
            CMSSignedData cms = new CMSSignedData(asn1);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
        finally {
            try {
                asn1.close();
            }
            catch (Exception e) {
            }
        }
    }

    /** Indica se il file pdf è firmato
     *
     * @param file il file da controllare
     * @return "true" se il file pdf è firmato, "false" altrimenti
     */
    public static boolean isSigned(File file) {
    ArrayList signsList = new ArrayList();

        PdfReader tempReader = null;
        // se il file e' un pdf
        try {
            tempReader = new PdfReader(file.getPath());

            // torna tutti i campi firma firmati (sia visibili che invisibili)
            signsList = tempReader.getAcroFields().getSignatureNames();
        }
        finally {
            return !signsList.isEmpty();
        }
    }

     /** Indica se il file pdf ha campi firma non firmati (sia visibili che invisibili)
     *
     * @param file il file da controllare
     * @return "true" se il file pdf ha campi firma non firmati, "false" altrimenti
     */
    public static boolean hasBlankSignatureFields(File file) {
    ArrayList blankSignsList = new ArrayList();

        PdfReader tempReader = null;
        // se il file e' un pdf
        try {
            tempReader = new PdfReader(file.getPath());

            // torna tutti i campi firma non firmati (sia visibili che invisibili)
            blankSignsList = tempReader.getAcroFields().getBlankSignatureNames();
        }
        finally {
            return !blankSignsList.isEmpty();
        }
    }

     /** Indica se il file pdf ha campi firma non firmati visibili
     *
     * @param file il file da controllare
     * @return "true" se il file pdf ha campi firma visibili non firmati, "false" altrimenti
     */
    public static boolean hasVisibleBlankSignatureFields(File file) {
    ArrayList blankSignsList = new ArrayList();

        PdfReader tempReader = null;
        // se il file e' un pdf
        try {
            tempReader = new PdfReader(file.getPath());

            // torna tutti i campi firma non firmati (sia visibili che invisibili)
            blankSignsList = tempReader.getAcroFields().getBlankSignatureNames();
            for (int i=0; i<blankSignsList.size(); i++) {
                List fieldPosition = tempReader.getAcroFields().getFieldPositions((String) blankSignsList.get(i));

                AcroFields.FieldPosition fieldpos = (AcroFields.FieldPosition) fieldPosition.get(0);
                // se la larghezza e l'altezza del rettangolo in cui il campo firma è posizionato sono 0 allora il campo firma è invisibile, quindi lo rimuovo dal risultato
                if (fieldpos.position.getHeight() == 0 && fieldpos.position.getHeight() == 0)
                    blankSignsList.remove(i);
            }
        }
        finally {
            return !blankSignsList.isEmpty();
        }
    }
}
