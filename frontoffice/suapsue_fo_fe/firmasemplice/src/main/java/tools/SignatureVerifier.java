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
package tools;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfPKCS7;
import com.itextpdf.text.pdf.PdfReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.CertStore;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import sun.security.x509.X509CertImpl;
import utils.OCSPStatus;
import utils.OCSPStatusChecker;
import utils.SignGeneralStatus;
import utils.SignStatus;
import utils.UtilityFunctions;

/**
 *
 * @author Giuseppe
 */
public class SignatureVerifier {

	private static Logger logger = LoggerFactory.getLogger(SignatureVerifier.class);
	
private File file;
private String CSPTrustedListsURL;
private boolean checkCertificateRevocation;
private String keystorePath;
private char[] keystorePassword;
private String workDirectory;
private boolean UseOnlineTSLXml;
private String prefix;

private ArrayList signaturesResults;
private String signaturesResultsString;

private boolean allSignOK = false;

    /** Crea un oggetto "SignatureVerifier" per la verifica delle firme con ricerca dei certificati delle C.A. emittenti da un keystore locale
     *
     * @param file il file da controllare
     * @param keystorePath path del keystore
     * @param keystorePassword password del keystore
     * @param checkCertificateRevocation indica se procedere al controllo di revoca online del certificato
     */
    public SignatureVerifier(File file, String keystorePath, char[] keystorePassword, boolean checkCertificateRevocation) {

        // Aggiungo il provider Bouncy Castle in modo da usarlo per generare l'hash con l'algoritmo SHA256
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        this.file = file;
        this.checkCertificateRevocation = checkCertificateRevocation;
        this.CSPTrustedListsURL = null;
        this.keystorePath = keystorePath;
        this.keystorePassword = keystorePassword;
        this.UseOnlineTSLXml = false;
        this.prefix = null;
    }

    /** Crea un oggetto "SignatureVerifier" per la verifica delle firme con ricerca dei certificati delle C.A. emittenti usando il file TSLXml pubblico
     *
     * @param file il file da controllare
     * @param checkCertificateRevocation indica se procedere al controllo di revoca online del certificato
     * @param CSPTrustedListsURL indirizzo del file xml contenente la lista dei certificatori accreditati (Trusted Lists of Certification Service Providers)
     * @param workDirectory directory di temporanea di lavoro per lo scaricamento del file TSLXml
     */
    public SignatureVerifier(File file, boolean checkCertificateRevocation, String CSPTrustedListsURL, String workDirectory) {

        // Aggiungo il provider Bouncy Castle in modo da usarlo per generare l'hash con l'algoritmo SHA256
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        this.file = file;
        this.checkCertificateRevocation = checkCertificateRevocation;
        this.CSPTrustedListsURL = CSPTrustedListsURL;
        this.keystorePath = null;
        this.keystorePassword = null;
        this.UseOnlineTSLXml = true;
        this.workDirectory = workDirectory;
        this.prefix = null;
    }

    /** Crea un oggetto "SignatureVerifier" per la verifica delle firme con ricerca dei certificati delle C.A. emittenti usando il file TSLXml pubblico
     *
     * @param file il file da controllare
     * @param workDirectory directory di temporanea di lavoro per lo scaricamento del file TSLXml
     * @param prefix prefisso da aggiungere al file TSLXml scaricato. Usando il prefisso, l'oggetto scaricherà un file TSLXml per ogni istanza
     */
    public SignatureVerifier(File file, boolean checkCertificateRevocation, String CSPTrustedListsURL, String workDirectory, String prefix) {

        // Aggiungo il provider Bouncy Castle in modo da usarlo per generare l'hash con l'algoritmo SHA256
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        this.file = file;
        this.checkCertificateRevocation = checkCertificateRevocation;
        this.CSPTrustedListsURL = CSPTrustedListsURL;
        this.keystorePath = null;
        this.keystorePassword = null;
        this.UseOnlineTSLXml = true;
        this.workDirectory = workDirectory;
        this.prefix = prefix;
    }

