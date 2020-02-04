package it.ictechnology.certificate.parser.impl;

import java.security.cert.X509Certificate;

public abstract interface CertificateTypeDetector
{
	public abstract String resolveType(X509Certificate paramX509Certificate);
}