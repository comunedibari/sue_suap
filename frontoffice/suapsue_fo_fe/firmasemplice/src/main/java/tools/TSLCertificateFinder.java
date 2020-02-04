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


import com.sun.org.apache.xpath.internal.XPathAPI;
import java.io.File;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;
import sun.security.x509.X509CertImpl;

/**
 *
 * @author Giuseppe
 */
public class TSLCertificateFinder {
	
	private static Logger logger = LoggerFactory.getLogger(TSLCertificateFinder.class);
	
private File workDirectory;
private String prefix = null;
private String CSPTrustedListsURL;

    /** Crea un oggetto TSLCertificateFinder che permette di estrarre la C.A. emittente di un certificato dal file TSLXml messo a disposizione on-line dall'ente governativo preposto
     * Come directory di lavoro sarà usata la direcotry ottenuta dal comando System.getProperty("java.io.tmpdir")
     *
     * @param CSPTrustedListsURL indirizzo del file xml contenente la lista dei certificatori accreditati (Trusted Lists of Certification Service Providers)
     */
    public TSLCertificateFinder(String CSPTrustedListsURL) {
        this.CSPTrustedListsURL = CSPTrustedListsURL;
        this.workDirectory = new File(System.getProperty("java.io.tmpdir") + "/" + this.getClass().getCanonicalName() + "_workDirecory");
        if (!workDirectory.exists())
            workDirectory.mkdir();
    }

    /** Crea un oggetto TSLCertificateFinder che permette di estrarre la C.A. emittente di un certificato dal file TSLXml messo a disposizione on-line dall'ente governativo preposto
     *
     * @param CSPTrustedListsURL indirizzo del file xml contenente la lista dei certificatori accreditati (Trusted Lists of Certification Service Providers)
     * @param workDirectory direcotry temporanea in cui salvare il file TSLXml scaricato
     */
    public TSLCertificateFinder(String workDirectory, String CSPTrustedListsURL) {
        this.CSPTrustedListsURL = CSPTrustedListsURL;
        this.workDirectory = new File(workDirectory + "/" + this.getClass().getCanonicalName() + "_workDirecory");
        if (!this.workDirectory.exists())
            this.workDirectory.mkdir();
    }

    /** Setta un prefisso da aggiungere ai file XML scaricati
     *
     * @param prefix il prefisso da aggiungere ai file XML scaricati
     */
    public void setDownloadedXmlsPrefix(String prefix) {
        this.prefix = prefix;
    }

    /** Ritorna il certificato della C.A. che ha emesso il certificato passato in input
     *
     * @param cert in certificato del quale si desidera ottenere il certificato della C.A. emittente
     * @return Il certificato della C.A. emittente, "null" se il certificato non viene trovato
     * @throws CertificateException
     */
    public X509Certificate getIssuerCertFromTSLXml(X509Certificate cert) throws CertificateException {

        // scarico la lista dei certificatori accreditati (Trusted Lists of Certification Service Providers)
        File SCPTrustedList = downloadCSPTrustedList();

        // estraggo la nazione del certificato
        String issuerInfo = cert.getIssuerX500Principal().getName(X500Principal.RFC2253);
        int countryStringPointer = issuerInfo.indexOf("C=") + 2;
        String county = issuerInfo.substring(countryStringPointer);

        // trovo l'url del file TSLXml contenente la lista delle C.A. attendibili
        String TSLXmlUrl = null;
        try {
            TSLXmlUrl = getTSLXmlUrl(SCPTrustedList, county);
        }
        catch (TransformerConfigurationException ex) {
            logger.error("Errore nell'esecuzione dell'xpath per il recupero dell' url del file \"TSLXml\"", ex);
            return  null;
        }
        catch (TransformerException ex) {
            logger.error("Errore nell'esecuzione dell'xpath per il recupero dell' url del file \"TSLXml\"", ex);
            return  null;
        }

        if (TSLXmlUrl == null) {
            logger.warn("Errore nel recupero dell' url del file \"TSLXml\"");
            return null;
        }

        // scarico il file TSLXml
        File TSLXml = downloadTSLXml(TSLXmlUrl);

        // ottengo il certificato della C.A.
        X509Certificate issuerCert = null;
        try {
            issuerCert = getIssuerCertFromLocalTSLXml(TSLXml.getAbsolutePath(), cert);
        }
        catch (Exception ex) {
            logger.error("Errore nella ricerca del certificato nel file TSLXml", ex);
            return null;
        }
        if (prefix != null) {
            SCPTrustedList.delete();
            TSLXml.delete();
        }
        return issuerCert;
    }

