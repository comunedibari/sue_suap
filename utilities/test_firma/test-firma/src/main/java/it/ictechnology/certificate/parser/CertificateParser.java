package it.ictechnology.certificate.parser;

import java.security.cert.X509Certificate;

public abstract interface CertificateParser
{
	public abstract void parse(X509Certificate paramX509Certificate, Object paramObject);
}
