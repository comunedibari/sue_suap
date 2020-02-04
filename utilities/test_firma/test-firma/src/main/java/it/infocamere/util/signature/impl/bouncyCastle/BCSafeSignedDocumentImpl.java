package it.infocamere.util.signature.impl.bouncyCastle;

import it.ictechnology.certificate.parser.CertificateParser;
import it.infocamere.util.signature.Signature;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.cert.X509Certificate;

public class BCSafeSignedDocumentImpl extends BCSignedDocumentImpl
{
	public BCSafeSignedDocumentImpl(byte[] bytes, CertificateParser certificateParser)
	{
		super(bytes, certificateParser);
	}

	public BCSafeSignedDocumentImpl(File file, CertificateParser certificateParser) throws FileNotFoundException
	{
		super(file, certificateParser);
	}

	public BCSafeSignedDocumentImpl(InputStream inputStream, CertificateParser certificateParser)
	{
		super(inputStream, certificateParser);
	}

	protected Signature getSign(X509Certificate cert, CertificateParser parser)
	{
		return FirmaUtils.getSafeSign(cert, parser);
	}
}