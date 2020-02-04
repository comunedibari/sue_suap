package it.ictechnology.util.jaxp.sax;

import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class URLEntityResolver implements EntityResolver
{
	private static final Logger logger = LoggerFactory.getLogger(URLEntityResolver.class);

	private ClassLoader cl;

	public URLEntityResolver()
	{
		this(Thread.currentThread().getContextClassLoader());
	}

	public URLEntityResolver(ClassLoader cl)
	{
		this.cl = (cl == null ? getClass().getClassLoader() : cl);
	}

	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("resolving entity: publicId=" + publicId + ", systemId=" + systemId);
		}
		if (systemId != null)
		{
			String systemId2 = systemId.substring(systemId.lastIndexOf('/') + 1);

			URL r = this.cl.getResource(systemId2);

			if (r != null)
			{
				if (logger.isInfoEnabled())
				{
					logger.info("resolved url: systemId=" + systemId + ", url=" + r);
				}
				return new InputSource(r.toString());
			}

			if (logger.isInfoEnabled())
			{
				logger.info("unresolved url: systemId=" + systemId + ", classLoader=" + this.cl);
			}
			return null;
		}

		if (logger.isInfoEnabled())
		{
			logger.info("can't resolve, missing systemId");
		}
		return null;
	}
}