    /** Verifica lo stato delle firme.
     * Lo stato delle firme si può ottenere dai metodi: getSignsResults() e getSignsResultsString().
     * E' anche possibile conoscere lo stato generale delle firme dal metodo isAllSignOK().
     * @return "true" se tutte le firme e i certificati sono validi, "false" altrimenti.
     * @throws IOException
     */
    public boolean verify() throws IOException {
    String temp_signaturesResultsString = "";

        if (UtilityFunctions.isP7m(file)) {

            String sigName = null;
            boolean signRes = false;
            String sign = "";
            SignerInformationStore signers = null;
            CertStore certs = null;
            try {
                InputStream encodedStream = new FileInputStream(file);
                ASN1InputStream asn1 = new ASN1InputStream(encodedStream);
                CMSSignedData cms = new CMSSignedData(asn1);

                certs = cms.getCertificatesAndCRLs("Collection", "BC");
                signers = cms.getSignerInfos();
            }
            catch (Exception ex) {
                logger.error("", ex);
                return false;
            }

            Collection c = signers.getSigners();
            Iterator it = c.iterator();

            while (it.hasNext()) {

                X509Certificate signingCert = null;
                try {
                    SignerInformation signer = (SignerInformation)it.next();
                    Collection certCollection = certs.getCertificates(signer.getSID());
                    Iterator certIt = certCollection.iterator();
                    signingCert = (X509Certificate)certIt.next();

                    // estraggo il codice fiscale dal certificato e lo uso come identificativo della firma
                    String subjectField = signingCert.getSubjectDN().getName();
                    String certCodFisc = "";
                    if (subjectField != null) {
                        StringTokenizer tok = new StringTokenizer(subjectField, ",");
                        while (tok.hasMoreTokens()) {
                            String field = tok.nextToken();
                            String[] splittedField = field.split("=");
                            if (splittedField[0].trim().equalsIgnoreCase("SERIALNUMBER")) {
                                certCodFisc = splittedField[1];
                                break;
                            }
                        }
                    }

                    sigName = certCodFisc;
                    signRes = signer.verify(signingCert.getPublicKey(),"BC");
                    sign = "";
                    if (!signRes) {
                        sign = sigName + ";" + "false" + ";" +  "firma non valida";
                        if (logger.isInfoEnabled()) {
                        	logger.info("La firma NON è valida");
                        }
                        temp_signaturesResultsString += sign;
                        if (it.hasNext())
                            temp_signaturesResultsString += "\n";
                        continue;
                    }
                }
                catch (Exception ex) {
                    logger.error("", ex);
                    sign = sigName + ";" + "false" + ";" +  "impossibile verificare la firma";
                    temp_signaturesResultsString += sign;
                    if (it.hasNext())
                        temp_signaturesResultsString += "\n";
                    continue;
                }

                if (logger.isInfoEnabled()) {
	                logger.info("Firmatario: " + PdfPKCS7.getSubjectFields(signingCert));
	                logger.info("La firma è valida, procedo al controllo della revoca dei certificati...");
                }

                OCSPStatus ocspStatus = OCSPStatus.UNKNOWN;

                try {
                    ocspStatus = checkCertificateStatus(signingCert);
                }
                catch (Exception ex) {
                    logger.error("", ex);
                    ocspStatus = OCSPStatus.UNKNOWN;
                }

                if (ocspStatus == null)
                    sign = sigName + ";" + "true" + ";" +  "controllo della revoca disattivato";
                else if(ocspStatus == OCSPStatus.REVOKED)
                    sign = sigName + ";" + "null" + ";" +  "certificato revocato";
                else if (ocspStatus == OCSPStatus.GOOD)
                    sign = sigName + ";" + "true" + ";" +  "null";
                else if(ocspStatus == OCSPStatus.EXPIRED)
                    sign = sigName + ";" + "null" + ";" +  "certificato scaduto";
                else if(ocspStatus == OCSPStatus.NOT_YET_VALID)
                    sign = sigName + ";" + "null" + ";" +  "certificato non ancora valido";
                else
                    sign = sigName + ";" + "null" + ";" +  "impossibile verificare lo stato di revoca del certificato";

                temp_signaturesResultsString += sign;
                if (it.hasNext())
                    temp_signaturesResultsString += "\n";
            }
        }

        else if (UtilityFunctions.isPdf(file)) {
            PdfReader reader = null;
            try {
                reader = new PdfReader(file.getAbsolutePath());
            }
            catch (IOException ex) {
                logger.error("", ex);
                return false;
            }

            AcroFields af = reader.getAcroFields();
            ArrayList names = af.getSignatureNames();

            if (names.size() > 0) {
                temp_signaturesResultsString = "";
                for (int k = 0; k < names.size(); ++k) {
                    String sign;

                    String sigName = (String)names.get(k);

                    PdfPKCS7 pk = null;
                    X509Certificate signingCert = null;
                    try {
                        pk = af.verifySignature(sigName);
                        try {
                            signingCert = new X509CertImpl(pk.getSigningCertificate().getEncoded());
                        }
                        catch (CertificateEncodingException ex) {
                            logger.error("", ex);
                            sign = sigName + ";" + "null" + ";" +  "impossibile leggere il certificato di firma";
                            temp_signaturesResultsString += sign;
                            if (k < names.size() - 1)
                                temp_signaturesResultsString += "\n";
                            continue;
                        }
                        catch (CertificateException ex) {
                            logger.error("", ex);
                            sign = sigName + ";" + "null" + ";" +  "impossibile leggere il certificato di firma";
                            temp_signaturesResultsString += sign;
                            if (k < names.size() - 1)
                                temp_signaturesResultsString += "\n";
                            continue;
                        }

                        // estraggo il codice fiscale dal certificato e lo uso come identificativo della firma
                        String subjectField = signingCert.getSubjectDN().getName();
                        String certCodFisc = "";
                        if (subjectField != null) {
                            StringTokenizer tok = new StringTokenizer(subjectField, ",");
                            while (tok.hasMoreTokens()) {
                                String field = tok.nextToken();
                                String[] splittedField = field.split("=");
                                if (splittedField[0].trim().equalsIgnoreCase("SERIALNUMBER")) {
                                    certCodFisc = splittedField[1];
                                    break;
                                }
                            }
                        }
                        sigName += "/" + certCodFisc;
                 }
                    catch (Exception ex) {
                        logger.error("", ex);
                        sign = sigName + ";" + "null" + ";" +  "impossibile verificare la firma";
                        temp_signaturesResultsString += sign;
                        if (k < names.size() - 1)
                            temp_signaturesResultsString += "\n";
                        continue;
                    }
                    boolean signRes = false;
                    OCSPStatus ocspStatus = null;

                    // verifica della firma del PDF
                    try {
                        signRes = pk.verify();

                        if (!signRes) {
                            sign = sigName + ";" + "false" + ";" +  "firma non valida";
                            if (logger.isInfoEnabled()) {
                            	logger.info("La firma NON è valida");
                            }
                            temp_signaturesResultsString += sign;
                            if (k < names.size() - 1)
                                temp_signaturesResultsString += "\n";
                            continue;
                        }

                        if (logger.isInfoEnabled()) {
	                        logger.info("Firmatario: " + PdfPKCS7.getSubjectFields(signingCert));
	                        logger.info("La firma è valida e il documento non è stato modificato, procedo al controllo della revoca dei certificati...");
                        }
                        try {
                            ocspStatus = checkCertificateStatus(signingCert);
                        }
                        catch (Exception ex) {
                            logger.error("", ex);
                            ocspStatus = OCSPStatus.UNKNOWN;
                        }

                        if (ocspStatus == OCSPStatus.REVOKED)
                            sign = sigName + ";" + "null" + ";" +  "certificato revocato";
                        else if (ocspStatus == OCSPStatus.GOOD)
                            sign = sigName + ";" + "true" + ";" +  "null";
                        else
                            sign = sigName + ";" + "null" + ";" +  "impossibile verificare lo stato di revoca del certificato";
                    }
                    catch (SignatureException ex) {
                        logger.error("", ex);
                        sign = sigName + ";" + "null" + ";" +  "impossibile verificare la firma";
                    }
                    temp_signaturesResultsString += sign;
                    if (k < names.size() - 1)
                        temp_signaturesResultsString += "\n";
                }
            }
        }
        signaturesResultsString = temp_signaturesResultsString;
        signaturesResults = parseVerifierResponse(signaturesResultsString);
        return allSignOK;
    }

