package it.ictechnology.certificate.parser.impl;

import it.ictechnology.certificate.util.CertificateUtils;
import java.security.cert.X509Certificate;

public class CNSDetector implements CertificateTypeDetector
{
	public String resolveType(X509Certificate certificate)
	{
		return CertificateUtils.isCNS(certificate) ? "cns" : null;
	}
}
