package it.infocamere.util.signature.impl.bouncyCastle;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.ictechnology.certificate.parser.CertificateParser;
import it.infocamere.util.signature.Signature;
import it.infocamere.util.signature.SignedDocument;

public class BCSignedDocumentImpl implements SignedDocument
{
	private static final Logger logger = LoggerFactory.getLogger(BCSignedDocumentImpl.class);

	private CMSSignedData document = null;

	private byte[] origBytes = null;
	private File origFile = null;

	private boolean parsed = false;
	private boolean valid = false;
	private boolean signed = false;

	private List<Signature> signatures = null;
	private Set<String> signers = null;
	private Map<String, String> hash = null;

	private final CertificateParser certificateParser;

	public BCSignedDocumentImpl(byte[] bytes, CertificateParser certificateParser)
	{
		this.certificateParser = certificateParser;

		try
		{
			this.document = new CMSSignedData(bytes);

			this.signed = true;
		}
		catch (CMSException e)
		{
			logger.debug("Impossibile istanziare il documento da bytes. Probabilmente non è firmato.", e);

			this.origBytes = bytes;
		}
	}

	public BCSignedDocumentImpl(File file, CertificateParser certificateParser) throws FileNotFoundException
	{
		this.certificateParser = certificateParser;

		creaDaFile(file);
	}

	public BCSignedDocumentImpl(InputStream inputStream, CertificateParser certificateParser)
	{
		this.certificateParser = certificateParser;

		File f = null;

		try
		{
			f = File.createTempFile("tempStreamCopy", ".pdf");
			f.deleteOnExit();

			FileOutputStream fos = new FileOutputStream(f);

			IOUtils.copy(inputStream, fos);

			fos.close();
		}
		catch (Exception e)
		{
			throw new RuntimeException("Impossibile copiare l'inputstream nel file di appoggio: " + (f == null ? "--" : f.getName()), e);
		}

		logger.debug("Creato file di appoggio: " + f.getName());

		try
		{
			creaDaFile(f);
		}
		catch (FileNotFoundException e)
		{
			throw new RuntimeException("Impossibile istanziare il documento dal file di appoggio appena creato: " + (f == null ? "--" : f.getName()), e);
		}
	}

	private void creaDaFile(File file) throws FileNotFoundException
	{
		try
		{
			this.document = new CMSSignedData(new FileInputStream(file));

			this.signed = true;
		}
		catch (CMSException e)
		{
			logger.info("Impossibile istanziare il documento da file. Probabilmente non è firmato.", e);

			this.origFile = file;
		}
	}

	public byte[] getDocumentBytes()
	{
		try
		{
			if (this.signed)
			{
				return this.document.getEncoded();
			}
			if (this.origBytes != null)
				return this.origBytes;
			if (this.origFile != null)
			{
				StringWriter sw = new StringWriter(1024);

				IOUtils.copy(new FileInputStream(this.origFile), sw);

				return sw.toString().getBytes();
			}

			return null;
		}
		catch (IOException e)
		{
			throw new RuntimeException("Impossibile restituire il contenuto del documento", e);
		}
	}

	public String getSHA1Hash()
	{
		return getHash("SHA-1");
	}

	public String getSHA256Hash()
	{
		return getHash("SHA-256");
	}

	public String getHash(String algoritmo)
	{
		if ((this.hash != null) && (this.hash.containsKey(algoritmo)))
		{
			return (String) this.hash.get(algoritmo);
		}

		String h;
		if (this.signed)
		{
			if (this.hash == null)
				this.hash = new HashMap();

			this.hash.put(algoritmo, h = FirmaUtils.getDigestBase64(this.document, algoritmo));
		}
		else
		{
			try
			{
				InputStream is = this.origFile != null ? new FileInputStream(this.origFile) : new ByteArrayInputStream(this.origBytes);

				if (this.hash == null)
				{
					this.hash = new HashMap();
				}
				this.hash.put(algoritmo, h = FirmaUtils.getDigestBase64(is, algoritmo));
				is.close();
			}
			catch (Exception e)
			{
				throw new RuntimeException("Impossibile calcolare il digest del file plain");
			}
		}

		return h;
	}