    /** Crea un ArrayList di SignStatus a partire dal risultato della stringa "signaturesResultsString".
     *  Un oggetto SignStatus rappresenta lo stato di una firma e mette a disposizione dei medoti per facilitarne il controllo.
     * @param signStatusString la stringa risultato della funzione verifiySignatureStatus()
     * @return un ArrayList di oggetti SignStatus, ogniuno dei quali rappresenta una firma
     * @throws IOException
     */
    private ArrayList parseVerifierResponse(String signStatusString) throws IOException {
    ArrayList signsStatus = new ArrayList();

        BufferedReader reader = new BufferedReader(new StringReader(signStatusString));
        String line = reader.readLine();
        while (line != null) {
            SignGeneralStatus generalStatus = SignGeneralStatus.VALID;
            StringTokenizer tok = new StringTokenizer(line, ";");
            String signName = tok.nextToken();
            String signResultReaded = tok.nextToken();
            String signMessage = tok.nextToken();

            String signResult = null;
            if (signResultReaded.equalsIgnoreCase("null")) {
                if (signMessage.equalsIgnoreCase("impossibile verificare la firma")) {
                    signResult = "Sconosciuta";
                    generalStatus = SignGeneralStatus.UNKNOWN;
                }
                else {
                    signResult = "Valida";
                }
            }
            else if (signResultReaded.equalsIgnoreCase("true")) {
                signResult = "Valida";
            }
            else if (signResultReaded.equalsIgnoreCase("false")) {
                signResult = "NON valida";
                generalStatus = SignGeneralStatus.NOTVALID;
            }

            OCSPStatus signCertificateStatus = null;
            if (signResultReaded.equalsIgnoreCase("true"))
                signCertificateStatus = OCSPStatus.GOOD;
            else {
                if (signResultReaded.equalsIgnoreCase("false")) {
                    signCertificateStatus = OCSPStatus.UNKNOWN;
                    generalStatus = SignGeneralStatus.NOTVALID;
                }
                else {
                    if (signResultReaded.equalsIgnoreCase("null") && signMessage.equalsIgnoreCase("certificato revocato")) {
                        signCertificateStatus = OCSPStatus.REVOKED;
                        generalStatus = SignGeneralStatus.NOTVALID;
                    }
                    else {
                        signCertificateStatus = OCSPStatus.UNKNOWN;
                        generalStatus = SignGeneralStatus.UNKNOWN;
                    }
                }
            }

            if (signMessage.equalsIgnoreCase("null"))
                signMessage = "";
            SignStatus status = new SignStatus(signName, signResult, signCertificateStatus, signMessage, generalStatus);
            allSignOK = status.getGeneralStatus() == SignGeneralStatus.VALID ? true : false;
            signsStatus.add(status);
            line = reader.readLine();
        }
        return signsStatus;
    }

