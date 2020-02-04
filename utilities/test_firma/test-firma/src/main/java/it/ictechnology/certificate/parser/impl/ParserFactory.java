package it.ictechnology.certificate.parser.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import it.ictechnology.certificate.parser.CertificateParser;
import it.ictechnology.util.jaxp.sax.SAXUtility;

public class ParserFactory
{
	public CertificateParser parse(URL xmlSourceUrl) throws SAXException, IOException
	{
		return parse(new InputSource(xmlSourceUrl.toString()));
	}

	public Program parseProgram(URL xmlSourceUrl) throws SAXException, IOException
	{
		return parseProgram(new InputSource(xmlSourceUrl.toString()));
	}

	public CertificateParser parse(InputSource xmlSource) throws SAXException, IOException
	{
		CertificateParserImpl res = new CertificateParserImpl();

		Program program = parseProgram(xmlSource);

		res.setProgram(program);

		return res;
	}

	public Program parseProgram(InputSource xmlSource) throws SAXException, IOException
	{
		Program res = new Program();

		res.setDetection(new ArrayList());
		res.setFormats(new HashMap());

		SAXUtility.parse(false, false, xmlSource, new ProgramHandler(res));

		return res;
	}

	private static class ProgramHandler extends DefaultHandler
	{
		private final Collection<Program.TypeDetection> detection;
		private final Map<String, Program.Format> formats;
		private Collection<Program.Property> properties;
		private String propertyPrefix = "";
		private boolean javaProperty = false;

		public ProgramHandler(Program program)
		{
			this.detection = program.getDetection();
			this.formats = program.getFormats();
		}

		public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException
		{
			if ("format-alias".equals(name))
			{
				this.detection.add(new Program.Alias(Pattern.compile(attributes.getValue("issuer-match")), attributes.getValue("format-name")));

			}
			else if ("format-java-detector".equals(name))
			{
				try
				{
					this.detection.add(new Program.JavaDetection((CertificateTypeDetector) Class.forName(attributes.getValue("class")).newInstance()));

				}
				catch (Exception e)
				{

					throw new RuntimeException("Invalid program", e);
				}
			}
			else if ("format".equals(name))
			{
				Program.Format currentFormat = new Program.Format();

				currentFormat.setProperties(new ArrayList());

				this.properties = currentFormat.getProperties();

				this.formats.put(attributes.getValue("name"), currentFormat);
			}
			else if (this.properties != null)
			{
				String attributeAttr = attributes.getValue("attribute");

				if (attributeAttr != null)
				{
					Program.Property p = new Program.Property();

					p.setAttribute(attributeAttr);
					p.setJavaProperty(this.propertyPrefix + name);

					String groupAttr = attributes.getValue("group");
					String matcherAttr = attributes.getValue("matcher");

					if ((matcherAttr != null) && (matcherAttr.length() > 0))
					{
						p.setMatcher(Pattern.compile(matcherAttr));

						if ((groupAttr != null) && (groupAttr.length() > 0))
						{
							p.setGroupSelection(Integer.parseInt(groupAttr));
						}
					}
					this.properties.add(p);

					this.javaProperty = true;
				}
				else
				{
					this.propertyPrefix = (this.propertyPrefix + attributeAttr + '.');
				}
			}
		}

		public void endElement(String uri, String localName, String name) throws SAXException
		{
			if ("format".equals(name))
			{
				this.properties = null;
			}
			else if (this.properties != null)
			{
				if (this.javaProperty)
				{
					this.javaProperty = false;
				}
				else
				{
					int idx = this.propertyPrefix.lastIndexOf('.');

					if (idx == -1)
					{
						this.propertyPrefix = "";
					}
					else
					{
						this.propertyPrefix = this.propertyPrefix.substring(idx);
					}
				}
			}
		}
	}
}