	public List<Signature> getSignatures()
	{
		if (!this.signed)
		{
			return null;
		}

		if (!this.parsed)
		{
			parseDocument();
		}
		return this.signatures;
	}

	public Set<String> getSigners()
	{
		if (!this.signed)
		{
			return null;
		}
		if (!this.parsed)
		{
			parseDocument();
		}
		return this.signers;
	}

	public boolean isSigned()
	{
		return this.signed;
	}

	public boolean isValid()
	{
		if (!this.signed)
		{
			return false;
		}
		if (!this.parsed)
		{
			parseDocument();
		}
		return this.valid;
	}

	private static void testProviders()
	{
		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null)
		{
			Security.addProvider(new BouncyCastleProvider());
			logger.warn("Provider " + BouncyCastleProvider.PROVIDER_NAME + " non trovato. Aggiunto a Security!");
		}
	}

	private void parseDocument()
	{
		this.parsed = true;

		this.signatures = new ArrayList();
		this.signers = new HashSet();

		this.valid = false;
		this.hash = null;

		try
		{
			testProviders();

			CertStore certs = null;
			try
			{
				certs = this.document.getCertificatesAndCRLs("Collection", BouncyCastleProvider.PROVIDER_NAME);
			}
			catch (NoSuchProviderException e)
			{
				String errMsg = "Impossibile leggere i certificati. Cercavo il provider: " + BouncyCastleProvider.PROVIDER_NAME;
				logger.error(errMsg);

				Provider[] providers = Security.getProviders();

				for (int i = 0; i < providers.length; i++)
				{
					logger.info("Provider: " + providers[i].getName() + " V: " + providers[i].getVersion());
				}
				throw new RuntimeException(errMsg, e);
			}

			SignerInformationStore signers = this.document.getSignerInfos();
			Collection c = signers.getSigners();
			Iterator it = c.iterator();
			int nFirmeValide = 0;
			int nFirme = 0;
			SignerInformation signer;
			while (it.hasNext())
			{
				signer = (SignerInformation) it.next();
				Collection<? extends Certificate> certCollection = certs.getCertificates(signer.getSID());

				if ((certCollection != null) && (!certCollection.isEmpty()))
				{

					for (Certificate cert : certCollection)
					{
						if (logger.isDebugEnabled())
						{
							logger.debug("Certificato trovato: \n" + cert.toString() + "\n");
						}

						boolean v = signer.verify(cert.getPublicKey(), BouncyCastleProvider.PROVIDER_NAME);

						Signature s = getSign((X509Certificate) cert, this.certificateParser);
						s.setValid(v);

						addSignature(s);

						nFirme++;

						if (v)
						{
							nFirmeValide++;
						}
					}
				}
			}

			if (nFirmeValide == 0)
			{
				logger.error("Non e' stato trovato alcun certificato valido");
			}
			else
			{
				logger.info("Trovati n. " + nFirmeValide + " certificati validi su " + nFirme);

				this.valid = (nFirme == nFirmeValide);
			}
		}
		catch (Exception ex)
		{
			logger.error("Impossibile verificare la firma", ex);
			throw new RuntimeException(ex);
		}
	}

	protected Signature getSign(X509Certificate cert, CertificateParser parser)
	{
		return FirmaUtils.getSign(cert, parser);
	}

	private void addSignature(Signature s)
	{
		if (this.signatures == null)
		{
			this.signatures = new ArrayList();
			this.signers = new HashSet();
		}

		this.signatures.add(s);
		if (s.getCf() != null)
		{
			this.signers.add(s.getCf().toUpperCase());
		}
	}
}