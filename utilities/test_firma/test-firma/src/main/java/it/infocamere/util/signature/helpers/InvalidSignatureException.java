package it.infocamere.util.signature.helpers;

public class InvalidSignatureException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public InvalidSignatureException(String messaggio)
	{
		super(messaggio);
	}
}