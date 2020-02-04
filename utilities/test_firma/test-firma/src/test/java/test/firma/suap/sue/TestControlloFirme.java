package test.firma.suap.sue;

import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.xml.sax.InputSource;

import it.ictechnology.certificate.parser.CertificateParser;
import it.ictechnology.certificate.parser.impl.ParserFactory;
import it.infocamere.util.signature.Signature;
import it.infocamere.util.signature.impl.bouncyCastle.FirmaUtils;

public class TestControlloFirme
{
	private static final Logger logger = LoggerFactory.getLogger(TestControlloFirme.class.getName());
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testAutentica()
	{
		HashMap<String, String> ret = new HashMap();
		CertificateParser certificateParser = null;
		boolean ok = true;
		try
		{
			Resource res = new ClassPathResource("certificate-descriptions.xml");
//			Resource resDocAccettato = new ClassPathResource("documento_accettato.p7m");
//			Resource resDocAccettato = new ClassPathResource("documento_rifiutato.p7m");
//			Resource resDocAccettato = new ClassPathResource("ProcuraSpeciale_firmato.pdf.p7m");
//			Resource resDocAccettato = new ClassPathResource("doc01.pdf.p7m");
			
//			Resource resDocAccettato = new ClassPathResource("1526631266149_tav03.pdf.p7m");
//			Resource resDocAccettato = new ClassPathResource("1526631868297_tav01.pdf.p7m");
//			Resource resDocAccettato = new ClassPathResource("1526983918882_riepilogo.pdf.p7m");
			
			
//			Resource resDocAccettato = new ClassPathResource("CSCFPP52E12E038E-018595-9174309_20180531/1526631266149_tav03.pdf.p7m");
//			Resource resDocAccettato = new ClassPathResource("CSCFPP52E12E038E-018595-9174309_20180531/1526631503596_doc03.pdf.p7m");
//			Resource resDocAccettato = new ClassPathResource("CSCFPP52E12E038E-018595-9174309_20180531/1526631623272_doc08.pdf.p7m");
//			Resource resDocAccettato = new ClassPathResource("CSCFPP52E12E038E-018595-9174309_20180531/1526631868297_tav01.pdf.p7m");
//			Resource resDocAccettato = new ClassPathResource("CSCFPP52E12E038E-018595-9174309_20180531/1526983250253_doc01.pdf.p7m");
//			Resource resDocAccettato = new ClassPathResource("CSCFPP52E12E038E-018595-9174309_20180531/1526983292447_doc02.pdf.p7m");
//			Resource resDocAccettato = new ClassPathResource("CSCFPP52E12E038E-018595-9174309_20180531/1526983356543_tav02.dwf.p7m");
//			Resource resDocAccettato = new ClassPathResource("CSCFPP52E12E038E-018595-9174309_20180531/1526983389566_doc04.pdf.p7m");
//			Resource resDocAccettato = new ClassPathResource("CSCFPP52E12E038E-018595-9174309_20180531/1527145621480_riepilogo.pdf.p7m");

//			Resource resDocAccettato = new ClassPathResource("CSCFPP52E12E038E-018595-9174309_20180601/tav03.pdf.p7m");
//			Resource resDocAccettato = new ClassPathResource("CSCFPP52E12E038E-018595-9174309_20180601/doc03.pdf.p7m");
//			Resource resDocAccettato = new ClassPathResource("CSCFPP52E12E038E-018595-9174309_20180601/doc08.pdf.p7m");
//			Resource resDocAccettato = new ClassPathResource("CSCFPP52E12E038E-018595-9174309_20180601/tav01.pdf.p7m");
//			Resource resDocAccettato = new ClassPathResource("CSCFPP52E12E038E-018595-9174309_20180601/doc01.pdf.p7m");
//			Resource resDocAccettato = new ClassPathResource("CSCFPP52E12E038E-018595-9174309_20180601/doc02.pdf.p7m");
//			Resource resDocAccettato = new ClassPathResource("CSCFPP52E12E038E-018595-9174309_20180601/tav02.dwf.p7m");
//			Resource resDocAccettato = new ClassPathResource("CSCFPP52E12E038E-018595-9174309_20180601/doc04.pdf.p7m");
//			Resource resDocAccettato = new ClassPathResource("CSCFPP52E12E038E-018595-9174309_20180601/riepilogoCSCFPP52E12E038E-018595-9174309.pdf.p7m");
			Resource resDocAccettato = new ClassPathResource("TSTTST18A01H703G-018597-1866708/riepilogoTSTTST18A01H703G-018597-1866708.pdf.p7m");
			
			ParserFactory pf = new ParserFactory();
			certificateParser = pf.parse(new InputSource(res.getInputStream()));
			CMSSignedData document = new CMSSignedData(resDocAccettato.getInputStream());
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
		}
		catch (Exception e)
		{
			logger.error("Errore nella verifica firma", e);
		}
	}
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Test
//	public void testFirma()
//	{
//		HashMap<String, String> ret = new HashMap();
//		CertificateParser certificateParser = null;
//		boolean ok = true;
//		try
//		{
//			Resource res = new ClassPathResource("certificate-descriptions.xml");
//			Resource resDocAccettato = new ClassPathResource("Firma.cer");
//			ParserFactory pf = new ParserFactory();
//			certificateParser = pf.parse(new InputSource(res.getInputStream()));
//			CMSSignedData document = new CMSSignedData(resDocAccettato.getInputStream());
//			if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
//				Security.addProvider(new BouncyCastleProvider());
//			}
//			CertStore certs = document.getCertificatesAndCRLs("Collection", BouncyCastleProvider.PROVIDER_NAME);
//			SignerInformationStore signers = document.getSignerInfos();
//			for (Iterator it = signers.getSigners().iterator(); it.hasNext();) {
//				SignerInformation signer = (SignerInformation) it.next();
//				Collection certCollection = certs.getCertificates(signer.getSID());
//				Iterator i$ = certCollection.iterator();
//				Certificate cert = (Certificate) i$.next();
//				Signature s = getSign((X509Certificate) cert, certificateParser);
//				ret.put(s.getCf(), s.getFirmatario());
//			}
//		}
//		catch (Exception e)
//		{
//			logger.error("Errore nella verifica firma", e);
//		}
//	}
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Test
//	public void testVerificaFirma()
//	{
//		HashMap<String, String> ret = new HashMap();
//		CertificateParser certificateParser = null;
//		boolean ok = true;
//		try
//		{
//			Resource res = new ClassPathResource("certificate-descriptions.xml");
//			Resource resDocAccettato = new ClassPathResource("documento_accettato.p7m");
//			ParserFactory pf = new ParserFactory();
//			certificateParser = pf.parse(new InputSource(res.getInputStream()));
//			CMSSignedData document = new CMSSignedData(resDocAccettato.getInputStream());
//			if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
//				Security.addProvider(new BouncyCastleProvider());
//			}
//			CertStore certs = document.getCertificatesAndCRLs("Collection", BouncyCastleProvider.PROVIDER_NAME);
//			SignerInformationStore signers = document.getSignerInfos();
//			for (Iterator it = signers.getSigners().iterator(); it.hasNext();) {
//				SignerInformation signer = (SignerInformation) it.next();
//				Collection certCollection = certs.getCertificates(signer.getSID());
//				Iterator i$ = certCollection.iterator();
//				Certificate cert = (Certificate) i$.next();
//				Signature s = getSign((X509Certificate) cert, certificateParser);
//				ret.put(s.getCf(), s.getFirmatario());
//			}
//		}
//		catch (Exception e)
//		{
//			logger.error("Errore nella verifica firma", e);
//		}
//	}
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Test
//	public void testVerificaFirmaDocRifiutato()
//	{
//		HashMap<String, String> ret = new HashMap();
//		CertificateParser certificateParser = null;
//		boolean ok = true;
//		try
//		{
//			Resource res = new ClassPathResource("certificate-descriptions.xml");
//			Resource resDocRifiutato = new ClassPathResource("documento_rifiutato.p7m");
//			ParserFactory pf = new ParserFactory();
//			certificateParser = pf.parse(new InputSource(res.getInputStream()));
//			CMSSignedData document = new CMSSignedData(resDocRifiutato.getInputStream());
//			if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
//				Security.addProvider(new BouncyCastleProvider());
//			}
//			CertStore certs = document.getCertificatesAndCRLs("Collection", BouncyCastleProvider.PROVIDER_NAME);
//			SignerInformationStore signers = document.getSignerInfos();
//			for (Iterator it = signers.getSigners().iterator(); it.hasNext();) {
//				SignerInformation signer = (SignerInformation) it.next();
//				Collection certCollection = certs.getCertificates(signer.getSID());
//				Iterator i$ = certCollection.iterator();
//				Certificate cert = (Certificate) i$.next();
//				Signature s = getSign((X509Certificate) cert, certificateParser);
//				ret.put(s.getCf(), s.getFirmatario());
//			}
//		}
//		catch (Exception e)
//		{
//			logger.error("Errore nella verifica firma", e);
//		}
//	}	
	protected static Signature getSign(X509Certificate cert, CertificateParser parser) {
		return FirmaUtils.getSign(cert, parser);
	}
//    @SuppressWarnings("rawtypes")
//	private boolean verificaFirma(HashMap<String, String> listaCertificati, HashMap listaFirme) {
//        boolean controllo = true;
//        if (listaFirme != null && !listaFirme.isEmpty()) {
//            for (Iterator it = listaFirme.keySet().iterator(); it.hasNext();) {
//                String cfObbligato = (String) it.next();
//                boolean ok = false;
//                if (cfObbligato != null) {
//                    for (Iterator iterator = listaCertificati.keySet().iterator(); iterator.hasNext();) {
//                        String cfCertificato = (String) iterator.next();
//                        if (cfCertificato != null) {
//                            if (cfCertificato.toUpperCase().equalsIgnoreCase(cfObbligato)) {
//                                ok = true;
//                                break;
//                            }
//                        }
//                    }
//                }
//                if (!ok) {
//                    controllo = false;
//                    break;
//                }
//            }
//        }
//        return controllo;
//    }	
}
