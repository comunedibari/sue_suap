/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility;

import it.ictechnology.certificate.parser.CertificateParser;
import it.ictechnology.certificate.parser.impl.ParserFactory;
import it.infocamere.util.signature.Signature;
import it.infocamere.util.signature.impl.bouncyCastle.FirmaUtils;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SportelloBean;
import it.people.process.AbstractPplProcess;
import it.people.process.sign.entity.SignedInfo;
import it.people.security.Exception.SignException;
import it.people.security.SignResponseManager;
import it.people.util.MessageBundleHelper;
import it.people.wrappers.IRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author piergiorgio
 */
public class ControlloFirme {

    private String LISTA_FIRME = "$$firme";
    private String FLG_FIRME = "$$flg_firme";

    public boolean ControlloFirme(AbstractPplProcess process, IRequestWrapper request, SportelloBean sportello) {
        boolean ok = true;
        SignResponseManager srmgr = null;
        SignedInfo sInfo = null;
        HashMap<String, String> ret = new HashMap();
        try {
            ProcessData dataForm = (ProcessData) process.getData();
            boolean controllo = false;
            if (dataForm.getFirmaBookmark().equalsIgnoreCase(Costant.conFirmaLabel) && !dataForm.getTipoBookmark().equalsIgnoreCase(Costant.bookmarkTypeLivello2Label)) {
                if (sportello.isOnLineSign() || sportello.isOffLineSign()) {
                    controllo = true;
                }
            }
            if (controllo) {
                if (process.isOffLineSign() && process.isWaitingForOffLineSignedDocument() && process.getOffLineSignDownloadedDocumentHash() != null
                        && !process.getOffLineSignDownloadedDocumentHash().equalsIgnoreCase("")) {
                    srmgr = new SignResponseManager(process);
                } else {
                    srmgr = new SignResponseManager(request.getUnwrappedRequest());
                }

                sInfo = srmgr.process();

                URL certificateDescriptions = ControlloFirme.class.getResource("/it/people/resources/certificate-descriptions.xml");
                CertificateParser certificateParser = null;

                certificateParser = new ParserFactory().parse(new InputSource(certificateDescriptions.toURI().toString()));
                CMSSignedData document = new CMSSignedData(sInfo.getPkcs7Data());
                if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
                    Security.addProvider(new BouncyCastleProvider());
                }
                CertStore certs = document.getCertificatesAndCRLs("Collection", BouncyCastleProvider.PROVIDER_NAME);
                SignerInformationStore signers = document.getSignerInfos();
                for (Iterator it = signers.getSigners().iterator(); it.hasNext();) {
                    SignerInformation signer = (SignerInformation) it.next();
                    Collection certCollection = certs.getCertificates(signer.getSID());
                    Iterator i$ = certCollection.iterator();
                    Certificate cert = (Certificate) i$.next();
                    Signature s = getSign((X509Certificate) cert, certificateParser);
                    ret.put(s.getCf(), s.getFirmatario());
                }
                HashMap totale = (HashMap) dataForm.getCustomObject();
                if (totale != null) {
                    boolean flg = false;
                    if (totale.get(FLG_FIRME) != null) {
                        if (((String) totale.get(FLG_FIRME)).equalsIgnoreCase("S")) {
                            flg = true;
                        }
                    }
                    if (flg) {
                        if (totale.get(LISTA_FIRME) != null) {
                            HashMap listaFirme = (HashMap) totale.get(LISTA_FIRME);
                            ok = verificaFirma(ret, listaFirme);
                        }
                    }
                }
                if (!ok) {
                    String offLineSignFileUploadLabel = MessageBundleHelper.message("label.offLineSign.uploadSignedDocument", null, process.getProcessName(), process.getCommune().getKey(),
                            process.getContext().getLocale());
                    process.addServiceError(MessageBundleHelper.message("errors.firmatari", new Object[]{offLineSignFileUploadLabel},
                            process.getProcessName(), process.getCommune().getKey(), process.getContext().getLocale()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    protected Signature getSign(X509Certificate cert, CertificateParser parser) {
        FirmaUtils fu = new FirmaUtils();
        return fu.getSign(cert, parser);
    }

    public void cercaFirme(ProcessData dataForm, String xml, HttpServletRequest request) {
        HashMap ret = new HashMap();
        try {
            InputStream is = new ByteArrayInputStream(xml.getBytes(request.getCharacterEncoding()));
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document dom = db.parse(is);
            Element el = (Element) dom.getElementsByTagName("listaFirme").item(0);
            if (el != null) {
                NodeList nodes = el.getChildNodes();
                for (int i = 0; i < nodes.getLength(); i++) {
                    Node n = nodes.item(i);
                    if (n.getNodeType() == Node.ELEMENT_NODE) {
                        if ("listaFirma".equals(n.getNodeName())) {
                            String cf = null;
                            String nome = null;
                            NodeList nl = n.getChildNodes();
                            for (int j = 0; j < nl.getLength(); j++) {
                                Node n1 = nl.item(j);
                                if ("codiceFiscale".equals(n1.getNodeName())) {
                                    if (n1.getChildNodes() != null) {
                                        cf = n1.getFirstChild().getTextContent();
                                    }
                                }
                                if ("cognomeNome".equals(n1.getNodeName())) {
                                    if (n1.getChildNodes() != null) {
                                        nome = n1.getFirstChild().getTextContent();
                                    }
                                }
                            }
                            if (cf != null) {
                                ret.put(cf, nome);
                            }
                        }

                    }
                }
            }
            String flg = "N";
            el = (Element) dom.getElementsByTagName("controlloFirme").item(0);
            if (el != null) {
                if (el.getFirstChild() != null) {
                    flg = el.getFirstChild().getTextContent();
                }
            }
            if (ret != null && !ret.isEmpty()) {
                HashMap totale = new HashMap();
                totale.put(LISTA_FIRME, ret);
                totale.put(FLG_FIRME, flg);
                dataForm.setCustomObject(totale);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean verificaFirma(HashMap<String, String> listaCertificati, HashMap listaFirme) {
        boolean controllo = true;
        if (listaFirme != null && !listaFirme.isEmpty()) {
            for (Iterator it = listaFirme.keySet().iterator(); it.hasNext();) {
                String cfObbligato = (String) it.next();
                boolean ok = false;
                if (cfObbligato != null) {
                    for (Iterator iterator = listaCertificati.keySet().iterator(); iterator.hasNext();) {
                        String cfCertificato = (String) iterator.next();
                        if (cfCertificato != null) {
                            if (cfCertificato.toUpperCase().equalsIgnoreCase(cfObbligato)) {
                                ok = true;
                                break;
                            }
                        }
                    }
                }
                if (!ok) {
                    controllo = false;
                    break;
                }
            }
        }
        return controllo;
    }
}
