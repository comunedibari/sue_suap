package it.infocamere.util.signature.impl.bouncyCastle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.ictechnology.certificate.parser.CertificateParser;
import it.ictechnology.certificate.util.UtenteImpl;
import it.infocamere.util.signature.Signature;
import it.infocamere.util.signature.helpers.InvalidSignatureException;

public class FirmaUtils
{
	private static final Logger logger = LoggerFactory.getLogger(FirmaUtils.class);

	public static final String DIGEST_ALGORITHM_SHA1 = "SHA-1";

	public static final String DIGEST_ALGORITHM_SHA256 = "SHA-256";

	public static final String DIGEST_ALGORITHM_SHA384 = "SHA-384";

	public static final String DIGEST_ALGORITHM_SHA512 = "SHA-512";
	private static final Pattern patternCF = Pattern.compile("[A-Z]{6}[0-9LMNPQRSTUV]{2}[A-Z][0-9LMNPQRSTUV]{2}[A-Z][0-9LMNPQRSTUV]{3}[A-Z]");

	public static Signature getSafeSign(X509Certificate xCert, CertificateParser certificateParser)
	{
		Signature sign = getUnsafeSign(xCert, certificateParser);

		if ((sign.getCf() == null) || ("".equals(sign.getCf())))
		{
			logger.warn("Attenzione, non e' stato possibile recuperare il CodiceFiscale!");
		}
		return sign;
	}

	public static Signature getSign(X509Certificate xCert, CertificateParser certificateParser)
	{
		Signature sign = getUnsafeSign(xCert, certificateParser);

		if ((sign.getCf() == null) || ("".equals(sign.getCf())))
		{
			throw new InvalidSignatureException("Impossibile recuperare il CF!");
		}
		return sign;
	}

	private static Signature getUnsafeSign(X509Certificate xCert, CertificateParser certificateParser)
	{
		Signature sign = new Signature();

		sign.setX509Certificate(xCert);

		sign.setSeriale(xCert.getSerialNumber().toString(16) + " (" + xCert.getSerialNumber().toString() + ")");

		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss z");
		sign.setValidoDal(df.format(xCert.getNotBefore()));
		sign.setValidoAl(df.format(xCert.getNotAfter()));

		UtenteImpl datiFirmatario = new UtenteImpl();

		logger.info("Estrazione dei dati del firmatario del certificato:\n" + xCert.toString());

		certificateParser.parse(xCert, datiFirmatario);

		logger.info("Dati estratti dal certificato. " + datiFirmatario);

		sign.setFirmatario(datiFirmatario.getNome() + " " + datiFirmatario.getCognome());

		sign.setStato(datiFirmatario.getCertificato().getStato());
		sign.setOrganizzazione(datiFirmatario.getCertificato().getOrganizzazione());
		sign.setUnita(datiFirmatario.getCertificato().getUnitaOrganizzativa());
		sign.setEnte(datiFirmatario.getCertificato().getEnteCertificatore());

		String dirtyCF = datiFirmatario.getCodiceFiscale();

		if ((dirtyCF == null) || ("".equals(dirtyCF)))
		{
			return sign;
		}
		Matcher m = patternCF.matcher(dirtyCF);

		if (m.find())
		{
			String cf = m.group();

			sign.setCf(cf);

			logger.info("Il CF estratto e ripulito e':" + cf);
		}
		else
		{
			logger.warn("Attenzione, impossibile trovare un codice fiscale valido dentro: " + dirtyCF);
			sign.setCf(dirtyCF);
		}

		return sign;
	}

	public static final String getDigestBase64(InputStream is, String algorithm)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.reset();

			byte[] bytes = new byte['က'];
			int numBytes;
			while ((numBytes = is.read(bytes)) != -1)
			{
				md.update(bytes, 0, numBytes);
			}
			return new String(Base64.encode(md.digest()));

		}
		catch (Exception e)
		{

			throw new RuntimeException("Impossibile calcolare il digest dal documento firmato", e);
		}
	}

	public static final String getDigestBase64(CMSSignedData document, String algorithm)
	{
		CMSProcessable sd = document.getSignedContent();
		File f = null;

		try
		{
			f = File.createTempFile("signedContent", ".pdf");
			f.deleteOnExit();

			FileOutputStream fos = new FileOutputStream(f);
			sd.write(fos);
			fos.close();

			String d = getDigestBase64(new FileInputStream(f), algorithm);

			return d;
		}
		catch (Exception e)
		{
			throw new RuntimeException("Impossibile calcolare il digest dal documento firmato", e);
		}
		finally
		{
			try
			{
				f.delete();
			}
			catch (Exception ignored)
			{
			}
		}
	}
}