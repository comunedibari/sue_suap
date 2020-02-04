package it.ictechnology.certificate.parser.impl;

import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

public class Program
{
	private Collection<TypeDetection> detection;
	private Map<String, Format> formats;

	public static abstract interface TypeDetection
	{
		public abstract String resolveFormatName(X509Certificate paramX509Certificate);
	}

	public static class Alias implements Program.TypeDetection
	{
		private final Pattern issuerMatcher;
		private final String formatName;

		public Alias(Pattern issuerMatcher, String formatName)
		{
			if (issuerMatcher == null)
				throw new NullPointerException("missing issuerMatcher");
			if (formatName == null)
			{
				throw new NullPointerException("missing formatName");
			}
			this.issuerMatcher = issuerMatcher;
			this.formatName = formatName;
		}

		public String resolveFormatName(X509Certificate certificate)
		{
			String issuer = certificate.getIssuerDN().getName();

			if (this.issuerMatcher.matcher(issuer).find())
			{
				return this.formatName;
			}
			return null;
		}
	}

	public static class JavaDetection implements Program.TypeDetection
	{
		private CertificateTypeDetector detector;

		public JavaDetection(CertificateTypeDetector detector)
		{
			if (detector == null)
			{
				throw new NullPointerException("missing detector");
			}
			this.detector = detector;
		}

		public String resolveFormatName(X509Certificate certificate)
		{
			return this.detector.resolveType(certificate);
		}
	}

	public static class Property
	{
		private String javaProperty;

		private String attribute;

		private int groupSelection;

		private Pattern matcher;

		public String getJavaProperty()
		{
			return this.javaProperty;
		}

		public void setJavaProperty(String javaProperty)
		{
			this.javaProperty = javaProperty;
		}

		public String getAttribute()
		{
			return this.attribute;
		}

		public void setAttribute(String attribute)
		{
			this.attribute = attribute;
		}

		public int getGroupSelection()
		{
			return this.groupSelection;
		}

		public void setGroupSelection(int groupSelection)
		{
			this.groupSelection = groupSelection;
		}

		public Pattern getMatcher()
		{
			return this.matcher;
		}

		public void setMatcher(Pattern matcher)
		{
			this.matcher = matcher;
		}

		@Override
		public String toString()
		{
			return "Property [javaProperty=" + javaProperty + ", attribute=" + attribute + ", groupSelection=" + groupSelection + ", matcher=" + matcher + "]";
		}
		
	}

	public static class Format
	{
		Collection<Program.Property> properties;

		public Collection<Program.Property> getProperties()
		{
			return this.properties;
		}

		public void setProperties(Collection<Program.Property> properties)
		{
			this.properties = properties;
		}
	}

	public Collection<TypeDetection> getDetection()
	{
		return this.detection;
	}

	public void setDetection(Collection<TypeDetection> detection)
	{
		this.detection = detection;
	}

	public Map<String, Format> getFormats()
	{
		return this.formats;
	}

	public void setFormats(Map<String, Format> formats)
	{
		this.formats = formats;
	}
}