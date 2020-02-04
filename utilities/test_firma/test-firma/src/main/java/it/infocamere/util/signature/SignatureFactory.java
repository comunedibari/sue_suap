package it.infocamere.util.signature;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public abstract interface SignatureFactory
{
	public abstract SignedDocument createDocument(byte[] paramArrayOfByte);

	public abstract SignedDocument createDocument(File paramFile) throws FileNotFoundException;

	public abstract SignedDocument createDocument(InputStream paramInputStream);
}