    // scarica il file CSPTrustedList
    private File downloadCSPTrustedList() {
    File CSPTrustedList = null;

        if (prefix == null) {
            try {
                CSPTrustedList = new FileDownloader(true).downloadFileFromUrl(new URL(CSPTrustedListsURL), workDirectory + "/", true);
            }
            catch (Exception ex) {
                logger.error("Errore nello scaricamento del file TSLXml", ex);
            }
        }
        else {
            try {
                CSPTrustedList = new FileDownloader(true).downloadFileFromUrl(new URL(CSPTrustedListsURL), workDirectory + "/" + prefix, false);
            }
            catch (Exception ex) {
                logger.error("Errore nello scaricamento del file TSLXml", ex);
            }
        }
        return CSPTrustedList;
    }

    // scarica il file TSLXml
    private File downloadTSLXml(String TSLXmlUrl) {
    File TSLXml = null;

        if (prefix == null) {
            try {
                TSLXml = new FileDownloader(true).downloadFileFromUrl(new URL(TSLXmlUrl), workDirectory + "/", true);
            }
            catch (Exception ex) {
                logger.error("Errore nello scaricamento del file TSLXml", ex);
            }
        }
        else {
            try {
                TSLXml = new FileDownloader(true).downloadFileFromUrl(new URL(TSLXmlUrl), workDirectory + "/" + prefix, false);
            }
            catch (Exception ex) {
                logger.error("Errore nello scaricamento del file TSLXml", ex);
            }
        }
        return TSLXml;
    }

    // estrae, con una xpath, l'indirizzo del file TSLXml da scaricare dal file CSPTrustedList
    private String getTSLXmlUrl(File CSPTrustedList, String contry) throws TransformerConfigurationException, TransformerException {
        if (CSPTrustedList == null || !CSPTrustedList.exists()) {
            logger.warn("Errore nel parsing del file CSPTrustedList: nessun file CSPTrustedList passato");
            return null;
        }
        Document xmlDocument = ManageXMLFile.readFileXml(CSPTrustedList.getAbsolutePath());

        String xpathString = "//tsl:OtherTSLPointer[tsl:AdditionalInformation/tsl:OtherInformation/tsl:SchemeTerritory='" + contry + "' and "
                + "tsl:AdditionalInformation/tsl:OtherInformation/tslx:MimeType='application/vnd.etsi.tsl+xml']/tsl:TSLLocation";

        Transformer serializer = TransformerFactory.newInstance().newTransformer();
        serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        // Use the simple XPath API to select a nodeIterator.
        NodeIterator nl = XPathAPI.selectNodeIterator(xmlDocument, xpathString);
        Node n = nl.nextNode();
        String CSPTrustedListUrl = n.getFirstChild().getNodeValue();
        return CSPTrustedListUrl;
    }

