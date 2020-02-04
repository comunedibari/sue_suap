package it.ictechnology.certificate.util;

import java.security.cert.X509Certificate;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CertificateUtils
{
	private static final Logger logger = LoggerFactory.getLogger(CertificateUtils.class);

	public static String getParamValue(String str, String startStr)
	{
		int start = str.toUpperCase().indexOf(startStr.toUpperCase());

		if (start == -1)
		{
			return "";
		}
		start += startStr.length();

		int end = str.indexOf(',', start);

		return end == -1 ? str.substring(start) : str.substring(start, end);
	}

	public static boolean isCNS(X509Certificate cert)
	{
		boolean[] array = cert.getKeyUsage();

		if (array == null)
		{
			return false;
		}
		if (array[0])
		{
			if ((cert.getExtensionValue("1.3.76.14.1.1.11") != null) || (commonNameIsCNS(cert)))
				return true;
		}
		return false;
	}

	private static final Pattern cnsCommonName = Pattern.compile("^\"?[A-Z0-9]{16}/[A-Z0-9]+\\.[^=]+=\"?$");

	public static boolean commonNameIsCNS(X509Certificate cert)
	{
		String cn = getParamValue(cert.getSubjectDN().getName(), "CN=");

		return cnsCommonName.matcher(cn).matches();
	}

	private static final Pattern serialForFirmaQualificata = Pattern.compile("^(IT|PE):(.{16}|[0-9]{11})$");
	//private static final Pattern serialForFirmaQualificataNuova = Pattern.compile("^(TINIT)-(.{16}|[0-9]{11})$");

	public static boolean isFirmaQualificata(X509Certificate cert)
	{
		boolean[] array = cert.getKeyUsage();

		if (array == null)
		{
			return false;
		}

		if ((!array[0]) && (!array[1]))
		{
			return false;
		}

		String sn = getParamValue(cert.getSubjectDN().getName(), "SERIALNUMBER=");

		return (sn != null) && (serialForFirmaQualificata.matcher(sn).find()); //|| serialForFirmaQualificataNuova.matcher(sn).find());
	}
}