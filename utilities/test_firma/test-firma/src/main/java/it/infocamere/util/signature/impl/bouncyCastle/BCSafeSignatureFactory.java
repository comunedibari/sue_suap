package it.infocamere.util.signature.impl.bouncyCastle;

import it.ictechnology.certificate.parser.CertificateParser;
import it.infocamere.util.signature.SignatureFactory;
import it.infocamere.util.signature.SignedDocument;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class BCSafeSignatureFactory implements SignatureFactory
{
	private CertificateParser certificateParser;

	public CertificateParser getCertificateParser()
	{
		return this.certificateParser;
	}

	public void setCertificateParser(CertificateParser certificateParser)
	{
		this.certificateParser = certificateParser;
	}

	public SignedDocument createDocument(byte[] bytes)
	{
		return new BCSafeSignedDocumentImpl(bytes, this.certificateParser);
	}

	public SignedDocument createDocument(File file) throws FileNotFoundException
	{
		return new BCSafeSignedDocumentImpl(file, this.certificateParser);
	}

	public SignedDocument createDocument(InputStream inputStream)
	{
		return new BCSafeSignedDocumentImpl(inputStream, this.certificateParser);
	}
}
