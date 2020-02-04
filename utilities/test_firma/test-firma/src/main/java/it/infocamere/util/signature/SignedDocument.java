package it.infocamere.util.signature;

import java.util.List;
import java.util.Set;

public abstract interface SignedDocument
{
	public abstract boolean isSigned();

	public abstract boolean isValid();

	public abstract List<Signature> getSignatures();

	public abstract Set<String> getSigners();

	public abstract String getSHA1Hash();

	public abstract String getSHA256Hash();

	public abstract String getHash(String paramString);

	public abstract byte[] getDocumentBytes();
}