    // estrae, con una xpath, il certificato della C.A. emittente del certificato passato dal file TSLXml passato
    private X509Certificate getIssuerCertFromLocalTSLXml(String TSLXmlPath, X509Certificate cert) throws CertificateException, TransformerConfigurationException, TransformerException {

        String issuerName = cert.getIssuerDN().getName();

        if (TSLXmlPath == null) {
            logger.warn("Errore nel parsing del file TSLXml: nessun file TSLXml passato");
            return null;
        }
        Document xmlDocument = ManageXMLFile.readFileXml(TSLXmlPath);

        String xpathString = "/tsl:TrustServiceStatusList/tsl:TrustServiceProviderList/tsl:TrustServiceProvider/tsl:TSPServices/tsl:TSPService/tsl:ServiceInformation/tsl:ServiceDigitalIdentity/tsl:DigitalId/tsl:X509Certificate"
                    + "[translate(translate(../../../tsl:ServiceName/tsl:Name, \"ABCDEFGHIJKLMNOPQRSTUVWXYZ\", \"abcdefghijklmnopqrstuvwxyz\"), \" \", \"\") = "
                    + "translate(translate(\"" + issuerName + "\", \"ABCDEFGHIJKLMNOPQRSTUVWXYZ\", \"abcdefghijklmnopqrstuvwxyz\"), \" \", \"\")]/text()";

        Transformer serializer = TransformerFactory.newInstance().newTransformer();
        serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        // Use the simple XPath API to select a nodeIterator.
        NodeIterator nl = XPathAPI.selectNodeIterator(xmlDocument, xpathString);
        Node n = nl.nextNode();
        String base64cert = n.getNodeValue();


//
//      String base64cert = "";
//
//      Node n = nl.nextNode();
//        try {
//            // Serialize the found nodes to System.out
//            serializer.transform(new DOMSource(n), new StreamResult(System.out));
//            base64cert = n.getNodeValue();
//        }
//        catch (TransformerException ex) {
//            ex.printStackTrace();
//        }




       /*

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext(new NamespaceContext() {
            public String getNamespaceURI(String prefix) {
                if (prefix == null) throw new NullPointerException("Null prefix");
                else if ("tsl".equals(prefix)) return "http://uri.etsi.org/02231/v2#";
                else if ("xml".equals(prefix)) return XMLConstants.XML_NS_URI;
                return XMLConstants.NULL_NS_URI;
            }

            public String getPrefix(String namespaceURI) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public Iterator getPrefixes(String namespaceURI) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        Object result = null;
        try {
            String xpathIstr = "/tsl:TrustServiceStatusList/tsl:TrustServiceProviderList/tsl:TrustServiceProvider/tsl:TSPServices/tsl:TSPService/tsl:ServiceInformation/tsl:ServiceDigitalIdentity/tsl:DigitalId/tsl:X509Certificate"
                    + "[translate(translate(../../../tsl:ServiceName/tsl:Name, \"ABCDEFGHIJKLMNOPQRSTUVWXYZ\", \"abcdefghijklmnopqrstuvwxyz\"), \" \", \"\") = "
                    + "translate(translate(\"" + issuerName + "\", \"ABCDEFGHIJKLMNOPQRSTUVWXYZ\", \"abcdefghijklmnopqrstuvwxyz\"), \" \", \"\")]/text()";

            XPathExpression expr = xpath.compile(xpathIstr);
            result = expr.evaluate(xmlDocument, XPathConstants.NODESET);
        }
        catch (Exception ex) {
            ex.printStackTrace(System.out);
            return null;
        }
        
        NodeList nodes = (NodeList) result;
        String nodeValue = null;
        try {
            nodeValue = nodes.item(0).toString();
        }
        catch (NullPointerException ex) {
            System.out.println("Certificato della C.A. emittente del certificato non trovato\nEmittente cercato: \"" + issuerName + "\"");
            return null;
        }

        String base64cert = null;
        try {
            base64cert = nodeValue.substring(8, nodeValue.length() - 1);
        }
        catch (Exception ex) {
            ex.printStackTrace(System.out);
            return null;
        }
*/
        X509Certificate issuerCert = new X509CertImpl(tools.Base64Coder.decodeLines(base64cert));
        return issuerCert;
    }
}