    /** Esegue il controllo di validità on-line di un certificato usando il protocollo OCSP
     *
     * @param signingCert il certificato da controllare
     * @return un oggetto "OCSPStatus" che indentifica lo stato del certificato
     * @throws KeyStoreException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws InvalidAlgorithmParameterException
     */
    public OCSPStatus checkCertificateStatus(X509Certificate signingCert) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException {
    X509Certificate trustedCA = null;

        // controllo se il certificato è scaduto
        try {
            signingCert.checkValidity();
        }
        catch (CertificateExpiredException ex) {
            return OCSPStatus.EXPIRED;
        }
        catch (CertificateNotYetValidException ex) {
            return OCSPStatus.NOT_YET_VALID;
        }

        if (checkCertificateRevocation) {
            if (!UseOnlineTSLXml) {
                if (logger.isInfoEnabled()) {
                	logger.info("Cerco il certificato della C.A. emittente usando il keystore locale...");
                }
                KeyStore ks = KeyStore.getInstance("JKS");

                ks.load(new FileInputStream(keystorePath), keystorePassword);

                // cerco il certificato della CA emittente nel keystore
                Enumeration aliases = ks.aliases();

                while (aliases.hasMoreElements() && trustedCA == null) {
                    String alias = (String) aliases.nextElement();
                    X509Certificate cert = (X509Certificate)ks.getCertificate(alias);
                    if (cert.getSubjectX500Principal().equals(signingCert.getIssuerX500Principal())) {
                        trustedCA = cert;
                    }
                }
            }
            else {
                if (logger.isInfoEnabled()) {
                	logger.info("Cerco il certificato della C.A. cercandolo nel file TSLXml on-line");
                }
                TSLCertificateFinder certificateFinder = new TSLCertificateFinder(workDirectory, CSPTrustedListsURL);
                if (prefix != null)
                    certificateFinder.setDownloadedXmlsPrefix(prefix);
                trustedCA = certificateFinder.getIssuerCertFromTSLXml(signingCert);
            }

            if (trustedCA != null) {
                if (logger.isInfoEnabled()) {
                	logger.info("Trovato il certificato della C.A.: " + trustedCA.getSubjectDN());
                }
                // controllo lo stato di revoca del certificato
                return new OCSPStatusChecker().check(trustedCA, signingCert);
            }
            else {
                if (logger.isInfoEnabled()) {
                	logger.info("Certificato della C.A. non trovato");
                }
                return OCSPStatus.UNKNOWN;
            }
        }
        else {
            return null;
        }
    }

    /** Ritorna lo stato di una firma specifica
     *
     * @param signName il nome della firma della quale si desidera conoscere lo stato
     * @return un oggetto "SignStatus" che rappresenta lo stato della firma
     */
    public SignStatus getSignResult(String signName) {
        for (int i=0; i<signaturesResults.size(); i++) {
            SignStatus sign = (SignStatus)signaturesResults.get(i);
            if (sign.getSignatureName().equals(signName)) {
               return sign;
            }
        }
        return null;
    }

    /** Ritorna lo stato delle firme del file
     *
     * @return un "ArrayList" di oggetti "SignStatus" che rappresentano lo stato delle firme
     */
    public ArrayList getSignsResults() {
        return signaturesResults;
    }

    /** Ritorna lo stato delle firme del file in formato stringa
     *
     *  @return una stringa di più righe separate da un "\n" rappresentante lo stato delle firme. Ogni riga rappresenta una firma. La stringa è così formata:
     *  se il file è un pdf: "nome_campo_firma/codice_fiscale;booleano_indicante_la_validità_della_firma;tipo_di_errore";
     *  se il file è un p7m: "codice_fiscale;booleano_indicante_la_validità_della_firma;tipo_di_errore";
     *  il campo "booleano_indicante_la_validità_della_firma" vale:
     *      "true",  se la firma è valida e il certificato è valido;
     *      "false", se la firma non è valida (ad esempio, perché il documento è stato modificato dopo la firma);
     *      "null",  se non è stato possibile controllare la firma o il certificato, o se il certificato non è valido (ad esempio perché revocato);
     *  il campo "tipo_di_errore" può essere:
     *      "null" se la firma è valida e il certificato è valido;
     *      "firma non valida" se la firma non è valida (ad esempio, perché il documento è stato modificato dopo la firma);
     *      "impossibile verificare la firma" se non è stato possibile controllare la firma a causa di un errore;
     *      "impossibile leggere il certificato di firma" se non è stato possibile leggere il certificato della firma;
     *      "impossibile verificare lo stato di revoca del certificato" se non è stato possibile eseguire il controllo di verifica on-line del certificato (ad esempio per indisponibilità del server, oppure perché non è stato passato l'indirizzo della servlet di verifica);
     *      "certificato revocato" se il certificato è revocato
     */
    public String getSignsResultString() {
        return signaturesResultsString;
    }

    /** Ritorna i nomi di tutte le firma presenti nel file
     *
     * @return un "ArrayList" di stringhe contente i nomi delle firme
     */
    public ArrayList getSignatureNames() {
        ArrayList namesList = new ArrayList();
        for (int i=0; i<signaturesResults.size(); i++) {
            SignStatus sign = (SignStatus)signaturesResults.get(i);
            namesList.add(sign.getSignatureName());
        }
        return namesList;
    }

    /** Indica se tutte le firme sono valide
     *
     * @return "true" se tutte le firme e i relativi certificati sono valide, "false" altrimenti
     */
    public boolean isAllSignOK() {
        return allSignOK;
    }

}
